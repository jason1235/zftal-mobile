package com.zfsoft.hrm.summary.roster.service.impl;

import java.util.Date;
import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.summary.roster.business.IRosterBusiness;
import com.zfsoft.hrm.summary.roster.business.IRosterColumnBusiness;
import com.zfsoft.hrm.summary.roster.business.IRosterConfigRelationBusiness;
import com.zfsoft.hrm.summary.roster.business.IRosterParamBusiness;
import com.zfsoft.hrm.summary.roster.entity.Roster;
import com.zfsoft.hrm.summary.roster.entity.RosterColumn;
import com.zfsoft.hrm.summary.roster.entity.RosterConfigRelation;
import com.zfsoft.hrm.summary.roster.entity.RosterParam;
import com.zfsoft.hrm.summary.roster.query.RosterConfigRelationQuery;
import com.zfsoft.hrm.summary.roster.query.RosterQuery;
import com.zfsoft.hrm.summary.roster.service.svcinterface.IRosterService;

/** 
 * @author jinjj
 * @date 2012-8-30 上午11:46:23 
 *  
 */
public class RosterServiceImpl implements IRosterService {

	private IRosterBusiness rosterBusiness;
	private IRosterColumnBusiness rosterColumnBusiness;
	private IRosterConfigRelationBusiness rosterConfigRelationBusiness;
	private IRosterParamBusiness rosterParamBusiness;
	
	@Override
	public void save(Roster roster) {
		rosterBusiness.save(roster);
	}

	@Override
	public void update(Roster roster) {
		rosterBusiness.update(roster);
	}

	@Override
	public Roster getById(String guid) {
		return rosterBusiness.getById(guid);
	}

	@Override
	public void delete(String guid) {
		//TODO 关联删除字段，条件
		rosterBusiness.delete(guid);
		//删除选择条件
		RosterConfigRelationQuery configRelationQuery = new RosterConfigRelationQuery();
		configRelationQuery.setRosterId(guid);
		List<RosterConfigRelation> configRelationList = rosterConfigRelationBusiness.getList(configRelationQuery);
		for(RosterConfigRelation relation : configRelationList){
			rosterConfigRelationBusiness.delete(relation);
		}
		//删除字段
		List<RosterColumn> columnList = rosterColumnBusiness.getList(guid);
		for(RosterColumn column : columnList){
			rosterColumnBusiness.delete(column);
		}
		//删除参数
		RosterParam param = new RosterParam();
		param.setGuid(guid);
		rosterParamBusiness.delete(param);
	}

	@Override
	public PageList<Roster> getPagingList(RosterQuery query) {
		return rosterBusiness.getPagingList(query);
	}

	public void setRosterBusiness(IRosterBusiness rosterBusiness) {
		this.rosterBusiness = rosterBusiness;
	}

	/**
	 * 另存为花名册
	 * @param roster
	 */
	public void saveOther(Roster roster,RosterParam param){
		String guid = roster.getGuid();//待复制的模板
		//新增花名册
		rosterBusiness.save(roster);
		String newId = roster.getGuid();
		//复制条件
		RosterConfigRelationQuery configRelationQuery = new RosterConfigRelationQuery();
		configRelationQuery.setRosterId(guid);
		List<RosterConfigRelation> configRelationList = rosterConfigRelationBusiness.getList(configRelationQuery);
		for(RosterConfigRelation relation : configRelationList){
			relation.setRosterId(newId);
			relation.setCreatetime(new Date());
			rosterConfigRelationBusiness.save(relation);
		}
		//复制字段
		List<RosterColumn> columnList = rosterColumnBusiness.getList(guid);
		for(RosterColumn column : columnList){
			column.setRosterId(newId);
			rosterColumnBusiness.save(column);
		}
		//参数复制
		param.setGuid(newId);
		rosterParamBusiness.save(param);
	}

	public void setRosterColumnBusiness(IRosterColumnBusiness rosterColumnBusiness) {
		this.rosterColumnBusiness = rosterColumnBusiness;
	}

	public void setRosterConfigRelationBusiness(
			IRosterConfigRelationBusiness rosterConfigRelationBusiness) {
		this.rosterConfigRelationBusiness = rosterConfigRelationBusiness;
	}

	public void setRosterParamBusiness(IRosterParamBusiness rosterParamBusiness) {
		this.rosterParamBusiness = rosterParamBusiness;
	}

}
