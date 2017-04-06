package cn.com.ylink.ips.web.base.converter;


import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;
import cn.com.ylink.ips.web.base.converter.datetime.DateTimeConverter;

/**
 * 初始化类型转换器
 * 
 * @author LS
 *
 */
public class DateWebBindingInitializer implements WebBindingInitializer {

	/**
	 * 日期格式
	 */
	private String datePattern = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 提供用户设置自己的日期格式
	 * 
	 * @param datePattern
	 */
	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}

	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		binder.registerCustomEditor(Date.class, new DateTimeConverter(datePattern));
	}

}
