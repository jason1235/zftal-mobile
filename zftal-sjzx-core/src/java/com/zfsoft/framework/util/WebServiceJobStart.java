package com.zfsoft.framework.util;

import java.util.List;
import java.util.UUID;

import org.quartz.Calendar;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import com.zfsoft.hrm.schedule.ScheduleControlService;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhDsqConfig;
import com.zfsoft.sjzx.jhpz.wspz.service.WsjhConfigService;


public class WebServiceJobStart implements Runnable {
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
			for (String triggerName : scheduler.getTriggerNames(Constants.JOB_GROUP)) {
				scheduler.unscheduleJob(triggerName, Constants.JOB_GROUP);
			}
			for (String jobName: scheduler.getJobNames(Constants.JOB_GROUP)) {
				scheduler.deleteJob(jobName, Constants.JOB_GROUP);
			}
			
			JobTriggerBean jtb = null;
			WsjhConfigService wsjhConfigService = (WsjhConfigService) SpringUtil
					.getBean("wsjhConfigService");
			List<WsjhDsqConfig> wdcList = wsjhConfigService.getRunTasks();
			if (wdcList != null&&wdcList.size()>0) {
				for (WsjhDsqConfig wdc : wdcList) {
					String jobname = "com.zfsoft.sjzx.jhpz.wspz.service.WsjhConfigTask";
					int dsqxh = wdc.getDsqxh();
					Class<?> c = getClass(jobname);
					if (c != null) {
						WebServiceJobDetail jd = null;
						try {
							jd = (WebServiceJobDetail) c.newInstance();
						} catch (InstantiationException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
						jtb = jd.getTrigger(wdc);
						jd.setName(dsqxh + "e");
						jd.setWdc(wdc);
						JobDataMap jobDataMap = jd.getJobDataMap();
						jobDataMap.put("wdc", wdc);
						jd.setGroup(Constants.JOB_GROUP);
						jd.setDescription("后台服务");
						jd.setJobClass(WebServiceSimpleJob.class);
						Trigger trigger = jtb.getTrigger();
						if(trigger!=null){
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
