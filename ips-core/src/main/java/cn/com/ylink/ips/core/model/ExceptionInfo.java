/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2012-8-16
 */

package cn.com.ylink.ips.core.model;

import java.io.Serializable;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-8-16
 * @description：异常信息类
 */

public class ExceptionInfo implements Serializable {
	
	private static final long serialVersionUID = -2501088757568711878L;
	
	private String exceptionClass;
	private String code;
	private String message;
	
	public String getExceptionClass() {
		return exceptionClass;
	}
	public void setExceptionClass(String exceptionClass) {
		this.exceptionClass = exceptionClass;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
