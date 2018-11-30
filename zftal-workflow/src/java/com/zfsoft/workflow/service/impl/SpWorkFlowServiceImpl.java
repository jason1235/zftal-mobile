package com.zfsoft.workflow.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.dybill.service.ISpBillConfigService;
import com.zfsoft.hrm.dybill.service.ISpBillDataPushRunService;
import com.zfsoft.hrm.dybill.service.ISpBillExportService;
import com.zfsoft.hrm.dybill.service.ISpBillInstanceService;
import com.zfsoft.hrm.pendingAffair.entities.PendingAffairInfo;
import com.zfsoft.hrm.pendingAffair.service.svcinterface.IPendingAffairService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.workflow.dao.ISpProcedureBillDao;
import com.zfsoft.workflow.dao.ISpWorkProcedureBillDao;
import com.zfsoft.workflow.enumobject.BusinessEnum;
import com.zfsoft.workflow.enumobject.NodeTypeEnum;
import com.zfsoft.workflow.enumobject.TaskIsMustEnum;
import com.zfsoft.workflow.enumobject.TaskNameEnum;
import com.zfsoft.workflow.enumobject.WorkNodeEStatusEnum;
import com.zfsoft.workflow.enumobject.WorkNodeStatusEnum;
import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpAuditingLog;
import com.zfsoft.workflow.model.SpLine;
import com.zfsoft.workflow.model.SpNode;
import com.zfsoft.workflow.model.SpNodeBill;
import com.zfsoft.workflow.model.SpProcedure;
import com.zfsoft.workflow.model.SpProcedureBill;
import com.zfsoft.workflow.model.SpTask;
import com.zfsoft.workflow.model.SpWorkLine;
import com.zfsoft.workflow.model.SpWorkNode;
import com.zfsoft.workflow.model.SpWorkNodeBill;
import com.zfsoft.workflow.model.SpWorkProcedure;
import com.zfsoft.workflow.model.SpWorkProcedureBill;
import com.zfsoft.workflow.model.SpWorkTask;
import com.zfsoft.workflow.model.query.WorkAuditingQuery;
import com.zfsoft.workflow.model.query.WorkTaskQuery;
import com.zfsoft.workflow.model.result.BaseResult;
import com.zfsoft.workflow.model.result.NodeListSqlResult;
import com.zfsoft.workflow.model.result.TaskListSqlResult;
import com.zfsoft.workflow.service.ISpAuditingLogService;
import com.zfsoft.workflow.service.ISpProcedureService;
import com.zfsoft.workflow.service.ISpWorkFlowService;
import com.zfsoft.workflow.service.ISpWorkLineService;
import com.zfsoft.workflow.service.ISpWorkNodeBillService;
import com.zfsoft.workflow.service.ISpWorkNodeService;
import com.zfsoft.workflow.service.ISpWorkProcedureService;
import com.zfsoft.workflow.service.ISpWorkTaskService;

/**
 * 
 * 类描述：工作审批管理实现类
 * 
 * @version: 1.0
 * @author: yingjie.fan
 * @version: 2013-3-12 上午11:30:17
 */
public class SpWorkFlowServiceImpl extends BaseInterfaceServiceImpl implements ISpWorkFlowService {

	/* @model: 注入 */
	private ISpProcedureService spProcedureService;
	private ISpWorkProcedureService spWorkProcedureService;
	private ISpWorkNodeService spWorkNodeService;
	private ISpWorkTaskService spWorkTaskService;
	private ISpWorkLineService spWorkLineService;
	private ISpAuditingLogService spAuditingLogService;
	private ISpWorkNodeBillService spWorkNodeBillService;
	private ISpProcedureBillDao spProcedureBillDao;
	private ISpWorkProcedureBillDao spWorkProcedureBillDao;
	private ISpBillExportService spBillExportService;
	private ISpBillConfigService spBillConfigService;
	private ISpBillInstanceService spBillInstanceService;
	private IPendingAffairService pendingAffairService;
	private ISpBillDataPushRunService spBillDataPushRunService;

