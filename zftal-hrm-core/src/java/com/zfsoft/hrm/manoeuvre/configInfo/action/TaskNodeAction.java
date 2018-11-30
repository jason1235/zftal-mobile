package com.zfsoft.hrm.manoeuvre.configInfo.action;

import java.util.List;

import org.springframework.util.Assert;

import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.TaskNode;
import com.zfsoft.hrm.manoeuvre.configInfo.query.TaskNodeQuery;
import com.zfsoft.hrm.manoeuvre.configInfo.service.svcinterface.ITaskNodeService;
import com.zfsoft.util.base.StringUtil;

public class TaskNodeAction extends HrmAction {
	
	private static final long serialVersionUID = -5015610567832988847L;

	private TaskNode info = new TaskNode();
	
	private TaskNodeQuery query = new TaskNodeQuery();
	
	private List<TaskNode> infoList;
	
	private ITaskNodeService taskNodeService;
	
	private static final String SHOWWINERROR = "showWinError";
	
	//-------------------------------------------------------------------------------------
	
	public String list(){
		infoList = taskNodeService.getList(query);
		return LIST_PAGE;
	}
	
	public String edit(){
		try{
			if(!StringUtil.isEmpty(info.getNid())){
				info = taskNodeService.getById(info.getNid());
			}
			return EDIT_PAGE;
		}catch (Exception e) {
			getValueStack().set("message", e.getMessage());
			return SHOWWINERROR;
		}
	}
	
	public String save(){
		Assert.isTrue(!StringUtil.isEmpty(info.getNodeName()), "环节名称不可为空");
		if(StringUtil.isEmpty(info.getNid())){
			setSuccessMessage(taskNodeService.add(info) ? "新增审核环节成功" : "未成功新增审核环节");
		}
		else{
			setSuccessMessage(taskNodeService.modify(info) ? "修改审核环节成功" : "未成功修改审核环节");
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String remove(){
		Assert.isTrue(!StringUtil.isEmpty(query.getNid()), "未指定任何信息");
		taskNodeService.remove(query.getNid());
		setSuccessMessage("删除环节成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String changeOrder(){
		Assert.isTrue(!StringUtil.isEmpty(info.getNid()), "未指定任何信息");
		Assert.isTrue(!StringUtil.isEmpty(info.getOrder()), "未指定新顺序码");
		taskNodeService.modifyOrder(info);
		setSuccessMessage("环节移动成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String exchange(){
		String nid1 = getRequest().getParameter("nid1");
		String nid2 = getRequest().getParameter("nid2");
		Assert.isTrue(!StringUtil.isEmpty(nid1) && !StringUtil.isEmpty(nid2), "未正确指定环节");
		taskNodeService.exchange(nid1, nid2);
		setSuccessMessage("环节移动成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	

	/**
	 * 返回
	 * @return 
	 */
	public TaskNode getInfo() {
		return info;
	}

	/**
	 * 设置
	 * @param info 
	 */
	public void setInfo(TaskNode info) {
		this.info = info;
	}

	/**
	 * 返回
	 * @return 
	 */
	public TaskNodeQuery getQuery() {
		return query;
	}

	/**
	 * 设置
	 * @param query 
	 */
	public void setQuery(TaskNodeQuery query) {
		this.query = query;
	}

	/**
	 * 返回
	 * @return 
	 */
	public List<TaskNode> getInfoList() {
		return infoList;
	}

	/**
	 * 设置
	 * @param infoList 
	 */
	public void setInfoList(List<TaskNode> infoList) {
		this.infoList = infoList;
	}

	/**
	 * 返回
	 * @return 
	 */
	public ITaskNodeService getTaskNodeService() {
		return taskNodeService;
	}

	/**
	 * 设置
	 * @param taskNodeService 
	 */
	public void setTaskNodeService(ITaskNodeService taskNodeService) {
		this.taskNodeService = taskNodeService;
	}

	
	

}
