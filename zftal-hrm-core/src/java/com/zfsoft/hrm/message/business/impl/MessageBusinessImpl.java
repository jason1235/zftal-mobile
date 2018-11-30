package com.zfsoft.hrm.message.business.impl;

import java.util.Date;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.message.business.IMessageBusiness;
import com.zfsoft.hrm.message.dao.daointerface.IMessageDao;
import com.zfsoft.hrm.message.dao.daointerface.IMessageStatusDao;
import com.zfsoft.hrm.message.entity.Message;
import com.zfsoft.hrm.message.entity.MessageStatus;
import com.zfsoft.hrm.message.query.MessageQuery;
import com.zfsoft.util.base.StringUtil;

/** 
 * 消息信息business
 * @author jinjj
 * @date 2012-7-30 下午03:59:22 
 *  
 */
public class MessageBusinessImpl implements IMessageBusiness {

	private IMessageDao messageDao;
	private IMessageStatusDao messageStatusDao;
	
	@Override
	public void save(Message message) {
		message.setSendTime(new Date());
		messageDao.insert(message);
		saveReceiverStatus(message);
	}
	
	private void saveReceiverStatus(Message message){
		if(message != null && StringUtil.isNotEmpty(message.getReceiver())){
			String[] receviers = message.getReceiver().split(";");
			for(String receiver : receviers){
				MessageStatus ms = new MessageStatus();
				ms.setMessageId(message.getGuid());
				ms.setReceiver(receiver.trim());
				messageStatusDao.insert(ms);
			}
		}		
	}
	
	@Override
	public PageList<Message> getPageList(MessageQuery query) {
		PageList<Message> pageList = new PageList<Message>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(messageDao.getPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				pageList.addAll(messageDao.getPagingList(query));
			}
		}
		return pageList;
	}
	
	@Override
	public void updateStatus(MessageStatus status) {
		status.setReadTime(new Date());
		messageStatusDao.update(status);
	}

	@Override
	public Message getById(String guid) {
		Message msg = messageDao.getById(guid);
		return msg;
	}

	public void setMessageDao(IMessageDao messageDao) {
		this.messageDao = messageDao;
	}
	
	public void setMessageStatusDao(IMessageStatusDao messageStatusDao) {
		this.messageStatusDao = messageStatusDao;
	}

	@Override
	public void delete(String guid) {
		this.messageDao.delete(guid);
	}	
}
