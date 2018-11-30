package com.zfsoft.hrm.sms.service;

import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.mail.entities.MailConfig;
import com.zfsoft.hrm.sms.entities.SMSMessage;
import com.zfsoft.hrm.sms.entities.SMSSendTypeEnum;

/**
 * 
 * @author ChenMinming
 * @date 2013-10-9
 * @version V1.0.0
 */
public interface ISMSService {
	/**
	 * 发送短信
	 * @param message 发送的短信信息
	 */
	void doSend(SMSMessage message);
	
	/**
	 * 发送短信
	 * @param template 发送的短信信息模板
	 * @param data    填充到模板中的内容对象
	 * @param mobiles 要发送的手机号码
	 * @param config  服务器配置
	 */
	void doSend(String template,Map<String, String> data, String[] mobiles,MailConfig config);
	/**
	 * 发送短信
	 * @param sendType 发送的短信信息模板类型
	 * @param data     填充到模板中的内容对象
	 * @param mobiles  要发送的手机号码
	 */
	void doSend(SMSSendTypeEnum sendType,Map<String, String> data, String[] mobiles);


}
