package com.zfsoft.hrm.baseinfo.snapshot.service.svcinterface;

import java.util.Date;
import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.snapshot.entities.SnapshotLog;
import com.zfsoft.hrm.baseinfo.snapshot.query.SnapshotLogQuery;

/** 
 * 快照操作日志service
 * @ClassName: ISnapshotLogService 
 * @author jinjj
 * @date 2012-7-18 上午10:10:00 
 *  
 */
public interface ISnapshotLogService {

	/**
	 * 快照日志分页查询
	 * @param query
	 * @return
	 */
	public PageList getPage(SnapshotLogQuery query);
	
	/**
	 * 保存快照日志
	 * @param log
	 */
	public void save(SnapshotLog log);
	
	/**
	 * 获取快照日志集合
	 * @param query
	 * @return
	 */
	public List<SnapshotLog> getList(SnapshotLogQuery query);
	
	/**
	 * 删除快照日志
	 * @param snapTime
	 */
	public void delete(Date snapTime);
}
