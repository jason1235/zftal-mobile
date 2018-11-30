package com.zfsoft.hrm.staffturn.config;

/** 
 * 人员状态更新常量配置
 * @author jinjj
 * @date 2012-8-20 下午05:56:23 
 *  
 */
public interface IStatusUpdateConfig {

	/**
	 * 基本信息类GUID
	 */
	public final String BASEINFO_CLASS_ID = "C393FE11C4DC8E46E040007F01003F39";
	
	/**
	 * 人员当前状态字段名称
	 */
	public final String STATUS_COLUMN_NAME = "dqztm";
	
	/**
	 * 离校代码，用于更新至人员状态
	 */
	public final String LEAVE_SCHOOL_STATUS = "98";
	/**
	 * 退休人员
	 */
	public final String RETIRE_STATUS = "01";
	
	/**
	 * 延聘
	 */
	public final String RETIRE_ISEMPLOY = "12";
	/**
	 * 返聘
	 */
	public final String RETIRE_REENGAGE = "04";
	/**
	 * 死亡人员
	 */
	public final String DEAD_STATUS = "03";
	
	/**
	 * 离校代码中需过滤不做处理的离校类型码，避免覆盖退休，死亡状态
	 * 离休：11
	 * 退休：12
	 * 死亡：53
	 */
	public final String LEAVE_SCHOOL_STATUS_FILTER_CODE = "11,12,53";
}
