package cn.com.ylink.ips.core.annotation.handler;

import org.springframework.beans.factory.config.BeanDefinition;
import cn.com.ylink.ips.core.exception.ScannerAnnotationException;

import java.util.Map;
import java.util.Set;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-7-25
 * @description：注解操作类，处理某种类型的注解
 */

public interface AnnotationHandler {
	
	/**
	 * @description 按照某种规则解析实例化的注解类
	 * @author ZhangDM(Mingly)
	 * @throws ScannerAnnotationException 
	 * @date 2012-7-25
	 */
	public void handle(Map<String, Set<BeanDefinition>> annotationConfigs) throws ScannerAnnotationException;
}
