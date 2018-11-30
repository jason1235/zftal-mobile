package com.zfsoft.hrm.authpost.auth.service.impl;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.authpost.auth.dao.daointerface.IAuthDetailDao;
import com.zfsoft.hrm.authpost.auth.query.AuthDetailQuery;
import com.zfsoft.hrm.authpost.auth.service.svcinterface.IAuthDetailService;

/** 
 * 编制详细实现service
 * @author jinjj
 * @date 2012-7-27 上午11:50:00 
 *  
 */
public class AuthDetailServiceImpl implements IAuthDetailService {

	private IAuthDetailDao authDetailDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public PageList getPage(AuthDetailQuery query) {
		PageList pageList = new PageList();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(authDetailDao.getPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				pageList.addAll(authDetailDao.getPagingList(query));
			}
		}
		return pageList;
	}

	public void setAuthDetailDao(IAuthDetailDao authDetailDao) {
		this.authDetailDao = authDetailDao;
	}

}
