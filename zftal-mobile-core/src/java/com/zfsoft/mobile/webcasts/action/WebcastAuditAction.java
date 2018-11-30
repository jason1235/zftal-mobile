package com.zfsoft.mobile.webcasts.action;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.webcasts.entity.WebcastsAuditEntity;
import com.zfsoft.mobile.webcasts.service.IWebcastsAuditService;
import com.zfsoft.util.base.StringUtil;


public class WebcastAuditAction extends HrmAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String op;
	private WebcastsAuditEntity model = new WebcastsAuditEntity();
	private WebcastsAuditEntity query = new WebcastsAuditEntity();
	private PageList<WebcastsAuditEntity> pageList;
	private IWebcastsAuditService auditService;
	
	public String list(){
		pageList = auditService.getPageList(query);
		return "list";
	}
	
	public String toModify(){
		List<WebcastsAuditEntity> list = auditService.getPageList(query);
		if(list!=null&&list.size()>0){
			op="modify";
			model = list.get(0);
		}
		return "edit";
	}
	
	
	public String audit(){
		if(StringUtil.isNotBlank(query.getAppid())){
			model = auditService.findById(query);
			if(model!=null){
				/*if(!"0".equals(model.getIsaudit())){
					this.setErrorMessage("当前数据已经审核,不能重复审核");
					this.getValueStack().set("data", this.getMessage());
					return "data";
				}*/
				auditService.audit(query);
				this.setSuccessMessage("操作成功");
			}
		}
		this.getValueStack().set("data", getMessage());
		return "data";
	}
	
	public String delete(){
		if(StringUtil.isNotBlank(query.getAppid())){
			auditService.delete(query.getAppid());
			this.setSuccessMessage("成功删除数据！");
		}else{
			this.setSuccessMessage("获取删除数据失败");
		}
		this.getValueStack().set("data", getMessage());
		return "data";
	}
	
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public WebcastsAuditEntity getModel() {
		return model;
	}
	public void setModel(WebcastsAuditEntity model) {
		this.model = model;
	}
	public WebcastsAuditEntity getQuery() {
		return query;
	}
	public void setQuery(WebcastsAuditEntity query) {
		this.query = query;
	}
	public PageList<WebcastsAuditEntity> getPageList() {
		return pageList;
	}
	public void setPageList(PageList<WebcastsAuditEntity> pageList) {
		this.pageList = pageList;
	}
	public IWebcastsAuditService getAuditService() {
		return auditService;
	}
	public void setAuditService(IWebcastsAuditService auditService) {
		this.auditService = auditService;
	}
	
	
}
