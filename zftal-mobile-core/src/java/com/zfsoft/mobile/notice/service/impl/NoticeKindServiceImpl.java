package com.zfsoft.mobile.notice.service.impl;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.notice.dao.INoticeKindDao;
import com.zfsoft.mobile.notice.entity.NoticeKind;
import com.zfsoft.mobile.notice.query.NotiKindQuery;
import com.zfsoft.mobile.notice.service.INoticeKindService;

public class NoticeKindServiceImpl implements INoticeKindService {

	private INoticeKindDao noticeKindDao;
	
	@Override
	public PageList<NoticeKind> getPageList(NotiKindQuery query) {
		PageList<NoticeKind> pageList = new PageList<NoticeKind>();
		Paginator paginator = new Paginator();
		if (query != null) {
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage(query.getToPage());
			paginator.setItems(noticeKindDao.getPageCount(query));
			pageList.setPaginator(paginator);
			
			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				pageList.addAll(noticeKindDao.getPageList(query));
			}
		}
		return pageList;
	}

	@Override
	public NoticeKind findById(NotiKindQuery query) {
		
		return noticeKindDao.findById(query);
	}
	
	@Override
	public NoticeKind findByName(NotiKindQuery query) {
		
		return noticeKindDao.findByName(query);
	}

	@Override
	public void doSave(NotiKindQuery query) throws Exception {
		noticeKindDao.insert(query);
	}

	@Override
	public void doUpdate(NotiKindQuery query) throws Exception {
		noticeKindDao.update(query);
	}

	@Override
	public void doControl(NotiKindQuery query) throws Exception {
		noticeKindDao.noticeControl(query);

	}

	public INoticeKindDao getNoticeKindDao() {
		return noticeKindDao;
	}

	public void setNoticeKindDao(INoticeKindDao noticeKindDao) {
		this.noticeKindDao = noticeKindDao;
	}

	@Override
	public NoticeKind findByNameOtherId(NotiKindQuery query) {
		// TODO Auto-generated method stub
		return noticeKindDao.findByNameOtherId(query);
	}
	
	

}
