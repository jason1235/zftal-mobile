package com.zfsoft.hrm.tools.web.service.impl;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.tools.web.exception.SelectPersonException;
import com.zfsoft.hrm.tools.web.query.SelectPersonQuery;
import com.zfsoft.hrm.tools.web.service.svcinterface.ISelectPersonService;

/**
 * {@link ISelectPersonService}的实现
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-10
 * @version V1.0.0
 */
public class SelectPersonServiceImpl implements ISelectPersonService {
	
	private IDynaBeanBusiness dynaBeanBusiness;

	public void setDynaBeanBusiness(IDynaBeanBusiness dynaBeanBusiness) {
		this.dynaBeanBusiness = dynaBeanBusiness;
	}
	
	@Override
	public PageList<DynaBean> getPerson(SelectPersonQuery bean) throws SelectPersonException {
		
		InfoClass clazz = InfoClassCache.getOverallInfoClass( bean.getConfig().getType().getName() );
		
		DynaBeanQuery query = new DynaBeanQuery( clazz );
		query.setPerPageSize(bean.getPerPageSize());
		query.setToPage(bean.getToPage());
		query.setExpress( bean.getCondition() );
		query.setParams( bean.getValues() );
		
		bean.setStartRow(query.getStartRow());
		bean.setEndRow(query.getEndRow());
		return dynaBeanBusiness.findPagingInfoList(query);
	}

}
