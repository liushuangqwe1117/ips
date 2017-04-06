package cn.com.ylink.ips.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsgException extends BaseCheckedException {
	
	private final static Logger logger = LoggerFactory.getLogger(MsgException.class);
	private static final long serialVersionUID = 1;
	private String _errorCode = "90001";
	private String _moduleCode ="";
	private String _subsystem = "";
	private Exception _exception; //底层异常
	public MsgException(Throwable cause) {
		super("",cause);
	}
	public MsgException(String msg) {
		super("", msg);
		logger.debug(msg);
	}
	public MsgException(String errorCode, String msg) {
		super(errorCode, msg);
		_errorCode = errorCode;
		logger.debug("错误代码{"+errorCode+"},错误描述{"+msg+"}");
	}
	public MsgException(String errorCode, String msg,String moduleCode,String subsystem) {
		super(errorCode, msg);
		_errorCode = errorCode;
		_moduleCode = moduleCode;
		_subsystem = subsystem;	
		
		logger.debug("错误代码{"+errorCode+"},错误描述{"+msg+"}");
	}
	public MsgException(Exception exception,String msg) {
		super("", msg);
		_exception = exception;
		logger.debug(msg);
	}
	public MsgException(Exception exception,String errorCode, String msg) {
		super(errorCode, msg);
		_errorCode = errorCode;
		_exception = exception;
		logger.debug("错误代码{"+errorCode+"},错误描述{"+msg+"}");
	}
	public MsgException(Exception exception,String errorCode, String msg,String moduleCode,String subsystem) {
		super(errorCode, msg);
		_errorCode = errorCode;
		_exception = exception;
		_moduleCode = moduleCode;
		_subsystem = subsystem;	
		logger.debug("错误代码{"+errorCode+"},错误描述{"+msg+"}");
	}
	public String getErrorCode() {
		return _errorCode;
	}
	
	public Exception getRootCause() {
		if (_exception instanceof MsgException) {
			return ((MsgException) _exception).getRootCause();
		}
		return _exception == null ? this : _exception;
	}

	public String toString() {
		String desc = "错误码为：" + _errorCode + "，错误描述为：" + this.getMessage();
		if (_exception != null){
			desc = desc + "，底层异常为：" + _exception;
		}
		return desc;		
	}
	public String get_moduleCode()
	{
		return _moduleCode;
	}
	public String get_subsystem()
	{
		return _subsystem;
	}
}
