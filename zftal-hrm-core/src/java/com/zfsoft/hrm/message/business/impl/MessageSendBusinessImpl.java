package com.zfsoft.hrm.message.business.impl;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.message.business.IMessageSendBusiness;
import com.zfsoft.hrm.message.dao.daointerface.IMessageSendDao;
import com.zfsoft.hrm.message.entity.Message;
import com.zfsoft.hrm.message.query.MessageQuery;


/** 
 * @author jinjj
 * @date 2012-11-15 上午10:51:47 
 *  
 */
public class MessageSendBusinessImpl implements IMessageSendBusiness {
	
	private IMessageSendDao messageSendDao;

	@Override
	public PageList<Message> getPageList(MessageQuery query) {
		PageList<Message> pageList = new PageList<Message>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(messageSendDao.getPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				pageList.addAll(messageSendDao.getPagingList(query));
			}
		}
		return pageList;
	}

	@Override
	public Message getById(String guid) {
		Message msg = messageSendDao.getById(guid);
		return msg;
	}

	public void setMessageSendDao(IMessageSendDao messageSendDao) {
		this.messageSendDao = messageSendDao;
	}

	@Override
	public void delete(String guid) {
		this.messageSendDao.delete(guid);
		
	}

	@Override
	public List<Map<String, String>> getRoles() {
		return this.messageSendDao.getRoles();
	}

	@Override
	public List<String> findPersonByRole(Message msg) {
		return this.messageSendDao.findPersonByRole(msg);
	}

}
