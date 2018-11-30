package com.zfsoft.workflow.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.zfsoft.hrm.dybill.enums.ModeType;
import com.zfsoft.util.base.StringUtil;

/**
 * 代码自动生成(bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public class SpWorkNode extends SpNode implements Serializable {

	/* serialVersionUID: serialVersionUID */

	private static final long serialVersionUID = 1L;
	/* @property:ID */
	private String id;
	/* @property:工作审核流程ID */
	private String wpid;
	/* @property:工作ID */
	private String wid;
	/* @property:用户ID */
	private String userId;
	/* @property:部门ID */
	private String departmentId;
	/* @property:审核状态 */
	private String status;
	/* @property:审核人 */
	private String auditorId;
	/* @property:审核时间 */
	private Date auditTime;
	/* @property:审核结果 */
	private String auditResult;
	/* @property:审核意见 */
	private String suggestion;
	/* @property:执行状态 */
	private String estatus;
	
	/* @property:业务编码 */
	private String bcode;
	/* @property:工作审核任务对象集合 */
	private List<SpWorkTask> spWorkTaskList;
	/* @property:工作审核提交表单对象集合 */
	private List<SpWorkNodeBill> spCommitWorkNodeBillList;
	/* @property:工作审核审批表单对象集合 */
	private List<SpWorkNodeBill> spApproveWorkNodeBillList;
	/* @property:工作审核提交类型表单类权限串 */
	private String commitWorkBillClassesPrivilege;
	/* @property:工作审核审批类型表单类权限串 */
	private String approveWorkBillClassesPrivilege;
	/* @property:工作审核提交类型表单类权限串(不含开始的大权限控制 例 100000-SCARCH,100003-SCARCH)*/
	private String spCommitWorkNodeBillListString;
	/* @property:工作审核审批类型表单类权限串(不含开始的大权限控制 例 100000-SCARCH,100003-SCARCH) */
	private String spApproveWorkNodeBillListString;
	
	/* @property:角色ID数组 */
	private String[] roleIdArray;
	/* @property:是否可编辑 */
	private boolean edit;
	/* @property:表单实例id 表单回填时使用 由业务方法传值 为空时则不执行回填方法*/
	private String spBillInstanceId;

	/* Default constructor - creates a new instance with no values set. */
	public SpWorkNode() {
	}

	/**
	 * @return id : return the property id.
	 */

	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            : set the property id.
	 */

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return wPId : return the property wPId.
	 */

	public String getWpid() {
		return wpid;
	}

	/**
	 * @param wpid
	 *            : set the property wPId.
	 */

	public void setWpid(String wpid) {
		this.wpid = wpid;
	}

	/**
	 * @return wId : return the property wId.
	 */

	public String getWid() {
		return wid;
	}

	/**
	 * @param wId
	 *            : set the property wId.
	 */

	public void setWid(String wid) {
		this.wid = wid;
	}

	/**
	 * @return userId : return the property userId.
	 */
	
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId : set the property userId.
	 */
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return departmentId : return the property departmentId.
	 */
	
	public String getDepartmentId() {
		return departmentId;
	}

	/**
	 * @param departmentId : set the property departmentId.
	 */
	
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * @return status : return the property status.
	 */

	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            : set the property status.
	 */

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return auditorId : return the property auditorId.
	 */

	public String getAuditorId() {
		return auditorId;
	}

	/**
	 * @param auditorId
	 *            : set the property auditorId.
	 */

	public void setAuditorId(String auditorId) {
		this.auditorId = auditorId;
	}

	/**
	 * @return auditTime : return the property auditTime.
	 */

	public Date getAuditTime() {
		return auditTime;
	}

	/**
	 * @param auditTime
	 *            : set the property auditTime.
	 */

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	/**
	 * @return auditResult : return the property auditResult.
	 */

	public String getAuditResult() {
		return auditResult;
	}

	/**
	 * @param auditResult
	 *            : set the property auditResult.
	 */

	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}

	/**
	 * @return suggestion : return the property suggestion.
	 */

	public String getSuggestion() {
		return suggestion;
	}

	/**
	 * @param suggestion
	 *            : set the property suggestion.
	 */

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	/**
	 * @return spWorkTaskList : return the property spWorkTaskList.
	 */

	public List<SpWorkTask> getSpWorkTaskList() {
		return spWorkTaskList;
	}

	/**
	 * @param spWorkTaskList
	 *            : set the property spWorkTaskList.
	 */

	public void setSpWorkTaskList(List<SpWorkTask> spWorkTaskList) {
		this.spWorkTaskList = spWorkTaskList;
	}

	/**
	 * @return estatus : return the property estatus.
	 */
	
	public String getEstatus() {
		return estatus;
	}

	/**
	 * @param estatus : set the property estatus.
	 */
	
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	/**
	 * @return bcode : return the property bcode.
	 */
	
	public String getBcode() {
		return bcode;
	}

	/**
	 * @param bcode : set the property bcode.
	 */
	
	public void setBcode(String bcode) {
		this.bcode = bcode;
	}

	/**
	 * 将节点对象填充到工作审核节点对象中
	 * 
	 * @param spNode
	 * @return
	 */
	public static SpWorkNode putNodeObject(SpNode spNode) {
		SpWorkNode spWorkNode = new SpWorkNode();
		if (spNode != null) {
			spWorkNode.setNodeId(spNode.getNodeId());
			spWorkNode.setPid(spNode.getPid());
			spWorkNode.setNodeName(spNode.getNodeName());
			spWorkNode.setNodeType(spNode.getNodeType());
			spWorkNode.setNodeDesc(spNode.getNodeDesc());
			spWorkNode.setRoleId(spNode.getRoleId());
			spWorkNode.setOutType(spNode.getOutType());
			spWorkNode.setInType(spNode.getInType());
		}
		return spWorkNode;
	}

	
	/**
	 * @param roleIdArray : set the property roleIdArray.
	 */
	
	public void setRoleIdArray(String[] roleIdArray) {
		this.roleIdArray = roleIdArray;
	}

	
	/**
	 * @return roleIdArray : return the property roleIdArray.
	 */
	
	public String[] getRoleIdArray() {
		return roleIdArray;
	}

	/**
	 * @return spCommitWorkNodeBillList : return the property spCommitWorkNodeBillList.
	 */
	
	public List<SpWorkNodeBill> getSpCommitWorkNodeBillList() {
		return spCommitWorkNodeBillList;
	}

	/**
	 * @param spCommitWorkNodeBillList : set the property spCommitWorkNodeBillList.
	 */
	
	public void setSpCommitWorkNodeBillList(List<SpWorkNodeBill> spCommitWorkNodeBillList) {
		this.spCommitWorkNodeBillList = spCommitWorkNodeBillList;
	}

	/**
	 * @return spApproveWorkNodeBillList : return the property spApproveWorkNodeBillList.
	 */
	
	public List<SpWorkNodeBill> getSpApproveWorkNodeBillList() {
		return spApproveWorkNodeBillList;
	}

	/**
	 * @param spApproveWorkNodeBillList : set the property spApproveWorkNodeBillList.
	 */
	
	public void setSpApproveWorkNodeBillList(List<SpWorkNodeBill> spApproveWorkNodeBillList) {
		this.spApproveWorkNodeBillList = spApproveWorkNodeBillList;
	}

	/**
	 * @return commitWorkBillClassesPrivilege : return the property commitWorkBillClassesPrivilege.
	 */
	
	public String getCommitWorkBillClassesPrivilege() {
		if (this.getSpCommitWorkNodeBillList() != null) {
			String resultStr = "[";
			for (SpWorkNodeBill spWorkNodeBill : this.getSpCommitWorkNodeBillList()) {
				resultStr += spWorkNodeBill.getClassId() + "-" + spWorkNodeBill.getClassesPrivilege() + ",";
			}
			if(this.getSpCommitWorkNodeBillList().size() > 0){
				resultStr = StringUtil.removeLast(resultStr);
			}			
			resultStr = ModeType.NORMAL.toString() + resultStr + "]";
			commitWorkBillClassesPrivilege = resultStr;
		}		
		return commitWorkBillClassesPrivilege;
	}

	/**
	 * @param commitWorkBillClassesPrivilege : set the property commitWorkBillClassesPrivilege.
	 */
	
	public void setCommitWorkBillClassesPrivilege(String commitWorkBillClassesPrivilege) {
		this.commitWorkBillClassesPrivilege = commitWorkBillClassesPrivilege;
	}

	/**
	 * @return approveWorkBillClassesPrivilege : return the property approveWorkBillClassesPrivilege.
	 */
	
	public String getApproveWorkBillClassesPrivilege() {
		if (this.getSpApproveWorkNodeBillList() != null) {
			String resultStr = "[";
			for (SpWorkNodeBill spWorkNodeBill : this.getSpApproveWorkNodeBillList()) {
				resultStr += spWorkNodeBill.getClassId() + "-" + spWorkNodeBill.getClassesPrivilege() + ",";
			}
			if(this.getSpApproveWorkNodeBillList().size() > 0){
				resultStr = StringUtil.removeLast(resultStr);
			}
			resultStr = ModeType.NORMAL.toString() + resultStr + "]";
			approveWorkBillClassesPrivilege = resultStr;
		}
		return approveWorkBillClassesPrivilege;
		
	}

	/**
	 * @param approveWorkBillClassesPrivilege : set the property approveWorkBillClassesPrivilege.
	 */
	
	public void setApproveWorkBillClassesPrivilege(String approveWorkBillClassesPrivilege) {
		this.approveWorkBillClassesPrivilege = approveWorkBillClassesPrivilege;
	}

	/**
	 * 返回
	 */
	public String getSpCommitWorkNodeBillListString() {
		
		if(spCommitWorkNodeBillListString == null)
		{
			String resultStr = "";
			if (this.getSpCommitWorkNodeBillList() != null) {
				for (SpWorkNodeBill spWorkNodeBill : this.getSpCommitWorkNodeBillList()) {
					resultStr += spWorkNodeBill.getClassId() + "-" + spWorkNodeBill.getClassesPrivilege() + ",";
				}
				if(resultStr.length()>0){
					resultStr = StringUtil.removeLast(resultStr);
				}
			}
			spCommitWorkNodeBillListString = resultStr;
		}
		return spCommitWorkNodeBillListString;
	}

	/**
	 * 返回
	 */
	public String getSpApproveWorkNodeBillListString() {
		
		if(spApproveWorkNodeBillListString == null)
		{
			String resultStr = "";
			if (this.getSpApproveWorkNodeBillList() != null) {
				for (SpWorkNodeBill spWorkNodeBill : this.getSpApproveWorkNodeBillList()) {
					resultStr += spWorkNodeBill.getClassId() + "-" + spWorkNodeBill.getClassesPrivilege() + ",";
				}
				if(resultStr.length()>0){
					resultStr = StringUtil.removeLast(resultStr);
				}
			}
			spApproveWorkNodeBillListString = resultStr;
		}
		return spApproveWorkNodeBillListString;
	}


	/**
	 * @return edit : return the property edit.
	 */
	
	public boolean isEdit() {
		return edit;
	}

	/**
	 * @param edit : set the property edit.
	 */
	
	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	/**
	 * @return spBillInstanceId : return the property spBillInstanceId.
	 */
	public String getSpBillInstanceId() {
		return spBillInstanceId;
	}

	/**
	 * @param spBillInstanceId : set the property spBillInstanceId.
	 */
	public void setSpBillInstanceId(String spBillInstanceId) {
		this.spBillInstanceId = spBillInstanceId;
	}
}
