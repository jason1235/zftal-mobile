package com.zfsoft.hrm.contract.action;


import java.util.Date;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.contract.entity.CategoryConfig;
import com.zfsoft.hrm.contract.service.ICategoryConfigService;
import com.zfsoft.hrm.schedule.ScheduleControlService;
import com.zfsoft.hrm.schedule.util.QuartzTriggerUtil;

import com.zfsoft.orcus.lang.TimeUtil;
import com.zfsoft.util.date.DateTimeUtil;
/**
 * 合同种类管理
 * @author: xiaoyongjun
 * @since: 2014-2-27 上午09:56:36
 */
public class CategoryConfigAction extends HrmAction implements Job{
	
	/* serialVersionUID: serialVersionUID */	
	private static final long serialVersionUID = 1389804599621211596L;
	private ICategoryConfigService categoryConfigService;
	CategoryConfig categoryConfig=new CategoryConfig();
	PageList<CategoryConfig> pageList = new PageList<CategoryConfig>();
	
	private ScheduleControlService scheduleControlService;	
	private final static String CONTRACT_SCAN = "CONTRACT_SCAN";   //定时器名称

	/**
	 * 合同种类列表
	 */
	public String list(){
		pageList=categoryConfigService.getPagedList(categoryConfig);		
		for(CategoryConfig categoryConfig : pageList) {
			String[] gh = categoryConfig.getGlry().split(";");
			String[] xm = new String[gh.length]; 
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<=gh.length-1;i++){
				categoryConfig.setGlry(categoryConfigService.getXm(gh[i]));
				xm[i] = categoryConfig.getGlry();
				sb.append(xm[i]).append("、");
			}
			categoryConfig.setGlry(sb.substring(0, sb.length()-1));
		}
		if(pageList.size()>=1){
			try {
				createTrigger();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return LIST_PAGE;
	}	
	
	private Trigger createTrigger() throws Exception{
		Trigger trigger = scheduleControlService.getTriggerByTriggerName(CONTRACT_SCAN);
		if (trigger == null) {
			String date = TimeUtil.dateX();
			Date startDate = TimeUtil.addDay(date, 1);
			trigger = QuartzTriggerUtil.getMyDateTrigger(CONTRACT_SCAN, 1, startDate, null);
			scheduleControlService.addTriggerToJob(CONTRACT_SCAN, trigger, CategoryConfigAction.class);
		}			
		return trigger;
	}
	
	/**
	 * 定时执行方法入口
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		categoryConfigService = (ICategoryConfigService)SpringHolder.getBean("categoryConfigService");
		categoryConfigService.doScanByDate();
		System.out.println("------------------------合同提醒定时执行-------------------------------");
	}
	
	/**
	 * 通过手动筛选时间进行执行
	 * @return
	 */
	public String manualExeByDate(){
		categoryConfigService.doScanByDate();
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	/**
	 *增加 
	 */
	public String add(){
		return "edit";
	}
	/**
	 * 修改
	 */
	public String edit(){
		categoryConfig = categoryConfigService.getById(categoryConfig.getHtzldm());
		return "edit";
	}
	
	/**
	 * 修改保存
	 */
	public String update() throws Exception{
		if(null != categoryConfig) {
			categoryConfig.setXgr(getUser().getYhm());
			categoryConfig.setXgsj(DateTimeUtil.getFormatDateTime(TimeUtil.current("yyyy-MM-dd HH:mm:ss")));
		}
		categoryConfigService.update(categoryConfig);
		this.setSuccessMessage("保存成功！");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	/**
	 * 增加保存
	 */
	public String save() throws Exception{
		if(null != categoryConfig) {
			categoryConfig.setCjr(getUser().getYhm());
			categoryConfig.setCjsj(DateTimeUtil.getFormatDateTime(TimeUtil.current("yyyy-MM-dd HH:mm:ss")));
		}
		categoryConfigService.insert(categoryConfig);
		this.setSuccessMessage("保存成功！");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}
	/**
	 *删除 
	 */
	public String delete(){
		try {
			categoryConfigService.delete(categoryConfig.getHtzldm());
			this.setSuccessMessage("删除成功！");
		} catch (Exception e) {
			this.setErrorMessage(e.getMessage());
		}		
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}	
	
	/**
	 * 检查合同种类代码重复性
	 */
	public String check(){
		int i=categoryConfigService.checkDm(categoryConfig.getHtzldm());
		if(i>0){
			this.setErrorMessage("合同种类代码重复!");
			this.getValueStack().set(DATA, getMessage());
		}else{
			this.setSuccessMessage("未找到相同合同种类代码!");
			this.getValueStack().set(DATA, getMessage());
		}
		return DATA;
	}
	
	public ICategoryConfigService getCategoryConfigService() {
		return categoryConfigService;
	}

	public void setCategoryConfigService(
			ICategoryConfigService categoryConfigService) {
		this.categoryConfigService = categoryConfigService;
	}

	public CategoryConfig getCategoryConfig() {
		return categoryConfig;
	}

	public void setCategoryConfig(CategoryConfig categoryConfig) {
		this.categoryConfig = categoryConfig;
	}

	public PageList<CategoryConfig> getPageList() {
		return pageList;
	}

	public void setPageList(PageList<CategoryConfig> pageList) {
		this.pageList = pageList;
	}
	
	public ScheduleControlService getScheduleControlService() {
		return scheduleControlService;
	}

	public void setScheduleControlService(
			ScheduleControlService scheduleControlService) {
		this.scheduleControlService = scheduleControlService;
	}
}
