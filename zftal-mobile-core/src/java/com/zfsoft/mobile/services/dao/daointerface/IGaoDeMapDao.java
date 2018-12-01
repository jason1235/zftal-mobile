package com.zfsoft.mobile.services.dao.daointerface;

import java.util.Collection;
import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.services.entity.GaoDeMaoEntity;

public interface IGaoDeMapDao {

	void deleteAll();

	void insert(GaoDeMaoEntity entity);

	List<GaoDeMaoEntity> getList(GaoDeMaoEntity query);

	int getPageCount(GaoDeMaoEntity query);

	PageList<GaoDeMaoEntity> getPageList(GaoDeMaoEntity query);

}
