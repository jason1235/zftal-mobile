package com.zfsoft.hrm.baseinfo.audit.dao;

import java.util.List;

import com.zfsoft.hrm.baseinfo.audit.entity.AuditDefine;
import com.zfsoft.hrm.baseinfo.audit.query.AuditDefineQuery;

/** 
 * 审核定义
 * @author jinjj
 * @date 2012-9-28 上午11:50:23 
 *  
 */
public interface IAuditDefineDao {

	public void insert(AuditDefine define);
	
	public void updateOrder(AuditDefine define);
	
	public AuditDefine getById(String guid);
	
	public void delete(String guid);
	
	public List<AuditDefine> getList(AuditDefineQuery query);
}
