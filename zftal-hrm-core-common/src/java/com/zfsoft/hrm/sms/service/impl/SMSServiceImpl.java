package com.zfsoft.hrm.sms.service.impl;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.zfsoft.hrm.mail.entities.MailConfig;
import com.zfsoft.hrm.mail.service.svcinterface.IMailConfigService;
import com.zfsoft.hrm.sms.dao.daointerface.ISMSMessageDao;
import com.zfsoft.hrm.sms.entities.SMSMessage;
import com.zfsoft.hrm.sms.entities.SMSSendTypeEnum;
import com.zfsoft.hrm.sms.entities.SMSTemplate;
import com.zfsoft.hrm.sms.service.ISMSService;
import com.zfsoft.hrm.sms.service.ISMSTemplateService;
import com.zfsoft.util.encrypt.DBEncrypt;

/**
 * 
 * @author ChenMinming
 * @date 2013-10-22
 * @version V1.0.0
 */
public class SMSServiceImpl implements ISMSService{
	
	private ISMSMessageDao smsMessageDao;
	private IMailConfigService mailConfigService;
	private ISMSTemplateService smsTemplateService;

	@Override
	public void doSend(SMSMessage message) {
		smsMessageDao.insert(message);
	}

	@Override
	public void doSend(String template, Map<String, String> data,
			String[] mobiles, MailConfig config) {
		if(data!=null){
			for (String key : data.keySet()) {
				String p = Pattern.quote("${"+key+"}");
				template = template.replaceAll(p, data.get(key));
			}
		}
		if (mobiles!=null) {
			String mobString = "";
			for (int i=0;i<mobiles.length;i++) {
				mobString += mobiles[i];
				if (i < mobiles.length-1) {
					mobString+= ",";
				}
			}
			SMSMessage smsMessage = new SMSMessage(); 
			smsMessage.setContent(template);
			smsMessage.setSmid(config.getUser());
			smsMessage.setKey(new DBEncrypt().dCode(config.getPwd().getBytes()));
			smsMessage.setMobiles(mobString);
			doSend(smsMessage);
		}
	}
	
	@Override
	public void doSend(SMSSendTypeEnum sendType, Map<String, String> data,
			String[] mobiles) {
		SMSTemplate smsTemplate =smsTemplateService.getMailTemplate(sendType.getKey());
		MailConfig config = mailConfigService.findByType("SMS");
		doSend(smsTemplate.getContent(), data, mobiles, config);
	}
	
	/**
	 * 设置
	 * @param smsMessageDao 
	 */
	public void setSmsMessageDao(ISMSMessageDao smsMessageDao) {
		this.smsMessageDao = smsMessageDao;
	}

	/**
	 * 设置
	 * @param mailConfigService 
	 */
	public void setMailConfigService(IMailConfigService mailConfigService) {
		this.mailConfigService = mailConfigService;
	}

	/**
	 * 设置
	 * @param smsTemplateService 
	 */
	public void setSmsTemplateService(ISMSTemplateService smsTemplateService) {
		this.smsTemplateService = smsTemplateService;
	}


	
	

}
