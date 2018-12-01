package com.zfsoft.hrm.staffturn.retire.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;

import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanUtil;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.hrm.schedule.ScheduleControlService;
import com.zfsoft.hrm.schedule.util.QuartzTriggerUtil;
import com.zfsoft.hrm.staffturn.retire.entities.RetireRule;
import com.zfsoft.hrm.staffturn.retire.entities.RetireScheduleTypeEnum;
import com.zfsoft.hrm.staffturn.retire.service.svcinterface.IRetireScanService;
import com.zfsoft.hrm.staffturn.retire.service.svcinterface.RetireRuleService;
import com.zfsoft.orcus.lang.TimeUtil;

/** 
 * 退休提前提醒action
 * @author jinjj
 * @date 2012-7-31 上午08:28:40 
 *  
 */
public class RetireScanAction extends HrmAction implements Job {

	private static final long serialVersionUID = 1628194762680751072L;
	private IRetireScanService retireScanService;
	private ScheduleControlService scheduleControlService;
	private String receiver;
	private Integer open = 0;
	private Integer notifySelf = 0;
	private RetireScheduleTypeEnum period;
	private final static String RETIRE_SCAN = "RETIRE_SCAN";
	private final static String RECEIVER = "RECEIVER";
	private final static String OPEN = "OPEN";
	private final static String NOTIFYSELF = "NOTIFYSELF";
	private final static String PERIOD = "PERIOD";
	
	private RetireRule model=new RetireRule();
	
	private RetireRuleService retireRuleService;
	
	/**
	 * 进入配置页面
	 * @return
	 * @throws Exception
	 */
	public String setup() throws Exception{
		Trigger trigger = scheduleControlService.getTriggerByTriggerName(RETIRE_SCAN);
		if(trigger != null){
			JobDataMap map = trigger.getJobDataMap();
			receiver = map.getString(RECEIVER);
			if(map.get(OPEN)!= null)
				open = map.getInt(OPEN);
			if(map.get(NOTIFYSELF)!= null){
				notifySelf = map.getInt(NOTIFYSELF);
			}
			if(map.get(PERIOD)!= null){
				period = (RetireScheduleTypeEnum)map.get(PERIOD) ;
			}
		}
		getValueStack().set("retireScheduleTypeEnums", RetireScheduleTypeEnum.values());
		return "edit";
	}
	
