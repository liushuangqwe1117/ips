package cn.com.ylink.ips.core.annotation.handler;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.com.ylink.ips.core.annotation.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.util.CollectionUtils;
import cn.com.ylink.ips.core.exception.ScannerAnnotationException;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-7-25
 * @description：解析常量注解类
 */

public final class ClassPathConstantAnnotationHandler implements AnnotationHandler{
	
	private Logger log = LoggerFactory.getLogger(ClassPathConstantAnnotationHandler.class);
	
	/**
	 * key-value(namespace + "." + fieldName：value)
	 */
	private static Map<String, String> constants = new HashMap<String, String>();

	@Override
	public void handle(Map<String, Set<BeanDefinition>> annotationConfigs) throws ScannerAnnotationException {
		Set<BeanDefinition> allConstantAnnotationConfigs = (Set<BeanDefinition>)annotationConfigs.get(Constant.class.getName());
	    if(!CollectionUtils.isEmpty(allConstantAnnotationConfigs)){
	    	for (BeanDefinition candidate : allConstantAnnotationConfigs){
		    	try {
		    		findConstant(candidate);
		    	} catch (Exception e) {
		    		log.error(e.getMessage());
		    		throw new ScannerAnnotationException("handle all " + Constant.class.getName() + " error");
		    	}
	    	}
	    }
	}
	
	private void findConstant(BeanDefinition candidate) 
			throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException{
		
		String beanClassName = candidate.getBeanClassName();
		
	    Class clazz = Class.forName(beanClassName);
	    String namespace = ((Constant)clazz.getAnnotation(Constant.class)).namespace();
	    Field[] fields = clazz.getFields();
	    String fieldName = null;
	    String fieldValue = null;
	    for (Field field : fields) {
	    	fieldName = field.getName();
	    	fieldValue = String.valueOf(field.get(null));
	    	constants.put(namespace + "." + fieldName, fieldValue);
	    }
	}
	
	public static Map<String, String> getConstants(){
		return constants;
	}

}
