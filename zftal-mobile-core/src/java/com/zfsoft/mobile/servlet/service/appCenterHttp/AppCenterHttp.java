package com.zfsoft.mobile.servlet.service.appCenterHttp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.common.Config;
import com.google.gson.Gson;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.services.entity.Business;
import com.zfsoft.mobile.services.entity.ServiceManager;
import com.zfsoft.mobile.servlet.entity.AppServiceInfo;
import com.zfsoft.mobile.servlet.entity.BusinessSystemEntity;
import com.zfsoft.mobile.servlet.entity.FailureEntity;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.mobile.servlet.entity.ServiceEntity;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;

public class AppCenterHttp {
	private static Logger logger = Logger.getLogger(AppCenterHttp.class);
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
	
	/**
	 * 常用服务帅选
	 * @param userName
	 * @param moduletype
	 * @param strKey
	 * @param apptoken
	 * @return
	 */
	public String CommonBrushFunction(String userName, String moduletype,
			String strKey,String apptoken) {
		Gson gson = new Gson();
		List<ServiceEntity> entityList = new ArrayList<ServiceEntity>();
		ServiceEntity entity = null;
		try {
		if(infromation.equals("0")){
			logger.error("应用中心模块类型筛选："+"userName="+userName+",moduletype="+moduletype+",strKey="+strKey);
			}
		if(!ApptokenUtils.compare(userName, apptoken)){
			FailureEntity failure = new FailureEntity();
			failure.failure = "app_token error";
			return gson.toJson(failure);
		}
			userName  		= CodeUtil.decode(userName, apptoken);
			moduletype  	= CodeUtil.decode(moduletype, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
			
		
		
		List<ServiceManager> services;
		IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
    	if ("init".equals(moduletype)) {
    		services = mobileCommonService.getFrequentlyServiceByUser(userName);
    	} else {
    		services = mobileCommonService.getAllServiceByUser(userName, moduletype);
    	}
    	String url = getImageHost();
        for (ServiceManager sm : services) {
        	entity = new ServiceEntity();
        	entity.id = sm.getClassId();
        	entity.type = sm.getClassFwlx();
        	entity.name = sm.getClassFwmc();
        	entity.icon = url + sm.getClassFwtbdz();
            if (!StringUtils.isEmpty(sm.getAppUrl())) {
            	entity.url = sm.getAppUrl();
            }
            entity.androidUrl = sm.getClassAndroidUrl();
            entity.iosUrl 	  = sm.getClassIosUrl();
            if (StringUtils.isEmpty(sm.getWebUrl())) {
            	entity.wechatUrl = sm.getWebUrl();
            }
            entity.serviceCode = sm.getClassFwbm();
            String apkDownURL = !StringUtil.isEmpty(sm.getFileId()) ? 
								getImageHost()+"/file/attachement_download.html?model.guId="+sm.getFileId() : "";
			entity.apkdownUrl  = apkDownURL;
			entity.apkFileName = (StringUtils.isEmpty(sm.getApkfilename()) ? "" : sm.getApkfilename());
			entity.apkPackage  = sm.getClassAppyydz();
			entity.urlScheme   = sm.getIosURLScheme();
			entity.urliTunes   = sm.getIosURLiTunes();
			entity.procode     = sm.getProcode();
			entity.otherFlag   = sm.getOtherFlag();
			entity.moduletype  = sm.getClassSsywxt();
			entityList.add(entity);
			if(infromation.equals("0")){
				logger.error("应用中心模块类型筛选entity："+entity);
				}
        }
		} catch (Exception e) {
			e.printStackTrace();
		}
        if(infromation.equals("0")){
			logger.error("应用中心模块类型筛选end");
			}
        return gson.toJson(entityList);
	}
	
	/**
	 * 应用中心下拉类型筛选
	 * @param userName
	 * @param apptoken
	 * @return
	 */
	public String getALLXtYwByUser(String userName,String apptoken) {
		Gson gson = new Gson();
		List<BusinessSystemEntity> entityList = new ArrayList<BusinessSystemEntity>();
		BusinessSystemEntity entity = null;
		try {
		if(infromation.equals("0")){
			logger.error("应用中心下拉类型筛选获取："+"userName="+userName+",apptoken="+apptoken);
		}
		if(!ApptokenUtils.compare(userName, apptoken)){
			ResultEntity result = new ResultEntity<List<BusinessSystemEntity>>(2, "app_token error!", entityList);
			return gson.toJson(result);
		}
			userName  			= CodeUtil.decode(userName, apptoken);
			
		
		IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
		List<Business> xtyws = mobileCommonService.getAllXtYwByUser(userName);
		List<ServiceManager> services;
        for (Business xtyw : xtyws) {
        	entity = new BusinessSystemEntity();
        	entity.id = StringUtil.isEmpty(xtyw.getClassId()) ? "" : xtyw.getClassId();
        	entity.systemCode = StringUtil.isEmpty(xtyw.getClassXtbm()) ? "" : xtyw.getClassXtbm();
        	entity.systemName = StringUtil.isEmpty(xtyw.getClassXtmc()) ? "" : xtyw.getClassXtmc();
        	entity.procode = StringUtil.isEmpty(xtyw.getProcode()) ? "" : xtyw.getProcode();
        	entity.otherFlag = StringUtil.isEmpty(xtyw.getOtherFlag()) ? "" : xtyw.getOtherFlag();
        	
        	services = mobileCommonService.getAllServiceByUser(userName, entity.systemCode);
        	String url = getImageHost();
        	List<ServiceEntity> serviceEntityList = new ArrayList<ServiceEntity>();
        	ServiceEntity serviceEntity = null;
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
            entity.serviceEntityList = serviceEntityList;
            entityList.add(entity);
            if(infromation.equals("0")){
            	logger.error("应用中心模块类型筛选entity："+entity);
            }
        }
        if(infromation.equals("0")){
    		logger.error("获取推荐位新闻end");
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResultEntity result = new ResultEntity<List<BusinessSystemEntity>>(1, "成功", entityList);
		return gson.toJson(result);
	}
	
	
	
	/**
     * 根据账号取得该账号的所有服务
	 * @throws IOException 
     */
    public String CommonAllFunction(String userId,String strKey,String apptoken) {
    	Gson gson = new Gson();
		List<ServiceEntity> entityList = new ArrayList<ServiceEntity>();
		try {
		if(infromation.equals("0")){
			logger.error("应用中心常用模块获取："+"userId="+userId+",strKey="+strKey);
			}
		String errorMessage = "";
		if(!ApptokenUtils.compare(userId, apptoken)){
			FailureEntity failure = new FailureEntity();
			failure.failure = "app_token error";
			return gson.toJson(failure);
		}
		userId  		= CodeUtil.decode(userId, apptoken);
		strKey  		= CodeUtil.decode(strKey, apptoken);
		ServiceEntity entity = null;
		IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
		List<ServiceManager> services = mobileCommonService.getAllServiceByUser(userId, "");
        String url = getImageHost();
        for (ServiceManager sm : services) {
        	entity = new ServiceEntity();
        	entity.id = sm.getClassId();
        	entity.type = sm.getClassFwlx();
        	entity.name = sm.getClassFwmc();
        	entity.icon = url + sm.getClassFwtbdz();
        	if (!StringUtils.isEmpty(sm.getAppUrl())) {
            	entity.url = sm.getAppUrl();
            }
            entity.androidUrl = sm.getClassAndroidUrl();
            entity.iosUrl = sm.getClassIosUrl();
            if (!StringUtils.isEmpty(sm.getWebUrl())) {
            	entity.wechatUrl = sm.getWebUrl();
            }
            entity.serviceCode = sm.getClassFwbm();
            String apkDownURL = !StringUtil.isEmpty(sm.getFileId()) ? 
								getImageHost()+"/file/attachement_download.html?model.guId="+sm.getFileId() : "";
			entity.apkdownUrl 	= apkDownURL;
			entity.apkFileName 	= (StringUtils.isEmpty(sm.getApkfilename()) ? "" : sm.getApkfilename());
			entity.apkPackage 	= sm.getClassAppyydz();
			entity.urlScheme 	= sm.getIosURLScheme();
			entity.urliTunes 	= sm.getIosURLiTunes();
			entity.procode 		= sm.getProcode();
			entity.otherFlag 	= sm.getOtherFlag();
			entity.moduletype	= sm.getClassSsywxt();
			entityList.add(entity);
			if(infromation.equals("0")){
				logger.error("应用中心常用模块获取entity："+entity);
			}
			entity.isCommon     = sm.getIscommon();
        	
        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return gson.toJson(entityList);
    }
    
    
    /**
     * 根据账号取得该账号没有添加的所有服务
     * @throws IOException 
     */
    public String CommonOtherFunction(String userId,String strKey,String apptoken){
    	Gson gson = new Gson();
		List<ServiceEntity> entityList = new ArrayList<ServiceEntity>();
		try {
		if(infromation.equals("0")){
			logger.error("应用中心常用模块获取："+"userId="+userId+",strKey="+strKey);
			}
		String errorMessage = "";
		if(!ApptokenUtils.compare(userId, apptoken)){
			FailureEntity failure = new FailureEntity();
			failure.failure = "app_token error";
			return gson.toJson(failure);
		}
		userId  		= CodeUtil.decode(userId, apptoken);
		strKey  		= CodeUtil.decode(strKey, apptoken);
		ServiceEntity entity = null;
		IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
		Map<String, Object> params = new HashMap<String, Object>();
    	params.put("userId", userId);
		List<ServiceManager> services = mobileCommonService.getOtherServiceByUser(params);
        String url = getImageHost();
        for (ServiceManager sm : services) {
        	entity = new ServiceEntity();
        	entity.id = sm.getClassId();
        	entity.type = sm.getClassFwlx();
        	entity.name = sm.getClassFwmc();
        	entity.icon = url + sm.getClassFwtbdz();
        	if (!StringUtils.isEmpty(sm.getAppUrl())) {
            	entity.url = sm.getAppUrl();
            }
            entity.androidUrl = sm.getClassAndroidUrl();
            entity.iosUrl = sm.getClassIosUrl();
            if (!StringUtils.isEmpty(sm.getWebUrl())) {
            	entity.wechatUrl = sm.getWebUrl();
            }
            entity.serviceCode = sm.getClassFwbm();
            String apkDownURL = !StringUtil.isEmpty(sm.getFileId()) ? 
								getImageHost()+"/file/attachement_download.html?model.guId="+sm.getFileId() : "";
			entity.apkdownUrl 	= apkDownURL;
			entity.apkFileName 	= (StringUtils.isEmpty(sm.getApkfilename()) ? "" : sm.getApkfilename());
			entity.apkPackage 	= sm.getClassAppyydz();
			entity.urlScheme 	= sm.getIosURLScheme();
			entity.urliTunes 	= sm.getIosURLiTunes();
			entity.procode 		= sm.getProcode();
			entity.otherFlag 	= sm.getOtherFlag();
			entity.moduletype	= sm.getClassSsywxt();
			entityList.add(entity);
			if(infromation.equals("0")){
				logger.error("应用中心常用模块获取entity："+entity);
			}
			entity.isCommon     = sm.getIscommon();
        	
        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return gson.toJson(entityList);
    }
	
	
	/**
	 * 获取首页个人常用服务
	 * @param userName
	 * @param strKey
	 * @param apptoken
	 * @return
	 */
	public String Commonfunction(String userName, String strKey,String apptoken) {
		Gson gson = new Gson();
		List<ServiceEntity> entityList = new ArrayList<ServiceEntity>();
		try {
		if(infromation.equals("0")){
			logger.error("应用中心常用模块获取："+"userName="+userName+",strKey="+strKey);
			}
		if(!ApptokenUtils.compare(userName, apptoken)){
			FailureEntity failure = new FailureEntity();
			failure.failure = "app_token error";
			return gson.toJson(failure);
		}
			userName  		= CodeUtil.decode(userName, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
			
		
		ServiceEntity entity = null;
		IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
		List<ServiceManager> services = mobileCommonService.getFrequentlyServiceByUser(userName);
        String url = getImageHost();
        for (ServiceManager sm : services) {
        	entity = new ServiceEntity();
        	entity.id = sm.getClassId();
        	entity.type = sm.getClassFwlx();
        	entity.name = sm.getClassFwmc();
        	entity.icon = url + sm.getClassFwtbdz();
            if (!StringUtils.isEmpty(sm.getAppUrl())) {
            	entity.url = sm.getAppUrl();
            }
            entity.androidUrl = sm.getClassAndroidUrl();
            entity.iosUrl = sm.getClassIosUrl();
            if (!StringUtils.isEmpty(sm.getWebUrl())) {
            	entity.wechatUrl = sm.getWebUrl();
            }
            entity.serviceCode = sm.getClassFwbm();
            String apkDownURL = !StringUtil.isEmpty(sm.getFileId()) ? 
								getImageHost()+"/file/attachement_download.html?model.guId="+sm.getFileId() : "";
			entity.apkdownUrl 	= apkDownURL;
			entity.apkFileName 	= (StringUtils.isEmpty(sm.getApkfilename()) ? "" : sm.getApkfilename());
			entity.apkPackage 	= sm.getClassAppyydz();
			entity.urlScheme 	= sm.getIosURLScheme();
			entity.urliTunes 	= sm.getIosURLiTunes();
			entity.procode 		= sm.getProcode();
			entity.otherFlag 	= sm.getOtherFlag();
			entity.moduletype	= sm.getClassSsywxt();
			entityList.add(entity);
			if(infromation.equals("0")){
				logger.error("应用中心常用模块获取entity："+entity);
			}
        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        if(infromation.equals("0")){
    		logger.error("应用中心常用模块获取end");
    		}
        return gson.toJson(entityList);
	}
	
	/**
     * 添加常用服务
     */
    public String SubmitCommonFunction(String data,String apptoken) {
    	Gson gson = new Gson();
    	if(StringUtil.isEmpty(data) || StringUtil.isEmpty(apptoken)){
			ResultEntity result = new ResultEntity<String>(0, "参数传值出错！", null);
			return gson.toJson(result);
		}
    	if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			data  			= CodeUtil.decode(data, apptoken);
		} catch (Exception e) {
			logger.error(e.getMessage(), e.fillInStackTrace());
			ResultEntity result = new ResultEntity<String>(2, "app_token error!", null);
			return gson.toJson(result);
		}
    	
    	AppServiceInfo appServiceInfo = gson.fromJson(data, AppServiceInfo.class);
    	try {
	    	if(infromation.equals("0")){
				logger.error("应用中心常用模块获取："+appServiceInfo.toString());
				}
	    	String userId = appServiceInfo.getUserId();
	    	List<String> serviceList = appServiceInfo.getServiceList();
	    	IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
	    	if (serviceList != null && serviceList.size() > 0) {
	    		mobileCommonService.deleteAllFrequentlyService(userId);
	    		for (int i = 0; i < serviceList.size(); i++) {
	    			mobileCommonService.insertFrequentlyService(userId, serviceList.get(i).trim(), true);
	    		}
	    	}
    	} catch (Exception e) {
    		ResultEntity result = new ResultEntity<String>(0, "接口产生异常!", null);
			return gson.toJson(result);
		}
    	ResultEntity result = new ResultEntity<String>(1, "成功", null);
		return gson.toJson(result);
    }
	
}
