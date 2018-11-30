package com.zfsoft.mobile.interfaceManager.service;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.interfaceManager.dao.query.InterfaceManagerQuery;
import com.zfsoft.mobile.interfaceManager.entity.InterfaceManager;

public interface IInterfaceManagerService {

	PageList<InterfaceManager> getList(InterfaceManagerQuery query);

	void insert(InterfaceManager model);

	void update(InterfaceManager model);

	void remove(InterfaceManagerQuery query);

	void updateQiYong(InterfaceManagerQuery query);

	void updateTingYong(InterfaceManagerQuery query);
	
	InterfaceManager getInterfaceByBM(InterfaceManagerQuery query);

}
