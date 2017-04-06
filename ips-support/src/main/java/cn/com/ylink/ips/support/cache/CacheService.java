package cn.com.ylink.ips.support.cache;


import java.util.Date;

public interface CacheService {

	/**
	 * 获取缓存对象
	 * 
	 * @param key
	 * @return
	 */
	 Object getCacheObject(String key);

	/**
	 * 将对象存储到服务端
	 * 
	 * @param key
	 *            存储对象对应的key值
	 * @param obj
	 *            存储对象
	 * @return true:存储成功 false:存储失败
	 */
	 boolean addCacheObject(String key, Object obj);

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
	 boolean addCacheObject(String key, Object obj, Date expired);

	/**
	 * 刷新缓存数据
	 * 
	 * @param key
	 * @param obj
	 * @return
	 */
	 boolean replaceCacheObject(String key, Object obj);

	/**
	 * 刷新缓存数据
	 * 
	 * @param key
	 * @param obj
	 * @param expired
	 * @return
	 */
	 boolean replaceCacheObject(String key, Object obj, Date expired);

	/**
	 * delete 清空Key的缓存对象
	 * 
	 * @param key
	 */

	 boolean deleteCacheObject(String key);

	/**
	 * flushAll 清空所有缓存对象
	 * 
	 * @param key
	 */
	 boolean flushAll();
}
