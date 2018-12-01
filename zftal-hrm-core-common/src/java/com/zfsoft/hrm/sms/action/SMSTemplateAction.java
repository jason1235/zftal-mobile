package com.zfsoft.hrm.sms.action;

import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.mail.entities.MailConfig;
import com.zfsoft.hrm.sms.entities.SMSSendTypeEnum;
import com.zfsoft.hrm.sms.entities.SMSTemplate;
import com.zfsoft.hrm.sms.service.ISMSService;
import com.zfsoft.hrm.sms.service.ISMSTemplateService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.encrypt.DBEncrypt;

/**
 * 
 * @author ChenMinming
 * @date 2013-10-9
 * @version V1.0.0
 */
public class SMSTemplateAction extends HrmAction{
	
	private static final long serialVersionUID = 5253636219668043164L;
	
	private final SMSSendTypeEnum[] tyleList = SMSSendTypeEnum.values();
	
	private ISMSTemplateService smsTemplateService;
	
	private SMSTemplate model=new SMSTemplate();
	
	private ISMSService smsService;
	
	private MailConfig config;
	
	private String smsCont;
	
	private String toSMS;

	public String page(){
		String sendType = tyleList[0].getKey();
		if(model!=null&&!StringUtil.isEmpty(model.getSendType())){
			sendType=model.getSendType();
		}
		model=smsTemplateService.getMailTemplate(sendType);
		if (model==null) {
			model=new SMSTemplate();
			model.setSendType(sendType);
		}
		return "page";
	}
	
	public String config(){
		config = getMailConfigService().findByType("SMS");
		return "config";
	}
	
	public String saveConfig(){
		if (config==null) {
			config = new MailConfig();
		}
		config.setType("SMS");
		config.setPwd(new DBEncrypt().eCode(config.getPwd()));
		getMailConfigService().save(config);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String sendTestMail(){
		try {
			config.setPwd(new DBEncrypt().eCode(config.getPwd()));
			smsService.doSend(smsCont, null, new String[]{toSMS}, config);
			setSuccessMessage("发送成功");
		} catch (Exception e) {
			setErrorMessage("发送失败 请检查配置是否有误");
			e.printStackTrace();
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String save(){
		smsTemplateService.save(model);
		setSuccessMessage("保存成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String toDefault(){
		model.setContent(model.getDefaultCont());
		smsTemplateService.save(model);
		return DATA;
	}

	

	/**
	 * 设置
	 * @param smsTemplateService 
	 */
	public void setSmsTemplateService(ISMSTemplateService smsTemplateService) {
		this.smsTemplateService = smsTemplateService;
	}

	/**
	 * 返回
	 */
	public SMSTemplate getModel() {
		return model;
	}

	/**
	 * 设置
	 * @param model 
	 */
	public void setModel(SMSTemplate model) {
		this.model = model;
	}

	/**
	 * 返回
	 */
	public SMSSendTypeEnum[] getTyleList() {
		return tyleList;
	}

	/**
	 * 返回
	 */
	public MailConfig getConfig() {
		return config;
	}

	/**
	 * 设置
	 * @param config 
	 */
	public void setConfig(MailConfig config) {
		this.config = config;
	}

	/**
	 * 返回
	 */
	public String getSmsCont() {
		return smsCont;
	}

	/**
	 * 设置
	 * @param smsCont 
	 */
	public void setSmsCont(String smsCont) {
		this.smsCont = smsCont;
	}

	/**
	 * 返回
	 */
	public String getToSMS() {
		return toSMS;
	}

	/**
	 * 设置
	 * @param toSMS 
	 */
	public void setToSMS(String toSMS) {
		this.toSMS = toSMS;
	}
	/**
	 * 设置
	 * @param smsService 
	 */
	public void setSmsService(ISMSService smsService) {
		this.smsService = smsService;
	}
	

}
