package com.zfsoft.hrm.schedule;

import java.text.ParseException;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;


/**
 * 
 * @author 沈鲁威 Patrick Shen
 * @date 2012-7-19
 */
public class ScheduleControlService {
	
	private Scheduler localQuartzScheduler;
	
	public void setLocalQuartzScheduler(Scheduler localQuartzScheduler) {
		this.localQuartzScheduler = localQuartzScheduler;
	}
	
	/**
	 * 清空所有触发器合所有任务
	 * @throws SchedulerException
	 */
	public void clearAllTrigger() throws SchedulerException{
		for(String triggerName:localQuartzScheduler.getTriggerNames(Scheduler.DEFAULT_GROUP)){
			localQuartzScheduler.unscheduleJob(triggerName, Scheduler.DEFAULT_GROUP);
		}
		for(String jobName:localQuartzScheduler.getJobNames(Scheduler.DEFAULT_GROUP)){
			localQuartzScheduler.deleteJob(jobName, Scheduler.DEFAULT_GROUP);
		}
		
	}
	/**
	 * 暂停名称为name的触发器
	 * 
	 * @param name
	 * @throws SchedulerException
	 */
	public void pauseTrigger(String triggerName) throws SchedulerException {
		if(localQuartzScheduler.getTrigger(triggerName, Scheduler.DEFAULT_GROUP)==null)return;
		localQuartzScheduler.pauseTrigger(triggerName, Scheduler.DEFAULT_GROUP);
	}

	/**
	 * 恢复名称为name的触发器
	 * 
	 * @param name
	 * @throws SchedulerException
	 */
	public void resumeTrigger(String triggerName) throws SchedulerException {
		if(localQuartzScheduler.getTrigger(triggerName, Scheduler.DEFAULT_GROUP)==null)return;
		localQuartzScheduler.resumeTrigger(triggerName, Scheduler.DEFAULT_GROUP);
	}
	/**
	 * 重设定时器时间
	 * @param name
	 * @param expression
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public void resetTriggerTime(String triggerName, String expression)
			throws SchedulerException, ParseException {
		CronTrigger cronTrigger = (CronTrigger) localQuartzScheduler.getTrigger(
				triggerName, Scheduler.DEFAULT_GROUP);
		
		cronTrigger.setCronExpression(expression);
		localQuartzScheduler.rescheduleJob(triggerName, Scheduler.DEFAULT_GROUP,cronTrigger);
		
	}
	/**
	 * 对任务增加一个触发器
	 * @param name
	 * @param expression
	 * @param targetClass
	 * @throws ParseException
	 * @throws SchedulerException
	 */
	public void addTriggerToJob(String jobName,String triggerName, String expression,
			Class<?> targetClass) throws ParseException, SchedulerException{
		if(localQuartzScheduler.getTrigger(triggerName, Scheduler.DEFAULT_GROUP)!=null){
			localQuartzScheduler.unscheduleJob(triggerName, Scheduler.DEFAULT_GROUP);
		}
		CronTrigger trigger = new CronTrigger(triggerName, Scheduler.DEFAULT_GROUP, expression); 
		//对已存在的任务添加一个触发器
		if(localQuartzScheduler.getJobDetail(jobName,Scheduler.DEFAULT_GROUP)!=null){
			trigger.setJobGroup(Scheduler.DEFAULT_GROUP);
			trigger.setJobName(jobName);
			localQuartzScheduler.scheduleJob(trigger);
		}else{//新增一个任务并且添加触发器
			JobDetail jobDetail=new JobDetail(jobName,Scheduler.DEFAULT_GROUP,targetClass);
			
			localQuartzScheduler.scheduleJob(jobDetail,trigger);
		}
	}
	/**
	 *对任务增加一个触发器
	 * @param jobName
	 * @param trigger
	 * @param targetClass
	 * @throws SchedulerException
	 */
	public void addTriggerToJob(String jobName,Trigger trigger,
			Class<?> targetClass) throws SchedulerException{
		if(localQuartzScheduler.getTrigger(trigger.getName(), Scheduler.DEFAULT_GROUP)!=null){
			localQuartzScheduler.unscheduleJob(trigger.getName(), Scheduler.DEFAULT_GROUP);
		}
		//对已存在的任务添加一个触发器
		if(localQuartzScheduler.getJobDetail(jobName,Scheduler.DEFAULT_GROUP)!=null){
			trigger.setJobGroup(Scheduler.DEFAULT_GROUP);
			trigger.setJobName(jobName);
			localQuartzScheduler.scheduleJob(trigger);
		}else{//新增一个任务并且添加触发器
			JobDetail jobDetail=new JobDetail(jobName,Scheduler.DEFAULT_GROUP,targetClass);
			
			localQuartzScheduler.scheduleJob(jobDetail,trigger);
		}
	}
	/**
	 * 移除一个触发器
	 * @param name
	 * @throws ParseException
	 * @throws SchedulerException
	 */
	public void removeTrigger(String triggerName) throws ParseException, SchedulerException{
		localQuartzScheduler.unscheduleJob(triggerName, Scheduler.DEFAULT_GROUP);
	}
	/**
	 * 移除一个任务，同时会移除绑定在该任务上的所有触发器
	 * @param jobName
	 * @throws ParseException
	 * @throws SchedulerException
	 */
	public void removeJob(String jobName) throws ParseException, SchedulerException{
		localQuartzScheduler.deleteJob(jobName, Scheduler.DEFAULT_GROUP);
	}
	/**
	 * 获取绑定在任务上的所有触发器
	 * @param jobName
	 * @return
	 * @throws SchedulerException 
	 */
	public Trigger[] getTriggerByJobName(String jobName) throws SchedulerException{
		return localQuartzScheduler.getTriggersOfJob(jobName, Scheduler.DEFAULT_GROUP);
	}
	/**
	 * 获取对应名称的触发器
	 * @param triggerName
	 * @return
	 * @throws SchedulerException
	 */
	public Trigger getTriggerByTriggerName(String triggerName) throws SchedulerException{
		return localQuartzScheduler.getTrigger(triggerName, Scheduler.DEFAULT_GROUP);
	}
}
