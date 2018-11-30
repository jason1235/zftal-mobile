package com.zfsoft.hrm.sms.dao.daointerface;

import java.util.List;

import com.zfsoft.hrm.sms.entities.SMSTemplate;

/**
 * 
 * @author ChenMinming
 * @date 2013-10-9
 * @version V1.0.0
 */
public interface ISMSTemplateDao {
	/**
	 * 根据发送类型获取相应的短信模板
	 * @param sendType
	 * @return
	 */
	SMSTemplate findByType(String sendType);
	
	/**
	 * 根据发送类型获取相应的短信模板
	 * @param sendType
	 * @return
	 */
	void update(SMSTemplate Template);
	
	List<SMSTemplate> findList();
}
