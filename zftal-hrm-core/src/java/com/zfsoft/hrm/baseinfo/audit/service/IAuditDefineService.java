package com.zfsoft.hrm.baseinfo.audit.service;

import java.util.List;

import com.zfsoft.hrm.baseinfo.audit.entity.AuditConfigInfo;
import com.zfsoft.hrm.baseinfo.audit.entity.AuditDefine;
import com.zfsoft.hrm.baseinfo.audit.query.AuditDefineQuery;

/** 
 * 审核定义
 * @author jinjj
 * @date 2012-9-28 下午02:01:07 
 *  
 */
public interface IAuditDefineService {

	public void save(AuditDefine define);
	
	public void delete(String guid);
	
	public List<AuditDefine> getList(AuditDefineQuery query);
	
	public List<AuditConfigInfo> getPagingList(AuditDefineQuery query);
}
