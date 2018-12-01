package com.zfsoft.hrm.baseinfo.snapshot.query;

import java.util.Date;

import com.zfsoft.dao.query.BaseQuery;

/** 
 * 快照日志明细查询实体
 * @ClassName: SnapshotLogDetailQuery 
 * @author jinjj
 * @date 2012-7-17 上午11:02:21 
 *  
 */
public class SnapshotLogDetailQuery extends BaseQuery {

	private static final long serialVersionUID = -2635521914087246975L;
	private final static int pageSize = 20;
	
	public SnapshotLogDetailQuery(){
		setPerPageSize(pageSize);
	}
	
	private Date snapTime;

	/**
	 * Get快照时间戳
	 * @return 
	 */
	public Date getSnapTime() {
		return snapTime;
	}

	/**
	 * Set快照时间戳
	 * @param timestamp 
	 */
	public void setSnapTime(Date snapTime) {
		this.snapTime = snapTime;
	}
	
}
