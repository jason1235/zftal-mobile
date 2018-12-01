package com.zfsoft.hrm.baseinfo.snapshot.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.snapshot.entities.ProgressInfo;
import com.zfsoft.hrm.baseinfo.snapshot.entities.SnapshotLogDetail;
import com.zfsoft.hrm.baseinfo.snapshot.query.SnapshotLogDetailQuery;
import com.zfsoft.hrm.baseinfo.snapshot.service.svcinterface.ISnapshotLogDetailService;
import com.zfsoft.hrm.baseinfo.snapshot.service.svcinterface.ISnapshotService;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.schedule.ScheduleControlService;
import com.zfsoft.hrm.schedule.util.QuartzTriggerUtil;
import com.zfsoft.orcus.lang.TimeUtil;
import com.zfsoft.common.spring.SpringHolder;

/** 
 * 快照操作
 * @ClassName: SnapshotAction 
 * @author jinjj
 * @date 2012-7-18 下午03:21:57 
 *  
 */
public class SnapshotAction extends HrmAction implements Job{

	private static final long serialVersionUID = -197475325521947086L;
	private String snapTime;
	private String period;//定时周期
	private int trigger;//定时开关
	private String startDate;//开始日期
	private static Map<String,Integer> expressionMap = initExpression();
	private ScheduleControlService scheduleService;
	private SnapshotLogDetailQuery query = new SnapshotLogDetailQuery();

	private PageList<SnapshotLogDetail> pageList;
	private ISnapshotService snapshotService;
	private ISnapshotLogDetailService snapshotLogDetailService;
	
	private static final String OPEN ="OPEN";
	private static final String PERIOD = "PERIOD";
	private static final String START_DATE ="START_DATE";
	private static final String TRIGGER_NAME = "snapshot";
	
	/**
	 * 手动快照
	 */
	public String execute()throws Exception{
		if(StringUtils.isEmpty(snapTime)){
			throw new RuleException("快照时间为空");
		}
		Date date = TimeUtil.toDate(snapTime);
		//Date date = TimeUtil.toDate(TimeUtil.format(new Date(), "yyyy-MM"));
		snapshotService.doSnapshot(date);
		getMessage().setText("快照操作完成");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	
	/**
	 * 任务执行入口
	 */
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		//snapshotService = (ISnapshotService)ServiceFactory.getService("snapshotService");
		//无法IOC注入，人工获取对象
		snapshotService = (ISnapshotService)SpringHolder.getBean("snapshotService");
		snapshotService.doSnapshot(TimeUtil.toDate(TimeUtil.format(new Date(), "yyyy-MM")));
	}

	/**
	 * 手动快照页面
	 * @return
	 * @throws Exception
	 */
	public String handle()throws Exception{
		snapTime = TimeUtil.format(new Date(), "yyyy-MM");
		return "handle";
	}
	
	/**
	 * 定时设置页面
	 * @return
	 * @throws Exception
	 */
	public String setup()throws Exception{
		Trigger t = scheduleService.getTriggerByTriggerName(TRIGGER_NAME);
		if(t != null){
			JobDataMap map = t.getJobDataMap();
			period = map.getString(PERIOD);
			if(map.get(OPEN)!= null)
				trigger = map.getInt(OPEN);
			startDate = map.getString(START_DATE);
		}
		return "setup";
	}
	
	/**
	 * 定时任务设置
	 * @return
	 * @throws Exception
	 */
	public String timing() throws Exception{
		Integer num = expressionMap.get(period);
		if(num == null){
			throw new RuleException("非法的执行周期");
		}
		if(StringUtils.isEmpty(startDate)){
			throw new RuleException("初次执行时间为空");
		}
		scheduleService.removeTrigger(TRIGGER_NAME);
		Trigger t = QuartzTriggerUtil.getMyMonthTrigger(TRIGGER_NAME, num, TimeUtil.toDate(startDate), null);
		JobDataMap map = t.getJobDataMap();
		map.put(OPEN, trigger);
		map.put(PERIOD, period);
		map.put(START_DATE, startDate);
		t.setJobDataMap(map);
		scheduleService.addTriggerToJob(TRIGGER_NAME, t, SnapshotAction.class);
		if(trigger == 0){
			scheduleService.pauseTrigger(TRIGGER_NAME);
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	/**
	 * 初始化选择表达式
	 * @return
	 */
	private static Map<String,Integer> initExpression(){
		//cron 表达式：秒 分 时 天 月 年
		Map<String,Integer> map = new HashMap<String,Integer>();
		//map.put("month", "0 0 0 day * ?");
		//map.put("quarter_year", "0 0 0 day 0/3 ?");
		//map.put("half_year", "0 0 0 day 0/6 ?");
		map.put("month", 1);
		map.put("quarter_year", 3);
		map.put("half_year", 6);
		return map;
	}
	
	/**
	 * 删除快照
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception{
		if(StringUtils.isEmpty(snapTime)){
			throw new RuleException("快照时间为空");
		}
		Date date = TimeUtil.toDate(snapTime);
		snapshotService.delete(date);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String progress() throws Exception{
		HttpSession s = SessionFactory.getSession();
		ProgressInfo p = (ProgressInfo)s.getAttribute(IConstants.SESSION_PROGRESS);
		if(p == null){
			p = new ProgressInfo();
		}
		Map<String,Object> map = new HashMap<String,Object>();
		if(p.getStepIndex()>=0){
			map.put("success", true);
		}else{
			map.put("success", false);
		}
		map.put("progress", p);
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	/**
	 * 明细分页
	 * @return
	 * @throws Exception
	 */
	public String detailPage()throws Exception{
		//SnapshotLogDetailQuery detailQuery = new SnapshotLogDetailQuery();
		Date date = TimeUtil.toDate(snapTime);
		query.setSnapTime(date);
		pageList = snapshotLogDetailService.getPage(query);
		this.setInActionContext("paginator", pageList.getPaginator());
		return "detailPage";
	}

	public void setSnapshotService(ISnapshotService snapshotService) {
		this.snapshotService = snapshotService;
	}

	public String getSnapTime() {
		return snapTime;
	}

	public void setSnapTime(String snapTime) {
		this.snapTime = snapTime;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public int getTrigger() {
		return trigger;
	}

	public void setTrigger(int trigger) {
		this.trigger = trigger;
	}

	public void setScheduleService(ScheduleControlService scheduleService) {
		this.scheduleService = scheduleService;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public SnapshotLogDetailQuery getQuery() {
		return query;
	}


	public void setQuery(SnapshotLogDetailQuery query) {
		this.query = query;
	}


	public PageList<SnapshotLogDetail> getPageList() {
		return pageList;
	}

	public void setSnapshotLogDetailService(
			ISnapshotLogDetailService snapshotLogDetailService) {
		this.snapshotLogDetailService = snapshotLogDetailService;
	}

}
