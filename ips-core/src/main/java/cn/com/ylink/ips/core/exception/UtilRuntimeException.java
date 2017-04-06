package cn.com.ylink.ips.core.exception;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-8-5
 * @description：工具类异常
 */

public class UtilRuntimeException extends BaseRuntimeException {
	
	private static final long serialVersionUID = -6966056877913303408L;

	public UtilRuntimeException(String message) {
		super(message);
	}
	
	public UtilRuntimeException(final String code, final String message) {
        super(code, message);
    }
}
