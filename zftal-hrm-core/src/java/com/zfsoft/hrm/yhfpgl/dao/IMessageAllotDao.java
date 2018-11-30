package com.zfsoft.hrm.yhfpgl.dao;

import java.util.List;

import com.zfsoft.hrm.yhfpgl.entity.MessageAllot;

/**
 * 
 * @author ChenMinming
 * @date 2015-7-20
 * @version V1.0.0
 */
public interface IMessageAllotDao {

	public void insert(MessageAllot messageAllot);
	
	public List<MessageAllot> findList(MessageAllot messageAllot);
	
	public void delete(MessageAllot messageAllot);
}