	@Override
	public BaseResult addSpWorkFlow(HashMap<String, String> map) throws WorkFlowException {
		BaseResult result = new BaseResult();
		try {
			String businessCode = map.get("businessCode");
			String workId = map.get("workId");
			String userId = map.get("userId");
			String departmentId = map.get("departmentId");
			if (StringUtil.isEmpty(businessCode)) {
				throw new WorkFlowException("异常：业务编码不能为空！");
			}
			if (StringUtil.isEmpty(workId)) {
				throw new WorkFlowException("异常：工作ID不能为空！");
			}

			// 清楚垃圾数据
			spWorkProcedureService.removeSpWorkProcedureByWid(workId);

			// 添加工作审核流程对象
			SpProcedure spProcedure = spProcedureService.findSpProcedureByBCode(businessCode);
			if (spProcedure == null) {
				throw new WorkFlowException("异常：根据业务编码获取到的流程对象为空！");
			}
			SpWorkProcedure spWorkProcedure = SpWorkProcedure.putProcedureObject(spProcedure);
			spWorkProcedure.setWid(workId);
			spWorkProcedure.setBcode(businessCode);
			spWorkProcedure.setUserId(userId);
			spWorkProcedure.setDepartmentId(departmentId);
			spWorkProcedureService.addSpWorkProcedure(spWorkProcedure);

			// 添加工作审核节点对象
			List<SpNode> spNodeList = spProcedure.getSpNodeList();
			if (spNodeList != null && spNodeList.size() > 0) {
				boolean findStartNode = false;
				boolean findEndNode = false;
				for (SpNode spNode : spNodeList) {
					if (spNode != null) {
						SpWorkNode spWorkNode = SpWorkNode.putNodeObject(spNode);
						spWorkNode.setWid(workId);
						spWorkNode.setStatus(WorkNodeStatusEnum.WAIT_AUDITING.getKey());
						// 如果是第一个节点就默认打开待执行状态
						if (spNode.getNodeType().equals(NodeTypeEnum.START_NODE.getKey())) {
							spWorkNode.setEstatus(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey());
							spWorkNode.setDepartmentId(departmentId);
							// 添加代办事宜
							this.addPendingAffairInfo(spWorkNode);
							findStartNode=true;
						} else { 
							if (spNode.getNodeType().equals(NodeTypeEnum.END_NODE.getKey())) {
								findEndNode=true;
							}
							spWorkNode.setEstatus(WorkNodeEStatusEnum.CLOSE.getKey());
						}
						spWorkNodeService.addSpWorkNode(spWorkNode);
					}

					// 添加工作审核任务对象
					List<SpTask> spTaskList = spNode.getSpTaskList();
					if (spTaskList != null && spTaskList.size() > 0) {
						for (SpTask spTask : spTaskList) {
							if (spTask != null) {
								SpWorkTask spWorkTask = SpWorkTask.putTaskObject(spTask);
								spWorkTask.setWid(workId);
								spWorkTask.setNodeId(spNode.getNodeId());
								spWorkTaskService.addSpWorkTask(spWorkTask);
							}
						}
					}

					// 添加工作审核表单对象
					this.insertNodeBill(workId, spNode.getSpApproveNodeBillList());
					this.insertNodeBill(workId, spNode.getSpCommitNodeBillList());
				}
				if(!findStartNode){
					throw new WorkFlowException("异常：未找到该审核流程的开始节点！");
				}
				if(spNodeList.size()>1&&!findEndNode){
					throw new WorkFlowException("异常：未找到该审核流程的结束节点！");
				}
				
			} else {
				throw new WorkFlowException("异常：根据流程ID获取到的节点集合对象为空！");
			}

			// 添加工作审核节点连线对象
			List<SpLine> spLineList = spProcedure.getSpLineList();
			if (spLineList != null && spLineList.size() > 0) {
				for (SpLine spLine : spLineList) {
					SpWorkLine spWorkLine = SpWorkLine.putLineObject(spLine);
					spWorkLine.setWid(workId);
					spWorkLineService.addSpWorkLine(spWorkLine);
				}
			}

			// 添加工作审核流程表单对象
			this.insertProcedureBill(workId, spProcedure.getSpApproveBillList());
			this.insertProcedureBill(workId, spProcedure.getSpCommitBillList());

			result.setWorkId(workId);
			result.setWorkStatus(WorkNodeStatusEnum.WAIT_AUDITING.getKey());
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 
	 * 私有方法描述：增加工作审核流程表单关联关系
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-4-26 下午05:06:05
	 */
	private void insertProcedureBill(String workId, List<SpProcedureBill> spProcedureBillList) {
		if (spProcedureBillList != null && spProcedureBillList.size() > 0) {
			for (SpProcedureBill spBill : spProcedureBillList) {
				SpWorkProcedureBill spWorkProcedureBill = SpWorkProcedureBill.putWorkProcedureBillObject(spBill);
				spWorkProcedureBill.setWid(workId);
				spWorkProcedureBillDao.insert(spWorkProcedureBill);
			}
		}
	}

	/**
	 * 
	 * 私有方法描述：增加工作审核节点表单关联关系
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-4-26 下午04:34:17
	 */
	private void insertNodeBill(String workId, List<SpNodeBill> spNodeBillList) {
		if (spNodeBillList != null && spNodeBillList.size() > 0) {
			for (SpNodeBill nodeBill : spNodeBillList) {
				if (nodeBill != null) {
					SpWorkNodeBill spWorkNodeBill = SpWorkNodeBill.putWorkNodeBillObject(nodeBill);
					spWorkNodeBill.setWid(workId);
					spWorkNodeBillService.insert(spWorkNodeBill);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zfsoft.workflow.service.ISpWorkFlowService#doCancelDeclare(java.lang
	 * .String)
	 */

	@Override
	public boolean doCancelDeclare(String workId) throws WorkFlowException {
		try {
			if (StringUtil.isNotEmpty(workId)) {
				// 如果存在已执行的节点或任务，不允许取消
				/*
				 * SpWorkNode spWorkNode = new SpWorkNode();
				 * spWorkNode.setEstatus
				 * (WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
				 * List<SpWorkNode> nodeList =
				 * spWorkNodeService.findWorkNodeList(spWorkNode);
				 */
				/*
				 * SpWorkTask spWorkTask = new SpWorkTask();
				 * spWorkTask.setEstatus
				 * (WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
				 * List<SpWorkTask> taskList =
				 * spWorkTaskService.findWorkTaskList(spWorkTask); if(null !=
				 * nodeList || nodeList.size() >0 || null != taskList ||
				 * taskList.size() >0) return false;
				 */
				/*
				 * if(null != nodeList || nodeList.size() >0){ throw new
				 * WorkFlowException("异常：流程已开始，不能取消！"); }else{
				 */
				spWorkProcedureService.removeSpWorkProcedureByWid(workId);
				// 删除代办事宜
				pendingAffairService.removePendingAffairInfoByBid(workId);
				return true;
				// }
			} else {
				throw new WorkFlowException("异常：工作ID为空！");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public Map<String, Object> queryWorkFlowList(WorkAuditingQuery query) throws WorkFlowException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (query != null) {
				List<SpWorkNode> nodeList = spWorkNodeService.findWorkNodeListByCondition(query.getBusinessType(),
						query.getBusinessCode(), query.getStatus(), query.getRoleIdArray(), query.getAuditor());
				resultMap.put("list", nodeList);
				// 组装工作ID数组
				if (nodeList != null && nodeList.size() > 0) {
					String[] workIdArray = new String[nodeList.size()];
					int i = 0;
					SpWorkNode spWorkNode = null;
					for (Iterator<SpWorkNode> it = nodeList.iterator(); it.hasNext();) {
						spWorkNode = (SpWorkNode) it.next();
						if (spWorkNode != null) {
							workIdArray[i] = spWorkNode.getWid();
						}
						i++;
					}
					resultMap.put("workIdArray", workIdArray);
				}
			} else {
				throw new WorkFlowException("异常：查询对象为空！");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return resultMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zfsoft.workflow.service.ISpWorkFlowService#queryWorkFlowListSql(com
	 * .zfsoft.workflow.model.query.WorkAuditingQuery)
	 */

	@Override
	public NodeListSqlResult queryWorkFlowListSql(WorkAuditingQuery query) throws WorkFlowException {
		NodeListSqlResult result = new NodeListSqlResult();
		try {
			if (query != null) {
				String sqlContent = NodeListSqlResult.getSqlStartPart();
				String roleIdSql = "", eStatusSql = "", statusSql = "", bCodeSql = "", bTypeSql = "", auditorSql = "";

				if (StringUtil.isEmpty(query.getBusinessCode()) && StringUtil.isEmpty(query.getBusinessType())) {
					throw new WorkFlowException("异常：业务类型和业务ID必须有一个不能为空！");
				} else {
					if (StringUtil.isNotEmpty(query.getBusinessCode()) && StringUtil.isEmpty(query.getBusinessType())) {
						bCodeSql = " AND sb.b_code IN ('" + query.getBusinessCode() + "') ";
					}
					if (StringUtil.isNotEmpty(query.getBusinessType()) && StringUtil.isEmpty(query.getBusinessCode())) {
						bTypeSql = " AND sb.b_type = '" + query.getBusinessType() + "' ";
					}
					if (StringUtil.isNotEmpty(query.getBusinessType())
							&& StringUtil.isNotEmpty(query.getBusinessCode())) {
						bCodeSql = " AND sb.b_code IN ('" + query.getBusinessCode() + "') ";
						bTypeSql = " AND sb.b_type = '" + query.getBusinessType() + "' ";
					}
				}
				if (query.getRoleIdArray() == null || query.getRoleIdArray().length == 0) {
					throw new WorkFlowException("异常：角色ID为空！");
				} else {
					roleIdSql = " AND swn.ROLE_ID IN (" + this.handleRole(query.getRoleIdArray()) + ") ";
				}
				
				if (StringUtil.isNotEmpty(query.getStatus())) {
					if(!"ALL".equals(query.getStatus())){
						statusSql = " AND swn.STATUS = '" + query.getStatus() + "' ";
						// 审核不通过状态
						if (query.getStatus().equals(WorkNodeStatusEnum.NO_PASS_AUDITING.getKey())) {
							eStatusSql = " AND swn.E_STATUS = '" + WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey() + "' ";
							if (StringUtil.isNotEmpty(query.getAuditor())) {
								auditorSql = " AND swn.AUDITOR_ID = '" + query.getAuditor() + "' ";
							}
						}
						// 审核通过状态，包含完成执行和没有完成执行两种
						else if (query.getStatus().equals(WorkNodeStatusEnum.PASS_AUDITING.getKey())) {
							if (StringUtil.isNotEmpty(query.getAuditor())) {
								auditorSql = " AND swn.AUDITOR_ID = '" + query.getAuditor() + "' ";
							}
						}
						// 审核中状态
						else if (query.getStatus().equals(WorkNodeStatusEnum.IN_AUDITING.getKey())) {
							if (StringUtil.isNotEmpty(query.getAuditor())) {
								auditorSql = " AND swn.AUDITOR_ID = '" + query.getAuditor() + "' ";
							}
						}
						// 待审核状态
						else if(query.getStatus().equals(WorkNodeStatusEnum.WAIT_AUDITING.getKey())){
							eStatusSql = " AND swn.E_STATUS = '" + WorkNodeEStatusEnum.WAIT_EXECUTE.getKey() + "' ";
						}
					}					
				}
				// 全部（但不包含执行状态为关闭的记录）
				else{
					eStatusSql = " AND swn.E_STATUS <> '" + WorkNodeEStatusEnum.CLOSE.getKey() + "' ";
				}
				sqlContent = sqlContent.concat(bCodeSql).concat(bTypeSql).concat(roleIdSql).concat(auditorSql)
						.concat(statusSql).concat(eStatusSql);
				result.setSqlContent(sqlContent);
			} else {
				throw new WorkFlowException("异常：查询对象为空！");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zfsoft.workflow.service.ISpWorkFlowService#queryWorkFlowByWorkId(
	 * java.lang.String)
	 */

	@Override
	public SpWorkProcedure queryWorkFlowByWorkId(String workId) throws WorkFlowException {
		try {
			if (StringUtil.isNotEmpty(workId)) {
				return spWorkProcedureService.findWorkProcedureByWId(workId);
			} else {
				throw new WorkFlowException("异常：工作ID为空！");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public SpWorkProcedure queryWorkFlowByWorkIdAndRoleId(String workId, String[] roleIdArray) throws WorkFlowException {
		SpWorkProcedure spWorkProcedure = null;
		try {
			if (roleIdArray != null && roleIdArray.length == 0) {
				throw new WorkFlowException("异常：角色数组为空！");
			}
			if (StringUtil.isEmpty(workId)) {
				throw new WorkFlowException("异常：工作ID为空！");
			}
			List<SpWorkNode> nodeList = new ArrayList<SpWorkNode>();
			spWorkProcedure = spWorkProcedureService.findWorkProcedureByWId(workId);
			// 通过角色设置节点是否可以编辑
			if (roleIdArray != null && spWorkProcedure != null && spWorkProcedure.getSpWorkNodeList() != null
					&& spWorkProcedure.getSpWorkNodeList().size() > 0) {
				for (SpWorkNode spWorkNode : spWorkProcedure.getSpWorkNodeList()) {
					if (spWorkNode.getEstatus().equals(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey())
							&& isExists(roleIdArray,spWorkNode.getRoleId())) {
						spWorkNode.setEdit(true);
					} else {
						spWorkNode.setEdit(false);
					}
					nodeList.add(spWorkNode);
				}
				spWorkProcedure.setSpWorkNodeList(nodeList);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return spWorkProcedure;
	}

	private boolean isExists(String[] roles, String role){
		boolean bol = true;
		for(int i = 0; i < roles.length; i++){
			if(role.equals(roles[i])){
				bol = true;
				break;
			}
			bol = false;
		}
		
		return bol;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zfsoft.workflow.service.ISpWorkFlowService#doAuditingRsult(com.zfsoft
	 * .workflow.model.SpWorkNode)
	 */

	@Override
	public BaseResult doAuditingRsult(SpWorkNode spWorkNode, String[] roleIdArray, String returnNodeId)
			throws WorkFlowException {
		BaseResult result = new BaseResult();
		try {
			if (spWorkNode != null) {
				if (StringUtil.isEmpty(spWorkNode.getNodeId())) {
					throw new WorkFlowException("异常：节点ID为空！");
				}
				if (StringUtil.isEmpty(spWorkNode.getWid())) {
					throw new WorkFlowException("异常：工作ID为空！");
				}
				if (StringUtil.isEmpty(spWorkNode.getStatus())) {
					throw new WorkFlowException("异常：操作类型为空！");
				}
				if (StringUtil.isEmpty(spWorkNode.getSuggestion())) {
					throw new WorkFlowException("异常：审核意见为空！");
				}
				if (StringUtil.isEmpty(spWorkNode.getAuditorId())) {
					throw new WorkFlowException("异常：操作人为空！");
				}
				if (StringUtil.isEmpty(spWorkNode.getRoleId())) {
					SpWorkNode node = new SpWorkNode();
					node.setWid(spWorkNode.getWid());
					node.setNodeId(spWorkNode.getNodeId());
					String roleId = spWorkNodeService.findWorkNodeByWidAndNodeId(node).getRoleId();
					if(StringUtil.isEmpty(roleId)){
						throw new WorkFlowException("异常：节点操作角色为空！");
					}
					spWorkNode.setRoleId(roleId);
				} else {
					if (!isExists(roleIdArray,spWorkNode.getRoleId())) {
						throw new WorkFlowException("异常：不具备此节点的角色操作权限！");
					}
				}

				if (spWorkNode.getStatus().equals(// 保存
						WorkNodeStatusEnum.IN_AUDITING.getKey())) {
					// 保存审核信息及任务信息，纯洁的保持，不记录操作日志
					// 保存但不修改状态为审核中，仍然 保持待审核状态，避免操作人员找不到数据，造成混淆
					// 保存并将状态置为原状态 （原状态不一定是待审核）by ChenMinming
					SpWorkNode node = new SpWorkNode();
					node.setWid(spWorkNode.getWid());
					node.setNodeId(spWorkNode.getNodeId());
					node = spWorkNodeService.findWorkNodeByWidAndNodeId(node);
					spWorkNode.setStatus(node.getStatus());
					this.saveSpWorkNode(spWorkNode, false, false);
					this.saveSpWorkTask(spWorkNode, false);

				} else if (spWorkNode.getStatus().equals(// 审核通过
						WorkNodeStatusEnum.PASS_AUDITING.getKey())) {
					// 保存审核信息及任务信息，并记录操作日志；同时把任务执行状态置为“已执行”
					spWorkNode.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
					this.saveSpWorkNode(spWorkNode, true, true);
					// 执行节点中的任务
					this.saveSpWorkTask(spWorkNode, true);
					// 执行节点中的任务
					this.doAutoTask(spWorkNode, true);
					
					SpWorkNode spWorkNode2 = spWorkNodeService.findWorkNodeByWidAndNodeId(spWorkNode);
					SpWorkProcedure spWorkProcedure = this.queryWorkFlowByWorkId(spWorkNode2.getWid());
					List<SpWorkNode> stepNodeList = this.findStepNode(spWorkProcedure, spWorkNode2);
					if(this.findStepStatus(stepNodeList)){
						changeNextNodeStatus(spWorkNode2);
					}

				} else if (spWorkNode.getStatus().equals(// 审核不通过
						WorkNodeStatusEnum.NO_PASS_AUDITING.getKey())) {
					/*
					 * 保存审核信息及任务信息（含审核状态为不通过等），并记录操作日志； 记录操作日志；
					 * 无论相关必须执行的任务是否都已经执行，节点执行状态置为“已执行”， 但下一节点执行状态不改变，流程停止流转
					 */
					spWorkNode.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
					this.saveSpWorkNode(spWorkNode, true, true);
					this.saveSpWorkTask(spWorkNode, true);
					
					SpWorkNode spWorkNode2 = spWorkNodeService.findWorkNodeByWidAndNodeId(spWorkNode);
					SpWorkProcedure spWorkProcedure = this.queryWorkFlowByWorkId(spWorkNode2.getWid());
					List<SpWorkNode> stepNodeList = this.findStepNode(spWorkProcedure, spWorkNode2);
					if(this.findStepStatus(stepNodeList)){
						changeNextNodeStatus(spWorkNode2);
					}

				} else if (spWorkNode.getStatus().equals(// 如果是退回操作，必须判断退回节点ID是否为空
						WorkNodeStatusEnum.RETURN_AUDITING.getKey())) {
					if (StringUtil.isEmpty(returnNodeId)) {
						throw new WorkFlowException("异常：退回节点ID为空！");
					}
					// 如果是退回申报人则删除整个工作审批实例
					else if("-1".equals(returnNodeId)){
						spWorkNodeService.insertNodeLog(spWorkNode);// 记录操作日志
						this.doCancelDeclare(spWorkNode.getWid());
						return result;
					}
//					if (NodeTypeEnum.START_NODE.getKey().equalsIgnoreCase(spWorkNode.getNodeType())) {
//						throw new WorkFlowException("异常：已是开始节点！");
//					}

					// 清空 给定节点及其后所有节点 的 审核信息及任务审核信息，还原节点及任务为初始状态
					spWorkNodeService.insertNodeLog(spWorkNode);// 记录操作日志
					spWorkNode.setAuditorId(null);
					spWorkNode.setAuditTime(null);
					spWorkNode.setAuditResult(null);
					spWorkNode.setEstatus(WorkNodeEStatusEnum.CLOSE.getKey());

				    SpWorkProcedure p = this.queryWorkFlowByWorkId(spWorkNode.getWid());
				    Integer step=null;
				    for (SpWorkNode workNode : p.getSpWorkNodeList()) {
				    	if(step==null&&workNode.getNodeId().equals(returnNodeId)){
				    		step=workNode.getStep();
				    		spWorkNode.setEstatus(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey());
				    	}
				    	else if(step!=null&&workNode.getStep()>step){
				    		spWorkNode.setEstatus(WorkNodeEStatusEnum.CLOSE.getKey());
				    	}else{
				    		continue;
				    	}
				    	spWorkNode.setAuditorId(null);
						spWorkNode.setAuditTime(null);
						spWorkNode.setAuditResult(null);
				    	spWorkNode.setStatus(WorkNodeStatusEnum.WAIT_AUDITING.getKey());
						spWorkNode.setSuggestion(" ");
						spWorkNode.setNodeId(workNode.getNodeId());
				    	this.saveSpWorkNode(spWorkNode, false, true);
						// 获得节点关联的任务
						SpWorkTask wt = new SpWorkTask();
						wt.setWid(spWorkNode.getWid());
						wt.setNodeId(spWorkNode.getNodeId());
						spWorkNode.setSpWorkTaskList(spWorkTaskService.findWorkTaskList(wt));
						if (spWorkNode.getSpWorkTaskList() != null && spWorkNode.getSpWorkTaskList().size() > 0) {
							for (SpWorkTask spWorkTask : spWorkNode.getSpWorkTaskList()) {
								// spWorkTaskService.insertTaskLog(spWorkTask);//
								// 记录操作日志
								spWorkTask.setOperator(null);
								spWorkTask.setOpreateTime(null);
								spWorkTask.setResult(" ");
								spWorkTask.setEstatus(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey());
								spWorkTaskService.editSpWorkTask(spWorkTask);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.zfsoft.workflow.service.ISpWorkFlowService#doCancelAuditingRsult(com.zfsoft.workflow.model.SpWorkNode, java.lang.String[])
	 */
	
	@Override
	public BaseResult doCancelAuditingRsult(SpWorkNode spWorkNode, String[] roleIdArray) throws WorkFlowException {
		try {
			// 撤销操作就等同于从下一个节点退回
			SpWorkLine spWorkLine = new SpWorkLine();
			spWorkLine.setWid(spWorkNode.getWid());
			spWorkLine.setUnodeId(spWorkNode.getNodeId());
			List<SpWorkLine> spWorkLineList = spWorkLineService.findWorkLineList(spWorkLine);
			// 通过连线获取下一个节点
			SpWorkNode downSpWrokNode = new SpWorkNode();
			if(spWorkLineList != null && spWorkLineList.size() > 0){
				spWorkLine = spWorkLineList.get(0);
				downSpWrokNode.setWid(spWorkNode.getWid());
				downSpWrokNode.setNodeId(spWorkLine.getDnodeId());
				downSpWrokNode = spWorkNodeService.findWorkNodeByWidAndNodeId(downSpWrokNode);
				
				// 如果下一个节点不是待审核状态，不能执行撤销操作
				if(!WorkNodeStatusEnum.WAIT_AUDITING.getKey().equals(downSpWrokNode.getStatus())){
					throw new WorkFlowException("异常：下一个审核环节已经执行审核操作，不能执行撤销操作！");
				}
				downSpWrokNode.setStatus(WorkNodeStatusEnum.RETURN_AUDITING.getKey());// 执行退回操作
				downSpWrokNode.setSuggestion("撤销审核操作！");
				downSpWrokNode.setAuditorId(spWorkNode.getAuditorId());
				downSpWrokNode.setBcode(spWorkNode.getBcode());
				downSpWrokNode.setBtype(spWorkNode.getBtype());
				downSpWrokNode.setAuditResult(spWorkNode.getAuditResult());
				downSpWrokNode.setRoleId(spWorkNode.getRoleId());
				return this.doAuditingRsult(downSpWrokNode, roleIdArray, spWorkNode.getNodeId());
				
			}else{
				// 如果是最后一个节点
				SpWorkNode spWorkNode2 = spWorkNodeService.findWorkNodeByWidAndNodeId(spWorkNode);
				if(spWorkNode2 != null){
					spWorkNode2.setStatus(WorkNodeStatusEnum.WAIT_AUDITING.getKey());
					spWorkNode2.setEstatus(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey());
					spWorkNode2.setAuditorId(spWorkNode.getAuditorId());
					spWorkNode2.setBcode(spWorkNode.getBcode());
					spWorkNode2.setBtype(spWorkNode.getBtype());
					spWorkNode2.setAuditResult(spWorkNode.getAuditResult());
					spWorkNodeService.editSpWorkNode(spWorkNode2);
					spWorkNode2.setStatus(WorkNodeStatusEnum.RETURN_AUDITING.getKey());// 执行退回操作
					spWorkNode2.setSuggestion("撤销审核操作！");
					spWorkNodeService.insertNodeLog(spWorkNode2);// 记录操作日志
				}
			}			
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 
	 * 方法描述：执行节点中的任务
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-4-3 上午08:44:54
	 */
	private void saveSpWorkTask(SpWorkNode spWorkNode, boolean logFlag) {
		if(!spWorkNode.getStatus().equals(// 审核通过
				WorkNodeStatusEnum.PASS_AUDITING.getKey())) {
			return;
		}
		if (spWorkNode.getSpWorkTaskList() != null && spWorkNode.getSpWorkTaskList().size() > 0) {
			for (SpWorkTask spWorkTask : spWorkNode.getSpWorkTaskList()) {
				
				spWorkTask.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());// 执行状态
				spWorkTaskService.editSpWorkTask(spWorkTask);
				if (logFlag)
					spWorkTaskService.insertTaskLog(spWorkTask, null);// 记录操作日志
			}
		}
	}
	
	/**
	 * 
	 * 方法描述：执行节点中的自动任务
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: Chen Minming
	 * @since: 2013-9-12 10:10:10
	 */
	private void doAutoTask(SpWorkNode spWorkNode, boolean logFlag) {
		//当且只当节点通过时 才真正执行方法
		if(!spWorkNode.getStatus().equals(
				WorkNodeStatusEnum.PASS_AUDITING.getKey())) {
			return;
		}
		//查出当前工作节点下所有任务
		SpWorkNode oldNode = spWorkNodeService.findWorkNodeByWidAndNodeId(spWorkNode);
		List<SpWorkTask>  tasks = oldNode.getSpWorkTaskList();
		//对待审核的自动任务进行相应的处理【目前只对回填任务进行处理】
		if (tasks != null && tasks.size() > 0) {
			for (SpWorkTask spWorkTask : tasks) {
				//非自动任务-【跳过】
				if (!"Y".equals(spWorkTask.getIsAuto())) {
					continue;
				}
				//暂时【不对任务状态进行过滤】 
				//即自动任务在归属节点【多次审核通过】（如退回后重新通过的情况）时会【重复执行】
//				if(!WorkNodeEStatusEnum.WAIT_EXECUTE.getKey().equals(spWorkTask.getEstatus()){
//					continue;
//				}
				//非回填任务-【暂时跳过】
				if (!TaskNameEnum.DATA_INPUT.getKey().equals(spWorkTask.getTaskCode())) {
					continue;
				}
				//表单回填任务
				if (TaskNameEnum.DATA_INPUT.getKey().equals(spWorkTask.getTaskCode())) {
					if (StringUtil.isEmpty(spWorkNode.getSpBillInstanceId())) {
						throw new WorkFlowException("回填任务执行失败，请传入表单id");
					}
					spBillDataPushRunService.pushData(spWorkTask.getTaskId(),
							oldNode.getUserId(), spWorkNode.getSpBillInstanceId());
					spWorkTask.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());// 执行状态
				}
				
				spWorkTaskService.editSpWorkTask(spWorkTask);
				if (logFlag)
					spWorkTaskService.insertTaskLog(spWorkTask, null);// 记录操作日志
			}
		}
	}

	/**
	 * 
	 * 方法描述：修改节点状态并记录日志
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-4-3 上午08:44:24
	 */
	private void saveSpWorkNode(SpWorkNode spWorkNode, boolean logFlag, boolean updateDbsy) {
		// 保存审核信息及任务信息
		spWorkNodeService.editSpWorkNode(spWorkNode);
		// 如果需要修改或添加代办事宜
		if(updateDbsy){
			if(spWorkNode.getEstatus().equals(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey())){
				// 修改代办事宜
				this.updatePendingAffairInfo(spWorkNode);
			}else if(spWorkNode.getEstatus().equals(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey())){
				// 添加代办事宜
				this.addPendingAffairInfo(spWorkNode);
			}			
		}		
		if (logFlag){
			spWorkNodeService.insertNodeLog(spWorkNode);// 记录操作日志
		}			
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zfsoft.workflow.service.ISpWorkFlowService#doTaskRsult(java.util.
	 * List)
	 */
	@Override
	public List<SpWorkTask> doTaskRsult(List<SpWorkTask> spWorkTaskList) throws WorkFlowException {
		List<SpWorkTask> resultList = new ArrayList<SpWorkTask>();
		try {
			if (spWorkTaskList != null && spWorkTaskList.size() > 0) {
				for (SpWorkTask spWorkTask : spWorkTaskList) {
					if (spWorkTask != null && StringUtil.isNotEmpty(spWorkTask.getWid())) {
						List<SpWorkTask> taskList = null;
						/*
						 * TaskCode不为空，则优先根据TaskCode和Wid取
						 */
						if (StringUtil.isNotEmpty(spWorkTask.getTaskCode())) {
							// task =
							// spWorkTaskService.findWorkNodeByTaskCodeAndTaskId(spWorkTask);
							taskList = spWorkTaskService.findWorkTaskList(spWorkTask);
						} else if (StringUtil.isNotEmpty(spWorkTask.getTaskId())) {
							SpWorkTask t = spWorkTaskService.findWorkNodeByWidAndTaskId(spWorkTask);
							if (null != t) {
								taskList = new ArrayList<SpWorkTask>();
								taskList.add(t);
							}
						}
						if (null != taskList && taskList.size() > 0) {
							// String warnInfo = "";
							for (SpWorkTask task : taskList) {
								if (task != null && task.getEstatus().equals(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey())) {
									this.doTask(resultList, spWorkTask, task);
									/*
									 * // 判断是否满足执行条件 if
									 * (StringUtil.isEmpty(task.
									 * getExeCondition())) {
									 * this.doTask(resultList, spWorkTask,
									 * task); } else { if
									 * (this.isContentExeCondition(task)) {
									 * this.doTask(resultList, spWorkTask,
									 * task); } else { warnInfo =
									 * "不满足任务执行条件，执行任务操作失败";
									 * spWorkTaskService.editSpWorkTask
									 * (spWorkTask);
									 * spWorkTaskService.insertTaskLog
									 * (spWorkTask, warnInfo);// 记录操作日志 }
									 * 
									 * }
									 */
								}
							}
						}
					}
				}

			} else {
				throw new WorkFlowException("异常：执行的任务集合为空！");
			}
		} catch (Exception e) {
			throw new WorkFlowException(e.getMessage(), e);
		}
		return resultList;
	}

	/**
	 * 
	 * 私有方法描述：执行任务
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-5-6 上午11:14:19
	 */
	private void doTask(List<SpWorkTask> resultList, SpWorkTask spWorkTask, SpWorkTask task) {
		spWorkTask.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
		spWorkTaskService.editSpWorkTask(spWorkTask);
		spWorkTaskService.insertTaskLog(spWorkTask, null);// 记录操作日志
		editNodeEStatus(task);// 修改节点的执行状态
		resultList.add(spWorkTask);
	}

	/**
	 * 
	 * 私有方法描述：判断是否满足执行条件
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-5-3 下午05:42:10
	 */
	// private boolean isContentExeCondition(SpWorkTask task) {
	// boolean result = true;
	// SpWorkTask spWorkTask =
	// spWorkTaskService.findWorkNodeByWidAndTaskId(task);
	// if (spWorkTask != null) {
	// // 条件表达式格式：|name1|%|name2|>=|2|,|name3|>=|60|
	// Map<String, String> valueMap = spBillExportService.getValueMap(
	// spBillConfigService.getSpBillConfigById(spWorkTask.getBillId()),
	// spBillInstanceService.getNewSpBillInstance(spWorkTask.getInstanceId()));
	// if (valueMap != null && valueMap.size() > 0) {
	// // 取出所有条件表达式（逗号隔开样式）放到数组中
	// String[] allConditionarray = spWorkTask.getExeCondition().split(",");
	// if (allConditionarray != null && allConditionarray.length > 0) {
	// String expression = "";
	// for (String condition : allConditionarray) {
	// String[] conditionArray = condition.split("|");
	// for (String cond : conditionArray) {
	// condition.replace(cond, valueMap.get(cond));
	// }
	// expression = condition.replace("|", "");
	// if (!ScriptUtil.ExecuteStringScript(expression)) {
	// result = false;
	// }
	// }
	// } else {
	// result = false;
	// }
	// } else {
	// result = false;
	// }
	// } else {
	// result = false;
	// }
	// return result;
	// }

	private boolean findStepStatus(List<SpWorkNode> nodeList){
		int passNum = 0;
		int count = 0;
		for (SpWorkNode node : nodeList) {
			
			//会签节点需要统计 (会签节点次数/完成次数)
			if(NodeTypeEnum.COUNTERSIGN_NODE.getKey().equals(node.getNodeType())){
				count++;
				if(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey().equals(node.getEstatus())){
					//通过的节点需要去检测任务完成情况
					passNum++;
				}
			}
			//其他情况下只要有一个节点审核完成则整体为完成。
			else{
				if(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey().equals(node.getEstatus())){
					if(WorkNodeStatusEnum.PASS_AUDITING.getKey().equals(node.getStatus())){
						//统计环节所属任务（必须执行）中未执行的任务数量
						SpWorkTask wt = new SpWorkTask();
						wt.setIsMust(TaskIsMustEnum.NO.getKey());
						wt.setWid(node.getWid());
						wt.setNodeId(node.getNodeId());
						wt.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
						int cou = spWorkTaskService.countWorkTaskForNonExecute(wt);
						if(cou<1)
							return true;
					}
				}
			}
		}
		if(count>0&&passNum == count){
			return true;
		}
		return false;
		
	}
	//查询同环节下的所有工作节点
	private List<SpWorkNode> findStepNode(SpWorkProcedure spWorkProcedure,SpWorkNode spWorkNode){
		int step = 0;
		boolean foundNode = false;
		List<SpWorkNode> spWorkNodes = new ArrayList<SpWorkNode>();
		//遍历查询 找出与spWorkNode处于同一环节的节点集合
		for (SpWorkNode workNode : spWorkProcedure.getSpWorkNodeList()) {
			if(workNode.getStep()!=step){
				if(foundNode) break;
				spWorkNodes = new ArrayList<SpWorkNode>();
				step = workNode.getStep();
			}
			if(workNode.getNodeId().equals(spWorkNode.getNodeId())){
				foundNode = true;
			}
			spWorkNodes.add(workNode);
		}
		return spWorkNodes;
	}
	/**
	 * 
	 * 私有方法描述：修改节点的执行状态
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-3-30 下午12:41:19
	 */
	private void editNodeEStatus(SpWorkTask wt) {
		/*
		 * 节点是否需要往下走
		 */
		if (null != wt && StringUtil.isNotEmpty(wt.getWid()) && StringUtil.isNotEmpty(wt.getNodeId())) {
			SpWorkNode spWorkNode = new SpWorkNode();
			spWorkNode.setNodeId(wt.getNodeId());
			spWorkNode.setWid(wt.getWid());
			SpWorkNode spWorkNode2 = spWorkNodeService.findWorkNodeByWidAndNodeId(spWorkNode);
			SpWorkProcedure spWorkProcedure = this.queryWorkFlowByWorkId(spWorkNode2.getWid());
			List<SpWorkNode> stepNodeList = this.findStepNode(spWorkProcedure, spWorkNode2);
			if(this.findStepStatus(stepNodeList)){
				changeNextNodeStatus(spWorkNode2);
			}
		}
	}

	/**
	 * 
	 * 私有方法描述：修改下一个节点的执行状态
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-4-7 上午09:39:00
	 */
	private void changeNextNodeStatus(SpWorkNode spWorkNode) {
		// 当前非结束节点，更新下一节点执行状态
		if (null != spWorkNode && null != spWorkNode.getNodeType()
				&& !NodeTypeEnum.END_NODE.getKey().equalsIgnoreCase(spWorkNode.getNodeType())) {
			SpWorkLine spWorkLine = new SpWorkLine();
			spWorkLine.setWid(spWorkNode.getWid());
			spWorkLine.setUnodeId(spWorkNode.getNodeId());
			List<SpWorkLine> spWorkLineList = spWorkLineService.findWorkLineList(spWorkLine);
			if (null != spWorkLineList && spWorkLineList.size() > 0) {
				// 根据连线，全部下一节点置为“待执行”
				for (SpWorkLine tempSpWorkLine : spWorkLineList) {
					// spWorkLine = spWorkLineList.get(0);
					SpWorkNode nextNode = new SpWorkNode();
					nextNode.setEstatus(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey());
					nextNode.setWid(spWorkNode.getWid());
					nextNode.setNodeId(tempSpWorkLine.getDnodeId());
					spWorkNodeService.editSpWorkNode(nextNode);
					// 添加代办事宜
					nextNode = spWorkNodeService.findWorkNodeByWidAndNodeId(nextNode);
					this.addPendingAffairInfo(nextNode);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zfsoft.workflow.service.ISpWorkFlowService#queryWorkTaskListSql(com
	 * .zfsoft.workflow.model.query.WorkTaskQuery)
	 */

	@Override
	public TaskListSqlResult queryWorkTaskListSql(WorkTaskQuery query) throws WorkFlowException {
		TaskListSqlResult result = new TaskListSqlResult();
		try {
			String sqlContent = "";
			String sqlStatus = "";
			String sqlTaskType = "";
			if (query != null) {
				if (StringUtil.isEmpty(query.getTaskType())) {
					throw new WorkFlowException("异常：任务类别为空！");
				} else {
					sqlTaskType = " AND wt.TASK_CODE = '" + query.getTaskType() + "' ";
				}
				if(!StringUtil.isEmpty(query.getStatus())){
					sqlStatus = " AND wt.E_STATUS = '" + query.getStatus() + "' ";
				}

				sqlContent = TaskListSqlResult.getSqlStartPart().concat(sqlStatus).concat(sqlTaskType);
				result.setSqlContent(sqlContent);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return result;
	}

	/*
	 * 
	 * 方法描述：角色字符串数组转换为SQL字符串
	 * 
	 * @param:
	 * 
	 * @return:
	 * 
	 * @version: 1.0
	 * 
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * 
	 * @since: 2013-3-23 上午11:18:21
	 */
	private String handleRole(String[] roles) {
		String roleStr = "";
		for (String role : roles) {
			roleStr += "'";
			roleStr += role;
			roleStr += "'";
			roleStr += ",";
		}
		return roleStr.substring(0, roleStr.length() - 1);
	}

	@Override
	public List<SpAuditingLog> querySpAuditingLog(String wid, String roleId) throws WorkFlowException {
		if (StringUtil.isEmpty(wid))
			return null;
		SpAuditingLog spAuditingLog = new SpAuditingLog();
		spAuditingLog.setWid(wid);
		spAuditingLog.setOrole(roleId);
		return spAuditingLogService.findAuditingLog(spAuditingLog);
	}

	@Override
	public List<SpWorkTask> judgeTaskEstatus(String taskType, List<SpWorkTask> spWorkTaskList) throws WorkFlowException {
		List<SpWorkTask> resultList = new ArrayList<SpWorkTask>();
		try {
			if (StringUtil.isEmpty(taskType)) {
				throw new WorkFlowException("异常：任务类型为空！");
			}
			if (spWorkTaskList != null && spWorkTaskList.size() > 0) {
				SpWorkTask spWorkTask = new SpWorkTask();
				for (SpWorkTask task : spWorkTaskList) {
					spWorkTask.setWid(task.getWid());
					// 发送面试通知
					if (taskType.equals(TaskNameEnum.NOTIFY_ORAL_EXAM.getKey())) {
						spWorkTask.setTaskCode(TaskNameEnum.LIST_ORAL_EXAM.getKey());
						spWorkTask.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
					}
					// 发送笔试通知
					if (taskType.equals(TaskNameEnum.NOTIFY_WRIT_EXAM.getKey())) {
						spWorkTask.setTaskCode(TaskNameEnum.LIST_WRIT_EXAM.getKey());
						spWorkTask.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
					}
					// 发送岗位实践通知
					if (taskType.equals(TaskNameEnum.NOTIFY_POST_PRACTICE.getKey())) {
						spWorkTask.setTaskCode(TaskNameEnum.LIST_POST_PRACTICE.getKey());
						spWorkTask.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
					}
					// 发送拟录用通知
					if (taskType.equals(TaskNameEnum.NOTIFY_WANT_EMPLOY.getKey())) {
						spWorkTask.setTaskCode(TaskNameEnum.LIST_WANT_EMPLOY.getKey());
						spWorkTask.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
					}
					// 生成拟录用公示
					if (taskType.equals(TaskNameEnum.EDICT_WANT_EMPLOY.getKey())) {
						spWorkTask.setTaskCode(TaskNameEnum.LIST_WANT_EMPLOY.getKey());
						spWorkTask.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
					}
					// 发送录用通知
					if (taskType.equals(TaskNameEnum.NOTIFY_EMPLOY.getKey())) {
						spWorkTask.setTaskCode(TaskNameEnum.LIST_EMPLOY.getKey());
						spWorkTask.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
					}
					// 生成录用公示
					if (taskType.equals(TaskNameEnum.EDICT_EMPLOY.getKey())) {
						spWorkTask.setTaskCode(TaskNameEnum.LIST_EMPLOY.getKey());
						spWorkTask.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
					}
					// 人才入库
					if (taskType.equals(TaskNameEnum.PERSON_ENTER_FILE.getKey())) {
						spWorkTask.setTaskCode(TaskNameEnum.LIST_EMPLOY.getKey());
						spWorkTask.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
					}
					List<SpWorkTask> list = spWorkTaskService.findWorkTaskList(spWorkTask);
					if (list.size() > 0) {
						resultList.add(task);
					}
				}
			} else {
				throw new WorkFlowException("异常：任务对象集合为空！");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return resultList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zfsoft.workflow.service.ISpWorkFlowService#addPendingAffairInfo(com
	 * .zfsoft.workflow.model.SpWorkNode)
	 */

	@Override
	public boolean addPendingAffairInfo(SpWorkNode spWorkNode) throws WorkFlowException {
		try {
			if (spWorkNode != null) {
				PendingAffairInfo pendingAffairInfo = new PendingAffairInfo();
				SpWorkProcedure spWorkProcedure = new SpWorkProcedure();
				spWorkProcedure.setWid(spWorkNode.getWid());
				spWorkProcedure.setPid(spWorkNode.getPid());
				List<SpWorkProcedure> list = spWorkProcedureService.findWorkProcedureList(spWorkProcedure);
				if (list != null && list.size() > 0) {
					spWorkProcedure = list.get(0);
				}
				pendingAffairInfo.setMenu(spWorkProcedure.getLink());
				pendingAffairInfo.setAffairName(spWorkNode.getNodeName());
				// 个人信息代办事宜
				if(spWorkProcedure.getBcode().contains(BusinessEnum.SH_GRXX.getKey())){
					spWorkProcedure.setBcode(BusinessEnum.SH_GRXX.getKey());
				}
				// 岗位申报代办事宜
				if(spWorkProcedure.getBcode().contains(BusinessEnum.DG_SBCLSH.getKey())){
					spWorkProcedure.setBcode(BusinessEnum.DG_SBCLSH.getKey());
				}
				// 通用业务流代办事宜
				if(spWorkProcedure.getBcode().contains(BusinessEnum.SH_BNSCLASS.getKey())){
					//TODO
					spWorkProcedure.setBcode(BusinessEnum.SH_BNSCLASS.getKey());
				}
				pendingAffairInfo.setAffairType(spWorkProcedure.getBcode());
				pendingAffairInfo.setStatus(0);
				pendingAffairInfo.setRoleId(spWorkNode.getRoleId());
				pendingAffairInfo.setBusinessId(spWorkNode.getWid() + "-" + spWorkNode.getNodeId());
				//pendingAffairInfo.setUserId(spWorkNode.getUserId());
				pendingAffairInfo.setDeptId(spWorkNode.getDepartmentId());
				pendingAffairService.addPendingAffairInfo(pendingAffairInfo);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zfsoft.workflow.service.ISpWorkFlowService#updatePendingAffairInfo
	 * (com.zfsoft.workflow.model.SpWorkNode)
	 */

	@Override
	public boolean updatePendingAffairInfo(SpWorkNode spWorkNode) throws WorkFlowException {
		try {
			if (spWorkNode != null) {
				PendingAffairInfo query = new PendingAffairInfo();
				String businessId = spWorkNode.getWid() + "-" + spWorkNode.getNodeId();
				query.setBusinessId(businessId);
				query.setStatus(1);
				pendingAffairService.modifyByYwId(query);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.zfsoft.workflow.service.ISpWorkFlowService#queryWorkNodeByWidAndNodeId(com.zfsoft.workflow.model.SpWorkNode)
	 */
	
	@Override
	public SpWorkNode queryWorkNodeByWidAndNodeId(String workId, String nodeId) throws DataAccessException {
		try {
			if (StringUtil.isEmpty(workId)) {
				throw new WorkFlowException("异常：工作ID为空！");
			}
			if (StringUtil.isEmpty(nodeId)) {
				throw new WorkFlowException("异常：节点ID为空！");
			}
			SpWorkNode spWorkNode = new SpWorkNode();
			spWorkNode.setWid(workId);
			spWorkNode.setNodeId(nodeId);
			return spWorkNodeService.findWorkNodeByWidAndNodeId(spWorkNode);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	public void setSpAuditingLogService(ISpAuditingLogService spAuditingLogService) {
		this.spAuditingLogService = spAuditingLogService;
	}

	public void setSpProcedureService(ISpProcedureService spProcedureService) {
		this.spProcedureService = spProcedureService;
	}

	public void setSpWorkProcedureService(ISpWorkProcedureService spWorkProcedureService) {
		this.spWorkProcedureService = spWorkProcedureService;
	}

	public void setSpWorkNodeService(ISpWorkNodeService spWorkNodeService) {
		this.spWorkNodeService = spWorkNodeService;
	}

	public void setSpWorkTaskService(ISpWorkTaskService spWorkTaskService) {
		this.spWorkTaskService = spWorkTaskService;
	}

	public void setSpWorkLineService(ISpWorkLineService spWorkLineService) {
		this.spWorkLineService = spWorkLineService;
	}

	public void setSpWorkNodeBillService(ISpWorkNodeBillService spWorkNodeBillService) {
		this.spWorkNodeBillService = spWorkNodeBillService;
	}

	public void setSpProcedureBillDao(ISpProcedureBillDao spProcedureBillDao) {
		this.spProcedureBillDao = spProcedureBillDao;
	}

	public void setSpBillExportService(ISpBillExportService spBillExportService) {
		this.spBillExportService = spBillExportService;
	}

	public void setSpBillConfigService(ISpBillConfigService spBillConfigService) {
		this.spBillConfigService = spBillConfigService;
	}

	public void setSpBillInstanceService(ISpBillInstanceService spBillInstanceService) {
		this.spBillInstanceService = spBillInstanceService;
	}

	public void setSpWorkProcedureBillDao(ISpWorkProcedureBillDao spWorkProcedureBillDao) {
		this.spWorkProcedureBillDao = spWorkProcedureBillDao;
	}

	public void setPendingAffairService(IPendingAffairService pendingAffairService) {
		this.pendingAffairService = pendingAffairService;
	}
	
	public void setSpBillDataPushRunService(ISpBillDataPushRunService spBillDataPushRunService) {
		this.spBillDataPushRunService = spBillDataPushRunService;
	}
}
