package com.zfsoft.hrm.notice.service.impl;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.notice.business.INoticeBusiness;
import com.zfsoft.hrm.notice.entity.Notice;
import com.zfsoft.hrm.notice.query.NoticeQuery;
import com.zfsoft.hrm.notice.service.INoticeService;

/** 
 * @author jinjj
 * @date 2012-9-26 上午11:47:24 
 *  
 */
public class NoticeServiceImpl extends NoticeAttachementService implements INoticeService {

	private INoticeBusiness noticeBusiness;
	
	@Override
	public void save(Notice notice) {
		noticeBusiness.save(notice);
		addAttachements(notice);
	}

	@Override
	public void delete(String guid) {
		removeAttachementsByFormId(guid);
		noticeBusiness.delete(guid);
	}

	@Override
	public PageList<Notice> getPagingList(NoticeQuery query) {
		return noticeBusiness.getPagingList(query);
	}

	@Override
	public void update(Notice notice) {
		removeAttachements(notice);
		addAttachements(notice);
		noticeBusiness.update(notice);
	}

	@Override
	public void updateStatus(Notice notice) {
		noticeBusiness.updateStatus(notice);
	}

	@Override
	public Notice getById(String guid) {
		Notice notice = noticeBusiness.getById(guid);
		notice.setAttachements(getAttachementsByFormId(guid));
		return notice;
	}

	public void setNoticeBusiness(INoticeBusiness noticeBusiness) {
		this.noticeBusiness = noticeBusiness;
	}

}
