package com.zfsoft.hrm.summary.roster.service.impl;

import com.zfsoft.hrm.summary.roster.business.IRosterParamBusiness;
import com.zfsoft.hrm.summary.roster.entity.RosterParam;
import com.zfsoft.hrm.summary.roster.service.svcinterface.IRosterParamService;

/** 
 * 花名册参数
 * @author jinjj
 * @date 2012-11-21 上午08:50:16 
 *  
 */
public class RosterParamServiceImpl implements IRosterParamService {

	private IRosterParamBusiness paramBusiness;
	
	@Override
	public void save(RosterParam param) {
		paramBusiness.save(param);
	}

	@Override
	public RosterParam getById(RosterParam param) {
		return paramBusiness.getById(param);
	}

	public void setParamBusiness(IRosterParamBusiness paramBusiness) {
		this.paramBusiness = paramBusiness;
	}

}
