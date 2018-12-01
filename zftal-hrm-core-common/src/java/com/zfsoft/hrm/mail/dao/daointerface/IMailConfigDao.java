package com.zfsoft.hrm.mail.dao.daointerface;

import com.zfsoft.hrm.mail.entities.MailConfig;

/**
 * 
 * @author ChenMinming
 * @date 2013-10-17
 * @version V1.0.0
 */
public interface IMailConfigDao {
	/**
	 * 根据发送类型获取相应的短信模板
	 * @param sendType
	 * @return
	 */
	MailConfig findByType(String type);
	
	/**
	 * 根据发送类型获取相应的短信模板
	 * @param sendType
	 * @return
	 */
	void insert(MailConfig Template);
	
	/**
	 * 根据发送类型获取相应的短信模板
	 * @param sendType
	 * @return
	 */
	void remove(MailConfig Template);
}
