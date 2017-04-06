/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2012-10-21
 */

package cn.com.ylink.ips.support.security;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.*;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;


/** 
 * @author ZhangDM(Mingly)
 * @date 2012-10-21
 * @description：密钥对工具类
 */

public abstract class KeyStoreUtil {
	
	/**
	 * @description 获取私钥
	 * @param path
	 * @param keyStorePwd
	 * @param keyPwd
	 * @param alias
	 * @return
	 * @throws SecurityException
	 * @author ZhangDM(Mingly)
	 * @date 2012-10-21
	 */
	public static PrivateKey getPrivateKeyFromJks(String path,String keyStorePwd,
			String keyPwd,String alias) throws SecurityException {
		
		try{
			KeyStore keyStore=KeyStore.getInstance("jks");
			
			FileInputStream jksInputStream=new FileInputStream(new File(path));
			keyStore.load(jksInputStream, keyStorePwd.toCharArray());
			PrivateKey privateKey = (PrivateKey)keyStore.getKey(alias, keyPwd.toCharArray());
			jksInputStream.close();
			
			return privateKey;
		}catch (Exception e) {
			throw new SecurityException(e.getMessage());
		}
	}
	
	/**
	 * @description 获取私钥
	 * @param path
	 * @param keyStorePwd
	 * @param keyPwd
	 * @param alias
	 * @return
	 * @throws SecurityException
	 * @author ZhangDM(Mingly)
	 * @date 2012-10-21
	 */
	public static PrivateKey getPrivateKeyFromJksByte(byte[] jksByte,String keyStorePwd,
			String keyPwd,String alias) throws SecurityException {
		
		try{
			KeyStore keyStore=KeyStore.getInstance("jks");
			
			InputStream in = new ByteArrayInputStream(jksByte);
			keyStore.load(in, keyStorePwd.toCharArray());
			PrivateKey privateKey = (PrivateKey)keyStore.getKey(alias, keyPwd.toCharArray());
			in.close();
			
			return privateKey;
		}catch (Exception e) {
			throw new SecurityException(e.getMessage());
		}
	}
	
	/**
	 * @description 获取证书
	 * @param path
	 * @param keyStorePwd
	 * @param alias
	 * @return
	 * @throws SecurityException
	 * @author ZhangDM(Mingly)
	 * @date 2012-10-21
	 */
	public static X509Certificate getX509CertificateFromJks(String path,String keyStorePwd,
			String alias) throws SecurityException {
		
		try{
			KeyStore keyStore=KeyStore.getInstance("jks");
			
			FileInputStream jksInputStream=new FileInputStream(new File(path));
			keyStore.load(jksInputStream, keyStorePwd.toCharArray());
			Certificate certificate = keyStore.getCertificate(alias);
			jksInputStream.close();
			
			return (X509Certificate)certificate;
		}catch (Exception e) {
			throw new SecurityException(e.getMessage());
		}
	}
}
