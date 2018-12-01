package com.zfsoft.mobile.exampaper.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.zfsoft.common.log.User;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.exampaper.entity.Exampaper;
import com.zfsoft.mobile.exampaper.entity.ExampaperQes;
import com.zfsoft.mobile.exampaper.query.ExampaperQuery;
import com.zfsoft.mobile.exampaper.query.ResultQuery;
import com.zfsoft.mobile.exampaper.service.IExampaperService;
import com.zfsoft.util.base.StringUtil;

public class ExampaperAction extends HrmAction{
	
	private IExampaperService exampaperService;
	private ExampaperQuery query = new ExampaperQuery();
	private Exampaper model = new Exampaper();
	
	private String id;
	
	//跳转问卷列表页面
	public String list(){
		User user = (User) getSession().getAttribute(USER_INFO_KEY);
		if(user!=null){
			query.setUserId(user.getYhm());	
		}
		//query.setUserId("007007");	
		List<Exampaper> exampaperList = exampaperService.getPageList(query);
		
		//循环遍历该用户是否参与问卷
		if(exampaperList.size()>0){
			for (Exampaper exampaper : exampaperList) {
				query.setPaperMainId(exampaper.getPaperMainId());
				if(exampaperService.checkUserPartIn(query)>0){
					exampaper.setShiFouCanYu("yes");
				}else{
					exampaper.setShiFouCanYu("no");
				}
			}
		}
		getValueStack().set("exampaperList",exampaperList);
		return "list";
	}
	
	//根据问卷id获取该问卷下所有题目数据，以json格式返回
	public void getAllQes(){
		List<ExampaperQes> exampaperQesList = new ArrayList<ExampaperQes>();
		try {
			if(query.getPaperMainId()!=null&&!"".equals(query.getPaperMainId())){
				exampaperQesList = exampaperService.getAllQes(query);			
			}
			Gson gson = new Gson();
			String jsonStr = gson.toJson(exampaperQesList);
			HttpServletResponse response = getResponse();
			response.setHeader("Content-type", "application/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");	
			response.getWriter().write(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//跳转到填写问卷页面
    public String partIn(){
		getValueStack().set("partInPaperMainId",query.getPaperMainId());
		return "partIn";
	}
    
    //检查用户是否参与过某项投票
    public void checkUserPartIn(){
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	User user = (User) getSession().getAttribute(USER_INFO_KEY);
		if(user!=null){
			query.setUserId(user.getYhm());	
		}
		if(exampaperService.checkUserPartIn(query)>0){
			resultMap.put("shiFouCanyu", "yes");
		}else{
			resultMap.put("shiFouCanyu", "no");
		}
		try {
			Gson gson = new Gson();
			String jsonStr = gson.toJson(resultMap);
			HttpServletResponse response = getResponse();
			response.setHeader("Content-type", "application/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");	
			response.getWriter().write(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    //提交问卷填写
    public void insertResults(){
    	List<ResultQuery> resultList = new ArrayList<ResultQuery>();
		try {
			if(query.getJsonStr()!=null&&!"".equals(query.getJsonStr())){
				System.out.println(query.getJsonStr());	
				//获取yhid
				User user = (User) getSession().getAttribute(USER_INFO_KEY);
				if(user!=null){
					query.setUserId(user.getYhm());	
				}
			    //query.setUserId("007007");	
				
				//解析json封装到ResultQuery对象中,然后保存到数据库中
				resultList=JSON.parseArray(query.getJsonStr(),ResultQuery.class);  
				if(resultList.size()>0){
					for (ResultQuery resultQuery : resultList) {
						resultQuery.setYhid(query.getUserId());
						exampaperService.insertQesResult(resultQuery);
					}
				}
			}
			HttpServletResponse response = getResponse();
			response.setHeader("Content-type", "application/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");	
			response.getWriter().write("{\"data\":\"success\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	//检查是否仅发起人可见
	public String checkOwner(){
		Map<String, Object> data = new HashMap<String, Object>();
		Exampaper exampaper = exampaperService.selectExampaperById(id);
		if ("1".equals(exampaper.getQn_owner())) {
			User user = (User) getSession().getAttribute(USER_INFO_KEY);
			if (!user.getYhm().equals(exampaper.getCreater())) {
				 data.put("code", -1);
			     getValueStack().set(DATA, data);
			     return DATA;
			}
		}
        data.put("code", 0);
        getValueStack().set(DATA, data);
		return DATA;
	}
    
    
	public IExampaperService getExampaperService() {
		return exampaperService;
	}

	public void setExampaperService(IExampaperService exampaperService) {
		this.exampaperService = exampaperService;
	}

	public ExampaperQuery getQuery() {
		return query;
	}

	public void setQuery(ExampaperQuery query) {
		this.query = query;
	}

	public Exampaper getModel() {
		return model;
	}

	public void setModel(Exampaper model) {
		this.model = model;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
