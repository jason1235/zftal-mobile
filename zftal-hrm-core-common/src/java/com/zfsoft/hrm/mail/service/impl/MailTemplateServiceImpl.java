package com.zfsoft.hrm.mail.service.impl;

import java.util.List;

import com.zfsoft.hrm.mail.dao.daointerface.IMailTemplateDao;
import com.zfsoft.hrm.mail.entities.MailTemplate;
import com.zfsoft.hrm.mail.service.svcinterface.IMailTemplateService;

/**
 * 
 * @author yxlong
 * 2013-9-26
 */
public class MailTemplateServiceImpl implements IMailTemplateService {

	private IMailTemplateDao mailTemplateDao;
	@Override
	public void save(MailTemplate mailTemplate) {
		mailTemplateDao.insert(mailTemplate);
	}

	@Override
	public void modify(MailTemplate mailTemplate) {
		mailTemplateDao.update(mailTemplate);
	}

	@Override
	public void remove(String id) {
		mailTemplateDao.delete(id);
	}

	@Override
	public MailTemplate getMailTemplate(String id) {
		return mailTemplateDao.findMailTemplate(id);
	}

	@Override
	public MailTemplate getMailTemplateByTaskId(MailTemplate mailTemplate) {
		return mailTemplateDao.findMailTemplateByTaskId(mailTemplate);
	}
	
	@Override
	public List<MailTemplate> getMailTemplates() {
		return mailTemplateDao.findMailTemplates();
	}


	/**
	 * @return the mailTemplateDao
	 */
	public IMailTemplateDao getMailTemplateDao() {
		return mailTemplateDao;
	}

	/**
	 * @param mailTemplateDao the mailTemplateDao to set
	 */
	public void setMailTemplateDao(IMailTemplateDao mailTemplateDao) {
		this.mailTemplateDao = mailTemplateDao;
	}

}
