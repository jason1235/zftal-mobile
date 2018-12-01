package com.zfsoft.workflow.dao; 
  
 import java.util.List;

import com.zfsoft.workflow.exception.DaoException;
import com.zfsoft.workflow.model.SpNode;
  
 /**  
  * 代码自动生成(bibleUtil auto code generation) 
  * @version 3.2.0  
  */  
 public interface ISpNodeDao { 
 	/* @interface model: 添加一条SpNode记录 */ 
 	void insert(SpNode spNode) throws DaoException; 
  
 	/* @interface model: 更新一条SpNode记录 */ 
 	void update(SpNode spNode) throws DaoException; 
  
 	/* @interface model: 删除一条SpNode记录 */ 
 	void delete(String nodeId) throws DaoException; 
 	
 	/* @interface model: 删除流程下所有SpNode记录 */ 
 	void deleteByPid(String pId) throws DaoException; 
 	
 	/* @interface model: 根据节点ID删除关联的任务记录 */ 
 	void deleteTaskByNodeId(String nodeId) throws DaoException; 
 	
 	/* @interface model: 根据条件查询是否有SpNode记录 */
	int getCountByNodeNameAndPid(SpNode spNode) throws DaoException;
	
	/* @interface model: 查询所有SpNode结果集,返回SpNode对象的集合 */ 
 	List <SpNode> findNodeListByPid(String pId) throws DaoException; 
	
 	/* @interface model: 查询所有SpNode结果集,返回SpNode对象的集合 */ 
 	List <SpNode> findNodeList(SpNode spNode) throws DaoException;

	SpNode findNodeById(String nodeId); 
 	
 } 
 