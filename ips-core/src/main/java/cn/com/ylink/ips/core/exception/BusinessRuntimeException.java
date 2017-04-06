package cn.com.ylink.ips.core.exception;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-7-28b  
 * @description：业务运行异常基类
 */

public class BusinessRuntimeException extends BaseRuntimeException {

	private static final long serialVersionUID = -6427344104427391337L;
	
	public BusinessRuntimeException() {
		super();
	}
	
	public BusinessRuntimeException(String message) {
		super("", message);
	}
	
	public BusinessRuntimeException(final String code, final String message) {
        super(code, message);
    }

	public BusinessRuntimeException(String code, String message, Throwable cause) {
		super(code, message, cause);
	}

	public BusinessRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessRuntimeException(Throwable cause) {
		super(cause);
	}
	
}
