/**
 * 版权所有(C) 2013 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2013-1-8
 */

package cn.com.ylink.ips.support.security.gen;

import java.io.File;
import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import org.bouncycastle.jce.provider.BouncyCastleProvider;


/** 
 * @author ZhangDM(Mingly)
 * @date 2013-1-8
 * @description：证书工具类
 */

public abstract class CertUtil {
	
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
			throw new SecurityException("获取公钥失败，请检查");
		}
	}
	
	public static PrivateKey getPrivateKeyFromJks(String path,String keyStorePass,
			String keyPass,String alias) throws CertException{
		
		try{
			KeyStore keyStore=KeyStore.getInstance("jks");
			
			FileInputStream jksInputStream=new FileInputStream(new File(path));
			keyStore.load(jksInputStream, keyStorePass.toCharArray());
			PrivateKey privateKey = (PrivateKey)keyStore.getKey(alias, keyPass.toCharArray());
			jksInputStream.close();
			
			return privateKey;
		}catch (Exception e) {
			throw CertException.createCertException(e);
		}
	}
	
	public static X509Certificate getX509CertificateFromJks(String path,String keyStorePass,
			String alias) throws CertException{
		
		try{
			KeyStore keyStore=KeyStore.getInstance("jks");
			
			FileInputStream jksInputStream=new FileInputStream(new File(path));
			keyStore.load(jksInputStream, keyStorePass.toCharArray());
			Certificate certificate = keyStore.getCertificate(alias);
			jksInputStream.close();
			
			return (X509Certificate)certificate;
		}catch (Exception e) {
			throw CertException.createCertException(e);
		}
	}
	
	/**
	 * 从pfx证书中获取私钥
	 * @param path
	 * @param password
	 * @return
	 * @throws CertException
	 */
	public static PrivateKey getPrivateKeyFromPfx(String path,String pfxPass,String keyPass) throws SecurityException{
		try{
			KeyStore keyStore=KeyStore.getInstance("PKCS12");
			
			FileInputStream pfxInputStream=new FileInputStream(path);
			keyStore.load(pfxInputStream, pfxPass.toCharArray());
			
			Enumeration<String> aliases = keyStore.aliases();
			
			
			Key privateKey=null;
			
			while(aliases.hasMoreElements()){
				String alias = aliases.nextElement();
				privateKey=keyStore.getKey(alias, keyPass.toCharArray());
				break;
			}
			
			pfxInputStream.close();
			
			return (PrivateKey)privateKey;
			
		}catch (Exception e) {
			throw new SecurityException(e.getMessage());
		}
	}
}
