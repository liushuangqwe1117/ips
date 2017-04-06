/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2012-8-17
 */

package cn.com.ylink.ips.core.util.support;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-8-17
 * @description：json时间格式类
 */

public class DateJsonValueProcessor implements JsonValueProcessor {
	
	public static final String Default_DATE_PATTERN ="yyyy-MM-dd";
	private DateFormat dateFormat ;
	
	public DateJsonValueProcessor(String datePattern){
		try{
			dateFormat  = new SimpleDateFormat(datePattern);
		}catch(Exception e ){
			dateFormat = new SimpleDateFormat(Default_DATE_PATTERN);
		}
	}
	
	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		
		return process(value);
	}

	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
		
		return process(value);
	}
	
	private Object process(Object value){
		return dateFormat.format((Date)value);
	}
}
