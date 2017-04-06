package cn.com.ylink.ips.security.shiro.exception;

import org.apache.shiro.authc.AuthenticationException;

public class TimeLockedException extends AuthenticationException {
	private static final long serialVersionUID = 5038485428928343149L;

	public TimeLockedException() {
	}

	public TimeLockedException(String message, Throwable cause) {
		super(message, cause);
	}

	public TimeLockedException(String message) {
		super(message);
	}

	public TimeLockedException(Throwable cause) {
		super(cause);
	}
}
