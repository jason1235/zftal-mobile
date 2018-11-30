package com.zfsoft.hrm.baseinfo.finfo.query;

import java.io.Serializable;

/**
 * {@link FormInfoMember}查询条件实体
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-23
 * @version V1.0.0
 */
public class FormInfoMemberQuery implements Serializable {
	
	private static final long serialVersionUID = 8019156786331332916L;

	private String name;		//成员组组名
	
	private String classId;		//组成成员所使用信息类ID
	
	private Boolean batch;		//是否批量操作

	/**
	 * 返回成员组组名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置成员组组名
	 * @param name 成员组组名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回组成成员所使用信息类ID
	 */
	public String getClassId() {
		return classId;
	}

	/**
	 * 设置组成成员所使用信息类ID
	 * @param classId 组成成员所使用信息类ID
	 */
	public void setClassId(String classId) {
		this.classId = classId;
	}

	/**
	 * @return the batch
	 */
	public Boolean getBatch() {
		return batch;
	}

	/**
	 * @param batch the batch to set
	 */
	public void setBatch(Boolean batch) {
		this.batch = batch;
	}
	
}
