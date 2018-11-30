package com.zfsoft.hrm.message.service.svcinterface;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.message.entity.Message;
import com.zfsoft.hrm.message.query.MessageQuery;

/** 
 * 消息信息service
 * @author jinjj
 * @date 2012-7-30 下午03:46:36 
 *  
 */
public interface IMessageService {

	/**
	 * 新增消息
	 * @param message
	 */
	public void save(Message message);
	
	/**
	 * 消息分页
	 * @param query
	 * @return
	 */
	public PageList<Message> getPagingList(MessageQuery query);
	
	/**
	 * 更新消息
	 */
//	public void updateStatus(MessageStatus status);
	
	/**
	 * 以guid获取消息信息
	 * @param guid
	 * @return
	 */
	public Message getById(String guid);
	
	/**
	 * 删除消息
	 */
	public void delete(String guid) throws RuntimeException;
}
