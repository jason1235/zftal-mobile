package com.zfsoft.workflow.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.dybill.entity.SpBillConfig;
import com.zfsoft.hrm.dybill.enums.BillConfigStatus;
import com.zfsoft.hrm.dybill.enums.BillType;
import com.zfsoft.hrm.dybill.service.ISpBillConfigService;
import com.zfsoft.hrm.menu.service.IMenuService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.workflow.enumobject.NodeTypeEnum;
import com.zfsoft.workflow.enumobject.PtypeEnum;
import com.zfsoft.workflow.enumobject.StatusEnum;
import com.zfsoft.workflow.html.flow.FlowViewHtmlCreator;
import com.zfsoft.workflow.model.SpLine;
import com.zfsoft.workflow.model.SpLink;
import com.zfsoft.workflow.model.SpNode;
import com.zfsoft.workflow.model.SpProcedure;
import com.zfsoft.workflow.model.SpProcedureBill;
import com.zfsoft.workflow.model.query.SpProcedureQuery;
import com.zfsoft.workflow.service.ISpProcedureService;

/**
 * 流程类型维护
 * @author Patrick Shen
 */
public class SpProcedureAction extends HrmAction {
	private static final String CONFIG_PAGE="config";
	private static final long serialVersionUID = 6535431275595307300L;
	private String[] approveBillIds;
	private String[] commitBillIds;
	private IMenuService menuService;
	private String op="add";
	private String pid;
	private PageList<SpProcedure> procedureList=new PageList<SpProcedure>();
	private SpProcedureQuery query = new SpProcedureQuery();
	private ISpBillConfigService spBillConfigService;
	private SpProcedure spProcedure;
	
	private ISpProcedureService spProcedureService;

	private SpProcedure spQuery;
	
	private String sortFieldName = null;
	private String asc = "up";
	
	/**
	 * 请求增加
	 * @return
	 */
	public String addProcedure(){
		op="add";
		spProcedure=new SpProcedure();
		List<SpProcedureBill> commitBillList = new ArrayList<SpProcedureBill>();
		List<SpProcedureBill>approveBillList = new ArrayList<SpProcedureBill>();
		spProcedure.setPstatus(StatusEnum.INVALID_STATUS.getKey());
		this.getValueStack().set("ptypes", PtypeEnum.values());
		this.getValueStack().set("belongToSyses", menuService.getByLevel(1));
		
		this.initBill(commitBillList, approveBillList, spProcedure, op);
		this.getValueStack().set("commitBillList", commitBillList);
		this.getValueStack().set("approveBillList", approveBillList);
		
		return EDIT_PAGE;
	}

	public String configProcedure(){
		spProcedure=spProcedureService.findSpProcedureByPid(spQuery.getPid(),false);
		List<SpNode> startNode=new ArrayList<SpNode>();//开始
		List<String> startNodeId=new ArrayList<String>();//开始id
		List<SpNode> endNode=new ArrayList<SpNode>();//结束
		List<SpNode> countNode=new ArrayList<SpNode>();//会签
		List<SpNode> otherNode=new ArrayList<SpNode>();//普通
		
		//节点分类
		for (SpNode n : spProcedure.getSpNodeList()) {
			if(NodeTypeEnum.START_NODE.getKey().equals(n.getNodeType())){
				startNode.add(n);
				startNodeId.add(n.getNodeId());
			}else if(NodeTypeEnum.END_NODE.getKey().equals(n.getNodeType())){
				endNode.add(n);
			}else if(NodeTypeEnum.COUNTERSIGN_NODE.getKey().equals(n.getNodeType())){
				countNode.add(n);
			}else{
				otherNode.add(n);
			}
		}
		List<SpLine> spLines=spProcedure.getSpLineList();
		Map<String, SpNode> nodeMap =spProcedure.getSpNodeMap();
		List<SpLink> spLinks=new ArrayList<SpLink>();
		fillSpLink(spLinks, spLines, startNodeId, nodeMap);
		//界面显示需要，去除最后的结束环节，避免重复。
		if(spLinks.size()>0){
			SpLink link = spLinks.get(spLinks.size()-1);
			if(link.getNodes().size()==1
					&&NodeTypeEnum.END_NODE.getKey().equals(link.getNodes().get(0).getNodeType())){
				spLinks.remove(link);
			}
		}
		getValueStack().set("spLinks", spLinks);
		getValueStack().set("startNode", startNode);
		getValueStack().set("endNode", endNode);
		getValueStack().set("countNode", countNode);
		getValueStack().set("otherNode", otherNode);
		return CONFIG_PAGE;
	}
	
