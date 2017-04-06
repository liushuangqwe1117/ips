/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2012-8-7
 */

package cn.com.ylink.ips.web.base.util;

import java.math.BigInteger;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-8-7
 * @description：防止重复提交工具类
 */

public class TokenUtil {
	
	private static Logger logger = LoggerFactory.getLogger(TokenUtil.class);
	
	public static final String TOKEN_NAME = "_token_name_";
	
	private static final Random RANDOM = new Random();
	
	/**
	 * @description 生成token放到session中,token的key值为 tokenKey
	 * @param session
	 * @param tokenKey
	 * @return  
	 * @author ZhangDM(Mingly)
	 * @date 2012-8-7
	 */
	public static String setToken(HttpSession session, String tokenKey) {
		String tokenValue = generateGUID();
    	session.setAttribute(tokenKey, tokenValue);
	    return tokenValue;
	}
	
	/**
	 * @description 序列生成器
	 * @return  
	 * @author ZhangDM(Mingly)
	 * @date 2012-8-7
	 */
	public static String generateGUID() {
		return new BigInteger(165, RANDOM).toString(36).toUpperCase();
	}
	
	/**
	 * @description 判断是否重复提交
	 * @param request
	 * @return  true：重复提交
	 * 			false:没有重复提交
	 * @author ZhangDM(Mingly)
	 * @date 2012-8-7
	 */
	public static boolean isDuplicateSubmit(HttpServletRequest request) {
		
	    return !validToken(request.getParameterMap(), request.getSession());
	}
	
	 /**
	 * @description 判断令牌是否有效
	 * @param params
	 * @param session
	 * @return  
	 * @author ZhangDM(Mingly)
	 * @date 2012-8-7
	 */
	private static boolean validToken(Map params, HttpSession session){
	    
		if(!isExistTokenFromParam(params)){
			logger.warn("没有传来令牌,请在form中添加token标签以该防止重复提交");
	    	return false;
	    }

	    String tokenKey = getTokenKeyFromParam(params);

	    if (tokenKey == null) {
    		logger.warn("从传来令牌KEY值为空,请检查.程序出现异常现象");
	    	return false;
	    }

	    String tokenValue = getTokenValueFromParam(params, tokenKey);

	    if (tokenValue == null) {
    		logger.warn("从传来令牌值为空,请检查代码!");
	    	return false;
	    }

	    String tokenValueInSession = (String)session.getAttribute(tokenKey);

	    if ((tokenValueInSession == null) || (!tokenValue.equals(tokenValueInSession))) {
	    	return false;
	    }

	    session.removeAttribute(tokenKey);
	    return true;
	  }
	 
	 /**
	 * @description 判断参数中是否存在token的key
	 * @param params
	 * @return  
	 * @author ZhangDM(Mingly)
	 * @date 2012-8-7
	 */
	private static boolean isExistTokenFromParam(Map params){
		
		if (!params.containsKey(TOKEN_NAME)) {
			logger.warn("没有在参数传来令牌的名字,请检查.前台是否有添加token的jsp标签");
			return false;
		}
		return true;
	}
	
	/**
	 * @description 得到保存在session中令牌的key
	 * @param params
	 * @return  
	 * @author ZhangDM(Mingly)
	 * @date 2012-8-7
	 */
	private static String getTokenKeyFromParam(Map params){
		
		String[] tokens = (String[])params.get(TOKEN_NAME);

	    if ((tokens == null) || (tokens.length == 0)) {
	      return null;
	    }
	    return tokens[(tokens.length - 1)];
	}
	
	/**
	 * @description 得到令牌的value
	 * @param params
	 * @param tokenKey
	 * @return  
	 * @author ZhangDM(Mingly)
	 * @date 2012-8-7
	 */
	private static String getTokenValueFromParam(Map params, String tokenKey){
	    
		String[] tokenValues = (String[])params.get(tokenKey);

	    if ((tokenValues == null) || (tokenValues.length == 0)) {
	    	return null;
	    }
	    return tokenValues[tokenValues.length - 1];
	  }

}
