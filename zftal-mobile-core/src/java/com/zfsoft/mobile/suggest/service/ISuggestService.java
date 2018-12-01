package com.zfsoft.mobile.suggest.service;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.suggest.entity.SuggestEntity;
import com.zfsoft.mobile.suggest.entity.suggestPictureEntity;

public interface ISuggestService {
	
	public List<SuggestEntity> getAllList(SuggestEntity query);

	void insertSuggestPicture(suggestPictureEntity pictureEntity);

	void insertSuggestMain(SuggestEntity suggestEntity);

	PageList<SuggestEntity> getList(SuggestEntity query);

	SuggestEntity getDetail(SuggestEntity query);

	void reply(SuggestEntity query);

	List<suggestPictureEntity> getPictureList(String id);

}
