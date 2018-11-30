package com.zfsoft.mobile.vote.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.util.Byte_File_Object;
import com.zfsoft.mobile.common.enums.FwbmEnum;
import com.zfsoft.mobile.common.enums.ProductEnum;
import com.zfsoft.mobile.common.enums.ServiceTypeEnum;
import com.zfsoft.mobile.common.utils.FileUntils;
import com.zfsoft.mobile.common.utils.ImageTagHtml;
import com.zfsoft.mobile.exampaper.query.ResultQuery;
import com.zfsoft.mobile.reportFix.entity.ReportFixEntity;
import com.zfsoft.mobile.reportFix.entity.ReportFixPicsEntity;
import com.zfsoft.mobile.services.dao.query.BusinessQuery;
import com.zfsoft.mobile.services.entity.AppServiceEntity;
import com.zfsoft.mobile.services.entity.TwoExamPaperEntity;
import com.zfsoft.mobile.servlet.appCenterHttp.action.AppCenterHttpAction;
import com.zfsoft.mobile.servlet.entity.ListEntity;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.mobile.vote.entity.QzEntity;
import com.zfsoft.mobile.vote.entity.VoteCountEntity;
import com.zfsoft.mobile.vote.entity.VoteGroupEntity;
import com.zfsoft.mobile.vote.entity.VoteMainEntity;
import com.zfsoft.mobile.vote.entity.VoteOptionEntity;
import com.zfsoft.mobile.vote.entity.VotePartInPersonEntity;
import com.zfsoft.mobile.vote.entity.VoteResultDetailEntity;
import com.zfsoft.mobile.vote.entity.VoteResultEntity;
import com.zfsoft.mobile.vote.query.VoteMainQuery;
import com.zfsoft.mobile.vote.service.IVoteService;
import com.zfsoft.service.svcinterface.IYhglService;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;

public class VoteAction extends HrmAction{
	private static Logger logger = Logger.getLogger(VoteAction.class);
	
	private String op;
	private IVoteService voteService;
	private IYhglService yhglService;
	private VoteMainQuery query = new VoteMainQuery();
	private VoteMainEntity model = new VoteMainEntity();
	private String jsonStr;            //参数串
	
