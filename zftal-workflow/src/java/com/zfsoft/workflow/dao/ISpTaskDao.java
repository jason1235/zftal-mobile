package com.zfsoft.workflow.dao; 
  
 import java.util.List;

import com.zfsoft.workflow.exception.DaoException;
import com.zfsoft.workflow.model.SpTask;
  
 /**  
  * 任务管理接口类
  *  
  * @version 3.2.0  
  */  
 public interface ISpTaskDao { 
 	/* @interface model: 添加一条SpTask记录 */ 
 	void insert(SpTask spTask) throws DaoException; 
  
 	/* @interface model: 更新一条SpTask记录 */ 
 	void update(SpTask spTask) throws DaoException; 
  
 	/* @interface model: 删除一条SpTask记录 */ 
 	void delete(String taskId) throws DaoException; 
 	
 	/* @interface model: 删除流程下所有SpTask记录 */ 
 	void deleteByTaskId(String taskId) throws DaoException; 
 	
 	/* @interface model: 根据条件查询是否有SpTask记录 */
	int getCountByTaskNameAndTaskType(SpTask spTask) throws DaoException;
	
	SpTask findTaskListById(String id) throws DaoException;
  
 	/* @interface model: 根据节点ID查询SpTask结果集,返回SpTask对象的集合 */ 
 	List <SpTask> findTaskListByNodeId(String nodeId) throws DaoException;
 	
 	/* @interface model: 根据条件查询SpTask结果集,返回SpTask对象的集合 */ 
 	List <SpTask> findTaskList(SpTask spTask) throws DaoException;
 	
 } 
 