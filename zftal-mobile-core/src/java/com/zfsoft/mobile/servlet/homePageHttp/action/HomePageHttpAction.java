/**
 * 
 */
package com.zfsoft.mobile.servlet.homePageHttp.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.common.Config;
import com.common.WebServiceConf;
import com.google.gson.Gson;
import com.jw.org.tempuri.ICourseSchedule;
import com.newmobile.login.service.IWSSerService;
import com.util.MiddleWareUtil;
import com.util.WebServiceUtil;
import com.zfsoft.common.log.User;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.dao.entities.LoginModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.ballot.utils.CommonUtils;
import com.zfsoft.mobile.common.enums.ShowTypeEnum;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.common.utils.JSONUtils;
import com.zfsoft.mobile.configuration.entity.NewsConfig;
import com.zfsoft.mobile.configuration.query.NewsConfigQuery;
import com.zfsoft.mobile.configuration.service.INewsConfigService;
import com.zfsoft.mobile.news.entity.News;
import com.zfsoft.mobile.news.entity.NewsCatalog;
import com.zfsoft.mobile.news.query.NewsQuery;
import com.zfsoft.mobile.news.service.INewsCatalogService;
import com.zfsoft.mobile.news.service.INewsService;
import com.zfsoft.mobile.services.entity.LossObjectEntity;
import com.zfsoft.mobile.services.entity.ServiceManager;
import com.zfsoft.mobile.servlet.entity.AppServiceInfo;
import com.zfsoft.mobile.servlet.entity.ListEntity;
import com.zfsoft.mobile.servlet.entity.MhRecommend;
import com.zfsoft.mobile.servlet.entity.NewsEntity;
import com.zfsoft.mobile.servlet.entity.NewsTabEntity;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.mobile.servlet.entity.ServiceEntity;
import com.zfsoft.mobile.servlet.homePageHttp.newoa.jhc.cn.CourseScheduleEntity;
import com.zfsoft.mobile.servlet.homePageHttp.newoa.jhc.cn.MeetingEntity;
import com.zfsoft.mobile.servlet.homePageHttp.newoa.jhc.cn.WsInfoPortal;
import com.zfsoft.mobile.servlet.homePageHttp.service.IHomePageHttpService;
import com.zfsoft.mobile.webservices.entity.ScalendarEntity;
import com.zfsoft.service.svcinterface.ILoginService;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.date.DateTimeUtil;

/**
 * @author zhangxu
 * @description  app首页访问接口
 * @date 2017-5-8 下午04:53:27
 */
public class HomePageHttpAction {
	private static Logger logger = Logger.getLogger(HomePageHttpAction.class);
	private final String infromation=Config.getString("mobile.infromation");

	private IHomePageHttpService homePageHttpService;
	private ILoginService loginService;
	private INewsService newsService;
	private INewsConfigService newsConfigService;
	private INewsCatalogService newsCatalogService;
	
	
	public INewsConfigService getNewsConfigService() {
		return newsConfigService;
	}

	public void setNewsConfigService(INewsConfigService newsConfigService) {
		this.newsConfigService = newsConfigService;
	}

	public INewsCatalogService getNewsCatalogService() {
		return newsCatalogService;
	}

	public void setNewsCatalogService(INewsCatalogService newsCatalogService) {
		this.newsCatalogService = newsCatalogService;
	}

	public void setHomePageHttpService(IHomePageHttpService homePageHttpService) {
		this.homePageHttpService = homePageHttpService;
	}

	public IHomePageHttpService getHomePageHttpService() {
		return homePageHttpService;
	}
	
	
	public ILoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(ILoginService loginService) {
		this.loginService = loginService;
	}
	
