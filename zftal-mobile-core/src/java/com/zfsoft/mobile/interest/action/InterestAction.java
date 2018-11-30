package com.zfsoft.mobile.interest.action;

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

import com.google.gson.Gson;
//import com.ibm.db2.jcc.b.ad;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.zfsoft.common.log.User;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.util.Byte_File_Object;
import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.hrm.file.util.UploadFileUtil;
import com.zfsoft.mobile.ballot.utils.CommonUtils;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.interest.entity.InterestComment;
import com.zfsoft.mobile.interest.entity.InterestCommentQuery;
import com.zfsoft.mobile.interest.entity.InterestEntity;
import com.zfsoft.mobile.interest.entity.InterestPost;
import com.zfsoft.mobile.interest.entity.InterestPostQuery;
import com.zfsoft.mobile.interest.entity.InterestQuery;
import com.zfsoft.mobile.interest.entity.InterestType;
import com.zfsoft.mobile.interest.entity.PersonInfo;
import com.zfsoft.mobile.interest.entity.PersonInfoQuery;
import com.zfsoft.mobile.interest.service.InterestService;
import com.zfsoft.mobile.market.entity.Market;
import com.zfsoft.mobile.market.entity.MarketCampus;
import com.zfsoft.mobile.market.entity.MarketColl;
import com.zfsoft.mobile.market.entity.MarketComment;
import com.zfsoft.mobile.market.entity.MarketCommentQuery;
import com.zfsoft.mobile.market.entity.MarketQuery;
import com.zfsoft.mobile.market.entity.MarketType;
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
public class InterestAction  extends HrmAction {
	private static Logger logger = Logger.getLogger(AppCenterHttpAction.class);
	
	private InterestService interestService;
	
	private MarketService marketService;
	
	private InterestQuery interestQuery = new InterestQuery();
	
	private InterestPostQuery interestPostQuery = new InterestPostQuery();
	
	private InterestCommentQuery interestCommentQuery = new InterestCommentQuery();
	       
	SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private String interestId;
	
	private String status;
	
	//获取圈子分类
	public void typeList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			
			List<InterestType> list = interestService.selectTypeList();
			
