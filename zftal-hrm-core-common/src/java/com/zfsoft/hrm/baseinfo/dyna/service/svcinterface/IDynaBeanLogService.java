package com.zfsoft.hrm.baseinfo.dyna.service.svcinterface;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;

/** 
 * @author jinjj
 * @date 2013-1-4 下午04:49:50 
 *  
 */
public interface IDynaBeanLogService {

	public PageList<DynaBean> getPagingList(DynaBeanQuery query);
	
	public DynaBean findById(DynaBean bean);
}
