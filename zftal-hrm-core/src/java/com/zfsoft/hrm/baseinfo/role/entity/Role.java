package com.zfsoft.hrm.baseinfo.role.entity;

/** 
 * 角色
 * @author jinjj
 * @date 2012-10-8 上午11:38:29 
 *  
 */
public class Role {

	private String guid;
	
	private String name;
	
	private String remark;

	/**
	 * 唯一ID
	 * @return
	 */
	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * 角色名称
	 * @return
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 角色备注
	 * @return
	 */
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
