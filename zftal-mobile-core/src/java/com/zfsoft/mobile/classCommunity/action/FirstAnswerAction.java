package com.zfsoft.mobile.classCommunity.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
//import com.ibm.icu.text.SimpleDateFormat;
import com.zfsoft.mobile.classCommunity.entity.AnswerEntity;
import com.zfsoft.mobile.classCommunity.entity.ClassEntity;
import com.zfsoft.mobile.classCommunity.entity.FirstAnswerEntity;
import com.zfsoft.mobile.classCommunity.entity.FirstAnswerItemEntity;
import com.zfsoft.mobile.classCommunity.entity.FirstAnswerQuery;
import com.zfsoft.mobile.classCommunity.service.IFirstAnswerService;
import com.zfsoft.mobile.peEvaluation.query.PEDataQuery;
import com.zfsoft.mobile.servlet.entity.ListEntity;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;

/**
 * 抢答action
 * @author H110MF
 *
 */
public class FirstAnswerAction {

	private IFirstAnswerService firstAnswerService;

	private FirstAnswerQuery firstAnswerQuery = new FirstAnswerQuery();
	
	public void getList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		//String username=null;
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
			//username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
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
				//username     = CodeUtil.decode(username, apptoken);
				className    = CodeUtil.decode(className, apptoken);
				start        = CodeUtil.decode(start, apptoken);
				size         = CodeUtil.decode(size, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			firstAnswerQuery.setClassName(className);
			firstAnswerQuery.setToPage(Integer.valueOf(start));
			firstAnswerQuery.setPerPageSize(Integer.valueOf(size));
			List<FirstAnswerEntity> list = firstAnswerService.getList(firstAnswerQuery);
		
			ListEntity<FirstAnswerEntity> resultList = new ListEntity<FirstAnswerEntity>();
			resultList.setItemList(list);
			if(list == null || list.size() < Integer.valueOf(size))	{
				resultList.setOvered(true);
			}else{
				resultList.setOvered(false);
			}
			
			ResultEntity<ListEntity<FirstAnswerEntity>> result = new ResultEntity<ListEntity<FirstAnswerEntity>>(1, "成功", resultList);
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
	
	//选择题选项列表
	public void getItemList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String id;
		String apptoken = null;
		
		Gson gson = new Gson();
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try{
			id = new String(request.getParameter("id").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				id         = CodeUtil.decode(id, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			List<FirstAnswerItemEntity> list = firstAnswerService.getItemList(id);
		
			ResultEntity<List<FirstAnswerItemEntity>> result = new ResultEntity<List<FirstAnswerItemEntity>>(1, "成功", list);
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
	
	
	public void regAnswer(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String content;
		String username;
		String type;
		String items;
		String single;
		String apptoken = null;
		
		Gson gson = new Gson();
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try{
			content = new String(request.getParameter("content").getBytes("ISO8859-1"), "UTF-8");
			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
			type = new String(request.getParameter("type").getBytes("ISO8859-1"), "UTF-8");
			items = StringUtil.isEmpty(request.getParameter("items")) ? "" : request.getParameter("items");
			single = StringUtil.isEmpty(request.getParameter("single")) ? "" : request.getParameter("single");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				content         = CodeUtil.decode(content, apptoken);
				username         = CodeUtil.decode(username, apptoken);
				type         = CodeUtil.decode(type, apptoken);
				items         = CodeUtil.decode(items, apptoken);
				single         = CodeUtil.decode(single, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			FirstAnswerEntity firstAnswer = new FirstAnswerEntity();
			firstAnswer.setContent(content);
			firstAnswer.setPublisherId(username);
			firstAnswer.setType(type);
			firstAnswer.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			firstAnswer.setSingle(single);
			firstAnswerService.insertAnswer(firstAnswer);
			
			if ("1".equals(type)) {
				List<FirstAnswerItemEntity> item = gson.fromJson(items,new TypeToken<List<FirstAnswerItemEntity>>(){}.getType());
				for (FirstAnswerItemEntity firstAnswerItemEntity : item) {
					firstAnswerItemEntity.setProblemId(firstAnswer.getId());
					firstAnswerService.insertAnswerItem(firstAnswerItemEntity);
				}
			}
			ResultEntity<String> result = new ResultEntity<String>(1, "成功", "成功");
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
	
	//提交抢答
	public void subAnswer(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String id;
		String answer;
		String username;
		String apptoken = null;
		
		Gson gson = new Gson();
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try{
			id = new String(request.getParameter("id").getBytes("ISO8859-1"), "UTF-8");
			answer = new String(request.getParameter("answer").getBytes("ISO8859-1"), "UTF-8");
			username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				id         = CodeUtil.decode(id, apptoken);
				answer         = CodeUtil.decode(answer, apptoken);
				username         = CodeUtil.decode(username, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			AnswerEntity answerEntity = new AnswerEntity();
			answerEntity.setProblemId(id);
			answerEntity.setAnswer(answer);
			answerEntity.setPublisherId(username);
			int check = firstAnswerService.checkAnswer(answerEntity);
			if(check == 0){
				firstAnswerService.insertSubAnswer(answerEntity);
				ResultEntity<String> result = new ResultEntity<String>(1, "成功", "成功");
				System.out.println(gson.toJson(result));
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}else{
				ResultEntity<String> result = new ResultEntity<String>(0, "失败", "你已抢答过此题");
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			
			}catch(Exception ex){
				ResultEntity<String> result = new ResultEntity<String>(0, "失败", "后台异常，获取数据失败");
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				ex.printStackTrace();
			}
	}
	
	
	
	public IFirstAnswerService getFirstAnswerService() {
		return firstAnswerService;
	}

	public void setFirstAnswerService(IFirstAnswerService firstAnswerService) {
		this.firstAnswerService = firstAnswerService;
	}

	public FirstAnswerQuery getFirstAnswerQuery() {
		return firstAnswerQuery;
	}

	public void setFirstAnswerQuery(FirstAnswerQuery firstAnswerQuery) {
		this.firstAnswerQuery = firstAnswerQuery;
	}
	
	
	
}
