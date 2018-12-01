package com.zfsoft.hrm.baseinfo.snapshot.dao.daointerface;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.baseinfo.snapshot.entities.SnapshotLogDetail;
import com.zfsoft.hrm.baseinfo.snapshot.query.SnapshotLogDetailQuery;

/** 
 * 快照日志明细DAO
 * @ClassName: ISnapshotLogDetailDao 
 * @author jinjj
 * @date 2012-7-17 上午11:07:01 
 *  
 */
public interface ISnapshotLogDetailDao {

	/**
	 * 插入快照操作日志明细
	 * @param detail
	 * @throws DataAccessException
	 */
	public void insert(SnapshotLogDetail detail) throws DataAccessException;
	
	/**
	 * 获取快照日志明细分页列表
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public List<SnapshotLogDetail> getPagingList(SnapshotLogDetailQuery query)throws DataAccessException;
	
	/**
	 * 获取快照日志明细计数
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public int getPagingCount(SnapshotLogDetailQuery query) throws DataAccessException;
	
	/**
	 * 删除快照日志明细
	 * @param query
	 * @throws DataAccessException
	 */
	public void delete(SnapshotLogDetailQuery query) throws DataAccessException;
}
