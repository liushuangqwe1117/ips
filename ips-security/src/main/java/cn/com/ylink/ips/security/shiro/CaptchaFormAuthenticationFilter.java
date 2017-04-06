package cn.com.ylink.ips.security.shiro;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.ylink.ips.security.shiro.config.ShiroConfig;
import cn.com.ylink.ips.security.shiro.exception.ShiroBadCaptchaException;
import cn.com.ylink.ips.security.shiro.exception.TimeLockedException;
import cn.com.ylink.ips.security.shiro.model.UserModel;
import cn.com.ylink.ips.security.shiro.model.UserModelService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import cn.com.ylink.ips.core.util.RequestUtil;
import cn.com.ylink.ips.web.base.common.SessionKey;

public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter {
	private static Logger log = LoggerFactory
			.getLogger(CaptchaFormAuthenticationFilter.class);
	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";
	protected String captchaParam = "captcha";
	@Autowired
	@Qualifier("shiroConfig")
	protected ShiroConfig shiroConfig;
	protected UserModelService userModelService;
	protected Cache onlineUserCache;

	public String getCaptchaParam() {
		return this.captchaParam;
	}

	public void setCaptchaParam(String captchaParam) {
		this.captchaParam = captchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	public void setUserModelService(UserModelService userModelService) {
		this.userModelService = userModelService;
	}

	public void setOnlineUserCache(Cache cache) {
		this.onlineUserCache = cache;
	}

	protected CaptchaUsernamePasswordToken createToken(ServletRequest request,
			ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		String captcha = getCaptcha(request);
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);

		return new CaptchaUsernamePasswordToken(username, password, rememberMe,
				host, captcha);
	}

	protected void doCaptchaValidate(HttpServletRequest request,
			CaptchaUsernamePasswordToken token) {
		if (isCheckCaptcha(request)) {
			String captcha = (String) request.getSession().getAttribute(
					getKaptchaSessionKey());

			if ((captcha != null)
					&& (!captcha.equalsIgnoreCase(token.getCaptcha())))
				throw new ShiroBadCaptchaException("验证码错误！");
		}
	}

	protected String getKaptchaSessionKey() {
		return "kaptcha";
	}

	protected void doBusinessValidate(UserModel account) {
		if (account == null) {
			return;
		}
		if (!account.isEnabled()) {
			throw new LockedAccountException("用户账号已禁用！");
		}
		if (account.isExpired()) {
			throw new ExpiredCredentialsException("用户账号已过期！");
		}
		Calendar now = Calendar.getInstance();
		Date lockedTime = account.getLastLoginLockTime();
		if (lockedTime != null) {
			Calendar lockedCal = Calendar.getInstance();
			lockedCal.setTime(lockedTime);
			int minutes = 10;
			try {
				minutes = Integer.parseInt(this.shiroConfig.getProperty(
						"login.failure.lock.minutes", "10"));
			} catch (Exception e) {
				log.warn("登录锁定时间设置为10分钟。");
			}

			lockedCal.add(Calendar.MINUTE, minutes);
			int remain = lockedCal.get(Calendar.MINUTE) - now.get(Calendar.MINUTE);
			if (now.compareTo(lockedCal) < 1)
				throw new TimeLockedException("用户账号已被锁定，"
						+ (remain > 0 ? remain + "分钟后重试" : "请稍后重试") + "！");
		}
	}

	protected UserModel queryUser(String username) {
		if (username == null) {
			return null;
		}
		if (this.userModelService != null) {
			return this.userModelService.getUserModel(username);
		}
		log.warn("无法查询用户信息，UserService.getUser()接口未实现。");

		return null;
	}

	private void setFailedCount(HttpServletRequest request, int count) {
		request.getSession().setAttribute(SessionKey.LOGIN_FAILURE_COUNT,
				Integer.valueOf(count));
	}

	private int getFailedCount(HttpServletRequest request) {
		Integer failed_count = (Integer) request.getSession().getAttribute(
				SessionKey.LOGIN_FAILURE_COUNT);
		return failed_count == null ? 0 : failed_count.intValue();
	}

	protected void incFailedCount(HttpServletRequest request) {
		setFailedCount(request, getFailedCount(request) + 1);
	}

	protected void clearFailedCount(HttpServletRequest request) {
		setFailedCount(request, 0);
	}

	protected boolean doLock(UserModel user, int failed_count) {
		
		boolean locked = false;
		if (this.userModelService == null || user == null) {
			return false;
		}
		int times = 3;
		try {
			times = Integer.parseInt(this.shiroConfig.getProperty(
					"login.failure.lock.times", "3"));
		} catch (Exception localException1) {
		}
		if (failed_count >= times) {
			try {
				locked = this.userModelService.lock(user.getUserId(),
						new Date());
			} catch (Exception e) {
				log.warn("LoginAccountService.lock调用异常：", e);
			}
		}

		return locked;
	}

	protected boolean isCheckCaptcha(HttpServletRequest request) {
		boolean enabled = "true".equalsIgnoreCase(this.shiroConfig
				.getProperty("captcha.enabled", "false"));
		int times = 0;
		try {
			times = Integer.parseInt(this.shiroConfig.getProperty(
					"captcha.login.ignore.times", "0"));
		} catch (Exception localException) {
		}
		return (enabled) && (getFailedCount(request) >= times);
	}

	protected void writeCookie(HttpServletRequest request,
			HttpServletResponse response, String cookieName, String cookieValue) {
		if (cookieName == null)
			return;
		try {
			if (cookieValue != null)
				cookieValue = URLEncoder.encode(cookieValue, "utf-8");
		} catch (Exception localException) {
		}
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setPath(request.getContextPath());
		response.addCookie(cookie);
	}

	protected boolean executeLogin(ServletRequest request,
			ServletResponse response) throws Exception {
		CaptchaUsernamePasswordToken token = createToken(request, response);
		if (token == null) {
			String msg = "方法createToken()返回空值，登录操作必须获得非空的AuthenticationToken！";
			throw new IllegalStateException(msg);
		}

		String ip = RequestUtil.getRealRemoteAddr((HttpServletRequest) request);

		UserModel account = queryUser(token.getUsername());
		try {
			//校验验证码
			doCaptchaValidate((HttpServletRequest) request, token);
			
			doBusinessValidate(account);

			Subject subject = getSubject(request, response);
			subject.login(token);

			clearFailedCount((HttpServletRequest) request);

			Cookie cookie = new Cookie("REFRESH-HEADER", "TRUE");
			cookie.setPath(((HttpServletRequest) request).getContextPath());
			((HttpServletResponse) response).addCookie(cookie);

			this.userModelService.logLoginSuccess(account, new Date(), ip);
			//登录成功
			MySessionListener.putUser(account.getUserName(), ((HttpServletRequest) request).getSession().getId());
			writeSession(account, (HttpServletRequest) request);
			return onLoginSuccess(token, subject, request, response);
		} catch (AuthenticationException e) {
			if (!(e instanceof TimeLockedException)) {
				incFailedCount((HttpServletRequest) request);

				this.userModelService.logLoginFailure(account, new Date(), ip,
						((HttpServletRequest) request).getSession().getId(),
						e.getMessage());
				int failed_count = getFailedCount((HttpServletRequest) request);
				boolean locked = doLock(account, failed_count);
				if (locked) {
					log.warn("登录帐户[{}]在用户[{}]登录时被锁定。", account.getUserId(),
							token.getUsername());
				}
			}
			return onLoginFailure(token, e, request, response);
		}
	}

	/**
	 * 将用户写入Session
	 * @param account
	 * @param request
	 * @author LS
	 * @date 2013-8-1
	 */
	protected void writeSession(UserModel account, HttpServletRequest request) {
		request.getSession().setAttribute(SessionKey.LOGIN_ACCOUNT, account);

		if (this.onlineUserCache != null) {
			String id = account.getUserName();
			this.onlineUserCache.put(new Element(id, new OnlineUser(id, request
					.getSession().getId(), RequestUtil
					.getRealRemoteAddr(request))));
		}
	}

	@Override
	protected void saveRequestAndRedirectToLogin(ServletRequest request,
			ServletResponse response) throws IOException {
		redirectToLogin(request, response);
	}

	protected void setFailureAttribute(ServletRequest request,
			AuthenticationException ae) {
		request.setAttribute(getFailureKeyAttribute(), ae);
	}

	public String getSuccessUrl() {
		return super.getSuccessUrl();
	}

	public String getLoginUrl() {
		if ((this.shiroConfig == null)
				|| (!"true".equalsIgnoreCase(this.shiroConfig.getProperty(
						"cas.enabled", "false")))) {
			return super.getLoginUrl();
		}
		return this.shiroConfig.getProperty("cas.server.url.prefix")
				+ "?service=" + this.shiroConfig.getProperty("cas.service");
	}
}
