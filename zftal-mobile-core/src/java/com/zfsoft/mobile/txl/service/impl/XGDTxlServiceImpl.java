package com.zfsoft.mobile.txl.service.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.services.entity.Business;
import com.zfsoft.mobile.txl.dao.IXGDTxlDao;
import com.zfsoft.mobile.txl.entity.XGDTxl;
import com.zfsoft.mobile.txl.entity.XgdCommentsEntity;
import com.zfsoft.mobile.txl.query.XGDTxlQuery;
import com.zfsoft.mobile.txl.service.IXGDTxlService;

public class XGDTxlServiceImpl implements IXGDTxlService {

	private IXGDTxlDao xgdTxlDao;
	
	@Override
	public XGDTxl findById(XGDTxlQuery query) {
		return xgdTxlDao.findById(query);
	}

	@Override
	public PageList<XGDTxl> getPageList(XGDTxlQuery query) {
		PageList<XGDTxl> pageList = new PageList<XGDTxl>();
		Paginator paginator = new Paginator();
		if (query != null) {
			int items = xgdTxlDao.getPageCount(query);
			paginator.setItemsPerPage(items);
			paginator.setPage(query.getToPage());
			paginator.setItems(items);
			pageList.setPaginator(paginator);
			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				pageList.addAll(xgdTxlDao.getPageList(query));
			}
		}
		
		return pageList;
	}
	
	@Override
	public PageList<XGDTxl> getPageListRe(XGDTxlQuery query) {
		PageList<XGDTxl> pageList = new PageList<XGDTxl>();
		Paginator paginator = new Paginator();
		if (query != null) {
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(xgdTxlDao.getPageCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<XGDTxl> list = xgdTxlDao.getPageList(query);
				pageList.addAll(list);
			}
		}
		
		return pageList;
	}

	@Override
	public int getPageCount(XGDTxlQuery query) {
		return xgdTxlDao.getPageCount(query);
	}

	@Override
	public int insert(XGDTxlQuery query) {
		return xgdTxlDao.insert(query);
	}

	@Override
	public int update(XGDTxlQuery query) {
		return xgdTxlDao.update(query);
	}
	
	@Override
	public int deleteAll() {
		return xgdTxlDao.deleteAll();
	}

	public IXGDTxlDao getXgdTxlDao() {
		return xgdTxlDao;
	}

	public void setXgdTxlDao(IXGDTxlDao xgdTxlDao) {
		this.xgdTxlDao = xgdTxlDao;
	}

	@Override
	public void insertComment(XgdCommentsEntity entity) {
		xgdTxlDao.insertComment(entity);
	}

	@Override
	public List<XgdCommentsEntity> getCommentList() {
		return xgdTxlDao.getCommentList();
	}

	@Override
	public void deleteAllComment() {
		xgdTxlDao.deleteAllComment();
	}
	
	
	
}
