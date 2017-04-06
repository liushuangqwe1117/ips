package cn.com.ylink.ips.support.cache;


/**
 * 系统启动时初始化缓存数据
 * 
 * @author LS
 * 
 */
public abstract class InitCacheService {

	/**
	 * 缓存数据对应的Key值
	 * 
	 * @return
	 */
	public abstract String initCacheKey();

	/**
	 * 要缓存的数据
	 * 
	 * @return
	 */
	public abstract Object initCacheObject();

	/**
	 * 获取系统初始化时缓存的数据
	 * 
	 * @return
	 */
	public Object getInitCacheObject() {
		return CacheUtil.getCacheObject(initCacheKey());
	}

	/**
	 * 刷新缓存数据
	 * 
	 * @param newObj
	 */
	public void refreshInitCacheObject(Object newObj) {
		Object obj = CacheUtil.getCacheObject(initCacheKey());
		if (obj == null) {
			CacheUtil.addCacheObject(initCacheKey(), newObj);
		} else {
			CacheUtil.replaceCacheObject(initCacheKey(), newObj);
		}
	}
}
