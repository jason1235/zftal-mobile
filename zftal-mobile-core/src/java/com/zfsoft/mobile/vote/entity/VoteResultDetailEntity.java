package com.zfsoft.mobile.vote.entity;

/**
 * 投票结果详情实体
 * @author Administrator
 *
 */
public class VoteResultDetailEntity {
	private String userId;
	private String xm;
	private String times;
	private String lastVoteTime;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public String getLastVoteTime() {
		return lastVoteTime;
	}
	public void setLastVoteTime(String lastVoteTime) {
		this.lastVoteTime = lastVoteTime;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
}
