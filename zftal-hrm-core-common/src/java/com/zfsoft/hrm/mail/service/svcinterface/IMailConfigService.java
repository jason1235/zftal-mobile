package com.zfsoft.hrm.mail.service.svcinterface;

import com.zfsoft.hrm.mail.entities.MailConfig;

/**
 * 
 * @author ChenMinming
 * @date 2013-10-17
 * @version V1.0.0
 */
public interface IMailConfigService {
	/**
	 * 保存信件配置（先删后插）
	 * @param config
	 */
	public void save(MailConfig config);
	/**
	 * 根据类型查询配置
	 * @param type :  EMAIl, SMS
	 * @return
	 */
	public MailConfig findByType(String type);
}
