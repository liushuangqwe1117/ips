/**
 * 版权所有(C) 2013 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2013-1-8
 */

package cn.com.ylink.ips.support.security.gen;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

/** 
 * @author ZhangDM(Mingly)
 * @date 2013-1-8
 * @description：证书信息类
 */

public class CertInfo {
	
	private BigInteger cerSeqNum; //证书序列号
	
	private CertName issuerDN;   //证书颁发者
	
	private CertName subjectDN;   //证书主体
	
	private Date beginDate;  //生效日期
	
	private Date endDate;  //失效日期
	
	private PrivateKey privateKey; //签名私钥

	private PublicKey publicKey; //证书公钥
	
	private String signatureAlgorithm; //签名算法
	
	public CertInfo() {
		// TODO Auto-generated constructor stub
	}	

	/**
	 * 获取证书序列号
	 * @return
	 */
	public BigInteger getCerSeqNum() {
		return cerSeqNum;
	}

	/**
	 * 设置证书序列号
	 * @return
	 */
	public void setCerSeqNum(BigInteger cerSeqNum) {
		this.cerSeqNum = cerSeqNum;
	}

	/**
	 * 获取证书签发者
	 * @return
	 */
	public CertName getIssuerDN() {
		return issuerDN;
	}

	/**
	 * 设置证书签发者
	 * @return
	 */
	public void setIssuerDN(CertName issuerDN) {
		this.issuerDN = issuerDN;
	}

	/**
	 * 获取证书名称
	 * @return
	 */
	public CertName getSubjectDN() {
		return subjectDN;
	}

	/**
	 * 设置证书名称
	 * @return
	 */
	public void setSubjectDN(CertName subjectDN) {
		this.subjectDN = subjectDN;
	}

	/**
	 * 获取证书生效日期
	 * @return
	 */
	public Date getBeginDate() {
		return beginDate;
	}

	/**
	 * 设置证书生效日期
	 * @return
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * 获取证书结束日期
	 * @return
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * 设置证书结束日期
	 * @return
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 获取签名私钥
	 * @return
	 */
	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	/**
	 * 设置签名私钥
	 * @return
	 */
	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	/**
	 * 获取证书公钥
	 * @return
	 */
	public PublicKey getPublicKey() {
		return publicKey;
	}

	/**
	 * 设置证书公钥
	 * @return
	 */
	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	/**
	 * 获取证书签名算法
	 * @return
	 */
	public String getSignatureAlgorithm() {
		return signatureAlgorithm;
	}

	/**
	 * 设置证书签名算法
	 * @param signatureAlgorithm
	 */
	public void setSignatureAlgorithm(String signatureAlgorithm) {
		this.signatureAlgorithm = signatureAlgorithm;
	}
}
