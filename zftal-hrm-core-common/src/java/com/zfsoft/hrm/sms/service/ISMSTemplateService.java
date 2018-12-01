package com.zfsoft.hrm.sms.service;

import java.util.List;

import com.zfsoft.hrm.sms.entities.SMSTemplate;

/**
 * 
 * @author ChenMinming
 * @date 2013-10-9
 * @version V1.0.0
 */
public interface ISMSTemplateService {
	
	public void save(SMSTemplate template);
	
	public SMSTemplate getMailTemplate(String sendType);
	
	public List<SMSTemplate> getMailTemplates();
}
