package cn.com.ylink.ips.web.mvc.handler;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
/**
 * 通过配置跟Freemaker很好的兼容
 * 该类不灵活配置、不方便扩展，只为快速平滑适配与当前项目
 * @author huping
 *
 */
public class AdapterViewResolver implements ViewResolver{
	//freemaker返回路径前缀（约定）
		public static final String SUFFIX = "ftl_";

		private static Log logger = LogFactory.getLog(AdapterViewResolver.class);

		private ViewResolver defaultViewResolver = null;

		private ViewResolver freemakerViewResolver = null;

		@Override
		public View resolveViewName(String viewName, Locale locale) throws Exception {
			if (viewName.startsWith(SUFFIX)) {
				//转换成ftl试图解析器模式，替换掉前缀,加上后缀
				viewName = viewName.replace(SUFFIX, "")+".ftl";
				if (null != freemakerViewResolver) {
					if (logger.isDebugEnabled()) {
						logger.debug("found viewResolver '" + freemakerViewResolver + "' for viewName '" + viewName + "'");
					}
					return freemakerViewResolver.resolveViewName(viewName, locale);
				}
			} else if (defaultViewResolver != null) {
				return defaultViewResolver.resolveViewName(viewName, locale);
			}
			return null;
		}

		public ViewResolver getDefaultViewResolver() {
			return defaultViewResolver;
		}

		public void setDefaultViewResolver(ViewResolver defaultViewResolver) {
			this.defaultViewResolver = defaultViewResolver;
		}

		public void setFreemakerViewResolver(ViewResolver freemakerViewResolver) {
			this.freemakerViewResolver = freemakerViewResolver;
		}
}
