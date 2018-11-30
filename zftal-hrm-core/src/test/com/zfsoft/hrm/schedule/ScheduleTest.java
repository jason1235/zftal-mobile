package com.zfsoft.hrm.schedule;

import java.text.ParseException;
import java.util.Calendar;

import org.junit.Test;
import org.quartz.SchedulerException;
import org.springframework.test.annotation.Rollback;

import base.BaseTxTestCase;

import com.zfsoft.hrm.schedule.util.CycleType;
import com.zfsoft.hrm.schedule.util.QuartzTriggerUtil;

public class ScheduleTest extends BaseTxTestCase {
	//@Test
	//@Rollback(false)
	public void add() {
		ScheduleControlService scheduleControlService = (ScheduleControlService) this.applicationContext
				.getBean("scheduleControlService");
		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.MINUTE, 1);
		try {
			scheduleControlService.addTriggerToJob("job", QuartzTriggerUtil
					.getMyTrigger("test", CycleType.SECOND, 5, cal.getTime(),
							null), TestJobDetailServiceForDyQuartz.class);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	@Test
	@Rollback(false)
	public void remove() {
		ScheduleControlService scheduleControlService = (ScheduleControlService) this.applicationContext
				.getBean("scheduleControlService");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 1);
		try {
			scheduleControlService.removeJob("job");
			scheduleControlService.removeJob("RETIRE_SCAN");
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}
