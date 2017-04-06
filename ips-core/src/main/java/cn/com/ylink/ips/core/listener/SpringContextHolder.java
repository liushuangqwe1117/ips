package cn.com.ylink.ips.core.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-7-24
 * @description：
 */

public class SpringContextHolder implements ServletContextListener {
	
	private static WebApplicationContext springContext;
	
	private static ApplicationContext applicationContext;
	
	public static ApplicationContext getApplicationContext(){
		if(springContext != null)
			return springContext;
		if(applicationContext != null)
			return applicationContext;
		return null;
	}
	
	public void contextDestroyed(ServletContextEvent event) {
		
	}

	public void contextInitialized(ServletContextEvent event) {
		springContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
	}
	
	public static void setApplicationContext(ApplicationContext ac){
		applicationContext = ac;
	}
	
	
}
