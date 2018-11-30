package com.zfsoft.framework.util;

import org.quartz.Calendar;
import org.quartz.impl.calendar.BaseCalendar;

/**
 * @author  Administrator
 */
public class JobTimePointCalendar extends BaseCalendar {
	private static final long serialVersionUID = -6142576007915342588L;
	private int hour;
	private int min;
	private int sec;
	public JobTimePointCalendar() {
		super();
	}
	public JobTimePointCalendar(Calendar baseCalendar) {
		super(baseCalendar);
	}
 
	/**
	 * @return
	 * @uml.property  name="hour"
	 */
	public int getHour() {
		return hour;
	}
	/**
	 * @param hour
	 * @uml.property  name="hour"
	 */
	public void setHour(int hour) {
		this.hour = hour;
	}
	/**
	 * @return
	 * @uml.property  name="min"
	 */
	public int getMin() {
		return min;
	}
	/**
	 * @param min
	 * @uml.property  name="min"
	 */
	public void setMin(int min) {
		this.min = min;
	}
	/**
	 * @return
	 * @uml.property  name="sec"
	 */
	public int getSec() {
		return sec;
	}
	/**
	 * @param sec
	 * @uml.property  name="sec"
	 */
	public void setSec(int sec) {
		this.sec = sec;
	}
 
	public boolean isTimeIncluded(long timeStamp) {
		if (super.isTimeIncluded(timeStamp) == false) {
			return false;
		}
		java.util.Calendar cal = createJavaCalendar(timeStamp);//getJavaCalendar(timeStamp);
		int hour = cal.get(java.util.Calendar.HOUR_OF_DAY);
		int minute = cal.get(java.util.Calendar.MINUTE);
		int sec = cal.get(java.util.Calendar.SECOND);
		if(hour == this.hour && minute == this.min && this.sec == sec )
		{
			System.out.println(hour+":"+minute+":"+sec+"@first time");
			return true ;
		}
		else
			return false;
	}

/*	public long getNextIncludedTime(long timeStamp) {
		// Call base calendar implementation first
		long baseTime = super.getNextIncludedTime(timeStamp);
		if ((baseTime > 0) && (baseTime > timeStamp))
			timeStamp = baseTime; 
		java.util.Calendar cal = getEndOfDayJavaCalendar(timeStamp);
		int hour = cal.get(java.util.Calendar.HOUR_OF_DAY);
		int minute = cal.get(java.util.Calendar.MINUTE);
		System.out.println(hour+"@"+minute);
		if(hour == 14 && minute == 47 )
		{
			System.out.println(hour+"@"+minute+"first time");
			//isfirst = true;
			return timeStamp;
		}
 		while(hour == 14 && minute == 47)
	    	cal.add(java.util.Calendar.MINUTE, 1);
		//else
		//	cal.add(java.util.Calendar.MINUTE, 1);
		return cal.getTime().getTime();
	}*/
}