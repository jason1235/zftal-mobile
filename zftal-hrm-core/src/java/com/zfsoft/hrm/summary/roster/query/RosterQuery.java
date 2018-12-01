package com.zfsoft.hrm.summary.roster.query;

import com.zfsoft.dao.query.BaseQuery;

/** 
 * 花名册查询实体
 * @author jinjj
 * @date 2012-8-30 上午11:01:37 
 *  
 */
public class RosterQuery extends BaseQuery {

	private static final long serialVersionUID = -3209563023430220094L;
	private String name;
	
	/* 花名册增加类型  2013-9-5 added by 1021 */
	private String creator;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
	
}
