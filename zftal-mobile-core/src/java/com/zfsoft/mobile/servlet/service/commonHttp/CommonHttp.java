package com.zfsoft.mobile.servlet.service.commonHttp;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.common.Config;
import com.common.WebServiceConf;
import com.google.gson.Gson;
import com.jw.org.tempuri.ILogin;
import com.mh.CXFServe.service.ICaService;
import com.newmobile.login.CXFService.IWSSerServicePortType;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Userinfos;
import com.taobao.api.request.OpenimUsersAddRequest;
import com.taobao.api.request.OpenimUsersGetRequest;
import com.taobao.api.response.OpenimUsersAddResponse;
import com.taobao.api.response.OpenimUsersGetResponse;
import com.util.MiddleWareUtil;
import com.util.WebServiceUtil;
import com.zfsoft.common.log.User;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.dao.entities.LoginModel;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.finfo.util.FormInfoUtil;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.infoclass.query.InfoPropertyQuery;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.IInfoPropertyService;
import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.hrm.file.util.UploadFileUtil;
import com.zfsoft.mobile.MD5Util.MD5Util;
import com.zfsoft.mobile.basedata.service.IBaseDataService;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.common.utils.Authentication;
import com.zfsoft.mobile.common.utils.MobileSystemHolder;
import com.zfsoft.mobile.services.entity.GaoDeMaoEntity;
import com.zfsoft.mobile.services.entity.Point;
import com.zfsoft.mobile.services.service.IGaoDeMapService;
import com.zfsoft.mobile.servlet.entity.FailureEntity;
import com.zfsoft.mobile.servlet.entity.LoginEntity;
import com.zfsoft.mobile.servlet.entity.MyPortalEntity;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.mobile.webservices.entity.InformationListEntity;
import com.zfsoft.mobile.webservices.entity.ScalendarEntity;
import com.zfsoft.service.svcinterface.ILoginService;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;

public class CommonHttp {
	private static Logger logger = Logger.getLogger(CommonHttp.class);
	private final String infromation=Config.getString("mobile.infromation");
	
	
	
	
	/**
	 * 对ca认证过来的信息进行xml解析判断是否登录成功
	 * @throws DocumentException 
	 */
	public boolean containErrorCode(String xml) throws DocumentException{
		Document document = DocumentHelper.parseText(xml);
        Element elementTemplate = document.getRootElement();  
        Element ResultInfo = (Element)elementTemplate.selectSingleNode("//ResultInfo"); 
        Element code = (Element)elementTemplate.selectSingleNode("//code"); 

		if(ResultInfo != null && code != null){
			String errorCode = elementTemplate.elementText("code");
			if(errorCode != null && !errorCode.equals(""))
				return true;
		}
		
		return false;
	}
	
	/**
	 * 对ca认证过来的信息进行xml解析map返回
	 * @throws DocumentException 
	 */
	public Map<String, String> getMapValue(String xml) throws DocumentException{
		Map<String, String> map = new HashMap<String, String>();
		Document document = DocumentHelper.parseText(xml);
        Element elementTemplate = document.getRootElement();  
        Element bm = (Element)elementTemplate.selectSingleNode("//BM"); 

		if(bm != null && bm.getText() != null){
				map.put("BM", bm.getText());
		}
		
		return map;
	}
	
