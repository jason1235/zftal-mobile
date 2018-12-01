package com.zfsoft.service.impl;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.daointerface.IBmdmDao;
import com.zfsoft.dao.entities.BmdmModel;
import com.zfsoft.service.svcinterface.IBmdmService;

public class BmdmServiceImpl implements IBmdmService {

	private IBmdmDao dao;

	@Override
	public List<BmdmModel> queryModel(Map<String, String> map){
 
		return dao.queryModel(map);
	}
	
	@Override
	public List<BmdmModel> getModelList(BmdmModel model) {
 
		return dao.getModelList(model);
	}
	
	@Override
	public List<BmdmModel> getPagedList(BmdmModel model) {
		return dao.getPagedList(model);
	}
	
	public IBmdmDao getDao() {
		return dao;
	}

	public void setDao(IBmdmDao dao) {
		this.dao = dao;
	}
}
