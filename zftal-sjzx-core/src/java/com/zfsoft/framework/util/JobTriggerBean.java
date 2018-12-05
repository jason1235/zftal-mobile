package com.zfsoft.framework.util;

import org.quartz.Trigger;
import org.quartz.impl.calendar.BaseCalendar;

/**
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2012-12-12 上午11:16:52
 * @author liuchaoyong
 * @version 1.0
 */
public class JobTriggerBean {
	private BaseCalendar calendar;
	private Trigger trigger;
	/**
	 * @return
	 * @uml.property  name="calendar"
	 */
	public BaseCalendar getCalendar() {
		return calendar;
	}
	/**
	 * @param calendar
	 * @uml.property  name="calendar"
	 */
	public void setCalendar(BaseCalendar calendar) {
		this.calendar = calendar;
	}
	/**
	 * @return
	 * @uml.property  name="trigger"
	 */
	public Trigger getTrigger() {
		return trigger;
	}
	/**
	 * @param trigger
	 * @uml.property  name="trigger"
	 */
	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}
}
