package com.zfsoft.mobile.warehouse.dao;

import java.util.List;

import com.zfsoft.mobile.warehouse.entity.WareHouseEntity;

public interface WareHouseDao {
	
	List<WareHouseEntity> getList(WareHouseEntity entity);
	
	int getListCount(WareHouseEntity entity);
	
	void insertWareHouse(WareHouseEntity entity);
	
	int updateWareHouseyId(WareHouseEntity entity);

	WareHouseEntity findById(WareHouseEntity entity);
	
}
