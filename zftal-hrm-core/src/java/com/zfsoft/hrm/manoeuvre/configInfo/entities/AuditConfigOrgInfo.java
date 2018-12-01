package com.zfsoft.hrm.manoeuvre.configInfo.entities;

import java.io.Serializable;

public class AuditConfigOrgInfo implements Serializable {

	private static final long serialVersionUID = -8901525420597572840L;

	private String aoid;	//可审部门设置信息编号
	
	private String aid;		//所属审核环节设置信息编号
	
	private String oid;		//可审部门编号

	/**
	 * 返回
	 * @return 
	 */
	public String getAoid() {
		return aoid;
	}

	/**
	 * 设置
	 * @param aoid 
	 */
	public void setAoid(String aoid) {
		this.aoid = aoid;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getAid() {
		return aid;
	}

	/**
	 * 设置
	 * @param aid 
	 */
	public void setAid(String aid) {
		this.aid = aid;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getOid() {
		return oid;
	}

	/**
	 * 设置
	 * @param oid 
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}
	
	
}
