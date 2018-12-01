package com.zfsoft.workflow.model;

import java.io.Serializable;

/**
 * 代码自动生成(bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public class SpLine extends BaseObject implements Serializable {

	/* serialVersionUID: serialVersionUID */

	private static final long serialVersionUID = 1L;
	/* @property:连线ID */
	private String lineId;
	/* @property:流程ID */
	private String pid;
	/* @property:上节点ID */
	private String unodeId;
	/* @property:下节点ID */
	private String dnodeId;
	/* @property:条件表达式 */
	private String expression = "null";
	/* @property:连线描述 */
	private String lineDesc;
	/* @property:是否必须经过 */
	private String isMustPass;

	/* Default constructor - creates a new instance with no values set. */
	public SpLine() {
	}

	/**
	 * @return lineId : return the property lineId.
	 */

	public String getLineId() {
		return lineId;
	}

	/**
	 * @param lineId
	 *            : set the property lineId.
	 */

	public void setLineId(String lineId) {
		this.lineId = lineId;
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
	 * @return uNodeId : return the property uNodeId.
	 */

	public String getUnodeId() {
		return unodeId;
	}

	/**
	 * @param uNodeId
	 *            : set the property uNodeId.
	 */

	public void setUnodeId(String unodeId) {
		this.unodeId = unodeId;
	}

	/**
	 * @return dNodeId : return the property dNodeId.
	 */

	public String getDnodeId() {
		return dnodeId;
	}

	/**
	 * @param dNodeId
	 *            : set the property dNodeId.
	 */

	public void setDnodeId(String dnodeId) {
		this.dnodeId = dnodeId;
	}

	/**
	 * @return expression : return the property expression.
	 */

	public String getExpression() {
		return expression;
	}

	/**
	 * @param expression
	 *            : set the property expression.
	 */

	public void setExpression(String expression) {
		this.expression = expression;
	}

	/**
	 * @return lineDesc : return the property lineDesc.
	 */

	public String getLineDesc() {
		return lineDesc;
	}

	/**
	 * @param lineDesc
	 *            : set the property lineDesc.
	 */

	public void setLineDesc(String lineDesc) {
		this.lineDesc = lineDesc;
	}
	
	/**
	 * @return isMustPass : return the property isMustPass.
	 */
	
	public String getIsMustPass() {
		return isMustPass;
	}

	/**
	 * @param isMustPass : set the property isMustPass.
	 */
	
	public void setIsMustPass(String isMustPass) {
		this.isMustPass = isMustPass;
	}

}
