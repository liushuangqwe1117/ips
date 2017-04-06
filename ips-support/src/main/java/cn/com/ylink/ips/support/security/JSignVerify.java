/**
 * 版权所有(C) 2013 深圳市雁联计算系统有限公司
 * 创建:Administrator 2013-10-15
 */

package cn.com.ylink.ips.support.security;

/**
 * @author Administrator
 * @date 2013-10-15
 * @description：TODO
 */

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.Cipher;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;

/**
 * @author Administrator
 * @date 2013-9-30
 * @description：TODO
 */

public class JSignVerify {
    private static Log log = LogFactory.getLog(JSignVerify.class);
    PrivateKey priKey;
    PublicKey pubKey;

    /**
     * @param args
     */
    public static void main(String[] args) {
        String strData = "aaaaaabbbbbbbdddddeeeeffffffgggggg原始测试数据";
        String pathPfx = "E:/work/缴税平台/szfesc_wyjs/trunk/documents/others/证书/中心证书/szfs000000000000/szfs000000000000.pfx";//"E:/szfs00000000000001.pfx";
        String cerFile = "E:/work/缴税平台/szfesc_wyjs/trunk/documents/others/证书/中心证书/szfs000000000000/szfs000000000000.crt";//"E:/szfs00000000000001.crt";
        //String keyFile = "E:/szfs00000000000001.key";
        String pfxPwd = "12345678";
        JSignVerify signverify = null;
        try {
            //byte[] sign = ReturnValue.sign(strData.getBytes(), strData.getBytes().length, keyFile.getBytes(), pfxPwd.toCharArray());
            byte[] sign = JSignVerify.signSHA1(strData, pathPfx, pfxPwd);
            //String merCert = new IcbcUtil().getnKey("", cerFile);
            //ReturnValue.verifySign(strData.getBytes(), strData.getBytes().length, ReturnValue.base64dec(merCert.getBytes()), sign);

            System.out.println(new String(sign));
            System.out.println("核签结果：" + JSignVerify.verifyMsgSignSHA1(cerFile, sign, strData));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 签名函数入口:pfx 格式 SHA1withRSA 算法
     * message:  消息明文
     * keyFile:  私钥文件，自己的证书文件，一般为.pfx结尾
     * password: 私钥文件密码
     */
    public static byte[] signSHA1(String message, String keyFile, String password)
            throws InvalidKeyException, SignatureException {
        /*
        Crypt  crypt = new Crypt("gbk");
		//返回的是256位，即128位的base16编码
		String signedMsg = crypt.sign(strData, pathPfx, pfxPwd);
		byte[] binaryData = Base16.hexStrToBytes(signedMsg);
		return new String(Base64.encodeBase64(binaryData));
		*/
        byte[] signed = null;
        try {
            String alg = "SHA1withRSA";
            JSignVerify vv = new JSignVerify();
            vv.ReadPKCS12PrivateKey(keyFile, password);
            signed = vv.SignMemory(message.getBytes(), alg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signed;
    }


    /**
     * 签名函数入口:pfx 格式 MD5withRSA 算法
     * message:  消息明文
     * keyFile:  私钥文件，自己的证书文件，一般为.pfx结尾
     * password: 私钥文件密码
     */
    public static byte[] signMD5withRSA(String message, String keyFile, String password)
            throws InvalidKeyException, SignatureException {
		/*
		Crypt  crypt = new Crypt("gbk");
		//返回的是256位，即128位的base16编码
		String signedMsg = crypt.sign(strData, pathPfx, pfxPwd);
		byte[] binaryData = Base16.hexStrToBytes(signedMsg);
		return new String(Base64.encodeBase64(binaryData));
		*/
        byte[] signed = null;
        try {
            String alg = "MD5withRSA";
            JSignVerify vv = new JSignVerify();
            vv.ReadPKCS12PrivateKey(keyFile, password);
            signed = vv.SignMemory(message.getBytes(), alg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signed;
    }

    /**
     * 签名函数入口:pfx 格式 SHA1withRSA 算法
     * message:  消息明文
     * keyFile:  私钥文件，自己的证书文件，一般为.pfx结尾
     * password: 私钥文件密码
     */
    public static byte[] signSHA1(String message, String keyFile, String password, String encoding)
            throws InvalidKeyException, SignatureException {
		/*
		Crypt  crypt = new Crypt("gbk");
		//返回的是256位，即128位的base16编码
		String signedMsg = crypt.sign(strData, pathPfx, pfxPwd);
		byte[] binaryData = Base16.hexStrToBytes(signedMsg);
		return new String(Base64.encodeBase64(binaryData));
		*/
        byte[] signed = null;
        try {
            String alg = "SHA1withRSA";
            JSignVerify vv = new JSignVerify();
            vv.ReadPKCS12PrivateKey(keyFile, password);
            signed = vv.SignMemory(message.getBytes(encoding), alg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signed;
    }

    /**
     * 核签函数入口: SHA1withRSA 算法，不限制公钥格式
     * cerFile:  公钥文件
     * checkValue:  签名数据
     * xmlMsg: 原串
     */
    public static boolean verifyMsgSignSHA1(String cerFile, byte[] checkValue, String xmlMsg) throws Exception {
        // KFTZFPT.cer
        JSignVerify vv = null;
        boolean bFlag;
        try {
            vv = new JSignVerify();
            vv.ReadX509Certificate(cerFile);
            bFlag = vv.VerifyMemorySHA1(xmlMsg.getBytes(), checkValue);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception("验证签名失败," + e.getMessage());
        }
        return bFlag;


    }

    /**
     * 核签函数入口: SHA1withRSA 算法，不限制公钥格式
     * cerFile:  公钥文件
     * checkValue:  签名数据
     * xmlMsg: 原串
     */
    public static boolean verifyMsgSignSHA1(String cerFile, byte[] checkValue,
                                            String xmlMsg, String encoding) throws Exception {
        // KFTZFPT.cer
        JSignVerify vv = null;
        boolean bFlag;
        try {
            vv = new JSignVerify();
            vv.ReadX509Certificate(cerFile);
            bFlag = vv.VerifyMemorySHA1(xmlMsg.getBytes(encoding), checkValue);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception("验证签名失败," + e.getMessage());
        }
        return bFlag;


    }

    /**
     * 核签函数入口: SHA1withRSA 算法，不限制公钥格式
     * cerFile:  公钥文件
     * checkValue:  签名数据
     * xmlMsg: 原串
     */
    public static boolean verifyMsgSignSHA1(byte[] certData, byte[] checkValue, String xmlMsg) throws Exception {
        // KFTZFPT.cer
        JSignVerify vv = null;
        boolean bFlag;
        try {
            vv = new JSignVerify();
            vv.ReadX509Certificate(certData);
            bFlag = vv.VerifyMemorySHA1(xmlMsg.getBytes(), checkValue);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception("验证签名失败," + e.getMessage());
        }
        return bFlag;


    }

    /**
     * 核签函数入口: SHA1withRSA 算法，不限制公钥格式
     * cerFile:  公钥文件
     * checkValue:  签名数据
     * xmlMsg: 原串
     */
    public static boolean verifyMsgSignSHA1(byte[] certData, byte[] checkValue,
                                            byte[] xmlMsg) throws Exception {
        // KFTZFPT.cer
        JSignVerify vv = null;
        boolean bFlag;
        try {
            vv = new JSignVerify();
            vv.ReadX509Certificate(certData);
            bFlag = vv.VerifyMemorySHA1(xmlMsg, checkValue);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception("验证签名失败," + e.getMessage());
        }
        return bFlag;


    }

    public static byte[] SignMemory(byte[] info, PrivateKey priKey) {
        // 用私钥对信息生成数字签名
        String alg = "SHA1withRSA";
        java.security.Signature signet;
        byte[] signed = null;
        try {
            signet = java.security.Signature.getInstance(alg);
            signet.initSign(priKey);
            signet.update(info);
            signed = signet.sign(); // 对信息的数字签名
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return signed;
    }

    public static byte[] SignMemory(byte[] info, PrivateKey priKey, String alg) {
        // 用私钥对信息生成数字签名
        java.security.Signature signet;
        byte[] signed = null;
        try {
            signet = java.security.Signature.getInstance(alg);
            signet.initSign(priKey);
            signet.update(info);
            signed = signet.sign(); // 对信息的数字签名
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return signed;
    }

    public static boolean VerifyMemory(byte[] info, byte[] signed, PublicKey pubKey, String alg) throws Exception {
        // method = "SHA1withRSA" "MD5withRSA"
        java.security.Signature signetcheck = java.security.Signature.getInstance(alg);
        signetcheck.initVerify(pubKey);
        signetcheck.update(info);
        if (signetcheck.verify(signed)) {
            System.out.println("验证签名正常");
            return true;
        } else {
            System.out.println("验证签名失败");
            return false;
        }

    }

    public static byte[] PrivatekeyRSAEncryptWithPKCS1(byte[] buf, PrivateKey privateKey) throws Exception {
        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        // Cipher rsaCipher=Cipher.getInstance("RSA");
        rsaCipher.init(Cipher.ENCRYPT_MODE, privateKey);
        rsaCipher.update(buf);
        return rsaCipher.doFinal();

    }

    public static byte[] PublickeyDecryptWithPKCS1(byte[] cipherText, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] plainText = cipher.doFinal(cipherText);

        return plainText;

    }

    // 从X509证书文件读取公钥
    // .crt .cer文件都可以读取 .cer是IE导出的公钥证书（der格式）
    // -----BEGIN CERTIFICATE-----开始 文件头不许有其它内容
    // -----END CERTIFICATE-----
    public void ReadX509Certificate(String file) throws Exception {
        FileInputStream is = new FileInputStream(file);
        ReadX509Certificate(is);
    }

    // 从X509证书文件读取公钥
    // .crt .cer文件都可以读取 .cer是IE导出的公钥证书（der格式）
    // -----BEGIN CERTIFICATE-----开始 文件头不许有其它内容
    // -----END CERTIFICATE-----
    public void ReadX509Certificate(byte[] certData) throws Exception {
        InputStream is = new ByteArrayInputStream(certData);
        ReadX509Certificate(is);
    }

    public void ReadX509Certificate(InputStream is) throws Exception {

        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        // BufferedReader br = new BufferedReader(new InputStreamReader( is ));

        java.security.cert.Certificate cert = cf.generateCertificate(is);

        pubKey = cert.getPublicKey();

    }

    // 读取PKCS12格式的key（私钥）pfx格式
    public void ReadPKCS12PrivateKey(String filename, String pfxPassword) throws Exception {

        //Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        InputStream fis = null;
        try {
            File file = new File(filename);
            fis = new FileInputStream(file);
			/*
			if (Security.getProvider("BC") == null)
			{
				fis.close();
				throw new Exception("不能Load入BouncyCastle!");
			}*/

            // Create a keystore object
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            //KeyStore keyStore = KeyStore.getInstance("PKCS12", Security.getProvider("BC"));
            // Load the file into the keystore
            keyStore.load(fis, pfxPassword.toCharArray());

            // String aliaesName = "abcd";
            Enumeration aliases = keyStore.aliases();
            String keyAlias = null;
            if (aliases != null) {
                while (aliases.hasMoreElements()) {
                    keyAlias = (String) aliases.nextElement();
                    // System.out.println( keyAlias);
                    priKey = (PrivateKey) (keyStore.getKey(keyAlias, pfxPassword.toCharArray()));
                    // System.out.println( priKey);
                }
            }

        } catch (Exception e) {
            log.debug(e.getMessage(), e);
            if (fis != null)
                fis.close();
            throw e;
            // System.out.println(e.getMessage());
        }
        if (fis != null)
            fis.close();
    }

    // 读取PKCS8格式的私钥(openssl 生成的就是该格式)
    // -----BEGIN CERTIFICATE-----开始 文件头不许有其它内容
    // -----END CERTIFICATE-----
    public void ReadPKCS8PrivateKey(String keyfile, String password) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(keyfile));
        String s = br.readLine();

        String str = "";
        s = br.readLine();
        while (s.charAt(0) != '-') {
            str += s + "\r";
            s = br.readLine();
        }

        // 编码转换，进行BASE64解码
        byte[] b = Base64.decodeBase64(str);
        // 生成私匙
        // KeyFactory kf = KeyFactory.getInstance("RSA");//"SunJSSE"
        KeyFactory kf = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(b);
        priKey = kf.generatePrivate(keySpec);
    }

    // 读取原始私钥
    public void ReadRawPrivateKey(String keyfile) throws Exception {

        FileInputStream br = new FileInputStream(keyfile);

        byte[] b1 = new byte[2000];
        int k = br.read(b1);

        byte[] b = new byte[k];
        System.arraycopy(b1, 0, b, 0, k);
        // 生成私匙
        // KeyFactory kf = KeyFactory.getInstance("RSA");//"SunJSSE"
        KeyFactory kf = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(b);
        priKey = kf.generatePrivate(keySpec);

    }

    // 读取原始公钥
    public void ReadRawPublicKey(String keyfile) throws Exception {

        FileInputStream br = new FileInputStream(keyfile);

        byte[] b1 = new byte[2000];
        int k = br.read(b1);

        byte[] b = new byte[k];
        System.arraycopy(b1, 0, b, 0, k);
        // 生成私匙
        // KeyFactory kf = KeyFactory.getInstance("RSA");//"SunJSSE"
        KeyFactory kf = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(b);
        pubKey = kf.generatePublic(keySpec);
    }

    // 读取原始公钥
    public void ReadRawPublicKey(byte[] rawpublickey) throws Exception {

        // 生成私匙
        // KeyFactory kf = KeyFactory.getInstance("RSA");//"SunJSSE"
        KeyFactory kf = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(rawpublickey);
        pubKey = kf.generatePublic(keySpec);
    }

    // 根据128位大整数生成公钥
    public void ReadRawPublicKeyFromBigInt(String base16pubkey) throws Exception {
        byte e[] =
                {0, 1, 0, 1};

        byte[] bigint = Base16.hexStrToBytes("00" + base16pubkey);
        try {
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec pubkf = new RSAPublicKeySpec(new BigInteger(bigint), new BigInteger(e));
            pubKey = keyf.generatePublic(pubkf);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String ReadCerFile(String certfile) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(certfile));
        String s = "";
        String str = "";
        boolean bFlag = false;
        while (true) {
            s = br.readLine();
            if (s == null)
                break;
            s.trim();
            if (s.equals(""))
                continue;

            if (s.charAt(0) == '-') {
                if (bFlag)
                    break;
                bFlag = true;
                continue;
            }
            // 提取证书
            if (bFlag) {
                str += s + "\r";
                continue;
            }

        }

        if (str.equals("")) {
            throw new Exception("读取证书错误");
        }
        return str;
    }

    // 读取X509格式公钥pem证书
    public void ReadX509PublicKey(String certfile) throws Exception {
        String str = ReadCerFile(certfile);
        // 编码转换，进行BASE64解码
        byte[] b = Base64.decodeBase64(str);

        // 生成公钥
        KeyFactory kf = KeyFactory.getInstance("RSA");
        // KeyFactory kf = KeyFactory.getInstance("RSA", new
        // org.bouncycastle.jce.provider.BouncyCastleProvider());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(b);

        pubKey = kf.generatePublic(keySpec);

    }

    // 签名algorithm="SHA1withRSA" "MD5withRSA"
    public byte[] SignMemory(byte[] info, String algorithm) throws Exception {
        // 用私钥对信息生成数字签名
        java.security.Signature signet = java.security.Signature.getInstance(algorithm);// MD5withRSA//
        signet.initSign(priKey);
        signet.update(info);
        byte[] signed = signet.sign(); // 对信息的数字签名
        return signed;
    }

    // 验证内存签名
    public boolean VerifyMemoryMD5(byte[] info, byte[] signed) throws Exception {
        return VerifyMemory(info, signed, "MD5withRSA");
    }

    public boolean VerifyMemorySHA1(byte[] info, byte[] signed) throws Exception {
        return VerifyMemory(info, signed, "SHA1withRSA");
    }

    public boolean VerifyMemory(byte[] info, byte[] signed, String method) throws Exception {
        // method = "SHA1withRSA" "MD5withRSA"
        java.security.Signature signetcheck = java.security.Signature.getInstance(method);
        signetcheck.initVerify(pubKey);
        signetcheck.update(info);
        if (signetcheck.verify(signed)) {
            return true;
        } else {
            return false;
        }

    }

    public byte[] SignFile(String inFile) throws Exception {
        byte[] buf = new byte[1024];
        int num;
        FileInputStream fin = new FileInputStream(inFile);
        // String myinfo =
        // "orderId=10dkfadsfksdkssdkd&amount=80&orderTime=20060509"; // 要签名的信息
        // signet.update(myinfo.getBytes("ISO-8859-1"));

        // 用私钥对信息生成数字签名
        java.security.Signature signet = java.security.Signature.getInstance("MD5withRSA");
        signet.initSign(priKey);

        while ((num = fin.read(buf, 0, buf.length)) != -1) {
            signet.update(buf, 0, num);
        }

        byte[] signed = signet.sign(); // 对信息的数字签名
        log.error("签名并生成文件成功");
        return signed;
    }

    public boolean VerifyFileMD5(String inFile, byte[] pSigBuf, int SigLen) throws Exception {
        return VerifyFile(inFile, pSigBuf, SigLen, "MD5withRSA");
    }

    public boolean VerifyFileSHA1(String inFile, byte[] pSigBuf, int SigLen) throws Exception {
        return VerifyFile(inFile, pSigBuf, SigLen, "SHA1withRSA");
    }

    public boolean VerifyFile(String inFile, byte[] pSigBuf, int SigLen, String method) throws Exception {
        // method = "MD5withRSA" "SHA1withRSA"
        byte[] buf = new byte[1024];
        int num;
        FileInputStream fin = new FileInputStream(inFile);

        java.security.Signature signetcheck = java.security.Signature.getInstance(method);
        signetcheck.initVerify(pubKey);
        while ((num = fin.read(buf, 0, buf.length)) != -1) {
            signetcheck.update(buf, 0, num);
        }

        if (signetcheck.verify(pSigBuf, 0, SigLen)) {
            log.error("签名正常");
            return true;
        } else {
            log.error("非签名正常");
            return false;
        }

    }

    // 公钥加密
    public byte[] RSAEncrypt(byte[] buf) throws Exception {
        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        // Cipher rsaCipher=Cipher.getInstance("RSA");
        rsaCipher.init(Cipher.ENCRYPT_MODE, pubKey);
        rsaCipher.update(buf);
        return rsaCipher.doFinal();

    }

    //私钥加密
    public byte[] PrivatekeyRSAEncryptWithPKCS1(byte[] buf) throws Exception {
        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        // Cipher rsaCipher=Cipher.getInstance("RSA");
        rsaCipher.init(Cipher.ENCRYPT_MODE, priKey);
        rsaCipher.update(buf);
        return rsaCipher.doFinal();

    }

    // 私钥解密
    public byte[] RSADecrypt(byte[] buf) throws Exception {
        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        rsaCipher.init(Cipher.DECRYPT_MODE, priKey);
        rsaCipher.update(buf);
        return rsaCipher.doFinal();

    }

    public byte[] PrivatekeyDecrypt(byte[] cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        byte[] plainText = cipher.doFinal(cipherText);

        return plainText;

    }

    public byte[] PublickeyDecryptWithPKCS1(byte[] cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        byte[] plainText = cipher.doFinal(cipherText);

        return plainText;

    }
}

