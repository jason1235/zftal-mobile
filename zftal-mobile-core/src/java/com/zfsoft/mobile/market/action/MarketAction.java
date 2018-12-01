package com.zfsoft.mobile.market.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.util.Byte_File_Object;
import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.hrm.file.util.UploadFileUtil;
import com.zfsoft.mobile.ballot.utils.CommonUtils;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.common.utils.JSONUtils;
import com.zfsoft.mobile.market.entity.Market;
import com.zfsoft.mobile.market.entity.MarketCampus;
import com.zfsoft.mobile.market.entity.MarketColl;
import com.zfsoft.mobile.market.entity.MarketComment;
import com.zfsoft.mobile.market.entity.MarketCommentQuery;
import com.zfsoft.mobile.market.entity.MarketQuery;
import com.zfsoft.mobile.market.entity.MarketType;
import com.zfsoft.mobile.market.entity.MarketTypeQuery;
import com.zfsoft.mobile.market.service.MarketService;
import com.zfsoft.mobile.reportFix.entity.FixType;
import com.zfsoft.mobile.services.entity.ExpressCommentEntity;
import com.zfsoft.mobile.services.entity.LossObjectEntity;
import com.zfsoft.mobile.servlet.appCenterHttp.action.AppCenterHttpAction;
import com.zfsoft.mobile.servlet.entity.ListEntity;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;

//跳蚤市场app接口
public class MarketAction  extends HrmAction {
	private static Logger logger = Logger.getLogger(AppCenterHttpAction.class);
	
	private MarketService marketService;
	
	private MarketQuery query = new MarketQuery();
	
	private MarketTypeQuery marketTypeQuery = new MarketTypeQuery();
	
	private MarketCommentQuery commentQuery = new MarketCommentQuery();
	
	private String id = "";
	
	private String op;
	
	private MarketType marketType;
	
	private MarketCampus marketCampus = new MarketCampus();
	
	
	
	//跳蚤市场校区
	public String htCampusList(){
		List<MarketCampus> list = marketService.queryMarketCampus(marketCampus);
		this.getValueStack().set("list", list);
		return "htCampusList";
	}
	
	public String htAddCampus(){
		
		if( StringUtils.isEmpty(id) ){
			op = "add";
		}else{
			op = "modify";
			marketCampus = marketService.getMarketCampusById(id);
			this.getValueStack().set("marketCampus", marketCampus);
		}
		
		return "htAddCampus";
	}
	
	public void saveMarketCampus() throws IOException{
		Map<String,Object> map = marketService.saveMarketCampus(marketCampus);
		String result = JSONUtils.HashMap2Json(map);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.getWriter().print(result);
		response.getWriter().close();
	}
	
	public void htDelCampus() throws IOException {
		Map<String,Object> map = marketService.htDelCampus(id);
		String result = JSONUtils.HashMap2Json(map);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.getWriter().print(result);
		response.getWriter().close();
	}
	
	
	
	
	
	//跳蚤市场类型后台页面
	public String htTypeList(){
		List<MarketTypeQuery> list = marketService.queryMarketType(marketTypeQuery);
		this.getValueStack().set("list", list);
		return "typeList";
	}
	
	public String htAddType(){
		
		if( StringUtils.isEmpty(id) ){
			op = "add";
		}else{
			op = "modify";
			marketType = marketService.getMarketTypeById(id);
			this.getValueStack().set("marketType", marketType);
		}
		
		return "htAddType";
	}
	
	public void saveMarketType() throws IOException{
		Map<String,Object> map = marketService.saveMarketType(marketType);
		String result = JSONUtils.HashMap2Json(map);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.getWriter().print(result);
		response.getWriter().close();
	}
	
	public void htDelType() throws IOException {
		Map<String,Object> map = marketService.htDelType(id);
		String result = JSONUtils.HashMap2Json(map);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.getWriter().print(result);
		response.getWriter().close();
	}
	
	
	
	//分类列表
	public void typeList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			
			List<MarketType> list = marketService.selectMarketType();
			
