package com.zfsoft.hrm.summary.roster.business.impl;

import com.zfsoft.hrm.summary.roster.business.IRosterParamBusiness;
import com.zfsoft.hrm.summary.roster.dao.daointerface.IRosterParamDao;
import com.zfsoft.hrm.summary.roster.entity.RosterParam;

/** 
 * 花名册参数
 * @author jinjj
 * @date 2012-11-20 下午04:37:51 
 *  
 */
public class RosterParamBusinessImpl implements IRosterParamBusiness {

	private IRosterParamDao paramDao;
	
	@Override
	public void save(RosterParam param) {
		paramDao.delete(param);
		paramDao.insert(param);
	}

	@Override
	public RosterParam getById(RosterParam param) {
		return paramDao.getById(param);
	}
	
	public void delete(RosterParam param) {
		paramDao.delete(param);
	}

	public void setParamDao(IRosterParamDao paramDao) {
		this.paramDao = paramDao;
	}
}
