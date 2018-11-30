package com.zfsoft.hrm.baseinfo.snapshot.service.svcinterface;

import java.util.Date;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.snapshot.entities.Snapshot;
import com.zfsoft.hrm.baseinfo.snapshot.query.SnapshotQuery;

/** 
 * 快照service
 * @ClassName: ISnapshotService 
 * @author jinjj
 * @date 2012-7-18 上午10:06:08 
 *  
 */
public interface ISnapshotService {

	/**
	 * 执行快照操作
	 * @param snapTime 快照时间
	 */
	public void doSnapshot(Date snapTime);
	
	/**
	 * 移除快照数据
	 * @param snapTime 快照时间
	 */
	public void delete(Date snapTime);
	
	public PageList<Snapshot> getPagingList(SnapshotQuery query);
}
