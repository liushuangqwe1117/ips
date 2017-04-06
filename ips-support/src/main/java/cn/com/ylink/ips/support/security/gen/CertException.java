/**
 * 版权所有(C) 2013 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2013-1-8
 */

package cn.com.ylink.ips.support.security.gen;

/** 
 * @author ZhangDM(Mingly)
 * @date 2013-1-8
 * @description：证书异常类
 */

public class CertException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public CertException(Exception e) {
		super(e);
	}
	
	public static CertException  createCertException(Exception e){
	
		return new CertException(e);
	}
}
