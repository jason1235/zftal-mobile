package com.zfsoft.mobile.notice.service;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.notice.entity.NoticeKind;
import com.zfsoft.mobile.notice.query.NotiKindQuery;

/**
 * 通知种类Service接口
 * @author wy
 *
 */
public interface INoticeKindService {
	/**
	 * 获取通知种类分页列表
	 * @param query
	 * @return
	 */
	PageList<NoticeKind> getPageList(NotiKindQuery query);
	
	/**
	 * 根据种类ID获取通知种类
	 * @param query
	 * @return
	 */
	NoticeKind findById(NotiKindQuery query);
	
	/**
	 * 根据种类名称获取通知种类
	 * @param query
	 * @return
	 */
	NoticeKind findByName(NotiKindQuery query);
	
	/**
	 * 保存操作
	 * @param query
	 * @throws Exception
	 */
	void doSave(NotiKindQuery query) throws Exception;
	
	/**
	 * 更新操作
	 * @param query
	 * @throws Exception
	 */
	void doUpdate(NotiKindQuery query) throws Exception;
	
	/**
	 * 通知种类控制（删除，启用，停用）
	 * @param query
	 * @throws Exception
	 */
	void doControl(NotiKindQuery query) throws Exception;

	NoticeKind findByNameOtherId(NotiKindQuery query);
}
