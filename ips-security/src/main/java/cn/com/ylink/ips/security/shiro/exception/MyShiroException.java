package cn.com.ylink.ips.security.shiro.exception;

import cn.com.ylink.ips.core.exception.BaseRuntimeException;

public class MyShiroException extends BaseRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2260365156014928645L;

	public MyShiroException() {
		super();
	}

	public MyShiroException(String code, String message, Throwable cause) {
		super(code, message, cause);
	}

	public MyShiroException(String code, String message) {
		super(code, message);
	}

	public MyShiroException(String message, Throwable cause) {
		super(message, cause);
	}

	public MyShiroException(String code) {
		super(code);
	}

	public MyShiroException(Throwable cause) {
		super(cause);
	}

}
