/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2012-10-21
 */

package cn.com.ylink.ips.support.ftp;

import cn.com.ylink.ips.core.exception.BaseCheckedException;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-12-10
 * @description：FTP操作异常
 */

public class FTPException extends BaseCheckedException{
	
	
	private static final long serialVersionUID = 1L;

	public FTPException(String message) {
		super("", message);
	}
}
