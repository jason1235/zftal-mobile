package com.zfsoft.mobile.ballot.entity;


/*
 * 移动_文艺投票_用户投票记录
 */
public class BallotRecord {

	//投票记录的Id
	private String id;
	
	//投票人的id
	private String userId;
	
	//投票的信息id
	private String ballotDetailId;
	
	//投票的时间
	private String ballotTime;

	private String ballotId;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBallotDetailId() {
		return ballotDetailId;
	}

	public void setBallotDetailId(String ballotDetailId) {
		this.ballotDetailId = ballotDetailId;
	}

	public String getBallotTime() {
		return ballotTime;
	}

	public void setBallotTime(String ballotTime) {
		this.ballotTime = ballotTime;
	}

	public String getBallotId() {
		return ballotId;
	}

	public void setBallotId(String ballotId) {
		this.ballotId = ballotId;
	}
	
	
}