			ResultEntity<List<InterestType>> result = new ResultEntity<List<InterestType>>(1, "", list);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
			
		} catch (IOException e) {
			logger.error("typeList......error");
			e.printStackTrace();
		}
	}
	
	//圈子列表
	public void interestList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			
			String apptoken = null; 
			String start = null;
			String size = null;
			String type = null;
			String username = null;//用户id判断是否已加入圈子
			String name = null;//搜索条件
			String orderCode = null;//排序条件 1为按帖子数降序, 2为我的圈子列表
			
			username = StringUtil.isEmpty(request.getParameter("username")) ? "" : request.getParameter("username");
			name = StringUtil.isEmpty(request.getParameter("name")) ? "" : request.getParameter("name");
			orderCode = StringUtil.isEmpty(request.getParameter("orderCode")) ? "" : request.getParameter("orderCode");
			type = StringUtil.isEmpty(request.getParameter("type")) ? "" : request.getParameter("type");
			start = StringUtil.isEmpty(request.getParameter("start")) ? "" : request.getParameter("start");
			size = StringUtil.isEmpty(request.getParameter("size")) ? "" : request.getParameter("size");
			apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			try {
				username     = CodeUtil.decode(username, apptoken);
				name     = CodeUtil.decode(name, apptoken);
				orderCode     = CodeUtil.decode(orderCode, apptoken);
				type     = CodeUtil.decode(type, apptoken);
				start        = CodeUtil.decode(start, apptoken);
				size         = CodeUtil.decode(size, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			InterestQuery interestQuery = new InterestQuery();
			interestQuery.setToPage(Integer.valueOf(start));
			interestQuery.setPerPageSize(Integer.valueOf(size));
			interestQuery.setType(type);
			interestQuery.setName(name);
			interestQuery.setUserId(username);
			interestQuery.setOrderCode(orderCode);
			interestQuery.setStatus("1");
			List<InterestEntity> list = interestService.selectInterestListByType(interestQuery);
			
			
			ListEntity<InterestEntity> resultList = new ListEntity<InterestEntity>();
			resultList.setItemList(list);
			if(list == null || list.size() < Integer.valueOf(size))	{
				resultList.setOvered(true);
			}else{
				resultList.setOvered(false);
			}
			ResultEntity<ListEntity<InterestEntity>> result = new ResultEntity<ListEntity<InterestEntity>>(1, "成功", resultList);
			System.out.println(gson.toJson(result));
		    out.write(gson.toJson(result));
		    out.flush();
		    out.close();
			
			
		} catch (IOException e) {
			logger.error("interestList......error");
			e.printStackTrace();
		}
	}
	
	
	//圈子信息
	public void interestInfo(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			
			String apptoken = null; 
			String username = null;//用户id判断是否已加入圈子
			String interestId = null;
			
			username = StringUtil.isEmpty(request.getParameter("username")) ? "" : request.getParameter("username");
			interestId = StringUtil.isEmpty(request.getParameter("interestId")) ? "" : request.getParameter("interestId");
			apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			try {
				username     = CodeUtil.decode(username, apptoken);
				interestId     = CodeUtil.decode(interestId, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			InterestQuery interestQuery = new InterestQuery();
			interestQuery.setUserId(username);
			interestQuery.setId(interestId);
			InterestEntity model = interestService.selectInterestInfo(interestQuery);
			
			ResultEntity<InterestEntity> result = new ResultEntity<InterestEntity>(1, "成功", model);
			System.out.println(gson.toJson(result));
			out.write(gson.toJson(result));
			out.flush();
			out.close();
			
			
		} catch (IOException e) {
			logger.error("interestList......error");
			e.printStackTrace();
		}
	}
	
	//加入圈子
	public void joinInterest(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			
			String apptoken = null; 
			String username = null; 
			String interestId = null;
			
			interestId =  new String(request.getParameter("interestId").getBytes("ISO8859-1"), "UTF-8");
			username =  new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
			apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			if(!ApptokenUtils.compare(apptoken)){
			ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
			}
			try {
				interestId     = CodeUtil.decode(interestId, apptoken);
				username     = CodeUtil.decode(username, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			PersonInfo personInfo = new PersonInfo();
			personInfo.setUserId(username);
			personInfo.setType("1");
			personInfo.setInterestId(interestId);
			
			personInfo.setCreateTime(sd.format(new Date()));
			//校验是否已加入圈子
			
			interestService.insertPersonInfo(personInfo);
			
			ResultEntity<String> result = new ResultEntity<String>(1, "成功", "成功");
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
		} catch (IOException e) {
			logger.error("joinInterest......error");
			e.printStackTrace();
		}
	}
	
	
	
	
	//退出圈子
	public void exitInterest(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			
			String apptoken = null; 
			String username = null; 
			String interestId = null;
			
			interestId =  new String(request.getParameter("interestId").getBytes("ISO8859-1"), "UTF-8");
			username =  new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
			apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			if(!ApptokenUtils.compare(apptoken)){
			ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
			}
			try {
				interestId     = CodeUtil.decode(interestId, apptoken);
				username     = CodeUtil.decode(username, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			PersonInfo personInfo = new PersonInfo();
			personInfo.setUserId(username);
			personInfo.setInterestId(interestId);
			
			interestService.exitInterest(personInfo);
			
			ResultEntity<String> result = new ResultEntity<String>(1, "成功", "成功");
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
		} catch (IOException e) {
			logger.error("exitInterest......error");
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	//创建圈子
	public void createInterest(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			
			String apptoken = null; 
			String username = null; 
			String name = null;//圈子名称
			String img = null;//圈子图片
			String content = null;//圈子描述
			String type = null;//圈子所属分类
			String title = null;//圈子成员头衔
			String idCard = null;//学生证
			String postName = null;//帖子名称
			
			String id = null;//更新时圈子id
			
			img = StringUtil.isEmpty(request.getParameter("img")) ? "" : request.getParameter("img");
			content = StringUtil.isEmpty(request.getParameter("content")) ? "" : request.getParameter("content");
			type = StringUtil.isEmpty(request.getParameter("type")) ? "" : request.getParameter("type");
			title = StringUtil.isEmpty(request.getParameter("title")) ? "" : request.getParameter("title");
			idCard = StringUtil.isEmpty(request.getParameter("idCard")) ? "" : request.getParameter("idCard");
			name = StringUtil.isEmpty(request.getParameter("name")) ? "" : request.getParameter("name");
			username = StringUtil.isEmpty(request.getParameter("username")) ? "" : request.getParameter("username");
			id = StringUtil.isEmpty(request.getParameter("id")) ? "" : request.getParameter("id");
			postName = StringUtil.isEmpty(request.getParameter("postName")) ? "" : request.getParameter("postName");
			apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
			ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
			}
			try {
				id     = CodeUtil.decode(id, apptoken);
				img     = CodeUtil.decode(img, apptoken);
				content     = CodeUtil.decode(content, apptoken);
				type     = CodeUtil.decode(type, apptoken);
				title     = CodeUtil.decode(title, apptoken);
				idCard     = CodeUtil.decode(idCard, apptoken);
				name     = CodeUtil.decode(name, apptoken);
				postName     = CodeUtil.decode(postName, apptoken);
				username     = CodeUtil.decode(username, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			InterestEntity interestEntity = new InterestEntity();
			interestEntity.setName(name);
			interestEntity.setImg(img);
			interestEntity.setCreateUserId(username);
			interestEntity.setFollow(0);
			interestEntity.setStatus("0");
			interestEntity.setContent(content);
			interestEntity.setType(type);
			interestEntity.setPostNum(0);
			interestEntity.setIdCard(idCard);
			interestEntity.setTitle(title);
			interestEntity.setBlackNum(0);
			interestEntity.setAdminisId(username);
			interestEntity.setPostName(postName);
			interestEntity.setCreateTime(sd.format(new Date()));
			if (StringUtils.isNotBlank(id)) {
				
				//校验登录人是否为管理员
				InterestEntity checkEntity = interestService.getInterestById(id);
				String admin = interestEntity.getAdminisId();
				if (username.equals(admin)) {
					interestEntity.setId(id);
					interestService.updateInterestById(interestEntity);
				}else {
					ResultEntity<String> result = new ResultEntity<String>(0, "你没有权限", "你没有权限");
			        out.write(gson.toJson(result));
			        out.flush();
			        out.close();
				}
			}else {
				interestService.insertInterest(interestEntity);
			}
			
			ResultEntity<String> result = new ResultEntity<String>(1, "成功", "成功");
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
		} catch (IOException e) {
			logger.error("joinInterest......error");
			e.printStackTrace();
		}
	}
	
	//圈子帖子列表
	public void postList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			String apptoken = null; 
			String username = null; //用户名查看是否点赞
			String interestId = null;//圈子id
			String size = null;
			String start = null;
			
			interestId =  new String(request.getParameter("interestId").getBytes("ISO8859-1"), "UTF-8");
			username =  new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
			start = StringUtil.isEmpty(request.getParameter("start")) ? "" : request.getParameter("start");
			size = StringUtil.isEmpty(request.getParameter("size")) ? "" : request.getParameter("size");
			apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				}
			try {
				interestId     = CodeUtil.decode(interestId, apptoken);
				username     = CodeUtil.decode(username, apptoken);
				start     = CodeUtil.decode(start, apptoken);
				size     = CodeUtil.decode(size, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			InterestPostQuery interestPostQuery = new InterestPostQuery();
			interestPostQuery.setToPage(Integer.valueOf(start));
			interestPostQuery.setPerPageSize(Integer.valueOf(size));
			interestPostQuery.setUserId(username);
			interestPostQuery.setInterestId(interestId);
			
			List<InterestPost> list = interestService.getInterestPostList(interestPostQuery);
			//获取头像及用户名
			for (InterestPost interestPost : list) {
				interestPost.setUsername(marketService.getUserNameById(interestPost.getCreateUserId()));
				interestPost.setHeadImg(getheadImg(interestPost.getCreateUserId(), request));
			}
			
			ListEntity<InterestPost> resultList = new ListEntity<InterestPost>();
			resultList.setItemList(list);
			if(list == null || list.size() < Integer.valueOf(size))	{
				resultList.setOvered(true);
			}else{
				resultList.setOvered(false);
			}
			ResultEntity<ListEntity<InterestPost>> result = new ResultEntity<ListEntity<InterestPost>>(1, "成功", resultList);
			System.out.println(gson.toJson(result));
		    out.write(gson.toJson(result));
		    out.flush();
		    out.close();
		} catch (IOException e) {
			logger.error("postList......error");
			e.printStackTrace();
		}
	}
	
	//发表帖子
	public void insertPost(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			String apptoken = null; 
			String username = null; //发布人
			String interestId = null;//所属圈子id
			String content = null;//帖子内容
			String img = null;//帖子文件
			String address = null;//帖子位置信息
			
			username =  new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
			interestId =  new String(request.getParameter("interestId").getBytes("ISO8859-1"), "UTF-8");
			content = StringUtil.isEmpty(request.getParameter("content")) ? "" : request.getParameter("content");
			img = StringUtil.isEmpty(request.getParameter("img")) ? "" : request.getParameter("img");
			address = StringUtil.isEmpty(request.getParameter("address")) ? "" : request.getParameter("address");
			apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				}
			try {
				interestId     = CodeUtil.decode(interestId, apptoken);
				content     = CodeUtil.decode(content, apptoken);
				img     = CodeUtil.decode(img, apptoken);
				address     = CodeUtil.decode(address, apptoken);
				username     = CodeUtil.decode(username, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			InterestPost interestPost = new InterestPost();
			interestPost.setInterestId(interestId);
			interestPost.setContent(content);
			interestPost.setImg(img);
			interestPost.setAddress(address);
			interestPost.setGiveNum(0);
			interestPost.setCollNum(0);
			interestPost.setIsTop(0);
			interestPost.setCreateUserId(username);
			interestPost.setCreateTime(sd.format(new Date()));
			
			interestService.insertPost(interestPost);
			
			ResultEntity<String> result = new ResultEntity<String>(1, "成功", "成功");
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
		} catch (IOException e) {
			logger.error("typeList......error");
			e.printStackTrace();
		}
	}
	
	//帖子点赞
	public void interestPostGive(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			
			String apptoken = null; 
			String username = null; 
			String interestId = null;
			
			interestId =  new String(request.getParameter("interestId").getBytes("ISO8859-1"), "UTF-8");
			username =  new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
			apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			if(!ApptokenUtils.compare(apptoken)){
			ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
			}
			try {
				interestId     = CodeUtil.decode(interestId, apptoken);
				username     = CodeUtil.decode(username, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			PersonInfo personInfo = new PersonInfo();
			personInfo.setUserId(username);
			personInfo.setType("3");
			personInfo.setInterestId(interestId);
			personInfo.setCreateTime(sd.format(new Date()));
			interestService.insertPersonInfo(personInfo);
			//帖子更新点赞数
			InterestPost interestPost = interestService.getInterestPostById(interestId);
			interestPost.setGiveNum(interestPost.getGiveNum() + 1);
			interestService.updateInterestPost(interestPost);
			
			
			ResultEntity<String> result = new ResultEntity<String>(1, "成功", "成功");
			
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
		} catch (IOException e) {
			logger.error("interestPostGive......error");
			e.printStackTrace();
		}
	}
	
	//改变人员状态 ，拉黑或移出黑名单
	public void pullToBlack(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			
			String apptoken = null; 
			String username = null; 
			String interestId = null;
			String userId = null;//被拉黑的用户id
			String type = null;
			
			interestId =  new String(request.getParameter("interestId").getBytes("ISO8859-1"), "UTF-8");
			type =  new String(request.getParameter("type").getBytes("ISO8859-1"), "UTF-8");
			username =  new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
			userId =  new String(request.getParameter("userId").getBytes("ISO8859-1"), "UTF-8");
			apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
			ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
			}
			try {
				interestId     = CodeUtil.decode(interestId, apptoken);
				type     = CodeUtil.decode(type, apptoken);
				username     = CodeUtil.decode(username, apptoken);
				userId     = CodeUtil.decode(userId, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			ResultEntity<String> result;
			//校验登录人是否为管理员
			InterestEntity interestEntity = interestService.getInterestById(interestId);
			String admin = interestEntity.getAdminisId();
			if (username.equals(admin)) {
				//改变状态
				PersonInfo personInfo = new PersonInfo();
				personInfo.setCreateUserId(username);
				personInfo.setInterestId(interestId);
				personInfo.setUserId(userId);
				personInfo.setType(type);
				interestService.updatePersoninfo(personInfo);
				result = new ResultEntity<String>(1, "成功", "成功");
			}else {
				result = new ResultEntity<String>(0, "你没有权限", "你没有权限");
			}
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
		} catch (IOException e) {
			logger.error("pullToBlack......error");
			e.printStackTrace();
		}
	}
	
	//管理员置顶帖子
	public void topPost(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			String apptoken = null; 
			String username = null; 
			String interestId = null;
			String postId = null;//帖子id
			
			postId =  new String(request.getParameter("postId").getBytes("ISO8859-1"), "UTF-8");
			interestId =  new String(request.getParameter("interestId").getBytes("ISO8859-1"), "UTF-8");
			username =  new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
			apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			try {
				interestId     = CodeUtil.decode(interestId, apptoken);
				username     = CodeUtil.decode(username, apptoken);
				postId     = CodeUtil.decode(postId, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			ResultEntity<String> result;
			//校验登录人是否为管理员
			InterestEntity interestEntity = interestService.getInterestById(interestId);
			String admin = interestEntity.getAdminisId();
			if (username.equals(admin)) {
				//置顶帖子
				//将该圈子所有帖子设为不置顶
				interestService.setPostNotTop(interestId);
				//将该帖子置顶
				interestService.setPostTop(postId);
				result = new ResultEntity<String>(1, "成功", "成功");
			}else {
				result = new ResultEntity<String>(0, "你没有权限", "你没有权限");
			}
			out.write(gson.toJson(result));
	        out.flush();
	        out.close();
		} catch (IOException e) {
			logger.error("topPost......error");
			e.printStackTrace();
		}
	}
	
	//举报帖子
	public void reportPost(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			String apptoken = null; 
			String username = null; 
			String postId = null;//帖子id.
			String reportId = null;//举报原因
			
			reportId =  new String(request.getParameter("reportId").getBytes("ISO8859-1"), "UTF-8");
			postId =  new String(request.getParameter("postId").getBytes("ISO8859-1"), "UTF-8");
			username =  new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
			apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
			ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
			}
			try {
				postId     = CodeUtil.decode(postId, apptoken);
				username     = CodeUtil.decode(username, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			
			PersonInfo personInfo = new PersonInfo();
			personInfo.setUserId(username);
			personInfo.setType("4");
			personInfo.setInterestId(postId);
			personInfo.setCreateTime(sd.format(new Date()));
			personInfo.setReportId(reportId);
			
			interestService.insertPersonInfo(personInfo);
			
			ResultEntity<String> result = new ResultEntity<String>(1, "成功", "成功");
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
		} catch (IOException e) {
			logger.error("reportPost......error");
			e.printStackTrace();
		}
	}
	
	//删除帖子
	public void delPost(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			String apptoken = null; 
			String username = null; 
			String interestId = null;
			String postId = null;//帖子id
			String code = null;//0删除本条帖子  1.删除该发布者所有帖子
			
			code =  new String(request.getParameter("code").getBytes("ISO8859-1"), "UTF-8");
			postId =  new String(request.getParameter("postId").getBytes("ISO8859-1"), "UTF-8");
			interestId =  new String(request.getParameter("interestId").getBytes("ISO8859-1"), "UTF-8");
			username =  new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
			apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			try {
				interestId     = CodeUtil.decode(interestId, apptoken);
				username     = CodeUtil.decode(username, apptoken);
				postId     = CodeUtil.decode(postId, apptoken);
				code     = CodeUtil.decode(code, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			ResultEntity<String> result;
			//校验登录人是否为管理员
			InterestEntity interestEntity = interestService.getInterestById(interestId);
			String admin = interestEntity.getAdminisId();
			if (username.equals(admin)) {
				String userId = null;
				if ("0".equals(code)) {
					//删除本条帖子
					interestService.delPost(postId,userId);
				}else {
					//删除该发布者所有帖子
					userId = interestService.getInterestPostById(postId).getCreateUserId();
					interestService.delPost(postId,userId);
				}
				result = new ResultEntity<String>(1, "成功", "成功");
			}else {
				result = new ResultEntity<String>(0, "你没有权限", "你没有权限");
			}
			out.write(gson.toJson(result));
	        out.flush();
	        out.close();
		} catch (IOException e) {
			logger.error("typeList......error");
			e.printStackTrace();
		}
	}
	
	//圈子成员列表
	public void presonList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			
			String apptoken = null; 
			String interestId = null;
			String name = null;
			String type = null;
			String size = null;
			String start = null;
			
			interestId =  new String(request.getParameter("interestId").getBytes("ISO8859-1"), "UTF-8");
			type =  new String(request.getParameter("type").getBytes("ISO8859-1"), "UTF-8");
			name = StringUtil.isEmpty(request.getParameter("name")) ? "" : request.getParameter("name");
			
			start = StringUtil.isEmpty(request.getParameter("start")) ? "" : request.getParameter("start");
			size = StringUtil.isEmpty(request.getParameter("size")) ? "" : request.getParameter("size");
			apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			try {
				interestId     = CodeUtil.decode(interestId, apptoken);
				type     = CodeUtil.decode(type, apptoken);
				name     = CodeUtil.decode(name, apptoken);
				start     = CodeUtil.decode(start, apptoken);
				size     = CodeUtil.decode(size, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			PersonInfoQuery personInfo = new PersonInfoQuery();
			personInfo.setInterestId(interestId);
			personInfo.setType(type);
			personInfo.setName(name);
			personInfo.setToPage(Integer.valueOf(start));
			personInfo.setPerPageSize(Integer.valueOf(size));
			
			//成员列表信息
			List<PersonInfo> list = interestService.getPersonInfoList(personInfo);
			
			ListEntity<PersonInfo> resultList = new ListEntity<PersonInfo>();
			resultList.setItemList(list);
			if(list == null || list.size() < Integer.valueOf(size))	{
				resultList.setOvered(true);
			}else{
				resultList.setOvered(false);
			}
			
			ResultEntity<ListEntity<PersonInfo>> result = new ResultEntity<ListEntity<PersonInfo>>(1, "成功", resultList);
			System.out.println(gson.toJson(result));
		    out.write(gson.toJson(result));
		    out.flush();
		    out.close();
			
		} catch (IOException e) {
			logger.error("presonList......error");
			e.printStackTrace();
		}
	}

	//转让管理员
	public void transferAdmin(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			
			String apptoken = null; 
			String interestId = null;
			String username = null;
			String userId = null;
			
			interestId =  new String(request.getParameter("interestId").getBytes("ISO8859-1"), "UTF-8");
			username =  new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
			userId =  new String(request.getParameter("userId").getBytes("ISO8859-1"), "UTF-8");
			apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			try {
				interestId     = CodeUtil.decode(interestId, apptoken);
				username     = CodeUtil.decode(username, apptoken);
				userId     = CodeUtil.decode(userId, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			ResultEntity<String> result;
			//校验登录人是否为管理员
			InterestEntity interestEntity = interestService.getInterestById(interestId);
			String admin = interestEntity.getAdminisId();
			if (username.equals(admin)) {
				//转让管理员
				interestEntity.setAdminisId(userId);
				interestService.updateInterestById(interestEntity);
				
				result = new ResultEntity<String>(1, "成功", "成功");
			}else {
				result = new ResultEntity<String>(0, "你没有权限", "你没有权限");
			}
			out.write(gson.toJson(result));
	        out.flush();
	        out.close();
		} catch (IOException e) {
			logger.error("transferAdmin......error");
			e.printStackTrace();
		}
	}
	
	//添加评论
	public void addComment(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			
			String apptoken = null; 
			String postId = null;//帖子id
			String username = null;
			String commentId = null;
			String commentUserId = null;
			String content = null;
			
			apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			postId = StringUtil.isEmpty(request.getParameter("postId")) ? "" : request.getParameter("postId");
			username = StringUtil.isEmpty(request.getParameter("username")) ? "" : request.getParameter("username");
			commentId = StringUtil.isEmpty(request.getParameter("commentId")) ? "" : request.getParameter("commentId");
			commentUserId = StringUtil.isEmpty(request.getParameter("commentUserId")) ? "" : request.getParameter("commentUserId");
			content = StringUtil.isEmpty(request.getParameter("content")) ? "" : request.getParameter("content");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			try {
				username     = CodeUtil.decode(username, apptoken);
				postId     = CodeUtil.decode(postId, apptoken);
				commentId     = CodeUtil.decode(commentId, apptoken);
				commentUserId     = CodeUtil.decode(commentUserId, apptoken);
				content     = CodeUtil.decode(content, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			InterestComment interestComment = new InterestComment();
			interestComment.setInterPostId(postId);
			interestComment.setUserId(username);
			interestComment.setCommentId(commentId);
			interestComment.setCommentUserId(commentUserId);
			interestComment.setCreateTime(sd.format(new Date()));
			interestComment.setContent(content);
			
			interestService.addComment(interestComment);
			
			ResultEntity<String> result = new ResultEntity<String>(1, "成功", "成功");
	        out.write(gson.toJson(result));
	        out.flush();
	        out.close();
		} catch (IOException e) {
			logger.error("addComment......error");
			e.printStackTrace();
		}
	}
	
	//获取评论列表
	public void commentList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			
			String apptoken = null; 
			String postId = null;//帖子id
		    
			apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			postId = StringUtil.isEmpty(request.getParameter("postId")) ? "" : request.getParameter("postId");
			
			if(!ApptokenUtils.compare(apptoken)){
			ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
			}
			try {
				postId     = CodeUtil.decode(postId, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			List<InterestComment> list = interestService.getCommentList(postId,null);
			for (InterestComment interestComment : list) {
				List<InterestComment> comments = interestService.getCommentList(postId, interestComment.getId());
				for (InterestComment comment : comments) {
					//获取用户昵称加头像
					comment.setUserName(marketService.getUserNameById(comment.getUserId()));
					comment.setHeadPic(getheadImg(comment.getUserId(), request));
					if (StringUtils.isNotBlank(comment.getCommentUserId())) {
						comment.setCommentName(marketService.getUserNameById(comment.getCommentUserId()));
					}
				}
				interestComment.setComments(comments);
				interestComment.setUserName(marketService.getUserNameById(interestComment.getUserId()));
				interestComment.setHeadPic(getheadImg(interestComment.getUserId(), request));
			}
			
			ResultEntity<List<InterestComment>> result = new ResultEntity<List<InterestComment>>(1, "成功", list);
			System.out.println(gson.toJson(result));
		    out.write(gson.toJson(result));
		    out.flush();
		    out.close();
		} catch (IOException e) {
			logger.error("commentList......error");
			e.printStackTrace();
		}
	}
	
	
	/*=====================================================*/
	//后台页面接口
	//后台圈子列表
	public String htInterestList(){
		//TODO
		//是否已经加入圈子标志参数 isJoin 0未加入 ，1或大于1已加入
		User user = getUser();
		List<InterestEntity> list = interestService.selectInterestList(interestQuery);
		this.getValueStack().set("list", list);
		return "interestList";
	}
	
	//后台圈子帖子列表
	public String htInterestPostList(){
		interestPostQuery.setUserId("0");
		List<InterestPost> list = interestService.getInterestPostList(interestPostQuery);
		this.getValueStack().set("list", list);
		return "postList";
	}
	
	//后台评论列表
	public String htCommentList(){
		List<InterestComment> list = interestService.gethtCommentList(interestCommentQuery);
		
		this.getValueStack().set("list", list);
		return "commentList";
	}
	
	//后台审核圈子
	public String checkInterest(){
		InterestEntity interestEntity = new InterestEntity();
		interestEntity.setId(interestId);
		interestEntity.setStatus(status);
		interestService.updateInterestById(interestEntity);
		
		this.setSuccessMessage("操作成功");
		this.getValueStack().set("data", this.getMessage());
		return "data";
	}
	
	//后台删除圈子
	public String delInterest(){
		interestService.delInterestById(interestId);
		
		this.setSuccessMessage("操作成功");
		this.getValueStack().set("data", this.getMessage());
		return "data";
	}
	
	//后台删除帖子
	public String delInterestPost(){
		interestService.delInterestPostById(interestId);
		
		this.setSuccessMessage("操作成功");
		this.getValueStack().set("data", this.getMessage());
		return "data";
	}
	
	//后台删除评论
	public String delInterestComment(){
		interestService.delInterestCommentById(interestId);
		
		this.setSuccessMessage("操作成功");
		this.getValueStack().set("data", this.getMessage());
		return "data";
	}
	
	//后台获取圈子详情
	public String getInterest(){
		InterestEntity interestEntity =interestService.getInterestById(interestId);
		this.getValueStack().set("interestEntity", interestEntity);
		return "interestDetail";
	}
	
	/*=====================================================*/
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

	public InterestService getInterestService() {
		return interestService;
	}

	public void setInterestService(InterestService interestService) {
		this.interestService = interestService;
	}

	public MarketService getMarketService() {
		return marketService;
	}

	public void setMarketService(MarketService marketService) {
		this.marketService = marketService;
	}

	public InterestQuery getInterestQuery() {
		return interestQuery;
	}

	public void setInterestQuery(InterestQuery interestQuery) {
		this.interestQuery = interestQuery;
	}

	public String getInterestId() {
		return interestId;
	}

	public void setInterestId(String interestId) {
		this.interestId = interestId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public InterestPostQuery getInterestPostQuery() {
		return interestPostQuery;
	}

	public void setInterestPostQuery(InterestPostQuery interestPostQuery) {
		this.interestPostQuery = interestPostQuery;
	}

	public InterestCommentQuery getInterestCommentQuery() {
		return interestCommentQuery;
	}

	public void setInterestCommentQuery(InterestCommentQuery interestCommentQuery) {
		this.interestCommentQuery = interestCommentQuery;
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
