package com.zfsoft.hrm.authpost.post.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.authpost.post.query.PostHistoryQuery;
import com.zfsoft.hrm.authpost.post.service.svcinterface.IPostHistoryService;
import com.zfsoft.hrm.baseinfo.snapshot.entities.ProgressInfo;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.schedule.ScheduleControlService;
import com.zfsoft.hrm.schedule.util.QuartzTriggerUtil;
import com.zfsoft.orcus.lang.TimeUtil;
import com.zfsoft.common.spring.SpringHolder;

/** 
 * 历史岗位
 * @ClassName: SnapshotAction 
 * @author jinjj
 * @date 2012-7-18 下午03:21:57 
 *  
 */
public class PostHistoryAction extends HrmAction implements Job{

	private static final long serialVersionUID = -197475325521947086L;
	private String snapTime;
	private String period;//定时周期
	private int trigger;//定时开关
	private String startDate;//开始日期
	private PostHistoryQuery query = new PostHistoryQuery();
	private PageList pageList;
	private static Map<String,Integer> expressionMap = initExpression();
	private ScheduleControlService scheduleService;

	private IPostHistoryService postHistoryService;
	
	/**
	 * 手动快照
	 */
	public String execute()throws Exception{
		if(StringUtils.isEmpty(snapTime)){
			throw new RuleException("快照时间为空");
		}
		Date date = TimeUtil.toDate(snapTime);
		postHistoryService.doManual(date);
		getMessage().setText("快照操作完成");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	/**
	 * 明细分页
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception{
		Date date = TimeUtil.toDate(snapTime);
		query.setSnapTime(date);
		pageList = postHistoryService.getPage(query);
		this.setInActionContext("paginator", pageList.getPaginator());
		return "list";
	}
	/**
	 * 任务执行入口
	 */
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		//snapshotService = (ISnapshotService)ServiceFactory.getService("snapshotService");
		//无法IOC注入，人工获取对象
		postHistoryService = (IPostHistoryService)SpringHolder.getBean("postHistoryService");
		postHistoryService.doAuto();
	}

	/**
	 * 手动快照页面
	 * @return
	 * @throws Exception
	 */
	public String handle()throws Exception{
		snapTime = TimeUtil.format(new Date(), "yyyy-MM");
		return "manual";
	}
	
	/**
	 * 定时设置页面
	 * @return
	 * @throws Exception
	 */
	public String setup()throws Exception{
		return "auto";
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
		scheduleService.removeTrigger("snapshotTrigger");
		if(trigger == 1){
			Trigger t = QuartzTriggerUtil.getMyMonthTrigger("snapshot", num, TimeUtil.toDate(startDate), null);
			scheduleService.addTriggerToJob("snapshot", t, PostHistoryAction.class);
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
		postHistoryService.remove(date);
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
		map.put("success", true);
		map.put("progress", p);
		getValueStack().set(DATA, map);
		return DATA;
	}

	public void setPostHistoryService(IPostHistoryService postHistoryService) {
		this.postHistoryService = postHistoryService;
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

	public PostHistoryQuery getQuery() {
		return query;
	}

	public void setQuery(PostHistoryQuery query) {
		this.query = query;
	}

	public PageList getPageList() {
		return pageList;
	}

}
