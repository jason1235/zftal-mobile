package com.zfsoft.weibo.service;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.weibo.action.entity.WeiBoEntity;

public interface IWeiboService {

	PageList<WeiBoEntity> getPageList(WeiBoEntity query);

	void insert(WeiBoEntity model);

	void update(WeiBoEntity model);

	void delete(WeiBoEntity model);

	void tingyong(WeiBoEntity model);

	void qiyong(WeiBoEntity model);

	void updateAccessById(WeiBoEntity model);
}
