package com.zfsoft.hrm.summary.roster.business.impl;

import java.util.List;

import com.zfsoft.hrm.summary.roster.business.IRosterConfigRelationBusiness;
import com.zfsoft.hrm.summary.roster.dao.daointerface.IRosterConfigRelationDao;
import com.zfsoft.hrm.summary.roster.entity.RosterConfigRelation;
import com.zfsoft.hrm.summary.roster.query.RosterConfigRelationQuery;

/** 
 * 花名册条件关系business
 * @author jinjj
 * @date 2012-9-6 下午01:35:07 
 *  
 */
public class RosterConfigRelationBusinessImpl implements
		IRosterConfigRelationBusiness {

	private IRosterConfigRelationDao configRelationDao;
	
	@Override
	public void save(RosterConfigRelation relation) {
		RosterConfigRelation old =configRelationDao.getById(relation);
		if(old == null)
			configRelationDao.insert(relation);
		else{
			//已存在时的操作
		}
	}

	@Override
	public void delete(RosterConfigRelation relation) {
		configRelationDao.delete(relation);
	}

	@Override
	public List<RosterConfigRelation> getList(RosterConfigRelationQuery query) {
		return configRelationDao.getList(query);
	}

	public void setConfigRelationDao(IRosterConfigRelationDao configRelationDao) {
		this.configRelationDao = configRelationDao;
	}

}
