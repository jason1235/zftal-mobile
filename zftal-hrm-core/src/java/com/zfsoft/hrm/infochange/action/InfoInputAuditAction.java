package com.zfsoft.hrm.infochange.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.common.log.User;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dataprivilege.util.DeptFilterUtil;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.dybill.entity.SpBillInstanceLog;
import com.zfsoft.hrm.dybill.enums.ModeType;
import com.zfsoft.hrm.dybill.service.ISpBillInstanceLogService;
import com.zfsoft.hrm.infochange.entity.InfoChange;
import com.zfsoft.hrm.infochange.entity.InfoChangeAudit;
import com.zfsoft.hrm.infochange.query.InfoChangeQuery;
import com.zfsoft.hrm.infochange.service.IInfoInputService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.date.TimeUtil;
import com.zfsoft.workflow.enumobject.WorkNodeStatusEnum;
import com.zfsoft.workflow.model.SpBusiness;
import com.zfsoft.workflow.model.SpWorkNode;

/**
 * 
 * @author ChenMinming
 * @date 2013-8-23
 * @version V1.0.0
 */
public class InfoInputAuditAction extends HrmAction{

	private static final long serialVersionUID = -32148765418304874L;
	private IInfoInputService infoInputService;
	private PageList<InfoChange> pageList;
	private ISpBillInstanceLogService spBillInstanceLogService;

	private InfoChange infoChange = new InfoChange();
	private InfoChangeQuery query = new InfoChangeQuery();
	
	private SpBusiness spBusiness;

	private SpWorkNode node;
	private String backId;
	private boolean reAudit = false;

	public String page() throws Exception {
		query.setOnwer(false);
		pageList = infoInputService.getPagedList(query);
		int beginIndex = pageList.getPaginator().getBeginIndex();
		pageList.setPaginator(query);
//		getValueStack().set("infoClasses", infoClasses);
//		Map<String, String> changeMap = new HashMap<String, String>();
//		for (InfoChange change : pageList) {
//			String  con = infoInputService.getChangeString(change.getClassId(), change.getId());
//			changeMap.put(change.getId(), con);
//		}
//		getValueStack().set("changeMap", changeMap);
		getValueStack().set("statusArray", WorkNodeStatusEnum.values());
		getValueStack().set("beginIndex", beginIndex);
		return "page";
	}
	
	public String toEdit() throws Exception{
		spBusiness=infoInputService.findSpBusinessByRelDetail(query.getClassId(),infoChange.getId());
		infoChange = infoInputService.getInfoChangeById(query.getClassId(),infoChange.getId());
		if(!WorkNodeStatusEnum.PASS_AUDITING.equals(infoChange.getStatus())){
			throw new RuleException("只能编辑已经通过的信息");
		}
		getValueStack().set("privilegeExpression",spBusiness.getBillClassesPrivilegeString());
		return "editPage";
	}
	
	public String modify() throws Exception{
		spBusiness=infoInputService.findSpBusinessByRelDetail(query.getClassId(),infoChange.getId());
		infoChange = infoInputService.getInfoChangeById(query.getClassId(),infoChange.getId());
		if(!WorkNodeStatusEnum.PASS_AUDITING.equals(infoChange.getStatus())){
			throw new RuleException("只能编辑已经通过的信息");
		}
		infoInputService.modify(query.getClassId(),infoChange.getId());
		setSuccessMessage("修改成功");
		getValueStack().set(DATA, getMessage());
		return DATA; 
	}
	
	public String getChangeString(){
		String message=infoInputService.getChangeString(query.getClassId(), query.getId());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("message", message);
		getValueStack().set(DATA, map);
		return DATA; 
	}

	public String view() throws Exception {
		spBusiness=infoInputService.findSpBusinessByRelDetail(query.getClassId(),infoChange.getId());
		infoChange = infoInputService.getInfoChangeById(query.getClassId(),infoChange.getId());
		InfoChangeAudit audit = infoInputService.getAudit(query.getClassId(),infoChange.getId());
		SpBillInstanceLog log = new SpBillInstanceLog();
		log.setBillInstanceId(infoChange.getBillInstanceId());
//		List<SpBillInstanceLog> billLogs = spBillInstanceLogService.getLogList(log);
		getValueStack().set("privilegeExpression", ModeType.SEARCH+"["+audit.getNodePrivilegeListString()+"]");
		getValueStack().set("excutedList", audit.getExcutedList());
//		getValueStack().set("logList", audit.getLogList());
//		getValueStack().set("billLogs", billLogs);
		return "view";
	}
	
