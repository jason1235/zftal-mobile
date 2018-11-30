package com.zfsoft.workflow.service;

import java.util.List;

import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpWorkTask;

/**
 * 工作审核任务管理service接口类
 * 
 * @version 3.2.0
 */
public interface ISpWorkTaskService extends ISpAuditingLogService {

	/* @interface model: 添加一条SpWorkTask记录 */
	void addSpWorkTask(SpWorkTask spWorkTask) throws WorkFlowException;

	/* @interface model: 查询SpWorkTask结果集,返回SpWorkTask对象的集合 */
	List<SpWorkTask> findWorkTaskList(SpWorkTask spWorkTask) throws WorkFlowException;
	
	/* @interface model: 根据条件查询SpWorkTask结果,返回SpWorkTask对象 */
	SpWorkTask findWorkNodeByWidAndTaskId(SpWorkTask spWorkTask) throws WorkFlowException;
	
	/* @interface model: 根据条件查询SpWorkTask结果,返回SpWorkTask对象 */
	SpWorkTask findWorkNodeByTaskCodeAndTaskId(SpWorkTask spWorkTask) throws WorkFlowException;
	
	/**
	 * 更新任务审核信息
	 * @param spWorkTask
	 * @throws WorkFlowException
	 */
	void editSpWorkTask(SpWorkTask spWorkTask) throws WorkFlowException;

	/**
	 * 查询某节点执行状态为非 已执行 的计数
	 * @param wt
	 * @return
	 * @throws WorkFlowException
	 */
	int countWorkTaskForNonExecute(SpWorkTask wt) throws WorkFlowException;
}
