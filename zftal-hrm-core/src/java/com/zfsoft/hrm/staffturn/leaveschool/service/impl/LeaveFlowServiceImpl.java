package com.zfsoft.hrm.staffturn.leaveschool.service.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanUtil;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.message.business.IMessageBusiness;
import com.zfsoft.hrm.message.entity.Message;
import com.zfsoft.hrm.message.service.svcinterface.IMessageService;
import com.zfsoft.hrm.pendingAffair.entities.PendingAffairInfo;
import com.zfsoft.hrm.pendingAffair.service.svcinterface.IPendingAffairService;
import com.zfsoft.hrm.staffturn.config.IStatusUpdateConfig;
import com.zfsoft.hrm.staffturn.leaveschool.business.ILeaveFlowBusiness;
import com.zfsoft.hrm.staffturn.leaveschool.business.ILeaveProcessBusiness;
import com.zfsoft.hrm.staffturn.leaveschool.business.ILeaveStepBusiness;
import com.zfsoft.hrm.staffturn.leaveschool.entities.LeaveFlowInfo;
import com.zfsoft.hrm.staffturn.leaveschool.entities.LeaveProcess;
import com.zfsoft.hrm.staffturn.leaveschool.entities.LeaveStep;
import com.zfsoft.hrm.staffturn.leaveschool.query.LeaveFlowInfoQuery;
import com.zfsoft.hrm.staffturn.leaveschool.query.LeaveProcessQuery;
import com.zfsoft.hrm.staffturn.leaveschool.query.LeaveStepQuery;
import com.zfsoft.hrm.staffturn.leaveschool.service.svcinterface.ILeaveFlowService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.workflow.enumobject.BusinessEnum;

/** 
 * @author jinjj
 * @date 2012-8-2 上午08:03:26 
 *  
 */
public class LeaveFlowServiceImpl implements ILeaveFlowService {

	private ILeaveFlowBusiness leaveFlowBusiness;
	private IDynaBeanBusiness dynaBeanBusiness;
	private ILeaveProcessBusiness leaveProcessBusiness;
	private ILeaveStepBusiness leaveStepBusiness;
	private IMessageService messageService;
	private IMessageBusiness messageBusiness;
	private IPendingAffairService pendingAffairService;
	
	@Override
	public void save(LeaveFlowInfo info) {
		LeaveFlowInfo old = leaveFlowBusiness.getById(info.getAccountId());
		if(old != null){
			throw new RuleException("该用户已在离校流程管理中");
		}
		leaveFlowBusiness.save(info);
		String[] processDept = info.getProcessDept().split(",");
		StringBuilder sb = new StringBuilder();
		String accountName = null;
		for(int i=0;i<processDept.length;i++){//新增各部门流程
			LeaveProcess process = new LeaveProcess();
			process.setAccountId(info.getAccountId());
			process.setDeptId(processDept[i].trim());
			//读取处理人
			LeaveStepQuery query = new LeaveStepQuery();
			query.setDeptId(processDept[i].trim());
			LeaveStep step = leaveStepBusiness.getList(query).get(0);
			
			process.setOperator(step.getHandler());
			process.setStatus("0");
			leaveProcessBusiness.save(process);
			String handlerName = null;
			accountName = DynaBeanUtil.getPersonName(info.getAccountId());
			if(StringUtil.isEmpty(accountName)){
				accountName = info.getAccountId();
			}
			handlerName = DynaBeanUtil.getPersonName(step.getHandler());
			if(StringUtil.isEmpty(handlerName)){
				handlerName = step.getHandler();
			}
			String[] ghs = step.getHandlerList();
			for (String gh : ghs) {
				sendMessage("离校处理",gh,"离校人员:" + accountName + "等待处理！");
			}
			sb.append(handlerName);
			sb.append(" ");
		}
		sendMessage("离校处理",info.getAccountId(),"您正在等待:" + sb + "处理离校事宜！");
		//添加代办事宜
		insertPendingAffair(info);
	}

	private void insertPendingAffair(LeaveFlowInfo info){
		PendingAffairInfo pendingAffairInfo = new PendingAffairInfo();
		pendingAffairInfo.setMenu("N100803");
		pendingAffairInfo.setAffairName("离校处理");
		pendingAffairInfo.setStatus(0);
		pendingAffairInfo.setAffairType(BusinessEnum.YD_LXCL.getKey());
		LeaveProcessQuery query = new LeaveProcessQuery();
		query.setAccountId(info.getAccountId());
		List<LeaveProcess> list = leaveProcessBusiness.getList(query);
		for(LeaveProcess p:list){
			String[] ghs = p.getOperator().split(";");
			for (String gh : ghs) {
				pendingAffairInfo.setUserId(gh);
				pendingAffairInfo.setBusinessId(info.getAccountId()+"-"+p.getDeptId());
				pendingAffairService.addPendingAffairInfo(pendingAffairInfo);    //插入代办事宜表
			}
		}
	}
	
