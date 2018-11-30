/**
 * 
 */
package com.zfsoft.mobile.servlet.commonHttp.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
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
import com.jw.org.tempuri.IConfigurationInfo;
import com.jw.org.tempuri.ILogin;
import com.mh.CXFServe.service.ICaService;
import com.opensymphony.xwork2.ActionContext;
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
import com.zfsoft.dao.entities.LoginRecordModel;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.finfo.util.FormInfoUtil;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.infoclass.query.InfoPropertyQuery;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.IInfoPropertyService;
import com.zfsoft.hrm.core.util.Byte_File_Object;
import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.hrm.file.util.ImageDBUtil;
import com.zfsoft.hrm.file.util.UploadFileUtil;
import com.zfsoft.mobile.MD5Util.MD5Util;
import com.zfsoft.mobile.basedata.service.IBaseDataService;
import com.zfsoft.mobile.canteenfood.entity.Canteen;
import com.zfsoft.mobile.canteenfood.entity.Canteenaddress;
import com.zfsoft.mobile.canteenfood.entity.Foodcataofcanteen;
import com.zfsoft.mobile.canteenfood.entity.Foodofcanteen;
import com.zfsoft.mobile.canteenfood.entity.Foodorder;
import com.zfsoft.mobile.canteenfood.entity.Orderofcanteen;
import com.zfsoft.mobile.canteenfood.service.ICanteenService;
import com.zfsoft.mobile.canteenfood.service.ICanteenaddressService;
import com.zfsoft.mobile.canteenfood.service.IFoodcataofcanteenService;
import com.zfsoft.mobile.canteenfood.service.IFoodofcanteenService;
import com.zfsoft.mobile.canteenfood.service.IFoodorderService;
import com.zfsoft.mobile.canteenfood.service.IOrderofcanteenService;
import com.zfsoft.mobile.classCommunity.entity.DynamicEntity;
import com.zfsoft.mobile.classCommunity.entity.DynamicFileEntityForInsert;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.common.utils.Authentication;
import com.zfsoft.mobile.common.utils.FileUntils;
import com.zfsoft.mobile.common.utils.HttpClientUtil;
import com.zfsoft.mobile.common.utils.MobileSystemHolder;
import com.zfsoft.mobile.news.entity.SchoolScenery;
import com.zfsoft.mobile.news.entity.SchoolSceneryCatalog;
import com.zfsoft.mobile.news.service.ISchoolSceneryCatalogService;
import com.zfsoft.mobile.news.service.ISchoolSceneryService;
import com.zfsoft.mobile.servlet.commonHttp.service.ICommonHttpService;
import com.zfsoft.mobile.servlet.entity.ListEntity;
import com.zfsoft.mobile.servlet.entity.LoginEntity;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.mobile.suggest.entity.SuggestEntity;
import com.zfsoft.mobile.suggest.entity.suggestPictureEntity;
import com.zfsoft.mobile.suggest.service.ISuggestService;
import com.zfsoft.mobile.vote.entity.VoteMainEntity;
import com.zfsoft.mobile.webcasts.entity.WebcastsAuditEntity;
import com.zfsoft.mobile.webcasts.entity.WebcastsEntity;
import com.zfsoft.mobile.webcasts.service.IWebcastsAuditService;
import com.zfsoft.mobile.webcasts.service.IWebcastsService;
import com.zfsoft.mobile.webservices.entity.InformationListEntity;
import com.zfsoft.service.svcinterface.ILoginService;
import com.zfsoft.untils.AESEncrypt;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.encrypt.DBEncrypt;

/**
 * @author zhangxu
 * @description app通用访问接口
 * @date 2017-5-8 下午04:52:55
 */
public class CommonHttpAction {
	
	private static Logger logger = Logger.getLogger(CommonHttpAction.class);
	private final String infromation=Config.getString("mobile.infromation");
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private ICommonHttpService commonHttpService;
	private ILoginService loginService;
	private ISchoolSceneryCatalogService schoolSceneryCatalogService;
	private ISchoolSceneryService schoolSceneryService;
	private IBaseDataService baseDataService;
	private IInfoPropertyService infoPropertyService;
	private ISuggestService suggestService;
	
	private ICanteenService canteenService;
	private IFoodcataofcanteenService foodcataService;
	private IFoodofcanteenService foodService;
	private IOrderofcanteenService orderService;
	private IFoodorderService foodorderService;
	private ICanteenaddressService addressService;
	
