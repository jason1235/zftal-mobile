package com.zfsoft.hrm.manoeuvre.configInfo.business.businessinterface;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditConfigOrgInfo;
import com.zfsoft.hrm.manoeuvre.configInfo.query.AuditConfigOrgQuery;

public interface IAuditConfigOrgBusiness {

	public boolean add(AuditConfigOrgInfo info) throws RuntimeException;
	
	public boolean modify(AuditConfigOrgInfo info) throws RuntimeException;
	
	public void remove(String aoid) throws RuntimeException;
	
	public List<AuditConfigOrgInfo> getList(AuditConfigOrgQuery query) throws RuntimeException;
	
	public PageList<AuditConfigOrgInfo> getPage(AuditConfigOrgQuery query) throws RuntimeException;
	
	public AuditConfigOrgInfo getById(String aoid) throws RuntimeException;
	
	public void removeByAid(String aid) throws RuntimeException;
}
