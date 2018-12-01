package com.zfsoft.hrm.authpost.post.dao.daointerface;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.authpost.post.entities.PostHistoryLog;
import com.zfsoft.hrm.authpost.post.query.PostHistoryLogQuery;

/** 
 * 历史岗位快照日志DAO
 * @author jinjj
 * @date 2012-7-25 下午06:23:27 
 *  
 */
public interface IPostHistoryLogDao {

	/**
	 * 插入快照操作日志
	 * @param log
	 * @throws DataAccessException
	 */
	public void insert(PostHistoryLog log) throws DataAccessException;
	
	/**
	 * 获取快照分页列表
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public List<PostHistoryLog> getPagingList(PostHistoryLogQuery query)throws DataAccessException;
	
	/**
	 * 获取快照日志计数
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public int getPagingCount(PostHistoryLogQuery query) throws DataAccessException;
	
	/**
	 * 获取快照日志集合
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public List<PostHistoryLog> getList(PostHistoryLogQuery query)throws DataAccessException;
	
	/**
	 * 删除快照日志
	 * @param query
	 * @throws DataAccessException
	 */
	public void delete(PostHistoryLogQuery query) throws DataAccessException;
}