	private IWebcastsService webcastService;
	private IWebcastsAuditService auditService;
	
	
	public void submitSuggest(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
        response.setContentType("text/html;charset=UTF-8");  
		PrintWriter out = null;  
       // response.setContentType("text/html;charset=UTF-8");  
        FileItemFactory factory = new DiskFileItemFactory();  
        ServletFileUpload upload = new ServletFileUpload(factory);  
        List<FileItem> items = new ArrayList<FileItem>();  
        //boolean isRight = false;
        String username = null;
        String schoolCode = null;
        String versionNumber = null;
        String telephone = null;
        String qq = null;
        String textContent = null;
        suggestPictureEntity pictureEntity = null;
        String suggestId = null;
        String apptoken = null;
        SuggestEntity suggestEntity = new SuggestEntity();
        Gson gson = new Gson();
        try {  
        	out = response.getWriter();
            items = upload.parseRequest(request);  
            // 得到所有的文件  
            Iterator<FileItem> it = items.iterator();  
            while (it.hasNext()) {  
                FileItem fItem = (FileItem) it.next();  
                String paramName = "";  
                Object fValue = null;  
                if (fItem.isFormField()) { // 普通文本框的值  
                	paramName = fItem.getFieldName();  
                    fValue = fItem.getString("UTF-8");  
                    if(paramName.equals("schoolCode")){
                    	schoolCode = fValue.toString();
                    }else if(paramName.equals("versionNumber")){
                    	versionNumber = fValue.toString();
                    }else if(paramName.equals("username")){
                    	username = fValue.toString();
                    }else if(paramName.equals("telephone")){
                    	telephone = fValue.toString();
                    }else if(paramName.equals("qq")){
                    	qq = fValue.toString();
                    }else if(paramName.equals("apptoken")){
                    	apptoken = fValue.toString();
                    }else if(paramName.equals("textContent")){
                    	textContent = fValue.toString();
                    	qq       		= CodeUtil.decode(qq, apptoken);
                    	schoolCode      = CodeUtil.decode(schoolCode, apptoken);
                    	telephone       = CodeUtil.decode(telephone, apptoken);
                    	versionNumber   = CodeUtil.decode(versionNumber, apptoken);
                    	username        = CodeUtil.decode(username, apptoken);
                    	suggestEntity.setQq(qq);
                    	suggestEntity.setSchoolCode(schoolCode);
                    	suggestEntity.setTelephone(telephone);
                    	suggestEntity.setTextContent(textContent);
                    	suggestEntity.setVersionNumber(versionNumber);
                    	suggestEntity.setUserName(username);
                    	suggestService.insertSuggestMain(suggestEntity);
                    	suggestId = suggestEntity.getId();
                    }
                } else { // 获取上传文件的值  
                	logger.error("进入建议反馈图片上传方法");
                	//paramName = fItem.getFieldName();//userfile  
                   // fValue = fItem.getInputStream(); 
                    String filename = fItem.getName();//路径  
                    if(!StringUtil.isEmpty(filename)) {  
                        InputStream is = fItem.getInputStream();  
                        
                        byte[] content = null;
                        int size = is.available();
                        if(is != null && size != 0){
                        	content = Byte_File_Object.getBytesFromFile(is);
                        }
                        pictureEntity = new suggestPictureEntity();
                        pictureEntity.setSuggestId(suggestId);
                        pictureEntity.setPictureContent(content);
                        pictureEntity.setTitle(filename);
                        
                        String filePath = request.getSession().getServletContext().getRealPath("/") + "suggestPicture"; 
            			filePath = filePath.replace("\\", "/");
            			File newFile = new File(filePath);
            			if (!newFile.exists()) {
            				newFile.mkdir();
            			}
            			
            			File outFile = new File(filePath, filename);
            			if (!outFile.exists()) {
            				outFile.createNewFile();
            			} else {
            				outFile.delete();
            				outFile.createNewFile();
            			}
            			
            			FileOutputStream fileOutputStream = new FileOutputStream(outFile);
            			if(content != null){
            				fileOutputStream.write(content, 0, content.length);
            			}else{
            				fileOutputStream.write(pictureEntity.getPictureContent(), 0, pictureEntity.getPictureContent().length);
            			}
            			fileOutputStream.close();
            			is.close();
            			pictureEntity.setPicturePath("suggestPicture/"+filename);
            			
            			suggestService.insertSuggestPicture(pictureEntity);
                        
                    }  
                }  
            }  
            
            ResultEntity<String> result = new ResultEntity<String>(1, "成功", null);
            out.write(gson.toJson(result));
            out.flush();
            out.close();
            
        } catch (Exception e) {  
            //e.printStackTrace(); 
            logger.error("memo upload------: exception");  
            logger.error(e, e.fillInStackTrace());
            ResultEntity<String> result = new ResultEntity<String>(0, "操作失败", null);
            out.write(gson.toJson(result));
            out.flush();
            out.close();
         // 数据处理  
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
	 * 获取学校风景类别列表
	* @author: zhangxu
	* @Title: getSceneryCatalogList 
	* @Description: 获取学校风景类别
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void getSceneryCatalogList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		PrintWriter out;
		try {
			out = response.getWriter();
			List<SchoolSceneryCatalog> SceneryCatalogList = schoolSceneryCatalogService.getAllList();
			ResultEntity<List<SchoolSceneryCatalog>> result = new ResultEntity<List<SchoolSceneryCatalog>>(1, "成功", SceneryCatalogList);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error(" 获取学校风景类别  getSceneryCatalogList err：");
			logger.error(e, e.fillInStackTrace());
		}
	}
	
	
	/**
	 * 
	* @author: zhangxu
	* @Title: getSchoolSceneryList 
	* @Description: 根据学校风景类别id获取学校风景
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void getSchoolSceneryList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String sceneryCatalogId = null;
        String pageIndex = null;
        try {
			//BufferedReader sb  = request.getReader();
			sceneryCatalogId = new String(request.getParameter("sceneryCatalogId").getBytes("ISO8859-1"), "UTF-8");
			pageIndex = new String(request.getParameter("pageIndex").getBytes("ISO8859-1"), "UTF-8");
        } catch (IOException e) {
        	logger.error("登录接口login 参数解析异常 err：");
			logger.error(e, e.fillInStackTrace());
        }
        
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		PrintWriter out;
		try {
			out = response.getWriter();
			SchoolScenery  query = new SchoolScenery();
			query.setSceneryCatalogId(sceneryCatalogId);
			query.setIsActive("1");
			query.setPerPageSize(Integer.parseInt(pageIndex));
			List<SchoolScenery> schoolSceneryList = schoolSceneryService.getList(query);
			ResultEntity<List<SchoolScenery>> result = new ResultEntity<List<SchoolScenery>>(1, "成功", schoolSceneryList);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error("根据学校风景类别id获取学校风景  getSchoolSceneryList err：");
			logger.error(e, e.fillInStackTrace());
		}
	}
	
	/**
	 * 
	* @author: zhangxu
	* @Title: personDocumentInformation 
	* @Description: 获取数字档案某类别详情数据
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void personDocumentInformation(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = null;
		String strKey = null;
		String informationName = null;
		String informationId = null;
        String apptoken = null;
        try {
        	PrintWriter out = response.getWriter();
//			username = StringUtil.isEmpty(request.getParameter("username")) ? 
//					"" : java.net.URLDecoder.decode(request.getParameter("username"), "UTF-8");
//			strKey = StringUtil.isEmpty(request.getParameter("strKey")) ? 
//					"" : java.net.URLDecoder.decode(request.getParameter("strKey"), "UTF-8");
//			informationName = StringUtil.isEmpty(request.getParameter("informationName")) ? 
//					"" : java.net.URLDecoder.decode(request.getParameter("informationName"), "UTF-8");
//			informationId = StringUtil.isEmpty(request.getParameter("informationId")) ? 
//					"" : java.net.URLDecoder.decode(request.getParameter("informationId"), "UTF-8");
			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
			strKey = new String(request.getParameter("strKey").getBytes("ISO8859-1"), "UTF-8");
			informationName = new String(request.getParameter("informationName").getBytes("ISO8859-1"), "UTF-8");
			informationId = new String(request.getParameter("informationId").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			Gson gson = new Gson();
		 	if(StringUtil.isEmpty(username) || StringUtil.isEmpty(strKey)
		 			|| StringUtil.isEmpty(informationName)|| StringUtil.isEmpty(informationId)
		 			|| StringUtil.isEmpty(apptoken)){
				ResultEntity<List<Map<String,String>>> result = new ResultEntity<List<Map<String,String>>>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		 	if(!ApptokenUtils.compare(username, apptoken)){
				ResultEntity<List<Map<String,String>>> result = new ResultEntity<List<Map<String,String>>>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		 	try {
		 		username  		= CodeUtil.decode(username, apptoken);
				strKey  		= CodeUtil.decode(strKey, apptoken);
				informationName = CodeUtil.decode(informationName, apptoken);
				informationId  	= CodeUtil.decode(informationId, apptoken);
				
			} catch (Exception e) {
				ResultEntity<List<Map<String,String>>> result = new ResultEntity<List<Map<String,String>>>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		
			LoginModel model = new LoginModel();
			model.setYhm(username);
			User user = loginService.cxYhxx(model);
			InfoClass clazz = FormInfoUtil.reFillPropertyByRole(user.getJsdms(), informationId);
			InfoPropertyQuery propertyQuery = new InfoPropertyQuery();
			propertyQuery.setClassId( clazz.getGuid() );
			List<InfoProperty> properties = infoPropertyService.getInfoProperties( propertyQuery );
			DynaBeanQuery query = new DynaBeanQuery();
			query.setClazz(clazz);
			String express = " t.gh = '" + informationName + "'";
	        query.setExpress(express);
	        query.setPerPageSize(1);
	        List<DynaBean> list = baseDataService.getSynchronizedBaseData(query);
	        List<List<Map<String,String>>> totalList= new ArrayList<List<Map<String,String>>>();
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
	        
		//输出结果
	        ResultEntity<List<List<Map<String,String>>>> result = new ResultEntity<List<List<Map<String,String>>>>(1, "成功", totalList);
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
        }catch (Exception e) {
        	logger.error("获取数字档案某类别详情数据接口personDocumentInformation 参数解析异常 err：");
			logger.error(e, e.fillInStackTrace());
        }
	}
	
	/**
	 * 
	* @author: zhangxu
	* @Title: personDocumentInformationList 
	* @Description: 数字档案列表接口
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void personDocumentInformationList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = null;
        String apptoken = null;
        try {
        	PrintWriter out = response.getWriter();
//	   	 	username = StringUtil.isEmpty(request.getParameter("username")) ? 
//					"" : java.net.URLDecoder.decode(request.getParameter("username"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
		 	
		 	username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
		 	
		 	Gson gson = new Gson();
		 	if(StringUtil.isEmpty(username) || StringUtil.isEmpty(apptoken)){
				ResultEntity<List<InformationListEntity>> result = new ResultEntity<List<InformationListEntity>>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		 	if(!ApptokenUtils.compare(username, apptoken)){
				ResultEntity<List<InformationListEntity>> result = new ResultEntity<List<InformationListEntity>>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		 	try {
				username  			= CodeUtil.decode(username, apptoken);
				
			} catch (Exception e) {
				ResultEntity<List<InformationListEntity>> result = new ResultEntity<List<InformationListEntity>>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			
		 	List<InformationListEntity> informationList = null;
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
	    		model.setYhm(username);
	    		User user = loginService.cxYhxx(model);
	    		InfoClass clazz = FormInfoUtil.reFillPropertyByRole(user.getJsdms(), informationList.get(0).getInformationid());
	    		InfoPropertyQuery propertyQuery = new InfoPropertyQuery();
	    		propertyQuery.setClassId( clazz.getGuid() );
	    		List<InfoProperty> properties = infoPropertyService.getInfoProperties( propertyQuery );
	    		DynaBeanQuery query = new DynaBeanQuery();
	    		query.setClazz(clazz);
	    		String express = " t.gh = '" + username + "'";
	            query.setExpress(express);
	            query.setPerPageSize(1);
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
			
	        ResultEntity<List<InformationListEntity>> result = new ResultEntity<List<InformationListEntity>>(1, "成功", informationList);
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
        }catch (Exception e) {
        	logger.error("数字档案列表接口personDocumentInformationList 参数解析异常 err：");
			logger.error(e, e.fillInStackTrace());
        }
	}
	/*
	*//**
	 * 
	* @author: zhangxu
	* @Title: login 
	* @Description: 登录接口
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 *//*
	public void login(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = null;
        String password = null;
        String strKey   = null;
        String deviceId = null;
        //新老ca接口辨别参数 0或空，老接口  1.新街口
        String status = null;
        try {
			//BufferedReader sb  = request.getReader();
	   	 	username = StringUtil.isEmpty(request.getParameter("username")) ? 
	   			 "" : java.net.URLDecoder.decode(request.getParameter("username"), "UTF-8");
	        password = StringUtil.isEmpty(request.getParameter("password")) ? 
	       		 "" : java.net.URLDecoder.decode(request.getParameter("password"), "UTF-8");
	        strKey   = StringUtil.isEmpty(request.getParameter("strKey")) ? 
	       		 "" : java.net.URLDecoder.decode(request.getParameter("strKey"), "UTF-8");
	        
	        deviceId = StringUtil.isEmpty(request.getParameter("deviceId")) ? 
		       		 "" : java.net.URLDecoder.decode(request.getParameter("deviceId"), "UTF-8");
	        
	        status = StringUtil.isEmpty(request.getParameter("status")) ? 
		       		 "" : java.net.URLDecoder.decode(request.getParameter("status"), "UTF-8");
	        
	        username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	        password = new String(request.getParameter("password").getBytes("ISO8859-1"), "UTF-8");
	        strKey   = new String(request.getParameter("strKey").getBytes("ISO8859-1"), "UTF-8");
		        
        } catch (IOException e) {
        	logger.error("登录接口login 参数解析异常 err：");
			logger.error(e, e.fillInStackTrace());
        }
		Gson gson = new Gson();
		LoginEntity entity = new LoginEntity();
		PrintWriter out;
		try {
			out = response.getWriter();
			if(StringUtil.isEmpty(username) || StringUtil.isEmpty(password)
					|| StringUtil.isEmpty(strKey)){
				ResultEntity<LoginEntity> result = new ResultEntity<LoginEntity>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			try {
				
			
			if(infromation.equals("0")){
				logger.error("获取登陆接口："+"username="+username+",password="+password+",strKey="+strKey);
				}
			if (Authentication.authenticate(strKey)) {
				LoginModel model = new LoginModel();
				model.setYhm(username);
				model.setMm(password);
				Map<String, String> map = new HashMap<String, String>();
				
				
				*//**
				 * 台州学院oa独立登录,如果oa登录正确，直接返回，不用做后台登录校验
				 *//*
				
				Map<String,String> oaLoginMap = new HashMap<String,String>();
				if(!StringUtil.isEmpty(Config.getString("oaOnlyLogin"))&&"yes".equals(Config.getString("oaOnlyLogin"))){
					//访问oa webservice的checkLogin方法,将返回值拼成后台登录返回值格式返回
					String xmlStr=WebServiceUtil.createServiceOa().checkLogin(username, new DBEncrypt().eCode(password));
					if(!StringUtil.isEmpty(xmlStr)){
						Document doc = null;
						try{
							doc = DocumentHelper.parseText(xmlStr); // 将字符串转为XML
				            Element rootElt = doc.getRootElement(); // 获取根节点
				            Iterator iter = rootElt.elementIterator();
				            while(iter.hasNext()){
				            	Element nodes = (Element) iter.next();
				            	String nodeName = nodes.getName();
				            	String nodeValue = nodes.getStringValue();
				            	oaLoginMap.put(nodeName,nodeValue);
				            }
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				
				if ("1".equals(status)) {
					String zfxml = "";
					String endpointURL = WebServiceConf.WEBSERVICE_NEWMH;
					org.apache.axis.client.Service service = new org.apache.axis.client.Service();
					try {
						Call call;
						call = (Call) service.createCall();
						call.setTargetEndpointAddress(new java.net.URL(endpointURL));
						QName opAddEntry = new QName(
								WebServiceConf.WEBSERVICE_NEWMH,
								"getTicket2");
						call.setOperationName(opAddEntry);
						zfxml = (String) call.invoke(new Object[] { username, password });
						System.out.print("getTicket2：" + zfxml); // 测试
					} catch (Exception e) {
						System.out.println(e);
						ResultEntity<LoginEntity> result1 = new ResultEntity<LoginEntity>(0, "ca接口登录异常！", null);
						out.write(gson.toJson(zfxml));
						out.flush();
						out.close();
					}
					
					boolean canLogin = false;
					try {
						canLogin = containErrorCode(zfxml);
					} catch (DocumentException e) {
						e.printStackTrace();
					}
					if(canLogin){
						ResultEntity<LoginEntity> result = new ResultEntity<LoginEntity>(0, "用户名或密码不正确！", null);
						out.write(gson.toJson(result));
						out.flush();
						out.close();
					}else{
						try {
							map = getMapValue(zfxml);//获取ca认证返回过来的值
						} catch (DocumentException e) {
							e.printStackTrace();
						}
					}
					
				}else {
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
							zfxml =service.getTicket2(username, password, Xtmdlst, "");
							
						} catch (Exception e) {
							System.out.println(e);
							ResultEntity<LoginEntity> result = new ResultEntity<LoginEntity>(0, "ca接口登录异常！", null);
							out.write(gson.toJson(result));
							out.flush();
							out.close();
						}
							
							
							boolean canLogin = false;
							try {
								canLogin = containErrorCode(zfxml);
							} catch (DocumentException e) {
								e.printStackTrace();
							}
							if(canLogin){
								ResultEntity<LoginEntity> result = new ResultEntity<LoginEntity>(0, "用户名或密码不正确！", null);
								out.write(gson.toJson(result));
								out.flush();
								out.close();
							}else{
								try {
									map = getMapValue(zfxml);//获取ca认证返回过来的值
								} catch (DocumentException e) {
									e.printStackTrace();
								}
							}
						}
				}
					logger.error("--------setMm(null)---------");
					if(!StringUtil.isEmpty(Config.getString("webservice.host.mh")) || 
							!StringUtil.isEmpty(Config.getString("qymm"))){
						logger.error("-----------enter setmm null------------");
						//model.setMm(null);
						
					}
					
					User user = null;
					user = loginService.cxYhxx(model);
					//logger.error("user对象="+user.getYhm());
					
					if(oaLoginMap.containsKey("code")&&"201".equals(oaLoginMap.get("code"))){ //如果oa返回正确,则根据用户名获取用户信息即可
						user = new User();
						user.setSfqy("1");
						user.setYhm(username);
						user.setXm(oaLoginMap.get("message"));
					}else{//否则做移动后台用户登录校验
						user = loginService.getInfoByZgh(model);
						//user = loginService.cxYhxx(model);						
					}
					
					
					
					YhglModel yhglModel = new YhglModel();
					yhglModel.setZgh(username);
					String keyCode = null;
					try {
						keyCode = MD5Util.md5Encode(
												username + 
												Config.getString("key", "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS") + 
												System.currentTimeMillis()
											);
						if(StringUtils.isEmpty(keyCode)){
							logger.error("登录接口login keyCode为空 err：");
							ResultEntity<LoginEntity> result = new ResultEntity<LoginEntity>(0, "产生密钥异常！", null);
							out.write(gson.toJson(result));
							out.flush();
							out.close();
						}
					} catch (Exception e) {
						logger.error("登录接口login 产生密钥异常 err：");
						logger.error(e, e.fillInStackTrace());
						ResultEntity<LoginEntity> result = new ResultEntity<LoginEntity>(0, "产生密钥异常！", null);
						out.write(gson.toJson(result));
						out.flush();
						out.close();
					}
					
					if (user != null) {
						yhglModel.setStrKey(keyCode);
						loginService.updateStrKey(yhglModel);
						
						if(!StringUtil.isEmpty(user.getSfqy()) && !user.getSfqy().equals("1")){
							logger.error("登录接口login 用户没有被启用：");
							ResultEntity<LoginEntity> result = new ResultEntity<LoginEntity>(0, "用户没有被启用！", null);
							out.write(gson.toJson(result));
							out.flush();
							out.close();
						}
						String dqxnxq = "";
						String loginXML = "";
						if(!StringUtil.isEmpty(Config.getString("webservice.host.jw"))){
							try{
								IConfigurationInfo service = (IConfigurationInfo) WebServiceUtil
								.createFactoryJw(WebServiceConf.SERVICE_JWSERVICE_CONFIG,
										IConfigurationInfo.class, "ConfigurationInfo").create();

								loginXML = service.configurationInfo();
//								final String AND = "&";
//								final String alone = "N";
//								String parameterList = username + AND + password + AND + alone;
//								String jwKey = MiddleWareUtil.getJWSign(parameterList);
//								ILogin login = (ILogin) WebServiceUtil.createFactoryJw(
//										WebServiceConf.SERVICE_JWSERVICE_LOGIN, ILogin.class, "Login")
//										.create();
//								loginXML = login.login(parameterList, user.getYhlx().equals("")||user.getYhlx().equals("teacher") ? "js" : "xs", jwKey);
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
						//entity.setNowSchoolYearTerm(dqxnxq);
						entity.setNowSchoolYearTerm("2017-2018-2");
						entity.setApp_token(keyCode);
						
						
						//插入或更新登录记录表
						Map<String,Object> loginParams = new HashMap<String,Object>();
						loginParams.put("username", user.getYhm());
						if(loginService.selectLoginRecordByUsername(loginParams)>0){
							loginParams.put("lastLoginTime", sdf.format(new Date()));
							loginService.updateLoginRecord(loginParams);
						}else{
							loginParams.put("lastLoginTime", sdf.format(new Date()));
							loginService.addLoginRecord(loginParams);
						}
						
						//分析平台登录绑定校验唯一设备id
						if (org.apache.commons.lang3.StringUtils.isNotBlank(deviceId)) {
							Integer a = loginService.countUserByDeviceid(null,deviceId);
							if (a > 0) {
								Integer b = loginService.countUserByDeviceid(user.getYhm(),deviceId);
								if (b == 0) {
									logger.error("设备已绑定过账号");
									ResultEntity<LoginEntity> result = new ResultEntity<LoginEntity>(0, "该设备已绑定过账号", null);
									out.write(gson.toJson(result));
									out.flush();
									out.close();
								}
							}else{
								logger.error("deviceId="+user.getDeviceId());
								logger.error("user="+user.getYhm());
								if (user != null && user.getDeviceId() == null) {
									//尚未绑定账号进行绑定
									user.setDeviceId(deviceId);
									loginService.bindDevice(user);
									logger.error("绑定设备");
								}else {
									if (!deviceId.equals(user.getDeviceId())) {
										logger.error("与绑定的设备不一致");
										ResultEntity<LoginEntity> result = new ResultEntity<LoginEntity>(0, "登陆设备与绑定设备不一致", null);
										out.write(gson.toJson(result));
										out.flush();
										out.close();
									}
								}
							}
						}
						logger.error("绑定逻辑之后");
						
					}else{
						logger.error("登录接口login 产生密钥异常 err：");
						ResultEntity<LoginEntity> result = new ResultEntity<LoginEntity>(0, "后台显示用户为空，但ca登录正确！", null);
						out.write(gson.toJson(result));
						out.flush();
						out.close();
					}
				if(infromation.equals("0")){
					logger.error("登陆接口返回为："+entity);
					}	
				//ArrayList<LoginEntity> list = new ArrayList<LoginEntity>(); 
				//list.add(entity);
				if(!StringUtil.isEmpty(Config.getString("alibaichuan"))
						&& Config.getString("alibaichuan").equals("yes")){
					signUp(username, user.getXm());//阿里百川注册帐号，为移动端IM模块服务
				}
				
				//我的头像读取start
				String headPicturePath = null;
				IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
				List<ImageDB> imageList = mobileCommonService.getMyPicture(username);
				ImageDB image = imageList != null && imageList.size() > 0 ? imageList.get(0) : null;
				if(image == null){
					logger.error("我的门户接口:头像数据库图片不存在，路径也不存在！");
					headPicturePath = "";
				}else{
					String path = image.getPath();
					byte[] content = image.getFileContent();
					String headname = image.getFileName();
					String filename = StringUtil.isEmpty(headname) ? username+"headPicture" : headname;
					
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
								logger.error("登录接口login 我的头像生成产生异常err：");
								logger.error(e,e.fillInStackTrace());
//								ResultEntity<LoginEntity> result = new ResultEntity<LoginEntity>(0, "我的头像生成产生异常err", null);
//								out.write(gson.toJson(result));
//								out.flush();
//								out.close();
							}
						} 
						headPicturePath = getImageHost() + path;
						entity.setHeadPicturePath(headPicturePath);
					}
				}
				//我的头像读取end
				ResultEntity<LoginEntity> result = new ResultEntity<LoginEntity>(1, "成功", entity);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.error("登录接口login 无权访问err：");
//			ResultEntity<LoginEntity> result = new ResultEntity<LoginEntity>(0, "对不起，您无权访问", null);
//			out.write(gson.toJson(result));
//			out.flush();
//			out.close();
		} catch (IOException e) {
			logger.error("登录接口login 程序异常 err：");
			e.printStackTrace();
		}
	
	}
	*/
	
	
	//金职院
	/**
	 * 
	 * @author: zhangxu
	 * @Title: login 
	 * @Description: 登录接口
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void login(){
		HttpServletResponse response = ServletActionContext.getResponse();
	    response.setContentType("text/html");
	    response.setCharacterEncoding("utf-8");
	    HttpServletRequest request = ServletActionContext.getRequest();
	    String username = null;
	    String password = null;
	    String strKey = null;
	    String deviceId = null;
	    try
	    {
	      username = StringUtil.isEmpty(request.getParameter("username")) ? 
	        "" : URLDecoder.decode(request.getParameter("username"), "UTF-8");
	      password = StringUtil.isEmpty(request.getParameter("password")) ? 
	        "" : URLDecoder.decode(request.getParameter("password"), "UTF-8");
	      strKey = StringUtil.isEmpty(request.getParameter("strKey")) ? 
	        "" : URLDecoder.decode(request.getParameter("strKey"), "UTF-8");

	      deviceId = StringUtil.isEmpty(request.getParameter("deviceId")) ? 
	        "" : URLDecoder.decode(request.getParameter("deviceId"), "UTF-8");
	    }
	    catch (IOException e)
	    {
	      logger.error("登录接口login 参数解析异常 err：");
	      logger.error(e, e.fillInStackTrace());
	    }
	    Gson gson = new Gson();
	    LoginEntity entity = new LoginEntity();
	    try
	    {
	      PrintWriter out = response.getWriter();
	      if ((StringUtil.isEmpty(username)) || (StringUtil.isEmpty(password)) || 
	        (StringUtil.isEmpty(strKey))) {
	        ResultEntity<LoginEntity> result = new ResultEntity<LoginEntity>(0, "参数传值出错！", null);
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
	      }

	      try
	      {
	        if (this.infromation.equals("0")) {
	          logger.error("获取登陆接口：username=" + username + ",password=" + password + ",strKey=" + strKey);
	        }
	        if (Authentication.authenticate(strKey)) {
	          LoginModel model = new LoginModel();
	          model.setYhm(username);
	          model.setMm(password);
	          Map<String, String> map = new HashMap<String, String>();

	          if ((!StringUtil.isEmpty(Config.getString("webservice.host.mh"))) && 
	            (StringUtil.isEmpty(Config.getString("qymm")))) {
	            String zfxml = "";
	            try {
	              JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	              factory.setServiceClass(ICaService.class);
	              factory.setAddress(WebServiceConf.SERVICE_MHSERVICE);
	              factory.getInInterceptors().add(new LoggingInInterceptor());
	              factory.getOutInterceptors().add(new LoggingOutInterceptor());

	              ICaService service = (ICaService)factory.create();
	              String Xtmdlst = Config.getString("BusinessSystemNum");
	              zfxml = service.getTicket2(username, password, Xtmdlst, "");
	            }
	            catch (Exception e) {
	              System.out.println(e);
	              ResultEntity<LoginEntity> result = new ResultEntity<LoginEntity>(0, "ca接口登录异常！", null);
	              out.write(gson.toJson(result));
	              out.flush();
	              out.close();
	            }

	            boolean canLogin = false;
	            try {
	              canLogin = containErrorCode(zfxml);
	            } catch (DocumentException e) {
	              e.printStackTrace();
	            }
	            if (canLogin) {
	            	ResultEntity<LoginEntity> result = new ResultEntity<LoginEntity>(0, "用户名或密码不正确！", null);
	              out.write(gson.toJson(result));
	              out.flush();
	              out.close();
	            } else {
	              try {
	                map = getMapValue(zfxml);
	              } catch (DocumentException e) {
	                e.printStackTrace();
	              }
	            }
	          }
	          logger.error("--------setMm(null)---------");
	          if ((!StringUtil.isEmpty(Config.getString("webservice.host.mh"))) || 
	            (!StringUtil.isEmpty(Config.getString("qymm")))) {
	            logger.error("-----------enter setmm null------------");
	          }

	          User user = null;
	          user = this.loginService.cxYhxx(model);

	          YhglModel yhglModel = new YhglModel();
	          yhglModel.setZgh(username);
	          String keyCode = null;
	          try {
	            keyCode = MD5Util.md5Encode(
	              username + 
	              Config.getString("key", "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS") + 
	              System.currentTimeMillis());

	            if (org.apache.commons.lang.StringUtils.isEmpty(keyCode)) {
	              logger.error("登录接口login keyCode为空 err：");
	              ResultEntity<LoginEntity> result = new ResultEntity<LoginEntity>(0, "产生密钥异常！", null);
	              out.write(gson.toJson(result));
	              out.flush();
	              out.close();
	            }
	          } catch (Exception e) {
	            logger.error("登录接口login 产生密钥异常 err：");
	            logger.error(e, e.fillInStackTrace());
	            ResultEntity<LoginEntity> result = new ResultEntity<LoginEntity>(0, "产生密钥异常！", null);
	            out.write(gson.toJson(result));
	            out.flush();
	            out.close();
	          }

	          if (user != null) {
	            yhglModel.setStrKey(keyCode);
	            this.loginService.updateStrKey(yhglModel);

	            if ((!StringUtil.isEmpty(user.getSfqy())) && (!user.getSfqy().equals("1"))) {
	              logger.error("登录接口login 用户没有被启用：");
	              ResultEntity<LoginEntity> result = new ResultEntity<LoginEntity>(0, "产生密钥异常！", null);
	              out.write(gson.toJson(result));
	              out.flush();
	              out.close();
	            }
	            String dqxnxq = "";
	            String loginXML = "";
	            if (!StringUtil.isEmpty(Config.getString("webservice.host.jw")))
	              try {
	                IConfigurationInfo service = (IConfigurationInfo)
	                  WebServiceUtil.createFactoryJw(WebServiceConf.SERVICE_JWSERVICE_CONFIG, 
	                  IConfigurationInfo.class, "ConfigurationInfo").create();

	                loginXML = service.configurationInfo();
	                try
	                {
	                  Document document = DocumentHelper.parseText(loginXML);
	                  Element elementTemplate = document.getRootElement();
	                  Element xq = (Element)elementTemplate.selectSingleNode("//dqxnxq");

	                  if ((xq != null) && (xq.getText() != null))
	                    dqxnxq = xq.getText();
	                }
	                catch (DocumentException e)
	                {
	                  e.printStackTrace();
	                }
	              }
	              catch (Exception localException1)
	              {
	              }
	            String appName = MobileSystemHolder.getPropertiesValue("app_name");
	            entity.setName(user.getXm());
	            if ("student".equals(user.getYhlx()))
	              entity.setRole("XS");
	            else {
	              entity.setRole("JS");
	            }
	            if (!StringUtil.isEmpty(Config.getString("webservice.host.mh"))) {
	              String department = map.containsKey("BM") ? (String)map.get("BM") : "";
	              entity.setDepartment(department);
	            } else {
	              entity.setDepartment(user.getBmmc());
	            }
	            entity.setSchoolName("");
	            entity.setClassName("");
	            entity.setGradeName("");
	            entity.setUserId(user.getYhm());
	            entity.setAppname(appName);

	            entity.setNowSchoolYearTerm("2017-2018-2");
	            entity.setApp_token(keyCode);
	            
	            
	            if (org.apache.commons.lang3.StringUtils.isNotBlank(deviceId)) {
	            	
	            	String did = user.getDeviceId();
	            	
	            	if( !StringUtils.isEmpty(did) ){
	            		//当前登录用户的设备ID存在，分两种情况：
	            		//1、当前用户的设备号和传递进来的参数设备号一致，那么直接放行
	            		//2、当前用户的设备号和传递进来的参数设备号不一致，提示错误信息：
	            		
	            		if( !did.equals(deviceId) ){
	            			logger.error("设备已绑定过账号");
	            			ResultEntity<LoginEntity> result = new ResultEntity<LoginEntity>(0, "该设备已绑定过账号", null);
		                    out.write(gson.toJson(result));
		                    out.flush();
		                    out.close();
	            		}
	            		
	            	}else{
	            		//当前登录用户的设备ID不存在，分两种情况
	            		//根据当前用户的用户类型，和传入的设备号进行查询；
	            		//1、能查出有效结果，那么提示错误；
	            		//2、不能查出有效结果，放行，并且绑定设备ID
	            		//String lx = user.getRylx();
	            		String lx = user.getYhlx();
	            		
	            		List<YhglModel> yhModelList = loginService.getUserByDeviceidAndRylx(deviceId,lx);
	            		
	            		if( yhModelList!=null && yhModelList.size()>0 ){
	            			logger.error("设备已绑定过账号");
	            			ResultEntity<LoginEntity> result = new ResultEntity<LoginEntity>(0, "该设备已绑定过账号", null);
		                    out.write(gson.toJson(result));
		                    out.flush();
		                    out.close();
	            		}else{
	            			//绑定设备号
	            			user.setDeviceId(deviceId);
	    	                this.loginService.bindDevice(user);
	    	                logger.error("绑定设备");
	            		}
	            	}
	            	
	              }

	            logger.error("绑定逻辑之后");
	          }
	          else {
	            logger.error("登录接口login 产生密钥异常 err：");
	            ResultEntity<LoginEntity> result = new ResultEntity<LoginEntity>(0, "后台显示用户为空，但ca登录正确！", null);
	            out.write(gson.toJson(result));
	            out.flush();
	            out.close();
	          }
	          if (this.infromation.equals("0")) {
	            logger.error("登陆接口返回为：" + entity);
	          }

	          if ((!StringUtil.isEmpty(Config.getString("alibaichuan"))) && 
	            (Config.getString("alibaichuan").equals("yes"))) {
	            signUp(username, user.getXm());
	          }

	          String headPicturePath = null;
	          IMobileCommonService mobileCommonService = (IMobileCommonService)SpringHolder.getBean("mobileCommonService");
	          List imageList = mobileCommonService.getMyPicture(username);
	          ImageDB image = (imageList != null) && (imageList.size() > 0) ? (ImageDB)imageList.get(0) : null;
	          if (image == null) {
	            logger.error("我的门户接口:头像数据库图片不存在，路径也不存在！");
	            headPicturePath = "";
	          } else {
	            String path = image.getPath();
	            byte[] content = image.getFileContent();
	            String headname = image.getFileName();
	            String filename = StringUtil.isEmpty(headname) ? username + "headPicture" : headname;

	            if ((content == null) && (StringUtil.isEmpty(path))) {
	              logger.error("我的门户接口:头像数据库图片不存在，路径也不存在！");
	              headPicturePath = "";
	            } else {
	              String pathFile = BaseHolder.getPropertiesValue("MyPicture", "headPicture");
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

	                  if (content == null) {
	                    logger.error("我的门户接口:头像数据库图片不存在，路径也不存在！");
	                    headPicturePath = "";
	                  }
	                  ImageIO.setUseCache(false);
	                  ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);
	                  BufferedImage newImage = ImageIO.read(byteArrayInputStream);
	                  ImageIO.write(newImage, UploadFileUtil.checkedFileName(filename, new String[0]), outFile);
	                } catch (IOException e) {
	                  logger.error("登录接口login 我的头像生成产生异常err：");
	                  logger.error(e, e.fillInStackTrace());
	                }

	              }

	              headPicturePath = getImageHost() + path;
	              entity.setHeadPicturePath(headPicturePath);
	            }
	          }

	          ResultEntity<LoginEntity> result = new ResultEntity<LoginEntity>(1, "成功", entity);
	          out.write(gson.toJson(result));
	          out.flush();
	          out.close();
	        }
	      } catch (Exception e) {
	        e.printStackTrace();
	      }
	      logger.error("登录接口login 无权访问err：");
	    }
	    catch (IOException e)
	    {
	      logger.error("登录接口login 程序异常 err：");
	      e.printStackTrace();
	    }
	}
	
	/**
	 *获取商家列表 ---分页
	 * @param apptoken
	 * @param canteenname
	 * @param start
	 * @param size
	 * @return ResultEntity<ListEntity<Canteen>> result
	 * @author yangbilin
	 */
	public void getCanteenList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String canteenname="";
       	 	String startStr = null;
       	 	String sizeStr=null;
       	 	ResultEntity<ListEntity<Canteen>> result =null;
       	 	
       	 	String apptoken = request.getParameter("apptoken");
   	 		if(StringUtils.isNotBlank(request.getParameter("canteenname"))){
   	 		   canteenname = new String(request.getParameter("canteenname").getBytes("ISO8859-1"), "UTF-8");
   	 		}
   	 	    String latitude=StringUtil.isEmpty(request.getParameter("latitude")) ? "" : request.getParameter("latitude");
	 		String longitude=StringUtil.isEmpty(request.getParameter("longitude")) ? "" : request.getParameter("longitude");
   	 		String startInit=StringUtil.isEmpty(request.getParameter("start")) ? "" : request.getParameter("start");
   	 		String sizeInit=StringUtil.isEmpty(request.getParameter("size")) ? "" : request.getParameter("size");
   	 	
			Gson gson = new Gson();
			if(StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(startInit)|| StringUtil.isEmpty(sizeInit)
					|| StringUtil.isEmpty(canteenname)|| StringUtil.isEmpty(latitude)|| StringUtil.isEmpty(longitude)){
				result = new ResultEntity<ListEntity<Canteen>>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			try {
				if(StringUtils.isNotBlank(canteenname)){
					canteenname = CodeUtil.decode(canteenname, apptoken);
				}
				startStr = CodeUtil.decode(startInit, apptoken);
				sizeStr  = CodeUtil.decode(sizeInit, apptoken);
				latitude= CodeUtil.decode(latitude, apptoken);
				if("0".equals(latitude)){
					latitude=BaseHolder.getPropertiesValue("latitude");
				}
				longitude=CodeUtil.decode(longitude, apptoken);
				if("0".equals(longitude)){
					longitude=BaseHolder.getPropertiesValue("longitude");
				}
			} catch (Exception e) {
				result = new ResultEntity<ListEntity<Canteen>>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			ListEntity<Canteen> canteenList = new ListEntity<Canteen>();
			
			Canteen query = new Canteen(canteenname,"1");
			query.setToPage(Integer.valueOf(startStr));
			query.setPerPageSize(Integer.valueOf(sizeStr));
			//query.setLongitude(Double.parseDouble(longitude));
			//query.setLatitude(Double.parseDouble(latitude));
			
			PageList<Canteen> pagelist=canteenService.getPageList(query);
			if(pagelist!=null && pagelist.size()>0){
				int totalPage=pagelist.getPaginator().getPages();
				if(Integer.valueOf(startStr)<totalPage){
					canteenList.setOvered(true);
				}else{
					canteenList.setOvered(false);
				}
				//商品的图片集
				FileUntils fileUntils = new FileUntils();
				String readlPath=request.getSession().getServletContext().getRealPath("/");	
				for (Canteen canteen : pagelist) {
					String picpath=canteen.getPicPath();
					if(picpath!=null){
						String filePath=readlPath+picpath.substring(0,picpath.lastIndexOf("/")+1);
						filePath=filePath.replace("\\", "/");
						File folder = new File(filePath);
						if(!folder.exists()){
							folder.mkdir();
						}
						File file = new File(filePath,picpath.substring(picpath.lastIndexOf("/")+1,picpath.length()));
						if(!file.exists()){
							file.createNewFile();
							ImageDB imageDB=ImageDBUtil.getImageDBByGuid(canteen.getPicId());
							if(imageDB!=null){
								byte[] content=imageDB.getFileContent();
								FileOutputStream fileOutputStream = new FileOutputStream(file);
								fileOutputStream.write(content,0,content.length);
								fileOutputStream.close();
							}
						}
						//获取图片全路径
						canteen.setPicPath(fileUntils.getImageHost()+picpath);
					}
				}
				canteenList.setItemList(pagelist);
				result = new ResultEntity<ListEntity<Canteen>>(1, "成功",canteenList);
			}else{
				canteenList.setOvered(true);
				canteenList.setItemList(pagelist);
				result = new ResultEntity<ListEntity<Canteen>>(1, "成功",canteenList);
			}
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("获取商家列表异常getCanteenList  err：");
			e.printStackTrace();
		}
	}
	
	/**
	 *根据商家id获取该商家所有食品类别及该类别下的食品列表----不分页
	 * @param apptoken
	 * @param canteenid
	 * @return ResultEntity<List<Foodcataofcanteen>>
	 * @author yangbilin
	 */
	public void getCanteenDetailList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
       	 	//ResultEntity<ListEntity<Foodcataofcanteen>> result =null;
			ResultEntity<List<Foodcataofcanteen>> result =null;
       	 	String apptoken = request.getParameter("apptoken");
       	 	String canteenid=StringUtil.isEmpty(request.getParameter("canteenid")) ? "" : request.getParameter("canteenid");
       	 	
       	 	Gson gson = new Gson();
		 	if(StringUtil.isEmpty(canteenid) || StringUtil.isEmpty(apptoken)){
				result = new ResultEntity<List<Foodcataofcanteen>>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
		 	try {
		 		canteenid = CodeUtil.decode(canteenid, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<List<Foodcataofcanteen>>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			//ListEntity<Foodcataofcanteen> itemList = new ListEntity<Foodcataofcanteen>();
       	 	//根据商家id获取该商家下的食品类别
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("canteenId", canteenid);
			map.put("isactive", "1");
			List<Foodcataofcanteen> foodcataList=foodcataService.getFoodcataList(map);
       	 	if(foodcataList!=null&&foodcataList.size()>0){
       	 		for (Foodcataofcanteen foodcataofcanteen : foodcataList) {
       	 			/**
       	 			 * 根据类别id，获取类别下的食品
       	 			 * 并配置图片的全路径
       	 			 */
       	 		   map.put("foodcataId", foodcataofcanteen.getFoodcataId());
       	 		   List<Foodofcanteen> foodList= foodService.getFoodsList(map);
          	 	   if(foodList!=null&&foodList.size()>0){
	   	       	 	    FileUntils fileUntils = new FileUntils();
	   					String readlPath=request.getSession().getServletContext().getRealPath("/");	
	   					for (Foodofcanteen foods : foodList) {
	   						String picpath=foods.getPicPath();
	   						if(picpath!=null){
	   							String filePath=readlPath+picpath.substring(0,picpath.lastIndexOf("/")+1);
	   							filePath=filePath.replace("\\", "/");
	   							File folder = new File(filePath);
								if(!folder.exists()){
									folder.mkdir();
								}
	   							File file = new File(filePath,picpath.substring(picpath.lastIndexOf("/")+1,picpath.length()));
	   							if(!file.exists()){
	   								file.createNewFile();
	   								ImageDB imageDB=ImageDBUtil.getImageDBByGuid(foods.getPicId());
	   								if(imageDB!=null){
	   									byte[] content=imageDB.getFileContent();
	   									FileOutputStream fileOutputStream = new FileOutputStream(file);
	   									fileOutputStream.write(content,0,content.length);
	   									fileOutputStream.close();
	   								}
	   							}
	   							foods.setPicPath(fileUntils.getImageHost()+picpath);
	   						}
	   					}
	   					foodcataofcanteen.setFoodlist(foodList);
          	 	    }
				}
       	 		//itemList.setItemList(foodcataList);
       	 		result = new ResultEntity<List<Foodcataofcanteen>>(1, "成功", foodcataList);
       	 	}else{
	       	 	result = new ResultEntity<List<Foodcataofcanteen>>(0, "该商家下暂无数据", null);
       	 	}
   	 		out.write(gson.toJson(result));
   	 		out.flush();
   	 		out.close();
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("获取商家食品列表异常getCanteenDetailList err：");
			e.printStackTrace();
		}
	}
	
	/**
	 *下单
	 *@param  apptoken
	 *@param data:传递属性参数的封装参数
	 *@return ResultEntity<String>
	 *@author yangbilin 
	 */
	public void placeOrder(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			ResultEntity<String> result =null;
       	 	String apptoken = request.getParameter("apptoken");
       	 	String data=StringUtil.isEmpty(request.getParameter("data")) ? "" : request.getParameter("data");
       	 	Gson gson = new Gson();
		 	if(StringUtil.isEmpty(data) || StringUtil.isEmpty(apptoken)){
				result = new ResultEntity<String>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
		 	try {
		 		data = CodeUtil.decode(data, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			/**
			 *下单同步 
			 */
			synchronized (this) {
				JSONObject obj = JSONObject.fromObject(data);
				if(obj!=null){
					String userid = obj.getString("userid");
					//用餐人数
					int personnum = Integer.parseInt(obj.getString("personnumber"));
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					//送达时间
					Date deliverytime = sdf.parse(obj.getString("arrivetime"));
					//备注
					String description = obj.getString("description");
					String addressId = obj.getString("addressid");
					String canteenId = obj.getString("canteenid");
					//总价
					String summation = obj.getString("summation");
					//下单的食品封装list
					JSONArray list = obj.getJSONArray("list");
					if(list!=null&&list.size()>0){
						/**
						 *下单，保存订单信息 
						 */
						String orderid = UUID.randomUUID().toString();
						String orderId=orderid.replace("-", "");
						Orderofcanteen order = new Orderofcanteen(orderId,userid,addressId,personnum,description,
								canteenId,deliverytime,summation);
						int issuccess=orderService.placeOrder(order);
						if(issuccess>0){
							/**
							 *保存，订单和食品一对多关系 
							 */
							for(int i=0;i<list.size();i++){
								String foodId=list.getJSONObject(i).getString("foodid");
								int amount = Integer.parseInt(list.getJSONObject(i).getString("amount"));
								/**
								 *判断食品订单数量
								 *更新食品库存
								 */
								Foodofcanteen foods=foodService.findById(foodId);
								if(foods!=null){
									int storage = foods.getStorage();
									if(amount>storage){
										result = new ResultEntity<String>(0, "库存数量不足",null);
										out.write(gson.toJson(result));
										out.flush();
										out.close();
										return;
									}else{
										//保存，订单和食品一对多关系 
										Foodorder query=new Foodorder(foodId,orderId,amount);
										foodorderService.insertOrders(query);
										//更新食品库存
										int remaincount=storage-amount;
										foodService.updateStorage(new Foodofcanteen(foodId,remaincount));
									}
								}
							}
						}
					}
					result = new ResultEntity<String>(1, "成功",null);
				}else{
					result = new ResultEntity<String>(0, "获取订单数据失败",null);
				}
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("下单 程序异常placeOrder err：");
			e.printStackTrace();
		}
	}
	
	
	/**
	 *根据用户id获取当前用户的送餐地址列表----分页
	 *@param apptoken
	 *@param username
	 *@param start
	 *@param size
	 *@return ResultEntity<ListEntity<Canteenaddress>>
	 *@author yangbilin 
	 */
	public void getAddressListByUser(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
        try {
        	PrintWriter out = response.getWriter();
        	String username = null;
       	 	String startStr = null;
       	 	String sizeStr=null;
       	 	ResultEntity<ListEntity<Canteenaddress>> result = null;
       	 	
       	 	String apptoken=request.getParameter("apptoken");
       	    if(StringUtils.isNotBlank(request.getParameter("username"))){
	     		username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	     	}
       	    String startInit=StringUtil.isEmpty(request.getParameter("start")) ? "" : request.getParameter("start");
		 	String sizeInit=StringUtil.isEmpty(request.getParameter("size")) ? "" : request.getParameter("size");
		 	Gson gson = new Gson();
		 	if(StringUtil.isEmpty(username) || StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(startInit)
		 			||StringUtil.isEmpty(sizeInit)){
		 		result = new ResultEntity<ListEntity<Canteenaddress>>(0, "参数传值出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	if(!ApptokenUtils.compare(username, apptoken)){
		 		result = new ResultEntity<ListEntity<Canteenaddress>>(2, "app_token error!", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	try {
				username = CodeUtil.decode(username, apptoken);
				startStr = CodeUtil.decode(startInit, apptoken);
				sizeStr  = CodeUtil.decode(sizeInit, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<ListEntity<Canteenaddress>>(0, "加密方式出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			ListEntity<Canteenaddress> itemList = new ListEntity<Canteenaddress>();
			
			Canteenaddress query = new Canteenaddress();
			query.setUserId(username);
			query.setToPage(Integer.valueOf(startStr));
			query.setPerPageSize(Integer.valueOf(sizeStr));
			
			PageList<Canteenaddress> pagelist=addressService.getPageList(query);
			if(pagelist!=null&&pagelist.size()>0){
				int totalPage=pagelist.getPaginator().getPages();
				if(Integer.valueOf(startStr)<totalPage){
					itemList.setOvered(true);
				}else{
					itemList.setOvered(false);
				}
				itemList.setItemList(pagelist);
				result = new ResultEntity<ListEntity<Canteenaddress>>(1,"成功",itemList);
			}else{
				itemList.setOvered(true);
				itemList.setItemList(pagelist);
				result = new ResultEntity<ListEntity<Canteenaddress>>(1, "成功",itemList);
			}
			out.write(gson.toJson(result));
			out.flush();
			out.close();
        }catch (Exception e) {
        	logger.error("获取个人送餐地址getAddressListByUser   err：");
			logger.error(e, e.fillInStackTrace());
        }
	}
	
	/**
	 *增加/修改并保存送餐地址
	 *@param apptoken
	 *@param mobilephone
	 *@param username
	 *@param name
	 *@param schoolname
	 *@param specificaddress
	 *@param method ["add"|"update"]
	 *@param addressid
	 *@return ResultEntity<Canteenaddress>
	 * @author yangbilin
	 */
	public void submitAddressForUser(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
        try {
        	PrintWriter out = response.getWriter();
        	ResultEntity<Canteenaddress> result =null;
        	String username = null;
        	String name=null;
        	String schoolName=null;
        	String specificAddress=null;
        	
        	String apptoken = request.getParameter("apptoken");
        	String method = request.getParameter("method");
        	String mobilePhone=StringUtil.isEmpty(request.getParameter("mobilephone")) ? "" : request.getParameter("mobilephone");
        	String addressId=StringUtil.isEmpty(request.getParameter("addressid")) ? "" : request.getParameter("addressid");
        	if(StringUtils.isNotBlank(request.getParameter("username"))){
        		username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
        	}
        	if(StringUtils.isNotBlank(request.getParameter("name"))){
        		name = new String(request.getParameter("name").getBytes("ISO8859-1"), "UTF-8");
        	}
        	if(StringUtils.isNotBlank(request.getParameter("schoolname"))){
        		schoolName = new String(request.getParameter("schoolname").getBytes("ISO8859-1"), "UTF-8");
        	}
        	if(StringUtils.isNotBlank(request.getParameter("specificaddress"))){
        		specificAddress = new String(request.getParameter("specificaddress").getBytes("ISO8859-1"), "UTF-8");
        	}
		 	
			Gson gson = new Gson();
			if(!ApptokenUtils.compare(username, apptoken)){
				result = new ResultEntity<Canteenaddress>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			if(StringUtil.isEmpty(username)||StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(method)){
				result = new ResultEntity<Canteenaddress>(0, "方法参数不能为空", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			try {
				method = CodeUtil.decode(method, apptoken);
				username = CodeUtil.decode(username, apptoken);
				if("add".equals(method)){
					if(StringUtil.isBlank(schoolName)|| StringUtil.isBlank(specificAddress)|| StringUtil.isBlank(name)
							||StringUtil.isBlank(mobilePhone)){
						result = new ResultEntity<Canteenaddress>(0, "参数传值出错！", null);
						out.write(gson.toJson(result));
						out.flush();
						out.close();
						return;
					}
					name = CodeUtil.decode(name, apptoken);
					mobilePhone=CodeUtil.decode(mobilePhone, apptoken);
					schoolName = CodeUtil.decode(schoolName, apptoken);
					specificAddress = CodeUtil.decode(specificAddress, apptoken);
				}else if("update".equals(method)){
					if(StringUtil.isBlank(addressId)){
						result = new ResultEntity<Canteenaddress>(0, "参数传值出错！", null);
						out.write(gson.toJson(result));
						out.flush();
						out.close();
						return;
					}
					addressId = CodeUtil.decode(addressId, apptoken);
					if(StringUtils.isNotBlank(name)){
						name = CodeUtil.decode(name, apptoken);
					}
					if(StringUtils.isNotBlank(mobilePhone)){
						mobilePhone = CodeUtil.decode(mobilePhone, apptoken);
					}
					if(StringUtils.isNotBlank(schoolName)){
						schoolName = CodeUtil.decode(schoolName, apptoken);
					}
					if(StringUtils.isNotBlank(specificAddress)){
						specificAddress = CodeUtil.decode(specificAddress, apptoken);
					}
				}
			} catch (Exception e) {
				result = new ResultEntity<Canteenaddress>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			Canteenaddress query =new Canteenaddress(username,name,mobilePhone,schoolName,specificAddress);
			int flag=0;
			if("add".equals(method)){
				flag=addressService.insertAddress(query);
			}else{
				query.setAddressId(addressId);
				flag=addressService.updateAddress(query);
			}
			if(flag>0){
				result = new ResultEntity<Canteenaddress>(1, "成功",null);
			}else{
				result = new ResultEntity<Canteenaddress>(0, "失败",null);
			}
			out.write(gson.toJson(result));
			out.flush();
			out.close();
        }catch (Exception e) {
        	logger.error("个人送餐地址submitAddressForUser err：");
			logger.error(e, e.fillInStackTrace());
        }	
	}
	
	/**
	 *删除用户送餐地址
	 *@param apptoken
	 *@param addressid
	 *@param username
	 *@return ResultEntity<Canteenaddress>
	 *@author yangbilin
	 */
	public void deleteAddressByUser(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
        try {
        	PrintWriter out = response.getWriter();
        	ResultEntity<Canteenaddress> result =null;
        	String username = null;
        	
        	String apptoken = request.getParameter("apptoken");
        	String addressId=StringUtil.isEmpty(request.getParameter("addressid")) ? "" : request.getParameter("addressid");
        	if(StringUtils.isNotBlank(request.getParameter("username"))){
        		username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
        	}
        	
			Gson gson = new Gson();
		 	if(StringUtil.isEmpty(username)|| StringUtil.isEmpty(apptoken)||StringUtil.isBlank(addressId)){
				result = new ResultEntity<Canteenaddress>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
		 	if(!ApptokenUtils.compare(username, apptoken)){
				result = new ResultEntity<Canteenaddress>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
		 	try {
				username = CodeUtil.decode(username, apptoken);
				addressId = CodeUtil.decode(addressId, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<Canteenaddress>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			Canteenaddress query  =new Canteenaddress();
			query.setAddressId(addressId);
			query.setUserId(username);
			int flag=addressService.deleteAddress(query);
			if(flag>0){
				result = new ResultEntity<Canteenaddress>(1, "成功",null);
			}else{
				result = new ResultEntity<Canteenaddress>(0, "删除送餐地址失败",null);
			}
			out.write(gson.toJson(result));
			out.flush();
			out.close();
        }catch (Exception e) {
        	logger.error("删除个人送餐地址deleteAddressByUser err：");
			logger.error(e, e.fillInStackTrace());
        }	
	}
	
	/**
	 *根据当前用户userid获取该用户的订单列表---分页
	 *@param apptoken
	 *@param username
	 *@param start
	 *@param size
	 *@return ResultEntity<ListEntity<Orderofcanteen>>
	 *@author yangbilin
	 */
	public void getOrderlistByUser(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
        try {
        	PrintWriter out = response.getWriter();
        	String username = null;
       	 	String startStr = null;
       	 	String sizeStr=null;
       	 	ResultEntity<ListEntity<Orderofcanteen>> result = null;
       	 	
       	 	String apptoken=request.getParameter("apptoken");
       	    if(StringUtils.isNotBlank(request.getParameter("username"))){
	     		username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	     	}
       	    String startInit=StringUtil.isEmpty(request.getParameter("start")) ? "" : request.getParameter("start");
		 	String sizeInit=StringUtil.isEmpty(request.getParameter("size")) ? "" : request.getParameter("size");
		 	
		 	Gson gson = new Gson();
		 	if(StringUtil.isEmpty(username) || StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(startInit)
		 			||StringUtil.isEmpty(sizeInit)){
		 		result = new ResultEntity<ListEntity<Orderofcanteen>>(0, "参数传值出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	if(!ApptokenUtils.compare(username, apptoken)){
		 		result = new ResultEntity<ListEntity<Orderofcanteen>>(2, "app_token error!", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	try {
				username = CodeUtil.decode(username, apptoken);
				startStr = CodeUtil.decode(startInit, apptoken);
				sizeStr  = CodeUtil.decode(sizeInit, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<ListEntity<Orderofcanteen>>(0, "加密方式出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			ListEntity<Orderofcanteen> itemList = new ListEntity<Orderofcanteen>();
			
			Orderofcanteen query = new Orderofcanteen();
			query.setToPage(Integer.valueOf(startStr));
			query.setPerPageSize(Integer.valueOf(sizeStr));
			query.setUserId(username);
			PageList<Orderofcanteen> pagelist=orderService.getPageList(query);
			
			if(pagelist!=null&&pagelist.size()>0){
				int totalPage=pagelist.getPaginator().getPages();
				if(Integer.valueOf(startStr)<totalPage){
					itemList.setOvered(true);
				}else{
					itemList.setOvered(false);
				}
				//商品的图片集
				FileUntils fileUntils = new FileUntils();
				String readlPath=request.getSession().getServletContext().getRealPath("/");	
				for (Orderofcanteen orderofcanteen : pagelist) {
					String canteenid = orderofcanteen.getCanteenId();
					if(StringUtils.isNotBlank(canteenid)){
						/**
						 * 获取该订单的商家信息，如商家名称、商家图片、商家优惠、餐盒费、商家电话等等
						 */
						Canteen canteen=canteenService.findById(canteenid);
						if(canteen!=null){
							String picpath=canteen.getPicPath();
							if(picpath!=null){
								String filePath=readlPath+picpath.substring(0,picpath.lastIndexOf("/")+1);
								filePath=filePath.replace("\\", "/");
								File folder = new File(filePath);
								if(!folder.exists()){
									folder.mkdir();
								}
								File file = new File(filePath,picpath.substring(picpath.lastIndexOf("/")+1,picpath.length()));
								if(!file.exists()){
									file.createNewFile();
									ImageDB imageDB=ImageDBUtil.getImageDBByGuid(canteen.getPicId());
									if(imageDB!=null){
										byte[] content=imageDB.getFileContent();
										FileOutputStream fileOutputStream = new FileOutputStream(file);
										fileOutputStream.write(content,0,content.length);
										fileOutputStream.close();
									}
								}
								canteen.setPicPath(fileUntils.getImageHost()+picpath);
							}
							orderofcanteen.setCanteen(canteen);
						}
					}
				}
				itemList.setItemList(pagelist);
				result = new ResultEntity<ListEntity<Orderofcanteen>>(1, "成功",itemList);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}else{
				itemList.setOvered(true);
				itemList.setItemList(pagelist);
				result = new ResultEntity<ListEntity<Orderofcanteen>>(1, "成功",itemList);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("获取个人订单列表异常getOrderlistByUser  err：");
			e.printStackTrace();
		}
	}
	
	/**
	 *根据订单id获取当前订单详情
	 * @param apptoken
	 * @param username
	 * @param orderid
	 * @return ResultEntity<Orderofcanteen>
	 * @author yangbilin
	 */
	public void findOrderDetail(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
        try {
        	PrintWriter out = response.getWriter();
        	String username = null;
       	 	ResultEntity<Orderofcanteen> result = null;
       	 	
       	 	String apptoken=request.getParameter("apptoken");
       	    if(StringUtils.isNotBlank(request.getParameter("username"))){
	     		username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	     	}
       	    String orderId=StringUtil.isEmpty(request.getParameter("orderid")) ? "" : request.getParameter("orderid");
		 
       	    Gson gson = new Gson();
		 	if(StringUtil.isEmpty(username) || StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(orderId)){
		 		result = new ResultEntity<Orderofcanteen>(0, "参数传值出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	if(!ApptokenUtils.compare(username, apptoken)){
		 		result = new ResultEntity<Orderofcanteen>(2, "app_token error!", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	try {
				username = CodeUtil.decode(username, apptoken);
				orderId = CodeUtil.decode(orderId, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<Orderofcanteen>(0, "加密方式出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			if(StringUtils.isNotBlank(orderId)){
			   Orderofcanteen order = orderService.findById(orderId);
			   if(order!=null){
				   /**
				    *获取该订单的配送地址、接收人、接收电话等等 
				    */
				   Canteenaddress address=addressService.findById(order.getAddressid());
				   if(address!=null){
					   order.setAddress(address);
				   }
				   /**
				    *获取该订单的食品列表
				    */
				   Map<String,Object> params = new HashMap<String, Object>();
				   params.put("orderId", orderId);
				   
				   List<Foodofcanteen> foodslist = foodService.getFoodsListFororder(params);
				   if(foodslist!=null&&foodslist.size()>0){
					   //食品图片集
					   FileUntils fileUntils = new FileUntils();
					   String readlPath=request.getSession().getServletContext().getRealPath("/");	
					   for (Foodofcanteen foods : foodslist) {
						   String picpath=foods.getPicPath();
						   if(picpath!=null){
							   String filePath=readlPath+picpath.substring(0,picpath.lastIndexOf("/")+1);
							   filePath=filePath.replace("\\", "/");
							   File folder = new File(filePath);
								if(!folder.exists()){
									folder.mkdir();
								}
							   File file = new File(filePath,picpath.substring(picpath.lastIndexOf("/")+1,picpath.length()));
							   if(!file.exists()){
								   file.createNewFile();
								   ImageDB imageDB=ImageDBUtil.getImageDBByGuid(foods.getPicId());
								   if(imageDB!=null){
									   byte[] content=imageDB.getFileContent();
									   FileOutputStream fileOutputStream = new FileOutputStream(file);
									   fileOutputStream.write(content,0,content.length);
									   fileOutputStream.close();
								   }
							   }
							   //食品配图的全路径
							   foods.setPicPath(fileUntils.getImageHost()+picpath);
						   }
					   }
					   order.setFoodlist(foodslist);
				   }
				   result = new ResultEntity<Orderofcanteen>(1, "成功",order); 
			   }else{
				   result = new ResultEntity<Orderofcanteen>(0, "暂无数据", null);
			   }
			   out.write(gson.toJson(result));
			   out.flush();
			   out.close();
			}else{
				result = new ResultEntity<Orderofcanteen>(0, "暂无该订单", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("获取订单详情程序异常findOrderDetail err：");
			e.printStackTrace();
		}
	}
	
	/**
	 *申请直播间 
	 * @param apptoken
	 * @param username
	 * @return ResultEntity<WebcastsAuditEntity>
	 * @author yangbilin
	 */
	public void application(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
        try {
        	PrintWriter out = response.getWriter();
        	ResultEntity<WebcastsAuditEntity> result =null;
        	String username=null;
        	String appreason=null;
        	String apptoken = request.getParameter("apptoken");
        	if(StringUtils.isNotBlank(request.getParameter("username"))){
	     		username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	     	}
        	if(StringUtils.isNotBlank(request.getParameter("appreason"))){
        		appreason = new String(request.getParameter("appreason").getBytes("ISO8859-1"), "UTF-8");
	     	}
        	
        	Gson gson = new Gson();
		 	if(StringUtil.isEmpty(apptoken)|| StringUtil.isBlank(username)){
				result = new ResultEntity<WebcastsAuditEntity>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
		 	
		 	if(!ApptokenUtils.compare(username, apptoken)){
		 		result = new ResultEntity<WebcastsAuditEntity>(2, "app_token error!", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	try {
				username = CodeUtil.decode(username, apptoken);
				if(StringUtil.isNotBlank(appreason)){
					appreason = CodeUtil.decode(appreason, apptoken);
				}
			} catch (Exception e) {
				result = new ResultEntity<WebcastsAuditEntity>(0, "加密方式出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			WebcastsAuditEntity entity = auditService.getResultByUserid(username);
			if(entity!=null){
				if(StringUtils.isNotBlank(entity.getIsaudit())){
					if("0".equals(entity.getIsaudit())||"1".equals(entity.getIsaudit())){
						result = new ResultEntity<WebcastsAuditEntity>(0, "对不起,您已申请过直播间开通,无需重复申请",null);
						out.write(gson.toJson(result));
						out.flush();
						out.close();
						return;
					}
				}
			}
			entity = new WebcastsAuditEntity();
			entity.setUserid(username);
			entity.setAppreason(appreason);
			auditService.application(entity);
			result = new ResultEntity<WebcastsAuditEntity>(1, "成功",entity);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
        }catch (Exception e) {
        	logger.error("申请开通直播间application err：");
			logger.error(e, e.fillInStackTrace());
        }
	}
	
	/**
	 * 获取用户的申请和审核信息
	 * @param apptoken
	 * @param username
	 * @return ResultEntity<WebcastsAuditEntity>
	 * @author yangbilin
	 */
	public void getResultByUserid(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
        try {
        	PrintWriter out = response.getWriter();
        	ResultEntity<WebcastsAuditEntity> result =null;
        	String username="";
        	String apptoken = request.getParameter("apptoken");
        	if(StringUtils.isNotBlank(request.getParameter("username"))){
	     		username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	     	}
        	Gson gson = new Gson();
		 	if(StringUtil.isEmpty(apptoken)|| StringUtil.isBlank(username)){
				result = new ResultEntity<WebcastsAuditEntity>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
		 	
		 	if(!ApptokenUtils.compare(username, apptoken)){
		 		result = new ResultEntity<WebcastsAuditEntity>(2, "app_token error!", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	try {
				username = CodeUtil.decode(username, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<WebcastsAuditEntity>(0, "加密方式出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			WebcastsAuditEntity entity = auditService.getResultByUserid(username);
			if(entity!=null){
				result = new ResultEntity<WebcastsAuditEntity>(1, "成功",entity);
			}else{
				result = new ResultEntity<WebcastsAuditEntity>(0, "获取用户申请信息失败",null);
			}
			out.write(gson.toJson(result));
			out.flush();
			out.close();
        }catch (Exception e) {
        	logger.error("获取用户的申请和审核信息getResultByUserid err：");
			logger.error(e, e.fillInStackTrace());
        }
	}
	

	/**
	 *获取直播间列表getWebcastsList  ---分页
	 * @param apptoken
	 * @param anchorname
	 * @param start
	 * @param size
	 * @return ResultEntity<ListEntity<WebcastsEntity>> result
	 * @author yangbilin
	 */
	public void getWebcastsList(){
		System.out.println("开始----"+System.currentTimeMillis());
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
        try {
        	PrintWriter out = response.getWriter();
        	String anchorname = null;
       	 	String startStr = null;
       	 	String sizeStr=null;
        	ResultEntity<ListEntity<WebcastsEntity>> result =null;
        	
        	String apptoken = request.getParameter("apptoken");
		 	if(StringUtils.isNotBlank(request.getParameter("anchorname"))){
		 		anchorname = new String(request.getParameter("anchorname").getBytes("ISO8859-1"), "UTF-8");
        	}
		 	String startInit=StringUtil.isEmpty(request.getParameter("start")) ? "" : request.getParameter("start");
		 	String sizeInit=StringUtil.isEmpty(request.getParameter("size")) ? "" : request.getParameter("size");
		 	
			Gson gson = new Gson();
		 	if(StringUtil.isEmpty(apptoken)|| StringUtil.isBlank(startInit)|| StringUtil.isBlank(sizeInit)){
				result = new ResultEntity<ListEntity<WebcastsEntity>>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
		 	
		 	try {
				if(StringUtils.isNotBlank(anchorname)){
					anchorname = CodeUtil.decode(anchorname, apptoken);
				}
				startStr = CodeUtil.decode(startInit, apptoken);
				sizeStr  = CodeUtil.decode(sizeInit, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<ListEntity<WebcastsEntity>>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			ListEntity<WebcastsEntity> castslist =new  ListEntity<WebcastsEntity>();
			WebcastsEntity query=new WebcastsEntity();
			query.setToPage(Integer.valueOf(startStr));
			query.setPerPageSize(Integer.valueOf(sizeStr));
			query.setAnchorName(anchorname);
			query.setIsactive("1");
			query.setStatus("1");
			
		 	List<WebcastsEntity> pagelist= webcastService.getPageList(query);
		 	if(pagelist!=null&&pagelist.size()>0){
		 		query.setTotalItem(pagelist.size());	
				int totalPage=query.getTotalPage();
				if(Integer.valueOf(startStr)<totalPage){
					castslist.setOvered(true);
				}else{
					castslist.setOvered(false);
				}
				
				FileUntils fileUntils = new FileUntils();
				String readlPath=request.getSession().getServletContext().getRealPath("/");					
				for (WebcastsEntity webcasts : pagelist) {
					String picpath=webcasts.getPicPath();
					byte[] piccon=webcasts.getPiccon();
					if(picpath!=null){
						String filePath=readlPath+picpath.substring(0,picpath.lastIndexOf("/")+1);
						filePath=filePath.replace("\\", "/");
						File folder = new File(filePath);
						if(!folder.exists()){
							folder.mkdir();
						}
						File file = new File(filePath,picpath.substring(picpath.lastIndexOf("/")+1,picpath.length()));
						if(!file.exists()){
							file.createNewFile();
							if(piccon!=null){
								FileOutputStream fileOutputStream = new FileOutputStream(file);
								fileOutputStream.write(piccon,0,piccon.length);
								fileOutputStream.close();
							}
						}
						//获取图片全路径
						webcasts.setPicPath(fileUntils.getImageHost()+picpath);
					}
					webcasts.setPiccon(null);
				}
				castslist.setItemList(pagelist);
				System.out.println("结束1----"+System.currentTimeMillis());
				result = new ResultEntity<ListEntity<WebcastsEntity>>(1, "成功", castslist);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
		 	}else{
		 		castslist.setOvered(true);
		 		castslist.setItemList(pagelist);
		 		System.out.println("结束2----"+System.currentTimeMillis());
				result = new ResultEntity<ListEntity<WebcastsEntity>>(1, "成功", castslist);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
        }catch (Exception e) {
        	logger.error("获取直播间列表getWebcastsList err：");
			logger.error(e, e.fillInStackTrace());
        }
        
	}
	
	/**
	 *创建直播间
	 *@param apptoken  
	 *@param userid
	 *@param anchorname
	 *@param roomname
	 *@param piccon
	 *@param data.description 
	 */
	public void submitWebcastForUser(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String filePath = request.getSession().getServletContext().getRealPath("/");
		try {
			PrintWriter out=response.getWriter();
			ResultEntity<WebcastsEntity> result =null;
			boolean res=false;
			String apptoken =null;
			byte[] content = null;
			
			Gson gson = new Gson();
			String username="",anchorname="",roomname="",description="",filename="";
			
			FileItemFactory factory = new DiskFileItemFactory();  
		    ServletFileUpload upload = new ServletFileUpload(factory);  
		    List<FileItem> items = new ArrayList<FileItem>();
		    items = upload.parseRequest(request);
		   // 得到所有的文件  
            Iterator<FileItem> it = items.iterator();  
            while (it.hasNext()) {  
                FileItem fItem = (FileItem) it.next();
                String paramName = "";  
                Object fValue = null;  
                if(fItem.isFormField()){
                	paramName = fItem.getFieldName();  
                    fValue = fItem.getString("UTF-8");
                    if(paramName.equals("apptoken")){
                    	 apptoken = fValue.toString();
                    }else if(paramName.equals("username")){
                    	username = fValue.toString();
                    }else if(paramName.equals("anchorname")){
                    	anchorname = fValue.toString();
                    }else if(paramName.equals("roomname")){
                    	roomname = fValue.toString();
                    }else if(paramName.equals("description")){
                    	description = fValue.toString();
                    }
                }else{
                	filename = fItem.getName();//文件名称
                    if(StringUtils.isBlank(filename)){
                    	return;
                    }
                    content = fItem.get();
                }
             }
             if(!ApptokenUtils.compare(username, apptoken)){
	    		commonResult(2,"app_token error!",out,gson);
	    		return;
	    	 }
	    	 if(StringUtils.isNotBlank(username)){
	    		username=CodeUtil.decode(username, apptoken);
	    	 }else{
	    		commonResult(0,"用户名参数加密失败",out,gson);
	    		return;
	    	 }
	    	 
	    	 WebcastsAuditEntity audit = auditService.getResultByUserid(username);
	    	 if(audit!=null){
	    		if("0".equals(audit.getIsaudit())){
	    			commonResult(0,"当前用户已经提交申请,请等待管理员审核",out,gson);
	    			return;
	    		}else if("2".equals(audit.getIsaudit())){
	    			commonResult(0,"当前用户申请已被驳回,请重新申请",out,gson);
	    			return;
	    		}
	    	 }else{
	    		commonResult(0,"当前用户还未申请,请申请通过后创建直播间",out,gson);
    			return;
	    	 }
	    	 
	    	 WebcastsEntity webcast2 =  webcastService.getWebcastsByUserid(username);
	    	 if(webcast2!=null){
	    		 if("1".equals(webcast2.getIsactive())){
	    			 commonResult(0,"当前用户已拥有直播间,无需重复创建",out,gson);
		    		 return;
	    		 }else if("0".equals(webcast2.getIsactive())){
	    			 webcastService.deleteWebcasts(webcast2.getWebcastId());
	    		 }
	    	 }
	    	 
	    	 if(StringUtils.isNotBlank(anchorname)){
	    		anchorname=CodeUtil.decode(anchorname, apptoken);
	    	 }else{
	    		commonResult(0,"主播名称参数加密失败",out,gson);
	    		return;
	    	 }
	    	 if(StringUtils.isNotBlank(roomname)){
	    		roomname=CodeUtil.decode(roomname, apptoken);
	    	 }else{
	    		commonResult(0,"直播间名称参数加密失败",out,gson);
	    		return;
	    	 }
	    	 if(StringUtils.isNotBlank(description)){
	    		description=CodeUtil.decode(description, apptoken);
	    	 }else{
	    		commonResult(0,"直播间描述参数加密失败",out,gson);
	    		return;
	    	 }
	    	 WebcastsEntity webcast = new WebcastsEntity();
	    	 
	    	 if(content != null){
	    		String suffix = filename.substring(filename.lastIndexOf(".")+1,filename.length());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String datestr = sdf.format(new Date());
				String newfilename =RandomStringUtils.randomAlphabetic(10)+"_"+datestr+"."+suffix;
				String absoluteFilePath = filePath+"webcastPicture/"; 
				absoluteFilePath = absoluteFilePath.replace("\\", "/");
				File newFile = new File(absoluteFilePath);
				if (!newFile.exists()) {
					newFile.mkdir();
				}
				File outFile = new File(absoluteFilePath, newfilename);
				if (!outFile.exists()) {
					outFile.createNewFile();
				} else {
					outFile.delete();
					outFile.createNewFile();
				}
				FileOutputStream fileOutputStream = new FileOutputStream(outFile);
				fileOutputStream.write(content, 0, content.length);
				fileOutputStream.close();
				
				webcast.setPiccon(content);
				webcast.setPicPath("webcastPicture/"+newfilename);
	    	 }
	    	   webcast.setUserid(username);
	    	   webcast.setAnchorName(anchorname);
	    	   webcast.setRoomName(roomname);
	    	   webcast.setDescription(description);
	           webcastService.insert(webcast);
	           res=true;
               if(res){         	
    	            //commonResult(1,"成功",out,gson);
    	            WebcastsEntity webcast3  = webcastService.getWebcastsByUserid(username);
    	            result = new ResultEntity<WebcastsEntity>(1,"创建直播间成功",webcast3);
    	    		out.write(gson.toJson(result));
    	    		out.flush();
    	    		out.close();
    	       }else{
    	            commonResult(0,"创建直播间失败",out,gson);
    	       }
    		} catch (Exception e) {
    			e.printStackTrace();
    			logger.error("我的移动直播间上传submitWebcastForUser失败  err：");
    			logger.error(e, e.fillInStackTrace());
    		} 
	}
	
	/**
	 *修改直播间
	 *@param apptoken 
	 *@param username
	 *@param webcastId
	 *@param roomname
	 *@param piccon
	 */
	public void updateWebcastForUser(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String filePath = request.getSession().getServletContext().getRealPath("/");
		try {
			PrintWriter out=response.getWriter();
			ResultEntity<WebcastsEntity> result =null;
			String apptoken =null;
			byte[] content = null;
			
			Gson gson = new Gson();
			String username="",webcastId="",roomname="",filename="";
			WebcastsEntity webcast = new WebcastsEntity();
			
			FileItemFactory factory = new DiskFileItemFactory();  
		    ServletFileUpload upload = new ServletFileUpload(factory);  
		    List<FileItem> items = new ArrayList<FileItem>();
		    items = upload.parseRequest(request);
		   // 得到所有的文件  
            Iterator<FileItem> it = items.iterator();  
            while (it.hasNext()) {  
                FileItem fItem = (FileItem) it.next();
                String paramName = "";  
                Object fValue = null;  
                if(fItem.isFormField()){
                	paramName = fItem.getFieldName();  
                    fValue = fItem.getString("UTF-8");
                    if(paramName.equals("apptoken")){
                    	 apptoken = fValue.toString();
                    }else if(paramName.equals("username")){
                    	username = fValue.toString();
                    }else if(paramName.equals("webcastid")){
                    	webcastId = fValue.toString();
                    }else if(paramName.equals("roomname")){
                    	roomname = fValue.toString();
                    }
                }else{
                	filename = fItem.getName();//文件名称
                    if(StringUtils.isBlank(filename)){
                    	return;
                    }
                    content = fItem.get();
                }
             }
             if(!ApptokenUtils.compare(username, apptoken)){
	    		commonResult(2,"app_token error!",out,gson);
	    		return;
	    	 }
	    	 if(StringUtils.isNotBlank(username)){
	    		username=CodeUtil.decode(username, apptoken);
	    	 }else{
	    		commonResult(0,"用户名参数加密失败",out,gson);
	    		return;
	    	 }
	    	 if(StringUtils.isNotBlank(webcastId)){
	    		 webcastId=CodeUtil.decode(webcastId, apptoken);
	    	 }else{
	    		commonResult(0,"直播间id参数加密失败",out,gson);
	    		return;
	    	 }
	    	 if(StringUtils.isNotBlank(roomname)){
	    		roomname=CodeUtil.decode(roomname, apptoken);
	    	 }else{
	    		commonResult(0,"直播间名称参数加密失败",out,gson);
	    		return;
	    	 }
	    	 
	    	 if(content != null){
	    		String suffix = filename.substring(filename.lastIndexOf(".")+1,filename.length());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String datestr = sdf.format(new Date());
				String newfilename =RandomStringUtils.randomAlphabetic(10)+"_"+datestr+"."+suffix;
				String absoluteFilePath = filePath+"webcastPicture/"; 
				absoluteFilePath = absoluteFilePath.replace("\\", "/");
				File newFile = new File(absoluteFilePath);
				if (!newFile.exists()) {
					newFile.mkdir();
				}
				File outFile = new File(absoluteFilePath, newfilename);
				if (!outFile.exists()) {
					outFile.createNewFile();
				} else {
					outFile.delete();
					outFile.createNewFile();
				}
				FileOutputStream fileOutputStream = new FileOutputStream(outFile);
				fileOutputStream.write(content, 0, content.length);
				fileOutputStream.close();
				
				webcast.setPiccon(content);
				webcast.setPicPath("webcastPicture/"+newfilename);
	    	 }
	    	   webcast.setWebcastId(webcastId);
	    	   webcast.setUserid(username);
	    	   webcast.setRoomName(roomname);
	           int flag = webcastService.update(webcast);
               if(flag==1){
            	   WebcastsEntity webcast2  = webcastService.findById(webcastId);
   	               result = new ResultEntity<WebcastsEntity>(1,"成功",webcast2);
	   	           out.write(gson.toJson(result));
		    	   out.flush();
		    	   out.close();
    	       }else{
    	            commonResult(0,"移动直播间修改失败",out,gson);
    	       }
    		} catch (Exception e) {
    			e.printStackTrace();
    			logger.error("我的移动直播间修改updateWebcastForUser失败  err：");
    			logger.error(e, e.fillInStackTrace());
    		} 
	}
	
	/**
	 *修改直播状态
	 *@param apptoken 
	 *@param webcastid
	 *@param status
	 *@param screenmode
	 *@return ResultEntity<WebcastsEntity>
	 *@author yangbilin
	 */
	public void updateWebcastStatusForUser(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out=response.getWriter();
			ResultEntity<WebcastsEntity> result =null;
			
			String apptoken = request.getParameter("apptoken");
			String webcastId=StringUtil.isEmpty(request.getParameter("webcastid")) ? "" : request.getParameter("webcastid");
   	 		String status=StringUtil.isEmpty(request.getParameter("status")) ? "" : request.getParameter("status");
   	 		String screenMode=StringUtil.isEmpty(request.getParameter("screenmode")) ? "" : request.getParameter("screenmode");
   	 	
			Gson gson = new Gson();
			if(StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(webcastId)|| StringUtil.isEmpty(status)
					|| StringUtil.isEmpty(screenMode)){
				commonResult(0,"参数传值出错！",out,gson);
				return;
			}
			try {
				webcastId = CodeUtil.decode(webcastId, apptoken);
				status  = CodeUtil.decode(status, apptoken);
				screenMode  = CodeUtil.decode(screenMode, apptoken);
			} catch (Exception e) {
			    commonResult(0,"加密方式出错！",out,gson);
				return;
			}
			
			WebcastsEntity webcast2 = webcastService.findById(webcastId);
			if(webcast2!=null){
				if("0".equals(webcast2.getStatus())&&status.equals(webcast2.getStatus())){
					commonResult(0,"当前状态与直播状态相同!",out,gson);
					return;
				}
			}
			
			WebcastsEntity webcast = new WebcastsEntity();
			webcast.setWebcastId(webcastId);
    	    webcast.setStatus(status);
    	    webcast.setScreenMode(screenMode);
//            int flag=webcastService.updateStatus(webcast);
            webcastService.updateStatus(webcast);
//            if(flag==1){
            	webcast2  = webcastService.findById(webcastId);
	            result = new ResultEntity<WebcastsEntity>(1,"成功",webcast2);
	    		out.write(gson.toJson(result));
	    		out.flush();
	    		out.close();
//	        }else{
//	            commonResult(0,"失败",out,gson);
//	        }
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("我的移动直播状态修改失败updateWebcastStatusForUser  err：");
			logger.error(e, e.fillInStackTrace());
		} 
	}
	
	/**
	 *根据当前用户获取其直播间getWebcastsByUserid 
	 *@param apptoken
	 *@param username
	 *@return ResultEntity<WebcastsEntity>
	 *@author yangbilin
	 */
	public void getWebcastsByUserid(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
        try {
        	PrintWriter out = response.getWriter();
        	String username=null;
        	ResultEntity<WebcastsEntity> result =null;
       	 	
       	 	String apptoken=request.getParameter("apptoken");
       	    if(StringUtils.isNotBlank(request.getParameter("username"))){
	     		username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	     	}
       	    
       	    Gson gson = new Gson();
		 	if(StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(username)){
		 		result = new ResultEntity<WebcastsEntity>(0, "参数传值出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	try {
		 		username = CodeUtil.decode(username, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<WebcastsEntity>(0, "加密方式出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}		
			if(StringUtils.isNotBlank(username)){
				WebcastsEntity webcast  = webcastService.getWebcastsByUserid(username);
			   if(webcast!=null){
				   String readlPath=request.getSession().getServletContext().getRealPath("/");	
				   String picpath=webcast.getPicPath();
				   if(picpath!=null){
					   String filePath=readlPath+picpath.substring(0,picpath.lastIndexOf("/")+1);
					   filePath=filePath.replace("\\", "/");
					   File folder = new File(filePath);
						if(!folder.exists()){
							folder.mkdir();
						}
					   File file = new File(filePath,picpath.substring(picpath.lastIndexOf("/")+1,picpath.length()));
					   if(!file.exists()){
						   file.createNewFile();
						   byte[] content=webcast.getPiccon();
						   FileOutputStream fileOutputStream = new FileOutputStream(file);
						   fileOutputStream.write(content,0,content.length);
						   fileOutputStream.close();
					   }
					   webcast.setPicPath(new FileUntils().getImageHost()+picpath);
				   }
				   webcast.setPiccon(null);
				   result = new ResultEntity<WebcastsEntity>(1, "成功",webcast); 
			   }else{
				   result = new ResultEntity<WebcastsEntity>(0, "获取用户直播间信息失败",null); 
			   }
			}else{
			   result = new ResultEntity<WebcastsEntity>(0, "获取当前用户信息失败", null);
			}
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("根据当前用户获取其直播间信息程序异常getWebcastsByUserid err：");
			e.printStackTrace();
		}
	}
	
	/**
	 *获取某直播信息findWebCastById
	 *@param apptoken 
	 *@param webcastid
	 *@return ResultEntity<WebcastsEntity>
	 *@author yangbilin
	 */
	public void findWebCastById(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
        try {
        	PrintWriter out = response.getWriter();
        	ResultEntity<WebcastsEntity> result =null;
       	 	
       	 	String apptoken=request.getParameter("apptoken");
       	    String webcastId=StringUtil.isEmpty(request.getParameter("webcastid")) ? "" : request.getParameter("webcastid");
		 
       	    Gson gson = new Gson();
		 	if(StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(webcastId)){
		 		result = new ResultEntity<WebcastsEntity>(0, "参数传值出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	try {
		 		webcastId = CodeUtil.decode(webcastId, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<WebcastsEntity>(0, "加密方式出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}		
			if(StringUtils.isNotBlank(webcastId)){
				WebcastsEntity webcast=webcastService.findById(webcastId);
			   if(webcast!=null){
				   String readlPath=request.getSession().getServletContext().getRealPath("/");	
				   String picpath=webcast.getPicPath();
				   if(picpath!=null){
					   String filePath=readlPath+picpath.substring(0,picpath.lastIndexOf("/")+1);
					   filePath=filePath.replace("\\", "/");
					   File folder = new File(filePath);
						if(!folder.exists()){
							folder.mkdir();
						}
					   File file = new File(filePath,picpath.substring(picpath.lastIndexOf("/")+1,picpath.length()));
					   if(!file.exists()){
						   file.createNewFile();
						   byte[] content=webcast.getPiccon();
						   FileOutputStream fileOutputStream = new FileOutputStream(file);
						   fileOutputStream.write(content,0,content.length);
						   fileOutputStream.close();
					   }
					   webcast.setPicPath(new FileUntils().getImageHost()+picpath);
				   }
				   webcast.setPiccon(null);
			   }
			   result = new ResultEntity<WebcastsEntity>(1, "成功",webcast); 
			}else{
			   result = new ResultEntity<WebcastsEntity>(0, "获取直播信息失败", null);
			}
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("获取某直播信息程序异常findWebCastById err：");
			e.printStackTrace();
		}
	}
	
	/**
	 *应用统计
	 *@param apptoken
	 *@param username
	 *@return ResultEntity<WebcastsEntity>
	 *@author yangbilin
	 */
	public void insertVisitsToZFByFwbm(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
        try {
        	PrintWriter out = response.getWriter();
        	String username=null;
        	String fwbm=null;
        	String schoolCode=null;
        	ResultEntity<String> result =null;
       	 	
       	 	String apptoken=request.getParameter("apptoken");
       	    if(StringUtils.isNotBlank(request.getParameter("username"))){
	     		username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	     	}
       	    fwbm = new String(request.getParameter("fwbm").getBytes("ISO8859-1"), "UTF-8");
       	    if(StringUtils.isNotBlank(request.getParameter("schoolCode"))){
       	    	schoolCode = new String(request.getParameter("schoolCode").getBytes("ISO8859-1"), "UTF-8");
	     	}
       	    
       	    Gson gson = new Gson();
		 	if(StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(fwbm)){
		 		result = new ResultEntity<String>(0, "参数传值出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	try {
		 		username = CodeUtil.decode(username, apptoken);
		 		fwbm = CodeUtil.decode(fwbm, apptoken);
		 		schoolCode = CodeUtil.decode(schoolCode, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<String>(0, "加密方式出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}		
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("fwbm", fwbm);
			map.put("schoolCode", schoolCode);
			map.put("username", username);
			IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
			mobileCommonService.insertVisitsToZFByFwbm(map);
			result = new ResultEntity<String>(1, "成功",""); 
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("应用统计insertVisitsToZFByFwbm err：");
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	private void commonResult(int code,String msg,PrintWriter out,Gson gson){
		ResultEntity<List<WebcastsEntity>> result = new ResultEntity<List<WebcastsEntity>>(code,msg,null);
		out.write(gson.toJson(result));
		out.flush();
		out.close();
	}
	
	
	public ICanteenaddressService getAddressService() {
		return addressService;
	}
	public void setAddressService(ICanteenaddressService addressService) {
		this.addressService = addressService;
	}
	public IOrderofcanteenService getOrderService() {
		return orderService;
	}
	public void setOrderService(IOrderofcanteenService orderService) {
		this.orderService = orderService;
	}
	public IFoodorderService getFoodorderService() {
		return foodorderService;
	}
	public void setFoodorderService(IFoodorderService foodorderService) {
		this.foodorderService = foodorderService;
	}
	public ICanteenService getCanteenService() {
		return canteenService;
	}
	public void setCanteenService(ICanteenService canteenService) {
		this.canteenService = canteenService;
	}
	public IFoodcataofcanteenService getFoodcataService() {
		return foodcataService;
	}
	public void setFoodcataService(IFoodcataofcanteenService foodcataService) {
		this.foodcataService = foodcataService;
	}
	public IFoodofcanteenService getFoodService() {
		return foodService;
	}
	public void setFoodService(IFoodofcanteenService foodService) {
		this.foodService = foodService;
	}

	public IWebcastsService getWebcastService() {
		return webcastService;
	}


	public void setWebcastService(IWebcastsService webcastService) {
		this.webcastService = webcastService;
	}


	/**
	 * 
	* @author: zhangxu
	* @Title: installsCount 
	* @Description: 安装统计接口，用户安装app的统计接口
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public void installsCount(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			if(infromation.equals("0")){
				logger.error("引导页做安装统计：");
			}
			loginService.installsCount();
			ResultEntity<String> result = new ResultEntity<String>(1, "成功", "");
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error("引导页做安装统计异常 err：");
			logger.error(e, e.fillInStackTrace());
		}
	}
	
	
	
	
	
	
	/**
	 * 上传图片
	 */
	public void fixUpload(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		PrintWriter out = null;  
		String url = BaseHolder.getPropertiesValue("suploadPath");
		String filename = "";
		String name = null;
		String content = null;
		Gson gson = new Gson();
   	 	try {
	   	 	out = response.getWriter();
	   	    FileItemFactory factory = new DiskFileItemFactory();  
		    ServletFileUpload upload = new ServletFileUpload(factory);  
		    List<FileItem> items = new ArrayList<FileItem>();
		    items = upload.parseRequest(request);
	        // 得到所有的文件  
	        Iterator<FileItem> it = items.iterator();  
	        while (it.hasNext()) {  
	            FileItem fItem = (FileItem) it.next();  
	            String paramName = "";  
	            Object fValue = null;  
	            if (fItem.isFormField()) { // 普通文本框的值  
	            	
	            }else { // 获取上传文件的值  
	            	paramName = fItem.getFieldName();//userfile  
	                fValue = fItem.getInputStream(); 
	                filename = fItem.getName();//路径  
	                if(!StringUtil.isEmpty(filename)) {  
	                    InputStream is = fItem.getInputStream();  
	                    byte[] fileContent = null;
	                    int size = is.available();
	                    if(is != null && size != 0){
	                    	fileContent = Byte_File_Object.getBytesFromFile(is);
	                    }
	                    String filePath = request.getSession().getServletContext().getRealPath("/") + "images"; 
	        			filePath = filePath.replace("\\", "/");
	        			File newFile = new File(filePath);
	        			if (!newFile.exists()) {
	        				newFile.mkdir();
	        			}
	        			File outFile = new File(filePath, filename);
	        			boolean a = false;
	        			if (!outFile.exists()) {
	        				a = outFile.createNewFile();
	        			} else {
	        				outFile.delete();
	        				a = outFile.createNewFile();
	        			}
	        			System.out.println("jieguo wei " + a);
	        			FileOutputStream fileOutputStream = new FileOutputStream(outFile);
	        			fileOutputStream.write(fileContent);
	        			fileOutputStream.close();
	        			is.close();
		            }  
		        }  
		    }
		        ResultEntity<String> result = new ResultEntity<String>(1, "发布成功", "images/"+filename);
		        out.write(gson.toJson(result));
		        out.flush();
		        out.close();
   	 	} catch (Exception e) {
   	 		e.printStackTrace(); 
            ResultEntity<String> result = new ResultEntity<String>(0, "操作失败", "操作失败");
            out.write(gson.toJson(result));
            out.flush();
            out.close();
	 	}
	}
	
	
	
	
	public void ssoLogin(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		PrintWriter out = null;  
		String url = BaseHolder.getPropertiesValue("suploadPath");
		Gson gson = new Gson();
		try {
			ResultEntity<JSONObject> result = null;
			String apptoken = request.getParameter("apptoken");
		   	String username = request.getParameter("username");
		   	String password = request.getParameter("password");
			String app_id = request.getParameter("app_id");
			String yhlx = request.getParameter("yhlx");
			String gotoUrl = request.getParameter("gotoUrl");
		 		
			if(StringUtil.isEmpty(apptoken) || StringUtil.isEmpty(username) || StringUtil.isEmpty(password)){
				result = new ResultEntity<JSONObject>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			username = CodeUtil.decode(username, apptoken);
			password = CodeUtil.decode(password, apptoken);
			app_id = CodeUtil.decode(app_id, apptoken);
			yhlx = CodeUtil.decode(yhlx, apptoken);
			gotoUrl = CodeUtil.decode(gotoUrl, apptoken);

			String code = "code = " + AESEncrypt.Encrypt(username+"|"+password+"|"+app_id+"|"+yhlx+"|"+gotoUrl, 
					AESEncrypt.sKey,
					AESEncrypt.ivParameter
				);
			String jsonStr=HttpClientUtil.getResponse("POST",url, code);
			System.out.println(jsonStr);
			out.write(gson.toJson(jsonStr));
			out.flush();                    
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("ssoLogin   err：");
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	

	public IWebcastsAuditService getAuditService() {
		return auditService;
	}
	public void setAuditService(IWebcastsAuditService auditService) {
		this.auditService = auditService;
	}
	public ISchoolSceneryCatalogService getSchoolSceneryCatalogService() {
		return schoolSceneryCatalogService;
	}

	public void setSchoolSceneryCatalogService(
			ISchoolSceneryCatalogService schoolSceneryCatalogService) {
		this.schoolSceneryCatalogService = schoolSceneryCatalogService;
	}

	public ISchoolSceneryService getSchoolSceneryService() {
		return schoolSceneryService;
	}

	public void setSchoolSceneryService(ISchoolSceneryService schoolSceneryService) {
		this.schoolSceneryService = schoolSceneryService;
	}

	public void setLoginService(ILoginService loginService) {
		this.loginService = loginService;
	}

	public ILoginService getLoginService() {
		return loginService;
	}
	public void setCommonHttpService(ICommonHttpService commonHttpService) {
		this.commonHttpService = commonHttpService;
	}
	public ICommonHttpService getCommonHttpService() {
		return commonHttpService;
	}
	public IBaseDataService getBaseDataService() {
		return baseDataService;
	}
	public void setBaseDataService(IBaseDataService baseDataService) {
		this.baseDataService = baseDataService;
	}
	public IInfoPropertyService getInfoPropertyService() {
		return infoPropertyService;
	}
	public void setInfoPropertyService(IInfoPropertyService infoPropertyService) {
		this.infoPropertyService = infoPropertyService;
	}
	public ISuggestService getSuggestService() {
		return suggestService;
	}
	public void setSuggestService(ISuggestService suggestService) {
		this.suggestService = suggestService;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(java.net.URLEncoder.encode("http://service.zjgsu.edu.cn/zftal-sjyt-demoWeb/rest/wechat/tologin", "utf-8"));
	}
}
