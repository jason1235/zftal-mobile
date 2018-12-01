package com.zfsoft.mobile.services.service.impl;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.services.dao.daointerface.IGaoDeMapDao;
import com.zfsoft.mobile.services.entity.GaoDeMaoEntity;
import com.zfsoft.mobile.services.service.IGaoDeMapService;
import com.zfsoft.mobile.txl.entity.XGDTxl;

public class GaoDeMapServiceImpl implements IGaoDeMapService {
	
	private IGaoDeMapDao gaoDeMapDao;

	@Override
	public void deleteAll() {
		gaoDeMapDao.deleteAll();
	}

	@Override
	public void insert(GaoDeMaoEntity entity) {
		gaoDeMapDao.insert(entity);
	}

	public void setGaoDeMapDao(IGaoDeMapDao gaoDeMapDao) {
		this.gaoDeMapDao = gaoDeMapDao;
	}

	public IGaoDeMapDao getGaoDeMapDao() {
		return gaoDeMapDao;
	}

	@Override
	public List<GaoDeMaoEntity> getList(GaoDeMaoEntity query) {
		return gaoDeMapDao.getList(query);
	}

	@Override
	public PageList<GaoDeMaoEntity> getPageList(
			GaoDeMaoEntity query) {
		PageList<GaoDeMaoEntity> pageList = new PageList<GaoDeMaoEntity>();
		Paginator paginator = new Paginator();
		if (query != null) {
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(gaoDeMapDao.getPageCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<GaoDeMaoEntity> list = gaoDeMapDao.getPageList(query);
				pageList.addAll(list);
			}
		}
		
		return pageList;
	}

}
