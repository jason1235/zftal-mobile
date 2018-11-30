package com.zfsoft.hrm.message.business;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.message.entity.Message;
import com.zfsoft.hrm.message.query.MessageQuery;

/** 
 * 发送人消息business
 * @author jinjj
 * @date 2012-11-15 上午10:50:38 
 *  
 */
public interface IMessageSendBusiness {

	/**
	 * 获取消息分页
	 * @param query
	 * @return
	 */
	public PageList<Message> getPageList(MessageQuery query);
	
	/**
	 * 以guid获取消息信息
	 * @param guid
	 * @return
	 */
	public Message getById(String guid);
	
	/**
	 * 根据guid删除消息信息
	 */
	public void delete(String guid);

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
