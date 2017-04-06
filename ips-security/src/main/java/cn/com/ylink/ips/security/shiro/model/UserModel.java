package cn.com.ylink.ips.security.shiro.model;

import java.io.Serializable;
import java.util.Date;

public interface UserModel extends Serializable {

	/**
	 * 用户ID
	 * 
	 * @return
	 * @author LS
	 * @date 2013-7-30
	 */
	String getUserId();

	/**
	 * 登录账号名
	 * 
	 * @return
	 * @author LS
	 * @date 2013-7-30
	 */
	String getUserName();
	
	/**
	 * 获取的登陆用户真实姓名
	 * @return
	 * @author LS
	 * @date 2013-8-13
	 */
	String getRealName();

	/**
	 * 登录密码
	 * 
	 * @return
	 * @author LS
	 * @date 2013-7-30
	 */
	String getPassword();

	/**
	 * 最后一次登录锁定时间
	 * 
	 * @return
	 * @author LS
	 * @date 2013-7-30
	 */
	Date getLastLoginLockTime();

	/**
	 * 账户是否启用
	 * 
	 * @return
	 * @author LS
	 * @date 2013-7-30
	 */
	boolean isEnabled();

	/**
	 * 账户是否过期
	 * 
	 * @return
	 * @author LS
	 * @date 2013-7-30
	 */
	boolean isExpired();

}
