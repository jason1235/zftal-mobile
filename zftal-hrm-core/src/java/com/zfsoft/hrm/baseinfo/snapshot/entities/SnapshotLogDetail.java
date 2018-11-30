package com.zfsoft.hrm.baseinfo.snapshot.entities;

import java.io.Serializable;
import java.util.Date;

import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;

/** 
 * 快照日志详细信息实体
 * @ClassName: SnapshotLogDetail 
 * @author jinjj
 * @date 2012-7-17 上午10:31:04 
 *  
 */
public class SnapshotLogDetail implements Serializable{

	private static final long serialVersionUID = 3576227196700305976L;

	private String guid;
	
	private Date snapTime;
	
	private InfoClass clazz;
	
	private int tableSize;
	
	private Date operateTime;
	
	private String logId;

	/**
	 * Get数据时间戳
	 * @return 
	 */
	public Date getSnapTime() {
		return snapTime;
	}

	/**
	 * Set数据时间戳
	 * @param timestamp 
	 */
	public void setSnapTime(Date snapTime) {
		this.snapTime = snapTime;
	}


	/**
	 * Get信息类
	 */
	public InfoClass getClazz() {
		return clazz;
	}

	/**
	 * Set信息类
	 */
	public void setClazz(InfoClass clazz) {
		this.clazz = clazz;
	}

	/**
	 * Get数据表数据量
	 * @return 
	 */
	public int getTableSize() {
		return tableSize;
	}

	/**
	 * Set数据表数据量
	 * @param tableSize 
	 */
	public void setTableSize(int tableSize) {
		this.tableSize = tableSize;
	}

	/**
	 * Get日志记录时间
	 * @return 
	 */
	public Date getOperateTime() {
		return operateTime;
	}

	/**
	 * Set日志记录时间
	 * @param createTime 
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * Get唯一主键
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * Set唯一主键
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * Get快照日志ID
	 */
	public String getLogId() {
		return logId;
	}

	/**
	 * Set快照日志ID
	 */
	public void setLogId(String logId) {
		this.logId = logId;
	}
	
}
