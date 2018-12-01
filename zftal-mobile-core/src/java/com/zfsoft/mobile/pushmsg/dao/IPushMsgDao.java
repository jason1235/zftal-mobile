package com.zfsoft.mobile.pushmsg.dao;

import org.springframework.dao.DataAccessException;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.pushmsg.entity.PushMsg;
import com.zfsoft.mobile.pushmsg.query.PushMsgQuery;

public interface IPushMsgDao {

	/**
	 * 插入消息
	 * @param message
	 * @throws DataAccessException
	 */
	public void insert(PushMsg pushMsg) throws DataAccessException;
	
	public PageList<PushMsg> getPagingList(PushMsgQuery query) throws DataAccessException;
	
	public int getPagingCount(PushMsgQuery query) throws DataAccessException;
	
	public PushMsg getEntity(PushMsg pushMsg) throws DataAccessException;

}
