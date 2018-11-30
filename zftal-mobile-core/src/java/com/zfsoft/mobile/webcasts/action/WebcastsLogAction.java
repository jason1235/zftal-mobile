package com.zfsoft.mobile.webcasts.action;

import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.webcasts.service.IWebcastsLogService;

public class WebcastsLogAction extends HrmAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IWebcastsLogService webcastlogService;

	public IWebcastsLogService getWebcastlogService() {
		return webcastlogService;
	}

	public void setWebcastlogService(IWebcastsLogService webcastlogService) {
		this.webcastlogService = webcastlogService;
	}
	
	

}
