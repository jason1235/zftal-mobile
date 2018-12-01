package com.zfsoft.mobile.servlet.secureCenterHttp.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.common.Config;
import com.google.gson.Gson;
import com.zfsoft.common.log.User;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.dao.entities.LoginModel;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.hrm.file.util.UploadFileUtil;
import com.zfsoft.mobile.MD5Util.MD5Util;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.common.utils.Authentication;
import com.zfsoft.mobile.common.utils.FileUntils;
import com.zfsoft.mobile.common.utils.HttpClientUtil;
import com.zfsoft.mobile.common.utils.MobileSystemHolder;
import com.zfsoft.mobile.servlet.entity.LoginEntity;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.service.svcinterface.ILoginService;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.Base64;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;

/**
 *移动版安全中心
 *@author yangbilin
 *@date 2017-08-01 17:13:00 
 */
public class SecureCenterHttpAction {

	private static Logger logger = Logger.getLogger(SecureCenterHttpAction.class);
	private final String infromation=Config.getString("mobile.infromation");
	private final String httpurl=Config.getString("httpurl.casercuritycenter");
	
	private ILoginService loginService;
	/**
	 *绑定手机获取验证码/getVericodeForBdMobile.do  username  sjhm
	 *@param apptoken 
	 *@param username
	 *@param sjhm
	 *@return
	 *@author yangbilin 
	 */
	public void getVerificationcodeForBindingMobile(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
        	PrintWriter out = response.getWriter();
        	String username = null;
       	 	ResultEntity<String> result = null;
       	 	String apptoken=request.getParameter("apptoken");
       	    if(StringUtils.isNotBlank(request.getParameter("username"))){
	     		username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	     	}
       	    String sjhm=StringUtil.isEmpty(request.getParameter("sjhm")) ? "" : request.getParameter("sjhm");
		 
       	    Gson gson = new Gson();
		 	if(StringUtil.isEmpty(username) || StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(sjhm)){
		 		result = new ResultEntity<String>(0, "参数传值出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	if(!ApptokenUtils.compare(username, apptoken)){
		 		result = new ResultEntity<String>(2, "app_token error!", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	try {
				username = CodeUtil.decode(username, apptoken);
				sjhm = CodeUtil.decode(sjhm, apptoken);

				if(StringUtils.isNotBlank(username)){
					username=Base64.encode(username.getBytes());
				}
				if(StringUtils.isNotBlank(sjhm)){
					sjhm = Base64.encode(sjhm.getBytes());
				}
			} catch (Exception e) {
				result = new ResultEntity<String>(0, "加密方式出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			String par="sjhm="+sjhm+"&username="+username;
			Map<String,Object> resMap = commonSecureCenter(httpurl+"/getVericodeForBdMobile.do",par,gson);
			if(resMap!=null&&resMap.size()>0){
				result = new ResultEntity<String>((Integer)resMap.get("status"),(String)resMap.get("msg"),null);
			}
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("获取手机绑定验证码 getVerificationcodeForBindingMobile  err：");
			logger.error(e, e.fillInStackTrace());
		}
	}
	
	/**
	 *绑定手机/bindingMobile.do  sjhm yzm
	 *@param apptoken 
	 *@param yzm
	 *@param sjhm
	 *@return
	 *@author yangbilin 
	 */
	public void bindingMobile(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
        	PrintWriter out = response.getWriter();
        	ResultEntity<String> result = null;
       	 	String apptoken=request.getParameter("apptoken");
       	 	String sjhm=StringUtil.isEmpty(request.getParameter("sjhm")) ? "" : request.getParameter("sjhm");
       	    String yzm=StringUtil.isEmpty(request.getParameter("yzm")) ? "" : request.getParameter("yzm");
       	   
       	    Gson gson = new Gson();
		 	if(StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(sjhm)||StringUtil.isEmpty(yzm)){
		 		result = new ResultEntity<String>(0, "参数传值出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	try {
				sjhm = CodeUtil.decode(sjhm, apptoken);
				yzm = CodeUtil.decode(yzm, apptoken);
				
				if(StringUtils.isNotBlank(sjhm)){
					sjhm = Base64.encode(sjhm.getBytes());
				}
				if(StringUtils.isNotBlank(yzm)){
					yzm=Base64.encode(yzm.getBytes());
				}
			} catch (Exception e) {
				result = new ResultEntity<String>(0, "加密方式出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			String par="sjhm="+sjhm+"&yzm="+yzm;
			String jsonStr=HttpClientUtil.getResponse("POST", httpurl+"/bindingMobile.do", par);
			JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
			if(obj!=null&&obj.size()>0){
				if(obj.getInt("code")==205){
					/**
					 *如果成功，则返回true
					 *并返回成功的提示信息 
					 */
					result = new ResultEntity<String>(1,obj.getString("message"),null);
				}else{
					/**
					 *如果失败，则返回false
					 *并返回失败的提示信息 
					 */
					result = new ResultEntity<String>(0,obj.getString("message"), null);
				}
			}
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("绑定手机bindingMobile err：");
			logger.error(e, e.fillInStackTrace());
		}
	}
	
	/**
	 *解除绑定手机/unbindingMobile.do  username, sjhm, yzm
	 *@param apptoken
	 *@param username
	 *@param yzm
	 *@param sjhm
	 *@return
	 *@author yangbilin 
	 */
	public void unbindingMobile(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
        	ResultEntity<String> result = null;
        	String username=null;
       	 	String apptoken=request.getParameter("apptoken");
    	 	if(StringUtils.isNotBlank(request.getParameter("username"))){
	     		username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	     	}
       	 	String sjhm=StringUtil.isEmpty(request.getParameter("sjhm")) ? "" : request.getParameter("sjhm");
       	 	String yzm=StringUtil.isEmpty(request.getParameter("yzm")) ? "" : request.getParameter("yzm");
       	 	Gson gson = new Gson();
		 	if(StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(username)||StringUtil.isEmpty(sjhm)||StringUtil.isEmpty(yzm)){
		 		result = new ResultEntity<String>(0, "参数传值出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	try {
		 		username=CodeUtil.decode(username, apptoken);
				sjhm = CodeUtil.decode(sjhm, apptoken);
				yzm = CodeUtil.decode(yzm, apptoken);
				
				if(StringUtils.isNotBlank(username)){
					username=Base64.encode(username.getBytes());
				}
				if(StringUtils.isNotBlank(sjhm)){
					sjhm = Base64.encode(sjhm.getBytes());
				}
				if(StringUtils.isNotBlank(yzm)){
					yzm=Base64.encode(yzm.getBytes());
				}
			} catch (Exception e) {
				result = new ResultEntity<String>(0, "加密方式出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			String par="username="+username+"&sjhm="+sjhm+"&yzm="+yzm;
			String jsonStr=HttpClientUtil.getResponse("POST", httpurl+"/unbindingMobile.do", par);
			JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
			if(obj!=null&&obj.size()>0){
				if(obj.getInt("code")==201){
					/**
					 *如果成功，则返回true
					 *并返回成功的提示信息 
					 */
					result = new ResultEntity<String>(1,obj.getString("message"),null);
				}else{
					/**
					 *如果失败，则返回false
					 *并返回失败的提示信息 
					 */
					result = new ResultEntity<String>(0,obj.getString("message"), null);
				}
			}
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("解除绑定手机unbindingMobile err：");
			logger.error(e, e.fillInStackTrace());
		}
	}

	/**
	 *判断绑定手机或邮箱 /bdMobileOrEmail.do   username
	 *@param apptoken
	 *@param username
	 *@return
	 *@author yangbilin 
	 */
	public void isBindingMobileOrEmail(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
        	PrintWriter out = response.getWriter();
        	String username = null;
       	 	ResultEntity<JSONObject> result = null;
       	 	String apptoken=request.getParameter("apptoken");
       	    if(StringUtils.isNotBlank(request.getParameter("username"))){
	     		username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	     	}
		 
       	    Gson gson = new Gson();
		 	if(StringUtil.isEmpty(username) || StringUtil.isEmpty(apptoken)){
		 		result = new ResultEntity<JSONObject>(0, "参数传值出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	if(!ApptokenUtils.compare(username, apptoken)){
		 		result = new ResultEntity<JSONObject>(2, "app_token error!", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	try {
				username = CodeUtil.decode(username, apptoken);
				if(StringUtils.isNotBlank(username)){
					username=Base64.encode(username.getBytes());
				}
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			String par="username="+username;
			String jsonStr=HttpClientUtil.getResponse("POST", httpurl+"/bdMobileOrEmail.do", par);
			JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
			if(obj!=null&&obj.size()>0){
				int code=obj.getInt("code");
				JSONObject object = new JSONObject();
				if(code==501){
					/**
					 *如果成功，则返回true
					 *并返回成功的提示信息 :手机号码和邮箱都绑定
					 */
					object.put("sjhm", obj.getString("telephone"));
					object.put("email", obj.getString("email"));
					result = new ResultEntity<JSONObject>(1,obj.getString("message"),object);
				}else if(code==502){
					/**
					 *如果成功，则返回true
					 *并返回提示信息 ：该用户手机号绑定，邮箱未绑定！
					 */
					object.put("sjhm", obj.getString("telephone"));
					result = new ResultEntity<JSONObject>(1,obj.getString("message"),object);
				}else if(code==503){
					/**
					 *如果成功，则返回true
					 *并返回提示信息 ：该用户邮箱绑定，手机号未绑定！
					 */
					object.put("email", obj.getString("email"));
					result = new ResultEntity<JSONObject>(1,obj.getString("message"),object);
				}else if(code==504){
					/**
					 *如果成功，则返回true
					 *并返回提示信息 ：该用户邮箱未绑定，手机号未绑定！
					 */
					result = new ResultEntity<JSONObject>(1,obj.getString("message"), null);
				}else{
					/**
					 *如果失败，则返回false
					 *并返回失败的提示信息 
					 */
					result = new ResultEntity<JSONObject>(0,obj.getString("message"), null);
				}
			}
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("获取手机绑定验证码 getYzmForBindingMobile  err：");
			logger.error(e, e.fillInStackTrace());
		}
	}

	/**
	 *获取激活码/getAcodeForBdEmail.do  username email
	 *@param apptoken
	 *@param username
	 *@param email
	 *@return
	 *@author yangbilin 
	 */
	public void getActivecodeForBindingEmail(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
        	PrintWriter out = response.getWriter();
        	String username = null;
        	String email=null;
       	 	ResultEntity<String> result = null;
       	 	String apptoken=request.getParameter("apptoken");
       	  if(StringUtils.isNotBlank(request.getParameter("username"))){
	     		username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	     	}
       	    if(StringUtils.isNotBlank(request.getParameter("email"))){
       	    	email = new String(request.getParameter("email").getBytes("ISO8859-1"), "UTF-8");
	     	}
		 
       	    Gson gson = new Gson();
       	   if(StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(username) ||StringUtil.isEmpty(email)){
		 		result = new ResultEntity<String>(0, "参数传值出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	if(!ApptokenUtils.compare(username, apptoken)){
		 		result = new ResultEntity<String>(2, "app_token error!", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	try {
		 		
				username = CodeUtil.decode(username, apptoken);
				email = CodeUtil.decode(email, apptoken);
		 		
				if(StringUtils.isNotBlank(username)){
					username=Base64.encode(username.getBytes());
				}
				if(StringUtils.isNotBlank(email)){
					email=Base64.encode(email.getBytes());
				}
			} catch (Exception e) {
				result = new ResultEntity<String>(0, "加密方式出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			String par="username="+username+"&email="+email;
			Map<String,Object> resMap = commonSecureCenter2(httpurl+"/getAcodeForBdEmail.do",par,gson);
			if(resMap!=null&&resMap.size()>0){
				result = new ResultEntity<String>((Integer)resMap.get("status"),(String)resMap.get("msg"),null);
			}
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("绑定邮箱获取激活getActivecodeForBindingEmail  err：");
			logger.error(e, e.fillInStackTrace());
		}
	}
	
	/**
	 *绑定邮箱/bindingEmail.do  username radomnumber
	 *@param apptoken
	 *@param username
	 *@param radomnumber
	 *@return
	 *@author yangbilin 
	 */
	public void bindingEmail(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
        	PrintWriter out = response.getWriter();
        	ResultEntity<String> result = null;
        	String username=null;
       	 	String apptoken=request.getParameter("apptoken");
       	 	if(StringUtils.isNotBlank(request.getParameter("username"))){
	     		username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	     	}
       	 	String activecode=StringUtil.isEmpty(request.getParameter("radomnumber")) ? "" : request.getParameter("radomnumber");
       	    Gson gson = new Gson();
		 	if(StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(username)||StringUtil.isEmpty(activecode)){
		 		result = new ResultEntity<String>(0, "参数传值出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	if(!ApptokenUtils.compare(username, apptoken)){
		 		result = new ResultEntity<String>(2, "app_token error!", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	try {
		 		username = CodeUtil.decode(username, apptoken);
		 		activecode = CodeUtil.decode(activecode, apptoken);
				
				if(StringUtils.isNotBlank(username)){
					username = Base64.encode(username.getBytes());
				}
				if(StringUtils.isNotBlank(activecode)){
					activecode=Base64.encode(activecode.getBytes());
				}
			} catch (Exception e) {
				result = new ResultEntity<String>(0, "加密方式出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			String par="username="+username+"&radomnumber="+activecode;
			String jsonStr=HttpClientUtil.getResponse("POST", httpurl+"/bindingEmail.do", par);
			JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
			if(obj!=null&&obj.size()>0){
				if(obj.getInt("code")==203){
					/**
					 *如果成功，则返回true
					 *并返回成功的提示信息 
					 */
					result = new ResultEntity<String>(1,obj.getString("message"),null);
				}else{
					/**
					 *如果失败，则返回false
					 *并返回失败的提示信息 
					 */
					result = new ResultEntity<String>(0,obj.getString("message"), null);
				}
			}
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("绑定邮箱bindingEmail err：");
			logger.error(e, e.fillInStackTrace());
		}
		
	}
	
	/**
	 *解除绑定邮箱/unbindingEmail.do  email  radomnumber
	 *@param apptoken
	 *@param email
	 *@param radomnumber
	 *@return
	 *@author yangbilin 
	 */
	public void unbindingEmail(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
        	ResultEntity<String> result = null;
       	 	String apptoken=request.getParameter("apptoken");
       	 	String email=StringUtil.isEmpty(request.getParameter("email")) ? "" : request.getParameter("email");
       	 	String activecode=StringUtil.isEmpty(request.getParameter("radomnumber")) ? "" : request.getParameter("radomnumber");
       	 	Gson gson = new Gson();
       	    if(StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(email)||StringUtil.isEmpty(activecode)){
		 		result = new ResultEntity<String>(0, "参数传值出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	try {
		 		email = CodeUtil.decode(email, apptoken);
		 		activecode = CodeUtil.decode(activecode, apptoken);
				
		 		if(StringUtils.isNotBlank(email)){
					email = Base64.encode(email.getBytes());
				}
		 		if(StringUtils.isNotBlank(activecode)){
					activecode=Base64.encode(activecode.getBytes());
				}
			} catch (Exception e) {
				result = new ResultEntity<String>(0, "加密方式出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			String par="email="+email+"&radomnumber="+activecode;
			String jsonStr=HttpClientUtil.getResponse("POST", httpurl+"/unbindingEmail.do", par);
			JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
			if(obj!=null&&obj.size()>0){
				if(obj.getInt("code")==207){
					/**
					 *如果成功，则返回true
					 *并返回成功的提示信息 
					 */
					result = new ResultEntity<String>(1,obj.getString("message"),null);
				}else{
					/**
					 *如果失败，则返回false
					 *并返回失败的提示信息 
					 */
					result = new ResultEntity<String>(0,obj.getString("message"), null);
				}
			}
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("解除绑定邮箱unbindingEmail err：");
			logger.error(e, e.fillInStackTrace());
		}
	}
	
	/**
	 *通过手机找回密码 /findpwdByMobile.do  sjhm yzm  xmm
	 *@param sjhm
	 *@param yzm
	 *@param xmm
	 *@return
	 *@author yangbilin 
	 */
	public void findpwdByMobile(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
        	PrintWriter out = response.getWriter();
        	ResultEntity<String> result = null;
       	 	//String apptoken=request.getParameter("apptoken");
       	 	String sjhm=StringUtil.isEmpty(request.getParameter("sjhm")) ? "" : request.getParameter("sjhm");
       	    String yzm=StringUtil.isEmpty(request.getParameter("yzm")) ? "" : request.getParameter("yzm");
       	    String xmm=StringUtil.isEmpty(request.getParameter("xmm")) ? "" : request.getParameter("xmm");
       	    
       	    Gson gson = new Gson();
		 	if(StringUtil.isEmpty(sjhm)||StringUtil.isEmpty(yzm)||StringUtil.isEmpty(xmm)){
		 		result = new ResultEntity<String>(0, "参数传值出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	
		 	try {
				//sjhm = CodeUtil.decode(sjhm, apptoken);
				//yzm = CodeUtil.decode(yzm, apptoken);
				//xmm = CodeUtil.decode(xmm, apptoken);
		 		
		 		sjhm = new String(Base64.decode(sjhm));
		 		yzm =new String(Base64.decode(yzm));
		 		xmm = new String(Base64.decode(xmm));
		 		
				if(StringUtils.isNotBlank(sjhm)){
					sjhm = Base64.encode(sjhm.getBytes());
				}
				if(StringUtils.isNotBlank(yzm)){
					yzm=Base64.encode(yzm.getBytes());
				}
				if(StringUtils.isNotBlank(xmm)){
					xmm=Base64.encode(xmm.getBytes());
				}
			} catch (Exception e) {
				result = new ResultEntity<String>(0, "加密方式出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			String par="sjhm="+sjhm+"&yzm="+yzm+"&xmm="+xmm;
			Map<String,Object> resMap = commonSecureCenter2(httpurl+"/findpwdByMobile.do",par,gson);
			if(resMap!=null&&resMap.size()>0){
				result = new ResultEntity<String>((Integer)resMap.get("status"),(String)resMap.get("msg"),null);
			}
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("通过手机找回密码findpwdByMobile err：");
			logger.error(e, e.fillInStackTrace());
		}
	}
	
	/**
	 *通过手机获取验证码    包含两个方法：通过手机找回密码获取验证码、解绑手机获取验证码
	 *@param sjhm
	 *@param method
	 *@param apptoken 
	 *@return code =1 message提示信息
	 *@author yangbilin 
	 *@date 2017-08-02  09:20
	 */
	public void getVerificationcodeForMobile(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
        	PrintWriter out = response.getWriter();
        	ResultEntity<String> result = null;
       	 	String apptoken=request.getParameter("apptoken");
       	 	String sjhm=StringUtil.isEmpty(request.getParameter("sjhm")) ? "" : request.getParameter("sjhm");
       	 	String method=StringUtil.isEmpty(request.getParameter("method")) ? "" : request.getParameter("method");
       	 	
       	 	Gson gson = new Gson();
		 	if(StringUtils.isEmpty(apptoken)||StringUtil.isEmpty(sjhm)||StringUtil.isEmpty(method)){
		 		result = new ResultEntity<String>(0, "参数传值出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	try {
		 		if("0".equals(apptoken)){
		 			method = new String(Base64.decode(method));
		 			sjhm = new String(Base64.decode(sjhm));
		 		}else{
		 			method =CodeUtil.decode(method, apptoken);
		 			/**
			 		 *使用3des通过apptoken解密sjhm
			 		 *获取明文 
			 		 */
		 			sjhm =CodeUtil.decode(sjhm, apptoken);
		 		}	
		 		if(StringUtils.isBlank(method)){
		 			result = new ResultEntity<String>(0, "方法名不能为空！", null);
			 		out.write(gson.toJson(result));
					out.flush();
					out.close();
					return;
		 		}
				/**
				 *对手机号码明文进行base64位编码，传递到ca 
				 */
				if(StringUtils.isNotBlank(sjhm)){
					sjhm = Base64.encode(sjhm.getBytes());
				}
			} catch (Exception e) {
				result = new ResultEntity<String>(0, "加密方式出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			String par="sjhm="+sjhm;
			String reqUrl =httpurl;
			/**
			 * findpwd  通过手机找回密码获取验证码
			 * unbinding 解绑手机获取验证码
			 */
			if("findpwd".equals(method)){
				reqUrl=reqUrl+"/getVcodeForpwdByMobile.do";
			}else if("unbinding".equals(method)){
				reqUrl=reqUrl+"/getVericodeForUnbdMobile.do";
			}else{
				result = new ResultEntity<String>(0, "方法名传递错误，不存在该方法！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			Map<String,Object> resMap = commonSecureCenter(reqUrl,par,gson);
			if(resMap!=null&&resMap.size()>0){
				result = new ResultEntity<String>((Integer)resMap.get("status"),(String)resMap.get("msg"),null);
			}
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("通过手机获取验证码getVerificationcodeForMobile err：");
			logger.error(e, e.fillInStackTrace());
		}
	}
	
	/**
	 *通过邮箱获取激活码解除绑定或找回密码    包含两个方法：通过邮箱获取激活码解除绑定、找回密码 
	 *@param email
	 *@param method
	 *@param apptoken 
	 *@return code =204 message提示信息
	 *@author yangbilin 
	 *@date 2017-08-02  09:40
	 */
	public void getActivecodeUnbindOrFindpwdByEmail(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
        	PrintWriter out = response.getWriter();
        	ResultEntity<String> result = null;
       	 	String apptoken=request.getParameter("apptoken");
       	 	String email=StringUtil.isEmpty(request.getParameter("email")) ? "" : request.getParameter("email");
       	 	String method=StringUtil.isEmpty(request.getParameter("method")) ? "" : request.getParameter("method");
       	 	
       	 	Gson gson = new Gson();
		 	if(StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(email)||StringUtil.isEmpty(method)){
		 		result = new ResultEntity<String>(0, "参数传值出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	try {
		 		if("0".equals(apptoken)){
		 			method = new String(Base64.decode(method));
		 			email = new String(Base64.decode(email));
		 		}else{
		 			method =CodeUtil.decode(method, apptoken);
		 			/**
			 		 *使用3des通过apptoken解密sjhm
			 		 *获取明文 
			 		 */
		 			email =CodeUtil.decode(email, apptoken);
		 		}	
		 		if(StringUtils.isBlank(method)){
		 			result = new ResultEntity<String>(0, "方法名不能为空！", null);
			 		out.write(gson.toJson(result));
					out.flush();
					out.close();
					return;
		 		}
		 		if(StringUtils.isNotBlank(email)){
					email = Base64.encode(email.getBytes());
				}
			} catch (Exception e) {
				result = new ResultEntity<String>(0, "加密方式出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			String par="email="+email;
			String reqUrl =httpurl;
			/**
			 * findpwd  通过邮箱找回密码
			 * unbinding 解绑邮箱获取激活码
			 */
			if("findpwd".equals(method)){
				reqUrl=reqUrl+"/findpwdByEmail.do";
			}else if("unbinding".equals(method)){
				reqUrl=reqUrl+"/getAcodeForUnbdEmail.do";
			}else{
				result = new ResultEntity<String>(0, "方法名传递错误，不存在该方法！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			Map<String,Object> resMap = commonSecureCenter2(reqUrl,par,gson);
			if(resMap!=null&&resMap.size()>0){
				result = new ResultEntity<String>((Integer)resMap.get("status"),(String)resMap.get("msg"),null);
			}
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("通过邮箱获取激活码解除绑定或找回密码getActivecodeUnbindOrFindpwdByEmail err：");
			logger.error(e, e.fillInStackTrace());
		}
	}
	

	/**
	 * 通过openid进行校验   getTicketByOpenid.do  
	 * @param openid
	 * @param apptoken  
	 * @return    cacheck, xm,  bm, yhm,zjhm, jsName
	 * @author yangbilin
	 */
	public void getTicketByOpenid(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
        	PrintWriter out = response.getWriter();
        	ResultEntity<LoginEntity> result = null;
        	
        	String strKey   = StringUtil.isEmpty(request.getParameter("strKey")) ? "" : request.getParameter("strKey");
       	 	String openid=StringUtil.isEmpty(request.getParameter("openid")) ? "" : request.getParameter("openid");
       	 	
       	 	Gson gson = new Gson();
		 	if(StringUtil.isEmpty(openid)||StringUtil.isEmpty(strKey)){
		 		result = new ResultEntity<LoginEntity>(0, "参数传值出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	try {
		 		strKey=new String(Base64.decode(strKey));
		 		openid = new String(Base64.decode(openid));
		 		if(StringUtils.isNotBlank(openid)){
		 			openid = Base64.encode(openid.getBytes());
				}
			} catch (Exception e) {
				result = new ResultEntity<LoginEntity>(0, "加密方式出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			String par="openid="+openid;
			String jsonStr=HttpClientUtil.getResponse("POST", httpurl+"/getTicketByOpenid.do", par);
			JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
			if(obj!=null&&obj.size()>0){
				if(obj.containsKey("cacheck")&&obj.getBoolean("cacheck")){
					/**
					 *如果成功，则返回true
					 *并返回成功的提示信息 
					 */
					String username = obj.getString("yhm");
					String bm = obj.getString("bm");
					Map<String,Object> map = getLoginUser(username,strKey,bm,request);
					if(map!=null&&map.size()>0){
						if((Boolean) map.get("success")){
							LoginEntity entity = (LoginEntity) map.get("data");
							result = new ResultEntity<LoginEntity>(1,"成功",entity);
						}else{
							result = new ResultEntity<LoginEntity>(0,(String) map.get("msg"), null);
						}
					}
				}else{
					/**
					 *如果失败，则返回false
					 *并返回失败的提示信息 
					 */
					result = new ResultEntity<LoginEntity>(0,obj.getString("message"), null);
				}
			}
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("根据openid进行校验getTicketByOpenid  err：");
			logger.error(e, e.fillInStackTrace());
		}
	}
	
	/**
	 * 根据ca的openid认证,获取移动端用户信息
	 * @param username
	 * @param strKey
	 * @param bm
	 * @param request
	 * @return Map<String, Object>
	 * @author yangbilin
	 */
	private Map<String, Object> getLoginUser(String username,String strKey,String bm,HttpServletRequest request ){
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtil.isEmpty(username) || StringUtil.isEmpty(strKey)){
			logger.error("参数传值错误");
			map.put("msg","参数传值错误");
			map.put("success",false);
			return map;
		}
		LoginEntity entity = new LoginEntity();
		try {
			if(infromation.equals("0")){
				logger.error("获取登陆接口："+"username="+username+",strKey="+strKey);
			}
			if (Authentication.authenticate(strKey)) {
				LoginModel model = new LoginModel();
				model.setYhm(username);
				model.setMm(null);
				User user = loginService.cxYhxx(model);
				
				YhglModel yhglModel = new YhglModel();
				yhglModel.setZgh(username);
				String keyCode = null;
				try {
					keyCode = MD5Util.md5Encode(username + strKey + System.currentTimeMillis());
					if(StringUtils.isEmpty(keyCode)){
						logger.error("登录接口login keyCode为空 err：");
						map.put("msg","登录接口login keyCode为空");
						map.put("success",false);
						return map;
					}
				} catch (Exception e) {
					logger.error("登录接口login 产生密钥异常 err：");
					logger.error(e, e.fillInStackTrace());
					map.put("msg","登录接口login 产生密钥异常 err");
					map.put("success",false);
					return map;
				}
				yhglModel.setStrKey(keyCode);
				loginService.updateStrKey(yhglModel);
			if (user != null) {
				if(!StringUtil.isEmpty(user.getSfqy()) && !user.getSfqy().equals("1")){
					logger.error("登录接口login 产生密钥异常 err：");
					map.put("msg","登录接口login 产生密钥异常 err");
					map.put("success",false);
					return map;
				}
				String dqxnxq = "";
				String appName = MobileSystemHolder.getPropertiesValue("app_name");
				entity.setName(user.getXm());
				if ("student".equals(user.getYhlx())) {
					entity.setRole("XS");
				} else {
					entity.setRole("JS");
				}
				entity.setDepartment(bm);
				entity.setSchoolName("");
				entity.setClassName("");
				entity.setGradeName("");
				entity.setUserId(user.getYhm());
				entity.setAppname(appName);
				entity.setNowSchoolYearTerm(dqxnxq);
				entity.setApp_token(keyCode);
				
			}else{
				logger.error("登录接口login 产生密钥异常 err：");
				map.put("msg","登录接口login 产生密钥异常 err");
				map.put("success",false);
				return map;
			}
			if(infromation.equals("0")){
				logger.error("登陆接口返回为："+entity);
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
					FileUntils fileUntils = new FileUntils();
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
							map.put("msg","登录接口login 我的头像生成产生异常err");
							map.put("success",false);
							return map;
						}
					} 
					headPicturePath = fileUntils.getImageHost() + path;
					entity.setHeadPicturePath(headPicturePath);
				}
			}
			map.put("msg","获取用户信息成功");
			map.put("success",true);
			map.put("data",entity);
		 }
		}catch (Exception e) {
			logger.error("登录接口login 产生密钥异常 err：");
			logger.error(e, e.fillInStackTrace());
			map.put("msg","登录接口login 产生密钥异常 err");
			map.put("success",false);
			return map;
		}
		return map;
	}
	
	
	
	/**
	 * 绑定用户和第三方账号
	 * @param username 用户ID
	 * @param openid 第三方账号
	 * @param opentype 第三方类型
	 * @param apptoken
	 * @return
	 * @author yangbilin
	 */
	public void bindingUser(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
        	ResultEntity<String> result = null;
       	 	String apptoken=request.getParameter("apptoken");
       	 	String uid=StringUtil.isEmpty(request.getParameter("username")) ? "" : request.getParameter("username");
       	 	String openid=StringUtil.isEmpty(request.getParameter("openid")) ? "" : request.getParameter("openid");
       	 	String opentype=StringUtil.isEmpty(request.getParameter("opentype")) ? "" : request.getParameter("opentype");
       	 	
       	 	Gson gson = new Gson();
		 	if(StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(uid)||StringUtil.isEmpty(openid)||StringUtil.isEmpty(opentype)){
		 		result = new ResultEntity<String>(0, "参数传值出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	
		 	if(!ApptokenUtils.compare(uid, apptoken)){
		 		result = new ResultEntity<String>(2, "app_token error!", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 
		 	try {
		 		uid = CodeUtil.decode(uid, apptoken);
		 		openid = CodeUtil.decode(openid, apptoken);
		 		opentype = CodeUtil.decode(opentype, apptoken);
		 		
		 		if(StringUtils.isNotBlank(uid)){
		 			uid = Base64.encode(uid.getBytes());
				}
		 		if(StringUtils.isNotBlank(openid)){
		 			openid = Base64.encode(openid.getBytes());
				}
		 		if(StringUtils.isNotBlank(opentype)){
		 			opentype = Base64.encode(opentype.getBytes());
				}
			} catch (Exception e) {
				result = new ResultEntity<String>(0, "加密方式出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			String par="uid="+uid+"&openid="+openid+"&opentype="+opentype;
			String jsonStr=HttpClientUtil.getResponse("POST", httpurl+"/bindingUser.do", par);
			JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
			if(obj!=null&&obj.size()>0){
				if(obj.getInt("code")==201){
					/**
					 *如果成功，则返回true
					 *并返回成功的提示信息 
					 */
					result = new ResultEntity<String>(1,obj.getString("message"),null);
				}else{
					/**
					 *如果失败，则返回false
					 *并返回失败的提示信息 
					 */
					result = new ResultEntity<String>(0,obj.getString("message"), null);
				}
			}
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("绑定用户和第三方账号bindingUser err：");
			logger.error(e, e.fillInStackTrace());
		}
	}
	
	
	/**
	 * 解除用户和第三方账号绑定  /unbindingUser.do
	 * @param username 用户ID
	 * @param opentype 账号类型
	 * @return
	 * @author yangbilin
	 */
	public void unbindingUser(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
        	ResultEntity<String> result = null;
       	 	String apptoken=request.getParameter("apptoken");
       	 	String uid=StringUtil.isEmpty(request.getParameter("username")) ? "" : request.getParameter("username");
       	 	String opentype=StringUtil.isEmpty(request.getParameter("opentype")) ? "" : request.getParameter("opentype");
       	 	
       	 	Gson gson = new Gson();
		 	if(StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(uid)||StringUtil.isEmpty(opentype)){
		 		result = new ResultEntity<String>(0, "参数传值出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	
		 	if(!ApptokenUtils.compare(uid, apptoken)){
		 		result = new ResultEntity<String>(2, "app_token error!", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	
		 	try {
		 		uid = CodeUtil.decode(uid, apptoken);
		 		opentype = CodeUtil.decode(opentype, apptoken);
		 		
		 		if(StringUtils.isNotBlank(uid)){
		 			uid = Base64.encode(uid.getBytes());
				}
		 		if(StringUtils.isNotBlank(opentype)){
		 			opentype = Base64.encode(opentype.getBytes());
				}
			} catch (Exception e) {
				result = new ResultEntity<String>(0, "加密方式出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			String par="uid="+uid+"&opentype="+opentype;
			String jsonStr=HttpClientUtil.getResponse("POST", httpurl+"/unbindingUser.do", par);
			JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
			if(obj!=null&&obj.size()>0){
				if(obj.getInt("code")==201){
					/**
					 *如果成功，则返回true
					 *并返回成功的提示信息 
					 */
					result = new ResultEntity<String>(1,obj.getString("message"),null);
				}else{
					/**
					 *如果失败，则返回false
					 *并返回失败的提示信息 
					 */
					result = new ResultEntity<String>(0,obj.getString("message"), null);
				}
			}
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("解除绑定用户和第三方账号unbindingUser err：");
			logger.error(e, e.fillInStackTrace());
		}
	}

	/**
	 * 根据用户openid获取用户信息  /getUserinfo.do
	 * @param openid
	 * @param apptoken
	 * @return  jsonstring    yhm,kl,xm,opentype
	 * @author yangbilin
	 * @date 2017-08-03 09:40
	 */
	public void getUserinfo(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
        	ResultEntity<JSONObject> result = null;
       	 	String apptoken=request.getParameter("apptoken");
       	 	String openid=StringUtil.isEmpty(request.getParameter("openid")) ? "" : request.getParameter("openid");
       	 	
       	 	Gson gson = new Gson();
		 	if(StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(openid)){
		 		result = new ResultEntity<JSONObject>(0, "参数传值出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	
		 	try {
		 		openid = CodeUtil.decode(openid, apptoken);
		 		
		 		if(StringUtils.isNotBlank(openid)){
		 			openid = Base64.encode(openid.getBytes());
				}
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			String par="openid="+openid;
			String jsonStr=HttpClientUtil.getResponse("POST", httpurl+"/getUserinfo.do", par);
			JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
			if(obj!=null&&obj.size()>0){
				if(obj.containsKey("yhm")){
					/**
					 *获取用户信息
					 */
					result = new ResultEntity<JSONObject>(1,"成功",obj);
				}else{
					/**
					 *返回失败
					 */
					result = new ResultEntity<JSONObject>(0,obj.getString("message"), null);
				}
			}else{
				/**
				 *返回失败
				 */
				result = new ResultEntity<JSONObject>(0,"失败", null);
			}
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("根据用户openid获取用户信息getUserinfo err：");
			logger.error(e, e.fillInStackTrace());
		}
	}

	/**
	 * 根据用户ID获取绑定关系/getUserBinding.do 
	 * @param uid
	 * @param apptoken
	 * @return  jsonstring   yhm,openid, opentype
	 * @author yangbilin
	 * @date 2017-08-03 09:40
	 */
	public void getUserBinding(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
        	ResultEntity<JSONArray> result = null;
       	 	String apptoken=request.getParameter("apptoken");
       	 	String uid=StringUtil.isEmpty(request.getParameter("username")) ? "" : request.getParameter("username");
       	 	
       	 	Gson gson = new Gson();
		 	if(StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(uid)){
		 		result = new ResultEntity<JSONArray>(0, "参数传值出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	
		 	if(!ApptokenUtils.compare(uid, apptoken)){
		 		result = new ResultEntity<JSONArray>(2, "app_token error!", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
		 	
		 	try {
		 		uid = CodeUtil.decode(uid, apptoken);
		 	
		 		if(StringUtils.isNotBlank(uid)){
		 			uid = Base64.encode(uid.getBytes());
				}
			} catch (Exception e) {
				result = new ResultEntity<JSONArray>(0, "加密方式出错！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			String par="uid="+uid;
			String jsonStr=HttpClientUtil.getResponse("POST", httpurl+"/getUserBinding.do", par);
			JSONArray jsonarr=gson.fromJson(jsonStr, JSONArray.class);
			if(jsonarr!=null&&jsonarr.size()>0){
				if(jsonarr.size()==1){
					JSONObject object = jsonarr.getJSONObject(0);
					if(object.containsKey("code")){
						result = new ResultEntity<JSONArray>(0,(String)object.get("message"), jsonarr);
					}else{
						result = new ResultEntity<JSONArray>(1,"成功",jsonarr);
					}
				}else{
					result = new ResultEntity<JSONArray>(1,"成功",jsonarr);
				}
			}else{
				result = new ResultEntity<JSONArray>(1,"未绑定", null);
			}
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("根据用户ID获取绑定关系getUserBinding err：");
			logger.error(e, e.fillInStackTrace());
		}
	}

	
	/**
	 *通用方法1：code=1表示成功的方法
	 *@param reqUrl
	 *@param par
	 *@param gson
	 *@return Map<String,Object>
	 *@author yangbilin 
	 */
	private Map<String,Object> commonSecureCenter(String reqUrl,String par,Gson gson){
		String jsonStr=HttpClientUtil.getResponse("POST", reqUrl, par);
		JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
		Map<String,Object> map = new HashMap<String, Object>();
		if(obj!=null&&obj.size()>0){
			if(obj.getInt("code")==1){
				/**
				 *如果成功，则返回true
				 *并返回成功的提示信息 
				 */
				map.put("status",1);
				map.put("success",true);
			}else{
				/**
				 *如果失败，则返回false
				 *并返回失败的提示信息 
				 */
				map.put("status",0);
				map.put("success",false);
			}
			map.put("msg",obj.getString("message"));
		}
		return map;
	}
	
	/**
	 *通用方法2：code=204表示成功的方法
	 *@param reqUrl
	 *@param par
	 *@param gson
	 *@return Map<String,Object>
	 *@author yangbilin 
	 */
	private Map<String,Object> commonSecureCenter2(String reqUrl,String par,Gson gson){
		String jsonStr=HttpClientUtil.getResponse("POST", reqUrl, par);
		JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
		Map<String,Object> map = new HashMap<String, Object>();
		if(obj!=null&&obj.size()>0){
			//该code表示后台返回的code
			if(obj.getInt("code")==204){
				/**
				 *如果成功，则返回true
				 *并返回成功的提示信息 
				 */
				map.put("status",1);
				map.put("success",true);
			}else{
				/**
				 *如果失败，则返回false
				 *并返回失败的提示信息 
				 */
				map.put("status",0);
				map.put("success",false);
			}
			map.put("msg",obj.getString("message"));
		}
		return map;
	}
	
	public ILoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(ILoginService loginService) {
		this.loginService = loginService;
	}

	public static void main(String[] args) {
		//String jsonStr = "{\"code\":502,\"message\":\"该用户手机号绑定，邮箱未绑定！\",\"telephone\":\"13738004367\",\"email\":\"\"}";
		
		String jsonStr="[{\"yhm\":\"1399\",\"xm\":\"AA61F7EEC083ED8038A7CBEAE9C2AF3E\",\"opentype\":\"1\"},{\"yhm\":\"1399\",\"xm\":\"o22X0089P0RLbDAZMREC1H3CRirI\",\"opentype\":\"2\"}]";

		//
		/*JSONObject obj = JSONObject.fromObject(jsonStr);
		String code=(String) obj.get("yhm");
				JSONArray arr = JSONArray.fromObject(jsonStr);
		*/
		

		Gson gson = new Gson();
	
		JSONArray g1=gson.fromJson(jsonStr, JSONArray.class);

		for (int i = 0; i < g1.size(); i++) {
			JSONObject g=g1.getJSONObject(i);
			System.out.println(g.get("yhm"));
		}
		//JSONObject g=g1.getJSONObject(0);
	}
	
	
}
