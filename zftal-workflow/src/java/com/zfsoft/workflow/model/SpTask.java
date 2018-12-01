package com.zfsoft.workflow.model;

import java.io.Serializable;

/**
 * 
 * 类描述：任务对象
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-5-3 下午02:17:33
 */
public class SpTask extends BaseObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* @property:任务ID */
	private String taskId;
	/* @property:任务名称 */
	private String taskName;
	/* @property:任务编码 */
	private String taskCode;
	/* @property:任务类型 */
	private String taskType;
	/* @property:业务类型 */
	private String btype;
	/* @property:任务状态 */
	private String taskStatus;
	/* @property:任务描述 */
	private String taskDesc;
	/* @property:所属系统 */
	private String belongToSys;
	/* @property:是否必须执行 */
	private String isMust;
	/* @property:是否自动执行 */
	private String isAuto;
	/* @property:表单ID */
	private String billId;
	/* @property:表单类ID */
	private String classId;
	/* @property:表单类模式类别 */
	private String classModeType;
	/* @property:执行条件表达式 */
	private String exeCondition;
	/* @property:执行条件描述 */
	private String decCondition;
	/* @property:表单类权限串 */
	private String classPrivilege;

	private Boolean checked = false;

	/* Default constructor - creates a new instance with no values set. */
	public SpTask() {
	}

	/**
	 * @return taskId : return the property taskId.
	 */

	public String getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId
	 *            : set the property taskId.
	 */

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return taskName : return the property taskName.
	 */

	public String getTaskName() {
		return taskName;
	}

	/**
	 * @param taskName
	 *            : set the property taskName.
	 */

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * @return taskCode : return the property taskCode.
	 */

	public String getTaskCode() {
		return taskCode;
	}

	/**
	 * @param taskCode
	 *            : set the property taskCode.
	 */

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	/**
	 * @return taskType : return the property taskType.
	 */

	public String getTaskType() {
		return taskType;
	}

	/**
	 * @param taskType
	 *            : set the property taskType.
	 */

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	/**
	 * @return bType : return the property bType.
	 */

	public String getBtype() {
		return btype;
	}

	/**
	 * @param bType
	 *            : set the property bType.
	 */

	public void setBtype(String btype) {
		this.btype = btype;
	}

	/**
	 * @return taskStatus : return the property taskStatus.
	 */

	public String getTaskStatus() {
		return taskStatus;
	}

	/**
	 * @param taskStatus
	 *            : set the property taskStatus.
	 */

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	/**
	 * @return taskDesc : return the property taskDesc.
	 */

	public String getTaskDesc() {
		return taskDesc;
	}

	/**
	 * @param taskDesc
	 *            : set the property taskDesc.
	 */

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}


	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	/**
	 * @param belongToSys
	 *            : set the property belongToSys.
	 */

	public void setBelongToSys(String belongToSys) {
		this.belongToSys = belongToSys;
	}

	/**
	 * @return belongToSys : return the property belongToSys.
	 */

	public String getBelongToSys() {
		return belongToSys;
	}

	
	/**
	 * @param isMust : set the property isMust.
	 */
	
	public void setIsMust(String isMust) {
		this.isMust = isMust;
	}

	
	/**
	 * @return isMust : return the property isMust.
	 */
	
	public String getIsMust() {
		return isMust;
	}

	
	/**
	 * @param isAuto : set the property isAuto.
	 */
	
	public void setIsAuto(String isAuto) {
		this.isAuto = isAuto;
	}

	
	/**
	 * @return isAuto : return the property isAuto.
	 */
	
	public String getIsAuto() {
		return isAuto;
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

	/**
	 * @return exeCondition : return the property exeCondition.
	 */
	
	public String getExeCondition() {
		return exeCondition;
	}

	/**
	 * @param exeCondition : set the property exeCondition.
	 */
	
	public void setExeCondition(String exeCondition) {
		this.exeCondition = exeCondition;
	}

	/**
	 * @return decCondition : return the property decCondition.
	 */
	
	public String getDecCondition() {
		return decCondition;
	}

	/**
	 * @param decCondition : set the property decCondition.
	 */
	
	public void setDecCondition(String decCondition) {
		this.decCondition = decCondition;
	}

	/**
	 * @return classPrivilege : return the property classPrivilege.
	 */
	
	public String getClassPrivilege() {
		return classPrivilege;
	}

	/**
	 * @param classPrivilege : set the property classPrivilege.
	 */
	
	public void setClassPrivilege(String classPrivilege) {
		this.classPrivilege = classPrivilege;
	}

}
