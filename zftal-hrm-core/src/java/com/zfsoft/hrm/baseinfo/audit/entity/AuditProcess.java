package com.zfsoft.hrm.baseinfo.audit.entity;

import java.util.Date;
import java.util.List;

import com.zfsoft.hrm.baseinfo.audit.util.AuditDefineCacheUtil;

/** 
 * 审核进度
 * @author jinjj
 * @date 2012-9-28 上午11:35:06 
 *  
 */
public class AuditProcess {

	private String guid;
	
	private String logId;
	
	private int step;
	
	private String classId;
	
	private String gh;

	private Date createTime;
	
	private String roleId;
	
	private String globalId;
	
	/**
	 * 流程ID
	 * @return
	 */
	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * 日志ID
	 * @return
	 */
	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	/**
	 * 数据ID
	 * @return
	 */
	public String getGlobalId() {
		return globalId;
	}

	public void setGlobalId(String globalId) {
		this.globalId = globalId;
	}

	/**
	 * 审核步骤
	 * @return
	 */
	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
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

	/**
	 * 流程创建时间
	 * @return
	 */
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 角色ID
	 * @return
	 */
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	public int getStepSize(){
		List<AuditDefine> list = AuditDefineCacheUtil.getDefine(classId);
		if(list==null){
			return 0;
		}else{
			return list.size();
		}
	}
}
