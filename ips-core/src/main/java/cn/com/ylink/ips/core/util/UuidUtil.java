/**
 * 版权所有(C) 2013 深圳市雁联计算系统有限公司
 * 创建:LS 2013-9-3
 */

package cn.com.ylink.ips.core.util;

import java.util.UUID;

/**
 * @author LS
 * @date 2013-9-3
 * @description：TODO
 */

public class UuidUtil {

	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 测试
	 * 
	 * @description
	 * @param args
	 * @author LS
	 * @date 2013-9-3
	 */
	public static void main(String[] args) {
		System.out.println(getUUID());
	}

}
