package cn.com.ylink.ips.core.util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;

public class StringUtil {

	public static final String EMPTY_STRING = "";
	public static final char DEFAULT_DELIMITER_CHAR = ',';
	public static final char DEFAULT_QUOTE_CHAR = '"';

	public static String clean(String in) {
		String out = in;

		if (in != null) {
			out = in.trim();
			if (out.equals("")) {
				out = null;
			}
		}

		return out;
	}

	public static boolean hasText(String in) {
		return clean(in) != null;
	}

	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	/**
	 * 将字节数组转换为String
	    * @param b byte[]
	    * @return String
	    */
	public static byte[] StringToByte(String str) {
		byte []b = null;
		try{
			b = str.getBytes();
		}catch(Exception e){
	    }
	    return b;
	}
	
	/**
     * 将字节数组转换为String
     * @param b byte[]
     * @return String
     */
    public static String bytesToString(byte[] b) {
    	 String str = null;
         try{
             str = new String(b);
         }catch(Exception e){
         }
         return str;
    }
    
    
    
	//处理定长字符串右补空格
    public static String padRight(String str, int iSize){
        if(str == null) str = "";
        byte bTemp[] = StringUtil.StringToByte(str);
        int ilen = bTemp.length;
        byte[] bStr = null;
        try{
            bStr = new byte[iSize];
            if(ilen < iSize){
                System.arraycopy(bTemp, 0, bStr, 0, ilen);
                for (int i = 0; i < iSize - ilen; i++) {
                    bStr[i + ilen] = ' ';
                }
            }else{
                System.arraycopy(bTemp, 0, bStr, 0, iSize);
            }
        }catch(Exception e){
            return null;
        }
        str = StringUtil.bytesToString(bStr);
        return str;
    }
    //右对齐,左补0
    public static String padLeft(String str, int iSize){
        if(str == null) str = "";
        byte bTemp[] = StringUtil.StringToByte(str);
        int ilen = bTemp.length;
        byte[] bStr = null;
        try{
            bStr = new byte[iSize];
            if(ilen < iSize){
                System.arraycopy(bTemp, 0, bStr, iSize - ilen, ilen);
                for (int i = 0; i < iSize - ilen; i++) {
                    bStr[i] = '0';
                }
            }else{
                System.arraycopy(bTemp, 0, bStr, 0, iSize);
            }
        }catch(Exception e){
            return null;
        }
        str = StringUtil.bytesToString(bStr);
        return str;
    }
    
    
    
    
    
    
    
    
    
    
    
    /////////////////////////////////////
    /**
     * 将int转为低字节在前，高字节在后的byte数组
     * @param n int
     * @return byte[]
     */
    public static byte[] toLH(int n) {
        byte[] b = new byte[4];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        return b;
    }

    /**
     * 将int转为高字节在前，低字节在后的byte数组
     * @param n int
     * @return byte[]
     */
    public static byte[] toHH(int n) {
        byte[] b = new byte[4];
        b[3] = (byte) (n & 0xff);
        b[2] = (byte) (n >> 8 & 0xff);
        b[1] = (byte) (n >> 16 & 0xff);
        b[0] = (byte) (n >> 24 & 0xff);
        return b;
    }

    /**
     * 将long转为高字节在前，低字节在后的byte数组
     * @param n int
     * @return byte[]
     */
    public static byte[] toHH(long n) {
        byte[] b = new byte[4];
        b[3] = (byte) (n & 0xff);
        b[2] = (byte) (n >> 8 & 0xff);
        b[1] = (byte) (n >> 16 & 0xff);
        b[0] = (byte) (n >> 24 & 0xff);
        return b;
    }
    /**
     * 将short转为低字节在前，高字节在后的byte数组
     * @param n short
     * @return byte[]
     */
    public static byte[] toLH(short n) {
        byte[] b = new byte[2];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        return b;
    }

    /**
     * 将short转为高字节在前，低字节在后的byte数组
     * @param n short
     * @return byte[]
     */
    public static byte[] toHH(short n) {
        byte[] b = new byte[2];
        b[1] = (byte) (n & 0xff);
        b[0] = (byte) (n >> 8 & 0xff);
        return b;
    }

