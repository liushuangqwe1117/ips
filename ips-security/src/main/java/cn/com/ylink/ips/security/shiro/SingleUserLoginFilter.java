package cn.com.ylink.ips.security.shiro;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.Cache;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.com.ylink.ips.core.util.StringUtil;
import cn.com.ylink.ips.security.shiro.exception.OnlineUserExistException;
import cn.com.ylink.ips.web.base.common.SessionKey;

public class SingleUserLoginFilter extends AccessControlFilter {
	private static Logger log = LoggerFactory.getLogger(SingleUserLoginFilter.class);
	public static final String DEFAULT_LOGOUT_URL = "/logout.do";
	private String logoutUrl = DEFAULT_LOGOUT_URL;
	private Cache onlineUserCache;
	/**
	 * 保存已经登录的用户
	 */
	private static Map<String, String> userExistMap = Collections.synchronizedMap(new HashMap<String, String>());

	public static void setUserSessionId(String key, String sessionid) {
		userExistMap.put(key, sessionid);
	}

	public static String getUserSessionId(String key) {
		return userExistMap.get(key);
	}

	public String getLogoutUrl() {
		return this.logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	public void setOnlineUserCache(Cache cache) {
		this.onlineUserCache = cache;
	}

	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		if (this.onlineUserCache == null) {
			return true;
		}
		if ((!(request instanceof HttpServletRequest)) || (((HttpServletRequest) request).getSession() == null)) {
			return true;
		}
		Subject subject = getSubject(request, response);
		if (subject == null || subject.getPrincipal() == null) {
			return true;
		}
		String username = subject.getPrincipal().toString();
		if (StringUtil.isEmpty(username)) {
			return true;
		}
		String sessionId = ((HttpServletRequest) request).getSession().getId();

		if ((this.onlineUserCache.get(username) != null) && (this.onlineUserCache.get(username).getValue() != null)) {
			OnlineUser online = (OnlineUser) this.onlineUserCache.get(username).getValue();

			if (!sessionId.equals(online.getSessionid())) {
				OnlineUserExistException ouee = new OnlineUserExistException(online);
				((HttpServletRequest) request).getSession().setAttribute(SessionKey.EXCEPTION_OBJECT, ouee);
				setUserSessionId(username, online.getSessionid());
				log.info("登录用户[{}],SESSION ID[{}]由于重复登录下线。", username, sessionId);

				return false;
			}
		}

		return true;
	}

	protected void redirectToLogout(ServletRequest request, ServletResponse response) throws IOException {
		WebUtils.issueRedirect(request, response, getLogoutUrl());
	}

	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		redirectToLogout(request, response);

		return false;
	}
}