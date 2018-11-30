package com.zfsoft.mobile.webcasts.service.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.webcasts.dao.IWebcastsDao;
import com.zfsoft.mobile.webcasts.entity.WebcastsEntity;
import com.zfsoft.mobile.webcasts.service.IWebcastsService;


public class WebcastsServiceImpl implements IWebcastsService{
	
	private IWebcastsDao webcastDao;

	public IWebcastsDao getWebcastDao() {
		return webcastDao;
	}

	public void setWebcastDao(IWebcastsDao webcastDao) {
		this.webcastDao = webcastDao;
	}

	@Override
	public PageList<WebcastsEntity> getPageList(WebcastsEntity query) {
		// TODO Auto-generated method stub
		Paginator paginator = new Paginator();
		PageList<WebcastsEntity> pageList = new PageList<WebcastsEntity>();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			//当前页数
			paginator.setPage(query.getToPage());
			paginator.setItems(webcastDao.getPageCount(query));
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
				List<WebcastsEntity> list = webcastDao.getPageList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public WebcastsEntity findById(String webcastId) {
		// TODO Auto-generated method stub
		return webcastDao.findById(webcastId);
	}

	@Override
	public void insert(WebcastsEntity query) {
		// TODO Auto-generated method stub
		webcastDao.insert(query);
	}

	@Override
	public int update(WebcastsEntity query) {
		// TODO Auto-generated method stub
		return	webcastDao.update(query);
	}

	@Override
	public void updateStatus(WebcastsEntity query) {
		// TODO Auto-generated method stub
		webcastDao.updateStatus(query);
	}

	@Override
	public void updateDropNum(WebcastsEntity query) {
		// TODO Auto-generated method stub
		webcastDao.updateDropNum(query);
	}

	@Override
	public int webcastsControl(WebcastsEntity query) {
		// TODO Auto-generated method stub
		return webcastDao.webcastsControl(query);
	}

	@Override
	public void deleteWebcasts(String webcastId) {
		// TODO Auto-generated method stub
		webcastDao.deleteWebcasts(webcastId);
	}

	@Override
	public WebcastsEntity getWebcastsByUserid(String userid) {
		// TODO Auto-generated method stub
		return webcastDao.getWebcastsByUserid(userid);
	}

}
