/**
 * 版权所有(C) 2013 深圳市雁联计算系统有限公司
 * 创建:Administrator 2013-11-5
 */

package cn.com.ylink.ips.support.security;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.net.util.Base64;



/** 
 * @author Administrator
 * @date 2013-11-5
 * @description：TODO
 */

public class SignatureUtil {
	//数字签名，密钥算法  
    public  final String KEY_ALGORITHM="RSA";  
    
    /*
     * 签名/验证算法 
     */  
    public  final String SIGNATURE_ALGORITHM="SHA1withRSA";  
    
    
	/*发送方加签私钥*/
	private  String privateKey;
	
	/*接收方验证公钥*/
	private  String publicKey;
	
	/*编码方式*/
	private String charsetName;
	
	
    public SignatureUtil() {
    	this.privateKey="";
    	this.publicKey="";
    	this.charsetName="";
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	
	public void setCharsetName(String charsetName) {
		this.charsetName = charsetName;
	}

	/****************转码方法********************/
	
	public  byte[] decode(String encodeStr) {
		return Base64.decodeBase64(encodeStr);
	}

	public  String encode(byte[] str) throws Exception {
		return new String(Base64.encodeBase64(str),this.charsetName);
	}
	/****************转码方法********************/
	
	private  byte[] getPrivateKey() throws Exception {
		//需要先进行转码
		return decode(privateKey);
	}

	private  byte[] getPublicKey() throws Exception {
		//需要先进行转码
		return decode(publicKey);
	}

    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public  String getSignEncode(String message) throws Exception{
    	if("".equals(message)){
    		return "";
    	}
    	return encode(getSign(message, getPrivateKey()));
    }
    


	/**
     * 
     * @param message
     * @param signEncode
     * @return
     * @throws Exception
     */
    public  boolean verify(String message,String signEncode) throws Exception{
    	if(message==null||signEncode==null){
    		throw new IllegalArgumentException("报文或签名为null");
    	}
    	return verify(message, decode(signEncode), getPublicKey());
    }
    
	/**
	 * 根据私钥生成message的签名
	 * @param message
	 * @param privateKey 私钥
	 * @return
	 * @throws Exception 
	 */
	public  byte[] getSign(String message,byte[] privateKey) throws Exception{
        //取得私钥  
        PKCS8EncodedKeySpec pkcs8KeySpec=new PKCS8EncodedKeySpec(privateKey);  
        KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);  
        //生成私钥  
        PrivateKey priKey=keyFactory.generatePrivate(pkcs8KeySpec);  
        //实例化Signature  
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);  
        //初始化Signature  
        signature.initSign(priKey);  
        //更新  
        signature.update(message.getBytes(this.charsetName));  
        return signature.sign();  
	}
	
	/**
	 * 根据数字签名校验message是否正常（未被篡改）
	 * @param message
	 * @param sign 数字签名
	 * @param publicKey 公钥
	 * @return
	 * @throws Exception 
	 */
	public  boolean verify(String message,byte[] sign,byte[] publicKey) throws Exception{
        //转换公钥材料  
        //实例化密钥工厂  
        KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);  
        //初始化公钥  
        //密钥材料转换  
        X509EncodedKeySpec x509KeySpec=new X509EncodedKeySpec(publicKey);  
        //产生公钥  
        PublicKey pubKey=keyFactory.generatePublic(x509KeySpec);  
        //实例化Signature  
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);  
        //初始化Signature  
        signature.initVerify(pubKey);  
        //更新  
        signature.update(message.getBytes(this.charsetName));  
        //验证  
        return signature.verify(sign);  
	}
	
	/**
	 * 根据数字签名校验message是否正常（未被篡改）
	 * @param message
	 * @param sign 数字签名
	 * @param pubKeyStr 公钥
	 * @return
	 * @throws Exception 
	 */
	public static boolean  verify(String message, String signStr, String pubKeyStr) throws Exception{
        //转换公钥材料  
        //实例化密钥工厂  
        KeyFactory keyFactory=KeyFactory.getInstance("RSA");  
        //初始化公钥  
        //密钥材料转换  
        X509EncodedKeySpec x509KeySpec=new X509EncodedKeySpec(Base64.decodeBase64(pubKeyStr));  
        //产生公钥  
        PublicKey pubKey=keyFactory.generatePublic(x509KeySpec);  
        //实例化Signature  
        Signature signature = Signature.getInstance("SHA1withRSA");  
        //初始化Signature  
        signature.initVerify(pubKey);  
        //更新  
        signature.update(message.getBytes());  
        //验证  
        byte [] sign = Base64.decodeBase64(signStr);
        return signature.verify(sign);  
	}
	
	
	public static void main(String[] args) throws Exception {
		SignatureUtil signatureUtil = new SignatureUtil();
		signatureUtil.setCharsetName("UTF-8");
		/*
		//使用新的Base64工具重新生成公私玥：
		//地税私钥
		signatureUtil.setPrivateKey("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCN2RgUjcYurHB7Y1+QUXMnYS+KybEnjzqRjyjGnreYqZXkoxQ5EKmwPHUG5i1WSZyzSBMmi3ThWIt7Fa3VR376g6cn5A+zq31EJkXinvCtwDDznLNhorYDMp/2eO4s+lbNQfQ1ziZxqGMQ0eIXXsT7ZZIIasMONgctw0P3Ghy64xTsvMsdrzz7NWm+B/s26FUnzjl3okt7cb/7ZZzo9kBOvO53jTUYPFZXoS69DXkho6k6L2LAwSDzcAVfH6D+E5RBarKkwjYAvEwbNxUDmnWq+hqm/wMIbQ9juCUEpQ4sv8JvXphI5fQzI6z4Dd3Xwj6xG6hcMmfsTwoJkjq++riRAgMBAAECggEACJp9Smna9mPBIsrBNhiqml3bq1hWZWbbj46WU4225wyKKeb2SAvJrwXLt1Jot44ZcgpSFwloWm6rbxi+hWwjuIAZuGKtzSD+pQ6JOJiZUoCQt4Xun2JmFcWRTDrDf3jjoOl6LXVGgahL8e44/1/hJLphZB9Nm2QNhWoNikRML82VDoLosTC4rohFVrVFzpPSGqOH16WKkeISst5IepYzt3UF/jEf71TjHlc44+hb1CUG83vjgF66RdqtKy1yybm5Kp3veOewtLSMi3dZIuJHxRJ+qobUvMNlj4JDPApmtBDxjqn6PZcZIUOUcKjcLX/O2BuT8TGV1tyyb6M4yFPn/QKBgQC/MYu1yjCj0QW2/VPFapdA9/v7MmQEpCYg9x4eWSwMFwVFsg/7QvmNNC/woggKgH7WGsALg/XBxABFulCsmsFaEetT08H24/iURZKEagTzjBv2fMlO+fKVhNg4l1lCQQ26LZ0jlw4PkpYsGryoXoUDZCkk4vT/w0eChEZXLhrnzwKBgQC97a9+CcqZ/FW0PEDEhhPaQmSI5s3x3eMv7Rh1S80lnsc3N0fkeGdm2HS0mQp490JhGPDKb9nSK7RV9/oKyfIlTBXwDepPcfPgBU697KLtIoWKTk69KddCXJmMUaBfoUTUcCfMLmBAUCH4YVKzW3xz6nLdVG6bt8Z2sKlPVyARnwKBgQCAOQopcOAN/TCH+H0l+MZXm/gyDylks3Geyhw43jTcJtQyWai+KbyTJlzAVOpvoJ1vmaESF/3JZVmJadqQ53iknz/jiDCdE2ayibYpd7C6ZUybkjSv8pDuFZms2lPSSy6Znd208KlNaFGuYoADquAp1hKB2nPU03Ix5eQGs7T3oQKBgB3UV8yu0wAuTnOZRv7WpAWSieGqhkvncogTb9T0S8sNzfIRH5x1gJ/5fRLkOm3XP7iqRx80Q/yrWo5p/P52yy4qbgOe298QYaIck0pnAQvHcTvQorM5ABLvIjqUk2CRnpLS7p01524SZ9NvzaJXywsVlmbGnPyd93CqvFy2rVW3AoGAG0zLAqy6rCSp6fgbLPd2Qs2xPzOELnyC4qNqFq/0x4K23s+igIihwIRX84CyRVxDbd06x+6FTIkYjT6J3c2A04e2MtYCq/J8uYWzNupbEWvVEpM//oyVXQ4mMpGoXihJNMldziAPvltEPPQF80Q01FJUrh2En1/xFZXfbxw8Fg8=");
		//地税公钥
		signatureUtil.setPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjdkYFI3GLqxwe2NfkFFzJ2EvismxJ486kY8oxp63mKmV5KMUORCpsDx1BuYtVkmcs0gTJot04ViLexWt1Ud++oOnJ+QPs6t9RCZF4p7wrcAw85yzYaK2AzKf9njuLPpWzUH0Nc4mcahjENHiF17E+2WSCGrDDjYHLcND9xocuuMU7LzLHa88+zVpvgf7NuhVJ845d6JLe3G/+2Wc6PZATrzud401GDxWV6EuvQ15IaOpOi9iwMEg83AFXx+g/hOUQWqypMI2ALxMGzcVA5p1qvoapv8DCG0PY7glBKUOLL/Cb16YSOX0MyOs+A3d18I+sRuoXDJn7E8KCZI6vvq4kQIDAQAB");
	
		String message="地税地税8787283728738273872873878";
		//生成签名
		String signEncode = signatureUtil.getSignEncode(message);
		System.out.println(signEncode);
		//验签
		System.out.println(signatureUtil.verify(message, signEncode));
		
		System.out.println("结束");
		*/
		String pubkey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjdkYFI3GLqxwe2NfkFFzJ2EvismxJ486kY8oxp63mKmV5KMUORCpsDx1BuYtVkmcs0gTJot04ViLexWt1Ud++oOnJ+QPs6t9RCZF4p7wrcAw85yzYaK2AzKf9njuLPpWzUH0Nc4mcahjENHiF17E+2WSCGrDDjYHLcND9xocuuMU7LzLHa88+zVpvgf7NuhVJ845d6JLe3G/+2Wc6PZATrzud401GDxWV6EuvQ15IaOpOi9iwMEg83AFXx+g/hOUQWqypMI2ALxMGzcVA5p1qvoapv8DCG0PY7glBKUOLL/Cb16YSOX0MyOs+A3d18I+sRuoXDJn7E8KCZI6vvq4kQIDAQAB";
		String message="地税地税8787283728738273872873878";
		String signStr="PCT8QotuTdfQR82r2rRLewetNjAj9+Z4B5NPPe1Sbzv+J/BirsyHGAFWQpNCobDKt3ogVTB0PZRVW6tg6fOE8b1EDiSWZPVy/B/D7suViS4jKwiO5juHgNd6jwg/7v0BUJOHJshKrQZVlV1LrBMHICKK1orez5VkqVMonOoV/2iSwYPp8z/RsHfNTkyUcGNevQzIQiOgW7B2q1UopZzhztH6Eq6ollz70dv3IoTtly6xsjmqhdadG34JnzNshgFGmBdAlcuFmBTCvduZa327g546xIB1rjnpC548BgtHHRmvXkWGI30CTxUaY4VA3yrlj2TD0b7sxWKS5c6UMoqn+Q==";
		System.out.println(signatureUtil.verify(message,  signStr, pubkey));
		
	}
}
