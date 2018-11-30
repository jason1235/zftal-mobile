package com.zfsoft.mobile.exampaper.entity;

import java.util.Date;

/**
 * 问卷调查实体类
 * @author liucb
 */
public class Exampaper {

	private String paperMainId; //问卷id
	private String paperMainName; //问卷名称
	private String instruction; //问卷简介
	private Date createTime; //问卷创建时间或最后一次修改时间
	private Date startTime; //问卷开始时间
	private Date endTime; //问卷结束时间
	private String remark; //问卷备注
	private String creater; //问卷创建人
	private String status; //问卷状态
	private String qnOwner;
	private String shiFouCanYu; //用于记录某个用户是否参与该项问卷
	private String qn_owner;
	
	public String getPaperMainId() {
		return paperMainId;
	}
	public void setPaperMainId(String paperMainId) {
		this.paperMainId = paperMainId;
	}
	public String getPaperMainName() {
		return paperMainName;
	}
	public void setPaperMainName(String paperMainName) {
		this.paperMainName = paperMainName;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getQnOwner() {
		return qnOwner;
	}
	public void setQnOwner(String qnOwner) {
		this.qnOwner = qnOwner;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getShiFouCanYu() {
		return shiFouCanYu;
	}
	public void setShiFouCanYu(String shiFouCanYu) {
		this.shiFouCanYu = shiFouCanYu;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getQn_owner() {
		return qn_owner;
	}
	public void setQn_owner(String qn_owner) {
		this.qn_owner = qn_owner;
	} 
	
	
}
