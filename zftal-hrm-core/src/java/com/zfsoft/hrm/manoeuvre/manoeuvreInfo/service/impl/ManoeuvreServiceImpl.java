package com.zfsoft.hrm.manoeuvre.manoeuvreInfo.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.org.config.IOrgConstent;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.manoeuvre.configInfo.business.businessinterface.IAuditConfigurationBusiness;
import com.zfsoft.hrm.manoeuvre.configInfo.business.businessinterface.IAuditStatusBusiness;
import com.zfsoft.hrm.manoeuvre.configInfo.business.businessinterface.ITaskNodeBusiness;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditConfigOrgInfo;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditConfiguration;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditStatus;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.TaskNode;
import com.zfsoft.hrm.manoeuvre.configInfo.query.AuditConfigurationQuery;
import com.zfsoft.hrm.manoeuvre.exception.ManoeuvreException;
import com.zfsoft.hrm.manoeuvre.manoeuvreInfo.business.businessinterface.IManoeuvreBusiness;
import com.zfsoft.hrm.manoeuvre.manoeuvreInfo.entities.ManoeuvreInfo;
import com.zfsoft.hrm.manoeuvre.manoeuvreInfo.query.ManoeuvreQuery;
import com.zfsoft.hrm.manoeuvre.manoeuvreInfo.service.svcinterface.IManoeuvreService;
import com.zfsoft.hrm.pendingAffair.entities.PendingAffairInfo;
import com.zfsoft.hrm.pendingAffair.service.svcinterface.IPendingAffairService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.date.TimeUtil;
import com.zfsoft.workflow.enumobject.BusinessEnum;

public class ManoeuvreServiceImpl implements IManoeuvreService {
	
	private IManoeuvreBusiness manoeuvreBusiness;
	
	private ITaskNodeBusiness taskNodeBusiness;
	
	private IAuditConfigurationBusiness auditConfigurationBusiness;
	
	private IDynaBeanBusiness dynaBeanBusiness;
	
	private IAuditStatusBusiness auditStatusBusiness;
	
	private IPendingAffairService pendingAffairService;
	
	public void setManoeuvreBusiness(IManoeuvreBusiness manoeuvreBusiness) {
		this.manoeuvreBusiness = manoeuvreBusiness;
	}
	
	public void setAuditConfigurationBusiness(
			IAuditConfigurationBusiness auditConfigurationBusiness) {
		this.auditConfigurationBusiness = auditConfigurationBusiness;
	}
	
	public void setAuditStatusBusiness(IAuditStatusBusiness auditStatusBusiness) {
		this.auditStatusBusiness = auditStatusBusiness;
	}
	
	public void setTaskNodeBusiness(ITaskNodeBusiness taskNodeBusiness) {
		this.taskNodeBusiness = taskNodeBusiness;
	}
	
	
	//-------------------------------------------------------------------------------

	@Override
	public boolean add(ManoeuvreInfo info) {
		Assert.notNull(info, "新增人员调配信息不可为空");
		//如果是部门岗位维护变更，不是走申请流程，直接同步个人基本信息。 by陈成豪
		if(info.isCreatedByHR()){
			changeInfoByValues(info);
		}
		return manoeuvreBusiness.add(info);
	}

	@Override
	public ManoeuvreInfo getById(String id) {
		Assert.isTrue(!StringUtil.isEmpty(id), "未选定任何记录");
		return manoeuvreBusiness.getById(id);
	}

	@Override
	public List<ManoeuvreInfo> getList(ManoeuvreQuery query) {
		Assert.notNull(query, "查询信息不可为空");
		return manoeuvreBusiness.getList(query);
	}

	@Override
	public PageList<ManoeuvreInfo> getPageList(ManoeuvreQuery query) {
		if(query == null){
			query = new ManoeuvreQuery();
		}
		return manoeuvreBusiness.getPagingList(query);
	}

	@Override
	public boolean modify(ManoeuvreInfo info) {
		Assert.notNull(info, "新增人员调配信息不可为空");
		//如果是部门岗位维护变更，不是走申请流程，直接同步个人基本信息。 by陈成豪
		if(info.isCreatedByHR()&&!StringUtil.isEmpty(info.getStaffid())){
			changeInfoByValues(info);
		}
		return manoeuvreBusiness.modify(info);
	}

