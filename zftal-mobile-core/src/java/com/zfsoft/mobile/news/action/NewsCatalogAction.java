package com.zfsoft.mobile.news.action;


import java.util.HashMap;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.common.enums.ShowTypeEnum;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.common.utils.ImageTagHtml;
import com.zfsoft.mobile.configuration.entity.NewsConfig;
import com.zfsoft.mobile.configuration.query.NewsConfigQuery;
import com.zfsoft.mobile.configuration.service.INewsConfigService;
import com.zfsoft.mobile.news.entity.News;
import com.zfsoft.mobile.news.entity.NewsCatalog;
import com.zfsoft.mobile.news.query.NewsCatalogQuery;
import com.zfsoft.mobile.news.query.NewsQuery;
import com.zfsoft.mobile.news.service.INewsCatalogService;
import com.zfsoft.mobile.news.service.INewsService;
import com.zfsoft.mobile.news.service.impl.NewsServiceImpl;
import com.zfsoft.util.base.StringUtil;

/**
 * 资讯类别Action
 * @author wy
 *
 */
public class NewsCatalogAction extends HrmAction {
	
	private static final long serialVersionUID = 1L;
	
	private static final String TT_NAME = "tt_name";

	private INewsCatalogService newsCatalogService;
	
	private INewsService newsService;
	
	private INewsConfigService newsConfigService;
	
	private IMobileCommonService mobileCommonService;
	
	private PageList<NewsCatalog> pageList;
	
	private NewsCatalogQuery query = new NewsCatalogQuery();
	
	private NewsCatalog model;
	
	private String op = "add";
	
	private String catalogLogo = "";
	
	private String originalCatalog;
	
	public String getOriginalCatalog() {
		return originalCatalog;
	}

	public void setOriginalCatalog(String originalCatalog) {
		this.originalCatalog = originalCatalog;
	}

	/**
	 * 资讯类别列表Action
	 * @return
	 */
	public String list() {
		this.getValueStack().set("showTypeEnum", ShowTypeEnum.values());
		
		pageList = newsCatalogService.getPageList(query);
		NewsConfigQuery configQuery = new NewsConfigQuery();
		configQuery.setKey(TT_NAME);
		NewsConfig config = newsConfigService.findByKey(configQuery);
		NewsQuery nQuery = new NewsQuery();
		nQuery.setHeadline("1");
		PageList<News> news = newsService.getPageList(nQuery);
		int hc = 0;
		if (news != null && news.size() > 0) {
			hc = news.size();
		}
		this.getValueStack().set("headline", config);
		this.getValueStack().set("hc", hc);
		return "list";
	}
	
	/**
	 * 新增资讯Action
	 * @return
	 */
	public String add(){
		this.getValueStack().set("showTypeEnum", ShowTypeEnum.values());
		this.getValueStack().set("imageHtml", ImageTagHtml.getImageHtml("catalogLogo", Type.IMAGE, 1024, 150, 150, null, true));
		return "edit";
	}
	
	/**
	 * 修改资讯Action
	 * @return
	 */
	public String modify() {
		op = "modify";
		model = newsCatalogService.findById(query);
		this.getValueStack().set("showTypeEnum", ShowTypeEnum.values());
		this.getValueStack().set("imageHtml", ImageTagHtml.getImageHtml("catalogLogo", Type.IMAGE, 1024, 150, 150, model.getLogoId(), true));
		return "edit";
	}
	
	/**
	 * 新增/修改保存操作Action
	 * @return
	 */
	public String save() {
	
		if ("add".equals(op)) {
			query.setCreator(getUser().getYhm());
		} else {
			query.setUpdater(getUser().getYhm());
		}
		if (!StringUtil.isEmpty(catalogLogo)) {
			query.setLogoId(catalogLogo);
			query.setLogoUrl(mobileCommonService.getUploadImagePath(catalogLogo));
		}
		
		try {
			newsCatalogService.doSave(query,originalCatalog);
			this.setSuccessMessage("操作成功");
		} catch (Exception e) {
			this.setErrorMessage(e.getMessage());
			//e.printStackTrace();
		}
		
		this.getValueStack().set("data", this.getMessage());
		return "data";
	}
	
	/**
	 * 保存修改后的索引顺序Action
	 * @return
	 */
	public String updateIndex() {
		String[] ids = getRequest().getParameterValues("ids");
		String minStr = getRequest().getParameter("minIndex");
		//String maxStr = getRequest().getParameter("maxIndex");
		int min =0;
		if (minStr != null) {
			min = Integer.parseInt(minStr);
		}
		Map<String, String> map = null;
		if (ids != null) {
			try {
				for (int i =0; i <ids.length; i++) {
					map = new HashMap<String, String>();
					map.put("index", (i+min)+"");
					map.put("id", ids[i]);
					newsCatalogService.updateIndex(map);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return list();
	}
	
	/**
	 * 删除类别Action
	 * @return
	 */
	public String delete() {
		
		try {
			newsCatalogService.doDelete(query);
			this.setSuccessMessage("删除成功");
		} catch (Exception e) {
			this.setErrorMessage(e.getMessage());
			//e.printStackTrace();
		}
		this.getValueStack().set("data", getMessage());
		return "data";
	}
	
	/**
	 * 启用类别
	 * @return
	 */
	public String enable() {
		try {
			newsCatalogService.enable(query);
			this.setSuccessMessage("启用成功");
		} catch (Exception e) {
			this.setErrorMessage(e.getMessage());
			//e.printStackTrace();
		}
		this.getValueStack().set("data", getMessage());
		return "data";
	}
	
	/**
	 * 停用类别
	 * @return
	 */
	public String disable() {
		int count = newsCatalogService.countEnable(query);
		if (count > 0) {
			this.setErrorMessage("不能停用，该类别下还有启用的资讯");
			this.getValueStack().set("data", getMessage());
			return "data";
		}
		try {
			newsCatalogService.disable(query);
			this.setSuccessMessage("停用成功");
		} catch (Exception e) {
			this.setErrorMessage(e.getMessage());
			//e.printStackTrace();
		}
		this.getValueStack().set("data", getMessage());
		return "data";
	}

	public INewsCatalogService getNewsCatalogService() {
		return newsCatalogService;
	}

	public void setNewsCatalogService(INewsCatalogService newsCatalogService) {
		this.newsCatalogService = newsCatalogService;
	}

	public PageList<NewsCatalog> getPageList() {
		return pageList;
	}

	public void setPageList(PageList<NewsCatalog> pageList) {
		this.pageList = pageList;
	}

	public NewsCatalogQuery getQuery() {
		return query;
	}

	public void setQuery(NewsCatalogQuery query) {
		this.query = query;
	}

	public NewsCatalog getModel() {
		return model;
	}

	public void setModel(NewsCatalog model) {
		this.model = model;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getCatalogLogo() {
		return catalogLogo;
	}

	public void setCatalogLogo(String catalogLogo) {
		this.catalogLogo = catalogLogo;
	}

	public IMobileCommonService getMobileCommonService() {
		return mobileCommonService;
	}

	public void setMobileCommonService(IMobileCommonService mobileCommonService) {
		this.mobileCommonService = mobileCommonService;
	}

	public INewsConfigService getNewsConfigService() {
		return newsConfigService;
	}

	public void setNewsConfigService(INewsConfigService newsConfigService) {
		this.newsConfigService = newsConfigService;
	}

	public INewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(INewsService newsService) {
		this.newsService = newsService;
	}
	
	
}
