package com.zfsoft.mobile.oauth.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import net.sf.json.JSONArray;

import org.apache.axis.client.Call;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.common.Config;
import com.common.WebServiceConf;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mh.CXFServe.service.ICaService;
import com.newmobile.LibService.CXFService.Debt;
import com.newmobile.login.service.imp.MobileLoginXMLService;
import com.zfsoft.common.log.User;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.dao.entities.LoginModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.common.enums.ServiceTypeEnum;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.common.utils.JSONUtils;
import com.zfsoft.mobile.configuration.entity.NewsConfig;
import com.zfsoft.mobile.configuration.query.NewsConfigQuery;
import com.zfsoft.mobile.configuration.service.INewsConfigService;
import com.zfsoft.mobile.services.dao.query.BusinessQuery;
import com.zfsoft.mobile.services.entity.Business;
import com.zfsoft.mobile.services.entity.OauthYhDyXt;
import com.zfsoft.mobile.services.entity.ServiceManager;
import com.zfsoft.mobile.services.service.IBusinessService;
import com.zfsoft.mobile.servlet.entity.FailureEntity;
import com.zfsoft.mobile.webservices.entity.LibraryBusinessEntity;
import com.zfsoft.mobile.webservices.entity.LibraryUserEntity;
import com.zfsoft.mobile.webservices.entity.OauthCardBusinessEntity;
import com.zfsoft.mobile.webservices.entity.SalaryEntity;
import com.zfsoft.service.svcinterface.ILoginService;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;

