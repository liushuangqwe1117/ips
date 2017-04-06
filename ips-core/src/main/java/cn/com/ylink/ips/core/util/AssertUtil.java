/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2012-10-27
 */

package cn.com.ylink.ips.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;
import cn.com.ylink.ips.core.exception.IllegalArgumentRuntimeException;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-10-27
 * @description：数据验证
 */

public abstract class AssertUtil {
	
	private static void throwException(IllegalArgumentRuntimeException e) throws IllegalArgumentRuntimeException {
		throw e;
	}

	private static void throwException(IllegalArgumentRuntimeException e, String log) throws IllegalArgumentRuntimeException {
		//logger.debug(e + ";" + log, e);
		throw e;
	}

	public static void isTrue(boolean expression, String message) throws IllegalArgumentRuntimeException {
		isTrue(expression, null, message);
	}

	public static void isTrue(boolean expression, String errorCode, String message) throws IllegalArgumentRuntimeException {
		if (!expression) {
			throwException(new IllegalArgumentRuntimeException(errorCode, message));
		}
	}

	public static void isTrue(boolean expression, String errorCode, String message, String log) throws IllegalArgumentRuntimeException {
		if (!expression) {
			throwException(new IllegalArgumentRuntimeException(errorCode, message), log);
		}
	}

	public static void notTrue(boolean expression, String message) throws IllegalArgumentRuntimeException {
		notTrue(expression, null, message);
	}

	public static void notTrue(boolean expression, String errorCode, String message) throws IllegalArgumentRuntimeException {
		if (expression) {
			throwException(new IllegalArgumentRuntimeException(errorCode, message));
		}
	}

	public static void isNull(Object object, String message) throws IllegalArgumentRuntimeException {
		if (object != null) {
			throwException(new IllegalArgumentRuntimeException(message));
		}
	}

	public static void notNull(Object object, String message) throws IllegalArgumentRuntimeException {
		if (object == null) {
			throwException(new IllegalArgumentRuntimeException(message));
		}
	}

	public static void notNull(Object object, String errorCode, String message, String log) throws IllegalArgumentRuntimeException {
		if (object == null) {
			throwException(new IllegalArgumentRuntimeException(errorCode, message), log);
		}
	}

	public static void notNull(Object object, String errorCode, String message) throws IllegalArgumentRuntimeException {
		if (object == null) {
			throwException(new IllegalArgumentRuntimeException(errorCode, message));
		}
	}

	public static void isEmpty(String data, String message) throws IllegalArgumentRuntimeException {
		if (!StringUtils.isEmpty(data)) {
			throwException(new IllegalArgumentRuntimeException(message));
		}
	}

	public static void notEmpty(String data, String message) throws IllegalArgumentRuntimeException {
		if (StringUtils.isEmpty(data)) {
			throwException(new IllegalArgumentRuntimeException(message));
		}
	}

	public static void notEmpty(String data, String errorCode, String message) throws IllegalArgumentRuntimeException {
		if (StringUtils.isEmpty(data)) {
			throwException(new IllegalArgumentRuntimeException(errorCode, message));
		}
	}

	public static void isEmpty(Collection<?> data, String message) throws IllegalArgumentRuntimeException {
		if (!CollectionUtils.isEmpty(data)) {
			throwException(new IllegalArgumentRuntimeException(message));
		}
	}

	public static void notEmpty(Collection<?> data, String message) throws IllegalArgumentRuntimeException {
		if (CollectionUtils.isEmpty(data)) {
			throwException(new IllegalArgumentRuntimeException(message));
		}
	}

	public static void notEmpty(Collection<?> data, String errorCode, String message) throws IllegalArgumentRuntimeException {
		if (CollectionUtils.isEmpty(data)) {
			throwException(new IllegalArgumentRuntimeException(errorCode, message));
		}
	}

	public static void notEmpty(Object[] data, String message) throws IllegalArgumentRuntimeException {
		if (ArrayUtils.isEmpty(data)) {
			throwException(new IllegalArgumentRuntimeException(message));
		}
	}

