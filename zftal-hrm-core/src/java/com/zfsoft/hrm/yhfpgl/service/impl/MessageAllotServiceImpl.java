package com.zfsoft.hrm.yhfpgl.service.impl;

import java.util.List;

import com.zfsoft.hrm.yhfpgl.dao.IMessageAllotDao;
import com.zfsoft.hrm.yhfpgl.entity.MessageAllot;
import com.zfsoft.hrm.yhfpgl.service.IMessageAllotService;

/**
 * 
 * @author ChenMinming
 * @date 2015-7-20
 * @version V1.0.0
 */
public class MessageAllotServiceImpl implements IMessageAllotService {
	
	private IMessageAllotDao messageAllotDao;

	@Override
	public void save(List<MessageAllot> list,MessageAllot messageAllot) {
		messageAllotDao.delete(messageAllot);
		if(list==null) return;
		for (MessageAllot m : list) {
			m.setType(messageAllot.getType());
			m.setMainId(messageAllot.getMainId());
			messageAllotDao.insert(m);
		}
	}

	@Override
	public List<MessageAllot> findList(MessageAllot messageAllot) {
		return messageAllotDao.findList(messageAllot);
	}

	/**
	 * 设置
	 * @param messageAllotDao 
	 */
	public void setMessageAllotDao(IMessageAllotDao messageAllotDao) {
		this.messageAllotDao = messageAllotDao;
	}

}
