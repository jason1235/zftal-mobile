package com.zfsoft.workflow.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.workflow.model.SpWorkTask;

/**
 * 
 * 类描述：工作审核任务接口类
 * @author: <a href="mailto:fanyingjie@126.com">范英杰</a>
 * @since: 2013-3-12 下午06:25:37 
 * @version: 1.0
 */
public interface ISpWorkTaskDao {
	/* @interface model: 添加一条SpWorkTask记录 */
	void addSpWorkTask(SpWorkTask spWorkTask) throws DataAccessException;

	/* @interface model: 删除一条SpWorkTask记录 */
	void removeSpWorkTaskById(String id) throws DataAccessException;

	/* @interface model: 删除SpWorkTask记录 */
	void removeSpWorkTask(SpWorkTask spWorkTask) throws DataAccessException;
	
	/* @interface model: 删除SpWorkTask记录 */
	void removeSpWorkTaskByWid(String wid) throws DataAccessException;
	
	/* @interface model: 执行任务后，更新任务信息 */
	void editSpWorkTask(SpWorkTask spWorkTask) throws DataAccessException;
	
	/* @interface model: 根据任务ID和工作ID查询SpWorkTask结果,返回SpWorkTask对象 */
	SpWorkTask findWorkNodeByWidAndTaskId(SpWorkTask spWorkTask) throws DataAccessException;

	/* @interface model: 根据NODE_ID查询SpWorkTask结果集,返回SpWorkTask对象的集合 */
	List<SpWorkTask> findWorkTaskListByNodeIdAndBid(SpWorkTask spWorkTask) throws DataAccessException;
	
	/* @interface model: 查询SpWorkTask结果集,返回SpWorkTask对象的集合 */
	List<SpWorkTask> findWorkTaskList(SpWorkTask spWorkTask) throws DataAccessException;

	/**
	 * 查询某节点执行状态为非 已执行 的计数
	 * @param wt
	 * @return
	 * @throws DataAccessException
	 */
	int countWorkTaskForNonExecute(SpWorkTask wt) throws DataAccessException;
	
	/* @interface model: 根据工作ID查询SpWorkTask结果集,返回SpWorkTask对象的集合 */
	List<SpWorkTask> findWorkNodeListByWId(String wId) throws DataAccessException;
}
