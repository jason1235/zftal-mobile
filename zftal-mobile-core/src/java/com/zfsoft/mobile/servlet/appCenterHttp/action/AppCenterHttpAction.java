/**
 * 
 */
package com.zfsoft.mobile.servlet.appCenterHttp.action;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.common.Config;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.util.encode.MD5Util;
import com.zfsoft.common.log.User;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.dao.entities.LoginModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.core.util.Byte_File_Object;
import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.hrm.file.util.UploadFileUtil;
import com.zfsoft.mobile.accessStatistics.service.IVisitService;
import com.zfsoft.mobile.common.utils.FileUntils;
import com.zfsoft.mobile.favourites.entity.FavouritesEntity;
import com.zfsoft.mobile.favourites.service.IFavouritesService;
import com.zfsoft.mobile.services.entity.Business;
import com.zfsoft.mobile.services.entity.ExamDyYhEntity;
import com.zfsoft.mobile.services.entity.ExamPaperEntity;
import com.zfsoft.mobile.services.entity.ExamQuestionEntity;
import com.zfsoft.mobile.services.entity.ExpressCommentEntity;
import com.zfsoft.mobile.services.entity.ExpressCommentEntityForApp;
import com.zfsoft.mobile.services.entity.ExpressEntity;
import com.zfsoft.mobile.services.entity.ExpressEntityForApp;
import com.zfsoft.mobile.services.entity.ExpressPicEntity;
import com.zfsoft.mobile.services.entity.GaoDeMaoEntity;
import com.zfsoft.mobile.services.entity.LossObjectEntity;
import com.zfsoft.mobile.services.entity.LossObjectPictureEntity;
import com.zfsoft.mobile.services.entity.Point;
import com.zfsoft.mobile.services.entity.ServiceManager;
import com.zfsoft.mobile.services.service.IExpressService;
import com.zfsoft.mobile.services.service.IGaoDeMapService;
import com.zfsoft.mobile.services.service.ILossObjectService;
import com.zfsoft.mobile.services.service.IQuestionService;
import com.zfsoft.mobile.servlet.appCenterHttp.service.IAppCenterHttpService;
import com.zfsoft.mobile.servlet.entity.BusinessSystemEntity;
import com.zfsoft.mobile.servlet.entity.ListEntity;
import com.zfsoft.mobile.servlet.entity.MemoListEntity;
import com.zfsoft.mobile.servlet.entity.QuestionInfo;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.mobile.servlet.entity.ServiceEntity;
import com.zfsoft.mobile.suggest.entity.SuggestEntity;
import com.zfsoft.mobile.suggest.entity.suggestPictureEntity;
import com.zfsoft.mobile.webservices.entity.MemoCatalog;
import com.zfsoft.mobile.webservices.entity.MemoDB;
import com.zfsoft.mobile.webservices.entity.MemoPictureEntity;
import com.zfsoft.mobile.webservices.entity.Questionnaire;
import com.zfsoft.mobile.webservices.entity.TopicForSubmit;
import com.zfsoft.service.svcinterface.ILoginService;
import com.zfsoft.service.svcinterface.IYhglService;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;

/**
 * @author zhangxu
 * @description app应用中心访问接口
 * @date 2017-5-8 下午04:52:32
 */
public class AppCenterHttpAction {
	private static Logger logger = Logger.getLogger(AppCenterHttpAction.class);
	private final String infromation=Config.getString("mobile.infromation");
	
	private IAppCenterHttpService appCenterHttpService;
	
	private IFavouritesService favouritesService;
	private ILossObjectService lossObjectService;
	private ILoginService loginService;
	private IQuestionService questionService;
	private IVisitService visitService;
	private IGaoDeMapService gaoDeMapService;
	private IYhglService yhglService;
	private IExpressService expressService;
	

	public IExpressService getExpressService() {
		return expressService;
	}

	public void setExpressService(IExpressService expressService) {
		this.expressService = expressService;
	}

	public IGaoDeMapService getGaoDeMapService() {
		return gaoDeMapService;
	}

	public void setGaoDeMapService(IGaoDeMapService gaoDeMapService) {
		this.gaoDeMapService = gaoDeMapService;
	}

	public IQuestionService getQuestionService() {
		return questionService;
	}

	public void setQuestionService(IQuestionService questionService) {
		this.questionService = questionService;
	}

	public ILoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(ILoginService loginService) {
		this.loginService = loginService;
	}

	public void setAppCenterHttpService(IAppCenterHttpService appCenterHttpService) {
		this.appCenterHttpService = appCenterHttpService;
	}

	public IAppCenterHttpService getAppCenterHttpService() {
		return appCenterHttpService;
	}
	
	
	public IFavouritesService getFavouritesService() {
		return favouritesService;
	}

	public void setFavouritesService(IFavouritesService favouritesService) {
		this.favouritesService = favouritesService;
	}

	public ILossObjectService getLossObjectService() {
		return lossObjectService;
	}

	public void setLossObjectService(ILossObjectService lossObjectService) {
		this.lossObjectService = lossObjectService;
	}
	
	public IYhglService getYhglService() {
		return yhglService;
	}
	
	public void setYhglService(IYhglService yhglService) {
		this.yhglService = yhglService;
	}
	
	public void visitService(){
		visitService.testCache();
	}

	/**
	 * 增加表白墙评论
	* @author: zhangxu
	* @Title: insertExpressComment 
	* @Description: 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */

	public void insertExpressComment(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String expressId = null;
		String commentContent = null;
		String goodFlag = null;
		String username = null;
		String anonymous = null;
   	 	String apptoken = null;
   	 	try {
   	 		PrintWriter out = response.getWriter();
   	 	    username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
   	 		expressId = new String(request.getParameter("expressId").getBytes("ISO8859-1"), "UTF-8");
   	 		commentContent = new String(request.getParameter("commentContent").getBytes("ISO8859-1"), "UTF-8");
   	 		goodFlag = new String(request.getParameter("goodFlag").getBytes("ISO8859-1"), "UTF-8");
   	 	    anonymous = new String(request.getParameter("anonymous").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
		 	
		 	
			Gson gson = new Gson();
			if(StringUtil.isEmpty(expressId) || StringUtil.isEmpty(apptoken) 
					|| StringUtil.isEmpty(goodFlag) || StringUtil.isEmpty(commentContent)){
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
				username            = CodeUtil.decode(username, apptoken);
				expressId  			= CodeUtil.decode(expressId, apptoken);
				goodFlag  			= CodeUtil.decode(goodFlag, apptoken);
				commentContent  	= CodeUtil.decode(commentContent, apptoken);
				anonymous           = CodeUtil.decode(anonymous, apptoken);
				
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			ExpressCommentEntity query = new ExpressCommentEntity();
			query.setExpressId(expressId);
			query.setGoodFlag("".equals(goodFlag)||goodFlag==""? "0":goodFlag);
			query.setCommentContent(commentContent);
			query.setAnonymous(anonymous);
			
			if("1".equals(anonymous)){
				query.setUsername("***");
				query.setXm("***");
			}else{
				query.setUsername(username);
				String xm = yhglService.xmByZgh(username);
				query.setXm(xm);
			}
			
			expressService.insertComment(query);
	        ResultEntity<ListEntity<ExpressCommentEntity>> result = new ResultEntity<ListEntity<ExpressCommentEntity>>(1, "成功", null);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
   	 	
   	 	} catch (Exception e) {
			logger.error("updateExpressGoodFlag err：",e);
	 	}
	
	
	}
	
	/**
	 * 发布表白墙
	* @author: zhangxu
	* @Title: publishExpress 
	* @Description: 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void publishExpress(){
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
        String title = null;
        String textContent = null;
        String theType = null;
        String userName = null;
        String anonymous = null;
        String apptoken = null;
        String expressId = null;
        ExpressEntity expressEntity = new ExpressEntity();
        ExpressPicEntity picEntity;
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
                    if(paramName.equals("title")){
                    	title = fValue.toString();
                    }else if(paramName.equals("textContent")){
                    	textContent = fValue.toString();
                    }else if(paramName.equals("theType")){
                    	theType = fValue.toString();
                    }else if(paramName.equals("username")){
                    	userName = fValue.toString();
                    }else if(paramName.equals("apptoken")){
                    	apptoken = fValue.toString();
                    }
                    if(!StringUtil.isEmpty(apptoken) && !StringUtil.isEmpty(title)
                    		&& !StringUtil.isEmpty(textContent)&& !StringUtil.isEmpty(theType)
                    		&& !StringUtil.isEmpty(userName)){
                    	title       		= CodeUtil.decode(title, apptoken);
                    	textContent      = CodeUtil.decode(textContent, apptoken);
                    	theType       = CodeUtil.decode(theType, apptoken);
                    	userName   = CodeUtil.decode(userName, apptoken);
                    	//anonymous = CodeUtil.decode(anonymous, apptoken);
                    	expressEntity.setTitle(title);
                    	expressEntity.setTextContent(textContent);
                    	expressEntity.setTheType(theType);
                    	expressEntity.setUserName(userName);
                    	//expressEntity.setAnonymous(anonymous);
                    	expressService.insertExpress(expressEntity);
                    	expressId = expressEntity.getExpressId();
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
                        picEntity = new ExpressPicEntity();
                        picEntity.setExpressId(expressId);
                        picEntity.setPicContent(content);
                        picEntity.setPicName(filename);
                        
                        String filePath = request.getSession().getServletContext().getRealPath("/") + "expressPicture"; 
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
            				fileOutputStream.write(picEntity.getPicContent(), 0, picEntity.getPicContent().length);
            			}
            			fileOutputStream.close();
            			is.close();
            			picEntity.setPicPath("expressPicture/"+filename);
            			int maxOrderNumber = expressService.getMaxOrderNumber(expressId) + 1;
            			picEntity.setOrderNumber(maxOrderNumber);
            			expressService.insertExpressPic(picEntity);
                        
                    }  
                }  
            }  
            
            ResultEntity<String> result = new ResultEntity<String>(1, "成功", null);
            out.write(gson.toJson(result));
            out.flush();
            out.close();
            
        } catch (Exception e) {  
            //e.printStackTrace(); 
            logger.error("publishExpress error ", e);
            ResultEntity<String> result = new ResultEntity<String>(0, "操作失败", null);
            out.write(gson.toJson(result));
            out.flush();
            out.close();
         // 数据处理  
        }  
	
	}
	
	
	/**
	 * 获取表白墙评论数
	* @author: zhangxu
	* @Title: getExpressComentList 
	* @Description: 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void getExpressComentList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String expressId = null;
		String size = null;
		String start = null;
		String username = null;
   	 	String apptoken = null;
   	 	try {
   	 		PrintWriter out = response.getWriter();
   	 		username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
   	 		expressId = new String(request.getParameter("expressId").getBytes("ISO8859-1"), "UTF-8");
   	 		size = new String(request.getParameter("size").getBytes("ISO8859-1"), "UTF-8");
   	 		start = new String(request.getParameter("start").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
		 	
		 	
			Gson gson = new Gson();
			if(StringUtil.isEmpty(expressId) || StringUtil.isEmpty(apptoken) 
					|| StringUtil.isEmpty(size) || StringUtil.isEmpty(start)){
				ResultEntity<ListEntity<ExpressCommentEntityForApp>> result = new ResultEntity<ListEntity<ExpressCommentEntityForApp>>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<ListEntity<ExpressCommentEntityForApp>> result = new ResultEntity<ListEntity<ExpressCommentEntityForApp>>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				username  		= CodeUtil.decode(username, apptoken);
				expressId  		= CodeUtil.decode(expressId, apptoken);
				size  			= CodeUtil.decode(size, apptoken);
				start  			= CodeUtil.decode(start, apptoken);
			} catch (Exception e) {
				ResultEntity<ListEntity<ExpressCommentEntityForApp>> result = new ResultEntity<ListEntity<ExpressCommentEntityForApp>>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			ExpressCommentEntity query = new ExpressCommentEntity();
			query.setExpressId(expressId);
			query.setPerPageSize(Integer.valueOf(size));
			query.setToPage(Integer.valueOf(start));
			List<ExpressCommentEntityForApp> list = expressService.getCommentList(query);
			
			//遍历列表，设置评论的goodCount和currentUserGoodFlag字段
			Map<String,Object> paramsCheck = new HashMap<String, Object>();
			for (int i = 0; i < list.size(); i++) {
				ExpressCommentEntityForApp ecefa = list.get(i);
				if(ecefa!=null){
					String commentid = ecefa.getCommentId();
					paramsCheck.put("username", username);
					paramsCheck.put("commentid", commentid);
					int rows = expressService.checkUserGoodFlag(paramsCheck);
					if(rows>0){
						list.get(i).setCurrentUserGoodFlag("1");
					}else{
						list.get(i).setCurrentUserGoodFlag("0");
					}
				}
			}
			
			ListEntity<ExpressCommentEntityForApp> resultList = new ListEntity<ExpressCommentEntityForApp>();
			resultList.setItemList(list);
			if(list == null || list.size() < Integer.valueOf(size)){
				resultList.setOvered(true);
			}else{
				resultList.setOvered(false);
			} 
	        ResultEntity<ListEntity<ExpressCommentEntityForApp>> result = new ResultEntity<ListEntity<ExpressCommentEntityForApp>>(1, "成功", resultList);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
   	 	
   	 	} catch (Exception e) {
			logger.error("getExpressComentList",e);
	 	}
	
	
	}
	
	
	/**
	 * 点赞
	* @author: zhangxu
	* @Title: insertExpressComment 
	* @Description: 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */

	public void insertExpressGoodFlag(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String username= null;
		String commentid = null;
   	 	String apptoken = null;
   	 	try {
   	 		PrintWriter out = response.getWriter();
   	 	    username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
   	        commentid = new String(request.getParameter("commentid").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
		 	
		 	
			Gson gson = new Gson();
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				username  			= CodeUtil.decode(username, apptoken);
				commentid  			= CodeUtil.decode(commentid, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("username",username);
			params.put("commentid",commentid);
			
			//判断该用户是否已点赞
			int rows = expressService.checkUserGoodFlag(params);
			if(rows>0){
				ResultEntity<ListEntity<ExpressCommentEntity>> result = new ResultEntity<ListEntity<ExpressCommentEntity>>(0, "已点赞", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}else{
				expressService.insertExpressGoodFlag(params);
				expressService.updateGoodCount(params);
				ResultEntity<ListEntity<ExpressCommentEntity>> result = new ResultEntity<ListEntity<ExpressCommentEntity>>(1, "成功", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
   	 	
   	 	} catch (Exception e) {
			logger.error("insertExpressGoodFlag err：",e);
	 	}
	}
	
	/**
	 * 获取表白墙发布具体详情，不包括评论
	* @author: zhangxu
	* @Title: getExpressDetail 
	* @Description: 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
//	public void getExpressDetail(){
//		HttpServletResponse response = ServletActionContext.getResponse();
//		response.setContentType("text/html");
//		response.setCharacterEncoding("utf-8");
//		HttpServletRequest request = ServletActionContext.getRequest();
//		String expressId = null;
//   	 	String apptoken = null;
//   	 	try {
//   	 		PrintWriter out = response.getWriter();
//   	 		expressId = new String(request.getParameter("expressId").getBytes("ISO8859-1"), "UTF-8");
//		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
//		 	
//		 	
//			Gson gson = new Gson();
//			if(StringUtil.isEmpty(expressId) || StringUtil.isEmpty(apptoken)){
//				ResultEntity<ExpressEntity> result = new ResultEntity<ExpressEntity>(0, "参数传值出错！", null);
//				out.write(gson.toJson(result));
//				out.flush();
//				out.close();
//			}
//			if(!ApptokenUtils.compare(apptoken)){
//				ResultEntity<ExpressEntity> result = new ResultEntity<ExpressEntity>(2, "app_token error!", null);
//				out.write(gson.toJson(result));
//				out.flush();
//				out.close();
//			}
//			
//			try {
//				expressId  		= CodeUtil.decode(expressId, apptoken);
//			} catch (Exception e) {
//				ResultEntity<ExpressEntity> result = new ResultEntity<ExpressEntity>(0, "加密方式出错！", null);
//				out.write(gson.toJson(result));
//				out.flush();
//				out.close();
//			}
//			ExpressEntity query = new ExpressEntity();
//			query.setExpressId(expressId);
//			query = expressService.getList(query).get(0);
//			query.setPicList(expressService.getExpressPicById(query.getExpressId()));
//	        ResultEntity<ExpressEntity> result = new ResultEntity<ExpressEntity>(1, "成功", query);
//			out.write(gson.toJson(result));
//			out.flush();
//			out.close();
//   	 	
//   	 	} catch (Exception e) {
//	   	 	logger.error("提交问卷发布接口submitExam err：");
//			logger.error(e, e.fillInStackTrace());
//	 	}
//	}
	
	/**
	 * 获取表白墙列表页面
	* @author: zhangxu
	* @Title: getExpressList 
	* @Description: 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void getExpressList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String title = null;
		String theType = null;
		String size = null;
		String start = null;
   	 	String apptoken = null;
   	 	try {
   	 		PrintWriter out = response.getWriter();
   	 		title = new String(request.getParameter("title").getBytes("ISO8859-1"), "UTF-8");
   	 		theType = new String(request.getParameter("theType").getBytes("ISO8859-1"), "UTF-8");
   	 		size = new String(request.getParameter("size").getBytes("ISO8859-1"), "UTF-8");
   	 		start = new String(request.getParameter("start").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
		 	
		 	
			Gson gson = new Gson();
			if(StringUtil.isEmpty(title) || StringUtil.isEmpty(apptoken) 
					|| StringUtil.isEmpty(theType) || StringUtil.isEmpty(size)
					|| StringUtil.isEmpty(start)){
				ResultEntity<ListEntity<ExpressEntityForApp>> result = new ResultEntity<ListEntity<ExpressEntityForApp>>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<ListEntity<ExpressEntityForApp>> result = new ResultEntity<ListEntity<ExpressEntityForApp>>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				title  			= CodeUtil.decode(title, apptoken);
				theType  		= CodeUtil.decode(theType, apptoken);
				size  			= CodeUtil.decode(size, apptoken);
				start  			= CodeUtil.decode(start, apptoken);
			} catch (Exception e) {
				ResultEntity<ListEntity<ExpressEntityForApp>> result = new ResultEntity<ListEntity<ExpressEntityForApp>>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			ExpressEntity query = new ExpressEntity();
			query.setTitle(title);
			query.setTheType(theType);
			query.setPerPageSize(Integer.valueOf(size));
			query.setToPage(Integer.valueOf(start));
			List<ExpressEntityForApp> list = expressService.getList(query);
			if(list != null && list.size() > 0){
				for (int i = 0; i < list.size(); i++) {
					System.out.println(list.get(i).getCreateTime());
					List<ExpressPicEntity> picList = expressService.getExpressPicById(list.get(i).getExpressId());
					for (int j = 0; j < picList.size(); j++) {
						boolean check = false;
			    		try {
			    			check = checkImage(picList.get(j).getPicPath(), picList.get(j).getPicContent());
			    		} catch (IOException e) {
			    			// TODO Auto-generated catch block
			    			e.printStackTrace();
			    		}
			    		if(!check){
			    			picList.get(j).setPicPath(getImageHost()+"upload/default_image.jpg");
			    		}else{
			    			picList.get(j).setPicPath(getImageHost()+picList.get(j).getPicPath());
			    		}
			    		picList.get(j).setPicContent(null);
					}
					list.get(i).setPicList(picList);
					
					//匿名
					if ("1".equals(list.get(i).getAnonymous())) {
						list.get(i).setUserName("***");
					}
				}
			}
			ListEntity<ExpressEntityForApp> resultList = new ListEntity<ExpressEntityForApp>();
			resultList.setItemList(list);
			if(list == null || list.size() < Integer.valueOf(size))	resultList.setOvered(true);
			else resultList.setOvered(false);
	        ResultEntity<ListEntity<ExpressEntityForApp>> result = new ResultEntity<ListEntity<ExpressEntityForApp>>(1, "成功", resultList);
			String jsonStr = gson.toJson(result);
	        out.write(jsonStr);
			out.flush();
			out.close();
   	 	
   	 	} catch (Exception e) {
			logger.error("getExpressList",e);
			
	 	}
	}
	
	public boolean checkImage(String url,byte[] content) throws IOException {
		if(StringUtils.isEmpty(url) && content == null)
			return false;
		if(StringUtils.isEmpty(url)){
				return doImage(url,content);
		}
		if(!StringUtils.isEmpty(url)){
				HttpServletRequest request = ServletActionContext.getRequest();
				String tempPath = request.getSession().getServletContext().getRealPath("/") + url;
				File file = new File(tempPath);
				if(file.exists())
					return true;
				else{
						return doImage(url,content);
				}
		}
		return false;
	}
	
	public boolean doImage(String url, byte[] content) throws IOException{
		if(content == null) 
			return false;
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);
		BufferedImage newImage = ImageIO.read(byteArrayInputStream);
		
		String uploadPath = "expressPicture";
		String tempPath1 = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		if (StringUtil.isEmpty(url)) {
			tempPath1 = request.getSession().getServletContext().getRealPath("/") + uploadPath;
		} else {
			tempPath1 = request.getSession().getServletContext().getRealPath("/") + url;
		}
		String fileName = url.substring(url.indexOf("/"));
		if (tempPath1 == null || tempPath1.isEmpty()) {
            return false;
        }
		File outFile = null;
		if (tempPath1.toLowerCase().endsWith(".png") 
				|| tempPath1.toLowerCase().endsWith(".jpeg") 
				|| tempPath1.toLowerCase().endsWith(".jpg") 
				|| tempPath1.toLowerCase().endsWith(".bmp")
				|| tempPath1.toLowerCase().endsWith(".gif")
		) {
			outFile = new File(tempPath1);
		} else {
			File folder = new File(tempPath1);
			if(!folder.exists() || !folder.isDirectory())
				folder.mkdirs();
			outFile = new File(tempPath1, fileName);
		}
		String path = outFile.getAbsolutePath();
		String dirPath = path.substring(0, path.lastIndexOf(File.separator));
		File dirFile = new File(dirPath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		outFile.createNewFile();
		ImageIO.write(newImage, UploadFileUtil.checkedFileName(fileName), outFile);
		return true;
	}
	
	public void getMapList() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
//   	 	String apptoken = null;
	   	try {
	   		PrintWriter out = response.getWriter();
	   		Gson gson = new Gson();
//	   		apptoken = request.getParameter("apptoken");
//			if(StringUtil.isEmpty(apptoken)){
//				ResultEntity<List<GaoDeMaoEntity>> result = new ResultEntity<List<GaoDeMaoEntity>>(0, "参数传值出错！", null);
//				out.write(gson.toJson(result));
//				out.flush();
//				out.close();
//			}
//			if(!ApptokenUtils.compare(apptoken)){
//				ResultEntity<List<GaoDeMaoEntity>> result = new ResultEntity<List<GaoDeMaoEntity>>(2, "app_token error!", null);
//				out.write(gson.toJson(result));
//				out.flush();
//				out.close();
//			}
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
			ResultEntity<List<GaoDeMaoEntity>> result = new ResultEntity<List<GaoDeMaoEntity>>(1, "成功", mapList);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
	   	} catch (Exception e) {
	   		e.fillInStackTrace();
	 	}
   	 	
	}
	
	/**
	 * 获取校区内地图数据
	 */
	public void getSubMapList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String name = null;
	   	try {
	   		PrintWriter out = response.getWriter();
	   		Gson gson = new Gson();
	   		name = request.getParameter("name")==null? "0":request.getParameter("name");
			IGaoDeMapService gaoDeMapService = (IGaoDeMapService) SpringHolder.getBean("gaoDeMapService");
			GaoDeMaoEntity query = new GaoDeMaoEntity();
			query.setName(name);
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
			ResultEntity<List<GaoDeMaoEntity>> result = new ResultEntity<List<GaoDeMaoEntity>>(1, "成功", mapList);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
	   	} catch (Exception e) {
	   		e.printStackTrace();
	 	}
   	 	
	}
	
	/**
	 * 
	* @author: zhangxu
	* @Title: submitExamAnswer 
	* @Description: 提交问卷答案
	* @param @param userId 用户名
	* @param @param xml 问卷答案
	* @param @param strKey 密钥
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public void submitExamAnswer() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = null;
		String xml = null;
		String strKey = null;
   	 	String apptoken = null;
   	 	try {
   	 		PrintWriter out = response.getWriter();
	   	 	username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	   	 	xml = new String(request.getParameter("xml").getBytes("ISO8859-1"), "UTF-8");
	   	 	strKey = new String(request.getParameter("strKey").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
		 	
		 	
			Gson gson = new Gson();
			if(StringUtil.isEmpty(username) || StringUtil.isEmpty(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			if(!ApptokenUtils.compare(username, apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				username  			= CodeUtil.decode(username, apptoken);
				xml  				= CodeUtil.decode(xml, apptoken);
				strKey  			= CodeUtil.decode(strKey, apptoken);
				
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
//			List<QuestionInfo> questionInfoList = gson.fromJson(xml, List.class);
//			String questionid = "";
//			for (int i = 0; i < questionInfoList.size(); i++) {
//				ExamDyYhEntity entity = new ExamDyYhEntity();
//				entity.setYhid(username);
//				entity.setQuestionid(questionInfoList.get(i).getKey());
//				questionid = questionInfoList.get(i).getKey();
//				entity.setItemvalue(questionInfoList.get(i).getValue());
//				questionService.insertAnswer(entity);
//			}
//			String papaermainid = questionService.getPapermainidByQes(questionid);
//			Map<String, String> map = new HashMap<String, String>();
//			map.put("yhid", username);
//			map.put("papermainid", papaermainid);
//			questionService.insertExamYh(map);
			List<QuestionInfo> questionInfoList = gson.fromJson(xml, new TypeToken<List<QuestionInfo>>(){}.getType());
//			org.json.JSONArray array = new org.json.JSONArray(xml);
			String questionid = "";
			for (int i = 0; i < questionInfoList.size(); i++) {
				
				ExamDyYhEntity entity = new ExamDyYhEntity();
				entity.setYhid(username);
				entity.setQuestionid(questionInfoList.get(i).getKey());
				questionid = questionInfoList.get(i).getKey();
				entity.setItemvalue(questionInfoList.get(i).getValue());
				questionService.insertAnswer(entity);
			}
			String papaermainid = questionService.getPapermainidByQes(questionid);
			Map<String, String> map = new HashMap<String, String>();
			map.put("yhid", username);
			map.put("papermainid", papaermainid);
			questionService.insertExamYh(map);
			
			
	        ResultEntity<String> result = new ResultEntity<String>(1, "成功", null);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
   	 	
   	 	} catch (Exception e) {
	   	 	logger.error("提交问卷发布接口submitExam err：");
			logger.error(e, e.fillInStackTrace());
	 	}
	
	
	}
	
	/**
	 * 
	* @author: zhangxu
	* @Title: submitExam 
	* @Description: 提交问卷发布
	* @param @param username 用户id
	* @param @param xml  问卷信息
	* @param @param strKey   密钥
	* @return void    返回类型 
	* @throws
	 */
	public void submitExam(){

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = null;
		String xml = null;
		String strKey = null;
   	 	String apptoken = null;
   	 	try {
   	 		PrintWriter out = response.getWriter();
	   	 	username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	   	 	xml = new String(request.getParameter("xml").getBytes("ISO8859-1"), "UTF-8");
	   	 	strKey = new String(request.getParameter("strKey").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
		 	
		 	
			Gson gson = new Gson();
			if(StringUtil.isEmpty(username) || StringUtil.isEmpty(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			if(!ApptokenUtils.compare(username, apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				username  			= CodeUtil.decode(username, apptoken);
				xml  				= CodeUtil.decode(xml, apptoken);
				strKey  			= CodeUtil.decode(strKey, apptoken);
				
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			 Questionnaire questionnaire = gson.fromJson(xml, Questionnaire.class);
			 String qn_intro   = questionnaire.getQn_intro();
			 String qn_marking = questionnaire.getQn_marking();
			 String qn_name = questionnaire.getQn_name();
			 String qn_owner = questionnaire.getQn_owner();
			 ExamPaperEntity entity = new ExamPaperEntity();
			 entity.setPapermainname(qn_name);
			 entity.setInstruction(qn_intro);
			 entity.setRemark(qn_marking);
			 entity.setCreater(username);
			 entity.setCreatetime(new Date());
			 entity.setQn_owner(qn_owner);
			 questionService.insert(entity);
			 String papermainid = null;
			 papermainid = entity.getPapermainid();
			
			 ArrayList<TopicForSubmit>  list = questionnaire.getTopics();
				
			 for(int i = 0; i < list.size(); i++){
				 ExamQuestionEntity questionEntity = new ExamQuestionEntity();
				 questionEntity.setTitle(list.get(i).getTitle());
				 questionEntity.setType(String.valueOf(list.get(i).getType()));
				 questionEntity.setItems(list.get(i).getSelection());
				 questionEntity.setMaxItem(String.valueOf(list.get(i).getMaxSel()));
				 questionEntity.setSort(String.valueOf(i+1));
				 questionEntity.setPapermainid(papermainid);
				 questionService.insertQuestion(questionEntity);
			 }
	        
	        ResultEntity<String> result = new ResultEntity<String>(1, "成功", null);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
   	 	
   	 	} catch (Exception e) {
	   	 	logger.error("提交问卷发布接口submitExam err：");
			logger.error(e, e.fillInStackTrace());
	 	}
	
	}
	
	/**
	 * 
	* @author: zhangxu
	* @Title: getExamList 
	* @Description: 获取问卷
	* @param @param username 用户id
	* @param @param start 页码
	* @param @param size 每页多少
	* @param @param strKey    密钥
	* @return void    返回类型 
	* @throws
	 */
	public void getExamList(){

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = null;
		String start = null;
		String size = null;
		String strKey = null;
   	 	String apptoken = null;
   	 	try {
   	 		PrintWriter out = response.getWriter();
	   	 	username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	   	 	start = new String(request.getParameter("start").getBytes("ISO8859-1"), "UTF-8");
	   	 	size = new String(request.getParameter("size").getBytes("ISO8859-1"), "UTF-8");
	   	 	strKey = new String(request.getParameter("strKey").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
		 	
		 	
			Gson gson = new Gson();
			//List<ExamPaperEntity> entityList = new ArrayList<ExamPaperEntity>();
			if(StringUtil.isEmpty(username) || StringUtil.isEmpty(apptoken)){
				ResultEntity<ListEntity<ExamPaperEntity>> result = new ResultEntity<ListEntity<ExamPaperEntity>>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			if(!ApptokenUtils.compare(username, apptoken)){
				ResultEntity<ListEntity<ExamPaperEntity>> result = new ResultEntity<ListEntity<ExamPaperEntity>>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				username  			= CodeUtil.decode(username, apptoken);
				start  				= CodeUtil.decode(start, apptoken);
				size  				= CodeUtil.decode(size, apptoken);
				strKey  			= CodeUtil.decode(strKey, apptoken);
				
			} catch (Exception e) {
				ResultEntity<ListEntity<ExamPaperEntity>> result = new ResultEntity<ListEntity<ExamPaperEntity>>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			ExamPaperEntity query = new ExamPaperEntity();
			query.setPerPageSize(Integer.valueOf(size));
			query.setToPage(Integer.valueOf(start));
			query.setCreater(username);
			List<ExamPaperEntity> list = questionService.getExamList(query);
			if(list != null && list.size() > 0){
				for(int i=0; i<list.size(); i++){
					ExamQuestionEntity questionQuery = new ExamQuestionEntity();
					questionQuery.setPapermainid(list.get(i).getPapermainid());
					List<ExamQuestionEntity>  questionList = questionService.getQuestionList(questionQuery);
					list.get(i).setQuestionList(questionList);
				}
			}
			ListEntity<ExamPaperEntity> resultList = new ListEntity<ExamPaperEntity>();
			resultList.setItemList(list);
			if(list == null || list.size() < Integer.valueOf(size))	resultList.setOvered(true);
			else resultList.setOvered(false);
	        ResultEntity<ListEntity<ExamPaperEntity>> result = new ResultEntity<ListEntity<ExamPaperEntity>>(1, "成功", resultList);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
   	 	
   	 	} catch (Exception e) {
	   	 	logger.error("应用中心接口getALLXtYwByUser err：");
			logger.error(e, e.fillInStackTrace());
	 	}
	
	}
	
	/**
	 * 
	* @author: zhangxu
	* @Title: submitLosser 
	* @Description: 失物招领提交招领或丢失人
	* @param @param lossObjectId 记录id
	* @param @param losser	招领或丢失人
	* @param @param apptoken   密钥
	* @return void    返回类型 
	* @throws
	 */
	public void submitLosser(){


		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String lossObjectId = null;
		String losser = null;
		String apptoken = null;
   	 	try {
   	 		PrintWriter out = response.getWriter();
   	 		lossObjectId = new String(request.getParameter("lossObjectId").getBytes("ISO8859-1"), "UTF-8");
   	 		losser = new String(request.getParameter("losser").getBytes("ISO8859-1"), "UTF-8");
   	 		apptoken = request.getParameter("apptoken");
		 	apptoken = StringUtil.isEmpty(apptoken) ? "" : apptoken;
		 	
		 	
			Gson gson = new Gson();
			List<LossObjectEntity> entityList = null;
			if(StringUtil.isEmpty(lossObjectId) || StringUtil.isEmpty(losser) || StringUtil.isEmpty(apptoken)){
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
				lossObjectId  		= CodeUtil.decode(lossObjectId, apptoken);
				losser  			= CodeUtil.decode(losser, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			LossObjectEntity query = new LossObjectEntity();
			query.setId(lossObjectId);
			query.setLossuser(losser);
			lossObjectService.updateLosser(query);
	        ResultEntity<String> result = new ResultEntity<String>(1, "成功", null);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
   	 	
   	 	} catch (Exception e) {
	   	 	logger.error("获取失物招领列表submitLosser err：");
			logger.error(e, e.fillInStackTrace());
	 	}
	
	}
	
	/**
	 * 
	* @author: zhangxu
	* @Title: publishLoss 
	* @Description: 发布失物招领，有附件重新使用此方式
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void publishLoss(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
        response.setContentType("text/html;charset=UTF-8");  
		PrintWriter out = null;  
        response.setContentType("text/html;charset=UTF-8");  
        FileItemFactory factory = new DiskFileItemFactory();  
        ServletFileUpload upload = new ServletFileUpload(factory);  
        List<FileItem> items = new ArrayList<FileItem>();  
        boolean isRight = false;
        String username = null;
		String title = null;
		String place = null;
		String content = null;
		String flag = null;
		String telephone = null;
		String apptoken = null;
        Gson gson = new Gson();
        LoginModel model = new LoginModel();
        LossObjectPictureEntity pictureEntity = new LossObjectPictureEntity();
        String lossObjectId = null;
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
                    if(paramName.equals("username")){
                    	username = fValue.toString();
                    }else if(paramName.equals("title")){
                    	title = fValue.toString();
                    }else if(paramName.equals("place")){
                    	place = fValue.toString();
                    }else if(paramName.equals("content")){
                    	content = fValue.toString();
                    }else if(paramName.equals("flag")){
                    	flag = fValue.toString();
                    }else if(paramName.equals("telephone")){
                    	telephone = fValue.toString();
                    }else if(paramName.equals("apptoken")){
                    	apptoken = fValue.toString();
                    	try {
	                    	username   = CodeUtil.decode(username, apptoken);
	                    	title      = CodeUtil.decode(title, apptoken);
	                    	place      = CodeUtil.decode(place, apptoken);
	                    	telephone  = CodeUtil.decode(telephone, apptoken);
	                    	flag       = CodeUtil.decode(flag, apptoken);
                    	} catch (Exception e) {
                    		ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
                    		out.write(gson.toJson(result));
                    		out.flush();
                    		out.close();
                    	}
                    	model.setYhm(username);
            			User user = loginService.cxYhxx(model);
            			LossObjectEntity entity = new LossObjectEntity();
            			entity.setUsername(username);
            			entity.setName(user.getXm());
            			entity.setContent(content);
            			entity.setTitle(title);
            			entity.setPlace(place);
            			entity.setFlag(flag);
            			entity.setTelephone(telephone);
            			lossObjectService.insert(entity);
            			lossObjectId = entity.getId();
                    }
                } else { // 获取上传文件的值  
                	logger.error("进入建议反馈图片上传方法");
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
                        pictureEntity = new LossObjectPictureEntity();
                        pictureEntity.setLossObjectId(lossObjectId);
                        pictureEntity.setPictureContent(fileContent);
                        pictureEntity.setTitle(filename);
                        
                        String filePath = request.getSession().getServletContext().getRealPath("/") + "lossObjectPicture"; 
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
            				fileOutputStream.write(fileContent, 0, fileContent.length);
            			}else{
            				fileOutputStream.write(pictureEntity.getPictureContent(), 0, pictureEntity.getPictureContent().length);
            			}
            			fileOutputStream.close();
            			is.close();
            			pictureEntity.setPicturePath("lossObjectPicture/"+filename);
            			lossObjectService.insertLossObjectPicture(pictureEntity);
                        
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
	 * 
	* @author: zhangxu
	* @Title: publishLossObject 
	* @Description: 发布失物招领
	* @param @param username 发布者
	* @param @param title 标题
	* @param @param place 地点
	* @param @param content 内容
	* @param @param apptoken 密钥
	* @param @param flag    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void publishLossObject(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = null;
		String title = null;
		String place = null;
		String content = null;
		String flag = null;
		String apptoken = null;
   	 	try {
   	 		PrintWriter out = response.getWriter();
	   	 	username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	   	 	title = new String(request.getParameter("title").getBytes("ISO8859-1"), "UTF-8");
	   	 	place = new String(request.getParameter("place").getBytes("ISO8859-1"), "UTF-8");
	   	 	content = new String(request.getParameter("content").getBytes("ISO8859-1"), "UTF-8");
	   	 	flag = new String(request.getParameter("flag").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			Gson gson = new Gson();
			if(StringUtil.isEmpty(username) || StringUtil.isEmpty(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			if(!ApptokenUtils.compare(username, apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				username  			= CodeUtil.decode(username, apptoken);
				title  				= CodeUtil.decode(title, apptoken);
				place  				= CodeUtil.decode(place, apptoken);
				content  			= CodeUtil.decode(content, apptoken);
				flag  				= CodeUtil.decode(flag, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			LoginModel model = new LoginModel();
			model.setYhm(username);
			User user = loginService.cxYhxx(model);
			LossObjectEntity entity = new LossObjectEntity();
			entity.setUsername(username);
			entity.setName(user.getXm());
			entity.setContent(content);
			entity.setTitle(title);
			entity.setPlace(place);
			entity.setFlag(flag);
			lossObjectService.insert(entity);
			
	        ResultEntity<String> result = new ResultEntity<String>(1, "成功", null);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
   	 	
   	 	} catch (Exception e) {
	   	 	logger.error("应用中心接口getALLXtYwByUser err：");
			logger.error(e, e.fillInStackTrace());
	 	}
	
	}

	/**
	 * 
	* @author: zhangxu
	* @Title: getLossObjectList 
	* @Description: 获取失物招领列表
	* @param @param userid 用户id
	* @param @param title 标题模糊检索
	* @param @param isover 是否已招领
	* @param @param start 页码
	* @param @param size 每页多少
	* @param @param apptoken    密钥
	* @return void    返回类型 
	* @throws
	 */
	public void getLossObjectList(){

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = null;
		String title = null;
		String isover = null;
		String start = null;
		String size = null;
		String apptoken = null;
   	 	try {
   	 		PrintWriter out = response.getWriter();
	   	 	username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	   	 	title = new String(request.getParameter("title").getBytes("ISO8859-1"), "UTF-8");
	   	 	isover = new String(request.getParameter("isover").getBytes("ISO8859-1"), "UTF-8");
	   	 	start = new String(request.getParameter("start").getBytes("ISO8859-1"), "UTF-8");
	   	 	size = new String(request.getParameter("size").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
		 	
		 	
			Gson gson = new Gson();
			//List<LossObjectEntity> entityList = null;
			if(StringUtil.isEmpty(username) || StringUtil.isEmpty(apptoken)){
				ResultEntity<ListEntity<LossObjectEntity>> result = new ResultEntity<ListEntity<LossObjectEntity>>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<ListEntity<LossObjectEntity>> result = new ResultEntity<ListEntity<LossObjectEntity>>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				username  			= CodeUtil.decode(username, apptoken);
				title  				= CodeUtil.decode(title, apptoken);
				isover  			= CodeUtil.decode(isover, apptoken);
				start  				= CodeUtil.decode(start, apptoken);
				size  				= CodeUtil.decode(size, apptoken);
			} catch (Exception e) {
				ResultEntity<ListEntity<LossObjectEntity>> result = new ResultEntity<ListEntity<LossObjectEntity>>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			LossObjectEntity query = new LossObjectEntity();
			query.setUsername(username);
			query.setTitle(title);
			if(!StringUtil.isEmpty(isover))	query.setIspass("2");
			query.setIsover(isover);
			query.setToPage(Integer.valueOf(start));
			query.setPerPageSize(Integer.valueOf(size));
			if(StringUtil.isEmpty(username)){
				query.setIspass("2");
			}else{
				query.setIspass("4");
				query.setIspass("4");
			}
			List<LossObjectEntity> list = lossObjectService.getList(query);
			ListEntity<LossObjectEntity> resultList = new ListEntity<LossObjectEntity>();
			resultList.setItemList(list);
			if(list == null || list.size() < Integer.valueOf(size))	resultList.setOvered(true);
			else resultList.setOvered(false);
	        ResultEntity<ListEntity<LossObjectEntity>> result = new ResultEntity<ListEntity<LossObjectEntity>>(1, "成功", resultList);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
   	 	
   	 	} catch (Exception e) {
	   	 	logger.error("获取失物招领列表getLossObjectList err：");
			logger.error(e, e.fillInStackTrace());
	 	}
	}
	
	/**
	 * 删除备忘录
	 * @param memoFileNameList  备忘录名称列表,逗号","隔开
	 */
	public void deleteMyMemoList(){

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String memoFileNameList = null;
   	 	String apptoken = null;
   	 	try {
   	 		PrintWriter out = response.getWriter();
   	 		memoFileNameList = new String(request.getParameter("memoFileNameList").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
		 	
		 	
			Gson gson = new Gson();
			if(StringUtil.isEmpty(memoFileNameList) || StringUtil.isEmpty(apptoken)){
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
				memoFileNameList  			= CodeUtil.decode(memoFileNameList, apptoken);
				
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			String[] memoFileName = memoFileNameList.split(",");
			for(String memoEntityName : memoFileName){
				MemoDB memoQuery = new MemoDB();
				memoQuery.setMemoFileName(memoEntityName);
				appCenterHttpService.deleteMyMemo(memoQuery);
			}
	        
	        ResultEntity<String> result = new ResultEntity<String>(1, "成功", null);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
   	 	
   	 	} catch (Exception e) {
	   	 	logger.error("应用中心接口getALLXtYwByUser err：");
			logger.error(e, e.fillInStackTrace());
	 	}
	
	}
	
	/**
	 * 提交个人备忘录类别
	 * @param userId 用户id
	 * @param memoCatalogNameList 备忘录类别集合
	 * @return
	 */
	public void submitMemoCatalogList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = null;
		String memoCatalogNameList = null;
		String memoCatalogColorList = null;
   	 	String apptoken = null;
   	 	try {
   	 		PrintWriter out = response.getWriter();
   	 		username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
   	 		memoCatalogNameList = new String(request.getParameter("memoCatalogNameList").getBytes("ISO8859-1"), "UTF-8");
   	 		memoCatalogColorList = new String(request.getParameter("memoCatalogColorList").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
		 	
		 	
			Gson gson = new Gson();
			if(StringUtil.isEmpty(username) || StringUtil.isEmpty(apptoken)
					|| StringUtil.isEmpty(memoCatalogNameList) || StringUtil.isEmpty(memoCatalogColorList)){
				ResultEntity<List<MemoCatalog>> result = new ResultEntity<List<MemoCatalog>>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			if(!ApptokenUtils.compare(username,apptoken)){
				ResultEntity<List<MemoCatalog>> result = new ResultEntity<List<MemoCatalog>>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				username  			= CodeUtil.decode(username, apptoken);
				memoCatalogNameList = CodeUtil.decode(memoCatalogNameList, apptoken);
				memoCatalogColorList= CodeUtil.decode(memoCatalogColorList, apptoken);
				
			} catch (Exception e) {
				ResultEntity<List<MemoCatalog>> result = new ResultEntity<List<MemoCatalog>>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			//逻辑开始
			appCenterHttpService.deleteAllmemoCatalogByUser(username);
			String[] catalogNameList = memoCatalogNameList.split(",");
			String[] catalogColorList = memoCatalogColorList.split(",");
			MemoCatalog memoCatalog;
			List<MemoCatalog> memoCatalogList = null;
			if(StringUtil.isEmpty(memoCatalogNameList)){
				appCenterHttpService.deleteAllmemoCatalogByUser(username);
				memoCatalogList = new ArrayList<MemoCatalog>();
				memoCatalogList = appCenterHttpService.getMemoCatalogList(username);
				ResultEntity<List<MemoCatalog>> result = new ResultEntity<List<MemoCatalog>>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			for(int i = 0; i < catalogNameList.length; i++){
				if(catalogNameList[i].equals("旅游") || catalogNameList[i].equals("个人") 
						|| catalogNameList[i].equals("生活") || catalogNameList[i].equals("工作")
						|| catalogNameList[i].equals("未标签"))	continue;
				memoCatalog = new MemoCatalog();
				memoCatalog.setCatalogColor(catalogColorList[i]);
				memoCatalog.setMemoCatalogName(catalogNameList[i]);
				memoCatalog.setUserName(username);
				memoCatalog.setSortNumber(i+6);
				appCenterHttpService.insertmemoCatalogByUser(memoCatalog);
			}
			
			memoCatalogList = new ArrayList<MemoCatalog>();
			memoCatalogList = appCenterHttpService.getMemoCatalogList(username);
	        //逻辑结束
	        ResultEntity<List<MemoCatalog>> result = new ResultEntity<List<MemoCatalog>>(1, "成功", memoCatalogList);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
   	 		
   	 	} catch (Exception e) {
	   	 	logger.error("应用中心接口getALLXtYwByUser err：");
			logger.error(e, e.fillInStackTrace());
	 	}
	}
	
	/**
	 * 获取备忘录类别列表
	 * @param userId 用户id
	 * @return
	 */
	public void getMemoCatalogList(){

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = null;
   	 	String apptoken = null;
   	 	try {
   	 		PrintWriter out = response.getWriter();
	   	 	username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
		 	
			Gson gson = new Gson();
			if(StringUtil.isEmpty(username) || StringUtil.isEmpty(apptoken)){
				ResultEntity<List<MemoCatalog>> result = new ResultEntity<List<MemoCatalog>>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			if(!ApptokenUtils.compare(username, apptoken)){
				ResultEntity<List<MemoCatalog>> result = new ResultEntity<List<MemoCatalog>>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				username  			= CodeUtil.decode(username, apptoken);
			} catch (Exception e) {
				ResultEntity<List<MemoCatalog>> result = new ResultEntity<List<MemoCatalog>>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			List<MemoCatalog> memoCatalogList = new ArrayList<MemoCatalog>();
			memoCatalogList = appCenterHttpService.getMemoCatalogList(username);
	        
	        ResultEntity<List<MemoCatalog>> result = new ResultEntity<List<MemoCatalog>>(1, "成功", memoCatalogList);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
   	 	
   	 	} catch (Exception e) {
	   	 	logger.error("应用中心接口getALLXtYwByUser err：");
			logger.error(e, e.fillInStackTrace());
	 	}
	
	}
	
	/**
	 * 获取备忘录列表
	 * @param userId
	 * @param start
	 * @param size
	 * @param title
	 * @param memoCatalogName
	 * @return
	 */
	public void getMyMemoList(){

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = null;
		int start; 
		String startStr = null;
		int size;
		String sizeStr = null; 
		String title = null;
		String memoCatalogName = null;
   	 	String apptoken = null;
   	 	try {
   	 		PrintWriter out = response.getWriter();
	   	 	username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	   	 	startStr = new String(request.getParameter("start").getBytes("ISO8859-1"), "UTF-8");
	   	 	sizeStr = new String(request.getParameter("size").getBytes("ISO8859-1"), "UTF-8");
	   	 	title = new String(request.getParameter("title").getBytes("ISO8859-1"), "UTF-8");
	   	 	memoCatalogName = new String(request.getParameter("memoCatalogName").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			Gson gson = new Gson();
			if(StringUtil.isEmpty(username) || StringUtil.isEmpty(apptoken)){
				ResultEntity<MemoListEntity> result = new ResultEntity<MemoListEntity>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			if(!ApptokenUtils.compare(username, apptoken)){
				ResultEntity<MemoListEntity> result = new ResultEntity<MemoListEntity>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				username  			= CodeUtil.decode(username, apptoken);
				startStr  			= CodeUtil.decode(startStr, apptoken);
				sizeStr  			= CodeUtil.decode(sizeStr, apptoken);
				title  				= CodeUtil.decode(title, apptoken);
				memoCatalogName  	= CodeUtil.decode(memoCatalogName, apptoken);
				
			} catch (Exception e) {
				ResultEntity<MemoListEntity> result = new ResultEntity<MemoListEntity>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			start = Integer.valueOf(startStr);
			size = Integer.valueOf(sizeStr);
			MemoDB memoQuery = new MemoDB();
			memoQuery.setToPage(start);
			memoQuery.setPerPageSize(size);
			memoQuery.setMemoTitle(title);
			memoQuery.setUserName(username);
			memoQuery.setMemoCatalogName(memoCatalogName);
			List<MemoDB> memoList = appCenterHttpService.getMyMemoList(memoQuery);
			for(int i = 0; i < memoList.size(); i++){
				if(appCenterHttpService.checkMemo(memoList.get(i))){
					memoList.get(i).setMemoContent(null);
					memoList.get(i).setMemoPath(getImageHost()+memoList.get(i).getMemoPath());
					
					appCenterHttpService.checkMemoPictureEntity(memoList.get(i).getMemoFileName());
				}
			}
			MemoListEntity entity = new MemoListEntity();
			entity.setMemoList(memoList);
			if(memoList == null || memoList.size() == 0 || memoList.size() < 10){
				entity.setOvered(true);
			}else{
				entity.setOvered(false);
			}
	        
	        ResultEntity<MemoListEntity> result = new ResultEntity<MemoListEntity>(1, "成功", entity);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
   	 	
   	 	} catch (Exception e) {
	   	 	logger.error("应用中心接口getALLXtYwByUser err：");
			logger.error(e, e.fillInStackTrace());
	 	}
	
	}
	
	
	public void uploadMemoPicture(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
        response.setContentType("text/html;charset=UTF-8");  
        FileItemFactory factory = new DiskFileItemFactory();  
        ServletFileUpload upload = new ServletFileUpload(factory);  
        List<FileItem> items = new ArrayList<FileItem>();  
        String apptoken = null;
        String filename = null;
        //String picturePath = null;
        Gson gson = new Gson();
        try {  
        	PrintWriter out = response.getWriter();
            items = upload.parseRequest(request);  
            // 得到所有的文件  
            Iterator<FileItem> it = items.iterator();  
            while (it.hasNext()) {  
                FileItem fItem = (FileItem) it.next();  
                if (fItem.isFormField()) { // 普通文本框的值  
                	String paramName = "";  
                    Object fValue = null;  
                	paramName = fItem.getFieldName();  
                    fValue = fItem.getString("UTF-8");  
                    if(paramName.equals("apptoken")){
                    	apptoken = fValue.toString();
                    }
                } else { // 获取上传文件的值  
                	if(!ApptokenUtils.compare(apptoken)){
                		  
                		ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
        				out.write(gson.toJson(result));
        				out.flush();
        				out.close();
                        
                	}
                	logger.error("进入备忘录else");
                    //String filename = paramName + ".jpg";
                    filename = fItem.getName();//路径  
                    if(!StringUtil.isEmpty(filename)) {
                    	
                    	InputStream is = fItem.getInputStream();
                    	byte[] content = null;
                        int size = is.available();
                        if(is != null && size != 0){
                        	content = Byte_File_Object.getBytesFromFile(is);
                        }
                        
                        String filePath = request.getSession().getServletContext().getRealPath("/") + "memoFile"; 
                		filePath = filePath.replace("\\", "/");
                		//picturePath = filePath  + filename;
                        File outFile = new File(filePath, filename);
            			if (!outFile.exists()) {
            				outFile.createNewFile();
            			}
            			FileOutputStream fileOutputStream = new FileOutputStream(outFile);
            			if(content != null){
            				fileOutputStream.write(content, 0, content.length);
            			}
            			is.close();
            			fileOutputStream.close();
                        
            			ResultEntity<String> result = new ResultEntity<String>(1, "成功", Config.getString("suploadPath") + "/memoFile/" +  filename);
        				out.write(gson.toJson(result));
        				out.flush();
        				out.close();
                        
                    }  
                }  
            }  
        } catch (Exception e) {  
            //e.printStackTrace(); 
        	logger.error("memo picture upload-exception:");  
            logger.error(e,e.fillInStackTrace());
        }  
        
		
	}
	
	
	
	public void uploadMemo(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String creatTime = null;
        String memoTitle = null;
        String memoCatalogId = null;
        String username = null;
        String contentFlag = null;
        String memoFileName = null;
        String picturePath = null;
        String apptoken  = null;
   	 	try {
   	 		PrintWriter out = response.getWriter();
			Gson gson = new Gson();
			
			try {  
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);  
				List<FileItem> items = upload.parseRequest(request);  
	            // 得到所有的文件  
	            Iterator<FileItem> it = items.iterator();  
	            while (it.hasNext()) {  
	            	try {
	            		FileItem fItem = (FileItem) it.next();  
	            		String paramName = "";  
	            		Object fValue = null;  
	            		paramName = fItem.getFieldName();  
	            		if (fItem.isFormField()) { // 普通文本框的值  
	            			paramName = fItem.getFieldName();  
	            			fValue = fItem.getString("UTF-8");  
	            			if(paramName.equals("createTime")){
	            				creatTime = fValue.toString();
	            			}else if(paramName.equals("memoTitle")){
	            				memoTitle = fValue.toString();
	            			}else if(paramName.equals("memoCatalogId")){
	            				memoCatalogId = fValue.toString();
	            			}else if(paramName.equals("username")){
	            				username = fValue.toString();
	            			}else if(paramName.equals("contentFlag")){
	            				contentFlag = fValue.toString();
	            			}else if(paramName.equals("picturePath")){
	            				picturePath = fValue.toString();
	            			}else if(paramName.equals("apptoken")){
	            				apptoken = fValue.toString();
	            			}
	            			//logger.error("------备忘录上传中上传的是文本不应该上传文本-----");
	            		} else { // 获取上传文件的值  
	            			logger.error("进入备忘录else");
		                	try {
			                	creatTime       = CodeUtil.decode(creatTime, apptoken);
			                	memoTitle       = CodeUtil.decode(memoTitle, apptoken);
			                	memoCatalogId   = CodeUtil.decode(memoCatalogId, apptoken);
			                	username        = CodeUtil.decode(username, apptoken);
			                	contentFlag     = CodeUtil.decode(contentFlag, apptoken);
			                	picturePath     = CodeUtil.decode(picturePath, apptoken);
		                	} catch (Exception e) {
		        				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
		        				out.write(gson.toJson(result));
		        				out.flush();
		        				out.close();
		        			}
	            			paramName = fItem.getFieldName();//userfile  
	            			fValue = fItem.getInputStream(); 
	            			String filename = fItem.getName();//路径  
	            			if(!StringUtil.isEmpty(filename)) {
	            				InputStream is = fItem.getInputStream();
	            				byte[] content = null;
	            				int size = is.available();
	            				if(is != null && size != 0){
	            					content = Byte_File_Object.getBytesFromFile(is);
	            				}
	            				memoFileName = filename;
	            				String filePath = request.getSession().getServletContext().getRealPath("/") + "memoFile"; 
	            				filePath = filePath.replace("\\", "/");
	            				File outFile;
	            				MemoPictureEntity pictureEnity;
	            				int count = 0;
	            				if(!StringUtil.isEmpty(picturePath)){
	            					String[] picturePathList = picturePath.split(",");
	            					if(picturePathList != null && picturePathList.length > 0){
	            						for(String picPath : picturePathList){
	            							outFile = new File(filePath, picPath);
	            							if (!outFile.exists()) {
	            								ResultEntity<String> result = new ResultEntity<String>(0, "图片未上传至服务器！", null);
	            								out.write(gson.toJson(result));
	            								out.flush();
	            								out.close();
	            							}else{
	            								pictureEnity = new MemoPictureEntity();
	            								pictureEnity.setMemoFileName(memoFileName);
	            								pictureEnity.setPictureContent(Byte_File_Object.getBytesFromFile(outFile));
	            								pictureEnity.setTitle(picPath);
	            								pictureEnity.setPicturePath("memoFile/"+picPath);
	            								count = appCenterHttpService.getMemoPicture(pictureEnity);
	            								if(count == 0){
	            									appCenterHttpService.insertMemoPicture(pictureEnity);
	            								}
	            							}
	            							
	            						}
	            					}
	            				}
	            				
	            				
	            				MemoDB memoDB = new MemoDB();
	            				memoDB.setCreateTime(creatTime);
	            				memoDB.setMemoFileName(filename);
	            				memoDB.setMemoContent(content);
	            				memoDB.setUserName(username);
	            				memoDB.setMemoTitle(memoTitle);
	            				memoDB.setMemoCatalogId(memoCatalogId);
	            				memoDB.setContentFlag(contentFlag);
	            				memoDB.setMemoPath("memoFile/"+filename);
	            				MemoDB memoDBQuery = new MemoDB();
	            				memoDBQuery.setMemoFileName(filename);
	            				PageList<MemoDB> memoList = appCenterHttpService.getMyMemoList(memoDBQuery);
	            				MemoDB memoEntity = memoList != null && memoList.size() > 0 ? memoList.get(0) : null;
	            				if(memoEntity == null){
	            					appCenterHttpService.insertMemo(memoDB);
	            				}else{
	            					appCenterHttpService.updateMemo(memoDB);
	            				}
	            				if((memoEntity != null && memoEntity.getMemoContent() != null)
	            						|| content != null){
	            					//备忘录文件夹不存在则创建文件
	            					filePath = filePath.replace("\\", "/");
	            					File newFile = new File(filePath);
	            					if (!newFile.exists()) {
	            						newFile.mkdir();
	            					}
	            					
	            					outFile = new File(filePath, filename);
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
	            						fileOutputStream.write(memoEntity.getMemoContent(), 0, memoEntity.getMemoContent().length);
	            					}
	            					fileOutputStream.close();
	            				}
	            			}  
	            		}  
						
					} catch (Exception e) {
						continue;
					}
	            }  
	            ResultEntity<String> result = new ResultEntity<String>(1, "成功", null);
	            out.write(gson.toJson(result));
	            out.flush();
	            out.close();
	        } catch (Exception e) {  
	        	logger.error("memo upload exception:");  
	            logger.error(e,e.fillInStackTrace());
	        } 
	        ResultEntity<String> result = new ResultEntity<String>(0, "上传产生异常", null);
            out.write(gson.toJson(result));
            out.flush();
            out.close();
   	 	
   	 	} catch (Exception e) {
	   	 	logger.error("应用中心接口getALLXtYwByUser err：");
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
	 *app端上传收藏方法 
	 * @param apptoken
	 * @param Favourite
	 * @param username
	 * @author yangbilin
	 * @exception
	 */	
	public void submitFavouritesData(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String filePath = request.getSession().getServletContext().getRealPath("/");
		try {
			PrintWriter out=response.getWriter();
			boolean res=false;
			String typeFolder="default";
			String apptoken =null;
			String userName =null;
			byte[] content = null;
			//String favouritesAttachmentSize = null;
			int sortVal=0;
			Gson gson = new Gson();
			String users="",sort="",avatar="",custom="",title="",images="",
			attachmentcon="",favouritesattachmentsort="",
			attachmentPath="";
			
			FileItemFactory factory = new DiskFileItemFactory();  
		    ServletFileUpload upload = new ServletFileUpload(factory);  
		    List<FileItem> items = new ArrayList<FileItem>();
		    items = upload.parseRequest(request);
		 // 得到所有的文件  
            Iterator<FileItem> it = items.iterator();  
            FavouritesEntity favour = new FavouritesEntity();
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
                    	users = fValue.toString();
                    }else if(paramName.equals("favouritesAvatar")){
                    	avatar = fValue.toString();
                    }else if(paramName.equals("favouritesCustom")){
                    	custom = fValue.toString();
                    }else if(paramName.equals("favouritetitle")){
                    	title = fValue.toString();
                    }else if(paramName.equals("favouritesContent")){
                    	attachmentcon = fValue.toString();
                    }else if(paramName.equals("favouritesattachmentsort")){
                    	favouritesattachmentsort = fValue.toString();
                    }else if(paramName.equals("favouritesSort")){
                    	sort = fValue.toString();
                    	
                    	if(!ApptokenUtils.compare(users, apptoken)){
                    		commonResult(2,"app_token error!",out,gson);
                    		return;
                    	}
                    	if(StringUtils.isNotBlank(avatar)){
                    		avatar=CodeUtil.decode(avatar, apptoken);
                    	}else{
                    		commonResult(0,"头像参数favouritesAvatar获取失败",out,gson);
                    		return;
                    	}
                    	if(StringUtils.isNotBlank(custom)){
                    		custom=CodeUtil.decode(custom, apptoken);
                    	}else{
                    		commonResult(0,"被收藏的人名参数favouritesCustom获取失败",out,gson);
                    		return;
                    	}
                    	if(StringUtils.isNotBlank(users)){
                    		userName=CodeUtil.decode(users, apptoken);
                    	}else{
                    		commonResult(0,"用户名参数获取失败",out,gson);
                    		return;
                    	}
                    	if(StringUtils.isNotBlank(sort)){
                    		sort=CodeUtil.decode(sort, apptoken);
                    		sortVal = Integer.parseInt(sort);
                    		switch (sortVal) {
                    		case 2://2图片
                    			typeFolder="images";
                    			break;
                    		case 3://3视频
                    			typeFolder="videos";
                    			break;
                    		case 5://5 附件
                    			typeFolder="files";
                    			break;
                    		}
                    	}else{
                    		commonResult(0,"收藏种类参数获取失败",out,gson);
                    		return;
                    	}
                    	if(StringUtils.isNotBlank(title)){
                    		title=CodeUtil.decode(title, apptoken);
                    	}else{
                    		commonResult(0,"加密标题参数失败",out,gson);
                    		return;
                    	}
                    	if(StringUtils.isNotBlank(favouritesattachmentsort)){
                    		favouritesattachmentsort=CodeUtil.decode(favouritesattachmentsort, apptoken);
                    	}else{
                    		commonResult(0,"收藏附件种类参数favouritesattachmentsort失败",out,gson);
                    		return;
                    	}
                    	if(StringUtils.isNotBlank(attachmentcon)){
                    		attachmentcon=StringUtils.trim(CodeUtil.decode(attachmentcon, apptoken));
                    	}else{
                    		commonResult(0,"加密上传内容参数失败",out,gson);
                    		return;
                    	}
                    }
                }else{
                	String newmd5str = "";
                	String filename = fItem.getName();//文件名称
                    if(StringUtils.isBlank(filename)){
                    	return;
                    }
                    content = fItem.get();
					if(content != null){
						/** 
						 *对传递进来的内容进行MD5判断，如果数据库中存在，则删除 
						 *之后再对改数据进行保存
						 */
						newmd5str=checkAndGetMd5Str(new String(content),userName,filePath);
//						favouritesAttachmentSize = fItem.getSize();
						String suffix = filename.substring(filename.lastIndexOf(".")+1,filename.length());
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
						String datestr = sdf.format(new Date());
						String newfilename =RandomStringUtils.randomAlphabetic(10)+"_"+datestr+"."+suffix;
						String relativePath="favouritesPicture/"+typeFolder;
						String absoluteFilePath = filePath+relativePath; 
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
						//is.close();
						
						favour.setMd5str(newmd5str);
						
						favour.setFavouriteattachmentsize(FileUntils.GetFileSize(outFile));
						favour.setFavouriteattachmentsort(suffix);
						favour.setFavouritecontent(relativePath+newfilename);
						attachmentPath = relativePath+"/"+newfilename;
	                 }
	              }
	            }
	            setFavouritesData(
	            		favour,
	            		userName,
	            		custom,
	            		sort,
	            		title,
	            		avatar,
	            		content,
	            		favouritesattachmentsort,
	            		attachmentPath
	            	);
	            favour.setFavouritecontent(attachmentcon);
	            if(sortVal==1||sortVal==4){
	            	/** 
	            	 *对传递进来的内容进行MD5判断，如果数据库中存在，则删除 
	            	 *之后再对改数据进行保存
	            	 */
	            	String md5str =checkAndGetMd5Str(attachmentcon,userName,null);
	            	favour.setMd5str(md5str);
	            	favour.setFavouriteimage(images);
	            }
	            favouritesService.saveOrUpdate(favour);
	            res=true;
                if(res){         	
    	            commonResult(1,"成功",out,gson);
    	        }else{
    	            commonResult(0,"失败",out,gson);
    	        }
    		}catch (NoSuchAlgorithmException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			logger.error("我的收藏上传失败  err：");
    			logger.error(e, e.fillInStackTrace());
    		}catch (FileUploadException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			logger.error("我的收藏上传失败  err：");
    			logger.error(e, e.fillInStackTrace());
    		} catch (IOException e) {
    			 e.printStackTrace(); 
    	         logger.error("我的收藏接口  err：");
    			 logger.error(e, e.fillInStackTrace());
    		} catch (Exception e) {
    			e.printStackTrace();
    			logger.error("我的收藏上传失败  err：");
    			logger.error(e, e.fillInStackTrace());
    		} 
	}
	
	/**
	 *向前端推送该用户收藏列表 
	* @author: yangbilin
	* @Title: getFavouritesListByUser
	* @Description: 返回json
	* @param     username、apptoken、start、size
	* @return void  
	* @throws
	 */
	public void getFavouritesListByUser(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = null;
		String startInit = null;
   	 	String sizeInit=null;
   	 	String startStr = null;
   	 	String sizeStr=null;

   	 	try {
			PrintWriter out = response.getWriter();
			String apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(StringUtils.isNotBlank(request.getParameter("username"))){
				username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
			}
			if(StringUtils.isNotBlank(request.getParameter("start"))){
				startInit = new String(request.getParameter("start").getBytes("ISO8859-1"), "UTF-8");
			}
			if(StringUtils.isNotBlank(request.getParameter("size"))){
				sizeInit = new String(request.getParameter("size").getBytes("ISO8859-1"), "UTF-8");
			}
			
	   	 	Gson gson = new Gson();
	   	 	FavouritesEntity query = new FavouritesEntity();
			if(StringUtils.isBlank(apptoken)||StringUtils.isBlank(username)){
				commonResult(0,"参数传值出错！",out,gson);
				return;
			}else if(StringUtils.isBlank(startInit)||StringUtils.isBlank(sizeInit)){
				commonResult(0,"页面参数传值出错！",out,gson);
				return;
			}
			if(!ApptokenUtils.compare(username, apptoken)){
				commonResult(2,"app_token error!",out,gson);
				return;
			}
			try {
				username = CodeUtil.decode(username, apptoken);
				startStr = CodeUtil.decode(startInit, apptoken);
				sizeStr  = CodeUtil.decode(sizeInit, apptoken);
			} catch (Exception e) {
				commonResult(0,"加密方式出错！",out,gson);
				return;
			}
			query.setToPage(Integer.valueOf(startStr));
			query.setPerPageSize(Integer.valueOf(sizeStr));
			query.setUserid(username);
			
			ListEntity<FavouritesEntity> entityList = new ListEntity<FavouritesEntity>();
			PageList<FavouritesEntity> list = favouritesService.getPageList(query);
			if(list!=null&&list.size()>0){
				int totalPage=list.getPaginator().getPages();
				if(Integer.valueOf(startStr)<totalPage){
					entityList.setOvered(true);
				}else{
					entityList.setOvered(false);
				}
				String attachmentPath = null;
				FileUntils fileUntils = new FileUntils();
				for (int i = 0; i < list.size(); i++) {
					attachmentPath = list.get(i).getAttachmentPath();
					if(!StringUtil.isEmpty(attachmentPath)){
						String filePath = 
							request.getSession().getServletContext().getRealPath("/") 
							+ 
							attachmentPath.substring(
									0,
									attachmentPath.lastIndexOf("/")+1
							); 
						filePath = filePath.replace("\\", "/");
						File file = new File(
								filePath,
								attachmentPath.substring(
										attachmentPath.lastIndexOf("/")+1,
										attachmentPath.length()
								)
						);
						if(!file.exists()){
							FileOutputStream fileOutputStream = new FileOutputStream(file);
							fileOutputStream.write(list.get(i).getAttachmentcon(), 0, list.get(i).getAttachmentcon().length);
							fileOutputStream.close();
						}
						list.get(i).setAttachmentPath(fileUntils.getImageHost() + list.get(i).getAttachmentPath());
					}
					list.get(i).setAttachmentcon(null);
				}
				entityList.setItemList(list);
				ResultEntity<ListEntity<FavouritesEntity>> result = new ResultEntity<ListEntity<FavouritesEntity>>(1, "成功", entityList);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}else{
				entityList.setOvered(true);
				entityList.setItemList(list);
				ResultEntity<ListEntity<FavouritesEntity>> result = new ResultEntity<ListEntity<FavouritesEntity>>(1, "成功", entityList);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *根据收藏id删除当前收藏的方法 
	 *@param favourid、apptoken
	 *@author yangbilin
	 */
	public void deleteFavouriteById(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String filePath = request.getSession().getServletContext().getRealPath("/");
   	 	String id=request.getParameter("favourid");
   	 	String favourid="";
   	 	try {
   	 		PrintWriter out = response.getWriter();
   	 	    
   	 		String apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
		 	
			Gson gson = new Gson();
			FavouritesEntity query = new FavouritesEntity();
			if(StringUtil.isEmpty(id) || StringUtil.isEmpty(apptoken)){
				commonResult(0,"参数传值出错！",out,gson);
				return;
			}
			if(!ApptokenUtils.compare(apptoken)){
				commonResult(2,"app_token error!",out,gson);
				return;
			}
			
			try {
				favourid  = CodeUtil.decode(id, apptoken);
			} catch (Exception e) {
				commonResult(0,"加密方式出错！",out,gson);
				return;
			}
			query.setFavourid(favourid);
			FavouritesEntity fsEntity=favouritesService.findById(query);
			if(fsEntity!=null){
				String sort = fsEntity.getFavouritesort();
				if(StringUtils.isNotBlank(sort)){
					//int sortInt = Integer.parseInt(sort);
					if(!StringUtil.isEmpty(fsEntity.getAttachmentPath())){
						new FileUntils().deletePic(filePath, fsEntity.getAttachmentPath());
					}
					favouritesService.remove(favourid);
					
//					commonResult(1,"成功！",out,gson);
					ResultEntity<String> result = new ResultEntity<String>(1,"成功！",null);
					out.write(gson.toJson(result));
					out.flush();
					out.close();
					return;
				}
			}else{
//				commonResult(0,"不存在该收藏信息！",out,gson);
				ResultEntity<String> result = new ResultEntity<String>(0,"不存在该收藏信息！",null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
   	 	} catch (Exception e) {
	   	 	logger.error("删除收藏deleteFavouriteById err：");
			logger.error(e, e.fillInStackTrace());
	 	}
	}
	
	private FavouritesEntity setFavouritesData(
			FavouritesEntity favour,
			String userName,
			String custom,
			String sort,
			String title,
			String avatar,
			byte[] content,
			String favouritesattachmentsort,
			String attachmentPath
			){
		favour.setUserid(userName);
		favour.setFavouritecustom(custom);
		favour.setFavouritesort(sort);
		favour.setFavouritetitle(title);
		favour.setFavouriteavatar(avatar);
		favour.setAttachmentcon(content);
		favour.setFavouriteattachmentsort(favouritesattachmentsort);
		favour.setAttachmentPath(attachmentPath);
		return favour;
	}
	
	/** 
	 *对传递进来的内容进行MD5判断，如果数据库中存在，则删除 
	 *之后再对改数据进行保存
	 *@param content
	 *@param userName
	 *@param filePath
	 *@author yangbilin
	 */
	private String  checkAndGetMd5Str(String content,String userName,String filePath){
		String md5str=null;
		try {
			md5str = MD5Util.EncoderByMd5(content);
			FavouritesEntity query = new FavouritesEntity(userName,md5str);
			List<FavouritesEntity> isExists = favouritesService.checkAndGetMd5Str(query);
			if(isExists!=null&&isExists.size()>0){
				for (FavouritesEntity favouritesEntity : isExists) {
					if(StringUtils.isNotBlank(filePath)&&StringUtils.isNotBlank(favouritesEntity.getAttachmentPath())){
						//new FileUntils().deletePic(filePath, favouritesEntity.getFavouritecontent());
						new FileUntils().deletePic(filePath, favouritesEntity.getAttachmentPath());
					}
					favouritesService.remove(favouritesEntity.getFavourid());
				}
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("我的收藏MD5验证错误  err：");
			logger.error(e, e.fillInStackTrace());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("我的收藏MD5验证编码错误  err：");
			logger.error(e, e.fillInStackTrace());
		}
		return md5str;
	}
	
	private void commonResult(int code,String msg,PrintWriter out,Gson gson){
		ResultEntity<List<FavouritesEntity>> result = new ResultEntity<List<FavouritesEntity>>(code,msg,null);
		out.write(gson.toJson(result));
		out.flush();
		out.close();
	}
	
	
	/**
	 * 应用中心
	* @author: zhangxu
	* @Title: getALLXtYwByUser 
	* @Description: 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void getALLXtYwByUser(){
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
	   	 	username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
		 	
		 	
			Gson gson = new Gson();
			List<BusinessSystemEntity> entityList = new ArrayList<BusinessSystemEntity>();
			BusinessSystemEntity entity = null;
			if(StringUtil.isEmpty(username) || StringUtil.isEmpty(apptoken)){
				ResultEntity<List<BusinessSystemEntity>> result = new ResultEntity<List<BusinessSystemEntity>>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			if(!ApptokenUtils.compare(username, apptoken)){
				ResultEntity<List<BusinessSystemEntity>> result = new ResultEntity<List<BusinessSystemEntity>>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				username  			= CodeUtil.decode(username, apptoken);
				
			} catch (Exception e) {
				ResultEntity<List<BusinessSystemEntity>> result = new ResultEntity<List<BusinessSystemEntity>>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			List<Business> xtyws = appCenterHttpService.getAllXtYwByUser(username);
			List<ServiceManager> services;
	        for (Business xtyw : xtyws) {
	        	entity = new BusinessSystemEntity();
	        	entity.id = StringUtil.isEmpty(xtyw.getClassId()) ? "" : xtyw.getClassId();
	        	entity.systemCode = StringUtil.isEmpty(xtyw.getClassXtbm()) ? "" : xtyw.getClassXtbm();
	        	entity.systemName = StringUtil.isEmpty(xtyw.getClassXtmc()) ? "" : xtyw.getClassXtmc();
	        	entity.procode = StringUtil.isEmpty(xtyw.getProcode()) ? "" : xtyw.getProcode();
	        	entity.otherFlag = StringUtil.isEmpty(xtyw.getOtherFlag()) ? "" : xtyw.getOtherFlag();
	        	
	        	services = appCenterHttpService.getAllServiceByUser(username, entity.systemCode);
	        	String url = getImageHost();
	        	List<ServiceEntity> serviceEntityList = new ArrayList<ServiceEntity>();
	        	ServiceEntity serviceEntity = null;
	            for (ServiceManager sm : services){
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
					serviceEntity.isCommon    = StringUtil.isEmpty(sm.getIscommon()) ? "" : sm.getIscommon();
					serviceEntityList.add(serviceEntity);
	            }
	            entity.serviceEntityList = serviceEntityList;
	            entityList.add(entity);
	            if(infromation.equals("0")){
	            	logger.error("应用中心模块类型筛选entity："+entity);
	            }
	        }
	        
	        ResultEntity<List<BusinessSystemEntity>> result = new ResultEntity<List<BusinessSystemEntity>>(1, "成功", entityList);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
   	 	
   	 	} catch (Exception e) {
	   	 	logger.error("应用中心接口getALLXtYwByUser err：");
			logger.error(e, e.fillInStackTrace());
	 	}
	}
	
	/**
	* 服务搜索
	*/
	public void getSearchedServiceList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = null;
		String fwmc = null;
		String apptoken = null;
   	 	try {
   	 		PrintWriter out = response.getWriter();
	   	 	username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	   	    fwmc = new String(request.getParameter("fwmc").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
		 	
			Gson gson = new Gson();
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				username  			= CodeUtil.decode(username, apptoken);
				fwmc  			= CodeUtil.decode(fwmc, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			List<ServiceManager> services = appCenterHttpService.getSearchedServiceList(username,fwmc);
        	String url = getImageHost();
        	List<ServiceEntity> serviceEntityList = new ArrayList<ServiceEntity>();
        	ServiceEntity serviceEntity = null;
            for (ServiceManager sm : services){
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
				serviceEntity.isCommon    = StringUtil.isEmpty(sm.getIscommon()) ? "" : sm.getIscommon();
				serviceEntityList.add(serviceEntity);
            }
			
			
 	        ResultEntity<List<ServiceEntity>> result = new ResultEntity<List<ServiceEntity>>(1, "成功",serviceEntityList);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
   	 	
   	 	} catch (Exception e) {
	   	 	e.printStackTrace();
	 	}
	}

	
	public void setVisitService(IVisitService visitService) {
		this.visitService = visitService;
	}

	public IVisitService getVisitService() {
		return visitService;
	}
	
	
	public static String MD5(byte[] s)
    {
        //16进制字符
        char hexDigits[] =
        { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
                'e', 'f' };
        try
        {
            byte[] strTemp = s;
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            //移位 输出字符串
            for (int i = 0; i < j; i++)
            {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        }
        catch (Exception e)
        {
            return null;
        }
}
	
	public static void main(String[] args) {	
        //得到文件长度
        File file = new File("E://9.png");
        byte[] b = new byte[(int) file.length()];    
        try
        {
            InputStream in = new FileInputStream(file);
            in.read(b);
            System.out.println(AppCenterHttpAction.MD5(b));
            InputStream ian = new FileInputStream("e://11.jpg");
            ian.read(b);
            System.out.println(AppCenterHttpAction.MD5(b));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
	}
	
	/**
	* 新版失物招领列表接口
	* @author: liucb
	* @Title: newGetLossObjectList 
	* @Description: 获取失物招领列表
	* @param username 用户名
	* @param goodsName 物品名称
	* @param isover 是否已招领
	* @param start 页码
	* @param size 每页多少
	* @param apptoken 密钥
	* @return void    返回类型 
	* @throws
	*/
	public void newGetLossObjectList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = null;
		String goodsName = null;
		String isover = null;
		String start = null;
		String size = null;
		String apptoken = null;
   	 	try {
   	 		PrintWriter out = response.getWriter();
	   	 	username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	   	 	goodsName = new String(request.getParameter("goodsName").getBytes("ISO8859-1"), "UTF-8");
	   	 	isover = new String(request.getParameter("isover").getBytes("ISO8859-1"), "UTF-8");
	   	 	start = new String(request.getParameter("start").getBytes("ISO8859-1"), "UTF-8");
	   	 	size = new String(request.getParameter("size").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
		 	
			Gson gson = new Gson();
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<ListEntity<LossObjectEntity>> result = new ResultEntity<ListEntity<LossObjectEntity>>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				username  			= CodeUtil.decode(username, apptoken);
				goodsName  			= CodeUtil.decode(goodsName, apptoken);
				isover  			= CodeUtil.decode(isover, apptoken);
				start  				= CodeUtil.decode(start, apptoken);
				size  				= CodeUtil.decode(size, apptoken);
			} catch (Exception e) {
				ResultEntity<ListEntity<LossObjectEntity>> result = new ResultEntity<ListEntity<LossObjectEntity>>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			LossObjectEntity query = new LossObjectEntity();
			query.setUsername(username);
			query.setGoodsName(goodsName);
			query.setIspass("2");
			query.setIsover(isover);
			query.setToPage(Integer.valueOf(start));
			query.setPerPageSize(Integer.valueOf(size));
			List<LossObjectEntity> list = lossObjectService.getList(query);
			ListEntity<LossObjectEntity> resultList = new ListEntity<LossObjectEntity>();
			resultList.setItemList(list);
			if(list == null || list.size() < Integer.valueOf(size))	{
				resultList.setOvered(true);
			}else{
				resultList.setOvered(false);
			}
	        ResultEntity<ListEntity<LossObjectEntity>> result = new ResultEntity<ListEntity<LossObjectEntity>>(1, "成功", resultList);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
   	 	
   	 	} catch (Exception e) {
	   	 	e.printStackTrace();
	 	}
	}
	
	/**
	 * 新版失物招领登记接口
	 */
	public void newSubmitLosser(){
		
	}
	
  /**
	* 新版失物招领发布接口
	* @author: liucb
	* @Title: publishLossObject 
	* @Description: 发布失物招领
	* @param username 发布者
	* @param 物品名称
	* @param losePlace 丢失地点，点击寻物时，地点为丢失地点
	* @param pickPlace 捡到地点，点击寻主时，地点为捡到地点
	* @param content 内容
	* @param apptoken 密钥
	* @param flag 设定文件 
	* @return void 返回类型 
	* @throws
	 */
	@SuppressWarnings("unused")
	public void newPublishLoss(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		PrintWriter out = null;  
		
		String username = null;
		String goodsName = null;
		String place = null;
		String content = null;
		String qq = null;
		String flag = null;
		String telephone = null;
		String apptoken = null;
		String lossObjectId = null;
		Gson gson = new Gson();
		LossObjectEntity lossObjectEntity = new LossObjectEntity();
		LossObjectPictureEntity pictureEntity = new LossObjectPictureEntity();
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
	                if(paramName.equals("username")){
	                	username = fValue.toString();
	                }else if(paramName.equals("goodsName")){
	                	goodsName = fValue.toString();
	                }else if(paramName.equals("place")){
	                	place = fValue.toString();
	                }else if(paramName.equals("content")){
	                	content = fValue.toString();
	                }else if(paramName.equals("flag")){
	                	flag = fValue.toString();
	                }else if(paramName.equals("qq")){
	                	qq = fValue.toString();
	                }else if(paramName.equals("telephone")){
	                	telephone = fValue.toString();
	                }else if(paramName.equals("apptoken")){
	                	apptoken = fValue.toString();
	                }
	                
	                if(!StringUtil.isEmpty(username)
	                		&& !StringUtil.isEmpty(goodsName)
	                		&& !StringUtil.isEmpty(place)
	                		&& !StringUtil.isEmpty(qq)
	                		&& !StringUtil.isEmpty(flag)
	                		&& !StringUtil.isEmpty(telephone)
	                		&& !StringUtil.isEmpty(apptoken)){
	                	username       	= CodeUtil.decode(username, apptoken);
	                	goodsName       = CodeUtil.decode(goodsName, apptoken);
	                	place           = CodeUtil.decode(place, apptoken);
	                	content         = CodeUtil.decode(content, apptoken);
	                	qq       		= CodeUtil.decode(qq, apptoken);
	                	telephone       = CodeUtil.decode(telephone, apptoken);
	                	flag            = CodeUtil.decode(flag, apptoken);
	                	
	                	lossObjectEntity.setUsername(username);
	                	lossObjectEntity.setGoodsName(goodsName);
	                	lossObjectEntity.setTitle(goodsName);
	                	lossObjectEntity.setPlace(place);
	                	lossObjectEntity.setContent(content);
	                	lossObjectEntity.setQq(qq);
	                	lossObjectEntity.setTelephone(telephone);
	                	lossObjectEntity.setFlag(flag);
	                	
	                	lossObjectEntity.setIspass("2");
	                	lossObjectEntity.setIsover("0");
	                	
	                	LoginModel model = new LoginModel();
	                	model.setYhm(username);
	        			User user = loginService.cxYhxx(model);
	        			lossObjectEntity.setName(user.getXm());
	        			lossObjectService.newInsert(lossObjectEntity);
	        			lossObjectId = lossObjectEntity.getId();
	                }
	                	
	            } else { // 获取上传文件的值  
		            	logger.error("进入建议反馈图片上传方法");
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
		                    pictureEntity = new LossObjectPictureEntity();
		                    pictureEntity.setLossObjectId(lossObjectId);
		                    pictureEntity.setPictureContent(fileContent);
		                    pictureEntity.setTitle(filename);
		                    
		                    String filePath = request.getSession().getServletContext().getRealPath("/") + "lossObjectPicture"; 
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
		        				fileOutputStream.write(fileContent, 0, fileContent.length);
		        			}else{
		        				fileOutputStream.write(pictureEntity.getPictureContent(), 0, pictureEntity.getPictureContent().length);
		        			}
		        			fileOutputStream.close();
		        			is.close();
		        			String url = BaseHolder.getPropertiesValue("suploadPath");
		        			pictureEntity.setPicturePath(url+"lossObjectPicture/"+filename);
		        			lossObjectService.insertLossObjectPicture(pictureEntity);
		                    
		                }  
		            }  
		        }  
		        ResultEntity<String> result = new ResultEntity<String>(1, "发布成功", "发布成功");
		        out.write(gson.toJson(result));
		        out.flush();
		        out.close();
   	 	} catch (Exception e) {
   	 		e.printStackTrace(); 
            logger.error("memo upload------: exception");  
            ResultEntity<String> result = new ResultEntity<String>(0, "操作失败", "操作失败");
            out.write(gson.toJson(result));
            out.flush();
            out.close();
         // 数据处理  
	 	}
	}
	
  /**
	* 寻物启事，认领成功反馈，发布人积分+3，捡到人积分+5
	* @author: liucb
	* @param username 发布寻物启事者
	* @param pickedUsername 捡到者 账号
	* */
	public void newUpdateYhbSource(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = null;
		String lossuser = null;//捡到人或者丢失人
		String lossId = null;
		String apptoken = null;
		Gson gson = new Gson();
		
		try{
			PrintWriter out = response.getWriter();
	   	 	username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	   	    lossId   = new String(request.getParameter("lossId").getBytes("ISO8859-1"), "UTF-8");
	   	    lossuser = new String(request.getParameter("lossuser").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<ListEntity<LossObjectEntity>> result = new ResultEntity<ListEntity<LossObjectEntity>>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				username = CodeUtil.decode(username, apptoken);
				lossuser = CodeUtil.decode(lossuser, apptoken);
				lossId = CodeUtil.decode(lossId, apptoken);
			} catch (Exception e) {
				ResultEntity<ListEntity<LossObjectEntity>> result = new ResultEntity<ListEntity<LossObjectEntity>>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		
			//根据lossId获取失物详情
			LossObjectEntity query = new LossObjectEntity();
			query.setId(lossId);
			LossObjectEntity lossObjectEntity = lossObjectService.getOne(query);
			ResultEntity<String> result = null;
			
			if(lossObjectEntity!=null&&lossObjectEntity.getFlag()!=null){
				//招领成功+8积分
				if("0".equals(lossObjectEntity.getFlag())){
					//发布寻物启事者积分+3
					int sources=yhglService.sourceByZgh(username);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("userId", username);
					params.put("source", sources+3);
					yhglService.updateYhbSource(params);
					
					//捡到者积分+5
					Map<String, Object> params2 = new HashMap<String, Object>();
					params2.put("xm", lossuser);
					yhglService.updateYhbSourceWithXm(params2);
					
					result = new ResultEntity<String>(1,"提交成功，积分增加8分", "提交成功，积分增加3分");
				}else if("1".equals(lossObjectEntity.getFlag())){
					int sources=yhglService.sourceByZgh(username);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("userId", username);
					params.put("source", sources+8);
					yhglService.updateYhbSource(params);
					
					result = new ResultEntity<String>(1,"提交成功，积分增加8分", "提交成功，积分增加8分");
				}
				
				query.setLossuser(lossuser);//丢失者或者捡到者姓名
				query.setIsover("1");
				lossObjectService.update(query);//修改丢失者或者捡到者姓名
			}
			
		    
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
   }

	

	
}