	/**
	 * 更新定时配置
	 * @return
	 * @throws Exception
	 */
	public String timing() throws Exception{
		Trigger trigger =null;
		if(RetireScheduleTypeEnum.MONTH==period){
			String date = TimeUtil.beginTimeOfMonth();
			Date startDate = TimeUtil.addMonth(date, 1);    //每个月
			trigger=QuartzTriggerUtil.getMyMonthTrigger(RETIRE_SCAN, 1, startDate, null);
		}else if(RetireScheduleTypeEnum.YEAR==period){
			String beginDayOfYear = TimeUtil.current("yyyy-01-01");
			DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = df.parse(beginDayOfYear);      //每年
			trigger=QuartzTriggerUtil.getMyYearTrigger(RETIRE_SCAN, 1, startDate, null);
		}else if(RetireScheduleTypeEnum.SEASON==period){
			Date startDate = findNextDayCycleMonth(3);   //每季度
			trigger=QuartzTriggerUtil.getMyMonthTrigger(RETIRE_SCAN, 3, startDate, null);
		}else{
			Date startDate = findNextDayCycleMonth(6);    //开始时间为当年的1月1号或者7月1号
//			Date startDate = TimeUtil.addMonth(date, 6);   //每半年
			trigger=QuartzTriggerUtil.getMyMonthTrigger(RETIRE_SCAN, 6, startDate, null);
		}
		JobDataMap map = trigger.getJobDataMap();
		map.put(RECEIVER, receiver);
		map.put(OPEN, open);
		map.put(NOTIFYSELF, notifySelf);
		map.put(PERIOD, period);
		trigger.setJobDataMap(map);
		scheduleControlService.removeTrigger("RETIRE_SCAN");
		scheduleControlService.addTriggerToJob(RETIRE_SCAN, trigger, RetireScanAction.class);
		if(open == 0){
			scheduleControlService.pauseTrigger("RETIRE_SCAN");
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	
	private Date findNextDayCycleMonth(int Cycle) throws Exception{
		String beginDayOfYear = TimeUtil.current("yyyy-01-01");
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(beginDayOfYear);
		Date now = new Date();
		while (startDate.getTime()<now.getTime()) {
			startDate = TimeUtil.addMonth(startDate, 3);
		}
		return startDate;
	}
	
	/**
	 * 定时执行方法入口
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		retireScanService = (IRetireScanService)SpringHolder.getBean("retireScanService");
		JobDataMap map = context.getTrigger().getJobDataMap();
		
		retireScanService.doScan(map.getString(RECEIVER),map.getInt(NOTIFYSELF),(RetireScheduleTypeEnum)map.get(PERIOD));
	}
	
	public String manualExe()throws Exception{
		retireScanService.doScan(receiver,notifySelf,period);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	/**
	 * 通过手动筛选时间进行执行
	 * @author cch
	 * @return
	 */
	public String manualExeByDate(){
		retireScanService.doScanByDate(receiver,notifySelf,period,model.getManual_time());
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
    
	/**
	 * 跳转到选择预退休窗口
	 * @return
	 * @throws Exception
	 */
	public String period(){
		return "period";
	}
	
	public String config() throws Exception{
		List<RetireRule> list= retireRuleService.getList(model);
		InfoClass overall=InfoClassCache.getOverallInfoClass();
		for (RetireRule item : list) {
			// 20140423 add start
		    item.setReceiverNm(DynaBeanUtil.getPersonName(item.getReceiver() == null ? "" : item.getReceiver()));
			// 20140423 add 
			if(StringUtils.isEmpty(item.getCond_col())){
				continue;
			}
			for (InfoProperty property : overall.getProperties()) {
				if(item.getCond_col().equals(property.getFieldName())){
					item.setCond_col_name(property.getName());
					item.setCond_col_code(property.getCodeId());
					break;
				}
			}
		}
		
		getValueStack().set("pageList", list);
		return "list";
	}

	public String input(){
		InfoClass overall=InfoClassCache.getOverallInfoClass();
		List<InfoProperty> cond_cols=new ArrayList<InfoProperty>();
		for (InfoProperty infoProperty : overall.getProperties()) {
			if("CODE".equals(infoProperty.getFieldType()) && !ICodeConstants.SEX.equals(infoProperty.getCodeId())){
				cond_cols.add(infoProperty);
			}
		}
		
		getValueStack().set("cond_cols", cond_cols);
		return "add";
	}
	public String add(){
		model.setModify_time(new Date());
		retireRuleService.add(model);
		
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String query(){
		model= retireRuleService.getById(model);
		if(StringUtils.isEmpty(model.getCond_col())){
			return "modify";
		}
		InfoClass overall=InfoClassCache.getOverallInfoClass();
		for (InfoProperty infoProperty : overall.getProperties()) {
			if(infoProperty.getFieldName().equals(model.getCond_col())){
				model.setCond_col_name(infoProperty.getName());
				model.setCond_col_code(infoProperty.getCodeId());
			}
		}
		// 20140423 add start
		model.setReceiverNm(DynaBeanUtil.getPersonName( model.getReceiver() == null ? "" : model.getReceiver()));
		// 20140423 add end
		return "modify";
	}
	public String modify(){
		model.setModify_time(new Date());
		retireRuleService.modify(model);
		
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String delete(){
		retireRuleService.removeById(model);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String changeCond() throws Exception{
		String cond_col_code="";
		InfoClass overall=InfoClassCache.getOverallInfoClass();
		List<InfoProperty> cond_cols=new ArrayList<InfoProperty>();
		for (InfoProperty infoProperty : overall.getProperties()) {
			if("CODE".equals(infoProperty.getFieldType()) && !ICodeConstants.SEX.equals(infoProperty.getCodeId())){
				cond_cols.add(infoProperty);
				if(infoProperty.getFieldName().equals(model.getCond_col())){
					cond_col_code=infoProperty.getCodeId();
				}
			}
		}
		
		getValueStack().set("cond_col_code", cond_col_code);
		getValueStack().set("cond_cols", cond_cols);
		return "add";
	}
	
	public void setRetireScanService(IRetireScanService retireScanService) {
		this.retireScanService = retireScanService;
	}

	public void setScheduleControlService(
			ScheduleControlService scheduleControlService) {
		this.scheduleControlService = scheduleControlService;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public Integer getOpen() {
		return open;
	}

	public void setOpen(Integer open) {
		this.open = open;
	}

	public Integer getNotifySelf() {
		return notifySelf;
	}

	public void setNotifySelf(Integer notifySelf) {
		this.notifySelf = notifySelf;
	}

	public void setRetireRuleService(RetireRuleService retireRuleService) {
		this.retireRuleService = retireRuleService;
	}

	public RetireRule getModel() {
		return model;
	}

	public RetireScheduleTypeEnum getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = RetireScheduleTypeEnum.valueOf(period);
	}
	
}
