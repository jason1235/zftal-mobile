/**
 * 
 */
package com.zfsoft.mobile.servlet.commonHttp.service.impl;

import com.zfsoft.mobile.servlet.commonHttp.dao.daointerface.ICommonHttpDao;
import com.zfsoft.mobile.servlet.commonHttp.service.ICommonHttpService;

/**
 * @author zhangxu
 *
 */
public class CommonHttpServiceImpl implements ICommonHttpService {
	
	private ICommonHttpDao commonHttpDao;

	public void setCommonHttpDao(ICommonHttpDao commonHttpDao) {
		this.commonHttpDao = commonHttpDao;
	}

	public ICommonHttpDao getCommonHttpDao() {
		return commonHttpDao;
	}
	

}
