package com.zfsoft.hrm.authpost.auth.service.svcinterface;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.authpost.auth.query.AuthDetailQuery;

/** 
 * 编制详细service
 * @author jinjj
 * @date 2012-7-27 上午11:47:55 
 *  
 */
public interface IAuthDetailService {

	/**
	 * 编制详细分页
	 * @param query
	 * @return
	 */
	public PageList getPage(AuthDetailQuery query);
	
}
