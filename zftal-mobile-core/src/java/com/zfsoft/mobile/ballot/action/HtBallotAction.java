package com.zfsoft.mobile.ballot.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.ballot.entity.Ballot;
import com.zfsoft.mobile.ballot.entity.BallotDetail;
import com.zfsoft.mobile.ballot.query.BallotDetailQuery;
import com.zfsoft.mobile.ballot.query.BallotQuery;
import com.zfsoft.mobile.ballot.service.BallotDetailService;
import com.zfsoft.mobile.ballot.service.BallotService;
import com.zfsoft.mobile.ballot.utils.CommonUtils;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.common.utils.ImageTagHtml;
import com.zfsoft.mobile.servlet.appCenterHttp.action.AppCenterHttpAction;
import com.zfsoft.mobile.servlet.entity.ListEntity;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
//马靖暂时注释
//import com.zfsoft.untils.CodeUtil;

//文艺投票后台
public class HtBallotAction extends HrmAction{

	private static Logger logger = Logger.getLogger(AppCenterHttpAction.class);

	private BallotService ballotService;

	private BallotDetailService ballotDetailService;


	private BallotQuery ballotQuery = new BallotQuery();

	private Ballot ballot = new Ballot();

	private BallotDetailQuery ballotDetailQuery = new BallotDetailQuery();

	private BallotDetail ballotDetail = new BallotDetail();

	//ballot的id
	private String id = "";

	//ballotDetail的id
	private String detailId = "";

	private String op = "add";

	private String name = "";

	private IMobileCommonService mobileCommonService;

	//投票活动列表
	public String htballotList(){

		List<Ballot> list = ballotService.selectBallotList(ballotQuery);

		this.getValueStack().set("list", list);

		return "list";

	}

	//投票修改
	public String updateBallot(){
		op = "modify";
		ballot = ballotService.findBallotById(id);
		this.getValueStack().set("ballot", ballot);
		String baseImgPath = ImageTagHtml.getImageHtml("baseImgPath", Type.IMAGE, 256, 120, 90, ballot.getBaseImgName(), true);
        getValueStack().set("baseImgPath", baseImgPath);

        String bigImgPath = ImageTagHtml.getImageHtml("bigImgPath", Type.IMAGE, 256, 2, 1, ballot.getBigImgName(), true);
        getValueStack().set("bigImgPath", bigImgPath);
		return "edit";
	}

	//投票删除
	public String deleteBallot(){

		ballotService.deleteBallot(id);

		this.setSuccessMessage("操作成功");
		this.getValueStack().set("data", this.getMessage());
		return "data";
	}

	//投票启用禁用
	public String enable(){

		ballotService.updateBallot(ballot);
		this.setSuccessMessage("操作成功");
		this.getValueStack().set("data", this.getMessage());
		return "data";
	}

	//去新增页面
	public String toAdd(){
		String baseImgPath = ImageTagHtml.getImageHtml("baseImgPath", Type.IMAGE, 10*1024, 120, 90, null, true);
        getValueStack().set("baseImgPath", baseImgPath);
        String bigImgPath = ImageTagHtml.getImageHtml("bigImgPath", Type.IMAGE, 10*1024, 2, 1, null, true);
        getValueStack().set("bigImgPath", bigImgPath);
        return "edit";
	}

	//新增投票
	public String addBallot(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String baseImgPath = "";
		baseImgPath = mobileCommonService.getMinUploadImagePath(getRequest().getParameter("baseImgPath"));
		String bigImgPath = "";
		bigImgPath = mobileCommonService.getMinUploadImagePath(getRequest().getParameter("bigImgPath"));
		if (baseImgPath == "" || bigImgPath == "") {
			this.setErrorMessage("请上传缩略图或主图");
			this.getValueStack().set("data", this.getMessage());
			return "data";
		}
		//System.out.println(request.getSession().getServletContext().getRealPath("/"));
		//File file = new File(request.getSession().getServletContext().getRealPath(File.separator) + baseImgPath);
		//System.out.println(file.getName());
		//下载图片保存至数据库
		CommonUtils commonUtils = new CommonUtils();
		byte[] baseImg = commonUtils.getBytes(request.getSession().getServletContext().getRealPath(File.separator) + baseImgPath);
		byte[] bigImg =  commonUtils.getBytes(request.getSession().getServletContext().getRealPath(File.separator) + bigImgPath);


		ballot.setBaseImg(baseImg);
		ballot.setBaseImgPath(baseImgPath);
		ballot.setBaseImgName(getRequest().getParameter("baseImgPath"));
		ballot.setBigImg(bigImg);
		ballot.setBigImgPath(bigImgPath);
		ballot.setBigImgName(getRequest().getParameter("bigImgPath"));
		ballot.setCreateTime(new Date());
		ballot.setEnable(1);
		ballot.setCreateUserId(getUser().getYhm());
		if (StringUtils.isNotBlank(ballot.getId())) {
			ballotService.updateBallot(ballot);
		}else {
			ballotService.createBallot(ballot);
		}
		this.setSuccessMessage("操作成功");
		this.getValueStack().set("data", this.getMessage());
		return "data";
	}


