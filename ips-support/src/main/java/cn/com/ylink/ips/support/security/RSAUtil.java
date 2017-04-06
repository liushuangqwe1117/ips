/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2012-10-21
 */

package cn.com.ylink.ips.support.security;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.*;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import javax.crypto.Cipher;

import org.apache.commons.lang.ArrayUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @author mjc
 * @date 2012-10-21
 * @description：RSA算法工具类
 */

public abstract class RSAUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(RSAUtil.class);
	 /** *//** 
     * RSA最大加密明文大小 
     */  
    private static final int MAX_ENCRYPT_BLOCK = 117;  
      
    /** *//** 
     * RSA最大解密密文大小 
     */  
    private static final int MAX_DECRYPT_BLOCK = 128;  
	
    private static final int MAX_DECRYPT_BLOCK_NEW = 256;
    
	/**
	 * 非对称加密密钥算法
	 */
	private static final String KEY_ALGORITHM = "RSA";
	
	/**
	 * 非对称加签密钥算法
	 */
	private static final String SHA1_WITH_RSA ="SHA1withRSA";
	
	private static final Provider provider = new BouncyCastleProvider();
	
	/**
	 * @description 私钥解密
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception 
	 * @author mjc(Mingly)
	 * @date 2012-10-21
	 */
	public static byte[] decryptNoLimitByPrivateKey(byte[] data, PrivateKey privateKey) throws SecurityException {
		
		try{
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  
	        cipher.init(Cipher.DECRYPT_MODE, privateKey);  
	        return cipher.doFinal(data);  
		}catch (Exception e) {
			logger.info(e.getMessage());
			throw new SecurityException("私钥解密失败，请检查");
		}
		
	}
	
	/** *//** 
     * <P> 
     * 私钥解密 对数据分段解密
     * </p> 
     *  
     * @param encryptedData 已加密数据 
     * @param privateKey 私钥(BASE64编码) 
     * @param isOldSection true:使用128位进行分段  false:使用256位进行分段
     * @return 
     * @throws Exception 
     */  
    public static byte[] decryptByPrivateKey(byte[] encryptedData, PrivateKey privateKey, boolean isOldSection)  
            throws Exception { 
    	try{
    		int maxLength = 0;
    		if (isOldSection) {
    			maxLength = MAX_DECRYPT_BLOCK;
    		} else {
    			maxLength = MAX_DECRYPT_BLOCK_NEW;
    		}
    		
	    	Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  
	        cipher.init(Cipher.DECRYPT_MODE, privateKey);  
	        int inputLen = encryptedData.length;  
	        ByteArrayOutputStream out = new ByteArrayOutputStream();  
	        int offSet = 0;  
	        byte[] cache;  
	        int i = 0;  
	        // 对数据分段解密  
	        while (inputLen - offSet > 0) {  
	            if (inputLen - offSet > maxLength) {  
	                cache = cipher.doFinal(encryptedData, offSet, maxLength);  
	            } else {  
	                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);  
	            }  
	            out.write(cache, 0, cache.length);  
	            i++;  
	            offSet = i * maxLength;  
	        }  
	        byte[] decryptedData = out.toByteArray();  
	        out.close();  
	        return decryptedData;  
    	}catch (Exception e) {
			logger.info(e.getMessage());
			throw new SecurityException("私钥解密失败，请检查");
		}
    }  
	
	/**
	 * @description 公钥解密 --对数据分段解密
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception 
	 * @author mjc
	 * @date 2012-10-21
	 */
	public static byte[] decryptByPublicKey(byte[] data, PublicKey publicKey) throws SecurityException {
		
		try{
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); 
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			
			byte[] encodedByteArray = new byte[]{};
			for (int i = 0; i < data.length; i += 128) {
				byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i,  
                        i + 128));
				encodedByteArray = ArrayUtils.addAll(encodedByteArray, doFinal);
			}
			
			return encodedByteArray;
			
		}catch (Exception e) {
			logger.info(e.getMessage());
			throw new SecurityException("公钥解密失败，请检查");
		}
		
	}
	
	
	/** *//** 
     * <p> 
     * 公钥解密  --对数据分段解密(例子程序二)
     * </p> 
     *  
     * @param encryptedData 已加密数据 
     * @param publicKey 公钥 
     * @return 
     * @throws Exception 
     */  
    public static byte[] decryptByPublicKey1(byte[] data, PublicKey publicKey)  
            throws Exception {  
    	Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); 
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
        int inputLen = data.length;  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段解密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {  
                cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(data, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_DECRYPT_BLOCK;  
        }  
        byte[] decryptedData = out.toByteArray();  
        out.close();  
        return decryptedData;  
    }  
    
    
	/**
	 * @description 公钥解密，对加密内容没有字节上限制
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws SecurityException
	 * @author mjc
	 * @date 2014-6-9
	 */
	public static byte[] decryptNoLimitByPublicKey(byte[] data, PublicKey publicKey) 
			throws SecurityException {
		
		try{
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			
			byte[] doFinal = cipher.doFinal(data);
			return doFinal;
		}catch (Exception e) {
			logger.info(e.getMessage());
			throw new SecurityException("公钥解密失败，请检查");
		}
		
	}
	
	
	
    
    
	/**
	 * @description 私钥加密
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws Exception 
	 * @author mjc
	 * @date 2012-10-21
	 */
	public static byte[] encryptByPrivateKey(byte[] data, PrivateKey privateKey) throws SecurityException {
		try{
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			
			byte[] encodedByteArray = new byte[]{};
			for (int i = 0; i < data.length; i += 100) {
				byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i,  
                        i + 100));
				encodedByteArray = ArrayUtils.addAll(encodedByteArray, doFinal);
			}
			
			return encodedByteArray;
		}catch (Exception e) {
			logger.info(e.getMessage());
			throw new SecurityException("私钥加密失败，请检查");
		}
	}
	
	/**
	 * @description 私钥加密，对加密内容没有字节上限制
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws SecurityException
	 * @author mjc
	 * @date 2014-6-9
	 */
	public static byte[] encryptNoLimitByPrivateKey(byte[] data, PrivateKey privateKey) 
			throws SecurityException {
		try{
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			
			return cipher.doFinal(data);  
		}catch (Exception e) {
			logger.info(e.getMessage());
			throw new SecurityException("私钥加密失败，请检查");
		}
	}
	
	
	/** *//** 
     * <p> 
     * 私钥加密 
     * </p> 
     *  
     * @param data 源数据 
     * @param privateKey 私钥(BASE64编码) 
     * @return 
     * @throws Exception 
     */  
    public static byte[] encryptByPrivateKey1(byte[] data, PrivateKey privateKey)  
            throws Exception {  
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);  
        int inputLen = data.length;  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段加密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {  
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(data, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_ENCRYPT_BLOCK;  
        }  
        byte[] encryptedData = out.toByteArray();  
        out.close();  
        return encryptedData;  
    }  
	
	//公钥加密  
    public byte[] PublicEncrypt(byte[] data, PublicKey publicKey)throws Exception {  
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
        return cipher.doFinal(data);  
    }  
    
    /** *//** 
     * <p> 
     * 公钥加密 对加密内容没有字节上限制
     * </p> 
     *  
     * @param data 源数据 
     * @param publicKey 公钥(BASE64编码) 
     * @return 
     * @throws Exception 
     */  
    public static byte[] PublicEncrypt1(byte[] data, PublicKey publicKey)  
            throws Exception {  
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
        int inputLen = data.length;  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段加密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {  
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(data, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_ENCRYPT_BLOCK;  
        }  
        byte[] encryptedData = out.toByteArray();  
        out.close();  
        return encryptedData;  
    }  
	/**
	 * @description 加签
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws SecurityException
	 * @author ZhangDM(Mingly)
	 * @date 2012-10-21
	 */
	public static byte[] signByPrivateKey(byte[] data, PrivateKey privateKey) throws SecurityException {
		
		try{
			Signature signature = Signature.getInstance(SHA1_WITH_RSA, provider);
			signature.initSign(privateKey);
			signature.update(data);
			return signature.sign();
		}catch (Exception e) {
			logger.info(e.getMessage());
			throw new SecurityException("加签失败，请检查");
		}
	}
	
	/**
	 * @description 验签
	 * @param data
	 * @param signedData
	 * @param publicKey
	 * @return
	 * @throws SecurityException
	 * @author ZhangDM(Mingly)
	 * @date 2012-10-21
	 */
	public static boolean verifyByPublicKey(byte[] data, byte[] signedData, PublicKey publicKey) throws SecurityException {
		
		try{
			Signature signature = Signature.getInstance(SHA1_WITH_RSA, provider);
			signature.initVerify(publicKey);
			signature.update(data);
			return signature.verify(signedData);
		}catch (Exception e) {
			logger.info(e.getMessage());
			throw new SecurityException("验签失败，请检查");
		}
	}
	
	public PublicKey getPublicKey(String cerPath) {
		PublicKey pk = null;
		CertificateFactory cf;
		try {
			cf = CertificateFactory.getInstance("X.509");
			FileInputStream inputStream = new FileInputStream(cerPath);
			X509Certificate certificate = (X509Certificate) cf
					.generateCertificate(inputStream);
			pk = certificate.getPublicKey();
			inputStream.close();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return pk;
	}
	
	/** *//** 
     * <p> 
     * 获取私钥 
     * </p> 
     *  
     * @param keyMap 密钥对 
     * @return 
     * @throws Exception 
     */  
	private static PrivateKey getPrivateKey(String pfxPath, String pfxPassword) throws Exception{

		//Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		InputStream fis = null;
		PrivateKey priKey = null;
		KeyStore keyStore = null;
		try{
			File file = new File(pfxPath);
			fis = new FileInputStream(file);
			/*
			if (Security.getProvider("BC") == null)
			{
				fis.close();
				throw new Exception("不能Load入BouncyCastle!");
			}*/

			// Create a keystore object
			keyStore = KeyStore.getInstance("PKCS12");
			//KeyStore keyStore = KeyStore.getInstance("PKCS12", Security.getProvider("BC"));
			// Load the file into the keystore
			keyStore.load(fis, pfxPassword.toCharArray());

			// String aliaesName = "abcd";
			Enumeration aliases = keyStore.aliases();
			String keyAlias = null;
			if (aliases != null){
				while (aliases.hasMoreElements()){
					keyAlias = (String) aliases.nextElement();
					logger.info("Alias = [" +  keyAlias + "]");
					priKey = (PrivateKey) (keyStore.getKey(keyAlias, pfxPassword.toCharArray()));
					// System.out.println( priKey);
				}
			}
			
		}catch (Exception e){
			logger.info(e.getMessage(), e);
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}finally{
			if (fis != null)
				fis.close();
			// System.out.println(e.getMessage());
			if (fis != null)
				fis.close();
		}
		return priKey;
	}
	
	
	
	
	
	 /** *//** 
     * <p> 
     * 生成密钥对(公钥和私钥) 
     * </p> 
     *  
     * @return 
     * @throws Exception 
     */  
//    public static Map<String, Object> genKeyPair() throws Exception {  
//        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);  
//        keyPairGen.initialize(1024);  
//        KeyPair keyPair = keyPairGen.generateKeyPair();  
//        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  
//        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();  
//        Map<String, Object> keyMap = new HashMap<String, Object>(2);  
//        keyMap.put(PUBLIC_KEY, publicKey);  
//        keyMap.put(PRIVATE_KEY, privateKey);  
//        return keyMap;  
//    }  
      
    /** *//** 
     * <p> 
     * 用私钥对信息生成数字签名 
     * </p> 
     *  
     * @param data 已加密数据 
     * @param privateKey 私钥(BASE64编码) 
     *  
     * @return 
     * @throws Exception 
     */  
//    public static String sign(byte[] data, String privateKey) throws Exception {  
//        byte[] keyBytes = Base64Utils.decode(privateKey);  
//        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
//        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
//        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);  
//        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);  
//        signature.initSign(privateK);  
//        signature.update(data);  
//        return Base64Utils.encode(signature.sign());  
//    }  
  
    /** *//** 
     * <p> 
     * 校验数字签名 
     * </p> 
     *  
     * @param data 已加密数据 
     * @param publicKey 公钥(BASE64编码) 
     * @param sign 数字签名 
     *  
     * @return 
     * @throws Exception 
     *  
     */  
//    public static boolean verify(byte[] data, String publicKey, String sign)  
//            throws Exception {  
//        byte[] keyBytes = Base64Utils.decode(publicKey);  
//        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);  
//        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
//        PublicKey publicK = keyFactory.generatePublic(keySpec);  
//        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);  
//        signature.initVerify(publicK);  
//        signature.update(data);  
//        return signature.verify(Base64Utils.decode(sign));  
//    }
	
	
	
	
	
	/**
	 * @description 使用私钥解密，分段128位
	 * @param encryByteData 公钥加密数据
	 * @param pfxPath pfx 路径
	 * @param pfxPassword  pfx 证书密码
	 * @return
	 * @throws SecurityException
	 * @author mjc
	 * @date 2015-3-24
	 */
	public static byte[] decrypt(byte[] encryByteData, String pfxPath, String pfxPassword){
		byte[] decryptedData = null;
		try{
			decryptedData = RSAUtil.decryptByPrivateKey(encryByteData, getPrivateKey(pfxPath, pfxPassword), true);
		}catch(Exception e){
			e.printStackTrace();
		}
		return decryptedData;
	}
	
	/**
	 * @description 使用私钥解密，分段256位
	 * @param encryByteData 公钥加密数据
	 * @param pfxPath pfx 路径
	 * @param pfxPassword  pfx 证书密码
	 * @return
	 * @throws SecurityException
	 * @author mjc
	 * @date 2015-3-24
	 */
	public static byte[] decryptNew(byte[] encryByteData, String pfxPath, String pfxPassword){
		byte[] decryptedData = null;
		try{
			decryptedData = RSAUtil.decryptByPrivateKey(encryByteData, getPrivateKey(pfxPath, pfxPassword), false);
		}catch(Exception e){
			e.printStackTrace();
		}
		return decryptedData;
	}
	
	
	
	
}
