package com.zfsoft.mobile.notice.service;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.notice.entity.NoticeCatalog;
import com.zfsoft.mobile.notice.entity.NoticeKind;
import com.zfsoft.mobile.notice.query.NotiCatalogQuery;
import com.zfsoft.mobile.notice.query.NotiKindQuery;

/**
 * 通知分类Service接口
 * @author wy
 *
 */
public interface INoticeCatalogService {
	/**
	 * 获取通知分类分页列表
	 * @param query
	 * @return
	 */
	PageList<NoticeCatalog> getPageList(NotiCatalogQuery query);
	
	/**
	 * 根据分类ID获取通知种类
	 * @param query
	 * @return
	 */
	NoticeCatalog findById(NotiCatalogQuery query);
	
	/**
	 * 根据分类名称获取通知种类
	 * @param query
	 * @return
	 */
	NoticeCatalog findByName(NotiCatalogQuery query);
	
	/**
	 * 保存操作
	 * @param query
	 * @throws Exception
	 */
	void doSave(NotiCatalogQuery query) throws Exception;
	
	/**
	 * 更新操作
	 * @param query
	 * @throws Exception
	 */
	void doUpdate(NotiCatalogQuery query) throws Exception;
	
	/**
	 * 通知分类控制（删除，启用，停用）
	 * @param query
	 * @throws Exception
	 */
	void doControl(NotiCatalogQuery query) throws Exception;

	NoticeCatalog findByNameOtherId(NotiCatalogQuery query);
}
