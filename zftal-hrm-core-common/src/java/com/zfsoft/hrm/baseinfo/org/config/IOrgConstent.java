package com.zfsoft.hrm.baseinfo.org.config;

public interface IOrgConstent {

	/**
	 * 基本信息类GUID
	 */
	public final String BASEINFO_CLASS_ID = "C393FE11C4DC8E46E040007F01003F39";
	/**
	 * 综合信息类GUID
	 */
	public final String SYNINFO_CLASS_ID = "O14CAAC75489CDB5E040007F01001AC3";
	
	/**
	 * 人员部门字段名称
	 */
	public final String ORG_COLUMN_NAME = "dwh";
	/**
	 * 人员借入部门字段名称
	 */
	public final String ORG_COLUMN_JRBM = "jrbm";
	
	/**
	 * 专业技术职务字段名称（个人概况）
	 */
	public final String DUTY_COLUMN_NAME = "zjzw";
	
	/**
	 * 聘任专业技术职务字段名称（个人综合信息OVERALL）
	 */
	public final String PRZYJSZW_COLUMN_NAME = "przyjszw";
}
