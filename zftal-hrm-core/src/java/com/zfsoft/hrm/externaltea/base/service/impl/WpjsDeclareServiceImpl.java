package com.zfsoft.hrm.externaltea.base.service.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.externaltea.base.dao.IWpjsDeclareDao;
import com.zfsoft.hrm.externaltea.base.entity.WpjsDeclare;
import com.zfsoft.hrm.externaltea.base.query.WpjsDeclareQuery;
import com.zfsoft.hrm.externaltea.base.service.IWpjsDeclareService;

/** 
 * @author xyj
 * @date 2013-5-16 下午03:38:11 
 *  
 */
public class WpjsDeclareServiceImpl implements IWpjsDeclareService {
	private IWpjsDeclareDao wpjsDeclareDao;
	private IWpjsDeclareService wpjsDeclareService;


	public IWpjsDeclareService getWpjsDeclareService() {
		return wpjsDeclareService;
	}

	public void setWpjsDeclareService(IWpjsDeclareService wpjsDeclareService) {
		this.wpjsDeclareService = wpjsDeclareService;
	}

	public IWpjsDeclareDao getWpjsDeclareDao() {
		return wpjsDeclareDao;
	}

	public void setWpjsDeclareDao(IWpjsDeclareDao wpjsDeclareDao) {
		this.wpjsDeclareDao = wpjsDeclareDao;
	}
		
	public void save(WpjsDeclare declare) {
		WpjsDeclareQuery query = new WpjsDeclareQuery();
		query.setYgh(declare.getYgh());
		query.setXm(declare.getXm());
		query.setBmdm(declare.getBmdm());
		query.setXl(declare.getXl());
		query.setXw(declare.getXw());
		query.setZyjszw(declare.getZyjszw());
	
		wpjsDeclareDao.insert(declare);
	}

	@Override
	public void update(WpjsDeclare declare) {
		wpjsDeclareDao.update(declare);
	}

	@Override
	public WpjsDeclare getById(String id) {
		WpjsDeclare declare = wpjsDeclareDao.getById(id);
		return declare;
	}

	@Override
	public PageList<WpjsDeclare> getPageList(WpjsDeclareQuery query) {
		PageList<WpjsDeclare> pageList = new PageList<WpjsDeclare>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(wpjsDeclareDao.getPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<WpjsDeclare> list = wpjsDeclareDao.getPagingList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public void delete(String id) throws RuntimeException {
		WpjsDeclare declare = wpjsDeclareDao.getById(id);
		wpjsDeclareDao.delete(declare.getId());
		
	}

	@Override
	public PageList<WpjsDeclare> findWpjsDeclare(WpjsDeclareQuery query) {
		PageList<WpjsDeclare> pageList = new PageList<WpjsDeclare>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(wpjsDeclareDao.getPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<WpjsDeclare> list = wpjsDeclareDao.findWpjsDeclare(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}
	
	@Override
	public PageList<WpjsDeclare> findWpjsDeclareByTime(WpjsDeclareQuery query) {
		PageList<WpjsDeclare> pageList = new PageList<WpjsDeclare>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(wpjsDeclareDao.getPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<WpjsDeclare> list = wpjsDeclareDao.findWpjsDeclareByTime(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}	
}
