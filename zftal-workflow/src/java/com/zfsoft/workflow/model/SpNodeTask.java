package com.zfsoft.workflow.model;
/**
 * 节点任务关联对象
 * @see 2013-03-22
 * @author Patrick Shen
 */
public class SpNodeTask {
	private SpTask spTask;
	private SpNode spNode;
	private String need;
	private String auto;	
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
	
	private Boolean checked=false;
	
	public String getNeed() {
		return need;
	}
	public void setNeed(String need) {
		this.need = need;
	}
	public String getAuto() {
		return auto;
	}
	public void setAuto(String auto) {
		this.auto = auto;
	}
	public SpTask getSpTask() {
		return spTask;
	}
	public void setSpTask(SpTask spTask) {
		this.spTask = spTask;
	}
	public SpNode getSpNode() {
		return spNode;
	}
	public void setSpNode(SpNode spNode) {
		this.spNode = spNode;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
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
