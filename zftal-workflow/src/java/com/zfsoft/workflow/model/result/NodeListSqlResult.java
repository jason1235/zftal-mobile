package com.zfsoft.workflow.model.result;

import java.io.Serializable;

import com.zfsoft.workflow.model.BaseObject;

/**
 * 
 * 类描述：查询符合条件的工作审核结果集合接口返回SQL对象
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-3-19 下午12:29:15
 */
public class NodeListSqlResult extends BaseObject implements Serializable {
	
	/* serialVersionUID: serialVersionUID */
	
	private static final long serialVersionUID = -3053003984199613539L;
	
	private static final String sql_start_part = "SELECT swn.W_ID, '' AS TASK_ID, swn.P_ID, swn.NODE_ID, swn.NODE_NAME, swn.NODE_TYPE, swn.NODE_DESC, " +
			" swn.ROLE_ID, swn.STATUS, swn.AUDITOR_ID, swn.AUDIT_TIME, swn.AUDIT_RESULT, swn.SUGGESTION, swn.E_STATUS, sb.bill_id " +
			" FROM SP_WORK_NODE swn " +
			" LEFT JOIN sp_business sb " +
			" ON sb.p_id = swn.p_id " + 
			" WHERE 1 = 1 ";

	/* @property:SQL内容 */
	private String sqlContent;
	/* @property:表名称 */
	private String tableName = "workflow";
	/* @property:工作ID */
	private String wId = "W_ID";
	/* @property:流程ID */
	private String pId = "P_ID";
	/* @property:节点ID */
	private String nodeId = "NODE_ID";
	/* @property:流程ID */
	private String nodeName = "NODE_NAME";
	/* @property:流程ID */
	private String roleId = "ROLE_ID";
	/* @property:审核状态 */
	private String status = "STATUS";
	/* @property:审核人 */
	private String auditorId = "AUDITOR_ID";
	/* @property:审核时间 */
	private String auditTime = "AUDIT_TIME";
	/* @property:审核结果 */
	private String auditResult = "AUDIT_RESULT";
	/* @property:审核意见 */
	private String suggestion = "SUGGESTION";
	/* @property:执行状态 */
	private String eStatus  = "E_STATUS";
	/* @property:节点类型*/
	private String nodeType  = "NODE_TYPE";

	/* Default constructor - creates a new instance with no values set. */
	public NodeListSqlResult() {
	}

	
	/**
	 * @param sqlContent : set the property sqlContent.
	 */
	
	public void setSqlContent(String sqlContent) {
		this.sqlContent = sqlContent;
	}

	
	/**
	 * @return sqlContent : return the property sqlContent.
	 */
	
	public String getSqlContent() {
		return sqlContent;
	}


	
	/**
	 * @param tableName : set the property tableName.
	 */
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	
	/**
	 * @return tableName : return the property tableName.
	 */
	
	public String getTableName() {
		return tableName;
	}


	/**
	 * @return wId : return the property wId.
	 */
	
	public String getwId() {
		return wId;
	}


	/**
	 * @param wId : set the property wId.
	 */
	
	public void setwId(String wId) {
		this.wId = wId;
	}


	/**
	 * @return pId : return the property pId.
	 */
	
	public String getpId() {
		return pId;
	}


	/**
	 * @param pId : set the property pId.
	 */
	
	public void setpId(String pId) {
		this.pId = pId;
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
	 * @return nodeName : return the property nodeName.
	 */
	
	public String getNodeName() {
		return nodeName;
	}


	/**
	 * @param nodeName : set the property nodeName.
	 */
	
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}


	/**
	 * @return roleId : return the property roleId.
	 */
	
	public String getRoleId() {
		return roleId;
	}


	/**
	 * @param roleId : set the property roleId.
	 */
	
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}


	/**
	 * @return status : return the property status.
	 */
	
	public String getStatus() {
		return status;
	}


	/**
	 * @param status : set the property status.
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
	 * @param auditorId : set the property auditorId.
	 */
	
	public void setAuditorId(String auditorId) {
		this.auditorId = auditorId;
	}


	/**
	 * @return auditTime : return the property auditTime.
	 */
	
	public String getAuditTime() {
		return auditTime;
	}


	/**
	 * @param auditTime : set the property auditTime.
	 */
	
	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}


	/**
	 * @return auditResult : return the property auditResult.
	 */
	
	public String getAuditResult() {
		return auditResult;
	}


	/**
	 * @param auditResult : set the property auditResult.
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
	 * @param suggestion : set the property suggestion.
	 */
	
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}


	/**
	 * @return eStatus : return the property eStatus.
	 */
	
	public String geteStatus() {
		return eStatus;
	}

	/**
	 * @param eStatus : set the property eStatus.
	 */
	
	public void seteStatus(String eStatus) {
		this.eStatus = eStatus;
	}
	
	/**
	 * @return sqlStartPart : return the property sqlStartPart.
	 */
	
	public static String getSqlStartPart() {
		return sql_start_part;
	}


	/**
	 * 返回
	 */
	public String getNodeType() {
		return nodeType;
	}


	/**
	 * 设置
	 * @param nodeType 
	 */
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	
	

}
