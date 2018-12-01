package com.zfsoft.mobile.interfaceManager.dao.daointerface;

import java.util.List;

import com.zfsoft.mobile.interfaceManager.dao.query.InterfaceManagerQuery;
import com.zfsoft.mobile.interfaceManager.entity.InterfaceManager;

public interface IInterfaceManagerDao {

	int getListCount(InterfaceManagerQuery query);

	List<InterfaceManager> getList(InterfaceManagerQuery query);

	void insert(InterfaceManager model);

	void update(InterfaceManager model);

	void delete(InterfaceManagerQuery query);

	void updateById(InterfaceManagerQuery query);

	int getUpdateList(InterfaceManagerQuery interfaceQuery);

	int getInsertList(InterfaceManagerQuery interfaceQuery);
	
	InterfaceManager getInterfaceByBM(InterfaceManagerQuery query);

}
