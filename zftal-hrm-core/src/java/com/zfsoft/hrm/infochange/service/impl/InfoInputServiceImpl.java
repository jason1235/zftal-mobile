package com.zfsoft.hrm.infochange.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dataprivilege.util.DeptFilterUtil;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.html.ViewParse;
import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanUtil;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.dybill.entity.SpBillConfig;
import com.zfsoft.hrm.dybill.entity.SpBillInstance;
import com.zfsoft.hrm.dybill.service.ISpBillCheckService;
import com.zfsoft.hrm.dybill.service.ISpBillConfigService;
import com.zfsoft.hrm.dybill.service.ISpBillExportService;
import com.zfsoft.hrm.dybill.service.ISpBillInstanceLogService;
import com.zfsoft.hrm.dybill.service.ISpBillInstanceService;
import com.zfsoft.hrm.dybill.service.ISpBillStorageService;
import com.zfsoft.hrm.dybill.xml.XmlBillClass;
import com.zfsoft.hrm.dybill.xml.XmlBillClasses;
import com.zfsoft.hrm.dybill.xml.XmlBillProperty;
import com.zfsoft.hrm.dybill.xml.XmlValueClass;
import com.zfsoft.hrm.dybill.xml.XmlValueEntity;
import com.zfsoft.hrm.dybill.xml.XmlValueProperty;
import com.zfsoft.hrm.infochange.dao.IInfoInputDao;
import com.zfsoft.hrm.infochange.entity.InfoChange;
import com.zfsoft.hrm.infochange.entity.InfoChangeAudit;
import com.zfsoft.hrm.infochange.query.InfoChangeQuery;
import com.zfsoft.hrm.infochange.service.IInfoInputService;
import com.zfsoft.hrm.message.entity.Message;
import com.zfsoft.hrm.message.service.svcinterface.IMessageService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.date.TimeUtil;
import com.zfsoft.workflow.enumobject.BusinessEnum;
import com.zfsoft.workflow.enumobject.BusinessTypeEnum;
import com.zfsoft.workflow.enumobject.NodeTypeEnum;
import com.zfsoft.workflow.enumobject.WorkNodeEStatusEnum;
import com.zfsoft.workflow.enumobject.WorkNodeStatusEnum;
import com.zfsoft.workflow.exception.WorkFlowException;
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
 * @date 2013-8-26
 * @version V1.0.0
 */
public class InfoInputServiceImpl implements IInfoInputService {
	protected final Log    log = LogFactory.getLog(getClass());
	
	private IInfoInputDao infoInputDao;
	private ISpBusinessService spBusinessService;
	private ISpWorkFlowService spWorkFlowService;
	private ISpBillConfigService spBillConfigService;
	private ISpBillInstanceService spBillInstanceService;
	private ISpBillExportService spBillExportService;
	private ISpBillStorageService spBillStorageService;
	private ISpBillCheckService spBillCheckService;
	private IMessageService messageService;
	private IDynaBeanBusiness dynaBeanBusiness;
	private ISpBillInstanceLogService spBillInstanceLogService;
	
