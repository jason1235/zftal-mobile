package com.zfsoft.mobile.peEvaluation.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
//import com.ibm.icu.text.SimpleDateFormat;
//import com.ibm.icu.util.Calendar;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.peEvaluation.entity.ClassInfoEntity;
import com.zfsoft.mobile.peEvaluation.entity.PEDataEntity;
import com.zfsoft.mobile.peEvaluation.entity.StudentInfoEntity;
import com.zfsoft.mobile.peEvaluation.entity.StudentScoreCountEntity;
import com.zfsoft.mobile.peEvaluation.query.ClassInfoQuery;
import com.zfsoft.mobile.peEvaluation.query.ClassScoreQuery;
import com.zfsoft.mobile.peEvaluation.query.PEDataQuery;
import com.zfsoft.mobile.peEvaluation.query.StudentInfoQuery;
import com.zfsoft.mobile.peEvaluation.service.IPEEvaluationService;
import com.zfsoft.mobile.peEvaluation.until.AntexionUntil;
import com.zfsoft.mobile.peEvaluation.until.FiftyRunUntil;
import com.zfsoft.mobile.peEvaluation.until.GeneralUtil;
import com.zfsoft.mobile.peEvaluation.until.JumpUntil;
import com.zfsoft.mobile.peEvaluation.until.PullUpUntil;
import com.zfsoft.mobile.peEvaluation.until.PulmonaryUntil;
import com.zfsoft.mobile.peEvaluation.until.RunUntil;
import com.zfsoft.mobile.peEvaluation.until.SitUpUntil;
import com.zfsoft.mobile.servlet.entity.ListEntity;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;

/**
 * 体育测评action
 * @author liucb
 */
public class PEEvaluationAction extends HrmAction {
    private static Logger logger = Logger.getLogger(PEEvaluationAction.class);
	
	private IPEEvaluationService peEvaluationService;
	private ClassInfoQuery classInfoQuery = new ClassInfoQuery();
	private ClassInfoEntity classInfoModel = new ClassInfoEntity();
	private ClassScoreQuery classScoreQuery = new ClassScoreQuery();
	private StudentInfoQuery studentInfoQuery = new StudentInfoQuery();
	private PEDataQuery peDataQuery = new PEDataQuery();
	public PEDataQuery getPeDataQuery() {
		return peDataQuery;
	}


