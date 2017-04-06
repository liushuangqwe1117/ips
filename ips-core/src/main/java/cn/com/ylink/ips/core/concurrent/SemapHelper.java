/**
 * 版权所有(C) 2013 深圳市雁联计算系统有限公司
 * 创建:Administrator 2013-9-4
 */

package cn.com.ylink.ips.core.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @author jcmeng
 * @date 2013-9-4
 * @description：TODO
 */

public class SemapHelper {
	private static final Logger logger = LoggerFactory.getLogger(SemapHelper.class);
	//private static PayChannelDao payChannelDao = (PayChannelDao) SpringContext.getService("payChannelDao");

	/**
	 * 得到渠道数据库信号量
	 * @throws BizException
	 */
	public static void getLink(String poolName, int linkMaxNum) throws Exception {

		// 校验数据库连接（发送交易）信号量是否为空
		MySemaphore msh = MySemaphoreManager.getInstance().getMySemaphore(poolName);
		if (msh == null) {
			initLink(poolName, linkMaxNum);
		}else{
			logger.debug(poolName + " 当前还剩余信号量数:"
					+ (msh.getMaxCount() - msh.getCount()) + "，最大信号量为:"
					+ msh.getMaxCount());
		}

		// 校验该数据库信号量是否已满
		boolean isDBSemaphore = MySemaphoreManager.getInstance().getMySemaphore(poolName).canUsed();
		if (!isDBSemaphore) {
			logger.debug(poolName + " 信号量已满,进入等待中......");
		}
		MySemaphoreManager.getInstance().getMySemaphore(poolName).acquire();
		logger.debug("已获取到 " + poolName + " 信号量");
	}
	
	/**
	 * 初始化渠道数据库信息量
	 * 
	 * */
	public static void initLink(String poolName, int linkMaxNum)throws Exception{
		
		// 从系统参数中读取信号量的配置
		//if (ChnlDBPool.DB_LINK_NUM.getValue().equals(poolName)) {
		//	linkMaxNum = 100;
		//} else {
			// 其他情况，读取渠道的信号量，查渠道配置表
			if (linkMaxNum == 0){
				linkMaxNum = 100;
			}
		//}
		
		if(linkMaxNum == 0){
			throw new Exception(poolName + "信息量初始化时，信号数为空");
		}
		logger.debug("开始初始化"+ poolName +"信息量......");
		MySemaphoreManager.getInstance().setMySemaphoreCount(poolName, linkMaxNum);
		logger.debug("初始化"+ poolName +"信息量完成,信号量名称为：" + poolName + ",总数为："+linkMaxNum);
	}
	
	public static void releaseLink(String poolName) throws Exception {
		// 释放数据库连接信号量
		MySemaphoreManager.getInstance().getMySemaphore(poolName).release();
	}
	
}
