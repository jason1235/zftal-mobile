package com.zfsoft.hrm.schedule.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.Trigger;
 
public class QuartzTriggerUtil {
	private static String expressionSecend(int cycleNum, Date startDate,
			Date endDate) throws ParseException {
		return "*/" + cycleNum + " * * * * ?";
	}

	private static String expressionMinute(int cycleNum, Date startDate,
			Date endDate) throws ParseException {
		int startSecond = 0;
		if (startDate != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			startSecond = cal.get(Calendar.SECOND);
		}
		return startSecond + " " + "*/" + cycleNum + " * * * ?";
	}

	private static String expressionHour(int cycleNum, Date startDate,
			Date endDate) throws ParseException {
		int startSecond = 0;
		int startMinute = 0;
		if (startDate != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			startSecond = cal.get(Calendar.SECOND);
			startMinute = cal.get(Calendar.MINUTE);
		}
		return startSecond + " " + startMinute + " " + "*/"
				+ cycleNum + " * * ?";
	}

	private static String expressionDate(int cycleNum, Date startDate,
			Date endDate) throws ParseException {
		int startSecond = 0;
		int startMinute = 0;
		int startHour = 0;
		if (startDate != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			startSecond = cal.get(Calendar.SECOND);
			startMinute = cal.get(Calendar.MINUTE);
			startHour = cal.get(Calendar.HOUR_OF_DAY);
		}
		return startSecond + " " + startMinute + " " + startHour + " "
				+ "*/" + cycleNum + " * ?";
	}

	private static String expressionMonth(int cycleNum, Date startDate,
			Date endDate) throws ParseException {
		int startSecond = 0;
		int startMinute = 0;
		int startHour = 0;
		int startDay = 0;
		if (startDate != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			startSecond = cal.get(Calendar.SECOND);
			startMinute = cal.get(Calendar.MINUTE);
			startHour = cal.get(Calendar.HOUR);
			startDay = cal.get(Calendar.DATE);
		}
		return startSecond + " " + startMinute + " " + startHour + " "
				+ startDay + " " + "*/" + cycleNum + " ?";
	}

	private static String expressionYear(int cycleNum, Date startDate,
			Date endDate) throws ParseException {
		int startSecond = 0;
		int startMinute = 0;
		int startHour = 0;
		int startDay = 0;
		int startMonth = 0;
		if (startDate != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			startSecond = cal.get(Calendar.SECOND);
			startMinute = cal.get(Calendar.MINUTE);
			startHour = cal.get(Calendar.HOUR);
			startDay = cal.get(Calendar.DATE);
			startMonth = cal.get(Calendar.MONTH)+1;
		}
		return startSecond + " " + startMinute + " " + startHour + " "
				+ startDay + " " + startMonth + " ? " + "*/"
				+ cycleNum;
	}

	private static Trigger createTrigger(String triggerName, Date startDate,
			Date endDate, String expression) throws ParseException {
		Trigger trigger = new CronTrigger(triggerName, Scheduler.DEFAULT_GROUP,
				expression);
		// 设置开始时间
		trigger.setStartTime(startDate);
		// 设置结束时间
		if (endDate != null)
			trigger.setEndTime(endDate);
		return trigger;
	}

	/**
	 * 获取触发器
	 * @param triggerName
	 * @param cycleType CycleType.SECOND按秒为周期 CycleType.MINUTE 按分为周期 CycleType.HOUR 按时为周期  CycleType.DATE 按天为周期 CycleType.MONTH 按月为周期 CycleType.YEAR按年为周期
	 * @param cycleNum s:0-59 m:0-59 h:0-23 d:1-28 M:1-12
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	public static Trigger getMyTrigger(String triggerName, int cycleType,
			int cycleNum, Date startDate, Date endDate) throws ParseException {
		switch (cycleType) {
		case CycleType.SECOND:
			return createTrigger(triggerName, startDate, endDate,
					expressionSecend(cycleNum, startDate, endDate));
		case CycleType.MINUTE:
			return createTrigger(triggerName, startDate, endDate,
					expressionMinute(cycleNum, startDate, endDate));
		case CycleType.HOUR:
			return createTrigger(triggerName, startDate, endDate,
					expressionHour(cycleNum, startDate, endDate));
		case CycleType.DATE:
			return createTrigger(triggerName, startDate, endDate,
					expressionDate(cycleNum, startDate, endDate));
		case CycleType.MONTH:
			return createTrigger(triggerName, startDate, endDate,
					expressionMonth(cycleNum, startDate, endDate));
		case CycleType.YEAR:
			return createTrigger(triggerName, startDate, endDate,
					expressionYear(cycleNum, startDate, endDate));
		}

		return null;
	}

	public static Trigger getMySecondTrigger(String triggerName, int cycleNum,
			Date startDate, Date endDate) throws ParseException {
		return createTrigger(triggerName, startDate, endDate,
				expressionSecend(cycleNum, startDate, endDate));
	}

	public static Trigger getMyMinuteTrigger(String triggerName, int cycleNum,
			Date startDate, Date endDate) throws ParseException {
		return createTrigger(triggerName, startDate, endDate,
				expressionMinute(cycleNum, startDate, endDate));
	}

	public static Trigger getMyHourTrigger(String triggerName, int cycleNum,
			Date startDate, Date endDate) throws ParseException {
		return createTrigger(triggerName, startDate, endDate,
				expressionHour(cycleNum, startDate, endDate));
	}

	public static Trigger getMyDateTrigger(String triggerName, int cycleNum,
			Date startDate, Date endDate) throws ParseException {
		return createTrigger(triggerName, startDate, endDate,
				expressionDate(cycleNum, startDate, endDate));
	}

	public static Trigger getMyMonthTrigger(String triggerName, int cycleNum,
			Date startDate, Date endDate) throws ParseException {
		return createTrigger(triggerName, startDate, endDate,
				expressionMonth(cycleNum, startDate, endDate));
	}

	public static Trigger getMyYearTrigger(String triggerName, int cycleNum,
			Date startDate, Date endDate) throws ParseException {
		return createTrigger(triggerName, startDate, endDate,
				expressionYear(cycleNum, startDate, endDate));
	}

}
