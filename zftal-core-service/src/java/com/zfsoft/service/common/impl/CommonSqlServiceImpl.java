package com.zfsoft.service.common.impl;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.daointerface.ICommonSqlDao;
import com.zfsoft.dao.entities.BjdmModel;
import com.zfsoft.dao.entities.BmdmModel;
import com.zfsoft.dao.entities.NjdmModel;
import com.zfsoft.dao.entities.ZydmModel;
import com.zfsoft.service.common.ICommonSqlService;

/**
 * 
 * @author Administrator
 *
 */
public class CommonSqlServiceImpl implements ICommonSqlService {

	private ICommonSqlDao dao;

	@Override
	public List<BmdmModel> queryAllXy() throws Exception {
		return dao.queryAllXy();
	}

	@Override
	public List<ZydmModel> queryAllZy() throws Exception {
		return dao.queryAllZy();

	}

	@Override
	public List<NjdmModel> queryAllNj() throws Exception {
		return dao.queryAllNj();
	}

	@Override
	public List<BjdmModel> queryAllBj() throws Exception {
		return dao.queryAllBj();
	}


	public ICommonSqlDao getDao() {
		return dao;
	}

	public void setDao(ICommonSqlDao dao) {
		this.dao = dao;
	}

	@Override
	public List<ZydmModel> queryZydm(Map map) throws Exception {
		return dao.queryZydm(map);
	}

	@Override
	public List<BjdmModel> queryBjdm(BjdmModel model) throws Exception {
		return dao.queryBjdm(model);
	}
}
