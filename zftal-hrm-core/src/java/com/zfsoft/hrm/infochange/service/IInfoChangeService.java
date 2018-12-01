package com.zfsoft.hrm.infochange.service;

import java.util.List;

import com.zfsoft.common.log.User;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.infochange.entity.InfoChange;
import com.zfsoft.hrm.infochange.entity.InfoChangeAudit;
import com.zfsoft.hrm.infochange.query.InfoChangeQuery;
import com.zfsoft.workflow.model.SpWorkNode;
/** 
 * @author Patrick Shen
 * @date 2013-6-9 下午02:22:08 
 */
public interface IInfoChangeService {
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
	public void doPass(String classId,SpWorkNode node, String[] role);
	/**
	 * 
	 * @param node
	 * @param role
	 */
	public void doReject(String classId,SpWorkNode node, String[] role);
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
	 * @param classId 表单信息类编号
	 * @param id InfoChange 对象的编号
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
	 * 删除指定用户、指定信息类的未提交的空信息变更
	 * @param infoChanges
	 */
	public void doDeleteBlankInitail(String userId,String classId);
	
	public void doPassBatch(String idList, User user);
}