	public String ballotDetailList(){

		List<BallotDetail> list = ballotDetailService.selectByBallotId(ballotDetailQuery);

		this.getValueStack().set("list", list);
		this.getValueStack().set("ballotId", ballotDetailQuery.getBallotId());

		return "detailList";
	}

	//去新增候选页面
	public String toAddDetail(){
		String baseImgPath = ImageTagHtml.getImageHtml("baseImgPath", Type.IMAGE, 10*1024, 90, 90, null, true);
        getValueStack().set("baseImgPath", baseImgPath);
        this.getValueStack().set("ballotId", id);
        return "editDetail";
	}

	//去候选修改
	public String updateBallotDetail(){
		op = "modify";
		ballotDetail = ballotDetailService.findById(detailId);
		this.getValueStack().set("ballot", ballot);
		String baseImgPath = ImageTagHtml.getImageHtml("baseImgPath", Type.IMAGE, 256, 90, 90, ballotDetail.getBaseImgName(), true);
        getValueStack().set("baseImgPath", baseImgPath);
        this.getValueStack().set("ballotId", ballotDetail.getBallotId());
		return "editDetail";
	}

	//新增候选人
	public String saveBallotDetail(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String baseImgPath = "";
		baseImgPath = mobileCommonService.getMinUploadImagePath(getRequest().getParameter("baseImgPath"));
		if (baseImgPath == "") {
			this.setErrorMessage("请上传缩略图或主图");
			this.getValueStack().set("data", this.getMessage());
			return "data";
		}
		CommonUtils commonUtils = new CommonUtils();
		byte[] baseImg = {};
		//马靖暂时注释
//		byte[] baseImg = commonUtils.getBytes(request.getSession().getServletContext().getRealPath(File.separator) + baseImgPath);

		ballotDetail.setBaseImg(baseImg);
		ballotDetail.setBaseImgPath(baseImgPath);
		ballotDetail.setBaseImgName(getRequest().getParameter("baseImgPath"));
		if (StringUtils.isNotBlank(ballotDetail.getId())) {
			ballotDetailService.updateBallotDetail(ballotDetail);
		}else {
			ballotDetailService.createBallolDetail(ballotDetail);
		}


		this.setSuccessMessage("操作成功");
		this.getValueStack().set("data", this.getMessage());
		return "data";
	}

	//删除候选人
	public String deleteBallotDetail(){

		ballotDetailService.deleteBallotDetail(detailId);
		this.setSuccessMessage("操作成功");
		this.getValueStack().set("data", this.getMessage());
		return "data";
	}


	//徐州工业角色信息服务
	public String setjsxx(){

		ballotDetailService.setjsxx();
		this.setSuccessMessage("操作成功");
		this.getValueStack().set("data", this.getMessage());
		return "data";
	}




	/*====================================================================================*/

	public BallotService getBallotService() {
		return ballotService;
	}

	public void setBallotService(BallotService ballotService) {
		this.ballotService = ballotService;
	}

	public BallotQuery getBallotQuery() {
		return ballotQuery;
	}

	public void setBallotQuery(BallotQuery ballotQuery) {
		this.ballotQuery = ballotQuery;
	}

	public Ballot getBallot() {
		return ballot;
	}

	public void setBallot(Ballot ballot) {
		this.ballot = ballot;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BallotDetailService getBallotDetailService() {
		return ballotDetailService;
	}

	public void setBallotDetailService(BallotDetailService ballotDetailService) {
		this.ballotDetailService = ballotDetailService;
	}

	public BallotDetail getBallotDetail() {
		return ballotDetail;
	}

	public void setBallotDetail(BallotDetail ballotDetail) {
		this.ballotDetail = ballotDetail;
	}

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		HtBallotAction.logger = logger;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public IMobileCommonService getMobileCommonService() {
		return mobileCommonService;
	}

	public void setMobileCommonService(IMobileCommonService mobileCommonService) {
		this.mobileCommonService = mobileCommonService;
	}

	public BallotDetailQuery getBallotDetailQuery() {
		return ballotDetailQuery;
	}

	public void setBallotDetailQuery(BallotDetailQuery ballotDetailQuery) {
		this.ballotDetailQuery = ballotDetailQuery;
	}



}
