/**
 * 版权所有(C) 2014 深圳市雁联计算系统有限公司
 * 创建:LS 2014-4-8
 */

package cn.com.ylink.ips.security.shiro;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/** 
 * @author LS
 * @date 2014-4-8
 * @description：TODO
 */

public class MySessionListener implements HttpSessionListener {
	
	private static Map<String,String>  userLoginMap = new HashMap<String,String>();

	@Override
	public void sessionCreated(HttpSessionEvent se) {
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		if(se.getSession() != null && se.getSession().getId() != null && !"".equals(se.getSession().getId()))
			removeUserByCookie(se.getSession().getId());
	}
	
	public static void putUser(String username , String cookie){
		userLoginMap.put(username, cookie);
	}
	
	public static void removeUserByUsername(String username){
		userLoginMap.remove(username);
	}
	
	public static String getCookieId(String username){
		return userLoginMap.get(username);
	}
	
	public static void removeUserByCookie(String cookie){
		if(userLoginMap.containsValue(cookie)){
			Iterator<Entry<String, String>> entryList = userLoginMap.entrySet().iterator();
			Entry<String, String> keyValue = null; 
			while(entryList.hasNext()){
				keyValue = entryList.next();
				if(keyValue.getValue().equals(cookie)){
					userLoginMap.remove(keyValue.getKey());
					break;
				}
			}
		}
	}
	
	/**
	 * 判断用户是否已经登录，如果登录则返回true，否则返回false
	 * @description 
	 * @param username
	 * @return  
	 * @author LS
	 * @date 2014-4-8
	 */
	public static boolean userHasLogin(String username){
		return userLoginMap.containsKey(username);
	}
	
}
