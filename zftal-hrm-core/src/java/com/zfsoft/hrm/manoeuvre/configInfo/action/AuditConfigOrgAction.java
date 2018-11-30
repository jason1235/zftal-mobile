package com.zfsoft.hrm.manoeuvre.configInfo.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.util.Assert;

import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditConfigOrgInfo;
import com.zfsoft.hrm.manoeuvre.configInfo.query.AuditConfigOrgQuery;
import com.zfsoft.hrm.manoeuvre.configInfo.service.svcinterface.IAuditConfigOrgService;
import com.zfsoft.hrm.manoeuvre.configInfo.util.AuditConfigOrgUtil;
import com.zfsoft.util.base.StringUtil;

public class AuditConfigOrgAction extends HrmAction {

	private static final long serialVersionUID = 6872777029755656945L;

	private AuditConfigOrgInfo info = new AuditConfigOrgInfo();
	
	private AuditConfigOrgQuery query = new AuditConfigOrgQuery();
	
	private List<AuditConfigOrgInfo> infoList;
	
	private String[] selectId;
	
	private IAuditConfigOrgService auditConfigOrgService;
	
	
	public String save(){
		Assert.isTrue(!StringUtil.isEmpty(query.getAid()), "未指定任何记录");
		try{
			auditConfigOrgService.saveBatch(query.getAid(), selectId);
			setSuccessMessage("保存成功");
		}catch (Exception e) {
			setErrorMessage("保存失败:" + e.getMessage());
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String load() throws Exception{
		Assert.isTrue(!StringUtil.isEmpty(query.getAid()), "未指定任何信息");
		infoList = auditConfigOrgService.getList(query);
		HashSet<String> orgSet = new HashSet<String>();
		for (AuditConfigOrgInfo aco : infoList) {
			if(!orgSet.contains(aco.getOid())){
				orgSet.add(aco.getOid());
			}
		}
		String html = AuditConfigOrgUtil.getConfigListHtml(null,1,new ArrayList<String>(),orgSet);
		getValueStack().set("orghtml", html);
		return LIST_PAGE;
	}
	
	
	/**
	 * 返回
	 * @return 
	 */
	public AuditConfigOrgInfo getInfo() {
		return info;
	}

	/**
	 * 设置
	 * @param info 
	 */
	public void setInfo(AuditConfigOrgInfo info) {
		this.info = info;
	}

	/**
	 * 返回
	 * @return 
	 */
	public AuditConfigOrgQuery getQuery() {
		return query;
	}

	/**
	 * 设置
	 * @param query 
	 */
	public void setQuery(AuditConfigOrgQuery query) {
		this.query = query;
	}

	/**
	 * 返回
	 * @return 
	 */
	public List<AuditConfigOrgInfo> getInfoList() {
		return infoList;
	}

	/**
	 * 设置
	 * @param infoList 
	 */
	public void setInfoList(List<AuditConfigOrgInfo> infoList) {
		this.infoList = infoList;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String[] getSelectId() {
		return selectId;
	}

	/**
	 * 设置
	 * @param selectIds 
	 */
	public void setSelectId(String[] selectId) {
		this.selectId = selectId;
	}

	/**
	 * 返回
	 * @return 
	 */
	public IAuditConfigOrgService getAuditConfigOrgService() {
		return auditConfigOrgService;
	}

	/**
	 * 设置
	 * @param auditConfigOrgService 
	 */
	public void setAuditConfigOrgService(
			IAuditConfigOrgService auditConfigOrgService) {
		this.auditConfigOrgService = auditConfigOrgService;
	}

}
