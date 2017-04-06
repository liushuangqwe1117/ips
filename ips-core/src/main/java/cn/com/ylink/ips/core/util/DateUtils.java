/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2012-8-14
 */

package cn.com.ylink.ips.core.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import cn.com.ylink.ips.core.exception.IllegalArgumentRuntimeException;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-8-14
 * @description：时间工具类
 */

public class DateUtils {
	/**
	 * 获取当前日期。按照监控要求的格式
	 * @description 
	 * @return  
	 * @author wuxiangqian
	 * @date 2013-11-29
	 */
	public static String getCurentDayForMonit(){
		return new SimpleDateFormat("yyyy年MM月dd日").format(new Date());
	}
	
	/**
	 * @description 得到当天时间字符串
	 * @return 
	 * @author ZhangDM(Mingly)
	 * @date 2012-8-14
	 */
	public static String getCurrentDate(){
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}
	/**
	 * @description 得到当年字符串
	 * @return 
	 * @author yuqingjun
	 * @date 2015-02-04
	 */
	public static String getCurrentYear(){
		return new SimpleDateFormat("yyyy年").format(new Date());
	}
	
	/**
	 * @description 根据时间模板得到当天时间字符串
	 * @param dateFormat
	 * @return  
	 * @author ZhangDM(Mingly)
	 * @date 2013-8-27
	 */
	public static String getCurrentByDateFormat(String dateFormat){
		return new SimpleDateFormat(dateFormat).format(new Date());
	}
	
	/**
	 * @description 将时间转化为符合要求的字符串
	 * @param date
	 * @param dateFormat
	 * @return  
	 * @author ZhangDM(Mingly)
	 * @date 2012-11-8
	 */
	public static String parseDateToStrByFormat(Date date, String dateFormat){
		if(date != null && StringUtils.isNotBlank(dateFormat)){		
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			return sdf.format(date);
		}else{
			return "";
		}
	}
	
