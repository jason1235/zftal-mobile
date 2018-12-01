package com.zfsoft.hrm.message.service.impl;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.message.business.IMessageBusiness;
import com.zfsoft.hrm.message.dao.daointerface.IMessageDao;
import com.zfsoft.hrm.message.entity.Message;
import com.zfsoft.hrm.message.entity.MessageStatus;
import com.zfsoft.hrm.message.query.MessageQuery;
import com.zfsoft.hrm.message.service.svcinterface.IMessageService;

/** 
 * 消息信息service实现
 * @author jinjj
 * @date 2012-7-30 下午04:10:42 
 *  
 */
public class MessageServiceImpl implements IMessageService {

	private IMessageBusiness messageBusiness;
	private IMessageDao messageDao;
	
	public IMessageDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(IMessageDao messageDao) {
		this.messageDao = messageDao;
	}

	@Override
	public void save(Message message) {
		messageBusiness.save(message);
	}
	
	@Override
	public PageList<Message> getPagingList(MessageQuery query) {
		return messageBusiness.getPageList(query);
	}
	
	public void updateStatus(MessageStatus status) {
		messageBusiness.updateStatus(status);
	}
	
	@Override
	public Message getById(String guid) {
		Message msg = messageBusiness.getById(guid);
		if(msg.getReadTime()== null){
			MessageStatus status = new MessageStatus();
			status.setGuid(guid);
			messageBusiness.updateStatus(status);
		}
		return msg;
	}

	public void setMessageBusiness(IMessageBusiness messageBusiness) {
		this.messageBusiness = messageBusiness;
	}

	@Override
	public void delete(String guid) throws RuntimeException {
		this.messageBusiness.delete(guid);
	}
}	
