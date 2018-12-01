package com.util;

import java.util.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * <p>
 * Title: 通用日历处理类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: http://www.ecity88.com
 * </p>
 * 
 * @author xxh
 * @version 1.0
 */

public class GeneralCalendar {
	private static final int[] days = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
			30, 31 };

	private Calendar cal = Calendar.getInstance();

	// private final static GeneralCalendar instance = new GeneralCalendar();

	final static int closeTime = 6;

	public GeneralCalendar() {
	}

	/**
	 * 取日期对象
	 * 
	 * @return
	 * @author wyz
	 * @since 2006-9-29 上午10:19:55
	 */
	public static GeneralCalendar getInstance() {
		return new GeneralCalendar();
	}

	// static public GeneralCalendar getInstance() {
	// return instance;
	// }

	public boolean isWeek() {
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		@SuppressWarnings("unused")
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		if (dayofweek == 7) { // 星期六
			return true;
		}
		if (dayofweek == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 返回日期 格式:yyyymmdd
	 * 
	 * @return int
	 */
	public int getDate() {
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		return year * 10000 + (month + 1) * 100 + day;
	}

	/**
	 * 返回时间 格式:时/分/秒 hhnnss
	 * 
	 * @return int
	 */
	public int getTime() {
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		return hour * 10000 + minute * 100 + second;
	}

	/**
	 * 返回日期时间
	 * 
	 * @return long
	 */
	public long getDateTime() {
		return (long) getDate() * 1000000L + (long) getTime();
	}

	public void set(int field, int value) {
		cal.set(field, value);
	}

	public static int getLastDate(int oldDate) {
		return getNextDate(oldDate, -1);
	}

	public static int getYesterday() {
		int currentdate = getCurrentDate().intValue();
		return getNextDate(currentdate, -1);
	}

	/**
	 * 获取下个天后的日期
	 * 
	 * @param oldDate
	 * @return
	 * @author lou
	 * @since May 6, 2008 3:00:30 PM
	 */
	public static int getNextDate(int oldDate) {
		return getNextDate(oldDate, 1);
	}

	/**
	 * 获取 days天后的日期
	 * 
	 * @param oldDate
	 * @param days
	 * @return
	 * @author lou
	 * @since May 6, 2008 2:59:39 PM
	 */
	public static int getNextDate(int oldDate, int days) {
		GeneralCalendar cal = new GeneralCalendar();
		cal.setDate(oldDate);
		cal.addDay(days);
		return cal.getDate();
	}
	
	/**
	 * 获取 days天后的日期
	 * 
	 * @param oldDate
	 * @param days
	 * @return
	 * @author cyj
	 * @since 20080903
	 */
	public static int getNextDate(Date oldDate, int days) {
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
		return getNextDate((new Integer(simpleDate.format(oldDate).replaceAll("-", ""))).intValue(),days);
	}

	/**
	 * @param oldDate
	 * @param monthCount
	 * @return
	 * @author lou
	 * @since May 6, 2008 3:09:59 PM
	 */
	public static int getNextMonth(int oldDate, int monthCount) {
		GeneralCalendar cal = new GeneralCalendar();
		cal.setDate(oldDate);
		cal.addMonth(monthCount);
		return cal.getDate();
	}

	public void setDate(int date) {
		cal.set(Calendar.YEAR, date / 10000);
		cal.set(Calendar.MONTH, (date % 10000) / 100 - 1);
		cal.set(Calendar.DAY_OF_MONTH, (date % 100));
	}

	public void addDay(int dayCount) {
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + dayCount);
	}

	/**
	 * 设置日期
	 * 
	 * @param date
	 *            long 格式yyyymmdd 或yyyymmddhhnnss
	 */
	public void set(long dateTime) {
		int date = 0;
		int time = 0;
		int hour = 0;
		int minute = 0;
		int second = 0;
		String str = String.valueOf(dateTime);
		if (str.length() != 8 && str.length() != 14)
			return;
		date = Integer.parseInt(str.substring(0, 8));
		int year = date / 10000;
		int month = (date - year * 10000) / 100;
		int day = date - year * 10000 - month * 100;
		if (str.length() == 14) {
			time = Integer.parseInt(str.substring(8, 14));
			hour = time / 10000;
			minute = (time - hour * 10000) / 100;
			second = time - hour * 10000 - minute * 100;
		}
		cal.set(year, month - 1, day, hour, minute, second);
	}

	/**
	 * 
	 * @param monthCount
	 *            int
	 * @return String
	 */
	public void addMonth(int monthCount) {
		cal.set(Calendar.MONTH, cal.get(Calendar.MONDAY) + monthCount);
	}

	public static int addDay(int date, int dayCount) {
		GeneralCalendar gcal = new GeneralCalendar();
		gcal.set(date);
		gcal.set(Calendar.DATE, gcal.get(Calendar.DATE) + dayCount);
		return getDate(gcal.cal);
	}

	// public void addMonthForInterest
	public void add(int field, int amount) {
		cal.set(field, cal.get(field) + amount);
	}

	public int get(int field) {
		return cal.get(field);
	}

	/**
	 * 格式化输出
	 * 
	 * @param sformat
	 *            String
	 * @return String
	 */
	public String format(String sformat) {
		// yyyymmddhhnnss
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumIntegerDigits(2);
		nf.setMinimumIntegerDigits(2);
		String strbuf = new String(sformat);
		strbuf = strbuf.replaceAll("yyyy", Integer.toString(cal
				.get(Calendar.YEAR)));
		strbuf = strbuf
				.replaceAll("mm", nf.format(cal.get(Calendar.MONTH) + 1));
		strbuf = strbuf.replaceAll("dd", nf.format(cal
				.get(Calendar.DAY_OF_MONTH)));
		strbuf = strbuf.replaceAll("hh", nf.format(cal
				.get(Calendar.HOUR_OF_DAY)));
		strbuf = strbuf.replaceAll("nn", nf.format(cal.get(Calendar.MINUTE)));
		strbuf = strbuf.replaceAll("ss", nf.format(cal.get(Calendar.SECOND)));
		return strbuf;
	}

	/**
	 * 格式化输出
	 * 
	 * @param sformat
	 *            String
	 * @return String
	 */
	public static String getCurrentDate(String sformat) {
		// yyyymmddhhnnss
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumIntegerDigits(2);
		nf.setMinimumIntegerDigits(2);
		Calendar cal = Calendar.getInstance();
		String strbuf = new String(sformat);
		strbuf = strbuf.replaceAll("yyyy", Integer.toString(cal
				.get(Calendar.YEAR)));
		strbuf = strbuf
				.replaceAll("mm", nf.format(cal.get(Calendar.MONTH) + 1));
		strbuf = strbuf.replaceAll("dd", nf.format(cal
				.get(Calendar.DAY_OF_MONTH)));
		strbuf = strbuf.replaceAll("hh", nf.format(cal
				.get(Calendar.HOUR_OF_DAY)));
		strbuf = strbuf.replaceAll("nn", nf.format(cal.get(Calendar.MINUTE)));
		strbuf = strbuf.replaceAll("ss", nf.format(cal.get(Calendar.SECOND)));
		return strbuf;
	}

	// 检验日期格式 年-月- 日
	public static boolean isRightDate(int date) {
		int year = date / 10000;
		int month = (date - year * 10000) / 100;
		int day = date - year * 10000 - month * 100;
		if (year < 1900 || year > 3000)
			return false;
		if (month > 12 || month < 1)
			return false;
		if ((year % 4 == 0 || (year % 100 == 0 && year % 400 == 0))
				&& month == 2)
			days[1] = 29;
		else
			days[1] = 28;
		if (day < 1 || day > days[month - 1])
			return false;
		return true;
	}

	// 检验时间 时-份-秒
	public static boolean isRightTime(int time) {
		int hour = time / 10000;
		int minute = (time - hour * 10000) / 100;
		int second = time - hour * 10000 - minute * 100;
		if (hour > 23 || hour < 0)
			return false;
		if (minute > 59 || minute < 0)
			return false;
		if (second > 59 || second < 0)
			return false;
		return true;
	}

	// 检验日期 时间 年-月-日-时-分-秒
	public static boolean isRightDateTime(long dateTime) {
		int date = (int) (dateTime / 1000000);
		int time = (int) (dateTime - date * 1000000);
		return isRightDate(date) && isRightTime(time);
	}

	public int getWeekOfYear() {
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	public int getMonthOfYear() {
		return cal.get(Calendar.MONTH);
	}

	public int getYear() {
		return cal.get(Calendar.YEAR);
	}

	public int getDayOfWeek() {
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	// 取得月的天数
	public int getDaysOfMonth() {// 0为1月
		int month = getMonthOfYear();
		int year = getYear();
		if (month == 1) {
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				return 29;
			else
				return 28;
		} else
			return days[month];

	}

	public long getSUNDateTime() {// 该天所在周的周日。注：周日为一周的第一天。
		int week = getDayOfWeek();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		// System.out.println(week + ":"+ day);
		cal.set(Calendar.DAY_OF_MONTH, day - week + 1);
		return getDateTime();
	}

	public long getFirstMonthDT() {
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return getDateTime();
	}

	public static long getNow() {
		return (long) (getCurrentDate().intValue()) * 1000000L
				+ (long) (getCurrentTime().intValue());
	}

	public static Integer getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		return new Integer(getDate(cal));
	}

	/**
	 * @获取当前的日期(Date类型)
	 * @getCurrentDateForCalendar
	 * @param curdate
	 * @return Calendar
	 * @author cyj
	 * @since 20080828
	 */
	public static Date getCurrentDateForTypeDate() {
		Date curredate = new Date();
		try {
			Calendar cal = Calendar.getInstance();
			String curdate = new Integer(getDate(cal)).toString();
			curdate = curdate.substring(0, 4) + "-" + curdate.substring(4, 6)
					+ "-" + curdate.substring(6, 8);
			SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
			curredate = simpleDate.parse(curdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return curredate;
	}

	/**
	 * @从Integer类型的日期转换成Calendar类型
	 * @getCurrentDateForCalendar
	 * @param curdate
	 * @return Calendar
	 * @author cyj
	 * @since 20080828
	 */
	public static Calendar getCalendarByIntdate(Integer curdate) {
		Calendar cal = Calendar.getInstance();
		try {
			if (curdate != null && curdate.toString().length() == 8) {
				String curdatestr = curdate.toString();
				curdatestr = curdatestr.substring(0, 4) + "-"
						+ curdatestr.substring(4, 6) + "-"
						+ curdatestr.substring(6, 8);
				SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
				Date curredate = simpleDate.parse(curdatestr);
				cal.setTime(curredate);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cal;
	}


	
	/**
	 * @从date类型的日期转换成Calendar类型
	 * @getCurrentDateForCalendar2
	 * @param curdate
	 * @return Calendar
	 * @author cyj
	 * @since 20080828
	 */
	public static Calendar getCalendarByDate(Date curdate) {
		Calendar cal = Calendar.getInstance();
		if(curdate!=null){
			cal.setTime(curdate);
		}
		return cal;
	}

	public static int getDate(Calendar cal) {
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		return year * 10000 + (month + 1) * 100 + day;
	}

	public static Integer getCurrentTime() {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		return new Integer(hour * 10000 + minute * 100 + second);
	}

	/**
	 * 计算两个日期的差值(月份),无假日顺延 // [0] -代表月份差 [1] -代表天数差 即20080507-20080807 的返回值为 3
	 * ，0
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @author lou
	 * @since Apr 27, 2008 2:24:14 PM
	 */
	public static int[] abstractDate(int beginDate, int endDate) {
		int[] retValue = new int[2];
		if (beginDate != 0 && beginDate < endDate) {
			int year1 = beginDate / 10000;
			int year2 = endDate / 10000;
			int month1 = (beginDate - year1 * 10000) / 100;
			int month2 = (endDate - year2 * 10000) / 100;
			int day1 = beginDate % 100;
			int day2 = endDate % 100;
			if (day2 >= day1) {
				retValue[0] = (year2 - year1) * 12 + (month2 - month1);
				retValue[1] = day2 - day1;
			} else {
				if (year2 % 4 == 0 || (year2 % 100 == 0 && year2 % 400 == 0))
					days[1] = 29;
				else
					days[1] = 28;
				retValue[0] = (year2 - year1) * 12 + (month2 - month1) - 1;
				if (day1 >= days[month2 - 2])
					retValue[1] = day2;
				else
					retValue[1] = 30 - day1 + day2;
			}

		}
		return retValue;
	}

	/**
	 * 计算两个日期的差值
	 * 
	 * @param beginDate
	 *            int
	 * @param endDate
	 *            int
	 * @return int[] 0-month 1-day
	 */
	public static int[] abstractDate(int beginDate, int endDate, int cycode) {
		int[] retValue = new int[2];
		// if(beginDate !=0 && beginDate < endDate){
		// //节假日顺延
		// beginDate = HolidayUtil.getInstance().getWorkDate(beginDate,cycode);
		// endDate = HolidayUtil.getInstance().getWorkDate(endDate,cycode);
		// int year1 = beginDate/10000;
		// int year2 = endDate/10000;
		// int month1 = (beginDate-year1*10000)/100;
		// int month2 = (endDate-year2*10000)/100;
		// int day1 = beginDate%100;
		// int day2 = endDate % 100;
		// if(day2 >= day1){
		// retValue[0] = (year2-year1)*12 + (month2-month1);
		// retValue[1] = day2 - day1;
		// }else{
		// if(year2 % 4 == 0 || (year2 % 100 == 0 && year2 % 400 ==0))
		// days[1] = 29;
		// else
		// days[1] = 28;
		// retValue[0] = (year2-year1)*12 + (month2-month1) -1;
		// if(day1 >= days[month2-2])
		// retValue[1] = day2;
		// else
		// retValue[1] = 30-day1 + day2;
		// }
		//
		// }
		return retValue;
	}

	/**
	 * 按30360的方式计算天数.节假日顺延
	 * 
	 * @param beginDate
	 *            int
	 * @param endDate
	 *            int
	 * @return int
	 */
	/*
	 * public static int getDay30360(int beginDate,int endDate){
	 * 
	 * if(isRightDate(beginDate) && isRightDate(endDate)){ beginDate =
	 * HolidayUtil.getInstance().getWorkDate(beginDate); endDate =
	 * HolidayUtil.getInstance().getWorkDate(endDate); int year1 =
	 * beginDate/10000; int year2 = endDate/10000; int month1 =
	 * (beginDate-year1*10000)/100; int month2 = (endDate-year2*10000)/100; int
	 * day1 = beginDate%100; int day2 = endDate % 100; return ((year2-year1)*12 +
	 * month2-month1)*30 + day2 - day1;
	 * 
	 * }else return 0; }
	 */
	public static int getSubMonth(int beginDate, int endDate) {
		if (isRightDate(beginDate) && isRightDate(endDate)) {
			int year1 = beginDate / 10000;
			int year2 = endDate / 10000;
			int month1 = (beginDate - year1 * 10000) / 100;
			int month2 = (endDate - year2 * 10000) / 100;
			return (year2 - year1) * 12 + month2 - month1;
		} else
			return 0;

	}

	/**
	 * 当日期为月末的一天时，加一个月后还是月末的那一天。
	 */
	public void addOneMonth() {
		if (isLeapYear())
			days[1] = 29;
		else
			days[1] = 28;
		if (isEndDay()) {
			int m = cal.get(Calendar.MONTH) + 1;
			cal.add(Calendar.DAY_OF_MONTH, days[m]);
		} else {
			addMonth(1);
		}
		// System.out.println(getDate());
	}

	/**
	 * 是否为月末的一天。
	 * 
	 * @return boolean
	 */
	public boolean isEndDay() {
		int m = cal.get(Calendar.MONTH);
		int d = cal.get(Calendar.DAY_OF_MONTH);
		if (isLeapYear())
			days[1] = 29;
		else
			days[1] = 28;
		if (d >= days[m])
			return true;
		else
			return false;
	}

	private boolean isLeapYear() {
		int year = cal.get(Calendar.YEAR);
		if (year % 4 == 0 || (year % 100 == 0 && year % 400 == 0))
			return true;
		else
			return false;
	}

	public void initBank() {
		// bankList.put(new Bank(8888,4));
		// for(int i=1;i<200;i++){
		// bankList.put(new Bank(i*1000,8888));
		// for(int j=1;j<20;j++){
		// bankList.put(new Bank(i * 1000 + j * 10, i * 1000));
		// for(int l=1;l<10;l++)
		// bankList.put(new Bank(i * 1000 + j * 10 + l, i * 1000 + j*10));
		// }
		// }
		// Bank[] list = bankList.values();
		// for(int k=0;k<list.length;k++){
		// System.out.println(list[k].getId() + "," + list[k].getHead());
		// }
	}

	/**
	 * 判断是否为正确的起息日，每季的15日
	 * 
	 * @param valueDate
	 *            int
	 * @return boolean
	 */
	public static boolean isValueDate(int valueDate) {
		if (!isRightDate(valueDate))
			return false;
		int year = valueDate / 10000;
		int month = (valueDate - year * 10000) / 100;
		int day = valueDate - year * 10000 - month * 100;
		if (month != 3 && month != 6 && month != 9 && month != 12)
			return false;
		if (day != 15)
			return false;
		return true;
	}

	public void testDb() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@ofptest:1521:ofp20", "ERAXFUNDS",
					"ERAXFUNDS");
			Statement stmt = conn.createStatement();
			ResultSet rts = stmt
					.executeQuery("select * from worker where rownum < 2");
			if (rts.next()) {
				String name = rts.getString("name");
				System.out.println("name:" + name);
			}
		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				System.out.println("close error:" + e.getMessage());
			}
		}
	}
	
	public static void main(String[] args) {
		// //由于 客户名称在转换 为汉字时 存在 “ ”字符，非空格字符。故需要进行转换
		try {
			System.out.println(formatDateToInt(getCurrentDateForTypeDate()));
			
			/*
			byte[] a = chsname.getBytes();
			for (int i = 0; i < a.length; i++) {
				System.out.println(a[i] + ":" + " ".getBytes()[0]);
			}
			chsname = chsname.replaceAll(new String(x, "gb2312"), "");// 舍去
			*/
			// -95
			// 字符
		} catch (Exception e) {

		}
	}

	/**
	 * 按30360的方式计算天数
	 * 
	 * @param beginDate
	 *            int
	 * @param endDate
	 *            int
	 * @return int
	 */
	public static int getDay30360(int beginDate, int endDate) {

		if (isRightDate(beginDate) && isRightDate(endDate)) {
			int year1 = beginDate / 10000;
			int year2 = endDate / 10000;
			int month1 = (beginDate - year1 * 10000) / 100;
			int month2 = (endDate - year2 * 10000) / 100;
			int day1 = beginDate % 100;
			int day2 = endDate % 100;
			return ((year2 - year1) * 12 + month2 - month1) * 30 + day2 - day1;

		} else
			return 0;
	}

	/**
	 * 按一年365天(不足一年按实际天数)的方式计算天数
	 * 
	 * @param beginDate
	 *            int
	 * @param endDate
	 *            int
	 * @return int
	 */
	public static int getDay365(int beginDate, int endDate) {
		if (isRightDate(beginDate) && isRightDate(endDate)) {
			int month = getSubMonth(beginDate, endDate);
			addMonth(beginDate, month % 12);
			return month / 12 * 365 + dateDiff(beginDate, endDate);

		} else
			return 0;
	}

	public static int addMonth(int date, int monthCount) {
		GeneralCalendar gcal = new GeneralCalendar();
		gcal.set(date);
		gcal.addMonth(monthCount);
		return getDate(gcal.cal);
	}

	// 计算两个日期的差，跟VB里的DateDiff差不多,nDate,eDate为Date对象
	public static int dateDiff(int beginDate, int endDate) {
		Date d1 = getDate1(beginDate);
		Date d2 = getDate1(endDate);
		// Get msec from each, and subtract.
		long diff = d2.getTime() - d1.getTime();
		return (int) (diff / (1000 * 60 * 60 * 24));
	}

	private static Date getDate1(int date) {
		int year = date / 10000;
		int month = date / 100 % 100;
		int day = date % 100;
		return new GregorianCalendar(year, month, day).getTime();
	}
	public static Date getDateByInt(int date) {
		int year = date / 10000;
		int month = date / 100 % 100;
		int day = date % 100;
		return new GregorianCalendar(year, month, day).getTime();
	}
	
	public static Date getSimpleDateByInt(Integer date){
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
		Date newDate = new Date();
		try {
			String tempdate = date.toString();
			tempdate = getFormatDate8(tempdate);
			newDate = simpleDate.parse(tempdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
	}
	
	/**
	 * @格式化日期，月或日为0-9的前面补0
	 * @param date
	 * @return String
	 * @by cyj
	 * @since 20080811
	 */
	public static String getFormatDate(String date) {
		if(date==null)
			return "";
		date = date.trim();
		if (date.length() == 7) {
			String date1 = date.substring(0, 4); // 年
			String date2 = "";
			String date3 = "";
			if (Integer.parseInt(date.substring(4, 6)) < 13) {
				date2 = date.substring(4, 6); // 月
				date3 = "0" + date.substring(6, 7); // 日
			} else {
				date2 = "0" + date.substring(4, 5); // 月
				date3 = date.substring(5, 7); // 日
			}
			date = date1 + date2 + date3;
		}
		if (date.length() == 6) {
			String date1 = date.substring(0, 4); // 年
			String date2 = "0" + date.substring(4, 5); // 月
			String date3 = "0" + date.substring(5, 6); // 日
			date = date1 + date2 + date3;
		}
		return date;
	}
	
	/**
	 * @格式化时间，0-9的前面补0
	 * @param time
	 * @return String
	 * @by cyj
	 * @since 20080811
	 */
	public static String getFormatTime(String time){
		if(time==null)
			return "";
		if(time.length()==5)
			return "0" + time;
		return time;
	}
	
	/**
	 * @从多个日期里获取最大的日期
	 * @param date(许多日期，按;分隔)
	 * @return String
	 * @by cyj
	 * @since 20080908
	 */
	public static String getMaxDateByDateStr(String date){
		if(date.substring(date.length()-1, date.length()).equals(";")) //最后一位是;号
			date = date.substring(0, date.length()-1); //去掉最后一位
		if(date!=null&&date.length()>0){
			String[] tempDate = date.split(";");
			String maxdate = "";
			for(int i=0;i<tempDate.length-1;i++){
				int date1 = Integer.parseInt(tempDate[i]);
				int date2 = Integer.parseInt(tempDate[i+1]);
				if(maxdate.length()==0){
					if(date1 > date2)
						maxdate = (new Integer(date1)).toString();
					else
						maxdate = (new Integer(date2)).toString();
				}
				else{
					if(Integer.parseInt(maxdate)<date2)
						maxdate = (new Integer(date2)).toString();
				}
			}
			date = maxdate;
		}
		return date;
	}
	
	/**
	 * @格式化8位的日期为10位(yyyyMMdd格式化成yyyy-MM-dd)
	 * @param date
	 * @return String
	 * @by cyj
	 * @since 20080911
	 */
	public static String getFormatDate8(String date){
		if(date == null || date.length() == 0)
			return "";
		if(date.length()<8)
			date = getFormatDate(date);
		if(date.length()==8)
			date = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
		return  date;
	}
	
	/**
	 * @格式化6位的时间为8位(hhMMss格式化为hh:MM:ss)
	 * @param time
	 * @return String
	 * @by cyj
	 * @since 20080911
	 */
	public static String getFormatTime6(String time){
		if(time == null || time.length() == 0)
			return "";
		if(time.length()==5)
			time = getFormatTime(time);
		if(time.length()==6)
			time = time.substring(0, 2) + ":" + time.substring(2, 4) + ":" + time.substring(4, 6);
		return time;
	}
	
	/**
	 * @格式化日期为int类型
	 * @param date
	 * @return int
	 * @author cyj
	 * @since 20080924
	 */
	public static int formatDateToInt(Date date){
		int dateInt = 0;
		if(date!=null){
			SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMdd");
			String datestr = simpleDate.format(date);
			dateInt = Integer.parseInt(datestr);
		}
		return dateInt;
	}
}
