package com.zfsoft.hrm.manoeuvre.configInfo.service.svcinterface;

import java.util.List;

import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditConfigOrgInfo;
import com.zfsoft.hrm.manoeuvre.configInfo.query.AuditConfigOrgQuery;

public interface IAuditConfigOrgService {
	
	public List<AuditConfigOrgInfo> getList(AuditConfigOrgQuery query);
	
	public void saveBatch(String aid, String[] selectId);
}
