package cn.com.ylink.ips.support.cache.memcached;

import java.util.Date;

import cn.com.ylink.ips.support.cache.CacheService;
import org.apache.commons.lang.StringUtils;

import com.whalin.MemCached.MemCachedClient;

public class MemCachedService implements CacheService {

	private MemCachedClient memcachedClient;

	private String systemCode = "";

	public void setMemcachedClient(MemCachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode+"_";
	}

	private String getSystemCode() {
		return this.systemCode;
	}

	/**
	 * 获取缓存对象
	 * 
	 * @param key
	 * @return
	 */
	public Object getCacheObject(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		return this.memcachedClient.get(getSystemCode() + key);
	}

	/**
	 * 将对象存储到服务端
	 * 
	 * @param key
	 *            存储对象对应的key值
	 * @param obj
	 *            存储对象
	 * @return true:存储成功 false:存储失败
	 */
	public boolean addCacheObject(String key, Object obj) {
		return addCacheObject(key, obj, null);
	}

	/**
	 * 将对象存储到服务端
	 * 
	 * @param key
	 *            存储对象对应的key值
	 * @param obj
	 *            存储对象
	 * @param expired
	 *            设置过期时间
	 * @return true:存储成功 false:存储失败
	 */
	public boolean addCacheObject(String key, Object obj, Date expired) {
		if (StringUtils.isBlank(key)) {
			return false;
		}
		if (expired == null) {
			return this.memcachedClient.set(getSystemCode() + key, obj);
		}
		return this.memcachedClient.set(getSystemCode() + key, obj,
				expired);
	}

	/**
	 * 刷新缓存数据
	 * 
	 * @param key
	 * @param obj
	 * @return
	 */
	public boolean replaceCacheObject(String key, Object obj) {
		return replaceCacheObject(key, obj, null);
	}

	/**
	 * 刷新缓存数据
	 * 
	 * @param key
	 * @param obj
	 * @param expired
	 * @return
	 */
	public boolean replaceCacheObject(String key, Object obj, Date expired) {
		if (StringUtils.isBlank(key)) {
			return false;
		}
		if (expired == null) {
			return this.memcachedClient
					.replace(getSystemCode() + key, obj);
		} else {
			return this.memcachedClient.replace(getSystemCode() + key,
					obj, expired);
		}
	}

	/**
	 * delete 清空Key的缓存对象
	 * 
	 * @param key
	 */
	public boolean deleteCacheObject(String key) {
		if (StringUtils.isBlank(key)) {
			return false;
		}
		return this.memcachedClient.delete(getSystemCode() + key);
	}

	/**
	 * flushAll 清空所有缓存对象
	 * 
	 * @param key
	 */
	public boolean flushAll() {
		return this.memcachedClient.flushAll();
	}

}
