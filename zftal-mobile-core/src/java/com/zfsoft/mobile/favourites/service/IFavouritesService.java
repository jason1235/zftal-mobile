package com.zfsoft.mobile.favourites.service;


import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.favourites.entity.FavouritesEntity;

public interface IFavouritesService {
	
	PageList<FavouritesEntity> getPageList(FavouritesEntity query);
	FavouritesEntity findById(FavouritesEntity query);
	
	List<FavouritesEntity> checkAndGetMd5Str(FavouritesEntity query);
	void saveOrUpdate(FavouritesEntity query);
	void remove(String ids);
}
