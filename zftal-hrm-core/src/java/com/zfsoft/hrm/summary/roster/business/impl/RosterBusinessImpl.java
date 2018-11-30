package com.zfsoft.hrm.summary.roster.business.impl;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.summary.roster.business.IRosterBusiness;
import com.zfsoft.hrm.summary.roster.dao.daointerface.IRosterDao;
import com.zfsoft.hrm.summary.roster.entity.Roster;
import com.zfsoft.hrm.summary.roster.query.RosterQuery;

/** 
 * 花名册business
 * @author jinjj
 * @date 2012-8-30 上午11:36:11 
 *  
 */
public class RosterBusinessImpl implements IRosterBusiness {

	private IRosterDao rosterDao;
	
	@Override
	public void save(Roster roster) {
		rosterDao.insert(roster);
	}

	@Override
	public void update(Roster roster) {
		rosterDao.update(roster);
	}

	@Override
	public Roster getById(String guid) {
		Roster roster = rosterDao.getById(guid);
		return roster;
	}

	@Override
	public void delete(String guid) {
		rosterDao.delete(guid);
	}

	@Override
	public PageList<Roster> getPagingList(RosterQuery query) {
		PageList<Roster> pageList = new PageList<Roster>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(rosterDao.getPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				pageList.addAll(rosterDao.getPagingList(query));
			}
		}
		return pageList;
	}

	public void setRosterDao(IRosterDao rosterDao) {
		this.rosterDao = rosterDao;
	}

}
