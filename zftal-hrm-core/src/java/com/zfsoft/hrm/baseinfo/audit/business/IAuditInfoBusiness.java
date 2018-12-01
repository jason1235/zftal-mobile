package com.zfsoft.hrm.baseinfo.audit.business;

import java.util.List;

import com.zfsoft.hrm.baseinfo.audit.entity.AuditInfo;
import com.zfsoft.hrm.baseinfo.audit.query.AuditInfoQuery;

/** 
 * 审核意见
 * @author jinjj
 * @date 2012-10-10 下午01:34:12 
 *  
 */
public interface IAuditInfoBusiness {

	/**
	 * 保存
	 * @param info
	 */
	public void save(AuditInfo info);
	
	/**
	 * 查询列表，时间倒序，最多5条
	 * @param query
	 * @return
	 */
	public List<AuditInfo> getList(AuditInfoQuery query);
}
