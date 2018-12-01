package com.zfsoft.mobile.myportal.service.impl;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.myportal.dao.IPortalRelativeRoleDao;
import com.zfsoft.mobile.myportal.entity.MyPortal;
import com.zfsoft.mobile.myportal.entity.PortalRelativeRole;
import com.zfsoft.mobile.myportal.service.IPortalRelativeRoleService;
import com.zfsoft.mobile.services.entity.ServiceManager;

public class PortalRelativeRoleServiceImpl implements
		IPortalRelativeRoleService {
	
	private IPortalRelativeRoleDao portalRelativeRoleDao;

	@Override
	public List<PortalRelativeRole> getPagedList(PortalRelativeRole query) {
		// TODO Auto-generated method stub
		return portalRelativeRoleDao.getPagedList(query);
	}

	@Override
	public PageList<PortalRelativeRole> getPagedListWeifp(PortalRelativeRole query) {
		// TODO Auto-generated method stub
		PageList<PortalRelativeRole> pageList = new PageList<PortalRelativeRole>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(portalRelativeRoleDao.getPagedListWeiCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<PortalRelativeRole> list = portalRelativeRoleDao.getPagedListWeifp(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public void insert(Map<String, Object> param) {
			portalRelativeRoleDao.insert(param);
			portalRelativeRoleDao.insertYhqx(param);
	}

	@Override
	public void delete(Map<String, Object> param) {
			portalRelativeRoleDao.delete(param);
			portalRelativeRoleDao.deleteYhqx(param);
	}

	public void setPortalRelativeRoleDao(IPortalRelativeRoleDao portalRelativeRoleDao) {
		this.portalRelativeRoleDao = portalRelativeRoleDao;
	}

	public IPortalRelativeRoleDao getPortalRelativeRoleDao() {
		return portalRelativeRoleDao;
	}

	
}
