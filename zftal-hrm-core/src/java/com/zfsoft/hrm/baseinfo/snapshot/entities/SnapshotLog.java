package com.zfsoft.hrm.baseinfo.snapshot.entities;

import java.io.Serializable;
import java.util.Date;

import com.zfsoft.util.date.DateTimeUtil;

/** 
 * 快照操作日志对象
 * @ClassName: SnapshotLog 
 * @author jinjj
 * @date 2012-7-17 上午10:29:41 
 *  
 */
public class SnapshotLog implements Serializable{

	private static final long serialVersionUID = -8177433894431309137L;

	private Date snapTime;
	
	private String operator = "system";
	
	private Date operateTime;
	
	private String guid;
	
	public String getSnapTimeStr() {
		if(snapTime==null){
			return null;
		}
		return DateTimeUtil.getFormatDate(snapTime);
	}

	/**
	 * Get日志时间戳
	 * @return 
	 */
	public Date getSnapTime() {
		return snapTime;
	}

	/**
	 * Set日志时间戳
	 * @param snapTime 
	 */
	public void setSnapTime(Date snapTime) {
		this.snapTime = snapTime;
	}

	/**
	 * Get操作人
	 * @return 
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * Set操作人
	 * @param operator 
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * Get操作时间
	 * @return 
	 */
	public Date getOperateTime() {
		return operateTime;
	}

	/**
	 * Set操作时间
	 * @param operateTime 
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
}