	//跳转投票列表页面
	public void list(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String username=null;
		String start = null;
		String size = null;
		String apptoken = null;
		String mineVoteFlag = null;
		String voteIsDraft = null;
		
		Gson gson = new Gson();
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try{
			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
			start = new String(request.getParameter("start").getBytes("ISO8859-1"), "UTF-8");
			size = new String(request.getParameter("size").getBytes("ISO8859-1"), "UTF-8");
			mineVoteFlag = new String(request.getParameter("mineVoteFlag")==null? "".getBytes("ISO8859-1"):request.getParameter("mineVoteFlag").getBytes("ISO8859-1"), "UTF-8");
			voteIsDraft = new String(request.getParameter("voteIsDraft")==null? "".getBytes("ISO8859-1"):request.getParameter("voteIsDraft").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				username     = CodeUtil.decode(username, apptoken);
				start        = CodeUtil.decode(start, apptoken);
				size         = CodeUtil.decode(size, apptoken);
				mineVoteFlag = CodeUtil.decode(mineVoteFlag, apptoken);
				voteIsDraft  = CodeUtil.decode(voteIsDraft, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		
			query.setUserId(username);	
			query.setMineVoteFlag(mineVoteFlag);
			query.setVoteIsDraft(voteIsDraft);
			//query.setUserId("007007");
			query.setToPage(Integer.valueOf(start));
			query.setPerPageSize(Integer.valueOf(size));
			List<VoteMainEntity> list = voteService.getList(query);
			
			//设置每个投票参与人数
			Map<String,Object> params = new HashMap<String,Object>();
			for(int i = 0;i<list.size();i++){
				if(list.get(i)!=null){
					String voteId = list.get(i).getVoteId();
					params.put("voteId",voteId);
					long persons = voteService.getVoteResultTotalPartInPersons(params);
					list.get(i).setVoteTotalPersonsCount(String.valueOf(persons));
				}
			}
			
			ListEntity<VoteMainEntity> resultList = new ListEntity<VoteMainEntity>();
			resultList.setItemList(list);
			if(list == null || list.size() < Integer.valueOf(size))	{
				resultList.setOvered(true);
			}else{
				resultList.setOvered(false);
			}
	        ResultEntity<ListEntity<VoteMainEntity>> result = new ResultEntity<ListEntity<VoteMainEntity>>(1, "成功", resultList);
			
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
	
	//我参与的投票列表
	public void getMyPartInList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String username=null;
		String start = null;
		String size = null;
		String apptoken = null;
		String voteIsDraft = null;
		
		Gson gson = new Gson();
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try{
			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
			start = new String(request.getParameter("start").getBytes("ISO8859-1"), "UTF-8");
			size = new String(request.getParameter("size").getBytes("ISO8859-1"), "UTF-8");
			voteIsDraft = new String(request.getParameter("voteIsDraft")==null? "".getBytes("ISO8859-1"):request.getParameter("voteIsDraft").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				username     = CodeUtil.decode(username, apptoken);
				start        = CodeUtil.decode(start, apptoken);
				size         = CodeUtil.decode(size, apptoken);
				voteIsDraft  = CodeUtil.decode(voteIsDraft, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		
			query.setUserId(username);	
			query.setVoteIsDraft(voteIsDraft);
			//query.setUserId("007007");
			query.setToPage(Integer.valueOf(start));
			query.setPerPageSize(Integer.valueOf(size));
			List<VoteMainEntity> list = voteService.getMyPartInList(query);
			
			//设置每个投票参与人数
			Map<String,Object> params = new HashMap<String,Object>();
			for(int i = 0;i<list.size();i++){
				if(list.get(i)!=null){
					String voteId = list.get(i).getVoteId();
					params.put("voteId",voteId);
					long persons = voteService.getVoteResultTotalPartInPersons(params);
					list.get(i).setVoteTotalPersonsCount(String.valueOf(persons));
				}
			}
			
			ListEntity<VoteMainEntity> resultList = new ListEntity<VoteMainEntity>();
			resultList.setItemList(list);
			if(list == null || list.size() < Integer.valueOf(size))	{
				resultList.setOvered(true);
			}else{
				resultList.setOvered(false);
			}
	        ResultEntity<ListEntity<VoteMainEntity>> result = new ResultEntity<ListEntity<VoteMainEntity>>(1, "成功", resultList);
			
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
	 * 添加投票(包含图片类型)
	 */
	public void insertVoteNew(){
		/**
		 * 1获取投票主体
		 * 2添加投票主体
		 * 3获取投票选项
		 * 4添加投票选项
		 * 5设置参与人
		 */
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		PrintWriter out = null;  
		
		String username= null;
   	    String voteTitle = null;
   	    String voteDescription = null;
   	    String voteType = null;
   	    String voteIsMultiSelect = null;
   	    String voteMaxChoice = null;
    	String voteIsAnonymous = null;
     	String voteResultOnlySee = null;
   	    String voteMaxScore = null;
   	    String voteScoreMethod = null;
    	String voteEndDate = null;
    	String voteIsDraft = null;
    	String optionJsonStr = null;
    	String qzId = null;
		
		Gson gson = new Gson();
		VoteMainEntity voteMainEntity = new VoteMainEntity();
		String voteId=null;
		List<VoteOptionEntity> optionsForInsert = null;
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
	                }else if(paramName.equals("voteTitle")){
	                	voteTitle = fValue.toString();
	                }else if(paramName.equals("voteDescription")){
	                	voteDescription = fValue.toString();
	                }else if(paramName.equals("voteType")){
	                	voteType = fValue.toString();
	                }else if(paramName.equals("voteIsMultiSelect")){
	                	voteIsMultiSelect = fValue.toString();
	                }else if(paramName.equals("voteMaxChoice")){
	                	voteMaxChoice = fValue.toString();
	                }else if(paramName.equals("voteIsAnonymous")){
	                	voteIsAnonymous = fValue.toString();
	                }else if(paramName.equals("voteResultOnlySee")){
	                	voteResultOnlySee = fValue.toString();
	                }else if(paramName.equals("voteMaxScore")){
	                	voteMaxScore = fValue.toString();
	                }else if(paramName.equals("voteScoreMethod")){
	                	System.out.println(fValue.toString());
	                	voteScoreMethod = fValue.toString()==null? "":fValue.toString();
	                }else if(paramName.equals("voteEndDate")){
	                	voteEndDate = fValue.toString();
	                }else if(paramName.equals("voteIsDraft")){
	                	voteIsDraft = fValue.toString();
	                }else if(paramName.equals("optionJsonStr")){
	                	optionJsonStr = fValue.toString();
	                }else if(paramName.equals("qzId")){
	                	qzId = fValue.toString();
	                	
	                	voteMainEntity.setVoteTitle(voteTitle);
	                	voteMainEntity.setVoteDescription(voteDescription);
	                	voteMainEntity.setVoteType(voteType);
	                	voteMainEntity.setVoteIsMultiSelect(voteIsMultiSelect);
	                	voteMainEntity.setVoteMaxChoice(voteMaxChoice);
	                	voteMainEntity.setVoteIsAnonymous(voteIsAnonymous);
	                	voteMainEntity.setVoteResultOnlySee(voteResultOnlySee);
	                	voteMainEntity.setVoteEndDate(new Date(Long.valueOf(voteEndDate)));
	                	voteMainEntity.setVoteStartDate(new Date());
	                	voteMainEntity.setVoteMaxScore(voteMaxScore);
	                	voteMainEntity.setVoteIsDraft(voteIsDraft);
	                	voteMainEntity.setVoteOrganiserId(username);
	                	voteMainEntity.setVoteScoreMethod(voteScoreMethod);
	                	
	                	
	                	//根据用户账号获取姓名填充到voteOrganiserName
	                	voteMainEntity.setVoteOrganiserName(yhglService.xmByZgh(username)==null? "":yhglService.xmByZgh(username));
	                	
	                	voteService.insertVoteMain(voteMainEntity);
	                	voteId = voteMainEntity.getVoteId();
	                	
	                	//将选项json串解析为选项实体
	                	optionsForInsert = JSON.parseArray(optionJsonStr,VoteOptionEntity.class);
	                	
	                	
	                	//非图片在这里处理选项，图片在图片else语句中处理选项
                		for (int i = 0; i < optionsForInsert.size(); i++) {
                			VoteOptionEntity voteOptionEntity = optionsForInsert.get(i);
                			if(voteOptionEntity!=null){
                				//包含图片的话，不执行插入选项
                				if(voteOptionEntity.getPictureName()!=null&&!"".equals(voteOptionEntity.getPictureName())){
                					continue;
                				}
                				voteOptionEntity.setVoteMainId(voteId);
                				voteService.insertVoteOption(voteOptionEntity);
                			}
                		}
	                	
	                	//添加投票群组信息5B1E40F79CE30519E0538513470A528D
	                	Map<String,Object> qzParams = new HashMap<String,Object>();
	                	qzParams.put("voteMainId", voteId);
	                	qzParams.put("qzId", qzId);
	                	voteService.insertVoteGroup(qzParams);
	                }
	            	
	            } else { // 获取上传文件的值  
		            	logger.error("进入建议反馈图片上传方法");
		            	paramName = fItem.getFieldName();//userfile  
		                fValue = fItem.getInputStream(); 
		                String filename = fItem.getName();//路径  
		                if(!StringUtil.isEmpty(filename)) { 
		                	
		                	for (int i = 0; i < optionsForInsert.size(); i++) {
	                			VoteOptionEntity voteOptionEntity = optionsForInsert.get(i);
	                			if(voteOptionEntity!=null){
	                				if(filename.equals(voteOptionEntity.getPictureName())){
	        		                	InputStream is = fItem.getInputStream();  
	        		                    
	        		                    byte[] fileContent = null;
	        		                    int size = is.available();
	        		                    if(is != null && size != 0){
	        		                    	fileContent = Byte_File_Object.getBytesFromFile(is);
	        		                    }
	        		                    
	        		                    String filePath = request.getSession().getServletContext().getRealPath("/") + "voteOptionPics"; 
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
	        		        			if(voteTitle != null){
	        		        				fileOutputStream.write(fileContent, 0, fileContent.length);
	        		        			}else{
	        		        				fileOutputStream.write(voteOptionEntity.getPictureContent(), 0, voteOptionEntity.getPictureContent().length);
	        		        			}
	        		        			fileOutputStream.close();
	        		        			is.close();
	        		        			String url = BaseHolder.getPropertiesValue("suploadPath");
	        		        			voteOptionEntity.setPicturePath(url+"voteOptionPics/"+filename);
	        		        			voteOptionEntity.setPictureName(filename);
	        		        			voteOptionEntity.setPictureContent(fileContent);
	        		        			
	        		        			voteOptionEntity.setVoteMainId(voteId);
	        		        			voteService.insertVoteOption(voteOptionEntity);
	                				}
	                			}
	                		}
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
	 * 添加投票
	 */
	public void insertVote(){
		/**
		 * 1获取投票主体
		 * 2添加投票主体
		 * 3获取投票选项
		 * 4添加投票选项
		 * 5设置参与人
		 */
		//1513244443219
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		/*
		String voteTitle=null;               //标题
		String voteType=null;                //类型 0文字1图片2打分
		String voteIsMultiSelect=null;       //是否多选
		String voteIsAnonymous=null;         //是否匿名
		String voteResultOnlySee=null;       //结果仅发起人可见
		String voteEndDate=null;               //截止时间  时间戳
		//Date voteStartDate=null;             //发起时间
		String voteOrganiserId=null;         //发起人id
		String voteOrganiserName=null;       //发起人姓名 
		String voteMaxScore = null;
		String qzId = null;
		*/

		Gson gson = new Gson();
		PrintWriter out = null;
		VoteMainEntity voteMainEntity = new VoteMainEntity();
   	 	try {
   	 	    out = response.getWriter();
   	 		
   	 	    String username= request.getParameter("username");
	   	    String voteTitle = request.getParameter("voteTitle");
	   	    String voteDescription = request.getParameter("voteDescription");
	   	    String voteType = request.getParameter("voteType");
	   	    String voteIsMultiSelect = request.getParameter("voteIsMultiSelect");
	   	    String voteMaxChoice = request.getParameter("voteMaxChoice");
	    	String voteIsAnonymous = request.getParameter("voteIsAnonymous");
	     	String voteResultOnlySee = request.getParameter("voteResultOnlySee");
	   	    String voteMaxScore = request.getParameter("voteMaxScore");
	   	    String voteScoreMethod = request.getParameter("voteScoreMethod");
	    	String voteEndDate = request.getParameter("voteEndDate");
	    	String voteIsDraft = request.getParameter("voteIsDraft");
	    	String optionJsonStr = request.getParameter("optionJsonStr");
	    	String qzId = request.getParameter("qzId");
		 	/*
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
		 	
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
				voteTitle  			= CodeUtil.decode(voteTitle, apptoken);
				voteType  			= CodeUtil.decode(voteType, apptoken);
				voteIsMultiSelect  	= CodeUtil.decode(voteIsMultiSelect, apptoken);
				voteIsAnonymous  	= CodeUtil.decode(voteIsAnonymous, apptoken);
				voteResultOnlySee  	= CodeUtil.decode(voteResultOnlySee, apptoken);
				voteMaxScore  		= CodeUtil.decode(voteMaxScore, apptoken);
				voteEndDate  		= CodeUtil.decode(voteEndDate, apptoken);
			} catch (Exception e) {
				ResultEntity<ListEntity<LossObjectEntity>> result = new ResultEntity<ListEntity<LossObjectEntity>>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			*/
			
			voteMainEntity.setVoteTitle(voteTitle);
			voteMainEntity.setVoteDescription(voteDescription);
        	voteMainEntity.setVoteType(voteType);
        	voteMainEntity.setVoteIsMultiSelect(voteIsMultiSelect);
        	voteMainEntity.setVoteMaxChoice(voteMaxChoice);
        	voteMainEntity.setVoteIsAnonymous(voteIsAnonymous);
        	voteMainEntity.setVoteResultOnlySee(voteResultOnlySee);
        	voteMainEntity.setVoteEndDate(new Date(Long.valueOf(voteEndDate)));
        	voteMainEntity.setVoteStartDate(new Date());
        	voteMainEntity.setVoteMaxScore(voteMaxScore);
        	voteMainEntity.setVoteIsDraft(voteIsDraft);
        	voteMainEntity.setVoteOrganiserId(username);
        	voteMainEntity.setVoteScoreMethod(voteScoreMethod);
        	
        	
        	//根据用户账号获取姓名填充到voteOrganiserName
        	voteMainEntity.setVoteOrganiserName(yhglService.xmByZgh(username)==null? "":yhglService.xmByZgh(username));
        	
        	voteService.insertVoteMain(voteMainEntity);
        	String voteId = voteMainEntity.getVoteId();
			
        	List<VoteOptionEntity> optionsForInsert = JSON.parseArray(optionJsonStr,VoteOptionEntity.class);
        	for (int i = 0; i < optionsForInsert.size(); i++) {
        		VoteOptionEntity voteOptionEntity = optionsForInsert.get(i);
        		if(voteOptionEntity!=null){
        			voteOptionEntity.setVoteMainId(voteId);
        			voteService.insertVoteOption(voteOptionEntity);
        		}
			}
        	//添加投票群组信息5B1E40F79CE30519E0538513470A528D
        	Map<String,Object> qzParams = new HashMap<String,Object>();
        	qzParams.put("voteMainId", voteId);
        	qzParams.put("qzId", qzId);
        	voteService.insertVoteGroup(qzParams);
        	
        	if("1".equals(voteIsDraft)){
        		ResultEntity<String> result = new ResultEntity<String>(1, "保存成功", "保存成功");
        		out.write(gson.toJson(result));
        		out.flush();
        		out.close();
        	}else{
        		ResultEntity<String> result = new ResultEntity<String>(1, "发布成功", "发布成功");
        		out.write(gson.toJson(result));
        		out.flush();
        		out.close();
        	}
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
	 * 修改投票,草稿转为非草稿
	 */
	public void updateVote(){
		/**
		 * 1获取投票主体
		 * 2添加投票主体
		 * 3获取投票选项
		 * 4添加投票选项
		 * 5设置参与人
		 */
		//1513244443219
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		/*
		String voteTitle=null;               //标题
		String voteType=null;                //类型 0文字1图片2打分
		String voteIsMultiSelect=null;       //是否多选
		String voteIsAnonymous=null;         //是否匿名
		String voteResultOnlySee=null;       //结果仅发起人可见
		String voteEndDate=null;               //截止时间  时间戳
		//Date voteStartDate=null;             //发起时间
		String voteOrganiserId=null;         //发起人id
		String voteOrganiserName=null;       //发起人姓名 
		String voteMaxScore = null;
		String qzId = null;
		*/

		Gson gson = new Gson();
		PrintWriter out = null;
		VoteMainEntity voteMainEntity = new VoteMainEntity();
   	 	try {
   	 	    out = response.getWriter();
   	 		
   	 	    //String username= request.getParameter("username");
   	 	    String voteId = request.getParameter("voteId");
   	 	    String voteIsDraft = request.getParameter("voteIsDraft");
   	 	    /*
	   	    String voteTitle = request.getParameter("voteTitle");
	   	    String voteType = request.getParameter("voteType");
	   	    String voteIsMultiSelect = request.getParameter("voteIsMultiSelect");
	    	String voteIsAnonymous = request.getParameter("voteIsAnonymous");
	     	String voteResultOnlySee = request.getParameter("voteResultOnlySee");
	   	    String voteMaxScore = request.getParameter("voteMaxScore");
	    	String voteEndDate = request.getParameter("voteEndDate");
	    	String[] optionValues = request.getParameterValues("option");
	    	String qzId = request.getParameter("qzId");*/
			
	    	voteMainEntity.setVoteId(voteId);
	    	/*
	    	voteMainEntity.setVoteTitle(voteTitle);
        	voteMainEntity.setVoteType(voteType);
        	voteMainEntity.setVoteIsMultiSelect(voteIsMultiSelect);
        	voteMainEntity.setVoteIsAnonymous(voteIsAnonymous);
        	voteMainEntity.setVoteResultOnlySee(voteResultOnlySee);
        	voteMainEntity.setVoteEndDate(new Date(Long.valueOf(voteEndDate)));
        	voteMainEntity.setVoteStartDate(new Date());
        	voteMainEntity.setVoteMaxScore(voteMaxScore);
	    	voteMainEntity.setVoteOrganiserId(username);
        	*/
        	voteMainEntity.setVoteIsDraft(voteIsDraft);
        	
        	//根据用户账号获取姓名填充到voteOrganiserName
        	//voteMainEntity.setVoteOrganiserName(yhglService.xmByZgh(username)==null? "":yhglService.xmByZgh(username));
        	
        	voteService.updateVoteMain(voteMainEntity);
			
        	/*
        	//添加投票选项
        	for (int i = 0; i < optionValues.length; i++) {
				VoteOptionEntity voteOptionEntity = new VoteOptionEntity();
				voteOptionEntity.setVoteOptionDescription(optionValues[i]);
				voteOptionEntity.setVoteOptionSort(i+"");
				voteOptionEntity.setVoteMainId(voteId);
				
				voteService.insertVoteOption(voteOptionEntity);
			}
        	
        	//添加投票群组信息5B1E40F79CE30519E0538513470A528D
        	Map<String,Object> qzParams = new HashMap<String,Object>();
        	qzParams.put("voteMainId", voteId);
        	qzParams.put("qzId", qzId);
        	voteService.insertVoteGroup(qzParams);
        	*/
        	
	        ResultEntity<String> result = new ResultEntity<String>(1, "提交成功", "提交成功");
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
	
	//根据voteId获取投票的所有选项
	public void getOptionsByVoteId(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String username=null;
		String voteId = null;
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
			voteId = new String(request.getParameter("voteId").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				username     = CodeUtil.decode(username, apptoken);
				voteId        = CodeUtil.decode(voteId, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		    
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("voteMainId",voteId);
			List<VoteOptionEntity> list = voteService.getOptionsByVoteId(params);
	        ResultEntity<List<VoteOptionEntity>> result = new ResultEntity<List<VoteOptionEntity>>(1, "成功", list);
			
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
	
	
	//根据voteId获取投票详情
	public void getByVoteId(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String username=null;
		String voteId = null;
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
			voteId = new String(request.getParameter("voteId").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				username     = CodeUtil.decode(username, apptoken);
				voteId       = CodeUtil.decode(voteId, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		    
			//Map<String,Object> result = new HashMap<String, Object>();
			//根据voteId获取所有相关的投票选项
			Map<String,Object> voteIdparams = new HashMap<String, Object>();
			voteIdparams.put("voteId", voteId);
			VoteMainEntity voteMain = voteService.getVoteById(voteIdparams);
			//result.put("voteMain", voteMain);
			
			ResultEntity<?>  totalResult = null;
			
			//如果投票未结束,返回投票选项以供选择，如果投票已结束，返回统计数据
			if("进行中".equals(voteMain.getStatus())){
				//获取该项投票的所有选项
				Map<String,Object> params = new HashMap<String, Object>();
				params.put("voteMainId",voteId);
				List<VoteOptionEntity> list = voteService.getOptionsByVoteId(params);
				
				//查询选项投票情况
				Map<String,Object> checkedMap = new HashMap<String,Object>();
				checkedMap.put("voteuserid", username);
				for (int i = 0; i < list.size(); i++) {
					if(list.get(i)!=null){
						checkedMap.put("voteoptionid", list.get(i).getVoteOptionId());
						VoteResultEntity vre = voteService.getCheckedOptionByUserId(checkedMap);
						if(vre!=null){
							if(voteMain.getVoteType()!=null&&"1".equals(voteMain.getVoteType())){
								list.get(i).setVotedOption(vre.getVoteOptionScore());//打分类型将该字段设置为选项分数
							}else{
								list.get(i).setVotedOption("voted");//选项类型将该字段设置为voted
							}
						}
					}
				}
				
				totalResult = new ResultEntity<List<VoteOptionEntity>>(1, "成功", list);
				//result.put("voteOptions",list);
				
			}else if("已结束".equals(voteMain.getStatus())){
			   //获取所有选项的统计数据
				Map<String,Object> params = new HashMap<String, Object>();
				params.put("voteId",voteId);
				List<VoteCountEntity> list = voteService.getVoteResultCount(params);
				
				//保留两位小数
				for (int i = 0; i < list.size(); i++) {
					String percentage = list.get(i).getPercentage();
					DecimalFormat   df   = new DecimalFormat("#.0000");  
					percentage = df.format(Double.valueOf(percentage));
					list.get(i).setPercentage(percentage);
				}
				totalResult = new ResultEntity<List<VoteCountEntity>>(1, "成功", list);
				//result.put("voteResultCount",list);
			}
			
	        String jsonStr  = gson.toJson(totalResult);
	        out.write(jsonStr);
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
	 * 投票结果详情
	 */
	public void getVoteResultDetail(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String username=null;
		String voteId = null;
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
				voteId = new String(request.getParameter("voteId").getBytes("ISO8859-1"), "UTF-8");
			 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
				
				if(!ApptokenUtils.compare(apptoken)){
					ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
					out.write(gson.toJson(result));
					out.flush();
					out.close();
				}
				
				try {
					username     = CodeUtil.decode(username, apptoken);
					voteId       = CodeUtil.decode(voteId, apptoken);
				} catch (Exception e) {
					ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
					out.write(gson.toJson(result));
					out.flush();
					out.close();
				}
				
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("voteId", voteId);
				List<VoteResultDetailEntity> resultDetails = voteService.getVoteResultDetail(params);
				
				ResultEntity<List<VoteResultDetailEntity>> result = new ResultEntity<List<VoteResultDetailEntity>>(1, "成功", resultDetails);
				
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
	 * 投票投票群组内参与人员和未参与人员情况
	 */
	public void getVotePartInPersonDetail(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String username=null;
		String voteId = null;
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
				voteId = new String(request.getParameter("voteId").getBytes("ISO8859-1"), "UTF-8");
			 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
				
				if(!ApptokenUtils.compare(apptoken)){
					ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
					out.write(gson.toJson(result));
					out.flush();
					out.close();
				}
				
				try {
					username     = CodeUtil.decode(username, apptoken);
					voteId        = CodeUtil.decode(voteId, apptoken);
				} catch (Exception e) {
					ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
					out.write(gson.toJson(result));
					out.flush();
					out.close();
				}
				Map<String,Object> resultPersons = new HashMap<String,Object>();
				
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("voteId", voteId);
				//获取参与人员
				List<VotePartInPersonEntity> partIns = voteService.getVoteHavePartInPersons(params);
				resultPersons.put("partIns", partIns);
				
				
				//获取未参与人员
				List<VotePartInPersonEntity> notPartIns = voteService.getVoteHaveNotPartInPersons(params);
				resultPersons.put("notPartIns", notPartIns);
				
				
				ResultEntity<Map<String,Object>> result = new ResultEntity<Map<String,Object>>(1, "成功", resultPersons);
				
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
	 * 提交投票结果
	 */
	public void insertVoteResult(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();

		Gson gson = new Gson();
		PrintWriter out = null;
   	 	try {
   	 	    out = response.getWriter();
   	 		String username = request.getParameter("username");
   	 		String voteId = request.getParameter("voteId");
   	 	    String scoreVoteJsonStr= request.getParameter("scoreVoteJsonStr");
   	 	    if(StringUtil.isEmpty(scoreVoteJsonStr)){
	   	 	    ResultEntity<String> result = new ResultEntity<String>(1, "参数错误", "参数错误");
		        out.write(gson.toJson(result));
		        out.flush();
		        out.close();
		        return;
   	 	    }
   	 	    Map<String,Object> params = new HashMap<String,Object>();
   	 	    params.put("userId", username);
   	 	    params.put("voteId", voteId);
   	 	    int rows = voteService.checkHaveOrNotVote(params);
   	 	    
   	 	    if(rows>0){
   	 	        ResultEntity<String> result = new ResultEntity<String>(1, "您已参与过该投票，不能再次投票！", "您已参与过该投票，不能再次投票！");
   	 	        out.write(gson.toJson(result));
	 	    	out.flush();
	 	    	out.close();
   	 	    }else{
   	 	    	List<VoteResultEntity> resultsForInsert = JSON.parseArray(scoreVoteJsonStr,VoteResultEntity.class);
   	 	    	
   	 	    	for (int i = 0; i < resultsForInsert.size(); i++) {
   	 	    		VoteResultEntity voteRes = resultsForInsert.get(i);
   	 	    		if(voteRes!=null){
   	 	    			voteRes.setVoteOptionScore(voteRes.getVoteOptionScore()==null? "":voteRes.getVoteOptionScore()); 
   	 	    			voteRes.setVoteTime(new Date());
   	 	    			voteService.insertVoteResult(voteRes);
   	 	    		}
   	 	    	}
   	 	    	
   	 	    	ResultEntity<String> result = new ResultEntity<String>(1, "提交成功", "提交成功");
   	 	    	out.write(gson.toJson(result));
   	 	    	out.flush();
   	 	    	out.close();
   	 	    }
   	 	    
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
	  * 群组列表
	  * @return
	  */
	public void  getQzList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();

		Gson gson = new Gson();
		PrintWriter out = null;
   	 	try {
   	 	    out = response.getWriter();
   	 		 
   	 	    List<QzEntity> list = voteService.getQzList();
   	 	    
	        ResultEntity<List<QzEntity>> result = new ResultEntity<List<QzEntity>>(1, "获取成功", list);
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
	

	public IVoteService getVoteService() {
		return voteService;
	}

	public void setVoteService(IVoteService voteService) {
		this.voteService = voteService;
	}

	public VoteMainQuery getQuery() {
		return query;
	}

	public void setQuery(VoteMainQuery query) {
		this.query = query;
	}

	public VoteMainEntity getModel() {
		return model;
	}

	public void setModel(VoteMainEntity model) {
		this.model = model;
	}

	public IYhglService getYhglService() {
		return yhglService;
	}

	public void setYhglService(IYhglService yhglService) {
		this.yhglService = yhglService;
	}

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		//System.out.println(System.currentTimeMillis());
		VoteResultEntity vre = new VoteResultEntity();
		vre.setVoteUserId("007007");
		vre.setVoteOptionId("60605DE241ED1C90E0538513470ABA49");
		vre.setVoteOptionScore("5");
		VoteResultEntity vre2 = new VoteResultEntity();
		vre2.setVoteUserId("007007");
		vre2.setVoteOptionId("60605DE241EE1C90E0538513470ABA49");
		vre2.setVoteOptionScore("5");
		List<VoteResultEntity> list = new ArrayList<VoteResultEntity>();
		list.add(vre);
		list.add(vre2);
		System.out.println(java.net.URLEncoder.encode(new Gson().toJson(list),"UTF-8") );
		VoteOptionEntity vo = new VoteOptionEntity();
		vo.setVoteOptionId("");
		vo.setVoteMainId("123");
		vo.setVoteOptionDescription("c罗");
		vo.setVoteOptionSort("1");
		VoteOptionEntity vo2 = new VoteOptionEntity();
		vo2.setVoteOptionId("");
		vo2.setVoteMainId("124");
		vo2.setVoteOptionDescription("梅西");
		vo2.setVoteOptionSort("2");
		List<VoteOptionEntity> llist = new ArrayList<VoteOptionEntity>();
		llist.add(vo);
		llist.add(vo2);
		System.out.println(new Gson().toJson(llist));
		//[{"voteUserId":"007007","voteOptionId":"60605DE241ED1C90E0538513470ABA49","voteOptionScore":"5"},{"voteUserId":"007007","voteOptionId":"60605DE241EE1C90E0538513470ABA49","voteOptionScore":"5"}]
		//[{"voteUserId":"007007","voteOptionId":"60605DE241ED1C90E0538513470ABA49"},{"voteUserId":"007007","voteOptionId":"60605DE241EE1C90E0538513470ABA49"}]
	}
	
	/**
	 * 该注释以上全部是给移动端的接口，以下全部是给移动后台的接口
	 */
	
	//后台投票列表
	public String hdVoteList(){
		PageList<VoteMainEntity> list = voteService.getYdhtList(query);
		getValueStack().set("list", list);
		return "list";
	}
	
	//跳转到添加页面
	public String toAdd(){
		return "toAdd";
	}
	
	//后台投票增加
	public void hdVoteAdd(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		Gson gson = new Gson();
		PrintWriter out = null;
		VoteMainEntity voteMainEntity = new VoteMainEntity();
   	 	try {
   	 	    out = response.getWriter();
   	 		
   	 	    //从session获取用户信息
   	 	    //String username= request.getParameter("username");
   	 	    String username= "admin";
	   	    String voteTitle = request.getParameter("voteTitle");
	   	    String voteDescription = request.getParameter("voteDescription");
	   	    String voteType = request.getParameter("voteType");
	   	    String voteIsMultiSelect = request.getParameter("voteIsMultiSelect");
	   	    String voteMaxChoice = request.getParameter("voteMaxChoice");
	    	String voteIsAnonymous = request.getParameter("voteIsAnonymous");
	     	String voteResultOnlySee = request.getParameter("voteResultOnlySee");
	   	    String voteMaxScore = request.getParameter("voteMaxScore");
	   	    String voteScoreMethod = request.getParameter("voteScoreMethod");
	    	String voteEndDate = request.getParameter("voteEndDate");
	    	String voteIsDraft = request.getParameter("voteIsDraft");
	    	String qzId = request.getParameter("qzId");
	    	
	    	
	    	//获取所有选项
	    	String[] optionValues = request.getParameterValues("option");
			
			voteMainEntity.setVoteTitle(voteTitle);
			voteMainEntity.setVoteDescription(voteDescription);
        	voteMainEntity.setVoteType(voteType);
        	voteMainEntity.setVoteIsMultiSelect(voteIsMultiSelect==null? "":voteIsMultiSelect);
        	voteMainEntity.setVoteMaxChoice(voteMaxChoice==null? "":voteMaxChoice);
        	voteMainEntity.setVoteIsAnonymous(voteIsAnonymous);
        	voteMainEntity.setVoteResultOnlySee(voteResultOnlySee);
        	voteMainEntity.setVoteEndDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(voteEndDate));
        	voteMainEntity.setVoteStartDate(new Date());
        	voteMainEntity.setVoteMaxScore(voteMaxScore==null? "":voteMaxScore);
        	voteMainEntity.setVoteScoreMethod(voteScoreMethod==null? "":voteScoreMethod);
        	voteMainEntity.setVoteIsDraft(voteIsDraft);
        	voteMainEntity.setVoteOrganiserId(username);
        	
        	
        	//根据用户账号获取姓名填充到voteOrganiserName
        	//voteMainEntity.setVoteOrganiserName(yhglService.xmByZgh(username)==null? "":yhglService.xmByZgh(username));
        	voteMainEntity.setVoteOrganiserName("管理员");
        	voteService.insertVoteMain(voteMainEntity);
        	String voteId = voteMainEntity.getVoteId();
        	
        	//插入选项数据到数据库
        	if(optionValues!=null){
        		for (int i = 0; i < optionValues.length; i++) {
        			VoteOptionEntity voteOptionEntity = new VoteOptionEntity();
        			voteOptionEntity.setVoteMainId(voteId);
        			voteOptionEntity.setVoteOptionSort(i+"");
        			voteOptionEntity.setVoteOptionDescription(optionValues[i]);
        			voteService.insertVoteOption(voteOptionEntity);	
        		}        		
        	}
        	
        	//添加投票群组信息5B1E40F79CE30519E0538513470A528D
        	Map<String,Object> qzParams = new HashMap<String,Object>();
        	qzParams.put("voteMainId", voteId);
        	qzParams.put("qzId", qzId);
        	voteService.insertVoteGroup(qzParams);
			
	        ResultEntity<String> result = new ResultEntity<String>(1, "操作成功","success");
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
   	 	} catch (Exception e) {
   	 		e.printStackTrace(); 
            logger.error("memo upload------: exception");  
            ResultEntity<String> result = new ResultEntity<String>(0, "操作失败", "error");
            out.write(gson.toJson(result));
            out.flush();
            out.close();
         // 数据处理  
	 	}
	}
	
	//跳转到修改页面
	public String toUpdate(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String voteId = request.getParameter("voteId");
		
		//投票信息
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("voteId",voteId);
		VoteMainEntity voteMainEntity = voteService.getVoteById(params);
		getValueStack().set("voteMainEntity", voteMainEntity);
		
		//相关选项
		Map<String,Object> params2 = new HashMap<String,Object>();
		params2.put("voteMainId",voteId);
		List<VoteOptionEntity> voteOptionEntities = voteService.getOptionsByVoteId(params2);
		getValueStack().set("voteOptionEntities", voteOptionEntities);
		
		//相关分组
		VoteGroupEntity vgp = voteService.getVoteGroupById(params);
		getValueStack().set("voteGroup", vgp);
		
		//群组列表
		List<QzEntity> qzEntities = voteService.getQzList();
		getValueStack().set("qzEntities", qzEntities);
		
		return "toUpdate";
	}
	
	//后台投票修改
	public void hdVoteUpdate(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		Gson gson = new Gson();
		PrintWriter out = null;
		VoteMainEntity voteMainEntity = new VoteMainEntity();
   	 	try {
   	 	    out = response.getWriter();
   	 		
   	 	    //从session获取用户信息
   	 	    //String username= request.getParameter("username");
   	 	    String username= "admin";
   	 	    String voteId = request.getParameter("voteId");
	   	    String voteTitle = request.getParameter("voteTitle");
	   	    String voteDescription = request.getParameter("voteDescription");
	   	    String voteType = request.getParameter("voteType");
	   	    String voteIsMultiSelect = request.getParameter("voteIsMultiSelect");
	   	    String voteMaxChoice = request.getParameter("voteMaxChoice");
	    	String voteIsAnonymous = request.getParameter("voteIsAnonymous");
	     	String voteResultOnlySee = request.getParameter("voteResultOnlySee");
	   	    String voteMaxScore = request.getParameter("voteMaxScore");
	   	    String voteScoreMethod = request.getParameter("voteScoreMethod");
	    	String voteEndDate = request.getParameter("voteEndDate");
	    	String voteIsDraft = request.getParameter("voteIsDraft");
	    	String qzId = request.getParameter("qzId");
	    	
	    	
	    	//获取所有选项
	    	String[] optionValues = request.getParameterValues("option");
			
	    	voteMainEntity.setVoteId(voteId);
			voteMainEntity.setVoteTitle(voteTitle);
			voteMainEntity.setVoteDescription(voteDescription);
        	voteMainEntity.setVoteType(voteType);
        	voteMainEntity.setVoteIsMultiSelect(voteIsMultiSelect==null? "":voteIsMultiSelect);
        	voteMainEntity.setVoteMaxChoice(voteMaxChoice==null? "":voteMaxChoice);
        	voteMainEntity.setVoteIsAnonymous(voteIsAnonymous);
        	voteMainEntity.setVoteResultOnlySee(voteResultOnlySee);
        	voteMainEntity.setVoteEndDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(voteEndDate));
        	voteMainEntity.setVoteStartDate(new Date());
        	voteMainEntity.setVoteMaxScore(voteMaxScore==null? "":voteMaxScore);
        	voteMainEntity.setVoteScoreMethod(voteScoreMethod==null? "":voteScoreMethod);
        	voteMainEntity.setVoteIsDraft(voteIsDraft);
        	//voteMainEntity.setVoteOrganiserId(username);
        	
        	
        	//根据用户账号获取姓名填充到voteOrganiserName
        	//voteMainEntity.setVoteOrganiserName(yhglService.xmByZgh(username)==null? "":yhglService.xmByZgh(username));
        	//voteMainEntity.setVoteOrganiserName("管理员");
        	voteService.updateVoteMain(voteMainEntity);
        	
        	Map<String,Object> params = new HashMap<String,Object>();
        	params.put("voteId", voteId);
        	
        	//先删除原来的相关选项
        	voteService.deleteVoteOptionsByVoteId(params);
        	
        	//删除结果信息
        	voteService.deleteVoteResultsByVoteId(params);
        	
        	//删除分组信息
        	voteService.deleteVoteGroupByVoteId(params);
        	
        	//插入选项数据到数据库
        	if(optionValues!=null){
        		for (int i = 0; i < optionValues.length; i++) {
        			VoteOptionEntity voteOptionEntity = new VoteOptionEntity();
        			voteOptionEntity.setVoteMainId(voteId);
        			voteOptionEntity.setVoteOptionSort(i+"");
        			voteOptionEntity.setVoteOptionDescription(optionValues[i]);
        			voteService.insertVoteOption(voteOptionEntity);	
        		}        		
        	}
        	
        	//添加投票群组信息5B1E40F79CE30519E0538513470A528D
        	Map<String,Object> qzParams = new HashMap<String,Object>();
        	qzParams.put("voteMainId", voteId);
        	qzParams.put("qzId", qzId);
        	voteService.insertVoteGroup(qzParams);
			
	        ResultEntity<String> result = new ResultEntity<String>(1, "操作成功","success");
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
   	 	} catch (Exception e) {
   	 		e.printStackTrace(); 
            logger.error("memo upload------: exception");  
            ResultEntity<String> result = new ResultEntity<String>(0, "操作失败", "error");
            out.write(gson.toJson(result));
            out.flush();
            out.close();
         // 数据处理  
	 	}
	}
	
	//后台投票删除
	public void hdVoteRemove(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		Gson gson = new Gson();
		PrintWriter out = null;
   	 	try {
   	 	    out = response.getWriter();
   	 	   
	   	    String voteId = request.getParameter("voteId");
	   	    Map<String,Object> params = new HashMap<String,Object>();
	   	    params.put("voteId", voteId);
	   	    int rows = voteService.deleteVoteByVoteId(params);
        	
	        ResultEntity<String> result = new ResultEntity<String>(1, "success", String .valueOf(rows));
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
   	 	} catch (Exception e) {
   	 		e.printStackTrace(); 
            logger.error("memo upload------: exception");  
            ResultEntity<String> result = new ResultEntity<String>(0, "操作失败", "error");
            out.write(gson.toJson(result));
            out.flush();
            out.close();
         // 数据处理  
	 	}
	}
	
	//跳转到投票结果统计
	public String toResult(){
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String voteId = new String(request.getParameter("voteId").getBytes("ISO8859-1"), "UTF-8");
			getValueStack().set("voteIdForResultCount", voteId);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "toResult";
	}

	//获取投票统计数据
	public void hdVoteResultCount(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String voteId = null;
		
		Gson gson = new Gson();
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try{
			voteId = new String(request.getParameter("voteId").getBytes("ISO8859-1"), "UTF-8");
			
			Map<String,Object> resultMap = new HashMap<String, Object>();
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("voteId", voteId);
			
			//1、获取投票信息详情
			VoteMainEntity voteMainEntity = voteService.getVoteById(params);
			resultMap.put("voteMain", voteMainEntity);

			//2、获取投票结果统计
			List<VoteCountEntity> voteCountEntities = voteService.getVoteResultCount(params);
			resultMap.put("resultCount", voteCountEntities);
			
			//3、获取投票参与情况
			Map<String,Object> partInMap = new HashMap<String,Object>();
			List<VotePartInPersonEntity> partIns =voteService.getVoteHavePartInPersons(params);
			List<VotePartInPersonEntity> notPartIns = voteService.getVoteHaveNotPartInPersons(params);
			partInMap.put("partIns",partIns);
			partInMap.put("notPartIns",notPartIns);
			resultMap.put("partInMaps", partInMap);
			
			//4、获取投票结果详情
			List<VoteResultDetailEntity> voteResultDetailEntities = voteService.getVoteResultDetail(params);
			resultMap.put("resultDetail", voteResultDetailEntities);
			
			ResultEntity<Map<String,Object>> result = new ResultEntity<Map<String,Object>>(1, "成功", resultMap);
			
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
	
	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}
}
