package com.zfsoft.mobile.index.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.common.Config;
import com.google.gson.Gson;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.ballot.entity.Ballot;
import com.zfsoft.mobile.ballot.query.BallotQuery;
import com.zfsoft.mobile.ballot.utils.CommonUtils;
import com.zfsoft.mobile.common.utils.HttpClientUtil;
import com.zfsoft.mobile.index.entity.TsgJyxx;
import com.zfsoft.mobile.index.entity.YktJbxx;
import com.zfsoft.mobile.index.service.IndexInfoService;
import com.zfsoft.mobile.servlet.entity.ListEntity;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;


//首页信息
public class IndexInfoAction extends HrmAction{

	private IndexInfoService indexInfoService;
	
	private final String httpurl=Config.getString("httpurl.host.oa")+"/oaMobile";
	private final String oasign=Config.getString("oasign");

	//首页获取一卡通余额和最后消费商户
	public void getLibAndBal(){
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String apptoken = "";
		String userId = "";
		try {
			PrintWriter out = response.getWriter();
			apptoken = new String(request.getParameter("apptoken").getBytes("ISO8859-1"), "UTF-8");
			userId = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
			Gson gson = new Gson();
			
			try {
				userId  				= CodeUtil.decode(userId, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			YktJbxx info = indexInfoService.getYktInfo(userId);
			if (info == null) {
				ResultEntity<Map<String, Object>> result = new ResultEntity<Map<String, Object>>(1, "成功", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			Map<String, Object> map = new HashMap<String, Object>();
			//判断是教师还是学生
			if (info.getYhlx().equals("1")) {
				//查询教师表最后消费地点
				map = indexInfoService.getTeaSh(info.getKh());
			}else {
				//查询学生最后消费地点
				map = indexInfoService.getStuSh(info.getKh());
			}
			
			map.put("YUE", info.getYue());
			
			ResultEntity<Map<String, Object>> result = new ResultEntity<Map<String, Object>>(1, "成功", map);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//首页获取图书馆即将到期图书和即将到期时间
	public void getLibInfo(){
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String apptoken = "";
		String userId = "";
		try {
			PrintWriter out = response.getWriter();
			apptoken = new String(request.getParameter("apptoken").getBytes("ISO8859-1"), "UTF-8");
			userId = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
			Gson gson = new Gson();
			
			try {
				userId  				= CodeUtil.decode(userId, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			ResultEntity<TsgJyxx> result;
			List<TsgJyxx> list = indexInfoService.selectLibInfo(userId);
			if (list.size() > 0) {
				result = new ResultEntity<TsgJyxx>(1, "成功", list.get(0));
			}else {
				result = new ResultEntity<TsgJyxx>(1, "成功", null);
			}
			
			out.write(gson.toJson(result));
			out.flush();
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//首页课表
	public void getTimeTable(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			CommonUtils commonUtils = new CommonUtils();
			int week = commonUtils.getWeek(new Date());
		
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("dqz", "8");
			map1.put("kcmc", "英语口语2");
			map1.put("rkjs", "杨灿");
			map1.put("kcdm", "16010593");
			map1.put("kcxz", "专业课选修");
			map1.put("xf", "2");
			map1.put("skz", "1,2,3,4,5,7,8,9,10,11,12,13,14,15,16,17");
			map1.put("xqj", "5");
			map1.put("js", "1,2");
			map1.put("skjs", "西教2-4楼-408-54-多媒体;西教2-4楼-408-54-多媒体");
			map1.put("kcb", ",");
			map1.put("dsz", "");
			
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("dqz", "8");
			map2.put("kcmc", "前厅服务与管理A");
			map2.put("rkjs", "陈璐");
			map2.put("kcdm", "14010379");
			map2.put("kcxz", "专业课选修");
			map2.put("xf", "2");
			map2.put("skz", "1,2,3,4,5,7,8,9,10,11,12,13,14,15,16,17");
			map2.put("xqj", "5");
			map2.put("js", "3,4");
			map2.put("skjs", "西教2-4楼-408-54-多媒体;西教2-4楼-408-54-多媒体");
			map2.put("kcb", ",");
			map2.put("dsz", "");
			
			Map<String, Object> map3 = new HashMap<String, Object>();
			map3.put("dqz", "8");
			map3.put("kcmc", "ESLA2");
			map3.put("rkjs", "盖乐");
			map3.put("kcdm", "16010395");
			map3.put("kcxz", "专业课选修");
			map3.put("xf", "2");
			map3.put("skz", "1,2,3,4,5,7,8,9,10,11,12,13,14,15,16,17");
			map3.put("xqj", "5");
			map3.put("js", "5,6");
			map3.put("skjs", "西教3-4楼-408-52-中加酒馆专用教室;");
			map3.put("kcb", ",");
			map3.put("dsz", "");
			
			Map<String, Object> map4 = new HashMap<String, Object>();
			map4.put("dqz", "8");
			map4.put("kcmc", "Special Events as Tourism Generators");
			map4.put("rkjs", "Coltan Fagan/顾敏艳");
			map4.put("kcdm", "14020061");
			map4.put("kcxz", "专业课选修");
			map4.put("xf", "2");
			map4.put("skz", "1,2,3,4,5,7,8,9,10,11,12,13,14,15,16,17");
			map4.put("xqj", "5");
			map4.put("js", "7,8");
			map4.put("skjs", "西教3-4楼-408-52-中加酒馆专用教室;");
			map4.put("kcb", ",");
			map4.put("dsz", "");
			
			Map<String, Object> map5 = new HashMap<String, Object>();
			map5.put("dqz", "8");
			map5.put("kcmc", "创业基础A");
			map5.put("rkjs", "沈燕飞");
			map5.put("kcdm", "21030028");
			map5.put("kcxz", "通识课选修");
			map5.put("xf", "2");
			map5.put("skz", "1,2,3,4,5,7,8,9,10,11,12,13,14,15,16,17");
			map5.put("xqj", "5");
			map5.put("js", "9,10");
			map5.put("skjs", "西教3-4楼-408-52-中加酒馆专用教室;");
			map5.put("kcb", ",");
			map5.put("dsz", "");
			
			Date myDate = new Date();
			ResultEntity<Map<String, Object>> result = null ;
			if (week == 1) {
				result = new ResultEntity<Map<String, Object>>(1, "成功", map1);
			}else if (week == 2 || week == 3) {
				result = new ResultEntity<Map<String, Object>>(1, "成功", map2);
			}else if (week == 4 || week == 5) {
				result = new ResultEntity<Map<String, Object>>(1, "成功", map3);
			}else if (week == 6) {
				result = new ResultEntity<Map<String, Object>>(1, "成功", map4);
			}else if (week == 7) {
				result = new ResultEntity<Map<String, Object>>(1, "成功", map5);
			}
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//首页考试
	public void getMyTGest(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
			
			
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("ksmc", "大学语文");//考试名称
			map1.put("kssj", "9：00");//考试时间
			map1.put("ksdd", "西教3-4楼-408-52-中加酒馆专用教室");//考试地点
			map1.put("ksxq", "南校区");//考试校区
			
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("ksmc", "高等数学");//考试名称
			map2.put("kssj", "14：00");//考试时间
			map2.put("ksdd", "西教2-4楼-408-54-多媒体");//考试地点
			map2.put("ksxq", "北校区");//考试校区
			
			Date myDate = new Date();
			ResultEntity<Map<String, Object>> result ;
			int a = myDate.getDate();
			if (a % 2 == 0) {
				result = new ResultEntity<Map<String, Object>>(1, "成功", map1);
			}else {
				result = new ResultEntity<Map<String, Object>>(1, "成功", map2);
			}
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//首页事项跟踪
	public void getSxgz(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
		
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("sxmc", "我的请假申请");//姓名
			map1.put("sxsj", "五月五日");//时间
			map1.put("sxzt", "未审核");//状态
			
			ResultEntity<Map<String, Object>> result = new ResultEntity<Map<String, Object>>(1, "成功", map1);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//首页教师监考安排
	public void getJkap(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
		
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("ksmc", "大学语文");//监考课程名
			map1.put("kssj", "9：00");//监考时间
			map1.put("ksdd", "西教3-4楼-408-52-中加酒馆专用教室");//监考地点
			
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("ksmc", "高等数学");//考试名称
			map2.put("kssj", "14：00");//考试时间
			map2.put("ksdd", "西教2-4楼-408-54-多媒体");//考试地点
			
			
			Date myDate = new Date();
			ResultEntity<Map<String, Object>> result ;
			int a = myDate.getDate();
			if (a % 2 == 0) {
				result = new ResultEntity<Map<String, Object>>(1, "成功", map1);
			}else {
				result = new ResultEntity<Map<String, Object>>(1, "成功", map2);
			}
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//首页学生成绩查询
	public void getCjcx(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		try {
			PrintWriter out = response.getWriter();
		
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("cjmc", "大学语文");//监考课程名
			map1.put("cjfs", "79");//监考时间
			
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("cjmc", "高等数学");//考试名称
			map2.put("cjfs", "83");//考试时间
			
			
			Date myDate = new Date();
			ResultEntity<Map<String, Object>> result ;
			int a = myDate.getDate();
			if (a % 2 == 0) {
				result = new ResultEntity<Map<String, Object>>(1, "成功", map1);
			}else {
				result = new ResultEntity<Map<String, Object>>(1, "成功", map2);
			}
			
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 取得个人会议和全校会议数据集合
	 */
	public void getNewConferenceList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PrintWriter out = response.getWriter();
			String yhm="";
			String type="1";
			String startStr="1";
			String sizeStr="5";
	   	    ResultEntity<JSONObject> result = null;
	   	    
	 		if(StringUtils.isNotBlank(request.getParameter("username"))){
	 			yhm = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
	 		}
	 		
			Gson gson = new Gson();
			
			String apptoken = request.getParameter("apptoken");
			if(!ApptokenUtils.compare(yhm, apptoken)){
		 		result = new ResultEntity<JSONObject>(2, "app_token error!", null);
		 		out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
		 	}
			try {
				yhm = CodeUtil.decode(yhm, apptoken);
				
			} catch (Exception e) {
				result = new ResultEntity<JSONObject>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
				return;
			}
			
			String par="yhm="+yhm+"&start="+startStr+"&size="+sizeStr+"&type="+type+"&sign="+(oasign+yhm);
			String reqUrl = httpurl+"/getNewConferenceList";
			String jsonStr=HttpClientUtil.getResponse("POST",reqUrl, par);
			if(StringUtils.isNotBlank(jsonStr)){
				JSONObject obj=gson.fromJson(jsonStr, JSONObject.class);
				out.write(gson.toJson(obj));
			}
			out.flush();
			out.close();	
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
/*	========================================================================*/
	
	public IndexInfoService getIndexInfoService() {
		return indexInfoService;
	}

	public void setIndexInfoService(IndexInfoService indexInfoService) {
		this.indexInfoService = indexInfoService;
	}
	
}
