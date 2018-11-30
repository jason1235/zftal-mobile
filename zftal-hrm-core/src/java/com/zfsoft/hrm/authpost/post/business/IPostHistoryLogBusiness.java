package com.zfsoft.hrm.authpost.post.business;

import java.util.Date;
import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.authpost.post.entities.PostHistoryLog;
import com.zfsoft.hrm.authpost.post.query.PostHistoryLogQuery;

/** 
 * 历史岗位日志business
 * @author jinjj
 * @date 2012-7-26 下午01:17:24 
 *  
 */
public interface IPostHistoryLogBusiness {

	/**
	 * 记录自动操作日志
	 */
	public void saveAutoLog();
	
	/**
	 * 记录手动操作日志
	 * @param snapTime
	 */
	public void saveManualLog(Date snapTime);
	
	/**
	 * 删除日志
	 * @param snapTime
	 */
	public void remove(Date snapTime);
	
	/**
	 * 日志分页
	 * @param query
	 * @return
	 */
	public PageList getPage(PostHistoryLogQuery query);
	
	/**
	 * 获取日志集合
	 * @param query
	 * @return
	 */
	public List<PostHistoryLog> getList(PostHistoryLogQuery query);
}
