/**
 * 
 */
package com.zfsoft.sjzx.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * ���ڴ���util
 * 
 * hzmy-fms - DateTimeUtil.java
 *
 * com.hzmy.fms.common.util
 *
 * Copyright 2009 Hangzhou Port International Logistics Co.,ltd
 * All right reserved. 
 *
 * Created on 2009-4-19
 * @author jianghao
 */
public class DateTimeUtil {
	public final static int TIME_DAY_MILLISECOND = 86400000;
	// /
	// ����ʱ��������ʾ��ʽ
	// /
	private final static String DATE_FORMAT = "yyyy-MM-dd";

	private final static String DATE_FORMAT_CN = "yyyy��MM��dd��";

	private final static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private final static String TIME_FORMAT_CN = "yyyy��MM��dd�� HH:mm:ss";

	private final static String MONTH_FORMAT = "yyyy-MM";

	private final static String DAY_FORMAT = "yyyyMMdd";

	// private final static String TIME_FORMAT_MILLI = "yyyy-MM-dd
	// HH:mm:ss.SSS";

	/**
	 * ȡ�õ�ǰϵͳʱ�䣬����java.util.Date����
	 * 
	 * @see java.util.Date
	 * @return java.util.Date ���ط�������ǰϵͳʱ��
	 */
	public static java.util.Date getCurrDate() {
		return new java.util.Date();
	}

	/**
	 * ȡ�õ�ǰϵͳʱ���
	 * 
	 * @see java.sql.Timestamp
	 * @return java.sql.Timestamp ϵͳʱ���
	 */
	public static java.sql.Timestamp getCurrTimestamp() {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}

	/**
	 * ��2007-12-1���2007-12-01����2007-9-1��Ϊ2007-09-01��
	 * 
	 * @param date
	 * @return
	 */
	public static String getFormatDateV2(String date) {
		if (date == null) {
			return null;
		}

		String[] datearr = StringUtil.split(date, "-");
		if (datearr == null || datearr.length != 3) {
			return date;
		}

		StringBuffer ret = new StringBuffer();
		ret.append(datearr[0]);
		ret.append("-");
		ret.append(Integer.parseInt(datearr[1]) < 10 ? "0" + Integer.parseInt(datearr[1]) : datearr[1]);
		ret.append("-");
		ret.append(Integer.parseInt(datearr[2]) < 10 ? "0" + Integer.parseInt(datearr[2]) : datearr[2]);
		return ret.toString();
	}

	/**
	 * ��ʱ�䴮�л�ȡСʱ����
	 * 
	 * @param timestr
	 *            "2007-10-12 13:25:00"
	 * @return
	 */
	public static int getHourFromTimeString(String timestr) {
		if (StringUtil.isBlank(timestr)) {
			return 0;
		}

		return Integer.parseInt(timestr.substring(timestr.length() - 8, timestr.length() - 6));
	}

