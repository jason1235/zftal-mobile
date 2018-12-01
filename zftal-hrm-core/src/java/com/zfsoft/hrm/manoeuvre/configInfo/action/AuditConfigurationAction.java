package com.zfsoft.hrm.manoeuvre.configInfo.action;

import java.util.List;

import org.springframework.util.Assert;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditConfiguration;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.TaskNode;
import com.zfsoft.hrm.manoeuvre.configInfo.query.AuditConfigurationQuery;
import com.zfsoft.hrm.manoeuvre.configInfo.service.svcinterface.IAuditConfigurationService;
import com.zfsoft.hrm.manoeuvre.configInfo.service.svcinterface.ITaskNodeService;
import com.zfsoft.hrm.manoeuvre.exception.ManoeuvreException;
import com.zfsoft.util.base.StringUtil;

public class AuditConfigurationAction extends HrmAction {

	private static final long serialVersionUID = 1784504569378574307L;

	private AuditConfiguration info;
	
	private TaskNode taskNode;
	
	private AuditConfigurationQuery query;
	
	private String aid;
	
	private PageList<AuditConfiguration> page;
	
	private IAuditConfigurationService auditConfigurationService;
	
	private ITaskNodeService taskNodeService;
	
	private static final String SHOW_PAGE = "show";
	
	private static final String SHOWWINERROR = "showWinError";

	private String hidaudittype;
	
	//---------------------------------------------------------------------------------
	
	public String list() throws Exception {
		page = new PageList<AuditConfiguration>();
		taskNode = new TaskNode();
		if(query != null && query.getTaskNode() != null && query.getTaskNode().getNid() != null && !"".equals(query.getTaskNode().getNid())){
			query.setSortCol("SHR");
			page = auditConfigurationService.getPage(query);
			taskNode = taskNodeService.getById(query.getTaskNode().getNid());
		}
		this.setInActionContext("paginator", page.getPaginator());
		
		return LIST_PAGE;
	}
	
	public String edit() {
		try{
			info = new AuditConfiguration();
			if(aid != null && !"".equals(aid)){
				info = auditConfigurationService.getInfoById(aid);
			}
			getValueStack().set("roles", auditConfigurationService.getRoles());
			return EDIT_PAGE;
		}catch(Exception e){
			getValueStack().set("message", e.getMessage());
			return SHOWWINERROR;
		}
	}
	
	public String show() {
		try{
			Assert.isTrue(!StringUtil.isEmpty(aid), "未选定任何记录");
			info = auditConfigurationService.getInfoById(aid);
			Assert.notNull(info, "未找到选定记录详细信息");
			return SHOW_PAGE;
		}catch(Exception e){
			getValueStack().set("message", e.getMessage());
			return SHOWWINERROR;
		}
	}
	
	public String save() throws Exception {
		if(info == null){
			throw new ManoeuvreException("提交信息不可为空");
		}
		else if(info.getTaskNode() == null || info.getTaskNode().getNid() == null || "".equals(info.getTaskNode().getNid())){
			throw new ManoeuvreException("所属节点信息不可为空");
		}
		else if(info.getAssessor() == null || "".equals(info.getAssessor())){
			if ("2".equals(hidaudittype)) {
				throw new ManoeuvreException("审核人不可为空");
			}
		}
		else if(info.getExtensionType() == null || "".equals(info.getExtensionType())){
			throw new ManoeuvreException("审核类型不可为空");
		} 
		
		if(info.getAid() == null || "".equals(info.getAid())){
			info.setAudittype(hidaudittype);
			if ("2".equals(hidaudittype)) {
				setSuccessMessage(auditConfigurationService.add(info) ? "新增审核设置信息成功" : "未成功新增基础设置信息");
			} else {
				List<String> persons = auditConfigurationService.findPersonByRole(info);
				for (String assessor : persons) {
					info.setAssessor(assessor);
					auditConfigurationService.add(info);
				}
				setSuccessMessage("新增审核设置信息成功");
			}
		}
		else{
			setSuccessMessage(auditConfigurationService.modify(info) ? "修改审核设置信息成功" : "未成功修改审核设置信息");
		}
		
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String delete() throws Exception {
		if(aid == null || "".equals(aid)){
			throw new ManoeuvreException("未选定任何记录");
		}
		auditConfigurationService.remove(aid);
		setSuccessMessage("删除审核设置信息成功");
		
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	

	//-----------------------------------------------------------------------------
	
	/**
	 * 返回
	 * @return 
	 */
	public AuditConfiguration getInfo() {
		return info;
	}

	/**
	 * 设置
	 * @param info 
	 */
	public void setInfo(AuditConfiguration info) {
		this.info = info;
	}

	/**
	 * 返回
	 * @return 
	 */
	public TaskNode getTaskNode() {
		return taskNode;
	}

	/**
	 * 设置
	 * @param taskNode 
	 */
	public void setTaskNode(TaskNode taskNode) {
		this.taskNode = taskNode;
	}

	/**
	 * 返回
	 * @return 
	 */
	public AuditConfigurationQuery getQuery() {
		return query;
	}

	/**
	 * 设置
	 * @param query 
	 */
	public void setQuery(AuditConfigurationQuery query) {
		this.query = query;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getAid() {
		return aid;
	}

	/**
	 * 设置
	 * @param aid 
	 */
	public void setAid(String aid) {
		this.aid = aid;
	}

	/**
	 * 返回
	 * @return 
	 */
	public PageList<AuditConfiguration> getPage() {
		return page;
	}

	/**
	 * 设置
	 * @param page 
	 */
	public void setPage(PageList<AuditConfiguration> page) {
		this.page = page;
	}

	/**
	 * 返回
	 * @return 
	 */
	public IAuditConfigurationService getAuditConfigurationService() {
		return auditConfigurationService;
	}

	/**
	 * 设置
	 * @param auditConfigurationService 
	 */
	public void setAuditConfigurationService(
			IAuditConfigurationService auditConfigurationService) {
		this.auditConfigurationService = auditConfigurationService;
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

	/**
	 * 返回
	 * @return the hidaudittype
	 */
	public String getHidaudittype() {
		return hidaudittype;
	}

	/**
	 * 设置
	 * @param hidaudittype the hidaudittype to set
	 */
	public void setHidaudittype(String hidaudittype) {
		this.hidaudittype = hidaudittype;
	}

}
