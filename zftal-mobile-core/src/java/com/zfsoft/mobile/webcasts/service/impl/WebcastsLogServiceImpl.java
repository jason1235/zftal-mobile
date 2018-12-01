package com.zfsoft.mobile.webcasts.service.impl;

import com.zfsoft.mobile.webcasts.dao.IWebcastsLogDao;
import com.zfsoft.mobile.webcasts.service.IWebcastsLogService;

public class WebcastsLogServiceImpl implements IWebcastsLogService{

	private IWebcastsLogDao webcastlogDao;

	public IWebcastsLogDao getWebcastlogDao() {
		return webcastlogDao;
	}

	public void setWebcastlogDao(IWebcastsLogDao webcastlogDao) {
		this.webcastlogDao = webcastlogDao;
	}
	
	
}
