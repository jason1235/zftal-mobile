package com.zfsoft.mobile.favourites.dao;


import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.favourites.entity.FavouritesEntity;

public interface IFavouritesDao {

	int getPageCount(FavouritesEntity query);
	PageList<FavouritesEntity> getPageList(FavouritesEntity query);
	
	List<FavouritesEntity> checkAndGetMd5Str(FavouritesEntity query);
	void insert(FavouritesEntity query);
	
	void delete(String id);
	
	FavouritesEntity findById(FavouritesEntity query);
}