	private void fillSpLink(List<SpLink> spLinks,List<SpLine> spLines,List<String> nodeIds,Map<String, SpNode> nodeMap){
		SpLink spLink = null;
		List<String> subNodeIds=new ArrayList<String>();
		for(int i=spLines.size()-1;i>=0;i--){
			SpLine line = spLines.get(i);
			if(nodeIds.contains(line.getUnodeId())){
				spLines.remove(line);
				if(spLink==null){
					spLink = new SpLink();
					spLink.setPid(pid);
					spLink.setExpression(line.getExpression());
					spLink.setLineDesc(line.getLineDesc());
					spLink.setIsMustPass(line.getIsMustPass());
					spLinks.add(spLink);
				}
				SpNode n = nodeMap.get(line.getDnodeId());
				if(spLink.getNodes()==null||!spLink.getNodes().contains(n)){
					spLink.addNode(n);
				}
				//记录此环节涉及节点 作为下一环节的查询依据
				subNodeIds.add(line.getDnodeId());
			}
		}
		if(!subNodeIds.isEmpty()){
			fillSpLink(spLinks, spLines, subNodeIds, nodeMap);
		}
	}

	public String detail() throws Exception{
		
		return "detail";
	}

	public String disabledProcedure(){
		spProcedure=spProcedureService.findSpProcedureByPid(spQuery.getPid(),false);
		spProcedure.setPstatus(StatusEnum.INVALID_STATUS.getKey());
		spProcedureService.update(spProcedure);
		this.setSuccessMessage("设置成功");
		this.getValueStack().set(DATA, this.getMessage());
		return DATA;
	}
	
	public String enabledProcedure(){
		spProcedure=spProcedureService.findSpProcedureByPid(spQuery.getPid(),false);
		spProcedure.setPstatus(StatusEnum.VALID_STATUS.getKey());
		spProcedureService.update(spProcedure);
		this.setSuccessMessage("设置成功");
		this.getValueStack().set(DATA, this.getMessage());
		return DATA;
	}
	/**
	 * @return approveBillIds : return the property approveBillIds.
	 */
	
	public String[] getApproveBillIds() {
		return approveBillIds;
	}
	/**
	 * @return commitBillIds : return the property commitBillIds.
	 */
	
	public String[] getCommitBillIds() {
		return commitBillIds;
	}

	public String getOp() {
		return op;
	}

	public String getPid() {
		return pid;
	}
	public PageList<SpProcedure> getProcedureList() {
		return procedureList;
	}
	
	/**
	 * 获取流程列表
	 * @return
	 */
/*	public String list(){
		this.getValueStack().set("procedureList",spProcedureService.findSpProcedureList(new SpProcedure()));
		return LIST_PAGE;
	}*/
	
