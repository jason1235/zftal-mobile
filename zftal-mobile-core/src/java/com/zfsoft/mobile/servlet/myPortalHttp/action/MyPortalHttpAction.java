/**
 * 
 */
package com.zfsoft.mobile.servlet.myPortalHttp.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.common.Config;
import com.google.gson.Gson;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.core.util.Byte_File_Object;
import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.hrm.file.util.ImageDBUtil;
import com.zfsoft.hrm.file.util.UploadFileUtil;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.common.utils.FileUntils;
import com.zfsoft.mobile.myportal.entity.MyPortal;
import com.zfsoft.mobile.myportal.service.IMyPortalService;
import com.zfsoft.mobile.pushmsg.entity.PushMsg;
import com.zfsoft.mobile.pushmsg.query.PushMsgQuery;
import com.zfsoft.mobile.pushmsg.service.IPushMsgService;
import com.zfsoft.mobile.servlet.entity.ListEntity;
import com.zfsoft.mobile.servlet.entity.MyPortalEntity;
import com.zfsoft.mobile.servlet.entity.MyPortalEntityItemEntity;
import com.zfsoft.mobile.servlet.entity.MyPortalNewEntity;
import com.zfsoft.mobile.servlet.entity.MyPortalNewEntityWithFwMap;
import com.zfsoft.mobile.servlet.entity.OneCardConsumeItemEntity;
import com.zfsoft.mobile.servlet.entity.OneCardEntity;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.mobile.servlet.myPortalHttp.service.IMyPortalHttpService;
import com.zfsoft.mobile.sourceexchange.entity.Sourceconsumerhis;
import com.zfsoft.mobile.sourceexchange.entity.Sourcegoods;
import com.zfsoft.mobile.sourceexchange.entity.Sourcesigninhis;
import com.zfsoft.mobile.sourceexchange.query.SourcegoodsQuery;
import com.zfsoft.mobile.sourceexchange.service.ISourceconsumerhisService;
import com.zfsoft.mobile.sourceexchange.service.ISourcegoodsService;
import com.zfsoft.mobile.sourceexchange.service.ISourcesigninService;
import com.zfsoft.mobile.webservices.entity.CardBusinessEntity;
import com.zfsoft.mobile.webservices.query.CardBusinessQuery;
import com.zfsoft.service.svcinterface.IYhglService;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;

/**
 * @author zhangxu
 * @description app我的门户访问接口
 * @date 2017-5-8 下午04:53:58
 */
public class MyPortalHttpAction {
	private static Logger logger = Logger.getLogger(MyPortalHttpAction.class);
	private final String infromation=Config.getString("mobile.infromation");

	private IMyPortalHttpService myPortalHttpService;
	private ISourcesigninService sourceSignService;
	private IYhglService yhglService;
	private ISourcegoodsService goodsService;
	private ISourceconsumerhisService consumingHisService;
	private IPushMsgService pushMsgService;
	
	public IPushMsgService getPushMsgService() {
		return pushMsgService;
	}

	public void setPushMsgService(IPushMsgService pushMsgService) {
		this.pushMsgService = pushMsgService;
	}

