package com.zfsoft.mobile.classCommunity.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import com.zfsoft.mobile.classCommunity.entity.ClassEntity;
import com.zfsoft.mobile.classCommunity.entity.DynamicCommentEntity;
import com.zfsoft.mobile.classCommunity.entity.DynamicCommentReplyEntity;
import com.zfsoft.mobile.classCommunity.entity.DynamicEntity;
import com.zfsoft.mobile.classCommunity.entity.DynamicFileEntityForInsert;
import com.zfsoft.mobile.classCommunity.query.ClassEntityQuery;
import com.zfsoft.mobile.classCommunity.query.DynamicEntityQuery;
import com.zfsoft.mobile.classCommunity.service.IDynamicService;
import com.zfsoft.mobile.servlet.entity.ListEntity;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;

/**
 * 帖子action
 * @author H110MF
 *
 */
public class DynamicAction extends HrmAction{
	private IDynamicService dynamicService;
	private ClassEntityQuery classEntityQuery = new ClassEntityQuery();
	private DynamicEntityQuery dynamicEntityQuery = new DynamicEntityQuery();
	

	//获取班级列表数据
	public void getClassList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String username=null;
		String className = null;
		String start = null;
		String size = null;
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
			className = new String(request.getParameter("className").getBytes("ISO8859-1"), "UTF-8");
			start = new String(request.getParameter("start").getBytes("ISO8859-1"), "UTF-8");
			size = new String(request.getParameter("size").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				username     = CodeUtil.decode(username, apptoken);
				className    = CodeUtil.decode(className, apptoken);
				start        = CodeUtil.decode(start, apptoken);
				size         = CodeUtil.decode(size, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		
			classEntityQuery.setUsername(username);
			classEntityQuery.setName(className);
			classEntityQuery.setToPage(Integer.valueOf(start));
			classEntityQuery.setPerPageSize(Integer.valueOf(size));
			
			
			//查询出我已经加入的班级圈子
			List<ClassEntity> list = dynamicService.getClassList(classEntityQuery);
			
			if(list == null){
				list = new ArrayList<ClassEntity>();
			}
			/**
			 * 统计每个班级圈子的成员总数和帖子总数
			 */
			int countMember = 0;
			int countDynamic = 0;
			if(list.size() > 0){
				for (int i = 0; i < list.size(); i++) {
					if(list.get(i)!=null){
						list.get(i).setType("1");
						countMember = dynamicService.getClassMemberCount(list.get(i).getId());  
						list.get(i).setMemberCount(countMember+"");
						countDynamic =  dynamicService.getClassDynamicCount(list.get(i).getId());
						list.get(i).setDynamicCount(countDynamic+"");
					}
				}
			}
			
			
			//查询出可加入的圈子
			List<ClassEntity> list2 = dynamicService.getMyWantClassList(classEntityQuery); 
			if(list2 == null){
				list2 = new ArrayList<ClassEntity>();
			}
			if(list2.size()>0){
				for (int i = 0; i < list2.size(); i++) {
					if(list2.get(i)!=null){
						list2.get(i).setType("0");
					}
				}
			}
			
			//如果list2的size大于5，随机取5个
			if(list2.size()>5){
				List<ClassEntity> list3 = getRandomList(list2, 5);
				list.addAll(list3);
			}else{
				list.addAll(list2);
			}
			
			ListEntity<ClassEntity> resultList = new ListEntity<ClassEntity>();
			resultList.setItemList(list);
			if(list == null || list.size() < Integer.valueOf(size))	{
				resultList.setOvered(true);
			}else{
				resultList.setOvered(false);
			}
			
	        ResultEntity<ListEntity<ClassEntity>> result = new ResultEntity<ListEntity<ClassEntity>>(1, "成功", resultList);
			System.out.println(gson.toJson(result));
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
		}catch(Exception ex){
			ResultEntity<String> result = new ResultEntity<String>(0, "失败", "后台异常，获取数据失败");
			out.write(gson.toJson(result));
			out.flush();
			out.close();
			ex.printStackTrace();
		}
	}
	
	
	//获取帖子列表数据
	public void getDynamicList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String username=null;
		String classId = null;
		String start = null;
		String size = null;
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
			classId = new String(request.getParameter("classId").getBytes("ISO8859-1"), "UTF-8");
			start = new String(request.getParameter("start").getBytes("ISO8859-1"), "UTF-8");
			size = new String(request.getParameter("size").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				username     = CodeUtil.decode(username, apptoken);
				classId    = CodeUtil.decode(classId, apptoken);
				start        = CodeUtil.decode(start, apptoken);
				size         = CodeUtil.decode(size, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		
			dynamicEntityQuery.setClassId(classId);
			dynamicEntityQuery.setToPage(Integer.valueOf(start));
			dynamicEntityQuery.setPerPageSize(Integer.valueOf(size));
			
            List<DynamicEntity> list = dynamicService.getDynamicPageList(dynamicEntityQuery);
            
            Map<String,String> params = new HashMap<String,String>();
            //判断当前用户是否点赞过列表中的帖子
            if(list!=null && list.size()>0){
            	for (int i = 0; i < list.size(); i++) {
            		if(list.get(i)!= null){
            			params.put("username", username);
            			params.put("dynamicId", list.get(i).getId());
            			int rows = dynamicService.getUserPraiseRecordCount(params);
            			if(rows > 0){
            				list.get(i).setHavePraise("1");
            			}else{
            				list.get(i).setHavePraise("0");
            			}
            		}
				}
            }
			
			ListEntity<DynamicEntity> resultList = new ListEntity<DynamicEntity>();
			resultList.setItemList(list);
			if(list == null || list.size() < Integer.valueOf(size))	{
				resultList.setOvered(true);
			}else{
				resultList.setOvered(false);
			}
			
	        ResultEntity<ListEntity<DynamicEntity>> result = new ResultEntity<ListEntity<DynamicEntity>>(1, "成功", resultList);
			System.out.println(gson.toJson(result));
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
		}catch(Exception ex){
			ResultEntity<String> result = new ResultEntity<String>(0, "失败", "后台异常，获取数据失败");
			out.write(gson.toJson(result));
			out.flush();
			out.close();
			ex.printStackTrace();
		}
	}
	
