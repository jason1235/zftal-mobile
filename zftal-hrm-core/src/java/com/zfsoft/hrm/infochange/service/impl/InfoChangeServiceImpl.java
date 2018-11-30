package com.zfsoft.hrm.infochange.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.log.User;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dataprivilege.util.DeptFilterUtil;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.html.ViewParse;
import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanUtil;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.dybill.entity.SpBillConfig;
import com.zfsoft.hrm.dybill.entity.SpBillInstance;
import com.zfsoft.hrm.dybill.service.ISpBillCheckService;
import com.zfsoft.hrm.dybill.service.ISpBillConfigService;
import com.zfsoft.hrm.dybill.service.ISpBillExportService;
import com.zfsoft.hrm.dybill.service.ISpBillInstanceService;
import com.zfsoft.hrm.dybill.service.ISpBillStorageService;
import com.zfsoft.hrm.dybill.xml.XmlBillClass;
import com.zfsoft.hrm.dybill.xml.XmlBillClasses;
import com.zfsoft.hrm.dybill.xml.XmlBillProperty;
import com.zfsoft.hrm.dybill.xml.XmlValueClass;
import com.zfsoft.hrm.dybill.xml.XmlValueEntity;
import com.zfsoft.hrm.dybill.xml.XmlValueProperty;
import com.zfsoft.hrm.infochange.dao.IInfoChangeDao;
import com.zfsoft.hrm.infochange.entity.InfoChange;
import com.zfsoft.hrm.infochange.entity.InfoChangeAudit;
import com.zfsoft.hrm.infochange.query.InfoChangeQuery;
import com.zfsoft.hrm.infochange.service.IInfoChangeService;
import com.zfsoft.hrm.message.entity.Message;
import com.zfsoft.hrm.message.service.svcinterface.IMessageService;
import com.zfsoft.hrm.pendingAffair.service.svcinterface.IPendingAffairService;
import com.zfsoft.util.base.StringUtil;
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
 * @author Patrick Shen
 * @date 2013-6-9 下午02:22:08 
 */
public class InfoChangeServiceImpl implements IInfoChangeService {
	private IInfoChangeDao infoChangeDao;
	private ISpBusinessService spBusinessService;
	private ISpWorkFlowService spWorkFlowService;
	private ISpBillConfigService spBillConfigService;
	private ISpBillInstanceService spBillInstanceService;
	private ISpBillExportService spBillExportService;
	private ISpBillStorageService spBillStorageService;
	private ISpBillCheckService spBillCheckService;
	private IMessageService messageService;
	
	private IPendingAffairService pendingAffairService;
	
	public void setInfoChangeDao(IInfoChangeDao infoChangeDao) {
		this.infoChangeDao = infoChangeDao;
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
	public void setSpBillExportService(ISpBillExportService spBillExportService) {
		this.spBillExportService = spBillExportService;
	}
	public void setSpBillStorageService(ISpBillStorageService spBillStorageService) {
		this.spBillStorageService = spBillStorageService;
	}
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}
	
	@Override
	public InfoChange getInfoChangeById(String classId,String id){
		InfoChangeQuery query=new InfoChangeQuery();
		query.setId(id);
		InfoChange infoChange=infoChangeDao.findById(query);
		if(classId == null) classId = infoChange.getClassId();
		SpBusiness spBusiness=spBusinessService.findSpBusinessByRelDetail(classId,id);
		SpBillConfig spBillConfig=spBillConfigService.getSpBillConfigById(spBusiness.getBillId());
		SpBillInstance spBillInstance=spBillInstanceService.getSpBillInstanceById(spBusiness.getBillId(), infoChange.getBillInstanceId());
		infoChange.setInstance(spBillExportService.getValueMap(spBillConfig, spBillInstance));
		return infoChange;
	}
	
