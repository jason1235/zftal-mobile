package com.zfsoft.hrm.message.dao.daointerface;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.message.entity.MessageStatus;

/** 
 * 消息状态DAO
 * @author jinjj
 * @date 2012-7-30 下午03:37:57 
 *  
 */
public interface IMessageStatusDao {

	/**
	 * 插入阅读状态，默认无阅读时间，即为未读
	 * @param status
	 * @throws DataAccessException
	 */
	public void insert(MessageStatus status) throws DataAccessException;
	
	/**
	 * 以GUID更新阅读状态，即插入阅读时间
	 * @param status
	 * @throws DataAccessException
	 */
	public void update(MessageStatus status);
}
