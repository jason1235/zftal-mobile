package com.zfsoft.workflow.model;

import java.io.Serializable;

/**
 * 
 * 类描述：流程表单关联表实体
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-4-24 下午02:40:48
 */
public class SpProcedureBill extends BaseObject implements Serializable{
	
	
	/* serialVersionUID: serialVersionUID */
	
	private static final long serialVersionUID = -239471087152489298L;
	/* @property:ID */
	private String id;
	/* @property:流程ID */
	private String pid;
	/* @property:表单ID */
	private String billId;
	/* @property:表单类型 */
	private String billType;
	
	/* @property:表单名称 */
	private String billName;
	/* @property:是否选中 */
	private boolean checked;
	/* @property:表单类权限串 */
	private String classesPrivilege;
	
	/**
	 * @return id : return the property id.
	 */
	
	public String getId() {
		return id;
	}
	/**
	 * @param id : set the property id.
	 */
	
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return pid : return the property pid.
	 */
	
	public String getNodeId() {
		return pid;
	}
	/**
	 * @param pid : set the property pid.
	 */
	
	public void setNodeId(String pid) {
		this.pid = pid;
	}
	/**
	 * @return billId : return the property billId.
	 */
	
	public String getBillId() {
		return billId;
	}
	/**
	 * @param billId : set the property billId.
	 */
	
	public void setBillId(String billId) {
		this.billId = billId;
	}
	/**
	 * @return checked : return the property checked.
	 */
	
	public boolean isChecked() {
		return checked;
	}
	/**
	 * @param checked : set the property checked.
	 */
	
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	/**
	 * @return pid : return the property pid.
	 */
	
	public String getPid() {
		return pid;
	}
	/**
	 * @param pid : set the property pid.
	 */
	
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * @return billType : return the property billType.
	 */
	
	public String getBillType() {
		return billType;
	}
	/**
	 * @param billType : set the property billType.
	 */
	
	public void setBillType(String billType) {
		this.billType = billType;
	}
	/**
	 * @return billName : return the property billName.
	 */
	
	public String getBillName() {
		return billName;
	}
	/**
	 * @param billName : set the property billName.
	 */
	
	public void setBillName(String billName) {
		this.billName = billName;
	}
	/**
	 * @return classesPrivilege : return the property classesPrivilege.
	 */
	
	public String getClassesPrivilege() {
		return classesPrivilege;
	}
	/**
	 * @param classesPrivilege : set the property classesPrivilege.
	 */
	
	public void setClassesPrivilege(String classesPrivilege) {
		this.classesPrivilege = classesPrivilege;
	}
}
