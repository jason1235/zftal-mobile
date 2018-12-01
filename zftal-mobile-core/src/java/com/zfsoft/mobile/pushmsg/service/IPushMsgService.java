package com.zfsoft.mobile.pushmsg.service;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.pushmsg.entity.PushMsg;
import com.zfsoft.mobile.pushmsg.query.PushMsgQuery;

public interface IPushMsgService {

	/**
	 * 新增消息
	 * @param message
	 */
	public void save(PushMsg pushMsg)throws RuntimeException;
	
	public PageList<PushMsg> getPageList(PushMsgQuery query)throws RuntimeException;
	
	public PushMsg getEntity(PushMsg entity)throws RuntimeException;
}
