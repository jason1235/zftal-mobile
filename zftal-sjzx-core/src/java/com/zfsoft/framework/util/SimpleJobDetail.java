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
 * <p>Description:������������������̳� </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2012-12-11 ����4:47:13
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
	 * ����ִ�з���
	 */
	public abstract void doaction();
	/**
	 * ����ִ����־��¼
	 */
	public abstract void dolog();
	
	public JobTriggerBean getTrigger(Dsqpzb dsq) {
		JobTriggerBean jtb = new JobTriggerBean(); 
		Trigger trigger = null;
		Integer  dsqxh=dsq.getDsqxh();
		String ddsjd = dsq.getDdsjd();//����ʱ���
		Integer jgsj = dsq.getJgsj();//���ʱ��
		String jdqssj = dsq.getJdqssj();//�׶���ʼʱ��
		String jdjssj = dsq.getJdjssj();//�׶ν���ʱ��
		Integer dsqlx = dsq.getDsqlx();//��ʱ������
		String dsqzt = dsq.getDsqzt();//1,����0,�ر�
		String jdzxjh = dsq.getJdzxjh();//�׶�ִ�мƻ�,1����ѭ��,0����ѭ��
		Integer sjdwbh = dsq.getSjdwbh();//ʱ�䵥λ���
		//����ִ��
		if(dsqlx==1){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startDate = null;
			Date endDate = null;
			//��ʱ��ο��ƣ���ÿ����ʱ���ִ��
			if((jdqssj == null || jdqssj.equals("")) && ((jdjssj == null || jdjssj.equals(""))))
			{
				String[] time = ddsjd.split(":");
				int hour = NumberUtils.toInt(time[0], 0);
				int minu = NumberUtils.toInt(time[1], 0);
				trigger = TriggerUtils.makeDailyTrigger(dsqxh+"e", hour, minu);
			}else{
				//��ʼʱ��
				int firstLine = jdqssj.indexOf("-");
				String str = jdqssj.substring(0, firstLine); 
				//�������䶨��x
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
				//ÿ�궨��x
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
		}else if(dsqlx==2) //���ִ��
		{

			//����Ϊ�գ���û��ʱ��ο��ƣ�ÿ��Nִ��
			if((jdqssj == null || jdqssj.equals("")) && ((jdjssj == null || jdjssj.equals(""))))
			{ 
				int interval = 0;
				//��
				if(sjdwbh==1)
				{
					interval = jgsj; 
					trigger = TriggerUtils.makeSecondlyTrigger(dsqxh+"e", interval, -1);
				}
				//��
				else if(sjdwbh==2)
				{
					//1000,60000,3600000,86400000,2592000000
					interval = jgsj; 
					trigger = TriggerUtils.makeMinutelyTrigger(dsqxh+"e", interval, -1);
				}
				//ʱ
				else if(sjdwbh==3)
				{
					interval = jgsj; 
					trigger = TriggerUtils.makeHourlyTrigger(dsqxh+"e", interval, -1);
				}
				//��
				else if(sjdwbh==4)
				{
					interval = 24 * jgsj; 
					trigger = TriggerUtils.makeHourlyTrigger(dsqxh+"e", interval, -1);
				}
			}
			//ʱ��������
			else
			{
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date startDate = null;
				Date endDate = null;
				//��ʼʱ��
				int firstLine = jdqssj.indexOf("-");
				String str = jdqssj.substring(0, firstLine); 
				//�̶���ݼ��
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
				//ÿ����
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
					System.out.println("ÿ����ִ��startDate:"+df.format(cal.getTime()));
					
					jdjssj = jdjssj + " 23:59:59";
					try {
						endDate = df.parse(jdjssj);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					cal.setTime(endDate);
					cal.set(Calendar.YEAR, year);
					System.out.println("ÿ����ִ��endDate:"+df.format(cal.getTime()));
					
				}
				 
				int interval = 0;
				//��
				if(sjdwbh==1)
				{
					interval = 1000 * jgsj;  
				}
				//��
				else if(sjdwbh==2)
				{
					//1000,60000,3600000,86400000,2592000000
					interval = 60000 * jgsj;  
				}
				//ʱ
				else if(sjdwbh==3)
				{
					interval = 3600000 * jgsj;  
				}
				//��
				else if(sjdwbh==4)
				{
					interval = 86400000 * jgsj;  
				}				 
				if(!str.equals("0000"))
				{
					//ʱ��ο��Կ���
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
					//ÿ���꣬�п�ʼʱ���޽���ʱ��
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
