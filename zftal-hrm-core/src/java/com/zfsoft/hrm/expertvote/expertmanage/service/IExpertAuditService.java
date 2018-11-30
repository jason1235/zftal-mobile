package com.zfsoft.hrm.expertvote.expertmanage.service;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.expertvote.expertmanage.entity.ExpertAudit;
import com.zfsoft.hrm.expertvote.expertmanage.entity.ExpertDeclare;
import com.zfsoft.hrm.expertvote.expertmanage.query.ExpertAuditQuery;
import com.zfsoft.workflow.model.SpWorkNode;

/** 
 * @author jinjj
 * @date 2013-4-24 上午10:22:53 
 *  
 */
public interface IExpertAuditService {

	public PageList<ExpertDeclare> getPageList(ExpertAuditQuery query) throws Exception;
	
	public ExpertAudit getAudit(String declareId);

	public void doPass(SpWorkNode node,String[] role);

	public void doReject(SpWorkNode node,String[] role);

	public void doBack(SpWorkNode node,String[] role, String backId);

	public void doSave(SpWorkNode node,String[] role);

}