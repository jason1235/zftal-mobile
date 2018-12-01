package com.zfsoft.framework.util;

import java.util.List;
import java.util.UUID;

import org.quartz.Calendar;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import com.zfsoft.hrm.schedule.ScheduleControlService;
import com.zfsoft.sjzx.jhpz.dsqpzb.model.Dsqpzb;
import com.zfsoft.sjzx.jhpz.dsqpzb.service.DsqpzbService;

/**
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company: zfsoft.com
 * </p>
 * 
 * @since 2012-12-11 下午4:01:40
 * @author liuchaoyong
 * @version 1.0
 */
public class JobStart implements Runnable {
	private Scheduler scheduler = null;
	
	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	public void run() {
		try {
			// 睡眠20秒
			Thread.sleep(20000);
			// 加载执行的调度
			JobTriggerBean jtb = null;
			DsqpzbService dsqpzbService = (DsqpzbService) SpringUtil
					.getBean("dsqpzbService");
			List<Dsqpzb> dsqpzbList = dsqpzbService.getRunTasks();
			if (dsqpzbList != null) {
				for (Dsqpzb dsq : dsqpzbList) {
					String jobname = dsq.getJobclassname();
					int dsqxh = dsq.getDsqxh();
					Class<?> c = getClass(jobname);
					if (c != null) {
						SimpleJobDetail jd = null;
						try {
							jd = (SimpleJobDetail) c.newInstance();
						} catch (InstantiationException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
						jtb = jd.getTrigger(dsq);
						jd.setName(dsqxh + "e");
						jd.setDsqpzb(dsq);
						jd.setGroup(Constants.JOB_GROUP);
						jd.setDescription("后台服务");
						jd.setJobClass(SimpleJob.class);
						Trigger trigger = jtb.getTrigger();
						trigger.setName(dsqxh + "e");
						trigger.setGroup(Constants.JOB_GROUP);

						Calendar cal = jtb.getCalendar();
						if (cal != null) {
							scheduler.addCalendar(cal.getDescription(), cal,
									true, true);
							trigger.setCalendarName(cal.getDescription());
						}
						scheduler.scheduleJob(jd, trigger);
					}

				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	private Class<?> getClass(String classname) {
		if (classname != null && !"".equals(classname)) {
			ClassLoader loader = getClass().getClassLoader();
			Class<?> c = null;
			try {
				c = loader.loadClass(classname);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return c;
		}
		return null;
	}
}
