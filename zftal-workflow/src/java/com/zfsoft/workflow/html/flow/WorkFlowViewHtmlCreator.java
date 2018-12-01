package com.zfsoft.workflow.html.flow;

import java.util.ArrayList;
import java.util.List;

import com.zfsoft.common.system.SubSystemHolder;
import com.zfsoft.workflow.enumobject.NodeTypeEnum;
import com.zfsoft.workflow.enumobject.WorkNodeEStatusEnum;
import com.zfsoft.workflow.model.SpNode;
import com.zfsoft.workflow.model.SpWorkNode;
import com.zfsoft.workflow.model.SpWorkProcedure;
import com.zfsoft.workflow.model.SpWorkTask;

/** 
 * @author jinjj
 * @date 2013-4-2 下午03:07:22 
 *  
 */
public class WorkFlowViewHtmlCreator {

	private SpWorkProcedure procedure;
	private List<SpWorkNode> nodeList;
	private StringBuilder sb = new StringBuilder();
	private SpWorkNode startNode;
	private SpWorkNode endNode;
	private SpWorkNode currentNode;
	private static String INITIAL = "0";
	
	private static String TASK_EXCUTED_ICO = "pjlc_ic13_green.png";
	private static String TASK_UNEXCUTED_ICO = "pjlc_ic09_gray.png";
	private static String TASK_WAITED_ICO = "pjlc_ic07_blue.png";
	
	public WorkFlowViewHtmlCreator(SpWorkProcedure procedure){
		this.procedure = procedure;
		nodeList = procedure.getSpWorkNodeList();
		processNode();
	}
	
	private void processNode(){
		startNode = new SpWorkNode();//创建开始节点图例
		startNode.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
		startNode.setNodeName("流程开始");
		
		endNode = new SpWorkNode();//创建结束节点图例
		endNode.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
		endNode.setNodeName("流程结束");
		
		SpWorkNode node = nodeList.get(nodeList.size()-1);//设置结束节点状态
		if(!WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey().equals(node.getEstatus())){
			endNode.setEstatus(INITIAL);
		}
	}
	
	public String html(){
		sb.append("<div class=\"awards_process\" style='width:100%'>");
		sb.append("<h3 class=\"awards_process_h3\">"+procedure.getPname()+"</h3>");
		createTip();
		createNodeInfo();
		createBottom();
		sb.append("</div>");
		return sb.toString();
	}
	
	/**
	 * 创建状态图例提示
	 */
	private void createTip(){
		sb.append("<div class='check_process' style='width:100%;margin:0 20px'><div class='dirct' style='width:100%'><h6 class='h6_green'><span></span>已完成</h6><h6 class='h6_blue'><span></span>进行中</h6><h6 class='h6_gray'><span></span>未完成</h6></div></div>");
	}

	/**
	 * 创建节点信息
	 */
	private void createNodeInfo(){
		sb.append("<div class=\"awards_process_inbox\">");
		sb.append("<ul>");
		createNode(startNode,true);
		Integer step = 0;
		List<SpWorkNode> subNodeList = new ArrayList<SpWorkNode>();
		for(SpWorkNode node:nodeList){
			if(node.getStep()!=null&&node.getStep().equals(step)){
			}else{
				createNode(subNodeList,true);
				subNodeList = new ArrayList<SpWorkNode>();
			}
			subNodeList.add(node);
			step = node.getStep();
		}
		createNode(subNodeList,true);
		createNode(endNode,false);
		sb.append("</ul>");
		sb.append("</div>");
	}

	/**
	 * 生成节点
	 * @param node
	 * @param direction
	 */
	private void createNode(List<SpWorkNode> nodeList,boolean direction){
		if(nodeList==null||nodeList.isEmpty()) return;
		sb.append("<li>");
		sb.append("<table width='100%'><tr>");
		for (SpWorkNode node : nodeList) {
			String style = " processbox_unsubmit";
			if(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey().equals(node.getEstatus())){
				style = " processbox_submitting";
			}
			if(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey().equals(node.getEstatus())){
				style = " processbox_submited";
			}
			sb.append("<td class=\"processbox_submit"+style+"\" style='float: none'>");
			sb.append("<div>");
			sb.append("<span style='width:100%;text-align: center;padding: 0px;background: none;' >"+node.getNodeName()+"</span>");
			sb.append("</div>");
			currentNode = node;
			createTaskInfo(node.getSpWorkTaskList());
			sb.append("</td>");
		}
		sb.append("</tr></table>");
		if(direction){
			createDirection(nodeList);
		}
		sb.append("</li>");
	}
	/**
	 * 生成节点
	 * @param node
	 * @param direction
	 */
	private void createNode(SpWorkNode node,boolean direction){
		List<SpWorkNode> nodeList = new ArrayList<SpWorkNode>();
		nodeList.add(node);
		createNode(nodeList, direction);
	}
	
	private void createDirection(List<SpWorkNode> nodeList){
		int passNum = 0;
		int count = 0;
		String style = " ico_list_unsubmit";
		for (SpWorkNode node : nodeList) {
			//同环节下有一个节点待执行则整体为待执行
			if(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey().equals(node.getEstatus())){
				style = " ico_list_submitting";
			}
			//会签节点需要统计 (会签节点次数/完成次数)
			if(NodeTypeEnum.COUNTERSIGN_NODE.getKey().equals(node.getNodeType())){
				count++;
				if(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey().equals(node.getEstatus())){
					passNum++;
				}
			}
			//其他情况下只要有一个节点审核完成则整体为完成。
			else{
				if(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey().equals(node.getEstatus())){
					style = " ico_list_submited";
				}
			}
		}
		if(count>0&&passNum == count){
			style = " ico_list_submited";
		}
		sb.append("<table align='center' ><tr><td style='padding-right: 40px;'><div class=\"ico_list_submit"+style+"\" style='width:100%'></div></td></tr></table>");
	}
	
	private void createTaskInfo(List<SpWorkTask> taskList){
		if(taskList == null){
			return;
		}
		sb.append("<div>");
		for(SpWorkTask task:taskList){
			createTask(task);
		}
		sb.append("</div>");
	}
	
	private void createTask(SpWorkTask task){
		String ico = TASK_UNEXCUTED_ICO;
		if(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey().equals(currentNode.getEstatus())||
				WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey().equals(currentNode.getEstatus())){
			if(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey().equals(task.getEstatus())){
				ico = TASK_WAITED_ICO;
			}
			if(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey().equals(task.getEstatus())){
				ico = TASK_EXCUTED_ICO;
			}
		}
		sb.append("<a href='#'>");
		sb.append("<img height=\"24\" wdith=\"24\" src=\""+SubSystemHolder.getStyleUrl()+"/images/"+ico+"\">");
		sb.append(task.getTaskName());
		sb.append("</a>");
	}
	
	private void createBottom(){
		sb.append("<div class=\"over_bt_box\" style='padding:0;background:none;'>");
		sb.append("<button class=\"bt_blue bt_gray\" type='button' onclick='divClose();'>关闭窗口</button>");
		sb.append("</div>");
	}
	
}
