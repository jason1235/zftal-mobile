package com.zfsoft.service.impl;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.daointerface.IZydmDao;
import com.zfsoft.dao.entities.ZydmModel;
import com.zfsoft.service.svcinterface.IZydmService;

public class ZydmServiceImpl implements IZydmService {

	private IZydmDao dao;

	@Override
	public List<ZydmModel> queryModel(Map<String, String> map)
			 {
		return dao.queryModel(map);
	}

	public IZydmDao getDao() {
		return dao;
	}

	public void setDao(IZydmDao dao) {
		this.dao = dao;
	}

}
