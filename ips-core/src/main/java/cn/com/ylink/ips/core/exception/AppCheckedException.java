package cn.com.ylink.ips.core.exception;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-7-28
 * @description：对外服务检查异常基类
 */

public class AppCheckedException extends BaseCheckedException {

	private static final long serialVersionUID = -6427344104427391337L;
	
	public AppCheckedException(String message) {
		super("", message);
	}
	
	public AppCheckedException(final String code, final String message) {
        super(code, message);
    }
	
}
