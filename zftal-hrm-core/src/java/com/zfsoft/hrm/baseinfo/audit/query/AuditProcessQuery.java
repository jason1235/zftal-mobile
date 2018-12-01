package com.zfsoft.hrm.baseinfo.audit.query;

import java.util.List;

import com.zfsoft.dao.query.BaseQuery;

/** 
 * 审核流程query
 * @author jinjj
 * @date 2012-10-9 下午03:02:45 
 *  
 */
public class AuditProcessQuery extends BaseQuery {

	private static final long serialVersionUID = -7549223413478567064L;

	private Integer stepType;
	
	private String classId;
	
	private String gh;
	
	private String logId;
	
	private List<String> roleId;
	
	private String express;
	
	private String globalId;

	/**
	 * 流程状态 -1：审核未通过 0:审核未完成  1:审核中 99:审核完成
	 * @return
	 */
	public Integer getStepType() {
		return stepType;
	}

	public void setStepType(Integer stepType) {
		this.stepType = stepType;
	}

	/**
	 * 信息类ID
	 * @return
	 */
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	/**
	 * 工号
	 * @return
	 */
	public String getGh() {
		return gh;
	}

	public void setGh(String gh) {
		this.gh = gh;
	}

	public List<String> getRoleId() {
		return roleId;
	}

	public void setRoleId(List<String> roleId) {
		this.roleId = roleId;
	}

	/**
	 * 数据ID 用于保证数据只一条待审记录
	 * @return
	 */
	public String getGlobalId() {
		return globalId;
	}

	public void setGlobalId(String globalId) {
		this.globalId = globalId;
	}

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

}
