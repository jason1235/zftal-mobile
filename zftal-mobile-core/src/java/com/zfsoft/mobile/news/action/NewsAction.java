package com.zfsoft.mobile.news.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.common.enums.ServiceSourceEnum;
import com.zfsoft.mobile.common.enums.ServiceTypeEnum;
import com.zfsoft.mobile.common.enums.ShowTypeEnum;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.common.utils.ImageTagHtml;
import com.zfsoft.mobile.common.utils.MobileSystemHolder;
import com.zfsoft.mobile.configuration.entity.NewsConfig;
import com.zfsoft.mobile.configuration.query.NewsConfigQuery;
import com.zfsoft.mobile.configuration.service.INewsConfigService;
import com.zfsoft.mobile.news.entity.Counts;
import com.zfsoft.mobile.news.entity.News;
import com.zfsoft.mobile.news.entity.NewsCatalog;
import com.zfsoft.mobile.news.query.NewsCatalogQuery;
import com.zfsoft.mobile.news.query.NewsQuery;
import com.zfsoft.mobile.news.service.INewsCatalogService;
import com.zfsoft.mobile.news.service.INewsService;
import com.zfsoft.mobile.services.dao.query.BusinessQuery;
import com.zfsoft.mobile.services.dao.query.ServiceManagerQuery;
import com.zfsoft.mobile.services.entity.Business;
import com.zfsoft.mobile.services.entity.ServiceManager;
import com.zfsoft.mobile.services.service.IBusinessService;
import com.zfsoft.mobile.services.service.IServiceManagerService;
import com.zfsoft.util.base.StringUtil;

public class NewsAction extends HrmAction {

	private static final String MAX_NEWS_TOP = "max_news_top";
	private static final String MAX_NEWS_RECOMMEND = "max_news_recommend";
	private static final String MAX_NEWS_HEADLINE = "max_news_headline";
	
	private INewsService newsService;
	
	private INewsCatalogService newsCatalogService;
	
	private IBusinessService businessService;
	
	private IMobileCommonService mobileCommonService;
	
	private INewsConfigService newsConfigService;
	
	private IServiceManagerService serviceManagerService;
	
	private PageList<News> pageList;
	
	private NewsQuery query = new NewsQuery();
	
	private News model;
	
	private String op = "add";
	
	//private String newsLogo="";
	
	private List<String> newsLogo;
	
	public String test() {
		System.out.println(newsLogo.size());
		return "data";
	}
	
	/**
	 * 资讯列表Action
	 * @return
	 */
	public String list() {
		pageList = newsService.getPageList(query);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isAll", "false");
		List<NewsCatalog> catalogList = newsCatalogService.getAllCatalog(map);
		List<Business> businessList = businessService.getList(new BusinessQuery());
		this.getValueStack().set("catalogList", catalogList);
		this.getValueStack().set("businessList", businessList);
		this.getValueStack().set("showTypeEnum", ShowTypeEnum.values());
		return "list";
	}
	
	public String show() {
		if (query.getId() == null) {
			return "show";
		}
		model = newsService.findById(query);
		this.getValueStack().set("news", model);
		return "show";
	}
	
	public String image() {
		this.getValueStack().set("data", ImageTagHtml.getImageHtml("newsLogo", Type.IMAGE, 1024, 2, 1, null, true));
		return "data";
	}
	
	/**
	 * 资讯添加Action
	 * @return
	 */
	public String add() {
		List<Business> businessList = businessService.getList(new BusinessQuery());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isAll", "true");
		List<NewsCatalog> catalogList = newsCatalogService.getAllCatalog(map);
		this.getValueStack().set("catalogList",catalogList);
		this.getValueStack().set("catalog", catalogToJSONString(catalogList));
		this.getValueStack().set("businessList", businessList);
		this.getValueStack().set("imageHtml", ImageTagHtml.getImageHtml("newsLogo", Type.IMAGE, 1024, 2, 1, null, true));
		
		String fileHtml = ImageTagHtml.getFileHtml("vedioId", Type.FILE,"mp4,avi,wmv", 200, 150, 150,null);
        getValueStack().set("fileHtml", fileHtml);
		return "edit";
	}
	
