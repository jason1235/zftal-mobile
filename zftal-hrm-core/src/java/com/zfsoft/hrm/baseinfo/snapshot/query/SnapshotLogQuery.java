package com.zfsoft.hrm.baseinfo.snapshot.query;

import java.util.Date;

import com.zfsoft.dao.query.BaseQuery;

/** 
 * 快照日志查询实体
 * @ClassName: SnapshotLogQuery 
 * @author jinjj
 * @date 2012-7-17 上午10:59:40 
 *  
 */
public class SnapshotLogQuery extends BaseQuery {

	private static final long serialVersionUID = -3298815356198248923L;
	private final static int pageSize = 20;
	
	private Date snapTime;
	
	public SnapshotLogQuery(){
		setPerPageSize(pageSize);
	}

	/**
	 * 快照时间
	 * @return
	 */
	public Date getSnapTime() {
		return snapTime;
	}

	/**
	 * 快照时间
	 * @param snapTime
	 */
	public void setSnapTime(Date snapTime) {
		this.snapTime = snapTime;
	}
	
}
