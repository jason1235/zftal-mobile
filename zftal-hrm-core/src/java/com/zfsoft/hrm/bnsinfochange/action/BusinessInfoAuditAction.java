package com.zfsoft.hrm.bnsinfochange.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.common.log.User;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dataprivilege.util.DeptFilterUtil;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.bnsinfochange.entity.BusinessInfoChange;
import com.zfsoft.hrm.bnsinfochange.entity.BusinessInfoChangeAudit;
import com.zfsoft.hrm.bnsinfochange.query.BusinessInfoQuery;
import com.zfsoft.hrm.bnsinfochange.service.IBusinessInfoChangeService;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.dybill.entity.SpBillInstanceLog;
import com.zfsoft.hrm.dybill.enums.ModeType;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.date.TimeUtil;
import com.zfsoft.workflow.enumobject.WorkNodeStatusEnum;
import com.zfsoft.workflow.model.SpBusiness;
import com.zfsoft.workflow.model.SpWorkNode;

/**
 * 
 * @author ChenMinming
 * @date 2014-6-20
 * @version V1.0.0
 */
public class BusinessInfoAuditAction extends HrmAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3443105819931170341L;
	private IBusinessInfoChangeService businessInfoChangeService;
	private PageList<BusinessInfoChange> pageList;

	private BusinessInfoChange businessInfoChange = new BusinessInfoChange();
	private BusinessInfoQuery query = new BusinessInfoQuery();
	
	private SpBusiness spBusiness;

	private SpWorkNode node;
	private String backId;
	private boolean reAudit = false;

	public String page() throws Exception {
		if(query.getStatus()==null){
			query.setStatus(WorkNodeStatusEnum.WAIT_AUDITING);
		}
		query.setOnwer(false);
		pageList = businessInfoChangeService.getPagedList(query);
		int beginIndex = pageList.getPaginator().getBeginIndex();
		pageList.setPaginator(query);
		getValueStack().set("statusArray", WorkNodeStatusEnum.values());
		getValueStack().set("beginIndex", beginIndex);
		getValueStack().set("clazz", InfoClassCache.getInfoClass(query.getClassId()));
		return "page";
	}
//	
//	public String toEdit() throws Exception{
//		spBusiness=businessInfoChangeService.findSpBusiness(query.getClassId(),businessInfoChange.getId());
//		businessInfoChange = businessInfoChangeService.getInfoChangeById(query.getClassId(),businessInfoChange.getId());
//		if(!WorkNodeStatusEnum.PASS_AUDITING.equals(businessInfoChange.getStatus())){
//			throw new RuleException("只能编辑已经通过的信息");
//		}
//		getValueStack().set("privilegeExpression",spBusiness.getBillClassesPrivilegeString());
//		return "editPage";
//	}
//	
//	public String modify() throws Exception{
//		spBusiness=businessInfoChangeService.findSpBusiness(query.getClassId(),businessInfoChange.getId());
//		businessInfoChange = businessInfoChangeService.getInfoChangeById(query.getClassId(),businessInfoChange.getId());
//		if(!WorkNodeStatusEnum.PASS_AUDITING.equals(businessInfoChange.getStatus())){
//			throw new RuleException("只能编辑已经通过的信息");
//		}
//		businessInfoChangeService.modify(query.getClassId(),businessInfoChange.getId());
//		setSuccessMessage("修改成功");
//		getValueStack().set(DATA, getMessage());
//		return DATA; 
//	}
//	

	public String view() throws Exception {
		spBusiness=businessInfoChangeService.findSpBusiness(query.getClassId(),businessInfoChange.getId());
		businessInfoChange = businessInfoChangeService.getInfoChangeById(query.getClassId(),businessInfoChange.getId());
		BusinessInfoChangeAudit audit = businessInfoChangeService.getAudit(query.getClassId(),businessInfoChange.getId());
		SpBillInstanceLog log = new SpBillInstanceLog();
		log.setBillInstanceId(businessInfoChange.getBillInstanceId());
		getValueStack().set("privilegeExpression", ModeType.SEARCH+"["+audit.getNodePrivilegeListString()+"]");
		getValueStack().set("excutedList", audit.getExcutedList());
		getValueStack().set("logList", audit.getLogList());
		return "view";
	}
	
