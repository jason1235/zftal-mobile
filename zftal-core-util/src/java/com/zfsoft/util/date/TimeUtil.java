package com.zfsoft.util.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 时间工具
 * 
 * @author Administrator
 * 
 */
public class TimeUtil {

	private TimeUtil() {

	}

	/**
	 * 取得当前时间
	 * 
	 * @return
	 */
	public static long getNow() {
		return System.currentTimeMillis();
	}

	/**
	 * 取得当前时间（数据库时间搓）
	 * 
	 * @return
	 */
	public static Timestamp getNowTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 取得当前天零晨时间
	 * 
	 * @return
	 */
	public static long getDayBeginTime() {
		Calendar c = GregorianCalendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);

		c.set(Calendar.SECOND, 0);
		return c.getTimeInMillis();
	}

	/**
	 * 取得当明天零晨时间
	 * 
	 * @return
	 */
	public static long getNextDayBeginTime() {
		Calendar c = GregorianCalendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);

		c.set(Calendar.SECOND, 0);
		c.add(Calendar.DAY_OF_MONTH, 1);
		return c.getTimeInMillis();
	}

	/** 年月日时分秒 */
	public static String getDateTime() {
		java.text.SimpleDateFormat f = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String time = f.format(new java.util.Date());
		return time;
	}

	/** 当前时间 */
	public static String getDateTime(java.text.SimpleDateFormat f) {
		String time = f.format(new java.util.Date());
		return time;
	}

	/** 年月日 */
	public static String getDay() {
		java.text.SimpleDateFormat f = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		String time = f.format(new java.util.Date());
		return time;
	}

	/** 小时 */
	public static String getHour() {
		java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("hh24");
		String time = f.format(new java.util.Date());
		return time;
	}

	/**
	 * 
	 * getYear
	 * <p>
	 * 当前年份
	 * </p>
	 * 
	 * @return
	 * @return String
	 * @author litao
	 * @date 2011-11-11
	 */
	public static String getYear() {
		return getDateTime(new java.text.SimpleDateFormat("yyyy"));
	}

	/**
	 * 取得日期格式
	 * 
	 * @param time
	 * @param format
	 * @return
	 */
	public static String getDataTime(long time, String format) {
		Timestamp ts = new Timestamp(time);
		java.text.SimpleDateFormat f = new java.text.SimpleDateFormat(format);
		String ret = f.format(ts);
		return ret;
	}

	/**
	 * 取得当前时间（数据库时间搓）
	 * 
	 * @return
	 */
	public static Timestamp getTimestamp(String time, String format) {
		java.text.SimpleDateFormat f = new java.text.SimpleDateFormat(format);
		try {
			return new Timestamp(f.parse(time).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 取得当前时间（数据库时间搓）
	 * 
	 * @return
	 */
	public static Timestamp getTimestamp(String time) {
		java.text.SimpleDateFormat f = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.m");
		try {
			return new Timestamp(f.parse(time).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 取得当前时间（数据库时间搓）
	 * 
	 * @return
	 */
	public static boolean isDate(String time) {
		java.text.SimpleDateFormat f = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.m");
		try {
			f.parse(time);
			return true;
		} catch (ParseException e) {
			return false;
		}
		
	}
	
	public static void main(String... strings) {
		System.out.println(TimeUtil.getYear());
	}
}