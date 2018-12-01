package com.zfsoft.mobile.notice.action;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.notice.entity.NoticeCatalog;
import com.zfsoft.mobile.notice.entity.NoticeKind;
import com.zfsoft.mobile.notice.query.NotiCatalogQuery;
import com.zfsoft.mobile.notice.query.NotiKindQuery;
import com.zfsoft.mobile.notice.service.INoticeCatalogService;
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
public class NoticeCatalogAction extends HrmAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3227982602545758470L;

	private INoticeCatalogService noticeCatalogService;
	
	private IBusinessService businessService;
	
	private PageList<NoticeCatalog> pageList;
	
	private NotiCatalogQuery query = new NotiCatalogQuery();
	
	private NoticeCatalog model = new NoticeCatalog();
	
	private String op ="add";
	
	/**
	 * 通知公告列表Action
	 * @return
	 */
	public String list() {
		BusinessQuery businessquery = new BusinessQuery();
		PageList<Business> businessList = businessService.getList(businessquery);
		this.getValueStack().set("businessList", businessList);
		pageList = noticeCatalogService.getPageList(query);
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
		model = noticeCatalogService.findById(query);
		return "edit";
	}
	
	/**
	 * 通知公告保存Action
	 * @return
	 */
	public String save() {
		if ("add".equals(op)) {
			NoticeCatalog noticeCatalog = noticeCatalogService.findByName(query);
			if (noticeCatalog != null) {
				this.setErrorMessage("通知种类已存在");
				this.getValueStack().set("data", getMessage());
				return "data";
			}
			query.setCreator(getUser().getYhm());
			try {
				noticeCatalogService.doSave(query);
				this.setSuccessMessage("操作成功");
			} catch (Exception e) {
				this.setSuccessMessage(e.getMessage());
				e.printStackTrace();
			}
		} else if ("modify".equals(op)) {
			NoticeCatalog noticeCatalog = noticeCatalogService.findByNameOtherId(query);
			if (noticeCatalog != null) {
				this.setErrorMessage("通知种类已存在");
				this.getValueStack().set("data", getMessage());
				return "data";
			}
			query.setUpdater(getUser().getYhm());
			this.setSuccessMessage("操作成功");
			try {
				noticeCatalogService.doUpdate(query);
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
			noticeCatalogService.doControl(query);
			this.setSuccessMessage("操作成功");
		} catch (Exception e) {
			this.setErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		this.getValueStack().set("data", getMessage());
		return "data";
	}

	

	public INoticeCatalogService getNoticeCatalogService() {
		return noticeCatalogService;
	}

	public void setNoticeCatalogService(INoticeCatalogService noticeCatalogService) {
		this.noticeCatalogService = noticeCatalogService;
	}

	public PageList<NoticeCatalog> getPageList() {
		return pageList;
	}

	public void setPageList(PageList<NoticeCatalog> pageList) {
		this.pageList = pageList;
	}

	public NoticeCatalog getModel() {
		return model;
	}

	public void setModel(NoticeCatalog model) {
		this.model = model;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public NotiCatalogQuery getQuery() {
		return query;
	}

	public void setQuery(NotiCatalogQuery query) {
		this.query = query;
	}

	public void setBusinessService(IBusinessService businessService) {
		this.businessService = businessService;
	}

	public IBusinessService getBusinessService() {
		return businessService;
	}
	
	
}
