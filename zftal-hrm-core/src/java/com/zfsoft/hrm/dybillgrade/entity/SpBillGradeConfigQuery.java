package com.zfsoft.hrm.dybillgrade.entity;

import com.zfsoft.dao.query.BaseQuery;

/**
 * 
 * @author ChenMinming
 * @date 2014-6-6
 * @version V1.0.0
 */
public class SpBillGradeConfigQuery extends BaseQuery{
	
	private static final long serialVersionUID = -3295603014417483214L;
	private String name;
	private String businessCode;
	private String billConfigId;
	/**
	 * 返回
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置
	 * @param name 
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 返回
	 */
	public String getBusinessCode() {
		return businessCode;
	}
	/**
	 * 设置
	 * @param businessCode 
	 */
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	/**
	 * 返回
	 */
	public String getBillConfigId() {
		return billConfigId;
	}
	/**
	 * 设置
	 * @param billConfigId 
	 */
	public void setBillConfigId(String billConfigId) {
		this.billConfigId = billConfigId;
	}
}
