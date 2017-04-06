package cn.com.ylink.ips.security.shiro;

import java.util.Map;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;

public class MyShiroFilterFactoryBean extends ShiroFilterFactoryBean {
	private static final transient Logger log = LoggerFactory
			.getLogger(MyShiroFilterFactoryBean.class);
	public static final String URLS_SECTION_TYPE_INI = "INI";
	public static final String URLS_SECTION_TYPE_DB = "DB";
	public static final String URLS_SECTION_TYPE_INIANDDB = "INI_DB";
	protected boolean regexPathMatcher = false;

	protected String urlsSectionType = "INI";
	private UrlsSectionService urlsSectionService;

	public boolean isRegexPathMatcher() {
		return this.regexPathMatcher;
	}

	public void setRegexPathMatcher(boolean regexPathMatcher) {
		this.regexPathMatcher = regexPathMatcher;
	}

	public String getUrlsSectionType() {
		return this.urlsSectionType;
	}

	public void setUrlsSectionType(String urlsSectionType) {
		this.urlsSectionType = urlsSectionType;
	}

	public void setUrlsSectionService(UrlsSectionService urlsSectionService) {
		this.urlsSectionService = urlsSectionService;
	}

	protected AbstractShiroFilter createInstance() throws Exception {
		log.debug("Creating Shiro Filter instance.");

		SecurityManager securityManager = getSecurityManager();
		if (securityManager == null) {
			String msg = "SecurityManager property must be set.";
			throw new BeanInitializationException(msg);
		}

		if (!(securityManager instanceof WebSecurityManager)) {
			String msg = "The security manager does not implement the WebSecurityManager interface.";
			throw new BeanInitializationException(msg);
		}

		FilterChainManager manager = createFilterChainManager();

		MyPathMatchingFilterChainResolver chainResolver = new MyPathMatchingFilterChainResolver(
				this.regexPathMatcher);
		chainResolver.setFilterChainManager(manager);

		return new SpringShiroFilter((WebSecurityManager) securityManager,
				chainResolver);
	}

	public void setFilterChainDefinitions(String definitions) {
		super.setFilterChainDefinitions(definitions);
		addUrlsSection();
	}

	public void addUrlsSection() {
		if (("DB".equals(this.urlsSectionType))
				|| ("INI_DB".equals(this.urlsSectionType)))
			if (this.urlsSectionService != null) {
				Map<String, String> section = getFilterChainDefinitionMap();
				Map<String, String> loadSec = this.urlsSectionService
						.loadUrlsSection();
				if ((loadSec == null) || (loadSec.isEmpty())) {
					log.info("Shiro Urls Section 无数据加载！");
				} else {
					if ("DB".equals(this.urlsSectionType)) {
						section.clear();
					}
					section.putAll(loadSec);
					log.info("加载Shiro Urls Section[{}]条，当前结果[{}]条！",
							Integer.valueOf(loadSec.size()),
							Integer.valueOf(section.size()));
				}
			} else {
				log.warn("urlsSectionType=[{}],服务接口[{}]未实现注入！",
						this.urlsSectionType, "UrlsSectionService");
			}
	}

	private static final class SpringShiroFilter extends AbstractShiroFilter {
		protected SpringShiroFilter(WebSecurityManager webSecurityManager,
				FilterChainResolver resolver) {
			if (webSecurityManager == null) {
				throw new IllegalArgumentException(
						"WebSecurityManager property cannot be null.");
			}
			setSecurityManager(webSecurityManager);
			if (resolver != null)
				setFilterChainResolver(resolver);
		}
	}
}