package com.zfsoft.hrm.dagl.query;

import java.util.Date;

import com.zfsoft.dao.query.BaseQuery;

/**
 * 
 * @author ChenMinming
 * @date 2014-10-21
 * @version V1.0.0
 */
public class ArchivesQuery extends BaseQuery{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2417882179633255757L;
	private String id;
	//档案编号
	private String bh;
	//档案归属员工工号
	private String gh;
	//档案描述
	private String detail;
	//档案状态
	private String status;
	//存放位置
	private String savePoint;
	//档案类型
	private String type;
	//状态最后变更时间
	private Date changeStatusTime;
	//档案归属员工姓名
	private String xm;
	/**
	 * 返回
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置
	 * @param id 
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 返回
	 */
	public String getBh() {
		return bh;
	}
	/**
	 * 设置
	 * @param bh 
	 */
	public void setBh(String bh) {
		this.bh = bh;
	}
	/**
	 * 返回
	 */
	public String getGh() {
		return gh;
	}
	/**
	 * 设置
	 * @param gh 
	 */
	public void setGh(String gh) {
		this.gh = gh;
	}
	/**
	 * 返回
	 */
	public String getDetail() {
		return detail;
	}
	/**
	 * 设置
	 * @param detail 
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}
	/**
	 * 返回
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置
	 * @param status 
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 返回
	 */
	public String getSavePoint() {
		return savePoint;
	}
	/**
	 * 设置
	 * @param savePoint 
	 */
	public void setSavePoint(String savePoint) {
		this.savePoint = savePoint;
	}
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
	public Date getChangeStatusTime() {
		return changeStatusTime;
	}
	/**
	 * 设置
	 * @param changeStatusTime 
	 */
	public void setChangeStatusTime(Date changeStatusTime) {
		this.changeStatusTime = changeStatusTime;
	}
	/**
	 * 返回
	 */
	public String getXm() {
		return xm;
	}
	/**
	 * 设置
	 * @param xm 
	 */
	public void setXm(String xm) {
		this.xm = xm;
	}
	
	
}