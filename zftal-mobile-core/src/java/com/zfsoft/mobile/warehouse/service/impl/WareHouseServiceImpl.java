package com.zfsoft.mobile.warehouse.service.impl;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.ballot.entity.Ballot;
import com.zfsoft.mobile.warehouse.dao.WareHouseDao;
import com.zfsoft.mobile.warehouse.entity.WareHouseEntity;
import com.zfsoft.mobile.warehouse.service.WareHouseService;

public class WareHouseServiceImpl implements WareHouseService{

	private WareHouseDao dao;
	
	@Override
	public PageList<WareHouseEntity> getList(WareHouseEntity entity) {
		PageList<WareHouseEntity> list = new PageList<WareHouseEntity>();
		Paginator paginator = new Paginator();
		if (entity != null) {
			paginator.setItemsPerPage(entity.getPerPageSize());
			paginator.setPage(entity.getToPage());
			paginator.setItems(dao.getListCount(entity));
			list.setPaginator(paginator);
			
			if (paginator.getBeginIndex() <= paginator.getItems()) {
				entity.setStartRow(paginator.getBeginIndex());
				entity.setEndRow(paginator.getEndIndex());
				list.addAll(dao.getList(entity));
			}
		}
		return list;
	}



	@Override
	public WareHouseEntity findById(WareHouseEntity entity) {
		return dao.findById(entity);
	}
	



	@Override
	public void insertWareHouse(WareHouseEntity entity) {
		dao.insertWareHouse(entity);
	}



	@Override
	public int updateWareHouseyId(WareHouseEntity entity) {
		return dao.updateWareHouseyId(entity);
	}
	
	/*-------------------------------------------------------------------------*/
	
	
	public WareHouseDao getDao() {
		return dao;
	}

	public void setDao(WareHouseDao dao) {
		this.dao = dao;
	}
}
