package com.zfsoft.mobile.interfaceManager.service;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.interfaceManager.dao.query.ServiceInterfaceQuery;
import com.zfsoft.mobile.interfaceManager.entity.ServiceInterface;

public interface IServiceInterfaceService {

	PageList<ServiceInterface> getList(ServiceInterfaceQuery query);

	void insert(ServiceInterface model);

	void update(ServiceInterface model);

	void remove(ServiceInterfaceQuery query);

}
