package com.zfsoft.mobile.myportal.dao;

import java.util.List;
import java.util.Map;

import com.zfsoft.mobile.myportal.entity.PortalRelativeRole;

public interface IPortalRelativeRoleDao {

	List<PortalRelativeRole> getPagedList(PortalRelativeRole query);

	int getPagedListWeiCount(PortalRelativeRole query);
							 
	List<PortalRelativeRole> getPagedListWeifp(PortalRelativeRole query);

	void insert(Map<String, Object> param);

	void delete(Map<String, Object> param);

	void insertYhqx(Map<String, Object> param);

	void deleteYhqx(Map<String, Object> param);


}