    /**
     * 将将int转为高字节在前，低字节在后的byte数组

       public static byte[] toHH(int number) {
      int temp = number;
      byte[] b = new byte[4];
      for (int i = b.length - 1; i > -1; i--) {
        b[i] = new Integer(temp & 0xff).byteValue();
        temp = temp >> 8;
      }
      return b;
       }

       public static byte[] IntToByteArray(int i) {
          byte[] abyte0 = new byte[4];
          abyte0[3] = (byte) (0xff & i);
          abyte0[2] = (byte) ((0xff00 & i) >> 8);
          abyte0[1] = (byte) ((0xff0000 & i) >> 16);
          abyte0[0] = (byte) ((0xff000000 & i) >> 24);
          return abyte0;
       }


     */

    /**
     * 将float转为低字节在前，高字节在后的byte数组
     */
    public static byte[] toLH(float f) {
        return toLH(Float.floatToRawIntBits(f));
    }

    /**
     * 将float转为高字节在前，低字节在后的byte数组
     */
    public static byte[] toHH(float f) {
        return toHH(Float.floatToRawIntBits(f));
    }

    /**
     * 将String转为byte数组
     */
    public static byte[] stringToBytes(String s, int length) {
        while (s.getBytes().length < length) {
            s += " ";
        }
        return s.getBytes();
    }

    /**
     * 将字节数组转换为String
     * @param b byte[]
     * @return String
     */
    public static String bytesToGBKString(byte[] b) {
        String str = null;
        try{
            str = new String(b, "GBK");
        }catch(Exception e){
        }
        return str;
    }

    /**
    * 将字节数组转换为String
    * @param b byte[]
    * @return String
    */
   public static byte[] StringToGBKByte(String str) {
       byte []b = null;
       try{
           b = str.getBytes("GBK");
       }catch(Exception e){
       }
       return b;
   }



    

    /**
     * 将字符串转换为byte数组
     * @param s String
     * @return byte[]
     */
    public static byte[] stringToBytes(String s) {
        return s.getBytes();
    }



    /**
     * 将高字节数组转换为int
     * @param b byte[]
     * @return int
     */
    public static int hBytesToInt(byte[] b) {
        int s = 0;
        for (int i = 0; i < 3; i++) {
            if (b[i] >= 0) {
                s = s + b[i];
            }
            else {
                s = s + 256 + b[i];
            }
            s = s * 256;
        }
        if (b[3] >= 0) {
            s = s + b[3];
        }
        else {
            s = s + 256 + b[3];
        }
        return s;
    }
    
    

    /**
     * 将低字节数组转换为int
     * @param b byte[]
     * @return int
     */
    public static int lBytesToInt(byte[] b) {
        int s = 0;
        for (int i = 0; i < 3; i++) {
            if (b[3 - i] >= 0) {
                s = s + b[3 - i];
            }
            else {
                s = s + 256 + b[3 - i];
            }
            s = s * 256;
        }
        if (b[0] >= 0) {
            s = s + b[0];
        }
        else {
            s = s + 256 + b[0];
        }
        return s;
    }

    /**
     * 高字节数组到short的转换
     * @param b byte[]
     * @return short
     */
    public static short hBytesToShort(byte[] b) {
        int s = 0;
        if (b[0] >= 0) {
            s = s + b[0];
        }
        else {
            s = s + 256 + b[0];
        }
        s = s * 256;
        if (b[1] >= 0) {
            s = s + b[1];
        }
        else {
            s = s + 256 + b[1];
        }
        short result = (short) s;
        return result;
    }

    /**
     * 低字节数组到short的转换
     * @param b byte[]
     * @return short
     */
    public static short lBytesToShort(byte[] b) {
        int s = 0;
        if (b[1] >= 0) {
            s = s + b[1];
        }
        else {
            s = s + 256 + b[1];
        }
        s = s * 256;
        if (b[0] >= 0) {
            s = s + b[0];
        }
        else {
            s = s + 256 + b[0];
        }
        short result = (short) s;
        return result;
    }

