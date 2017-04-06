/**
 * 版权所有(C) 2013 深圳市雁联计算系统有限公司
 * 创建:Administrator 2013-9-5
 */

package cn.com.ylink.ips.core.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;
/** 
 * @author Administrator
 * @date 2013-9-5
 * @description：TODO
 */

public class JDigest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException{
		// TODO Auto-generated method stub
		String bb  = JDigest.digestMD5_Base64String("333333");
		System.out.println(bb.length()+"----"+bb);
	}
	/**
     * 利用SHA-1算法生成报文押
     * 
     * @param data
     *            报文数据
     * @param key
     *            密钥
     * @return
     * @throws NoSuchAlgorithmException
     */
	public static byte[] digestSHA1(byte[] data, byte[] key) throws NoSuchAlgorithmException {
        // SHA-1 Hash value of data
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        BASE64Encoder b64e = new BASE64Encoder();

        md.update(data);
        md.update(key);

        return b64e.encode(md.digest()).getBytes();
    }
	public static String digestMD5_Base64(byte[] data) throws NoSuchAlgorithmException {
        // SHA-1 Hash value of data
        MessageDigest md = MessageDigest.getInstance("MD5");
        BASE64Encoder b64e = new BASE64Encoder();

        md.update(data);


        return b64e.encode(md.digest());
    }
	public static byte[] digestMD5(byte[] data) throws NoSuchAlgorithmException {
        // SHA-1 Hash value of data
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data);
        return md.digest();
    }
	
	public static String digestMD5_Base64String(String data) throws NoSuchAlgorithmException {
        // SHA-1 Hash value of data
        MessageDigest md = MessageDigest.getInstance("MD5");
        BASE64Encoder b64e = new BASE64Encoder();

        md.update(data.getBytes());


        return b64e.encode(md.digest());
    }

}

