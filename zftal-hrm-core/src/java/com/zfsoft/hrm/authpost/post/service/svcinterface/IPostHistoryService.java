package com.zfsoft.hrm.authpost.post.service.svcinterface;

import java.util.Date;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.authpost.post.query.PostHistoryQuery;

/** 
 * 历史岗位service
 * @author jinjj
 * @date 2012-7-26 下午01:12:32 
 *  
 */
public interface IPostHistoryService {

	/**
	 * 自动执行
	 */
	public void doAuto();
	
	/**
	 * 人工执行
	 * @param snapTime
	 */
	public void doManual(Date snapTime);
	
	/**
	 * 删除历史数据
	 * @param snapTime
	 */
	public void remove(Date snapTime);
	
	/**
	 * 历史数据分页
	 * @param query
	 * @return
	 */
	public PageList getPage(PostHistoryQuery query);
}
