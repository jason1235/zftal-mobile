package com.zfsoft.mobile.interfaceManager.service.impl;

import java.util.Date;
import java.util.List;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.interfaceManager.dao.daointerface.IServiceInterfaceDao;
import com.zfsoft.mobile.interfaceManager.dao.query.ServiceInterfaceQuery;
import com.zfsoft.mobile.interfaceManager.entity.InterfaceManager;
import com.zfsoft.mobile.interfaceManager.entity.ServiceInterface;
import com.zfsoft.mobile.interfaceManager.service.IServiceInterfaceService;

public class ServiceInterfaceServiceImpl implements IServiceInterfaceService{

	private IServiceInterfaceDao serviceInterfaceDao;

	public void setServiceInterfaceDao(IServiceInterfaceDao serviceInterfaceDao) {
		this.serviceInterfaceDao = serviceInterfaceDao;
	}

	public IServiceInterfaceDao getServiceInterfaceDao() {
		return serviceInterfaceDao;
	}

	@Override
	public PageList<ServiceInterface> getList(ServiceInterfaceQuery query) {
		// TODO Auto-generated method stub
		PageList<ServiceInterface> pageList = new PageList<ServiceInterface>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(serviceInterfaceDao.getListCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<ServiceInterface> list = serviceInterfaceDao.getList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public void insert(ServiceInterface model) {
		// TODO Auto-generated method stub
		//check(model);
		model.setClassCjsj(new Date());
		model.setClassCjzid(SessionFactory.getUser().getYhm());
		model.setClassDeleted("0");
		serviceInterfaceDao.insert(model);
	}

	@Override
	public void update(ServiceInterface model) {
		// TODO Auto-generated method stub
		model.setClassGxsj(new Date());
		model.setClassGxzid(SessionFactory.getUser().getYhm());
		serviceInterfaceDao.update(model);
	}

	@Override
	public void remove(ServiceInterfaceQuery query) {
		// TODO Auto-generated method stub
		query.setClassGxsj(new Date());
		query.setClassGxzid(SessionFactory.getUser().getYhm());
		serviceInterfaceDao.delete(query);
	}
}
