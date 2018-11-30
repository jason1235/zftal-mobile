package com.zfsoft.mobile.servlet.oaHttp.action;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.common.Config;
import com.google.gson.Gson;
import com.oa.service.FileModel;
import com.util.WebServiceUtil;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.hrm.core.util.Byte_File_Object;
import com.zfsoft.mobile.common.utils.HttpClientUtil;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;

/**
 * OA接口调用
 * @author yangbilin
 * @date 2017-08-28
 */
public class OaHttpAction {
	
	private static Logger logger = Logger.getLogger(OaHttpAction.class);
	private final String httpurl=Config.getString("httpurl.host.oa")+"/oaMobile";
	private final String oasign=Config.getString("oasign");
	
	public void getAddressInfo(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
	   	    ResultEntity<JSONObject> result = null;
	   	    
	   	    String apptoken = request.getParameter("apptoken");
	   	    String username = request.getParameter("username");
	   	    String yhid = request.getParameter("yhid");
	 		
			Gson gson = new Gson();
			if(StringUtil.isEmpty(apptoken) || StringUtil.isEmpty(username) || StringUtil.isEmpty(yhid)){
				result = new ResultEntity<JSONObject>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			if(!ApptokenUtils.compare(username,apptoken)){
		 		result = new ResultEntity<JSONObject>(2, "app_token error!", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
			try {
				username = CodeUtil.decode(username, apptoken);
				yhid = CodeUtil.decode(yhid, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			//username="225";startStr="1";sizeStr="10";
			String par="yhm="+username+"&yhid="+yhid+"&sign="+(oasign+username);
			String reqUrl = httpurl+"/getAddressInfo";
			String jsonStr=HttpClientUtil.getResponse("POST",reqUrl, par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();                    
			out.close();	
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("通讯录getAddressInfo   err：");
			e.printStackTrace();
		}	
	}
	
	
	public void processDownload(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
        int BUFFER_SIZE = 4096;
        InputStream in = null;
        OutputStream out = null;
        
        System.out.println("Come on, baby .......");
        
        try{
            request.setCharacterEncoding("utf-8");  
            response.setCharacterEncoding("utf-8");  
            response.setContentType("application/octet-stream");
            
            String id = request.getHeader("id");
            
            System.out.println("id:" + id);
            
            //可以根据传递来的userName和passwd做进一步处理，比如验证请求是否合法等             
            File file = new File("C:\\Users\\Administrator\\Desktop\\catalina.out");
            response.setContentLength((int) file.length());
            response.setHeader("Accept-Ranges", "bytes");
            
            int readLength = 0;
            
            in = new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE);
            out = new BufferedOutputStream(response.getOutputStream());
            
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((readLength=in.read(buffer)) > 0) {
                byte[] bytes = new byte[readLength];
                System.arraycopy(buffer, 0, bytes, 0, readLength);
                out.write(bytes);
            }
            
            out.flush();
            
             
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }
	
	
	/**
	 * 1.3.3.5	文件下载getFileModel
	 */
	public void getFileModel(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter printWriterout = response.getWriter();
	   	    ResultEntity<String> result = null;
	   	    
	   	    String apptoken = new String(request.getParameter("apptoken").getBytes("ISO8859-1"), "UTF-8");
	   	    String id = new String(request.getParameter("id").getBytes("ISO8859-1"), "UTF-8");
	   	    String fileName = new String(request.getParameter("fileName").getBytes("ISO8859-1"), "UTF-8");
	 		
			Gson gson = new Gson();
			if(StringUtil.isEmpty(apptoken)|| StringUtil.isEmpty(id)||StringUtil.isEmpty(fileName)){
				result = new ResultEntity<String>(0, "参数传值出错！", null);
				printWriterout.write(gson.toJson(result));
				printWriterout.flush();
				printWriterout.close();
				return;
			}
			if(!ApptokenUtils.compare(apptoken)){
		 		result = new ResultEntity<String>(2, "app_token error!", null);
		 		printWriterout.write(gson.toJson(result));
		 		printWriterout.flush();
		 		printWriterout.close();
				return;
		 	}
			try {
				id = CodeUtil.decode(id, apptoken);
				fileName = CodeUtil.decode(fileName, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<String>(0, "加密方式出错！", null);
				printWriterout.write(gson.toJson(result));
				printWriterout.flush();
				printWriterout.close();
				return;
			}
			
			FileOutputStream out = null;
	        InputStream in = null;
	        
	        try{
	        	String reqUrl = httpurl+"/getFileModel";
	        	logger.error(reqUrl+"!!!");
	            URL url = new URL(reqUrl);
	            URLConnection urlConnection = url.openConnection();
	            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
	            
	            // true -- will setting parameters
	            httpURLConnection.setDoOutput(true);
	            // true--will allow read in from
	            httpURLConnection.setDoInput(true);
	            // will not use caches
	            httpURLConnection.setUseCaches(false);
	            // setting serialized
	            httpURLConnection.setRequestProperty("Content-type", "application/x-java-serialized-object");
	            // default is GET                        
	            httpURLConnection.setRequestMethod("POST");
	            httpURLConnection.setRequestProperty("connection", "Keep-Alive");
	            httpURLConnection.setRequestProperty("Charsert", "UTF-8");
	            // 1 min
	            httpURLConnection.setConnectTimeout(60000);
	            // 1 min
	            httpURLConnection.setReadTimeout(60000);

	            httpURLConnection.addRequestProperty("id", id);

	            // connect to server (tcp)
	            httpURLConnection.connect();

	            in = httpURLConnection.getInputStream();// send request to
	            
	            String localPath = request.getSession().getServletContext().getRealPath("") + File.separator +"upload" + File.separator + id+fileName.substring(fileName.indexOf("."), fileName.length());
	            logger.error(localPath+" !!!");
	            
	            // server
	            File file = new File(localPath);   
	            if(!file.exists()){
	            	logger.error("createFile!!! "+localPath);
	                file.createNewFile();
	            }

	            out = new FileOutputStream(file); 
	            byte[] buffer = new byte[4096];
	            int readLength = 0;
	            while ((readLength=in.read(buffer)) > 0) {
	                byte[] bytes = new byte[readLength];
	                System.arraycopy(buffer, 0, bytes, 0, readLength);
	                out.write(bytes);
	            }
	            
	            out.flush();
	        }catch(Exception e){
	            e.printStackTrace();
	        }finally{
	            try {
	                if(in != null){
	                    in.close();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            
	            try {
	                if(out != null){
	                    out.close();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
			String fileUrl = getImageHost() + "/upload/" + id+fileName.substring(fileName.indexOf("."), fileName.length());
	        result = new ResultEntity<String>(1, "成功", fileUrl);
			printWriterout.write(gson.toJson(result));
			printWriterout.flush();
			printWriterout.close();
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("文件下载getFileModel   err：");
			e.printStackTrace();
		}	
	}
	
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
	 * <p>Description: 将CLASS拼装XML</p>
	 * @param output
	 * @param list
	 * @param lx
	 * @return
	 *
	 * @since Nov 13, 2012 2:19:31 PM
	 * @author huangzhaoxia
	 */
	@SuppressWarnings("unchecked")
	public static String xmlToStringNew(String []output,List list,String lx)
	{
		StringBuffer msg=new StringBuffer();
		msg.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?> <ZFSOFT>");
		
		Iterator iterator=list.iterator();
		while(iterator.hasNext())
		{
			HashMap<String,String> hash=new HashMap<String,String>();
			hash=(HashMap<String,String>)iterator.next();
			msg.append("<"+lx.toUpperCase()+">");
			
		    for(int i=0;i<output.length;i++)
		    {
		    	msg.append("<"+output[i].toUpperCase()+">");
		    	msg.append(hash.get(output[i])==null?"":hash.get(output[i]));
		    	msg.append("</"+output[i].toUpperCase()+">");
		    }
			msg.append("</"+lx+">");
			
		}
		msg.append("</ZFSOFT>");
		
		return msg.toString();
	}
	
	/**
	 * 取得用户发送权限getSendInfo
	 */
	public void getSendInfo(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String username="";
	   	    ResultEntity<JSONObject> result = null;
	   	    
	   	    String apptoken = request.getParameter("apptoken");
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		
			Gson gson = new Gson();
			if(StringUtil.isEmpty(apptoken)|| StringUtil.isEmpty(username)){
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
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			//username="225";startStr="1";sizeStr="10";
			String par="yhm="+username+"&sign="+(oasign+username);
			String reqUrl = httpurl+"/getSendInfo";
			String jsonStr=HttpClientUtil.getResponse("POST",reqUrl, par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();	
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("取得用户发送权限getSendInfo   err：");
			e.printStackTrace();
		}	
	}
	
	/**
	 * 1.3.3.3	 查询用户和部门信息getDepAndUserInfoForSearch
	 */
	public void getDepAndUserInfoForSearch(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String username="";
			String name = "";
	   	    ResultEntity<JSONObject> result = null;
	   	    
	   	    String apptoken = request.getParameter("apptoken");
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("name"))){
	 			name = new String(request.getParameter("name").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		
			Gson gson = new Gson();
			if(StringUtil.isEmpty(apptoken)|| StringUtil.isEmpty(username)
					|| StringUtil.isEmpty(name)){
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
				name =URLEncoder.encode(CodeUtil.decode(name, apptoken),"UTF-8");
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			//username="225";startStr="1";sizeStr="10";
			String par="yhm="+username+"&name="+name+"&sign="+(oasign+username);
			String reqUrl = httpurl+"/getDepAndUserInfoForSearch";
			String jsonStr=HttpClientUtil.getResponse("POST",reqUrl, par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();	
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("查询用户和部门信息 getDepAndUserInfoForSearch   err：");
			e.printStackTrace();
		}	
	}
	
	/**
	 * 1.3.3.2	 根据部门代码获取部门对应用户和子部门
	 */
	public void getDepAndUserByDepNum(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String username="",depname="";
	   	    ResultEntity<JSONObject> result = null;
	   	    
	   	    String apptoken = request.getParameter("apptoken");
	   	    String depnum =StringUtil.isEmpty(request.getParameter("depnum")) ? "" : request.getParameter("depnum");
	   	    String sum =StringUtil.isEmpty(request.getParameter("sum")) ? "" : request.getParameter("sum");
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("depname"))){
	 			depname = new String(request.getParameter("depname").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		
			Gson gson = new Gson();
			if(StringUtil.isEmpty(apptoken)|| StringUtil.isEmpty(username)|| StringUtil.isEmpty(depnum)){
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
				depnum = CodeUtil.decode(depnum, apptoken);
				if(StringUtils.isNotBlank(depname)){
					depname =URLEncoder.encode(CodeUtil.decode(depname, apptoken),"UTF-8");
				}
				if(StringUtils.isNotBlank(sum)){
					sum = CodeUtil.decode(sum, apptoken);
				}
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			//username="225";startStr="1";sizeStr="10";
			String par="yhm="+username+"&sign="+(oasign+username)+"&depnum="+depnum+"&depname="+depname+"&sum="+sum;
			String reqUrl = httpurl+"/getDepAndUserByDepNum";
			String jsonStr=HttpClientUtil.getResponse("POST",reqUrl, par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();	
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("根据部门代码获取部门对应用户和子部门 getDepAndUserByDepNum   err：");
			e.printStackTrace();
		}	
	}
	
	/**
	 * 获取一级部门列表 
	 */
	public void getFirstDepInfo(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String username="";
	   	    ResultEntity<JSONObject> result = null;
	   	    
	   	    String apptoken = request.getParameter("apptoken");
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		
			Gson gson = new Gson();
			if(StringUtil.isEmpty(apptoken)|| StringUtil.isEmpty(username)){
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
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			//username="225";startStr="1";sizeStr="10";
			String par="yhm="+username+"&sign="+(oasign+username);
			String reqUrl = httpurl+"/getFirstDepInfo";
			String jsonStr=HttpClientUtil.getResponse("POST",reqUrl, par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();	
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("获取一级部门列表 getFirstDepInfo  err：");
			e.printStackTrace();
		}	
	}
	
	
	/**
	 * 获取流程类型列表 
	 */
	public void getLclxList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String username="";
	   	    ResultEntity<JSONObject> result = null;
	   	    
	   	    String apptoken = request.getParameter("apptoken");
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		
			Gson gson = new Gson();
			if(StringUtil.isEmpty(apptoken)|| StringUtil.isEmpty(username)){
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
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			//username="225";startStr="1";sizeStr="10";
			String par="yhm="+username+"&sign="+(oasign+username);
			String reqUrl = httpurl+"/getLclxList";
			String jsonStr=HttpClientUtil.getResponse("POST",reqUrl, par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();	
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("根据【待办事宜名和流程类型ID】获取列表 getLclxList  err：");
			e.printStackTrace();
		}	
	}
	
	
	/**
	 * 根据【待办事宜名和流程类型ID】获取待办列表 
	 * Yhm:当前登录的用户名
	 * Start：页码
	 * Size：每页数量
	 * Lxid:类型id
	 * Condition:名称
	 * sign:公钥
	 */
	public void getTaskListByConditionAndLx(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String username="",condition="";
	   	 	String startStr = null;
	   	 	String sizeStr=null;
	   	    ResultEntity<JSONObject> result = null;
	   	    
	   	    String apptoken = request.getParameter("apptoken");
	   	    String lxid = request.getParameter("lxid");
	   	    String method = request.getParameter("method");
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("condition"))){
	 			condition = new String(request.getParameter("condition").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		String startInit=StringUtil.isEmpty(request.getParameter("start")) ? "" : request.getParameter("start");
	 		String sizeInit=StringUtil.isEmpty(request.getParameter("size")) ? "" : request.getParameter("size");
	 		
			Gson gson = new Gson();
			if(StringUtil.isEmpty(apptoken)|| StringUtil.isEmpty(method)
					||StringUtil.isEmpty(startInit)|| StringUtil.isEmpty(sizeInit)
					|| StringUtil.isEmpty(username)|| StringUtil.isEmpty(lxid)){
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
				method = CodeUtil.decode(method, apptoken);
				if(StringUtils.isBlank(method)){
		 			result = new ResultEntity<JSONObject>(0, "方法名不能为空！", null);
			 		out.write(gson.toJson(result));
					out.flush();
					out.close();
					return;
		 		}
				username = CodeUtil.decode(username, apptoken);
				startStr = CodeUtil.decode(startInit, apptoken);
				sizeStr  = CodeUtil.decode(sizeInit, apptoken);
				lxid = CodeUtil.decode(lxid, apptoken);
				if(StringUtils.isNotBlank(condition)){
					condition  =URLEncoder.encode(CodeUtil.decode(condition, apptoken), "UTF-8") ;
				}
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			//username="225";startStr="1";sizeStr="10";
			String par="yhm="+username+"&start="+startStr+"&size="+sizeStr+"&lxid="+lxid+"&condition="+condition+"&sign="+(oasign+username);
			String reqUrl ="";
			if("getTodoTaskListByConditionAndLx".equals(method)){
				reqUrl=httpurl+"/getTodoTaskListByConditionAndLx";
			}else if("getDoneTaskListByConditionAndLx".equals(method)){
				reqUrl=httpurl+"/getDoneTaskListByConditionAndLx";
			}else if("getSoluteTaskListByConditionAndLx".equals(method)){
				reqUrl=httpurl+"/getSoluteTaskListByConditionAndLx";
			}else{
				result = new ResultEntity<JSONObject>(0, "方法名传递错误，不存在该方法！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			String jsonStr=HttpClientUtil.getResponse("POST",reqUrl, par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();	
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("根据【待办事宜名和流程类型ID】获取列表 getTodoTaskListByConditionAndLx  err：");
			e.printStackTrace();
		}	
	}
	
	
	/********************************流程事宜相关方法start********************************************/

	/**
	 *获取待办事宜列表getTodoTaskList/获取已办事宜列表getDoneTaskList/获取办结事宜列表getSoluteTaskList
	 *@param username
	 *@param size
	 *@param start
	 *@param method
	 *@param apptoken
	 *@return 
	 *@author yangbilin 
	 */
	public void getOaTaskListForTodoOrDoneOrSolute(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String username="";
	   	 	String startStr = null;
	   	 	String sizeStr=null;
	   	    ResultEntity<JSONObject> result = null;
	   	    
	   	    String apptoken = request.getParameter("apptoken");
	   	    String method = request.getParameter("method");
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		String startInit=StringUtil.isEmpty(request.getParameter("start")) ? "" : request.getParameter("start");
	 		String sizeInit=StringUtil.isEmpty(request.getParameter("size")) ? "" : request.getParameter("size");
	 		
			Gson gson = new Gson();
			if(StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(method)||StringUtil.isEmpty(startInit)|| StringUtil.isEmpty(sizeInit)
					|| StringUtil.isEmpty(username)){
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
				method = CodeUtil.decode(method, apptoken);
				if(StringUtils.isBlank(method)){
		 			result = new ResultEntity<JSONObject>(0, "方法名不能为空！", null);
			 		out.write(gson.toJson(result));
					out.flush();
					out.close();
					return;
		 		}
				username = CodeUtil.decode(username, apptoken);
				startStr = CodeUtil.decode(startInit, apptoken);
				sizeStr  = CodeUtil.decode(sizeInit, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			//username="225";startStr="1";sizeStr="10";
			String par="yhm="+username+"&start="+startStr+"&size="+sizeStr+"&sign="+(oasign+username);
			String reqUrl ="";
			if("getTodoTaskList".equals(method)){
				reqUrl=httpurl+"/getTodoTaskList";
			}else if("getDoneTaskList".equals(method)){
				reqUrl=httpurl+"/getDoneTaskList";
			}else if("getSoluteTaskList".equals(method)){
				reqUrl=httpurl+"/getSoluteTaskList";
			}else{
				result = new ResultEntity<JSONObject>(0, "方法名传递错误，不存在该方法！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			String jsonStr=HttpClientUtil.getResponse("POST",reqUrl, par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();	
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("获取待办事宜列表或已办事宜列表或办结事宜列表异常getOaListForCommon  err：");
			e.printStackTrace();
		}	
	}
	
	/**
	 *根据【是否阅办】获取待办列表
	 *@param username
	 *@param size
	 *@param start
	 *@param sfyb
	 *@param apptoken
	 *@return 
	 *@author yangbilin 
	 */
	public void getTodoTaskListBySfyb(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String username="";
	   	 	String startStr = null;
	   	 	String sizeStr=null;
	   	    ResultEntity<JSONObject> result = null;
	   	    
	   	    String apptoken = request.getParameter("apptoken");
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		String startInit=StringUtil.isEmpty(request.getParameter("start")) ? "" : request.getParameter("start");
	 		String sizeInit=StringUtil.isEmpty(request.getParameter("size")) ? "" : request.getParameter("size");
	 		String sfyb = StringUtil.isEmpty(request.getParameter("sfyb")) ? "" : request.getParameter("sfyb");
	 		
			Gson gson = new Gson();
			if(StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(startInit)|| StringUtil.isEmpty(sizeInit)
					|| StringUtil.isEmpty(username)){
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
				if(StringUtils.isNotBlank(sfyb)){
					sfyb = CodeUtil.decode(sfyb, apptoken);
				}
				startStr = CodeUtil.decode(startInit, apptoken);
				sizeStr  = CodeUtil.decode(sizeInit, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}

			String par="yhm="+username+"&sfyb="+sfyb+"&start="+startStr+"&size="+sizeStr+"&sign="+(oasign+username);
			String jsonStr=HttpClientUtil.getResponse("POST", httpurl+"/getTodoTaskListBySfyb", par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("根据【是否阅办】获取待办列表异常getTodoTaskListBySfyb  err：");
			e.printStackTrace();
		}
	}
	
	/**
	 *提交流程前检测方法doSubmitBefore/流程退回前检测方法 doBackBefore/流程跟踪方法 getFlowInfo
	 *@param username
	 *@param id
	 *@param method
	 *@param apptoken
	 *@return 
	 *@author yangbilin 
	 */
	public void doOperationForSubmitOrBackbeforeOrFlowinfo(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String username="";
	   	    ResultEntity<JSONObject> result = null;
	   	    
	   	    String apptoken = request.getParameter("apptoken");
	   	    String method = request.getParameter("method");
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		String id=StringUtil.isEmpty(request.getParameter("id")) ? "" : request.getParameter("id");
	 		
			Gson gson = new Gson();
			if(StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(method)||StringUtil.isEmpty(id)|| StringUtil.isEmpty(username)){
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
				method = CodeUtil.decode(method, apptoken);
				if(StringUtils.isBlank(method)){
		 			result = new ResultEntity<JSONObject>(0, "方法名不能为空！", null);
			 		out.write(gson.toJson(result));
					out.flush();
					out.close();
					return;
		 		}
				username = CodeUtil.decode(username, apptoken);
				id = CodeUtil.decode(id, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}

			String par="yhm="+username+"&id="+id+"&sign="+(oasign+username);
			String reqUrl ="";
			if("doSubmitBefore".equals(method)){
				reqUrl=httpurl+"/doSubmitBefore";
			}else if("doBackBefore".equals(method)){
				reqUrl=httpurl+"/doBackBefore";
			}else if("getFlowInfo".equals(method)){
				reqUrl=httpurl+"/getFlowInfo";
			}else{
				result = new ResultEntity<JSONObject>(0, "方法名传递错误，不存在该方法！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			String jsonStr=HttpClientUtil.getResponse("POST", reqUrl, par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("提交流程前检测或流程退回前检测或流程跟踪方法异常doOperationForSubmitOrBackbeforeOrFlowinfo  err：");
			e.printStackTrace();
		}
	}
	
	/**
	 *根据当前待办事宜ID、下一环节ID和操作人提交流程doSubmitFlowone
	 *@param username
	 *@param id
	 *@param submitData "nextid="+nextid+"&nextuser="+nextuser+"&comment="+comment+"&tablename="+tablename+"&ftzd="+ftzd+"&ftfs="+ftfs+"&zid="+zid
	 *@param apptoken
	 *@return 
	 *@author yangbilin 
	 */
	public void doSubmitFlowone(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String username="",nextuser="",comment="";
	   	    ResultEntity<JSONObject> result = null;
	   	    
	   	    String apptoken = request.getParameter("apptoken");
	   	    if(StringUtils.isNotBlank(request.getParameter("username"))){
	   	    	username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	   	    }
	   	    String id=StringUtil.isEmpty(request.getParameter("id")) ? "" : request.getParameter("id");
	   	    String nextid=StringUtil.isEmpty(request.getParameter("nextid")) ? "" : request.getParameter("nextid");
	   	    if(StringUtils.isNotBlank(request.getParameter("nextuser"))){
	   	    	nextuser = new String(request.getParameter("nextuser").getBytes("ISO8859-1"), "UTF-8");
	   	    }
	   	    String tablename=StringUtil.isEmpty(request.getParameter("tablename")) ? "" : request.getParameter("tablename");
	   	    String ftzd=StringUtil.isEmpty(request.getParameter("ftzd")) ? "" : request.getParameter("ftzd");
	   	    String ftfs=StringUtil.isEmpty(request.getParameter("ftfs")) ? "" : request.getParameter("ftfs");
	   	    String zid=StringUtil.isEmpty(request.getParameter("zid")) ? "" : request.getParameter("zid");
	 		if(StringUtils.isNotBlank(request.getParameter("comment"))){
	 			comment = new String(request.getParameter("comment").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		
	 		Gson gson = new Gson();
			if(StringUtil.isEmpty(apptoken)|| StringUtil.isEmpty(username)||StringUtil.isEmpty(id)||StringUtil.isEmpty(nextid)
					||StringUtil.isEmpty(tablename)||StringUtil.isEmpty(zid)){
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
				id = CodeUtil.decode(id, apptoken);
				nextid= CodeUtil.decode(nextid, apptoken);
				if(StringUtils.isNotBlank(nextuser)){
					nextuser= URLEncoder.encode(CodeUtil.decode(nextuser, apptoken), "UTF-8");
				}
				if(StringUtils.isNotBlank(comment)){
					comment=URLEncoder.encode(CodeUtil.decode(comment, apptoken), "UTF-8");
				}
				tablename= CodeUtil.decode(tablename, apptoken);
				if(StringUtils.isNotBlank(ftzd)){
					ftzd= CodeUtil.decode(ftzd, apptoken);
				}
				if(StringUtils.isNotBlank(ftfs)){
					ftfs= CodeUtil.decode(ftfs, apptoken);
				}
				zid= CodeUtil.decode(zid, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}

			String par="yhm="+username+"&id="+id+"&sign="+(oasign+username)+"&nextid="+nextid+"&nextuser="+nextuser+
		   	 "&comment="+comment+"&tablename="+tablename+"&ftzd="+ftzd+"&ftfs="+ftfs+"&zid="+zid;
			String jsonStr=HttpClientUtil.getResponse("POST", httpurl+"/doSubmitFlowone", par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("提交流程方法异常doSubmitFlowone  err：");
			e.printStackTrace();
		}
	}
	
	/**
	 *流程退回方法 doBackFlow
	 *@param username
	 *@param id
	 *@param comment
	 *@param apptoken
	 *@return 
	 *@author yangbilin 
	 */
	public void doBackFlow(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String username="",comment="";
	   	    ResultEntity<JSONObject> result = null;
	   	    
	   	    String apptoken = request.getParameter("apptoken");
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		String id=StringUtil.isEmpty(request.getParameter("id")) ? "" : request.getParameter("id");
	 		if(StringUtils.isNotBlank(request.getParameter("comment"))){
	 			comment = new String(request.getParameter("comment").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		
			Gson gson = new Gson();
			if(StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(id)|| StringUtil.isEmpty(username)){
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
				id = CodeUtil.decode(id, apptoken);
				if(StringUtils.isNotBlank(comment)){
					comment = URLEncoder.encode(CodeUtil.decode(comment, apptoken),"UTF-8");
				}
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}

			String par="yhm="+username+"&id="+id+"&comment="+comment+"&sign="+(oasign+username);
			String jsonStr=HttpClientUtil.getResponse("POST", httpurl+"/doBackFlow", par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("流程退回方法 doBackFlow err：");
			e.printStackTrace();
		}
	}
	
	/**
	 *查看待办、已办、办结事宜详细信息 getTableInfo
	 *@param username
	 *@param id
	 *@param tablename
	 *@param apptoken
	 *@return 
	 *@author yangbilin 
	 */
	public void getTableInfo(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String username="";
	   	    ResultEntity<JSONObject> result = null;
	   	    
	   	    String apptoken = request.getParameter("apptoken");
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		String id=StringUtil.isEmpty(request.getParameter("id")) ? "" : request.getParameter("id");
	 		String tablename=StringUtil.isEmpty(request.getParameter("tablename")) ? "" : request.getParameter("tablename");
	 		
			Gson gson = new Gson();
			if(StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(id)|| StringUtil.isEmpty(username)|| StringUtil.isEmpty(tablename)){
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
				id = CodeUtil.decode(id, apptoken);
				tablename = CodeUtil.decode(tablename, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}

			String par="yhm="+username+"&id="+id+"&tablename="+tablename+"&sign="+(oasign+username);
			String jsonStr=HttpClientUtil.getResponse("POST", httpurl+"/getTableInfo", par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("查看待办、已办、办结事宜详细信息异常 getTableInfo  err：");
			e.printStackTrace();
		}
	}
	
	/**
	 *获取领导常用审批意见 getLdcyyjbList
	 *@param username
	 *@param apptoken
	 *@return 
	 *@author yangbilin 
	 */
	public void getLdcyyjbList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String username="";
	   	    ResultEntity<JSONObject> result = null;
	   	    
	   	    String apptoken = request.getParameter("apptoken");
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		
			Gson gson = new Gson();
			if(StringUtil.isEmpty(apptoken)|| StringUtil.isEmpty(username)){
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
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}

			String par="yhm="+username+"&sign="+(oasign+username);
			String jsonStr=HttpClientUtil.getResponse("POST", httpurl+"/getLdcyyjbList", par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("获取领导常用审批意见异常 getLdcyyjbList err：");
			e.printStackTrace();
		}
	}
	
	/**
	 *流程任务执行方法excuTaskForFlow
	 *@param username
	 *@param id
	 *@param tableid
	 *@param apptoken
	 *@return 
	 *@author yangbilin 
	 */
	public void excuTaskForFlow(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String username="";
	   	    ResultEntity<JSONObject> result = null;
	   	    
	   	    String apptoken = request.getParameter("apptoken");
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		String id=StringUtil.isEmpty(request.getParameter("id")) ? "" : request.getParameter("id");
	 		String taskId=StringUtil.isEmpty(request.getParameter("taskId")) ? "" : request.getParameter("taskId");
	 		
			Gson gson = new Gson();
			if(StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(id)|| StringUtil.isEmpty(username)|| StringUtil.isEmpty(taskId)){
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
				id = CodeUtil.decode(id, apptoken);
				taskId = CodeUtil.decode(taskId, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}

			String par="yhm="+username+"&id="+id+"&taskId="+taskId+"&sign="+(oasign+username);
			String jsonStr=HttpClientUtil.getResponse("POST", httpurl+"/excuTaskForFlow", par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("流程任务执行方法异常 excuTaskForFlow  err：");
			e.printStackTrace();
		}
	}
	
	/**
	 *判断用户签名口令方法checkPassword
	 *@param username
	 *@param flowPassword 
	 *@param apptoken
	 *@return 
	 *@author yangbilin 
	 */
	public void checkPassword(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String username="";
	   	    ResultEntity<JSONObject> result = null;
	   	    
	   	    String apptoken = request.getParameter("apptoken");
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		String flowPassword=StringUtil.isEmpty(request.getParameter("flowPassword")) ? "" : request.getParameter("flowPassword");
	 		
			Gson gson = new Gson();
			if(StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(flowPassword)|| StringUtil.isEmpty(username)){
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
				flowPassword = CodeUtil.decode(flowPassword, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}

			String par="yhm="+username+"&flowPassword="+flowPassword+"&sign="+(oasign+username);
			String jsonStr=HttpClientUtil.getResponse("POST", httpurl+"/checkPassword", par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("判断用户签名口令方法异常 checkPassword  err：");
			e.printStackTrace();
		}
	}
	/********************************流程事宜相关方法end********************************************/
	
	/*-------------------------------华丽丽的接口分割线--------------------------------------------*/
	
	/********************************邮件相关方法start********************************************/
	/**
	 *获取邮件列表带筛选条件getMailListByTypeAndCond
	 *@param username
	 *@param size
	 *@param start
	 *@param type 1收件箱 2草稿箱 3已发送邮件 4 未读邮件  5星标邮件6已删除邮件
     *@param keyword 筛选条件
	 *@param apptoken
	 *@return 
	 *@author yangbilin 
	 */
	public void getMailListByTypeAndCond(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String username="",keyword="";String cond ="";
	   	 	String startStr = null,sizeStr=null;
	   	    ResultEntity<JSONObject> result = null;
	   	    
	   	    String apptoken = request.getParameter("apptoken");
	   	    String type = request.getParameter("type");
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("keyword"))){
	 			keyword = new String(request.getParameter("keyword").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		String startInit=StringUtil.isEmpty(request.getParameter("start")) ? "" : request.getParameter("start");
	 		String sizeInit=StringUtil.isEmpty(request.getParameter("size")) ? "" : request.getParameter("size");
	 		
			Gson gson = new Gson();
			if(StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(type)||StringUtil.isEmpty(startInit)|| StringUtil.isEmpty(sizeInit)
					|| StringUtil.isEmpty(username)){
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
				startStr = CodeUtil.decode(startInit, apptoken);
				sizeStr  = CodeUtil.decode(sizeInit, apptoken);
				type = CodeUtil.decode(type, apptoken);
				if(StringUtils.isNotBlank(keyword)){
					keyword  = CodeUtil.decode(keyword, apptoken);
					cond =URLEncoder.encode(keyword, "utf-8");
				}
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			//String par="yhm=342&type=1&start=1&size=10&cond="+cond+"&sign=zfsoft342";
			String par="yhm="+username+"&type="+type+"&start="+startStr+"&size="+sizeStr+"&cond="+cond+"&sign="+(oasign+username);
			String jsonStr=HttpClientUtil.getResponse("POST",httpurl+"/getMailListByTypeAndCond", par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();	
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("获取邮件列表带筛选条件方法异常getMailListByTypeAndCond  err：");
			e.printStackTrace();
		}	
	}
	
	/**
	 *获取邮件列表getMailListByType
	 *@param username
	 *@param size
	 *@param start
	 *@param type 1,收件箱2,草稿箱3,已发送列表
	 *@param apptoken
	 *@return 
	 *@author yangbilin 
	 */
	public void getMailListByType(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String username="";
	   	 	String startStr = null,sizeStr=null;
	   	    ResultEntity<JSONObject> result = null;
	   	    
	   	    String apptoken = request.getParameter("apptoken");
	   	    String type = request.getParameter("type");
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		String startInit=StringUtil.isEmpty(request.getParameter("start")) ? "" : request.getParameter("start");
	 		String sizeInit=StringUtil.isEmpty(request.getParameter("size")) ? "" : request.getParameter("size");
	 		
			Gson gson = new Gson();
			if(StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(type)||StringUtil.isEmpty(startInit)|| StringUtil.isEmpty(sizeInit)
					|| StringUtil.isEmpty(username)){
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
				startStr = CodeUtil.decode(startInit, apptoken);
				sizeStr  = CodeUtil.decode(sizeInit, apptoken);
				type = CodeUtil.decode(type, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
		
			String par="yhm="+username+"&type="+type+"&start="+startStr+"&size="+sizeStr+"&sign="+(oasign+username);
			String jsonStr=HttpClientUtil.getResponse("POST",httpurl+"/getMailListByType", par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();	
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("获取邮件列表方法异常getMailListByType  err：");
			e.printStackTrace();
		}	
	}
	
	/**
	 *获取邮件详细信息getMailInfoById/更新邮件为已查看 updateMailByID/获取草稿箱详细信息 getDraftInfoById
	 *@param username
	 *@param emailid 邮件id
	 *@param method
	 *@param apptoken
	 *@return 
	 *@author yangbilin 
	 */
	public void getMailOrDraftInfoOrUpdateMailById(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String username="";
	   	    ResultEntity<JSONObject> result = null;
	   	    
	   	    String apptoken = request.getParameter("apptoken");
	   	    String method = request.getParameter("method");
	   	    String emailid=StringUtil.isEmpty(request.getParameter("emailid")) ? "" : request.getParameter("emailid");
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		
			Gson gson = new Gson();
			if(StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(emailid)||StringUtil.isEmpty(method)|| StringUtil.isEmpty(username)){
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
				method = CodeUtil.decode(method, apptoken);
				if(StringUtils.isBlank(method)){
		 			result = new ResultEntity<JSONObject>(0, "方法名不能为空！", null);
			 		out.write(gson.toJson(result));
					out.flush();
					out.close();
					return;
		 		}
				username = CodeUtil.decode(username, apptoken);
				emailid = CodeUtil.decode(emailid, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
		    
			//String par="yhm=342&eid=150607316497789107&sign=zfsoft342";
			String par="yhm="+username+"&eid="+emailid+"&sign="+(oasign+username);
			String reqUrl ="";
			//method="getMailInfoById";
			if("getMailInfoById".equals(method)){
				reqUrl=httpurl+"/getMailInfoById";
			}else if("updateMailByID".equals(method)){
				reqUrl=httpurl+"/updateMailByID";
			}else if("getDraftInfoById".equals(method)){
				reqUrl=httpurl+"/getDraftInfoById";
				par="yhm="+username+"&yjid="+emailid+"&sign="+(oasign+username);
			}else{
				result = new ResultEntity<JSONObject>(0, "方法名传递错误，不存在该方法！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			String jsonStr=HttpClientUtil.getResponse("POST",reqUrl, par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();	
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("通过邮件ID获取邮件详细信息或更新邮件为已查看或获取草稿箱详细信息方法异常getMailInfoOrUpdateMailById  err：");
			e.printStackTrace();
		}	
	}
	
	/**
	 *删除邮件 deleteMailByID/更新邮件星标状态updateMailXbByID
	 *@param username
	 *@param method
	 *@param emailid 邮件id
     *@param type:1，删除收件箱邮件（逻辑删除）和message相关信息2，删除草稿箱邮件（物理删除）
     *3，删除已发送邮件（物理删除）和message相关信息/0取消星标 1标记星标
	 *@param apptoken
	 *@return 
	 *@author yangbilin 
	 */
	public void doDeleteOrUpdatestarEmailByID(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String username="";
	   	    ResultEntity<JSONObject> result = null;
	   	    
	   	    String apptoken = request.getParameter("apptoken");
	   	    String type = request.getParameter("type");
	   	    String method = request.getParameter("method");
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		String emailid=StringUtil.isEmpty(request.getParameter("emailid")) ? "" : request.getParameter("emailid");
	 		
			Gson gson = new Gson();
			if(StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(type)||StringUtil.isEmpty(emailid)||StringUtil.isEmpty(method)
					|| StringUtil.isEmpty(username)){
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
				method = CodeUtil.decode(method, apptoken);
				if(StringUtils.isBlank(method)){
		 			result = new ResultEntity<JSONObject>(0, "方法名不能为空！", null);
			 		out.write(gson.toJson(result));
					out.flush();
					out.close();
					return;
		 		}
				username = CodeUtil.decode(username, apptoken);
				emailid = CodeUtil.decode(emailid, apptoken);
				type = CodeUtil.decode(type, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			String reqUrl ="";
			String par="yhm="+username+"&type="+type+"&sign="+(oasign+username);
			if("deleteMailByID".equals(method)){
				par=par+"&eid="+emailid;
				reqUrl=httpurl+"/deleteMailByID";
			}else if("updateMailXbByID".equals(method)){
				par=par+"&id="+emailid;
				reqUrl=httpurl+"/updateMailXbByID";
			}else{
				result = new ResultEntity<JSONObject>(0, "方法名传递错误，不存在该方法！", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			String jsonStr=HttpClientUtil.getResponse("POST",reqUrl, par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();	
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("通过邮箱ID删除邮件或更新邮件星标状态方法异常doDeleteOrUpdatestarEmailByID err：");
			e.printStackTrace();
		}	
	}
	
	/**
	 *获取新邮件数量getNewMailCount
	 *@param username
	 *@param apptoken
	 *@return 
	 *@author yangbilin 
	 */
	public void getNewMailCount(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String username="";
	   	    ResultEntity<JSONObject> result = null;
	   	    
	   	    String apptoken = request.getParameter("apptoken");
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		
			Gson gson = new Gson();
			if(StringUtil.isEmpty(apptoken)|| StringUtil.isEmpty(username)){
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
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			String par="yhm="+username+"&sign="+(oasign+username);
			String jsonStr=HttpClientUtil.getResponse("POST", httpurl+"/getNewMailCount", par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("获取新邮件数量方法异常 getNewMailCount err：");
			e.printStackTrace();
		}
	}
	/**
	*发送邮件sendMail
	*@param username:当前登录的用户名
	*@param type:3转发 4草稿箱发送
	*@param apptoken
	*@param sendData:"yjid=&fsrxm=&sjrxm=&sjrdm=&title=&content="  不能为空
	*@param ccData:"csrxm=&csrdm="   可以为空
	Yjid:邮件ID(转发时提供)
	Fsrxm：发送人姓名
	Sjrxm：收件人姓名
	Sjrdm：收件人姓名
		
	Csrxm：抄送人员姓名
	Csrdm：抄送人员代码
	
	*/
	public void sendMail(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String username="",fsrxm="",sjrxm="",title="",content="",csrxm="";
	   	    ResultEntity<JSONObject> result = null;
	   	    
	   	    String apptoken = request.getParameter("apptoken");
	   	    String type =StringUtil.isEmpty(request.getParameter("type")) ? "" : request.getParameter("type");
	   	    String yjid =StringUtil.isEmpty(request.getParameter("yjid")) ? "" : request.getParameter("yjid");
	   	    String sjrdm =StringUtil.isEmpty(request.getParameter("sjrdm")) ? "" : request.getParameter("sjrdm");
	   	    String csrdm =StringUtil.isEmpty(request.getParameter("csrdm")) ? "" : request.getParameter("csrdm");
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("fsrxm"))){
	 			fsrxm = new String(request.getParameter("fsrxm").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("sjrxm"))){
	 			sjrxm = new String(request.getParameter("sjrxm").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("title"))){
	 			title = new String(request.getParameter("title").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("content"))){
	 			content = new String(request.getParameter("content").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("csrxm"))){
	 			csrxm = new String(request.getParameter("csrxm").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		
	 		Gson gson = new Gson();
			if(StringUtil.isEmpty(apptoken)|| StringUtil.isEmpty(username)||StringUtil.isEmpty(fsrxm)||StringUtil.isEmpty(sjrxm)
					||StringUtil.isEmpty(sjrdm)||StringUtil.isEmpty(title)||StringUtil.isEmpty(content)||StringUtil.isEmpty(type)){
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
				
				fsrxm = URLEncoder.encode(CodeUtil.decode(fsrxm, apptoken), "UTF-8");
				sjrxm = URLEncoder.encode(CodeUtil.decode(sjrxm, apptoken), "UTF-8");
				sjrdm = CodeUtil.decode(sjrdm, apptoken);
				title = URLEncoder.encode(CodeUtil.decode(title, apptoken), "UTF-8");
				content = URLEncoder.encode(CodeUtil.decode(content, apptoken), "UTF-8");
				type=CodeUtil.decode(type, apptoken);
				
				if(StringUtils.isNotBlank(yjid)){
					yjid =CodeUtil.decode(yjid, apptoken);
				}
				if(StringUtils.isNotBlank(csrxm)){
					csrxm =URLEncoder.encode(CodeUtil.decode(csrxm, apptoken), "UTF-8");
				}
				if(StringUtils.isNotBlank(csrdm)){
					csrdm = CodeUtil.decode(csrdm, apptoken);
				}
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			//String par="yhm=999&yjid=15058999866472913308&fsrxm=999&sjrxm=谢凯,李伟洲,叶成春,姚强强,周嘉伟&sjrdm=2-999,2-459,2-342,2-255,2-1229&title=我的回复 测试带附件0920&content=龙&csrxm=&csrdm=&type=3&sign=zfsoft999";
			//String par="yhm=999&yjid=15058999866472913308&fsrxm=999&sjrxm=周嘉伟&sjrdm=255&title=我的回复 测试带附件0920&content=开开心心&csrxm=&csrdm=&type=3&sign=zfsoft999";
			String par="yhm="+username+"&yjid="+yjid+"&fsrxm="+fsrxm+"&sjrxm="+sjrxm+"&sjrdm="+sjrdm+"&title="+title+"&content="+content+"&csrxm="+csrxm+"&csrdm="+csrdm+"&type="+type+"&sign="+(oasign+username);
			String jsonStr=HttpClientUtil.getResponse("POST", httpurl+"/sendMail", par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("发送邮件方法异常sendMail err：");
			e.printStackTrace();
		}
	}
	
	/**
	*保存至草稿箱saveToDraft
	*@param username:当前登录的用户名
	*@param apptoken
	*@param sendData:"yjid=&fsrxm=&sjrxm=&sjrdm=&title=&content="  可以为空
	*@param ccData:"csrxm=&csrdm="   可以为空
		Yjid:邮件ID(第一次保存时默认为空)
		Fsrxm：发送人姓名
		Sjrxm：收件人姓名
		Sjrdm：收件人用户名
		Csrxm：抄送人员姓名
		Csrdm：抄送人员代码
	*/
	public void saveToDraft(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String username="",fsrxm="",sjrxm="",title="",content="",csrxm="";
	   	    ResultEntity<JSONObject> result = null;
	   	    
	   	    String apptoken = request.getParameter("apptoken");
	   	    String yjid =StringUtil.isEmpty(request.getParameter("yjid")) ? "" : request.getParameter("yjid");
	   	    String sjrdm =StringUtil.isEmpty(request.getParameter("sjrdm")) ? "" : request.getParameter("sjrdm");
	   	    String csrdm =StringUtil.isEmpty(request.getParameter("csrdm")) ? "" : request.getParameter("csrdm");
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("fsrxm"))){
	 			fsrxm = new String(request.getParameter("fsrxm").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("sjrxm"))){
	 			sjrxm = new String(request.getParameter("sjrxm").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("title"))){
	 			title = new String(request.getParameter("title").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("content"))){
	 			content = new String(request.getParameter("content").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("csrxm"))){
	 			csrxm = new String(request.getParameter("csrxm").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		
	 		Gson gson = new Gson();
			if(StringUtil.isEmpty(apptoken)|| StringUtil.isEmpty(username)){
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
				if(StringUtils.isNotBlank(fsrxm)){
					fsrxm = URLEncoder.encode(CodeUtil.decode(fsrxm, apptoken), "UTF-8");
				}
				if(StringUtils.isNotBlank(sjrxm)){
					sjrxm = URLEncoder.encode(CodeUtil.decode(sjrxm, apptoken), "UTF-8");
				}
				if(StringUtils.isNotBlank(sjrdm)){
					sjrdm = CodeUtil.decode(sjrdm, apptoken);
				}
				if(StringUtils.isNotBlank(title)){
					title = URLEncoder.encode(CodeUtil.decode(title, apptoken), "UTF-8");
				}
				if(StringUtils.isNotBlank(content)){
					content = URLEncoder.encode(CodeUtil.decode(content, apptoken), "UTF-8");
				}
				if(StringUtils.isNotBlank(yjid)){
					yjid =CodeUtil.decode(yjid, apptoken);
				}
				if(StringUtils.isNotBlank(csrxm)){
					csrxm =URLEncoder.encode(CodeUtil.decode(csrxm, apptoken), "UTF-8");
				}
				if(StringUtils.isNotBlank(csrdm)){
					csrdm = CodeUtil.decode(csrdm, apptoken);
				}
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			String par="yhm="+username+"&yjid="+yjid+"&fsrxm="+fsrxm+"&sjrxm="+sjrxm+"&sjrdm="+sjrdm+"&title="+title+
			"&content="+content+"&csrxm="+csrxm+"&csrdm="+csrdm+"&sign="+(oasign+username);
			//String par="yhm="+username+"&"+sendData+"&"+ccData+"&sign="+(oasign+username);
			String jsonStr=HttpClientUtil.getResponse("POST", httpurl+"/saveToDraft", par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("保存至草稿箱方法异常saveToDraft err：");
			e.printStackTrace();
		}
	}
	
	/********************************邮件相关方法end********************************************/
	
	/**
	 * 取得通知公告类别
	 * @param Yhm:当前登录的用户名
	 * @param apptoken:token字符串，用于加解密
	 */
	public void getNoticeType(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String yhm="";
	   	    ResultEntity<JSONObject> result = null;
	   	    
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			yhm = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		
			Gson gson = new Gson();
			
			String apptoken = request.getParameter("apptoken");
			if(!ApptokenUtils.compare(yhm, apptoken)){
		 		result = new ResultEntity<JSONObject>(2, "app_token error!", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
			try {
				yhm = CodeUtil.decode(yhm, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}

			String par="yhm="+yhm+"&sign="+(oasign+yhm);
			String reqUrl = httpurl+"/getNoticType";
			String jsonStr=HttpClientUtil.getResponse("POST",reqUrl, par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();	
		}catch (Exception e) {
			logger.error("获取通知公告类别异常getNoticType  err：");
			e.printStackTrace();
		}
	}
	
	/**
	 * 取得通知公告数据列表
	 * @param Yhm:当前登录的用户名
	 * @param Type :类型(可为空)
	 * @param start:页码
     * @param size:每页显示数量
     * @param apptoken:token字符串，用于加解密
	 */
	public void getNoticeList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String yhm="";
			String type="";
			String startStr="1";
			String sizeStr="10";
	   	    ResultEntity<JSONObject> result = null;
	   	    
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			yhm = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("Type"))){
	 			type = new String(request.getParameter("Type").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("start"))){
	 			startStr = new String(request.getParameter("start").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("size"))){
	 			sizeStr = new String(request.getParameter("size").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		
			Gson gson = new Gson();
			
			String apptoken = request.getParameter("apptoken");
			if(!ApptokenUtils.compare(yhm, apptoken)){
		 		result = new ResultEntity<JSONObject>(2, "app_token error!", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
			try {
				yhm = CodeUtil.decode(yhm, apptoken);
				type = CodeUtil.decode(type, apptoken);
				startStr = CodeUtil.decode(startStr, apptoken);
				sizeStr = CodeUtil.decode(sizeStr, apptoken);
				
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			String par="yhm="+yhm+"&start="+startStr+"&size="+sizeStr+"&type="+type+"&sign="+(oasign+yhm);
			String reqUrl = httpurl+"/getNoticeList";
			String jsonStr=HttpClientUtil.getResponse("POST",reqUrl, par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();	
		}catch (Exception e) {
			logger.error("获取通知公告数据列表异常getNoticeList  err：");
			e.printStackTrace();
		}
	}
	
	/**
	 * 取得通知公告详细信息
	 * @param Yhm:当前登录的用户名
	 * @param id:通知公告id
	 * @param apptoken:token字符串，用于加解密
	 */
	public void getNoticeInfo(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String yhm="";
			String id="";
	   	    ResultEntity<JSONObject> result = null;
	   	    
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			yhm = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("id"))){
	 			id = new String(request.getParameter("id").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		
			Gson gson = new Gson();
			
			String apptoken = request.getParameter("apptoken");
			if(!ApptokenUtils.compare(yhm, apptoken)){
		 		result = new ResultEntity<JSONObject>(2, "app_token error!", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
			try {
				yhm = CodeUtil.decode(yhm, apptoken);
				id = CodeUtil.decode(id, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			String par="yhm="+yhm+"&id="+id+"&sign="+(oasign+yhm);
			String reqUrl = httpurl+"/getNoticeInfo";
			String jsonStr=HttpClientUtil.getResponse("POST",reqUrl, par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();	
		}catch (Exception e) {
			logger.error("获取通知公告详细信息异常getNoticeInfo  err：");
			e.printStackTrace();
		}
	}
	
	/**
	 * 取得个人会议和全校会议数据集合
	 */
	public void getNewConferenceList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String yhm="";
			String type="";
			String startStr="1";
			String sizeStr="10";
	   	    ResultEntity<JSONObject> result = null;
	   	    
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			yhm = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("Type"))){
	 			type = new String(request.getParameter("Type").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("start"))){
	 			startStr = new String(request.getParameter("start").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("size"))){
	 			sizeStr = new String(request.getParameter("size").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		
			Gson gson = new Gson();
			
			String apptoken = request.getParameter("apptoken");
			if(!ApptokenUtils.compare(yhm, apptoken)){
		 		result = new ResultEntity<JSONObject>(2, "app_token error!", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
			try {
				yhm = CodeUtil.decode(yhm, apptoken);
				type = CodeUtil.decode(type, apptoken);
				startStr = CodeUtil.decode(startStr, apptoken);
				sizeStr = CodeUtil.decode(sizeStr, apptoken);
				
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			String par="yhm="+yhm+"&start="+startStr+"&size="+sizeStr+"&type="+type+"&sign="+(oasign+yhm);
			String reqUrl = httpurl+"/getNewConferenceList";
			String jsonStr=HttpClientUtil.getResponse("POST",reqUrl, par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();	
		}catch (Exception e) {
			logger.error("获取个人会议和全校会议数据集合异常getNewConferenceList  err：");
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据ID取得会议详情数据信息
	 * @param Yhm:当前登录的用户名
	 * @param id:通知公告id
	 * @param apptoken:token字符串，用于加解密
	 */
	public void getConferenceInfo(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String yhm="";
			String id="";
	   	    ResultEntity<JSONObject> result = null;
	   	    
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			yhm = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("id"))){
	 			id = new String(request.getParameter("id").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		
			Gson gson = new Gson();
			
			String apptoken = request.getParameter("apptoken");
			if(!ApptokenUtils.compare(yhm, apptoken)){
		 		result = new ResultEntity<JSONObject>(2, "app_token error!", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
			try {
				yhm = CodeUtil.decode(yhm, apptoken);
				id = CodeUtil.decode(id, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			String par="yhm="+yhm+"&id="+id+"&sign="+(oasign+yhm);
			String reqUrl = httpurl+"/getConferenceInfo";
			String jsonStr=HttpClientUtil.getResponse("POST",reqUrl, par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();	
		}catch (Exception e) {
			logger.error("获取会议详情数据信息异常getConferenceInfo  err：");
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据手机号查询姓名和部门
	 * @param yhm:当前登录的用户名
	 * @param mobiletel:手机号
	 * @param apptoken:token字符串，用于加解密
	 */
	public void getXmAndDepByMobiletel(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String yhm="";
			String mobiletel="";
	   	    ResultEntity<JSONObject> result = null;
	   	    
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			yhm = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("mobiletel"))){
	 			mobiletel = new String(request.getParameter("mobiletel").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		
			Gson gson = new Gson();
			
			String apptoken = request.getParameter("apptoken");
			if(!ApptokenUtils.compare(yhm, apptoken)){
		 		result = new ResultEntity<JSONObject>(2, "app_token error!", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
			try {
				yhm = CodeUtil.decode(yhm, apptoken);
				mobiletel = CodeUtil.decode(mobiletel, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			String par="yhm="+yhm+"&mobiletel="+mobiletel+"&sign="+(oasign+yhm);
			String reqUrl = httpurl+"/getXmAndDepByMobiletel";
			String jsonStr=HttpClientUtil.getResponse("POST",reqUrl, par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();	
		}catch (Exception e) {
			logger.error("根据手机号查询姓名和部门getXmAndDepByMobiletel  err：");
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 获取待办事宜列表
	 */
	public void getTodoTaskList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String yhm="";
			String startStr="1";
			String sizeStr="10";
	   	    ResultEntity<JSONObject> result = null;
	   	    
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			yhm = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("start"))){
	 			startStr = new String(request.getParameter("start").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("size"))){
	 			sizeStr = new String(request.getParameter("size").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		
			Gson gson = new Gson();
			
			String apptoken = request.getParameter("apptoken");
			if(!ApptokenUtils.compare(yhm, apptoken)){
		 		result = new ResultEntity<JSONObject>(2, "app_token error!", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
			try {
				yhm = CodeUtil.decode(yhm, apptoken);
				startStr = CodeUtil.decode(startStr, apptoken);
				sizeStr = CodeUtil.decode(sizeStr, apptoken);
				
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			String par="yhm="+yhm+"&start="+startStr+"&size="+sizeStr+"&sign="+(oasign+yhm);
			String reqUrl = httpurl+"/getTodoTaskList";
			String jsonStr=HttpClientUtil.getResponse("POST",reqUrl, par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();	
		}catch (Exception e) {
			logger.error("获取待办事宜列表数据集合异常getNewConferenceList  err：");
			e.printStackTrace();
		}
	}
	
	/**
	 * 附件上传
	 * @throws IOException 
	 */
	public void fjUploadByType() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		PrintWriter out = null;  
		
		String yhm = null;
		String zid = null;
		String sign = null;
		
		Gson gson = new Gson();
   	 	try {
	   	 	out = response.getWriter();
	   	 	
	   	    Map<String,String> resultMap = new HashMap<String,String>();
	   	    
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
	            	paramName = fItem.getFieldName();  
	                fValue = fItem.getString("UTF-8");  
	                if(paramName.equals("yhm")){
	                	yhm = fValue.toString();
	                }else if(paramName.equals("zid")){
	                	zid = fValue.toString();
	                }else if(paramName.equals("sign")){
	                	sign = fValue.toString();
	                }
	            } else { // 获取上传文件的值  
	            	logger.error("进入附件上传方法");
	            	paramName = fItem.getFieldName();//userfile  
	                fValue = fItem.getInputStream(); 
	                String filename = fItem.getName();//路径  
	                if(!StringUtil.isEmpty(filename)) {  
	                    InputStream is = fItem.getInputStream();  
	                    
	                    byte[] fileContent = null;
	                    int size = is.available();
	                    if(is != null && size != 0){
	                    	fileContent = Byte_File_Object.getBytesFromFile(is);
	                    }
	                    
	                    String filePath = request.getSession().getServletContext().getRealPath("/") + "oaFjUploadByType"; 
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
	        			if(fileContent != null){
	        				fileOutputStream.write(fileContent, 0, fileContent.length);
	        			}
	        			fileOutputStream.close();
	        			is.close();
	        			
	        			
	        			//调用oawebservice，实现附件上传
	         			FileModel fileModel = new FileModel();
	         			fileModel.setName(filename);
	         			DataSource source = new FileDataSource(new File(filePath,filename));
	         			fileModel.setDataHandler(new DataHandler(source));
	         			
	         			String xmlStr=null;
	         			String zzid = "";
	         			if(zid!=null&&!"".equals(zid)){
	         				zzid = zid;
	         			}else{
	         				if(resultMap.containsKey("message")&&resultMap.get("message")!=null){
	         					zzid = resultMap.get("message");
	         				}
	         			}
	         			xmlStr = WebServiceUtil.createServiceOa().fjUploadByType(yhm, zzid , filename, "mail", fileModel, sign);
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
	        		            	
	        		            	if(!resultMap.containsKey("code")||!resultMap.containsKey("message")){
	        		            		resultMap.put(nodeName, nodeValue);
	        		            	}
	        		            }
	        				}catch (Exception e) {
	        					e.printStackTrace();
	        				}
	        				   
	         			}
	                 }  
	        	        
	            }  
		    }  
	        ResultEntity<Map<String,String>> result = new ResultEntity<Map<String,String>>(1, "上传成功",resultMap);
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
   	 	}catch(Exception e){
	   	 	 e.printStackTrace(); 
	         logger.error(e);
	         logger.error("memo upload------: exception");  
	         out = response.getWriter();  
	         out.print("{\"success\":\"false\", \"msg\":\"附件上传失败\"}");  
	         out.close();
   	 	}
	}
	/**
	 * 获取当前环节可编辑的字段列表
	 * @param Yhm:当前登录的用户名
	 * @param id:通知公告id
	 * @param apptoken:token字符串，用于加解密
	 */
	public void getCurrentSectionFiled(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String yhm="";
			String id="";
			String tableName="";
			String zid="";
	   	    ResultEntity<JSONObject> result = null;
	   	    
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			yhm = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("id"))){
	 			id = new String(request.getParameter("id").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("tableName"))){
	 			tableName = new String(request.getParameter("tableName").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("zid"))){
	 			zid = new String(request.getParameter("zid").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		
			Gson gson = new Gson();
			
			String apptoken = request.getParameter("apptoken");
			if(!ApptokenUtils.compare(yhm, apptoken)){
		 		result = new ResultEntity<JSONObject>(2, "app_token error!", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
			try {
				yhm = CodeUtil.decode(yhm, apptoken);
				id = CodeUtil.decode(id, apptoken);
				tableName = CodeUtil.decode(tableName, apptoken);
				zid = CodeUtil.decode(zid, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			String par="yhm="+yhm+"&id="+id+"&tableName="+tableName+"&zid="+zid+"&sign="+(oasign+yhm);
			String reqUrl = httpurl+"/getCurrentSectionFiled";
			String jsonStr=HttpClientUtil.getResponse("POST",reqUrl, par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();	
		}catch (Exception e) {
			logger.error("获取当前环节可编辑的字段列表getCurrentSectionFiled  err：");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 流程环节内更新业务表数据
	 * @param Yhm:当前登录的用户名
	 * @param id:通知公告id
	 * @param apptoken:token字符串，用于加解密
	 */
	public void updateCurrentSectionFiled(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String yhm="";
			String id="";
			String tableName="";
			String paramJson="";
	   	    ResultEntity<JSONObject> result = null;
	   	    
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			yhm = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("id"))){
	 			id = new String(request.getParameter("id").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("tableName"))){
	 			tableName = new String(request.getParameter("tableName").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		if(StringUtils.isNotBlank(request.getParameter("paramJson"))){
	 			paramJson = new String(request.getParameter("paramJson").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		
			Gson gson = new Gson();
			
			String apptoken = request.getParameter("apptoken");
			if(!ApptokenUtils.compare(yhm, apptoken)){
		 		result = new ResultEntity<JSONObject>(2, "app_token error!", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
			try {
				yhm = CodeUtil.decode(yhm, apptoken);
				id = CodeUtil.decode(id, apptoken);
				if(StringUtils.isNotBlank(tableName)){
					tableName= URLEncoder.encode(CodeUtil.decode(tableName, apptoken), "UTF-8");
				}
				if(StringUtils.isNotBlank(paramJson)){
					paramJson= URLEncoder.encode(CodeUtil.decode(paramJson, apptoken), "UTF-8");
				}
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			String par="yhm="+yhm+"&id="+id+"&tableName="+tableName+"&paramJson="+paramJson+"&sign="+(oasign+yhm);
			String reqUrl = httpurl+"/updateCurrentSectionFiled";
			String jsonStr=HttpClientUtil.getResponse("POST",reqUrl, par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();	
		}catch (Exception e) {
			logger.error("流程环节内更新业务表数据updateCurrentSectionFiled  err：");
			e.printStackTrace();
		}
	}
	
}
