package com.zfsoft.hrm.baseinfo.snapshot.dao.daointerface;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.baseinfo.snapshot.entities.SnapshotLog;
import com.zfsoft.hrm.baseinfo.snapshot.query.SnapshotLogQuery;

/** 
 * 快照操作日志DAO
 * @ClassName: ISnapshotLogDao 
 * @author jinjj
 * @date 2012-7-17 上午10:28:07 
 *  
 */
public interface ISnapshotLogDao {

	/**
	 * 插入快照操作日志
	 * @param log
	 * @throws DataAccessException
	 */
	public void insert(SnapshotLog log) throws DataAccessException;
	
	/**
	 * 获取快照分页列表
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public List<SnapshotLog> getPagingList(SnapshotLogQuery query)throws DataAccessException;
	
	/**
	 * 获取快照日志计数
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public int getPagingCount(SnapshotLogQuery query) throws DataAccessException;
	
	/**
	 * 获取快照日志集合
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public List<SnapshotLog> getList(SnapshotLogQuery query)throws DataAccessException;
	
	/**
	 * 删除快照日志
	 * @param query
	 * @throws DataAccessException
	 */
	public void delete(SnapshotLogQuery query) throws DataAccessException;
}
