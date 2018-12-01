package com.zfsoft.hrm.baseinfo.data.service;

import java.util.List;


import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.data.entity.Property;

public interface IPropertyService {
	public PageList<Property> getPagedProperty(Property property);
	
	public void delete(String id);
	
	public void add(Property property);
	
	public List<Property> getByMbid(String id);
	
	public List<String> getZdmc(String mbid);
}
