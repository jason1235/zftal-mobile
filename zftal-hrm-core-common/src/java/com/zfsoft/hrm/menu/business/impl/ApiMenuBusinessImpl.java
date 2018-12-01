package com.zfsoft.hrm.menu.business.impl;

import java.util.List;

import com.zfsoft.dao.entities.ApiIndexModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.menu.business.IApiMenuBusiness;
import com.zfsoft.hrm.menu.dao.IApiMenuDao;

public class ApiMenuBusinessImpl implements IApiMenuBusiness{

	private IApiMenuDao apiMenuDao;

	public void setApiMenuDao(IApiMenuDao apiMenuDao) {
		this.apiMenuDao = apiMenuDao;
	}

	public IApiMenuDao getApiMenuDao() {
		return apiMenuDao;
	}

	@Override
	public PageList<ApiIndexModel> getMenu(ApiIndexModel query) {
		PageList<ApiIndexModel> pageList = new PageList<ApiIndexModel>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(apiMenuDao.getListCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<ApiIndexModel> list = apiMenuDao.getList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public void remove(String gnmkdm) {
		apiMenuDao.delete(gnmkdm);
	}

	@Override
	public void insertMenu(ApiIndexModel model) {
		apiMenuDao.insertMenu(model);
	}

	@Override
	public void modify(ApiIndexModel model) {
		apiMenuDao.update(model);
	}

	@Override
	public void updateSfqy(ApiIndexModel model) {
		apiMenuDao.updateSfqy(model);
	}

	@Override
	public List<ApiIndexModel> getAllList() {
		return apiMenuDao.getAllList();
	}
	
}
