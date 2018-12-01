package com.zfsoft.mobile.ballot.entity;


/*
 * 移动_文艺投票_投票详情信息表
 */
public class BallotDetail {

	//投票详情的Id
	private String id;
	
	//所属投票的id
	private String ballotId;
	
	//投票信息图片文件
	private byte[] baseImg;
	
	//投票信息图片文件路劲
	private String baseImgPath;
	
	//投票信息名称
	private String name;
	
	//获得票数
	private Integer count;
	
	private String baseImgName;
	
	//候选人编号
	private String identifier;
	
	//用户是否已投票
	private Integer isBallot;

	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBallotId() {
		return ballotId;
	}

	public void setBallotId(String ballotId) {
		this.ballotId = ballotId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getBaseImgName() {
		return baseImgName;
	}

	public void setBaseImgName(String baseImgName) {
		this.baseImgName = baseImgName;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Integer getIsBallot() {
		return isBallot;
	}

	public void setIsBallot(Integer isBallot) {
		this.isBallot = isBallot;
	}
	
}