	public void setPeDataQuery(PEDataQuery peDataQuery) {
		this.peDataQuery = peDataQuery;
	}


	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//获取班级列表数据
	public void classInfoList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String username = null;
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
			start = new String(request.getParameter("start").getBytes("ISO8859-1"), "UTF-8");
			size = new String(request.getParameter("size").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(username,apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				start        = CodeUtil.decode(start, apptoken);
				size         = CodeUtil.decode(size, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		
			classInfoQuery.setToPage(Integer.valueOf(start));
			classInfoQuery.setPerPageSize(Integer.valueOf(size));
			List<ClassInfoEntity> list = peEvaluationService.getClassInfoList(classInfoQuery);
			
			ListEntity<ClassInfoEntity> resultList = new ListEntity<ClassInfoEntity>();
			resultList.setItemList(list);
			if(list == null || list.size() < Integer.valueOf(size))	{
				resultList.setOvered(true);
			}else{
				resultList.setOvered(false);
			}
			
	        ResultEntity<ListEntity<ClassInfoEntity>> result = new ResultEntity<ListEntity<ClassInfoEntity>>(1, "成功", resultList);
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

	
	//获取班级学生分数列表数据
	public void classScoreList(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String username = null;
		String start = null;
		String size = null;
		String dataType = null;
		String classId  = null;
		String columnStr = null;
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
			start = new String(request.getParameter("start").getBytes("ISO8859-1"), "UTF-8");
			size = new String(request.getParameter("size").getBytes("ISO8859-1"), "UTF-8");
			dataType = new String(request.getParameter("dataType").getBytes("ISO8859-1"), "UTF-8");
			classId = new String(request.getParameter("classId").getBytes("ISO8859-1"), "UTF-8");
			columnStr = new String(request.getParameter("columnStr").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(username,apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				start        = CodeUtil.decode(start, apptoken);
				size         = CodeUtil.decode(size, apptoken);
				dataType     = CodeUtil.decode(dataType, apptoken);
				classId      = CodeUtil.decode(classId, apptoken);
				columnStr    = CodeUtil.decode(columnStr, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		
			classScoreQuery.setToPage(Integer.valueOf(start));
			classScoreQuery.setPerPageSize(Integer.valueOf(size));
			classScoreQuery.setClassId(classId);
			classScoreQuery.setDataType(dataType);
			
			if("1".equals(columnStr)){
				classScoreQuery.setColumnStr("PULMONARY");
				classScoreQuery.setOrderKey("desc");
			}else if("2".equals(columnStr)){
				classScoreQuery.setColumnStr("FIFTYRUN");
				classScoreQuery.setOrderKey("asc");
			}else if("3".equals(columnStr)){
				classScoreQuery.setColumnStr("JUMP");
				classScoreQuery.setOrderKey("desc");
			}else if("4".equals(columnStr)){
				classScoreQuery.setColumnStr("ANTEXION");
				classScoreQuery.setOrderKey("desc");
			}else if("5".equals(columnStr)){
				classScoreQuery.setColumnStr("EIGHTHUNDREDRUN");
				classScoreQuery.setOrderKey("asc");
			}else if("6".equals(columnStr)){
				classScoreQuery.setColumnStr("ONETHOUSANDRUN");
				classScoreQuery.setOrderKey("asc");
			}else if("7".equals(columnStr)){
				classScoreQuery.setColumnStr("SITUP");
				classScoreQuery.setOrderKey("desc");
			}else if("8".equals(columnStr)){
				classScoreQuery.setColumnStr("PULLUP");
				classScoreQuery.setOrderKey("desc");
			}else{
				classScoreQuery.setColumnStr("PULMONARY");
				classScoreQuery.setOrderKey("desc");
			}
			
			List<PEDataEntity> list = peEvaluationService.getClassScoreList(classScoreQuery);
			
			ListEntity<PEDataEntity> resultList = new ListEntity<PEDataEntity>();
			resultList.setItemList(list);
			if(list == null || list.size() < Integer.valueOf(size))	{
				resultList.setOvered(true);
			}else{
				resultList.setOvered(false);
			}
			
	        ResultEntity<ListEntity<PEDataEntity>> result = new ResultEntity<ListEntity<PEDataEntity>>(1, "成功", resultList);
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
	
	//获取学生基本信息
	public void getStudentInfo(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String username = null;
		String schoolNumber = null;
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
			schoolNumber = new String(request.getParameter("schoolNumber").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(username,apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				schoolNumber        = CodeUtil.decode(schoolNumber, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		
			studentInfoQuery.setSchoolNumber(schoolNumber);
			StudentInfoEntity studentInfo = peEvaluationService.getStudentInfo(studentInfoQuery);
			
	        ResultEntity<StudentInfoEntity> result = new ResultEntity<StudentInfoEntity>(1, "成功", studentInfo);
	        System.out.println(JSON.toJSONString(result,SerializerFeature.WriteMapNullValue));
	        out.write(JSON.toJSONString(result,SerializerFeature.WriteMapNullValue));
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
	
	//根据班级获取学生基本信息列表
	public void getStudentInfoListByClass(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String username = null;
		String classId = null;
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
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(username,apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				classId        = CodeUtil.decode(classId, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
		
			studentInfoQuery.setClassId(classId);
			List<StudentInfoEntity> studentInfo = peEvaluationService.getStudentInfoListByClass(studentInfoQuery);
			
	        ResultEntity<List<StudentInfoEntity> > result = new ResultEntity<List<StudentInfoEntity> >(1, "成功", studentInfo);
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
	
	
	//提交体测数据到后台
	public void submitData(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String jsonStr = null;
		
		Gson gson = new Gson();
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try{
			jsonStr = new String(request.getParameter("jsonStr").getBytes("ISO8859-1"), "UTF-8");
			
		
			List<PEDataQuery> peDataQueryEntities = gson.fromJson(jsonStr,new TypeToken<List<PEDataQuery>>(){}.getType());
				
			/**
			 * 循环遍历体测数据表是否存在该条记录，不存在直接插入数据，存在则更新字段
			 */
			for (int i = 0; i < peDataQueryEntities.size(); i++) {
				PEDataQuery peDataEntityQry = peDataQueryEntities.get(i);
				//先计算分数，再更新到数据库
				//肺活量
				if(peDataEntityQry.getPulmonary()!=null&&!"".equals(peDataEntityQry.getPulmonary())){
					peDataEntityQry.setPulmonaryScore(""+PulmonaryUntil.countPulmonaryScore(Integer.parseInt(peDataEntityQry.getPulmonary()), peDataEntityQry.getGender()));
				}
				//50m
				if(peDataEntityQry.getFiftyRun()!=null&&!"".equals(peDataEntityQry.getFiftyRun())){
					peDataEntityQry.setFiftyRunScore(""+FiftyRunUntil.countFiftyRunScore(Double.parseDouble(peDataEntityQry.getFiftyRun()),peDataEntityQry.getGender()));
				} 
				//立定跳远
				if(peDataEntityQry.getJump()!=null&&!"".equals(peDataEntityQry.getJump())){
					peDataEntityQry.setJumpScore(""+JumpUntil.countJumpScore(Integer.parseInt(peDataEntityQry.getJump()), peDataEntityQry.getGender()));
				}
				//坐位体前屈
				if(peDataEntityQry.getAntexion()!=null&&!"".equals(peDataEntityQry.getAntexion())){
					peDataEntityQry.setAntexionScore(""+AntexionUntil.countAntexionScore(Double.parseDouble(peDataEntityQry.getAntexion()), peDataEntityQry.getGender()));
				}
				//仰卧起坐
				if(peDataEntityQry.getSitUp()!=null&&!"".equals(peDataEntityQry.getSitUp())){
					peDataEntityQry.setSitUpScore(""+SitUpUntil.countSitUpScore(Integer.parseInt(peDataEntityQry.getSitUp())));
				}
				//引体向上
				if(peDataEntityQry.getPullUp()!=null&&!"".equals(peDataEntityQry.getPullUp())){
					peDataEntityQry.setPullUpScore(""+PullUpUntil.countPullUpScore(Integer.parseInt(peDataEntityQry.getPullUp())));
				}
				//800m
				if(peDataEntityQry.getEightHundredRun()!=null&&!"".equals(peDataEntityQry.getEightHundredRun())){
					peDataEntityQry.setEightHundredRunScore(""+RunUntil.countRunScore(Double.parseDouble(peDataEntityQry.getEightHundredRun()),peDataEntityQry.getGender()));
				}
				//1000m
				if(peDataEntityQry.getOneThousandRun()!=null&&!"".equals(peDataEntityQry.getOneThousandRun())){
					peDataEntityQry.setOneThousandRunScore(""+RunUntil.countRunScore(Double.parseDouble(peDataEntityQry.getOneThousandRun()),peDataEntityQry.getGender()));
				}
				
				peDataEntityQry.setYear(""+Calendar.getInstance().get(Calendar.YEAR));
				peDataEntityQry.setCreateTime(sdf .format(new Date()));
				//计算综合评定
				String general = GeneralUtil.getGeneral(peDataEntityQry, peDataEntityQry.getGender());
				peDataEntityQry.setGeneralScore(general);
				if(peEvaluationService.checkHaveData(peDataEntityQry)>0){
					peEvaluationService.updateData(peDataEntityQry);
				}else{
					peEvaluationService.insertData(peDataEntityQry);
				}
			}
			
	        ResultEntity<String> result = new ResultEntity<String>(1, "成功","成功");
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
	
	//学生查询成绩
	public void queryStudentScore(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String username = null;
		String schoolNumber = null;
		String dataType = null;
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
			schoolNumber = new String(request.getParameter("schoolNumber").getBytes("ISO8859-1"), "UTF-8");
			dataType = new String(request.getParameter("dataType").getBytes("ISO8859-1"), "UTF-8");
		 	apptoken = StringUtil.isEmpty(request.getParameter("apptoken")) ? "" : request.getParameter("apptoken");
			
			if(!ApptokenUtils.compare(username,apptoken)){
				ResultEntity<String> result = new ResultEntity<String>(2, "app_token error!", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			try {
				schoolNumber    = CodeUtil.decode(schoolNumber, apptoken);
				dataType        = CodeUtil.decode(dataType, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			peDataQuery.setSchoolNumber(schoolNumber);
			peDataQuery.setDataType(dataType);
			
			PEDataEntity studentScore = peEvaluationService.queryStudentScore(peDataQuery);
			//根据获取到的成绩信息统计班级和学院百分比
			ClassScoreQuery classScoreQuery = new ClassScoreQuery();
			classScoreQuery.setDataType(studentScore.getDataType());
			classScoreQuery.setSchoolNumber(studentScore.getSchoolNumber());
			
			double classPercentVal = 0;
			double institutePercentVal = 0;
			
			List<StudentScoreCountEntity> countList = new ArrayList<StudentScoreCountEntity>();
			
			//肺活量统计
			StudentScoreCountEntity pulmonaryCountEntity = new StudentScoreCountEntity();
			classScoreQuery.setColumnStr("pulmonary");
			classScoreQuery.setColumnData(studentScore.getPulmonary()==null? "0":studentScore.getPulmonary());
			classScoreQuery.setOperator("<");
			classPercentVal = peEvaluationService.countScoreClassPercent(classScoreQuery);
			institutePercentVal = peEvaluationService.countScoreInstitutePercent(classScoreQuery);
			
			pulmonaryCountEntity.setItem("肺活量");
			pulmonaryCountEntity.setValue(studentScore.getPulmonary()==null? "0":studentScore.getPulmonary());
			pulmonaryCountEntity.setClassPercent(classPercentVal+"");
			pulmonaryCountEntity.setInstitutePercent(institutePercentVal+"");
			countList.add(pulmonaryCountEntity);
			
			//50米统计
			StudentScoreCountEntity fiftyRunCountEntity = new StudentScoreCountEntity();
			classScoreQuery.setColumnStr("fiftyRun");
			classScoreQuery.setColumnData(studentScore.getFiftyRun()==null? "0":studentScore.getFiftyRun());
			classScoreQuery.setOperator(">");
			classPercentVal = peEvaluationService.countScoreClassPercent(classScoreQuery);
			institutePercentVal = peEvaluationService.countScoreInstitutePercent(classScoreQuery);
			
			fiftyRunCountEntity.setItem("50m跑");
			fiftyRunCountEntity.setValue(studentScore.getFiftyRun()==null? "0":studentScore.getFiftyRun());
			fiftyRunCountEntity.setClassPercent(classPercentVal+"");
			fiftyRunCountEntity.setInstitutePercent(institutePercentVal+"");
			countList.add(fiftyRunCountEntity);
			
			//跳远统计
			StudentScoreCountEntity jumpCountEntity = new StudentScoreCountEntity();
			classScoreQuery.setColumnStr("jump");
			classScoreQuery.setColumnData(studentScore.getJump()==null? "0":studentScore.getJump());
			classScoreQuery.setOperator("<");
			classPercentVal = peEvaluationService.countScoreClassPercent(classScoreQuery);
			institutePercentVal = peEvaluationService.countScoreInstitutePercent(classScoreQuery);
			
			jumpCountEntity.setItem("立定跳远");
			jumpCountEntity.setValue(studentScore.getJump()==null? "0":studentScore.getJump());
			jumpCountEntity.setClassPercent(classPercentVal+"");
			jumpCountEntity.setInstitutePercent(institutePercentVal+"");
			countList.add(jumpCountEntity);
			
			//坐位体前屈统计
			StudentScoreCountEntity antexionCountEntity = new StudentScoreCountEntity();
			classScoreQuery.setColumnStr("antexion");
			classScoreQuery.setColumnData(studentScore.getAntexion()==null? "0":studentScore.getAntexion());
			classScoreQuery.setOperator("<");
			classPercentVal = peEvaluationService.countScoreClassPercent(classScoreQuery);
			institutePercentVal = peEvaluationService.countScoreInstitutePercent(classScoreQuery);
			
			antexionCountEntity.setItem("坐位体前屈");
			antexionCountEntity.setValue(studentScore.getAntexion()==null? "0":studentScore.getAntexion());
			antexionCountEntity.setClassPercent(classPercentVal+"");
			antexionCountEntity.setInstitutePercent(institutePercentVal+"");
			countList.add(antexionCountEntity);
			
			//仰卧起坐统计
			StudentScoreCountEntity sitUpCountEntity = new StudentScoreCountEntity();
			classScoreQuery.setColumnStr("sitUp");
			classScoreQuery.setColumnData(studentScore.getSitUp()==null? "0":studentScore.getSitUp());
			classScoreQuery.setOperator("<");
			classPercentVal = peEvaluationService.countScoreClassPercent(classScoreQuery);
			institutePercentVal = peEvaluationService.countScoreInstitutePercent(classScoreQuery);
			
			sitUpCountEntity.setItem("仰卧起坐");
			sitUpCountEntity.setValue(studentScore.getSitUp()==null? "0":studentScore.getSitUp());
			sitUpCountEntity.setClassPercent(classPercentVal+"");
			sitUpCountEntity.setInstitutePercent(institutePercentVal+"");
			countList.add(sitUpCountEntity);
			
			//引体向上统计
			StudentScoreCountEntity pullUpCountEntity = new StudentScoreCountEntity();
			classScoreQuery.setColumnStr("pullUp");
			classScoreQuery.setColumnData(studentScore.getPullUp()==null? "0":studentScore.getPullUp());
			classScoreQuery.setOperator("<");
			classPercentVal = peEvaluationService.countScoreClassPercent(classScoreQuery);
			institutePercentVal = peEvaluationService.countScoreInstitutePercent(classScoreQuery);
			
			pullUpCountEntity.setItem("引体向上");
			pullUpCountEntity.setValue(studentScore.getPullUp()==null? "0":studentScore.getPullUp());
			pullUpCountEntity.setClassPercent(classPercentVal+"");
			pullUpCountEntity.setInstitutePercent(institutePercentVal+"");
			countList.add(pullUpCountEntity);
			
			//800米统计
			StudentScoreCountEntity eightCountEntity = new StudentScoreCountEntity();
			classScoreQuery.setColumnStr("eightHundredRun");
			classScoreQuery.setColumnData(studentScore.getEightHundredRun()==null? "0":studentScore.getEightHundredRun());
			classScoreQuery.setOperator(">");
			classPercentVal = peEvaluationService.countScoreClassPercent(classScoreQuery);
			institutePercentVal = peEvaluationService.countScoreInstitutePercent(classScoreQuery);
			
			eightCountEntity.setItem("800m跑");
			eightCountEntity.setValue(studentScore.getEightHundredRun()==null? "0":studentScore.getEightHundredRun());
			eightCountEntity.setClassPercent(classPercentVal+"");
			eightCountEntity.setInstitutePercent(institutePercentVal+"");
			countList.add(eightCountEntity);
			
			//1000米统计
			StudentScoreCountEntity oneCountEntity = new StudentScoreCountEntity();
			classScoreQuery.setColumnStr("oneThousandRun");
			classScoreQuery.setColumnData(studentScore.getOneThousandRun()==null? "0":studentScore.getOneThousandRun());
			classScoreQuery.setOperator(">");
			classPercentVal = peEvaluationService.countScoreClassPercent(classScoreQuery);
			institutePercentVal = peEvaluationService.countScoreInstitutePercent(classScoreQuery);
			
			oneCountEntity.setItem("1000m跑");
			oneCountEntity.setValue(studentScore.getOneThousandRun()==null? "0":studentScore.getOneThousandRun());
			oneCountEntity.setClassPercent(classPercentVal+"");
			oneCountEntity.setInstitutePercent(institutePercentVal+"");
			countList.add(oneCountEntity);
			
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap.put("studentScore", studentScore);
			resultMap.put("countList", countList);
			
	        ResultEntity<String> result = new ResultEntity<String>(1, "成功", JSON.toJSONString(resultMap,SerializerFeature.WriteMapNullValue));
	        //String retStr = JSON.toJSONString(result,SerializerFeature.WriteMapNullValue);
	        out.write(JSON.toJSONString(result,SerializerFeature.WriteMapNullValue));
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

	public ClassInfoQuery getClassInfoQuery() {
		return classInfoQuery;
	}

	public void setClassInfoQuery(ClassInfoQuery classInfoQuery) {
		this.classInfoQuery = classInfoQuery;
	}

	public ClassInfoEntity getClassInfoModel() {
		return classInfoModel;
	}

	public void setClassInfoModel(ClassInfoEntity classInfoModel) {
		this.classInfoModel = classInfoModel;
	}

	public IPEEvaluationService getPeEvaluationService() {
		return peEvaluationService;
	}

	public void setPeEvaluationService(IPEEvaluationService peEvaluationService) {
		this.peEvaluationService = peEvaluationService;
	}


	public ClassScoreQuery getClassScoreQuery() {
		return classScoreQuery;
	}


	public void setClassScoreQuery(ClassScoreQuery classScoreQuery) {
		this.classScoreQuery = classScoreQuery;
	}


	public StudentInfoQuery getStudentInfoQuery() {
		return studentInfoQuery;
	}


	public void setStudentInfoQuery(StudentInfoQuery studentInfoQuery) {
		this.studentInfoQuery = studentInfoQuery;
	}
}
