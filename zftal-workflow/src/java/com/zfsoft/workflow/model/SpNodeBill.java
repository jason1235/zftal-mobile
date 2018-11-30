package com.zfsoft.workflow.model;

import java.io.Serializable;

/**
 * 
 * 类描述：节点动态表单关联对象
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-4-12 上午10:01:52
 */
public class SpNodeBill extends BaseObject implements Serializable{
	
	
	/* serialVersionUID: serialVersionUID */
	
	private static final long serialVersionUID = -239471087152489298L;
	/* @property:ID */
	private String id;
	/* @property:节点ID */
	private String nodeId;
	/* @property:表单ID */
	private String billId;
	/* @property:表单类型 */
	private String billType;
	/* @property:表单类ID */
	private String classId;
	/* @property:表单类权限串 */
	private String classesPrivilege;
	/* @property:表单类模式类型 */
	private String classModeType;
	
	/* @property:是否选中 */
	private boolean checked;
	/* @property:动态表单CLASS名称 */
	private String className;
	
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
	 * @return nodeId : return the property nodeId.
	 */
	
	public String getNodeId() {
		return nodeId;
	}
	/**
	 * @param nodeId : set the property nodeId.
	 */
	
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
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
	 * @return classId : return the property classId.
	 */
	
	public String getClassId() {
		return classId;
	}
	/**
	 * @param classId : set the property classId.
	 */
	
	public void setClassId(String classId) {
		this.classId = classId;
	}

	/**
	 * @return className : return the property className.
	 */
	
	public String getClassName() {
		return className;
	}
	/**
	 * @param className : set the property className.
	 */
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	/**
	 * @return classModeType : return the property classModeType.
	 */
	
	public String getClassModeType() {
		return classModeType;
	}
	/**
	 * @param classModeType : set the property classModeType.
	 */
	
	public void setClassModeType(String classModeType) {
		this.classModeType = classModeType;
	}

}
