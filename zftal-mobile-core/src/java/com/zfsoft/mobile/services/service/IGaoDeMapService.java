package com.zfsoft.mobile.services.service;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.services.entity.GaoDeMaoEntity;

public interface IGaoDeMapService {

	void deleteAll();

	void insert(GaoDeMaoEntity entity);

	List<GaoDeMaoEntity> getList(GaoDeMaoEntity query);

	PageList<GaoDeMaoEntity> getPageList(GaoDeMaoEntity query);
	
}
