package com.zfsoft.hrm.yhfpgl.service;

import java.util.List;

import com.zfsoft.hrm.yhfpgl.entity.MessageAllot;

/**
 * 
 * @author ChenMinming
 * @date 2015-7-20
 * @version V1.0.0
 */
public interface IMessageAllotService {
	
	void save(List<MessageAllot> list,MessageAllot messageAllot);
	List<MessageAllot> findList(MessageAllot messageAllot);
}
