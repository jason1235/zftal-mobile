package com.zfsoft.mobile.servlet.service.homePageHttp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.common.Config;
import com.google.gson.Gson;
import com.zfsoft.common.log.User;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.dao.entities.LoginModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.common.enums.ShowTypeEnum;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.configuration.entity.NewsConfig;
import com.zfsoft.mobile.configuration.query.NewsConfigQuery;
import com.zfsoft.mobile.configuration.service.INewsConfigService;
import com.zfsoft.mobile.news.entity.News;
import com.zfsoft.mobile.news.entity.NewsCatalog;
import com.zfsoft.mobile.news.query.NewsQuery;
import com.zfsoft.mobile.news.service.INewsCatalogService;
import com.zfsoft.mobile.news.service.INewsService;
import com.zfsoft.mobile.services.entity.ServiceManager;
import com.zfsoft.mobile.servlet.entity.BusinessSystemEntity;
import com.zfsoft.mobile.servlet.entity.MhRecommend;
import com.zfsoft.mobile.servlet.entity.MyPortalEntity;
import com.zfsoft.mobile.servlet.entity.MyPortalEntityItemEntity;
import com.zfsoft.mobile.servlet.entity.NewsEntity;
import com.zfsoft.mobile.servlet.entity.NewsTabEntity;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.mobile.servlet.entity.ServiceEntity;
import com.zfsoft.mobile.servlet.service.commonHttp.CommonHttp;
import com.zfsoft.service.svcinterface.ILoginService;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.date.DateTimeUtil;

