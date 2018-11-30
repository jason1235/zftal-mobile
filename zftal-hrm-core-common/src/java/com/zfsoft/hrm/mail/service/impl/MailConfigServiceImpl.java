package com.zfsoft.hrm.mail.service.impl;

import com.zfsoft.hrm.mail.dao.daointerface.IMailConfigDao;
import com.zfsoft.hrm.mail.entities.MailConfig;
import com.zfsoft.hrm.mail.service.svcinterface.IMailConfigService;

/**
 * 
 * @author ChenMinming
 * @date 2013-10-17
 * @version V1.0.0
 */
public class MailConfigServiceImpl implements IMailConfigService {
	
	private IMailConfigDao mailConfigDao;

	@Override
	public MailConfig findByType(String type) {
		return mailConfigDao.findByType(type);
	}

	@Override
	public void save(MailConfig config) {
		mailConfigDao.remove(config);
		mailConfigDao.insert(config);
	}

	/**
	 * 设置
	 * @param mailConfigDao 
	 */
	public void setMailConfigDao(IMailConfigDao mailConfigDao) {
		this.mailConfigDao = mailConfigDao;
	}

	
}
