package com.zfsoft.workflow.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.dao.entities.JsglModel;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.dybill.entity.SpBillConfig;
import com.zfsoft.hrm.dybill.enums.BillType;
import com.zfsoft.hrm.dybill.enums.PrivilegeType;
import com.zfsoft.hrm.dybill.service.ISpBillConfigService;
import com.zfsoft.hrm.dybill.service.ISpBillDataPushEventConfigService;
import com.zfsoft.hrm.dybill.xml.XmlBillClass;
import com.zfsoft.hrm.menu.service.IMenuService;
import com.zfsoft.service.svcinterface.IYhglService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.workflow.enumobject.NodeTypeEnum;
import com.zfsoft.workflow.model.SpNode;
import com.zfsoft.workflow.model.SpNodeBill;
import com.zfsoft.workflow.model.SpNodeTask;
import com.zfsoft.workflow.model.SpProcedure;
import com.zfsoft.workflow.model.SpProcedureBill;
import com.zfsoft.workflow.model.SpTask;
import com.zfsoft.workflow.service.ISpNodeBillService;
import com.zfsoft.workflow.service.ISpNodeService;
import com.zfsoft.workflow.service.ISpProcedureService;
import com.zfsoft.workflow.service.ISpTaskService;

/**
 * 流程节点维护
 * 
 * @author Patrick Shen
 */
public class SpNodeAction extends HrmAction {
	private static final long serialVersionUID = 6535431275595307300L;
	private ISpBillDataPushEventConfigService spBillDataPushEventConfigService;
	private ISpProcedureService spProcedureService;
	private ISpBillConfigService spBillConfigService;
	private ISpNodeBillService spNodeBillService;
	private ISpNodeService spNodeService;
	private ISpTaskService spTaskService;
	private IYhglService yhglService;
	private IMenuService menuService;
	
	private SpNode spNode;
	private String op = "add";
	private String[] taskIds;
	private String[] pushTaskIds;
	private String[] commitBillClassIds;
	private String[] commitClassPrivilege;
	private String[] approveBillClassIds;
	private String[] approveClassPrivilege;
	private String[] taskIsMusts;
	private String[] taskIsAutos;	

	private List<JsglModel> getRoleOfModule() {
		SpProcedure spProcedure = spProcedureService.findSpProcedureByPid(spNode.getPid(),false);
		List<JsglModel> jsList = new ArrayList<JsglModel>();
		for (JsglModel js : yhglService.cxJsdmList()) {
			if (spProcedure.getBelongToSys().equals(js.getGnmkdm()) || StringUtil.isEmpty(js.getGnmkdm())) {
				jsList.add(js);
			}
		}
		return jsList;
	}

	private List<SpTask> getTaskOfModule() {
		SpProcedure spProcedure = spProcedureService.findSpProcedureByPid(spNode.getPid(),false);
		List<SpTask> taskList = new ArrayList<SpTask>();
		for (SpTask spTask : spTaskService.findTaskList(new SpTask())) {
			if (spTask.getBelongToSys().equals(spProcedure.getBelongToSys())) {
				taskList.add(spTask);
			}
		}
		return taskList;
	}

	private List<SpTask> getPushTask() {
		List<SpTask> taskList = new ArrayList<SpTask>();
		SpProcedure spProcedure = spProcedureService.findSpProcedureByPid(spNode.getPid(),false);
		List<SpProcedureBill> list = spProcedure.getSpCommitBillList();
		for (SpTask spTask : spTaskService.findTaskList(new SpTask())) {
			for (SpProcedureBill spProcedureBill : list) {				
				if (spTask.getBelongToSys().equals(spProcedureBill.getBillId())) {
					taskList.add(spTask);
					break;
				}
			}
		}
		return taskList;
	}
	