	@Override
	public boolean modifyCurrentTask(ManoeuvreInfo info) {
		Assert.notNull(info, "人员调配信息不可为空");
		info.setCurrentNode(getNextNode(info.getCurrentNode()));
		//添加代办事宜
		insertPendingAffair(info);
		return manoeuvreBusiness.modifyCurrentTask(info);
	}

	private void insertPendingAffair(ManoeuvreInfo info){
		PendingAffairInfo pendingAffairInfo = new PendingAffairInfo();
		pendingAffairInfo.setMenu("N100603");
		pendingAffairInfo.setAffairName(info.getCurrentNode().getNodeName());
		pendingAffairInfo.setStatus(0);
		pendingAffairInfo.setAffairType(BusinessEnum.YD_DDSH.getKey());	
		
		List<AuditConfiguration> auditList = auditConfigurationBusiness.getListById(info.getCurrentNode().getNid());
		for (AuditConfiguration configuration : auditList) {
			pendingAffairInfo.setUserId(configuration.getAssessor());
			/**
			 * 2014.10.09 设置待办事宜记录时判断一下部门
			 */
			boolean notInAudit = true;
			
			for(AuditConfigOrgInfo i:configuration.getAuditConfigOrgList()){
				//非调入审核（0）判断调出部门（原部门）
				if(!"0".equals(configuration.getExtensionType())){
					if(i.getOid().equals(info.getCurrentOrg())){
						notInAudit=false;
						break;
					}
				}
				//非调出审核（1）判断调入部门
				if(!"1".equals(configuration.getExtensionType())){
					if(i.getOid().equals(info.getPlanOrg())){
						notInAudit=false;
						break;
					}
				}
			}
			if(notInAudit) continue;
				
			pendingAffairInfo.setBusinessId(info.getGuid()+"-"+configuration.getTaskNode().getNid());
			pendingAffairService.addPendingAffairInfo(pendingAffairInfo);    //插入代办事宜表
		}
	}
	
	@Override
	public void remove(String id) {
		Assert.isTrue(!StringUtil.isEmpty(id), "未选定任何记录");
		manoeuvreBusiness.remove(id);
	}
	
	private void auditValidate(ManoeuvreInfo info, AuditStatus auditStatus){
		Assert.notNull(info,"审核信息不可为空");
		Assert.notNull(auditStatus,"审核状态信息不可为空");
		Assert.notNull(info.getCurrentNode(),"审核信息未提交");
		Assert.notNull(info.getCurrentNode().getNid(),"审核信息未提交");
		Assert.isTrue(!info.isFinishAudit(), "该信息已审核，不可再审");
	}
	
	private void initAuditStatusInfo(ManoeuvreInfo info, AuditStatus auditStatus){
		auditStatus.setAuditTime(new Date());
		auditStatus.setManoeuvreInfo(info);
		auditStatus.setTaskNodeName(info.getCurrentNode().getNodeName());
		auditStatusBusiness.add(auditStatus);
	}
	
	/**
	 * 获得指定环节节点的后一节点
	 * @param taskNode
	 * @param trainType
	 * @return
	 */
	private TaskNode getNextNode(TaskNode taskNode){
		if(taskNode == null || StringUtil.isEmpty(taskNode.getNid())){
			return taskNodeBusiness.getFirstNode();
		}
		return taskNodeBusiness.getNext(taskNode.getNid());
	}
	
	/**
	 * 获得指定环节节点的前一节点
	 * @param taskNode
	 * @param trainType
	 * @return
	 */
	private TaskNode getPrevNode(TaskNode taskNode){
		if(taskNode == null || StringUtil.isEmpty(taskNode.getNid())){
			return taskNodeBusiness.getLastNode();
		}
		return taskNodeBusiness.getPrev(taskNode.getNid());
	}
	
