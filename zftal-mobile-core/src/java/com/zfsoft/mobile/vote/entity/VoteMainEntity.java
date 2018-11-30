package com.zfsoft.mobile.vote.entity;

import java.util.Date;

/**
 * 投票实体
 * @author liucb
 *
 */
public class VoteMainEntity {
	
	private String voteId;             //id
	private String voteTitle;          //标题
	private String voteDescription;    //描述
	private String voteType;           //类型 0文字1打分2图片
	private String voteIsMultiSelect;  //是否多选
	private String voteMaxChoice;      //多选时最多选几项
	private String voteIsAnonymous;    //是否匿名
	private String voteResultOnlySee;  //结果仅发起人可见
	private Date voteEndDate;          //截止时间
	private Date voteStartDate;        //发起时间
	private String voteOrganiserId;    //发起人id VOTEORGANISERID
	private String voteOrganiserName;  //发起人姓名 
	private String voteMaxScore;       //打分题最大分值
	private String voteScoreMethod;    //打分类型分数统计方式 0平均分1总分
	private String status;             //投票状态
	private String voteIsDraft;        //是否草稿
	private String voteStop;           //结束投票 0匿名1实名  默认匿名
	private String voteTotalPersonsCount;     //总计参与人数
	 
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
	public String getVoteMaxScore() {
		return voteMaxScore;
	}
	public void setVoteMaxScore(String voteMaxScore) {
		this.voteMaxScore = voteMaxScore;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getVoteTotalPersonsCount() {
		return voteTotalPersonsCount;
	}
	public void setVoteTotalPersonsCount(String voteTotalPersonsCount) {
		this.voteTotalPersonsCount = voteTotalPersonsCount;
	}
	public String getVoteDescription() {
		return voteDescription;
	}
	public void setVoteDescription(String voteDescription) {
		this.voteDescription = voteDescription;
	}
	public String getVoteMaxChoice() {
		return voteMaxChoice;
	}
	public void setVoteMaxChoice(String voteMaxChoice) {
		this.voteMaxChoice = voteMaxChoice;
	}
	public String getVoteScoreMethod() {
		return voteScoreMethod;
	}
	public void setVoteScoreMethod(String voteScoreMethod) {
		this.voteScoreMethod = voteScoreMethod;
	}
}