	/*
	 * 
	 * 私有方法描述：获取表单信息
	 *
	 * @param: 
	 * @return: 
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-4-25 下午04:16:28
	 */
	private void initBillClass(SpNode spNode, String billType, String oprate) {
		if(spNode != null){
			List<SpNodeBill> spNodeBillList = null;
			// 初始化提交类型的表单类集合
			if(billType.equals(BillType.COMMIT.toString())){
				List<SpProcedureBill> commitBillList = spProcedureService.findSpProcedureByPid(spNode.getPid(),false).getSpCommitBillList();
				if(commitBillList != null){
					Map<String, List<SpNodeBill>> commitNodeClassMap = new HashMap<String, List<SpNodeBill>>();
					// 流程关联的表单循环
					for(SpProcedureBill spb : commitBillList){
						SpBillConfig billConfig = spBillConfigService.getSpBillConfigById(spb.getBillId());
						if(billConfig.getBillType() == BillType.COMMIT){
							List<XmlBillClass> billClassList = spBillConfigService.getXmlBillClassListLastVersion(spb.getBillId());
							if(billClassList != null){
								spNodeBillList = new ArrayList<SpNodeBill>();
								// 表单类循环
								for(XmlBillClass xmlBillClass : billClassList){
									SpNodeBill nodeBill = new SpNodeBill();
									nodeBill.setClassId(String.valueOf(xmlBillClass.getId()));
									nodeBill.setChecked(false);
									if(oprate.equals("modify") && spNode.getSpCommitNodeBillList() != null){
										for(SpNodeBill snb : spNode.getSpCommitNodeBillList()){
											if(snb.getClassId().equals(String.valueOf(xmlBillClass.getId()))){
												nodeBill = snb;
												nodeBill.setClassesPrivilege(snb.getClassesPrivilege());
												nodeBill.setChecked(true);
											}
										}
									}
									nodeBill.setClassName(xmlBillClass.getName());
									spNodeBillList.add(nodeBill);
								}
							}
							if(billConfig != null ){
								commitNodeClassMap.put(billConfig.getName(), spNodeBillList);
							}
						}
					}
					this.getValueStack().set("commitNodeClassMap", commitNodeClassMap);
				}			
			}
			// 初始化审批类型的表单类集合
			else{
				List<SpProcedureBill> approveBillList = spProcedureService.findSpProcedureByPid(spNode.getPid(),false).getSpApproveBillList();
				if(approveBillList != null){
					Map<String, List<SpNodeBill>> approveNodeClassMap = new HashMap<String, List<SpNodeBill>>();
					// 流程关联的表单循环
					for(SpProcedureBill spb : approveBillList){
						SpBillConfig billConfig = spBillConfigService.getSpBillConfigById(spb.getBillId());	
						if(billConfig.getBillType() == BillType.APPROVE){
							List<XmlBillClass> billClassList = spBillConfigService.getXmlBillClassListLastVersion(spb.getBillId());
							if(billClassList != null){
								spNodeBillList = new ArrayList<SpNodeBill>();
								// 表单类循环
								for(XmlBillClass xmlBillClass : billClassList){
									SpNodeBill nBill = new SpNodeBill();
									nBill.setClassId(String.valueOf(xmlBillClass.getId()));
									nBill.setChecked(false);
									if(oprate.equals("modify") && spNode.getSpApproveNodeBillList() != null){
										for(SpNodeBill snb : spNode.getSpApproveNodeBillList()){
											if(snb.getClassId().equals(String.valueOf(xmlBillClass.getId()))){
												nBill = snb;
												nBill.setClassesPrivilege(snb.getClassesPrivilege());
												nBill.setChecked(true);
											}
										}
									}
									nBill.setClassName(xmlBillClass.getName());
									spNodeBillList.add(nBill);
								}
							}
							if(billConfig != null ){
								approveNodeClassMap.put(String.valueOf(billConfig.getName()), spNodeBillList);
							}
						}						
					}
					this.getValueStack().set("approveNodeClassMap", approveNodeClassMap);
				}
			}
		}
	}

	/**
	 * 请求增加节点
	 * 
	 * @return
	 */
	public String addNode() {
		op = "add";
		this.getValueStack().set("roleList", getRoleOfModule());
		List<SpNodeTask> nodeTasks = new ArrayList<SpNodeTask>();
		List<SpNodeTask> pushTasks = new ArrayList<SpNodeTask>();
		List<SpTask> tasks = getTaskOfModule();
		this.initTask(tasks, nodeTasks, op);
		this.initTask(getPushTask(), pushTasks, op);
		this.getValueStack().set("taskList", nodeTasks);
		this.getValueStack().set("pushTasks", pushTasks);
		this.getValueStack().set("nodeTypes", NodeTypeEnum.values());
		this.initBillClass(spNode, BillType.COMMIT.toString(), op);
		this.initBillClass(spNode, BillType.APPROVE.toString(), op);
		this.getValueStack().set("privilegeTypeList", PrivilegeType.values());
		return EDIT_PAGE;
	}

