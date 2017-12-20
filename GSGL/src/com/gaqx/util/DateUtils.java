/**   
 * 特别声明：本技术材料受《中华人民共和国着作权法》、《计算机软件保护条例》
 * 等法律、法规、行政规章以及有关国际条约的保护，武汉中地数码科技有限公
 * 司享有知识产权、保留一切权利并视其为技术秘密。未经本公司书面许可，任何人
 * 不得擅自（包括但不限于：以非法的方式复制、传播、展示、镜像、上载、下载）使
 * 用，不得向第三方泄露、透露、披露。否则，本公司将依法追究侵权者的法律责任。
 * 特此声明！
 * 
   Copyright (c) 2013,武汉中地数码科技有限公司
 */
package com.gaqx.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.Vector;


public class DateUtils {
	public final static String DATEFORMAT_YMD = "yyyyMMdd";
	public final static String DATEFORMART_YMDH = "yyyyMMddHH";
	public final static String DATEFORMART_HM = "yyyyMMddHHmm";
	public final static String DATEFORMART_HMS = "yyyyMMddHHmmss";

	private final static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");  
	private final static SimpleDateFormat longHourSdf = new SimpleDateFormat("yyyy-MM-dd HH");
	private final static SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 

	public static DateFormat FORMAT_TIME = null;

	public static Date str2DateBylength(String date_Str) {
		String dateStr = date_Str;
		int len = dateStr.length();
		try {
			if (len >= 14) {
				dateStr = dateStr.substring(0, 14);
				FORMAT_TIME = new SimpleDateFormat(DATEFORMART_HMS);
			} else if (len >= 12) {
				dateStr = dateStr.substring(0, 12);
				FORMAT_TIME = new SimpleDateFormat(DATEFORMART_HM);
			} else if (len >= 10) {
				dateStr = dateStr.substring(0, 10);
				FORMAT_TIME = new SimpleDateFormat(DATEFORMART_YMDH);
			} else if (len >= 8) {
				dateStr = dateStr.substring(0, 8);
				FORMAT_TIME = new SimpleDateFormat(DATEFORMAT_YMD);
			} else {
				return new Date();
			}
			return FORMAT_TIME.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
			return new Date();
		}

	}

	/**
	 * 对日期(时间)中的日进行加减计算. <br>
	 * 例子: <br>
	 * 如果Date类型的d为 2005年8月20日,那么 <br>
	 * calculateByDate(d,-10)的值为2005年8月10日 <br>
	 * 而calculateByDate(d,+10)的值为2005年8月30日 <br>
	 * 
	 * @param d
	 *            日期(时间).
	 * @param amount
	 *            加减计算的幅度.+n=加n天;-n=减n天.
	 * @return 计算后的日期(时间).
	 */
	public static Date calculateByYear(Date d, int amount) {
		return calculate(d, GregorianCalendar.YEAR, amount);
	}

	public static Date calculateByMonth(Date d, int amount) {
		return calculate(d, GregorianCalendar.MONTH, amount);
	}

	public static Date calculateByDate(Date d, int amount) {
		return calculate(d, GregorianCalendar.DATE, amount);
	}

	public static Date calculateByHour(Date d, int amount) {
		return calculate(d, GregorianCalendar.HOUR, amount);
	}

	public static Date calculateByMinute(Date d, int amount) {
		return calculate(d, GregorianCalendar.MINUTE, amount);
	}

	/**
	 * 对日期(时间)中由field参数指定的日期成员进行加减计算. <br>
	 * 例子: <br>
	 * 如果Date类型的d为 2005年8月20日,那么 <br>
	 * calculate(d,GregorianCalendar.YEAR,-10)的值为1995年8月20日 <br>
	 * 而calculate(d,GregorianCalendar.YEAR,+10)的值为2015年8月20日 <br>
	 * 
	 * @param d
	 *            日期(时间).
	 * @param field
	 *            日期成员. <br>
	 *            日期成员主要有: <br>
	 *            年:GregorianCalendar.YEAR <br>
	 *            月:GregorianCalendar.MONTH <br>
	 *            日:GregorianCalendar.DATE <br>
	 *            时:GregorianCalendar.HOUR <br>
	 *            分:GregorianCalendar.MINUTE <br>
	 *            秒:GregorianCalendar.SECOND <br>
	 *            毫秒:GregorianCalendar.MILLISECOND <br>
	 * @param amount
	 *            加减计算的幅度.+n=加n个由参数field指定的日期成员值;-n=减n个由参数field代表的日期成员值.
	 * @return 计算后的日期(时间).
	 */
	public static Date calculate(Date d, int field, int amount) {
		if (d == null)
			return null;
		GregorianCalendar g = new GregorianCalendar();
		// g.setGregorianChange(d);
		g.setTime(d);
		g.add(field, amount);
		return g.getTime();
	}

