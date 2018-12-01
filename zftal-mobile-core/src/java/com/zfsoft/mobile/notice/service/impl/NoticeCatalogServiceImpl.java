package com.zfsoft.mobile.notice.service.impl;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.notice.dao.INoticeCatalogDao;
import com.zfsoft.mobile.notice.dao.INoticeKindDao;
import com.zfsoft.mobile.notice.entity.NoticeCatalog;
import com.zfsoft.mobile.notice.entity.NoticeKind;
import com.zfsoft.mobile.notice.query.NotiCatalogQuery;
import com.zfsoft.mobile.notice.query.NotiKindQuery;
import com.zfsoft.mobile.notice.service.INoticeCatalogService;
import com.zfsoft.mobile.notice.service.INoticeKindService;

public class NoticeCatalogServiceImpl implements INoticeCatalogService {

	private INoticeCatalogDao noticeCatalogDao;
	
	public INoticeCatalogDao getNoticeCatalogDao() {
		return noticeCatalogDao;
	}

	public void setNoticeCatalogDao(INoticeCatalogDao noticeCatalogDao) {
		this.noticeCatalogDao = noticeCatalogDao;
	}

	@Override
	public PageList<NoticeCatalog> getPageList(NotiCatalogQuery query) {
		PageList<NoticeCatalog> pageList = new PageList<NoticeCatalog>();
		Paginator paginator = new Paginator();
		if (query != null) {
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage(query.getToPage());
			paginator.setItems(noticeCatalogDao.getPageCount(query));
			pageList.setPaginator(paginator);
			
			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				pageList.addAll(noticeCatalogDao.getPageList(query));
			}
		}
		return pageList;
	}

	@Override
	public NoticeCatalog findById(NotiCatalogQuery query) {
		
		return noticeCatalogDao.findById(query);
	}
	
	@Override
	public NoticeCatalog findByName(NotiCatalogQuery query) {
		
		return noticeCatalogDao.findByName(query);
	}

	@Override
	public void doSave(NotiCatalogQuery query) throws Exception {
		noticeCatalogDao.insert(query);
	}

	@Override
	public void doUpdate(NotiCatalogQuery query) throws Exception {
		noticeCatalogDao.update(query);
	}

	@Override
	public void doControl(NotiCatalogQuery query) throws Exception {
		noticeCatalogDao.noticeControl(query);

	}

	@Override
	public NoticeCatalog findByNameOtherId(NotiCatalogQuery query) {
		// TODO Auto-generated method stub
		return noticeCatalogDao.findByNameOtherId(query);
	}


	
	

}
