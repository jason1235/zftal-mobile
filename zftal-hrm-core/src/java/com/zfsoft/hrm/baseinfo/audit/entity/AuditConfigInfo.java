package com.zfsoft.hrm.baseinfo.audit.entity;

import java.util.List;

/** 
 * 审核配置信息
 * @author jinjj
 * @date 2012-9-28 下午02:57:11 
 *  
 */
public class AuditConfigInfo {

	private String classId;
	
	private String className;
	
	private List<AuditDefine> list;

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public List<AuditDefine> getList() {
		return list;
	}

	public void setList(List<AuditDefine> list) {
		this.list = list;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
}
