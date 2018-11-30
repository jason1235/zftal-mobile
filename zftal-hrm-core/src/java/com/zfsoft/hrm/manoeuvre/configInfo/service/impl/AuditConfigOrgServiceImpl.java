package com.zfsoft.hrm.manoeuvre.configInfo.service.impl;

import java.util.List;

import org.springframework.util.Assert;

import com.zfsoft.hrm.manoeuvre.configInfo.business.businessinterface.IAuditConfigOrgBusiness;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditConfigOrgInfo;
import com.zfsoft.hrm.manoeuvre.configInfo.query.AuditConfigOrgQuery;
import com.zfsoft.hrm.manoeuvre.configInfo.service.svcinterface.IAuditConfigOrgService;

public class AuditConfigOrgServiceImpl implements IAuditConfigOrgService {

	private IAuditConfigOrgBusiness auditConfigOrgBusiness;
	
	
	public void setAuditConfigOrgBusiness(
			IAuditConfigOrgBusiness auditConfigOrgBusiness) {
		this.auditConfigOrgBusiness = auditConfigOrgBusiness;
	}
	
	@Override
	public void saveBatch(String aid, String[] selectId){
		auditConfigOrgBusiness.removeByAid(aid);
		if(selectId == null || selectId.length == 0){
			return;
		}
		for (String sid : selectId) {
			AuditConfigOrgInfo info = new AuditConfigOrgInfo();
			info.setAid(aid);
			info.setOid(sid);
			Assert.isTrue(auditConfigOrgBusiness.add(info), "保存可审核部门出错");
		}
	}

	@Override
	public List<AuditConfigOrgInfo> getList(AuditConfigOrgQuery query) {
		return auditConfigOrgBusiness.getList(query);
	}
	
}
