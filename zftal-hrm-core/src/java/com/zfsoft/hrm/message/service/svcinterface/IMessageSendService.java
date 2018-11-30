package com.zfsoft.hrm.message.service.svcinterface;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.message.entity.Message;
import com.zfsoft.hrm.message.query.MessageQuery;

/** 
 * 发送人消息
 * @author jinjj
 * @date 2012-11-15 上午11:00:33 
 *  
 */
public interface IMessageSendService {

	/**
	 * 消息分页
	 * @param query
	 * @return
	 */
	public PageList<Message> getPagingList(MessageQuery query);
	
	/**
	 * 以guid获取消息信息
	 * @param guid
	 * @return
	 */
	public Message getById(String guid);
	
	/**
	 * 通过guid删除消息信息
	 */
	public void delete(String guid) throws RuntimeException;

	/**
	 * 取得角色
	 * @return
	 */
	public List<Map<String, String>> getRoles();

	/**
	 * 取得角色包含的人员
	 * @param param
	 * @return
	 */
	public List<String> findPersonByRole(Message msg);
}
