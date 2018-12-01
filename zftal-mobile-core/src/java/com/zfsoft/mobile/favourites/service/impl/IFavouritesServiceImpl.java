package com.zfsoft.mobile.favourites.service.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.favourites.dao.IFavouritesDao;
import com.zfsoft.mobile.favourites.entity.FavouritesEntity;
import com.zfsoft.mobile.favourites.service.IFavouritesService;

/**
 * 我的收藏实现类
 * @author yangbilin
 */
public class IFavouritesServiceImpl implements IFavouritesService{

	private IFavouritesDao favourDao;
	
	@Override
	public PageList<FavouritesEntity> getPageList(FavouritesEntity query) {
		// TODO Auto-generated method stub
		PageList<FavouritesEntity> pagelist = new PageList<FavouritesEntity>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			//当前页数
			paginator.setPage(query.getToPage());
			//该列表的所有项数量
			paginator.setItems(favourDao.getPageCount(query));
			pagelist.setPaginator(paginator);
			//当前页码大于总页数，返回列表
			if((Integer)query.getToPage() > paginator.getPages()){
				return pagelist;
			}
			/**
			 * 当前页的起始项序号小于总项数量
			 * 设置当前页的起始和结束序号并返回页面
			 */
			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<FavouritesEntity> list = favourDao.getPageList(query);
				pagelist.addAll(list);
			}
		}
		return pagelist;
	}

	@Override
	public FavouritesEntity findById(FavouritesEntity query) {
		// TODO Auto-generated method stub
		return favourDao.findById(query);
	}

	@Override
	public void saveOrUpdate(FavouritesEntity query) {
		// TODO Auto-generated method stub
		//if(StringUtils.isBlank(query.getFavourid())){
			favourDao.insert(query);
		//}
	}

	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub
		favourDao.delete(id);
	}

	public IFavouritesDao getFavourDao() {
		return favourDao;
	}

	public void setFavourDao(IFavouritesDao favourDao) {
		this.favourDao = favourDao;
	}

	@Override
	public List<FavouritesEntity> checkAndGetMd5Str(FavouritesEntity query) {
		// TODO Auto-generated method stub
		return favourDao.checkAndGetMd5Str(query);
	}


}
