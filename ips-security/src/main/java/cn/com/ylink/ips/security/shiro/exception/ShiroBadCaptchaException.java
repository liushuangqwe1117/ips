package cn.com.ylink.ips.security.shiro.exception;

import org.apache.shiro.authc.AuthenticationException;

public class ShiroBadCaptchaException extends AuthenticationException {
	private static final long serialVersionUID = -4987144327107616457L;

	public ShiroBadCaptchaException() {
	}

	public ShiroBadCaptchaException(String message, Throwable cause) {
		super(message, cause);
	}

	public ShiroBadCaptchaException(String message) {
		super(message);
	}

	public ShiroBadCaptchaException(Throwable cause) {
		super(cause);
	}
}
