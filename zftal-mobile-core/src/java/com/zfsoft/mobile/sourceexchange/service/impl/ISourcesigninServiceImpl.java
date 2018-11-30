package com.zfsoft.mobile.sourceexchange.service.impl;


import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.sourceexchange.dao.ISourcesigninDao;
import com.zfsoft.mobile.sourceexchange.entity.Sourcesigninhis;
import com.zfsoft.mobile.sourceexchange.service.ISourcesigninService;

public class ISourcesigninServiceImpl implements ISourcesigninService{
	
	private ISourcesigninDao signinDao;

	@Override
	public PageList<Sourcesigninhis> getPageList(Sourcesigninhis query) {
		Paginator paginator = new Paginator();
		PageList<Sourcesigninhis> pagelist=new PageList<Sourcesigninhis>();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			//当前页数
			paginator.setPage(query.getToPage());
			paginator.setItems(signinDao.getPageCount(query));
			pagelist.setPaginator(paginator);
			//当前页码大于总页数，返回列表
			if((Integer)query.getToPage() > paginator.getPages()){
				return pagelist;
			}
			/**
			 * 当前页的起始项序号小于总项数量
			 * 设置当前页的起始和结束序号并返回页面
			 */
			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<Sourcesigninhis> list=signinDao.getPageList(query);
				pagelist.addAll(list);
			}
		}
		return pagelist;
	}

	@Override
	public int signIn(Sourcesigninhis source) {
		// TODO Auto-generated method stub
		return signinDao.signIn(source);
	}

	@Override
	public int isSignedToday(Sourcesigninhis signed) {
		// TODO Auto-generated method stub
		return signinDao.isSignedToday(signed);
	}

	public ISourcesigninDao getSigninDao() {
		return signinDao;
	}

	public void setSigninDao(ISourcesigninDao signinDao) {
		this.signinDao = signinDao;
	}

}
