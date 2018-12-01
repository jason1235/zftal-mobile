package com.zfsoft.workflow.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpWorkNode;

/**
 * 工作审核节点连线管理service接口类
 * 
 * @version 3.2.0
 */
public interface ISpWorkNodeService extends ISpAuditingLogService {

	/* @interface model: 添加一条SpWorkNode记录 */
	void addSpWorkNode(SpWorkNode spWorkNode) throws WorkFlowException;

	/* @interface model: 更新一条SpWorkNode记录 */
	void editSpWorkNode(SpWorkNode spWorkNode) throws WorkFlowException;

	/* @interface model: 更新SpWorkNode及关联的任务记录 */
	void editSpWorkNodeAndTask(SpWorkNode spWorkNode) throws WorkFlowException;
	
	/* @interface model: 根据工作ID和节点查询SpWorkNode结果,返回SpWorkNode对象 */
	SpWorkNode findWorkNodeByWidAndNodeId(SpWorkNode spWorkNode) throws DataAccessException;

	/* @interface model: 查询SpWorkNode结果集,返回SpWorkNode对象的集合 */
	List<SpWorkNode> findWorkNodeList(SpWorkNode spWorkNode)
			throws WorkFlowException;

	/* @interface model: 根据条件查询SpWorkNode结果,返回SpWorkNode对象集合 */
	List<SpWorkNode> findWorkNodeListByCondition(String bType, String bCode, String status,
			String[] roleIdArray, String auditor) throws WorkFlowException;
}
