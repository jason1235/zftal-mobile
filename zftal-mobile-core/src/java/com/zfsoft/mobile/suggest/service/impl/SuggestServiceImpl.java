package com.zfsoft.mobile.suggest.service.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.suggest.dao.ISuggestDao;
import com.zfsoft.mobile.suggest.entity.SuggestEntity;
import com.zfsoft.mobile.suggest.entity.suggestPictureEntity;
import com.zfsoft.mobile.suggest.service.ISuggestService;

public class SuggestServiceImpl implements ISuggestService{

	ISuggestDao suggestDao;

	public ISuggestDao getSuggestDao() {
		return suggestDao;
	}

	public void setSuggestDao(ISuggestDao suggestDao) {
		this.suggestDao = suggestDao;
	}

	@Override
	public void insertSuggestPicture(suggestPictureEntity pictureEntity) {
		suggestDao.insertSuggestPicture(pictureEntity);
	}

	@Override
	public void insertSuggestMain(SuggestEntity suggestEntity) {
		suggestDao.insertSuggestMain(suggestEntity);
	}

	@Override
	public PageList<SuggestEntity> getList(SuggestEntity query) {
		PageList<SuggestEntity> pageList = new PageList<SuggestEntity>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(suggestDao.getListCount(query));
			pageList.setPaginator(paginator);
			if((Integer)query.getToPage() > paginator.getPages()){
				return pageList;
			}
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<SuggestEntity> list = suggestDao.getList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public SuggestEntity getDetail(SuggestEntity query) {
		return suggestDao.getDetail(query);
	}

	@Override
	public void reply(SuggestEntity query) {
		suggestDao.reply(query);
	}

	@Override
	public List<suggestPictureEntity> getPictureList(String id) {
		return suggestDao.getPictureList(id);
	}

	@Override
	public List<SuggestEntity> getAllList(SuggestEntity query) {
		query.setIsApp("0");
		return suggestDao.getAllList(query);
	}
	
	
}
