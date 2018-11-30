package com.zfsoft.hrm.message.dao.daointerface;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.message.entity.Message;
import com.zfsoft.hrm.message.query.MessageQuery;

/** 
 * 消息内容DAO
 * @author jinjj
 * @date 2012-7-30 下午03:14:11 
 *  
 */
public interface IMessageDao {

	/**
	 * 插入消息
	 * @param message
	 * @throws DataAccessException
	 */
	public void insert(Message message) throws DataAccessException;
	
	
	/**
	 * 分页信息
	 * @param query
	 * @return
	 */
	public List<Message> getPagingList(MessageQuery query);
	
	/**
	 * 分页计数
	 * @param query
	 * @return
	 */
	public int getPagingCount(MessageQuery query);
	
	/**
	 * 以guid查询消息信息
	 * @param guid
	 * @return
	 */
	public Message getById(String guid);
	/**
	 * 删除消息
	 */
	public void delete(String guid);
}
