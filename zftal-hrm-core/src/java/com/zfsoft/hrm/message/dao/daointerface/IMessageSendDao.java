package com.zfsoft.hrm.message.dao.daointerface;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.message.entity.Message;
import com.zfsoft.hrm.message.query.MessageQuery;

/** 
 * 发送人消息DAO
 * @author jinjj
 * @date 2012-11-15 上午10:42:14 
 *  
 */
public interface IMessageSendDao {

	/**
	 * 分页信息
	 * @param query
	 * @return
	 */
	public List<Message> getPagingList(MessageQuery query);
	
	/**
	 * 分页计数
	 * @param query
	 * @return
	 */
	public int getPagingCount(MessageQuery query);
	
	/**
	 * 以guid查询消息信息
	 * @param guid
	 * @return
	 */
	public Message getById(String guid);
	
	/**
	 * 通过guid删除消息信息
	 */
	public void delete(String guid) throws DataAccessException;

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
