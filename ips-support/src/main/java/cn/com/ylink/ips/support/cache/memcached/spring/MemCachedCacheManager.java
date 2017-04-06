/**
 * 版权所有(C) 2013 深圳市雁联计算系统有限公司
 * 创建:LS 2013-9-2
 */

package cn.com.ylink.ips.support.cache.memcached.spring;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.springframework.cache.Cache;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;

/**
 * @author LS
 * @date 2013-9-2
 * @description：memcahced缓存管理器
 */

public class MemCachedCacheManager extends
		AbstractTransactionSupportingCacheManager {

	private MemCachedCache memCachedCache;

	public void setMemCachedCache(MemCachedCache memCachedCache) {
		this.memCachedCache = memCachedCache;
	}

	@Override
	protected Collection<? extends Cache> loadCaches() {
		Collection<Cache> caches = new LinkedHashSet<Cache>(1);
		caches.add(memCachedCache);
		return caches;
	}

}
