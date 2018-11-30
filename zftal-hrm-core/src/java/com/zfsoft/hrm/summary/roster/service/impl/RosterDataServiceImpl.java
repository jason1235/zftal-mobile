package com.zfsoft.hrm.summary.roster.service.impl;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.summary.roster.business.IRosterDataBusiness;
import com.zfsoft.hrm.summary.roster.query.RosterDataQuery;
import com.zfsoft.hrm.summary.roster.service.svcinterface.IRosterDataService;

/** 
 * @author jinjj
 * @date 2012-9-10 上午09:42:36 
 *  
 */
public class RosterDataServiceImpl implements IRosterDataService {

	
	private IRosterDataBusiness rosterDataBusiness;
	
	@Override
	public PageList<Map<String, String>> getPageList(RosterDataQuery query) {
		PageList<Map<String, String>> page = rosterDataBusiness.getPage(query);
		return page;
	}
	
	@Override
	public List<Map<String, String>> getList(RosterDataQuery query) {
		List<Map<String, String>> list = rosterDataBusiness.getList(query);
		return list;
	}

	public void setRosterDataBusiness(IRosterDataBusiness rosterDataBusiness) {
		this.rosterDataBusiness = rosterDataBusiness;
	}

}
