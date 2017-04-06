/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2012-10-21
 */

package cn.com.ylink.ips.support.security;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/** 
 * @author ZhangDM(Mingly)
 * @date 2012-10-21
 * @description：证书工具类
 */

public abstract class CertificateUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(CertificateUtil.class);
	
	/**
	 * 非对称加密密钥算法
	 */
	private static final String CERT_TYPE = "X.509";
	
	/**
	 * @description 从本地证书获取公钥
	 * @param path
	 * @return
	 * @throws MsgException 
	 * @author ZhangDM(Mingly)
	 * @date 2012-10-21
	 */
	public static PublicKey getPublicKeyFromCer(String path) throws SecurityException{
		
		try{
			CertificateFactory certificateFactory = CertificateFactory.getInstance(CERT_TYPE, 
					new BouncyCastleProvider());
			FileInputStream certInputStream = new FileInputStream(path);
			Certificate certificate = certificateFactory.generateCertificate(certInputStream);
			certInputStream.close();
			
			return certificate.getPublicKey();
		}catch (Exception e) {
			logger.info(e.getMessage());
			throw new SecurityException("获取公钥失败，请检查");
		}
	}
	
	/**
	 * @description 从证书字节数组中获取公钥
	 * @param path
	 * @return
	 * @throws MsgException 
	 * @author ZhangDM(Mingly)
	 * @date 2012-10-21
	 */
	public static PublicKey getPublicKeyFromCerByte(byte[] cerByte) throws SecurityException{
		
		try{
			ByteArrayInputStream bais = new ByteArrayInputStream(cerByte);
			CertificateFactory certificateFactory = CertificateFactory.getInstance(CERT_TYPE, 
					new BouncyCastleProvider());
			Certificate certificate = certificateFactory.generateCertificate(bais);
			
			return certificate.getPublicKey();
		}catch (Exception e) {
			logger.info(e.getMessage());
			throw new SecurityException("获取公钥失败，请检查");
		}
	}
	
}
