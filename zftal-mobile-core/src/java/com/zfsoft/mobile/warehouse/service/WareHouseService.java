package com.zfsoft.mobile.warehouse.service;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.warehouse.entity.WareHouseEntity;

public interface WareHouseService {

	PageList<WareHouseEntity> getList(WareHouseEntity entity);
	
	WareHouseEntity findById(WareHouseEntity entity);

	void insertWareHouse(WareHouseEntity entity);
	
	int updateWareHouseyId(WareHouseEntity entity);
	
}
