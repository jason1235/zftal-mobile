package com.zfsoft.workflow.model;

import java.io.Serializable;
import java.util.List;

import com.zfsoft.hrm.dybill.enums.ModeType;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.workflow.enumobject.NodeTypeEnum;

/**
 * 代码自动生成(bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public class SpNode extends BaseObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* @property:节点ID */
	private String nodeId;
	/* @property:流程ID */
	private String pid;
	/* @property:节点名称 */
	private String nodeName;
	/* @property:节点状态 */
	private String nodeStatus;
	/* @property:节点类型 */
	private String nodeType;
	/* @property:节点描述 */
	private String nodeDesc;
	/* @property:角色ID */
	private String roleId;
	/* @property:角色名称 */
	private String roleName;
	/* @property:流入类型 */
	private String inType;
	/* @property:流出类型 */
	private String outType;
	/* @property:是否自动 */
	private String isAuto;
	/* @property:业务类型 */
	private String btype;

	/* @property:任务对象集合 */
	private List<SpTask> spTaskList;
	/* @property:提交类型表单对象集合 */
	private List<SpNodeBill> spCommitNodeBillList;
	/* @property:审批类型表单对象集合 */
	private List<SpNodeBill> spApproveNodeBillList;
	/* @property:提交类型表单类权限串 */
	private String commitBillClassesPrivilege;
	/* @property:审批类型表单类权限串 */
	private String approveBillClassesPrivilege;
	/* @property:节点处于流程中的环节层级（开始节点为0） */
	private Integer step;

	/* Default constructor - creates a new instance with no values set. */
	public SpNode() {
	}

	/**
	 * @return nodeId : return the property nodeId.
	 */

	public String getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId
	 *            : set the property nodeId.
	 */

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * @return pId : return the property pId.
	 */

	public String getPid() {
		return pid;
	}

	/**
	 * @param pId
	 *            : set the property pId.
	 */

	public void setPid(String pid) {
		this.pid = pid;
	}

	/**
	 * @return nodeName : return the property nodeName.
	 */

	public String getNodeName() {
		return nodeName;
	}

	/**
	 * @param nodeName
	 *            : set the property nodeName.
	 */

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * @return nodeStatus : return the property nodeStatus.
	 */

	public String getNodeStatus() {
		return nodeStatus;
	}

	/**
	 * @param nodeStatus
	 *            : set the property nodeStatus.
	 */

	public void setNodeStatus(String nodeStatus) {
		this.nodeStatus = nodeStatus;
	}

	/**
	 * @return nodeType : return the property nodeType.
	 */

	public String getNodeType() {
		return nodeType;
	}

	public String getNodeTypeStr() {
		return NodeTypeEnum.valueOf(nodeType).getText();
	}

	/**
	 * @param nodeType
	 *            : set the property nodeType.
	 */

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	/**
	 * @return nodeDesc : return the property nodeDesc.
	 */

	public String getNodeDesc() {
		return nodeDesc;
	}

	/**
	 * @param nodeDesc
	 *            : set the property nodeDesc.
	 */

	public void setNodeDesc(String nodeDesc) {
		this.nodeDesc = nodeDesc;
	}

	/**
	 * @return roleId : return the property roleId.
	 */

	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 *            : set the property roleId.
	 */

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return inType : return the property inType.
	 */

	public String getInType() {
		return inType;
	}

	/**
	 * @param inType
	 *            : set the property inType.
	 */

	public void setInType(String inType) {
		this.inType = inType;
	}

	/**
	 * @return outType : return the property outType.
	 */

	public String getOutType() {
		return outType;
	}

	/**
	 * @param outType
	 *            : set the property outType.
	 */

	public void setOutType(String outType) {
		this.outType = outType;
	}

	/**
	 * @return spTaskList : return the property spTaskList.
	 */

	public List<SpTask> getSpTaskList() {
		return spTaskList;
	}

	/**
	 * @param spTaskList
	 *            : set the property spTaskList.
	 */

	public void setSpTaskList(List<SpTask> spTaskList) {
		this.spTaskList = spTaskList;
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
	 * @return isAuto : return the property isAuto.
	 */

	public String getIsAuto() {
		return isAuto;
	}

	/**
	 * @param isAuto
	 *            : set the property isAuto.
	 */

	public void setIsAuto(String isAuto) {
		this.isAuto = isAuto;
	}

	/**
	 * @return commitBillClassesPrivilege : return the property
	 *         commitBillClassesPrivilege.
	 */

	public String getCommitBillClassesPrivilege() {
		if (this.getSpCommitNodeBillList() != null) {
			String resultStr = "[";
			for (SpNodeBill spNodeBill : this.getSpCommitNodeBillList()) {
				resultStr += spNodeBill.getClassId() + "-" + spNodeBill.getClassesPrivilege() + ",";
			}
			resultStr = StringUtil.removeLast(resultStr);
			resultStr = ModeType.NORMAL.toString() + resultStr + "]";
			commitBillClassesPrivilege = resultStr;
		}		
		return commitBillClassesPrivilege;
	}

	/**
	 * @param commitBillClassesPrivilege
	 *            : set the property commitBillClassesPrivilege.
	 */

	public void setCommitBillClassesPrivilege(String commitBillClassesPrivilege) {
		this.commitBillClassesPrivilege = commitBillClassesPrivilege;
	}

	/**
	 * @return approveBillClassesPrivilege : return the property
	 *         approveBillClassesPrivilege.
	 */

	public String getApproveBillClassesPrivilege() {
		if (this.getSpApproveNodeBillList() != null) {
			String resultStr = "[";
			for (SpNodeBill spNodeBill : this.getSpApproveNodeBillList()) {
				resultStr += spNodeBill.getClassId() + "-" + spNodeBill.getClassesPrivilege() + ",";
			}
			resultStr = StringUtil.removeLast(resultStr);
			resultStr = ModeType.NORMAL.toString() + resultStr + "]";
			approveBillClassesPrivilege = resultStr;
		}
		return approveBillClassesPrivilege;
	}

	/**
	 * @param approveBillClassesPrivilege
	 *            : set the property approveBillClassesPrivilege.
	 */

	public void setApproveBillClassesPrivilege(String approveBillClassesPrivilege) {
		this.approveBillClassesPrivilege = approveBillClassesPrivilege;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return spCommitNodeBillList : return the property spCommitNodeBillList.
	 */

	public List<SpNodeBill> getSpCommitNodeBillList() {
		return spCommitNodeBillList;
	}

	/**
	 * @param spCommitNodeBillList
	 *            : set the property spCommitNodeBillList.
	 */

	public void setSpCommitNodeBillList(List<SpNodeBill> spCommitNodeBillList) {
		this.spCommitNodeBillList = spCommitNodeBillList;
	}

	/**
	 * @return spApproveNodeBillList : return the property
	 *         spApproveNodeBillList.
	 */

	public List<SpNodeBill> getSpApproveNodeBillList() {
		return spApproveNodeBillList;
	}

	/**
	 * @param spApproveNodeBillList
	 *            : set the property spApproveNodeBillList.
	 */

	public void setSpApproveNodeBillList(List<SpNodeBill> spApproveNodeBillList) {
		this.spApproveNodeBillList = spApproveNodeBillList;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}
}
