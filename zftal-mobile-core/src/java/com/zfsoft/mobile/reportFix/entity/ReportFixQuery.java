package com.zfsoft.mobile.reportFix.entity;

import java.util.Date;

import com.zfsoft.dao.query.BaseQuery;

/**
 * 投票查询实体
 * @author liucb
 *
 */
public class ReportFixQuery  extends BaseQuery{
   private String id; //唯一主键
   private String problem; //问题描述
   private String telephone; //联系方式
   private String status; //状态
   private Date createTime; //创建时间
   private String evaluate; //评价
   private String score; //评价打分
   private String userId; //报修人id
   private String userName; //报修人名称
   private String haveEvaluate; //是否有评价，传0
   private String repairId; //维修工id
   private String type;
   
   private String bxr;//报修人
   private String wxr;//维修人
   private String bxbt;//报修标题
   
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProblem() {
		return problem;
	}
	public void setProblem(String problem) {
		this.problem = problem;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getEvaluate() {
		return evaluate;
	}
	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getHaveEvaluate() {
		return haveEvaluate;
	}
	public void setHaveEvaluate(String haveEvaluate) {
		this.haveEvaluate = haveEvaluate;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getRepairId() {
		return repairId;
	}
	public void setRepairId(String repairId) {
		this.repairId = repairId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBxr() {
		return bxr;
	}
	public void setBxr(String bxr) {
		this.bxr = bxr;
	}
	public String getWxr() {
		return wxr;
	}
	public void setWxr(String wxr) {
		this.wxr = wxr;
	}
	public String getBxbt() {
		return bxbt;
	}
	public void setBxbt(String bxbt) {
		this.bxbt = bxbt;
	}
	
}
