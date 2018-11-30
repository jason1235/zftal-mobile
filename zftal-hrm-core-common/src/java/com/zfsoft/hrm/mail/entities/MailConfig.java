package com.zfsoft.hrm.mail.entities;

/**
 * 
 * @author ChenMinming
 * @date 2013-10-17
 * @version V1.0.0
 */
public class MailConfig {
	/**
	 * 信件配置类型（EMAIL 邮件;SMS 短信）
	 */
	private String type;
	/**
	 * 信件服务器地址
	 */
	private String host;
	/**
	 * 信件服务器post
	 */
	private Integer port=25;
	/**
	 * 发送邮箱
	 */
	private String send;
	/**
	 * 鉴权账号
	 */
	private String user;
	/**
	 * 鉴权密码
	 */
	private String pwd;
	/**
	 * 返回
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置
	 * @param type 
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 返回
	 */
	public String getHost() {
		return host;
	}
	/**
	 * 设置
	 * @param host 
	 */
	public void setHost(String host) {
		this.host = host;
	}
	/**
	 * 返回
	 */
	public String getSend() {
		return send;
	}
	/**
	 * 设置
	 * @param send 
	 */
	public void setSend(String send) {
		this.send = send;
	}
	/**
	 * 返回
	 */
	public String getUser() {
		return user;
	}
	/**
	 * 设置
	 * @param user 
	 */
	public void setUser(String user) {
		this.user = user;
	}
	/**
	 * 返回
	 */
	public String getPwd() {
		return pwd;
	}
	/**
	 * 设置
	 * @param pwd 
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	/**
	 * 返回
	 */
	public Integer getPort() {
		return port;
	}
	/**
	 * 设置
	 * @param post 
	 */
	public void setPort(Integer port) {
		this.port = port;
	}
	
	
}