			ResultEntity<List<MarketType>> result = new ResultEntity<List<MarketType>>(1, "", list);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
			
		} catch (IOException e) {
			logger.error("typeList......error");
			e.printStackTrace();
		}
	}
	
	//校区列表
	public void campusList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			
			List<MarketCampus> list = marketService.selectMarketCampus();
			
			ResultEntity<List<MarketCampus>> result = new ResultEntity<List<MarketCampus>>(1, "", list);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
			
		} catch (IOException e) {
			logger.error("campusList......error");
			e.printStackTrace();
		}
	}
	
	
	public void addMarket(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		PrintWriter out = null;  
		
		String id = null;
		String title = null;         //物品标题
		String content = null;
		String price = null;
		String type = null;
		String tel = null;  //发布者账号
		String createUserId = null;        //发布者姓名
		String campus = null; 
		String oldPrice = null; 
		String qNum = ""; 
		String apptoken = null; 
		byte[] picfileContent = null;
		
		StringBuilder sb = new StringBuilder();
		
		Gson gson = new Gson();
		Market market = new Market();
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
	                if(paramName.equals("title")){
	                	title = fValue.toString();
	                }else if(paramName.equals("id")){
	                	id = fValue.toString();
	                }else if(paramName.equals("content")){
	                	content = fValue.toString();
	                }else if(paramName.equals("price")){
	                	price = fValue.toString();
	                }else if(paramName.equals("type")){
	                	type = fValue.toString();
	                }else if(paramName.equals("tel")){
	                	tel = fValue.toString();
	                }else if(paramName.equals("username")){
	                	createUserId = fValue.toString();
	                }else if(paramName.equals("campus")){
	                	campus = fValue.toString();
	                }else if(paramName.equals("oldPrice")){
	                	oldPrice = fValue.toString();
	                }else if(paramName.equals("qNum")){
	                	qNum = fValue.toString();
	                }else if(paramName.equals("apptoken")){
	                	apptoken = fValue.toString();
	                	
	                	title  = CodeUtil.decode(title, apptoken);
	                	content   = CodeUtil.decode(content, apptoken);
	                	price    = CodeUtil.decode(price, apptoken);
	                	type       	= CodeUtil.decode(type, apptoken);
	                	tel   = CodeUtil.decode(tel, apptoken);
	                	createUserId     = CodeUtil.decode(createUserId, apptoken);
	                	campus      = CodeUtil.decode(campus, apptoken);
	                	oldPrice    = CodeUtil.decode(oldPrice, apptoken);
	                	qNum    = CodeUtil.decode(qNum, apptoken);
	                	id  = CodeUtil.decode(id, apptoken);
	                	
	                	market.setTitle(title);
	                	market.setContent(content);
	                	market.setPrice(Float.parseFloat(price));
	                	market.setType(type);
	                	market.setTel(tel);
	                	market.setCreateUserId(createUserId);
	                	market.setCampus(campus);
	                	market.setOldPrice(Float.parseFloat(oldPrice));
	                	market.setqNum(qNum);
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
		                    
		                    String filePath = request.getSession().getServletContext().getRealPath("/") + "iamges"; 
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
		        			
		        			fileOutputStream.write(fileContent, 0, fileContent.length);
		        			
		        			fileOutputStream.close();
		        			is.close();
		        			String url = BaseHolder.getPropertiesValue("suploadPath");
		        			//market.setPic("iamges/" + filename);
		        			sb.append("iamges/" + filename + ",");
		        			//signInEntity.setQrCodeFileName(filename);
		                }  
		            }  
		        }
	        
	        	market.setPic(sb.toString());
		       
		        if (StringUtils.isNotEmpty(id)) {
					market.setId(id);
					marketService.updateMarket(market);
				}else {
					SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			        market.setCreateTime(sd.format(new Date()));
					market.setStatus("0");
					marketService.addMarket(market);
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
	
	//物品列表
	public void marketList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		boolean validate = true;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			
			String title = null;
			String type = null;
			String campus = null;
			String start = null;
			String size = null;
			String apptoken = null;
			String sort = "0";
			String username = null;
			String coll = null;
			String status = null;
			
			title = StringUtil.isEmpty(request.getParameter("title")) ? "" : request.getParameter("title");
			type = StringUtil.isEmpty(request.getParameter("type")) ? "" : request.getParameter("type");
			campus = StringUtil.isEmpty(request.getParameter("campus")) ? "" : request.getParameter("campus");
			start = StringUtil.isEmpty(request.getParameter("start")) ? "" : request.getParameter("start");
			size = StringUtil.isEmpty(request.getParameter("size")) ? "" : request.getParameter("size");
			sort = StringUtil.isEmpty(request.getParameter("sort")) ? "" : request.getParameter("sort");
			username = StringUtil.isEmpty(request.getParameter("username")) ? "" : request.getParameter("username");
			coll = StringUtil.isEmpty(request.getParameter("coll")) ? "" : request.getParameter("coll");
			status = StringUtil.isEmpty(request.getParameter("status")) ? "" : request.getParameter("status");
			apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				validate = false;
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			if( validate ){
				try {
					title     = CodeUtil.decode(title, apptoken);
					type     = CodeUtil.decode(type, apptoken);
					campus    = CodeUtil.decode(campus, apptoken);
					start        = CodeUtil.decode(start, apptoken);
					size         = CodeUtil.decode(size, apptoken);
					sort         = CodeUtil.decode(sort, apptoken);
					username         = CodeUtil.decode(username, apptoken);
					coll         = CodeUtil.decode(coll, apptoken);
					status         = CodeUtil.decode(status, apptoken);
				} catch (Exception e) {
					validate = false;
					ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
					out.write(gson.toJson(result));
					out.flush();
					out.close();
				}
			}
			
			if( validate ){
				MarketQuery marketQuery = new MarketQuery();
				marketQuery.setTitle(title);
				marketQuery.setType(type);
				marketQuery.setCampus(campus);
				marketQuery.setSort(sort);
				marketQuery.setCreateUserId(username);
				marketQuery.setColl(coll);
				marketQuery.setStatus(status);
				marketQuery.setToPage(Integer.valueOf(start));
				marketQuery.setPerPageSize(Integer.valueOf(size));
				List<Market> list = marketService.selectMarketList(marketQuery);
				//列表图片处理
				/*for (MarketQuery marketQuery2 : list) {
					String a = marketQuery2.getPic();
					String[] aa = a.split(",");
					marketQuery2.setPic(aa[0]);
				}*/
				
				if( list!=null && list.size()>0 ){
					for (int i = 0; i < list.size(); i++) {
						String a = list.get(i).getPic();
						String[] aa = a.split(",");
						list.get(i).setPic(aa[0]);
					}
				}else{
					list = new ArrayList<Market>();
				}
				
				ListEntity<Market> resultList = new ListEntity<Market>();
				resultList.setItemList(list);
				if(list == null || list.size() < Integer.valueOf(size))	{
					resultList.setOvered(true);
				}else{
					resultList.setOvered(false);
				}
				ResultEntity<ListEntity<Market>> result = new ResultEntity<ListEntity<Market>>(1, "成功", resultList);
				System.out.println(gson.toJson(result));
			    out.write(gson.toJson(result));
			    out.flush();
			    out.close();
			}
			
		} catch (IOException e) {
			logger.error("marketList......error");
			e.printStackTrace();
			
			ListEntity<Market> resultList = new ListEntity<Market>();
			resultList.setItemList(new ArrayList<Market>());
			
			ResultEntity<ListEntity<Market>> result = new ResultEntity<ListEntity<Market>>(1, "成功", resultList);
			
			try {
				out = response.getWriter();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			out.write(gson.toJson(result));
		    out.flush();
		    out.close();
		}
	}
	
	public void getMarketById(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			String id = null;
			String username = null;
			String apptoken = null;
			id = new String(request.getParameter("id").getBytes("ISO8859-1"), "UTF-8");
			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
			apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			if(!ApptokenUtils.compare(apptoken)){
			ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
			}
			try {
				id     = CodeUtil.decode(id, apptoken);
				username     = CodeUtil.decode(username, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			Market market = marketService.getMarketById(id);
			//图片路径处理
			List<String> pics = new ArrayList<String>();
			String a = market.getPic();
			String[] aa = a.split(",");
			for (String string : aa) {
				pics.add(string);
			}
			market.setPics(pics);
			
			//获取头像
			//我的头像读取start
			String headPicturePath = null;
			IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
			List<ImageDB> imageList = mobileCommonService.getMyPicture(market.getCreateUserId());
			ImageDB image = imageList != null && imageList.size() > 0 ? imageList.get(0) : null;
			if(image == null){
				logger.error("我的门户接口:头像数据库图片不存在，路径也不存在！");
				headPicturePath = "";
			}else{
				String path = image.getPath();
				byte[] content = image.getFileContent();
				String headname = image.getFileName();
				String filename = StringUtil.isEmpty(headname) ? market.getCreateUserId()+"headPicture" : headname;
				
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
						}
					} 
					headPicturePath = getImageHost() + path;
					market.setHeadImg(headPicturePath);
				}
			}
			//我的头像读取end
			
			//判断是否收藏
			Integer count = marketService.getIsCollByUserId(username,market.getId());
			if (count > 0) {
				//yishoucang
				market.setIsColl(1);
			}
			
			ResultEntity<Market> result = new ResultEntity<Market>(1, "成功", market);
		    out.write(gson.toJson(result));
		    out.flush();
		    out.close();
			
		} catch (IOException e) {
			logger.error("getMarketById......error");
			e.printStackTrace();
		}
	}
	
	
	//添加到我的收藏,删除收藏
	public void collMarket(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			
			String id = null;
			String username = null;
			String apptoken = null;
			String code = null;
			
			id = new String(request.getParameter("id").getBytes("ISO8859-1"), "UTF-8");
			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
			code = new String(request.getParameter("code").getBytes("ISO8859-1"), "UTF-8");
			apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			try {
				id     = CodeUtil.decode(id, apptoken);
				username         = CodeUtil.decode(username, apptoken);
				code         = CodeUtil.decode(code, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			//判断是否收藏
			Integer count = marketService.getIsCollByUserId(username,id);
			
			MarketColl coll = new MarketColl();
			coll.setMarketId(id);
			coll.setUserId(username);
			if ("0".equals(code)) {
				if (count == 0) {
					SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					coll.setCreateTime(sd.format(new Date()));
					marketService.addMarketColl(coll);
				}else {
					ResultEntity<String> result = new ResultEntity<String>(0, "失败", "您已收藏");
				    out.write(gson.toJson(result));
				    out.flush();
				    out.close();
				}
			}else{
				if (count == 0) {
					ResultEntity<String> result = new ResultEntity<String>(0, "失败", "请先收藏");
				    out.write(gson.toJson(result));
				    out.flush();
				    out.close();
				}else {
					marketService.delMarketColl(coll);
				}
			}
			
			ResultEntity<String> result = new ResultEntity<String>(1, "成功", "");
		    out.write(gson.toJson(result));
		    out.flush();
		    out.close();
			
		} catch (IOException e) {
			logger.error("marketList......error");
			e.printStackTrace();
		}
	}
	
	
	//删除物品
	public void delMarket(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			String id = null;
			String apptoken = null;
			id = new String(request.getParameter("id").getBytes("ISO8859-1"), "UTF-8");
			apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			try {
				id     = CodeUtil.decode(id, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			//判断状态
			
			marketService.delMarket(id);
			ResultEntity<String> result = new ResultEntity<String>(1, "成功", "");
		    out.write(gson.toJson(result));
		    out.flush();
		    out.close();
		} catch (IOException e) {
			logger.error("delMarket......error");
			e.printStackTrace();
		}
	}
	
	//修该物品状态
	public void editMarket(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			String id = null;
			String status = null;
			String apptoken = null;
			id = new String(request.getParameter("id").getBytes("ISO8859-1"), "UTF-8");
			status = new String(request.getParameter("status").getBytes("ISO8859-1"), "UTF-8");
			apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			try {
				id     = CodeUtil.decode(id, apptoken);
				status     = CodeUtil.decode(status, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			Market market = new Market();
			market.setId(id);
			market.setStatus(status);
			marketService.updateMarket(market);
			
			ResultEntity<String> result = new ResultEntity<String>(1, "成功", "");
		    out.write(gson.toJson(result));
		    out.flush();
		    out.close();
		} catch (IOException e) {
			logger.error("editMarket......error");
			e.printStackTrace();
		}
	}
	
	
	//添加留言
	public void addComment(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			
			String marketId = null;//物品id
			String content = null;//评论内容
			String username = null;//评论人id
			String commentId = null;//如果是二级留言上传一级留言的id
			
			String apptoken = null;
			marketId = new String(request.getParameter("marketId").getBytes("ISO8859-1"), "UTF-8");
			content = new String(request.getParameter("content").getBytes("ISO8859-1"), "UTF-8");
			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
			commentId = StringUtil.isEmpty(request.getParameter("commentId")) ? "" : request.getParameter("commentId");
			apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			try {
				commentId     = CodeUtil.decode(commentId, apptoken);
				marketId     = CodeUtil.decode(marketId, apptoken);
				content     = CodeUtil.decode(content, apptoken);
				username     = CodeUtil.decode(username, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			MarketComment comment = new MarketComment();
			comment.setMarketId(marketId);
			comment.setContent(content);
			comment.setCreateUserId(username);
			comment.setCommentId(commentId);
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			comment.setCreateTime(sd.format(new Date()));
			marketService.addMarketComment(comment);
			
			ResultEntity<String> result = new ResultEntity<String>(1, "成功", "");
		    out.write(gson.toJson(result));
		    out.flush();
		    out.close();
		} catch (IOException e) {
			logger.error("addComment......error");
			e.printStackTrace();
		}
	}
	
	
	//留言列表
	public void commentList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			
			String marketId = null;//物品id
			String apptoken = null;
			
			marketId = new String(request.getParameter("marketId").getBytes("ISO8859-1"), "UTF-8");
			apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			try {
				marketId     = CodeUtil.decode(marketId, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			List<MarketComment> list = marketService.getCommentList(marketId,null);
			for (MarketComment marketComment : list) {
				List<MarketComment> comments = marketService.getCommentList(marketId,marketComment.getId());
				for (MarketComment comment : comments) {
					//获取用户昵称加头像
					comment.setUserName(marketService.getUserNameById(comment.getCreateUserId()));
					comment.setHeadPic(getheadImg(comment.getCreateUserId(), request));
				}
				//获取用户昵称加头像
				marketComment.setComments(comments);
				marketComment.setUserName(marketService.getUserNameById(marketComment.getCreateUserId()));
				marketComment.setHeadPic(getheadImg(marketComment.getCreateUserId(), request));
			}
			
			ResultEntity<List<MarketComment>> result = new ResultEntity<List<MarketComment>>(1, "成功", list);
		    out.write(gson.toJson(result));
		    out.flush();
		    out.close();
		} catch (IOException e) {
			logger.error("commentList......error");
			e.printStackTrace();
		}
	}
	
	
	//后台页面接口
	public String htMarketList(){
		List<Market> list = marketService.selectHtMatketList(query);
		this.getValueStack().set("list", list);
		return "marketList";
	}
	
	//物品删除
	public String htDelMarket(){
		marketService.delMarket(query.getId());
		this.setSuccessMessage("操作成功");
		this.getValueStack().set("data", this.getMessage());
		return "data";
	}
	
	public String htCommentList(){
		List<MarketComment> list = marketService.gethtCommentList(commentQuery);
		
		this.getValueStack().set("list", list);
		return "commentList";
	}
	
	//物品留言删除
	public String htDelMarketComment(){
		commentQuery.setId(id);
		marketService.delMarketComment(commentQuery.getId());
		this.setSuccessMessage("操作成功");
		this.getValueStack().set("data", this.getMessage());
		return "data";
	}
	
	
	
	
	/*=====================================================*/

	public MarketService getMarketService() {
		return marketService;
	}

	public void setMarketService(MarketService marketService) {
		this.marketService = marketService;
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
	
	private String getheadImg(String userId,HttpServletRequest request){
		//我的头像读取start
		String headPicturePath = null;
		IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
		List<ImageDB> imageList = mobileCommonService.getMyPicture(userId);
		ImageDB image = imageList != null && imageList.size() > 0 ? imageList.get(0) : null;
		if(image == null){
			logger.error("我的门户接口:头像数据库图片不存在，路径也不存在！");
			headPicturePath = "";
		}else{
			String path = image.getPath();
			byte[] content = image.getFileContent();
			String headname = image.getFileName();
			String filename = StringUtil.isEmpty(headname) ? userId+"headPicture" : headname;
			
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
					}
				} 
				headPicturePath = getImageHost() + path;
				//market.setHeadImg(headPicturePath);
			}
		}
		return headPicturePath;
		//我的头像读取end
	}

	public MarketQuery getQuery() {
		return query;
	}
	public void setQuery(MarketQuery query) {
		this.query = query;
	}
	public MarketCommentQuery getCommentQuery() {
		return commentQuery;
	}
	public void setCommentQuery(MarketCommentQuery commentQuery) {
		this.commentQuery = commentQuery;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public MarketTypeQuery getMarketTypeQuery() {
		return marketTypeQuery;
	}

	public void setMarketTypeQuery(MarketTypeQuery marketTypeQuery) {
		this.marketTypeQuery = marketTypeQuery;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public MarketType getMarketType() {
		return marketType;
	}

	public void setMarketType(MarketType marketType) {
		this.marketType = marketType;
	}

	public MarketCampus getMarketCampus() {
		return marketCampus;
	}

	public void setMarketCampus(MarketCampus marketCampus) {
		this.marketCampus = marketCampus;
	}
	
	
	
	/*public void typeList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			
		} catch (IOException e) {
			logger.error("typeList......error");
			e.printStackTrace();
		}
	}*/
}
