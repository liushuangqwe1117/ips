package cn.com.ylink.ips.support.cache;

import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class InitCacheListener implements
		ApplicationListener<ContextRefreshedEvent> {

	/**
	 * 日志
	 */
	private static Logger _log = LoggerFactory
			.getLogger(InitCacheListener.class);

	public void onApplicationEvent(ContextRefreshedEvent event) {
		initCacheService(event.getApplicationContext());
	}

	// 初始化缓存数据
	public void initCacheService(ApplicationContext context) {
		if (context == null)
			return;
		String key = "";
		try {
			// 初始化缓存
			Set<String> names = getBeanNamesByClass(context,
					InitCacheService.class);
			if (names != null && names.size() > 0) {
				// 清空缓存
				CacheUtil.flushAll();
				for (String s : names) {
					InitCacheService cs = context.getBean(s,
							InitCacheService.class);
					Object obj = cs.initCacheObject();
					key = cs.initCacheKey();
					CacheUtil.addCacheObject(key, obj);
					if (_log.isInfoEnabled()) {
						_log.info("缓存数据[key=" + key + ";data="
								+ String.valueOf(obj) + "]");
					}
				}
			}
		} catch (Exception e) {
			_log.error("缓存数据出错:key=" + key, e);
		}
	}

	/**
	 * 查找指定类型的实例
	 * 
	 * @param bf
	 * @param clazz
	 * @return
	 */
	private Set<String> getBeanNamesByClass(BeanFactory bf, Class<?> clazz) {
		if (bf == null) {
			return null;
		}
		Set<String> set = new LinkedHashSet<String>();
		if (bf instanceof ListableBeanFactory) {
			ListableBeanFactory lbf = (ListableBeanFactory) bf;
			String[] names = lbf.getBeanNamesForType(clazz);
			if (names != null && names.length > 0) {
				for (String n : names) {
					set.add(n);
				}
			}
		} else if (bf instanceof HierarchicalBeanFactory) {
			HierarchicalBeanFactory hbf = (HierarchicalBeanFactory) bf;
			Set<String> pset = getBeanNamesByClass(hbf.getParentBeanFactory(),
					clazz);
			if (pset != null) {
				set.addAll(pset);
			}
		}
		return set;
	}
}
