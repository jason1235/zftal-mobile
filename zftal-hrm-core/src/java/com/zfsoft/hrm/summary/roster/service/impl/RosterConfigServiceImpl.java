package com.zfsoft.hrm.summary.roster.service.impl;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.summary.roster.business.IRosterConfigBusiness;
import com.zfsoft.hrm.summary.roster.entity.RosterConfig;
import com.zfsoft.hrm.summary.roster.query.RosterConfigQuery;
import com.zfsoft.hrm.summary.roster.service.svcinterface.IRosterConfigService;

/** 
 * 花名册配置service
 * @author jinjj
 * @date 2012-8-31 上午10:00:31 
 *  
 */
public class RosterConfigServiceImpl implements IRosterConfigService {

	private IRosterConfigBusiness rosterConfigBusiness;
	
	@Override
	public void save(RosterConfig config) {
		rosterConfigBusiness.save(config);
	}

	@Override
	public void update(RosterConfig config) {
		rosterConfigBusiness.update(config);
	}

	@Override
	public RosterConfig getById(String guid) {
		RosterConfig config = rosterConfigBusiness.getById(guid);
		return config;
	}

	@Override
	public void delete(String guid) {
		//TODO 需要删除已有关联条件数据
		rosterConfigBusiness.delete(guid);
	}

	@Override
	public PageList<RosterConfig> getPagingList(RosterConfigQuery query) {
		return rosterConfigBusiness.getPagingList(query);
	}
	
	@Override
	public List<RosterConfig> getList(RosterConfigQuery query) {
		List<RosterConfig> list = rosterConfigBusiness.getList(query);
		return list;
	}

	@Override
	public Map<String, Object> getAllCounts() {
		return rosterConfigBusiness.getAllCounts();
	}

	public void setRosterConfigBusiness(IRosterConfigBusiness rosterConfigBusiness) {
		this.rosterConfigBusiness = rosterConfigBusiness;
	}

}
