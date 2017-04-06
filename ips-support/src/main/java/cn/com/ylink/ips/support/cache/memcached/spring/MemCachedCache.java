/**
 * 版权所有(C) 2013 深圳市雁联计算系统有限公司
 * 创建:LS 2013-9-2
 */

package cn.com.ylink.ips.support.cache.memcached.spring;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.util.Assert;

import com.whalin.MemCached.MemCachedClient;

/**
 * @author LS
 * @date 2013-9-2
 * @description： MemCachedCache
 */

public class MemCachedCache implements Cache {

	private MemCachedClient memcachedClient;

	private String cacheName = "";

	public MemCachedCache(MemCachedClient memcachedClient, String cacheName) {
		Assert.notNull(memcachedClient, "memcachedClient must not be null");
		Assert.hasText(cacheName, "cacheName must not be blank");
		this.memcachedClient = memcachedClient;
		this.cacheName = cacheName;
	}

	public String getName() {
		return cacheName;
	}

	public MemCachedClient getNativeCache() {
		return memcachedClient;
	}

	public ValueWrapper get(Object key) {
		Assert.notNull(key);
		Assert.hasText(key.toString(), "key must not be blank");
		Object obj = memcachedClient.get(getName() + key.toString());
		return (obj != null ? new SimpleValueWrapper(obj) : null);
	}

	public void put(Object key, Object value) {
		Assert.notNull(key);
		Assert.hasText(key.toString(), "key must not be blank");
		memcachedClient.set(getName() + key.toString(), value);
	}

	public void evict(Object key) {
		Assert.notNull(key);
		Assert.hasText(key.toString(), "key must not be blank");
		memcachedClient.delete(getName() + key.toString());
	}

	public void clear() {
		memcachedClient.flushAll();
	}

	public <T> T get(Object arg0, Class<T> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public ValueWrapper putIfAbsent(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}
}
