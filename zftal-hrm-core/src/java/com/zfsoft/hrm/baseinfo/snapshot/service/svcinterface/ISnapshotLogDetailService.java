package com.zfsoft.hrm.baseinfo.snapshot.service.svcinterface;

import java.util.Date;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.snapshot.entities.SnapshotLogDetail;
import com.zfsoft.hrm.baseinfo.snapshot.query.SnapshotLogDetailQuery;

/** 
 * 快照日志明细service
 * @ClassName: ISnapshotLogDetailService 
 * @author jinjj
 * @date 2012-7-18 上午10:13:14 
 *  
 */
public interface ISnapshotLogDetailService {

	/**
	 * 快照日志明细分页
	 * @param query
	 * @return
	 */
	public PageList getPage(SnapshotLogDetailQuery query);
	
	/**
	 * 保存快照日志明细
	 * @param detail
	 */
	public void save(SnapshotLogDetail detail);
	
	/**
	 * 删除快照日志明细
	 * @param snapTime
	 */
	public void delete(Date snapTime);
}