	@Override
	public void update(LeaveFlowInfo info) {
		LeaveProcessQuery query = new LeaveProcessQuery();
		query.setAccountId(info.getAccountId());
		query.setStatus("0");
		List<LeaveProcess> list = leaveProcessBusiness.getList(query);
		if(list.size()>0){
			throw new RuleException("该员工仍有流程未处理完成");
		}
		leaveFlowBusiness.update(info);
		//if(!IStatusUpdateConfig.LEAVE_SCHOOL_STATUS_FILTER_CODE.contains(info.getType())){
			updateUserStatus(info);
		//}
	}

	@Override
	public List<LeaveFlowInfo> getList(LeaveFlowInfoQuery query) {
		List<LeaveFlowInfo> list = leaveFlowBusiness.getPagingList(query);
		fetchOverallInfo(list);
		return list;
	}

	/** 
	 * 获取overall用户信息
	 * @param list
	 */
	private void fetchOverallInfo(List<LeaveFlowInfo> list) {
		for(int i=0;i<list.size();i++){
			LeaveFlowInfo flow = list.get(i);
			DynaBeanQuery dyQuery=new DynaBeanQuery( InfoClassCache.getOverallInfoClass() );
			dyQuery.setExpress( "gh = #{params.gh}" );
			dyQuery.setParam( "gh", flow.getAccountId() );
			List<DynaBean> dyBeans=dynaBeanBusiness.queryBeans( dyQuery );
			if(dyBeans.size()>0){
				flow.setDynaBean(dyBeans.get(0));
			}else{
				flow.setDynaBean(new DynaBean(InfoClassCache.getOverallInfoClass()));
			}
			//查询流程处理结果，没有未处理的流程，则标记为TRUE
			LeaveProcessQuery query = new LeaveProcessQuery();
			query.setAccountId(flow.getAccountId());
			query.setStatus("0");
			List<LeaveProcess> processList = leaveProcessBusiness.getList(query);
			if(processList.size()==0){
				flow.setProcessStatus(true);
			}
			list.set(i, flow);
		}
	}
	
	private void updateUserStatus(LeaveFlowInfo info){
		InfoClass clazz = InfoClassCache.getInfoClass(IStatusUpdateConfig.BASEINFO_CLASS_ID);
		DynaBean bean = new DynaBean(clazz);
		DynaBeanQuery dyQuery=new DynaBeanQuery( clazz );
		dyQuery.setExpress( "gh = #{params.gh}" );
		dyQuery.setParam( "gh", info.getAccountId() );
		List<DynaBean> dyBeans=dynaBeanBusiness.queryBeans( dyQuery );
		if(dyBeans.size()>0){
			bean = dyBeans.get(0);
		}else{
			throw new RuleException("未找到该员工基本数据");
		}
		bean.setValue(IStatusUpdateConfig.STATUS_COLUMN_NAME, IStatusUpdateConfig.LEAVE_SCHOOL_STATUS);
		dynaBeanBusiness.modifyRecord(bean);
	}
	
	@Override
	public PageList<LeaveFlowInfo> getPagingList(LeaveFlowInfoQuery query) {
		PageList<LeaveFlowInfo> list = leaveFlowBusiness.getPagingList(query);
		fetchOverallInfo(list);
		return list;
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
	
	@Override
	public void sendMessage(String receiver) {
		Message message = buildMessage(receiver);
		message.setReceiver(receiver);
		messageBusiness.save(message);
	}

	private Message buildMessage(String receiver){
		Message message = new Message();
		message.setSender("system");
		message.setTitle("人员离校提醒");
		message.setContent("员工:[code]"+receiver+"[:code] 有离校工作需要办理，请注意处理");
		return message;
	}
	
	@Override
	public LeaveFlowInfo getById(String accountId) {
		return leaveFlowBusiness.getById(accountId);
	}


	@Override
	public void getGhBYDwm(StringBuilder sb) {
		// TODO Auto-generated method stub
		
	}
	
	public void setLeaveFlowBusiness(ILeaveFlowBusiness leaveFlowBusiness) {
		this.leaveFlowBusiness = leaveFlowBusiness;
	}

	public void setDynaBeanBusiness(IDynaBeanBusiness dynaBeanBusiness) {
		this.dynaBeanBusiness = dynaBeanBusiness;
	}

	public void setLeaveProcessBusiness(ILeaveProcessBusiness leaveProcessBusiness) {
		this.leaveProcessBusiness = leaveProcessBusiness;
	}

	public void setLeaveStepBusiness(ILeaveStepBusiness leaveStepBusiness) {
		this.leaveStepBusiness = leaveStepBusiness;
	}

	/**
	 * @return the messageService
	 */
	public IMessageService getMessageService() {
		return messageService;
	}

	/**
	 * @param messageService the messageService to set
	 */
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	public void setMessageBusiness(IMessageBusiness messageBusiness) {
		this.messageBusiness = messageBusiness;
	}

	public void setPendingAffairService(IPendingAffairService pendingAffairService) {
		this.pendingAffairService = pendingAffairService;
	}
}
