package com.zfsoft.hrm.message.action;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.quartz.JobDataMap;
import org.quartz.Trigger;

import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.message.config.MessageTimerSendConfig;
import com.zfsoft.hrm.message.service.impl.MessageTimerSendConfigServiceImpl;
import com.zfsoft.hrm.message.service.svcinterface.MessageTimerSendConfigService;
import com.zfsoft.hrm.schedule.ScheduleControlService;
import com.zfsoft.hrm.schedule.util.QuartzTriggerUtil;
import com.zfsoft.util.base.StringUtil;

/** 
 * 消息提醒配置
 * @author jinjj
 * @date 2012-9-24 下午03:08:58 
 *  
 */
public class MessageSetupAction extends HrmAction{

	private static final long serialVersionUID = 6310763619806509485L;

	private ScheduleControlService scheduleControlService;
	private MessageTimerSendConfigService messageTimerSendConfigService;

	private MessageTimerSendConfig config;
	private String op;
	
	private static Pattern PATTERN_GH=Pattern.compile("[\\s\\.]gh[\\s,]", Pattern.CASE_INSENSITIVE);
	
	public String list(){
		List<MessageTimerSendConfig> list=messageTimerSendConfigService.getList(new MessageTimerSendConfig());
		getValueStack().set("list", list);
		return "list";
	}
	
	public String addInit() throws Exception{
		getValueStack().set("infoClasses", InfoClassCache.getInfoClasses());
		op = "add";
		return "add";
	}
	
	public String add() throws Exception{
		if(config != null && config.getSql() == ""){
			setErrorMessage("数据sql不能为空！");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
		if(config != null && !StringUtil.isEmpty(config.getXxlzq()) && !StringUtil.isEmpty(config.getSql()) && config.getYwlx() == "0"){
			config.setSql(
					config.getSql() + " where a.OPERATION_TIME_>=trunc(sysdate-" 
					+ config.getXxlzq() + 
					") and a.OPERATION_TIME_<trunc(sysdate)"
					);
		}
		
		
		try {
			messageTimerSendConfigService.executeSelectSql(config.getSql());
		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
		if(!PATTERN_GH.matcher(config.getSql()).find()){
			setErrorMessage("数据sql必须查询出gh列！");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
		
		
		
		messageTimerSendConfigService.add(config);
		
		if(config.getSfjsts() == 0){
			
			Trigger trigger = createTrigger();
			if(trigger == null){
				throw new RuleException("定时器生成异常");
			}
			JobDataMap map = trigger.getJobDataMap();
			map.put("data", config);
			trigger.setJobDataMap(map);
			scheduleControlService.addTriggerToJob(config.getId(), trigger, MessageTimerSendConfigServiceImpl.class);
			if("0".equals(config.getZt())){
				scheduleControlService.pauseTrigger(config.getId());
			}
		}else{//这里填写及时推送代码
			
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String edit() throws Exception{
		config=messageTimerSendConfigService.getById(config);
		getValueStack().set("infoClasses", InfoClassCache.getInfoClasses());
		op = "modify";
		return "add";
	}
	
	public String update() throws Exception{
		if(config != null && config.getSql() == ""){
			setErrorMessage("数据sql不能为空！");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
		try {
			messageTimerSendConfigService.executeSelectSql(config.getSql());
		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
		if(!PATTERN_GH.matcher(config.getSql()).find()){
			setErrorMessage("数据sql必须查询出gh列！");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
		
		if(config.getSfjsts() == 0){
			Trigger trigger = createTrigger();
			if(trigger == null){
				throw new RuleException("定时器生成异常");
			}
			JobDataMap map = trigger.getJobDataMap();
			map.put("data", config);
			trigger.setJobDataMap(map);
			scheduleControlService.removeTrigger(config.getId());
			scheduleControlService.addTriggerToJob(config.getId(), trigger, MessageTimerSendConfigServiceImpl.class);
			if("0".equals(config.getZt())){
				scheduleControlService.pauseTrigger(config.getId());
			}
		}else{//这里填写及时推送代码
			
		}
		
		
		messageTimerSendConfigService.modify(config);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String delete() throws Exception{
		scheduleControlService.removeTrigger(config.getId());
		messageTimerSendConfigService.removeById(config);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	private Trigger createTrigger() throws Exception{
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 1);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date startDate = cal.getTime();
		return QuartzTriggerUtil.getMyDateTrigger(config.getId(), 1, startDate, null);
	}

	public String execute(){
		config=messageTimerSendConfigService.getById(config);
		messageTimerSendConfigService.sendMessages(config);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public void setScheduleControlService(
			ScheduleControlService scheduleControlService) {
		this.scheduleControlService = scheduleControlService;
	}
	
	public void setMessageTimerSendConfigService(
			MessageTimerSendConfigService messageTimerSendConfigService) {
		this.messageTimerSendConfigService = messageTimerSendConfigService;
	}

	public MessageTimerSendConfig getConfig() {
		return config;
	}

	public void setConfig(MessageTimerSendConfig config) {
		this.config = config;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getOp() {
		return op;
	}
	
}
