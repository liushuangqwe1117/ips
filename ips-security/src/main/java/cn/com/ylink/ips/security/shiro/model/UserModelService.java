package cn.com.ylink.ips.security.shiro.model;

import java.util.Date;

public interface UserModelService {

	/**
	 * 根据登录名获取用户信息
	 * 
	 * @param username
	 * @return
	 * @author LS
	 * @date 2013-7-30
	 */
	UserModel getUserModel(String username);

	/**
	 * 锁定账户
	 * 
	 * @param userId
	 *            账户ID
	 * @param currentDate
	 *            锁定时间
	 * @return
	 * @author LS
	 * @date 2013-7-30
	 */
	boolean lock(String userId, Date currentDate);

	/**
	 * 记录用户登录成功日志
	 * 
	 * @param user
	 *            用户信息
	 * @param loginSuccessDate
	 *            成功登录时间
	 * @param ip
	 *            用户IP
	 * @author LS
	 * @date 2013-7-30
	 */
	void logLoginSuccess(UserModel user, Date loginSuccessDate, String ip);

	/**
	 * 记录用户登录失败日志
	 * 
	 * @param user
	 *            用户信息
	 * @param loginFailureDate
	 *            登录失败时间
	 * @param ip
	 *            用户IP
	 * @param sessionId
	 *            会话ID
	 * @param exceptionMsg
	 *            登录异常信息
	 * @author LS
	 * @date 2013-7-30
	 */
	void logLoginFailure(UserModel user, Date loginFailureDate, String ip,
			String sessionId, String exceptionMsg);
}
