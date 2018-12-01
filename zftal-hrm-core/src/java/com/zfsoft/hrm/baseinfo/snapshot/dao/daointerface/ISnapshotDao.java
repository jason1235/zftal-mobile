package com.zfsoft.hrm.baseinfo.snapshot.dao.daointerface;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.baseinfo.snapshot.entities.Snapshot;
import com.zfsoft.hrm.baseinfo.snapshot.query.SnapshotQuery;

/** 
 * 快照处理DAO
 * @ClassName: ISnapshotDao 
 * @author jinjj
 * @date 2012-7-17 上午10:05:56 
 *  
 */
public interface ISnapshotDao {

	/**
	 * 快照单个信息表数据
	 * @param snap {@link Snapshot}
	 * @return 快照数据条数
	 * @throws DataAccessException
	 */
	public int insert(Snapshot snap) throws DataAccessException;
	
	/**
	 * 删除单个信息表快照数据
	 * @param snap {@link Snapshot}
	 * @throws DataAccessException
	 */
	public void delete(Snapshot snap) throws DataAccessException;
	
	/**
	 * 快照分页
	 * @param query
	 * @return
	 */
	public List<Map<String,Object>> getPagingList(SnapshotQuery query);
	
	/**
	 * 分页计数
	 * @param query
	 * @return
	 */
	public int getPagingCount(SnapshotQuery query);
	
	/**
	 * 根据快照时间和全局ID查询单个快照
	 * @param snap
	 * @return
	 */
	public Map<String,Object> getById(Snapshot snap);
}
