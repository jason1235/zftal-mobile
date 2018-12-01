package com.zfsoft.hrm.sms.service.impl;

import java.util.List;

import com.zfsoft.hrm.sms.dao.daointerface.ISMSTemplateDao;
import com.zfsoft.hrm.sms.entities.SMSTemplate;
import com.zfsoft.hrm.sms.service.ISMSTemplateService;

/**
 * 
 * @author ChenMinming
 * @date 2013-10-15
 * @version V1.0.0
 */
public class SMSTemplateServiceImpl implements ISMSTemplateService {
	private ISMSTemplateDao smsTemplateDao;

	

	/**
	 * 设置
	 * @param smsTemplateDao 
	 */
	public void setSmsTemplateDao(ISMSTemplateDao smsTemplateDao) {
		this.smsTemplateDao = smsTemplateDao;
	}



	@Override
	public SMSTemplate getMailTemplate(String sendType) {
		// TODO Auto-generated method stub
		return smsTemplateDao.findByType(sendType);
	}



	@Override
	public List<SMSTemplate> getMailTemplates() {
		// TODO Auto-generated method stub
		return smsTemplateDao.findList();
	}


	@Override
	public void save(SMSTemplate template) {
		smsTemplateDao.update(template);
	}
	
	

}
