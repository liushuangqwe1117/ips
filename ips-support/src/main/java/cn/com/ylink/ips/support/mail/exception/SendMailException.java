/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2012-8-10
 */

package cn.com.ylink.ips.support.mail.exception;

import cn.com.ylink.ips.core.exception.BaseCheckedException;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-8-10
 * @description：邮件异常
 */

public class SendMailException extends BaseCheckedException {

	private static final long serialVersionUID = -5225103929045961447L;
	
	public SendMailException(String message) {
		super("", message);
	}
}
