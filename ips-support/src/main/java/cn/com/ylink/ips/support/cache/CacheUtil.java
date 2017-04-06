package cn.com.ylink.ips.support.cache;

import java.util.Date;

public final class CacheUtil {

	private static CacheService cacheService;
	private static Boolean openCache = true;

	private CacheUtil() {
	}

	public static void setCacheService(CacheService cacheService) {
		CacheUtil.cacheService = cacheService;
	}

	private static boolean isOpenCache() {
		if(openCache == null){
			return true;
		}
		return openCache;
	}

	public static void setOpenCache(Boolean openCache) {
		CacheUtil.openCache = openCache;
	}

	/**
	 * 获取缓存对象
	 * 
	 * @param key
	 * @return
	 */
	public static Object getCacheObject(String key) {
		if(!isOpenCache()){
			return null;
		}
		return cacheService.getCacheObject(key);
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
	public static boolean addCacheObject(String key, Object obj) {
		if(!isOpenCache()){
			return false;
		}
		return cacheService.addCacheObject(key, obj);
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
	public static boolean addCacheObject(String key, Object obj, Date expired) {
		if(!isOpenCache()){
			return false;
		}
		return cacheService.addCacheObject(key, obj, expired);
	}

	/**
	 * 刷新缓存数据
	 * 
	 * @param key
	 * @param obj
	 * @return
	 */
	public static boolean replaceCacheObject(String key, Object obj) {
		if(!isOpenCache()){
			return false;
		}
		return cacheService.replaceCacheObject(key, obj);
	}

	/**
	 * 刷新缓存数据
	 * 
	 * @param key
	 * @param obj
	 * @param expired
	 * @return
	 */
	public static boolean replaceCacheObject(String key, Object obj,
			Date expired) {
		if(!isOpenCache()){
			return false;
		}
		return cacheService.replaceCacheObject(key, obj, expired);
	}

	/**
	 * delete 清空Key的缓存对象
	 * 
	 * @param key
	 */

	public static boolean deleteCacheObject(String key) {
		if(!isOpenCache()){
			return false;
		}
		return cacheService.deleteCacheObject(key);
	}

	/**
	 * flushAll 清空所有缓存对象
	 * 
	 * @param key
	 */
	public static boolean flushAll() {
		if(!isOpenCache()){
			return false;
		}
		return cacheService.flushAll();
	}
}