    /**
     * 高字节数组转换为float
     * @param b byte[]
     * @return float
     */
    public static float hBytesToFloat(byte[] b) {
        int i = 0;
        Float F = new Float(0.0);
        i = ( ( ( (b[0] & 0xff) << 8 | (b[1] & 0xff)) << 8) | (b[2] & 0xff)) <<
            8 | (b[3] & 0xff);
        return Float.intBitsToFloat(i);
    }

    /**
     * 低字节数组转换为float
     * @param b byte[]
     * @return float
     */
    public static float lBytesToFloat(byte[] b) {
        int i = 0;
        Float F = new Float(0.0);
        i = ( ( ( (b[3] & 0xff) << 8 | (b[2] & 0xff)) << 8) | (b[1] & 0xff)) <<
            8 | (b[0] & 0xff);
        return Float.intBitsToFloat(i);
    }

    /**
     * 将byte数组中的元素倒序排列
     */
    public static byte[] bytesReverseOrder(byte[] b) {
        int length = b.length;
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[length - i - 1] = b[i];
        }
        return result;
    }

    /**
     * 打印byte数组
     */
    public static void printBytes(byte[] bb) {
        int length = bb.length;
        for (int i = 0; i < length; i++) {
            System.out.print(bb[i] + " ");
        }
        System.out.println("");
    }

    public static void logBytes(byte[] bb) {
        int length = bb.length;
        String out = "";
        for (int i = 0; i < length; i++) {
            out = out + bb[i] + " ";
        }

    }

    /**
     * 将int类型的值转换为字节序颠倒过来对应的int值
     * @param i int
     * @return int
     */
    public static int reverseInt(int i) {
        int result = hBytesToInt(toLH(i));
        return result;
    }

    /**
     * 将short类型的值转换为字节序颠倒过来对应的short值
     * @param s short
     * @return short
     */
    public static short reverseShort(short s) {
        short result = hBytesToShort(toLH(s));
        return result;
    }

    /**
     * 将float类型的值转换为字节序颠倒过来对应的float值
     * @param f float
     * @return float
     */
    public static float reverseFloat(float f) {
        float result = hBytesToFloat(toLH(f));
        return result;
    }
    /* 16进制输出byte[] */ //例如 byte[0] = 1 byte[1] = 2 ---->  0x3132
    public static String rhex(byte[] in){
      DataInputStream data = new DataInputStream(new ByteArrayInputStream(in));
      String str = "0x";
      try {
        for(int j = 0; j < in.length; j++)
        {
          String tmp = Integer.toHexString(data.readUnsignedByte());
          if ( tmp.length() == 1)
          {
            tmp = "0" + tmp;
          }
          str = str + tmp;
        }
      }
      catch (Exception ex) {
      }
      return str;
    }