	public static Date dateTimeString2Date(String date_str, String date_format) {
		try {
			Calendar cal = Calendar.getInstance();
			java.sql.Timestamp timestampnow = new java.sql.Timestamp(cal.getTimeInMillis());
			SimpleDateFormat formatter = new SimpleDateFormat(date_format);
			ParsePosition pos = new ParsePosition(0);
			java.util.Date current = formatter.parse(date_str, pos);
			timestampnow = new java.sql.Timestamp(current.getTime());
			return timestampnow;
		} catch (NullPointerException e) {
			return null;
		}
	}

	/**
	 * 日期(时间)转化为字符串.
	 * 
	 * @param formater
	 *            日期或时间的格式.
	 * @param aDate
	 *            java.util.Date类的实例.
	 * @return 日期转化后的字符串.
	 */
	public static String date2String(String formater, Date aDate) {
		if (formater == null || "".equals(formater))
			return null;
		if (aDate == null)
			return null;
		return (new SimpleDateFormat(formater)).format(aDate);
	}

	/**
	 * 当前日期(时间)转化为字符串.
	 * 
	 * @param formater
	 *            日期或时间的格式.
	 * @return 日期转化后的字符串.
	 */
	public static String date2String(String formater) {
		return date2String(formater, new Date());
	}

	/**
	 * 获取当前日期对应的星期数. <br>
	 * 1=星期天,2=星期一,3=星期二,4=星期三,5=星期四,6=星期五,7=星期六
	 * 
	 * @return 当前日期对应的星期数
	 */
	public static int dayOfWeek() {
		GregorianCalendar g = new GregorianCalendar();
		int ret = g.get(java.util.Calendar.DAY_OF_WEEK);
		g = null;
		return ret;
	}
	/**
	 * 获取当前日期对应的星期数. <br>
	 * 1=星期天,2=星期一,3=星期二,4=星期三,5=星期四,6=星期五,7=星期六
	 * 
	 * @return 当前日期对应的星期数
	 */
	public static String todayOfWeek(){
		String week = "";
		GregorianCalendar g = new GregorianCalendar();
		int ret = g.get(java.util.Calendar.DAY_OF_WEEK);
		g = null;
		switch(ret){
		case 1:week="星期天";break;
		case 2:week="星期一";break;
		case 3:week="星期二";break;
		case 4:week="星期三";break;
		case 5:week="星期四";break;
		case 6:week="星期五";break;
		case 7:week="星期六";break;
		}
		return week;
	}

	public static String getDayOfWeek(Date date){
		String week = "";
		@SuppressWarnings("deprecation")
		int day = date.getDay()+1;
		switch(day){
		case 1:week="星期天";break;
		case 2:week="星期一";break;
		case 3:week="星期二";break;
		case 4:week="星期三";break;
		case 5:week="星期四";break;
		case 6:week="星期五";break;
		case 7:week="星期六";break;
		}
		return week;
	}

	/**
	 * 获取所有的时区编号. <br>
	 * 排序规则:按照ASCII字符的正序进行排序. <br>
	 * 排序时候忽略字符大小写.
	 * 
	 * @return 所有的时区编号(时区编号已经按照字符[忽略大小写]排序).
	 */
	public static String[] fecthAllTimeZoneIds() {
		Vector<String> v = new Vector<String>();
		String[] ids = TimeZone.getAvailableIDs();
		for (int i = 0; i < ids.length; i++) {
			v.add(ids[i]);
		}
		java.util.Collections.sort(v, String.CASE_INSENSITIVE_ORDER);
		v.copyInto(ids);
		v = null;
		return ids;
	}


	/**
	 * 将世界时的日期时间字符串根据转换为北京时的日期时间.
	 * 
	 * @param srcFormater
	 *            待转化的日期时间的格式.
	 * @param srcDateTime
	 *            待转化的日期时间.
	 * @param dstFormater
	 *            目标的日期时间的格式.
	 * 
	 * @return dstDateTime 转化后的日期时间.
	 */
	public static String stringTimezone(String srcFormater, String srcDateTime, String dstFormater) {
		if (srcFormater == null || "".equals(srcFormater))
			return null;
		if (srcDateTime == null || "".equals(srcDateTime))
			return null;
		if (dstFormater == null || "".equals(dstFormater))
			return null;
		String dstDateTime = "";
		SimpleDateFormat sdf = new SimpleDateFormat(srcFormater);
		try {
			int diffTime = TimeZone.getTimeZone("UTC").getRawOffset() - TimeZone.getDefault().getRawOffset();
			Date d = sdf.parse(srcDateTime);
			long nowTime = d.getTime();
			long newNowTime = nowTime - diffTime;
			d = new Date(newNowTime);
			dstDateTime = (new SimpleDateFormat(dstFormater)).format(d);
		} catch (ParseException e) {

		}
		return dstDateTime;
	}

