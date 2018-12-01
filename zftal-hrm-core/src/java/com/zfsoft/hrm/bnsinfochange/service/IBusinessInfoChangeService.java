package com.zfsoft.hrm.bnsinfochange.service;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.bnsinfochange.entity.BusinessInfoChange;
import com.zfsoft.hrm.bnsinfochange.entity.BusinessInfoChangeAudit;
import com.zfsoft.hrm.bnsinfochange.query.BusinessInfoQuery;
import com.zfsoft.workflow.model.SpBusiness;
import com.zfsoft.workflow.model.SpWorkNode;

/**
 * 
 * @author ChenMinming
 * @date 2014-6-17
 * @version V1.0.0
 */
public interface IBusinessInfoChangeService {

	/**
	 * 
	 * @param userId
	 * @param opType 
	 * @return
	 */
	public BusinessInfoChange getNewInfoChange(String classId,String userId, String opType);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public BusinessInfoChange getInfoChangeById(String classId,String id);
	/**
	 * 
	 * @param query
	 * @return
	 */
	public List<BusinessInfoChange> getInfoChangeList(BusinessInfoQuery query);
	/**
	 * 
	 * @param id
	 */
	public void doCommit(String classId,String id);
	
	/**
	 * 
	 * @param id
	 */
	public BusinessInfoChange doReCommit(String classId,String id);
	/**
	 * 
	 * @param yhm
	 */
	public void doCancel(String classId,String id);
	/**
	 * 
	 * @param node
	 * @param role
	 */
	public void doPass(String classId,SpWorkNode node, String[] role, boolean reAudit);
	/**
	 * 
	 * @param node
	 * @param role
	 */
	public void doReject(String classId,SpWorkNode node, String[] role, boolean reAudit);
	/**
	 * 
	 * @param node
	 * @param role
	 * @param backId
	 */
	public void doBack(String classId,SpWorkNode node, String[] role, String backId);
	/**
	 * 
	 * @param node
	 * @param role
	 */
	public void doSave(String classId,SpWorkNode node, String[] role);
	/**
	 * 
	 * @param training
	 * @return
	 * @throws Exception
	 */
	public PageList<BusinessInfoChange> getPagedList(BusinessInfoQuery training)throws Exception;
	/**
	 * 
	 * @param id
	 * @return
	 */
	public BusinessInfoChangeAudit getAudit(String classId,String id);
	/**
	 * 填充instance Map对象
	 * @param list
	 * @param classId
	 */
	public void fillInstance2InfoChange(List<BusinessInfoChange> list,String classId);
	/**
	 * 取消上报
	 * @param classId
	 * @param id
	 */
	public void doCancelDeclare(String classId,String id);
	/**
	 * 修改记录至信息类
	 * @param infoChanges
	 */
	public void modify(String classId,String id);
	
	
	SpBusiness findSpBusiness(String classId, String workId);
}
