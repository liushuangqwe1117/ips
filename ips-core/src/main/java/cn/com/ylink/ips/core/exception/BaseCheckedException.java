package cn.com.ylink.ips.core.exception;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-7-25
 * @description：检查类异常基类
 */

public class BaseCheckedException extends Exception {

	private static final long serialVersionUID = -812611710307967457L;
	
	private String code;
	
	public BaseCheckedException(final String message, final Throwable cause) {
        super(message, cause);
    }
	
	public BaseCheckedException(final String code, final String message, final Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BaseCheckedException(final String code) {
        super("");
        this.code = code;
    }
    
    public BaseCheckedException(final String code, final String message) {
        super(message);
        this.code = code;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
    
    

}
