package cn.com.ylink.ips.core.util;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.com.ylink.ips.core.exception.UtilRuntimeException;

/**
 * @author ZhangDM(Mingly)
 * @date 2012-8-5
 * @description：反射工具
 */

public class ReflectionUtils {
	
	private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

	/**
	 * @description 获取obj对象fieldName的Field
	 * @author ZhangDM(Mingly)
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 * @date 2012-8-5
	 */
	public static Field getFieldByFieldName(Object obj, String fieldName) 
			throws SecurityException, NoSuchFieldException {
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; 
				superClass = superClass.getSuperclass()) {
			return superClass.getDeclaredField(fieldName);
		}
		return null;
	}

	/**
	 * @description 获取obj对象fieldName的属性值
	 * @author ZhangDM(Mingly)
	 * @date 2012-8-5
	 */
	public static Object getValueByFieldName(Object obj, String fieldName){
		
		Object value = null;
		try{
			Field field = getFieldByFieldName(obj, fieldName);
			if (field != null) {
				if (field.isAccessible()) {
					value = field.get(obj);
				} else {
					field.setAccessible(true);
					value = field.get(obj);
					field.setAccessible(false);
				}
			}
		}catch(Exception ex){
			logger.warn("ReflectionUtils method[getValueByFieldName] error");
			throw new UtilRuntimeException(ex.getMessage());
		}
		
		return value;
	}

	/**
	 * @description 设置obj对象fieldName的属性值
	 * @author ZhangDM(Mingly)
	 * @date 2012-8-5
	 */
	public static void setValueByFieldName(Object obj, String fieldName,
			Object value) {
		try{
			Field field = obj.getClass().getDeclaredField(fieldName);
			if (field.isAccessible()) {
				field.set(obj, value);
			} else {
				field.setAccessible(true);
				field.set(obj, value);
				field.setAccessible(false);
			}
		}catch(Exception ex){
			logger.warn("ReflectionUtils method[setValueByFieldName] error");
			throw new UtilRuntimeException(ex.getMessage());
		}
	}
}
