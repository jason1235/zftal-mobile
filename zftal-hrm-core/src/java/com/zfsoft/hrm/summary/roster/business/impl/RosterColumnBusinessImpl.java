package com.zfsoft.hrm.summary.roster.business.impl;

import java.util.List;

import com.zfsoft.hrm.summary.roster.business.IRosterColumnBusiness;
import com.zfsoft.hrm.summary.roster.dao.daointerface.IRosterColumnDao;
import com.zfsoft.hrm.summary.roster.entity.RosterColumn;

/**
 * 花名册字段business实现 
 * @author jinjj
 * @date 2012-9-10 下午05:22:04 
 *  
 */
public class RosterColumnBusinessImpl implements IRosterColumnBusiness {

	private IRosterColumnDao rosterColumnDao;
	
	@Override
	public void save(RosterColumn column) {
		RosterColumn old = rosterColumnDao.getById(column);
		if(old == null){
			rosterColumnDao.insert(column);
		}
	}

	@Override
	public void delete(RosterColumn column) {
		rosterColumnDao.delete(column);
	}

	@Override
	public void updateOrder(RosterColumn column) {
		RosterColumn old = rosterColumnDao.getById(column);
		column.setSort(old.getSort()==null?"":old.getSort());
		rosterColumnDao.update(column);
	}

	@Override
	public void updateSort(RosterColumn column) {
		RosterColumn old = rosterColumnDao.getById(column);
		column.setOrder(old.getOrder()==null?"":old.getOrder());
		rosterColumnDao.update(column);
	}

	@Override
	public List<RosterColumn> getList(String rosterId) {
		List<RosterColumn> list = rosterColumnDao.getList(rosterId);
		return list;
	}

	public void setRosterColumnDao(IRosterColumnDao rosterColumnDao) {
		this.rosterColumnDao = rosterColumnDao;
	}

}