public class HomePageHttp {
	private static Logger logger = Logger.getLogger(HomePageHttp.class);
	private final String infromation=Config.getString("mobile.infromation");
	/**
	 * 获取服务器访问路径
	 * @return
	 */
	private String getImageHost() {
		String url = BaseHolder.getPropertiesValue("suploadPath");
		if (url == null) {
			return "/";
		}
        url = url.replace("\\", "/");
        if (!url.endsWith("/")) {
        	url += "/";
        }
		return url;
	}
	
	
	private static final String SERVICE_FRONT = "service_front";
	private static final String TT_NAME = "tt_name";
	
	
	
	
	/**
	 * 获取新闻栏目
	 * @param username
	 * @param strKey
	 * @return
	 */
	public String getNewsTab(String username,String strKey) {
		Gson gson = new Gson();
		List<NewsTabEntity> tabList = null;
		if(StringUtil.isEmpty(strKey)){
			ResultEntity result = new ResultEntity<List<NewsTabEntity>>(0, "参数传值出错！", tabList);
			return gson.toJson(result);
		}
		tabList = new ArrayList<NewsTabEntity>();
		try {
			
		if(infromation.equals("0")){
			logger.error("获取新闻栏目:username"+username+",strKey"+strKey);
			}
		
		String url = getImageHost();
		NewsConfigQuery configQuery = new NewsConfigQuery();
		configQuery.setKey(SERVICE_FRONT);
		INewsConfigService newsConfigService = (INewsConfigService)SpringHolder.getBean("newsConfigService");
		NewsConfig config = newsConfigService.findByKey(configQuery);
		String serviceIds = null;
		if (config != null) {
			serviceIds = config.getValue();
		}
		//String serviceIds = MobileSystemHolder.getPropertiesValue("fwids");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("isAll", "true");
		INewsCatalogService newsCatalogService = (INewsCatalogService)SpringHolder.getBean("newsCatalogService");
		List<NewsCatalog> catalogs = newsCatalogService.getAllCatalog(queryMap);
		configQuery.setKey(TT_NAME);
		config = newsConfigService.findByKey(configQuery);
		String ttName = "头条";
		if (config != null) {
			ttName = config.getValue();
		}
		//String ttName = MobileSystemHolder.getPropertiesValue("tt_name");
		//if (ttName == null) {
		//	ttName = "头条";
		//}
		ILoginService loginService = (ILoginService)SpringHolder.getBean("loginService");
		IMobileCommonService mobileCommonService = (IMobileCommonService)SpringHolder.getBean("mobileCommonService");
		if (!StringUtil.isEmpty(username) && !StringUtil.isEmpty(serviceIds)) {
			// List<ServiceManager> services =
			// mobileCommonService.getFrequentlyServiceByUser(userId);
			LoginModel loginModel = new LoginModel();
			loginModel.setYhm(username);
			User user = loginService.cxYhxx(loginModel);
			if(user != null){
				String role = user.getYhlx();
				if (!"student".equals(role)) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("fwids", serviceIds.split(","));
					map.put("userId", username);
					List<ServiceManager> services = mobileCommonService
					.getLoginFw(map);
					NewsTabEntity entity = null;
					for (ServiceManager service : services) {
						entity = new NewsTabEntity();
						entity.setCatalogId(service.getClassFwbm());
						entity.setCatalogCode(service.getClassSsywxt());
						entity.setCatalogName(service.getClassFwmc());
						entity.setWebUrl(service.getWebUrl());
						boolean check = false;
						try {
							check = mobileCommonService.checkImage(
									service.getClassFwtbdz(),
									service.getClassFwtbid());
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						if (check){
							entity.setLogo(url + service.getClassFwtbdz());
						}
						else{
							entity.setLogo(url + "upload/default_image.jpg");
						}
						tabList.add(entity);
						if(infromation.equals("0")){
							logger.error("获取新entity"+entity);
						}
					}
				}
				
			}
		}
		NewsTabEntity entity = null;
		if(!StringUtil.isEmpty(ttName)){
			entity = new NewsTabEntity();
			entity.setCatalogCode("TT");
			entity.setCatalogName(ttName);
			entity.setWebUrl("web_index.html");
			entity.setType(ShowTypeEnum.TELETEXT_SHOW.getKey());
			entity.setListType("1");
			tabList.add(entity);
			if(infromation.equals("0")){
				logger.error("获取新entity"+entity);
				}
		}
		for (NewsCatalog catalog : catalogs) {
			entity = new NewsTabEntity();
			entity.setCatalogId(catalog.getCatalogId());
			entity.setCatalogCode(catalog.getCatalogCode());
			entity.setCatalogName(catalog.getCatalogName());
			entity.setWebUrl("web_index.html?catalogId="
					+ catalog.getCatalogCode() + "&amp;type="
					+ catalog.getShowType() + "&amp;listType="
					+ catalog.getListType());
			entity.setType(catalog.getShowType());
			entity.setListType(catalog.getListType());
			
			boolean check = false;
			try {
				check = mobileCommonService.checkImage(catalog.getLogoUrl(),
						catalog.getLogoId());
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (check){
				entity.setLogo(url + catalog.getLogoUrl());
			}else{
				entity.setLogo(url + "upload/default_image.jpg");
			}
			tabList.add(entity);
			if(infromation.equals("0")){
				logger.error("获取新entity"+entity);
				}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(infromation.equals("0")){
			logger.error("获取新闻栏目end");
			}
		ResultEntity result = new ResultEntity<List<NewsTabEntity>>(1, "成功", tabList);
		return gson.toJson(result);
	}
	
	/**
	 * 获取新闻栏目对应的列表
	 * @param id
	 * @param start
	 * @param size
	 * @return
	 */
	public String getNewsList(String catalogCode, String starts, String sizes){
		if(infromation.equals("0")){
			logger.error("获取新闻栏目对应的列表："+",catalogCode="+catalogCode+",start="+starts+",size="+sizes);
			}
		Gson gson = new Gson();
		List<NewsEntity> NewsList = null;
		if(StringUtil.isEmpty(starts) || StringUtil.isEmpty(sizes)){
			ResultEntity result = new ResultEntity<List<NewsEntity>>(0, "参数传值出错！", NewsList);
			return gson.toJson(result);
		}
		try {
			
		
		int size =Integer.valueOf(sizes);
		int start= Integer.valueOf(starts);
		NewsEntity entity = null;
		NewsQuery newsQuery = new NewsQuery();
		newsQuery.setStatus("1");
		if (StringUtils.isEmpty(catalogCode) || "TT".equals(catalogCode)) {
			newsQuery.setHeadline("1");
		} else {
			newsQuery.setCatalogCode(catalogCode);
		}
		if (size > 0) {
			newsQuery.setPerPageSize(size);
		}
		if (start >=0) {
			newsQuery.setToPage(start);
		}
		 INewsService newsService = (INewsService) SpringHolder.getBean("newsService");
		PageList<News> newsList = newsService.getPageList(newsQuery);
		if (newsList.size() <= 0) {
			ResultEntity result = new ResultEntity<List<NewsEntity>>(1, "成功", NewsList);
			return gson.toJson(result);
		}
		NewsList = new ArrayList<NewsEntity>();
		StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><zfsoft pagesize=\"");
        sb.append(newsList.getPaginator().getPages() + "\" start=\"" + start + "\" size=\"" + newsList.size() + "\">");
        String url = getImageHost();
        String goUrl = "";
        	for (News news : newsList) {
        		entity = new NewsEntity();
        		goUrl = url + "mobile/web_content.html?newsId=" + news.getId();
        		sb.append("<news>");
        		entity.setId(news.getId());
        		entity.setTimeCreate(DateTimeUtil.getFormatDate(news.getCreateTime()));
        		entity.setTitle(news.getTitle());
        		goUrl = StringUtil.isEmpty(news.getNewsURL()) ? "" :url + "mobile/web_content.html?newsId=" + news.getId();
        		entity.setWebUrl(goUrl);
        		IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
        		
        		String isremotelogo = news.getIsremotelogo();
        		if(!StringUtil.isEmpty(isremotelogo) && isremotelogo.equals("1")){
        			entity.getLogoPathList().add(news.getRemotelogourl());
        		}else{
        			if(!StringUtil.isEmpty(news.getPicId())){
        				String[] picIds = news.getPicId().split(",");
        				String[] picUrls = news.getPicUrl().split(",");
        				boolean check = false;
        				for (int i = 0; i < picIds.length; i++) {
        					
        					try {
        						check = mobileCommonService.checkImage(picUrls[i], picIds[i]);
        					} catch (IOException e) {
        						// TODO Auto-generated catch block
        						e.printStackTrace();
        					}
        					if(check){
        						entity.getLogoPathList().add(url + picUrls[i]);
        					}
        					else{
        						entity.getLogoPathList().add(url + "upload/default_image.jpg");
        					}
        					
        				}
        			}
        		}
        		entity.setIntroduce(news.getIntro());
        		entity.setCatalogCode(news.getCatalogCode());
        		entity.setAuthor(news.getAuthor()==null?"":news.getAuthor());
        		NewsList.add(entity);
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
        if(infromation.equals("0")){
			logger.error("获取新闻栏目对应的列表结束");
			}
        ResultEntity result = new ResultEntity<List<NewsEntity>>(1, "成功", NewsList);
		return gson.toJson(result);
	}
	
	
	
	public String Commonfunction(String username,String strKey,String apptoken){
		Gson gson = new Gson();
		List<ServiceEntity> serviceEntityList = null;
		if(!ApptokenUtils.compare(username, apptoken)){
			ResultEntity result = new ResultEntity<List<ServiceEntity>>(2, "app_token error!", serviceEntityList);
			return gson.toJson(result);
		}
		try {
			username  		= CodeUtil.decode(username, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			logger.error("Commonfunction Exception:");
			logger.error(e.getMessage(), e.fillInStackTrace());
		}
		if(StringUtil.isEmpty(username) || StringUtil.isEmpty(strKey)
				|| StringUtil.isEmpty(apptoken)){
			ResultEntity result = new ResultEntity<List<ServiceEntity>>(0, "参数传值出错！", serviceEntityList);
			return gson.toJson(result);
		}
		serviceEntityList = new ArrayList<ServiceEntity>();
		
		IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
        List<ServiceManager> services = mobileCommonService.getFrequentlyServiceByUser(username);
        ServiceEntity serviceEntity;
        String url = getImageHost();
        for (ServiceManager sm : services) {
        	serviceEntity = new ServiceEntity();
        	serviceEntity.id = StringUtil.isEmpty(sm.getClassId()) ? "" : sm.getClassId();
        	serviceEntity.type = StringUtil.isEmpty(sm.getClassFwlx()) ? "" : sm.getClassFwlx();
        	serviceEntity.name = StringUtil.isEmpty(sm.getClassFwmc()) ? "" : sm.getClassFwmc();
        	serviceEntity.icon = url + sm.getClassFwtbdz();
            if (!StringUtils.isEmpty(sm.getAppUrl())) {
            	serviceEntity.url = StringUtil.isEmpty(sm.getAppUrl()) ? "" : sm.getAppUrl();
            }
            serviceEntity.androidUrl = StringUtil.isEmpty(sm.getClassAndroidUrl()) ? "" : sm.getClassAndroidUrl();
            serviceEntity.iosUrl 	  = StringUtil.isEmpty(sm.getClassIosUrl()) ? "" : sm.getClassIosUrl();
            if (StringUtils.isEmpty(sm.getWebUrl())) {
            	serviceEntity.wechatUrl = StringUtil.isEmpty(sm.getWebUrl()) ? "" : sm.getWebUrl();
            }
            serviceEntity.serviceCode = StringUtil.isEmpty(sm.getClassFwbm()) ? "" : sm.getClassFwbm();
            String apkDownURL = !StringUtil.isEmpty(sm.getFileId()) ? 
								getImageHost()+"/file/attachement_download.html?model.guId="+sm.getFileId() : "";
			serviceEntity.apkdownUrl  = apkDownURL;
			serviceEntity.apkFileName = (StringUtils.isEmpty(sm.getApkfilename()) ? "" : sm.getApkfilename());
			serviceEntity.apkPackage  = StringUtil.isEmpty(sm.getClassAppyydz()) ? "" : sm.getClassAppyydz();
			serviceEntity.urlScheme   = StringUtil.isEmpty(sm.getIosURLScheme()) ? "" : sm.getIosURLScheme();
			serviceEntity.urliTunes   = StringUtil.isEmpty(sm.getIosURLiTunes()) ? "" : sm.getIosURLiTunes();
			serviceEntity.procode     = StringUtil.isEmpty(sm.getProcode()) ? "" : sm.getProcode();
			serviceEntity.otherFlag   = StringUtil.isEmpty(sm.getOtherFlag()) ? "" : sm.getOtherFlag();
			serviceEntity.moduletype  = StringUtil.isEmpty(sm.getClassSsywxt()) ? "" : sm.getClassSsywxt();
			serviceEntityList.add(serviceEntity);
        }
        ResultEntity result = new ResultEntity<List<ServiceEntity>>(1, "成功", serviceEntityList);
        return gson.toJson(result);
    }
	
	
	
	
	/**
	 * 获取首页不登录时服务
	 * @return
	 */
	
	public String getCommonService(){
		IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
		List<ServiceManager> services = mobileCommonService.getCommonService();
        List<ServiceEntity> serviceEntityList = new ArrayList<ServiceEntity>();
        ServiceEntity serviceEntity;
        String url = getImageHost();
        for (ServiceManager sm : services) {
        	serviceEntity = new ServiceEntity();
        	serviceEntity.id = StringUtil.isEmpty(sm.getClassId()) ? "" : sm.getClassId();
        	serviceEntity.type = StringUtil.isEmpty(sm.getClassFwlx()) ? "" : sm.getClassFwlx();
        	serviceEntity.name = StringUtil.isEmpty(sm.getClassFwmc()) ? "" : sm.getClassFwmc();
        	serviceEntity.icon = url + sm.getClassFwtbdz();
            if (!StringUtils.isEmpty(sm.getAppUrl())) {
            	serviceEntity.url = StringUtil.isEmpty(sm.getAppUrl()) ? "" : sm.getAppUrl();
            }
            serviceEntity.androidUrl = StringUtil.isEmpty(sm.getClassAndroidUrl()) ? "" : sm.getClassAndroidUrl();
            serviceEntity.iosUrl 	  = StringUtil.isEmpty(sm.getClassIosUrl()) ? "" : sm.getClassIosUrl();
            if (StringUtils.isEmpty(sm.getWebUrl())) {
            	serviceEntity.wechatUrl = StringUtil.isEmpty(sm.getWebUrl()) ? "" : sm.getWebUrl();
            }
            serviceEntity.serviceCode = StringUtil.isEmpty(sm.getClassFwbm()) ? "" : sm.getClassFwbm();
            String apkDownURL = !StringUtil.isEmpty(sm.getFileId()) ? 
								getImageHost()+"/file/attachement_download.html?model.guId="+sm.getFileId() : "";
			serviceEntity.apkdownUrl  = apkDownURL;
			serviceEntity.apkFileName = (StringUtils.isEmpty(sm.getApkfilename()) ? "" : sm.getApkfilename());
			serviceEntity.apkPackage  = StringUtil.isEmpty(sm.getClassAppyydz()) ? "" : sm.getClassAppyydz();
			serviceEntity.urlScheme   = StringUtil.isEmpty(sm.getIosURLScheme()) ? "" : sm.getIosURLScheme();
			serviceEntity.urliTunes   = StringUtil.isEmpty(sm.getIosURLiTunes()) ? "" : sm.getIosURLiTunes();
			serviceEntity.procode     = StringUtil.isEmpty(sm.getProcode()) ? "" : sm.getProcode();
			serviceEntity.otherFlag   = StringUtil.isEmpty(sm.getOtherFlag()) ? "" : sm.getOtherFlag();
			serviceEntity.moduletype  = StringUtil.isEmpty(sm.getClassSsywxt()) ? "" : sm.getClassSsywxt();
			serviceEntityList.add(serviceEntity);
        }
        ResultEntity result = new ResultEntity<List<ServiceEntity>>(1, "成功", serviceEntityList);
        Gson gson = new Gson();
        return gson.toJson(result);
	}
	
	/**
	 * 获取推荐位新闻
	 * @param size
	 * @return
	 */
	public String getMhRecommendPage(String sizes){
		ResultEntity result;
		IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
		if(infromation.equals("0")){
			logger.error("获取推荐位新闻:"+",sizes="+sizes);
		}
		Gson gson = new Gson();
		List<MhRecommend> entityList = new ArrayList<MhRecommend>();
		try {
			
		
		MhRecommend entity = null;
		NewsQuery newsQuery = new NewsQuery();
		newsQuery.setStatus("1");
		int size = Integer.parseInt(sizes);
		newsQuery.setPerPageSize(size);
		newsQuery.setToPage(1);
		newsQuery.setRecommend("1");
		INewsService newsService = (INewsService) SpringHolder.getBean("newsService");
		PageList<News> newsList = newsService.getPageList(newsQuery);
		if (newsList.size() <= 0) {
			result = new ResultEntity<List<MhRecommend>>(1, "成功", entityList);
			return gson.toJson(result);
		}
        String url = getImageHost();
        String goUrl = "";
        for (News news : newsList) {
        	goUrl = url + "mobile/web_content.html?newsId=" + news.getId();
        	entity = new MhRecommend();
        	entity.setId(news.getId());
        	entity.setTitle(news.getTitle());
        	String isremotelogo = news.getIsremotelogo();
        	if(!StringUtil.isEmpty(isremotelogo) && isremotelogo.equals("1")){
        		entity.getLogoPathList().add(news.getRemotelogourl());
            }else{
            	if(!StringUtil.isEmpty(news.getPicId()) && !StringUtil.isEmpty(news.getPicUrl())){
            		String[] picIds = news.getPicId().split(",");
            		String[] picUrls = news.getPicUrl().split(",");
            		boolean check = false;
            		for (int i = 0; i < picIds.length; i++) {
            			
            			try {
            				check = mobileCommonService.checkImage(picUrls[i], picIds[i]);
            			} catch (IOException e) {
            				// TODO Auto-generated catch block
            				e.printStackTrace();
            			}
            			if(check){
            				entity.getLogoPathList().add(url + picUrls[i]);
            			}
            			else{
            				entity.getLogoPathList().add(url + "upload/default_image.jpg");
            			}
            			
            		}
            	}
            }
        	if(Config.getString("newsUrl","no").equals("yes")){
        		if(!StringUtil.isEmpty(news.getNewsURL())){
        			entity.setUrl(news.getNewsURL());
        		}else{
        			entity.setUrl(goUrl);
        		}
        	}else{
        		entity.setUrl(goUrl);
        	}
        	entityList.add(entity);
        }
		} catch (Exception e) {
			logger.error(e, e.fillInStackTrace());
			result = new ResultEntity<List<MhRecommend>>(0, "后台产生异常！", entityList);
			return gson.toJson(result);
		}
        if(infromation.equals("0")){
    		logger.error("获取推荐位新闻end");
    		}
        result = new ResultEntity<List<MhRecommend>>(1, "成功", entityList);
		return gson.toJson(result);
	}
}
