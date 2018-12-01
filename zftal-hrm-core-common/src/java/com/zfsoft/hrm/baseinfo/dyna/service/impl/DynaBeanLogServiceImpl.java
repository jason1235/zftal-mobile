package com.zfsoft.hrm.baseinfo.dyna.service.impl;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanLogBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.service.svcinterface.IDynaBeanLogService;

/** 
 * @author jinjj
 * @date 2013-1-4 下午04:51:04 
 *  
 */
public class DynaBeanLogServiceImpl implements IDynaBeanLogService {

	private IDynaBeanLogBusiness dynaBeanLogBusiness;
	@Override
	public PageList<DynaBean> getPagingList(DynaBeanQuery query) {
		return dynaBeanLogBusiness.getPagingList(query);
	}
	
	public DynaBean findById(DynaBean bean){
		return dynaBeanLogBusiness.findById(bean);
	}
	
	public void setDynaBeanLogBusiness(IDynaBeanLogBusiness dynaBeanLogBusiness) {
		this.dynaBeanLogBusiness = dynaBeanLogBusiness;
	}

}
