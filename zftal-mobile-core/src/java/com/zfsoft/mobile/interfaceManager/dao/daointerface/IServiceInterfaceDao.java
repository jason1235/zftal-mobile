package com.zfsoft.mobile.interfaceManager.dao.daointerface;

import java.util.List;

import com.zfsoft.mobile.interfaceManager.dao.query.ServiceInterfaceQuery;
import com.zfsoft.mobile.interfaceManager.entity.ServiceInterface;

public interface IServiceInterfaceDao {

	int getListCount(ServiceInterfaceQuery query);

	List<ServiceInterface> getList(ServiceInterfaceQuery query);

	void insert(ServiceInterface model);

	void update(ServiceInterface model);

	void delete(ServiceInterfaceQuery query);

}
