/**
 * 版权所有(C) 2013 深圳市雁联计算系统有限公司
 * 创建:Administrator 2013-9-4
 */

package cn.com.ylink.ips.core.concurrent;

import java.util.HashMap;

/** 
 * @author jcmeng
 * @date 2013-9-4
 * @description：TODO
 */


public class MySemaphoreManager
{
    private static MySemaphoreManager instance = new MySemaphoreManager();
    private static HashMap mySemaphoreMap = new HashMap();
    public static MySemaphoreManager getInstance() 
    {
        return instance;
    }
    public void setMySemaphoreCount(String sFrontNo,int iMaxCount)
    {
    	MySemaphore mySemaphore;
    	mySemaphore = (MySemaphore)mySemaphoreMap.get(sFrontNo);
    	if (mySemaphore==null)
    	{
    		mySemaphore = new MySemaphore(iMaxCount);
    		mySemaphoreMap.put(sFrontNo, mySemaphore);
    	}
    	else
    		mySemaphore.setCount(iMaxCount);
    }
    public MySemaphore getMySemaphore(String sFrontNo)
    {
    	MySemaphore mySemaphore;
    	mySemaphore = (MySemaphore)mySemaphoreMap.get(sFrontNo);
    	return mySemaphore;
    }

}
//使用方法
//一次设置
//MySemaphoreManager.getInstance().setMySemaphoreCount("front1_0",10);
//多次使用
//MySemaphoreManager.getInstance().getMySemaphore("front1_0").acquire;
//MySemaphoreManager.getInstance().getMySemaphore("front1_0").release();
//渠道线程池命名规则
//实时交易的front1_0 批量交易 查询 协议的front1_1
