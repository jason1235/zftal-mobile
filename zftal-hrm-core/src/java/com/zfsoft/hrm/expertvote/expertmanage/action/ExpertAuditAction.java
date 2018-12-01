package com.zfsoft.hrm.expertvote.expertmanage.action;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.common.log.User;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.dybill.enums.ModeType;
import com.zfsoft.hrm.expertvote.expertmanage.entity.ExpertAudit;
import com.zfsoft.hrm.expertvote.expertmanage.entity.ExpertDeclare;
import com.zfsoft.hrm.expertvote.expertmanage.query.ExpertAuditQuery;
import com.zfsoft.hrm.expertvote.expertmanage.service.IExpertAuditService;
import com.zfsoft.hrm.expertvote.expertmanage.service.IExpertDeclareService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.workflow.enumobject.WorkNodeStatusEnum;
import com.zfsoft.workflow.model.SpWorkNode;

/**
 * @author: xiaoyongjun
 * @since: 2014-3-17 上午09:05:19
 */
public class ExpertAuditAction extends HrmAction {

	private static final long serialVersionUID = -6641664997150280438L;
	private PageList<ExpertDeclare> pageList;
	private IExpertAuditService expertAuditService;
	private IExpertDeclareService expertDeclareService;
	private ExpertDeclare expertDeclare = new ExpertDeclare();

	private ExpertAuditQuery query = new ExpertAuditQuery();
	private SpWorkNode node;

	private String sortFieldName = null;
	private String asc = "up";
	private String backId;
	/**
	 * 审核列表
	 * @param: 
	 * @return:
	 */
	public String page() throws Exception {
		if (StringUtils.isEmpty(query.getStatus())) {
			query.setStatus(WorkNodeStatusEnum.WAIT_AUDITING.getKey());
		}
		if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( " xm" );
		}
		pageList = expertAuditService.getPageList(query);
		int beginIndex = pageList.getPaginator().getBeginIndex();
		getValueStack().set("statusArray", WorkNodeStatusEnum.values());
		getValueStack().set("beginIndex", beginIndex);
		return "page";
	}
	/**
	 * 查看
	 */
	public String view() throws Exception {
		expertDeclare = expertDeclareService.getById(expertDeclare.getId());
		ExpertAudit audit = expertAuditService.getAudit(expertDeclare.getId());
		getValueStack().set("excutedList", audit.getExcutedList());
		getValueStack().set("logList", audit.getLogList());
		getValueStack().set("privilegeExpression", 
				ModeType.SEARCH+"["+audit.getNodePrivilegeListString()+"]");
		return "view";
	}
	/**
	 * 审定
	 * @param: 
	 * @return:
	 */
	public String detail() throws Exception {
		expertDeclare = expertDeclareService.getById(expertDeclare.getId());
		ExpertAudit audit = expertAuditService.getAudit(expertDeclare.getId());
		getValueStack().set("privilegeExpression", audit.getCurrentNode().getCommitWorkBillClassesPrivilege());
		getValueStack().set("excutedList", audit.getExcutedList());
		getValueStack().set("logList", audit.getLogList());
		getValueStack().set("currentNode", audit.getCurrentNode());
		return "detail";
	}

	private void preAudit() throws Exception {
		User user = getUser();
		node.setNodeId(node.getNodeId());
		node.setAuditorId(user.getYhm());
		node.setAuditTime(new Date());
		node.setWid(expertDeclare.getId());
		node.setRoleId(user.getJsdms().get(0));
		if (StringUtils.isBlank(node.getSuggestion())) {
			throw new RuleException("审核意见不能为空");
		}
	}
	/**
	 * 审核通过 
	 */
	public String pass() throws Exception {
		preAudit();
		expertAuditService.doPass(node, this.getRole());
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	/**
	 * 审核不通过
	 */
	public String reject() throws Exception {
		preAudit();
		expertAuditService.doReject(node, this.getRole());
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	/**
	 * 退回
	 */
	public String back() throws Exception {
		preAudit();
		expertAuditService.doBack(node, this.getRole(), backId);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String save() throws Exception {
		preAudit();
		expertAuditService.doSave(node, this.getRole());
		getValueStack().set(DATA, getMessage());
		return DATA;
	}


	public PageList<ExpertDeclare> getPageList() {
		return pageList;
	}

	public void setPageList(PageList<ExpertDeclare> pageList) {
		this.pageList = pageList;
	}

	public ExpertAuditQuery getQuery() {
		return query;
	}

	public void setQuery(ExpertAuditQuery query) {
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
	public void setNode(SpWorkNode node) {
		this.node = node;
	}
	public SpWorkNode getNode() {
		return node;
	}
	public String getBackId() {
		return backId;
	}
	public void setBackId(String backId) {
		this.backId = backId;
	}
	/**
	 * 返回
	 */
	public ExpertDeclare getExpertDeclare() {
		return expertDeclare;
	}
	/**
	 * 设置
	 * @param expertDeclare 
	 */
	public void setExpertDeclare(ExpertDeclare expertDeclare) {
		this.expertDeclare = expertDeclare;
	}
	/**
	 * 设置
	 * @param expertAuditService 
	 */
	public void setExpertAuditService(IExpertAuditService expertAuditService) {
		this.expertAuditService = expertAuditService;
	}
	/**
	 * 设置
	 * @param expertDeclareService 
	 */
	public void setExpertDeclareService(IExpertDeclareService expertDeclareService) {
		this.expertDeclareService = expertDeclareService;
	}
}