	/**
	 * 将日期时间字符串根据转换为指定时区的日期时间.
	 * 
	 * @param srcFormater
	 *            待转化的日期时间的格式.
	 * @param srcDateTime
	 *            待转化的日期时间.
	 * @param dstFormater
	 *            目标的日期时间的格式.
	 * @param dstTimeZoneId
	 *            目标的时区编号.
	 * 
	 * @return 转化后的日期时间.
	 */
	public static String string2Timezone(String srcFormater, String srcDateTime, String dstFormater,
			String dstTimeZoneId) {
		if (srcFormater == null || "".equals(srcFormater))
			return null;
		if (srcDateTime == null || "".equals(srcDateTime))
			return null;
		if (dstFormater == null || "".equals(dstFormater))
			return null;
		if (dstTimeZoneId == null || "".equals(dstTimeZoneId))
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(srcFormater);
		try {
			int diffTime = getDiffTimeZoneRawOffset(dstTimeZoneId);
			Date d = sdf.parse(srcDateTime);
			long nowTime = d.getTime();
			long newNowTime = nowTime - diffTime;
			d = new Date(newNowTime);
			return date2String(dstFormater, d);
		} catch (ParseException e) {
			// Log.output(e.toString(), Log.STD_ERR);
			return null;
		} finally {
			sdf = null;
		}
	}


	/**
	 * 获取系统当前默认时区与指定时区的时间差.(单位:毫秒)
	 * 
	 * @param timeZoneId
	 *            时区Id
	 * @return 系统当前默认时区与指定时区的时间差.(单位:毫秒)
	 */
	private static int getDiffTimeZoneRawOffset(String timeZoneId) {
		return TimeZone.getDefault().getRawOffset() - TimeZone.getTimeZone(timeZoneId).getRawOffset();
	}

	/**
	 * 将日期时间字符串根据转换为指定时区的日期时间.
	 * 
	 * @param srcDateTime
	 *            待转化的日期时间.
	 * @param dstTimeZoneId
	 *            目标的时区编号.
	 * 
	 * @return 转化后的日期时间.
	 * @see #string2Timezone(String, String, String, String)
	 */
	public static String string2TimezoneDefault(String srcDateTime, String dstTimeZoneId) {
		return string2Timezone("yyyy-MM-dd HH:mm:ss", srcDateTime, "yyyy-MM-dd HH:mm:ss", dstTimeZoneId);
	}

	/**
	 * 判断时间strStartDate是否在时间strEndDate之前
	 * 
	 * @param strStartDate
	 * @param strEndDate
	 * @return
	 */
	public static boolean isDateBefore(String strStartDate, String strEndDate) {

		try {
			DateFormat df = SimpleDateFormat.getDateTimeInstance();
			return df.parse(strStartDate).before(df.parse(strEndDate));

		} catch (ParseException e) {
			System.out.print("[SYS] " + e.getMessage());
			return false;
		}
	}

