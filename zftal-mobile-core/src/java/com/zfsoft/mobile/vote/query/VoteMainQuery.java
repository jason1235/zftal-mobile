package com.zfsoft.mobile.vote.query;

import java.util.Date;

import com.zfsoft.dao.query.BaseQuery;

public class VoteMainQuery extends BaseQuery{
	private String voteId;             //id
	private String voteTitle;          //标题
	private String voteType;           //类型 0文字1图片2打分
	private String voteIsMultiSelect;  //是否多选
	private String voteIsAnonymous;    //是否匿名
	private String voteResultOnlySee;  //结果仅发起人可见
	private Date voteEndDate;          //截止时间
	private Date voteStartDate;        //发起时间
	private String voteOrganiserId;    //发起人id
	private String voteOrganiserName;  //发起人姓名 
	private String userId;             //用户账号
	private String mineVoteFlag;       //值为1时查询我发起的投票  
	private String participateFlag;    //值为1时查询我参与的投票 
	private String voteIsDraft;        //是否草稿 0不是 1是
	private String voteStop;           //结束投票 0匿名1实名  默认匿名
	
	public String getVoteId() {
		return voteId;
	}
	public void setVoteId(String voteId) {
		this.voteId = voteId;
	}
	public String getVoteTitle() {
		return voteTitle;
	}
	public void setVoteTitle(String voteTitle) {
		this.voteTitle = voteTitle;
	}
	public String getVoteType() {
		return voteType;
	}
	public void setVoteType(String voteType) {
		this.voteType = voteType;
	}
	public String getVoteIsMultiSelect() {
		return voteIsMultiSelect;
	}
	public void setVoteIsMultiSelect(String voteIsMultiSelect) {
		this.voteIsMultiSelect = voteIsMultiSelect;
	}
	public String getVoteIsAnonymous() {
		return voteIsAnonymous;
	}
	public void setVoteIsAnonymous(String voteIsAnonymous) {
		this.voteIsAnonymous = voteIsAnonymous;
	}
	public String getVoteResultOnlySee() {
		return voteResultOnlySee;
	}
	public void setVoteResultOnlySee(String voteResultOnlySee) {
		this.voteResultOnlySee = voteResultOnlySee;
	}
	public Date getVoteEndDate() {
		return voteEndDate;
	}
	public void setVoteEndDate(Date voteEndDate) {
		this.voteEndDate = voteEndDate;
	}
	public Date getVoteStartDate() {
		return voteStartDate;
	}
	public void setVoteStartDate(Date voteStartDate) {
		this.voteStartDate = voteStartDate;
	}
	public String getVoteOrganiserId() {
		return voteOrganiserId;
	}
	public void setVoteOrganiserId(String voteOrganiserId) {
		this.voteOrganiserId = voteOrganiserId;
	}
	public String getVoteOrganiserName() {
		return voteOrganiserName;
	}
	public void setVoteOrganiserName(String voteOrganiserName) {
		this.voteOrganiserName = voteOrganiserName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMineVoteFlag() {
		return mineVoteFlag;
	}
	public void setMineVoteFlag(String mineVoteFlag) {
		this.mineVoteFlag = mineVoteFlag;
	}
	public String getParticipateFlag() {
		return participateFlag;
	}
	public void setParticipateFlag(String participateFlag) {
		this.participateFlag = participateFlag;
	}
	public String getVoteIsDraft() {
		return voteIsDraft;
	}
	public void setVoteIsDraft(String voteIsDraft) {
		this.voteIsDraft = voteIsDraft;
	}
	public String getVoteStop() {
		return voteStop;
	}
	public void setVoteStop(String voteStop) {
		this.voteStop = voteStop;
	}
}
