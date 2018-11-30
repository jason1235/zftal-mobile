package com.zfsoft.hrm.manoeuvre.configInfo.dao.daointerface;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.manoeuvre.configInfo.entities.TaskNode;
import com.zfsoft.hrm.manoeuvre.configInfo.query.TaskNodeQuery;


/**
 * 审核环节节点信息dao
 * @author yongjun.fang
 *
 */
public interface ITaskNodeDao {
	/**
	 * 添加审核环节节点信息
	 * @param info
	 * @return
	 * @throws DataAccessException
	 */
	public int insert(TaskNode info) throws DataAccessException;
	
	/**
	 * 修改审核环节节点信息
	 * @param info
	 * @return
	 * @throws DataAccessException
	 */
	public int update(TaskNode info) throws DataAccessException;
	
	/**
	 * 删除审核环节节点信息
	 * @param nid
	 * @throws DataAccessException
	 */
	public void delete(String nid) throws DataAccessException;
	
	/**
	 * 查询审核环节节点信息列表
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public List<TaskNode> findList(TaskNodeQuery query) throws DataAccessException;
	
	/**
	 * 分页查询审核环节节点信息列表
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public List<TaskNode> findPage(TaskNodeQuery query) throws DataAccessException;
	
	/**
	 * 根据id查找审核环节节点信息
	 * @param nid
	 * @return
	 * @throws DataAccessException
	 */
	public TaskNode findById(String nid) throws DataAccessException;
	
	/**
	 * 分页计数
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public int findPageCount(TaskNodeQuery query) throws DataAccessException;
	
	/**
	 * 查询指定进修类型下，最终审核环节列表
	 * @return
	 * @throws DataAccessException
	 */
	public TaskNode findLastNode() throws DataAccessException;
	
	/**
	 * 查询指定进修类型下，最初审核环节列表
	 * @return
	 * @throws DataAccessException
	 */
	public TaskNode findFirstNode() throws DataAccessException;
	
	/**
	 * 指定顺序码target所在数据以下的全部数据顺序码加一
	 * @param target
	 * @return
	 * @throws DataAccessException
	 */
	public int plusOne(long target) throws DataAccessException;
	
	/**
	 * 指定顺序码target所在数据以下到顺序码为orgin的数据之前(不包括orgin)的全部数据顺序码加一
	 * @param target
	 * @param orgin
	 * @return
	 * @throws DataAccessException
	 */
	public int partPlusOne(long target, long orgin) throws DataAccessException;
	
	/**
	 * 指定顺序码orgin所在数据以下(不包括orgin)的全部数据顺序码减一
	 * @param orgin
	 * @return
	 * @throws DataAccessException
	 */
	public int reduceOne(long orgin) throws DataAccessException;
	
	/**
	 * 指定顺序码orgin所在数据以下(不包括orgin)到顺序码为target的数据之前的全部数据顺序码减一
	 * @param orgin
	 * @param target
	 * @return
	 * @throws DataAccessException
	 */
	public int partReduceOne(long orgin, long target) throws DataAccessException;
	
	/**
	 * 查找指定节点的下一节点
	 * @param nid
	 * @return
	 * @throws DataAccessException
	 */
	public List<TaskNode> findNext(String nid) throws DataAccessException;
	
	/**
	 * 查找指定节点的上一节点
	 * @param nid
	 * @return
	 * @throws DataAccessException
	 */
	public List<TaskNode> findPrev(String nid) throws DataAccessException;
}
