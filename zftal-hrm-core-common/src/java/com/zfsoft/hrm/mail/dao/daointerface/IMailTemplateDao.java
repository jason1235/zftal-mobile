package com.zfsoft.hrm.mail.dao.daointerface;

import java.util.List;

import com.zfsoft.hrm.mail.entities.MailTemplate;

/**
 * 
 * @author yxlong
 * 2013-9-26
 */
public interface IMailTemplateDao {
	public void insert(MailTemplate mailTemplate);
	public void update(MailTemplate mailTemplate);
	public void delete(String id);
	public MailTemplate findMailTemplate(String id);
	public List<MailTemplate> findMailTemplates();
	public MailTemplate findMailTemplateByTaskId(MailTemplate mailTemplate);

}
