package com.zfsoft.hrm.summary.roster.service.svcinterface;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.summary.roster.query.RosterDataQuery;

/** 
 * @author jinjj
 * @date 2012-9-10 上午09:39:28 
 *  
 */
public interface IRosterDataService {

	public PageList<Map<String,String>> getPageList(RosterDataQuery query);
	
	public List<Map<String,String>> getList(RosterDataQuery query);
}
