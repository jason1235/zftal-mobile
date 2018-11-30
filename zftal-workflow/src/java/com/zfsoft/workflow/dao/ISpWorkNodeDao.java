package com.zfsoft.workflow.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.workflow.model.SpWorkNode;

/**
 * 代码自动生成(bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public interface ISpWorkNodeDao  {
	/* @interface model: 添加一条SpWorkNode记录 */
	void addSpWorkNode(SpWorkNode spWorkNode) throws DataAccessException;

	/* @interface model: 更新一条SpWorkNode记录 */
	void editSpWorkNode(SpWorkNode spWorkNode) throws DataAccessException;

	/* @interface model: 根据条件更新节点执行状态 */
	void editEStatus(SpWorkNode spWorkNode) throws DataAccessException;

	/* @interface model: 删除一条SpWorkNode记录 */
	void removeSpWorkNodeById(String id) throws DataAccessException;

	/* @interface model: 根据工作ID和节点ID删除SpWorkNode记录 */
	void removeSpWorkNodeByWidAndNodeId(SpWorkNode spWorkNode)
			throws DataAccessException;

	/* @interface model: 根据工作ID删除全部SpWorkNode记录 */
	void removeSpWorkNodeByWid(String wid) throws DataAccessException;
	
	/* @interface model: 根据工作ID和节点查询SpWorkNode结果,返回SpWorkNode对象 */
	SpWorkNode findWorkNodeByWidAndNodeId(SpWorkNode spWorkNode) throws DataAccessException;
	
	/* @interface model: 根据工作ID查询SpWorkNode结果集,返回SpWorkNode对象的集合 */
	List<SpWorkNode> findWorkNodeListByWid(String wid) throws DataAccessException;

	/* @interface model: 查询所有SpWorkNode结果集,返回SpWorkNode对象的集合 */
	List<SpWorkNode> findWorkNodeList(SpWorkNode spWorkNode)
			throws DataAccessException;

	/* @interface model: 根据条件查询SpWorkNode结果,返回SpWorkNode对象集合 */
	List<SpWorkNode> findWorkNodeListByCondition(SpWorkNode spWorkNode)
			throws DataAccessException;

}