	public INewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(INewsService newsService) {
		this.newsService = newsService;
	}
	
	
	/**
	 * 获取校历数据 
	 * @param ymd
	 * @param apptoken
	 * @return
	 */
	public void getTermWeek() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		Gson gson = new Gson();
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String apptoken = null;
		String ymd = null;
		apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
//		ymd = StringUtil.isEmpty(request.getParameter("ymd")) ? "" : request.getParameter("ymd");
		if(StringUtil.isEmpty(apptoken)){
			ResultEntity<List<ScalendarEntity>> result = new ResultEntity<List<ScalendarEntity>>(0, "参数传值出错！", null);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		}
		if(!ApptokenUtils.compare(apptoken)){
			ResultEntity<List<ScalendarEntity>> result = new ResultEntity<List<ScalendarEntity>>(2, "app_token error!", null);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		}
//		try {
//			ymd       = CodeUtil.decode(ymd, apptoken);
//		} catch (Exception e) {
//			ResultEntity<List<ScalendarEntity>> result = new ResultEntity<List<ScalendarEntity>>(0, "加密方式出错！", null);
//			out.write(gson.toJson(result));
//			out.flush();
//			out.close();
//		}	
		if(infromation.equals("0")){
			logger.error("获取当前学期周:");
		}
		IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
		List<ScalendarEntity> entityList = mobileCommonService.getTermWeek(ymd);
		for(ScalendarEntity entity : entityList){
			if(infromation.equals("0")){
				logger.error("获取当前学期周entity:"+entity);
			}
		}
		if(infromation.equals("0")){
			logger.error("获取当前学期周----end");
		}
		ResultEntity<List<ScalendarEntity>> result = new ResultEntity<List<ScalendarEntity>>(1, "成功", entityList);
		out.write(gson.toJson(result));
		out.flush();
		out.close();
	}
	
	/**
	 * 金华职业个性化开发获取课表
	* @author: zhangxu
	* @Title: CourseScheduleSearch 
	* @Description: 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void CourseScheduleSearch(){
		

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			CommonUtils commonUtils = new CommonUtils();
			int week = commonUtils.getWeek(new Date());
		
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("dqz", "8");
			map1.put("kcmc", "英语口语2");
			map1.put("rkjs", "杨灿");
			map1.put("kcdm", "16010593");
			map1.put("kcxz", "专业课选修");
			map1.put("xf", "2");
			map1.put("skz", "1,2,3,4,5,7,8,9,10,11,12,13,14,15,16,17");
			map1.put("xqj", "5");
			map1.put("js", "1,2");
			map1.put("skjs", "西教2-4楼-408-54-多媒体;西教2-4楼-408-54-多媒体");
			map1.put("kcb", ",");
			map1.put("dsz", "");
			
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("dqz", "8");
			map2.put("kcmc", "前厅服务与管理A");
			map2.put("rkjs", "陈璐");
			map2.put("kcdm", "14010379");
			map2.put("kcxz", "专业课选修");
			map2.put("xf", "2");
			map2.put("skz", "1,2,3,4,5,7,8,9,10,11,12,13,14,15,16,17");
			map2.put("xqj", "5");
			map2.put("js", "3,4");
			map2.put("skjs", "西教2-4楼-408-54-多媒体;西教2-4楼-408-54-多媒体");
			map2.put("kcb", ",");
			map2.put("dsz", "");
			
			Map<String, Object> map3 = new HashMap<String, Object>();
			map3.put("dqz", "8");
			map3.put("kcmc", "ESLA2");
			map3.put("rkjs", "盖乐");
			map3.put("kcdm", "16010395");
			map3.put("kcxz", "专业课选修");
			map3.put("xf", "2");
			map3.put("skz", "1,2,3,4,5,7,8,9,10,11,12,13,14,15,16,17");
			map3.put("xqj", "5");
			map3.put("js", "5,6");
			map3.put("skjs", "西教3-4楼-408-52-中加酒馆专用教室;");
			map3.put("kcb", ",");
			map3.put("dsz", "");
			
			Map<String, Object> map4 = new HashMap<String, Object>();
			map4.put("dqz", "8");
			map4.put("kcmc", "Special Events as Tourism Generators");
			map4.put("rkjs", "Coltan Fagan/顾敏艳");
			map4.put("kcdm", "14020061");
			map4.put("kcxz", "专业课选修");
			map4.put("xf", "2");
			map4.put("skz", "1,2,3,4,5,7,8,9,10,11,12,13,14,15,16,17");
			map4.put("xqj", "5");
			map4.put("js", "7,8");
			map4.put("skjs", "西教3-4楼-408-52-中加酒馆专用教室;");
			map4.put("kcb", ",");
			map4.put("dsz", "");
			
			Map<String, Object> map5 = new HashMap<String, Object>();
			map5.put("dqz", "8");
			map5.put("kcmc", "创业基础A");
			map5.put("rkjs", "沈燕飞");
			map5.put("kcdm", "21030028");
			map5.put("kcxz", "通识课选修");
			map5.put("xf", "2");
			map5.put("skz", "1,2,3,4,5,7,8,9,10,11,12,13,14,15,16,17");
			map5.put("xqj", "5");
			map5.put("js", "9,10");
			map5.put("skjs", "西教3-4楼-408-52-中加酒馆专用教室;");
			map5.put("kcb", ",");
			map5.put("dsz", "");
			
			List list = new ArrayList();
			if (week == 1) {
				list.add(map1);
			}else if (week == 2 || week == 3) {
				list.add(map2);
			}else if (week == 4 || week == 5) {
				list.add(map3);
			}else if (week == 6) {
				list.add(map4);
			}else if (week == 7) {
				list.add(map5);
			}
			
			ResultEntity<List> result = new ResultEntity<List>(1, "成功", list);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		String apptoken = null;
		String username = null;
		String year = null;
		String term = null;
		String role = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
	 	username = StringUtil.isEmpty(request.getParameter("username")) ? "" : request.getParameter("username");
	 	year = StringUtil.isEmpty(request.getParameter("year")) ? "" : request.getParameter("year");
	 	term = StringUtil.isEmpty(request.getParameter("term")) ? "" : request.getParameter("term");
	 	role = StringUtil.isEmpty(request.getParameter("role")) ? "" : request.getParameter("role");
		Gson gson = new Gson();
	 	if(StringUtil.isEmpty(apptoken) || StringUtil.isEmpty(username) || StringUtil.isEmpty(year)
	 			|| StringUtil.isEmpty(term) || StringUtil.isEmpty(role)){
			ResultEntity<List<CourseScheduleEntity>> result = new ResultEntity<List<CourseScheduleEntity>>(0, "参数传值出错！", null);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		}
	 	if(!ApptokenUtils.compare(apptoken)){
			ResultEntity<List<CourseScheduleEntity>> result = new ResultEntity<List<CourseScheduleEntity>>(2, "app_token error!", null);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		}
	 	try {
	 		username  		= CodeUtil.decode(username, apptoken);
	 		year  			= CodeUtil.decode(year, apptoken);
	 		term  			= CodeUtil.decode(term, apptoken);
	 		role  			= CodeUtil.decode(role, apptoken);
			
		} catch (Exception e) {
			ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		}
		
		// 走教务系统接口
		String parameterList = username + "&" + year + "&" + term;
		// 调用WebService
		ICourseSchedule service = (ICourseSchedule) WebServiceUtil
		.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_TIMETABLE,
				ICourseSchedule.class, "CourseScheduleSearch")
				.create();
		System.out.println(parameterList);
		String JWSign = MiddleWareUtil.getJWSign(parameterList);
		System.out.println(JWSign);
		
		String str = service.courseScheduleSearch(parameterList, role, "0",
				JWSign);
		str = str.replaceAll("<br>", ";");
		str = JSONUtils.xml2json(str);
		System.out.println("str:"+str);
//        JSONObject jsonObject = JSONObject.fromObject(str);
        JSONArray jsonArray = JSONArray.fromObject(str ); 
        List<CourseScheduleEntity> courseScheduleEntityList = null;
        if(jsonArray.size()>0){
        	courseScheduleEntityList = new ArrayList<CourseScheduleEntity>();
        	CourseScheduleEntity  courseScheduleEntity;
        	Calendar cal = Calendar.getInstance();
        	Date nowDate = new Date();
            cal.setTime(nowDate);
//    		int week_of_year = cal.get(Calendar.DAY_OF_WEEK);
//    		week_of_year = week_of_year == 1 ? 7 : week_of_year -1 ;
//    		logger.error("week_of_year:"+week_of_year);
//    		String week_of_year_Str = String.valueOf(week_of_year);
        	for(int i=0;i<jsonArray.size();i++){
        		courseScheduleEntity = new CourseScheduleEntity();
        	    JSONObject meetJson = jsonArray.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
//        	    if(!meetJson.getString("xqj").equals(week_of_year_Str)) continue;
        	    courseScheduleEntity.setDqz(meetJson.getString("dqz"));
        	    courseScheduleEntity.setKcmc(meetJson.getString("kcmc"));
        	    courseScheduleEntity.setRkjs(meetJson.getString("rkjs"));
        	    courseScheduleEntity.setKcdm(meetJson.getString("kcdm"));
        	    courseScheduleEntity.setKcxz(meetJson.getString("kcxz"));
        	    courseScheduleEntity.setXf(meetJson.getString("xf"));
        	    courseScheduleEntity.setSkz(meetJson.getString("skz"));
        	    courseScheduleEntity.setXqj(meetJson.getString("xqj"));
        	    courseScheduleEntity.setJs(meetJson.getString("js"));
        	    courseScheduleEntity.setSkjs(meetJson.getString("skjs"));
        	    courseScheduleEntity.setKcb(meetJson.getString("kcb"));
        	    courseScheduleEntity.setDsz(meetJson.getString("dsz"));
        	    courseScheduleEntityList.add(courseScheduleEntity);
        	  }
        }
        ResultEntity<List<CourseScheduleEntity>> result = new ResultEntity<List<CourseScheduleEntity>>(1, "成功", courseScheduleEntityList);
		try {
			out = response.getWriter();
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	
	/**
	 * 金华职业个性化开发首页获取会议列表
	* @author: zhangxu
	* @Title: getDataMapForMeeting 
	* @Description: 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void getDataMapForMeeting(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		
		String apptoken = null;
    	PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
		Gson gson = new Gson();
	 	if(StringUtil.isEmpty(apptoken)){
			ResultEntity<List<MeetingEntity>> result = new ResultEntity<List<MeetingEntity>>(0, "参数传值出错！", null);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		}
	 	if(!ApptokenUtils.compare(apptoken)){
			ResultEntity<List<MeetingEntity>> result = new ResultEntity<List<MeetingEntity>>(2, "app_token error!", null);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		}
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(WsInfoPortal.class);  
        bean.setAddress(WebServiceConf.SERVICE_JINHUAOAESERVICE);  
        WsInfoPortal WsInfoPortal = (WsInfoPortal)bean.create();  
        String resultXML = WsInfoPortal.getDataMapForMeeting();
        logger.error(resultXML);
        resultXML = resultXML.substring(resultXML.indexOf("<meetingList>"), resultXML.indexOf("</meetingList>")+14);
        resultXML = JSONUtils.xml2json(resultXML);
        JSONObject jsonObject = JSONObject.fromObject(resultXML);
        JSONArray jsonArray = jsonObject.getJSONArray("meeting");
        List<MeetingEntity> meetingList = null;
        if(jsonArray.size()>0){
        	meetingList = new ArrayList<MeetingEntity>();
        	MeetingEntity  meetingEntity;
        	for(int i=0;i<jsonArray.size();i++){
        		JSONObject meetJson = jsonArray.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
        		String startDate = meetJson.getString("startdate");
        		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//        		if(!startDate.equals(df.format(new Date()))) continue;// new Date()为获取当前系统时间
        		
        		meetingEntity = new MeetingEntity();
        	    meetingEntity.setAddress(meetJson.getString("address"));
        	    meetingEntity.setSubject(meetJson.getString("subject"));
        	    meetingEntity.setHost(meetJson.getString("host"));
        	    meetingEntity.setOrganizer(meetJson.getString("organizer"));
        	    meetingEntity.setParticipants(meetJson.getString("participants"));
        	    meetingEntity.setPkid(meetJson.getString("pkid"));
        	    meetingEntity.setStartdate(meetJson.getString("startdate"));
        	    meetingEntity.setStarttime(meetJson.getString("starttime"));
        	    meetingList.add(meetingEntity);
        	  }
        }
        ResultEntity<List<MeetingEntity>> result = new ResultEntity<List<MeetingEntity>>(1, "成功", meetingList);
		try {
			out = response.getWriter();
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	* @author: zhangxu
	* @Title: getAdvertList 
	* @Description: 获取广告位
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void getAdvertList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		NewsQuery newsQuery = new NewsQuery();
		Gson gson = new Gson();
		newsQuery.setHeadline("1");
		List<News> newsList = newsService.getPageList(newsQuery);
		String goUrl;
		String url = getImageHost();
		if(newsList != null && newsList.size() > 0){
			for(int i = 0;i < newsList.size();i++ ){
				goUrl = url + "mobile/web_content.html?newsId=" + newsList.get(i).getId();
				newsList.get(i).setNewsURL(goUrl);
				newsList.get(i).setPicUrl(url+newsList.get(i).getPicUrl());
			}
		}
		ResultEntity<List<News>> result = new ResultEntity<List<News>>(1, "成功", newsList);
		PrintWriter out;
		try {
			out = response.getWriter();
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	* @author: zhangxu
	* @Title: getNewsList 
	* @Description: 获取首页新闻列表
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void getNewsList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String catalogCode = null; 
		String starts = null; 
		String sizes = null;
        try {
        	PrintWriter out = response.getWriter();
        	catalogCode = new String(request.getParameter("catalogCode").getBytes("ISO8859-1"), "UTF-8");
        	starts = new String(request.getParameter("starts").getBytes("ISO8859-1"), "UTF-8");
        	sizes = new String(request.getParameter("sizes").getBytes("ISO8859-1"), "UTF-8");
			Gson gson = new Gson();
		 	if(StringUtil.isEmpty(starts) || StringUtil.isEmpty(sizes)){
				ResultEntity<ListEntity<NewsEntity>> result = new ResultEntity<ListEntity<NewsEntity>>(0, "参数传值出错！", new ListEntity<NewsEntity>());
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		
		 	List<NewsEntity> NewsList = null;
			if(StringUtil.isEmpty(starts) || StringUtil.isEmpty(sizes)){
				ResultEntity<ListEntity<NewsEntity>> result = new ResultEntity<ListEntity<NewsEntity>>(0, "参数传值出错！", new ListEntity<NewsEntity>());
				out.write(gson.toJson(result));
				out.flush();
				out.close();
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
			PageList<News> newsList = newsService.getPageList(newsQuery);
			if (newsList.size() <= 0) {
				ResultEntity<ListEntity<NewsEntity>> result = new ResultEntity<ListEntity<NewsEntity>>(1, "成功", new ListEntity<NewsEntity>());
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			NewsList = new ArrayList<NewsEntity>();
	        String url = getImageHost();
	        String goUrl = "";
	        	for (News news : newsList) {
	        		entity = new NewsEntity();
	        		goUrl = url + "mobile/web_content.html?newsId=" + news.getId();
	        		entity.setId(news.getId());
	        		entity.setTimeCreate(DateTimeUtil.getFormatDate(news.getCreateTime()));
	        		entity.setTitle(news.getTitle());
//	        		goUrl = StringUtil.isEmpty(news.getNewsURL()) ? "" :url + "mobile/web_content.html?newsId=" + news.getId();
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
	        						check = homePageHttpService.checkImage(picUrls[i], picIds[i]);
	        					} catch (Exception e) {
	        						logger.error("getNewsList  checkImage err：");
	        						logger.error(e, e.fillInStackTrace());
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
			//输出结果
	        
	        ListEntity<NewsEntity> resultList = new ListEntity<NewsEntity>();
			resultList.setItemList(NewsList);
			if(NewsList == null || NewsList.size() < Integer.valueOf(sizes))	resultList.setOvered(true);
			else resultList.setOvered(false);
			ResultEntity<ListEntity<NewsEntity>> result = new ResultEntity<ListEntity<NewsEntity>>(1, "成功", resultList);
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
        }catch (Exception e) {
        	logger.error("获取首页新闻列表getNewsList err：");
			logger.error(e, e.fillInStackTrace());
        }
	}

	private static final String SERVICE_FRONT = "service_front";
	private static final String TT_NAME = "tt_name";
	/**
	 * 
	* @author: zhangxu
	* @Title: getNewsTab 
	* @Description:获取新闻栏目 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void getNewsTab(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = null;
        String strKey = null;
        try {
        	PrintWriter out = response.getWriter();
        	username = request.getParameter("username");
			username = !StringUtil.isEmpty(username) ? 
					new String(username.getBytes("ISO8859-1"), "UTF-8") : "";
			strKey = new String(request.getParameter("strKey").getBytes("ISO8859-1"), "UTF-8");
			Gson gson = new Gson();
		
			List<NewsTabEntity> tabList = null;
			if(StringUtil.isEmpty(strKey)){
				ResultEntity<List<NewsTabEntity>> result = new ResultEntity<List<NewsTabEntity>>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
		        out.flush();
		        out.close();
			}
			tabList = new ArrayList<NewsTabEntity>();
			try {
				
			if(infromation.equals("0")){
				logger.error("获取新闻栏目:username"+username+",strKey"+strKey);
				}
			
			String url = getImageHost();
			NewsConfigQuery configQuery = new NewsConfigQuery();
			configQuery.setKey(SERVICE_FRONT);
			NewsConfig config = newsConfigService.findByKey(configQuery);
			String serviceIds = null;
			if (config != null) {
				serviceIds = config.getValue();
			}
			//String serviceIds = MobileSystemHolder.getPropertiesValue("fwids");
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("isAll", "true");
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
						List<ServiceManager> services = homePageHttpService
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
								check = homePageHttpService.checkImage(
										service.getClassFwtbdz(),
										service.getClassFwtbid());
							} catch (Exception e) {
								logger.error("getNewsTab checkImage  err：");
								logger.error(e, e.fillInStackTrace());
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
					check = homePageHttpService.checkImage(catalog.getLogoUrl(),
							catalog.getLogoId());
				} catch (Exception e) {
					logger.error("getNewsTab checkImage2  err：");
					logger.error(e, e.fillInStackTrace());
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
			//输出结果
			ResultEntity<List<NewsTabEntity>> result = new ResultEntity<List<NewsTabEntity>>(1, "成功", tabList);
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
        }catch (Exception e) {
        	logger.error("获取新闻栏目 getNewsTab err：");
			logger.error(e, e.fillInStackTrace());
        }
	}
	/**
	 * 
	* @author: zhangxu
	* @Title: SubmitCommonFunction 
	* @Description: 编辑个人常用服务 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void SubmitCommonFunction(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String data = null;
        String apptoken = null;
        try {
        	PrintWriter out = response.getWriter();
        	data = new String(request.getParameter("data").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			Gson gson = new Gson();
		 	if(StringUtil.isEmpty(data) || StringUtil.isEmpty(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		 	if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		 	try {
		 		data  			= CodeUtil.decode(data, apptoken);
				
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			AppServiceInfo appServiceInfo = gson.fromJson(data, AppServiceInfo.class);
			if(infromation.equals("0")){
				logger.error("应用中心常用模块获取："+appServiceInfo.toString());
				}
	    	String userId = appServiceInfo.getUserId();
	    	List<String> serviceList = appServiceInfo.getServiceList();
	    	if (serviceList != null && serviceList.size() > 0) {
	    		homePageHttpService.deleteAllFrequentlyService(userId);
	    		for (int i = 0; i < serviceList.size(); i++) {
	    			homePageHttpService.insertFrequentlyService(userId, serviceList.get(i).trim(), true);
	    		}
	    	}
			//输出结果
			ResultEntity<String> result = new ResultEntity<String>(1, "成功", null);
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
        }catch (Exception e) {
        	logger.error("编辑个人常用服务 SubmitCommonFunction 参数解析异常 err：");
			logger.error(e, e.fillInStackTrace());
        }
	}
	
	/**
	 * 
	* @author: zhangxu
	* @Title: Commonfunction 
	* @Description: 根据帐号取得该帐号的常用服务
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void Commonfunction(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = null;
        String apptoken = null;
        String strKey   = null;
        try {
        	PrintWriter out = response.getWriter();
			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
			strKey   = new String(request.getParameter("strKey").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			Gson gson = new Gson();
		 	if(StringUtil.isEmpty(username) || StringUtil.isEmpty(strKey)
		 			|| StringUtil.isEmpty(apptoken)){
				ResultEntity<List<ServiceEntity>> result = new ResultEntity<List<ServiceEntity>>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		 	if(!ApptokenUtils.compare(username, apptoken)){
				ResultEntity<List<ServiceEntity>> result = new ResultEntity<List<ServiceEntity>>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		 	try {
				username  			= CodeUtil.decode(username, apptoken);
				strKey  			= CodeUtil.decode(strKey, apptoken);
				
			} catch (Exception e) {
				ResultEntity<List<ServiceEntity>> result = new ResultEntity<List<ServiceEntity>>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		
			List<ServiceEntity> serviceEntityList = new ArrayList<ServiceEntity>();
			
	        List<ServiceManager> services = homePageHttpService.getFrequentlyServiceByUser(username);
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
				serviceEntity.isSignal    = StringUtil.isEmpty(sm.getIsSignal()) ? "" : sm.getIsSignal();
				serviceEntity.recommendFlag = StringUtil.isEmpty(sm.getRecommendFlag()) ? "" : sm.getRecommendFlag();
				serviceEntity.signalUrl   = StringUtil.isEmpty(sm.getSignalUrl()) ? "" : sm.getSignalUrl();
				serviceEntity.signalXtbm  = StringUtil.isEmpty(sm.getSignalXtbm()) ? "" : sm.getSignalXtbm();
				serviceEntityList.add(serviceEntity);
	        }
			//输出结果
			ResultEntity<List<ServiceEntity>> result = new ResultEntity<List<ServiceEntity>>(1, "成功", serviceEntityList);
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
        }catch (Exception e) {
        	logger.error("根据帐号取得该帐号的常用服务Commonfunction 参数解析异常 err：");
			logger.error(e, e.fillInStackTrace());
        }
	}
	
	/**
	 * 
	* @author: zhangxu
	* @Title: getCommonService 
	* @Description:获取首页不登录时服务 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void getCommonService(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
        try {
        	PrintWriter out = response.getWriter();
			Gson gson = new Gson();
		
			List<ServiceManager> services = homePageHttpService.getCommonService();
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
				serviceEntity.isCommon    = StringUtil.isEmpty(sm.getIscommon()) ? "" : sm.getIscommon();
				serviceEntityList.add(serviceEntity);
	        }
			//输出结果
			ResultEntity<List<ServiceEntity>> result = new ResultEntity<List<ServiceEntity>>(1, "成功", serviceEntityList);
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
        }catch (Exception e) {
        	logger.error("获取首页不登录时服务getCommonService err：");
			logger.error(e, e.fillInStackTrace());
        }
	}
	
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
	 * 
	* @author: zhangxu
	* @Title: getMhRecommendPage 
	* @Description: 获取推荐位新闻
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void getMhRecommendPage(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
        try {
        	PrintWriter out = response.getWriter();
        	String sizes = new String(request.getParameter("size").getBytes("ISO8859-1"), "UTF-8");
			Gson gson = new Gson();
		 	if(StringUtil.isEmpty(sizes)){
				ResultEntity<List<MhRecommend>> result = new ResultEntity<List<MhRecommend>>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		
			if(infromation.equals("0")){
				logger.error("获取推荐位新闻:"+",sizes="+sizes);
			}
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
					ResultEntity<List<MhRecommend>> result = new ResultEntity<List<MhRecommend>>(1, "成功", entityList);
					out.write(gson.toJson(result));
					out.flush();
					out.close();
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
		            				check = homePageHttpService.checkImage(picUrls[i], picIds[i]);
		            			} catch (Exception e) {
		            				logger.error("获取推荐位新闻checkimage err：");
		            				logger.error(e, e.fillInStackTrace());
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
				ResultEntity<List<MhRecommend>> result = new ResultEntity<List<MhRecommend>>(0, "后台产生异常！", null);
				out.write(gson.toJson(result));
		        out.flush();
		        out.close();
			}
	        if(infromation.equals("0")){
	    		logger.error("获取推荐位新闻end");
	    		}
			//输出结果
			ResultEntity<List<MhRecommend>> result = new ResultEntity<List<MhRecommend>>(1, "成功", entityList);
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
        }catch (Exception e) {
        	logger.error("获取推荐位新闻getMhRecommendPage 参数解析异常 err：");
			logger.error(e, e.fillInStackTrace());
        }
	}
}