	public void setInfoInputDao(IInfoInputDao infoInputDao) {
		this.infoInputDao = infoInputDao;
	}
	public void setSpBillCheckService(ISpBillCheckService spBillCheckService) {
		this.spBillCheckService = spBillCheckService;
	}
	public void setSpBusinessService(ISpBusinessService spBusinessService) {
		this.spBusinessService = spBusinessService;
	}
	public void setSpWorkFlowService(ISpWorkFlowService spWorkFlowService) {
		this.spWorkFlowService = spWorkFlowService;
	}
	public void setSpBillConfigService(ISpBillConfigService spBillConfigService) {
		this.spBillConfigService = spBillConfigService;
	}
	public void setSpBillInstanceService(
			ISpBillInstanceService spBillInstanceService) {
		this.spBillInstanceService = spBillInstanceService;
	}
	public void setSpBillInstanceLogService(
			ISpBillInstanceLogService spBillInstanceLogService) {
		this.spBillInstanceLogService = spBillInstanceLogService;
	}
	public void setSpBillExportService(ISpBillExportService spBillExportService) {
		this.spBillExportService = spBillExportService;
	}
	public void setSpBillStorageService(ISpBillStorageService spBillStorageService) {
		this.spBillStorageService = spBillStorageService;
	}
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}
	public void setDynaBeanBusiness(IDynaBeanBusiness dynaBeanBusiness){
		this.dynaBeanBusiness = dynaBeanBusiness;
	}
	
	@Override
	public InfoChange getInfoChangeById(String classId,String id){
		SpBusiness spBusiness=this.findSpBusinessByRelDetail(classId,id);
		InfoChangeQuery query=new InfoChangeQuery();
		query.setId(id);
		InfoChange infoChange=infoInputDao.findById(query);
		SpBillInstance spBillInstance=spBillInstanceService.getSpBillInstanceById(spBusiness.getBillId(), infoChange.getBillInstanceId());
		SpBillConfig spBillConfig=spBillConfigService.getSpBillConfigByVersion(spBusiness.getBillId(),spBillInstance.getVersion());
		infoChange.setInstance(spBillExportService.getValueMap(spBillConfig, spBillInstance));
		return infoChange;
	}
	
	@Override
	public void fillInstance2InfoChange(List<InfoChange> list,String classId){
		if(list==null||list.isEmpty())
			return;
		
		for (InfoChange infoChange : list) {
			SpBusiness spBusiness=this.findSpBusinessByRelDetail(classId,infoChange.getId());
			SpBillInstance spBillInstance=spBillInstanceService.getSpBillInstanceById(spBusiness.getBillId(), infoChange.getBillInstanceId());
			SpBillConfig spBillConfig = null;
			if(spBillInstance != null){
				spBillConfig=spBillConfigService.getSpBillConfigByVersion(spBusiness.getBillId(), spBillInstance.getVersion());
			}			
			infoChange.setInstance(getMap(spBillConfig, spBillInstance));
		}
	}
	//解析获取instance的Map（因为信息类变更进行过特殊处理， 不能采用通用方式获取value， 需要获取newValue）
	private Map<String, String> getMap(SpBillConfig spBillConfig,SpBillInstance spBillInstance){
		if(spBillInstance==null){
			return new HashMap<String, String>();
		}
		Map<String, String> valueMap=new HashMap<String, String>();
		XmlBillClasses xmlBillClasses=spBillConfig.getXmlBillClasses();
		XmlBillClass xmlBillClass;
		String identityName;
		String fieldName;
		for (XmlValueClass xmlValueClass : 
				spBillInstance.getXmlValueClasses().getValueClasses()) {
			xmlBillClass=xmlBillClasses.getBillClassById(xmlValueClass.getBillClassId());
			if(xmlBillClass == null){//排除修改表单模板后，实例对象中的历史数据，查询表单类为空的问题
				continue;
			}
			xmlValueClass.setXmlBillClass(xmlBillClass);
			List<XmlValueEntity> xmlValueEntityList=xmlValueClass.getLastValueEntity();
			if(xmlValueEntityList.size()==0){
				continue;
			}
			identityName=xmlBillClass.getIdentityName();
			for (XmlValueEntity xmlValueEntity : xmlValueEntityList) {
				if(xmlValueEntity.getValueProperties()==null){
					continue;
				}
				for (XmlValueProperty xmlValueProperty : xmlValueEntity.getValueProperties()) {
					XmlBillProperty property = xmlBillClass.getBillPropertyById(xmlValueProperty.getBillPropertyId());
					if(property ==null){
						continue;
					}
					fieldName=property.getFieldName();
					if("CODE".equals(property.getFieldType())){
						String value = CodeUtil.getItemValue(property.getCodeId(), xmlValueProperty.getNewValue());
						String key = StringUtils.lowerCase(identityName+"."+fieldName);
						if(StringUtil.isEmpty(valueMap.get(key))){
							valueMap.put(key, value);
						}
					}
					else{
						String key = StringUtils.lowerCase(identityName+"."+fieldName);
						if(StringUtil.isEmpty(valueMap.get(key))){
							valueMap.put(key, xmlValueProperty.getNewValue());
						}
					}
				}
			}
			
		}
		return valueMap;
	}
	
	@Override
	public List<InfoChange> getInfoChangeList(InfoChangeQuery query) {
		SpBusiness spBusiness=this.findSpBusinessByRelDetail(query.getClassId(),null);
		List<InfoChange> list=infoInputDao.findList(query);
		for (InfoChange infoChange : list) {
			SpBillInstance spBillInstance=spBillInstanceService.getSpBillInstanceById(spBusiness.getBillId(), infoChange.getBillInstanceId());
			SpBillConfig spBillConfig=spBillConfigService.getSpBillConfigByVersion(spBusiness.getBillId(), spBillInstance.getVersion());
			infoChange.setInstance(spBillExportService.getValueMap(spBillConfig, spBillInstance));
		}
		return list;
	}
	@Override
	public InfoChange getNewInfoChange(String classId,String userId,String opType,String guid) {
		InfoChange infoChange=new InfoChange();
		SpBusiness spBusiness=this.findSpBusinessByRelDetail(classId,null);
		SpBillInstance spBillInstance=spBillInstanceService.getNewSpBillInstanceLocal(spBusiness.getBillId(),guid);
		infoChange.setBillInstanceId(spBillInstance.getId());
		infoChange.setCreateDate(TimeUtil.getNowTimestamp());
		infoChange.setOpType(opType);
		infoChange.setUserId(userId);
		infoChange.setGlobalid(guid);
		infoChange.setClassId(classId);
		infoInputDao.insert(infoChange);
		return infoChange;
	}
	
	
	@Override
	public void doCancel(String classId,String id) {
		SpBusiness spBusiness=this.findSpBusinessByRelDetail(classId,id);
		InfoChange infoChange=getInfoChangeById(classId,id);
		if(infoChange.getStatus()==null||infoChange.getStatus()==WorkNodeStatusEnum.INITAIL
				||infoChange.getStatus()==WorkNodeStatusEnum.NO_PASS_AUDITING
				||infoChange.getStatus()==WorkNodeStatusEnum.WAIT_AUDITING){
			spBillInstanceService.removeSpBillInstance(spBusiness.getBillId(), infoChange.getBillInstanceId());
			infoInputDao.delete(infoChange);
		}
	}
	
	@Override
	public InfoChange doReCommit(String classId,String id) {
//		SpBusiness spBusiness=spBusinessService.findSpBusinessByRelDetail(classId,id);
		InfoChange infoChange=getInfoChangeById(classId,id);
		if(!WorkNodeStatusEnum.NO_PASS_AUDITING.equals(infoChange.getStatus())){
			return null;
		}
		spWorkFlowService.doCancelDeclare(id);
//		SpBillInstance spBillInstance=spBillInstanceService.getSpBillInstanceById(spBusiness.getBillId(),infoChange.getBillInstanceId());
		infoChange.setBillInstanceId(infoChange.getBillInstanceId());
		infoChange.setCreateDate(TimeUtil.getNowTimestamp());
		infoChange.setStatus(WorkNodeStatusEnum.INITAIL);
		infoInputDao.update(infoChange);
		return infoChange;
	}
	@Override
	public void modify(String classId,String id){
		SpBusiness spBusiness=this.findSpBusinessByRelDetail(classId,id);
		InfoChange infoChange=getInfoChangeById(classId,id);
		spBillStorageService.hrmInstanceToStorage(spBusiness.getBillId(), infoChange.getBillInstanceId(),
				infoChange.getGlobalid(),"modify",infoChange.getUserId());
	}
	@Override
	public void doCommit(String classId,String id) {
		InfoChange infoChange=getInfoChangeById(classId,id);
		SpBusiness spBusiness=this.findSpBusinessByRelDetail(classId,id);
		//进行上报，开启流程
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("businessCode", spBusiness.getBcode());
		map.put("workId", infoChange.getId());
		map.put("userId", infoChange.getUserId());
		DynaBean selfInfo = DynaBeanUtil.getPerson(infoChange.getUserId());
		if(selfInfo!=null){
			map.put("departmentId", (String)selfInfo.getValue("dwm"));
		}
		BaseResult res = spWorkFlowService.addSpWorkFlow(map);
		//表单修改日志初始状态记录
		SpBillInstance spBillInstance=spBillInstanceService.getSpBillInstanceById(spBusiness.getBillId(), infoChange.getBillInstanceId());
		spBillInstanceLogService.insertInstanceLog(spBillInstance, infoChange.getUserId());
		//回填流程状态
		infoChange.setStatus(WorkNodeStatusEnum.valueOf(res.getWorkStatus()));
		infoChange.setCommitDate(TimeUtil.getNowTimestamp());
		infoInputDao.update(infoChange);
	}
	
	@Override
	public void doPass(String classId,SpWorkNode node, String[] role, boolean reAudit) {
		SpBusiness spBusiness=this.findSpBusinessByRelDetail(classId,node.getWid());
		SpWorkProcedure p = spWorkFlowService.queryWorkFlowByWorkId(node.getWid());
		InfoChange infoChange = getInfoChangeById(classId,node.getWid());
		WorkNodeStatusEnum oldStatus = infoChange.getStatus();
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
			String validateMsg = spBillCheckService.intanceCheck(spBusiness.getBillId(), infoChange.getBillInstanceId(),
					lastNode.getCommitWorkBillClassesPrivilege());
			if(!StringUtils.isEmpty(validateMsg)){
				throw new RuleException(validateMsg);
			}
		}
		if (lastNode.getSpApproveWorkNodeBillList()!=null 
				&& lastNode.getSpApproveWorkNodeBillList().size()>0){
			//验证审核表单填写情况
			String validateMsg = spBillCheckService.intanceCheck(spBusiness.getBillId(), infoChange.getBillInstanceId(),
					lastNode.getApproveWorkBillClassesPrivilege());
			if(!StringUtils.isEmpty(validateMsg)){
				throw new RuleException(validateMsg);
			}
		}
		// 验证节点任务
		List<SpWorkTask> taskList = lastNode.getSpWorkTaskList();
		if (taskList != null && taskList.size() > 0) {
			for (SpWorkTask task : taskList) {
				if (task.getEstatus().equals(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey())) {
					if (true) {
						List<SpWorkTask> finishNode = new ArrayList<SpWorkTask>();
						finishNode.add(task);
						spWorkFlowService.doTaskRsult(finishNode);
					} 
				}
			}
		}
		// 当最后一个节点为END_NODE时，更新审核任务为通过
		if (list.size()==1||lastNode.getNodeType().equals(NodeTypeEnum.END_NODE.getKey())) {
			infoChange.setStatus(WorkNodeStatusEnum.PASS_AUDITING);
			//发送通过邮件给申报人
			Message message = new Message();
			message.setTitle("信息录入审核通过");
			message.setSender("system");
			message.setReceiver(infoChange.getUserId());
			message.setContent("您所发起的"+infoChange.getClassName()+"信息变更流程已经审核通过。");
			messageService.save(message);
		} else {
			infoChange.setStatus(WorkNodeStatusEnum.IN_AUDITING);
		}
		node.setStatus(WorkNodeStatusEnum.PASS_AUDITING.getKey());
		
		
		spWorkFlowService.doAuditingRsult(node, role, null);		
		node=spWorkFlowService.queryWorkNodeByWidAndNodeId(node.getWid(), node.getNodeId());
		//最后一个节点。审核通过信息入库
		if (list.size()==1||node.getNodeType().equals(NodeTypeEnum.END_NODE.getKey())){
			String opType = infoChange.getOpType();
			if(reAudit&&WorkNodeStatusEnum.PASS_AUDITING.equals(oldStatus)&&"add".equals(opType)){
				opType = "modify";
			}
			String guid = spBillStorageService.hrmInstanceToStorage(spBusiness.getBillId(), infoChange.getBillInstanceId(),
					infoChange.getGlobalid(),opType,infoChange.getUserId());
			if(StringUtil.isEmpty(infoChange.getGlobalid())){
				infoChange.setGlobalid(guid);
			}
		}
		infoChange.setAuditDate(TimeUtil.getNowTimestamp());
		infoInputDao.update(infoChange);
	}
	@Override
	public void doReject(String classId,SpWorkNode node, String[] role,boolean reAudit) {
		// 更新任务状态为不通过
		InfoChange infoChange = getInfoChangeById(classId,node.getWid());
		if(WorkNodeStatusEnum.PASS_AUDITING.equals(infoChange.getStatus())
				&&"add".equals(infoChange.getOpType())){
			InfoClass clazz = InfoClassCache.getInfoClass(infoChange.getClassId());
			DynaBean bean=new DynaBean(clazz);
			bean.setValue("globalid", infoChange.getGlobalid());
			dynaBeanBusiness.removeRecord(bean);
			infoChange.setGlobalid("");
		}
		if(reAudit){
			spWorkFlowService.doCancelAuditingRsult(node, role);
		}
		infoChange.setStatus(WorkNodeStatusEnum.NO_PASS_AUDITING);
		infoChange.setAuditDate(TimeUtil.getNowTimestamp());
		infoInputDao.update(infoChange);
		Message message = new Message();
		message.setTitle("信息录入审核未通过");
		message.setSender("system");
		message.setReceiver(infoChange.getUserId());
		message.setContent("您所发起的"+infoChange.getClassName()+"信息变更流程未能审核通过。");
		messageService.save(message);
		node.setStatus(WorkNodeStatusEnum.NO_PASS_AUDITING.getKey());
		spWorkFlowService.doAuditingRsult(node, role, null);
	}
	@Override
	public void doBack(String classId,SpWorkNode node, String[] role, String backId) {
		//涉及到使用新的工作流审核的，审批通过，不通过，退回本人需要发消息给申报人  by 陈成豪
		InfoChange infoChange = getInfoChangeById(classId,node.getWid());
		Message message = new Message();
		message.setTitle("信息录入审核退回");
		message.setSender("system");
		message.setReceiver(infoChange.getUserId());
		message.setContent("您所发起的"+infoChange.getClassName()+"信息变更流程被退回。");
		messageService.save(message);
		node.setStatus(WorkNodeStatusEnum.RETURN_AUDITING.getKey());
		spWorkFlowService.doAuditingRsult(node, role, backId);
	}
	@Override
	public void doSave(String classId,SpWorkNode node, String[] role) {
		node.setStatus(WorkNodeStatusEnum.IN_AUDITING.getKey());
		spWorkFlowService.doAuditingRsult(node, role, null);
	}
	@Override
	public PageList<InfoChange> getPagedList(InfoChangeQuery query) throws Exception {
		
		if(query.getClassId()==null||"".equals(query.getClassId())){
			query.setClassId(null);
		}
		
		WorkAuditingQuery auditQuery = new WorkAuditingQuery();// 设置审核流程查询参数
		
		if(!query.getOnwer()){
			if(query.getClassId()==null||"".equals(query.getClassId())){
				auditQuery.setBusinessType(BusinessTypeEnum.DG_TYPE.getKey());
				query.setClassId(null);
			}else{
				SpBusiness spBusiness=this.findSpBusinessByRelDetail(query.getClassId(), null);
				
				auditQuery.setBusinessType(BusinessTypeEnum.DG_TYPE.getKey());
				auditQuery.setBusinessCode(spBusiness.getBcode());
			}
	 		
			// session 中读取用户角色信息
			List<String> roleList = SessionFactory.getUser().getJsdms();
			auditQuery.setRoleIdArray(roleList.toArray(new String[roleList.size()]));
			if(query.getStatus()!=null){
				auditQuery.setStatus(query.getStatus().getKey());
			}
			
			NodeListSqlResult result = spWorkFlowService.queryWorkFlowListSql(auditQuery);
			String express ="";
			if (result != null) {// 关联上工作流程对象表
				express = " right join (" + result.getSqlContent() + ") t on (info.id = t." + result.getwId() 
				+" and ( "+ result.getNodeType() +"!= '"+NodeTypeEnum.COMMIT_NODE.getKey() +"' or ov.gh = '"+ SessionFactory.getUser().getYhm() +"'))";
			} 
			String express2 = DeptFilterUtil.getCondition("ovdw", "dwm");
			if(!StringUtil.isEmpty(express2)){
				express2="1=1 and exists (select 1 from overall ovdw where ovdw.gh=info.user_id and "+express2+") and op_type = 'add'";
			}
		
			query.setExpress2(express2);
			query.setExpress(express);
			query.setStatus(null);
		}
		
		PageList<InfoChange> pageList = infoInputDao.getPagedList(query);
		if(!query.getOnwer()&&!StringUtil.isEmpty(auditQuery.getStatus())){
			query.setStatus(WorkNodeStatusEnum.valueOf(auditQuery.getStatus()));
		}
		if(!query.getOnwer()){
			for (InfoChange infoChange : pageList) {
				//如果查询条件状态不为空 则直接以查询条件的状态当做状态使用
				if(query.getStatus()!=null){
					infoChange.setStatus(query.getStatus());
					continue;
				}
				//如果本地状态为审核通过 则直接以本地状态为准（因为结果为审核通过则意味着每个环节均审核通过）
				if(WorkNodeStatusEnum.PASS_AUDITING.equals(infoChange.getStatus())){
					continue;
				}
				//取整个流程实例对象
				SpWorkProcedure p = spWorkFlowService.queryWorkFlowByWorkId(infoChange.getId());
				if(p==null){
					continue;
				}
				String status = null;
				List<SpWorkNode> list = p.getSpWorkNodeList();
				//遍历每一个节点 取当前用户角色在流程中最后处理节点的状态
				for(SpWorkNode node:list)
				{
					//如果节点状态为close，则不继续遍历（因为close节点以及之后的节点均未被执行 遍历无意义）
					if(node.getEstatus().equals(WorkNodeEStatusEnum.CLOSE.getKey())){
						break;
					}
					//如果当前用户的角色中包含节点的执行角色，则更新状态为改节点状态
					if(SessionFactory.getUser().getJsdms().toString().indexOf(node.getRoleId())!=-1){
						status = node.getStatus();
					}
				}
				if(status != null){
					infoChange.setStatus(WorkNodeStatusEnum.valueOf(status));
				}
			}
		}
		return pageList;
	}
	
	@Override
	public InfoChangeAudit getAudit(String classId,String id) {
		SpWorkProcedure p = spWorkFlowService.queryWorkFlowByWorkId(id);
		if(p==null){
			return new InfoChangeAudit();
		}
		List<SpWorkNode> list = p.getSpWorkNodeList();
		List<SpAuditingLog> logList = spWorkFlowService.querySpAuditingLog(id, null);
		InfoChangeAudit audit = new InfoChangeAudit();
		audit.setNodeList(list);
		audit.setLogList(logList);
		return audit;
	}
	@Override
	public Boolean doCheckModify(String classId, String id) {
		SpBusiness spBusiness=this.findSpBusinessByRelDetail(classId,id);
		InfoChangeQuery query=new InfoChangeQuery();
		query.setId(id);
		InfoChange infoChange=infoInputDao.findById(query);
		SpBillInstance spBillInstance=spBillInstanceService.getSpBillInstanceById(spBusiness.getBillId(), infoChange.getBillInstanceId());
		if(spBillInstance != null && spBillInstance.getXmlValueClasses() != null 
				&& spBillInstance.getXmlValueClasses().getValueClasses() != null){
			XmlValueClass valueClass=spBillInstance.getXmlValueClasses().getValueClasses().get(0);
			if(valueClass != null){
				XmlValueEntity valueEntity=valueClass.getValueEntities().get(0);
				for(XmlValueProperty valueProperty:valueEntity.getValueProperties()){
					if(valueProperty.getNewValue()!=null&&!valueProperty.getNewValue().equals(valueProperty.getValue())){
						return true;
					}
				}
			}			
		}		
		return false;
	}
	@Override
	public String getChangeString(String classId, String id) {
		String result="";
		SpBusiness spBusiness=this.findSpBusinessByRelDetail(classId,id);
		InfoChangeQuery query=new InfoChangeQuery();
		query.setId(id);
		InfoChange infoChange=infoInputDao.findById(query);
		
		SpBillInstance spBillInstance=spBillInstanceService.getSpBillInstanceById(spBusiness.getBillId(), infoChange.getBillInstanceId());
		XmlValueClass valueClass=spBillInstance.getXmlValueClasses().getValueClasses().get(0);
		XmlValueEntity valueEntity=valueClass.getValueEntities().get(0);
		List<XmlBillProperty>  xmlBillPropertys = spBillConfigService.getXmlBillPropertyListByVersion(spBusiness.getBillId(),spBillInstance.getVersion(), valueClass.getBillClassId());
		String name;
		for(XmlBillProperty xmlBillProperty:xmlBillPropertys){
//			if(valueProperty.getNewValue()!=null&&!valueProperty.getNewValue().equals(valueProperty.getValue())){
//				try{
//				name=spBillConfigService.getXmlBillPropertyById(spBusiness.getBillId(), valueClass.getBillClassId(), valueProperty.getBillPropertyId()).getName();
//				result+= name+":"+
//				ViewParse.parse(
//						spBillConfigService.getXmlBillPropertyById(spBusiness.getBillId(), 
//								valueClass.getBillClassId(), valueProperty.getBillPropertyId()).getInfoProperty(),
//					valueProperty.getNewValue())+"<br/>";
//				}catch(Exception e){
//					
//				}
//			}
			if(xmlBillProperty.getVisable()&&xmlBillProperty.getEditable()){
				try{
					XmlValueProperty valueProperty = valueEntity.getValuePropertyById(xmlBillProperty.getId());
					name=xmlBillProperty.getName();
					result+= name+":"+
					ViewParse.parse(
							xmlBillProperty.getInfoProperty(),
						valueProperty.getNewValue())+"<br/>";
					}catch(Exception e){
						
					}
			};
		}
		return "<div>"+result+"</div>";
	}
	
	@Override
	public void doCancelDeclare(String classId, String id) {
		InfoChange infoChange = getInfoChangeById(classId,id);
		spWorkFlowService.doCancelDeclare(infoChange.getId());
		infoChange.setStatus(WorkNodeStatusEnum.INITAIL);
		infoInputDao.update(infoChange);
	}
	@Override
	public void doDeleteBlankInitail(String userId, String classId) {
		
		SpBusiness spBusiness=null;
		
		//查询未指定用户、指定信息类的未提交的信息变更
		InfoChangeQuery query= new InfoChangeQuery();
		query.setUserId(userId);
		query.setClassId(classId);
		query.setStatus(WorkNodeStatusEnum.INITAIL);
		List<InfoChange> infoChanges = infoInputDao.findList(query);
		
		
		if(infoChanges==null||infoChanges.size()==0){
			return;
		}
		//检查是否改动，如果没有，则删除
		for (InfoChange infoChange : infoChanges) {
			//是否改动
			if(infoChange != null){
				Boolean modified=this.doCheckModify(classId, infoChange.getId());
				if(!modified){
					spBusiness=this.findSpBusinessByRelDetail(classId,infoChange.getId());
					spBillInstanceService.removeSpBillInstance(spBusiness.getBillId(), infoChange.getBillInstanceId());
					infoInputDao.delete(infoChange);
				}
			}			
		}
	}
	@Override
	public SpBusiness findSpBusinessByRelDetail(String relDetail, String workId) {
		SpBusiness result=null;
		try{
			if(StringUtil.isNotEmpty(relDetail)){
				String bcode = BusinessEnum.DG_SBCLSH.getKey()+ "_" + relDetail;
				result=spBusinessService.findSpBusinessByBcode(bcode,workId);
			}
			if(result==null){
				throw new WorkFlowException("不存在记录");
			}else{
				return result;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}
	
}