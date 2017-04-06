package cn.com.ylink.ips.core.util;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-7-25
 * @description：注解工具类,解析包下指定的注解类
 */

public final class AnnotationUtils {
	
	@SuppressWarnings("unchecked")
	public static Set<BeanDefinition> findAnnotationBeanDefinition(String[] basePackages, 
			String annotationType) throws ClassNotFoundException{
		
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false){
			@Override
			protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition){
			    AnnotationMetadata metadata = beanDefinition.getMetadata();

			    return (((metadata.isInterface()) || (metadata.isAbstract()) || (metadata.isConcrete()) || 
			    		(metadata.isFinal()))) && (metadata.isIndependent());
			}
		};
	    scanner.addIncludeFilter(new AnnotationTypeFilter((Class<? extends Annotation>) Class.forName(annotationType)));
	    Set<BeanDefinition> candidates = null;
	    String[] arrayOfString = basePackages; 
	    for (int i = 0; i < basePackages.length; ++i) { 
	    	String basePackage = arrayOfString[i];
	    	if (candidates == null)
	    		candidates = scanner.findCandidateComponents(basePackage);
	    	else {
	    		candidates.addAll(scanner.findCandidateComponents(basePackage));
	    	} 
	    }
	    return candidates;
	  }
}
