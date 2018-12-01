package com.zfsoft.hrm.baseinfo.audit.business;

import java.util.List;

import com.zfsoft.hrm.baseinfo.audit.entity.AuditDefine;
import com.zfsoft.hrm.baseinfo.audit.query.AuditDefineQuery;

/** 
 * @author jinjj
 * @date 2012-9-28 下午01:47:41 
 *  
 */
public interface IAuditDefineBusiness {

	public void save(AuditDefine define);
	
	public void delete(String guid);
	
	public List<AuditDefine> getList(AuditDefineQuery query);
	
	public AuditDefine getById(String guid);
}
