package com.zfsoft.hrm.baseinfo.infoclass.business.impl;

import java.util.List;

import com.zfsoft.hrm.baseinfo.infoclass.business.bizinterface.IInfoPropertyViewBusiness;
import com.zfsoft.hrm.baseinfo.infoclass.dao.daointerface.IInfoPropertyViewDao;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoPropertyView;
import com.zfsoft.hrm.baseinfo.infoclass.query.InfoPropertyViewQuery;

/** 
 * @author jinjj
 * @date 2012-11-13 上午10:41:36 
 *  
 */
public class InfoPropertyViewBusinessImpl implements IInfoPropertyViewBusiness {

	private IInfoPropertyViewDao viewDao;
	
	@Override
	public void save(InfoPropertyView view) {
		viewDao.insert(view);
	}

	@Override
	public void deleteBatch(InfoPropertyViewQuery query) {
		viewDao.deleteBatch(query);
	}

	@Override
	public List<InfoPropertyView> getList(InfoPropertyViewQuery query) {
		return viewDao.getList(query);
	}

	public void setViewDao(IInfoPropertyViewDao viewDao) {
		this.viewDao = viewDao;
	}

}