	/**
	 * 请求修改节点
	 * 
	 * @return
	 */
	public String modifyNode() {
		op = "modify";
		spNode = spNodeService.findNodeById(spNode.getNodeId());
		List<SpTask> tasks = getTaskOfModule();
		List<SpNodeTask> nodeTasks = new ArrayList<SpNodeTask>();
		List<SpNodeTask> pushTasks = new ArrayList<SpNodeTask>();
		if (spNode != null && spNode.getSpTaskList() != null) {
			this.initTask(tasks, nodeTasks, op);
			this.initTask(getPushTask(), pushTasks, op);
		}
		this.getValueStack().set("pushTasks", pushTasks);
		this.getValueStack().set("roleList", getRoleOfModule());
		this.getValueStack().set("taskList", nodeTasks);
		this.getValueStack().set("nodeTypes", NodeTypeEnum.values());
		this.initBillClass(spNode, BillType.COMMIT.toString(), op);
		this.initBillClass(spNode, BillType.APPROVE.toString(), op);
		this.getValueStack().set("privilegeTypeList", PrivilegeType.values());
		return EDIT_PAGE;
	}

	/*
	 * 
	 * 私有方法描述：共用方法
	 *
	 * @param: 
	 * @return: 
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-4-23 下午02:22:03
	 */
	private void initTask(List<SpTask> tasks, List<SpNodeTask> nodeTasks, String oprate) {
		for (SpTask spTask : tasks) {
			SpNodeTask nodeTask = new SpNodeTask();
			nodeTask.setAuto("Y");
			nodeTask.setNeed("Y");
			nodeTask.setSpNode(spNode);
			nodeTask.setSpTask(spTask);
			nodeTask.setChecked(false);
			if(oprate.equals("modify")){
				for (SpNodeTask existsTask : spTaskService.findNodeTaskListByNodeId(spNode.getNodeId())) {
					if (spTask.getTaskId().equals(existsTask.getSpTask().getTaskId())) {
						nodeTask = existsTask;
						nodeTask.setChecked(true);
					}
				}
			}			
			nodeTasks.add(nodeTask);
		}
	}

	/**
	 * 保存修改节点
	 * 
	 * @return
	 */
	public String saveNode() {
		if (spNode.getRoleId() == "" || spNode.getRoleId() == null) {
			this.setErrorMessage("角色不能为空!");
			this.getValueStack().set(DATA, this.getMessage());
			return DATA;
		}
		// 设置是否自动执行状态：如果为分支节点则自动执行
		if(spNode.getNodeType().equals(NodeTypeEnum.BRANCH_NODE.getKey())){
			spNode.setIsAuto("1");
		}else{
			spNode.setIsAuto("0");
		}
		Map<String,String[]> map = new HashMap<String,String[]>();
		map.put("taskIds", taskIds);
		map.put("commitBillClassIds", commitBillClassIds);
		map.put("approveBillClassIds", approveBillClassIds);
		map.put("commitClassPrivilege", commitClassPrivilege);
		map.put("approveClassPrivilege", approveClassPrivilege);
		map.put("taskIsMusts", taskIsMusts);
		map.put("taskIsAutos", taskIsAutos);
		map.put("pushTaskIds", pushTaskIds);
		SpNode nodeQuery=new SpNode();
		nodeQuery.setPid(spNode.getPid());
		List<SpNode> spNodes= spNodeService.findNodeList(nodeQuery);
		SpNode startNode=null,endNode=null;
		for (SpNode node : spNodes) {
			if(NodeTypeEnum.START_NODE.getKey().equals(node.getNodeType())){
				startNode=node;
			}else if(NodeTypeEnum.END_NODE.getKey().equals(node.getNodeType())){
				endNode=node;
			}
		}
		if (op.equals("add")) {
			if(NodeTypeEnum.START_NODE.getKey().equals(spNode.getNodeType())&&startNode!=null){
				this.setErrorMessage("起始节点已经存在！");
				this.getValueStack().set(DATA, this.getMessage());
				return DATA;
			}
			if(NodeTypeEnum.END_NODE.getKey().equals(spNode.getNodeType())&&endNode!=null){
				this.setErrorMessage("终止节点已经存在！");
				this.getValueStack().set(DATA, this.getMessage());
				return DATA;
			}
			spNodeService.insert(spNode, map);
		} else {
			if(NodeTypeEnum.START_NODE.getKey().equals(spNode.getNodeType())
					&&startNode!=null
					&&!StringUtils.equals(spNode.getNodeId(), startNode.getNodeId())){
				this.setErrorMessage("起始节点已经存在！");
				this.getValueStack().set(DATA, this.getMessage());
				return DATA;
			}
			if(NodeTypeEnum.END_NODE.getKey().equals(spNode.getNodeType())
					&&endNode!=null
					&&!StringUtils.equals(spNode.getNodeId(), endNode.getNodeId())){
				this.setErrorMessage("终止节点已经存在！");
				this.getValueStack().set(DATA, this.getMessage());
				return DATA;
			}
			spNodeService.update(spNode, map);
		}
		this.setSuccessMessage("保存成功");
		this.getValueStack().set(DATA, this.getMessage());
		return DATA;
	}

