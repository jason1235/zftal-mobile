package com.zfsoft.mobile.myportal.service;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.myportal.entity.MyPortal;
import com.zfsoft.mobile.myportal.entity.PortalRelativeRole;

public interface IPortalRelativeRoleService {

	List<PortalRelativeRole> getPagedList(PortalRelativeRole query);

	PageList<PortalRelativeRole> getPagedListWeifp(PortalRelativeRole query);

	void insert(Map<String, Object> param);

	void delete(Map<String, Object> param);

}
