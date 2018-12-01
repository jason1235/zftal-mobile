package com.zfsoft.hrm.summary.roster.query;

import com.zfsoft.dao.query.BaseQuery;

/** 
 * @author jinjj
 * @date 2012-9-6 上午11:56:19 
 *  
 */
public class RosterConfigRelationQuery extends BaseQuery {

	private static final long serialVersionUID = -765385241899465934L;

	private String rosterId;

	public String getRosterId() {
		return rosterId;
	}

	public void setRosterId(String rosterId) {
		this.rosterId = rosterId;
	}
	
}
