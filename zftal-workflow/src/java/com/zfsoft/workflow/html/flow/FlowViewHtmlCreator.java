package com.zfsoft.workflow.html.flow;

import java.util.ArrayList;
import java.util.List;

import com.zfsoft.common.system.SubSystemHolder;
import com.zfsoft.workflow.model.SpNode;
import com.zfsoft.workflow.model.SpProcedure;
import com.zfsoft.workflow.model.SpTask;

/** 
 * 
 * 类描述：构造流程预览页面
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-5-2 下午01:51:17
 */
public class FlowViewHtmlCreator {

	private SpProcedure procedure;
	private List<SpNode> nodeList;
	private StringBuilder sb = new StringBuilder();
	private SpNode startNode;
	private SpNode endNode;
	
	private static String TASK_EXCUTED_ICO = "pjlc_ic13_green.png";
	
	public FlowViewHtmlCreator(SpProcedure procedure){
		this.procedure = procedure;
		nodeList = procedure.getSpNodeList();
		processNode();
	}
	
	private void processNode(){
		startNode = new SpNode();//创建开始节点图例
		startNode.setNodeName("流程开始");
		
		endNode = new SpNode();//创建结束节点图例
		endNode.setNodeName("流程结束");
	}
	
	public String html(){
		sb.append("<div class=\"awards_process\" style='width:100%'>");
		sb.append("<h3 class=\"awards_process_h3\">"+procedure.getPname()+"</h3>");
		createNodeInfo();
		createBottom();
		sb.append("</div>");
		return sb.toString();
	}
	
	/**
	 * 创建节点信息
	 */
	private void createNodeInfo(){
		sb.append("<div class=\"awards_process_inbox\">");
		sb.append("<ul>");
		createNode(startNode,true);
		Integer step = 0;
		List<SpNode> subNodeList = new ArrayList<SpNode>();
		for(SpNode node:nodeList){
			if(node.getStep()!=null&&node.getStep().equals(step)){
			}else{
				createNode(subNodeList,true);
				subNodeList = new ArrayList<SpNode>();
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
	private void createNode(SpNode node,boolean direction){
		List<SpNode> nodeList = new ArrayList<SpNode>();
		nodeList.add(node);
		createNode(nodeList, direction);
	}
	
	/**
	 * 生成节点
	 * @param node
	 * @param direction
	 */
	private void createNode(List<SpNode> nodeList,boolean direction){
		if(nodeList==null||nodeList.isEmpty()) return;
		String style = " processbox_submited";
		sb.append("<li>");
		
		sb.append("<div><table style='width:100%'><tr>");
		for (SpNode node : nodeList) {			
			sb.append("<td class=\"processbox_submit"+style+"\" style='float: none'>");
			sb.append("<div>");
			sb.append("<span style='width:100%;text-align: center;padding: 0px;background: none;' >"+node.getNodeName()+"</span>");
			sb.append("</div>");
			createTaskInfo(node.getSpTaskList());
			sb.append("</td>");
		}
		sb.append("</tr></table></div>");
		if(direction){
			createDirection();
		}
		sb.append("</li>");
	}
	
	private void createDirection(){
		String style = " ico_list_submited";
		sb.append("<table align='center' ><tr><td style='padding-right: 40px;'><div class=\"ico_list_submit"+style+"\" style='width:100%'></div></td></tr></table>");
	}
	
	private void createTaskInfo(List<SpTask> taskList){
		if(taskList == null){
			return;
		}
		sb.append("<div style='float:left'>");
		for(SpTask task:taskList){
			createTask(task);
		}
		sb.append("</div>");
	}
	
	private void createTask(SpTask task){
		String ico = TASK_EXCUTED_ICO;
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