	/**
	 * 删除流程节点类型
	 * 
	 * @return
	 */
	public String removeNode() {
		spNodeService.delete(spNode.getNodeId());
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

	public SpNode getSpNode() {
		return spNode;
	}

	public void setSpNode(SpNode spNode) {
		this.spNode = spNode;
	}

	public void setSpNodeService(ISpNodeService spNodeService) {
		this.spNodeService = spNodeService;
	}

	public String[] getTaskIds() {
		return taskIds;
	}

	public void setTaskIds(String[] taskIds) {
		this.taskIds = taskIds;
	}

	public void setSpTaskService(ISpTaskService spTaskService) {
		this.spTaskService = spTaskService;
	}

	public void setYhglService(IYhglService yhglService) {
		this.yhglService = yhglService;
	}

	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}

	public void setSpProcedureService(ISpProcedureService spProcedureService) {
		this.spProcedureService = spProcedureService;
	}

	/**
	 * @param spBillConfigService : set the property spBillConfigService.
	 */
	
	public void setSpBillConfigService(ISpBillConfigService spBillConfigService) {
		this.spBillConfigService = spBillConfigService;
	}

	public String[] getTaskIsMusts() {
		return taskIsMusts;
	}

	public void setTaskIsMusts(String[] taskIsMusts) {
		this.taskIsMusts = taskIsMusts;
	}

	public String[] getTaskIsAutos() {
		return taskIsAutos;
	}

	public void setTaskIsAutos(String[] taskIsAutos) {
		this.taskIsAutos = taskIsAutos;
	}

	/**
	 * @return commitBillClassIds : return the property commitBillClassIds.
	 */
	
	public String[] getCommitBillClassIds() {
		return commitBillClassIds;
	}

	/**
	 * @param commitBillClassIds : set the property commitBillClassIds.
	 */
	
	public void setCommitBillClassIds(String[] commitBillClassIds) {
		this.commitBillClassIds = commitBillClassIds;
	}

	/**
	 * @return approveBillClassIds : return the property approveBillClassIds.
	 */
	
	public String[] getApproveBillClassIds() {
		return approveBillClassIds;
	}

	/**
	 * @param approveBillClassIds : set the property approveBillClassIds.
	 */
	
	public void setApproveBillClassIds(String[] approveBillClassIds) {
		this.approveBillClassIds = approveBillClassIds;
	}

	/**
	 * @return commitClassPrivilege : return the property commitClassPrivilege.
	 */
	
	public String[] getCommitClassPrivilege() {
		return commitClassPrivilege;
	}

	/**
	 * @param commitClassPrivilege : set the property commitClassPrivilege.
	 */
	
	public void setCommitClassPrivilege(String[] commitClassPrivilege) {
		this.commitClassPrivilege = commitClassPrivilege;
	}

	/**
	 * @return approveClassPrivilege : return the property approveClassPrivilege.
	 */
	
	public String[] getApproveClassPrivilege() {
		return approveClassPrivilege;
	}

	/**
	 * @param approveClassPrivilege : set the property approveClassPrivilege.
	 */
	
	public void setApproveClassPrivilege(String[] approveClassPrivilege) {
		this.approveClassPrivilege = approveClassPrivilege;
	}

	/**
	 * @param spNodeBillService : set the property spNodeBillService.
	 */
	
	public void setSpNodeBillService(ISpNodeBillService spNodeBillService) {
		this.spNodeBillService = spNodeBillService;
	}

	/**
	 * @param spBillDataPushEventConfigService : set the property spBillDataPushEventConfigService
	 */
	public void setSpBillDataPushEventConfigService(
			ISpBillDataPushEventConfigService spBillDataPushEventConfigService) {
		this.spBillDataPushEventConfigService = spBillDataPushEventConfigService;
	}

	public String[] getPushTaskIds() {
		return pushTaskIds;
	}

	public void setPushTaskIds(String[] pushTaskIds) {
		this.pushTaskIds = pushTaskIds;
	}

}
