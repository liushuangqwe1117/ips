package cn.com.ylink.ips.web.base.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-7-29
 * @description：异常信息转换类
 */

public class ExceptionMsgUtil {
	
	private static Logger logger = LoggerFactory.getLogger(ExceptionMsgUtil.class);
	
	private static String EXCEPTIONMSG_PROPERTIES_NAME = "properties/exceptionMsg";
	
	private static Map<String, String> exceptionMsgMap = new HashMap<String, String>();
	
	static{
		ResourceBundle rb = null;
		try{
			rb = ResourceBundle.getBundle(EXCEPTIONMSG_PROPERTIES_NAME);
		}catch(Exception e){
			logger.warn(EXCEPTIONMSG_PROPERTIES_NAME + ".properties is not existed");
		}
		
		if(rb != null){
			Enumeration<String> e = rb.getKeys();
			while (e.hasMoreElements()) {
				String key = e.nextElement();
				String value = rb.getString(key);
				exceptionMsgMap.put(key, value);
			}
		}
	}
	
	
	/**
	 * @description 通过代号得到异常信息
	 * @param code
	 * @return  String
	 * @author ZhangDM(Mingly)
	 * @date Dec 8, 2011
	 */
	public static String getExceptionMsg(String code){
		
		return exceptionMsgMap.get(code);
	}
}
