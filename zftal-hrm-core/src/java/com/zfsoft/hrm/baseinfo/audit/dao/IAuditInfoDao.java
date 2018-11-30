package com.zfsoft.hrm.baseinfo.audit.dao;

import java.util.List;

import com.zfsoft.hrm.baseinfo.audit.entity.AuditInfo;
import com.zfsoft.hrm.baseinfo.audit.query.AuditInfoQuery;

/** 
 * 审核意见DAO
 * @author jinjj
 * @date 2012-10-9 下午02:50:20 
 *  
 */
public interface IAuditInfoDao {

	/**
	 * 插入
	 * @param process
	 */
	public void insert(AuditInfo info);
	
	/**
	 * 查询列表
	 * @param query
	 * @return
	 */
	public List<AuditInfo> getList(AuditInfoQuery query);
	
}