	/**
	 * ���ص�ǰʱ�������绹������
	 * 
	 * @see Calendar.AM 0
	 * @see Calendar.PM 1
	 * @author lenghao
	 * @createTime 2008-8-2 ����04:22:07
	 * @return
	 */
	public static Integer getCurrDateAMorPM() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.AM_PM);
	}

	/**
	 * �õ���ʽ��������ڣ���ʽΪyyyy-MM-dd����2006-02-15
	 * 
	 * @param currDate
	 *            Ҫ��ʽ��������
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String ���ظ�ʽ��������ڣ�Ĭ�ϸ�ʽΪΪyyyy-MM-dd����2006-02-15
	 */
	public static String getFormatDate(java.util.Date currDate) {
		return getFormatDate(currDate, DATE_FORMAT);
	}

	/**
	 * �õ���ʽ��������ڣ���ʽΪyyyy-MM-dd����2006-02-15
	 * 
	 * @param currDate
	 *            Ҫ��ʽ��������
	 * @see #getFormatDate(java.util.Date)
	 * @return Date ���ظ�ʽ��������ڣ�Ĭ�ϸ�ʽΪΪyyyy-MM-dd����2006-02-15
	 */
	public static Date getFormatDateToDate(java.util.Date currDate) {
		return getFormatDate(getFormatDate(currDate));
	}

	/**
	 * �õ���ʽ��������ڣ���ʽΪyyyy��MM��dd�գ���2006��02��15��
	 * 
	 * @param currDate
	 *            Ҫ��ʽ��������
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String ���ظ�ʽ��������ڣ�Ĭ�ϸ�ʽΪyyyy��MM��dd�գ���2006��02��15��
	 */
	public static String getFormatDate_CN(java.util.Date currDate) {
		return getFormatDate(currDate, DATE_FORMAT_CN);
	}

	/**
	 * �õ���ʽ��������ڣ���ʽΪyyyy��MM��dd�գ���2006��02��15��
	 * 
	 * @param currDate
	 *            Ҫ��ʽ��������
	 * @see #getFormatDate_CN(String)
	 * @return Date ���ظ�ʽ��������ڣ�Ĭ�ϸ�ʽΪyyyy��MM��dd�գ���2006��02��15��
	 */
	public static Date getFormatDateToDate_CN(java.util.Date currDate) {
		return getFormatDate_CN(getFormatDate_CN(currDate));
	}

	/**
	 * �õ���ʽ��������ڣ���ʽΪyyyy-MM-dd����2006-02-15
	 * 
	 * @param currDate
	 *            Ҫ��ʽ��������
	 * @see #getFormatDate(String, String)
	 * @return Date ���ظ�ʽ��������ڣ�Ĭ�ϸ�ʽΪyyyy-MM-dd����2006-02-15
	 */
	public static Date getFormatDate(String currDate) {
		return getFormatDate(currDate, DATE_FORMAT);
	}

	/**
	 * �õ���ʽ��������ڣ���ʽΪyyyy��MM��dd�գ���2006��02��15��
	 * 
	 * @param currDate
	 *            Ҫ��ʽ��������
	 * @see #getFormatDate(String, String)
	 * @return ���ظ�ʽ��������ڣ�Ĭ�ϸ�ʽΪyyyy��MM��dd�գ���2006��02��15��
	 */
	public static Date getFormatDate_CN(String currDate) {
		return getFormatDate(currDate, DATE_FORMAT_CN);
	}

	/**
	 * ���ݸ�ʽ�õ���ʽ���������
	 * 
	 * @param currDate
	 *            Ҫ��ʽ��������
	 * @param format
	 *            ���ڸ�ʽ����yyyy-MM-dd
	 * @see java.text.SimpleDateFormat#format(java.util.Date)
	 * @return String ���ظ�ʽ��������ڣ���ʽ�ɲ���<code>format</code> ���壬��yyyy-MM-dd����2006-02-15
	 */
	public static String getFormatDate(java.util.Date currDate, String format) {
		if (currDate == null) {
			return "";
		}
		SimpleDateFormat dtFormatdB = null;
		try {
			dtFormatdB = new SimpleDateFormat(format);
			return dtFormatdB.format(currDate);
		} catch (Exception e) {
			dtFormatdB = new SimpleDateFormat(DATE_FORMAT);
			try {
				return dtFormatdB.format(currDate);
			} catch (Exception ex) {
			}
		}
		return null;
	}

	/**
	 * �õ���ʽ�����ʱ�䣬��ʽΪyyyy-MM-dd HH:mm:ss����2006-02-15 15:23:45
	 * 
	 * @param currDate
	 *            Ҫ��ʽ����ʱ��
	 * @see #getFormatDateTime(java.util.Date, String)
	 * @return String ���ظ�ʽ�����ʱ�䣬Ĭ�ϸ�ʽΪyyyy-MM-dd HH:mm:ss����2006-02-15 15:23:45
	 */
	public static String getFormatDateTime(java.util.Date currDate) {
		return getFormatDateTime(currDate, TIME_FORMAT);
	}

	/**
	 * �õ���ʽ�����ʱ�䣬��ʽΪyyyy-MM-dd HH:mm:ss����2006-02-15 15:23:45
	 * 
	 * @param currDate
	 *            Ҫ��ʽ����ʱ��
	 * @see #getFormatDateTime(String)
	 * @return Date ���ظ�ʽ�����ʱ�䣬Ĭ�ϸ�ʽΪyyyy-MM-dd HH:mm:ss����2006-02-15 15:23:45
	 */
	public static Date getFormatDateTimeToTime(java.util.Date currDate) {
		return getFormatDateTime(getFormatDateTime(currDate));
	}

	/**
	 * �õ���ʽ�����ʱ�䣬��ʽΪyyyy-MM-dd HH:mm:ss����2006-02-15 15:23:45
	 * 
	 * @param currDate
	 *            Ҫ��ʽ����ʱ��
	 * @see #getFormatDateTime(String, String)
	 * @return Date ���ظ�ʽ�����ʱ�䣬Ĭ�ϸ�ʽΪyyyy-MM-dd HH:mm:ss����2006-02-15 15:23:45
	 */
	public static Date getFormatDateTime(String currDate) {
		return getFormatDateTime(currDate, TIME_FORMAT);
	}

	/**
	 * �õ���ʽ�����ʱ�䣬��ʽΪyyyy��MM��dd�� HH:mm:ss����2006��02��15�� 15:23:45
	 * 
	 * @param currDate
	 *            Ҫ��ʽ����ʱ��
	 * @see #getFormatDateTime(java.util.Date, String)
	 * @return String ���ظ�ʽ�����ʱ�䣬Ĭ�ϸ�ʽΪyyyy��MM��dd�� HH:mm:ss����2006��02��15�� 15:23:45
	 */
	public static String getFormatDateTime_CN(java.util.Date currDate) {
		return getFormatDateTime(currDate, TIME_FORMAT_CN);
	}

	/**
	 * �õ���ʽ�����ʱ�䣬��ʽΪyyyy��MM��dd�� HH:mm:ss����2006��02��15�� 15:23:45
	 * 
	 * @param currDate
	 *            Ҫ��ʽ����ʱ��
	 * @see #getFormatDateTime_CN(String)
	 * @return Date ���ظ�ʽ�����ʱ�䣬Ĭ�ϸ�ʽΪyyyy��MM��dd�� HH:mm:ss����2006��02��15�� 15:23:45
	 */
	public static Date getFormatDateTimeToTime_CN(java.util.Date currDate) {
		return getFormatDateTime_CN(getFormatDateTime_CN(currDate));
	}

	/**
	 * �õ���ʽ�����ʱ�䣬��ʽΪyyyy��MM��dd�� HH:mm:ss����2006��02��15�� 15:23:45
	 * 
	 * @param currDate
	 *            Ҫ��ʽ����ʱ��
	 * @see #getFormatDateTime(String, String)
	 * @return Date ���ظ�ʽ�����ʱ�䣬Ĭ�ϸ�ʽΪyyyy��MM��dd�� HH:mm:ss����2006��02��15�� 15:23:45
	 */
	public static Date getFormatDateTime_CN(String currDate) {
		return getFormatDateTime(currDate, TIME_FORMAT_CN);
	}

	/**
	 * ���ݸ�ʽ�õ���ʽ�����ʱ��
	 * 
	 * @param currDate
	 *            Ҫ��ʽ����ʱ��
	 * @param format
	 *            ʱ���ʽ����yyyy-MM-dd HH:mm:ss
	 * @see java.text.SimpleDateFormat#format(java.util.Date)
	 * @return String ���ظ�ʽ�����ʱ�䣬��ʽ�ɲ���<code>format</code>���壬��yyyy-MM-dd HH:mm:ss
	 */
	public static String getFormatDateTime(java.util.Date currDate, String format) {
		if (currDate == null) {
			return "";
		}
		SimpleDateFormat dtFormatdB = null;
		try {
			dtFormatdB = new SimpleDateFormat(format);
			return dtFormatdB.format(currDate);
		} catch (Exception e) {
			dtFormatdB = new SimpleDateFormat(TIME_FORMAT);
			try {
				return dtFormatdB.format(currDate);
			} catch (Exception ex) {
			}
		}
		return "";
	}

	/**
	 * ���ݸ�ʽ�õ���ʽ���������
	 * 
	 * @param currDate
	 *            Ҫ��ʽ��������
	 * @param format
	 *            ���ڸ�ʽ����yyyy-MM-dd
	 * @see java.text.SimpleDateFormat#parse(java.lang.String)
	 * @return Date ���ظ�ʽ��������ڣ���ʽ�ɲ���<code>format</code> ���壬��yyyy-MM-dd����2006-02-15
	 */
	public static Date getFormatDate(String currDate, String format) {
		if (currDate == null) {
			return null;
		}
		SimpleDateFormat dtFormatdB = null;
		try {
			dtFormatdB = new SimpleDateFormat(format);
			return dtFormatdB.parse(currDate);
		} catch (Exception e) {
			dtFormatdB = new SimpleDateFormat(DATE_FORMAT);
			try {
				return dtFormatdB.parse(currDate);
			} catch (Exception ex) {
			}
		}
		return null;
	}

	/**
	 * ���ݸ�ʽ�õ���ʽ�����ʱ��
	 * 
	 * @param currDate
	 *            Ҫ��ʽ����ʱ��
	 * @param format
	 *            ʱ���ʽ����yyyy-MM-dd HH:mm:ss
	 * @see java.text.SimpleDateFormat#parse(java.lang.String)
	 * @return Date ���ظ�ʽ�����ʱ�䣬��ʽ�ɲ���<code>format</code>���壬��yyyy-MM-dd HH:mm:ss
	 */
	public static Date getFormatDateTime(String currDate, String format) {
		if (currDate == null) {
			return null;
		}
		SimpleDateFormat dtFormatdB = null;
		try {
			dtFormatdB = new SimpleDateFormat(format);
			return dtFormatdB.parse(currDate);
		} catch (Exception e) {
			dtFormatdB = new SimpleDateFormat(TIME_FORMAT);
			try {
				return dtFormatdB.parse(currDate);
			} catch (Exception ex) {
			}
		}
		return null;
	}

	/**
	 * �õ����յ�����ʱ�� �������Ϊ2007-9-1,��ô���2007-8-1
	 */
	public static String getDateBeforeMonth() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}

	/**
	 * �õ����յ�ǰ������ʱ�� ���number=2����Ϊ2007-9-1,��ô���2007-7-1
	 */
	public static String getDateBeforeMonth(int number) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -number);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}

	public static long getDaysOfDates(Date first, Date second) {
		Date d1 = getFormatDateTime(getFormatDate(first), DATE_FORMAT);
		Date d2 = getFormatDateTime(getFormatDate(second), DATE_FORMAT);

		long mils = d1.getTime() - d2.getTime();

		return mils / (TIME_DAY_MILLISECOND);
	}

	/**
	 * �������Date������֮��������������2������1����
	 * 
	 * @param Date
	 *            first, Date second
	 * @return int ��������
	 */
	public static int getDaysBetweenDates(Date first, Date second) {
		Date d1 = getFormatDateTime(getFormatDate(first), DATE_FORMAT);
		Date d2 = getFormatDateTime(getFormatDate(second), DATE_FORMAT);

		Long mils = (d2.getTime() - d1.getTime()) / (TIME_DAY_MILLISECOND);

		return mils.intValue();
	}

	/**
	 * �������String������֮��������������2������1����
	 * 
	 * @param String
	 *            first, String second
	 * @return int ��������
	 */
	public static int getDaysBetweenDates(String first, String second) {
		Date d1 = getFormatDateTime(first, DATE_FORMAT);
		Date d2 = getFormatDateTime(second, DATE_FORMAT);

		Long mils = (d2.getTime() - d1.getTime()) / (TIME_DAY_MILLISECOND);

		return mils.intValue();
	}

	/**
	 * @author lenghao
	 * @createTime 2008-8-5 ����01:57:09
	 * @param first
	 * @param second
	 * @return ��ȡ����Date֮����������б�
	 */
	public static List<Date> getDaysListBetweenDates(Date first, Date second) {
		List<Date> dateList = new ArrayList<Date>();
		Date d1 = getFormatDateTime(getFormatDate(first), DATE_FORMAT);
		Date d2 = getFormatDateTime(getFormatDate(second), DATE_FORMAT);
		if (d1.compareTo(d2) > 0) {
			return dateList;
		}
		do {
			dateList.add(d1);
			d1 = getDateBeforeOrAfter(d1, 1);
		} while (d1.compareTo(d2) <= 0);
		return dateList;
	}

	/**
		 *
		 *
		 */
	public static String getDateBeforeDay() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}

	/**
	 * �õ���ʽ����ĵ�ǰϵͳ���ڣ���ʽΪyyyy-MM-dd����2006-02-15
	 * 
	 * @see #getFormatDate(java.util.Date)
	 * @return String ���ظ�ʽ����ĵ�ǰ������ϵͳ���ڣ���ʽΪyyyy-MM-dd����2006-02-15
	 */
	public static String getCurrDateStr() {
		return getFormatDate(getCurrDate());
	}

	/**
	 * �õ���ʽ����ĵ�ǰϵͳʱ�䣬��ʽΪyyyy-MM-dd HH:mm:ss����2006-02-15 15:23:45
	 * 
	 * @see #getFormatDateTime(java.util.Date)
	 * @return String ���ظ�ʽ����ĵ�ǰ������ϵͳʱ�䣬��ʽΪyyyy-MM-dd HH:mm:ss����2006-02-15 15:23:45
	 */
	public static String getCurrDateTimeStr() {
		return getFormatDateTime(getCurrDate());
	}

	/**
	 * �õ���ʽ����ĵ�ǰϵͳ���ڣ���ʽΪyyyy��MM��dd�գ���2006��02��15��
	 * 
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String ���ص�ǰ������ϵͳ���ڣ���ʽΪyyyy��MM��dd�գ���2006��02��15��
	 */
	public static String getCurrDateStr_CN() {
		return getFormatDate(getCurrDate(), DATE_FORMAT_CN);
	}

	/**
	 * �õ���ʽ����ĵ�ǰϵͳʱ�䣬��ʽΪyyyy��MM��dd�� HH:mm:ss����2006��02��15�� 15:23:45
	 * 
	 * @see #getFormatDateTime(java.util.Date, String)
	 * @return String ���ظ�ʽ����ĵ�ǰ������ϵͳʱ�䣬��ʽΪyyyy��MM��dd�� HH:mm:ss����2006��02��15�� 15:23:45
	 */
	public static String getCurrDateTimeStr_CN() {
		return getFormatDateTime(getCurrDate(), TIME_FORMAT_CN);
	}

	/**
	 * �õ�ϵͳ��ǰ���ڵ�ǰ���ߺ���
	 * 
	 * @param iDate
	 *            ���Ҫ���ǰ�������ڣ��ò���Ϊ������ ���Ҫ��ú������ڣ��ò���Ϊ����
	 * @see java.util.Calendar#add(int, int)
	 * @return Date ����ϵͳ��ǰ���ڵ�ǰ���ߺ���
	 */
	public static Date getDateBeforeOrAfter(int iDate) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, iDate);
		return cal.getTime();
	}

	/**
	 * �õ����ڵ�ǰ���ߺ���
	 * 
	 * @param iDate
	 *            ���Ҫ���ǰ�������ڣ��ò���Ϊ������ ���Ҫ��ú������ڣ��ò���Ϊ����
	 * @see java.util.Calendar#add(int, int)
	 * @return Date ���ز���<code>curDate</code>�������ڵ�ǰ���ߺ���
	 */
	public static Date getDateBeforeOrAfter(Date curDate, int iDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(curDate);
		cal.add(Calendar.DAY_OF_MONTH, iDate);
		return cal.getTime();
	}

	/**
	 * �õ���ʽ������·ݣ���ʽΪyyyy-MM����2006-02
	 * 
	 * @param currDate
	 *            Ҫ��ʽ��������
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String ���ظ�ʽ������·ݣ���ʽΪyyyy-MM����2006-02
	 */
	public static String getFormatMonth(java.util.Date currDate) {
		return getFormatDate(currDate, MONTH_FORMAT);
	}

	/**
	 * �õ���ʽ������գ���ʽΪyyyyMMdd����20060210
	 * 
	 * @param currDate
	 *            Ҫ��ʽ��������
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String ���ظ�ʽ������գ���ʽΪyyyyMMdd����20060210
	 */
	public static String getFormatDay(java.util.Date currDate) {
		return getFormatDate(currDate, DAY_FORMAT);
	}

	/**
	 * �õ���ʽ����ĵ��µ�һ�죬��ʽΪyyyy-MM-dd����2006-02-01
	 * 
	 * @param currDate
	 *            Ҫ��ʽ��������
	 * @see java.util.Calendar#getMinimum(int)
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String ���ظ�ʽ����ĵ��µ�һ�죬��ʽΪyyyy-MM-dd����2006-02-01
	 */
	public static String getFirstDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		int firstDay = cal.getMinimum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, firstDay);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}

	/**
	 * �õ���ʽ��������µ�һ�죬��ʽΪyyyy-MM-dd����2006-02-01
	 * 
	 * @param currDate
	 *            Ҫ��ʽ��������
	 * @see java.util.Calendar#getMinimum(int)
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String ���ظ�ʽ��������µ�һ�죬��ʽΪyyyy-MM-dd����2006-02-01
	 */
	public static String getFirstDayOfNextMonth() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, +1);
		int firstDay = cal.getMinimum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, firstDay);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}

	/**
	 * �õ���ʽ����ĵ��µ�һ�죬��ʽΪyyyy-MM-dd����2006-02-01
	 * 
	 * @param currDate
	 *            Ҫ��ʽ��������
	 * @see java.util.Calendar#getMinimum(int)
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String ���ظ�ʽ����ĵ��µ�һ�죬��ʽΪyyyy-MM-dd����2006-02-01
	 */
	public static String getFirstDayOfMonth(Date currDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currDate);
		int firstDay = cal.getMinimum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, firstDay);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}

	/**
	 * �õ���ʽ����ĵ������һ�죬��ʽΪyyyy-MM-dd����2006-02-28
	 * 
	 * @param currDate
	 *            Ҫ��ʽ��������
	 * @see java.util.Calendar#getMinimum(int)
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String ���ظ�ʽ����ĵ������һ�죬��ʽΪyyyy-MM-dd����2006-02-28
	 */
	public static String getLastDayOfMonth(Date currDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currDate);
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}

	/**
	 * �õ���ʽ����ĵ������һ�죬��ʽΪyyyy-MM-dd����2006-02-28
	 * 
	 * @param currDate
	 *            Ҫ��ʽ��������
	 * @see java.util.Calendar#getMinimum(int)
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String ���ظ�ʽ����ĵ������һ�죬��ʽΪyyyy-MM-dd����2006-02-28
	 */
	public static String getLastDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}

	/**
	 * �õ����ڵ�ǰ���ߺ�Сʱ
	 * 
	 * @param iHour
	 *            ���Ҫ���ǰ��Сʱ���ڣ��ò���Ϊ������ ���Ҫ��ú�Сʱ���ڣ��ò���Ϊ����
	 * @see java.util.Calendar#add(int, int)
	 * @return Date ���ز���<code>curDate</code>�������ڵ�ǰ���ߺ�Сʱ
	 */
	public static Date getDateBeforeOrAfterHours(Date curDate, int iHour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(curDate);
		cal.add(Calendar.HOUR_OF_DAY, iHour);
		return cal.getTime();
	}

	/**
	 * �ж������Ƿ��ڵ�ǰ����
	 * 
	 * @param curDate
	 * @param compareDate
	 * @return
	 */
	public static boolean isSameWeek(Date curDate, Date compareDate) {
		if (curDate == null || compareDate == null) {
			return false;
		}

		Calendar calSun = Calendar.getInstance();
		calSun.setTime(getFormatDateToDate(curDate));
		calSun.set(Calendar.DAY_OF_WEEK, 1);

		Calendar calNext = Calendar.getInstance();
		calNext.setTime(calSun.getTime());
		calNext.add(Calendar.DATE, 7);

		Calendar calComp = Calendar.getInstance();
		calComp.setTime(compareDate);
		if (calComp.after(calSun) && calComp.before(calNext)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ʱ���ѯʱ,����ʱ��� 23:59:59
	 */
	public static String addDateEndfix(String datestring) {
		if ((datestring == null) || datestring.equals("")) {
			return null;
		}
		return datestring + " 23:59:59";
	}

	/**
	 * ���ظ�ʽ��������
	 * 
	 * @param datePre
	 *            ��ʽ"yyyy-MM-dd 23:59:59";
	 * @return
	 */
	public static Date getFormatDateEndfix(String dateStr) {
		dateStr = addDateEndfix(dateStr);
		return getFormatDateTime(dateStr);
	}

	/**
	 * ���ظ�ʽ��������
	 * 
	 * @param datePre
	 *            ��ʽ"yyyy-MM-dd HH:mm:ss";
	 * @return
	 */
	public static Date formatEndTime(String datePre) {
		if (datePre == null)
			return null;
		String dateStr = addDateEndfix(datePre);
		return getFormatDateTime(dateStr);
	}

	// date1����compday�����Ժ�������뵱ǰʱ��Ƚϣ�������ڵ�ǰʱ�䷵��true������false
	public static Boolean compareDay(Date date1, int compday) {
		if (date1 == null)
			return false;
		Date dateComp = getDateBeforeOrAfter(date1, compday);
		Date nowdate = new Date();
		if (dateComp.after(nowdate))
			return true;
		else
			return false;
	}

	/**
	 * ����ʱ�θ�ʽת�������������48λ��01�������������²����� <li>
	 * 1.�Ƚ�������ÿ��0�������0��ÿ��1���2��1���γ�һ��96λ�Ķ����ƴ���</li> <li>
	 * 2.��������96λ�Ķ����ƴ��ֳ�3�飬ÿ��32λ��</li> <li>3.��ÿ��32λ�Ķ����ƴ�ת����һ��8λ��16���ƴ���</li> <li>4.��3��8λ��16���ƴ��ϲ���һ�������м���","�ָ</li>
	 * 
	 * @param timespan
	 *            һ��48λ�Ķ����ƴ����磺"011111111011111111111111111111111111111111111110"
	 * @return һ��16���ƴ���ÿλ����","�ָ�磺"3fffcfff,ffffffff,fffffffc"
	 */
	public static String convertBinaryTime2Hex(String timespan) {
		if (timespan == null || timespan.equals("")) {
			return "";
		}

		String ret = "";
		String tmp = "";
		for (int i = 0; i < timespan.length(); i++) {
			tmp += timespan.charAt(i);
			tmp += timespan.charAt(i);
			// tmp += i;
			if ((i + 1) % 16 == 0) {
				if (!ret.equals("")) {
					ret += ",";
				}
				Long t = Long.parseLong(tmp, 2);
				String hexStr = Long.toHexString(t);
				if (hexStr.length() < 8) {
					int length = hexStr.length();
					for (int n = 0; n < 8 - length; n++) {
						hexStr = "0" + hexStr;
					}
				}

				ret += hexStr;
				tmp = "";
			}
		}

		return ret;
	}

	/**
	 * ����ʱ�θ�ʽת�����������26λ��2���ƴ�ת����48λ�Ķ����ƴ���
	 * 
	 * @param timespan
	 *            һ��16���ƴ���ÿλ����","�ָ�磺"3fffcfff,ffffffff,fffffffc"
	 * @return һ��48λ�Ķ����ƴ����磺"011111111011111111111111111111111111111111111110"
	 */
	public static String convertHexTime2Binary(String timespan) {
		if (timespan == null || timespan.equals("")) {
			return "";
		}

		String tmp = "";
		String ret = "";
		String[] strArr = timespan.split(",");
		for (int i = 0; i < strArr.length; i++) {
			String binStr = Long.toBinaryString(Long.parseLong(strArr[i], 16));
			if (binStr.length() < 32) {
				int length = binStr.length();
				for (int n = 0; n < 32 - length; n++) {
					binStr = "0" + binStr;
				}
			}
			tmp += binStr;
		}

		for (int i = 0; i < 48; i++) {
			ret += tmp.charAt(i * 2);
		}

		return ret;
	}

	/**
	 * ����ʱ�θ�ʽת�����������32λ��10���ƴ�ת����48λ�Ķ����ƴ���
	 * 
	 * @param timespan
	 *            һ��16���ƴ���ÿλ����","�ָ�磺"1234567890,1234567890,1234567890c"
	 * @return һ��48λ�Ķ����ƴ����磺"011111111011111111111111111111111111111111111110"
	 */
	public static String convertDecTime2Binary(String timespan) {
		if (timespan == null || timespan.equals("")) {
			return "";
		}

		String tmp = "";
		String ret = "";
		String[] strArr = timespan.split(",");
		for (int i = 0; i < strArr.length; i++) {
			String binStr = Long.toBinaryString(Long.parseLong(strArr[i], 10));
			if (binStr.length() < 32) {
				int length = binStr.length();
				for (int n = 0; n < 32 - length; n++) {
					binStr = "0" + binStr;
				}
			}
			tmp += binStr;
		}

		for (int i = 0; i < 48; i++) {
			ret += tmp.charAt(i * 2);
		}

		return ret;
	}

	/**
	 * ����ʱ�θ�ʽת�������������48λ��01�������������²����� <li>
	 * 1.�Ƚ�������ÿ��0�������0��ÿ��1���2��1���γ�һ��96λ�Ķ����ƴ���</li> <li>
	 * 2.��������96λ�Ķ����ƴ��ֳ�3�飬ÿ��32λ��</li> <li>3.��ÿ��32λ�Ķ����ƴ�ת����һ��10λ��10���ƴ���</li> <li>4.��3��8λ��16���ƴ��ϲ���һ�������м���","�ָ</li>
	 * 
	 * @param timespan
	 *            һ��48λ�Ķ����ƴ����磺"011111111011111111111111111111111111111111111110"
	 * @return һ��16���ƴ���ÿλ����","�ָ�磺"1234567890,1234567890,1234567890"
	 */
	public static String convertBinaryTime2Dec(String timespan) {
		if (timespan == null || timespan.equals("")) {
			return "";
		}

		String ret = "";
		String tmp = "";
		for (int i = 0; i < timespan.length(); i++) {
			tmp += timespan.charAt(i);
			tmp += timespan.charAt(i);
			// tmp += i;
			if ((i + 1) % 16 == 0) {
				if (!ret.equals("")) {
					ret += ",";
				}
				Long t = Long.parseLong(tmp, 2);
				String decStr = Long.toString(t);
				if (decStr.length() < 10) {
					int length = decStr.length();
					for (int n = 0; n < 10 - length; n++) {
						decStr = "0" + decStr;
					}
				}

				ret += decStr;
				tmp = "";
			}
		}

		return ret;
	}

	/**
	 * ����ָ������+addMonth��+15�� ���ظ�ʽ"2008-02-15"
	 * 
	 * @param date
	 * @param addMonth
	 * @param monthDay
	 * @return
	 */
	public static String genericSpecdate(Date date, int addMonth, int monthDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, addMonth);
		cal.set(Calendar.DAY_OF_MONTH, monthDay);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}

	/**
	 * ����Խ���Ϊ��λ��������ǰ���Ժ�����ڵı�׼��ʽ"Wed Feb 20 00:00:00 CST 2008"����0��0��0�롣
	 * 
	 * @param idx
	 * @return
	 */
	public static Date getDateBeforeOrAfterV2(int idx) {
		return getDateBeforeOrAfter(getFormatDateToDate(getCurrDate()), idx);
	}

	/**
	 * ��ø���ʱ����������ǰ���Ժ�����ڵı�׼��ʽ��
	 * 
	 * @param curDate
	 * @param seconds
	 * @return curDate
	 */
	public static Date getSpecifiedDateTimeBySeconds(Date curDate, int seconds) {
		long time = (curDate.getTime() / 1000) + seconds;
		curDate.setTime(time * 1000);
		return curDate;
	}

	/**
	 * ��ø������ڵ���23��59��59��ı�׼��ʽ��
	 * 
	 * @param curDate
	 * @return curDate
	 */
	public static Date getSpecifiedDateTime_235959(Date curDate) {
		return getSpecifiedDateTimeBySeconds(getFormatDateToDate(curDate), 24 * 60 * 60 - 1);
	}

	public static String getSpecifiedDateTime_month(Date curDate) {
		return getFormatDateTime(curDate, "MM.dd");
	}

	// change by bbq
	public static final String dtSimple = "yyyy-MM-dd";

	private static final Object[] Date = null;

	/**
	 * alahan add 20050825 ��ȡ����ʱ����������
	 * 
	 * @param dt
	 *            �������ڣ�����Ϊ��
	 * @param diff
	 *            ��Ҫ��ȡ���diff������� ���Ϊ����ȡ�Ժ�����ڣ�����ʱ����ǰ��
	 * @return
	 */
	public static String getDiffStringDate(Date dt, int diff) {
		Calendar ca = Calendar.getInstance();

		if (dt == null) {
			ca.setTime(new Date());
		} else {
			ca.setTime(dt);
		}

		ca.add(Calendar.DATE, diff);
		return dtSimpleFormat(ca.getTime());
	}

	/**
	 * yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static final String dtSimpleFormat(Date date) {
		if (date == null) {
			return "";
		}

		return getFormat(dtSimple).format(date);
	}

	// SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static final DateFormat getFormat(String format) {
		return new SimpleDateFormat(format);
	}

	/**
	 * ȡ�ö�������м�����������
	 * 
	 * @author Alvise
	 * @param startDateAndEndDate
	 * @return
	 */
	public static int maxContinuousDays(Date[][] startDateAndEndDate) {
		// ð������
		for (int i = 0; i < startDateAndEndDate.length - 1; i++) {
			for (int j = 0; j < startDateAndEndDate.length - i - 1; j++) {
				if (DateTimeUtil.getDaysBetweenDates(startDateAndEndDate[j + 1][0], startDateAndEndDate[j][0]) > 0) {
					Date[] tempDate = startDateAndEndDate[j];
					startDateAndEndDate[j] = startDateAndEndDate[j + 1];
					startDateAndEndDate[j + 1] = tempDate;
				}
			}
		}

		// for (int i = 0; i < startDateAndEndDate.length; i++) {
		// if (startDateAndEndDate[i][0] == null)
		// break;
		// System.out.println(DateTimeUtil.getFormatDate(
		// startDateAndEndDate[i][0]) + ","
		// + DateTimeUtil.getFormatDate(startDateAndEndDate[i][1]));
		// }
		//
		// System.out.println(
		// "===========================================");

		// �ϲ�������ʱ���
		int j = 0;
		Date[][] startDateAndEndDateNew = new Date[startDateAndEndDate.length][2];
		for (int i = 0; i < startDateAndEndDateNew.length; i++) {
			if (j >= startDateAndEndDate.length)
				break;

			startDateAndEndDateNew[i] = startDateAndEndDate[j];
			j++;
			while (j < startDateAndEndDate.length) {
				if (DateTimeUtil.getDaysBetweenDates(startDateAndEndDateNew[i][1], startDateAndEndDate[j][0]) > 0) {
					break;
				} else if (DateTimeUtil.getDaysBetweenDates(startDateAndEndDateNew[i][1], startDateAndEndDate[j][1]) > 0) {
					startDateAndEndDateNew[i][1] = startDateAndEndDate[j][1];
					j++;
				} else if (DateTimeUtil.getDaysBetweenDates(startDateAndEndDateNew[i][1], startDateAndEndDate[j][1]) <= 0) {
					j++;
				}

			}
		}

		// for (int i = 0; i < startDateAndEndDateNew.length; i++) {
		// if (startDateAndEndDateNew[i][0] == null)
		// break;
		// System.out.println(DateTimeUtil.getFormatDate(startDateAndEndDateNew[i][0]) + ","
		// + DateTimeUtil.getFormatDate(startDateAndEndDateNew[i][1]));
		// }

		// ѡ������
		int maxDays = 0;
		for (int i = 0; i < startDateAndEndDateNew.length - 1; i++) {
			Date curEndDate = startDateAndEndDateNew[i][1];
			Date nextStartDate = startDateAndEndDateNew[i + 1][0];
			if (curEndDate == null || nextStartDate == null) {
				break;
			}

			int temDays = DateTimeUtil.getDaysBetweenDates(curEndDate, nextStartDate);
			if (temDays > maxDays) {
				maxDays = temDays;
			}
		}
		return maxDays;
	}

	/**
	 * ȡ�ö�������м�����������,����Ĳ������� ","��";"�ָ���ַ��ַ������� "2008-08-03,2008-08-04;"
	 * 
	 * @author Alvise
	 * @param startDateAndEndDate
	 * @return
	 */
	public static int maxContinuousDays(String dateStr) {
		String[] seDate = dateStr.split(";");
		Date[][] startDateAndEndDate = new Date[seDate.length][2];

		for (int i = 0; i < seDate.length; i++) {
			String[] tempDate = seDate[i].split(",");
			startDateAndEndDate[i][0] = DateTimeUtil.getFormatDate(tempDate[0]);
			startDateAndEndDate[i][1] = DateTimeUtil.getFormatDate(tempDate[1]);
		}

		return maxContinuousDays(startDateAndEndDate);

	}

	/**
	 * �ж�ʱ���1��ʱ���2�Ƿ��н���
	 * 
	 * @param begintimeOne
	 * @param endtimeOne
	 * @param begintimeTwo
	 * @param endtimeTwo
	 * @return true:�н���,false:û�н���
	 */
	public static boolean isConfilct(String begintimeOne, String endtimeOne, String begintimeTwo, String endtimeTwo) {
		Date beginOne = getFormatDate(begintimeOne);
		Date endOne = getFormatDate(endtimeOne);
		Date beginTwo = getFormatDate(begintimeTwo);
		Date endTwo = getFormatDate(endtimeTwo);
		if ((beginOne.compareTo(beginTwo) <= 0 && endOne.compareTo(beginTwo) >= 0) || (beginOne.compareTo(endTwo) <= 0 && endOne.compareTo(endTwo) >= 0)
				|| (beginTwo.compareTo(beginOne) <= 0 && endTwo.compareTo(beginOne) >= 0) || (beginTwo.compareTo(endOne) <= 0 && endTwo.compareTo(endOne) >= 0)) {
			return true;
		}
		return false;
	}

	/**
	 * ȡ������ɹ���ʱ��
	 * 
	 * @param busytimes
	 *            ������ʱ��,��ʽΪ2008-08-06,2008-08-06;2008-08-9,2008-08-12;2008-08-14,2008-08-22;2008-09-04,2008-09-04
	 * @param days
	 *            ����ʱ��
	 * @return ��߿ɹ���ʱ��
	 */
	public static String getCansellTime(String busytimes, int days) {
		Map<String, Integer> dayMap = new HashMap<String, Integer>();
		String[] busytimeArr = StringUtils.split(busytimes, ";");
		for (int i = 0; i < busytimeArr.length; i++) {
			String[] time = StringUtils.split(busytimeArr[i], ",");
			Date d1 = getFormatDateTime(time[0], DATE_FORMAT);
			Date d2 = getFormatDateTime(time[1], DATE_FORMAT);
			while (d1.compareTo(d2) <= 0) {
				dayMap.put(getFormatDate(d1), null);
				d1 = getDateBeforeOrAfter(d1, 1);
			}
		}

		Date lastDate = getFormatDateTime(getFormatDate(getDateBeforeOrAfter(29)), DATE_FORMAT);
		Date beginDate = getFormatDateTime(getFormatDate(getDateBeforeOrAfter(2)), DATE_FORMAT);
		Date endDate = getDateBeforeOrAfter(beginDate, days - 1);

		while (beginDate.compareTo(lastDate) <= 0) {
			boolean conflict = false;
			List<Date> daysList = getDaysListBetweenDates(beginDate, endDate);
			for (Date d : daysList) {
				if (dayMap.containsKey(getFormatDate(d))) {
					conflict = true;
					break;
				}
			}
			if (!conflict) {
				break;
			}
			beginDate = getDateBeforeOrAfter(beginDate, 1);
			endDate = getDateBeforeOrAfter(beginDate, days - 1);
		}
		return getFormatDate(beginDate);
	}

	public static void main(String[] args) {
		// System.out
		// .print(maxContinuousDays("2008-08-06,2008-08-06;2008-08-06,2008-08-12;2008-09-04,2008-09-04"));
		System.out.println(getCansellTime("", 7));
	}
}
