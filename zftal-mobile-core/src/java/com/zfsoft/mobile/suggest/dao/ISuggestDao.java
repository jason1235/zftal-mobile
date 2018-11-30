package com.zfsoft.mobile.suggest.dao;

import java.util.List;

import com.zfsoft.mobile.suggest.entity.SuggestEntity;
import com.zfsoft.mobile.suggest.entity.suggestPictureEntity;

public interface ISuggestDao {

	void insertSuggestPicture(suggestPictureEntity pictureEntity);

	void insertSuggestMain(SuggestEntity suggestEntity);

	int getListCount(SuggestEntity query);

	List<SuggestEntity> getList(SuggestEntity query);

	SuggestEntity getDetail(SuggestEntity query);

	void reply(SuggestEntity query);

	List<suggestPictureEntity> getPictureList(String suggestId);

	List<SuggestEntity> getAllList(SuggestEntity query);

}
