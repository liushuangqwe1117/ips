/**
 * 版权所有(C) 2013 深圳市雁联计算系统有限公司
 * 创建:Administrator 2013-9-4
 */

package cn.com.ylink.ips.core.concurrent;

import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @author jcmeng
 * @date 2013-9-4
 * @description：TODO
 */


public class MySemaphore
{
	private static final Logger logger = LoggerFactory.getLogger(MySemaphore.class);
	private int count;
	private int maxCount;

	public MySemaphore(int count)
	{
		this.count = 0;
		this.maxCount = count;
	}

	synchronized public void acquire()
	{// throws InterruptedException{
		while (count >= maxCount)
		{
			try
			{
				wait();
			} catch (InterruptedException e)
			{
				
				logger.debug(e.getMessage());
			}
		}
		count++;
	}

	//超时 秒
	synchronized public void acquire(long timeout) throws TimeoutException
	{
		while (count >= maxCount)
		{
			try
			{
				long start = System.currentTimeMillis();
				wait(timeout*1000);
				long now  = System.currentTimeMillis();
				if (now - start>=timeout*1000)
				{
					throw new TimeoutException("获取渠道前置超时");
				}
			} catch (InterruptedException e)
			{
				logger.debug(e.getMessage());
			}
		}
		count++;
	}
	synchronized public void release()
	{
		count--;
		notify();
	}

	synchronized public boolean setCount(int count)
	{
		if (count>= this.count)
		{
			this.maxCount = count;
			return true;
		}
		return false;
		
	}

	public boolean canUsed()
	{
		if (count < maxCount)
			return true;
		else
			return false;
	}

	/**
	 * @return Returns the maxCount.
	 */
	public synchronized int getMaxCount() {
		return maxCount;
	}

	/**
	 * @return Returns the count.
	 */
	public synchronized int getCount() {
		return count;
	}
}
