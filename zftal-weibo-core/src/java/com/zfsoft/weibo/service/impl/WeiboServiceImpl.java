package com.zfsoft.weibo.service.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.weibo.action.entity.WeiBoEntity;
import com.zfsoft.weibo.dao.IWeiboDao;
import com.zfsoft.weibo.service.IWeiboService;

public class WeiboServiceImpl implements IWeiboService {

	private IWeiboDao weiboDao;

	public void setWeiboDao(IWeiboDao weiboDao) {
		this.weiboDao = weiboDao;
	}

	public IWeiboDao getWeiboDao() {
		return weiboDao;
	}

	@Override
	public PageList<WeiBoEntity> getPageList(WeiBoEntity query) {
		// TODO Auto-generated method stub
		PageList<WeiBoEntity> pageList = new PageList<WeiBoEntity>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(weiboDao.getPageListCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<WeiBoEntity> list = weiboDao.getPageList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public void insert(WeiBoEntity model) {
		// TODO Auto-generated method stub
		model.setIstimeout("1");
		model.setSfqy("0");
		weiboDao.insert(model);
	}

	@Override
	public void update(WeiBoEntity model) {
		// TODO Auto-generated method stub
		weiboDao.update(model);
	}

	@Override
	public void delete(WeiBoEntity model) {
		// TODO Auto-generated method stub
		weiboDao.delete(model);
	}

	@Override
	public void tingyong(WeiBoEntity model) {
		// TODO Auto-generated method stub
		model.setSfqy("0");
		weiboDao.updateById(model);
	}

	@Override
	public void qiyong(WeiBoEntity model) {
		// TODO Auto-generated method stub
		model.setSfqy("1");
		weiboDao.updateById(model);
	}

	@Override
	public void updateAccessById(WeiBoEntity model) {
		// TODO Auto-generated method stub
		weiboDao.updateAccessById(model);
	}
	
	
}
