package com.zfsoft.service.impl;

import com.zfsoft.dao.daointerface.ILogDao;
import com.zfsoft.dao.entities.OperateLogModel;
import com.zfsoft.service.svcinterface.ILogService;

public class LogServiceImpl implements ILogService {

	private ILogDao dao;

	public ILogDao getDao() {
		return dao;
	}

	public void setDao(ILogDao dao) {
		this.dao = dao;
	}

	public void insert(OperateLogModel model) {
		dao.insert(model);
	}

}
