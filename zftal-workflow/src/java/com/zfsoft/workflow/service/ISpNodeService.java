package com.zfsoft.workflow.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.zfsoft.workflow.exception.DaoException;
import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpNode;

/**
 * 节点管理service接口类
 * 
 * @version 3.2.0
 */
public interface ISpNodeService extends BaseInterface {
	/* @interface model: 添加一条SpNode记录 */
	void insert(SpNode spNode) throws WorkFlowException;

	/* @interface model: 更新一条SpNode记录 */
	void update(SpNode spNode) throws WorkFlowException;

	/* @interface model: 删除一条SpNode记录 */
	void delete(String nodeId) throws WorkFlowException;

	/* @interface model: 删除流程下所有SpNode记录 */
	void deleteByPid(String pId) throws DaoException;

	/* @interface model: 根据连线中的下个节点ID查询连线的上个节点集合 */
	List<SpNode> findNodeListByDnodeId(String dnodeId) throws DataAccessException;

	/* @interface model: 根据连线中的下个节点ID查询连线的上个节点 */
	List<SpNode> findNodeByDnodeId(String dnodeId) throws DataAccessException;

	/* @interface model: 根据连线中的上个节点ID查询连线的下个节点集合 */
	List<SpNode> findNodeListByUnodeId(String uNodeId) throws DataAccessException;

	/* @interface model: 根据连线中的上个节点ID查询连线的下个节点 */
	List<SpNode> findNodeByUnodeId(String uNodeId) throws DataAccessException;

	/* @interface model: 查询所有SpNode结果集,返回SpNode对象的集合 */
	List<SpNode> findNodeList(SpNode spNode) throws WorkFlowException;

	SpNode findNodeById(String string);
	
	void insert(SpNode spNode, Map<String,String[]> map);

	void update(SpNode spNode, Map<String,String[]> map);
}