	@Override
	public void fillInstance2InfoChange(List<InfoChange> list,String classId){
		if(list==null||list.isEmpty())
			return;
		SpBusiness spBusiness=null;
		SpBillConfig spBillConfig=null;
		for (InfoChange infoChange : list) {
			spBusiness=spBusinessService.findSpBusinessByRelDetail(classId,infoChange.getId());
			spBillConfig=spBillConfigService.getSpBillConfigById(spBusiness.getBillId());
			SpBillInstance spBillInstance=spBillInstanceService.getSpBillInstanceById(spBusiness.getBillId(), infoChange.getBillInstanceId());
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
		SpBusiness spBusiness=null;
		List<InfoChange> list=infoChangeDao.findList(query);
		for (InfoChange infoChange : list) {
			spBusiness=spBusinessService.findSpBusinessByRelDetail(query.getClassId(),infoChange.getId());
			SpBillConfig spBillConfig=spBillConfigService.getSpBillConfigById(spBusiness.getBillId());
			SpBillInstance spBillInstance=spBillInstanceService.getSpBillInstanceById(spBusiness.getBillId(), infoChange.getBillInstanceId());
			infoChange.setInstance(spBillExportService.getValueMap(spBillConfig, spBillInstance));
		}
		return list;
	}
	@Override
	public InfoChange getNewInfoChange(String classId,String userId,String opType,String guid) {
		InfoChange infoChange=new InfoChange();
		SpBusiness spBusiness=spBusinessService.findSpBusinessByRelDetail(classId,null);
		SpBillInstance spBillInstance=spBillInstanceService.getNewSpBillInstanceLocal(spBusiness.getBillId(),guid);
		infoChange.setBillInstanceId(spBillInstance.getId());
		infoChange.setBillConfigId(spBusiness.getBillId());
		infoChange.setCreateDate(new Date());
		infoChange.setOpType(opType);
		infoChange.setUserId(userId);
		infoChange.setGlobalid(guid);
		infoChange.setClassId(classId);
		infoChangeDao.insert(infoChange);
		return infoChange;
	}
	
	
	@Override
	public void doCancel(String classId,String id) {
		SpBusiness spBusiness=spBusinessService.findSpBusinessByRelDetail(classId,id);
		InfoChange infoChange=getInfoChangeById(classId,id);
		if(infoChange.getStatus()==null||infoChange.getStatus()==WorkNodeStatusEnum.INITAIL
				||infoChange.getStatus()==WorkNodeStatusEnum.NO_PASS_AUDITING
				||infoChange.getStatus()==WorkNodeStatusEnum.WAIT_AUDITING){
			spBillInstanceService.removeSpBillInstance(spBusiness.getBillId(), infoChange.getBillInstanceId());
			spWorkFlowService.doCancelDeclare(infoChange.getId());
			infoChangeDao.delete(infoChange);
		}
	}
	
	@Override
	public InfoChange doReCommit(String classId,String id) {
		SpBusiness spBusiness=spBusinessService.findSpBusinessByRelDetail(classId,id);
		InfoChange infoChange=getInfoChangeById(classId,id);
		SpBillInstance spBillInstance=spBillInstanceService.getSpBillInstanceById(spBusiness.getBillId(),infoChange.getBillInstanceId());
		infoChange.setBillInstanceId(spBillInstance.getId());
		infoChange.setCreateDate(new Date());
		infoChange.setStatus(null);
		infoChangeDao.update(infoChange);
		return infoChange;
	}
	@Override
	public void doCommit(String classId,String id) {
		InfoChange query=getInfoChangeById(classId,id);
		SpBusiness spBusiness=spBusinessService.findSpBusinessByRelDetail(classId,id);
		//进行上报，开启流程
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("businessCode", spBusiness.getBcode());
		map.put("workId", query.getId());
		map.put("userId", query.getUserId());
		DynaBean selfInfo = DynaBeanUtil.getPerson(query.getUserId());
		if(selfInfo!=null){
			map.put("departmentId", (String)selfInfo.getValue("dwm"));
		}
		BaseResult res = spWorkFlowService.addSpWorkFlow(map);
		//回填流程状态
		query.setStatus(WorkNodeStatusEnum.valueOf(res.getWorkStatus()));
		
		query.setCommitDate(new Date());
		infoChangeDao.update(query);
	}
	
	@Override
	public void doPass(String classId,SpWorkNode node, String[] role) {
		SpWorkProcedure p = spWorkFlowService.queryWorkFlowByWorkId(node.getWid());
		InfoChange infoChange = getInfoChangeById(classId,node.getWid());
		SpBusiness spBusiness=spBusinessService.findSpBusinessByRelDetail(classId,infoChange.getId());
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
			message.setTitle(infoChange.getClassName()+"信息变更审核通过");
			message.setSender("system");
			message.setReceiver(infoChange.getUserId());
			message.setContent("您所发起的"+infoChange.getClassName()+"信息变更流程已经审核通过。<br/>审核意见："+node.getSuggestion());
			messageService.save(message);
		} else {
			infoChange.setStatus(WorkNodeStatusEnum.IN_AUDITING);
		}
		node.setStatus(WorkNodeStatusEnum.PASS_AUDITING.getKey());
		infoChange.setAuditDate(new Date());
		infoChangeDao.update(infoChange);
		spWorkFlowService.doAuditingRsult(node, role, null);		
		node=spWorkFlowService.queryWorkNodeByWidAndNodeId(node.getWid(), node.getNodeId());
		//最后一个节点。审核通过信息入库
		if (list.size()==1||node.getNodeType().equals(NodeTypeEnum.END_NODE.getKey())){
			spBillStorageService.hrmInstanceToStorage(spBusiness.getBillId(), infoChange.getBillInstanceId(),
					infoChange.getGlobalid(),infoChange.getOpType(),infoChange.getUserId());
		}
	}
	@Override
	public void doReject(String classId,SpWorkNode node, String[] role) {
		// 更新任务状态为不通过
		InfoChange infoChange = getInfoChangeById(classId,node.getWid());
		infoChange.setStatus(WorkNodeStatusEnum.NO_PASS_AUDITING);
		infoChange.setAuditDate(new Date());
		infoChangeDao.update(infoChange);
		Message message = new Message();
		message.setTitle("<span style='color:red'>"+infoChange.getClassName()+"信息变更审核未通过</span>");
		message.setSender("system");
		message.setReceiver(infoChange.getUserId());
		message.setContent("您所发起的"+infoChange.getClassName()+"信息变更流程未能审核通过。<br/>审核意见："+node.getSuggestion());
		messageService.save(message);
		node.setStatus(WorkNodeStatusEnum.NO_PASS_AUDITING.getKey());
		spWorkFlowService.doAuditingRsult(node, role, null);
	}
	@Override
	public void doBack(String classId,SpWorkNode node, String[] role, String backId) {
		//审批流程被退回时给申请人添加退回提醒  by陈成豪
		InfoChange infoChange = getInfoChangeById(classId,node.getWid());
		infoChange.setStatus(WorkNodeStatusEnum.NO_PASS_AUDITING);
		infoChange.setAuditDate(new Date());
		infoChangeDao.update(infoChange);
		Message message = new Message();
		message.setTitle("<span style='color:red'>"+infoChange.getClassName()+"信息变更被退回</span>");
		message.setSender("system");
		message.setReceiver(infoChange.getUserId());
		message.setContent("您所发起的"+infoChange.getClassName()+"信息变更流程被退回。<br/>审核意见："+node.getSuggestion());
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
				auditQuery.setBusinessType("GRXX_TYPE");
				query.setClassId(null);
			}else{
				SpBusiness spBusiness=spBusinessService.findSpBusinessByRelDetail(query.getClassId(),null);
				auditQuery.setBusinessType("GRXX_TYPE");
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
				express = " join (" + result.getSqlContent() + ") t on (info.id = t." + result.getwId() 
				+" and ( "+ result.getNodeType() +"!= '"+NodeTypeEnum.COMMIT_NODE.getKey() +"' or ov.gh = '"+ SessionFactory.getUser().getYhm() +"'))";
			} 
			String express2 = DeptFilterUtil.getCondition("ovdw", "dwm");
			if(!StringUtil.isEmpty(express2)){
				express2="and 1=1 and exists (select 1 from overall ovdw where ovdw.gh=info.user_id and "+express2+")";
			}
		
