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

import com.zfsoft.sjzx.jhpz.wspz.model.WsjhDsqConfig;



public  abstract class WebServiceJobDetail extends JobDetail{
	private static final long serialVersionUID = 1L;
	
	private WsjhDsqConfig wdc=null;
	
	
	
	public WsjhDsqConfig getWdc() {
		return wdc;
	}
	public void setWdc(WsjhDsqConfig wdc) {
		this.wdc = wdc;
	}
	protected  Object getBean(String beanName){
		return SpringUtil.getBean(beanName);
	}
	/**
	 * 锟斤拷锟斤拷执锟叫凤拷锟斤拷
	 */
	public abstract void doaction();
	/**
	 * 锟斤拷锟斤拷执锟斤拷锟斤拷志锟斤拷录
	 */
	public abstract void dolog();
	
	
	public JobTriggerBean getTrigger(WsjhDsqConfig dsq) {
		JobTriggerBean jtb = new JobTriggerBean(); 
		Trigger trigger = null;
		Integer  dsqxh=dsq.getDsqxh();
		String ddsjd = dsq.getDdsjd();//锟斤拷锟斤拷时锟斤拷锟�
		Integer jgsj = dsq.getJgsj();//锟斤拷锟绞憋拷锟�
		String jdqssj = dsq.getJdqssj();//锟阶讹拷锟斤拷始时锟斤拷
		String jdjssj = dsq.getJdjssj();//锟阶段斤拷锟斤拷时锟斤拷
		Integer dsqlx = dsq.getDsqlx();//锟斤拷时锟斤拷锟斤拷锟斤拷
		String dsqzt = dsq.getDsqzt();//0,锟斤拷锟斤拷1,停止
		String jdzxjh = dsq.getJdzxjh();//锟阶讹拷执锟叫计伙拷,1锟斤拷锟斤拷循锟斤拷,0锟斤拷锟斤拷循锟斤拷
		Integer sjdwbh = dsq.getSjdwbh();//时锟戒单位锟斤拷锟�
		//锟斤拷锟斤拷执锟斤拷
		if(dsqlx==1&&"0".equals(dsqzt)){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startDate = null;
			Date endDate = null;
			//锟斤拷时锟斤拷慰锟斤拷疲锟斤拷锟矫匡拷锟斤拷锟绞憋拷锟斤拷执锟斤拷
			if((jdqssj == null || jdqssj.equals("")) && ((jdjssj == null || jdjssj.equals(""))))
			{
				String[] time = ddsjd.split(":");
				int hour = NumberUtils.toInt(time[0], 0);
				int minu = NumberUtils.toInt(time[1], 0);
				trigger = TriggerUtils.makeDailyTrigger(dsqxh+"e", hour, minu);
			}else{
				//锟斤拷始时锟斤拷
				int firstLine = jdqssj.indexOf("-");
				String str = jdqssj.substring(0, firstLine); 
				//锟斤拷锟斤拷锟斤拷涠拷锟絰
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
				//每锟疥定锟斤拷x
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
		}else if(dsqlx==2&&"0".equals(dsqzt)) {

			//锟斤拷锟斤拷为锟秸ｏ拷锟斤拷没锟斤拷时锟斤拷慰锟斤拷疲锟矫匡拷锟絅执锟斤拷
			if((jdqssj == null || jdqssj.equals("")) && ((jdjssj == null || jdjssj.equals(""))))
			{ 
				int interval = 0;
				//锟斤拷
				if(sjdwbh==1)
				{
					interval = jgsj; 
					trigger = TriggerUtils.makeSecondlyTrigger(dsqxh+"e", interval, -1);
				}
				//锟斤拷
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
				//锟斤拷
				else if(sjdwbh==4)
				{
					interval = 24 * jgsj; 
					trigger = TriggerUtils.makeHourlyTrigger(dsqxh+"e", interval, -1);
				}
			}
			//时锟斤拷锟斤拷锟斤拷锟�
			else
			{
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date startDate = null;
				Date endDate = null;
				//锟斤拷始时锟斤拷
				int firstLine = jdqssj.indexOf("-");
				String str = jdqssj.substring(0, firstLine); 
				//锟教讹拷锟斤拷菁锟斤拷
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
				//每锟斤拷锟斤拷
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
					System.out.println("每锟斤拷锟斤拷执锟斤拷startDate:"+df.format(cal.getTime()));
					
					jdjssj = jdjssj + " 23:59:59";
					try {
						endDate = df.parse(jdjssj);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					cal.setTime(endDate);
					cal.set(Calendar.YEAR, year);
					System.out.println("每锟斤拷锟斤拷执锟斤拷endDate:"+df.format(cal.getTime()));
					
				}
				 
				int interval = 0;
				//锟斤拷
				if(sjdwbh==1)
				{
					interval = 1000 * jgsj;  
				}
				//锟斤拷
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
				//锟斤拷
				else if(sjdwbh==4)
				{
					interval = 86400000 * jgsj;  
				}				 
				if(!str.equals("0000"))
				{
					//时锟斤拷慰锟斤拷钥锟斤拷锟�
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
					//每锟斤拷锟疥，锟叫匡拷始时锟斤拷锟睫斤拷锟斤拷时锟斤拷
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
}
