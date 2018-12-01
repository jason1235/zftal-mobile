package com.zfsoft.mobile.servlet.entity;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class OneCardEntity {

	private String cardNumber;//卡号
	private String cardBlance;//余额
	
	
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCardBlance() {
		return cardBlance;
	}
	public void setCardBlance(String cardBlance) {
		this.cardBlance = cardBlance;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
	
}
