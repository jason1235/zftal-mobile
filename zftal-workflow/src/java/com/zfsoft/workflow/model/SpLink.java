package com.zfsoft.workflow.model;

import java.util.ArrayList;
import java.util.List;

import com.zfsoft.util.base.StringUtil;

/**
 * 工作流环节对象（页面展示用对象）
 * 虽然单纯的用连线与节点可以描述出几乎所有的工作流，
 * 但是在实际界面展示以及逻辑处理的时候，多节点并行时出于描述需要添加环节对象。
 * 用以描述在审批流程中处于类似位置的连线集合。
 * @author ChenMinming
 * @date 2014-8-29
 * @version V1.0.0
 */
public class SpLink {


	/* serialVersionUID: serialVersionUID */

	private static final long serialVersionUID = 1L;
	/* @property:流程ID */
	private String pid;
	/* @property:环节涉及节点 */
	private List<SpNode> nodes;
	/* @property:条件表达式 */
	private String expression = "null";
	/* @property:连线描述 */
	private String lineDesc;
	/* @property:是否必须经过 */
	private String isMustPass="1";

	/* Default constructor - creates a new instance with no values set. */
	public SpLink() {
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

	public List<SpNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<SpNode> nodes) {
		this.nodes = nodes;
	}
	
	public void setNodesStr(String nodesStr) {
		if(StringUtil.isEmpty(nodesStr))
			return;
		String[] ids = nodesStr.split(",");
		for (String nodeId : ids) {
			SpNode n = new SpNode();
			n.setNodeId(nodeId.trim());
			this.addNode(n);
		}
	}
	
	public void addNode(SpNode node) {
		if(nodes==null) nodes=new ArrayList<SpNode>();
		nodes.add(node);
	}
}
