package com.zfsoft.hrm.notice.dao;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.notice.entity.Notice;
import com.zfsoft.hrm.notice.query.NoticeQuery;

/** 
 * 通知dao
 * @author jinjj
 * @date 2012-9-26 上午10:22:44 
 *  
 */
public interface INoticeDao {

	/**
	 * 插入
	 * @param notice
	 */
	public void insert(Notice notice);
	
	/**
	 * 更新
	 * @param notice
	 */
	public void update(Notice notice);
	
	/**
	 * 查询
	 * @param guid
	 * @return
	 */
	public Notice getById(String guid);
	
	/**
	 * 删除
	 * @param guid
	 */
	public void delete(String guid);
	
	/**
	 * 分页集合
	 * @param query
	 * @return
	 */
	public PageList<Notice> getPagingList(NoticeQuery query);
	
	/**
	 * 分页计数
	 * @param query
	 * @return
	 */
	public int getPagingCount(NoticeQuery query);
	
	/**
	 * 查询集合
	 * @param query
	 * @return
	 */
	public List<Notice> getList(NoticeQuery query);
}