	@Override
	public void doAudit(ManoeuvreInfo info, AuditStatus auditStatus) throws ManoeuvreException {
		try{
			auditValidate(info, auditStatus);
			initAuditStatusInfo(info, auditStatus);
			TaskNode current = taskNodeBusiness.getById(info.getCurrentNode().getNid());
			//审核结果为否决时，直接结束审核流程
			if(AuditStatus.RESULT_UNPASS.equals(auditStatus.getResult())){
				info.setFinishAudit(true);
				info.setAuditResult(AuditStatus.FINALRESULT_UNPASS);
				updatePendingAffair(info,current);
				manoeuvreBusiness.modify(info);
				return;
			}
			
			Assert.notNull(current, "审核出错,无法找到当前节点");
			TaskNode nextNode = getNextNode(current);
			TaskNode prevNode = getPrevNode(current);
			
			//审核结果为通过，且当前环节为最终环节时，结束审核流程
			if(AuditStatus.RESULT_PASS.equals(auditStatus.getResult()) && nextNode == null){
				info.setFinishAudit(true);
				info.setAuditResult(AuditStatus.FINALRESULT_PASS);
				changeInfo(info);
				//更改当前代办事宜状态
				updatePendingAffair(info,current);
				manoeuvreBusiness.modify(info);
			}
			//审核结果为通过，且当前环节不是最终环节，环节下移
			else if(AuditStatus.RESULT_PASS.equals(auditStatus.getResult()) && nextNode != null){
				info.setCurrentNode(nextNode);
				//更改当前代办事宜状态
				updatePendingAffair(info,current);
				//插入新的代办事宜
				insertPendingAffair(info);
				manoeuvreBusiness.modifyCurrentTask(info);
			}
			//审核结果为退回时，环节上移
			else if(AuditStatus.RESULT_RETREAD.equals(auditStatus.getResult())){
				info.setCurrentNode(prevNode);
				//更改当前代办事宜状态
				updatePendingAffair(info,current);
				//插入新的代办事宜
				insertPendingAffair(info);
				manoeuvreBusiness.modifyCurrentTask(info);
			}
			else{
				throw new ManoeuvreException("未找到指定审核结果");
			}
		}catch(IllegalArgumentException e){
			throw new ManoeuvreException("参数异常",e);
		}catch(RuntimeException e){
			throw new ManoeuvreException("审核失败",e);
		}
	}
	
	private void updatePendingAffair(ManoeuvreInfo info,TaskNode current){
		PendingAffairInfo query = new PendingAffairInfo();
		String businessId = info.getGuid()+"-"+current.getNid();
		query.setBusinessId(businessId);
		query.setStatus(1);
		pendingAffairService.modifyByYwId(query);		
	}
	
	@Override
	public List<AuditConfiguration> getAuditConfigurationList(String staffid) throws ManoeuvreException {
		
		try{
			Assert.notNull(staffid,"审核人不可为空");
			AuditConfigurationQuery auditConfigurationQuery = new AuditConfigurationQuery();
			auditConfigurationQuery.setAssessor(staffid);
			List<AuditConfiguration> auditConfigInfoList = auditConfigurationBusiness.getList(auditConfigurationQuery);
			return auditConfigInfoList;
		}catch(IllegalArgumentException e){
			throw new ManoeuvreException("参数异常",e);
		}catch(RuntimeException e){
			throw new ManoeuvreException("审核失败",e);
		}
	}
	private void changeInfo(ManoeuvreInfo info){
		info.getPlanOrg();//调入部门
//		info.getPlanPost();//调入岗位
//		info.getPlanPostType();//调入岗位类别
		insertUserChangeInfo(info);
		insertDeptChangeInfo(info);
		
		InfoClass clazz = InfoClassCache.getInfoClass(IOrgConstent.BASEINFO_CLASS_ID);
		DynaBean bean = new DynaBean(clazz);
		DynaBeanQuery dyQuery = new DynaBeanQuery(clazz);
		dyQuery.setExpress("gh = #{params.gh}");
		dyQuery.setParam("gh", info.getStaffid());
		List<DynaBean> dyBeans = dynaBeanBusiness.queryBeans(dyQuery);
		if (dyBeans.size() > 0) {
			bean = dyBeans.get(0);
		} else {
			throw new RuleException("用户基本信息不存在，无法同步数据");
		}
		if("02".equals(info.getDdlx())){
			bean.setValue(IOrgConstent.ORG_COLUMN_JRBM, info.getPlanOrg());
		}else{
			bean.setValue(IOrgConstent.ORG_COLUMN_NAME, info.getPlanOrg());
			bean.setValue("rzgwm", info.getPlanPost());
			bean.setValue("gwlbm", info.getPlanPostType());
			//如果编织类别数据非空，则同步个人基本信息数据添加该字段信息   by陈成豪
			if(info.getFormationType()!=null&&!info.getFormationType().equals("")){
				bean.setValue("bzlbm", info.getFormationType());
			}
		}
		
//		bean.setValue(IOrgConstent.PRZYJSZW_COLUMN_NAME, info.getPlanPost());
		dynaBeanBusiness.modifyRecord(bean);
	}
	
