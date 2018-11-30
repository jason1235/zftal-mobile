package com.zfsoft.mobile.webcasts.service.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.webcasts.dao.IWebcastsAuditDao;
import com.zfsoft.mobile.webcasts.entity.WebcastsAuditEntity;
import com.zfsoft.mobile.webcasts.service.IWebcastsAuditService;

public class WebcastsAuditServiceImpl implements IWebcastsAuditService{

	private IWebcastsAuditDao auditDao;
	
	@Override
	public PageList<WebcastsAuditEntity> getPageList(WebcastsAuditEntity query) {
		// TODO Auto-generated method stub
		Paginator paginator = new Paginator();
		PageList<WebcastsAuditEntity> pageList = new PageList<WebcastsAuditEntity>();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			//当前页数
			paginator.setPage(query.getToPage());
			paginator.setItems(auditDao.getPageCount(query));
			pageList.setPaginator(paginator);
			//当前页码大于总页数，返回列表
			if((Integer)query.getToPage() > paginator.getPages()){
				return pageList;
			}
			/**
			 * 当前页的起始项序号小于总项数量
			 * 设置当前页的起始和结束序号并返回页面
			 */
			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<WebcastsAuditEntity> list = auditDao.getPageList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public void application(WebcastsAuditEntity query) {
		// TODO Auto-generated method stub
		auditDao.application(query);
	}

	@Override
	public void audit(WebcastsAuditEntity query) {
		// TODO Auto-generated method stub
		auditDao.audit(query);
	}

	@Override
	public WebcastsAuditEntity getResultByUserid(String userid) {
		// TODO Auto-generated method stub
		return auditDao.getResultByUserid(userid);
	}

	@Override
	public WebcastsAuditEntity findById(WebcastsAuditEntity query) {
		// TODO Auto-generated method stub
		return auditDao.findById(query);
	}

	public IWebcastsAuditDao getAuditDao() {
		return auditDao;
	}

	public void setAuditDao(IWebcastsAuditDao auditDao) {
		this.auditDao = auditDao;
	}

	@Override
	public void delete(String appid) {
		// TODO Auto-generated method stub
		auditDao.delete(appid);
	}

	
	
}
