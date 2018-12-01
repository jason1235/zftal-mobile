package com.zfsoft.service.impl;

import com.zfsoft.dao.daointerface.INjdmDao;
import com.zfsoft.service.svcinterface.INjdmService;

public class NjdmServiceImpl implements INjdmService {

	private INjdmDao dao;

	public INjdmDao getDao() {
		return dao;
	}

	public void setDao(INjdmDao dao) {
		this.dao = dao;
	}

}
