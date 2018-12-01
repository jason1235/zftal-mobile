package com.zfsoft.mobile.vote.entity;


/**
 * 投票结果选项统计实体
 * @author Administrator
 *
 */
public class VoteCountEntity {
	private String optionId;  //选项id
	private String description; //选项名称
	private int amount; //被选次数
	private String percentage; //百分比
	private String score;//打分类型总分
	
	public String getOptionId() {
		return optionId;
	}
	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
}