	public String personDocumentInformation(String userName, String informationName,
			String informationId, String strKey,String apptoken) {
		List<List<Map<String,String>>> totalList = null ;
		Gson gson = new Gson();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String sizes = new String(request.getParameter("size").getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		if(StringUtil.isEmpty(userName) || StringUtil.isEmpty(strKey) || StringUtil.isEmpty(informationId)
				|| StringUtil.isEmpty(apptoken) || StringUtil.isEmpty(informationName)){
			ResultEntity result = new ResultEntity<List<List<Map<String,String>>>>(0, "参数传值出错！", totalList);
			return gson.toJson(result);
		}
		if(!ApptokenUtils.compare(userName, apptoken)){
			ResultEntity result = new ResultEntity<List<List<Map<String,String>>>>(2, "app_token error!", totalList);
			return gson.toJson(result);
		}
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
			informationName = CodeUtil.decode(informationName, apptoken);
			informationId  	= CodeUtil.decode(informationId, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("获取数字档案具体详情信息类内容："+"userName="+userName+"informationName="+informationName
					+"informationId="+informationId+"strKey="+strKey);
			}
		
		
		LoginModel model = new LoginModel();
		model.setYhm(userName);
		ILoginService loginService = (ILoginService) SpringHolder.getBean("loginService");
		User user = loginService.cxYhxx(model);
		InfoClass clazz = FormInfoUtil.reFillPropertyByRole(user.getJsdms(), informationId);
		InfoPropertyQuery propertyQuery = new InfoPropertyQuery();
		propertyQuery.setClassId( clazz.getGuid() );
		IInfoPropertyService infoPropertyService = (IInfoPropertyService) SpringHolder.getBean("baseInfoClassPropertyService");
		List<InfoProperty> properties = infoPropertyService.getInfoProperties( propertyQuery );
		DynaBeanQuery query = new DynaBeanQuery();
		query.setClazz(clazz);
		String express = " t.gh = '" + informationName + "'";
        query.setExpress(express);
        query.setPerPageSize(1);
        IBaseDataService baseDataService = (IBaseDataService) SpringHolder.getBean("baseDataService");
        List<DynaBean> list = baseDataService.getSynchronizedBaseData(query);
        totalList= new ArrayList<List<Map<String,String>>>();
        List<Map<String,String>> dataList;
        Map<String,String> dataMap ;
        if(list != null && list.size() > 0){
        	for(DynaBean dynaBean : list){
        		dataList= new ArrayList<Map<String,String>>();
        		Map<String, Object> map = dynaBean.getValues();
        		for(InfoProperty property : properties){
            		if(property.getViewable()){
            			for(Map.Entry<String, Object> entry: map.entrySet()) {
            				if(property.getFieldName().equals(entry.getKey())){
            					dataMap = new HashMap<String, String>();
            					dataMap.put(property.getDescription(), entry.getValue().toString());
            					dataList.add(dataMap);
            				}
            			}
            		}
            	}
        		totalList.add(dataList);
        	}
        	
        }
		
		if(infromation.equals("0")){
		logger.error("获取数字档案具体详情信息类内容："+str);
		}
		ResultEntity result = new ResultEntity<List<List<Map<String,String>>>>(1, "成功", totalList);
		return gson.toJson(result);
	}
	
	public String personDocumentInformationList(String userName, String strKey,String apptoken) {
		List<InformationListEntity> informationList = null;
		Gson gson = new Gson();
		if(StringUtil.isEmpty(userName) || StringUtil.isEmpty(strKey) || StringUtil.isEmpty(apptoken)){
			ResultEntity result = new ResultEntity<List<InformationListEntity>>(0, "参数传值出错！", informationList);
			return gson.toJson(result);
		}
		if(!ApptokenUtils.compare(userName, apptoken)){
			ResultEntity result = new ResultEntity<List<InformationListEntity>>(2, "app_token error!", informationList);
			return gson.toJson(result);
		}
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("获取数字档案信息类列表："+"userName="+userName);
			}
		IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
		informationList = mobileCommonService.getAllInformationList();
        if(informationList != null && informationList.size() > 0){
        	for (int i = 0; i < informationList.size(); i++) {
        		String url = getImageHost();
        		boolean check = false;
        		try {
        			check = mobileCommonService.checkImage(
        					informationList.get(i).getInformationico(),
        					null);
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        		
        		if (check){
        			informationList.get(i).setInformationico(url + informationList.get(i).getInformationico());
        		}
        		else{
        			informationList.get(i).setInformationico(url + "upload/default_image.jpg");
        		}
        		if(i != 0){
        			informationList.get(i).setInformationList(new ArrayList<List<Map<String,String>>>());
        		}
			}
        	
        	List<List<Map<String,String>>> totalList = new ArrayList<List<Map<String,String>>>() ;
        	LoginModel model = new LoginModel();
    		model.setYhm(userName);
    		ILoginService loginService = (ILoginService) SpringHolder.getBean("loginService");
    		User user = loginService.cxYhxx(model);
    		InfoClass clazz = FormInfoUtil.reFillPropertyByRole(user.getJsdms(), informationList.get(0).getInformationid());
    		InfoPropertyQuery propertyQuery = new InfoPropertyQuery();
    		propertyQuery.setClassId( clazz.getGuid() );
    		IInfoPropertyService infoPropertyService = (IInfoPropertyService) SpringHolder.getBean("baseInfoClassPropertyService");
    		List<InfoProperty> properties = infoPropertyService.getInfoProperties( propertyQuery );
    		DynaBeanQuery query = new DynaBeanQuery();
    		query.setClazz(clazz);
    		String express = " t.gh = '" + userName + "'";
            query.setExpress(express);
            query.setPerPageSize(1);
            IBaseDataService baseDataService = (IBaseDataService) SpringHolder.getBean("baseDataService");
            List<DynaBean> list = baseDataService.getSynchronizedBaseData(query);
            List<Map<String,String>> dataList;
            Map<String,String> dataMap ;
            if(list != null && list.size() > 0){
            	for(DynaBean dynaBean : list){
            		dataList= new ArrayList<Map<String,String>>();
            		Map<String, Object> map = dynaBean.getValues();
            		for(InfoProperty property : properties){
                		if(property.getViewable()){
                			for(Map.Entry<String, Object> entry: map.entrySet()) {
                				if(property.getFieldName().equals(entry.getKey())){
                					dataMap = new HashMap<String, String>();
                					dataMap.put(property.getDescription(), entry.getValue().toString());
                					dataList.add(dataMap);
                				}
                			}
                		}
                	}
            		totalList.add(dataList);
            	}
            	
            }
            informationList.get(0).setInformationList(totalList);
        	
        }
		
        if(infromation.equals("0")){
        	logger.error("获取数字档案信息类列表："+str);
        }
        ResultEntity result = new ResultEntity<List<InformationListEntity>>(1, "成功", informationList);
		return gson.toJson(result);
	}
	
	
	/**
	 * app端进入app引导页调用此接口做安装统计
	 */
	public String installsCount() {
		Gson gson = new Gson();
		if(infromation.equals("0")){
			logger.error("引导页做安装统计：");
		}
		ILoginService loginServiceImpl = (ILoginService)SpringHolder.getBean("loginService");
		loginServiceImpl.installsCount();
		ResultEntity result = new ResultEntity<String>(1, "成功", "");
		return gson.toJson(result);
	}
	
	/**
	 * 获取校历数据 
	 * @param ymd
	 * @param apptoken
	 * @return
	 */
	public String getTermWeek(String ymd,String apptoken ) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			ymd       = CodeUtil.decode(ymd, apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		Gson gson = new Gson();
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
		return gson.toJson(entityList);
	}
	
	/**
	 * 百度地图数据获取
	 * @return
	 */
	public String getMapList() {
		Gson gson = new Gson();
		IGaoDeMapService gaoDeMapService = (IGaoDeMapService) SpringHolder.getBean("gaoDeMapService");
		GaoDeMaoEntity query = new GaoDeMaoEntity();
		List<GaoDeMaoEntity> mapList = gaoDeMapService.getList(query);
		List<Point> pointList = null;
		Point pointTemp = null;
		for (int i = 0; i < mapList.size(); i++) {
			pointList = new ArrayList<Point>();
			String[] longList= mapList.get(i).getLongitude().split(",");
			String[] latiList= mapList.get(i).getLatitude().split(",");
			for (int j = 0; j < latiList.length; j++) {
				pointTemp = new Point(latiList[j], longList[j]);
				pointList.add(pointTemp);
			}
			mapList.get(i).setPointList(pointList);
		}
		try {
			
		if(infromation.equals("0")){
			logger.error("地图数据获取：");
			}
		for(GaoDeMaoEntity entity : mapList){
			if(infromation.equals("0")){
				logger.error("地图数据获取entity---"+entity);
				}
		}
		if(infromation.equals("0")){
			logger.error("地图数据获取end---");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(mapList);
	}
	
	/**
	 * 提交建议
	 * @param userName
	 * @param suggestion
	 * @param strKey
	 * @return
	 */
	public String submitSuggestion(String userName, String suggestion,
			String strKey) {
		Gson gson = new Gson();
		String str = "";
		if(infromation.equals("0")){
			logger.error("意见反馈："+"userName="+userName+",suggestion="+suggestion);
			}
		try {
		Map<String, String> param = new HashMap<String, String>();
		param.put("userId", userName);
		param.put("suggestion", suggestion);
		IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
		mobileCommonService.submitSuggestion(param);
		str="{\"message\":\"操作成功\"}";
			if(infromation.equals("0")){
				logger.error("message:"+str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(infromation.equals("0")){
			logger.error("意见反馈end："+str);
			}
		return str;
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
	
	public String getMyPicturePath(String userName,String apptoken){
		Gson gson = new Gson();
		String str=null;
		if(!ApptokenUtils.compare(userName, apptoken)){
			FailureEntity failure = new FailureEntity();
			failure.failure = "app_token error";
			return gson.toJson(failure);
		}
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
		if(infromation.equals("0")){
			logger.error("获取我的头像路径："+"userName="+userName+",apptoken="+apptoken);
			}
		IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
		List<ImageDB> imageList = mobileCommonService.getMyPicture(userName);
		ImageDB image = imageList != null && imageList.size() > 0 ? imageList.get(0) : null;
		if(image == null){
			FailureEntity failure = new FailureEntity();
			failure.failure = "数据库图片不存在，路径也不存在！";
			return gson.toJson(failure);
		}
			
		String path = image.getPath();
		byte[] content = image.getFileContent();
		String headname = image.getFileName();
		String filename = StringUtil.isEmpty(headname) ? userName+"headPicture" : headname;
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		if(content == null && StringUtil.isEmpty(path)){
			FailureEntity failure = new FailureEntity();
			failure.failure = "数据库图片不存在，路径也不存在！";
			return gson.toJson(failure);
		}else{
			String pathFile = BaseHolder.getPropertiesValue("MyPicture","headPicture");
			String pathurl = request.getSession().getServletContext().getRealPath("/") + pathFile;
			File newFile = new File(pathurl);
			if (!newFile.exists()) {
				newFile.mkdir();
			}
			String url = request.getSession().getServletContext().getRealPath("/") + path;
			url = url.replace("\\", "/");
			File outFile = new File(url);
			if (!outFile.exists()) {
					try {
						outFile.createNewFile();
					
						if(content == null){
							FailureEntity failure = new FailureEntity();
							failure.failure = "数据库图片不存在，路径也不存在！";
							return gson.toJson(failure);
						}
						ImageIO.setUseCache(false);
						ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);
						BufferedImage newImage = ImageIO.read(byteArrayInputStream);
						ImageIO.write(newImage, UploadFileUtil.checkedFileName(filename), outFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
			} 
			str="{\"path\":\""+getImageHost()+path+"\"}";
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 登录接口
	 * @param userName
	 * @param passWord
	 * @param sign
	 * @return
	 */
	public String login(String userName, String passWord, String sign){
		Gson gson = new Gson();
		LoginEntity entity = new LoginEntity();
		if(StringUtil.isEmpty(userName) || StringUtil.isEmpty(passWord)
				|| StringUtil.isEmpty(sign)){
			ResultEntity result = new ResultEntity<LoginEntity>(0, "参数传值出错！", new LoginEntity());
			return gson.toJson(result);
		}
		try {
			
		
		if(infromation.equals("0")){
			logger.error("获取登陆接口："+"userName="+userName+",passWord="+passWord+",strKey="+sign);
			}
		StringBuilder sb = new StringBuilder();
		if (Authentication.authenticate(sign)) {
			LoginModel model = new LoginModel();
			model.setYhm(userName);
			model.setMm(passWord);
			Map<String, String> map = new HashMap<String, String>();
			
			
			if(!StringUtil.isEmpty(Config.getString("webservice.host.mh"))
					&& StringUtil.isEmpty(Config.getString("qymm"))){
				String zfxml = "";
				try {
					JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
					factory.setServiceClass(ICaService.class);
					factory.setAddress(WebServiceConf.SERVICE_MHSERVICE);//接口地址
					factory.getInInterceptors().add(new LoggingInInterceptor());
					factory.getOutInterceptors().add(new LoggingOutInterceptor());
					
					ICaService service = (ICaService) factory.create();
					
					String Xtmdlst = Config.getString("BusinessSystemNum"); 
					
					
					zfxml =service.getTicket2(userName, passWord, Xtmdlst, "");
					
				} catch (Exception e) {
					System.out.println(e);
					ResultEntity result = new ResultEntity<LoginEntity>(0, "ca接口登录异常！", new LoginEntity());
					return gson.toJson(result);
				}
					
					
					boolean canLogin = false;
					try {
						canLogin = containErrorCode(zfxml);
					} catch (DocumentException e) {
						e.printStackTrace();
					}
					if(canLogin){
						ResultEntity result = new ResultEntity<LoginEntity>(0, "用户名或密码不正确！", new LoginEntity());
						return gson.toJson(result);
					}else{
						try {
							map = getMapValue(zfxml);//获取ca认证返回过来的值
						} catch (DocumentException e) {
							e.printStackTrace();
						}
					}
					
					
					
					
					
				}
				logger.error("--------setMm(null)---------");
				if(!StringUtil.isEmpty(Config.getString("webservice.host.mh")) || 
						!StringUtil.isEmpty(Config.getString("qymm"))){
					logger.error("-----------enter setmm null------------");
					model.setMm(null);
					
				}
				ILoginService loginService = (ILoginService)SpringHolder.getBean("loginService");
				User user = loginService.cxYhxx(model);
				
				YhglModel yhglModel = new YhglModel();
				yhglModel.setZgh(userName);
				String keyCode = null;
				try {
					keyCode = MD5Util.md5Encode(
											userName + 
											Config.getString("key", "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS") + 
											System.currentTimeMillis()
										);
					if(StringUtils.isEmpty(keyCode))
						throw new Exception("产生秘钥异常!");
				} catch (Exception e) {
					e.printStackTrace();
				}
				yhglModel.setStrKey(keyCode);
				
				loginService.updateStrKey(yhglModel);
				
				
				
				
				if (user != null) {
					if(!StringUtil.isEmpty(user.getSfqy()) && !user.getSfqy().equals("1")){
						ResultEntity result = new ResultEntity<LoginEntity>(0, "用户没有被启用！", new LoginEntity());
						return gson.toJson(result);
					}
					
//					if(!StringUtil.isEmpty(user.getErrNum()) && Integer.valueOf(user.getErrNum())>=10){
//						ResultEntity result = new ResultEntity<LoginEntity>(0, "用户被锁定！", new LoginEntity());
//						return gson.toJson(result);
//					}
					
					
					
					String dqxnxq = "";
					String loginXML = "";
					if(!StringUtil.isEmpty(Config.getString("webservice.host.jw"))){
						try{
							final String AND = "&";
							final String alone = "N";
							String parameterList = userName + AND + passWord + AND + alone;
							String jwKey = MiddleWareUtil.getJWSign(parameterList);
							ILogin login = (ILogin) WebServiceUtil.createFactoryJw(
									WebServiceConf.SERVICE_JWSERVICE_LOGIN, ILogin.class, "Login")
									.create();
							loginXML = login.login(parameterList, user.getYhlx().equals("")||user.getYhlx().equals("teacher") ? "js" : "xs", jwKey);
							try {
								Document document = DocumentHelper.parseText(loginXML);
								Element elementTemplate = document.getRootElement();  
								Element xq = (Element)elementTemplate.selectSingleNode("//dqxnxq"); 
								
								if(xq != null && xq.getText() != null){
									dqxnxq = xq.getText();
								}
							} catch (DocumentException e) {
								
								e.printStackTrace();
							} 
						} catch (Exception e) {
							
						}
					}
					String appName = MobileSystemHolder.getPropertiesValue("app_name");
					entity.setName(user.getXm());
					if ("student".equals(user.getYhlx())) {
						entity.setRole("XS");
					} else {
						entity.setRole("JS");
					}
					if(!StringUtil.isEmpty(Config.getString("webservice.host.mh"))){
						String department = map.containsKey("BM") ? map.get("BM") : "";
						entity.setDepartment(department);
					}else{
						entity.setDepartment(user.getBmmc());
					}
					entity.setSchoolName("");
					entity.setClassName("");
					entity.setGradeName("");
					entity.setUserId(user.getYhm());
					entity.setAppname(appName);
					sb.append("<yhm>").append(user.getYhm()).append("</yhm>");
					entity.setNowSchoolYearTerm(dqxnxq);
					entity.setApp_token(keyCode);
				}else{
					ResultEntity result = new ResultEntity<LoginEntity>(0, "后台显示用户为空，但ca登录正确！", entity);
					return gson.toJson(result);
				}
			if(infromation.equals("0")){
				logger.error("登陆接口返回为："+entity);
				}	
			//ArrayList<LoginEntity> list = new ArrayList<LoginEntity>(); 
			//list.add(entity);
			if(!StringUtil.isEmpty(Config.getString("alibaichuan"))
					&& Config.getString("alibaichuan").equals("yes")){
				//signUp(userName, user.getXm());//阿里百川注册帐号，为移动端IM模块服务
			}
			
			//我的头像读取start
			String headPicturePath = null;
			IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
			List<ImageDB> imageList = mobileCommonService.getMyPicture(userName);
			ImageDB image = imageList != null && imageList.size() > 0 ? imageList.get(0) : null;
			if(image == null){
				logger.error("我的门户接口:头像数据库图片不存在，路径也不存在！");
				headPicturePath = "";
			}else{
				String path = image.getPath();
				byte[] content = image.getFileContent();
				String headname = image.getFileName();
				String filename = StringUtil.isEmpty(headname) ? userName+"headPicture" : headname;
				
				HttpServletRequest request = ServletActionContext.getRequest();
				
				if(content == null && StringUtil.isEmpty(path)){
					logger.error("我的门户接口:头像数据库图片不存在，路径也不存在！");
					headPicturePath = "";
				}else{
					String pathFile = BaseHolder.getPropertiesValue("MyPicture","headPicture");
					String pathurl = request.getSession().getServletContext().getRealPath("/") + pathFile;
					File newFile = new File(pathurl);
					if (!newFile.exists()) {
						newFile.mkdir();
					}
					String url = request.getSession().getServletContext().getRealPath("/") + path;
					url = url.replace("\\", "/");
					File outFile = new File(url);
					if (!outFile.exists()) {
						try {
							outFile.createNewFile();
							
							if(content == null){
								logger.error("我的门户接口:头像数据库图片不存在，路径也不存在！");
								headPicturePath = "";
							}
							ImageIO.setUseCache(false);
							ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);
							BufferedImage newImage = ImageIO.read(byteArrayInputStream);
							ImageIO.write(newImage, UploadFileUtil.checkedFileName(filename), outFile);
						} catch (IOException e) {
							logger.error("我的头像生成产生异常----！");
							logger.error(e,e.fillInStackTrace());
						}
					} 
					headPicturePath = getImageHost() + path;
					entity.setHeadPicturePath(headPicturePath);
				}
			}
			//我的头像读取end
			
			ResultEntity result = new ResultEntity<LoginEntity>(1, "成功", entity);
			return gson.toJson(result);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResultEntity result = new ResultEntity<LoginEntity>(0, "对不起，您无权访问", new LoginEntity());
		return gson.toJson(result);
		/*FailureEntity failure = new FailureEntity();
		failure.failure = "对不起，您无权访问";
		return gson.toJson(failure);*/
	}
	
	
	/**
	 * 登录接口
	 * @param userName
	 * @param passWord
	 * @param sign
	 * @return
	 */
	public String wiseduLogin(String userName, String sign){
		Gson gson = new Gson();
		LoginEntity entity = new LoginEntity();
		if(StringUtil.isEmpty(userName) || StringUtil.isEmpty(sign)){
			ResultEntity result = new ResultEntity<LoginEntity>(0, "参数传值出错！", new LoginEntity());
			return gson.toJson(result);
		}
		try {
			
		
		if(infromation.equals("0")){
			logger.error("获取登陆接口："+"userName="+userName+",strKey="+sign);
			}
		StringBuilder sb = new StringBuilder();
		if (Authentication.authenticate(sign)) {
			LoginModel model = new LoginModel();
			model.setYhm(userName);
			Map<String, String> map = new HashMap<String, String>();
			ILoginService loginService = (ILoginService)SpringHolder.getBean("loginService");
			User user = loginService.cxYhxx(model);
			YhglModel yhglModel = new YhglModel();
			yhglModel.setZgh(userName);
			String keyCode = null;
			try {
				keyCode = MD5Util.md5Encode(
										userName + 
										Config.getString("key", "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS") + 
										System.currentTimeMillis()
									);
				if(StringUtils.isEmpty(keyCode))
					throw new Exception("产生秘钥异常!");
			} catch (Exception e) {
				e.printStackTrace();
			}
			yhglModel.setStrKey(keyCode);
			loginService.updateStrKey(yhglModel);
			if (user != null) {
				if(!StringUtil.isEmpty(user.getSfqy()) && !user.getSfqy().equals("1")){
					ResultEntity result = new ResultEntity<LoginEntity>(0, "用户没有被启用！", new LoginEntity());
					return gson.toJson(result);
				}
				String dqxnxq = "";
				String loginXML = "";
				String appName = MobileSystemHolder.getPropertiesValue("app_name");
				entity.setName(user.getXm());
				if ("student".equals(user.getYhlx())) {
					entity.setRole("XS");
				} else {
					entity.setRole("JS");
				}
				entity.setDepartment(user.getBmmc());
				entity.setSchoolName("");
				entity.setClassName("");
				entity.setGradeName("");
				entity.setUserId(user.getYhm());
				entity.setAppname(appName);
				sb.append("<yhm>").append(user.getYhm()).append("</yhm>");
				entity.setNowSchoolYearTerm(dqxnxq);
				entity.setApp_token(keyCode);
			}else{
				ResultEntity result = new ResultEntity<LoginEntity>(0, "后台显示用户为空", entity);
				return gson.toJson(result);
			}
			if(infromation.equals("0")){
				logger.error("登陆接口返回为："+entity);
			}	
			//ArrayList<LoginEntity> list = new ArrayList<LoginEntity>(); 
			//list.add(entity);
			if(!StringUtil.isEmpty(Config.getString("alibaichuan"))
					&& Config.getString("alibaichuan").equals("yes")){
				//signUp(userName, user.getXm());//阿里百川注册帐号，为移动端IM模块服务
			}
			//我的头像读取start
			String headPicturePath = null;
			IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
			List<ImageDB> imageList = mobileCommonService.getMyPicture(userName);
			ImageDB image = imageList != null && imageList.size() > 0 ? imageList.get(0) : null;
			if(image == null){
				logger.error("我的门户接口:头像数据库图片不存在，路径也不存在！");
				headPicturePath = "";
			}else{
				String path = image.getPath();
				byte[] content = image.getFileContent();
				String headname = image.getFileName();
				String filename = StringUtil.isEmpty(headname) ? userName+"headPicture" : headname;
				
				HttpServletRequest request = ServletActionContext.getRequest();
				
				if(content == null && StringUtil.isEmpty(path)){
					logger.error("我的门户接口:头像数据库图片不存在，路径也不存在！");
					headPicturePath = "";
				}else{
					String pathFile = BaseHolder.getPropertiesValue("MyPicture","headPicture");
					String pathurl = request.getSession().getServletContext().getRealPath("/") + pathFile;
					File newFile = new File(pathurl);
					if (!newFile.exists()) {
						newFile.mkdir();
					}
					String url = request.getSession().getServletContext().getRealPath("/") + path;
					url = url.replace("\\", "/");
					File outFile = new File(url);
					if (!outFile.exists()) {
						try {
							outFile.createNewFile();
							
							if(content == null){
								logger.error("我的门户接口:头像数据库图片不存在，路径也不存在！");
								headPicturePath = "";
							}
							ImageIO.setUseCache(false);
							ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);
							BufferedImage newImage = ImageIO.read(byteArrayInputStream);
							ImageIO.write(newImage, UploadFileUtil.checkedFileName(filename), outFile);
						} catch (IOException e) {
							logger.error("我的头像生成产生异常----！");
							logger.error(e,e.fillInStackTrace());
						}
					} 
					headPicturePath = getImageHost() + path;
					entity.setHeadPicturePath(headPicturePath);
				}
			}
			//我的头像读取end
			
			ResultEntity result = new ResultEntity<LoginEntity>(1, "成功", entity);
			return gson.toJson(result);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResultEntity result = new ResultEntity<LoginEntity>(0, "对不起，您无权访问", new LoginEntity());
		return gson.toJson(result);
		/*FailureEntity failure = new FailureEntity();
		failure.failure = "对不起，您无权访问";
		return gson.toJson(failure);*/
	}
	
	/**
	 * 阿里百川是否注册验证，没注册则注册，主要为移动端IM聊天功能服务
	 * @param userId
	 * @param name
	 */
	public void signUp(String userId, String name){
		try {
		TaobaoClient client = new DefaultTaobaoClient(
				Config.getString("alibaiUrl"), 
				Config.getString("alibaiappkey"), 
				Config.getString("alibaisecret")
				);
		boolean exits = false;
		//验证是否已经在阿里百川存在此帐号
		OpenimUsersGetRequest req = new OpenimUsersGetRequest();
		req.setUserids(userId);
		OpenimUsersGetResponse rsp;
			rsp = client.execute(req);
			System.out.println(rsp.getBody());
			String bodyData = rsp.getBody();
			logger.error("阿里百川是否存在用户数据：userId="+userId+",response="+bodyData);
			net.sf.json.JSONObject object =  net.sf.json.JSONObject.fromObject(bodyData);
			net.sf.json.JSONObject userinfos = object.optJSONObject("openim_users_get_response").optJSONObject("userinfos");
			if(userinfos != null || userinfos.size() == 1 ){
				exits = true;
			}
			//System.out.println(userinfos == null || userinfos.size() == 0 ? 0 : 1);
			//如果已经存在，则不走下面的接口，不存在则注册
			TaobaoClient clientAdd = new DefaultTaobaoClient(
					Config.getString("alibaiUrl"), 
					Config.getString("alibaiappkey"), 
					Config.getString("alibaisecret")
			);
			OpenimUsersAddRequest reqAdd = new OpenimUsersAddRequest();
			List<Userinfos> list2 = new ArrayList<Userinfos>();
			Userinfos obj3 = new Userinfos();
			list2.add(obj3);
			obj3.setNick(name);
			String headPicturePath = getHeadPictureByUserId(userId);
			if(!StringUtil.isEmpty(headPicturePath)){
				obj3.setIconUrl(headPicturePath);
			}
			obj3.setUserid(userId);
			obj3.setPassword(Config.getString("alibaiPassword"));
			obj3.setName(name);
			reqAdd.setUserinfos(list2);
			OpenimUsersAddResponse rspAdd;
			rspAdd = clientAdd.execute(reqAdd);
			System.out.println(rspAdd.getBody());	
			String bodyDataAdd = rspAdd.getBody();
			net.sf.json.JSONObject objectAdd = net.sf.json.JSONObject.fromObject(bodyDataAdd);
			net.sf.json.JSONObject userinfosAdd = objectAdd.optJSONObject("openim_users_add_response").optJSONObject("uid_succ");
			if(userinfosAdd != null || userinfosAdd.size() == 1){
				logger.error("阿里百川帐号注册成功-success：userId="+userId+",response="+bodyDataAdd);
			}
			System.out.println(userinfosAdd == null || userinfosAdd.size() == 0 ? 0 : 1);
		} catch (ApiException e) {
			logger.error("阿里百川异常-alibaichuan：", e);
			logger.error(e, e.fillInStackTrace());
			//e.printStackTrace();
		}
		
	}
	
	/**
	 * 直接根据用户名userid获取头像路径方法
	 * @param userName
	 * @return
	 */
	public String getHeadPictureByUserId(String userName){
		String headPath = null;
		IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
		List<ImageDB> imageList = mobileCommonService.getMyPicture(userName);
		ImageDB image = imageList != null && imageList.size() > 0 ? imageList.get(0) : null;
		if(image == null){
			return headPath;
		}
			
		String path = image.getPath();
		byte[] content = image.getFileContent();
		String headname = image.getFileName();
		String filename = StringUtil.isEmpty(headname) ? userName+"headPicture" : headname;
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		if(content == null && StringUtil.isEmpty(path)){
			return headPath;
		}else{
			String pathFile = BaseHolder.getPropertiesValue("MyPicture","headPicture");
			String pathurl = request.getSession().getServletContext().getRealPath("/") + pathFile;
			File newFile = new File(pathurl);
			if (!newFile.exists()) {
				newFile.mkdir();
			}
			String url = request.getSession().getServletContext().getRealPath("/") + path;
			url = url.replace("\\", "/");
			File outFile = new File(url);
			if (!outFile.exists()) {
					try {
						outFile.createNewFile();
					
						if(content == null){
							return headPath;
						}
						ImageIO.setUseCache(false);
						ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);
						BufferedImage newImage = ImageIO.read(byteArrayInputStream);
						ImageIO.write(newImage, UploadFileUtil.checkedFileName(filename), outFile);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			} 
			headPath = getImageHost()+path;
		}
		return headPath;
	}
}
