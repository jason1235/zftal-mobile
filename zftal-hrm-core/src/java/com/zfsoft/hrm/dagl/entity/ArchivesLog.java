package com.zfsoft.hrm.dagl.entity;

import java.util.Date;

/**
 * 
 * @author ChenMinming
 * @date 2014-10-21
 * @version V1.0.0
 */
public class ArchivesLog {
	private String id;
	private String archivesId;
	private String archiveItem;
	private Date logTime;
	private String logOperator;
	private Date operatorTime;
	private String logMessage;
	/**
	 * 返回
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置
	 * @param id 
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 返回
	 */
	public Date getLogTime() {
		return logTime;
	}
	/**
	 * 设置
	 * @param logTime 
	 */
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	/**
	 * 返回
	 */
	public String getLogOperator() {
		return logOperator;
	}
	/**
	 * 设置
	 * @param logOperator 
	 */
	public void setLogOperator(String logOperator) {
		this.logOperator = logOperator;
	}
	/**
	 * 返回
	 */
	public Date getOperatorTime() {
		return operatorTime;
	}
	/**
	 * 设置
	 * @param operatorTime 
	 */
	public void setOperatorTime(Date operatorTime) {
		this.operatorTime = operatorTime;
	}
	/**
	 * 返回
	 */
	public String getLogMessage() {
		return logMessage;
	}
	/**
	 * 设置
	 * @param logMessage 
	 */
	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}
	/**
	 * 返回
	 */
	public String getArchivesId() {
		return archivesId;
	}
	/**
	 * 设置
	 * @param archivesId 
	 */
	public void setArchivesId(String archivesId) {
		this.archivesId = archivesId;
	}
	/**
	 * 返回
	 */
	public String getArchiveItem() {
		return archiveItem;
	}
	/**
	 * 设置
	 * @param archiveItem 
	 */
	public void setArchiveItem(String archiveItem) {
		this.archiveItem = archiveItem;
	}
	
	
}
