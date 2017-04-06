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

public class Base16 {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
    public static String bytesToHexStr2(byte[] value) 
    {
        String newString = "";
        for (int i = 0; i < value.length; i++) 
        {
        	byte b = value[i];
        	String str = Integer.toHexString(b);
        	if (str.length() > 2) {
        		str = str.substring(str.length() - 2);
        	}
        	if (str.length() < 2) {
        		str = "0" + str;
        	}
        	newString += str;
        }
        return newString.toUpperCase();
    }

    private static final char[] bcdLookup = { '0', '1', '2', '3', '4', '5',
		'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
   /**
   * Transform the specified byte into a Hex String form.
   */
   public static final String bytesToHexStr(byte[] bcd) 
   {
	   StringBuffer s = new StringBuffer(bcd.length * 2);

	   for (int i = 0; i < bcd.length; i++) 
	   {
		   s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
		   s.append(bcdLookup[bcd[i] & 0x0f]);
	   }

	   return s.toString();
   }
   	public static final byte[] hexStrToBytes(String s) 
   	{
   		byte[] bytes;

   		bytes = new byte[s.length() / 2];

   		for (int i = 0; i < bytes.length; i++) {
   			bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2),16);
   		}

   		return bytes;
   	}

}