	/**
	 * 获取分页流程列表
	 * @return
	 */
	public String list(){
		if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( " BELONG_TO_SYS_NAME" );
		}
		procedureList = spProcedureService.getPagedSpProcedureList(query);
		int beginIndex = procedureList.getPaginator().getBeginIndex();
		getValueStack().set("beginIndex",beginIndex);
		this.getValueStack().set("belongToSyses", menuService.getByLevel(1));
		return LIST_PAGE;
	}
	

	/*
	 * 
	 * 私有方法描述：初始化表单信息
	 *
	 * @param: 
	 * @return: 
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-4-25 上午11:15:00
	 */
	private void initBill(List<SpProcedureBill> commitBillList, List<SpProcedureBill>approveBillList, SpProcedure sp, String oprate) {
		SpBillConfig spBillConfig = new SpBillConfig();
		spBillConfig.setBillType(null);
		spBillConfig.setStatus(BillConfigStatus.USING);
		for (SpBillConfig billConfig : spBillConfigService.getSpBillConfigList(spBillConfig)) {
			SpProcedureBill pBill = new SpProcedureBill();
			pBill.setBillId(billConfig.getId());
			pBill.setBillType(billConfig.getBillType().toString());
			pBill.setPid(sp.getPid());
			
			if(billConfig.getBillType() == BillType.COMMIT){
				if(!oprate.equals("add") && sp.getSpCommitBillList() != null){
					for (SpProcedureBill spb : sp.getSpCommitBillList()) {
						if (StringUtil.isNotEmpty(spb.getBillId()) && spb.getBillId().equals(billConfig.getId())) {
							pBill = spb;
							pBill.setChecked(true);
						}
					}
				}
				pBill.setBillName(billConfig.getName());
				commitBillList.add(pBill);
			}else if(billConfig.getBillType() == BillType.APPROVE){
				if(!oprate.equals("add") && sp.getSpApproveBillList() != null){
					for (SpProcedureBill spb : sp.getSpApproveBillList()) {
						if (StringUtil.isNotEmpty(spb.getBillId()) && spb.getBillId().equals(billConfig.getId())) {
							pBill = spb;
							pBill.setChecked(true);
						}
					}
				}
				pBill.setBillName(billConfig.getName());
				approveBillList.add(pBill);
			}
		}
	}
	
	/**
	 * 请求修改
	 * @return
	 */
	public String modifyProcedure(){
		op="modify";
		List<SpProcedureBill> commitBillList = new ArrayList<SpProcedureBill>();
		List<SpProcedureBill>approveBillList = new ArrayList<SpProcedureBill>();
		spProcedure=spProcedureService.findSpProcedureByPid(spQuery.getPid(),false);
		this.getValueStack().set("belongToSyses", menuService.getByLevel(1));
		this.getValueStack().set("ptypes", PtypeEnum.values());
		
		this.initBill(commitBillList, approveBillList, spProcedure, op);
		this.getValueStack().set("commitBillList", commitBillList);
		this.getValueStack().set("approveBillList", approveBillList);
		
		return EDIT_PAGE;
	}
	/**
	 * 
	 * 方法描述：流程预览
	 *
	 * @param: 
	 * @return: 
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-5-2 下午01:04:37
	 */
	public String preview(){
		spProcedure = spProcedureService.findSpProcedureByPid(pid,true);
		if(spProcedure == null){
			throw new RuleException("流程信息未找到！");
		}else{
			FlowViewHtmlCreator creator = new FlowViewHtmlCreator(spProcedure);
			String html=creator.html();
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("html", html);
			map.put("success", true);
			getValueStack().set(DATA, map);
		}
		return DATA;
	}
	/**
	 * 删除流程类型
	 * @return
	 */
	public String removeProcedure(){
		spProcedureService.delete(spQuery.getPid());
		this.setSuccessMessage("删除成功");
		this.getValueStack().set(DATA, this.getMessage());
		return DATA;
	}
	/**
	 * 保存修改
	 * @return
	 */
	public String saveProcedure(){
		if(op.equals("add")){
			spProcedureService.insert(spProcedure, commitBillIds, approveBillIds);
		}else{
			spProcedureService.update(spProcedure, commitBillIds, approveBillIds);
		}
		this.setSuccessMessage("保存成功");
		this.getValueStack().set(DATA, this.getMessage());
		return DATA;
	}
	/**
	 * @param approveBillIds : set the property approveBillIds.
	 */
	
	public void setApproveBillIds(String[] approveBillIds) {
		this.approveBillIds = approveBillIds;
	}

	/**
	 * @param commitBillIds : set the property commitBillIds.
	 */
	
	public void setCommitBillIds(String[] commitBillIds) {
		this.commitBillIds = commitBillIds;
	}
	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}

	public void setOp(String op) {
		this.op = op;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}

	public void setProcedureList(PageList<SpProcedure> procedureList) {
		this.procedureList = procedureList;
	}

	public void setQuery(SpProcedureQuery query) {
		this.query = query;
	}

	public void setSpBillConfigService(ISpBillConfigService spBillConfigService) {
		this.spBillConfigService = spBillConfigService;
	}

	public void setSpProcedure(SpProcedure spProcedure) {
		this.spProcedure = spProcedure;
	}	
	
	public void setSpProcedureService(ISpProcedureService spProcedureService) {
		this.spProcedureService = spProcedureService;
	}

	public void setSpQuery(SpProcedure spQuery) {
		this.spQuery = spQuery;
	}
	
	public SpProcedureQuery getQuery() {
		return query;
	}
	
	public SpProcedure getSpProcedure() {
		return spProcedure;
	}
	public SpProcedure getSpQuery() {
		return spQuery;
	}

	public String getSortFieldName() {
		return sortFieldName;
	}

	public void setSortFieldName(String sortFieldName) {
		this.sortFieldName = sortFieldName;
	}

	public String getAsc() {
		return asc;
	}

	public void setAsc(String asc) {
		this.asc = asc;
	}
}
