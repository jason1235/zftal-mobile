package com.zfsoft.workflow.enumobject;

/**
 * 
 * 类描述：业务枚举类
 * 
 * @version: 1.0
 * @author: yingjie.fan
 * @version: 2013-3-12 下午01:43:43
 */
public enum BusinessEnum {
	// 职务聘任模块使用
	JH_GJZW("高级职务计划审核", "JH_GJZW"), // 高级职务计划审核
	PS_ZWRD("职务认定审核", "PS_ZWRD"), // 职务认定审核
	PS_JSZJZWYS("晋升中级职务预审核", "PS_JSZJZWYS"), // 晋升中级职务预审核
	PS_JSZJZW("晋升中级职务审核", "PS_JSZJZW"), // 晋升中级职务审核
	PS_JSGJZW("晋升高级职务审核", "PS_JSGJZW"), // 晋升高级职务审核
	
	PS_JSGJZWYS("晋升高级职务预审核", "PS_JSGJZWYS"), // 晋升高级职务预审核
	
	PS_WKFGYS("晋升文科副高职务预审核", "PS_WKFGYS"), // 晋升文科副高职务预审核
	PS_LKFGYS("晋升理科副高职务预审核", "PS_LKFGYS"), // 晋升理科副高职务预审核
	PS_WKZGYS("晋升文科正高职务预审核", "PS_WKZGYS"), // 晋升文科正高职务预审核
	PS_LKZGYS("晋升理科正高职务预审核", "PS_LKZGYS"), // 晋升理科正高职务预审核
	
	// 社会福利模块使用
	FL_TQJ("探亲假审核", "FL_TQJ"), // 探亲假审核
	FL_SZFXJ("丧葬抚恤金审核", "FL_SZFXJ"), // 丧葬抚恤金审核
	FL_ZGDBBZ("职工大病补助审核", "FL_ZGDBBZ"), // 职工大病补助审核
	FL_DSZNFY("独生子女费用审核", "FL_DSZNFY"), // 独生子女费用审核
	FL_TFBX("托费报销审核", "FL_TFBX"), // 托费报销审核
	
	// 【个人信息】模块使用
	SH_GRXX("个人信息审核", "SH_GRXX"), // 个人信息审核
	
	//科级定级使用
	KJ_KJDJ("科级定级审核", "KJ_KJDJ"), // 科级定级审核
	
	//年度考核使用
	KH_NDKH("年度考核审核", "KH_NDKH"), // 年度考核审核
	//年度部门汇总考核
	KH_NDKH_DEPT("年度部门汇总考核", "KH_NDKH_DEPT"), // 年度部门汇总考核
	
	KH_NDKH_BSHCZKH("博士后出站考核", "KH_NDKH_BSHCZKH"),
	
	//培训进修使用
	PX_DEGREE_TRAIN("学历学位进修审核", "PX_DEGREE_TRAIN"), // 学历学位进修审核
	PX_OUT_TRAIN("出国进修审核", "PX_OUT_TRAIN"), // 出国进修审核
	PX_PRACTICE("下企业实践审核", "PX_PRACTICE"), // 下企业实践审核
	PX_NOT_DEGREE_TRAIN("非学历学位进修审核", "PX_NOT_DEGREE_TRAIN"), // 非学历学位进修审核
	PX_DOCTOR_TRAIN("博士后进站进修审核", "PX_DOCTOR_TRAIN"),
	
	//考勤管理使用
	KQ_YDKQ("月度考勤审核", "KQ_YDKQ"), // 月度考勤审核
	
	//加班管理使用
	KQ_JBSQ("加班申请审核","KQ_JBSQ"),  //加班申请
	
	//专家推荐审核
	ZJ_TJSH("专家推荐审核","ZJ_TJSH"),                //专家推荐审核
	
	// 培训进修模块使用
	PX_PXJX("培训进修审核", "PX_PXJX"), // 培训进修审核
	
	// 岗位申报模块使用
	DG_SGSH("上岗审核", "DG_SGSH"), // 上岗审核
	DG_KHSH("考核审核", "DG_KHSH"), // 考核审核
	DG_SBCLSH("申报材料审核", "DG_SBCLSH"),//申报材料审核
	//人事异动下调动审核推代办事宜中使用
	YD_DDSH("调动审核","YD_DDSH"),//调动审核
	
	//离校管理添加代办事宜
	YD_LXCL("离校处理","YD_LXCL"),//离校处理
	
	//人才报到确认添加代办事宜
	YP_RCBD("人才报到确认","YP_RCBD"),//人才报到确认
	
	// 人才招聘模块使用
	JH_BZJH("编制计划审核", "JH_BZJH"), // 编制计划审核
	YP_JXKY("教学科研类人员应聘审核", "YP_JXKY"), // 教学科研类人员应聘审核
	YP_FDY("辅导员类人员应聘审核", "YP_FDY"), // 辅导员类人员应聘审核
	YP_QT("其他类人员审核", "YP_QT"), // 其他类人员审核
	YP_JL("个人简历", "YP_JL"), // 个人简历
	
	// 月工资审核模块使用
	SH_YGZ_DIRECTOR("班主任申报审核", "SH_YGZ_DIRECTOR"), // 班主任申报审核
	SH_YGZ_TEACHER("教师申报审核", "SH_YGZ_TEACHER"), // 教师申报审核
	SH_YGZ_TRADE("科员申报审核", "SH_YGZ_TRADE"), // 科员申报审核
	
	// 【业务信息类审核】
	SH_BNSCLASS("业务信息类审核", "SH_BNSCLASS"); // 个人信息审核

	/**
	 * 定义枚举类型自己的属性
	 */
	private final String text;
	private final String key;

	private BusinessEnum(String text, String key) {
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
