package com.zfsoft.service.impl;

import com.zfsoft.dao.daointerface.IBjdmDao;
import com.zfsoft.service.svcinterface.IBjdmService;

public class BjdmServiceImpl implements IBjdmService {

	private IBjdmDao dao;

	public IBjdmDao getDao() {
		return dao;
	}

	public void setDao(IBjdmDao dao) {
		this.dao = dao;
	}

	 

}