	/**
	 * 获取当前日期字符串，格式为yyyy-MM-dd
	 * 
	 * @return String
	 */
	public static String getCurrentPrettyDate()
	{
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	/**
	 * 获取当前时间字符串，格式为HHmmss
	 * 
	 * @return String
	 */
	public static String getCurrentTime()
	{
		return new SimpleDateFormat("HHmmss").format(new Date());
	}
	
	public static String getCurrentTime1()
	{
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}

	public static String getCurrentDateTime()
	{
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	/**
	 * 获取对应Date的日期字符串，格式为yyyyMMddHHmmss
	 * @param date
	 * @return
	 */
	
	public static String getDateTime(Date date)
	{
		return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
	}
	
	/*
	 * 获取当前日期前一天，格式为YYYYMMDD
	 */
	public static String getCurrentChnDateFront()
	{
		String strDate = new SimpleDateFormat("yyyyMMdd").format(addDays(new Date(),-1));
		strDate = strDate.substring(0, 4)  + strDate.substring(4, 6) + strDate.substring(6) ;
		return strDate;
	}
	

	/**
	 * 获取当前的毫秒时间，为long值
	 * 
	 * @return long
	 */
	public static long getCurrentLongTime()
	{
		return new Date().getTime();
	}

	/**
	 * 获取当前时间的字符串，格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String
	 */
	public static String getCurrentPrettyDateTime()
	{
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	
	/**
	 * 获取当前时间的字符串，格式为yyyy-MM-ddTHH:mm:ss
	 * 
	 * @return String
	 */
	public static String getCurrentXsdDateTime()
	{
		return getCurrentPrettyDate()+"T"+getCurrentTime1();
	}

	/**
	 * 获取对应Date的日期字符串，格式为yyyyMMdd
	 * 
	 * @param date
	 *            源Date
	 * @return
	 */
	public static String getDate(Date date)
	{
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}

	/**
	 * 获取对应Date的时间字符串，格式为HHmmss
	 * 
	 * @param date
	 *            源Date
	 * @return String
	 */
	public static String getTime(Date date)
	{
		return new SimpleDateFormat("HHmmss").format(date);
	}
		
	/**
	 * 获取对应Date的时间字符串，格式为HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String getTime1(Date date)
	{
		return new SimpleDateFormat("HH:mm:ss").format(date);
	}

	/**
	 * 获取对应Date的日期字符串，格式为yyyy-MM-dd
	 * 
	 * @param date
	 *            源Date
	 * @return String
	 */
	public static String getPrettyDate(Date date)
	{
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	/**
	 * 获取对应Date的字符串，格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 *            源Date
	 * @return String
	 */
	public static String getPrettyDateTime(Date date)
	{
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	/**
	 * 获取Sybase的日期转换函数表达式，用于SQL中，格式是yyyy/MM/dd HH:mm:ss，没办法，Sybase不支持yyyy-MM-dd
	 * HH:mm:ss
	 * 
	 * @param field
	 *            要转换成yyyy/MM/dd HH:mm:ss格式字符串的日期类型字段名
	 * @return String
	 */
	public static String getSybaseConvertSQL(String field)
	{
		return "(CONVERT(varchar(12), " + field + ", 111) + ' ' + CONVERT(varchar(12), " + field + ", 108))";
	}

	/**
	 * 获取Sybase的日期转换函数表达式，用于SQL中，格式是yyyy/MM/dd HH:mm:ss，没办法，Sybase不支持yyyy-MM-dd
	 * HH:mm:ss
	 * 
	 * @param field
	 *            要转换成yyyy/MM/dd HH:mm:ss格式字符串的日期类型字段名
	 * @return String
	 */
	public static String getSybaseYYYYMMDD(String field)
	{
		return "(CONVERT(varchar(12), " + field + ", 112))";
	}

	/**
	 * 获取一年的所有日期列表
	 * 
	 * @param year
	 *            年份
	 * @return ArrayList
	 */
	public static List getAllDates(String year)
	{
		ArrayList list = new ArrayList();
		int intYear;
		try
		{
			intYear = Integer.parseInt(year);
		}
		catch (NumberFormatException e)
		{ // 如果不是合法的年份，返回空的列表
			return list;
		}
		intYear = intYear - 1900; // 需要减去1900
		for (int month = 0; month < 12; month++)
		{
			for (int day = 1; day < 32; day++)
			{
				Date date = new Date(intYear, month, day);
				if (!list.contains(date))
				{
					list.add(date);
				}
			}
		}
		Collections.sort(list);
		return list;
	}

	/*
	 * 获取当前日期，格式为XXXX年XX月XX日
	 */
	public static String getCurrentChnDate()
	{
		String strDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		strDate = strDate.substring(0, 4) + "年" + strDate.substring(4, 6) + "月" + strDate.substring(6) + "日";
		return strDate;
	}

	/*
	 * 将日期转换为格式XXXX年XX月XX日
	 */
	public static String getChnDate(Date date)
	{
		String strDate = new SimpleDateFormat("yyyyMMdd").format(date);
		strDate = strDate.substring(0, 4) + "年" + strDate.substring(4, 6) + "月" + strDate.substring(6) + "日";
		return strDate;
	}
	/*
	 * 将日期转换为格式XXXX年XX月XX日
	 */
	public static String getChnTime(Date date)
	{
		String strDate = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒").format(new Date());
		return strDate;
	}

	/*
	 * 将字符串'yyyyMMdd'日期转换为格式XXXX年XX月XX日
	 */
	public static String getChnDate(String strDate)
	{
		return strDate.substring(0, 4) + "年" + strDate.substring(4, 6) + "月" + strDate.substring(6) + "日";
	}

	/**
	 * 检查日期字符串是否合法
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @param pattern
	 *            日期格式
	 * @return 布尔
	 */
	//'yyyyMMdd'  'HHmmss' 所以年月日不是yyyymmdd
	public static boolean isValidDate(String dateStr, String pattern)
	{
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(pattern);
		
		df.setLenient(false);//来强调严格遵守该格式
		//df.setLenient(true);
		Date date = null;
		try
		{
			date = df.parse(dateStr);
			return true;
		}
		catch (ParseException e)
		{
			return false;
		}
		
		//String result=df.format(date);//判断转换前后两个字符串是否相等即可知道合不合法
		//return result.equals(dateStr);
	}
	
	  /**
	 * @description 
	 * @param date 格式yyyyMMdd
	 * @return  
	 * @author Iquil
	 * @date 2013-12-1
	 */
	public static String getNextDay(String date) {
		    if (date == null || date.trim().length() == 0) {
		      return "";
		    }
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(f.parse(date));
		} catch (ParseException ex) {
			return date;
		}
		calendar.add(calendar.DAY_OF_YEAR, 1);
		return f.format(calendar.getTime());
	}
	

	//'yyyyMMdd'  'HHmmss' 所以年月日不是yyyymmdd
	public static  void checkValidDate(String dateStr, String pattern) throws IllegalArgumentRuntimeException
	{
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(pattern);
		
		df.setLenient(false);//来强调严格遵守该格式
		//df.setLenient(true);
		try
		{
			df.parse(dateStr);
		}
		catch (ParseException e)
		{
			throw new IllegalArgumentRuntimeException("9303","["+dateStr+"]日期格式非法");
			
		}
	}

	public static int getMaxdayInAMonth(int year, int month)
	{
		Calendar time = Calendar.getInstance();
		time.clear();
		time.set(Calendar.YEAR, year); // year 为 int
		time.set(Calendar.MONTH, month - 1);// 注意,Calendar对象默认一月为0
		int day = time.getActualMaximum(Calendar.DAY_OF_MONTH);// 本月份的天数
		// 注：在使用set方法之前，必须先clear一下，否则很多信息会继承自系统当前时间
		return day;

	}

	// Calendar转化为Date
	public static Date CalendarToDate()
	{
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		return date;
	}

	// Date转化为Calendar
	public static Calendar DateToCalendar()
	{
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	/**
	 * 按日加减日期
	 * 
	 * @param date：日期
	 * @param num：要加减的日数
	 * @return：成功，则返回加减后的日期；失败，则返回null
	 */
	public static Date addDays(Date date, int num)
	{
		if (date == null)
		{
			return null;
		}

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, num);

		return c.getTime();
	}
	
	
	/**
	 * 按月加减日期
	 * 
	 * @param date：日期
	 * @param num：要加减的月数
	 * @return：成功，则返回加减后的日期；失败，则返回null
	 */
	public static Date addMonths(Date date, int num)
	{
		if (date == null)
		{
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, num);
		return c.getTime();
	}

	/**
	 * 按年加减日期
	 * 
	 * @param date：日期
	 * @param num：要加减的年数
	 * @return：成功，则返回加减后的日期；失败，则返回null
	 */
	public static Date addYears(Date date, int num)
	{
		if (date == null)
		{
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, num);
		return c.getTime();
	}

	/**
	 * 按秒 加减日期
	 * 
	 * @param date：日期
	 * @param num：要加减的秒
	 * @return：成功，则返回加减后的日期；失败，则返回null
	 */
	public static Date addSeconds(Date date, int num)
	{
		if (date == null)
		{
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, num);
		return c.getTime();
	}

	public static Date getDate(String dateStr, String dateFormat) throws Exception
	{
		SimpleDateFormat df = new SimpleDateFormat(dateFormat);
		java.util.Date cDate = df.parse(dateStr);

		return cDate;
	}
	public static Date getDate(String dateStr) throws Exception
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
		java.util.Date cDate = df.parse(dateStr);

		return cDate;
	}
	public static Date getDateHH(String dateStr) throws Exception
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		java.util.Date cDate = df.parse(dateStr);

		return cDate;
	}

	public static Date convertTimerToDate(Timestamp time) throws Exception
	{
		Date dd = new Date(time.getTime());
		return dd;
	}
	public static long DateDiff(Date date1,Date date2) throws Exception
	{
		return date1.getTime() - date2.getTime();
	}
	
	/**
	 * 获取当前日期上一个月的第一天，格式为yyyy-MM-dd hh:ms:ss
	 * 
	 * @return String
	 */
	public static String getLastMonthFirstDay(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND, 0);
		return getPrettyDateTime(calendar.getTime());
	}
	/**
	 * 获取当前日期上一个月的第一天，格式为yyyy-MM-dd hh:ms:ss
	 * 
	 * @return String
	 */
	public static String getLastMonthEndDay(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE,59);
		calendar.set(Calendar.SECOND, 59);
		return getPrettyDateTime(calendar.getTime());
	}
	
