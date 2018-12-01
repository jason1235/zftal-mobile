/**
 * 
 */
package com.zfsoft.mobile.alipay.entity;

import com.google.gson.Gson;

/**
 * @author zhangxu
 * @description 
 * @date 2017-5-15 上午11:27:25
 */
public class BizContent {

	private String total_amount;//
	private String subject;//
	private String out_trade_no;//
	private String product_code;//
	public String getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	@Override
	public String toString() {
		Gson gson = new Gson();
		System.out.println(gson.toJson(this));
		return gson.toJson(this);
	}
	
	
}