	/**
	 * 资讯编辑Action
	 * @return
	 */
	public String modify() {
		op = "modify";
		model = newsService.findById(query);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isAll", "false");
		List<NewsCatalog> catalogList = newsCatalogService.getAllCatalog(map);
		List<Business> businessList = businessService.getList(new BusinessQuery());
		this.getValueStack().set("catalogList", catalogList);
		this.getValueStack().set("catalog", catalogToJSONString(catalogList));
		this.getValueStack().set("businessList", businessList);
		this.getValueStack().set("query", query);
		if(StringUtil.isEmpty(model.getPicId())){
			getValueStack().set("imageHtmlArr", "");
			getValueStack().set("flag", false);
		}else{
			String[] logoIds   = model.getPicId().split(",");
			ArrayList<String> imageHtmlArr = new ArrayList<String>();
			for (int i = 0; i < logoIds.length; i++) {
				imageHtmlArr.add(ImageTagHtml.getImageHtml("newsLogo", Type.IMAGE, 1024, 2, 1, logoIds[i], true));
			}
			getValueStack().set("imageHtmlArr", imageHtmlArr);
			getValueStack().set("flag", logoIds.length == 3 ? true : false);
		}
		
		String fileHtml = ImageTagHtml.getFileHtml("vedioId", Type.FILE,"mp4,avi,wmv", 200, 150, 150,model.getVedioId());
        getValueStack().set("fileHtml", fileHtml);
		//this.getValueStack().set("imageHtml", ImageTagHtml.getImageHtml("newsLogo", Type.IMAGE, 1024, 90, 90, model.getPicId(), true));
		return "edit";
	}
	
