/**
 * 版权所有(C) 2013 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2013-1-8
 */

package cn.com.ylink.ips.support.security.gen;

import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;

/** 
 * @author ZhangDM(Mingly)
 * @date 2013-1-8
 * @description：证书工厂类
 */

public class CertFactory {
	
	private static final CertFactory certFactory=new CertFactory();
	
	private CertFactory(){
		
	}
	
	public  static CertFactory getInstance(){
		return certFactory;
	}
	
	
	public X509Certificate generateX509Cert(CertInfo certInfo, PrivateKey privateKey) throws CertException{
		
		Security.addProvider(new BouncyCastleProvider());
		
		X509V3CertificateGenerator x509v3CertificateGenerator=new X509V3CertificateGenerator();
		
		x509v3CertificateGenerator.setIssuerDN(new X509Name(certInfo.getIssuerDN().toString()));
		x509v3CertificateGenerator.setSubjectDN(new X509Name(certInfo.getSubjectDN().toString()));
		x509v3CertificateGenerator.setSerialNumber(certInfo.getCerSeqNum());
		x509v3CertificateGenerator.setNotBefore(certInfo.getBeginDate());
		x509v3CertificateGenerator.setNotAfter(certInfo.getEndDate());
		x509v3CertificateGenerator.setPublicKey(certInfo.getPublicKey());
		x509v3CertificateGenerator.setSignatureAlgorithm(certInfo.getSignatureAlgorithm());
		
		try {
			return x509v3CertificateGenerator.generate(privateKey);
		} catch (Exception e) {
			throw CertException.createCertException(e);
		}  
		
	}
}
