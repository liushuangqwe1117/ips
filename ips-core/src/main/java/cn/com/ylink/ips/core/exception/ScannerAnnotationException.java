package cn.com.ylink.ips.core.exception;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-7-25
 * @description：扫描注解异常
 */

public class ScannerAnnotationException extends BaseCheckedException {

	private static final long serialVersionUID = -3819023223459394759L;
	
	private static final String MESSAGE = "scanner annotation failed";

    public ScannerAnnotationException() {
        this(MESSAGE);
    }
	
	public ScannerAnnotationException(final String message) {
		super(message);
	}
	
	public ScannerAnnotationException(final String code, final String message) {
		super(code, message);
	}
	
	public ScannerAnnotationException(final String message,final Throwable cause) {
        super(message, cause);
    }
	
	public ScannerAnnotationException(final String code, final String message,final Throwable cause) {
        super(code, message, cause);
    }
}
