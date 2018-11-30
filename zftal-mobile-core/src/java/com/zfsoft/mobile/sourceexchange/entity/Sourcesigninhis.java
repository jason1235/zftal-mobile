package com.zfsoft.mobile.sourceexchange.entity;

import java.io.Serializable;
import java.util.Date;

import com.zfsoft.dao.query.BaseQuery;

/**
 *用户签到实体类
 * Table(value="M_SOURCE_SIGNINHIS")
 * @author yangbilin
 * @createtime 20170708 15:45
 */
public class Sourcesigninhis extends BaseQuery implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userid;  //用户id
	private Integer source; //积分签到
	private String appsource;//签到来源
	private Date createtime;//创建时间
	
	private String createtimeStr;
	private String username;
	private String ast;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
		createtimeStr = format.format(createtime);
		this.createtime = createtime;
	}
	public String getAppsource() {
		return appsource;
	}
	public void setAppsource(String appsource) {
		this.appsource = appsource;
	}

	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCreatetimeStr() {
		return createtimeStr;
	}
	public void setCreatetimeStr(String createtimeStr) {
		this.createtimeStr = createtimeStr;
	}
	public String getAst() {
		return ast;
	}
	public void setAst(String ast) {
		this.ast = ast;
	}
	
	
}
