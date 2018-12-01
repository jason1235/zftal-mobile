package com.zfsoft.mobile.web.action;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.struts2.ServletActionContext;

import com.common.WebServiceConf;
import com.newmobile.login.service.IMobileLoginXMLService;
import com.zfsoft.common.log.User;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.dao.entities.LoginModel;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.MD5Util.MD5UtilInAndroid;
import com.zfsoft.mobile.common.enums.ShowTypeEnum;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.common.utils.Authentication;
import com.zfsoft.mobile.common.utils.JSONUtils;
import com.zfsoft.mobile.common.utils.MobileSystemHolder;
import com.zfsoft.mobile.news.entity.News;
import com.zfsoft.mobile.news.entity.NewsCatalog;
import com.zfsoft.mobile.news.query.NewsCatalogQuery;
import com.zfsoft.mobile.news.query.NewsQuery;
import com.zfsoft.mobile.news.service.INewsCatalogService;
import com.zfsoft.mobile.news.service.INewsService;
import com.zfsoft.mobile.services.entity.Business;
import com.zfsoft.mobile.web.common.Config;
import com.zfsoft.service.svcinterface.ILoginService;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.weibo.weibo4j.http.HttpRequest;

public class WebAction extends HrmAction {

	private static String NEW_MOBILE_ADDR = "";

	private static final String USER_INFO = "user_info";

	private String catalogId = "TT";
	private String newsId;
	private String serviceCode;
	private String callback;
	private String type = "TELETEXT_SHOW";
	private String listType = "1";
	
	private String school;

	private String username;
	private String password;
	private String module;

	private String start = "1";
	private String size = "10";

	private INewsService newsService;
	private INewsCatalogService newsCatalogService;
	private IMobileCommonService mobileCommonService;

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String header() {

		return "header";
	}

