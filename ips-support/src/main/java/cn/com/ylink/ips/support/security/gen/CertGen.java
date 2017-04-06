/**
 * 版权所有(C) 2013 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2013-1-8
 */

package cn.com.ylink.ips.support.security.gen;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;


/** 
 * @author ZhangDM(Mingly)
 * @date 2013-1-8
 * @description：生成密钥对
 */

public class CertGen {
	
	public void genCert(CertName certName) throws Exception{
		
		KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance(CipherAlgorithmName.RSA);
		
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();
		
		CertInfo certInfo = new CertInfo();
		certInfo.setIssuerDN(certName);
		certInfo.setSubjectDN(certName);
		certInfo.setCerSeqNum(new BigInteger("0123456789"));
		certInfo.setBeginDate(new Date());
		certInfo.setEndDate(new java.util.Date(new java.util.Date().getTime()+315360000000L));
		certInfo.setSignatureAlgorithm(CipherAlgorithmName.SHA1_WITH_RSA);
		certInfo.setPublicKey(publicKey);
		
		X509Certificate cert = CertFactory.getInstance().generateX509Cert(certInfo, privateKey);
		saveToJks("E:\\zlrt.jks", cert, privateKey, "111111", "zlrt", "111111");
	}
	
	public void genCert2(CertName certName) throws Exception{
		
		KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance(CipherAlgorithmName.RSA);
		
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();
		
		CertInfo certInfo = new CertInfo();
		certInfo.setIssuerDN(certName);
		certInfo.setSubjectDN(certName);
		certInfo.setCerSeqNum(new BigInteger("0123456789"));
		certInfo.setBeginDate(new Date());
		certInfo.setEndDate(new java.util.Date(new java.util.Date().getTime()+315360000000L));
		certInfo.setSignatureAlgorithm(CipherAlgorithmName.SHA1_WITH_RSA);
		certInfo.setPublicKey(publicKey);
		
		PrivateKey privateKey2 = CertUtil.getPrivateKeyFromJks("E:\\zlrt.jks", "111111", "111111", "zlrt");
		
		X509Certificate cert = CertFactory.getInstance().generateX509Cert(certInfo, privateKey2);
		saveToJks("E:\\hcfund.jks", cert, privateKey, "111111", "zlrt", "111111");
	}
	
	public static void saveToJks(String path,X509Certificate cert,PrivateKey key,
			String keyPass,String alias,String keyStorePass) throws CertException{
		
		try{
			KeyStore keyStore = KeyStore.getInstance("jks");
			keyStore.load(null, keyStorePass.toCharArray());
			keyStore.setKeyEntry(alias, key, keyPass.toCharArray(), new Certificate[]{cert});
			
			FileOutputStream jksOutputStream=new FileOutputStream(new File(path));
			keyStore.store(jksOutputStream, keyStorePass.toCharArray());
			jksOutputStream.close();
		}catch (Exception e) {
			throw CertException.createCertException(e);
		}
	}
}
