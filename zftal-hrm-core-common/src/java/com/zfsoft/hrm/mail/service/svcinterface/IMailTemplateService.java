package com.zfsoft.hrm.mail.service.svcinterface;

import java.util.List;

import com.zfsoft.hrm.mail.entities.MailTemplate;

/**
 * 
 * @author yxlong
 * 2013-9-26
 */
public interface IMailTemplateService {
	public void save(MailTemplate mailTemplate);
	public void modify(MailTemplate mailTemplate);
	public void remove(String id);
	public MailTemplate getMailTemplate(String id);
	public List<MailTemplate> getMailTemplates();
	public MailTemplate getMailTemplateByTaskId(MailTemplate mailTemplate);

}
