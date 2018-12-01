package com.zfsoft.hrm.summary.roster.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.zfsoft.hrm.summary.roster.business.IRosterConfigBusiness;
import com.zfsoft.hrm.summary.roster.business.IRosterConfigRelationBusiness;
import com.zfsoft.hrm.summary.roster.entity.RosterConfig;
import com.zfsoft.hrm.summary.roster.entity.RosterConfigRelation;
import com.zfsoft.hrm.summary.roster.query.RosterConfigQuery;
import com.zfsoft.hrm.summary.roster.query.RosterConfigRelationQuery;
import com.zfsoft.hrm.summary.roster.service.svcinterface.IRosterConfigRelationService;

/** 
 * 花名册条件关系service
 * @author jinjj
 * @date 2012-9-6 下午02:03:33 
 *  
 */
public class RosterConfigRelationServiceImpl implements
		IRosterConfigRelationService {

	private IRosterConfigRelationBusiness configRelationBusiness;
	private IRosterConfigBusiness rosterConfigBusiness;
	
	@Override
	public void save(RosterConfigRelation relation) {
		configRelationBusiness.save(relation);
	}

	@Override
	public void delete(RosterConfigRelation relation) {
		configRelationBusiness.delete(relation);
	}

	@Override
	public List<RosterConfig> getList(RosterConfigRelationQuery query) {
		List<RosterConfigRelation> rList =configRelationBusiness.getList(query);
		List<RosterConfig> cList = rosterConfigBusiness.getList(new RosterConfigQuery());
		List<RosterConfig> list = new ArrayList<RosterConfig>();
		
		for(RosterConfigRelation r : rList){
			for(RosterConfig c : cList){
				if(r.getConfigId().equals(c.getGuid())){
					list.add(c);
					break;
				}
			}
		}
		return list;
	}

	public void setConfigRelationBusiness(
			IRosterConfigRelationBusiness configRelationBusiness) {
		this.configRelationBusiness = configRelationBusiness;
	}

	public void setRosterConfigBusiness(IRosterConfigBusiness rosterConfigBusiness) {
		this.rosterConfigBusiness = rosterConfigBusiness;
	}

}
