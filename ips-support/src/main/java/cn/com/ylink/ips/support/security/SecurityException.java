/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2012-10-21
 */

package cn.com.ylink.ips.support.security;

import cn.com.ylink.ips.core.exception.BaseCheckedException;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-10-21
 * @description：安全异常
 */

public class SecurityException extends BaseCheckedException{
	
	
	private static final long serialVersionUID = 1L;

	public SecurityException(String message) {
		super("", message);
	}
}
