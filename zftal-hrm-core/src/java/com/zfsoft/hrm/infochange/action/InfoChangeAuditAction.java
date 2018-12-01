package com.zfsoft.hrm.infochange.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.common.log.User;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.dybill.enums.ModeType;
import com.zfsoft.hrm.dybill.enums.PrivilegeType;
import com.zfsoft.hrm.infochange.entity.InfoChange;
import com.zfsoft.hrm.infochange.entity.InfoChangeAudit;
import com.zfsoft.hrm.infochange.query.InfoChangeQuery;
import com.zfsoft.hrm.infochange.service.IInfoChangeService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.workflow.enumobject.WorkNodeStatusEnum;
import com.zfsoft.workflow.model.SpBusiness;
import com.zfsoft.workflow.model.SpWorkNode;
import com.zfsoft.workflow.service.ISpBusinessService;

/** 
 * @author Patrick Shen
 * @date 2013-6-9 下午02:22:08 
 */
public class InfoChangeAuditAction extends HrmAction {

	private static final long serialVersionUID = -6641664997150280438L;
	private IInfoChangeService infoChangeService;
	private PageList<InfoChange> pageList;
	private ISpBusinessService spBusinessService;

	private InfoChange infoChange = new InfoChange();
	private InfoChangeQuery query = new InfoChangeQuery();
	
	private SpBusiness spBusiness;

	private SpWorkNode node;
	private String backId;
	
	private String sortFieldName = null;
	private String asc = "up";

	public String page() throws Exception {
		List<InfoClass> infoClasses=new ArrayList<InfoClass>();
		
		for (InfoClass infoClass : InfoClassCache.getInfoClasses()) {
			try{
				spBusinessService.findSpBusinessByRelDetail(infoClass.getGuid(),null);
				infoClasses.add(infoClass);
			}catch(Exception e){
			}
		}
		 String readSession = getRequest().getParameter("readSession");
		if("true".equals(readSession)){
			query = (InfoChangeQuery )getSession().getAttribute("InfoChangeAuditAction_InfoChangeQuery");
			if(query==null) query = new InfoChangeQuery();
		}
	    
		
		if(query.getClassId()==null&&infoClasses.size()>0){
			//query.setClassId(infoClasses.get(0).getGuid());
		}
//		if(query.getClassId()==null){
//			getValueStack().set("statusArray", WorkNodeStatusEnum.values());
//			getValueStack().set("infoClasses", infoClasses);
//			return "page";
//		}
		
		query.setOnwer(false);
		if(query.getStatus()==null){
			query.setStatus(WorkNodeStatusEnum.WAIT_AUDITING);
		}
		if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( " commitDate DESC" );
		}
		pageList = infoChangeService.getPagedList(query);
		int beginIndex = pageList.getPaginator().getBeginIndex();
		pageList.setPaginator(query);
		getSession().setAttribute("InfoChangeAuditAction_InfoChangeQuery",query);
		getValueStack().set("infoClasses", infoClasses);
		getValueStack().set("statusArray", WorkNodeStatusEnum.values());
		getValueStack().set("beginIndex", beginIndex);
		return "page";
	}

	public String view() throws Exception {
		spBusiness=spBusinessService.findSpBusinessByRelDetail(query.getClassId(),infoChange.getId());
		infoChange = infoChangeService.getInfoChangeById(query.getClassId(),infoChange.getId());
		InfoChangeAudit audit = infoChangeService.getAudit(query.getClassId(),infoChange.getId());
		getValueStack().set("privilegeExpression", ModeType.SEARCH+"["+audit.getNodePrivilegeListString()+"]");
		getValueStack().set("excutedList", audit.getExcutedList());
		getValueStack().set("logList", audit.getLogList());
		getValueStack().set("word", getRequest().getParameter("word"));
		return "view";
	}

	public String detail() throws Exception {
		spBusiness=spBusinessService.findSpBusinessByRelDetail(query.getClassId(),infoChange.getId());
		infoChange = infoChangeService.getInfoChangeById(query.getClassId(),infoChange.getId());
		InfoChangeAudit audit = infoChangeService.getAudit(query.getClassId(),infoChange.getId());
		String privilegeExpression = audit.getCurrentNode().getCommitWorkBillClassesPrivilege();
		if(!"add".equals(infoChange.getOpType())){
			privilegeExpression = privilegeExpression
			.replaceAll(PrivilegeType.SEARCH_ADD_DELETE_EDIT.toString(), PrivilegeType.SEARCH_EDIT.toString())
			.replaceAll(PrivilegeType.SEARCH_ADD_DELETE.toString(), PrivilegeType.SEARCH.toString());
		}
		if(audit==null){
			return "detail";
		}
		if(audit.getCurrentNode() != null){
			getValueStack().set("privilegeExpression", privilegeExpression);
			getValueStack().set("currentNode", audit.getCurrentNode());
		}		
		getValueStack().set("excutedList", audit.getExcutedList());
		getValueStack().set("logList", audit.getLogList());		
		getValueStack().set("word", getRequest().getParameter("word"));
		return "detail";
	}

	private void preAudit() throws Exception {
		User user = getUser();

		node.setAuditorId(user.getYhm());
		node.setAuditTime(new Date());
		node.setWid(infoChange.getId());
		node.setRoleId(user.getJsdms().get(0));
		if (StringUtils.isBlank(node.getSuggestion())) {
			throw new RuleException("审核意见不能为空");
		}
	}

	public String pass() throws Exception {
		preAudit();
		infoChangeService.doPass(query.getClassId(),node, this.getRole());
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String passBatch() throws Exception {
		infoChangeService.doPassBatch(query.getId(), getUser());
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String reject() throws Exception {
		preAudit();
		infoChangeService.doReject(query.getClassId(),node, this.getRole());
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String back() throws Exception {
		preAudit();
		infoChangeService.doBack(query.getClassId(),node, this.getRole(), backId);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String save() throws Exception {
		preAudit();
		infoChangeService.doSave(query.getClassId(),node, this.getRole());
		getValueStack().set(DATA, getMessage());
		return DATA;
	}


	public void setInfoChangeService(IInfoChangeService infoChangeService) {
		this.infoChangeService = infoChangeService;
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

	public void setSpBusinessService(ISpBusinessService spBusinessService) {
		this.spBusinessService = spBusinessService;
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

	public String getSortFieldName() {
		return sortFieldName;
	}

	public void setSortFieldName(String sortFieldName) {
		this.sortFieldName = sortFieldName;
	}

	public String getAsc() {
		return asc;
	}

	public void setAsc(String asc) {
		this.asc = asc;
	} 

	
}
