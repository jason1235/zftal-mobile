package com.zfsoft.hrm.infochange.entity;

import java.util.ArrayList;
import java.util.List;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.workflow.enumobject.NodeTypeEnum;
import com.zfsoft.workflow.enumobject.WorkNodeEStatusEnum;
import com.zfsoft.workflow.model.SpAuditingLog;
import com.zfsoft.workflow.model.SpWorkNode;

/** 
 * @author Patrick Shen
 * @date 2013-6-9 下午02:22:08 
 */
public class InfoChangeAudit {

	private List<SpWorkNode> nodeList;
	private List<SpAuditingLog> logList;
	
	private List<SpWorkNode> excutedList = new ArrayList<SpWorkNode>();
	private SpWorkNode currentNode;
	private String nodePrivilegeListString;
	
	public List<SpWorkNode> getNodeList() {
		return nodeList;
	}
	public void setNodeList(List<SpWorkNode> nodeList) {
		this.nodeList = nodeList;
		processList();
	}
	public List<SpAuditingLog> getLogList() {
		return logList;
	}
	public void setLogList(List<SpAuditingLog> logList) {
		this.logList = logList;
	}
	
	//分拣节点
	private void processList(){
		String privilege = "";
		List<String> userRole = SessionFactory.getUser().getJsdms();
		for(SpWorkNode node : nodeList){
			if(node.getEstatus().equals(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey())){
				if(!node.getNodeType().equals(NodeTypeEnum.COMMIT_NODE.getKey())){
					excutedList.add(node);
				}
				if(userRole.indexOf(node.getRoleId())!=-1)
				{
					privilege+=node.getSpCommitWorkNodeBillListString()+",";
				}
			}
			if(node.getEstatus().equals(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey())){
				
				if(userRole.indexOf(node.getRoleId())!=-1)
				{
					currentNode = node;
					privilege+=node.getSpCommitWorkNodeBillListString()+",";
				}
			}
		}
		if(privilege.length()>0){
			privilege = StringUtil.removeLast(privilege);
		}
		nodePrivilegeListString = privilege;
	}
	public List<SpWorkNode> getExcutedList() {
		return excutedList;
	}
	public SpWorkNode getCurrentNode() {
		return currentNode;
	}
	
	public String getNodePrivilegeListString()
	{
		return nodePrivilegeListString;
	}
	
}
