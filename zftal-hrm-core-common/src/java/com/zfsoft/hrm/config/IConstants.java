package com.zfsoft.hrm.config;

import java.util.ResourceBundle;

import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;


/** 
 * @ClassName: IConstants 
 * @Description: 常量类
 * @author jinjj
 * @date 2012-5-30 下午04:12:24 
 *  
 */
public class IConstants {

	public final static String getValueFormProp(String field,String defVal){
		try{
			ResourceBundle p = ResourceBundle.getBundle("constants");
			return p.getString(field);
		}catch(Throwable t){
			return defVal;
		}
		
	}
	/**
	 * 通用节点根标识
	 */
	public final static String ROOT = "root";
	
	/**
	 * 通用字符串分隔符,";"
	 */
	public final static String SPLIT_STR = ";";
	
	/**
	 * 通用条件ID，存储于条件SESSION中，用于深层页面返回上层时保存条件
	 */
	public final static String CONDITION_ID = "condition_id";
	
	/**
	 * session中保存的进度对象名称
	 */
	public final static String SESSION_PROGRESS = "session_progress";
	
	/**
	 * 通用页面数据行数
	 */
	public final static int COMMON_PAGE_SIZE = 20;
	/**
	 * 个人综合信息
	 */
	public final static String INFO_CATALOG_PERSON_SUMMARY = "O14CAAC75489CDB5E040007F01001AC3";
	
	/**
	 * 个人概况信息类id
	 */
	public final static String INFO_CATALOG_PERSON_JBXX = "C393FE11C4DC8E46E040007F01003F39";
	/**
	 * 信息类目录类型-默认类型（教师）
	 */
	public final static String INFO_CATALOG_TYPE_DEFAULT = "teacher";
	
	/**
	 * 信息类目录类型-教师
	 */
	public final static String INFO_CATALOG_TYPE_TEACHER = "teacher";
	
	/**
	 * 信息类目录类型-学生
	 */
	public final static String INFO_CATALOG_TYPE_STUDENT = "student";
	
	/**
	 * 信息维护描述信息-教职工信息服务
	 */
	public final static String FINFO_NAME_FELLOWS = "fellows";
	
	/**
	 * 放汇总查询的菜单
	 */
	public final static String COMMON_SEARCH_ROOT_MENU =getValueFormProp("COMMON_SEARCH_ROOT_MENU","N0500");

	/**
	 * 放分类查询的菜单
	 */
	public final static String CATALOG_QUERY_ROOT_MENU =getValueFormProp("CATALOG_QUERY_ROOT_MENU","N0502");
	
	/**
	 * 放统计分析的菜单
	 */
	public final static String REPORT_ROOT_MENU =getValueFormProp("REPORT_ROOT_MENU","N0503");
	
	/**
	 * 放报表趋势分析的菜单
	 */
	public final static String REPORT_QSFX_MENU =getValueFormProp("REPORT_QSFX_MENU","N0504");
	
	/**
	 * 放复合报表的菜单
	 */
	public final static String SENIORREPORT_ROOT_MENU =getValueFormProp("SENIORREPORT_ROOT_MENU","N0507");
	
	/**
	 * 代办事宜中用户请求信息类审核菜单
	 */
	public final static String DBSY_AUDIT_PROCESS_MENU =getValueFormProp("DBSY_AUDIT_PROCESS_MENU","N129701");
	
	/**
	 * 放个人信息的菜单
	 */
	public final static String PERSON_INFO_ROOT_MENU =getValueFormProp("PERSON_INFO_ROOT_MENU","N11");
	/**
	 * 放个人信息批量的菜单
	 */
	public final static String PERSON_INFO_BATCH_ROOT_MENU = getValueFormProp("PERSON_INFO_BATCH_ROOT_MENU", "N12");;
	
	/**
	 * 放考核审核管理的菜单
	 */
	public final static String AUDITING_ROOT_MENU = getValueFormProp("AUDITING_ROOT_MENU", "N0406");
	/**
	 *放登记表的菜单
	 */
	public final static String FORMINFO_ROOT_MENU = getValueFormProp("FORMINFO_ROOT_MENU", "N1003");

	/**
	 * 文件编码
	 */
	public final static String FILE_ENCODE = "utf-8";
	
	/**
	 * 显示模式
	 */
	public final static String VIEW_STYLE = "VIEW_STYLE";
	
	/**
	 * 信息类酬金管理
	 */
	public final static String CJGL = "CJGL";
	
	// 20140423 add start
	/**
	 * 干部
	 */
	public final static String CADRE = "02";
	
	/**
	 * 工人
	 */
	public final static String WORKER = "01";
	
	/**
	 * 副科以上
	 */
	public final static String DUTY_LEVER = "102";
	
	/**
	 * 发送对象_组织部
	 */
	public final static String SEND_LEVER_1 = CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, "008")==null?"":"008";
	
	/**
	 * 发送对象_人才交流中心
	 */
	public final static String SEND_LEVER_2 = CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, "002")==null?"":"002"; 
	
	/**
	 * 发送对象_劳资科
	 */
	public final static String SEND_LEVER_3 = CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, "0003")==null?"":"0003";
	
	/**
	 * 是否标志_是
	 */
	public final static String MARK_YES = "1";
	
	/**
	 * 是否标志_否
	 */
	public final static String MARK_NO = "0";
	// 20140423 add end
}