	public String footer() {

		return "footer";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getListType() {
		return listType;
	}

	public void setListType(String listType) {
		this.listType = listType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	
	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public void setNewsService(INewsService newsService) {
		this.newsService = newsService;
	}

	public INewsService getNewsService() {
		return newsService;
	}

	
	public INewsCatalogService getNewsCatalogService() {
		return newsCatalogService;
	}

	public void setNewsCatalogService(INewsCatalogService newsCatalogService) {
		this.newsCatalogService = newsCatalogService;
	}

	public IMobileCommonService getMobileCommonService() {
		return mobileCommonService;
	}

	public void setMobileCommonService(IMobileCommonService mobileCommonService) {
		this.mobileCommonService = mobileCommonService;
	}

	/**
	 * 首页资讯action
	 * 
	 * @return
	 */
	public String index() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String more = request.getParameter("more");
		if(!StringUtil.isEmpty(more) && more.equals("yes")){
			String start = request.getParameter("start");
			String newsList = "";
			if ("TT".equals(catalogId) || StringUtil.isEmpty(catalogId)) {
				newsList = getNewsList("", start, size);
			} else {
				newsList = getNewsList(catalogId, start, size);
			}
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("success", true);
            data.put("start", start);
            data.put("newsList", JSONUtils.xml2json(newsList));
            getValueStack().set(DATA, data);
    		return DATA;
		}
		
		
		if(getUser() == null){
			//start 应付演示
			LoginModel model = new LoginModel();
			model.setYhm("1053");
			ILoginService loginService = (ILoginService) SpringHolder.getBean("loginService");
			User user = loginService.cxYhxx(model);
			HttpSession session = getSession();
			session.setAttribute(USER_INFO_KEY, user);
		}
		
		
		//获取推荐位新闻，首页最上方
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IMobileLoginXMLService.class);
		factory.setAddress(Config.NEW_MOBILE_ADDR);
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IMobileLoginXMLService service = (IMobileLoginXMLService) factory
				.create();
		String recommendList = "";
		try {
			recommendList = service.getMhRecommendPage("5");
			getValueStack().set("recommendList", JSONUtils.xml2json(recommendList));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//获取常用服务
		String commonApps = getCommonByUser();
		this.getValueStack().set("apps", JSONUtils.xml2json(commonApps));
		//获取头条新闻
		String TTlist = "";
		if ("TT".equals(catalogId) || StringUtil.isEmpty(catalogId)) {
			TTlist = getNewsList("", start, size);
		} else {
			TTlist = getNewsList(catalogId, start, size);
		}
		this.getValueStack().set("start", start);
		this.getValueStack().set("TTlist", JSONUtils.xml2json(TTlist));
		
		
		String apptoken = getApptoken(getSessionUser());
		// String str = service.commonfunction("255", Authentication.sign());
		String newsTab = null;
		try {
			newsTab = service.getNewsTab(getSessionUser(),"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.getValueStack().set("newsTab", JSONUtils.xml2json(newsTab));
		
		
		
		//end 应付演示
		/*String title = "";
		String newsList = "";
		if ("TT".equals(catalogId) || StringUtil.isEmpty(catalogId)) {
			newsList = getNewsList("", start, size);
			title = MobileSystemHolder.getPropertiesValue("tt_name");
		} else {
			newsList = getNewsList(catalogId, start, size);
			NewsCatalogQuery catalogQuery = new NewsCatalogQuery();
			catalogQuery.setCatalogCode(catalogId);
			NewsCatalog newsCatalog = newsCatalogService.findByName(catalogQuery);
			title = newsCatalog.getCatalogName();
		}
		String menu = getMenu();
		

		this.getValueStack().set("newsList", JSONUtils.xml2json(newsList));
		this.getValueStack().set("menu", JSONUtils.xml2json(menu));
		// this.getValueStack().set("tabs", getWSResult(WS_ADDR,
		// "getMobileAppType", new Object[]{}));
		News news = null;
		if (ShowTypeEnum.IMAGE_SHOW.getKey().equals(type)) {

			// return "index_pic";
		} else if (ShowTypeEnum.DETAIL_SHOW.getKey().equals(type) && !StringUtil.isEmpty(newsList)) {

			try {
				JSONObject json = JSONObject.fromObject(JSONUtils
						.xml2json(newsList));
				if (json.containsKey("news")) {
					String id = "";
					try {
						id = json.getJSONObject("news").getString("id");
					} catch (JSONException e) {
						JSONArray newsArray = json.getJSONArray("news");
						if (newsArray.size() > 0) {
							JSONObject obj = (JSONObject) newsArray.get(0);
							id = obj.getString("id");
						}

					}
					NewsQuery query = new NewsQuery();
					if (!StringUtil.isEmpty(id)) {
						query.setId(id);
						news = newsService.findById(query);
					}
				}
			} catch (Exception e) {

				e.printStackTrace();
			}

			// return "index_detail";
		} else {
			this.getValueStack().set("listType", listType);
			// return "index";
		}
		this.getValueStack().set("news", JSONUtils.obj2json(news));
		this.getValueStack().set("title",title);
		if ("jit".equals(school)) {
			return "index_jit";
		}*/
		return "index";

	}

	public String list() {
		getResponse().setHeader("Access-Control-Allow-Origin", "*");
		String newsList = "";
		if ("TT".equals(catalogId) || StringUtil.isEmpty(catalogId)) {
			newsList = getNewsList("", start, size);
		} else {
			newsList = getNewsList(catalogId, start, size);
		}
		/*if (ShowTypeEnum.DETAIL_SHOW.getKey().equals(type)) {
			News news = null;
			try {
				JSONObject json = JSONObject.fromObject(JSONUtils
						.xml2json(newsList));
				if (json.containsKey("news")) {
					String id = "";
					try {
						id = json.getJSONObject("news").getString("id");
					} catch (JSONException e) {
						JSONArray newsArray = json.getJSONArray("news");
						if (newsArray.size() > 0) {
							JSONObject obj = (JSONObject) newsArray.get(0);
							id = obj.getString("id");
						}

					}
					NewsQuery query = new NewsQuery();
					if (!StringUtil.isEmpty(id)) {
						query.setId(id);
						news = newsService.findById(query);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.getValueStack().set("data", JSONUtils.obj2json(news));
			return "data";
		}*/
		this.getValueStack().set("data", JSONUtils.xml2json(newsList));
		return "data";
	}

	/**
	 * 应用中心Action
	 * 
	 * @return
	 */
	public String appCenter() {
		
		if(getUser() == null){
			//start 应付演示
			LoginModel model = new LoginModel();
			model.setYhm("1053");
			ILoginService loginService = (ILoginService) SpringHolder.getBean("loginService");
			User user = loginService.cxYhxx(model);
			HttpSession session = getSession();
			session.setAttribute(USER_INFO_KEY, user);
		}
		
		//this.initRoles(user);
		if (getSession().getAttribute(USER_INFO) == null) {
			this.getValueStack().set("module", "appCenter");
			// return "login";
		}
		// String apps = getCommon();
		String commonApps = getAllServiceByUser();
		this.getValueStack().set("apps", JSONUtils.xml2json(commonApps));
		// this.getValueStack().set("commonApps",
		// JSONUtils.xml2json(commonApps));
		List<Business> business = mobileCommonService
				.getAllXtYwByUser(getSessionUser());
		this.getValueStack().set("business", JSONUtils.obj2json2(business));
		return "appCenter";
	}
	//获取教务的url访问
	public String getjwURL(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String url = request.getParameter("url");
		Map<String, String> map = getMapParam(url);//新增
		String urlprocode = map.get("procode");
		String urlchose   = map.get("choice");
		String urluid     = map.get("uid");
		String jwurl = null; 
		  if(urlprocode != null && (urlprocode.equals("002") || urlprocode.equals("009"))){
			    //jwuid=urluid.substring(0,urluid.indexOf("&role=XS"));
			    //jwuid = map.get("role"); 
			    Date date = new Date();
				long time =date.getTime();
				String time1 =String.valueOf(time);
				String jwtime=time1.substring(0,10);
				String publickey = MD5UtilInAndroid.getMD5ofStr(urlprocode+urlchose+urluid+"DAFF8EA19E6BAC86E040007F01004EA"+jwtime);
				jwurl=url+"&"+"key="+publickey+"&"+"time="+jwtime;
		 }
	    this.getValueStack().set("data", jwurl);
		return "data";
	}
	
	public String getxgURL(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String url = request.getParameter("url");
		
		String result = null;
		try {
		//获取TGC认证票据
		String endpointURL = WebServiceConf.WEBSERVICE_CA_MANAGE;
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			// call.setOperationName("getSzdacx" );//设置对应方法 (原
			// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
			// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
			QName opAddEntry = new QName(
					WebServiceConf.WEBSERVICE_NAMESPACE_MANAGE,
					"getTGC");
			call.setOperationName(opAddEntry);
			result = (String) call.invoke(new Object[] { getSessionUser(), "123123." });
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("TGC认证票据Erro" + e); // 测试
		}
		
		String xgurl =  url+"&tgc="+result;
		this.getValueStack().set("data", xgurl);
		return "data";
		
	}
	
	/**
	 * 对url进行解析，解析成map，如[<procode,value>,<choice,value>,<uid,value>]
	 * @param url
	 * @return
	 */
	public  Map<String, String> getMapParam(String url){
		int index = url.indexOf("?");
		int index2 = 0;
		if ( url == null || url == "" || index == -1 ) {
			return null;
		}
		
		Map<String,String> params = new HashMap<String, String>();
		url = url.substring(index + 1);
		String[] paramArray = url.split("&");
		for(String param : paramArray){
			index2 = param.indexOf("=");
			if (index2 <= 0) {
				continue;
			}
			params.put(param.substring(0,index2), param.substring(index2 + 1));
			//System.out.println("----key----" + param.substring(0,index2));
			//System.out.println("---value---" + param.substring(index2 + 1));
		}
		
		return params;
	}

	/**
	 * 筛选服务
	 * 
	 * @return
	 */
	public String brushService() {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IMobileLoginXMLService.class);
		factory.setAddress(Config.NEW_MOBILE_ADDR);
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IMobileLoginXMLService service = (IMobileLoginXMLService) factory
				.create();
		String ret = "";
		String key = Authentication.sign();
		String user = getSessionUser();
		String apptoken = getApptoken(user);
		try {
			if ("common".equals(serviceCode)) {
					ret = service.Commonfunction(CodeUtil.encode(user, apptoken), 
							CodeUtil.encode(key, apptoken),apptoken);
			} else if ("all".equals(serviceCode)) {
				ret = service.CommonAllFunction(CodeUtil.encode(user, apptoken), 
						CodeUtil.encode(key, apptoken),apptoken);
			} else {
				ret = service.CommonBrushFunction(CodeUtil.encode(user, apptoken), CodeUtil.encode(serviceCode, apptoken),
						CodeUtil.encode(Authentication.sign(), apptoken),apptoken);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.getValueStack().set("data", JSONUtils.xml2json(ret));
		return "data";
	}

	private String getApptoken(String user) {
		ILoginService loginService = (ILoginService) SpringHolder.getBean("loginService");
		String apptoken = loginService.getStrKeyByYhm(user);
		return apptoken;
	}

	/**
	 * 订阅中心
	 * 
	 * @return
	 */
	public String subscribe() {
		String subServices = getCommonByUser();
		String otherServices = getCommonOtherServices();
		this.getValueStack()
				.set("subServices", JSONUtils.xml2json(subServices));
		this.getValueStack().set("otherServices",
				JSONUtils.xml2json(otherServices));
		return "subscribe";
	}

	/**
	 * 保存订阅修改
	 * 
	 * @return
	 */
	public String saveSubscribe() {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IMobileLoginXMLService.class);
		factory.setAddress(Config.NEW_MOBILE_ADDR);
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IMobileLoginXMLService service = (IMobileLoginXMLService) factory
				.create();
		String apptoken = getApptoken(getSessionUser());
		try {
			String xmlResult = service.SubmitCommonFunction(getSessionUser(),
					serviceCode,apptoken);
			JSONObject obj = JSONObject.fromObject(JSONUtils
					.xml2json(xmlResult));
			if (obj.containsKey("code")) {
				if ("201".equals(obj.get("code"))) {
					this.getValueStack().set("data", "保存成功");
				}
			} else {
				this.getValueStack().set("data", "保存失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.getValueStack().set("data", "保存失败");
		}
		return "data";
	}

	/**
	 * 内容页Action
	 * 
	 * @return
	 */
	public String content() {
		NewsQuery query = new NewsQuery();
		News news = null;
		if (!StringUtil.isEmpty(newsId)) {
			query.setId(newsId);
			news = newsService.findById(query);
			this.getValueStack().set("type", news.getShowType());
			if(!StringUtil.isEmpty(news.getContent())){
				news.setContent(news.getContent().replaceAll("\n", ""));
				String contentHtml = news.getContent();
				contentHtml = contentHtml.replaceAll("&lt;","<");
				contentHtml = contentHtml.replaceAll("&quot;","'");
				contentHtml = contentHtml.replaceAll("&gt;",">");
				contentHtml = contentHtml.replaceAll("&amp;","&");
				news.setContent(contentHtml);
			}
		}
		this.getValueStack().set("news", news);
		this.getValueStack().set("title", news.getTitle());

		return "content";
	}

	/**
	 * 我的门户Action
	 * 
	 * @return
	 */
	public String portal() {
		
		if(getUser() == null){
			//start 应付演示
			LoginModel model = new LoginModel();
			model.setYhm("1053");
			ILoginService loginService = (ILoginService) SpringHolder.getBean("loginService");
			User user = loginService.cxYhxx(model);
			HttpSession session = getSession();
			session.setAttribute(USER_INFO_KEY, user);
		}
		
		String portalInfo = getAllPortalByUser();
		this.getValueStack().set("portalInfo", JSONUtils.xml2json(portalInfo));
			
		if (getSession().getAttribute(USER_INFO) == null) {
			this.getValueStack().set("module", "portal");
			// return "login";
		}
		this.getValueStack().set("user", getUser());
		return "portal";
	}

	/**
	 * 根据类别获取资讯Action
	 * 
	 * @return
	 */
	public String news() {
		String str = getNewsList(catalogId, start, size);
		this.getValueStack().set("data", JSONUtils.xml2json(str));
		return "data";
	}

	/**
	 * 登录Action
	 * 
	 * @return
	 */
	public String login() {
		return "login";
	}

	/**
	 * 处理登陆Action
	 * 
	 * @return
	 */
	public String handler() {
		System.out.println("username="+username+"password"+password+"module="+module);
		if (checkLogin(getSession(), username, password)) {
			if ("appCenter".equals(module)) {
				System.out.println("appCenter()");
				return appCenter();
			} else if ("portal".equals(module)) {
				System.out.println("portal()");
				return portal();
			}
		}
		return login();
	}

	public boolean checkLogin(HttpSession session, String userName,
			String password) {
		System.out.println("checkLogin");
		if(!StringUtil.isEmpty(com.common.Config.getString("webservice.host.mh"))){
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(IMobileLoginXMLService.class);
			factory.setAddress(Config.NEW_MOBILE_ADDR);
			factory.getInInterceptors().add(new LoggingInInterceptor());
			factory.getOutInterceptors().add(new LoggingOutInterceptor());
			
			IMobileLoginXMLService service = (IMobileLoginXMLService) factory
			.create();
			// String str = service.login(userName, password,
			// Authentication.sign());
			String str = service.Login(userName, password, Authentication.sign(),null);
			JSONObject json = null;
			try {
				json = JSONObject.fromObject(JSONUtils.xml2json(str));
				if (json.containsKey("mh")) {
					String user = json.getJSONObject("mh").getString("yhm");
					session.setAttribute(USER_INFO, user);
					return true;
				} else if (json.containsKey("code")) {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		LoginModel model = new LoginModel();
		model.setYhm(userName);
		model.setMm(password);
		if(!StringUtil.isEmpty(com.common.Config.getString("webservice.host.mh")) || 
				!StringUtil.isEmpty(com.common.Config.getString("qymm"))){
			System.out.println("-----------enter setmm null------------");
			model.setMm(null);
			
		}
		ILoginService loginServiceImpl = (ILoginService)SpringHolder.getBean("loginService");
		User user = loginServiceImpl.cxYhxx(model);
		session.setAttribute(USER_INFO_KEY, user);
		System.out.println("user="+user);
		if(user != null) return true;
		
		return false;
	}

	/**
	 * 获取资讯列表
	 * 
	 * @param catalog
	 *            类别
	 * @param start
	 *            页码
	 * @param size
	 *            数量
	 * @return
	 */
	private String getNewsList(String catalog, String start, String size) {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IMobileLoginXMLService.class);
		factory.setAddress(Config.NEW_MOBILE_ADDR);
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IMobileLoginXMLService service = (IMobileLoginXMLService) factory
				.create();

		String str = service.getNewsList(catalog, start, size);

		return str;
	}

	/**
	 * 获取资讯分类列表
	 * 
	 * @return
	 */
	private String getMenu() {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IMobileLoginXMLService.class);
		factory.setAddress(Config.NEW_MOBILE_ADDR);
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IMobileLoginXMLService service = (IMobileLoginXMLService) factory
				.create();

		String str = service
				.getNewsTab(getSessionUser(), Authentication.sign());

		return str;
	}

	/**
	 * 获取用户所有服务
	 * 
	 * @return
	 */
	private String getCommon() {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IMobileLoginXMLService.class);
		factory.setAddress(Config.NEW_MOBILE_ADDR);
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IMobileLoginXMLService service = (IMobileLoginXMLService) factory
				.create();
		String apptoken = getApptoken(getSessionUser());
		String str = service.CommonAllFunction(getSessionUser(),
				Authentication.sign(),apptoken);

		return str;
	}

	/**
	 * 获取用户未订阅的服务
	 * 
	 * @return
	 */
	private String getCommonOtherServices() {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IMobileLoginXMLService.class);
		factory.setAddress(Config.NEW_MOBILE_ADDR);
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IMobileLoginXMLService service = (IMobileLoginXMLService) factory
				.create();
		String apptoken = getApptoken(getSessionUser());
		String str = service.CommonOtherFunction(getSessionUser(),
				Authentication.sign(),apptoken);

		return str;
	}
	

	/**
	 * 获取用户常用服务
	 * 
	 * @return
	 */
	private String getCommonByUser() {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IMobileLoginXMLService.class);
		factory.setAddress(Config.NEW_MOBILE_ADDR);
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IMobileLoginXMLService service = (IMobileLoginXMLService) factory
				.create();
		String apptoken = getApptoken(getSessionUser());
		// String str = service.commonfunction("255", Authentication.sign());
		String str = null;
		try {
			str = service.Commonfunction(CodeUtil.encode(getSessionUser(), apptoken),
					CodeUtil.encode(Authentication.sign(), apptoken), apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return str;
	}
	
	/**
	 * 获取用户所有服务
	 * 
	 * @return
	 */
	private String getAllServiceByUser() {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IMobileLoginXMLService.class);
		factory.setAddress(Config.NEW_MOBILE_ADDR);
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IMobileLoginXMLService service = (IMobileLoginXMLService) factory
				.create();
		String apptoken = getApptoken(getSessionUser());
		// String str = service.commonfunction("255", Authentication.sign());
		String str = null;
		try {
			str = service.CommonAllFunction(CodeUtil.encode(getSessionUser(), apptoken),
					CodeUtil.encode(Authentication.sign(), apptoken), apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return str;
	}
	
	/**
	 * 获取用户所有门户选项
	 * @return
	 */
	private String getAllPortalByUser(){
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IMobileLoginXMLService.class);
		factory.setAddress(Config.NEW_MOBILE_ADDR);
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IMobileLoginXMLService service = (IMobileLoginXMLService) factory
				.create();
		String apptoken = getApptoken(getSessionUser());
		// String str = service.commonfunction("255", Authentication.sign());
		String str = null;
		try {
			str = service.myPortalFunction(CodeUtil.encode(getSessionUser(), apptoken),
					CodeUtil.encode(Authentication.sign(), apptoken), apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return str;
	}

	private String getAllYWXTByUser(String user) {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IMobileLoginXMLService.class);
		factory.setAddress(Config.NEW_MOBILE_ADDR);
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IMobileLoginXMLService service = (IMobileLoginXMLService) factory
				.create();
		String apptoken = getApptoken(getSessionUser());
		String str = service.Commonfunction(getSessionUser(),
				Authentication.sign(), apptoken);

		return str;
	}
	
	public String getNewsContent(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String catalog = request.getParameter("catalog");
		String start   = StringUtil.isEmpty(request.getParameter("start")) ? 
							"1" : request.getParameter("start");
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IMobileLoginXMLService.class);
		factory.setAddress(Config.NEW_MOBILE_ADDR);
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IMobileLoginXMLService service = (IMobileLoginXMLService) factory
				.create();
		String content = service.getNewsList(catalog, start, "10");
		String temp = JSONUtils.xml2json(content);
		Map<String, Object> data = new HashMap<String, Object>();
        data.put("success", true);
        data.put("content", JSONUtils.xml2json(content));
        getValueStack().set(DATA, data);
		return DATA;
	}

	/**
	 * 增加用户服务
	 */
	public String insertfuwu() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String fwbm = request.getParameter("fwbm");
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IMobileLoginXMLService.class);
		factory.setAddress(Config.NEW_MOBILE_ADDR);
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IMobileLoginXMLService service = (IMobileLoginXMLService) factory
				.create();
		String apptoken = getApptoken(getSessionUser());
		try {
			// String str = service.submitCommonFunction("700429", fwbm);
			String str = service.SubmitCommonFunction(getSessionUser(), fwbm, apptoken);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			this.setErrorMessage("操作失败，请重新操作！");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
		this.setSuccessMessage("添加服务成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	/**
	 * 删除用户服务
	 */
	public String deletefuwu() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String fwbm = request.getParameter("fwbm");
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IMobileLoginXMLService.class);
		factory.setAddress(Config.NEW_MOBILE_ADDR);
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		IMobileLoginXMLService service = (IMobileLoginXMLService) factory
				.create();

		try {
			// String str = service.deletefuwu("700429", Authentication.sign(),
			// fwbm);
			String str = "";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			this.setErrorMessage("操作失败，请重新操作！");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}

		this.setSuccessMessage("删除服务成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	private String getSessionUser() {
		User user = getUser();
		if (user != null) {
			return user.getYhm();
		} else {
			return null;
		}
	}

	/**
	 * 拖拽pai
	 */
}
