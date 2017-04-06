/**
 * 版权所有(C) 2013 深圳市雁联计算系统有限公司
 * 创建：LS 2013-8-13
 */
package cn.com.ylink.ips.security.shiro;

import cn.com.ylink.ips.security.shiro.model.UserModel;

/**
 * @author LS
 * @date 2013-8-13
 * @description
 */
public class UserModelContext {

	private static ThreadLocal<UserModel> threadLocal = new ThreadLocal<UserModel>();
	
	public static UserModel get(){
		return threadLocal.get();
	}
	
	public static void set(UserModel userModel){
		threadLocal.set(userModel);
	}
	
	public static void remove(){
		threadLocal.remove();
	}
	
}
