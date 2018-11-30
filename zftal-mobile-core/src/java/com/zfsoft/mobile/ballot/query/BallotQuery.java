package com.zfsoft.mobile.ballot.query;

import java.util.Date;

import com.zfsoft.dao.query.BaseQuery;


/*
 * 移动_文艺投票_投票信息表
 */
public class BallotQuery extends BaseQuery{

	//投票的Id
	private String id;
	
	//投票的标题
	private String name;
	
	//缩略图文件
	private byte[] baseImg;
	
	//缩略图路径
	private String baseImgPath;
	
	//开始时间
	private String startTime;

	//结束时间
	private String endTime;
	
	//参与人数
	private Integer parteNum;
	
	//投票规则：1：每人每天一票，2.每人固定票数
	private Integer ruleType;
	
	//每人可投票数
	private Integer ballotNum;
	
	//累计票数
	private Integer cumulative;
	
	//活动介绍
	private String content;
	
	//大图文件
	private byte[] bigImg;
	
	//大图文件路径
	private String bigImgPath;
	
	//创建投票的用户id
	private String createUserId;
	
	//创建时间
	private Date createTime;
	
	//活动的状态 0.未开始 1.进行中 2.已结束
	private Integer status;

	private String baseImgName;
	
	private String bigImgName;
	
	//是否启用 0.禁用 1.启用
	private Integer enable;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getBaseImg() {
		return baseImg;
	}

	public void setBaseImg(byte[] baseImg) {
		this.baseImg = baseImg;
	}

	public String getBaseImgPath() {
		return baseImgPath;
	}

	public void setBaseImgPath(String baseImgPath) {
		this.baseImgPath = baseImgPath;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getParteNum() {
		return parteNum;
	}

	public void setParteNum(Integer parteNum) {
		this.parteNum = parteNum;
	}

	public Integer getRuleType() {
		return ruleType;
	}

	public void setRuleType(Integer ruleType) {
		this.ruleType = ruleType;
	}

	public Integer getBallotNum() {
		return ballotNum;
	}

	public void setBallotNum(Integer ballotNum) {
		this.ballotNum = ballotNum;
	}

	public Integer getCumulative() {
		return cumulative;
	}

	public void setCumulative(Integer cumulative) {
		this.cumulative = cumulative;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public byte[] getBigImg() {
		return bigImg;
	}

	public void setBigImg(byte[] bigImg) {
		this.bigImg = bigImg;
	}

	public String getBigImgPath() {
		return bigImgPath;
	}

	public void setBigImgPath(String bigImgPath) {
		this.bigImgPath = bigImgPath;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBaseImgName() {
		return baseImgName;
	}

	public void setBaseImgName(String baseImgName) {
		this.baseImgName = baseImgName;
	}

	public String getBigImgName() {
		return bigImgName;
	}

	public void setBigImgName(String bigImgName) {
		this.bigImgName = bigImgName;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}
	
	
}
