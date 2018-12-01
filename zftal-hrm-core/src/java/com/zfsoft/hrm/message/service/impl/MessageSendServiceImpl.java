package com.zfsoft.hrm.message.service.impl;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.message.business.IMessageSendBusiness;
import com.zfsoft.hrm.message.entity.Message;
import com.zfsoft.hrm.message.query.MessageQuery;
import com.zfsoft.hrm.message.service.svcinterface.IMessageSendService;

/** 
 * @author jinjj
 * @date 2012-11-15 上午11:02:45 
 *  
 */
public class MessageSendServiceImpl implements IMessageSendService {

	private IMessageSendBusiness messageSendBusiness;
	
	@Override
	public PageList<Message> getPagingList(MessageQuery query) {
		return messageSendBusiness.getPageList(query);
	}

	@Override
	public Message getById(String guid) {
		Message msg = messageSendBusiness.getById(guid);
		return msg;
	}

	public void setMessageSendBusiness(IMessageSendBusiness messageSendBusiness) {
		this.messageSendBusiness = messageSendBusiness;
	}

	@Override
	public void delete(String guid) throws RuntimeException {
		this.messageSendBusiness.delete(guid);		
	}

	@Override
	public List<Map<String, String>> getRoles() {
		return messageSendBusiness.getRoles();
	}

	@Override
	public List<String> findPersonByRole(Message msg) {
		return messageSendBusiness.findPersonByRole(msg);
	}

}
