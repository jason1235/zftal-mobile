package com.zfsoft.hrm.message.business;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.message.entity.Message;
import com.zfsoft.hrm.message.entity.MessageStatus;
import com.zfsoft.hrm.message.query.MessageQuery;

/** 
 * 消息信息service
 * @author jinjj
 * @date 2012-7-30 下午03:46:36 
 *  
 */
public interface IMessageBusiness {

	/**
	 * 新增消息,并按照接收人插入多个消息状态
	 * @param message
	 */
	public void save(Message message);
	
	/**
	 * 获取消息分页
	 * @param query
	 * @return
	 */
	public PageList<Message> getPageList(MessageQuery query);
	
	/**
	 * 以guid更新消息阅读状态
	 * @param status
	 */
	public void updateStatus(MessageStatus status);
	
	/**
	 * 以guid获取消息信息
	 * @param guid
	 * @return
	 */
	public Message getById(String guid);
	/**
	 * 根据guid删除消息信息
	 * @param guid
	 * @return
	 */
	public void delete(String guid);
}
