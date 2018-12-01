package com.zfsoft.mobile.classCommunity.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
//import com.ibm.icu.text.SimpleDateFormat;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.util.Byte_File_Object;
import com.zfsoft.mobile.classCommunity.entity.ClassSignInEntity;
import com.zfsoft.mobile.classCommunity.entity.ClassSignInEntityForInsert;
import com.zfsoft.mobile.classCommunity.entity.ClassSignInRecordEntity;
import com.zfsoft.mobile.classCommunity.entity.DynamicCommentEntity;
import com.zfsoft.mobile.classCommunity.entity.DynamicEntity;
import com.zfsoft.mobile.classCommunity.entity.DynamicFileEntityForInsert;
import com.zfsoft.mobile.classCommunity.service.IClassSignInService;
import com.zfsoft.mobile.classCommunity.util.MapUtil;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;

/**
 * 课堂签到action
 * @author H110MF
 *
 */
public class ClassSignInAction extends HrmAction{
	private IClassSignInService classSignInService; 
	
	
	/**
	 * 新增签到课程信息
	 */
	public void insertClassSignIn(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		PrintWriter out = null;  
		
		String classSignInId = null;         //手机端生成课程信息唯一值
		String className = null;
		String startTime = null;
		String endTime = null;
		String username = null;  //发布者账号
		String xm = null;        //发布者姓名
		String loglat = null; 
		String address = null;
		String bak = null;
		String apptoken = null;
		byte[] qrCodeFileContent = null;
		
		Gson gson = new Gson();
		ClassSignInEntityForInsert signInEntity = new ClassSignInEntityForInsert();
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
	            	paramName = fItem.getFieldName();  
	                fValue = fItem.getString("UTF-8");  
	                if(paramName.equals("classSignInId")){
	                	classSignInId = fValue.toString();
	                }else if(paramName.equals("className")){
	                	className = fValue.toString();
	                }else if(paramName.equals("username")){
	                	username = fValue.toString();
	                }else if(paramName.equals("xm")){
	                	xm = fValue.toString();
	                }else if(paramName.equals("startTime")){
	                	startTime = fValue.toString();
	                }else if(paramName.equals("endTime")){
	                	endTime = fValue.toString();
	                }else if(paramName.equals("loglat")){
	                	loglat = fValue.toString();
	                }else if(paramName.equals("address")){
	                	address = fValue.toString();
	                }else if(paramName.equals("bak")){
	                	bak = fValue.toString();
	                }else if(paramName.equals("apptoken")){
	                	apptoken = fValue.toString();
	                	
	                	if(!StringUtil.isEmpty(classSignInId)
	                			&& !StringUtil.isEmpty(className)
	                			&& !StringUtil.isEmpty(username)
	                			&& !StringUtil.isEmpty(xm)
	                			&& !StringUtil.isEmpty(startTime)
	                			&& !StringUtil.isEmpty(endTime)
	                			&& !StringUtil.isEmpty(loglat)
	                			&& !StringUtil.isEmpty(address)
	                			&& !StringUtil.isEmpty(apptoken)){
	                		classSignInId  = CodeUtil.decode(classSignInId, apptoken);
	                		className   = CodeUtil.decode(className, apptoken);
	                		username    = CodeUtil.decode(username, apptoken);
	                		xm       	= CodeUtil.decode(xm, apptoken);
	                		startTime   = CodeUtil.decode(startTime, apptoken);
	                		endTime     = CodeUtil.decode(endTime, apptoken);
	                		loglat      = CodeUtil.decode(loglat, apptoken);
	                		address     = CodeUtil.decode(address, apptoken);
	                		bak     = CodeUtil.decode(bak, apptoken);
	                		
	                		signInEntity.setClassSignInId(classSignInId);
	                		signInEntity.setTeacherId(username);
	                		signInEntity.setTeacherName(xm);
	                		signInEntity.setClassName(className);
	                		signInEntity.setStartTime(startTime);
	                		signInEntity.setEndTime(endTime);
	                		signInEntity.setLoglat(loglat);
	                		signInEntity.setAddress(address);
	                		signInEntity.setBak(bak);
	                	}
	                }
	            } else { // 获取上传文件的值  
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
		                    
		                    signInEntity.setQrCodeFileContent(fileContent);
		                    
		                    String filePath = request.getSession().getServletContext().getRealPath("/") + "classCommunityClassSignIn"; 
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
		        			if(classSignInId != null){
		        				fileOutputStream.write(fileContent, 0, fileContent.length);
		        			}else{
		        				fileOutputStream.write(signInEntity.getQrCodeFileContent(), 0, signInEntity.getQrCodeFileContent().length);
		        			}
		        			fileOutputStream.close();
		        			is.close();
		        			String url = BaseHolder.getPropertiesValue("suploadPath");
		        			signInEntity.setQrCodeFilePath(url+"classCommunityClassSignIn/"+filename);
		        			signInEntity.setQrCodeFileName(filename);
		                }  
		            }  
		        }
		        
	            classSignInService.insertClassSignIn(signInEntity);
	            
		        ResultEntity<String> result = new ResultEntity<String>(1, "发布成功", "发布成功");
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
	
	//新增学生签到记录
	public void insertClassSignInRecord(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String username=null;
		String name = null;
		String signInAddr = null;
		String signInTime = null;
		String loglat = null;
		String distance = null;
		String classSignInfoId = null;
		String apptoken = null;
		
		Gson gson = new Gson();
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try{
			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
			name = new String(request.getParameter("name").getBytes("ISO8859-1"), "UTF-8");
			signInAddr = new String(request.getParameter("signInAddr").getBytes("ISO8859-1"), "UTF-8");
			loglat = new String(request.getParameter("loglat").getBytes("ISO8859-1"), "UTF-8");
			classSignInfoId = new String(request.getParameter("classSignInfoId").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				username     = CodeUtil.decode(username, apptoken);
				name         = CodeUtil.decode(name, apptoken);
				signInAddr   = CodeUtil.decode(signInAddr, apptoken);
				loglat       = CodeUtil.decode(loglat, apptoken);
				classSignInfoId  = CodeUtil.decode(classSignInfoId, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		
			ClassSignInRecordEntity classSignInRecordEntity = new ClassSignInRecordEntity();
			classSignInRecordEntity.setStudentNumber(username);
			classSignInRecordEntity.setLoglat(loglat);
			classSignInRecordEntity.setSignInTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			classSignInRecordEntity.setSignInAddr(signInAddr);
			if(loglat!=null){
				String[] laglatStrs = loglat.split(",");
				if(laglatStrs.length==4){
					classSignInRecordEntity.setDistance(MapUtil.getDistance(laglatStrs[0], laglatStrs[1], laglatStrs[2], laglatStrs[3])+"km");
				}else{
					classSignInRecordEntity.setDistance("0km");
				}
			}
			classSignInRecordEntity.setClassSignInfoId(classSignInfoId);
			
			classSignInService.insertClassSignInRecord(classSignInRecordEntity);
			
	        ResultEntity<String> result = new ResultEntity<String>(1, "签到成功", "签到成功");
			
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
		}catch(Exception ex){
			ResultEntity<String> result = new ResultEntity<String>(0, "失败", "后台异常，签到失败");
			out.write(gson.toJson(result));
			out.flush();
			out.close();
			ex.printStackTrace();
		}
	}
	

	public IClassSignInService getClassSignInService() {
		return classSignInService;
	}

	public void setClassSignInService(IClassSignInService classSignInService) {
		this.classSignInService = classSignInService;
	}
}