	public static String format(String format, Date date) {

		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			return df.format(date);

		} catch (Exception e) {
			System.out.print("[SYS] " + e.getMessage());
			return "";
		}
	}

	public static String getImgTitleTimeStr(String type, String time) {
		StringBuffer sb = new StringBuffer();
		String[] tmpArr = time.split("-");
		if (type.equals("hou")) {
			sb.append(tmpArr[0]).append("年");
			sb.append(tmpArr[1]).append("月");
			sb.append(tmpArr[2]).append("日");
			sb.append(tmpArr[3]).append("时");
		} else if (type.equals("day")) {
			sb.append(tmpArr[0]).append("年");
			sb.append(tmpArr[1]).append("月");
			sb.append(tmpArr[2]).append("日");
		}
		return sb.toString();
	}

	/**
	 * 功能描述：请用一句话描述这个方法实现的功能<br>
	 * 创建作者：王明生<br>
	 * 创建时间：2013-9-27 下午04:23:16<br>
	 * 
	 * @param year
	 * @param month
	 * @return
	 * @return String
	 */
	public static String getMonthDayStart(int year, int month, int day) {
		String fillMonth = fillNumber(month);
		String fillDay = fillNumber(day);
		// return year + "-" + fillMonth + "-" + fillDay + " 00:00:00";
		return year + fillMonth + fillDay + "0000";
	}

	/**
	 * 功能描述：请用一句话描述这个方法实现的功能<br>
	 * 创建作者：王明生<br>
	 * 创建时间：2013-9-27 下午04:23:19<br>
	 * 
	 * @param year
	 * @param month
	 * @return
	 * @return String
	 */
	public static String getMonthDayEnd(int year, int month, int day) {
		String fillMonth = fillNumber(month);
		String fillDay = fillNumber(day);
		// return year + "-" + fillMonth + "-" + fillDay + " 23:00:00";
		return year + fillMonth + fillDay + "2350";

	}

	/**
	 * 功能描述：获取指定年月的天数<br>
	 * 创建作者：王明生<br>
	 * 创建时间：2013-9-29 上午09:05:37<br>
	 * 
	 * @param year
	 * @param month
	 * @return
	 * @throws Exception
	 * @return int
	 */
	public static int getMonthDays(int year, int month) throws Exception {
		Calendar now = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String yearStr = Integer.toString(year);
		String monthStr = Integer.toString(month);
		if (monthStr.length() == 1) {
			monthStr = "0" + monthStr;
		}
		String time = yearStr + "-" + monthStr;
		now.setTime(sdf.parse(time));
		return now.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 功能描述：将单位数字转为化双位数字<br>
	 * 创建作者：王明生<br>
	 * 创建时间：2013-9-29 上午11:39:54<br>
	 * 
	 * @param number
	 *            单位数字
	 * @return String
	 */
	public static String fillNumber(int number) {
		String numStr = Integer.toString(number);
		if (number < 10) {
			numStr = "0" + numStr;
		}
		return numStr;
	}

	/**
	 * 功能描述：将指定格式的日期字符串转化为日期对象<br>
	 * 创建作者：王明生<br>
	 * 创建时间：2013-10-21 上午11:51:56<br>
	 * 
	 * @param dateStr
	 * @param format
	 * @return Date
	 */
	public static Date str2DateByFormat(String dateStr, String format) throws Exception {
		FORMAT_TIME = new SimpleDateFormat(format);
		return FORMAT_TIME.parse(dateStr);
	}

	/**
	 * 
	 * 功能描述：将一种格式的时间字符串转化为另一种格式<br>
	 * 创建作者：王明生<br>
	 * 创建时间：2013-11-4 上午09:55:27<br>
	 * 
	 * @param dateStr
	 *            时间字符串
	 * @param oldFormat
	 *            旧格式
	 * @param newFormat
	 *            新格式
	 * @throws Exception
	 * @return String 新的时间字符串
	 */
	public static String dateStr2Str(String dateStr, String oldFormat, String newFormat) throws Exception {
		Date date = str2DateByFormat(dateStr, oldFormat);
		return date2String(newFormat, date);
	}

	public static int getDayOfYear(String dateStr, String format) throws Exception {
		GregorianCalendar calendar = new GregorianCalendar();
		Date date = DateUtils.str2DateByFormat(dateStr, format);
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * @Description: 获得本周一0点时间  
	 * @author : wy
	 * @param @return
	 * @return Date
	 * @throws
	 */
	public static Date getTimesWeekmorning() {  
		Calendar cal = Calendar.getInstance();  
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  
		return  cal.getTime();  
	}  

	/**
	 * @Description: 获得本周日24点时间  
	 * @author : wy
	 * @param @return
	 * @return Date
	 * @throws
	 */
	public  static Date getTimesWeeknight() {  
		Calendar cal = Calendar.getInstance();  
		cal.setTime(getTimesWeekmorning());  
		cal.add(Calendar.DAY_OF_WEEK, 7);  
		return cal.getTime();  
	}  

	/**
	 * @Description:  获得本月第一天0点时间  
	 * @author : wy
	 * @param @return
	 * @return Date
	 * @throws
	 */
	public static Date getTimesMonthmorning() {  
		Calendar cal = Calendar.getInstance();  
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));  
		return  cal.getTime();  
	}  

	/**
	 * @Description:  获得本月最后一天24点时间  
	 * @author : wy
	 * @param @return
	 * @return Date
	 * @throws
	 */
	public static Date getTimesMonthnight() {  
		Calendar cal = Calendar.getInstance();  
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));  
		cal.set(Calendar.HOUR_OF_DAY, 24);  
		return cal.getTime();  
	}  

	/**
	 * @Description: 得到当月的天数
	 * @author : wy
	 * @param @return
	 * @return int
	 * @throws
	 */
	public static int getCurrentMonthLastDay()  
	{  
		Calendar a = Calendar.getInstance();  
		a.set(Calendar.DATE, 1);//把日期设置为当月第一天  
		a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天  
		int maxDate = a.get(Calendar.DATE);  
		return maxDate;  
	}
	
	/**
     * 当前季度的开始时间，即2012-01-01 00:00:00
     * 
     * @return
     */
    public static  Date getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 6);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前季度的结束时间，即2012-03-31 23:59:59
     * 
     * @return
     */
    public  static Date getCurrentQuarterEndTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 8);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }  

	public static void main(String[] argc) {
		System.out.println(date2String("yyyy-MM-dd HH:mm:ss",getCurrentQuarterStartTime()));
		System.out.println(date2String("yyyy-MM-dd HH:mm:ss",getCurrentQuarterEndTime()));
	}


}
