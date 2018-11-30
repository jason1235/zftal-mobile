package com.zfsoft.mobile.ballot.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.ballot.entity.Ballot;
import com.zfsoft.mobile.ballot.entity.BallotDetail;
import com.zfsoft.mobile.ballot.entity.BallotRecord;
import com.zfsoft.mobile.ballot.query.BallotDetailQuery;
import com.zfsoft.mobile.ballot.query.BallotQuery;
import com.zfsoft.mobile.ballot.service.BallotDetailService;
import com.zfsoft.mobile.ballot.service.BallotRecordService;
import com.zfsoft.mobile.ballot.service.BallotService;
import com.zfsoft.mobile.ballot.utils.CommonUtils;
import com.zfsoft.mobile.services.entity.ExpressCommentEntity;
import com.zfsoft.mobile.services.entity.LossObjectEntity;
import com.zfsoft.mobile.servlet.appCenterHttp.action.AppCenterHttpAction;
import com.zfsoft.mobile.servlet.entity.ListEntity;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.untils.CodeUtil;

//文艺投票app接口
public class BallotAction  extends HrmAction {
	private static Logger logger = Logger.getLogger(AppCenterHttpAction.class);
	
	private BallotService ballotService;
	
	private BallotDetailService ballotDetailService;
	
	private BallotRecordService ballotRecordService;
	

