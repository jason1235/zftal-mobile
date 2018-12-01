package com.zfsoft.workflow.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.workflow.model.SpLine;

/**
 * 节点连线接口类
 * 
 * @version 3.2.0
 */
public interface ISpLineDao {
	/* @interface model: 添加一条SpLine记录 */
	void insert(SpLine spLine) throws DataAccessException;

	/* @interface model: 删除一条SpLine记录 */
	void delete(String spLineId) throws DataAccessException;
	
	/* @interface model: 根据节点ID删除节点连接的所有线 */
	void deleteLineByNodeId(String nodeId) throws DataAccessException;
	
	/* @interface model: 根据节点ID删除节点连接的所有线 */
	void deleteLineByPId(String pId) throws DataAccessException;
	
	/* @interface model: 调整连线位置 */
	void updateOrder(SpLine spLine) throws DataAccessException;
	
	/* @interface model: 修改连线表达式 */
	void updateExpression(String expression) throws DataAccessException;
	
	/* @interface model: 根据上节点下节点查询连线数量 */
	int getCountByUnodeIdAndDnodeId(SpLine spLine)throws DataAccessException;
	
	/* @interface model: 查询所有SpLine结果集,返回SpLine对象的集合 */
	List<SpLine> findLineListByPid(String pId) throws DataAccessException;

	/* @interface model: 查询所有SpLine结果集,返回SpLine对象的集合 */
	List<SpLine> findLineList(SpLine spLine) throws DataAccessException;

	SpLine findLineListById(String lineId);

	void update(SpLine spLine);
}
