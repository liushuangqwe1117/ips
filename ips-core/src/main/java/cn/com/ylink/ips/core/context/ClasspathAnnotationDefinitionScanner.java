package cn.com.ylink.ips.core.context;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.com.ylink.ips.core.util.AnnotationUtils;
import org.springframework.beans.factory.config.BeanDefinition;

/**
 * @author ZhangDM(Mingly)
 * @date 2012-7-25
 * @description：
 */

public class ClasspathAnnotationDefinitionScanner {

	private Map<String, Set<BeanDefinition>> annotationConfigs;

	public Map<String, Set<BeanDefinition>> getAnnotationConfigs() {
		return this.annotationConfigs;
	}

	public void findAllAnnotationConfig(String[] allAnnotationTypes,
			String[] basePackages) throws ClassNotFoundException {

		Map<String, Set<BeanDefinition>> tempAnnotationConfigs = new HashMap<String, Set<BeanDefinition>>(
				allAnnotationTypes.length);

		for (String annotationType : allAnnotationTypes) {
			Set<BeanDefinition> candidates = AnnotationUtils
					.findAnnotationBeanDefinition(basePackages, annotationType);
			if (candidates != null && candidates.size() > 0) {
				tempAnnotationConfigs.put(annotationType, candidates);
			}
		}
		this.annotationConfigs = Collections
				.unmodifiableMap(tempAnnotationConfigs);
	}
}
