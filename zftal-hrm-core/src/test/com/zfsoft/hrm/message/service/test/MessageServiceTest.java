package com.zfsoft.hrm.message.service.test;

import java.util.Date;

import junit.framework.TestCase;

import com.zfsoft.common.factory.ServiceFactory;
import com.zfsoft.hrm.message.entity.Message;
import com.zfsoft.hrm.message.service.svcinterface.IMessageService;

/** 
 * 消息信息test
 * @author jinjj
 * @date 2012-7-30 下午04:51:15 
 *  
 */
public class MessageServiceTest extends TestCase {

	private IMessageService messageService;
	
	public void setUp(){
		//messageService = (IMessageService)applicationContext.getBean("messageService");
		messageService = (IMessageService)ServiceFactory.getService("messageService");
	}
	
	public void testSave(){
		Message message = new Message();
		message.setContent("test");
		message.setSender("system");
		message.setTitle("test");
		message.setReceiver("0001");
		message.setSendTime(new Date());
		messageService.save(message);
	}
}