	/**
	 * 保存操作Action
	 * @return
	 */
	public String save() {
		if ("add".equals(op)) {
			query.setCreator(getUser().getYhm());
		} else {
			query.setUpdater(getUser().getYhm());
		}
		
		System.out.println(query.getContent());
		/*if(StringUtil.isEmpty(query.getContent())){
			this.setErrorMessage("资讯内容不能为空");
			this.getValueStack().set("data", getMessage());
			return "data";
		}*/
		
		//String title = query.getTitle();
		
		
		String vedioUrl = null;
		query.setVedioId(getRequest().getParameter("vedioId"));
		if(!StringUtil.isEmpty(getRequest().getParameter("vedioId"))){
			vedioUrl = mobileCommonService.getNewsVedioPath(getRequest().getParameter("vedioId"));
		}
		query.setVedioUrl(vedioUrl);
		
		
		try {
			StringBuilder logos = new StringBuilder();
			StringBuilder logoUrls = new StringBuilder();
			
			int logoSize = 0;
			if(newsLogo != null){
				for (int i = 0; i < newsLogo.size(); i++) {
					if(StringUtil.isEmpty(newsLogo.get(i))){
						logoSize++;
					}
				}
			}
			
			if(newsLogo != null && logoSize != newsLogo.size() && newsLogo.size() > 0){
				for (int i = 0; i < newsLogo.size(); i++) {
					if(StringUtil.isEmpty(newsLogo.get(i))){
						this.setErrorMessage("请确认所有图片已上传!");
						this.getValueStack().set("data", getMessage());
						return "data";
					}
					if(i != newsLogo.size()-1){
						logos.append(newsLogo.get(i)).append(",");
						logoUrls.append(mobileCommonService.getNewsUploadImagePath(newsLogo.get(i))).append(",");
					}else{
						logos.append(newsLogo.get(i));
						logoUrls.append(mobileCommonService.getNewsUploadImagePath(newsLogo.get(i)));
					}
				}
			}
			
			News entity = newsService.findById(query);
			if(entity != null && !StringUtil.isEmpty(entity.getRecommend()) && entity.getRecommend().equals("1")){
				if(query.getIsremotelogo().equals("1")){
					if(StringUtil.isEmpty(query.getRemotelogourl())){
						this.setErrorMessage("此资讯为推荐位新闻，不能没有图片");
						this.getValueStack().set("data", getMessage());
						return "data";
					}
				}else{
					if(StringUtil.isEmpty(logos.toString()) || StringUtil.isEmpty(logoUrls.toString())){
						this.setErrorMessage("此资讯为推荐位新闻，不能没有图片");
						this.getValueStack().set("data", getMessage());
						return "data";
					}
				}
			}
			
			query.setPicId(logos.toString());
			query.setPicUrl(logoUrls.toString());
			//query.setId(null);
			query.setCreator(getUser().getYhm());
			newsService.doSave(query);
			
			
			
			/*if (newsLogo != null && newsLogo.size() > 0) {
				if (newsLogo.size() > 1) {
					// 已经上传了多张图片，此时的状态变为添加状态
					query.setId(null);
					query.setCreator(getUser().getYhm());
				}
				for (int i = 0; i < newsLogo.size(); i++) {
					query.setPicId(newsLogo.get(i));
					query.setPicUrl(mobileCommonService.getNewsUploadImagePath(query.getPicId()));
					if (title != null) {
						String[] titles = title.split(",");
						if (titles.length-1 >= i) {
							query.setTitle(titles[i]);
						}
					}
					
					NewsCatalogQuery catalogQuery = new NewsCatalogQuery();
					catalogQuery.setCatalogCode(query.getCatalogCode());
					String zxxs = newsCatalogService.getPageList(catalogQuery).get(0).getShowType();
					if(zxxs.equals("DETAIL_SHOW")){
						if (StringUtil.isEmpty(query.getId())) {
							//判断只能添加一个简洁start
							NewsQuery jjQuery = new NewsQuery();
							jjQuery.setShowType(ShowTypeEnum.DETAIL_SHOW.getKey());
							int size = newsService.getPageList(jjQuery).size();
							if(size>0){
								this.setErrorMessage("已经存在资讯简介!");
								this.getValueStack().set("data", getMessage());
								return "data";
							}
							//判断只能添加一个简洁end
							
							int maxWebFwbm = serviceManagerService.getMaxWebFwbm();
							query.setFwbm(String.valueOf(maxWebFwbm));
							newsService.doSave(query);
							ServiceManager serviceModel = new ServiceManager();
							serviceModel.setClassFwbm(String.valueOf(maxWebFwbm));
							serviceModel.setClassFwmc(query.getTitle());
							serviceModel.setClassFwms(query.getIntro());
							BusinessQuery businessQuery = new BusinessQuery();
							businessQuery.setProcode("999");
							businessService.getYdht(businessQuery);
							String ssywxt = businessService.getList(businessQuery).get(0).getClassId();
							serviceModel.setClassSsywxt(ssywxt);
							serviceModel.setClassFwly(ServiceSourceEnum.CUSTOM_SOURCE.getKey());
							serviceModel.setClassFwlx(ServiceTypeEnum.WEB_SERVICE.getKey());
							String goUrl = "mobile/web_content.html?newsId=" + query.getId();
							serviceModel.setClassFwlj(goUrl);
							serviceModel.setNewsid(query.getId());
							serviceManagerService.insert(serviceModel);
						}else{
							newsService.doSave(query);
							NewsQuery newsquery = new NewsQuery();
							newsquery.setId(query.getId());
							String fwbm = newsService.getPageList(newsquery).get(0).getFwbm();
							ServiceManagerQuery serviceQuery = new ServiceManagerQuery();
							serviceQuery.setClassFwbm(fwbm);
							ServiceManager serviceModel = serviceManagerService.getList(serviceQuery).get(0);
							serviceModel.setClassFwmc(query.getTitle());
							serviceModel.setClassFwms(query.getIntro());
							serviceManagerService.update(serviceModel);
						}
					}else{
						newsService.doSave(query);
					}
						
				}
			}*/
			
			
			this.setSuccessMessage("保存成功");
		} catch (Exception e) {
			this.setErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		this.getValueStack().set("data", getMessage());
		return "data";
	}
	
	
	/**
	 * 资讯控制Action
	 * @return
	 */
	public String control() {
		Counts counts = new Counts();
//		if ("1".equals(query.getTop())) {
//			counts = newsService.getTopAndRmdCount(query);
//			int maxTop = getMax(MAX_NEWS_TOP);
//			//int maxTop = Integer.parseInt(MobileSystemHolder.getPropertiesValue("max_top"));
//			if (counts.getTopCount() >= maxTop) {
//				this.setErrorMessage("置顶数不能超过最大值"+maxTop);
//				this.getValueStack().set("data", getMessage());
//				return "data";
//			}
//		}
		if ("1".equals(query.getRecommend())) {
			counts = newsService.getTopAndRmdCount(query);
			//int maxRmd = Integer.parseInt(MobileSystemHolder.getPropertiesValue("max_recommend"));
			int maxRmd = getMax(MAX_NEWS_RECOMMEND);
			if (counts.getRmdCount() >= maxRmd) {
				this.setErrorMessage("推荐数不能超过最大值"+maxRmd);
				this.getValueStack().set("data", getMessage());
				return "data";
			}
			
			News entity = newsService.findById(query);
			if(entity.getShowType().equals("VEDIO_SHOW")){
				this.setErrorMessage("此资讯为视频类资讯，不能推荐为首页轮播图");
				this.getValueStack().set("data", getMessage());
				return "data";
			}
			if(entity.getIsremotelogo().equals("1")){
				if(StringUtil.isEmpty(entity.getRemotelogourl())){
					this.setErrorMessage("此资讯没有图片，不能推荐为首页轮播图");
					this.getValueStack().set("data", getMessage());
					return "data";
				}
			}else{
				if(StringUtil.isEmpty(entity.getPicId()) || StringUtil.isEmpty(entity.getPicUrl())){
					this.setErrorMessage("此资讯没有图片，不能推荐为首页轮播图");
					this.getValueStack().set("data", getMessage());
					return "data";
				}
			}
			
		}
		
		if ("1".equals(query.getHeadline())) {
			int count = newsService.getHeadlineCount();
			//int maxHeadline = Integer.parseInt(MobileSystemHolder.getPropertiesValue("max_headline"));
//			int maxHeadline = getMax(MAX_NEWS_HEADLINE);
			int maxHeadline = 5;
			if (count >= maxHeadline) {
//				this.setErrorMessage("头条数不能超过最大值"+maxHeadline);
				this.setErrorMessage("头条数不能超过最大值5");
				this.getValueStack().set("data", getMessage());
				return "data";
			}
		}
		
		if("1".equals(query.getStatus())){
			NewsCatalogQuery catalogQuery = new NewsCatalogQuery();
			catalogQuery.setCatalogCode(query.getCatalogCode());
			NewsCatalog catalog = newsCatalogService.getPageList(catalogQuery).get(0);
			if(catalog.getStatus().equals("0")){
				this.setErrorMessage("该资讯类别是停用状态，资讯不可启用，请先启用资讯类别！");
				this.getValueStack().set("data", getMessage());
				return "data";
			}
			
		}
		
		try {
			NewsCatalogQuery catalogQuery = new NewsCatalogQuery();
			catalogQuery.setCatalogCode(query.getCatalogCode());
			String zxxs = newsCatalogService.getPageList(catalogQuery).get(0).getShowType();
			if(zxxs.equals("DETAIL_SHOW")){
				NewsQuery newsquery = new NewsQuery();
				newsquery.setId(query.getId());
				String fwbm = newsService.getPageList(newsquery).get(0).getFwbm();
				Map<String, Object> param = new HashMap<String, Object>();
				List<String> tids = new ArrayList<String>();
				param.put("yhid", SessionFactory.getUser().getYhm());
				ServiceManagerQuery serviceQuery = new ServiceManagerQuery();
				serviceQuery.setClassFwbm(fwbm);
				serviceQuery.setClassDeleted("0");
				ServiceManager serviceModel = serviceManagerService.getList(serviceQuery).get(0);
				tids.add(serviceModel.getClassId());
				param.put("ids", tids);
				if(!StringUtil.isEmpty(query.getDeleted()) && query.getDeleted().equals("1")){
					serviceManagerService.remove(param);
				}
				/*if(!StringUtil.isEmpty(query.getStatus())){
					if(query.getStatus().equals("1")){
						param.put("fbzt", "1");
						serviceManagerService.fabu(param);
					}
					if(query.getStatus().equals("0")){
						param.put("fbzt", "0");
						serviceManagerService.quxiao(param);
					}
				}*/
			}
			newsService.controlNews(query);
			this.setSuccessMessage("操作成功");
		} catch (Exception e) {
			this.setErrorMessage(e.getMessage());
			//e.printStackTrace();
		}
		this.getValueStack().set("data", getMessage());
		return "data";
	}

	private int getMax(String key) {
		NewsConfigQuery configQuery = new NewsConfigQuery();
		configQuery.setKey(key);
		NewsConfig config = newsConfigService.findByKey(configQuery);
		int maxTop = 9999;
		if (config != null) {
			String maxTopValue = config.getValue();
			if (maxTopValue != null) {
				try {
					maxTop = Integer.parseInt(maxTopValue);
				} catch (Exception e) {
				}
				
			}
		}
		return maxTop;
	}
	
	/**
	 * 置顶/推荐统计Action
	 * @return
	 */
	public String count() {
		
		Counts counts = newsService.getTopAndRmdCount(query);
		String msg = "置顶数：" + counts.getTopCount() + "，推荐数：" + counts.getRmdCount();
		this.getValueStack().set("data", msg);
		return "data";
	}
	
	/**
	 * 将资讯类别List转为JSON格式字符串
	 * @param catalogList
	 * @return
	 */
	private String catalogToJSONString(List<NewsCatalog> catalogList) {
		StringBuilder builder = new StringBuilder("[");
		for (NewsCatalog catalog : catalogList) {
			if (StringUtil.isEmpty(catalog.getCatalogCode())) {
				catalog.setCatalogCode("");
			}
			if (StringUtil.isEmpty(catalog.getCatalogName())) {
				catalog.setCatalogName("");
			}
			builder.append("{'catalogCode':'").append(catalog.getCatalogCode()).append("','catalogName':'")
			.append(catalog.getCatalogName()).append("','showType':'");
			if (StringUtil.isEmpty(catalog.getShowType())) {
				builder.append("'},");
			} else {
				builder.append(ShowTypeEnum.valueOf(catalog.getShowType()).getText())
				.append("'},");
			}
			
		}
		String ret = builder.toString();
		if (ret.lastIndexOf(",") > -1) {
			ret = ret.substring(0, ret.length()-1) + "]";
		} else {
			ret = ret + "]";
		}
		return ret;
	}

	public INewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(INewsService newsService) {
		this.newsService = newsService;
	}

	public INewsCatalogService getNewsCatalogService() {
		return newsCatalogService;
	}

	public void setNewsCatalogService(INewsCatalogService newsCatalogService) {
		this.newsCatalogService = newsCatalogService;
	}

	public PageList<News> getPageList() {
		return pageList;
	}

	public void setPageList(PageList<News> pageList) {
		this.pageList = pageList;
	}

	public NewsQuery getQuery() {
		return query;
	}

	public void setQuery(NewsQuery query) {
		this.query = query;
	}

	public News getModel() {
		return model;
	}

	public void setModel(News model) {
		this.model = model;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public IBusinessService getBusinessService() {
		return businessService;
	}

	public void setBusinessService(IBusinessService businessService) {
		this.businessService = businessService;
	}

	public IMobileCommonService getMobileCommonService() {
		return mobileCommonService;
	}

	public void setMobileCommonService(IMobileCommonService mobileCommonService) {
		this.mobileCommonService = mobileCommonService;
	}

	public void setNewsLogo(List<String> newsLogo) {
		this.newsLogo = newsLogo;
	}

	public List<String> getNewsLogo() {
		return newsLogo;
	}

	public INewsConfigService getNewsConfigService() {
		return newsConfigService;
	}

	public void setNewsConfigService(INewsConfigService newsConfigService) {
		this.newsConfigService = newsConfigService;
	}

	public void setServiceManagerService(IServiceManagerService serviceManagerService) {
		this.serviceManagerService = serviceManagerService;
	}

	public IServiceManagerService getServiceManagerService() {
		return serviceManagerService;
	}

	
	
	
}
