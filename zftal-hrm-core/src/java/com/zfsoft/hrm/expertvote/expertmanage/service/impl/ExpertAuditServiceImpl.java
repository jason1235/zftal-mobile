package com.zfsoft.hrm.expertvote.expertmanage.service.impl;


import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dataprivilege.util.DeptFilterUtil;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.message.entity.Message;
import com.zfsoft.hrm.message.service.svcinterface.IMessageService;
import com.zfsoft.hrm.expertvote.expertmanage.entity.ExpertAudit;
import com.zfsoft.hrm.expertvote.expertmanage.entity.ExpertDeclare;
import com.zfsoft.hrm.expertvote.expertmanage.entity.ExpertInfo;
import com.zfsoft.hrm.expertvote.expertmanage.query.ExpertAuditQuery;
import com.zfsoft.hrm.expertvote.expertmanage.service.IExpertAuditService;
import com.zfsoft.hrm.expertvote.expertmanage.service.IExpertDeclareService;
import com.zfsoft.hrm.expertvote.expertmanage.service.IExpertInfoService;
import com.zfsoft.workflow.enumobject.BusinessEnum;
import com.zfsoft.workflow.enumobject.BusinessTypeEnum;
import com.zfsoft.workflow.enumobject.NodeTypeEnum;
import com.zfsoft.workflow.enumobject.WorkNodeStatusEnum;
import com.zfsoft.workflow.model.SpAuditingLog;
import com.zfsoft.workflow.model.SpWorkNode;
import com.zfsoft.workflow.model.SpWorkProcedure;
import com.zfsoft.workflow.model.query.WorkAuditingQuery;
import com.zfsoft.workflow.model.result.NodeListSqlResult;
import com.zfsoft.workflow.service.ISpWorkFlowService;

/**
 * 
 * @author: xiaoyongjun
 * @since: 2014-3-14 上午11:53:21
 */
public class ExpertAuditServiceImpl implements IExpertAuditService {

	private ISpWorkFlowService spWorkFlowService;
	private IExpertDeclareService expertDeclareService;
	
	private IMessageService messageService;
	private IExpertInfoService expertInfoService;
	//private IDynaBeanBusiness dynaBeanBusiness;

	public PageList<ExpertDeclare> getPageList(ExpertAuditQuery query) throws Exception {
		WorkAuditingQuery auditQuery = new WorkAuditingQuery();// 设置审核流程查询参数
		auditQuery.setBusinessCode(BusinessEnum.ZJ_TJSH.getKey());
		auditQuery.setBusinessType(BusinessTypeEnum.ZWPR_TYPE.getKey());
		// session 中读取用户角色信息
		auditQuery.setRoleIdArray(SessionFactory.getUser().getJsdms().toArray(new String[0]));
		auditQuery.setStatus(query.getStatus());
		NodeListSqlResult result = spWorkFlowService.queryWorkFlowListSql(auditQuery);
		System.out.println(result.getSqlContent());
		// Map<String,Object> map =
		// spWorkFlowService.queryWorkFlowList(auditQuery);
		// String[] workIds = (String[])map.get("workIdArray");
		String express = result.getSqlContent();
		// if(workIds != null){//处理可显示业务ID
		// StringBuilder sb = new StringBuilder();
		// for(String id : workIds){
		// if(sb.length()>0){
		// sb.append(",");
		// }
		// sb.append("'");
		// sb.append(id);
		// sb.append("'");
		// }
		// express = " pr_id in("+sb.toString()+")";
		// }else{
		// express = " 1=0";
		// }
		query.setExpress(express);
		String express2 = DeptFilterUtil.getCondition("", "o.dwm");
		query.setExpress2(express2);
		// 流程状态 != 审核人的节点状态，故CLONE后清空
		ExpertAuditQuery cloneQuery = (ExpertAuditQuery) BeanUtils.cloneBean(query);
		cloneQuery.setStatus(null);
		PageList<ExpertDeclare> pageList = expertDeclareService.getPageList(cloneQuery);
		return pageList;
	}

	@Override
	public ExpertAudit getAudit(String declareId) {
		SpWorkProcedure p = spWorkFlowService.queryWorkFlowByWorkId(declareId);
		ExpertAudit audit = new ExpertAudit();
		if(p != null){
			List<SpWorkNode> list = p.getSpWorkNodeList();
			audit.setNodeList(list);
		}
		List<SpAuditingLog> logList = spWorkFlowService.querySpAuditingLog(declareId, null);
		audit.setLogList(logList);
		return audit;
	}

