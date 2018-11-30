package com.zfsoft.hrm.notice.business.impl;

import java.util.Date;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.notice.business.INoticeBusiness;
import com.zfsoft.hrm.notice.dao.INoticeDao;
import com.zfsoft.hrm.notice.entity.Notice;
import com.zfsoft.hrm.notice.query.NoticeQuery;

/** 
 * @author jinjj
 * @date 2012-9-26 上午11:37:01 
 *  
 */
public class NoticeBusinessImpl implements INoticeBusiness {

	private INoticeDao noticeDao;
	
	@Override
	public void save(Notice notice) {
		notice.setCreateTime(new Date());
		noticeDao.insert(notice);
	}

	@Override
	public void delete(String guid) {
		noticeDao.delete(guid);
	}

	@Override
	public PageList<Notice> getPagingList(NoticeQuery query) {
		PageList<Notice> pageList = new PageList<Notice>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(noticeDao.getPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				pageList.addAll(noticeDao.getPagingList(query));
			}
		}
		return pageList;
	}

	@Override
	public void update(Notice notice) {
		noticeDao.update(notice);
	}

	@Override
	public void updateStatus(Notice notice) {
		Notice old = noticeDao.getById(notice.getGuid());
		old.setStatus(notice.getStatus());
		noticeDao.update(old);
	}

	@Override
	public Notice getById(String guid) {
		return noticeDao.getById(guid);
	}

	public void setNoticeDao(INoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}

}
