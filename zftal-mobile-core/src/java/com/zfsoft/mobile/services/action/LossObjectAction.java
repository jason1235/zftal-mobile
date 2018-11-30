package com.zfsoft.mobile.services.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
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
import com.zfsoft.common.log.User;
import com.zfsoft.dao.entities.LoginModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.util.Byte_File_Object;
import com.zfsoft.mobile.services.entity.LossObjectEntity;
import com.zfsoft.mobile.services.entity.LossObjectPictureEntity;
import com.zfsoft.mobile.services.service.ILossObjectService;
import com.zfsoft.mobile.servlet.entity.ListEntity;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.service.svcinterface.ILoginService;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;

public class LossObjectAction extends HrmAction {

	private ILossObjectService lossObjectService;
	private LossObjectEntity query = new LossObjectEntity();
	private ILoginService loginService;
	
	
	
	
	public ILoginService getLoginService() {
		return loginService;
	}



	public void setLoginService(ILoginService loginService) {
		this.loginService = loginService;
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
        List<FileItem> items = new ArrayList();  
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
            			entity.setIspass("1");
            			lossObjectService.insert(entity);
            			lossObjectId = entity.getId();
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
            e.printStackTrace(); 
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
   	 		e.printStackTrace();
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
			query.setIspass("4");
			List<LossObjectEntity> list = lossObjectService.getList(query);
			ListEntity<LossObjectEntity> resultList = new ListEntity<LossObjectEntity>();
			resultList.setItemList(list);
			if(list == null || list.size() < Integer.valueOf(size))	resultList.setOvered(true);
	        ResultEntity<ListEntity<LossObjectEntity>> result = new ResultEntity<ListEntity<LossObjectEntity>>(1, "成功", resultList);
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
		String username = request.getParameter("username");
		String title = request.getParameter("title");
		String place = request.getParameter("place");
		String content = request.getParameter("content");
		String flag = request.getParameter("flag");
		String apptoken = request.getParameter("apptoken");
   	 	try {
   	 		PrintWriter out = response.getWriter();
   	 		Gson gson = new Gson();
	   	 	if(StringUtil.isEmpty(username)){
				ResultEntity<String> result = new ResultEntity<String>(0, "参数username为空！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
	   	 	if(StringUtil.isEmpty(title)){
				ResultEntity<String> result = new ResultEntity<String>(0, "参数title为空！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
	   	 	if(StringUtil.isEmpty(place)){
				ResultEntity<String> result = new ResultEntity<String>(0, "参数place为空！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
	   	 	if(StringUtil.isEmpty(flag)){
				ResultEntity<String> result = new ResultEntity<String>(0, "参数flag为空！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
	   	 	if(StringUtil.isEmpty(apptoken)){
	   	 		ResultEntity<String> result = new ResultEntity<String>(0, "参数apptoken为空！", null);
	   	 		out.write(gson.toJson(result));
	   	 		out.flush();
	   	 		out.close();
	   	 		return;
	   	 	}
	   	 	username = new String(username.getBytes("ISO8859-1"), "UTF-8");
	   	 	title = new String(title.getBytes("ISO8859-1"), "UTF-8");
	   	 	place = new String(place.getBytes("ISO8859-1"), "UTF-8");
	   	 	content = new String(content.getBytes("ISO8859-1"), "UTF-8");
	   	 	flag = new String(flag.getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(apptoken) ? "" : apptoken;
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
//				content  			= CodeUtil.decode(content, apptoken);
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
   	 		e.printStackTrace();
	 	}
	
	}
	
	

	public LossObjectEntity getQuery() {
		return query;
	}

	public void setQuery(LossObjectEntity query) {
		this.query = query;
	}

	public void setLossObjectService(ILossObjectService lossObjectService) {
		this.lossObjectService = lossObjectService;
	}

	public ILossObjectService getLossObjectService() {
		return lossObjectService;
	}
	
	public String detail(){
		PageList<LossObjectEntity> list = lossObjectService.getList(query);
		getValueStack().set("entity", list.get(0));
		List<LossObjectPictureEntity> lossObjectPictureList = lossObjectService.getPictureList(query.getId());
		getValueStack().set("lossObjectPictureList", lossObjectPictureList);
		return "detail";
	}
	
	//获取列表
	public String list(){
		PageList<LossObjectEntity> list = lossObjectService.getList(query);
		getValueStack().set("list", list);
		return "list";
	}
	//审核是否通过
	public String control(){
		
		lossObjectService.update(query);
		this.setSuccessMessage("操作成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String delete(){
		query.setIspass("3");
		lossObjectService.update(query);
		//lossObjectService.delete(query);
		this.setSuccessMessage("操作成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	//审核是否通过此失物招领
	public String shenhe(){
		return "shenhe";
	}
	//招领人审核
	public String zlrsh(){
		PageList<LossObjectEntity> list = lossObjectService.getList(query);
		query = list.get(0);
		return "zlrsh";
	}
	
}
