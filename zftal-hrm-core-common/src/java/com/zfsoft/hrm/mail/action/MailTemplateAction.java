package com.zfsoft.hrm.mail.action;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import com.opensymphony.xwork2.ModelDriven;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.mail.entities.MailConfig;
import com.zfsoft.hrm.mail.entities.MailTemplate;
import com.zfsoft.hrm.mail.entities.TaskNameEnum;
import com.zfsoft.hrm.mail.service.svcinterface.IMailTemplateService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.encrypt.DBEncrypt;

/**
 * 
 * @author yxlong
 * 2013-9-26
 */
public class MailTemplateAction extends HrmAction implements ModelDriven<MailTemplate> {

	private static final long serialVersionUID = -3682512791835363027L;
	private MailTemplate model = new MailTemplate();
	private List<MailTemplate> list = new ArrayList<MailTemplate>();
	private List<String> ids = new ArrayList<String>();
	
	private IMailTemplateService mailTemplateService;
	
	private MailConfig config;
	
	private String mailCont;
	
	private String toMail;
	
	public String list(){
		list = mailTemplateService.getMailTemplates();
		return "list";
	}
	
	public String config(){
		config = getMailConfigService().findByType("EMAIL");
		return "config";
	}
	
	public String sendTestMail(){
		try {
			config.setPwd(new DBEncrypt().eCode(config.getPwd()));
			setMailConfig(config);
			getMailEngine().sendSimpleMessage(new String[]{toMail}, null, "Test Mail", mailCont);
		} catch (Exception e) {
			setErrorMessage("发送失败 请检查配置是否有误");
			e.printStackTrace();
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String saveConfig(){
		if (config==null) {
			config = new MailConfig();
		}
		config.setType("EMAIL");
		config.setPwd(new DBEncrypt().eCode(config.getPwd()));
		getMailConfigService().save(config);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	
	public String save(){
		model.setDefaultValue(0);
		if(mailTemplateService.getMailTemplateByTaskId(model) == null){
			mailTemplateService.save(model);
		}else{
			setErrorMessage("邮件模板已经存在！");
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String modify(){
		mailTemplateService.modify(model);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String remove(){
		try {
			for(String id : ids){
				mailTemplateService.remove(id);
			}
		} catch (Exception e) {
			setErrorMessage("删除错误："+e.getMessage());
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String toPage(){
		if(StringUtil.isNotEmpty(model.getId())){
			model = mailTemplateService.getMailTemplate(model.getId());
		}
		getValueStack().set("taskList", TaskNameEnum.values());
		return "edit";
	}
	
	@Override
	public MailTemplate getModel() {
		return model;
	}

	/**
	 * @return the list
	 */
	public List<MailTemplate> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<MailTemplate> list) {
		this.list = list;
	}

	/**
	 * @return the mailTemplateService
	 */
	public IMailTemplateService getMailTemplateService() {
		return mailTemplateService;
	}

	/**
	 * @param mailTemplateService the mailTemplateService to set
	 */
	public void setMailTemplateService(IMailTemplateService mailTemplateService) {
		this.mailTemplateService = mailTemplateService;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(MailTemplate model) {
		this.model = model;
	}

	/**
	 * @return the ids
	 */
	public List<String> getIds() {
		return ids;
	}

	/**
	 * @param ids the ids to set
	 */
	public void setIds(List<String> ids) {
		this.ids = ids;
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
	 * 设置
	 * @param toMail 
	 */
	public void setToMail(String toMail) {
		this.toMail = toMail;
	}

	/**
	 * 返回
	 */
	public String getMailCont() {
		return mailCont;
	}

	/**
	 * 设置
	 * @param mailCont 
	 */
	public void setMailCont(String mailCont) {
		this.mailCont = mailCont;
	}
	
	
}
