package com.zfsoft.hrm.bnsinfochange.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dataprivilege.util.DeptFilterUtil;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanUtil;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.bnsinfochange.dao.IBusinessInfoChangeDao;
import com.zfsoft.hrm.bnsinfochange.entity.BusinessInfoChange;
import com.zfsoft.hrm.bnsinfochange.entity.BusinessInfoChangeAudit;
import com.zfsoft.hrm.bnsinfochange.query.BusinessInfoQuery;
import com.zfsoft.hrm.bnsinfochange.service.IBusinessInfoChangeService;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.dybill.entity.SpBillInstance;
import com.zfsoft.hrm.dybill.service.ISpBillCheckService;
import com.zfsoft.hrm.dybill.service.ISpBillConfigService;
import com.zfsoft.hrm.dybill.service.ISpBillInstanceService;
import com.zfsoft.hrm.infochange.entity.InfoChange;
import com.zfsoft.hrm.message.entity.Message;
import com.zfsoft.hrm.message.service.svcinterface.IMessageService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.date.TimeUtil;
import com.zfsoft.workflow.enumobject.BusinessEnum;
import com.zfsoft.workflow.enumobject.BusinessTypeEnum;
import com.zfsoft.workflow.enumobject.NodeTypeEnum;
import com.zfsoft.workflow.enumobject.WorkNodeEStatusEnum;
import com.zfsoft.workflow.enumobject.WorkNodeStatusEnum;
import com.zfsoft.workflow.model.SpAuditingLog;
import com.zfsoft.workflow.model.SpBusiness;
import com.zfsoft.workflow.model.SpWorkNode;
import com.zfsoft.workflow.model.SpWorkProcedure;
import com.zfsoft.workflow.model.SpWorkTask;
import com.zfsoft.workflow.model.query.WorkAuditingQuery;
import com.zfsoft.workflow.model.result.BaseResult;
import com.zfsoft.workflow.model.result.NodeListSqlResult;
import com.zfsoft.workflow.service.ISpBusinessService;
import com.zfsoft.workflow.service.ISpWorkFlowService;

/**
 * 
 * @author ChenMinming
 * @date 2014-6-17
 * @version V1.0.0
 */
