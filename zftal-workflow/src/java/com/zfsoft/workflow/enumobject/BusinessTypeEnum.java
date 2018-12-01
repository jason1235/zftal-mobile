package com.zfsoft.workflow.enumobject;

/**
 * 
 * 类描述：业务类型枚举类
 * 
 * @version: 1.0
 * @author: yingjie.fan
 * @version: 2013-3-12 下午01:43:43
 */
public enum BusinessTypeEnum {
	// 职务聘任（职称评审）业务模块使用
	GJZW_TYPE("高级职务计划类别", "GJZW_TYPE"), // 高级职务计划类别
	ZWPR_TYPE("职务聘任类别", "ZWPR_TYPE"), // 职务聘任类别
	
	// 社会福利模块使用
	SHFL_TYPE("社会福利类别", "SHFL_TYPE"),// 社会福利类别
	
	// 科级定级模块使用
	KJDJ_TYPE("科级定级类别", "KJDJ_TYPE"),// 科级定级类别
	
	// 年度考核模块使用
	KH_NDKH("年度考核类别", "KH_NDKH"),// 年度考核类别

	//培训进修模块使用
	PX_DEGREE_TRAIN_TYPE("学历学位进修类别", "PX_DEGREE_TRAIN_TYPE"), // 学历学位进修类别
	PX_OUT_TRAIN_TYPE("出国进修类别", "PX_OUT_TRAIN_TYPE"), // 出国进修类别
	PX_PRACTICE_TYPE("下企业实践类别", "PX_PRACTICE_TYPE"), // 下企业实践类别
	PX_NOT_DEGREE_TRAIN_TYPE("非学历学位进修类别", "PX_NOT_DEGREE_TRAIN_TYPE"), // 非学历学位进修类别
	PX_DCOTOR_TRAIN_TYPE("博士后进站进修类别", "PX_DCOTOR_TRAIN_TYPE"),

	//考勤管理使用
	KQGL_TYPE("考勤管理类别", "KQGL_TYPE"), // 考勤管理类别
	
	// 培训进修模块使用
	PXJX_TYPE("培训进修类别", "PX_TYPE"), // 培训进修类别
	
	// 岗位申报模块使用
	DG_TYPE("岗位申报类别", "DG_TYPE"), // 岗位申报类别
	
	// 人才招聘模块业务使用
	BZJH_TYPE("编制计划类别", "BZJH_TYPE"), // 编制计划类别
	RYYP_TYPE("人员应聘类别", "RYYP_TYPE"),// 人员应聘类别
	
	// 月工资审核模块使用
	YGZSH_TYPE("月工资审核类别", "YGZSH_TYPE"), // 月工资审核类别
	
	BUSINESSCLASS_TYPE("编制计划类别", "BUSINESSCLASS_TYPE"); // 业务信息类审批类别

	/**
	 * 定义枚举类型自己的属性
	 */
	private final String text;
	private final String key;

	private BusinessTypeEnum(String text, String key) {
		this.text = text;
		this.key = key;
	}

	/**
	 * 展示文本
	 * 
	 * @return
	 */
	public String getText() {
		return text;
	}

	/**
	 * 代码编号
	 * 
	 * @return
	 */
	public String getKey() {
		return key;
	}
}