	@Override
	public void doPass(SpWorkNode node, String[] role) {
		SpWorkProcedure p = spWorkFlowService.queryWorkFlowByWorkId(node.getWid());
		List<SpWorkNode> list = p.getSpWorkNodeList();
		SpWorkNode lastNode = null;
		for (SpWorkNode n : list) {
			if (n.getNodeId().equals(node.getNodeId())) {
				lastNode = n;
			}
		}
		if (lastNode == null) {
			throw new RuleException("任务节点未找到或者已删除");
		}
		// 当最后一个节点为END_NODE时，更新审核任务为通过
		ExpertDeclare zjkDeclare = expertDeclareService.getById(node.getWid());
		if (lastNode.getNodeType().equals(NodeTypeEnum.END_NODE.getKey())||list.size()==1) {
			//涉及到使用新的工作流审核的，审批通过，不通过，退回本人需要发消息给申报人  by 陈成豪
			sendMessage("专家推荐审核",zjkDeclare.getSbz(),"您的专家推荐申请已经审核通过了！");
			zjkDeclare.setStatus(WorkNodeStatusEnum.PASS_AUDITING.getKey());
			// 同步，审核通过后回填专家信息到专家库
			ExpertInfo expertInfo = new ExpertInfo();
			expertInfo.setGh(zjkDeclare.getTjrgh());
			expertInfo.setType(zjkDeclare.getType());
			expertInfo.setSbz(zjkDeclare.getSbz());
			expertInfo.setDedate(new Date());
			expertInfo.setConfig_id(zjkDeclare.getConfig_id());
			expertInfo.setInstance_id(zjkDeclare.getInstance_id());
			expertInfoService.insert(expertInfo);
		} else {
			zjkDeclare.setStatus(WorkNodeStatusEnum.IN_AUDITING.getKey());
		}
		node.setStatus(WorkNodeStatusEnum.PASS_AUDITING.getKey());
		zjkDeclare.setAdudate(new Date());
		expertDeclareService.update(zjkDeclare,false);
		spWorkFlowService.doAuditingRsult(node, role, null);
	}
	
	private void sendMessage(String title, String receiver, String content){
		//发送通过邮件给申报人
		Message message = new Message();
		message.setTitle(title);
		message.setSender("system");
		message.setReceiver(receiver);
		message.setContent(content);
		messageService.save(message);
	}
/*
	*//**
	 * @param declare
	 *//*
	private void updateUserInfo(ZjkDeclare declare) {
		InfoClass clazz = InfoClassCache.getInfoClass(IOrgConstent.BASEINFO_CLASS_ID);
		DynaBean bean = new DynaBean(clazz);
		DynaBeanQuery dyQuery = new DynaBeanQuery(clazz);
		dyQuery.setExpress("gh = #{params.gh}");
		dyQuery.setParam("gh", declare.getSbz());
		List<DynaBean> dyBeans = dynaBeanBusiness.queryBeans(dyQuery);
		if (dyBeans.size() > 0) {
			bean = dyBeans.get(0);
		} else {
			throw new RuleException("用户基本信息不存在，无法同步数据");
		}
		//bean.setValue(IOrgConstent.DUTY_COLUMN_NAME, declare.getPr_duty());
		//bean.setValue(IOrgConstent.PRZYJSZW_COLUMN_NAME, declare.getPr_duty());
		// TODO 遗漏问题：需要修改聘任时间（目前没有聘任时间这个字段，工资管理模块需要这个时间，否则职务津贴无法发放）
		dynaBeanBusiness.modifyRecord(bean);
	}*/

	@Override
	public void doReject(SpWorkNode node, String[] role) {
		// 更新任务状态为不通过
		ExpertDeclare declare = expertDeclareService.getById(node.getWid());
		declare.setStatus(WorkNodeStatusEnum.NO_PASS_AUDITING.getKey());
		declare.setAdudate(new Date());
		expertDeclareService.update(declare,false);
		//涉及到使用新的工作流审核的，审批通过，不通过，退回本人需要发消息给申报人  by 陈成豪
		sendMessage("专家推荐审核",declare.getSbz(),"您的专家推荐审核不通过！");
		node.setStatus(WorkNodeStatusEnum.NO_PASS_AUDITING.getKey());
		spWorkFlowService.doAuditingRsult(node, role, null);
	}

	@Override
	public void doBack(SpWorkNode node, String[] role, String backId) {
		if("-1".equals(backId)){
			ExpertDeclare declare = expertDeclareService.getById(node.getWid());
			declare.setStatus(WorkNodeStatusEnum.INITAIL.getKey());
			declare.setAdudate(new Date());
			expertDeclareService.update(declare,false);
			//涉及到使用新的工作流审核的，审批通过，不通过，退回本人需要发消息给申报人  by 陈成豪
			sendMessage("专家推荐审核",declare.getSbz(),"您的专家推荐申请被退回！");
		}
		node.setStatus(WorkNodeStatusEnum.RETURN_AUDITING.getKey());
		spWorkFlowService.doAuditingRsult(node, role, backId);
	}

	@Override
	public void doSave(SpWorkNode node, String[] role) {
		node.setStatus(WorkNodeStatusEnum.IN_AUDITING.getKey());
		spWorkFlowService.doAuditingRsult(node, role, null);
	}

	public void setSpWorkFlowService(ISpWorkFlowService spWorkFlowService) {
		this.spWorkFlowService = spWorkFlowService;
	}


	public IMessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	public void setExpertInfoService(IExpertInfoService expertInfoService) {
		this.expertInfoService = expertInfoService;
	}

	/**
	 * 设置
	 * @param expertDeclareService 
	 */
	public void setExpertDeclareService(IExpertDeclareService expertDeclareService) {
		this.expertDeclareService = expertDeclareService;
	}
}
