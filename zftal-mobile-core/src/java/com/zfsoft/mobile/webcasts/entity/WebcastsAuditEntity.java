package com.zfsoft.mobile.webcasts.entity;

import java.io.Serializable;
import java.util.Date;

import com.zfsoft.dao.query.BaseQuery;

/**
 *移动直播间申请审核实体封装类 
 * @table("M_WEBCASTS_AUDIT")
 * @author yangbilin
 * @createtime 2017-09-12
 */
public class WebcastsAuditEntity extends BaseQuery implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String appid;
	private String userid;
	private String isaudit;  //审核状态1通过  \0申请 2驳回
	private String appreason;//申请原因
	private String auditReason;//审核理由
	private Date createTime;  //创建时间
	private Date auditTime;   //审核时间
	
	private String createtimeStr;
	private String username;
	
	
	
	public WebcastsAuditEntity() {
		super();
	}
	
	
	
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 *审核状态 1通过  \0申请 2驳回
	 *@return String
	 */
	public String getIsaudit() {
		return isaudit;
	}

	public void setIsaudit(String isaudit) {
		this.isaudit = isaudit;
	}
	
	/**
	 *申请原因
	 *@return String 
	 */
	public String getAppreason() {
		return appreason;
	}

	public void setAppreason(String appreason) {
		this.appreason = appreason;
	}

	/**
	 *审核理由
	 *@return String
	 */
	public String getAuditReason() {
		return auditReason;
	}

	public void setAuditReason(String auditReason) {
		this.auditReason = auditReason;
	}

	/**
	 *创建时间
	 *@return Date
	 */
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
		createtimeStr = format.format(createTime);
		this.createTime = createTime;
	}
	
	/**
	 *创建时间
	 *@return  String
	 */
	public String getCreatetimeStr() {
		return createtimeStr;
	}

	public void setCreatetimeStr(String createtimeStr) {
		this.createtimeStr = createtimeStr;
	}
	
	/**
	 *审核时间
	 *@return Date
	 */
	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
}
