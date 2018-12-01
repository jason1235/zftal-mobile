package com.zfsoft.mobile.servlet.entity;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class OneCardConsumeItemEntity {

	private String consumeAspect;//消费地点
	private String consumetime;//消费时间
	private String balance;//消费之后一卡通余额
	private String outlay;//消费金额
	
	public String getConsumeAspect() {
		return consumeAspect;
	}
	public void setConsumeAspect(String consumeAspect) {
		this.consumeAspect = consumeAspect;
	}
	public String getConsumetime() {
		return consumetime;
	}
	public void setConsumetime(String consumetime) {
		this.consumetime = consumetime;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getOutlay() {
		return outlay;
	}
	public void setOutlay(String outlay) {
		this.outlay = outlay;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
	
}
