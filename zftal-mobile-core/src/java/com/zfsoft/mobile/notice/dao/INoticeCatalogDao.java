package com.zfsoft.mobile.notice.dao;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.notice.entity.NoticeCatalog;
import com.zfsoft.mobile.notice.query.NotiCatalogQuery;

public interface INoticeCatalogDao {
	
	/**
	 * 通知分类分类列表
	 * @param query
	 * @return
	 */
	PageList<NoticeCatalog> getPageList(NotiCatalogQuery query);
	
	/**
	 * 分页总数
	 * @param query
	 * @return
	 */
	int getPageCount(NotiCatalogQuery query);
	
	/**
	 * 增加操作
	 * @param query
	 */
	void insert(NotiCatalogQuery query);
	
	/**
	 * 根据ID查询通知分类
	 * @param query
	 * @return
	 */
	NoticeCatalog findById(NotiCatalogQuery query);
	
	/**
	 * 根据名称查询通知分类
	 * @param query
	 * @return
	 */
	NoticeCatalog findByName(NotiCatalogQuery query);
	
	/**
	 * 更新通知分类
	 * @param query
	 */
	void update(NotiCatalogQuery query);
	
	/**
	 * 通知控制（删除，启用，停用）
	 * @param query
	 */
	void noticeControl(NotiCatalogQuery query);

	NoticeCatalog findByNameOtherId(NotiCatalogQuery query);
}
