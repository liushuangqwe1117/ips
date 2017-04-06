/**
 * 版权所有(C) 2013 深圳市雁联计算系统有限公司
 * 创建:LS 2013-8-28
 */

package cn.com.ylink.ips.core.exception;

/**
 * @author LS
 * @date 2013-8-28
 * @description：TODO
 */

public class IllegalArgumentRuntimeException extends BaseRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8036666892401999801L;

	public IllegalArgumentRuntimeException() {
		super();
	}

	public IllegalArgumentRuntimeException(String code, String message,
			Throwable cause) {
		super(code, message, cause);
	}

	public IllegalArgumentRuntimeException(String code, String message) {
		super(code, message);
	}

	public IllegalArgumentRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalArgumentRuntimeException(String message) {
		super("", message);
	}

	public IllegalArgumentRuntimeException(Throwable cause) {
		super(cause);
	}

}
