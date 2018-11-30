package com.zfsoft.mobile.exampaper.query;

import com.zfsoft.dao.query.BaseQuery;

/**
 * 问卷调查实体类
 * @author liucb
 */
public class ExampaperQuery extends BaseQuery{

	private static final long serialVersionUID = 1L;
	private String paperMainId; //问卷id
	private String paperMainName; //问卷名称
	private String instruction; //问卷简介
	private String createTime; //问卷创建时间或最后一次修改时间
	private String startTime; //问卷开始时间
	private String endTime; //问卷结束时间
	private String remark; //问卷备注
	private String creater; //问卷创建人
	private String qnOwner;
	private String userId; //用户ID
	private String jsonStr;
	
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getJsonStr() {
		return jsonStr;
	}
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	} 
}