public class BusinessInfoChangeServiceImpl implements
		IBusinessInfoChangeService {
	private IBusinessInfoChangeDao businessInfoChangeDao;
	private ISpBusinessService spBusinessService;
	private ISpWorkFlowService spWorkFlowService;
	private IMessageService messageService;
	private IDynaBeanBusiness dynaBeanBusiness;
	private ISpBillConfigService spBillConfigService;
	private ISpBillInstanceService spBillInstanceService;
	private ISpBillCheckService spBillCheckService;

	@Override
	public void doBack(String classId, SpWorkNode node, String[] role,
			String backId) {
		if("-1".equals(backId)){
			BusinessInfoChange businessInfoChange = getInfoChangeById(classId, node.getWid());
			businessInfoChange.setStatus(WorkNodeStatusEnum.INITAIL);
			businessInfoChange.setAuditDate(new Date());
			businessInfoChangeDao.update(businessInfoChange);
			InfoClass clazz = InfoClassCache.getInfoClass(classId);
			//发送通过邮件给申报人
			Message message = new Message();
			message.setTitle(clazz.getName()+"申请被退回");
			message.setSender("system");
			message.setReceiver(businessInfoChange.getUserId());
			message.setContent("您所发起的"+businessInfoChange.getClassName()+"申请被退回！");
			messageService.save(message);
		}
		node.setStatus(WorkNodeStatusEnum.RETURN_AUDITING.getKey());
		spWorkFlowService.doAuditingRsult(node, role, backId);

	}

	@Override
	public void doCancel(String classId, String id) {
		SpBusiness spBusiness = findSpBusiness(classId, id);
		BusinessInfoChange businessInfoChange = getInfoChangeById(classId, id);
		if (businessInfoChange == null)
			return;
		spBillInstanceService.removeSpBillInstance(spBusiness.getBillId(),
				businessInfoChange.getBillInstanceId());
		if (spBusiness.getProcedure() != null) {
			spWorkFlowService.doCancelDeclare(id);
		}
		businessInfoChangeDao.delete(businessInfoChange);
	}

	@Override
	public void doCancelDeclare(String classId, String id) {
		BusinessInfoChange subBusinessInfoChange = getInfoChangeById(classId,id);
		spWorkFlowService.doCancelDeclare(subBusinessInfoChange.getId());
		subBusinessInfoChange.setStatus(WorkNodeStatusEnum.INITAIL);
		businessInfoChangeDao.update(subBusinessInfoChange);
	}

//	@Override
//	public Boolean doCheckModify(String classId, String id) {
//		SpBusiness spBusiness = findSpBusiness(classId, id);
//		BusinessInfoChange businessInfoChange = getInfoChangeById(classId, id);
//		SpBillInstance spBillInstance = spBillInstanceService.getSpBillInstanceById(spBusiness.getBillId(),
//				businessInfoChange.getBillInstanceId());
//		XmlValueClasses xmlValueClasses = spBillInstance.getXmlValueClasses();
//		SpBillConfig spBillConfig = spBillConfigService.getSpBillConfigByVersion(spBusiness.getBillId(), spBillInstance.getVersion());
//		List<XmlBillClass> list = spBillConfig.getXmlBillClasses().getBillClasses();
//		for (XmlBillClass xmlBillClass : list) {
//			XmlValueClass valueClass = xmlValueClasses.getValueClassByClassId(xmlBillClass.getId());
//			if(valueClass==null||valueClass.getValueEntities()==null||valueClass.getValueEntities().isEmpty())
//				continue;
//			if(valueClass.getValueEntities().size()> xmlBillClass.getCatchRecordNum()){
//				return true;
//			}
//		}
//		return false;
//	}

	@Override
	public void doCommit(String classId, String id) {
		// 上报前对自定义表单数据进行校验，是否填写完整（追加信息则不需要）
		BusinessInfoChange businessInfoChange = getInfoChangeById(classId, id);
		SpBusiness spb = findSpBusiness(classId, businessInfoChange.getId());
		if (spb == null || StringUtil.isEmpty(spb.getBillId())) {
			throw new RuleException("流程表单未配置");
		}
		if(StringUtil.isEmpty(businessInfoChange.getBillConfigId())
				||StringUtil.isEmpty(businessInfoChange.getBillInstanceId())){
			SpBillInstance instance = spBillInstanceService
					.getNewSpBillInstance(spb.getBillId());
			businessInfoChange.setBillInstanceId(instance.getId());
			businessInfoChange.setBillConfigId(spb.getBillId());
		}
			String validateMsg = spBillCheckService.intanceCheck(businessInfoChange
					.getBillConfigId(), businessInfoChange.getBillInstanceId(), spb
					.getBillClassesPrivilegeString());
			if (!StringUtils.isEmpty(validateMsg)) {
				throw new RuleException(validateMsg);
			}
		// 进行上报，开启流程
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("businessCode", spb.getBcode());
		map.put("workId", businessInfoChange.getId());
		map.put("userId", businessInfoChange.getUserId());
		DynaBean selfInfo = DynaBeanUtil.getPerson(businessInfoChange.getUserId());
		if(selfInfo!=null){
			map.put("departmentId", (String)selfInfo.getValue("dwm"));
		}
		BaseResult res = spWorkFlowService.addSpWorkFlow(map);
		//回填流程状态
		businessInfoChange.setStatus(WorkNodeStatusEnum.valueOf(res.getWorkStatus()));
		businessInfoChange.setCommitDate(TimeUtil.getNowTimestamp());
		businessInfoChangeDao.update(businessInfoChange);
	}


	@Override
	public void doPass(String classId, SpWorkNode node, String[] role,
			boolean reAudit) {
		SpBusiness spBusiness=this.findSpBusiness(classId,node.getWid());
		SpWorkProcedure p = spWorkFlowService.queryWorkFlowByWorkId(node.getWid());
		BusinessInfoChange businessInfoChange = getInfoChangeById(classId,node.getWid());
		WorkNodeStatusEnum oldStatus = businessInfoChange.getStatus();
		List<SpWorkNode> list = p.getSpWorkNodeList();
		SpWorkNode lastNode = null;
		for (SpWorkNode n : list) {
			if (n.getNodeId().equals(node.getNodeId())) {
				lastNode = n;
				break;
			}
		}
		if (lastNode == null) {
			throw new RuleException("节点未找到或者已删除");
		}
		if(reAudit){
			spWorkFlowService.doCancelAuditingRsult(node, role);
		}
		if (NodeTypeEnum.COMMIT_NODE.getKey().equals(lastNode.getNodeType())){
			String validateMsg = spBillCheckService.intanceCheck(spBusiness.getBillId(), businessInfoChange.getBillInstanceId(),
					lastNode.getCommitWorkBillClassesPrivilege());
			if(!StringUtils.isEmpty(validateMsg)){
				throw new RuleException(validateMsg);
			}
		}
		if (lastNode.getSpApproveWorkNodeBillList()!=null 
				&& lastNode.getSpApproveWorkNodeBillList().size()>0){
			//验证审核表单填写情况
			String validateMsg = spBillCheckService.intanceCheck(spBusiness.getBillId(), businessInfoChange.getBillInstanceId(),
					lastNode.getApproveWorkBillClassesPrivilege());
			if(!StringUtils.isEmpty(validateMsg)){
				throw new RuleException(validateMsg);
			}
		}
		// 验证节点任务
//		List<SpWorkTask> taskList = lastNode.getSpWorkTaskList();
//		if (taskList != null && taskList.size() > 0) {
//			for (SpWorkTask task : taskList) {
//				if (task.getEstatus().equals(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey())) {
//					if (true) {
//						List<SpWorkTask> finishNode = new ArrayList<SpWorkTask>();
//						finishNode.add(task);
//						spWorkFlowService.doTaskRsult(finishNode);
//					} 
//				}
//			}
//		}
		// 当最后一个节点为END_NODE时，更新审核任务为通过
		if (list.size()==1||lastNode.getNodeType().equals(NodeTypeEnum.END_NODE.getKey())) {
			InfoClass clazz = InfoClassCache.getInfoClass(classId);
			businessInfoChange.setStatus(WorkNodeStatusEnum.PASS_AUDITING);
			//发送通过邮件给申报人
			Message message = new Message();
			message.setTitle(clazz.getName()+"申请通过");
			message.setSender("system");
			message.setReceiver(businessInfoChange.getUserId());
			message.setContent("您所发起的"+businessInfoChange.getClassName()+"申请流程已经审核通过。");
			messageService.save(message);
		} else {
			businessInfoChange.setStatus(WorkNodeStatusEnum.IN_AUDITING);
		}
		node.setStatus(WorkNodeStatusEnum.PASS_AUDITING.getKey());
		
		node.setSpBillInstanceId(businessInfoChange.getBillInstanceId());
		spWorkFlowService.doAuditingRsult(node, role, null);
		businessInfoChange.setAuditDate(TimeUtil.getNowTimestamp());
		businessInfoChangeDao.update(businessInfoChange);
	}

	@Override
	public BusinessInfoChange doReCommit(String classId, String id) {
		BusinessInfoChange businessInfoChange=getInfoChangeById(classId,id);
		if(!WorkNodeStatusEnum.NO_PASS_AUDITING.equals(businessInfoChange.getStatus())){
			return null;
		}
		spWorkFlowService.doCancelDeclare(id);
		businessInfoChange.setCreateDate(TimeUtil.getNowTimestamp());
		businessInfoChange.setStatus(WorkNodeStatusEnum.INITAIL);
		businessInfoChangeDao.update(businessInfoChange);
		return businessInfoChange;
	}

	@Override
	public void doReject(String classId, SpWorkNode node, String[] role,
			boolean reAudit) {
		// 更新任务状态为不通过
		BusinessInfoChange businessInfoChange = getInfoChangeById(classId,node.getWid());
		businessInfoChange.setStatus(WorkNodeStatusEnum.NO_PASS_AUDITING);
		businessInfoChange.setAuditDate(TimeUtil.getNowTimestamp());
		businessInfoChangeDao.update(businessInfoChange);
		Message message = new Message();
		message.setTitle(businessInfoChange.getClassName()+"申请未通过");
		message.setSender("system");
		message.setReceiver(businessInfoChange.getUserId());
		message.setContent("您所发起的"+businessInfoChange.getClassName()+"申请流程已经审核通过。");
		messageService.save(message);
		node.setStatus(WorkNodeStatusEnum.NO_PASS_AUDITING.getKey());
		spWorkFlowService.doAuditingRsult(node, role, null);

	}

	@Override
	public void doSave(String classId, SpWorkNode node, String[] role) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fillInstance2InfoChange(List<BusinessInfoChange> list,
			String classId) {
		// TODO Auto-generated method stub

	}

	@Override
	public SpBusiness findSpBusiness(String classId, String workId) {
		return spBusinessService.findSpBusinessByBcode(BusinessEnum.SH_BNSCLASS
				.getKey()
				+ "_" + classId, workId);
	}

	@Override
	public BusinessInfoChangeAudit getAudit(String classId, String id) {
		SpWorkProcedure p = spWorkFlowService.queryWorkFlowByWorkId(id);
		if(p==null){
			return new BusinessInfoChangeAudit();
		}
		List<SpWorkNode> list = p.getSpWorkNodeList();
		List<SpAuditingLog> logList = spWorkFlowService.querySpAuditingLog(id, null);
		BusinessInfoChangeAudit audit = new BusinessInfoChangeAudit();
		audit.setNodeList(list);
		audit.setLogList(logList);
		return audit;
	}

	@Override
	public BusinessInfoChange getInfoChangeById(String classId, String id) {
		BusinessInfoQuery query = new BusinessInfoQuery();
		query.setId(id);
		BusinessInfoChange infoChange = businessInfoChangeDao.findById(query);
		return infoChange;
	}

	@Override
	public BusinessInfoChange getNewInfoChange(String classId, String userId,
			String opType) {
		BusinessInfoChange businessInfoChange = new BusinessInfoChange();
		SpBusiness spBusiness = findSpBusiness(classId, null);
		SpBillInstance spBillInstance = spBillInstanceService
				.getNewSpBillInstance(spBusiness.getBillId(), userId);
		businessInfoChange.setBillInstanceId(spBillInstance.getId());
		businessInfoChange.setBillConfigId(spBusiness.getBillId());
		businessInfoChange.setCreateDate(new Date());
		businessInfoChange.setOpType(opType);
		businessInfoChange.setUserId(userId);
		businessInfoChange.setClassId(classId);
		businessInfoChangeDao.insert(businessInfoChange);
		return businessInfoChange;
	}

	@Override
	public PageList<BusinessInfoChange> getPagedList(BusinessInfoQuery query)
			throws Exception {
		if (query.getClassId() == null || "".equals(query.getClassId())) {
			query.setClassId(null);
		}

		WorkAuditingQuery auditQuery = new WorkAuditingQuery();// 设置审核流程查询参数

		if (!query.getOnwer()) {
			if (query.getClassId() == null || "".equals(query.getClassId())) {
				auditQuery.setBusinessType(BusinessTypeEnum.BUSINESSCLASS_TYPE.getKey());
				query.setClassId(null);
			} else {
				SpBusiness spBusiness = findSpBusiness(query.getClassId(), null);
				auditQuery.setBusinessType(BusinessTypeEnum.BUSINESSCLASS_TYPE.getKey());
				auditQuery.setBusinessCode(spBusiness.getBcode());
			}

			// session 中读取用户角色信息
			List<String> roleList = SessionFactory.getUser().getJsdms();
			auditQuery.setRoleIdArray(roleList.toArray(new String[roleList
					.size()]));
			if (query.getStatus() != null) {
				auditQuery.setStatus(query.getStatus().getKey());
			}

			NodeListSqlResult result = spWorkFlowService
					.queryWorkFlowListSql(auditQuery);
			String express = "";
			if (result != null) {// 关联上工作流程对象表
				express = " right join (" + result.getSqlContent()
						+ ") t on (info.id = t." + result.getwId() + " and ( "
						+ result.getNodeType() + "!= '"
						+ NodeTypeEnum.COMMIT_NODE.getKey() + "' or ov.gh = '"
						+ SessionFactory.getUser().getYhm() + "'))";
			}
			String express2 = DeptFilterUtil.getCondition("ovdw", "dwm");
			if (!StringUtil.isEmpty(express2)) {
				express2 = "and 1=1 and exists (select 1 from overall ovdw where ovdw.gh=info.user_id and "
						+ express2 + ")";
			}

			query.setExpress2(express2);
			query.setExpress(express);
			query.setStatus(null);
		}

		PageList<BusinessInfoChange> pageList = businessInfoChangeDao
				.getPagedList(query);

		for (BusinessInfoChange infoChange : pageList) {
			if (!StringUtils.isEmpty(auditQuery.getStatus())) {
				infoChange.setStatus(WorkNodeStatusEnum.valueOf(auditQuery
						.getStatus()));
			}
		}
		if ((!query.getOnwer())&&auditQuery.getStatus()!=null) {
			query.setStatus(WorkNodeStatusEnum.valueOf(auditQuery.getStatus()));
		}

		return pageList;
	}

	@Override
	public void modify(String classId, String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<BusinessInfoChange> getInfoChangeList(BusinessInfoQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 设置
	 * 
	 * @param businessInfoChangeDao
	 */
	public void setBusinessInfoChangeDao(
			IBusinessInfoChangeDao businessInfoChangeDao) {
		this.businessInfoChangeDao = businessInfoChangeDao;
	}

	/**
	 * 设置
	 * 
	 * @param spBusinessService
	 */
	public void setSpBusinessService(ISpBusinessService spBusinessService) {
		this.spBusinessService = spBusinessService;
	}

	/**
	 * 设置
	 * 
	 * @param spWorkFlowService
	 */
	public void setSpWorkFlowService(ISpWorkFlowService spWorkFlowService) {
		this.spWorkFlowService = spWorkFlowService;
	}

	/**
	 * 设置
	 * 
	 * @param messageService
	 */
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	/**
	 * 设置
	 * 
	 * @param dynaBeanBusiness
	 */
	public void setDynaBeanBusiness(IDynaBeanBusiness dynaBeanBusiness) {
		this.dynaBeanBusiness = dynaBeanBusiness;
	}

	/**
	 * 设置
	 * 
	 * @param spBillInstanceLogService
	 */
	public void setSpBillConfigService(
			ISpBillConfigService spBillConfigService) {
		this.spBillConfigService = spBillConfigService;
	}

	/**
	 * 设置
	 * 
	 * @param spBillInstanceService
	 */
	public void setSpBillInstanceService(
			ISpBillInstanceService spBillInstanceService) {
		this.spBillInstanceService = spBillInstanceService;
	}

	/**
	 * 设置
	 * @param spBillCheckService 
	 */
	public void setSpBillCheckService(ISpBillCheckService spBillCheckService) {
		this.spBillCheckService = spBillCheckService;
	}


}