	/**
	 * 
	* @author: zhangxu
	* @Title: PushMsgList 
	* @Description: 获取个人消息推送消息列表
	* @param @param userId 用户id
	* @param @param start 页码
	* @param @param size 每页记录数
	* @param @param strKey 常量密钥
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public void PushMsgList() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = null; 
		String start = null; 
		String size = null;
		String apptoken = null;
        try {
        	PrintWriter out = response.getWriter();
        	username = request.getParameter("username");
        	start = request.getParameter("start");
        	size = request.getParameter("size");
        	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			Gson gson = new Gson();
		 	if(StringUtil.isEmpty(username) || StringUtil.isEmpty(start) 
		 			|| StringUtil.isEmpty(size) ){
				ResultEntity<ListEntity<PushMsg>> result = new ResultEntity<ListEntity<PushMsg>>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		 	if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<ListEntity<PushMsg>> result = new ResultEntity<ListEntity<PushMsg>>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		 	username = new String(username.getBytes("ISO8859-1"), "UTF-8");
        	start = new String(start.getBytes("ISO8859-1"), "UTF-8");
        	size = new String(size.getBytes("ISO8859-1"), "UTF-8");
        	try {
        		username  			= CodeUtil.decode(username, apptoken);
        		start  				= CodeUtil.decode(start, apptoken);
        		size  				= CodeUtil.decode(size, apptoken);
				
			} catch (Exception e) {
				ResultEntity<ListEntity<PushMsg>> result = new ResultEntity<ListEntity<PushMsg>>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		 	
				
			
			int startInt =Integer.valueOf(start);
			int sizeInt= Integer.valueOf(size);
			PushMsgQuery query = new PushMsgQuery();
			query.setTsdx(username);
			query.setPerPageSize(sizeInt);
			query.setToPage(startInt);
			query.setOrderStr(" tssj desc ");
			List<PushMsg> pushMsgList = pushMsgService.getPageList(query);
			ListEntity<PushMsg> resultList = new ListEntity<PushMsg>();
			resultList.setItemList(pushMsgList);
			if(pushMsgList == null || pushMsgList.size() < Integer.valueOf(size))	resultList.setOvered(true);
			else resultList.setOvered(false);
			
	        if(infromation.equals("0")){
				logger.error("获取消息推送列表结束");
				}
			//输出结果
			ResultEntity<ListEntity<PushMsg>> result = new ResultEntity<ListEntity<PushMsg>>(1, "成功", resultList);
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
        }catch (Exception e) {
        	logger.error("获取消息推送列表PushMsgList err：");
			logger.error(e, e.fillInStackTrace());
        }
	}

	public void setMyPortalHttpService(IMyPortalHttpService myPortalHttpService) {
		this.myPortalHttpService = myPortalHttpService;
	}

	public IMyPortalHttpService getMyPortalHttpService() {
		return myPortalHttpService;
	}


	public void uploadMyheadPic(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
        response.setContentType("text/html;charset=UTF-8");  
        //String uri = request.getRequestURI();
        FileItemFactory factory = new DiskFileItemFactory();  
        ServletFileUpload upload = new ServletFileUpload(factory);  
        List<FileItem> items = new ArrayList<FileItem>();  
        String apptoken = null;
        Gson gson = new Gson();
        try {  
        	PrintWriter out = response.getWriter();
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
                    if(paramName.equals("apptoken")){
                    	apptoken = fValue.toString();
                    }
                    logger.error("------图片上传中上传的是文本不应该上传文本-----");
                } else { // 获取上传文件的值  
                	logger.error("进入图片else");
                	if(!ApptokenUtils.compare(apptoken)){
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
                        byte[] content = Byte_File_Object.getBytesFromFile(is);
                        ImageDB ImageDB=new ImageDB();
                		ImageDB.setFileName(filename);
                		ImageDB.setFileContent(content);
                		ImageDB.setOpUser(paramName);
                		
                		String path = BaseHolder.getPropertiesValue("MyPicture","headPicture");
                		ImageDB.setPath(path+"/"+filename);
                		
                		List<ImageDB> imageList = myPortalHttpService.getMyPicture(paramName);
                		ImageDB image = imageList != null && imageList.size() > 0 ? imageList.get(0) : null;
                		if(image == null){
                			myPortalHttpService.insertMyPicture(ImageDB);
                		}else{
                			myPortalHttpService.updateMyPicture(ImageDB);
                		}
                		
                		//图片文件夹不存在则创建文件
                        String filePath = request.getSession().getServletContext().getRealPath("/") + path; 
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
        				
    					FileImageOutputStream imageOutput = null;
    					imageOutput  = new FileImageOutputStream(outFile);  
    					imageOutput.write(content, 0, content.length);  
    					imageOutput.close();  
    					ResultEntity<String> result = new ResultEntity<String>(1, "成功", null);
        				out.write(gson.toJson(result));
        				out.flush();
        				out.close();
                    }  
                }  
            }  
        } catch (Exception e) {  
        	logger.error("上传头像出错");  
        	logger.error(e,e.fillInStackTrace());
            e.printStackTrace(); 
        }  
	
	}
	
	/**
	 * 
	* @author: zhangxu
	* @Title: getocdetail 
	* @Description: 获取一卡通消费明细 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void getocdetail(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
        try {
        	PrintWriter out = response.getWriter();
        	String detailtype = new String(request.getParameter("detailtype").getBytes("ISO8859-1"), "UTF-8"); 
        	String ocid = new String(request.getParameter("ocid").getBytes("ISO8859-1"), "UTF-8"); 
			String pageindex = new String(request.getParameter("pageindex").getBytes("ISO8859-1"), "UTF-8"); 
			String pagesize = new String(request.getParameter("pagesize").getBytes("ISO8859-1"), "UTF-8"); 
			String strkey = new String(request.getParameter("strKey").getBytes("ISO8859-1"), "UTF-8");
		 	String apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			Gson gson = new Gson();
		 	if(StringUtil.isEmpty(detailtype) || StringUtil.isEmpty(ocid) ||
					StringUtil.isEmpty(pageindex) || StringUtil.isEmpty(pagesize) ||
					StringUtil.isEmpty(strkey) || StringUtil.isEmpty(apptoken)){
				ResultEntity<ListEntity> result = new ResultEntity<ListEntity>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		 	if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<ListEntity> result = new ResultEntity<ListEntity>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		 	try {
		 		detailtype  		= CodeUtil.decode(detailtype, apptoken);
				ocid  				= CodeUtil.decode(ocid, apptoken);
				pageindex  			= CodeUtil.decode(pageindex, apptoken);
				pagesize  			= CodeUtil.decode(pagesize, apptoken);
				strkey  			= CodeUtil.decode(strkey, apptoken);
				
			} catch (Exception e) {
				ResultEntity<ListEntity> result = new ResultEntity<ListEntity>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		
			if(infromation.equals("0")){
				logger.error("获取一卡通消费明细："+"detailtype="+detailtype+",ocid="+ocid+
						",pageindex="+pageindex+",pagesize="+pagesize+",strkey="+strkey);
				}	
			int count = 0;
			PageList<CardBusinessEntity> list = new PageList<CardBusinessEntity>();
			CardBusinessQuery businessQuery = new CardBusinessQuery();
			businessQuery.setOcid(ocid);
			businessQuery.setToPage(Integer.parseInt(pageindex));
			businessQuery.setPerPageSize(Integer.parseInt(pagesize));
			if(detailtype.equals("1")){
				count = myPortalHttpService.getodetailCount(businessQuery);
				list = myPortalHttpService.getodetailList(businessQuery);
			}
			if(detailtype.equals("0")){
				count = myPortalHttpService.getcdetailCount(businessQuery);
				list = myPortalHttpService.getcdetailList(businessQuery);
			}
			List<OneCardConsumeItemEntity> consumeList = new ArrayList<OneCardConsumeItemEntity>();
			OneCardConsumeItemEntity oneCardConsumeItemEntity;
			if(list != null && list.size() > 0){
				for(CardBusinessEntity cardBusinessEntity : list){
					oneCardConsumeItemEntity= new OneCardConsumeItemEntity();
					oneCardConsumeItemEntity.setConsumeAspect(cardBusinessEntity.getClassSh());
					oneCardConsumeItemEntity.setConsumetime(
							(new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm")).format(cardBusinessEntity.getClassJysj())
							);
					oneCardConsumeItemEntity.setBalance(cardBusinessEntity.getClassYe());
					oneCardConsumeItemEntity.setOutlay((detailtype.equals("1")? "-" : "+") + cardBusinessEntity.getClassJye());
					consumeList.add(oneCardConsumeItemEntity);
				}
			}
			int pagecount = count / Integer.parseInt(pagesize);

			if ((count % Integer.parseInt(pagesize)) != 0) {
				pagecount++;
			}
			ListEntity<OneCardConsumeItemEntity> entity = new ListEntity<OneCardConsumeItemEntity>();
			entity.setItemList(consumeList);
			if(consumeList == null || consumeList.size() == 0 || consumeList.size() < 10){
				entity.setOvered(true);
			}else{
				entity.setOvered(false);
			}
			//输出结果
			ResultEntity<ListEntity> result = new ResultEntity<ListEntity>(1, "成功", entity);
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
        }catch (Exception e) {
        	logger.error("获取一卡通消费明细 getocdetail  err：");
			logger.error(e, e.fillInStackTrace());
        }
	}
	
	/**
	 * 
	* @author: zhangxu
	* @Title: getocbalance 
	* @Description: 获取个人一卡通帐号和余额
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public void getocbalance(){
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
				ResultEntity<OneCardEntity> result = new ResultEntity<OneCardEntity>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		 	if(!ApptokenUtils.compare(username, apptoken)){
				ResultEntity<OneCardEntity> result = new ResultEntity<OneCardEntity>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		 	try {
				username  			= CodeUtil.decode(username, apptoken);
				
			} catch (Exception e) {
				ResultEntity<OneCardEntity> result = new ResultEntity<OneCardEntity>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		
			String cardNumber = myPortalHttpService.getCardKH(username);
			if(StringUtil.isEmpty(cardNumber)){
				ResultEntity result = new ResultEntity<OneCardEntity>(0, "不存在此用户的一卡通", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			OneCardEntity entity = new OneCardEntity();
			entity = new OneCardEntity();
			entity.setCardNumber(cardNumber);
			entity.setCardBlance(String.valueOf(myPortalHttpService.getCardNumber(username)));
			
			
			//输出结果
			ResultEntity<OneCardEntity> result = new ResultEntity<OneCardEntity>(1, "成功", entity);
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
	        }catch (Exception e) {
	        	logger.error("获取个人一卡通帐号和余额getocbalance err：");
				logger.error(e, e.fillInStackTrace());
	        }
		}
	
	
	/**
	 * 
	* @author: zhangxu
	* @Title: myPortalFunction 
	* @Description: 读取我的门户中所需选项
	* @param userName:用户名 ,strKey:常量秘钥,apptoken:个人秘钥
	* @return String    返回类型 
	* @throws
	 */
	public void myPortalFunction(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = null;
   	 	String strKey = null;
   	 	String apptoken = null;
   	 	try {
   	 		PrintWriter out = response.getWriter();
//			username = StringUtil.isEmpty(request.getParameter("username")) ? 
//						"" : java.net.URLDecoder.decode(request.getParameter("username"), "UTF-8");
//	   	 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
//	   	 	strKey = StringUtil.isEmpty(request.getParameter("strKey")) ? 
//   	 				"" : java.net.URLDecoder.decode(request.getParameter("strKey"), "UTF-8");
	   	 	
   	 		username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
   	 		apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
   	 		strKey = new String(request.getParameter("strKey").getBytes("ISO8859-1"), "UTF-8");
			Gson gson = new Gson();
			List<MyPortalEntityItemEntity> entityList = new ArrayList<MyPortalEntityItemEntity>();
			if(StringUtil.isEmpty(username) || StringUtil.isEmpty(strKey)
					|| StringUtil.isEmpty(apptoken)){
				ResultEntity<MyPortalEntity> result = new ResultEntity<MyPortalEntity>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			MyPortalEntityItemEntity entity = null;
			if(!ApptokenUtils.compare(username, apptoken)){
				ResultEntity<MyPortalEntity> result = new ResultEntity<MyPortalEntity>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				username  			= CodeUtil.decode(username, apptoken);
				strKey  			= CodeUtil.decode(strKey, apptoken);
				
			} catch (Exception e) {
				ResultEntity<MyPortalEntity> result = new ResultEntity<MyPortalEntity>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}	
				
			
			if(infromation.equals("0")){
				logger.error("我的门户接口未读未还接口获取："+"userName="+username+",strKey="+strKey+",apptoken="+apptoken);
			}
			IMyPortalService myPortalService = (IMyPortalService) SpringHolder.getBean("myPortalService");
			List<MyPortal> apps = myPortalService.getAllMyPortal(username);
			IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
			apps = mobileCommonService.getPortalUrl(apps, username);
			Map<String, String> param = new HashMap<String, String>();
			param.put("userId", username);
			Map<String, Object> retMap = mobileCommonService.getPortalInfo(param);
			String url = getImageHost();
			if (apps != null && apps.size() > 0) {
				for (MyPortal app : apps) {
					entity = new MyPortalEntityItemEntity();
					entity.id = app.getId();
					String type = app.getType();
					type = type.equals("1") ? "WEB_SERVICE" : 
						   type.equals("0") ? "APP_SERVICE" : " FROM_OTHER";	
					entity.serviceType = type;
					entity.name = app.getName();
					if ("903".equals(app.getCode()) && retMap!=null && retMap.get("WHS") != null) {
						entity.itemName  = "未还图书";
						entity.itemValue = StringUtil.isEmpty(retMap.get("WHS").toString())  ? 
											"0" : retMap.get("WHS").toString();
						entity.itemUnit  = "本";
					}else if ("506".equals(app.getCode()) && retMap!=null && retMap.get("YUE") != null) {
						entity.itemName  = "余额";
						entity.itemValue = StringUtil.isEmpty(retMap.get("YUE").toString()) ? 
								 			"0" : retMap.get("YUE").toString();
						entity.itemUnit  = "元";
					} else if ("904".equals(app.getCode()) && retMap!=null && retMap.get("YUE") != null) {
						entity.itemName  = "余额";
						entity.itemValue = StringUtil.isEmpty(retMap.get("YUE").toString()) ? 
											"0" : retMap.get("YUE").toString();
						entity.itemUnit  = "元";
					} else if ("906".equals(app.getCode()) && retMap!=null && retMap.get("SFGZ") != null) {
						entity.itemName  = "收入";
						entity.itemValue = StringUtil.isEmpty(retMap.get("YUE").toString()) ? 
											retMap.get("SFGZ").toString() : "0";
						entity.itemUnit  = "元";
					} else {
						entity.itemName  = "";
						entity.itemValue = "";
						entity.itemUnit  = "";
					}
					entity.icon = url + app.getTburl();
					entity.url = app.getAddr();
					entity.serviceCode = app.getCode();
					entity.bak ="";
					entityList.add(entity);
					if(infromation.equals("0")){
						logger.error("我的门户接口未读未还接口获取entity："+entity);
						}
				}
				
			}
			if(infromation.equals("0")){
				logger.error("我的门户接口未读未还接口end");
			}
			
			String headPicturePath = null;
			//我的头像读取
			List<ImageDB> imageList = myPortalHttpService.getMyPicture(username);
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
					String projectUrl = request.getSession().getServletContext().getRealPath("/") + path;
					projectUrl = projectUrl.replace("\\", "/");
					File outFile = new File(projectUrl);
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
							logger.error("我的门户选项接口myPortalFunction 生成图片产生异常 err：");
							logger.error(e, e.fillInStackTrace());
							ResultEntity<MyPortalEntity> result = new ResultEntity<MyPortalEntity>(0, "获取我的门户数据产生异常", new MyPortalEntity());
							out.write(gson.toJson(result));
							out.flush();
							out.close();
						}
					} 
					headPicturePath = getImageHost() + path;
				}
			}
				
			
			MyPortalEntity myPortalEntity = new MyPortalEntity(headPicturePath, entityList);
			ResultEntity<MyPortalEntity> result = new ResultEntity<MyPortalEntity>(1, "成功", myPortalEntity);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
   	 	} catch (Exception e) {
	   	 	logger.error("我的门户选项接口myPortalFunction err：");
			logger.error(e, e.fillInStackTrace());
	 	}
	}
	
	/**
	 * @description 获取我的门户，重新实现
	 * @param username
	 * @param apptoken
	 * @return ResultEntity<MyPortalNewEntity>
	 * @author yangbilin
	 * @createtime 2017-07-10
	 * 
	 */
	public void sourceFunctionForPortal(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
   	 	try {
   	 		String userName = null;
   	 		Gson gson = new Gson();
   	 		PrintWriter out = response.getWriter();
   	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
   	 			userName = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
   	 		}
   	 	    String apptoken = request.getParameter("apptoken");
			
	 		if(StringUtil.isEmpty(userName)|| StringUtil.isEmpty(apptoken)){
				ResultEntity<MyPortalNewEntity> result = new ResultEntity<MyPortalNewEntity>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			if(!ApptokenUtils.compare(userName, apptoken)){
				ResultEntity<MyPortalNewEntity> result = new ResultEntity<MyPortalNewEntity>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			try {
				userName  = CodeUtil.decode(userName, apptoken);
			} catch (Exception e) {
				ResultEntity<MyPortalNewEntity> result = new ResultEntity<MyPortalNewEntity>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}	
		
			String source="0";
			String isTodaySign="0";
			String sourceLevel = "Lv0";
			String levelLimit = "0,25";
			
			if(infromation.equals("0")){
				logger.error("我的门户接口未读未还接口获取："+"userName="+userName+",apptoken="+apptoken);
			}
			
			//获取用户门户所有服务
			IMyPortalService myPortalService = (IMyPortalService) SpringHolder.getBean("myPortalService");
			
			//##############################################################
			/**
			 * 1、先获取m_wdmh表中所有tsgn类型
			 * 2、根据tsgn字段分别获取相关服务列表
			 * 3、对服务列表进行url处理
			 * 4、将服务放进map，key值为特色功能类别实体中lbmc，value值为该组服务列表
			 */
			//门户服务分组列表Map
			Map<String,Object> mhfwFzMap = new HashMap<String, Object>();
			
			//获取m_wdmh表中所有tsgn类型
			List<String> tsgnLbList = myPortalService.getMhLbListInWdmh();
			
			Map<String,Object> tsgnParams = new HashMap<String, Object>();
			tsgnParams.put("userName", userName);
			
			//遍历tsgnLbList，获取相应服务列表
			for(int i=0;i<tsgnLbList.size();i++){
				List<MyPortalEntityItemEntity> entityList = new ArrayList<MyPortalEntityItemEntity>();	
				if(!StringUtil.isEmpty(tsgnLbList.get(i))){
					String tsgn = tsgnLbList.get(i);
					tsgnParams.put("tsgn",tsgn);
					List<MyPortal> apps = myPortalService.getFwListByTsgn(tsgnParams);
					

					//用户移动端设置的常用服务
					IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
					apps = mobileCommonService.getPortalUrl(apps, userName);
					Map<String, String> param = new HashMap<String, String>();
					param.put("userId", userName);
					//获取我的门户图书馆未归还图书数，一卡通余额，工资信息
					/**
					 *第一：获取用户积分数量
					 *第二：根据用户名称到用户积分签到表M_SOURCE_SIGNINHIS中根据创建时间判断是否已签到，返回istodaysign的值
					 *第三：积分是否与我的门户其他服务一起返回
					 */
					Map<String, Object> retMap = mobileCommonService.getPortalInfo(param);
					MyPortalEntityItemEntity entity = null;
					String url = getImageHost();
					if (apps != null && apps.size() > 0) {
						for (MyPortal app : apps) {
							entity = new MyPortalEntityItemEntity();
							entity.id = app.getId();
							String type = app.getType();
							type = type.equals("1") ? "WEB_SERVICE" : 
								   type.equals("0") ? "APP_SERVICE" : " FROM_OTHER";	
							entity.serviceType = type;
							entity.name = app.getName();
//							if ("903".equals(app.getCode()) && retMap!=null && retMap.get("WHS") != null) {
//								entity.itemName  = "未还图书";
//								entity.itemValue = StringUtil.isEmpty(retMap.get("WHS").toString())  ? 
//													"0" : retMap.get("WHS").toString();
//								entity.itemUnit  = "本";
//							}else if ("506".equals(app.getCode()) && retMap!=null && retMap.get("YUE") != null) {
//								entity.itemName  = "余额";
//								entity.itemValue = StringUtil.isEmpty(retMap.get("YUE").toString()) ? 
//										 			"0" : retMap.get("YUE").toString();
//								entity.itemUnit  = "元";
//							} else if ("904".equals(app.getCode()) && retMap!=null && retMap.get("YUE") != null) {
//								entity.itemName  = "余额";
//								entity.itemValue = StringUtil.isEmpty(retMap.get("YUE").toString()) ? 
//													"0" : retMap.get("YUE").toString();
//								entity.itemUnit  = "元";
//							} else if ("906".equals(app.getCode()) && retMap!=null && retMap.get("SFGZ") != null) {
//								entity.itemName  = "收入";
//								entity.itemValue = StringUtil.isEmpty(retMap.get("YUE").toString()) ? 
//													retMap.get("SFGZ").toString() : "0";
//								entity.itemUnit  = "元";
//							} else{
//								entity.itemName  = "";
//								entity.itemValue = "";
//								entity.itemUnit  = "";
//							}
							entity.icon = url + app.getTburl();
							entity.url = app.getAddr();
							entity.serviceCode = app.getCode();
							entity.bak ="";
							entity.tsgn=app.getTsgn();
							entity.isfx=app.getIsfx();
							entityList.add(entity);
							if(infromation.equals("0")){
								logger.error("我的门户接口未读未还接口获取entity："+entity);
							}
						}
					}
					mhfwFzMap.put(tsgn, entityList);
					
				}//if语句结束括号
				
			}//循环尾部括号
			
			//List<MyPortal> apps = myPortalService.getAllMyPortal(userName);
			
			
		
			String headPicturePath = null;
			//我的头像读取
			List<ImageDB> imageList = myPortalHttpService.getMyPicture(userName);
			ImageDB image = imageList != null && imageList.size() > 0 ? imageList.get(0) : null;
			if(image == null){
				logger.error("我的门户接口:头像数据库图片不存在，路径也不存在！");
				headPicturePath = "";
			}else{
				String path = image.getPath();
				byte[] content = image.getFileContent();
				String headname = image.getFileName();
				String filename = StringUtil.isEmpty(headname) ? userName+"headPicture" : headname;
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
					String projectUrl = request.getSession().getServletContext().getRealPath("/") + path;
					projectUrl = projectUrl.replace("\\", "/");
					File outFile = new File(projectUrl);
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
							logger.error("我的门户选项接口myPortalFunction 生成图片产生异常 err：");
							logger.error(e, e.fillInStackTrace());
							ResultEntity<MyPortalEntity> result = new ResultEntity<MyPortalEntity>(0, "获取我的门户数据产生异常", new MyPortalEntity());
							out.write(gson.toJson(result));
							out.flush();
							out.close();
							return;
						}
					} 
					headPicturePath = getImageHost() + path;
				}
			}
			Map<String,Object> sourceMap = myPortalHttpService.getPortalSource(userName);
			if(sourceMap!=null&&sourceMap.size()>0){
				for (String key : sourceMap.keySet()) {
					if("source".equalsIgnoreCase(key)){
						if(sourceMap.get(key)!=null){
							source=String.valueOf(sourceMap.get(key));
							
							//计算积分等级
						    String[] sourceCount = sourceCount(Integer.parseInt(source));
						    sourceLevel = sourceCount[0];
						    levelLimit = sourceCount[1];
						}
					}
					if("isTodaySign".equalsIgnoreCase(key)){
						if(sourceMap.get(key)!=null){
					    	isTodaySign=String.valueOf(sourceMap.get(key));
					    }
					}
				}
			}
			MyPortalNewEntityWithFwMap myPortalEntity = new MyPortalNewEntityWithFwMap(headPicturePath,source,sourceLevel,levelLimit,isTodaySign,mhfwFzMap);
			//MyPortalNewEntity myPortalEntity = new MyPortalNewEntity(headPicturePath,source,isTodaySign,entityList);
			ResultEntity<MyPortalNewEntityWithFwMap> result = new ResultEntity<MyPortalNewEntityWithFwMap>(1, "成功", myPortalEntity);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
   	 	}catch (Exception e) {
   	 		logger.error("我的门户选项接口sourceFunctionForPortal err：");
   	 		e.printStackTrace();
//			logger.error(e, e.fillInStackTrace());
	 	}
	}
	
	/**
	 * @description 签到并返回用户积分值
	 * @param username
	 * @param apptoken
	 * @param source
	 * @param appsource
	 * @return ResultEntity<String>
	 * @author yangbilin
	 * @createtime 2017-07-10
	 */
	public void signinAndGetSource(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
        try {
        	String username = null;
        	ResultEntity<String> result =null;
        	PrintWriter out = response.getWriter();
        	if(StringUtils.isNotBlank(request.getParameter("username"))){
        		username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
        	}
        	String apptoken = request.getParameter("apptoken");
		 	String source=StringUtil.isEmpty(request.getParameter("source")) ? "" : request.getParameter("source");
		 	String appsource=StringUtil.isEmpty(request.getParameter("appsource")) ? "" : request.getParameter("appsource");
		 	
			Gson gson = new Gson();
		 	if(StringUtil.isEmpty(username) || StringUtil.isEmpty(apptoken)
		 			||StringUtil.isBlank(source)|| StringUtil.isBlank(appsource)){
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
				source = CodeUtil.decode(source, apptoken);
				appsource=CodeUtil.decode(appsource, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			if(StringUtils.isNotBlank(source)&&StringUtils.isNotBlank(appsource)){
				//签到
				Sourcesigninhis sourcehis = new Sourcesigninhis();
				sourcehis.setUserid(username);
				sourcehis.setCreatetime(new Date());
				int isSigned=sourceSignService.isSignedToday(sourcehis);
				if(isSigned>0){
					result = new ResultEntity<String>(0, "今天已签到",null);
					out.write(gson.toJson(result));
					out.flush();
					out.close();
					return;
				}
				int bonus=Integer.parseInt(source);
				sourcehis.setSource(bonus);
				sourcehis.setAppsource(appsource);
				
				//新增用户积分签到历史记录表
				int issuccess=sourceSignService.signIn(sourcehis);
				if(issuccess>0){
					//更新用户表的积分数
					int sources=yhglService.sourceByZgh(username);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("userId", username);
					params.put("source", sources+bonus);
					yhglService.updateYhbSource(params);
					//如果都成功，则返回true，及积分数量进行封装
					result = new ResultEntity<String>(1, "成功",String.valueOf(sources+bonus));
				}else{
					result = new ResultEntity<String>(0, "签到失败,请稍后重试",null);
				}
				out.write(gson.toJson(result));
				out.flush();
				out.close();
		    }
        }catch (Exception e) {
        	logger.error("签到并返回积分总数signinAndGetSource err：");
			logger.error(e, e.fillInStackTrace());
        }
	}
	
	/**
	 * @description 获取积分商品列表
	 * @param apptoken
	 * @param price
	 * @param goodsname
	 * @param start
	 * @param size
	 * @return ResultEntity<ListEntity<Sourcegoods>>
	 * @author yangbilin
	 * @createtime 2017-07-11
	 * 
	 */
	public void getSourceGoodsList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
        try {
        	PrintWriter out = response.getWriter();
        	String goodsname = null;
       	 	String startStr = null;
       	 	String sizeStr=null;
        	ResultEntity<ListEntity<Sourcegoods>> result =null;
        	
        	String apptoken = request.getParameter("apptoken");
		 	String price=StringUtil.isEmpty(request.getParameter("price")) ? "" : request.getParameter("price");
		 	if(StringUtils.isNotBlank(request.getParameter("goodsname"))){
		 		goodsname = new String(request.getParameter("goodsname").getBytes("ISO8859-1"), "UTF-8");
        	}
		 	String startInit=StringUtil.isEmpty(request.getParameter("start")) ? "" : request.getParameter("start");
		 	String sizeInit=StringUtil.isEmpty(request.getParameter("size")) ? "" : request.getParameter("size");
		 	
			Gson gson = new Gson();
		 	if(StringUtil.isEmpty(apptoken)||StringUtil.isBlank(price)
		 			|| StringUtil.isBlank(startInit)|| StringUtil.isBlank(sizeInit)){
				result = new ResultEntity<ListEntity<Sourcegoods>>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
		 	
		 	try {
				price = CodeUtil.decode(price, apptoken);
				if(StringUtils.isNotBlank(goodsname)){
					goodsname = CodeUtil.decode(goodsname, apptoken);
				}
				startStr = CodeUtil.decode(startInit, apptoken);
				sizeStr  = CodeUtil.decode(sizeInit, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<ListEntity<Sourcegoods>>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			ListEntity<Sourcegoods> goodslist =new  ListEntity<Sourcegoods>();
			SourcegoodsQuery query=new SourcegoodsQuery();
			query.setToPage(Integer.valueOf(startStr));
			query.setPerPageSize(Integer.valueOf(sizeStr));
			query.setGoodsname(goodsname);
			query.setIsactive("1");
			query.setPrice(price);
			
		 	List<Sourcegoods> pagelist= goodsService.getPageList(query);
		 	if(pagelist!=null&&pagelist.size()>0){
		 		query.setTotalItem(pagelist.size());	
				int totalPage=query.getTotalPage();
				if(Integer.valueOf(startStr)<totalPage){
					goodslist.setOvered(true);
				}else{
					goodslist.setOvered(false);
				}
				//商品的图片集
				FileUntils fileUntils = new FileUntils();
				String readlPath=request.getSession().getServletContext().getRealPath("/");	
				for (Sourcegoods sourcegoods : pagelist) {
					List<String> picCol=new ArrayList<String>();
					String[] picid =StringUtils.split(sourcegoods.getPicids(),",");
					String[] picpath =StringUtils.split(sourcegoods.getPicpaths(),",");
					if(picpath!=null){
						for (int i=0;i<picpath.length;i++) {
							String filePath=readlPath+picpath[i].substring(0,picpath[i].lastIndexOf("/")+1);
							filePath=filePath.replace("\\", "/");
							File folder = new File(filePath);
							if(!folder.exists()){
								folder.mkdir();
							}
							File file = new File(filePath,picpath[i].substring(picpath[i].lastIndexOf("/")+1,picpath[i].length()));
							if(!file.exists()){
								file.createNewFile();
							
								String imgid=picid[i];
								ImageDB imageDB=ImageDBUtil.getImageDBByGuid(imgid);
								if(imageDB==null){
									logger.error("该积分数据库图片不存在，路径也不存在！");
									continue;
								}
								byte[] content=imageDB.getFileContent();
								FileOutputStream fileOutputStream = new FileOutputStream(file);
								fileOutputStream.write(content,0,content.length);
								fileOutputStream.close();
							}
							picCol.add(fileUntils.getImageHost()+picpath[i]);
						}
					}
					sourcegoods.setPicPathList(picCol);
				}
				goodslist.setItemList(pagelist);
				result = new ResultEntity<ListEntity<Sourcegoods>>(1, "成功", goodslist);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
		 	}else{
		 		goodslist.setOvered(true);
		 		goodslist.setItemList(pagelist);
				result = new ResultEntity<ListEntity<Sourcegoods>>(1, "成功", goodslist);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
        }catch (Exception e) {
        	logger.error("获取积分商品列表getSourceGoodsList err：");
			logger.error(e, e.fillInStackTrace());
        }
	}
	
	/**
	 * @description 积分兑换商品
	 * @param apptoken
	 * @param username
	 * @param amount
	 * @param goodsid
	 * @return ResultEntity<String>
	 * @author yangbilin
	 * @createtime 2017-07-11
	 * 
	 */
	public void purchaseGoods(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
        try {
        	String username = null;
        	ResultEntity<String> result =null;
        	PrintWriter out = response.getWriter();
        	if(StringUtils.isNotBlank(request.getParameter("username"))){
        		username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
        	}
        	String apptoken = request.getParameter("apptoken");
		 	String amount=StringUtil.isEmpty(request.getParameter("amount")) ? "" : request.getParameter("amount");
		 	String goodsId=StringUtil.isEmpty(request.getParameter("goodsid")) ? "" : request.getParameter("goodsid");
		 	
			Gson gson = new Gson();
		 	if(StringUtil.isEmpty(username) || StringUtil.isEmpty(apptoken)
		 			||StringUtil.isBlank(amount)|| StringUtil.isBlank(goodsId)){
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
				amount = CodeUtil.decode(amount, apptoken);
				goodsId=CodeUtil.decode(goodsId, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			synchronized (this) {
				SourcegoodsQuery query = new SourcegoodsQuery();
				query.setGoodsid(goodsId);
				Sourcegoods sourcegoods=goodsService.findById(query);
				int numbericvalue=sourcegoods.getNumbericvalue();
				int storage=sourcegoods.getStorage();
				int inventory=Integer.parseInt(amount);
				//兑换购买商品，库存减少，且用户表中的source减少
				if(storage<inventory){
					result = new ResultEntity<String>(0, "对不起，库存数量不足，无法兑换!", null);
					out.write(gson.toJson(result));
					out.flush();
					out.close();
				}else{
					//获取用户的积分总数
					int sources=yhglService.sourceByZgh(username);
					int minusSource=sources-numbericvalue*inventory;
					if(minusSource<0){
						result = new ResultEntity<String>(0, "对不起，您的积分不足，无法兑换!", null);
						out.write(gson.toJson(result));
						out.flush();
						out.close();
						return;
					}else{
						//更新积分商品库存
						int remant=storage-inventory;
						query.setStorage(remant);
						goodsService.updateStorage(query);
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("userId", username);
						params.put("source", minusSource);
						yhglService.updateYhbSource(params);
					}
					//添加积分购买记录
					Sourceconsumerhis consuminghis = new Sourceconsumerhis();
					consuminghis.setUserid(username);
					consuminghis.setGoodsid(goodsId);
					consuminghis.setAmount(inventory);
					consumingHisService.purchaseGoods(consuminghis);
					//如果都成功，则返回true
					result = new ResultEntity<String>(1, "成功",minusSource+"");
					out.write(gson.toJson(result));
					out.flush();
					out.close();
				}
			}
        }catch (Exception e) {
        	e.printStackTrace();
        	logger.error("积分购买商品purchaseGoods err：");
			logger.error(e, e.fillInStackTrace());
        }
	}
	
	
	/**
	 * @description 积分兑换商品记录列表
	 * @param apptoken
	 * @param username
	 * @param start
	 * @param size
	 * @return ResultEntity<ListEntity<Sourceconsumerhis>>
	 * @author yangbilin
	 * @createtime 2017-07-11
	 * 
	 */
	public void getSourceConsumerHis(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
        try {
        	PrintWriter out = response.getWriter();
        	String username = null;
       	 	String startStr = null;
       	 	String sizeStr=null;
       	    ResultEntity<ListEntity<Sourceconsumerhis>> result =null;
	       	if(StringUtils.isNotBlank(request.getParameter("username"))){
	     		username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	     	}
	       	String apptoken=request.getParameter("apptoken");
			String startInit=StringUtil.isEmpty(request.getParameter("start")) ? "" : request.getParameter("start");
		 	String sizeInit=StringUtil.isEmpty(request.getParameter("size")) ? "" : request.getParameter("size");
		 	Gson gson = new Gson();
		 	if(StringUtil.isEmpty(username) || StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(startInit)
		 			||StringUtil.isEmpty(sizeInit)){
				result = new ResultEntity<ListEntity<Sourceconsumerhis>>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
		 	if(!ApptokenUtils.compare(username, apptoken)){
				result = new ResultEntity<ListEntity<Sourceconsumerhis>>(2, "app_token error!", null);
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
				result = new ResultEntity<ListEntity<Sourceconsumerhis>>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			ListEntity<Sourceconsumerhis> entityList = new ListEntity<Sourceconsumerhis>();
			Sourceconsumerhis query = new Sourceconsumerhis();
			query.setUserid(username);
			query.setToPage(Integer.valueOf(startStr));
			query.setPerPageSize(Integer.valueOf(sizeStr));
			PageList<Sourceconsumerhis> pagelist = consumingHisService.getPageList(query);
			if(pagelist!=null&&pagelist.size()>0){
				query.setTotalItem(pagelist.size());	
				int totalPage=query.getTotalPage();
				if(Integer.valueOf(startStr)<totalPage){
					entityList.setOvered(true);
				}else{
					entityList.setOvered(false);
				}
				//获取兑换记录的商品id，并通过id获取其商品图片的第一张图片、商品名称
				FileUntils fileUntils = new FileUntils();
				for (Sourceconsumerhis sourceconsumerhis : pagelist) {
					SourcegoodsQuery goods=new SourcegoodsQuery(sourceconsumerhis.getGoodsid());
					Sourcegoods sourcegoods=goodsService.findById(goods);
					if(sourcegoods!=null){
						String picpath =StringUtils.split(sourcegoods.getPicpaths(),",")[0];
						if(picpath!=null){
							sourceconsumerhis.setGoodspicPath(fileUntils.getImageHost()+picpath);
						}
						sourceconsumerhis.setGoodsname(sourcegoods.getGoodsname());
					}
				}
				entityList.setItemList(pagelist);
				result = new ResultEntity<ListEntity<Sourceconsumerhis>>(1, "成功", entityList);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}else{
				entityList.setOvered(true);
				entityList.setItemList(pagelist);
				result = new ResultEntity<ListEntity<Sourceconsumerhis>>(1, "成功", entityList);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
        }catch (Exception e) {
        	logger.error("获取积分兑换历史列表getSourceConsumerHis err：");
			logger.error(e, e.fillInStackTrace());
        }
	}
	
	
	public void findByGoodsId(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
        try {
        	PrintWriter out = response.getWriter();
        	String username = null;
       	    ResultEntity<Sourcegoods> result =null;
       	    if(StringUtils.isNotBlank(request.getParameter("username"))){
	     		username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	     	}
       	    String apptoken = request.getParameter("apptoken");
       	    String goodsid=StringUtil.isEmpty(request.getParameter("goodsid")) ? "" : request.getParameter("goodsid");
       	    Gson gson = new Gson();
		 	if(StringUtil.isEmpty(username) || StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(goodsid)){
				result = new ResultEntity<Sourcegoods>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
		 	if(!ApptokenUtils.compare(username, apptoken)){
				result = new ResultEntity<Sourcegoods>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
		 	try {
				username = CodeUtil.decode(username, apptoken);
				goodsid = CodeUtil.decode(goodsid, apptoken);
			} catch (Exception e) {
				result = new ResultEntity<Sourcegoods>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			SourcegoodsQuery query=new SourcegoodsQuery();
			query.setGoodsid(goodsid);
			Sourcegoods sourcegoods=goodsService.findById(query);
			if(sourcegoods!=null){
				//商品的图片集
				FileUntils fileUntils = new FileUntils();
				List<String> picCol=new ArrayList<String>();
				String readlPath=request.getSession().getServletContext().getRealPath("/");	
				String[] picid =StringUtils.split(sourcegoods.getPicids(),",");
				String[] picpath =StringUtils.split(sourcegoods.getPicpaths(),",");
				if(picpath!=null){
					for (int i=0;i<picpath.length;i++) {
						String filePath=readlPath+picpath[i].substring(0,picpath[i].lastIndexOf("/")+1);
						filePath=filePath.replace("\\", "/");
						File folder = new File(filePath);
						if(!folder.exists()){
							folder.mkdir();
						}
						File file = new File(filePath,picpath[i].substring(picpath[i].lastIndexOf("/")+1,picpath[i].length()));
						if(!file.exists()){
							file.createNewFile();
						
							String imgid=picid[i];
							ImageDB imageDB=ImageDBUtil.getImageDBByGuid(imgid);
							if(imageDB==null){
								logger.error("该商品数据库图片不存在，路径也不存在！");
								continue;
							}
							byte[] content=imageDB.getFileContent();
							FileOutputStream fileOutputStream = new FileOutputStream(file);
							fileOutputStream.write(content,0,content.length);
							fileOutputStream.close();
						}
						picCol.add(fileUntils.getImageHost()+picpath[i]);
					}
				}
				sourcegoods.setPicPathList(picCol);
				//如果都成功，则返回true
				result = new ResultEntity<Sourcegoods>(1, "成功",sourcegoods);
			}else{
				result = new ResultEntity<Sourcegoods>(0, "暂无数据", null);
			}
			out.write(gson.toJson(result));
			out.flush();
			out.close();
        }catch (Exception e) {
        	logger.error("积分商品详情findByGoodsId err：");
			logger.error(e, e.fillInStackTrace());
        }	
		
	}
	
	/**
	 * @description 积分签到历史列表
	 * @param apptoken
	 * @param username
	 * @param start
	 * @param size
	 * @return ResultEntity<ListEntity<Sourcesigninhis>>
	 * @author yangbilin
	 * @createtime 2017-07-11
	 * 
	 */
	public void getSourceIncomeHis(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
        try {
        	PrintWriter out = response.getWriter();
        	String username = null;
       	 	String startStr = null;
       	 	String sizeStr=null;
       	    ResultEntity<ListEntity<Sourcesigninhis>> result =null;
       	    if(StringUtils.isNotBlank(request.getParameter("username"))){
	     		username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	     	}
       	    String apptoken = request.getParameter("apptoken");
			
			String startInit=StringUtil.isEmpty(request.getParameter("start")) ? "" : request.getParameter("start");
		 	String sizeInit=StringUtil.isEmpty(request.getParameter("size")) ? "" : request.getParameter("size");
		 	
		 	Gson gson = new Gson();
		 	if(StringUtil.isEmpty(username) || StringUtil.isEmpty(apptoken)||StringUtil.isEmpty(startInit)
		 			||StringUtil.isEmpty(sizeInit)){
				result = new ResultEntity<ListEntity<Sourcesigninhis>>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
		 	if(!ApptokenUtils.compare(username, apptoken)){
				result = new ResultEntity<ListEntity<Sourcesigninhis>>(2, "app_token error!", null);
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
				result = new ResultEntity<ListEntity<Sourcesigninhis>>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			ListEntity<Sourcesigninhis> entityList = new ListEntity<Sourcesigninhis>();
			Sourcesigninhis query = new Sourcesigninhis();
			query.setToPage(Integer.valueOf(startStr));
			query.setPerPageSize(Integer.valueOf(sizeStr));
			query.setUserid(username);
			PageList<Sourcesigninhis> pagelist = sourceSignService.getPageList(query);
			if(pagelist!=null&&pagelist.size()>0){
				query.setTotalItem(pagelist.size());	
				int totalPage=query.getTotalPage();
				if(Integer.valueOf(startStr)<totalPage){
					entityList.setOvered(true);
				}else{
					entityList.setOvered(false);
				}
				entityList.setItemList(pagelist);
				result = new ResultEntity<ListEntity<Sourcesigninhis>>(1, "成功", entityList);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}else{
				entityList.setOvered(true);
				entityList.setItemList(pagelist);
				result = new ResultEntity<ListEntity<Sourcesigninhis>>(1, "成功", entityList);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
        }catch (Exception e) {
	        	logger.error("获取积分签到列表getSourceIncomeHis err：");
				logger.error(e, e.fillInStackTrace());
	    }
	}
	/**
	 *积分排行榜前10以及个人数据
	 *@param username
	 *@return  ResultEntity<ListEntity<YhglModel>>
	 *@author yangbilin 
	 */
	public void getSourcerankingList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
        try {
        	PrintWriter out = response.getWriter();
        	String username = null;
        	String lxmc = null;
        	ResultEntity<ListEntity<YhglModel>> result =null;
       	    if(StringUtils.isNotBlank(request.getParameter("username"))){
	     		username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	     	}
       	    String apptoken = request.getParameter("apptoken");
       	    lxmc = request.getParameter("lxmc");
       	    
       	    Gson gson = new Gson();
		 	if(StringUtil.isEmpty(username) || StringUtil.isEmpty(apptoken)){
				result = new ResultEntity<ListEntity<YhglModel>>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
		 	if(!ApptokenUtils.compare(username, apptoken)){
				result = new ResultEntity<ListEntity<YhglModel>>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
		 	try {
				username = CodeUtil.decode(username, apptoken);
				lxmc = CodeUtil.decode(lxmc, apptoken);
				
			} catch (Exception e) {
				result = new ResultEntity<ListEntity<YhglModel>>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			String headPicturePath="";
			ListEntity<YhglModel> entityList = new ListEntity<YhglModel>();
			//获取用户个人
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("username", username);
			List<YhglModel> personal= yhglService.getRankinglist(params);
			if(personal!=null&&personal.size()>0){
				YhglModel userOne=personal.get(0);
				entityList.setRanking(userOne.getRanking());
			}else{
				result = new ResultEntity<ListEntity<YhglModel>>(0, "获取我的个人信息失败", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			if(StringUtil.isEmpty(lxmc) ){
	       		lxmc = "0";
	       	 }
	       	 if( !"0".equals(lxmc) ){
	       		lxmc = "1";
	       	 }
			
			//获取前10条积分排行
			HashMap<String,Object> param = new HashMap<String,Object>();
			param.put("username", username);
			param.put("lxmc", lxmc);
			//List<YhglModel> limitList= yhglService.getRankinglist(param);
       	    List<YhglModel> limitList= new ArrayList<YhglModel>();
       	 
       	    if( "0".equals(lxmc) ){
       	    	limitList= yhglService.getRankinglist(param);
       	    }else{
       	    	limitList= yhglService.getGradeRankinglist(param);
       	    }
       	 
			//List<YhglModel> limitList= yhglService.getRankinglist(new HashMap<String,Object>());
			if(limitList!=null&&limitList.size()>0){
				for (YhglModel yhglModel : limitList) {
					Map<String,Object> map= getPicpath(headPicturePath,yhglModel.getZgh(),request);
					if(map!=null&&map.size()>0){
						if((Boolean) map.get("issuccess")){
							headPicturePath = (String) map.get("headPicturePath");
							yhglModel.setWjlj(headPicturePath);
						}else{
							result = new ResultEntity<ListEntity<YhglModel>>(0, (String)map.get("msg"), null);
							out.write(gson.toJson(result));
							out.flush();
							out.close();
							return;
						}
					}
				}
				entityList.setItemList(limitList);
				result = new ResultEntity<ListEntity<YhglModel>>(1, "成功", entityList);
			}else{
				entityList.setItemList(limitList);
				result = new ResultEntity<ListEntity<YhglModel>>(1, "成功", entityList);
			}
			entityList.setOvered(false);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
        }catch (Exception e) {
        	logger.error("获取积分排行榜getSourcerankingList err：");
			logger.error(e, e.fillInStackTrace());
	    }
	}
	
	private Map<String,Object> getPicpath(String headPicturePath,String username,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("issuccess", true);
		//我的头像读取
		List<ImageDB> imageList = myPortalHttpService.getMyPicture(username);
		ImageDB image = imageList != null && imageList.size() > 0 ? imageList.get(0) : null;
		if(image == null){
			logger.error("头像数据库图片不存在，路径不存在！");
			headPicturePath = "";
		}else{
			String path = image.getPath();
			byte[] content = image.getFileContent();
			String headname = image.getFileName();
			String filename = StringUtil.isEmpty(headname) ? username+"headPicture" : headname;
			if(content == null && StringUtil.isEmpty(path)){
				logger.error("头像数据库图片不存在，路径不存在！");
				headPicturePath = "";
			}else{
				String pathFile = BaseHolder.getPropertiesValue("MyPicture","headPicture");
				String pathurl = request.getSession().getServletContext().getRealPath("/") + pathFile;
				File newFile = new File(pathurl);
				if (!newFile.exists()) {
					newFile.mkdir();
				}
				String projectUrl = request.getSession().getServletContext().getRealPath("/") + path;
				projectUrl = projectUrl.replace("\\", "/");
				File outFile = new File(projectUrl);
				if (!outFile.exists()) {
					try {
						outFile.createNewFile();
						if(content == null){
							logger.error("头像数据库图片不存在，路径不存在！");
							headPicturePath = "";
						}
						ImageIO.setUseCache(false);
						ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);
						BufferedImage newImage = ImageIO.read(byteArrayInputStream);
						ImageIO.write(newImage, UploadFileUtil.checkedFileName(filename), outFile);
					} catch (IOException e) {
						logger.error("我的门户选项接口myPortalFunction 生成图片产生异常 err：");
						logger.error(e, e.fillInStackTrace());
						map.put("issuccess", false);
						map.put("msg", "获取积分排行数据产生异常");
					}
				} 
				headPicturePath = getImageHost() + path;
			}
		}
		map.put("headPicturePath", headPicturePath);
		return map;
	}
	
	
	/*
	 * @description 获取我的发现
	 * @param username
	 * @param apptoken
	 * @return ResultEntity<MyPortalNewEntity>
	 * @author liucb
	 * @createtime 2018-01-15
	 */
	public void sourceFunctionForWdfx(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
   	 	try {
   	 		String userName = null;
   	 		Gson gson = new Gson();
   	 		PrintWriter out = response.getWriter();
   	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
   	 			userName = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
   	 		}
   	 	    String apptoken = request.getParameter("apptoken");
			
	 		if(StringUtil.isEmpty(userName)|| StringUtil.isEmpty(apptoken)){
				ResultEntity<MyPortalNewEntity> result = new ResultEntity<MyPortalNewEntity>(0, "参数传值出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			if(!ApptokenUtils.compare(userName, apptoken)){
				ResultEntity<MyPortalNewEntity> result = new ResultEntity<MyPortalNewEntity>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			try {
				userName  = CodeUtil.decode(userName, apptoken);
			} catch (Exception e) {
				ResultEntity<MyPortalNewEntity> result = new ResultEntity<MyPortalNewEntity>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}	
		
			if(infromation.equals("0")){
				logger.error("我的门户接口未读未还接口获取："+"userName="+userName+",apptoken="+apptoken);
			}
			
			//获取用户门户所有服务
			IMyPortalService myPortalService = (IMyPortalService) SpringHolder.getBean("myPortalService");
			
			//##############################################################
			/**
			 * 1、先获取m_wdmh表中所有tsgn类型
			 * 2、根据tsgn字段分别获取相关服务列表
			 * 3、对服务列表进行url处理
			 * 4、将服务放进map，key值为特色功能类别实体中lbmc，value值为该组服务列表
			 */
			//门户服务分组列表Map
			Map<String,Object> mhfwFzMap = new HashMap<String, Object>();
			
			//获取m_wdmh表中所有发现的tsgn类型
			List<String> tsgnLbList = myPortalService.getFxLbListInWdmh();
			
			Map<String,Object> tsgnParams = new HashMap<String, Object>();
			tsgnParams.put("userName", userName);
			
			//遍历tsgnLbList，获取相应服务列表
			for(int i=0;i<tsgnLbList.size();i++){
				List<MyPortalEntityItemEntity> entityList = new ArrayList<MyPortalEntityItemEntity>();	
				if(!StringUtil.isEmpty(tsgnLbList.get(i))){
					String tsgn = tsgnLbList.get(i);
					tsgnParams.put("tsgn",tsgn);
					List<MyPortal> apps = myPortalService.getAllMyFx(tsgnParams);
					

					//用户移动端设置的常用服务
					IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
					apps = mobileCommonService.getPortalUrl(apps, userName);
					Map<String, String> param = new HashMap<String, String>();
					param.put("userId", userName);
					
					MyPortalEntityItemEntity entity = null;
					String url = getImageHost();
					if (apps != null && apps.size() > 0) {
						for (MyPortal app : apps) {
							entity = new MyPortalEntityItemEntity();
							entity.id = app.getId();
							String type = app.getType();
							type = type.equals("1") ? "WEB_SERVICE" : 
								   type.equals("0") ? "APP_SERVICE" : " FROM_OTHER";	
							entity.serviceType = type;
							entity.name = app.getName();
							entity.icon = url + app.getTburl();
							entity.url = app.getAddr();
							entity.serviceCode = app.getCode();
							entity.bak ="";
							entity.tsgn=app.getTsgn();
							entity.isfx=app.getIsfx();
							entityList.add(entity);
							if(infromation.equals("0")){
								logger.error("我的门户接口未读未还接口获取entity："+entity);
							}
						}
					}
					mhfwFzMap.put(tsgn, entityList);
					
				}//if语句结束括号
				
			}//循环尾部括号
			
			MyPortalNewEntityWithFwMap myPortalEntity = new MyPortalNewEntityWithFwMap("","","",mhfwFzMap);
			ResultEntity<MyPortalNewEntityWithFwMap> result = new ResultEntity<MyPortalNewEntityWithFwMap>(1, "成功", myPortalEntity);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
   	 	}catch (Exception e) {
   	 		logger.error("我的发现选项接口sourceFunctionForWdfx err：");
   	 		e.printStackTrace();
//			logger.error(e, e.fillInStackTrace());
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
	
	
	public ISourcesigninService getSourceSignService() {
		return sourceSignService;
	}

	public void setSourceSignService(ISourcesigninService sourceSignService) {
		this.sourceSignService = sourceSignService;
	}

	public IYhglService getYhglService() {
		return yhglService;
	}

	public void setYhglService(IYhglService yhglService) {
		this.yhglService = yhglService;
	}
	
	
	public ISourcegoodsService getGoodsService() {
		return goodsService;
	}

	public void setGoodsService(ISourcegoodsService goodsService) {
		this.goodsService = goodsService;
	}

	public ISourceconsumerhisService getConsumingHisService() {
		return consumingHisService;
	}

	public void setConsumingHisService(ISourceconsumerhisService consumingHisService) {
		this.consumingHisService = consumingHisService;
	}
	
	/*public static void main(String[] args) {
	        Scanner sc = new Scanner(System.in);
	        while (sc.hasNext()){
	            int num = sc.nextInt();
	            sourceCount(num);
	        }
	}*/

	//积分等级计算
    public static String[] sourceCount(int num){
    	String sourceLevel = "";
    	String levelLimit = "";
        int a = num / 25;
        int b = (int) (Math.log(a)/Math.log(2))+1;
        if(a==0){
        	b=0;
        	levelLimit = "0,25";
        }else{
        	levelLimit = (int)(25*Math.pow(2,b-1)) +","+(int)(25*Math.pow(2,b));
        }
        sourceLevel = "Lv"+b;
        //System.out.println(sourceLevel+"----"+levelLimit);
        return new String[]{sourceLevel,levelLimit};
    }
  }
