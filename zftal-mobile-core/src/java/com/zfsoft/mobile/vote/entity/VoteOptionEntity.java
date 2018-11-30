package com.zfsoft.mobile.vote.entity;

/**
 * 投票选项实体
 * @author liucb
 *
 */
public class VoteOptionEntity {

	private String voteOptionId;
	private String voteOptionDescription;
	private String voteOptionSort;
	private String voteMainId;
	private String pictureName;
	private String picturePath;
	private byte[] pictureContent;
	
	private String votedOption;  //用于详情判断是否投过该选项
	
	
	public String getVoteOptionId() {
		return voteOptionId;
	}
	public void setVoteOptionId(String voteOptionId) {
		this.voteOptionId = voteOptionId;
	}
	public String getVoteOptionDescription() {
		return voteOptionDescription;
	}
	public void setVoteOptionDescription(String voteOptionDescription) {
		this.voteOptionDescription = voteOptionDescription;
	}
	public String getVoteOptionSort() {
		return voteOptionSort;
	}
	public void setVoteOptionSort(String voteOptionSort) {
		this.voteOptionSort = voteOptionSort;
	}
	public String getVoteMainId() {
		return voteMainId;
	}
	public void setVoteMainId(String voteMainId) {
		this.voteMainId = voteMainId;
	}
	public String getPictureName() {
		return pictureName;
	}
	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public byte[] getPictureContent() {
		return pictureContent;
	}
	public void setPictureContent(byte[] pictureContent) {
		this.pictureContent = pictureContent;
	}
	public String getVotedOption() {
		return votedOption;
	}
	public void setVotedOption(String votedOption) {
		this.votedOption = votedOption;
	}
}
