package com.zfsoft.workflow.dao; 
  
 import java.util.List;

import com.zfsoft.workflow.exception.DaoException;
import com.zfsoft.workflow.model.SpNodeTask;
  
 /**  
  * 节点任务管理接口类
  *  @author Patrick Shen
  */  
 public interface ISpNodeTaskDao { 
 	/* @interface model: 添加一条SpNodeTask记录 */ 
 	void insert(SpNodeTask spNodeTask) throws DaoException; 
  
 	/* @interface model: 删除一条SpNodeTask记录 */ 
 	void delete(String nodeId,String taskId) throws DaoException; 
 	
 	/* @interface model: 删除节点下所有SpNodeTask记录 */ 
 	void deleteByNodeId(String nodeId) throws DaoException; 
 	
 	/* @interface model: 根据节点ID查询SpNodeTask结果集,返回SpNodeTask对象的集合 */ 
 	List <SpNodeTask> findNodeTaskListByNodeId(String nodeId) throws DaoException;
 	
 	/* @interface model: 根据条件查询SpNodeTask结果集,返回SpNodeTask对象的集合 */ 
 	List <SpNodeTask> findNodeTaskList(SpNodeTask spNodeTask) throws DaoException;
 	
 } 
 