package com.zfsoft.hrm.schedule;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestJobDetailServiceForDyQuartz implements Job {
	public static int i=0;
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		i++;
		System.out.println(new Date(System.currentTimeMillis())+"     " +i);
	}
	
}