	/**
	 * 获得2个日期的天数差
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	  public static int daysBetween(String smdate,String bdate) throws ParseException{   
		       SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");   
		         Calendar cal = Calendar.getInstance();     
		        cal.setTime(sdf.parse(smdate));     
		       long time1 = cal.getTimeInMillis();                  
		          cal.setTime(sdf.parse(bdate));     
		         long time2 = cal.getTimeInMillis();          
		         long between_days=(time2-time1)/(1000*3600*24);   
		            
		        return Integer.parseInt(String.valueOf(between_days));      
		    }
	  
	  
	  /**
		 * 获取指定日期的前N天   date = yyyyMMdd
		 * @description 
		 * @param beforeDay
		 * @return  
		 * @author YuWT
		 * @date 2014-8-14
		 */
		public static String getBeforDay(String date ,int beforeDay){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date d = null;
			try {
				d = sdf.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Calendar c =Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.DAY_OF_MONTH, -beforeDay);
			String upDay = format(c.getTime(), "yyyyMMdd");
			return upDay;
		}
		
		
		 /**
		 * 获取指定日期的前N天   date = yyyyMMdd
		 * @description 
		 * @param beforeDay
		 * @return  
		 * @author YuWT
		 * @date 2014-8-14
		 */
		public static String getAfterforDay(String date ,int afterDay){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date d = null;
			try {
				d = sdf.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Calendar c =Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.DAY_OF_MONTH, afterDay);
			String upDay = format(c.getTime(), "yyyyMMdd");
			return upDay;
		}
		
		/**
		 * 获得指定日期的后 n小时数
		 * @description 
		 * @param date
		 * @param num
		 * @return  
		 * @author lzp
		 * @date 2015-5-5
		 */
		public static Date addHours(Date date, int num){
			if (date == null){
				return null;
			}
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.HOUR_OF_DAY, num);

			return c.getTime();
		}
		
		public static String format(Date date,String pattern){
			return new SimpleDateFormat(pattern).format(date);
		}
	  public static void main(String[] args) {
		try {
			//Date tmepDate = DateUtils.getDate(DateUtils.getCurrentDate(),"yyyyMMdd");
			//Date t = DateUtils.addDays(DateUtils.getDate(DateUtils.getCurrentDate(),"yyyyMMdd"), -3);
			//System.out.println(DateUtils.parseDateToStrByFormat(tmepDate, "yyyyMMdd235959"));
			//System.out.println(getDate(DateUtils.parseDateToStrByFormat(t, "yyyyMMdd235959")));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dateTime = new Date();
			System.out.println(format.format(dateTime));
			System.out.println(format.format(addHours(dateTime, 26)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
