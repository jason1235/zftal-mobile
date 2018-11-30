package com.zfsoft.workflow.service; 
  
 import java.util.List;

import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpAuditingLog;
import com.zfsoft.workflow.model.SpWorkNode;
import com.zfsoft.workflow.model.SpWorkTask;
  
 /**  
  * 审批操作日志接口类
  * @author fanyj
  * 
  */  
public interface ISpAuditingLogService  {
	/**
	 * 新增节点操作日志
	 * 
	 * @param spWorkNode 工作审核节点对象
	 * @return 
	 * @throws WorkFlowException 异常对象
	 */
	void insertNodeLog(SpWorkNode spWorkNode) throws WorkFlowException;
	
	/**
	 * 新增任务操作日志
	 * 
	 * @param spWorkTask 工作审核任务对象
	 * @param warnInfo   提醒信息
	 * @return 
	 * @throws WorkFlowException 异常对象
	 */
	void insertTaskLog(SpWorkTask spWorkTask, String warnInfo) throws WorkFlowException;
	
	/**
	 * 通用保存日志方法
	 * @param logType  日志类型
	 * @param wId  工作ID
	 * @param bType  业务类型
	 * @param oType  操作类型
	 * @param oStatus  操作状态
	 * @param oResult  操作结果
	 * @param oContent  操作内容
	 * @param oRole  操作角色
	 * @param operator  操作人
	 * @throws WorkFlowException
	 */
	void saveLog(String logType, String wId, String bType, 
			String oType, String oStatus, String oResult, 
			String oContent, String oRole, String operator) 
		throws WorkFlowException;

	/**
	 * 按照工作id查询工作审核日志信息
	 * 
	 * @param wId 工作ID
	 * @return List 关于这个工作的所有审核日志记录（按日志时间倒序排序）
	 * @throws WorkFlowException 异常对象
	 */
	List<SpAuditingLog> findAuditingLogByWid(String wId) throws WorkFlowException;

	/**
	 * 查询某业务历史操作日志
	 * @param wid 业务编号，必须的
	 * @param roleId 角色
	 * @return
	 * @throws WorkFlowException
	 */
	List<SpAuditingLog> findAuditingLog(SpAuditingLog spAuditingLog) throws WorkFlowException;
}
