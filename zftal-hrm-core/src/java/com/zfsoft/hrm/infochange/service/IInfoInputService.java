package com.zfsoft.hrm.infochange.service;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.infochange.entity.InfoChange;
import com.zfsoft.hrm.infochange.entity.InfoChangeAudit;
import com.zfsoft.hrm.infochange.query.InfoChangeQuery;
import com.zfsoft.workflow.model.SpBusiness;
import com.zfsoft.workflow.model.SpWorkNode;

/**
 * 
 * @author ChenMinming
 * @date 2013-8-27
 * @version V1.0.0
 */
public interface IInfoInputService {
	/**
	 * 
	 * @param userId
	 * @param opType 
	 * @return
	 */
	public InfoChange getNewInfoChange(String classId,String userId, String opType,String guid);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public InfoChange getInfoChangeById(String classId,String id);
	/**
	 * 
	 * @param query
	 * @return
	 */
	public List<InfoChange> getInfoChangeList(InfoChangeQuery query);
	/**
	 * 
	 * @param id
	 */
	public void doCommit(String classId,String id);
	
	/**
	 * 
	 * @param id
	 */
	public InfoChange doReCommit(String classId,String id);
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
	public PageList<InfoChange> getPagedList(InfoChangeQuery training)throws Exception;
	/**
	 * 
	 * @param id
	 * @return
	 */
	public InfoChangeAudit getAudit(String classId,String id);
	/**
	 * 检查是否改动
	 * @param classId
	 * @param id
	 * @return true 为改动
	 */
	public Boolean doCheckModify(String classId, String id);
	/**
	 * 获取比对串
	 * @param classId
	 * @param id
	 * @return
	 */
	public String getChangeString(String classId, String id);
	/**
	 * 填充instance Map对象
	 * @param list
	 * @param classId
	 */
	public void fillInstance2InfoChange(List<InfoChange> list,String classId);
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
	
	/**
	 * 删除指定用户、指定信息类的未提交的空信息变更
	 * @param infoChanges
	 */
	public void doDeleteBlankInitail(String userId,String classId);
	
	SpBusiness findSpBusinessByRelDetail(String relDetail, String workId);
}
