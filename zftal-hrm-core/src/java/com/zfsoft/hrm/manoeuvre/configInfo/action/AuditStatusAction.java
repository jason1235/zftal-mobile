package com.zfsoft.hrm.manoeuvre.configInfo.action;

import org.springframework.util.Assert;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditStatus;
import com.zfsoft.hrm.manoeuvre.configInfo.query.AuditStatusQuery;
import com.zfsoft.hrm.manoeuvre.configInfo.service.svcinterface.IAuditStatusService;
import com.zfsoft.hrm.manoeuvre.exception.ManoeuvreException;
import com.zfsoft.util.base.StringUtil;

public class AuditStatusAction extends HrmAction {
	
	private static final long serialVersionUID = 1436307650397865940L;

	private AuditStatus info;
	
	private AuditStatusQuery query;
	
	private String showType;
	
	private String sid;
	
	private PageList<AuditStatus> page;
	
	private IAuditStatusService auditStatusService;
	
	private static final String SHOW_PAGE = "show";
	
	private static final String SHOWWINERROR = "showWinError";

	
	public String list(){
		if(query != null){
			query.setPerPageSize(10);
			page = auditStatusService.getPage(query);
		}
		else{
			page = new PageList<AuditStatus>();
		}
		Paginator pag = page.getPaginator();
		pag.setItemsPerPage(10);
		page.setPaginator(pag);
		AuditStatusQuery pquery = query;
		getValueStack().set("pquery", pquery);
		this.setInActionContext("p_paginator", page.getPaginator());
		return LIST_PAGE;
	} 
	
	public String show(){
		Assert.isTrue(!StringUtil.isEmpty(sid), "未找到指定信息");
		info = auditStatusService.getById(sid);
		AuditStatusQuery pquery = query;
		getValueStack().set("pquery", pquery);
		return SHOW_PAGE;
	}
	
	public String edit(){
		try{
			if(sid != null && !"".equals(sid)){
				info = auditStatusService.getById(sid);
			}
			if(sid == null || "".equals(sid) || info == null){
				info = new AuditStatus();
			}
			return EDIT_PAGE;
		}catch(Exception e){
			getValueStack().set("message", e.getMessage());
			return SHOWWINERROR;
		}
	}
	
	public String save(){
		if(info == null){
			throw new ManoeuvreException("提交信息不可为空");
		}
		else if(info.getManoeuvreInfo() == null || info.getManoeuvreInfo().getGuid() == null || "".equals(info.getManoeuvreInfo().getGuid())){
			throw new ManoeuvreException("未找到所属进修信息");
		}
		else if(info.getTaskNodeName() == null || "".equals(info.getTaskNodeName())){
			throw new ManoeuvreException("未找到审核环节信息");
		}
		else if(info.getAuditTime() == null){
			throw new ManoeuvreException("审核时间不可为空");
		}
		else if(info.getResult() == null || "".equals(info.getResult())){
			throw new ManoeuvreException("审核结果不可为空");
		}
		if(info.getSid() == null || "".equals(info.getSid())){
			setSuccessMessage(auditStatusService.add(info) ? "审核完成" : "审核未完成");
		}
		else{
			setSuccessMessage(auditStatusService.modify(info) ? "审核状态修改完成" : "审核状态修改未完成");
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String delete(){
		Assert.isTrue(!StringUtil.isEmpty(sid), "未选定任何信息");
		setSuccessMessage(auditStatusService.remove(sid) ? "删除审核状态信息成功" : "未成功删除审核状态信息");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getShowType() {
		return showType;
	}

	/**
	 * 设置
	 * @param showType 
	 */
	public void setShowType(String showType) {
		this.showType = showType;
	}

	/**
	 * 返回
	 * @return 
	 */
	public AuditStatus getInfo() {
		return info;
	}

	/**
	 * 设置
	 * @param info 
	 */
	public void setInfo(AuditStatus info) {
		this.info = info;
	}

	/**
	 * 返回
	 * @return 
	 */
	public AuditStatusQuery getQuery() {
		return query;
	}

	/**
	 * 设置
	 * @param query 
	 */
	public void setQuery(AuditStatusQuery query) {
		this.query = query;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getSid() {
		return sid;
	}

	/**
	 * 设置
	 * @param sid 
	 */
	public void setSid(String sid) {
		this.sid = sid;
	}

	/**
	 * 返回
	 * @return 
	 */
	public PageList<AuditStatus> getPage() {
		return page;
	}

	/**
	 * 设置
	 * @param page 
	 */
	public void setPage(PageList<AuditStatus> page) {
		this.page = page;
	}

	/**
	 * 返回
	 * @return 
	 */
	public IAuditStatusService getAuditStatusService() {
		return auditStatusService;
	}

	/**
	 * 设置
	 * @param auditStatusService 
	 */
	public void setAuditStatusService(IAuditStatusService auditStatusService) {
		this.auditStatusService = auditStatusService;
	}

}
