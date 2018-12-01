package com.zfsoft.weibo.dao;

import java.util.List;

import com.zfsoft.weibo.action.entity.WeiBoEntity;

public interface IWeiboDao {

	int getPageListCount(WeiBoEntity query);

	List<WeiBoEntity> getPageList(WeiBoEntity query);

	void insert(WeiBoEntity model);

	void update(WeiBoEntity model);
	
	void delete(WeiBoEntity model);

	void updateById(WeiBoEntity model);
	
	void updateAccessById(WeiBoEntity model);

}
