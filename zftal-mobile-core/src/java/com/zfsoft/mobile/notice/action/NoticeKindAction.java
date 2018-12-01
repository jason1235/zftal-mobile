package com.zfsoft.mobile.notice.action;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.notice.entity.NoticeKind;
import com.zfsoft.mobile.notice.query.NotiKindQuery;
import com.zfsoft.mobile.notice.service.INoticeKindService;
import com.zfsoft.mobile.services.dao.query.BusinessQuery;
import com.zfsoft.mobile.services.entity.Business;
import com.zfsoft.mobile.services.service.IBusinessService;
import com.zfsoft.mobile.services.service.impl.BusinessServiceImpl;

/**
 * 通知公告Action
 * @author wy
 *
 */
public class NoticeKindAction extends HrmAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7973346259017857317L;

	private INoticeKindService noticeKindService;
	
	private IBusinessService businessService;
	
	private PageList<NoticeKind> pageList;
	
	private NotiKindQuery query = new NotiKindQuery();
	
	private NoticeKind model = new NoticeKind();
	
	private String op ="add";
	
	/**
	 * 通知公告列表Action
	 * @return
	 */
	public String list() {
		BusinessQuery businessquery = new BusinessQuery();
		PageList<Business> businessList = businessService.getList(businessquery);
		this.getValueStack().set("businessList", businessList);
		pageList = noticeKindService.getPageList(query);
		return "list";
	}
	
	/**
	 * 通知公告增加Action
	 * @return
	 */
	public String add() {
		BusinessQuery businessquery = new BusinessQuery();
		PageList<Business> businessList = businessService.getList(businessquery);
		this.getValueStack().set("businessList", businessList);
		return "edit";
	}
	
	/**
	 * 通知公告修改Action
	 * @return
	 */
	public String modify() {
		BusinessQuery businessquery = new BusinessQuery();
		PageList<Business> businessList = businessService.getList(businessquery);
		this.getValueStack().set("businessList", businessList);
		op = "modify";
		model = noticeKindService.findById(query);
		return "edit";
	}
	
	/**
	 * 通知公告保存Action
	 * @return
	 */
	public String save() {
		if ("add".equals(op)) {
			NoticeKind noticeKind = noticeKindService.findByName(query);
			if (noticeKind != null) {
				this.setErrorMessage("种类名称已存在");
				this.getValueStack().set("data", getMessage());
				return "data";
			}
			query.setCreator(getUser().getYhm());
			try {
				noticeKindService.doSave(query);
				this.setSuccessMessage("操作成功");
			} catch (Exception e) {
				this.setSuccessMessage(e.getMessage());
				e.printStackTrace();
			}
		} else if ("modify".equals(op)) {
			NoticeKind noticeKind = noticeKindService.findByNameOtherId(query);
			if (noticeKind != null) {
				this.setErrorMessage("不可修改为此种类名称，已存在！");
				this.getValueStack().set("data", getMessage());
				return "data";
			}
			query.setUpdater(getUser().getYhm());
			this.setSuccessMessage("操作成功");
			try {
				noticeKindService.doUpdate(query);
			} catch (Exception e) {
				this.setSuccessMessage(e.getMessage());
				e.printStackTrace();
			}
		}
		this.getValueStack().set("data", getMessage());
		return "data";
	}
	
	/**
	 * 通知公告种类控制Action
	 * @return
	 */
	public String control() {
		try {
			noticeKindService.doControl(query);
			this.setSuccessMessage("操作成功");
		} catch (Exception e) {
			this.setErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		this.getValueStack().set("data", getMessage());
		return "data";
	}

	public INoticeKindService getNoticeKindService() {
		return noticeKindService;
	}

	public void setNoticeKindService(INoticeKindService noticeKindService) {
		this.noticeKindService = noticeKindService;
	}

	public PageList<NoticeKind> getPageList() {
		return pageList;
	}

	public void setPageList(PageList<NoticeKind> pageList) {
		this.pageList = pageList;
	}

	public NoticeKind getModel() {
		return model;
	}

	public void setModel(NoticeKind model) {
		this.model = model;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public NotiKindQuery getQuery() {
		return query;
	}

	public void setQuery(NotiKindQuery query) {
		this.query = query;
	}

	public void setBusinessService(IBusinessService businessService) {
		this.businessService = businessService;
	}

	public IBusinessService getBusinessService() {
		return businessService;
	}
	
	
}
