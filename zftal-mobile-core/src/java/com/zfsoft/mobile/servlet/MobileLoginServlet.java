package com.zfsoft.mobile.servlet;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONException;
import org.json.JSONObject;

import com.common.Config;
import com.common.WebServiceConf;
import com.google.gson.Gson;
import com.jw.org.tempuri.ILogin;
import com.mh.CXFServe.service.ICaService;
import com.newmobile.login.CXFService.IWSSerServicePortType;
import com.newmobile.login.service.imp.MobileLoginXMLService;
import com.util.MiddleWareUtil;
import com.util.WebServiceUtil;
import com.zfsoft.common.log.User;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.dao.entities.LoginModel;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.hrm.file.util.UploadFileUtil;
import com.zfsoft.mobile.MD5Util.MD5Util;
import com.zfsoft.mobile.common.enums.ShowTypeEnum;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.common.utils.Authentication;
import com.zfsoft.mobile.common.utils.JSONUtils;
import com.zfsoft.mobile.common.utils.MobileSystemHolder;
import com.zfsoft.mobile.configuration.entity.NewsConfig;
import com.zfsoft.mobile.configuration.query.NewsConfigQuery;
import com.zfsoft.mobile.configuration.service.INewsConfigService;
import com.zfsoft.mobile.myportal.entity.MyPortal;
import com.zfsoft.mobile.myportal.service.IMyPortalService;
import com.zfsoft.mobile.news.entity.News;
import com.zfsoft.mobile.news.entity.NewsCatalog;
import com.zfsoft.mobile.news.query.NewsQuery;
import com.zfsoft.mobile.news.service.INewsCatalogService;
import com.zfsoft.mobile.news.service.INewsService;
import com.zfsoft.mobile.services.entity.Business;
import com.zfsoft.mobile.services.entity.GaoDeMaoEntity;
import com.zfsoft.mobile.services.entity.Point;
import com.zfsoft.mobile.services.entity.ServiceManager;
import com.zfsoft.mobile.services.service.IGaoDeMapService;
import com.zfsoft.mobile.servlet.entity.BusinessSystemEntity;
import com.zfsoft.mobile.servlet.entity.FailureEntity;
import com.zfsoft.mobile.servlet.entity.LoginEntity;
import com.zfsoft.mobile.servlet.entity.MhRecommend;
import com.zfsoft.mobile.servlet.entity.MyPortalEntity;
import com.zfsoft.mobile.servlet.entity.NewsEntity;
import com.zfsoft.mobile.servlet.entity.NewsTabEntity;
import com.zfsoft.mobile.servlet.entity.ServiceEntity;
import com.zfsoft.mobile.servlet.service.appCenterHttp.AppCenterHttp;
import com.zfsoft.mobile.servlet.service.commonHttp.CommonHttp;
import com.zfsoft.mobile.servlet.service.homePageHttp.HomePageHttp;
import com.zfsoft.mobile.servlet.service.myPortalHttp.MyPortalHttp;
import com.zfsoft.mobile.servlet.service.oaHttp.OAWebserviceHttp;
import com.zfsoft.mobile.webservices.entity.ScalendarEntity;
import com.zfsoft.service.svcinterface.ILoginService;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.date.DateTimeUtil;

