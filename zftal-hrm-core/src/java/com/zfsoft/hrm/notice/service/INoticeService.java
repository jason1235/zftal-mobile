package com.zfsoft.hrm.notice.service;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.notice.entity.Notice;
import com.zfsoft.hrm.notice.query.NoticeQuery;

/** 
 * @author jinjj
 * @date 2012-9-26 上午11:46:22 
 *  
 */
public interface INoticeService {

	/**
	 * 保存
	 * @param notice
	 */
	public void save(Notice notice);
	
	/**
	 * 删除
	 * @param guid
	 */
	public void delete(String guid);
	
	/**
	 * 分页
	 * @param query
	 * @return
	 */
	public PageList<Notice> getPagingList(NoticeQuery query);
	
	/**
	 * 更新
	 * @param notice
	 */
	public void update(Notice notice);
	
	/**
	 * 更新发布状态
	 * @param notice
	 */
	public void updateStatus(Notice notice);
	
	/**
	 * 查询
	 * @param guid
	 * @return
	 */
	public Notice getById(String guid);
}
