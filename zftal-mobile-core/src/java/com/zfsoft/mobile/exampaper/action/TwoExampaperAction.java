package com.zfsoft.mobile.exampaper.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import com.zfsoft.mobile.exampaper.entity.TwoExampaper;
import com.zfsoft.mobile.exampaper.entity.TwoExampaperQes;
import com.zfsoft.mobile.exampaper.query.ExampaperQuery;
import com.zfsoft.mobile.exampaper.query.ResultQuery;
import com.zfsoft.mobile.exampaper.query.TwoExampaperQuery;
import com.zfsoft.mobile.exampaper.query.TwoResultQuery;
import com.zfsoft.mobile.exampaper.service.IExampaperService;
import com.zfsoft.mobile.exampaper.service.ITwoExampaperService;
import com.zfsoft.util.base.StringUtil;

public class TwoExampaperAction extends HrmAction{
	
	private ITwoExampaperService twoExampaperService;
	private TwoExampaperQuery query = new TwoExampaperQuery();
	private TwoExampaper model = new TwoExampaper();
	
	
	
	//跳转问卷列表页面
	public String list(){
		User user = (User) getSession().getAttribute(USER_INFO_KEY);
		if(user!=null){
			query.setUserId(user.getYhm());	
		}
		//query.setUserId("007007");	
		List<TwoExampaper> exampaperList = twoExampaperService.getPageList(query);
		
		//循环遍历该用户是否参与问卷
		if(exampaperList.size()>0){
			for (TwoExampaper exampaper : exampaperList) {
				query.setPaperMainId(exampaper.getPaperMainId());
				if(twoExampaperService.checkUserPartIn(query)>0){
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
		List<TwoExampaperQes> exampaperQesList = new ArrayList<TwoExampaperQes>();
		try {
			if(!StringUtil.isEmpty(query.getPaperMainId())){
				exampaperQesList = twoExampaperService.getAllQes(query);			
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
    	TwoExampaper twoExamPaper = twoExampaperService.getPageList(query).get(0);
		getValueStack().set("twoExamPaper",twoExamPaper);
		return "partIn";
	}
    
    //检查用户是否参与过某项投票
    public void checkUserPartIn(){
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	User user = (User) getSession().getAttribute(USER_INFO_KEY);
		if(user!=null){
			query.setUserId(user.getYhm());	
		}
		if(twoExampaperService.checkUserPartIn(query)>0){
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
    	List<TwoResultQuery> resultList = new ArrayList<TwoResultQuery>();
		try {
			if(query.getJsonStr()!=null&&!"".equals(query.getJsonStr())){
				
				List<TwoExampaperQes> exampaperQesList = twoExampaperService.getAllQes(query);
				Map<String, String> answerMap = new HashMap<String, String>();
				Map<String, String> scoreMap = new HashMap<String, String>();
				for (int i = 0; i < exampaperQesList.size(); i++) {
					answerMap.put(exampaperQesList.get(i).getQuestionId(), exampaperQesList.get(i).getAnwers());
					scoreMap.put(exampaperQesList.get(i).getQuestionId(), exampaperQesList.get(i).getScore());
				}
				
				
				System.out.println(query.getJsonStr());	
				//获取yhid
				User user = (User) getSession().getAttribute(USER_INFO_KEY);
				if(user!=null){
					query.setUserId(user.getYhm());	
				}
			    //query.setUserId("007007");	
				
				//解析json封装到ResultQuery对象中,然后保存到数据库中
				resultList=JSON.parseArray(query.getJsonStr(),TwoResultQuery.class);  
				if(resultList.size()>0){
					for (TwoResultQuery resultQuery : resultList) {
						resultQuery.setYhid(query.getUserId());
						resultQuery.setScore(
									resultQuery.getItemValue().equals(answerMap.get(resultQuery.getQuestionId())) ?
											scoreMap.get(resultQuery.getQuestionId()) : "0"
								);
						twoExampaperService.insertQesResult(resultQuery);
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
    
	

	public ITwoExampaperService getTwoExampaperService() {
		return twoExampaperService;
	}

	public void setTwoExampaperService(ITwoExampaperService twoExampaperService) {
		this.twoExampaperService = twoExampaperService;
	}

	public TwoExampaperQuery getQuery() {
		return query;
	}

	public void setQuery(TwoExampaperQuery query) {
		this.query = query;
	}

	public TwoExampaper getModel() {
		return model;
	}

	public void setModel(TwoExampaper model) {
		this.model = model;
	}

	

}
