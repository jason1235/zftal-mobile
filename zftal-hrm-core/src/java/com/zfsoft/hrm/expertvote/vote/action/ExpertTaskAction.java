package com.zfsoft.hrm.expertvote.vote.action;

import java.util.List;

import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.expertvote.vote.entity.DeclareInstance;
import com.zfsoft.hrm.expertvote.vote.entity.ExpertAssessInstance;
import com.zfsoft.hrm.expertvote.vote.entity.ExpertTask;
import com.zfsoft.hrm.expertvote.vote.entity.ExpertTaskDetail;
import com.zfsoft.hrm.expertvote.vote.query.DeclareInstanceQuery;
import com.zfsoft.hrm.expertvote.vote.query.ExpertGroupQuery;
import com.zfsoft.hrm.expertvote.vote.query.ExpertTaskQuery;
import com.zfsoft.hrm.expertvote.vote.service.IDeclareInstanceService;
import com.zfsoft.hrm.expertvote.vote.service.IExpertAssessService;
import com.zfsoft.hrm.expertvote.vote.service.IExpertGroupService;
import com.zfsoft.hrm.expertvote.vote.service.IExpertTaskService;
import com.zfsoft.hrm.menu.service.IMenuService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.workflow.enumobject.WorkNodeStatusEnum;

/**
 * 专家组任务管理
 * @author ChenMinming
 * @date 2014-3-14
 * @version V1.0.0
 */
public class ExpertTaskAction extends HrmAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 201403181513946L;
	private ExpertTask model;
	private ExpertTaskDetail detail;
	private String groupId;
	private ExpertTaskQuery query=new ExpertTaskQuery();
	private PageList<ExpertTask> pageList=new PageList<ExpertTask>();
	private DeclareInstanceQuery instanceQuery= new DeclareInstanceQuery();
	
	private IExpertTaskService expertTaskService;
	private IExpertGroupService expertGroupService;
	private IExpertAssessService expertAssessService;
	private IDeclareInstanceService declareInstanceService;
	
	
	public String page(){
		pageList=expertTaskService.getPageList(query);
		int beginIndex = pageList.getPaginator().getBeginIndex();
		getValueStack().set("statusArray", WorkNodeStatusEnum.values());
		getValueStack().set("beginIndex",beginIndex);
		return "page";
	}
	
	public String add(){
		if(model!=null&&!StringUtil.isEmpty(model.getId())){
			model=expertTaskService.getById(model.getId());
		}
		IMenuService menuService = SpringHolder.getBean("menuService",IMenuService.class);
		this.getValueStack().set("belongToSyses", menuService.getByLevel(1));
		return "add";
	}
	
	public String send(){
		ExpertAssessInstance instance = new ExpertAssessInstance();
		instance.setTaskId(detail.getId());
		instance.setBusinessClassId(detail.getBusinessClassId());
		instance.setGroupId(groupId);
		expertAssessService.removeInstances(instance);
		expertAssessService.createInstanceList(instance);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	public String chooseGroupReSend(){
		List<String> sbjList=expertTaskService.getGroupIdByBsnsClassId(detail);
		if(sbjList!=null&&sbjList.size()>0){
			groupId=sbjList.get(0);
		}
		ExpertGroupQuery groupQuery = new ExpertGroupQuery();
		groupQuery.setLevel(query.getLevel());
		getValueStack().set("groups", expertGroupService.getList( groupQuery));
		return "choose";
	}
	
	public String save(){
		if(!StringUtil.isEmpty(model.getId())){
			expertTaskService.modifyExpertGroup(model);
		}else{
			expertTaskService.saveExpertGroup(model);
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String remove(){
		expertTaskService.delete(model.getId());
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String chooseGroup(){
		ExpertGroupQuery groupQuery = new ExpertGroupQuery();
		groupQuery.setLevel(query.getLevel());
		getValueStack().set("groups", expertGroupService.getList( groupQuery));
		return "choose";
	}
	
	public String detail(){
		model = expertTaskService.getById(model.getId());
		if(detail == null){
			detail = new ExpertTaskDetail();
			detail.setSend("NotSend");
		}
		detail.setId(model.getId());
		detail.setName(model.getName());
		expertTaskService.getTaskCount(detail);
		getValueStack().set("subjectList", expertTaskService.getBsnsClassIds(detail));
		return "detail";
	}
	
	public String declareList(){
		PageList<DeclareInstance> declareList = declareInstanceService.getPageList(instanceQuery);
		getValueStack().set("declareList", declareList);
		return "declarelist";
	}

	public void setExpertTaskService(IExpertTaskService expertTaskService) {
		this.expertTaskService = expertTaskService;
	}
	
	public void setExpertGroupService(IExpertGroupService expertGroupService) {
		this.expertGroupService = expertGroupService;
	}

	public ExpertTask getModel() {
		return model;
	}

	public void setModel(ExpertTask model) {
		this.model = model;
	}

	public ExpertTaskQuery getQuery() {
		return query;
	}

	public void setQuery(ExpertTaskQuery query) {
		this.query = query;
	}

	public PageList<ExpertTask> getPageList() {
		return pageList;
	}

	public void setPageList(PageList<ExpertTask> pageList) {
		this.pageList = pageList;
	}

	public ExpertTaskDetail getDetail() {
		return detail;
	}

	public void setDetail(ExpertTaskDetail detail) {
		this.detail = detail;
	}

	public void setExpertAssessService(IExpertAssessService expertAssessService) {
		this.expertAssessService = expertAssessService;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * 设置
	 * @param declareInstanceService 
	 */
	public void setDeclareInstanceService(
			IDeclareInstanceService declareInstanceService) {
		this.declareInstanceService = declareInstanceService;
	}

	/**
	 * 返回
	 */
	public DeclareInstanceQuery getInstanceQuery() {
		return instanceQuery;
	}

	/**
	 * 设置
	 * @param instanceQuery 
	 */
	public void setInstanceQuery(DeclareInstanceQuery instanceQuery) {
		this.instanceQuery = instanceQuery;
	}
	
}