			query.setExpress2(express2);
			query.setExpress(express);
			query.setStatus(null);
		}
		
		PageList<InfoChange> pageList = infoChangeDao.getPagedList(query);
		
		
		for (InfoChange infoChange : pageList) {
			if(!StringUtils.isEmpty(auditQuery.getStatus())){
				infoChange.setStatus(WorkNodeStatusEnum.valueOf(auditQuery.getStatus()));
			}
		}
		if(!query.getOnwer()){
			query.setStatus(WorkNodeStatusEnum.valueOf(auditQuery.getStatus()));
		}
//		SpBillConfig spBillConfig;
//		SpBillInstance spBillInstance;
//		for (InfoChange infoChange : pageList) {
//			spBillConfig=spBillConfigService.getSpBillConfigById(infoChange.getBillConfigId());
//			spBillInstance=spBillInstanceService.getSpBillInstanceById(infoChange.getBillConfigId(), infoChange.getBillInstanceId());
//			infoChange.setInstance(spBillExportService.getValueMap(spBillConfig, spBillInstance));
//		}
		return pageList;
	}
	
	@Override
	public InfoChangeAudit getAudit(String classId,String id) {
		SpWorkProcedure p = spWorkFlowService.queryWorkFlowByWorkId(id);
		if(p==null){
			return null;
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
		SpBusiness spBusiness=spBusinessService.findSpBusinessByRelDetail(classId,id);
		InfoChangeQuery query=new InfoChangeQuery();
		query.setId(id);
		InfoChange infoChange=infoChangeDao.findById(query);
		SpBillInstance spBillInstance=spBillInstanceService.getSpBillInstanceById(spBusiness.getBillId(), infoChange.getBillInstanceId());
		if(spBillInstance==null) return false;
		XmlValueClass valueClass=spBillInstance.getXmlValueClasses().getValueClasses().get(0);
		if(valueClass.getValueEntities()==null||valueClass.getValueEntities().isEmpty()){
			return false;
		}
		XmlValueEntity valueEntity=valueClass.getValueEntities().get(0);
		for(XmlValueProperty valueProperty:valueEntity.getValueProperties()){
			if(valueProperty.getNewValue()!=null&&!valueProperty.getNewValue().equals(valueProperty.getValue())){
				return true;
			}
		}
		return false;
	}
	@Override
	public String getChangeString(String classId, String id) {
		String result="";
		SpBusiness spBusiness=spBusinessService.findSpBusinessByRelDetail(classId,id);
		InfoChangeQuery query=new InfoChangeQuery();
		query.setId(id);
		InfoChange infoChange=infoChangeDao.findById(query);
		SpBillInstance spBillInstance=spBillInstanceService.getSpBillInstanceById(spBusiness.getBillId(), infoChange.getBillInstanceId());
		XmlValueClass valueClass=spBillInstance.getXmlValueClasses().getValueClasses().get(0);
//		XmlValueEntity valueEntity=valueClass.getValueEntities().get(0);
		
		String name;
		int no=0;
		for (XmlValueEntity valueEntity : valueClass.getValueEntities()) {
			no++;
			if (valueClass.getValueEntities().size()>1) {
				result+=no+".<br>";
			}
			for(XmlValueProperty valueProperty:valueEntity.getValueProperties()){
				if(valueProperty.getNewValue()!=null&&!valueProperty.getNewValue().equals(valueProperty.getValue())){
					try{
					name=spBillConfigService.getXmlBillPropertyByVersion(spBusiness.getBillId(),spBillInstance.getVersion(),
							valueClass.getBillClassId(), valueProperty.getBillPropertyId()).getName();
					result+= name+"(老数据:"+valueProperty.getValue()+" 新数据:"+
					ViewParse.parse(
							spBillConfigService.getXmlBillPropertyByVersion(spBusiness.getBillId(), spBillInstance.getVersion(),
									valueClass.getBillClassId(), valueProperty.getBillPropertyId()).getInfoProperty(),
						valueProperty.getNewValue())+")<br/>";
					}catch(Exception e){
						
					}
				}
			}
		}
		return "<div>"+result+"</div>";
	}
	@Override
	public void doCancelDeclare(String classId, String id) {
		InfoChange infoChange = getInfoChangeById(classId,id);
		spWorkFlowService.doCancelDeclare(infoChange.getId());
		infoChange.setStatus(WorkNodeStatusEnum.INITAIL);
		infoChangeDao.update(infoChange);
	}
	@Override
	public void doDeleteBlankInitail(String userId, String classId) {
		
		SpBusiness spBusiness=null;
		
		//查询未指定用户、指定信息类的未提交的信息变更
		InfoChangeQuery query= new InfoChangeQuery();
		query.setUserId(userId);
		query.setClassId(classId);
		query.setStatus(WorkNodeStatusEnum.INITAIL);
		List<InfoChange> infoChanges = infoChangeDao.findList(query);
		
		
		if(infoChanges==null||infoChanges.size()==0){
			return;
		}
		//检查是否改动，如果没有，则删除
		for (InfoChange infoChange : infoChanges) {
			//是否改动
			Boolean modified=this.doCheckModify(classId, infoChange.getId());
			if(!modified){
				spBusiness=spBusinessService.findSpBusinessByRelDetail(classId,infoChange.getId());
				spBillInstanceService.removeSpBillInstance(spBusiness.getBillId(), infoChange.getBillInstanceId());
				infoChangeDao.delete(infoChange);
			}
		}
	}
	
	@Override
	public void doPassBatch(String idList, User user) {
		// TODO Auto-generated method stub
		//检测id串
		Assert.notNull(idList, "批量操作的数据ID串不能为空");
		String[] ids = idList.split(",");
		Assert.isTrue(ids.length>=2, "数据量小于2,不能批量操作");
		Date auditDate = new Date();
		for(String id : ids){
			//读取流程信息，获取审核节点
			Assert.isTrue(!StringUtils.isEmpty(id),"数据项ID不能为空");
			InfoChangeQuery query=new InfoChangeQuery();
			query.setId(id.trim());
			InfoChange infoChange=infoChangeDao.findById(query);
			InfoChangeAudit audit = getAudit(infoChange.getClassId(), infoChange.getId());
			SpWorkNode current =  audit.getCurrentNode();
			Assert.notNull(current, "没有待执行节点，流程不正确或者已经结束");
			
			//组装审核信息
			SpWorkNode node = new SpWorkNode();
			node.setAuditorId(user.getYhm());
			node.setAuditTime(auditDate);
			node.setWid(id.trim());
			node.setRoleId(user.getJsdms().get(0));
			node.setNodeId(current.getNodeId());
			node.setSuggestion("通过!");
			
			doPass(infoChange.getClassId(), node, user.getJsdms().toArray(new String[0]));
		}
	}
	public void setPendingAffairService(IPendingAffairService pendingAffairService) {
		this.pendingAffairService = pendingAffairService;
	}

}
