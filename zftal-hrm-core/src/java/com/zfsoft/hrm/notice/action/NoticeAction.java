package com.zfsoft.hrm.notice.action;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.log.User;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.notice.entity.Notice;
import com.zfsoft.hrm.notice.query.NoticeQuery;
import com.zfsoft.hrm.notice.service.INoticeService;
import com.zfsoft.util.base.StringUtil;

/** 
 * 最新通知
 * @author jinjj
 * @date 2012-9-26 下午01:52:48 
 *  
 */
public class NoticeAction extends HrmAction {

	private static final long serialVersionUID = -2045932714669129342L;

	private INoticeService noticeService;
	
	private Notice notice;
	private NoticeQuery query = new NoticeQuery();
	private PageList<Notice> pageList;
	private String sortFieldName = null;
	private String asc = "up";

	public String page() throws Exception{
		if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( " ZDZT DESC" );
		}
		pageList = noticeService.getPagingList(query);
		return "page";
	}
	
	public String input() throws Exception{
		notice = new Notice();
		return "input";
	}
	
	public String save() throws Exception{
		try{
			User user = SessionFactory.getUser();
			notice.setAuthor(user.getYhm());
			noticeService.save(notice);
		}catch (Exception e) {
			setErrorMessage("添加信息失败" + e.getMessage());
		}
		getValueStack().set("success", getMessage());
		return "saveSuccess";
	}
	
	public String edit() throws Exception{
		notice = noticeService.getById(notice.getGuid());
		return "input";
	}
	
	public String update() throws Exception{
		try{
			noticeService.update(notice);
		}catch (Exception e) {
			setErrorMessage("修改信息失败" + e.getMessage());
		}
		getValueStack().set("success", getMessage());
		return "saveSuccess";
	}
	
	public String updateStatus() throws Exception{
		noticeService.updateStatus(notice);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String delete() throws Exception{
		noticeService.delete(notice.getGuid());
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public NoticeQuery getQuery() {
		return query;
	}

	public void setQuery(NoticeQuery query) {
		this.query = query;
	}

	public PageList<Notice> getPageList() {
		return pageList;
	}

	public void setNoticeService(INoticeService noticeService) {
		this.noticeService = noticeService;
	}

	public String getSortFieldName() {
		return sortFieldName;
	}

	public void setSortFieldName(String sortFieldName) {
		this.sortFieldName = sortFieldName;
	}

	public String getAsc() {
		return asc;
	}

	public void setAsc(String asc) {
		this.asc = asc;
	}
	
	
}