public class OauthServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(OauthServlet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -2058077014058803838L;
	private static final String USERNAME = "username";
	
	public OauthServlet(){
		
	}
	
	public void destroy() {
		  super.destroy(); 
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	   throws ServletException, IOException {
	   doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	   throws ServletException, IOException {
		 
		 try { 
		   String uri = request.getRequestURI();
		   boolean flag = uri.contains("?") ? true : false;
		   String path = "";
		   if(flag){
			   path = uri.substring(uri.lastIndexOf("/"),uri.lastIndexOf("?"));
		   }else{
			   path = uri.substring(uri.lastIndexOf("/"),uri.length());
		   }
		   
		   if(path.equals("/getAccess_token")){
			   getAccess_token(response, request);
		   }else if(path.equals("/getCommonAllWebURL")){
			   getCommonAllWebURL(response, request);
		   }else if(path.equals("/authorizeURL")){
			   authorizeURL(response, request);
		   }else if(path.equals("/authorizeAccess_token")){
			   authorizeAccess_token(response, request);
		   }else if(path.equals("/authorize_cardAdd")){
			   authorize_cardAdd(response, request);
		   }else if(path.equals("/authorize_salaryAdd")){
			   authorize_salaryAdd(response, request);
		   }else if(path.equals("/authorize_salaryAdd")){
			   authorize_salaryAdd(response, request);
		   }else if(path.equals("/authorize_libUserAdd")){
			   authorize_libUserAdd(response, request);
		   }else if(path.equals("/authorize_libBusinessAdd")){
			   authorize_libBusinessAdd(response, request);
		   }
		   else{
			  response.setContentType("text/html;charset=utf-8");
			  PrintWriter out = response.getWriter();
              out = response.getWriter();  
              out.print("{\"failure\":\"404\", \"msg\":\"链接有误，请检查链接重新尝试\"}");  
              out.close();  
		   }
		 } catch (Exception e) {  
			 e.printStackTrace();  
		 }  
	      
	}
	
	public void authorize_libBusinessAdd(HttpServletResponse response,HttpServletRequest request){
		try {
			try {
				request.setCharacterEncoding("UTF-8");
                Map parametermap = request.getParameterMap();
                // 通过循环遍历的方式获得key和value并set到jsonobject中
                Iterator paiter = parametermap.keySet().iterator();
                boolean flag = false;
                List<LibraryBusinessEntity> businessList = null;
                while (paiter.hasNext()) {
                        String key = paiter.next().toString();
                        String[] values = (String[])parametermap.get(key);
                        String value0 = URLDecoder.decode(values[0], "UTF-8");
                        if(key.equals("access_token")){//解析秘钥是否正确
                        	String access_token = value0;
	             			   if(!ApptokenUtils.compare(access_token)){//验证access_token
	             				   response.setContentType("text/html;charset=utf-8");
	             				   PrintWriter out;
	             					out = response.getWriter();
	             							   
	             				   out = response.getWriter();  
	             				   out.print("{\"failure\":\"101\", \"msg\":\"access_token error，请重新登录\"}");  
	             				   out.close(); 
	             			   }
	             		    ILoginService loginServiceImpl = (ILoginService)SpringHolder.getBean("loginService");
	             		    String zgh = loginServiceImpl.getStrKey(access_token);
	             		    NewsConfigQuery configQuery = new NewsConfigQuery();
	             			configQuery.setKey(USERNAME);
	             			INewsConfigService newsConfigService = (INewsConfigService)SpringHolder.getBean("newsConfigService");
	             			NewsConfig config = newsConfigService.findByKey(configQuery);
	             			//判断是否后台设置此用户有权限与平台进行数据交互
	             			if(config == null || StringUtil.isEmpty(config.getValue())){
	             				   response.setContentType("text/html;charset=utf-8");
	             				   PrintWriter out;
	             				   out = response.getWriter();
	             				   out = response.getWriter();  
	             				   String str = URLEncoder.encode("{\"failure\":\"201\", \"msg\":\"后台未设置可以与平台交互的用户，请联系平台管理员\"}", "UTF-8");
	             				   out.print(str);  
	             				   out.close(); 
	             			}else{
	             				if(!config.getValue().equals(zgh)){
	             				   response.setContentType("text/html;charset=utf-8");
	             				   PrintWriter out;
	             				   out = response.getWriter();
	             				   out = response.getWriter();  
	             				   out.print("{\"failure\":\"202\", \"msg\":\"后台未设置可以与平台交互的用户，请联系平台管理员\"}");  
	             				   out.close(); 
	             				}
	             			}
	             			flag= true;
                        }
                        if(key.equals("data")){//解析传输的数据是否正确
                        	JSONArray data = JSONArray.fromObject(value0);
                        	businessList = JSONArray.toList(data,LibraryBusinessEntity.class);
                        	if(businessList == null){
                        		PrintWriter out;
	             				   out = response.getWriter();
	             				   out = response.getWriter();  
	             				   out.print("{\"failure\":\"207\", \"msg\":\"图书馆借阅记录数据不能为空,请重新上传\"}");  
	             				   out.close(); 
                        	}
                        	logger.error(businessList);
                        }
                        
                }
                if(flag){
                	IMobileCommonService mobileCommonService = (IMobileCommonService)SpringHolder.getBean("mobileCommonService");
                	if(businessList != null){
                		mobileCommonService.insertLibBusiList(businessList);
                	}
                }
	        } catch (Exception e) {
	        		logger.error("获取json数据出错，错误信息如下：nt" + e.getMessage());
	        		response.setContentType("text/html;charset=utf-8");
  				   PrintWriter out;
  				   out = response.getWriter();
  				   out = response.getWriter();  
  				   out.print(java.net.URLEncoder.encode("{\"failure\":\"203\", \"msg\":\"您上传的格式有错，请仔细检查\"}","UTF-8"));  
  				   out.close(); 
	        }
			
	        PrintWriter out;
	        out = response.getWriter();
	        out = response.getWriter();  
	        out.print("{\"success\":\"200\", \"msg\":\"operation success\"}");  
	        out.close(); 
		} catch (Exception e) {
			 logger.error(e);
			 response.setContentType("text/html;charset=utf-8");
			 PrintWriter out;
			try {
				out = response.getWriter();
				out.print("{\"failure\":\"104\", \"msg\":\"后台产生异常\"}");  
				out.close(); 
			} catch (IOException e1) {
				logger.error("后台输出流异常");
				e1.printStackTrace();
			}
		}  
	}
	
	
	public void authorize_libUserAdd(HttpServletResponse response,HttpServletRequest request){
		try {
			try {
				request.setCharacterEncoding("UTF-8");
                Map parametermap = request.getParameterMap();
                // 通过循环遍历的方式获得key和value并set到jsonobject中
                Iterator paiter = parametermap.keySet().iterator();
                boolean flag = false;
                List<LibraryUserEntity> userList = null;
                while (paiter.hasNext()) {
                        String key = paiter.next().toString();
                        String[] values = (String[])parametermap.get(key);
                        String value0 = URLDecoder.decode(values[0], "UTF-8");
                        if(key.equals("access_token")){//解析秘钥是否正确
                        	String access_token = value0;
	             			   if(!ApptokenUtils.compare(access_token)){//验证access_token
	             				   response.setContentType("text/html;charset=utf-8");
	             				   PrintWriter out;
	             					out = response.getWriter();
	             							   
	             				   out = response.getWriter();  
	             				   out.print("{\"failure\":\"101\", \"msg\":\"access_token error，请重新登录\"}");  
	             				   out.close(); 
	             			   }
	             		    ILoginService loginServiceImpl = (ILoginService)SpringHolder.getBean("loginService");
	             		    String zgh = loginServiceImpl.getStrKey(access_token);
	             		    NewsConfigQuery configQuery = new NewsConfigQuery();
	             			configQuery.setKey(USERNAME);
	             			INewsConfigService newsConfigService = (INewsConfigService)SpringHolder.getBean("newsConfigService");
	             			NewsConfig config = newsConfigService.findByKey(configQuery);
	             			//判断是否后台设置此用户有权限与平台进行数据交互
	             			if(config == null || StringUtil.isEmpty(config.getValue())){
	             				   response.setContentType("text/html;charset=utf-8");
	             				   PrintWriter out;
	             				   out = response.getWriter();
	             				   out = response.getWriter();  
	             				   out.print("{\"failure\":\"201\", \"msg\":\"后台未设置可以与平台交互的用户，请联系平台管理员\"}");  
	             				   out.close(); 
	             			}else{
	             				if(!config.getValue().equals(zgh)){
	             				   response.setContentType("text/html;charset=utf-8");
	             				   PrintWriter out;
	             				   out = response.getWriter();
	             				   out = response.getWriter();  
	             				   out.print("{\"failure\":\"202\", \"msg\":\"后台未设置可以与平台交互的用户，请联系平台管理员\"}");  
	             				   out.close(); 
	             				}
	             			}
	             			flag= true;
                        }
                        if(key.equals("data")){//解析传输的数据是否正确
                        	JSONArray data = JSONArray.fromObject(value0);
                        	userList = JSONArray.toList(data,LibraryUserEntity.class);
                        	if(userList == null){
                        		PrintWriter out;
	             				   out = response.getWriter();
	             				   out = response.getWriter();  
	             				   out.print("{\"failure\":\"206\", \"msg\":\"图书馆用户数据不能为空,请重新上传\"}");  
	             				   out.close(); 
                        	}
                        	logger.error(userList);
                        }
                        
                }
                if(flag){
                	IMobileCommonService mobileCommonService = (IMobileCommonService)SpringHolder.getBean("mobileCommonService");
                	if(userList != null){
                		mobileCommonService.insertLibUserList(userList);
                	}
                }
	        } catch (Exception e) {
	        		logger.error("获取json数据出错，错误信息如下：nt" + e.getMessage());
	        		response.setContentType("text/html;charset=utf-8");
  				   PrintWriter out;
  				   out = response.getWriter();
  				   out = response.getWriter();  
  				   out.print("{\"failure\":\"203\", \"msg\":\"您上传的格式有错，请仔细检查\"}");  
  				   out.close(); 
	        }
			
	        PrintWriter out;
	        out = response.getWriter();
	        out = response.getWriter();  
	        out.print("{\"success\":\"200\", \"msg\":\"operation success\"}");  
	        out.close(); 
		} catch (Exception e) {
			 logger.error(e);
			 response.setContentType("text/html;charset=utf-8");
			 PrintWriter out;
			try {
				out = response.getWriter();
				out.print("{\"failure\":\"104\", \"msg\":\"后台产生异常\"}");  
				out.close(); 
			} catch (IOException e1) {
				logger.error("后台输出流异常");
				e1.printStackTrace();
			}
		}  
	}
	
	public void authorize_salaryAdd(HttpServletResponse response,HttpServletRequest request){
		try {
			try {
				request.setCharacterEncoding("UTF-8");
                Map parametermap = request.getParameterMap();
                // 通过循环遍历的方式获得key和value并set到jsonobject中
                Iterator paiter = parametermap.keySet().iterator();
                boolean flag = false;
                List<SalaryEntity> salaryList = null;
                while (paiter.hasNext()) {
                        String key = paiter.next().toString();
                        String[] values = (String[])parametermap.get(key);
                        String value0 = URLDecoder.decode(values[0], "UTF-8");
                        if(key.equals("access_token")){//解析秘钥是否正确
                        	String access_token = value0;
	             			   if(!ApptokenUtils.compare(access_token)){//验证access_token
	             				   response.setContentType("text/html;charset=utf-8");
	             				   PrintWriter out;
	             					out = response.getWriter();
	             							   
	             				   out = response.getWriter();  
	             				   out.print("{\"failure\":\"101\", \"msg\":\"access_token error，请重新登录\"}");  
	             				   out.close(); 
	             			   }
	             		    ILoginService loginServiceImpl = (ILoginService)SpringHolder.getBean("loginService");
	             		    String zgh = loginServiceImpl.getStrKey(access_token);
	             		    NewsConfigQuery configQuery = new NewsConfigQuery();
	             			configQuery.setKey(USERNAME);
	             			INewsConfigService newsConfigService = (INewsConfigService)SpringHolder.getBean("newsConfigService");
	             			NewsConfig config = newsConfigService.findByKey(configQuery);
	             			//判断是否后台设置此用户有权限与平台进行数据交互
	             			if(config == null || StringUtil.isEmpty(config.getValue())){
	             				   response.setContentType("text/html;charset=utf-8");
	             				   PrintWriter out;
	             				   out = response.getWriter();
	             				   out = response.getWriter();  
	             				   out.print("{\"failure\":\"201\", \"msg\":\"后台未设置可以与平台交互的用户，请联系平台管理员\"}");  
	             				   out.close(); 
	             			}else{
	             				if(!config.getValue().equals(zgh)){
	             				   response.setContentType("text/html;charset=utf-8");
	             				   PrintWriter out;
	             				   out = response.getWriter();
	             				   out = response.getWriter();  
	             				   out.print("{\"failure\":\"202\", \"msg\":\"后台未设置可以与平台交互的用户，请联系平台管理员\"}");  
	             				   out.close(); 
	             				}
	             			}
	             			flag= true;
                        }
                        if(key.equals("data")){//解析传输的数据是否正确
                        	JSONArray data = JSONArray.fromObject(value0);
                        	salaryList = JSONArray.toList(data,SalaryEntity.class);
                        	if(salaryList == null){
                        		PrintWriter out;
	             				   out = response.getWriter();
	             				   out = response.getWriter();  
	             				   out.print("{\"failure\":\"205\", \"msg\":\"工资数据不能为空,请重新上传\"}");  
	             				   out.close(); 
                        	}
                        	logger.error(salaryList);
                        }
                        
                }
                if(flag){
                	IMobileCommonService mobileCommonService = (IMobileCommonService)SpringHolder.getBean("mobileCommonService");
                	if(salaryList != null){
                		mobileCommonService.insertSalaryList(salaryList);
                	}
                }
	        } catch (Exception e) {
	        		logger.error("获取json数据出错，错误信息如下：nt" + e.getMessage());
	        		response.setContentType("text/html;charset=utf-8");
  				   PrintWriter out;
  				   out = response.getWriter();
  				   out = response.getWriter();  
  				   out.print("{\"failure\":\"203\", \"msg\":\"您上传的格式有错，请仔细检查\"}");  
  				   out.close(); 
	        }
			
	        PrintWriter out;
	        out = response.getWriter();
	        out = response.getWriter();  
	        out.print("{\"success\":\"200\", \"msg\":\"operation success\"}");  
	        out.close(); 
		} catch (Exception e) {
			 logger.error(e);
			 response.setContentType("text/html;charset=utf-8");
			 PrintWriter out;
			try {
				out = response.getWriter();
				out.print("{\"failure\":\"104\", \"msg\":\"后台产生异常\"}");  
				out.close(); 
			} catch (IOException e1) {
				logger.error("后台输出流异常");
				e1.printStackTrace();
			}
		}  
	}
	
	/**
	 * 授权用户对接移动后台,进行一卡通的数据对接
	 * @param response
	 * @param request
	 */
	public void authorize_cardAdd(HttpServletResponse response,HttpServletRequest request){
		try {
			try {
				request.setCharacterEncoding("UTF-8");
                Map parametermap = request.getParameterMap();
                // 通过循环遍历的方式获得key和value并set到jsonobject中
                Iterator paiter = parametermap.keySet().iterator();
                boolean flag = false;
                List<OauthCardBusinessEntity> businessList = null;
                while (paiter.hasNext()) {
                        String key = paiter.next().toString();
                        String[] values = (String[])parametermap.get(key);
                        String value0 = URLDecoder.decode(values[0], "UTF-8");
                        if(key.equals("access_token")){//解析秘钥是否正确
                        	String access_token = value0;
	             			   if(!ApptokenUtils.compare(access_token)){//验证access_token
	             				   response.setContentType("text/html;charset=utf-8");
	             				   PrintWriter out;
	             					out = response.getWriter();
	             							   
	             				   out = response.getWriter();  
	             				   out.print("{\"failure\":\"101\", \"msg\":\"access_token error，请重新登录\"}");  
	             				   out.close(); 
	             			   }
	             		    ILoginService loginServiceImpl = (ILoginService)SpringHolder.getBean("loginService");
	             		    String zgh = loginServiceImpl.getStrKey(access_token);
	             		    NewsConfigQuery configQuery = new NewsConfigQuery();
	             			configQuery.setKey(USERNAME);
	             			INewsConfigService newsConfigService = (INewsConfigService)SpringHolder.getBean("newsConfigService");
	             			NewsConfig config = newsConfigService.findByKey(configQuery);
	             			//判断是否后台设置此用户有权限与平台进行数据交互
	             			if(config == null || StringUtil.isEmpty(config.getValue())){
	             				   response.setContentType("text/html;charset=utf-8");
	             				   PrintWriter out;
	             				   out = response.getWriter();
	             				   out = response.getWriter();  
	             				   out.print("{\"failure\":\"201\", \"msg\":\"后台未设置可以与平台交互的用户，请联系平台管理员\"}");  
	             				   out.close(); 
	             			}else{
	             				if(!config.getValue().equals(zgh)){
	             				   response.setContentType("text/html;charset=utf-8");
	             				   PrintWriter out;
	             				   out = response.getWriter();
	             				   out = response.getWriter();  
	             				   out.print("{\"failure\":\"202\", \"msg\":\"后台未设置可以与平台交互的用户，请联系平台管理员\"}");  
	             				   out.close(); 
	             				}
	             			}
	             			flag= true;
                        }
                        if(key.equals("data")){//解析传输的数据是否正确
                        	JSONArray data = JSONArray.fromObject(value0);
                        	businessList = JSONArray.toList(data,OauthCardBusinessEntity.class);
                        	if(businessList == null){
                        		PrintWriter out;
	             				   out = response.getWriter();
	             				   out = response.getWriter();  
	             				   out.print("{\"failure\":\"204\", \"msg\":\"一卡通数据不能为空,请重新上传\"}");  
	             				   out.close(); 
                        	}
                        	
                        	logger.error(businessList);
                        }
                        
                }
                if(flag){
                	IMobileCommonService mobileCommonService = (IMobileCommonService)SpringHolder.getBean("mobileCommonService");
                	if(businessList != null){
                		mobileCommonService.insertCardBusiness(businessList);
                	}
                }
	        } catch (Exception e) {
	        		logger.error("获取json数据出错，错误信息如下：nt" + e.getMessage());
	        		response.setContentType("text/html;charset=utf-8");
  				   PrintWriter out;
  				   out = response.getWriter();
  				   out = response.getWriter();  
  				   out.print("{\"failure\":\"203\", \"msg\":\"您上传的格式有错，请仔细检查\"}");  
  				   out.close(); 
	        }
			
	        PrintWriter out;
	        out = response.getWriter();
	        out = response.getWriter();  
	        out.print("{\"success\":\"200\", \"msg\":\"operation success\"}");  
	        out.close(); 
		} catch (Exception e) {
			 logger.error(e);
			 response.setContentType("text/html;charset=utf-8");
			 PrintWriter out;
			try {
				out = response.getWriter();
				out.print("{\"failure\":\"104\", \"msg\":\"后台产生异常\"}");  
				out.close(); 
			} catch (IOException e1) {
				logger.error("后台输出流异常");
				e1.printStackTrace();
			}
		}  
	}
	
	/**
	 * 获取ca票据方法
	 * @param username
	 * @param password
	 * @return
	 */
	public String getCATGC(String username, String password) {
		String result = null;
		String endpointURL = WebServiceConf.WEBSERVICE_CA_MANAGE;
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		
		try {
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
			result = (String) call.invoke(new Object[] { username, password });
			System.out.print("获取TGC认证票据：" + result); // 测试
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("TGC认证票据Erro" + e); // 测试
		}
		return result;
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
	 * 获取所有应用中心url
	 * @param response
	 * @param request
	 */
	public void getCommonAllWebURL(HttpServletResponse response,HttpServletRequest request){
		try { 
			   String access_token = request.getParameter("access_token");
			   if(!ApptokenUtils.compare(access_token)){
//				   response.setContentType("text/html;charset=utf-8");
				   request.setCharacterEncoding("UTF-8");  
				    response.setCharacterEncoding("UTF-8");  
				    response.setContentType("text/html; charset=UTF-8");  
				   PrintWriter out = response.getWriter();			   
				   out = response.getWriter();  
				   out.print("{\"failure\":\"101\", \"msg\":\"access_token error，请重新登录\"}");  
				   out.close(); 
			   }
			   ILoginService loginServiceImpl = (ILoginService)SpringHolder.getBean("loginService");
			   String username = loginServiceImpl.getStrKey(access_token);
			 
			   //String TGCTicket = getCATGC(username, password);
			   IMobileCommonService mobileCommonService = (IMobileCommonService)SpringHolder.getBean("mobileCommonService");
			   List<ServiceManager> services = mobileCommonService.getAllServiceByUser(username, "");
			   List<Map<String, String>> serviceList = new ArrayList<Map<String,String>>();
			   Map<String, String> map = null;
			   for(ServiceManager serviceEntity: services){
				   if(!serviceEntity.getClassFwlx().equals(ServiceTypeEnum.WEB_SERVICE.getKey())) continue;
				   map = new HashMap<String, String>();
				   String uri = serviceEntity.getWebUrl();
				   /*boolean flag = uri.contains("?") ? true : false;
				   if(flag){
					   uri = uri +"&TGC="+TGCTicket;
				   }else{
					   uri = uri +"?TGC="+TGCTicket;
				   }*/
				   map.put("url", uri);
				   map.put("serviceName", serviceEntity.getClassFwmc());
				   serviceList.add(map);
			   }
			   String result = JSONUtils.obj2json(serviceList);
			   PrintWriter out;
			   response.setHeader("Content-type", "textml;charset=UTF-8");
			   response.setCharacterEncoding("UTF-8");
			   out = response.getWriter();
			   out.write(result);  
			   out.close();
			   
				   
		   
		} catch (Exception e) {  
			 logger.error(e);
			 response.setContentType("text/html;charset=utf-8");
			 PrintWriter out;
			try {
				out = response.getWriter();
				out.print("{\"failure\":\"104\", \"msg\":\"后台产生异常\"}");  
				out.close(); 
			} catch (IOException e1) {
				logger.error("后台输出流异常");
				e1.printStackTrace();
			}
		 }  
	}
	
	/**
	 * 获取access_token
	 * @param response
	 * @param request
	 */
	public void getAccess_token(HttpServletResponse response,HttpServletRequest request){
		try { 
		   String username = request.getParameter("username");
		   String password = request.getParameter("password");
		   String uuid     = request.getParameter("uuid");
		   if(StringUtil.isEmpty(username) || StringUtil.isEmpty(password) || StringUtil.isEmpty(uuid)){
				  response.setContentType("text/html;charset=utf-8");
				  PrintWriter out = response.getWriter();
				  out = response.getWriter();  
	              out.print("{\"failure\":\"106\", \"msg\":\"用户名或密码或随机32位字符uuid为空\"}");  
	              out.close(); 
		   }
		   password  		= CodeUtil.decode(password, uuid);
		   ILoginService loginService = (ILoginService)SpringHolder.getBean("loginService");
		   if(!StringUtil.isEmpty(Config.getString("webservice.host.mh"))){
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
					response.setContentType("text/html;charset=utf-8");
					PrintWriter out = response.getWriter();
					out = response.getWriter();  
					out.print("{\"failure\":\"107\", \"msg\":\"ca接口登录异常,请联系管理员！\"}");  
					out.close();
				}
					
					boolean canLogin = false;
					try {
						canLogin = containErrorCode(zfxml);
					} catch (DocumentException e) {
						e.printStackTrace();
					}
					if(canLogin){
						  response.setContentType("text/html;charset=utf-8");
						  PrintWriter out = response.getWriter();
						  out = response.getWriter();  
						  out.print("{\"failure\":\"107\", \"msg\":\"用户名或密码错误\"}");  
						  out.close();
					}
			}else{
				   
				   LoginModel model = new LoginModel();
				   model.setYhm(username);
				   model.setMm(password);
				   User user = loginService.cxYhxx(model);
				   if(user == null){
					  response.setContentType("text/html;charset=utf-8");
					  PrintWriter out = response.getWriter();
					  out = response.getWriter();  
					  out.print("{\"failure\":\"107\", \"msg\":\"用户名或密码错误\"}");  
					  out.close();
				   }
			}
			  String acess_token = loginService.getStrKeyByYhm(username);
			  response.setContentType("text/html;charset=utf-8");
			  PrintWriter out = response.getWriter();
			  out = response.getWriter();  
	          out.print("{\"acess_token\":\""+acess_token+"\"}");  
	          out.close();
		} catch (Exception e) {  
			 logger.error(e);
			 response.setContentType("text/html;charset=utf-8");
			 PrintWriter out;
			try {
				out = response.getWriter();
				out.print("{\"failure\":\"104\", \"msg\":\"后台产生异常\"}");  
				out.close(); 
			} catch (IOException e1) {
				logger.error("后台输出流异常");
				e1.printStackTrace();
			}
		 }  
	}
	/**
	 * 根据access_token获取个人基本信息(现在只获取职工号)
	 * @param response
	 * @param request
	 */
	public void authorizeAccess_token(HttpServletResponse response,HttpServletRequest request){
		try { 
			   String access_token = request.getParameter("access_token");
			   if(!ApptokenUtils.compare(access_token)){
				   response.setContentType("text/html;charset=utf-8");
				   PrintWriter out = response.getWriter();			   
				   out = response.getWriter();  
				   out.print("{\"failure\":\"101\", \"msg\":\"access_token error，请重新登录\"}");  
				   out.close(); 
			   }
			   ILoginService loginServiceImpl = (ILoginService)SpringHolder.getBean("loginService");
			   String zgh = loginServiceImpl.getStrKey(access_token);
			   LoginModel model = new LoginModel();
			   model.setYhm(zgh);
			   User user = loginServiceImpl.cxYhxx(model);
				
			   response.setContentType("text/html;charset=utf-8");
			   PrintWriter out = response.getWriter();
	           out = response.getWriter();  
	           out.print("{\"username\":\""+zgh+"\""+",\"role\":\""+user.getYhlx()+"\"}");  
	           out.close(); 
			} catch (Exception e) {  
				 logger.error(e);
				 response.setContentType("text/html;charset=utf-8");
				 PrintWriter out;
			try {
				out = response.getWriter();
				out.print("{\"failure\":\"104\", \"msg\":\"后台产生异常\"}");  
				out.close(); 
			} catch (IOException e1) {
				logger.error("后台输出流异常");
				e1.printStackTrace();
			}
		 }  
	}
	/**
	 * 根据access_token做授权认证跳转
	 * @param response
	 * @param request
	 */
	public void authorizeURL(HttpServletResponse response,HttpServletRequest request){
		try { 
			String access_token = request.getParameter("access_token");
		   if(!ApptokenUtils.compare(access_token)){
			   response.setContentType("text/html;charset=utf-8");
			   PrintWriter out = response.getWriter();			   
			   out = response.getWriter();  
			   out.print("{\"failure\":\"101\", \"msg\":\"app_token error，请重新登录\"}");  
			   out.close(); 
		   }
		   if(StringUtil.isEmpty(access_token)){
			  response.setContentType("text/html;charset=utf-8");
			  PrintWriter out = response.getWriter();
			  out = response.getWriter();  
              out.print("{\"failure\":\"102\", \"msg\":\"access_token为空，请加上access_token\"}");  
              out.close(); 
		   }
		   String procode = "";
		   String desProcode = request.getParameter("procode");
		   logger.error("desProcode:"+desProcode);
		   String redirect_uri = request.getParameter("redirect_uri");
		   try {
			   procode  		= CodeUtil.decode(desProcode, access_token);
				
			} catch (Exception e) {
				logger.error(e);
				//e.printStackTrace();
				response.setContentType("text/html;charset=utf-8");
			    PrintWriter out = response.getWriter();
			    out = response.getWriter();  
                out.print("{\"failure\":\"103\", \"msg\":\"解析procode出错\"}");  
                out.close();
			}	
		   ILoginService loginServiceImpl = (ILoginService)SpringHolder.getBean("loginService");
		   String zgh = loginServiceImpl.getStrKey(access_token);
		   IBusinessService businessService = (IBusinessService)SpringHolder.getBean("businessService"); 
		   OauthYhDyXt query = new OauthYhDyXt();
		   query.setYhid(zgh);
		   query.setProcode(procode);
		   List<OauthYhDyXt> list = businessService.getOauthYhDyXtList(query);
		   BusinessQuery businessQuery = new BusinessQuery();
		   businessQuery.setProcode(procode);
		   PageList<Business> businessList  = businessService.getList(businessQuery);
		   if(businessList == null || (businessList != null && businessList.size() == 0)){
			   response.setContentType("text/html;charset=utf-8");
			   PrintWriter out = response.getWriter();
			   out = response.getWriter();  
               out.print("{\"failure\":\"104\", \"msg\":\"后台不存在此类业务系统,请检查是否后台存在\"}");  
               out.close(); 
		   }
		   String businessName = businessList.get(0).getClassXtmc();
		   OauthYhDyXt model = new OauthYhDyXt();
		   String timeout = "";
		   desProcode =  java.net.URLEncoder.encode(desProcode,"UTF-8");
		   if(list != null && list.size() > 0){
			   model = list.get(0);
			   timeout = CodeUtil.encode("1", access_token);
			   timeout = java.net.URLEncoder.encode(timeout,"UTF-8");
			   if(model.getEndtime() < (new Date().getTime()) ){
				   redirect_uri = java.net.URLEncoder.encode(redirect_uri,"UTF-8");
				   
				   response.sendRedirect(Config.getString("suploadPath")+"oauth/oauth_authorize.html?access_token="+
						   access_token +"&procode=" + desProcode +"&timeout=" + timeout+"&redirect_uri=" + redirect_uri+"&businessName=" + (URLEncoder.encode(businessName, "utf-8")));//当前时间大于有效时间，进入授权页面重新授权
				   
			   }else{
				   response.sendRedirect(Config.getString("suploadPath")+"oauth/oauth_authorizeForYh.html?access_token="+
						   access_token +"&procode=" + desProcode +"&timeout=" + timeout+"&redirect_uri=" + redirect_uri+"&flag="+timeout);//直接跳转
				  }
		   }else{//不存在与此业务系统的第三方授权，进入授权页面
			   timeout = CodeUtil.encode("0", access_token);
			   timeout = java.net.URLEncoder.encode(timeout,"UTF-8");
			   redirect_uri = java.net.URLEncoder.encode(redirect_uri,"UTF-8");
			   response.sendRedirect(Config.getString("suploadPath")+"oauth/oauth_authorize.html?access_token="+
						   access_token +"&procode=" + desProcode +"&timeout=" + timeout+"&redirect_uri=" + redirect_uri+"&businessName=" +  (URLEncoder.encode(businessName, "utf-8")));//当前时间大于有效时间，进入授权页面重新授权
			   
			   
		   }
		 } catch (Exception e) {  
			 logger.error(e);
			 response.setContentType("text/html;charset=utf-8");
			 PrintWriter out;
			try {
				out = response.getWriter();
				out.print("{\"failure\":\"105\", \"msg\":\"后台产生异常\"}");  
				out.close(); 
			} catch (IOException e1) {
				logger.error("后台输出流异常");
				e1.printStackTrace();
			}
		 }  
	}
	
	public static void main(String[] args) {
		try {
			String url ="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe83023a145c7f2cf&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
			System.out.println(java.net.URLEncoder.encode("http://demo.zfsoft.com/zftal-mobile/mobile/web_index.html","UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
