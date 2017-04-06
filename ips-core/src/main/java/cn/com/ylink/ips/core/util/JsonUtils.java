/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2012-8-17
 */

package cn.com.ylink.ips.core.util;

import java.util.Date;

import cn.com.ylink.ips.core.util.support.DateJsonValueProcessor;
import cn.com.ylink.ips.core.util.support.JsonFilter;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-8-17
 * @description：json转换工具
 */

public class JsonUtils {
	
	/**
	 * @description 解析对象属性返回json字符串
	 * @param data 数据对象
	 * @param params 过滤的属性
	 * @return json字符串
	 * @author ZhangDM(Mingly)
	 * @date 2012-8-17
	 */
	public static String resultJsonFromObject(Object data, String... params){
		
		String default_date_pattern = "yyyy-MM-dd";
		return resultJsonFromObject(data, default_date_pattern, params);
		
	}
	
	/**
	 * @description 解析对象属性返回json字符串
	 * @param data 数据对象
	 * @param datePattern 时间格式
	 * @param params 过滤的属性
	 * @return json字符串
	 * @author ZhangDM(Mingly)
	 * @date 2012-8-17
	 */
	@SuppressWarnings("null")
	public static String resultJsonFromObject(Object data, String datePattern, String... params){
		
		JsonConfig config = null;
		if(params != null && params.length > 0){
			config = JsonFilter.getFilter(params);
		}
		if(config == null)
			config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(datePattern));
		JSONObject json = JSONObject.fromObject(data, config);
		return json.toString();
	}
	
	/**
	 * @description 将json字符串转为为指定类型对象
	 * @param json
	 * @param beanClass
	 * @return 
	 * @author ZhangDM(Mingly)
	 * @date 2012-8-19
	 */
	@SuppressWarnings("rawtypes")
	public static Object resultObjectFromJson(String json, Class beanClass){
		
		JSONObject jsonObject=JSONObject.fromObject(json);
		Object obj = JSONObject.toBean(jsonObject, beanClass);
		return obj;
	}
	
}
