package com.zfsoft.mobile.alipay.entity;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.google.gson.Gson;

public class AlipaySignEntity {

	private String app_id;//
	private BizContent biz_content;//
	private String charset;//
	private String method;//
	private String sign_type;//
	private String notify_url;//
	private String timestamp;//
	private String version;//
	private String sign;//
	private String orderInfoEncoded;//
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	
	public BizContent getBiz_content() {
		return biz_content;
	}
	public void setBiz_content(BizContent biz_content) {
		this.biz_content = biz_content;
	}
	@Override
	public String toString() {
		return "app_id=" + app_id + "&biz_content="
				+ biz_content + "&charset=" + charset + "&method=" + method
				+ "&sign_type=" + sign_type + "&notify_url=" + notify_url
				+ "&timestamp=" + timestamp + "&version=" + version
				+ "&sign=" + sign;
	}
	
	
	public static void main(String[] args) {
		AlipaySignEntity entity = new AlipaySignEntity();
		entity.setApp_id("2016112103056954");
		entity.setMethod("alipay.trade.app.pay");
		entity.setSign_type("RSA");
		entity.setCharset("utf-8");
		BizContent bizContent = new BizContent();
		bizContent.setOut_trade_no("051114173113835");
		bizContent.setProduct_code("QUICK_MSECURITY_PAY");
		bizContent.setSubject("卡片充值");
		bizContent.setTotal_amount("0.01");
		entity.setBiz_content(bizContent);
		entity.setNotify_url("http://demo.zfsoft.com/ALiPay/servlet/NotifyServlet/getSign");
		entity.setTimestamp("2017-05-1116:12:31");
		entity.setVersion("1.0");
		Gson gson = new Gson();
		System.out.println(gson.toJson(entity));
		 AlipaySignEntity dataEntity = gson.fromJson(gson.toJson(entity), AlipaySignEntity.class);
	}
	public void setOrderInfoEncoded(String orderInfoEncoded) {
		this.orderInfoEncoded = orderInfoEncoded;
	}
	public String getOrderInfoEncoded() {
		return orderInfoEncoded;
	}
	
}
