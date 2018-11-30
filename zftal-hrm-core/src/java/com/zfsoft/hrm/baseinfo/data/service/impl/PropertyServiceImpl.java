package com.zfsoft.hrm.baseinfo.data.service.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.baseinfo.data.dao.IPropertyDao;
import com.zfsoft.hrm.baseinfo.data.entity.Property;
import com.zfsoft.hrm.baseinfo.data.service.IPropertyService;

public class PropertyServiceImpl implements IPropertyService {
	private IPropertyDao propertyDao;
	
	public IPropertyDao getPropertyDao() {
		return propertyDao;
	}

	public void setPropertyDao(IPropertyDao propertyDao) {
		this.propertyDao = propertyDao;
	}

	@Override
	public PageList<Property> getPagedProperty(Property property) {
		PageList<Property> pageList = new PageList<Property>();
		pageList.addAll(propertyDao.getPagedProperty(property));
		Paginator paginator = new Paginator();
		paginator.setItemsPerPage(property.getShowCount());
		paginator.setPage(property.getCurrentPage());
		paginator.setItems(property.getTotalResult());
		pageList.setPaginator(paginator);
		return pageList;
	}

	@Override
	public void delete(String id) {
		propertyDao.delete(id);
	}

	@Override
	public void add(Property property) {
		propertyDao.insert(property);
	}

	@Override
	public List<Property> getByMbid(String mbid) {
		return propertyDao.getProperties(mbid);
	}

	@Override
	public List<String> getZdmc(String mbid) {
		return propertyDao.getZdmc(mbid);
	}
}
