package com.zfsoft.framework.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;

import com.zfsoft.sjzx.jhpz.dsqpzb.model.Dsqpzb;
import com.zfsoft.sjzx.jhpz.dsqpzb.service.DsqpzbService;

/**
 * <p>Description:抽象类用来调度子类继承 </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2012-12-11 下午4:47:13
 * @author liuchaoyong
 * @version 1.0
 */
public  abstract class SimpleJobDetail extends JobDetail{
	private static final long serialVersionUID = 1L;
	private Dsqpzb  dsqpzb=null;	
	public Dsqpzb getDsqpzb() {
		return dsqpzb;
	}
	public void setDsqpzb(Dsqpzb dsqpzb) {
		this.dsqpzb = dsqpzb;
	}
	protected  Object getBean(String beanName){
		return SpringUtil.getBean(beanName);
	}
	/**
	 * 调度执行方法
	 */
	public abstract void doaction();
	/**
	 * 调度执行日志记录
	 */
	public abstract void dolog();
	
	public JobTriggerBean getTrigger(Dsqpzb dsq) {
		JobTriggerBean jtb = new JobTriggerBean(); 
		Trigger trigger = null;
		Integer  dsqxh=dsq.getDsqxh();
		String ddsjd = dsq.getDdsjd();//定点时间点
		Integer jgsj = dsq.getJgsj();//间隔时间
		String jdqssj = dsq.getJdqssj();//阶段起始时间
		String jdjssj = dsq.getJdjssj();//阶段结束时间
		Integer dsqlx = dsq.getDsqlx();//定时器类型
		String dsqzt = dsq.getDsqzt();//1,开启0,关闭
		String jdzxjh = dsq.getJdzxjh();//阶段执行计划,1按年循环,0按月循环
		Integer sjdwbh = dsq.getSjdwbh();//时间单位编号
		//定点执行
		if(dsqlx==1){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startDate = null;
			Date endDate = null;
			//无时间段控制，则每隔天时间点执行
			if((jdqssj == null || jdqssj.equals("")) && ((jdjssj == null || jdjssj.equals(""))))
			{
				String[] time = ddsjd.split(":");
				int hour = NumberUtils.toInt(time[0], 0);
				int minu = NumberUtils.toInt(time[1], 0);
				trigger = TriggerUtils.makeDailyTrigger(dsqxh+"e", hour, minu);
			}else{
				//开始时间
				int firstLine = jdqssj.indexOf("-");
				String str = jdqssj.substring(0, firstLine); 
				//日期区间定点x
				if(!str.equals("0000")){
					jdqssj = jdqssj + " 00:00:00";	
					try {
						startDate = df.parse(jdqssj);
					} catch (ParseException e) {
						e.printStackTrace();
					}						
					jdjssj = jdjssj + " 23:59:59";
					try {
						endDate = df.parse(jdjssj);
					} catch (ParseException e) {				
						e.printStackTrace();
					}				
					
				}
				//每年定点x
				else {
					Calendar cal = Calendar.getInstance();
					cal.setTime(new Date());
					int year = cal.get(Calendar.YEAR);						
					jdqssj = jdqssj + " 00:00:00";	
					try {
						startDate = df.parse(jdqssj);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					cal.setTime(startDate);
					cal.set(Calendar.YEAR, year);
					startDate = cal.getTime();	
					jdjssj = jdjssj + " 23:59:59";
					try {
						endDate = df.parse(jdjssj);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					cal.setTime(endDate);
					cal.set(Calendar.YEAR, year);
					endDate = cal.getTime();				
				}
				String[] time = ddsjd.split(":");
				int hour = NumberUtils.toInt(time[0], 0);
				int minu = NumberUtils.toInt(time[1], 0);
				JobTimePointCalendar jtpc = new JobTimePointCalendar();
				jtpc.setHour(hour);
				jtpc.setMin(minu);
				jtpc.setDescription(dsqxh+"e");
				jtb.setCalendar(jtpc);
				
				if(!str.equals("0000")){
					SimpleTrigger trig = new SimpleTrigger();
					trig.setName(dsqxh+"e");
			        trig.setStartTime(startDate);
			        trig.setEndTime(endDate);
			        trig.setRepeatCount(-1); 
			        trig.setRepeatInterval(1000);
			        trig.setCalendarName(dsqxh+"e");
			        trigger = trig;
				} else {								
					SimpleTrigger trig = new SimpleTrigger();
					trig.setName(dsqxh+"e");
			        trig.setStartTime(startDate);
			        trig.setEndTime(endDate);
			        trig.setRepeatCount(-1); 
			        trig.setRepeatInterval(1000);
			        trig.setCalendarName(dsqxh+"e");
			        trigger = trig;
				}
			
			}
		}else if(dsqlx==2) //间隔执行
		{

			//日期为空，则没有时间段控制，每隔N执行
			if((jdqssj == null || jdqssj.equals("")) && ((jdjssj == null || jdjssj.equals(""))))
			{ 
				int interval = 0;
				//秒
				if(sjdwbh==1)
				{
					interval = jgsj; 
					trigger = TriggerUtils.makeSecondlyTrigger(dsqxh+"e", interval, -1);
				}
				//分
				else if(sjdwbh==2)
				{
					//1000,60000,3600000,86400000,2592000000
					interval = jgsj; 
					trigger = TriggerUtils.makeMinutelyTrigger(dsqxh+"e", interval, -1);
				}
				//时
				else if(sjdwbh==3)
				{
					interval = jgsj; 
					trigger = TriggerUtils.makeHourlyTrigger(dsqxh+"e", interval, -1);
				}
				//天
				else if(sjdwbh==4)
				{
					interval = 24 * jgsj; 
					trigger = TriggerUtils.makeHourlyTrigger(dsqxh+"e", interval, -1);
				}
			}
			//时间区间间隔
			else
			{
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date startDate = null;
				Date endDate = null;
				//开始时间
				int firstLine = jdqssj.indexOf("-");
				String str = jdqssj.substring(0, firstLine); 
				//固定年份间隔
				if(!str.equals("0000"))
				{
					jdqssj = jdqssj + ":00";									
					try {
						startDate = df.parse(jdqssj);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					jdjssj = jdjssj + ":59";
					try {
						endDate = df.parse(jdjssj);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				//每年间隔
				else
				{
					Calendar cal = Calendar.getInstance();
					cal.setTime(new Date());
					int year = cal.get(Calendar.YEAR);
					jdqssj = jdqssj + " 00:00:00";	
					try {
						startDate = df.parse(jdqssj);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					cal.setTime(startDate);
					cal.set(Calendar.YEAR, year);
					startDate = cal.getTime();
					System.out.println("每年间隔执行startDate:"+df.format(cal.getTime()));
					
					jdjssj = jdjssj + " 23:59:59";
					try {
						endDate = df.parse(jdjssj);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					cal.setTime(endDate);
					cal.set(Calendar.YEAR, year);
					System.out.println("每年间隔执行endDate:"+df.format(cal.getTime()));
					
				}
				 
				int interval = 0;
				//秒
				if(sjdwbh==1)
				{
					interval = 1000 * jgsj;  
				}
				//分
				else if(sjdwbh==2)
				{
					//1000,60000,3600000,86400000,2592000000
					interval = 60000 * jgsj;  
				}
				//时
				else if(sjdwbh==3)
				{
					interval = 3600000 * jgsj;  
				}
				//天
				else if(sjdwbh==4)
				{
					interval = 86400000 * jgsj;  
				}				 
				if(!str.equals("0000"))
				{
					//时间段可以控制
					//startDate = null;
					//endDate = null;
					
					SimpleTrigger trig = new SimpleTrigger();
					trig.setName(dsqxh+"e");
					trig.setGroup(Constants.JOB_GROUP);
			        trig.setStartTime(startDate);
			        trig.setEndTime(endDate);
			        trig.setRepeatCount(-1); 
			        trig.setRepeatInterval(interval);
			        trigger = trig;
				}
				else 
				{
					//每隔年，有开始时间无结束时间
					//startDate = null;
					
					SimpleTrigger trig = new SimpleTrigger();
					trig.setName(dsqxh+"e");
					trig.setGroup(Constants.JOB_GROUP);
			        trig.setStartTime(startDate);
			        //trig.setEndTime(endDate);
			        trig.setRepeatCount(-1); 
			        trig.setRepeatInterval(interval);
			        trigger = trig;
				}
			}
			
			
		}
		jtb.setTrigger(trigger);
		return jtb;
	}
	protected void updateLastRunTime(Dsqpzb dsq) {
		DsqpzbService dsqpzbService = (DsqpzbService) SpringUtil
				.getBean("dsqpzbService");
		dsqpzbService.updateLastRunTime(dsq);
	}
}