//	public String checkNode(){
//		BusinessInfoChangeAudit audit=businessInfoChangeService.getAudit( query.getClassId(),businessInfoChange.getId());
//		if(findLastNode(audit)==null){
//			setErrorMessage("未能找到已经审核的节点，无法重新审核");
//		}else{
//			setSuccessMessage("已经找到节点");
//		}
//		getValueStack().set(DATA, getMessage());
//		return DATA; 
//	}

	private SpWorkNode findLastNode(BusinessInfoChangeAudit audit){
		List<SpWorkNode> excNodes=audit.getExcutedList();
		if(excNodes==null||excNodes.isEmpty()){
			return null;
		}
		SpWorkNode node = excNodes.get(excNodes.size()-1);
		node.getRoleId();
		for(String role :getUser().getJsdms()){
			if(node.getRoleId().equals(role)){
				excNodes.remove(excNodes.size()-1);
				return node;
			}
		}
		return null; 
	}
	public String detail() throws Exception {
		spBusiness=businessInfoChangeService.findSpBusiness(query.getClassId(),businessInfoChange.getId());
		businessInfoChange = businessInfoChangeService.getInfoChangeById(query.getClassId(),businessInfoChange.getId());
		BusinessInfoChangeAudit audit = businessInfoChangeService.getAudit(query.getClassId(),businessInfoChange.getId());
		if(audit==null){
			return "detail";
		}
		if (reAudit) {
			SpWorkNode node = findLastNode(audit);
			if(node!=null){
				getValueStack().set("privilegeExpression", node.getCommitWorkBillClassesPrivilege());
				getValueStack().set("currentNode", node);
			}
		}
		else if(audit.getCurrentNode() != null){
			getValueStack().set("privilegeExpression", audit.getCurrentNode().getCommitWorkBillClassesPrivilege());
			getValueStack().set("currentNode", audit.getCurrentNode());
		}		
		getValueStack().set("excutedList", audit.getExcutedList());
		getValueStack().set("logList", audit.getLogList());		
		return "detail";
	}

	private void preAudit() throws Exception {
		User user = getUser();

		node.setAuditorId(user.getYhm());
		node.setAuditTime(TimeUtil.getNowTimestamp());
		node.setWid(businessInfoChange.getId());
//		node.setRoleId(user.getJsdms().get(0));
		if (StringUtils.isBlank(node.getSuggestion())) {
			throw new RuleException("审核意见不能为空");
		}
	}

	public String pass() throws Exception {
		preAudit();
		businessInfoChangeService.doPass(query.getClassId(),node, this.getRole(), reAudit);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String reject() throws Exception {
		preAudit();
		businessInfoChangeService.doReject(query.getClassId(),node, this.getRole(), reAudit);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String back() throws Exception {
		preAudit();
		businessInfoChangeService.doBack(query.getClassId(),node, this.getRole(), backId);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String save() throws Exception {
		preAudit();
		businessInfoChangeService.doSave(query.getClassId(),node, this.getRole());
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	/**
	 * 返回
	 */
	public PageList<BusinessInfoChange> getPageList() {
		return pageList;
	}

	/**
	 * 设置
	 * @param pageList 
	 */
	public void setPageList(PageList<BusinessInfoChange> pageList) {
		this.pageList = pageList;
	}

	/**
	 * 返回
	 */
	public BusinessInfoChange getBusinessInfoChange() {
		return businessInfoChange;
	}

	/**
	 * 设置
	 * @param businessInfoChange 
	 */
	public void setBusinessInfoChange(BusinessInfoChange businessInfoChange) {
		this.businessInfoChange = businessInfoChange;
	}

	/**
	 * 返回
	 */
	public BusinessInfoQuery getQuery() {
		return query;
	}

	/**
	 * 设置
	 * @param query 
	 */
	public void setQuery(BusinessInfoQuery query) {
		this.query = query;
	}

	/**
	 * 返回
	 */
	public SpBusiness getSpBusiness() {
		return spBusiness;
	}

	/**
	 * 设置
	 * @param spBusiness 
	 */
	public void setSpBusiness(SpBusiness spBusiness) {
		this.spBusiness = spBusiness;
	}

	/**
	 * 返回
	 */
	public SpWorkNode getNode() {
		return node;
	}

	/**
	 * 设置
	 * @param node 
	 */
	public void setNode(SpWorkNode node) {
		this.node = node;
	}

	/**
	 * 返回
	 */
	public String getBackId() {
		return backId;
	}

	/**
	 * 设置
	 * @param backId 
	 */
	public void setBackId(String backId) {
		this.backId = backId;
	}

	/**
	 * 返回
	 */
	public boolean isReAudit() {
		return reAudit;
	}

	/**
	 * 设置
	 * @param reAudit 
	 */
	public void setReAudit(boolean reAudit) {
		this.reAudit = reAudit;
	}

	/**
	 * 设置
	 * @param businessInfoChangeService 
	 */
	public void setBusinessInfoChangeService(
			IBusinessInfoChangeService businessInfoChangeService) {
		this.businessInfoChangeService = businessInfoChangeService;
	}
}
