package com.zfsoft.hrm.notice.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.normal.resume.service.svcinterface.IStaffInfoService;
import com.zfsoft.hrm.notice.entity.Notice;
import com.zfsoft.hrm.notice.query.NoticeQuery;
import com.zfsoft.hrm.notice.service.INoticeService;
import com.zfsoft.util.base.StringUtil;

/** 
 * @author jinjj
 * @date 2012-9-26 下午05:33:29 
 *  
 */
public class NoticeViewAction extends HrmAction {

	private static final long serialVersionUID = -1792470475895677630L;

	private INoticeService noticeService;
	private IStaffInfoService staffInfoService;
	
	private Notice notice;
	private PageList<Notice> pageList;
	private NoticeQuery query = new NoticeQuery();
	private DynaBean bean;
	private String sortFieldName = null;
	private String asc = "up";
	
	public String page() throws Exception{
		query.setStatus(1);
		query.setUserName(getUser().getYhm());
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
	
	public String info() throws Exception{
		notice = noticeService.getById(notice.getGuid());
		return "info";
	}
	
	public String StaffInfolist() throws Exception{
		String gh = SessionFactory.getUser().getYhm();
		bean = staffInfoService.getStaffOverAllInfo(gh);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		if(!YhglModel.INNER_USER_ADMIN.equals(gh)){
			map.put("gh", getViewHtmlFromBean("gh"));
			map.put("xm", getViewHtmlFromBean("xm"));
			map.put("xb", getViewHtmlFromBean("xbm"));
			map.put("bm", getViewHtmlFromBean("dwm"));
			map.put("zc", getViewHtmlFromBean("przyjszw"));
			map.put("zzmm", getViewHtmlFromBean("zzmmm"));
			map.put("zp", getViewHtmlFromBean("zp"));
		}else{
			map.put("gh", YhglModel.INNER_USER_ADMIN);
			map.put("xm", YhglModel.INNER_USER_ADMIN);
			map.put("xb", "");
			map.put("bm", "");
			map.put("zc", "");
			map.put("zzmm", "");
			map.put("zp", "");
		}
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	private String getViewHtmlFromBean(String key){
		String value = ""; 
		if(bean != null){
			if(bean.getViewHtml() != null){
				value = bean.getViewHtml().get(key);
			}
		}		
		return value;
	}
	
	/**
	 * AJAX请求消息列表
	 * @return
	 * @throws Exception
	 */
	public String listData() throws Exception{
		query.setUserName(getUser().getYhm());
		query.setStatus(1);
		query.setPerPageSize(7);
		query.setOrderStr( " ZDZT DESC,FBSJ DESC" );
		pageList = noticeService.getPagingList(query);
		List<Notice> list = pageList;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("result", list);
		getValueStack().set(DATA, map);
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

	public void setStaffInfoService(IStaffInfoService staffInfoService) {
		this.staffInfoService = staffInfoService;
	}

	public DynaBean getBean() {
		return bean;
	}

	public void setBean(DynaBean bean) {
		this.bean = bean;
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
