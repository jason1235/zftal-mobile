package com.zfsoft.hrm.overall.base.service.impl;

import com.zfsoft.hrm.overall.base.dao.IOverAllDao;
import com.zfsoft.hrm.overall.base.entity.OverAll;
import com.zfsoft.hrm.overall.base.service.IOverAllService;

public class OverAllServiceImpl implements IOverAllService {
	private IOverAllService overAllService;
	public IOverAllService getOverAllService() {
		return overAllService;
	}
	public void setOverAllService(IOverAllService overAllService) {
		this.overAllService = overAllService;
	}
	public IOverAllDao getOverAllDao() {
		return overAllDao;
	}
	public void setOverAllDao(IOverAllDao overAllDao) {
		this.overAllDao = overAllDao;
	}
	private IOverAllDao overAllDao;

	
	@Override
	public OverAll getByGh(String gh) {
		OverAll overall = overAllDao.getByGh(gh);
		return overall;
	}

}
