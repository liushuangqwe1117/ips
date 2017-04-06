package cn.com.ylink.ips.core.context;

import java.util.Map;

import cn.com.ylink.ips.core.annotation.handler.AnnotationHandler;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.CollectionUtils;
import cn.com.ylink.ips.core.exception.ScannerAnnotationException;
import cn.com.ylink.ips.core.model.PackageInfo;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-7-25
 * @description：执行自定义注解
 */

public final class ContextRefreshListener implements ApplicationListener<ContextRefreshedEvent>{
	
	private static boolean isStart = false;
	
	private Logger log = LoggerFactory.getLogger(ContextRefreshListener.class);
	
	private ClasspathAnnotationDefinitionScanner scanner;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		//区分spring的IOC和spring MVC的IOC，只需要执行一次
		if(isStart)
			return;
		
		ApplicationContext applicationContext = event.getApplicationContext();
	    try {
	    	scanPackageAnno(applicationContext);
	    	
	    	handleAnnoHandler(applicationContext);
	    	
	    	isStart = true;
	    } catch (Exception e) {
	    	log.error(e.getMessage());
	    }
	}
	
	/**
	 * @description 扫描自定义的注解
	 * @author ZhangDM(Mingly)
	 * @throws ScannerAnnotationException 
	 * @date 2012-7-25
	 */
	private void scanPackageAnno(ApplicationContext applicationContext) throws ScannerAnnotationException{
		
		Map<String, PackageInfo> basePackagesMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, 
			PackageInfo.class, true, false);
		
	    String[] allBasePackages = (String[])null;
	    String[] allAnnotationTypes = (String[])null;
	    String[] basePackages = (String[])null;
	    String[] annotationTypes = (String[])null;
	    
	    PackageInfo packageInfo;
	    for (String key : basePackagesMap.keySet()) {
	    	packageInfo = (PackageInfo)basePackagesMap.get(key);
	    	basePackages = packageInfo.getBasePackages();
	    	if (basePackages != null) {
	    		allBasePackages = (String[])ArrayUtils.addAll(allBasePackages, basePackages);
	    	}
	    	annotationTypes = packageInfo.getAnnotationTypes();
	    	if (annotationTypes != null) {
	    		allAnnotationTypes = (String[])ArrayUtils.addAll(allAnnotationTypes, annotationTypes);
	    	}
	    }
	    if ((allBasePackages == null) || (allAnnotationTypes == null)) 
	    	return;
	    try {
	    	this.scanner.findAllAnnotationConfig(allAnnotationTypes, allBasePackages);
	    } catch (Exception e) {
	    	log.error(e.getMessage());
	    	throw new ScannerAnnotationException("scan all annotation config error");
	    }
	    
  	}
	
	/**
	 * @description 执行已经定义好的注解类型的帮助类
	 * @author ZhangDM(Mingly)
	 * @throws ScannerAnnotationException 
	 * @date 2012-7-25
	 */
	private void handleAnnoHandler(ApplicationContext applicationContext) throws ScannerAnnotationException{
		
		Map<String, AnnotationHandler> annotationHandlersMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(
    		applicationContext, AnnotationHandler.class, true, false);
	    for (String beanName : annotationHandlersMap.keySet()) {
	    	AnnotationHandler handler = (AnnotationHandler)annotationHandlersMap.get(beanName);
	    	if (handler != null && !CollectionUtils.isEmpty(scanner.getAnnotationConfigs()))
	    		handler.handle(this.scanner.getAnnotationConfigs());
    	}
	}

	public void setScanner(ClasspathAnnotationDefinitionScanner scanner) {
		this.scanner = scanner;
	}
	
}
