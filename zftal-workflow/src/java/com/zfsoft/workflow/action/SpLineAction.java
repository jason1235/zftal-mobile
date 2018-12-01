package com.zfsoft.workflow.action;

import java.util.ArrayList;
import java.util.List;

import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.workflow.enumobject.NodeTypeEnum;
import com.zfsoft.workflow.model.SpLine;
import com.zfsoft.workflow.model.SpLink;
import com.zfsoft.workflow.model.SpNode;
import com.zfsoft.workflow.service.ISpLineService;
import com.zfsoft.workflow.service.ISpNodeService;
/**
 * 流程走向维护
 * @author Patrick Shen
 */
public class SpLineAction extends HrmAction {
	private static final long serialVersionUID = 6535431275595307300L;
	
	private ISpLineService spLineService;
	private ISpNodeService spNodeService;
	private List<SpLink> spLinks;
	private SpLine spLine;
	private String op="add";
	/**
	 * 请求增加走向
	 * @return
	 */
	public String addLine(){
		op="add";
		SpNode spNode=new SpNode();
		spNode.setPid(spLine.getPid());
		this.getValueStack().set("nodeList", spNodeService.findNodeList(spNode));
		return EDIT_PAGE;
	}
	
	public String saveLink(){
		
		SpNode spNode=new SpNode();
		spNode.setPid(spLine.getPid());
		List<SpNode> nodes=spNodeService.findNodeList(spNode);
		List<SpNode> startNodes=new ArrayList<SpNode>();
		List<SpNode> endNodes=new ArrayList<SpNode>();
		for (SpNode n : nodes) {
			if(NodeTypeEnum.START_NODE.getKey().equals(n.getNodeType())){
				startNodes.add(n);
			}else if(NodeTypeEnum.END_NODE.getKey().equals(n.getNodeType())){
				endNodes.add(n);
			}
		}
		if(startNodes.isEmpty()){
			this.setErrorMessage("流程不能没有开始节点");
			this.getValueStack().set(DATA, this.getMessage());
			return DATA;
		}
		List<SpLine> spLines = new ArrayList<SpLine>();
		if(spLinks!=null&&spLinks.size()>0){
			if(endNodes.isEmpty()){
				this.setErrorMessage("多节点流程不能没有结束节点");
				this.getValueStack().set(DATA, this.getMessage());
				return DATA;
			}
			SpLink endLink=new SpLink();
			endLink.setNodes(endNodes);
			spLinks.add(endLink);
			List<SpNode> subNode = startNodes;
			for (SpLink link : spLinks) {
				
				if(subNode.size()>1&&link.getNodes().size()>1){
					this.setErrorMessage("多节点环节不能连续出现");
					this.getValueStack().set(DATA, this.getMessage());
					return DATA;
				}
				for (SpNode unode : subNode) {
					for (SpNode dnode : link.getNodes()) {
						SpLine line= new SpLine();
						line.setUnodeId(unode.getNodeId());
						line.setExpression(link.getExpression());
						line.setIsMustPass(link.getIsMustPass());
						line.setLineDesc(link.getLineDesc());
						line.setDnodeId(dnode.getNodeId());
						spLines.add(line);
					}
				}
				subNode = link.getNodes();
			}
		}else if(!(startNodes.isEmpty()||endNodes.isEmpty())){
			SpLine line= new SpLine();
			line.setUnodeId(startNodes.get(0).getNodeId());
			line.setIsMustPass("1");
			line.setDnodeId(endNodes.get(0).getNodeId());
			spLines.add(line);
		}
		
		spLineService.insertLineListAfresh(spLine.getPid(), spLines);
		
		this.setSuccessMessage("保存成功");
		this.getValueStack().set(DATA, this.getMessage());
		return DATA;
	}
	/**
	 * 请求修改走向
	 * @return
	 */
	public String modifyLine(){
		op="modify";
		spLine=spLineService.findLineListById(spLine.getLineId());
		SpNode spNode=new SpNode();
		spNode.setPid(spLine.getPid());
		this.getValueStack().set("nodeList", spNodeService.findNodeList(spNode));
		return EDIT_PAGE;
	}
	/**
	 * 保存修改走向
	 * @return
	 */
	public String saveLine(){
		if(op.equals("add")){
			spLineService.insert(spLine);
		}else{
			spLineService.update(spLine);
		}
		this.setSuccessMessage("保存成功");
		this.getValueStack().set(DATA, this.getMessage());
		return DATA;
	}
	/**
	 * 删除流程走向类型
	 * @return
	 */
	public String removeLine(){
		spLineService.delete(spLine.getLineId());
		this.setSuccessMessage("删除成功");
		this.getValueStack().set(DATA, this.getMessage());
		return DATA;
	}
	
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public SpLine getSpLine() {
		return spLine;
	}
	public void setSpLine(SpLine spLine) {
		this.spLine = spLine;
	}
	public void setSpLineService(ISpLineService spLineService) {
		this.spLineService = spLineService;
	}
	public void setSpNodeService(ISpNodeService spNodeService) {
		this.spNodeService = spNodeService;
	}
	/**
	 * 返回
	 */
	public List<SpLink> getSpLinks() {
		return spLinks;
	}
	/**
	 * 设置
	 * @param spLinks 
	 */
	public void setSpLinks(List<SpLink> spLinks) {
		this.spLinks = spLinks;
	}
	
	
}