	public static void notEmpty(Object[] data, String errorCode, String message) throws IllegalArgumentRuntimeException {
		if (ArrayUtils.isEmpty(data)) {
			throwException(new IllegalArgumentRuntimeException(errorCode, message));
		}
	}

	public static void equals(String s1, String s2, String message) throws IllegalArgumentRuntimeException {
		if (!StringUtils.equals(s1, s2)) {
			throwException(new IllegalArgumentRuntimeException(message));
		}
	}

	public static void equals(String s1, String s2, String message, String log) throws IllegalArgumentRuntimeException {
		if (!StringUtils.equals(s1, s2)) {
			throwException(new IllegalArgumentRuntimeException(message), log);
		}
	}

	public static void notEquals(String s1, String s2, String message) throws IllegalArgumentRuntimeException {
		if (StringUtils.equals(s1, s2)) {
			throwException(new IllegalArgumentRuntimeException(message));
		}
	}

	public static void checkLength(String data, String errorCode, String message, int maxLen) throws IllegalArgumentRuntimeException {
		if (data != null && data.getBytes().length > maxLen) {
			throwException(new IllegalArgumentRuntimeException(errorCode, message + "的内容[" + data + "]超过" + maxLen + "的最大限制"));
		}
	}
	public static void checkLength(String data, String errorCode, String message, int minLen,int maxLen) throws IllegalArgumentRuntimeException {
		if (data != null && (data.getBytes().length > maxLen || data.getBytes().length < minLen)) {
			throwException(new IllegalArgumentRuntimeException(errorCode, message + "的内容[" + data + "]长度应该在[" + minLen+","+maxLen + "]之间"));
		}
	}

	// 检查是否数字串
	public static void checkNChar(String data, String errorCode, String message) throws IllegalArgumentRuntimeException {
		if (data != null) {
			Pattern pattern = Pattern.compile("\\d*");
			Matcher isNum = pattern.matcher(data);
			if (!isNum.matches()) {
				throwException(new IllegalArgumentRuntimeException(errorCode, message + "的内容[" + data + "]包含不合法的数字"));
			}
		}
	}

	/*
	 * x-字符集由以下81个半角字符组成 a b c d e f g h i j k l m n o p q r s t u v w x y z A B
	 * C D E F G H I J K L M N O P Q R S T U V W X Y Z 0 1 2 3 4 5 6 7 8 9 . , - ( ) / = + ? ! % & * < > ; @ #
	 */
	public static void checkXChar(String data, String errorCode, String message) throws IllegalArgumentRuntimeException {
		if (data != null) {
			Pattern pattern = Pattern.compile("(\\w|\\s|#|\\.|,|-|\\(|\\)|/|=|\\+|\\?|!|%|\\*|&|<|>|;|@)*");
			Matcher isNum = pattern.matcher(data);
			if (!isNum.matches()) {
				throwException(new IllegalArgumentRuntimeException(errorCode, message + "的内容[" + data + "]包含不合法的字符"));
			}
		}
	}

	public static void checkContain(String data, List<?> list, String errorCode, String message) throws IllegalArgumentRuntimeException {
		if (data != null) {
			if (!list.contains(data)) {
				throwException(new IllegalArgumentRuntimeException(errorCode, message + "的内容[" + data + "]不在正常值范围"));
			}
		}
	}
	