	public String vwdetail() throws Exception {
		spBusiness=infoInputService.findSpBusinessByRelDetail(query.getClassId(),infoChange.getId());
		infoChange = infoInputService.getInfoChangeById(query.getClassId(),infoChange.getId());
		InfoChangeAudit audit = infoInputService.getAudit(query.getClassId(),infoChange.getId());
		SpBillInstanceLog log = new SpBillInstanceLog();
		log.setBillInstanceId(infoChange.getBillInstanceId());
		List<SpBillInstanceLog> billLogs = spBillInstanceLogService.getLogList(log);
		getValueStack().set("privilegeExpression", ModeType.SEARCH+"["+audit.getNodePrivilegeListString()+"]");
		getValueStack().set("excutedList", audit.getExcutedList());
		getValueStack().set("logList", audit.getLogList());
		getValueStack().set("billLogs", billLogs);
		return "vwdetail";
	}
	
	public String vwlist() throws Exception{
		List<InfoClass> infoClasses=new ArrayList<InfoClass>();
		
		getValueStack().set("infoClasses", infoClasses);
		String express2 = DeptFilterUtil.getCondition("ovdw", "dwm");
		if(!StringUtil.isEmpty(express2)){
			express2="1=1 and exists (select 1 from overall ovdw where ovdw.gh=info.user_id and "+express2+") and op_type = 'add' and status !='INITAIL'";
		}
		query.setExpress2(express2);
		pageList = infoInputService.getPagedList(query);
		int beginIndex = pageList.getPaginator().getBeginIndex();
		pageList.setPaginator(query);
		for (InfoClass infoClass : InfoClassCache.getInfoClasses()) {
			try{
				InfoChangeQuery icquery = new InfoChangeQuery();
				icquery.setClassId(infoClass.getGuid());
				icquery.setExpress2(express2);
				if(!infoInputService.getPagedList(icquery).isEmpty()){;
					infoClasses.add(infoClass);
				}
			}catch(Exception e){
			}
		}
		getValueStack().set("beginIndex",beginIndex);
		getValueStack().set("statusArray", WorkNodeStatusEnum.values());
		return "vwlist";
	}
	public String checkNode(){
		InfoChangeAudit audit=infoInputService.getAudit( query.getClassId(),infoChange.getId());
		if(findLastNode(audit)==null){
			setErrorMessage("未能找到已经审核的节点，无法重新审核");
		}else{
			setSuccessMessage("已经找到节点");
		}
		getValueStack().set(DATA, getMessage());
		return DATA; 
	}

	private SpWorkNode findLastNode(InfoChangeAudit audit){
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
		spBusiness=infoInputService.findSpBusinessByRelDetail(query.getClassId(),infoChange.getId());
		infoChange = infoInputService.getInfoChangeById(query.getClassId(),infoChange.getId());
		InfoChangeAudit audit = infoInputService.getAudit(query.getClassId(),infoChange.getId());
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
		node.setWid(infoChange.getId());
//		node.setRoleId(user.getJsdms().get(0));
		if (StringUtils.isBlank(node.getSuggestion())) {
			throw new RuleException("审核意见不能为空");
		}
	}

	public String pass() throws Exception {
		preAudit();
		infoInputService.doPass(query.getClassId(),node, this.getRole(), reAudit);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String reject() throws Exception {
		preAudit();
		infoInputService.doReject(query.getClassId(),node, this.getRole(), reAudit);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String back() throws Exception {
		preAudit();
		infoInputService.doBack(query.getClassId(),node, this.getRole(), backId);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String save() throws Exception {
		preAudit();
		infoInputService.doSave(query.getClassId(),node, this.getRole());
		getValueStack().set(DATA, getMessage());
		return DATA;
	}


	public void setinfoInputService(IInfoInputService infoInputService) {
		this.infoInputService = infoInputService;
	}

	public SpWorkNode getNode() {
		return node;
	}

	public void setNode(SpWorkNode node) {
		this.node = node;
	}

	public String getBackId() {
		return backId;
	}

	public void setBackId(String backId) {
		this.backId = backId;
	}
	public SpBusiness getSpBusiness() {
		return spBusiness;
	}

	public void setSpBusiness(SpBusiness spBusiness) {
		this.spBusiness = spBusiness;
	}

	public PageList<InfoChange> getPageList() {
		return pageList;
	}

	public void setPageList(PageList<InfoChange> pageList) {
		this.pageList = pageList;
	}

	public InfoChange getInfoChange() {
		return infoChange;
	}

	public void setInfoChange(InfoChange infoChange) {
		this.infoChange = infoChange;
	}

	public InfoChangeQuery getQuery() {
		return query;
	}

	public void setQuery(InfoChangeQuery query) {
		this.query = query;
	}

	public boolean isReAudit() {
		return reAudit;
	}

	public void setReAudit(boolean reAudit) {
		this.reAudit = reAudit;
	}

	public void setSpBillInstanceLogService(
			ISpBillInstanceLogService spBillInstanceLogService) {
		this.spBillInstanceLogService = spBillInstanceLogService;
	}

}
