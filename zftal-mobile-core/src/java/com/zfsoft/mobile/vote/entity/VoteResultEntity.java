package com.zfsoft.mobile.vote.entity;

import java.util.Date;

/**
 * 投票结果实体
 * @author Administrator
 *
 */
public class VoteResultEntity {
	private String voteResultId;
	private String voteUserId;
	private String voteOptionId;
	private String voteOptionScore;
	private Date voteTime;
	
	public String getVoteResultId() {
		return voteResultId;
	}
	public void setVoteResultId(String voteResultId) {
		this.voteResultId = voteResultId;
	}
	public String getVoteUserId() {
		return voteUserId;
	}
	public void setVoteUserId(String voteUserId) {
		this.voteUserId = voteUserId;
	}
	public String getVoteOptionId() {
		return voteOptionId;
	}
	public void setVoteOptionId(String voteOptionId) {
		this.voteOptionId = voteOptionId;
	}
	public String getVoteOptionScore() {
		return voteOptionScore;
	}
	public void setVoteOptionScore(String voteOptionScore) {
		this.voteOptionScore = voteOptionScore;
	}
	public Date getVoteTime() {
		return voteTime;
	}
	public void setVoteTime(Date voteTime) {
		this.voteTime = voteTime;
	}
}
