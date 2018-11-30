package com.zfsoft.workflow.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpLine;

/**
 * 节点管理service接口类
 * 
 * @version 3.2.0
 */
	
public interface ISpLineService extends BaseInterface {
	/* @interface model: 添加一条SpLine记录 */
	void insert(SpLine spLine) throws WorkFlowException;
	
	/* @interface model: 添加一条SpLine记录 */
	void insertLineListAfresh(String pid, List<SpLine> spLineList) throws WorkFlowException;

	/* @interface model: 删除一条SpLine记录 */
	void delete(String spLineId) throws WorkFlowException;
	
	/* @interface model: 根据节点ID删除节点连接的所有线 */
	void deleteLineByNodeId(String nodeId) throws WorkFlowException;
	
	/* @interface model: 调整连线位置 */
	void updateOrder(String uNodeId, String dNodeId) throws WorkFlowException;
	
	/* @interface model: 修改连线表达式 */
	void updateExpression(String expression) throws WorkFlowException;
	
	/* @interface model: 根据上节点下节点查询连线数量 */
	int getCountByUNodeIdAndDNodeId(String uNodeId,String dNodeId)throws WorkFlowException;
	
	/* @interface model: 根据上节点ID查询所有SpLine结果集,返回SpLine对象的集合 */
	List<SpLine> findLineListByUnodeId(String unodeId) throws DataAccessException;
	
	/* @interface model: 根据上节点ID查询所有SpLine结果集,返回SpLine对象 */
	SpLine findLineByUNodeId(String uNodeId) throws DataAccessException;
	
	/* @interface model: 根据下节点ID查询所有SpLine结果集,返回SpLine对象的集合 */
	List<SpLine> findLineListByDnodeId(String dnodeId) throws DataAccessException;
	
	/* @interface model: 根据下节点ID查询所有SpLine结果集,返回SpLine对象 */
	SpLine findLineByDNodeId(String dNodeId) throws DataAccessException;

	/* @interface model: 查询所有SpLine结果集,返回SpLine对象的集合 */
	List<SpLine> findLineList(SpLine spLine) throws WorkFlowException;
	
	/* @interface model: 根据流程ID查询所有SpLine结果集,返回SpLine对象的集合 */
	List<SpLine> findLineListByPid(String pId) throws WorkFlowException;

	SpLine findLineListById(String lineId);

	void update(SpLine spLine);
}
