package com.zfsoft.hrm.baseinfo.audit.query;

import com.zfsoft.dao.query.BaseQuery;

/** 
 * 审核意见query
 * @author jinjj
 * @date 2012-10-10 上午11:49:37 
 *  
 */
public class AuditInfoQuery extends BaseQuery {

	private static final long serialVersionUID = -7127877772812055630L;
	private String guid;

	/**
	 * 信息ID
	 * @return
	 */
	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
}