	//投票活动列表
	public void ballotList(){
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String apptoken = "";
		String start = null;
		String size = null;
		try {
			PrintWriter out = response.getWriter();
			BallotQuery query = new BallotQuery();
			apptoken = new String(request.getParameter("apptoken").getBytes("ISO8859-1"), "UTF-8");
			start = new String(request.getParameter("start").getBytes("ISO8859-1"), "UTF-8");
			size = new String(request.getParameter("size").getBytes("ISO8859-1"), "UTF-8");
			Gson gson = new Gson();
			
			try {
				start  				= CodeUtil.decode(start, apptoken);
				size  				= CodeUtil.decode(size, apptoken);
				System.err.println("start = " + start + "and size = "+ size);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			query.setToPage(Integer.valueOf(start));
			query.setPerPageSize(Integer.valueOf(size));
			query.setEnable(1);
			List<Ballot> list = ballotService.selectBallotList(query);
			String url = BaseHolder.getPropertiesValue("suploadPath");
			//图片处理
			for (Ballot ballot : list) {
				CommonUtils commonUtils = new CommonUtils();
				commonUtils.getDownPic(ballot.getBaseImgPath(), ballot.getBaseImg(),request);
				
				ballot.setBaseImgPath(url + ballot.getBaseImgPath());
			}
			
			
			
			ListEntity<Ballot> resultList = new ListEntity<Ballot>();
			resultList.setItemList(list);
			if(list == null || list.size() < Integer.valueOf(size)){
				resultList.setOvered(true);
			}else{
				resultList.setOvered(false);
			}	
			
			ResultEntity<ListEntity<Ballot>> result = new ResultEntity<ListEntity<Ballot>>(1, "", resultList);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//投票活动详情
	public void ballotDetail(){
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String apptoken = "";
		String id = "";
		try {
			PrintWriter out = response.getWriter();
			apptoken = new String(request.getParameter("apptoken").getBytes("ISO8859-1"), "UTF-8");
			id = new String(request.getParameter("id").getBytes("ISO8859-1"), "UTF-8");
			Gson gson = new Gson();
		
			try {
				id  				= CodeUtil.decode(id, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			Ballot ballot = ballotService.selectBallotById(id);
			String url = BaseHolder.getPropertiesValue("suploadPath");
			//图片处理
			CommonUtils commonUtils = new CommonUtils();
			commonUtils.getDownPic(ballot.getBigImgPath(), ballot.getBaseImg(),request);
			ballot.setBigImgPath(url + ballot.getBigImgPath());
			
			ResultEntity<Ballot> result = new ResultEntity<Ballot>(1, "", ballot);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	//投票活动详情
	public void ballotDetailList(){
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String apptoken = "";
		String id = "";
		String name = "";
		String start = null;
		String size = null;
		String zgh = "";
		try {
			PrintWriter out = response.getWriter();
			apptoken = new String(request.getParameter("apptoken").getBytes("ISO8859-1"), "UTF-8");
			id = new String(request.getParameter("id").getBytes("ISO8859-1"), "UTF-8");
			name = new String(request.getParameter("name").getBytes("ISO8859-1"), "UTF-8");
			start = new String(request.getParameter("start").getBytes("ISO8859-1"), "UTF-8");
			size = new String(request.getParameter("size").getBytes("ISO8859-1"), "UTF-8");
			zgh = new String(request.getParameter("userName").getBytes("ISO8859-1"), "UTF-8");
			Gson gson = new Gson();
			BallotDetailQuery query = new BallotDetailQuery();
			try {
			  	id  				= CodeUtil.decode(id, apptoken);
			  	name  				= CodeUtil.decode(name, apptoken);
				start  				= CodeUtil.decode(start, apptoken);
				size  				= CodeUtil.decode(size, apptoken);
				zgh					= CodeUtil.decode(zgh, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			query.setBallotId(id);
			query.setName(name);
			query.setToPage(Integer.valueOf(start));
			query.setPerPageSize(Integer.valueOf(size));
			
			List<BallotDetail> list = ballotDetailService.selectByBallotId(query);
			//处理图片
			String url = BaseHolder.getPropertiesValue("suploadPath");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String nowTime = sdf.format(new Date());
			Ballot ballot = ballotService.selectBallotById(id);
			for (BallotDetail ballotDetail : list) {
				CommonUtils commonUtils = new CommonUtils();
				commonUtils.getDownPic(ballotDetail.getBaseImgPath(), ballotDetail.getBaseImg(),request);
				
				ballotDetail.setBaseImgPath(url + ballotDetail.getBaseImgPath());
				
				//判断每个候选人该用户对他是否已投票
				int count = 0;
				if (ballot.getRuleType() == 1) {
					count = ballotRecordService.countBallotNum(zgh, ballotDetail.getId(), nowTime);
				}else {
					count = ballotRecordService.countBallotNum(zgh, ballotDetail.getId(), null);
				}
				if (count == 0) {
					ballotDetail.setIsBallot(0);
				}else {
					ballotDetail.setIsBallot(1);
				}
			}
			
			
			ListEntity<BallotDetail> resultList = new ListEntity<BallotDetail>();
			resultList.setItemList(list);
			if(list == null || list.size() < Integer.valueOf(size)){
				resultList.setOvered(true);
			}else{
				resultList.setOvered(false);
			}	
			
			ResultEntity<ListEntity<BallotDetail>> result = new ResultEntity<ListEntity<BallotDetail>>(1, "", resultList);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//用户发起投票
	public void doBallot(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String apptoken = "";
		String ballotId = "";
		String detailId = "";
		String zgh = "";
		try {
			PrintWriter out = response.getWriter();
			apptoken = new String(request.getParameter("apptoken").getBytes("ISO8859-1"), "UTF-8");
			ballotId = new String(request.getParameter("ballotId").getBytes("ISO8859-1"), "UTF-8");
			detailId = new String(request.getParameter("detailId").getBytes("ISO8859-1"), "UTF-8");
			zgh = new String(request.getParameter("userName").getBytes("ISO8859-1"), "UTF-8");
			Gson gson = new Gson();
			try {
			  	ballotId  				= CodeUtil.decode(ballotId, apptoken);
			  	detailId  				= CodeUtil.decode(detailId, apptoken);
			  	zgh  					= CodeUtil.decode(zgh, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			Ballot ballot = ballotService.findBallotById(ballotId);
			ResultEntity result = null;
			if (ballot.getStatus() == 1) {
				//投票还在进行中，判断是否满足投票条件
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String nowTime = sdf.format(new Date());
				if (ballot.getRuleType() == 1) {
					//每人每天可投票，查询票数是否超过
					int count = ballotRecordService.countBallotNumByBallotId(zgh, ballotId, nowTime);
					if (count < ballot.getBallotNum()) {
						//已投票数小于可投票数,继续投票
						//计入用户投票记录
						int a =  ballotRecordService.countBallotNumByBallotId(zgh, ballotId, null);
						BallotRecord record = new BallotRecord();
						record.setUserId(zgh);
						record.setBallotDetailId(detailId);
						record.setBallotTime(nowTime);
						record.setBallotId(ballotId);
						ballotRecordService.createBallotRecord(record);
						//被投票人票数+1
						BallotDetail ballotDetail = ballotDetailService.findById(detailId);
						ballotDetail.setCount(ballotDetail.getCount() + 1);
						ballotDetailService.updateBallotDetail(ballotDetail);
						//判断该用户是否投过票
						if (a == 0) {
							//用户是第一次投票，投票活动参与人数+1
							ballot.setParteNum(ballot.getParteNum() + 1);
						}
						//总票数+1
						ballot.setCumulative(ballot.getCumulative() + 1);
						int res = ballotService.updateBallot(ballot);
						
						result = new ResultEntity<String>(1, "", "投票成功");
					}else{
						result = new ResultEntity<String>(0, "当日投票数已达上限", null);
					}
				}else if (ballot.getRuleType() == 2) {
					//每人活动期间固定票数
					int count = ballotRecordService.countBallotNumByBallotId(zgh, ballotId, null);
					if (count < ballot.getBallotNum()) {
						//已投票数小于可投票数,继续投票
						//计入用户投票记录
						BallotRecord record = new BallotRecord();
						record.setUserId(zgh);
						record.setBallotDetailId(detailId);
						record.setBallotTime(nowTime);
						record.setBallotId(ballotId);
						ballotRecordService.createBallotRecord(record);
						//被投票人票数+1
						BallotDetail ballotDetail = ballotDetailService.findById(detailId);
						ballotDetail.setCount(ballotDetail.getCount() + 1);
						ballotDetailService.updateBallotDetail(ballotDetail);
						//判断该用户是否投过票
						if (count == 0) {
							//用户是第一次投票，投票活动参与人数+1
							ballot.setParteNum(ballot.getParteNum() + 1);
						}
						//总票数+1
						ballot.setCumulative(ballot.getCumulative() + 1);
						ballotService.updateBallot(ballot);
						result = new ResultEntity<String>(1, "", "投票成功");
					}else{
						result = new ResultEntity<String>(0, "投票数已达上限", null);
					}
				}else {
					result = new ResultEntity<String>(0, "投票信息有误", null);
				}
			}else {
				 result = new ResultEntity<String>(-1, "投票已结束或未开始", null);
			}
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//用户取消投票
	public void cancelBallot(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String apptoken = "";
		String ballotId = "";
		String detailId = "";
		String zgh = "";
		try {
			PrintWriter out = response.getWriter();
			apptoken = new String(request.getParameter("apptoken").getBytes("ISO8859-1"), "UTF-8");
			ballotId = new String(request.getParameter("ballotId").getBytes("ISO8859-1"), "UTF-8");
			detailId = new String(request.getParameter("detailId").getBytes("ISO8859-1"), "UTF-8");
			zgh = new String(request.getParameter("userName").getBytes("ISO8859-1"), "UTF-8");
			Gson gson = new Gson();
			try {
			  	ballotId  				= CodeUtil.decode(ballotId, apptoken);
			  	detailId  				= CodeUtil.decode(detailId, apptoken);
			  	zgh  					= CodeUtil.decode(zgh, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			
			Ballot ballot = ballotService.findBallotById(ballotId);
			ResultEntity result = null;
			if (ballot.getStatus() == 1) {
				//投票还在进行中，将取消的被投票人票数-1，
				//被投票人票数-1
				BallotDetail ballotDetail = ballotDetailService.findById(detailId);
				ballotDetail.setCount(ballotDetail.getCount() - 1);
				ballotDetailService.updateBallotDetail(ballotDetail);
				//删除用户投票记录
				ballotRecordService.deleteBallotRecord(zgh, detailId);
				//活动累计票数-1
				ballot.setCumulative(ballot.getCumulative() - 1);
				ballotService.updateBallot(ballot);
				result = new ResultEntity<String>(1, "", "取消投票成功");
			}else {
				 result = new ResultEntity<String>(-1, "投票已结束或未开始", null);
			}
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//获取当前排名
	public void ranking(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request = ServletActionContext.getRequest();
		String apptoken = "";
		String ballotId = "";
		try {
			PrintWriter out = response.getWriter();
			apptoken = new String(request.getParameter("apptoken").getBytes("ISO8859-1"), "UTF-8");
			ballotId = new String(request.getParameter("ballotId").getBytes("ISO8859-1"), "UTF-8");
			Gson gson = new Gson();
			try {
			  	ballotId  				= CodeUtil.decode(ballotId, apptoken);
			} catch (Exception e) {
				ResultEntity<String> result = new ResultEntity<String>(0, "加密方式出错！", null);
				out.write(gson.toJson(result));
				out.flush();
				out.close();
			}
			BallotDetailQuery query = new BallotDetailQuery();
			
			query.setBallotId(ballotId);
			query.setToPage(1);
			query.setPerPageSize(10);
			List<BallotDetail> list = ballotDetailService.selectRanking(query);
			
			//处理图片
			String url = BaseHolder.getPropertiesValue("suploadPath");
			for (BallotDetail ballotDetail : list) {
				CommonUtils commonUtils = new CommonUtils();
				commonUtils.getDownPic(ballotDetail.getBaseImgPath(), ballotDetail.getBaseImg(),request);
				
				ballotDetail.setBaseImgPath(url + ballotDetail.getBaseImgPath());
				
			}
			
			
			Ballot ballot = ballotService.findBallotById(ballotId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("parteNum", ballot.getParteNum());
			map.put("cumulative", ballot.getCumulative());
			map.put("bollotDetails", list);
			
			ResultEntity<Map<String, Object>> result = new ResultEntity<Map<String, Object>>(1, "", map);
			out.write(gson.toJson(result));
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
	
	//===========================================================
	
	public BallotService getBallotService() {
		return ballotService;
	}

	public void setBallotService(BallotService ballotService) {
		this.ballotService = ballotService;
	}

	public BallotDetailService getBallotDetailService() {
		return ballotDetailService;
	}

	public void setBallotDetailService(BallotDetailService ballotDetailService) {
		this.ballotDetailService = ballotDetailService;
	}

	public BallotRecordService getBallotRecordService() {
		return ballotRecordService;
	}

	public void setBallotRecordService(BallotRecordService ballotRecordService) {
		this.ballotRecordService = ballotRecordService;
	}
	
	
	
}