public class MobileLoginServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(MobileLoginServlet.class);
	private final String infromation=Config.getString("mobile.infromation");
	/**
	 * 
	 */
	private static final long serialVersionUID = -1378460130591775899L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		String str = "";
		try {
		 String uri = request.getRequestURI();
		 boolean flag = uri.contains("?") ? true : false;
		   String path = "";
		   if(flag){
			   path = uri.substring(uri.lastIndexOf("/"),uri.lastIndexOf("?"));
		   }else{
			   path = uri.substring(uri.lastIndexOf("/"),uri.length());
		   }
		   PrintWriter out = response.getWriter();
		   boolean flagParam = false;
	     if(path.equals("/login")){//登录webservice
	    	 String username = null;
	         String password = null;
	         String strKey   = null;
	         BufferedReader sb  = request.getReader();
        	 username = StringUtil.isEmpty(request.getParameter("username")) ? 
        			 "" : java.net.URLDecoder.decode(request.getParameter("username"), "UTF-8");
	         password = StringUtil.isEmpty(request.getParameter("password")) ? 
	        		 "" : java.net.URLDecoder.decode(request.getParameter("password"), "UTF-8");
	         strKey   = StringUtil.isEmpty(request.getParameter("strKey")) ? 
	        		 "" : java.net.URLDecoder.decode(request.getParameter("strKey"), "UTF-8");
        	 //str = Login(username, password, strKey);
        	 CommonHttp commonHttp = new CommonHttp();
        	 Class clazz = commonHttp.getClass(); 
        	 Method login = clazz.getDeclaredMethod("login",new Class[]{String.class,String.class,String.class});
        	 str = (String) login.invoke(clazz.newInstance(),username, password, strKey);
	     }else if(path.equals("/getMhRecommendPage")){//获取推荐的新闻
	    	 String size = null;
	    	 BufferedReader sb  = request.getReader();
	         String Jsonstr = getBodyString(sb);
	         if(StringUtil.isEmpty(Jsonstr)){
	        	 size = StringUtil.isEmpty(request.getParameter("size")) ? "" : request.getParameter("size");
		         if(!StringUtil.isEmpty(size))
		        	 flagParam = true;
	         }
	         try {
	        	 if((Jsonstr != null && Jsonstr != "") || flagParam){
	        		 if(!flagParam){
	        			 JSONObject json = new JSONObject(Jsonstr);
	        			 size = json.getString("size");
	        			 
	        		 }
	        		 HomePageHttp homePageHttp = new HomePageHttp();
		        	 Class clazz = homePageHttp.getClass(); 
		        	 Method MhRecommend = clazz.getDeclaredMethod("getMhRecommendPage",new Class[]{String.class});
		        	 str = (String) MhRecommend.invoke(clazz.newInstance(),size);
	        		 //str = getMhRecommendPage(size);
		         }
	         } catch (JSONException e) {
	        	 e.printStackTrace();
	         }
	 		
	     }else if(path.equals("/getNewsList")){//根据资讯类别获取资讯
	    	 String catalogCode = null;
	    	 String start = null;
	    	 String size = null;
	    	 catalogCode = StringUtil.isEmpty(request.getParameter("catalogCode")) ? "" : request.getParameter("catalogCode");
        	 start = StringUtil.isEmpty(request.getParameter("start")) ? "" : request.getParameter("start");
        	 size   = StringUtil.isEmpty(request.getParameter("size")) ? "" : request.getParameter("size");
    		 HomePageHttp homePageHttp = new HomePageHttp();
        	 Class clazz = homePageHttp.getClass(); 
        	 Method MhRecommend = clazz.getDeclaredMethod("getNewsList",new Class[]{String.class,String.class,String.class});
        	 str = (String) MhRecommend.invoke(clazz.newInstance(),catalogCode, start, size);
	     }else if(path.equals("/getNewsTab")){//根据资讯类别获取资讯
	    	 String username = null;
	    	 String strKey = null;
        	 username = StringUtil.isEmpty(request.getParameter("username")) ? "" : request.getParameter("username");
        	 strKey = StringUtil.isEmpty(request.getParameter("strKey")) ? "" : request.getParameter("strKey");
        	 flagParam = true;
        	 HomePageHttp homePageHttp = new HomePageHttp();
        	 Class clazz = homePageHttp.getClass(); 
        	 Method NewsTab = clazz.getDeclaredMethod("getNewsTab",new Class[]{String.class,String.class});
        	 str = (String) NewsTab.invoke(clazz.newInstance(),username, strKey);
	     }else if(path.equals("/CommonAllFunction")){//根据资讯类别获取资讯
	    	 String userName = null;
	    	 String strKey   = null;
	    	 String apptoken = null;
	    	 BufferedReader sb  = request.getReader();
	         String Jsonstr = getBodyString(sb);
	         if(StringUtil.isEmpty(Jsonstr)){
	        	 userName = StringUtil.isEmpty(request.getParameter("userName")) ? "" : request.getParameter("userName");
	        	 strKey = StringUtil.isEmpty(request.getParameter("strKey")) ? "" : request.getParameter("strKey");
	        	 apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
		         if(!StringUtil.isEmpty(userName) && !StringUtil.isEmpty(strKey) 
		        		 && !StringUtil.isEmpty(apptoken))
		        	 flagParam = true;
	         }
	         try {
	        	 if((Jsonstr != null && Jsonstr != "") || flagParam){
	        		 JSONObject json = new JSONObject(Jsonstr);
	        		 userName 	= json.getString("userName");
	        		 strKey 	= json.getString("strKey");
	        		 apptoken 	= json.getString("apptoken");
	        		 AppCenterHttp appCenterHttp = new AppCenterHttp();
		        	 Class clazz = appCenterHttp.getClass(); 
		        	 Method CommonAllFunction = clazz.getDeclaredMethod("CommonAllFunction",new Class[]{String.class,String.class,String.class});
		        	 str = (String) CommonAllFunction.invoke(clazz.newInstance(),userName, strKey, apptoken);
	        		 //str = Commonfunction(userName, strKey, apptoken);
		         }
	         } catch (JSONException e) {
	        	 e.printStackTrace();
	         }
	     }else if(path.equals("/CommonOtherFunction")){//根据资讯类别获取资讯
	    	 String userName = null;
	    	 String strKey   = null;
	    	 String apptoken = null;
	    	 BufferedReader sb  = request.getReader();
	         String Jsonstr = getBodyString(sb);
	         if(StringUtil.isEmpty(Jsonstr)){
	        	 userName = StringUtil.isEmpty(request.getParameter("userName")) ? "" : request.getParameter("userName");
	        	 strKey = StringUtil.isEmpty(request.getParameter("strKey")) ? "" : request.getParameter("strKey");
	        	 apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
		         if(!StringUtil.isEmpty(userName) && !StringUtil.isEmpty(strKey) 
		        		 && !StringUtil.isEmpty(apptoken))
		        	 flagParam = true;
	         }
	         try {
	        	 if((Jsonstr != null && Jsonstr != "") || flagParam){
	        		 JSONObject json = new JSONObject(Jsonstr);
	        		 userName 	= json.getString("userName");
	        		 strKey 	= json.getString("strKey");
	        		 apptoken 	= json.getString("apptoken");
	        		 AppCenterHttp appCenterHttp = new AppCenterHttp();
		        	 Class clazz = appCenterHttp.getClass(); 
		        	 Method CommonOtherFunction = clazz.getDeclaredMethod("CommonOtherFunction",new Class[]{String.class,String.class,String.class});
		        	 str = (String) CommonOtherFunction.invoke(clazz.newInstance(),userName, strKey, apptoken);
	        		 //str = Commonfunction(userName, strKey, apptoken);
		         }
	         } catch (JSONException e) {
	        	 e.printStackTrace();
	         }
	     }else if(path.equals("/SubmitCommonFunction")){//根据资讯类别获取资讯
	    	 	 String data = null;
	    	 	 String apptoken = null;
	    	 	 data = StringUtil.isEmpty(request.getParameter("data")) ? 
	    	 			 "" : java.net.URLDecoder.decode(request.getParameter("data"), "UTF-8");
	        	 apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
        		 AppCenterHttp appCenterHttp = new AppCenterHttp();
	        	 Class clazz = appCenterHttp.getClass(); 
	        	 Method SubmitCommonFunction = clazz.getDeclaredMethod("SubmitCommonFunction",new Class[]{String.class,String.class});
	        	 str = (String) SubmitCommonFunction.invoke(clazz.newInstance(),data,apptoken);
	        		 //str = Commonfunction(userName, strKey, apptoken);
	     }else if(path.equals("/getALLXtYwByUser")){//应用中心下拉类型筛选
	    	 String username = null;
	    	 String apptoken = null;
	    	 username = StringUtil.isEmpty(request.getParameter("username")) ? 
	    			 "" : java.net.URLDecoder.decode(request.getParameter("username"), "UTF-8");
        	 apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
    		 AppCenterHttp appCenterHttp = new AppCenterHttp();
    		 Class clazz = appCenterHttp.getClass(); 
        	 Method ALLXtYw = clazz.getDeclaredMethod("getALLXtYwByUser",new Class[]{String.class,String.class});
        	 str = (String) ALLXtYw.invoke(clazz.newInstance(),username, apptoken);
	     }else if(path.equals("/CommonBrushFunction")){//应用中心下拉类型筛选
	    	 String userName = null;
	    	 String moduletype = null;
	    	 String strKey = null;
	    	 String apptoken = null;
	    	 BufferedReader sb  = request.getReader();
	         String Jsonstr = getBodyString(sb);
	         if(StringUtil.isEmpty(Jsonstr)){
	        	 userName = StringUtil.isEmpty(request.getParameter("userName")) ? "" : request.getParameter("userName");
	        	 apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
	        	 strKey = StringUtil.isEmpty(request.getParameter("strKey")) ? "" : request.getParameter("strKey");
	        	 moduletype = StringUtil.isEmpty(request.getParameter("moduletype")) ? "" : request.getParameter("moduletype");
		         if(!StringUtil.isEmpty(userName) && !StringUtil.isEmpty(apptoken)
		        		 && !StringUtil.isEmpty(strKey) && !StringUtil.isEmpty(moduletype))
		        	 flagParam = true;
	         }
	         try {
	        	 if((Jsonstr != null && Jsonstr != "") || flagParam){
	        		 JSONObject json = new JSONObject(Jsonstr);
	        		 userName 		= json.getString("userName");
	        		 moduletype 	= json.getString("moduletype");
	        		 strKey 		= json.getString("strKey");
	        		 apptoken 		= json.getString("apptoken");
	        		 AppCenterHttp appCenterHttp = new AppCenterHttp();
	        		 Class clazz = appCenterHttp.getClass(); 
		        	 Method CommonBrush = clazz.getDeclaredMethod("CommonBrushFunction",
		        			 new Class[]{String.class,String.class,String.class,String.class});
		        	 str = (String) CommonBrush.invoke(clazz.newInstance(),userName,moduletype,
	        					strKey,apptoken);
	        		 
	        		 /*str = CommonBrushFunction(userName,moduletype,
	        					strKey,apptoken);*/
		         }
	         } catch (JSONException e) {
	        	 e.printStackTrace();
	         }
	     }else if(path.equals("/myPortalFunction")){//应用中心下拉类型筛选
	    	 String username = null;
	    	 String strKey = null;
	    	 String apptoken = null;
	    	 username = StringUtil.isEmpty(request.getParameter("username")) ? 
	    			 "" : java.net.URLDecoder.decode(request.getParameter("username"), "UTF-8");
        	 apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
        	 strKey = StringUtil.isEmpty(request.getParameter("strKey")) ? 
        			 "" : java.net.URLDecoder.decode(request.getParameter("strKey"), "UTF-8");
    		 MyPortalHttp myPortalHttp = new MyPortalHttp();
    		 Class clazz = myPortalHttp.getClass(); 
        	 Method myPortal = clazz.getDeclaredMethod("myPortalFunction",
        			 new Class[]{String.class,String.class,String.class});
        	 str = (String) myPortal.invoke(clazz.newInstance(),username,strKey,apptoken);
	     }else if(path.equals("/getMyPicturePath")){//获取我的头像路径
	    	 String userName = null;
	    	 String apptoken = null;
	    	 BufferedReader sb  = request.getReader();
	         String Jsonstr = getBodyString(sb);
	         if(StringUtil.isEmpty(Jsonstr)){
	        	 userName = StringUtil.isEmpty(request.getParameter("userName")) ? "" : request.getParameter("userName");
	        	 apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
		         if(!StringUtil.isEmpty(userName) && !StringUtil.isEmpty(apptoken))
		        	 flagParam = true;
	         }
	         try {
	        	 if((Jsonstr != null && Jsonstr != "") || flagParam){
	        		 JSONObject json = new JSONObject(Jsonstr);
	        		 userName 		= json.getString("userName");
	        		 apptoken 		= json.getString("apptoken");
	        		 CommonHttp commonHttp = new CommonHttp();
	        		 Class clazz = commonHttp.getClass(); 
		        	 Method MyPicturePath = clazz.getDeclaredMethod("getMyPicturePath",
		        			 new Class[]{String.class,String.class});
		        	 str = (String) MyPicturePath.invoke(clazz.newInstance(),userName,apptoken);
	        		 //str = getMyPicturePath(userName,apptoken);
		         }
	         } catch (JSONException e) {
	        	 e.printStackTrace();
	         }
	     }else if(path.equals("/submitSuggestion")){//意见反馈
	    	 String userName = null;
	    	 String suggestion = null;
	    	 String strKey = null;
	    	 BufferedReader sb  = request.getReader();
	         String Jsonstr = getBodyString(sb);
	         if(StringUtil.isEmpty(Jsonstr)){
	        	 userName = StringUtil.isEmpty(request.getParameter("userName")) ? "" : request.getParameter("userName");
	        	 strKey = StringUtil.isEmpty(request.getParameter("strKey")) ? "" : request.getParameter("strKey");
	        	 suggestion = StringUtil.isEmpty(request.getParameter("suggestion")) ? "" : request.getParameter("suggestion");
		         if(!StringUtil.isEmpty(userName) && !StringUtil.isEmpty(strKey)
		        		 && !StringUtil.isEmpty(suggestion))
		        	 flagParam = true;
	         }
	         try {
	        	 if((Jsonstr != null && Jsonstr != "") || flagParam){
	        		 JSONObject json = new JSONObject(Jsonstr);
	        		 userName 		= json.getString("userName");
	        		 suggestion 	= json.getString("suggestion");
	        		 strKey 		= json.getString("strKey");
	        		 CommonHttp commonHttp = new CommonHttp();
	        		 Class clazz = commonHttp.getClass(); 
		        	 Method submitSuggestion = clazz.getDeclaredMethod("submitSuggestion",
		        			 new Class[]{String.class,String.class});
		        	 str = (String) submitSuggestion.invoke(clazz.newInstance(),userName,suggestion,strKey);
	        		 //str = submitSuggestion(userName,suggestion,strKey);
		         }
	         } catch (JSONException e) {
	        	 e.printStackTrace();
	         }
	     }else if(path.equals("/getMapList")){//获取校园地图数据
	    	 CommonHttp commonHttp = new CommonHttp();
    		 Class clazz = commonHttp.getClass(); 
        	 Method getMapList = clazz.getDeclaredMethod("getMapList");
        	 str = (String) getMapList.invoke(clazz.newInstance());
        	 //str = getMapList();
	     }else if(path.equals("/fileUpload")){//应用中心下拉类型筛选
        		 
	     }else if(path.equals("/getTermWeek")){//应用中心下拉类型筛选
	    	 String ymd = null;
	    	 String apptoken = null;
	    	 BufferedReader sb  = request.getReader();
	         String Jsonstr = getBodyString(sb);
	         if(StringUtil.isEmpty(Jsonstr)){
	        	 ymd = StringUtil.isEmpty(request.getParameter("ymd")) ? "" : request.getParameter("ymd");
	        	 apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
		         if(!StringUtil.isEmpty(ymd) && !StringUtil.isEmpty(apptoken))
		        	 flagParam = true;
	         }
	         try {
	        	 if((Jsonstr != null && Jsonstr != "") || flagParam){
	        		 JSONObject json = new JSONObject(Jsonstr);
	        		 ymd 			= json.getString("ymd");
	        		 apptoken 		= json.getString("apptoken");
	        		 CommonHttp commonHttp = new CommonHttp();
	        		 Class clazz = commonHttp.getClass(); 
		        	 Method getTermWeek = clazz.getDeclaredMethod("getTermWeek",
		        			 new Class[]{String.class,String.class});
		        	 str = (String) getTermWeek.invoke(clazz.newInstance(),ymd,apptoken);
		        	 
	        		 //str = getTermWeek(ymd,apptoken);
		         }
	         } catch (JSONException e) {
	        	 e.printStackTrace();
	         }
	     }else if(path.equals("/votelike")){
	    	 String userName = null;
	    	 String jmid = null;
	    	 String tag = null;
			 String strKey = null;
			 String apptoken = null;
			 BufferedReader sb  = request.getReader();
	         String Jsonstr = getBodyString(sb);
	         if(StringUtil.isEmpty(Jsonstr)){
	        	 userName = StringUtil.isEmpty(request.getParameter("userName")) ? "" : request.getParameter("userName");
	        	 jmid = StringUtil.isEmpty(request.getParameter("jmid")) ? "" : request.getParameter("jmid");
	        	 tag = StringUtil.isEmpty(request.getParameter("tag")) ? "" : request.getParameter("tag");
	        	 strKey = StringUtil.isEmpty(request.getParameter("strKey")) ? "" : request.getParameter("strKey");
	        	 apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
		         if(!StringUtil.isEmpty(userName) && !StringUtil.isEmpty(jmid) && !StringUtil.isEmpty(tag) 
		        		 && !StringUtil.isEmpty(strKey) && !StringUtil.isEmpty(apptoken))
		        	 flagParam = true;
	         }
	         try {
	        	 if((Jsonstr != null && Jsonstr != "") || flagParam){
	        		 JSONObject json = new JSONObject(Jsonstr);
	        		 userName 			= json.getString("userName");
	        		 jmid 				= json.getString("jmid");
	        		 tag 				= json.getString("tag");
	        		 strKey 			= json.getString("strKey");
	        		 apptoken 			= json.getString("apptoken");
	        		 
	        		 MyPortalHttp myPortalHttp = new MyPortalHttp();
	        		 Class clazz = myPortalHttp.getClass(); 
		        	 Method votelike = clazz.getDeclaredMethod("votelike",
		        			 new Class[]{String.class,String.class,String.class,String.class,String.class});
		        	 str = (String) votelike.invoke(clazz.newInstance(),userName,jmid,tag,strKey,apptoken);
		         }
	         } catch (JSONException e) {
	        	 e.printStackTrace();
	         }
			 
	     }else if(path.equals("/voteRanklist")){
	    	 String userName = null;
	    	 String tphdid = null;
			 String strKey = null;
			 String apptoken = null;
			 BufferedReader sb  = request.getReader();
	         String Jsonstr = getBodyString(sb);
	         if(StringUtil.isEmpty(Jsonstr)){
	        	 userName = StringUtil.isEmpty(request.getParameter("userName")) ? "" : request.getParameter("userName");
	        	 tphdid = StringUtil.isEmpty(request.getParameter("tphdid")) ? "" : request.getParameter("tphdid");
	        	 strKey = StringUtil.isEmpty(request.getParameter("strKey")) ? "" : request.getParameter("strKey");
	        	 apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
		         if(!StringUtil.isEmpty(userName) && !StringUtil.isEmpty(tphdid)  
		        		 && !StringUtil.isEmpty(strKey) && !StringUtil.isEmpty(apptoken))
		        	 flagParam = true;
	         }
	         try {
	        	 if((Jsonstr != null && Jsonstr != "") || flagParam){
	        		 JSONObject json = new JSONObject(Jsonstr);
	        		 userName 			= json.getString("userName");
	        		 tphdid 			= json.getString("tphdid");
	        		 strKey 			= json.getString("strKey");
	        		 apptoken 			= json.getString("apptoken");
	        		 
	        		 MyPortalHttp myPortalHttp = new MyPortalHttp();
	        		 Class clazz = myPortalHttp.getClass(); 
		        	 Method voteRanklist = clazz.getDeclaredMethod("voteRanklist",
		        			 new Class[]{String.class,String.class,String.class,String.class});
		        	 str = (String) voteRanklist.invoke(clazz.newInstance(),userName,tphdid,strKey,apptoken);
		         }
	         } catch (JSONException e) {
	        	 e.printStackTrace();
	         }
			 
	     }else if(path.equals("/votelist")){
	    	 String userName = null;
	    	 String tphdid = null;
			 String strKey = null;
			 String apptoken = null;
			 BufferedReader sb  = request.getReader();
	         String Jsonstr = getBodyString(sb);
	         if(StringUtil.isEmpty(Jsonstr)){
	        	 userName = StringUtil.isEmpty(request.getParameter("userName")) ? "" : request.getParameter("userName");
	        	 tphdid = StringUtil.isEmpty(request.getParameter("tphdid")) ? "" : request.getParameter("tphdid");
	        	 strKey = StringUtil.isEmpty(request.getParameter("strKey")) ? "" : request.getParameter("strKey");
	        	 apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
		         if(!StringUtil.isEmpty(userName) && !StringUtil.isEmpty(tphdid)  
		        		 && !StringUtil.isEmpty(strKey) && !StringUtil.isEmpty(apptoken))
		        	 flagParam = true;
	         }
	         try {
	        	 if((Jsonstr != null && Jsonstr != "") || flagParam){
	        		 JSONObject json = new JSONObject(Jsonstr);
	        		 userName 			= json.getString("userName");
	        		 tphdid 			= json.getString("tphdid");
	        		 strKey 			= json.getString("strKey");
	        		 apptoken 			= json.getString("apptoken");
	        		 
	        		 MyPortalHttp myPortalHttp = new MyPortalHttp();
	        		 Class clazz = myPortalHttp.getClass(); 
		        	 Method votelist = clazz.getDeclaredMethod("votelist",
		        			 new Class[]{String.class,String.class,String.class,String.class});
		        	 str = (String) votelist.invoke(clazz.newInstance(),userName,tphdid,strKey,apptoken);
		         }
	         } catch (JSONException e) {
	        	 e.printStackTrace();
	         }
			 
	     }else if(path.equals("/installsCount")){
			 String apptoken = null;
    		 CommonHttp commonHttp = new CommonHttp();
    		 Class clazz = commonHttp.getClass(); 
        	 Method votelist = clazz.getDeclaredMethod("installsCount",
        			 new Class[]{});
        	 str = (String) votelist.invoke(clazz.newInstance());
	     }else if(path.equals("/personDocumentInformationList")){
	    	 String username = null;
	    	 String strKey = null;
	    	 String apptoken = null;
	    	 username = StringUtil.isEmpty(request.getParameter("username")) ? 
	    			 "" : java.net.URLDecoder.decode(request.getParameter("username"), "UTF-8");
        	 apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? 
        			 "" : request.getParameter("apptoken");
        	 strKey = StringUtil.isEmpty(request.getParameter("strKey")) ? 
        			 "" : java.net.URLDecoder.decode(request.getParameter("strKey"), "UTF-8");
        	 CommonHttp commonHttp = new CommonHttp();
    		 Class clazz = commonHttp.getClass(); 
        	 Method personDocumentInformationList = clazz.getDeclaredMethod("personDocumentInformationList",
        			 new Class[]{String.class,String.class,String.class});
        	 str = (String) personDocumentInformationList.invoke(clazz.newInstance(),username,strKey,apptoken);
	     }else if(path.equals("/personDocumentInformation")){
	    	 String username = null;
	    	 String informationName = null;
	    	 String informationId = null;
	    	 String strKey = null;
	    	 String apptoken = null;
	    	 username = StringUtil.isEmpty(request.getParameter("username")) ? 
	    			 "" : java.net.URLDecoder.decode(request.getParameter("username"), "UTF-8");
	    	 informationName = StringUtil.isEmpty(request.getParameter("informationName")) ? 
	    			 "" : java.net.URLDecoder.decode(request.getParameter("informationName"), "UTF-8");
	    	 informationId = StringUtil.isEmpty(request.getParameter("informationId")) ? 
	    			 "" : java.net.URLDecoder.decode(request.getParameter("informationId"), "UTF-8");
        	 apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
        	 strKey = StringUtil.isEmpty(request.getParameter("strKey")) ? 
        			 "" : java.net.URLDecoder.decode(request.getParameter("strKey"), "UTF-8");
        	 CommonHttp commonHttp = new CommonHttp();
    		 Class clazz = commonHttp.getClass(); 
        	 Method personDocumentInformation = clazz.getDeclaredMethod("personDocumentInformation",
        			 new Class[]{String.class,String.class,String.class,String.class,String.class});
        	 str = (String) personDocumentInformation.invoke(clazz.newInstance(),username,informationName,informationId,strKey,apptoken);
	     }else if(path.equals("/getTodoTaskList")){
	    	 String username = null;
	    	 String start = null;
	    	 String size = null;
	    	 String sign = null;
	    	 String apptoken = null;
	    	 username = StringUtil.isEmpty(request.getParameter("username")) ? 
	    			 "" : java.net.URLDecoder.decode(request.getParameter("username"), "UTF-8");
	    	 start = StringUtil.isEmpty(request.getParameter("start")) ? 
	    			 "" : java.net.URLDecoder.decode(request.getParameter("start"), "UTF-8");
	    	 size = StringUtil.isEmpty(request.getParameter("size")) ? 
	    			 "" : java.net.URLDecoder.decode(request.getParameter("size"), "UTF-8");
	    	 sign = StringUtil.isEmpty(request.getParameter("sign")) ? 
	    			 "" : java.net.URLDecoder.decode(request.getParameter("sign"), "UTF-8");
        	 apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
        	 OAWebserviceHttp oaWebserviceHttp = new OAWebserviceHttp();
    		 Class clazz = oaWebserviceHttp.getClass(); 
        	 Method getTodoTaskList = clazz.getDeclaredMethod("getTodoTaskList",
        			 new Class[]{String.class,String.class,String.class,String.class,String.class});
        	 str = (String) getTodoTaskList.invoke(clazz.newInstance(),username,start,size,sign,apptoken);
	     }else if(path.equals("/getDoneTaskList")){
	    	 String username = null;
	    	 String start = null;
	    	 String size = null;
	    	 String sign = null;
	    	 String apptoken = null;
	    	 username = StringUtil.isEmpty(request.getParameter("username")) ? 
	    			 "" : java.net.URLDecoder.decode(request.getParameter("username"), "UTF-8");
	    	 start = StringUtil.isEmpty(request.getParameter("start")) ? 
	    			 "" : java.net.URLDecoder.decode(request.getParameter("start"), "UTF-8");
	    	 size = StringUtil.isEmpty(request.getParameter("size")) ? 
	    			 "" : java.net.URLDecoder.decode(request.getParameter("size"), "UTF-8");
	    	 sign = StringUtil.isEmpty(request.getParameter("sign")) ? 
	    			 "" : java.net.URLDecoder.decode(request.getParameter("sign"), "UTF-8");
        	 apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
        	 OAWebserviceHttp oaWebserviceHttp = new OAWebserviceHttp();
    		 Class clazz = oaWebserviceHttp.getClass(); 
        	 Method getDoneTaskList = clazz.getDeclaredMethod("getDoneTaskList",
        			 new Class[]{String.class,String.class,String.class,String.class,String.class});
        	 str = (String) getDoneTaskList.invoke(clazz.newInstance(),username,start,size,sign,apptoken);
	     }else if(path.equals("/getSoluteTaskList")){
	    	 String username = null;
	    	 String start = null;
	    	 String size = null;
	    	 String sign = null;
	    	 String apptoken = null;
	    	 username = StringUtil.isEmpty(request.getParameter("username")) ? 
	    			 "" : java.net.URLDecoder.decode(request.getParameter("username"), "UTF-8");
	    	 start = StringUtil.isEmpty(request.getParameter("start")) ? 
	    			 "" : java.net.URLDecoder.decode(request.getParameter("start"), "UTF-8");
	    	 size = StringUtil.isEmpty(request.getParameter("size")) ? 
	    			 "" : java.net.URLDecoder.decode(request.getParameter("size"), "UTF-8");
	    	 sign = StringUtil.isEmpty(request.getParameter("sign")) ? 
	    			 "" : java.net.URLDecoder.decode(request.getParameter("sign"), "UTF-8");
        	 apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
        	 OAWebserviceHttp oaWebserviceHttp = new OAWebserviceHttp();
    		 Class clazz = oaWebserviceHttp.getClass(); 
        	 Method getSoluteTaskList = clazz.getDeclaredMethod("getSoluteTaskList",
        			 new Class[]{String.class,String.class,String.class,String.class,String.class});
        	 str = (String) getSoluteTaskList.invoke(clazz.newInstance(),username,start,size,sign,apptoken);
	     }else if(path.equals("/getocbalance")){//应用中心下拉类型筛选
	    	 String username = null;
	    	 String strKey = null;
	    	 String apptoken = null;
	    	 username = StringUtil.isEmpty(request.getParameter("username")) ? 
	    			 "" : java.net.URLDecoder.decode(request.getParameter("username"), "UTF-8");
        	 apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
        	 strKey = StringUtil.isEmpty(request.getParameter("strKey")) ? 
        			 "" : java.net.URLDecoder.decode(request.getParameter("strKey"), "UTF-8");
    		 MyPortalHttp myPortalHttp = new MyPortalHttp();
    		 Class clazz = myPortalHttp.getClass(); 
        	 Method myPortal = clazz.getDeclaredMethod("getocbalance",
        			 new Class[]{String.class,String.class,String.class});
        	 str = (String) myPortal.invoke(clazz.newInstance(),username,strKey,apptoken);
	     }else if(path.equals("/getocdetail")){//一卡通消费或充值明细
	    	 String detailtype = null;
	    	 String ocid = null;
	    	 String startdate = null;
			 String enddate = null;
			 String pageindex = null;
			 String pagesize = null;
	    	 String strKey = null;
	    	 String apptoken = null;
	    	 strKey = StringUtil.isEmpty(request.getParameter("strKey")) ? 
	    			 "" : java.net.URLDecoder.decode(request.getParameter("strKey"), "UTF-8");
	    	 detailtype = StringUtil.isEmpty(request.getParameter("detailtype")) ? 
	    			 "" : java.net.URLDecoder.decode(request.getParameter("detailtype"), "UTF-8");
	    	 ocid = StringUtil.isEmpty(request.getParameter("ocid")) ? 
	    			 "" : java.net.URLDecoder.decode(request.getParameter("ocid"), "UTF-8");
//	    	 startdate = StringUtil.isEmpty(request.getParameter("startdate")) ? 
//	    			 "" : java.net.URLDecoder.decode(request.getParameter("startdate"), "UTF-8");
//	    	 enddate = StringUtil.isEmpty(request.getParameter("enddate")) ? 
//	    			 "" : java.net.URLDecoder.decode(request.getParameter("enddate"), "UTF-8");
	    	 pagesize = StringUtil.isEmpty(request.getParameter("pagesize")) ? 
	    			 "" : java.net.URLDecoder.decode(request.getParameter("pagesize"), "UTF-8");
	    	 pageindex = StringUtil.isEmpty(request.getParameter("pageindex")) ? 
	    			 "" : java.net.URLDecoder.decode(request.getParameter("pageindex"), "UTF-8");
        	 apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
    		 MyPortalHttp myPortalHttp = new MyPortalHttp();
    		 Class clazz = myPortalHttp.getClass(); 
        	 Method myPortal = clazz.getDeclaredMethod("getocdetail",
        			 new Class[]{String.class,String.class,String.class,String.class,String.class,String.class});
        	 str = (String) myPortal.invoke(clazz.newInstance(), detailtype,  ocid,
        			 pageindex,  pagesize,  strKey,
        			 apptoken);
	     }else if(path.equals("/getMhRecommendPage")){//获取首页xinwen
	    	 String size = null;
	    	 size = StringUtil.isEmpty(request.getParameter("size")) ? 
	    			 "" : java.net.URLDecoder.decode(request.getParameter("size"), "UTF-8");
	    	 HomePageHttp homePageHttp = new HomePageHttp();
    		 Class clazz = homePageHttp.getClass(); 
        	 Method getMhRecommendPage = clazz.getDeclaredMethod("getMhRecommendPage",
        			 new Class[]{String.class});
        	 str = (String) getMhRecommendPage.invoke(clazz.newInstance(), size);
	     }else if(path.equals("/getCommonService")){//获取首页不登录时服务
	    	 HomePageHttp homePageHttp = new HomePageHttp();
    		 Class clazz = homePageHttp.getClass(); 
        	 Method getCommonService = clazz.getDeclaredMethod("getCommonService");
        	 str = (String) getCommonService.invoke(clazz.newInstance());
	     }else if(path.equals("/Commonfunction")){//获取首页登录时服务
	    	 String username = null;
	    	 String strKey = null;
	    	 String apptoken = null;
	    	 username = StringUtil.isEmpty(request.getParameter("username")) ? 
	    			 "" : java.net.URLDecoder.decode(request.getParameter("username"), "UTF-8");
	    	 strKey = StringUtil.isEmpty(request.getParameter("strKey")) ? 
	    			 "" : java.net.URLDecoder.decode(request.getParameter("strKey"), "UTF-8");
	    	 apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
	    	 HomePageHttp homePageHttp = new HomePageHttp();
    		 Class clazz = homePageHttp.getClass(); 
        	 Method Commonfunction = clazz.getDeclaredMethod("Commonfunction",
        			 new Class[]{String.class,String.class,String.class});
        	 str = (String) Commonfunction.invoke(clazz.newInstance(),username,strKey,apptoken);
	     }
	     
	     else{
	    	 logger.error("没有此类服务");
	    	 str="{\"failure\":\"没有此类服务\"}";
	     }
	     out.print(str);
	     out.flush();
	     out.close();
		} catch (Exception e) {
			 logger.error(e);
			 logger.error("后台异常");
			 e.printStackTrace();
			 /*str="{\"failure\":\"后台异常\"}";
			 PrintWriter out = response.getWriter();
			 out.print(str);
		     out.flush();
		     out.close();*/
		}
	}
	
	
	
	
	public static String getBodyString(BufferedReader br) {
		  String inputLine;
		       String str = "";
		     try {
		       while ((inputLine = br.readLine()) != null) {
		        str += inputLine;
		       }
		       br.close();
		     } catch (IOException e) {
		       System.out.println("IOException: " + e);
		     }
		     return str;
		 }

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
	

}