	// 检查是否是日期格式
	public static void checkDateString(String data, String errorCode, String message) throws IllegalArgumentRuntimeException {
		if(StringUtils.isEmpty(data)){
			return;
		}
		try {
			DateFormat format = new SimpleDateFormat("yyyyMMdd");
			format.setLenient(false);
			format.parse(data);
		} catch (Exception ex) {
			throwException(new IllegalArgumentRuntimeException(errorCode, message + "的内容[" + data + "]日期格式错误"));
		}
	}
	// 检查是否是日期+时间格式
	public static void checkDateTimeString(String data, String errorCode, String message) throws IllegalArgumentRuntimeException {
		if(StringUtils.isEmpty(data)){
			return;
		}
		try {
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			format.setLenient(false);
			format.parse(data);
		} catch (Exception ex) {
			throwException(new IllegalArgumentRuntimeException(errorCode, message + "的内容[" + data + "]日期时间格式错误"));
		}
	}
	// 检查是否是时间格式
	public static void checkTimeString(String data, String errorCode, String message) throws IllegalArgumentRuntimeException {
		if(StringUtils.isEmpty(data)){
			return;
		}
		try {
			DateFormat format = new SimpleDateFormat("HHmmss");
			format.setLenient(false);
			format.parse(data);
		} catch (Exception ex) {
			throwException(new IllegalArgumentRuntimeException(errorCode, message + "的内容[" + data + "]时间格式错误"));
		}
	}
	
	public static boolean hasChinese(String s) {
		if (StringUtils.isEmpty(s)) {
			return false;
		}
		
		return s.getBytes().length != s.length();
	}
	
	//aps-zmc 增加 为了XML 文本报文的字段检查
	//charSet="N"数字 "X"数字/字母 "G"数字/字母/汉字 "D"8位日期 "T"6位时间 "DT"14位日期时间
	//option="O" "M"
	//先检查option,再int minLen,int maxLen,最后是charSet
	public static void checkData(String fieldValue,String fieldName,int minLen,int maxLen,String charSet,String option) throws IllegalArgumentRuntimeException
	{
		String fieldDesc = "["+fieldName+"]域";
		//检查必须字段
		if (option.equals("O"))
		{
			if (StringUtils.isEmpty(fieldValue))
				return;
		}
		if (option.equals("M") && minLen > 0)
		{
			if (StringUtils.isEmpty(fieldValue))
				throw new IllegalArgumentRuntimeException("9303",fieldDesc+"是必选字段");
		}

		//检查字段长度
		if (fieldValue.getBytes().length <minLen || fieldValue.getBytes().length >maxLen)
			throw new IllegalArgumentRuntimeException("9303",fieldDesc+"的长度范围应该在["+minLen+","+maxLen+"]之间");
		
		//检查类型
		if (charSet.equals("N"))
			checkNChar(fieldValue,"9303",fieldDesc);
		else if (charSet.equals("X"))
			checkXChar(fieldValue,"9303",fieldDesc);
		else if (charSet.equals("D"))
			checkDateString(fieldValue,"9303",fieldDesc);
		else if (charSet.equals("T"))
			checkTimeString(fieldValue,"9303",fieldDesc);
		else if (charSet.equals("DT"))
			checkDateTimeString(fieldValue,"9303",fieldDesc);
	}
	
	/**
	 * @description 验证字符串（是否为空和长度）
	 * @param param
	 * @param val
	 * @param isNull
	 * @param length  
	 * @author ZhangDM(Mingly)
	 * @date 2012-10-27
	 */
	public static void assertStr(String param, String val, boolean isNull, int length){
		
		if(StringUtils.isNotBlank(val)){
			if(length <= 0)
				return;
			if(val.length() > length)
				throw new IllegalArgumentRuntimeException("参数长度超过规定范围[" + param + "]");
		}else{
			if(!isNull)
				Assert.hasText(val, "参数不能为空[" + param + "]");
		}
	}
	
	/**
	 * @description 验证金额分（大于等于零和长度）
	 * @param param
	 * @param val
	 * @param length  
	 * @author ZhangDM(Mingly)
	 * @date 2012-10-29
	 */
	public static void assertAmt(String param, long val, int length){
		
		if(val >= 0){
			if(new Long(val).toString().length() > length)
				throw new IllegalArgumentRuntimeException("参数长度超过规定范围[" + param + "]");
		}else{
			throw new IllegalArgumentRuntimeException("金额必须大于等于零[" + param + "]");
		}
	}
}
