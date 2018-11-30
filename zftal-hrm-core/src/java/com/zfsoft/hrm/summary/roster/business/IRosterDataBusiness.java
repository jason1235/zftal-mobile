package com.zfsoft.hrm.summary.roster.business;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.summary.roster.query.RosterDataQuery;

/** 
 * @author jinjj
 * @date 2012-9-7 上午10:43:29 
 *  
 */
public interface IRosterDataBusiness {

	public List<Map<String,String>> getList(RosterDataQuery query);
	
	public PageList<Map<String,String>> getPage(RosterDataQuery query);
}
