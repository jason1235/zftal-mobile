package com.zfsoft.mobile.notice.dao;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.notice.entity.NoticeKind;
import com.zfsoft.mobile.notice.query.NotiKindQuery;

public interface INoticeKindDao {
	
	/**
	 * 通知种类分类列表
	 * @param query
	 * @return
	 */
	PageList<NoticeKind> getPageList(NotiKindQuery query);
	
	/**
	 * 分页总数
	 * @param query
	 * @return
	 */
	int getPageCount(NotiKindQuery query);
	
	/**
	 * 增加操作
	 * @param query
	 */
	void insert(NotiKindQuery query);
	
	/**
	 * 根据ID查询通知种类
	 * @param query
	 * @return
	 */
	NoticeKind findById(NotiKindQuery query);
	
	/**
	 * 根据名称查询通知种类
	 * @param query
	 * @return
	 */
	NoticeKind findByName(NotiKindQuery query);
	
	/**
	 * 更新通知种类
	 * @param query
	 */
	void update(NotiKindQuery query);
	
	/**
	 * 通知控制（删除，启用，停用）
	 * @param query
	 */
	void noticeControl(NotiKindQuery query);

	NoticeKind findByNameOtherId(NotiKindQuery query);
}
