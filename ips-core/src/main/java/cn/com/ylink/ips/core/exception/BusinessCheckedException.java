package cn.com.ylink.ips.core.exception;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-7-28
 * @description：业务检查异常基类
 */

public class BusinessCheckedException extends BaseCheckedException {

	private static final long serialVersionUID = -6427344104427391337L;
	
	public BusinessCheckedException(String message) {
		super(message);
	}
	
	public BusinessCheckedException(final String code, final String message) {
        super(code, message);
    }

	public BusinessCheckedException(String code, String message, Throwable cause) {
		super(code, message, cause);
	}

	public BusinessCheckedException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
