package com.zfsoft.hrm.expertvote.vote.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.expertvote.vote.entity.ExpertAssessInstance;
import com.zfsoft.hrm.expertvote.vote.entity.ExpertGroup;
import com.zfsoft.hrm.expertvote.vote.entity.ExpertTask;
import com.zfsoft.hrm.expertvote.vote.query.ExpertAssessQuery;
import com.zfsoft.hrm.expertvote.vote.query.ExpertGroupQuery;
import com.zfsoft.hrm.expertvote.vote.query.ExpertTaskQuery;
import com.zfsoft.hrm.expertvote.vote.service.IDeclareInstanceService;
import com.zfsoft.hrm.expertvote.vote.service.IExpertAssessService;
import com.zfsoft.hrm.expertvote.vote.service.IExpertGroupService;
import com.zfsoft.hrm.expertvote.vote.service.IExpertTaskService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.date.TimeUtil;

/**
 * 
 * @author ChenMinming
 * @date 2014-3-24
 * @version V1.0.0
 */
public class ExpertTaskAssessAction extends HrmAction{

	private static final long serialVersionUID = 201403241359946L;
	
	private PageList<ExpertAssessInstance> pageList = new PageList<ExpertAssessInstance>();
	
	private ExpertAssessQuery query = new ExpertAssessQuery();
	
	private ExpertAssessInstance model;
	
	private IExpertAssessService expertAssessService;
	
	private IExpertGroupService expertGroupService;
	
	private IExpertTaskService expertTaskService;
	
	public String page(){
		ExpertGroupQuery groupQuery = new ExpertGroupQuery();
		groupQuery.setMemberId(getUser().getYhm());
		List<ExpertGroup> groups= expertGroupService.getList(groupQuery);
		getValueStack().set("groups", groups);
		if(StringUtil.isEmpty(query.getGroupId())&&groups!=null&&groups.size()>0){
			query.setGroupId(groups.get(0).getId());
		}
		ExpertTask task = fillTaskList(query.getGroupId());
		if(task!=null&&StringUtil.isEmpty(query.getTaskId())){
			query.setTaskId(task.getId());
		}
		if(StringUtil.isEmpty(query.getYear())){
			query.setYear(TimeUtil.getYear());
		}
		query.setGh(getUser().getYhm());
		pageList=expertAssessService.getPageList(query);
		int beginIndex = pageList.getPaginator().getBeginIndex();
		getValueStack().set("beginIndex",beginIndex);
		int sum=pageList.getPaginator().getItems();
		getValueStack().set("sum",sum);
		query.setResult("1");
		getValueStack().set("passNum",expertAssessService.findCount(query));
		int allowNum = 0;
		if(task!=null&&!StringUtil.isEmpty(task.getPassPoint())){
			allowNum = task.computeAllowNum(sum);
		}
		getValueStack().set("allowNum",allowNum);
		return "page";
	}
	
	public String findTaskList(){
		Map<String,Object> data = new HashMap<String, Object>();
		ExpertTaskQuery taskQuery = new ExpertTaskQuery();
		taskQuery.setGroupId(query.getGroupId());
		taskQuery.setExpertGh(getUser().getYhm());
		List<ExpertTask> taskList = expertTaskService.getList(taskQuery);
		data.put("success", true);
		data.put("list", taskList);
		getValueStack().set(DATA, data);
		return DATA;
	}
	
	public String saveResult(){
		query.setGh(getUser().getYhm());
		getValueStack().set(DATA, getMessage());
		if(StringUtil.isEmpty(query.getYear())){
			setErrorMessage("年度不能为空");
			return DATA;
		}
		if(StringUtil.isEmpty(query.getGroupId())){
			setErrorMessage("所属专家组不能为空");
			return DATA;
		}
		if(StringUtil.isEmpty(query.getTaskId())){
			setErrorMessage("所属任务不能为空");
			return DATA;
		}
		int sum = expertAssessService.findCount(query);
		query.setResult("1");
		int passNum = expertAssessService.findCount(query);
		ExpertTask task = expertTaskService.getById(query.getTaskId());
		if(task==null){
			setErrorMessage("所属任务不能为空");
			return DATA;
		}
		int allowNum = task.computeAllowNum(sum);
		if(allowNum<passNum){
			setErrorMessage("同意的人数不能大于所允许的人数（当前同意："+passNum+" 最大允许："+allowNum+"）");
			return DATA;
		}
		query.setResult(null);
		expertAssessService.doSaveResult(query);
		return DATA;
	}
	
	public String saveTask(){
		ExpertAssessInstance  instance = expertAssessService.findById(model.getId());
		if("1".equals(instance.getStatus())){
			setErrorMessage("已经提交的评审记录不允许再度更改！");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
		instance.setResult(model.getResult());
		expertAssessService.modify(instance);
		query.setResult("1");
		query.setGh(getUser().getYhm());
		setSuccessMessage(expertAssessService.findCount(query)+"");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String detail(){
		model = expertAssessService.findById(model.getId());
		IDeclareInstanceService service = (IDeclareInstanceService)SpringHolder.getBean("declareInstanceService_expertvote");
		getValueStack().set("declare", service.getById(model.getWorkId()));
		return "detail";
	}
	
	
	private ExpertTask fillTaskList(String groupId){
		ExpertTaskQuery taskQuery = new ExpertTaskQuery();
		taskQuery.setGroupId(groupId);
		taskQuery.setExpertGh(getUser().getYhm());
		List<ExpertTask> taskList = expertTaskService.getList(taskQuery);
		getValueStack().set("taskList", taskList);
		if(taskList!=null&&taskList.size()>0){
			return taskList.get(0);
		}
		return null;
	}

	public PageList<ExpertAssessInstance> getPageList() {
		return pageList;
	}

	public void setPageList(PageList<ExpertAssessInstance> pageList) {
		this.pageList = pageList;
	}

	public ExpertAssessQuery getQuery() {
		return query;
	}

	public void setQuery(ExpertAssessQuery query) {
		this.query = query;
	}

	public void setExpertAssessService(IExpertAssessService expertAssessService) {
		this.expertAssessService = expertAssessService;
	}

	public void setExpertGroupService(IExpertGroupService expertGroupService) {
		this.expertGroupService = expertGroupService;
	}

	public void setExpertTaskService(IExpertTaskService expertTaskService) {
		this.expertTaskService = expertTaskService;
	}

	public ExpertAssessInstance getModel() {
		return model;
	}

	public void setModel(ExpertAssessInstance model) {
		this.model = model;
	}
	
	

}