	private void changeInfoByValues(ManoeuvreInfo info){
		info.getPlanOrg();//调入部门
//		info.getPlanPost();//调入岗位
//		info.getPlanPostType();//调入岗位类别
		insertUserChangeInfo(info);
		insertDeptChangeInfo(info);
		
		InfoClass clazz = InfoClassCache.getInfoClass(IOrgConstent.BASEINFO_CLASS_ID);
		DynaBean bean = new DynaBean(clazz);
		DynaBeanQuery dyQuery = new DynaBeanQuery(clazz);
		dyQuery.setExpress("gh = #{params.gh}");
		dyQuery.setParam("gh", info.getStaffid());
		List<DynaBean> dyBeans = dynaBeanBusiness.queryBeans(dyQuery);
		if (dyBeans.size() > 0) {
			bean = dyBeans.get(0);
		} else {
			throw new RuleException("用户基本信息不存在，无法同步数据");
		}
		Map<String, Object> values = new HashMap<String, Object>();
		values.put(IOrgConstent.ORG_COLUMN_NAME, info.getPlanOrg());
		values.put("rzgwm", info.getPlanPost());
		values.put("gwlbm", info.getPlanPostType());
		//如果编织类别数据非空，则同步个人基本信息数据添加该字段信息   by陈成豪
		if(info.getFormationType()!=null&&!info.getFormationType().equals("")){
			values.put("bzlbm", info.getFormationType());
		}
//		bean.setValue(IOrgConstent.PRZYJSZW_COLUMN_NAME, info.getPlanPost());
		dynaBeanBusiness.modifyRecord(bean, values, false);
	}
	
	
	private void insertUserChangeInfo(ManoeuvreInfo info){
		try{
			InfoClass clazz = InfoClassCache.getInfoClass("E48C41329CF748F88154339072F4F07E");
			DynaBean bean = new DynaBean(clazz);
			bean.setValue("bdlx", info.getManoeuvreType());
			bean.setValue("gh", info.getStaffid());
			bean.setValue("bdyy", info.getReason());
			bean.setValue("bdsj", TimeUtil.getNowTimestamp());
			dynaBeanBusiness.addBean(bean);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void insertDeptChangeInfo(ManoeuvreInfo info){
		try{
			InfoClass clazz = InfoClassCache.getInfoClass("D4A4148AE4AAD584E040007F0100101C");
			DynaBean bean = new DynaBean(clazz);
			bean.setValue("bdlx", info.getManoeuvreType());
			bean.setValue("gh", info.getStaffid());
			bean.setValue("BMDDYY", info.getReason());
			bean.setValue("BMDDRQ", TimeUtil.getNowTimestamp());
			bean.setValue("ZZNDRBMH", info.getPlanOrg());
			bean.setValue("ZZNDCBMH", info.getCurrentOrg());
			bean.setValue("YBMGWLB", info.getPersonInfo().getValue("gwlb"));
			bean.setValue("DRGWLB", info.getPlanPostType());
			bean.setValue("YBMGW", info.getPersonInfo().getValue("rzgwm"));
			bean.setValue("DRGW", info.getPlanPost());
			bean.setValue("BGLB", info.getManoeuvreType());
			bean.setValue("ddlx", info.getDdlx());
			dynaBeanBusiness.addBean(bean);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 设置
	 * @param dynaBeanBusiness 
	 */
	public void setDynaBeanBusiness(IDynaBeanBusiness dynaBeanBusiness) {
		this.dynaBeanBusiness = dynaBeanBusiness;
	}

	@Override
	public boolean save(ManoeuvreInfo info) {
		Assert.notNull(info, "新增人员调配信息不可为空");
		return manoeuvreBusiness.save(info);
	}

	public void setPendingAffairService(IPendingAffairService pendingAffairService) {
		this.pendingAffairService = pendingAffairService;
	}	
}