	//获取帖子评论列表数据
	public void getDynamicCommentList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String dynamicId=null;
		String apptoken=null;
		
		Gson gson = new Gson();
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try{
			dynamicId = new String(request.getParameter("dynamicId").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				dynamicId     = CodeUtil.decode(dynamicId, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		
			List<DynamicCommentEntity> comments = dynamicService.getCommentList(dynamicId);
			if(comments != null){
				for (int j = 0; j < comments.size(); j++) {
					if(comments.get(j)!=null){
						String commentId = comments.get(j).getId();
						List<DynamicCommentReplyEntity> replys = dynamicService.getReplyList(commentId);
						if(replys == null){
							replys = new ArrayList<DynamicCommentReplyEntity>();
						}
						comments.get(j).setReplyList(replys);
					}
				}							
			}else{
				comments = new ArrayList<DynamicCommentEntity>();
			}
			
	        ResultEntity<List<DynamicCommentEntity> > result = new ResultEntity<List<DynamicCommentEntity>>(1, "成功", comments);
			System.out.println(gson.toJson(result));
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
		}catch(Exception ex){
			ResultEntity<String> result = new ResultEntity<String>(0, "失败", "后台异常，获取数据失败");
			out.write(gson.toJson(result));
			out.flush();
			out.close();
			ex.printStackTrace();
		}
	}
	
	/**
	 * 新增帖子
	 */
	public void insertDynamic(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		PrintWriter out = null;  
		
		String username = null;
		String name = null;
		String content = null;
		String type = "0";  //0不是,1是签到二维码
		String createTime = null; 
		
		String apptoken = null;
		
		String dynamicId = null;
		
		String classId = null;
		
		Gson gson = new Gson();
		DynamicEntity dynamicEntity = new DynamicEntity();
		DynamicFileEntityForInsert pictureEntity = new DynamicFileEntityForInsert();
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
	                }else if(paramName.equals("name")){
	                	name = fValue.toString();
	                }else if(paramName.equals("content")){
	                	content = fValue.toString();
	                }else if(paramName.equals("type")){
	                	type = fValue.toString();
	                }else if(paramName.equals("classId")){
	                	classId = fValue.toString();
	                }else if(paramName.equals("apptoken")){
	                	apptoken = fValue.toString();
	                	
	                	if(!StringUtil.isEmpty(username)
	                			&& !StringUtil.isEmpty(name)
	                			&& !StringUtil.isEmpty(content)
	                			&& !StringUtil.isEmpty(type)
	                			&& !StringUtil.isEmpty(apptoken)){
	                		username    = CodeUtil.decode(username, apptoken);
	                		name       	= CodeUtil.decode(name, apptoken);
	                		content     = CodeUtil.decode(content, apptoken);
	                		type        = CodeUtil.decode(type, apptoken);
	                		classId        = CodeUtil.decode(classId, apptoken);
	                		
	                		dynamicEntity.setPublisherId(username);
	                		dynamicEntity.setPublisherName(name);
	                		dynamicEntity.setContent(content);
	                		dynamicEntity.setType(type);
	                		dynamicEntity.setPraiseCount("0");
	                		dynamicEntity.setClassId(classId);
	                		createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	                		dynamicEntity.setCreateTime(createTime);
	                		
	                		
	                		//插入数据
	                		dynamicService.insertDynamic(dynamicEntity);
	                		dynamicId = dynamicEntity.getId();
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
		                    pictureEntity = new DynamicFileEntityForInsert();
		                    pictureEntity.setDynamicId(dynamicId);
		                    pictureEntity.setFileContent(fileContent);
		                    pictureEntity.setFileName(filename);
		                    
		                    String filePath = request.getSession().getServletContext().getRealPath("/") + "classCommunityDynamic"; 
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
		        				fileOutputStream.write(pictureEntity.getFileContent(), 0, pictureEntity.getFileContent().length);
		        			}
		        			fileOutputStream.close();
		        			is.close();
		        			String url = BaseHolder.getPropertiesValue("suploadPath");
		        			pictureEntity.setFilePath(url+"classCommunityDynamic/"+filename);
		        			pictureEntity.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		        			dynamicService.insertDynamicPicture(pictureEntity);
		                }  
		            }  
		        }
		        
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
	
	//新增评论
	public void insertComment(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String publisherId=null;
		String publisherName = null;
		String content = null;
		String dynamicId = null;
		String apptoken = null;
		
		Gson gson = new Gson();
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try{
			publisherId = new String(request.getParameter("publisherId").getBytes("ISO8859-1"), "UTF-8");
			publisherName = new String(request.getParameter("publisherName").getBytes("ISO8859-1"), "UTF-8");
			content = new String(request.getParameter("content").getBytes("ISO8859-1"), "UTF-8");
			dynamicId = new String(request.getParameter("dynamicId").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				publisherId     = CodeUtil.decode(publisherId, apptoken);
				publisherName   = CodeUtil.decode(publisherName, apptoken);
				content         = CodeUtil.decode(content, apptoken);
				dynamicId       = CodeUtil.decode(dynamicId, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		
			DynamicCommentEntity dynamicCommentEntity = new DynamicCommentEntity();
			dynamicCommentEntity.setPublisherId(publisherId);
			dynamicCommentEntity.setPublisherName(publisherName);
			dynamicCommentEntity.setContent(content);
			dynamicCommentEntity.setDynamicId(dynamicId);
			dynamicCommentEntity.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			
			dynamicService.insertDynamicComment(dynamicCommentEntity);
			
	        ResultEntity<String> result = new ResultEntity<String>(1, "评论成功", "评论成功");
			
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
		}catch(Exception ex){
			ResultEntity<String> result = new ResultEntity<String>(0, "失败", "后台异常，评论失败");
			out.write(gson.toJson(result));
			out.flush();
			out.close();
			ex.printStackTrace();
		}
	}
	
	//新增回复
	public void insertCommentReply(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String publisherId=null;
		String publisherName = null;
		String content = null;
		String commentId = null;
		String toPersonId = null;
		String toPersonName = null;
		String apptoken = null;
		
		Gson gson = new Gson();
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try{
			publisherId = new String(request.getParameter("publisherId").getBytes("ISO8859-1"), "UTF-8");
			publisherName = new String(request.getParameter("publisherName").getBytes("ISO8859-1"), "UTF-8");
			content = new String(request.getParameter("content").getBytes("ISO8859-1"), "UTF-8");
			commentId = new String(request.getParameter("commentId").getBytes("ISO8859-1"), "UTF-8");
			toPersonId = new String(request.getParameter("toPersonId").getBytes("ISO8859-1"), "UTF-8");
			toPersonName = new String(request.getParameter("toPersonName").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				publisherId     = CodeUtil.decode(publisherId, apptoken);
				publisherName   = CodeUtil.decode(publisherName, apptoken);
				content         = CodeUtil.decode(content, apptoken);
				commentId       = CodeUtil.decode(commentId, apptoken);
				toPersonId      = CodeUtil.decode(toPersonId, apptoken);
				toPersonName    = CodeUtil.decode(toPersonName, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		
			DynamicCommentReplyEntity dynamicCommentReplyEntity = new DynamicCommentReplyEntity();
			dynamicCommentReplyEntity.setPublisherId(publisherId);
			dynamicCommentReplyEntity.setPublisherName(publisherName);
			dynamicCommentReplyEntity.setContent(content);
			dynamicCommentReplyEntity.setCommentId(commentId);
			dynamicCommentReplyEntity.setToPersonId(toPersonId);
			dynamicCommentReplyEntity.setToPersonName(toPersonName);
			dynamicCommentReplyEntity.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			
			dynamicService.insertDynamicCommentReply(dynamicCommentReplyEntity);
			
	        ResultEntity<String> result = new ResultEntity<String>(1, "回复成功", "回复成功");
			
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
		}catch(Exception ex){
			ResultEntity<String> result = new ResultEntity<String>(0, "失败", "后台异常，回复失败");
			out.write(gson.toJson(result));
			out.flush();
			out.close();
			ex.printStackTrace();
		}
	}
	
	//点赞
	public void updatePraiseCount(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String username = null;
		String dynamicId=null;
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
			dynamicId = new String(request.getParameter("dynamicId").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				username     = CodeUtil.decode(username, apptoken);
				dynamicId     = CodeUtil.decode(dynamicId, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		
			//更新点赞count数
			dynamicService.updatePraiseCount(dynamicId);
			
			//添加点赞记录
			Map<String,String> params = new HashMap<String,String>();
			params.put("username", username);
			params.put("dynamicId", dynamicId);
			dynamicService.insertUserPraiseRecord(params);
			
	        ResultEntity<String> result = new ResultEntity<String>(1, "点赞成功", "点赞成功");
			
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
		}catch(Exception ex){
			ResultEntity<String> result = new ResultEntity<String>(0, "失败", "后台异常，点赞失败");
			out.write(gson.toJson(result));
			out.flush();
			out.close();
			ex.printStackTrace();
		}
	}
	
	public IDynamicService getDynamicService() {
		return dynamicService;
	}

	public void setDynamicService(IDynamicService dynamicService) {
		this.dynamicService = dynamicService;
	}
	
	/**
     * 从list中随机抽取若干不重复元素
     */
    public static List<ClassEntity> getRandomList(List<ClassEntity> paramList,int count){
        if(paramList.size()<count){
            return paramList;
        }
        Random random=new Random();
        List<Integer> tempList=new ArrayList<Integer>();
        List<ClassEntity> newList=new ArrayList<ClassEntity>();
        int temp=0;
        for(int i=0;i<count;i++){
            temp=random.nextInt(paramList.size());//将产生的随机数作为被抽list的索引
            if(!tempList.contains(temp)){
                tempList.add(temp);
                newList.add(paramList.get(temp));
            }else{
                i--;
            }   
        }
        return newList;
    }


	public ClassEntityQuery getClassEntityQuery() {
		return classEntityQuery;
	}


	public void setClassEntityQuery(ClassEntityQuery classEntityQuery) {
		this.classEntityQuery = classEntityQuery;
	}


	public DynamicEntityQuery getDynamicEntityQuery() {
		return dynamicEntityQuery;
	}


	public void setDynamicEntityQuery(DynamicEntityQuery dynamicEntityQuery) {
		this.dynamicEntityQuery = dynamicEntityQuery;
	}
}