//  将java的字符串变为Octet的字符串
    //iSize 为要生成字符串的大小
    public static String setOctetStrFormat(String str, int iSize){
        if(str == null) str = "";
        byte bTemp[] = StringToGBKByte(str);
        int ilen = bTemp.length;
        byte[] bStr = null;
        try{
            bStr = new byte[iSize];
            if(ilen <= iSize){
                System.arraycopy(bTemp, 0, bStr, 0, ilen);
                for (int i = 0; i < iSize - ilen; i++) {
                    bStr[i + ilen] = 0;
                }
            }else{
                System.arraycopy(bTemp, 0, bStr, 0, iSize);
            }
        }catch(Exception e){
            return null;
        }
        str = bytesToGBKString(bStr);
        return str;
    }
    //处理定长字符串右补空格
    public static String setStrFormat(String str, int iSize){
        if(str == null) str = "";
        byte bTemp[] = StringToGBKByte(str);
        int ilen = bTemp.length;
        byte[] bStr = null;
        try{
            bStr = new byte[iSize];
            if(ilen < iSize){
                System.arraycopy(bTemp, 0, bStr, 0, ilen);
                for (int i = 0; i < iSize - ilen; i++) {
                    bStr[i + ilen] = ' ';
                }
            }else{
                System.arraycopy(bTemp, 0, bStr, 0, iSize);
            }
        }catch(Exception e){
            return null;
        }
        str = bytesToGBKString(bStr);
        return str;
    }
    //处理定长字符串左补“0”
    public static String setLStrFormat(String str, int iSize){
        if(str == null) str = "";
        byte bTemp[] = StringToGBKByte(str);
        int ilen = bTemp.length;
        byte[] bStr = null;
        try{
            bStr = new byte[iSize];
            if(ilen < iSize){
                System.arraycopy(bTemp, 0, bStr, iSize - ilen, ilen);
                for (int i = 0; i < iSize - ilen; i++) {
                    bStr[i] = '0';
                }
            }else{
                System.arraycopy(bTemp, 0, bStr, 0, iSize);
            }
        }catch(Exception e){
            return null;
        }
        str = bytesToGBKString(bStr);
        return str;
    }
    
    //  处理定长字符串左补空格“ ”
    public static String setLBlankStrFormat(String str, int iSize){
        if(str == null) str = "";
        byte bTemp[] = StringToGBKByte(str);
        int ilen = bTemp.length;
        byte[] bStr = null;
        try{
            bStr = new byte[iSize];
            if(ilen < iSize){
                System.arraycopy(bTemp, 0, bStr, iSize - ilen, ilen);
                for (int i = 0; i < iSize - ilen; i++) {
                    bStr[i] = ' ';
                }
            }else{
                System.arraycopy(bTemp, 0, bStr, 0, iSize);
            }
        }catch(Exception e){
            return null;
        }
        str = bytesToGBKString(bStr);
        return str;
    }
    
    public static String setLErrStrFormat(String str){
    	if(str == null) str = "";
    	int iKey = 0;
    	for(int i = 0; i < str.length(); i++){
    		if(str.charAt(i) == '0'){
    			continue;
    		}else{
    			iKey = i;
    			break;
    		}
    	}
    	str = str.substring(iKey);
    	return str;
    }
    public static String nullToTemp(String str){
    	if(str == null) return "";
    	else
    		return str;
    }
    public static String nullToTemp(Object str){
    	if(str == null) return "";
    	else
    		return str.toString();
    }
    public static String nullToPrint(String str){
    	if(str == null) return "ERR IS NULL";
    	else
    		return str;
    }
    
    /** 
     * 把二进制字节数组转换为二进制字符串 
     * @param bArray 
     * @return 
     */ 
    public static String getBinaryFromByte(byte[] bArray){
    	return getHexStrFromByte(bytesToHexString(bArray));
    }
    /** 
     * 把十六进制换字符串转换为二进制字符串 
     * @param bArray 
     * @return 
     */ 
    public static String getHexStrFromByte(String hexStr){
    	StringBuffer buff = null;
    	String strBit = "";
		int temp = (hexStr.length()) % 2;
		if(temp != 0){
    		hexStr = setLStrFormat(hexStr, hexStr.length() + 1);
    	}
    	//存放二进制字符串
    	buff = new StringBuffer();
    	for(int i = 0; i < hexStr.length(); i++){
    		if( (i != 0 && i % 2 == 0) || i == hexStr.length() - 1){
    			if(i == hexStr.length() - 1){
    				buff.append(hexStr.charAt(i));
    			}
    			//先将十六进制的每2位取出，组成一个字节字符串
    			String strTemp = buff.toString().toUpperCase();
    			//System.out.println("==" + strTemp);
    			strBit = strBit + setLStrFormat(Integer.toBinaryString(Integer.parseInt(strTemp, 16)), 8);
    			//System.out.println("strBit==" + strBit);
    			buff = new StringBuffer();
    		}
    		buff.append(hexStr.charAt(i));
    		//System.out.println(buff.toString());
    	}
    	strBit = setLStrFormat(strBit, hexStr.length()*4);
    	return strBit;
    }
    /** 
     * 把位数组转换成字节数组集合
     * 每一个字节为List的一个元素
     * @param bArray 
     * @return 
     */ 
    public static LinkedList getByteFromBinary(int []bArray){
    	int temp = 0;
    	StringBuffer buff = null;
    	String []sArray = null;
    	byte[] result = null;
    	byte[] list = null;
    	LinkedList bList = null;
    	temp = (bArray.length) / 8;
    	if(temp < 1){
    		return null;
    	}else{
    		//存放二进制字符串
    		sArray = new String[temp];
    		//System.out.println(temp);
    		//result = new byte[temp];
    		buff = new StringBuffer();
    		bList = new LinkedList();
    		int count = 0;
    		for(int i = 0; i < bArray.length; i++){
    			
    			if( (i != 0 && i % 8 == 0) || i == bArray.length - 1){
    				if(i == bArray.length - 1){
    					buff.append(bArray[i]);
    				}
    				//先将位数组的每8位取出，组成二进制字符串
    				sArray[count] = buff.toString();
    				//将每个字节转换为16进制的字符串（先将二进制字符串转为整形，再由整形转换为十六进制）
    				//System.out.println("sArray[count]" + (sArray[count]));
    				String hStr = Integer.toHexString(Integer.valueOf(sArray[count], 2).intValue()).toUpperCase();
    				//System.out.println("hStr" + (hStr));
    				hStr = setLStrFormat(hStr, 2);
    				//System.out.println("length" + hexStringToByte(hStr).length);
    				//System.out.println("count" + count);
    				list = new byte[1];
    				//System.arraycopy(hexStringToByte(hStr), 0, result, count, 1);
    				System.arraycopy(hexStringToByte(hStr), 0, list, 0, 1);
    				
    				//System.out.println("list" + getBinaryFromByte(list));
    				bList.add(list);
    				buff = new StringBuffer();
    				count++;
    			}
    			buff.append(bArray[i]);
    		}
    	}
    	return bList;
    }
    /*
    * 把16进制字符串转换成字节数组 
    * @param hex 要求两位，不足左补0
    * @return 
    */ 
    public static byte[] hexStringToByte(String hex) { 
    	int len = (hex.length() / 2); 
    	byte[] result = new byte[len]; 
    	char[] achar = hex.toCharArray(); 
    	for (int i = 0; i < len; i++) { 
    		int pos = i * 2; 
    		result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1])); 
    	} 
    	return result; 
    } 
    private static byte toByte(char c) { 
        byte b = (byte) "0123456789ABCDEF".indexOf(c); 
        return b; 
    } 
    /** 
    * 把字节数组转换成16进制字符串 
    * @param bArray 
    * @return 
    */ 
    public static final String bytesToHexString(byte[] bArray) { 
    	StringBuffer sb = new StringBuffer(bArray.length); 
    	String sTemp; 
    	for (int i = 0; i < bArray.length; i++) { 
    		sTemp = Integer.toHexString(0xFF & bArray[i]); 
    		if (sTemp.length() < 2) 
    			sb.append(0); 
    		sb.append(sTemp.toUpperCase()); 
    	} 
    	return sb.toString(); 
    } 

    /** 
     * 把字节数组转换为对象 
     * @param bytes 
     * @return 
     * @throws IOException 
     * @throws ClassNotFoundException 
     */ 
    public static final Object bytesToObject(byte[] bytes) throws IOException, ClassNotFoundException { 
    	ByteArrayInputStream in = new ByteArrayInputStream(bytes); 
    	ObjectInputStream oi = new ObjectInputStream(in); 
    	Object o = oi.readObject(); 
    	oi.close(); 
     	return o; 
    } 

 	/** 
     * 把可序列化对象转换成字节数组 
     * @param s 
     * @return 
     * @throws IOException 
     */ 
 	public static final byte[] objectToBytes(Serializable s) throws IOException { 
 		ByteArrayOutputStream out = new ByteArrayOutputStream(); 
 		ObjectOutputStream ot = new ObjectOutputStream(out); 
 		ot.writeObject(s); 
 		ot.flush(); 
 		ot.close(); 
 		return out.toByteArray(); 
 	} 

 	public static final String objectToHexString(Serializable s) throws IOException{ 
 		return bytesToHexString(objectToBytes(s)); 
 	} 

 	public static final Object hexStringToObject(String hex) throws IOException, ClassNotFoundException{ 
 		return bytesToObject(hexStringToByte(hex)); 
 	} 

 	/** 
     * @函数功能: BCD码转为10进制串(阿拉伯数据) 
     * @输入参数: BCD码 
     * @输出结果: 10进制串 
     */ 
 	public static String bcd2Str(byte[] bytes){ 
 		StringBuffer temp=new StringBuffer(bytes.length*2); 

 		for(int i=0;i<bytes.length;i++){ 
 			temp.append((byte)((bytes[i]& 0xf0)>>>4)); 
 			temp.append((byte)(bytes[i]& 0x0f)); 
 		} 
 		return temp.toString().substring(0,1).equalsIgnoreCase("0")?temp.toString().substring(1):temp.toString(); 
 	} 

 	/** 
     * @函数功能: 10进制串转为BCD码 
     * @输入参数: 10进制串 
     * @输出结果: BCD码 
     */ 
 	public static byte[] str2Bcd(String asc) { 
 		int len = asc.length(); 
 		int mod = len % 2; 

 		if (mod != 0) { 
 			asc = "0" + asc; 
 			len = asc.length(); 
 		} 

 		byte abt[] = new byte[len]; 
 		if (len >= 2) { 
 			len = len / 2; 
 		} 

 		byte bbt[] = new byte[len]; 
 		abt = asc.getBytes(); 
 		int j, k; 

 		for (int p = 0; p < asc.length()/2; p++) { 
 			if ( (abt[2 * p] >= '0') && (abt[2 * p] <= '9')) { 
 				j = abt[2 * p] - '0'; 
 			} else if ( (abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) { 
 				j = abt[2 * p] - 'a' + 0x0a; 
 			} else { 
 				j = abt[2 * p] - 'A' + 0x0a; 
 			} 

 			if ( (abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) { 
 				k = abt[2 * p + 1] - '0'; 
 			} else if ( (abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) { 
 				k = abt[2 * p + 1] - 'a' + 0x0a; 
 			}else { 
 				k = abt[2 * p + 1] - 'A' + 0x0a; 
 			} 

 			int a = (j << 4) + k; 
 			byte b = (byte) a; 
 			bbt[p] = b; 
 		} 
 		return bbt; 
 	} 
 	/** 
     * @函数功能: BCD码转ASC码 
     * @输入参数: BCD串 
     * @输出结果: ASC码 
     */ 
 	/*
 	public static String BCD2ASC(byte[] bytes) { 
 		StringBuffer temp = new StringBuffer(bytes.length * 2); 

 		for (int i = 0; i < bytes.length; i++) { 
 			int h = ((bytes[i] & 0xf0) >>> 4); 
 			int l = (bytes[i] & 0x0f);   
 			temp.append(BToA[h]).append( BToA[l]); 
 		} 
 		return temp.toString() ; 
 	}*/ 

 	/** 
     * MD5加密字符串，返回加密后的16进制字符串 
     * @param origin 
     * @return 
     */ 
 	public static String MD5EncodeToHex(String origin) { 
 		return bytesToHexString(MD5Encode(origin)); 
    } 

 	/** 
     * MD5加密字符串，返回加密后的字节数组 
     * @param origin 
     * @return 
     */ 
 	public static byte[] MD5Encode(String origin){ 
 		return MD5Encode(origin.getBytes()); 
 	} 

 	/** 
     * MD5加密字节数组，返回加密后的字节数组 
     * @param bytes 
     * @return 
     */ 
 	public static byte[] MD5Encode(byte[] bytes){ 
 		MessageDigest md=null; 
 		try { 
 			md = MessageDigest.getInstance("MD5"); 
 			return md.digest(bytes); 
 		} catch (NoSuchAlgorithmException e) { 
 			e.printStackTrace(); 
 			return new byte[0]; 
 		} 
   
 	} 

}
