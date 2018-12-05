/**
 *
 */
package com.zfsoft.sjzx.jhpz.swhpz.model;

/**
 * @author Administrator 工作组日志详情
 */
public class GzzLogXq {

	private String startDate; // 开始时间

	private String endDate; // 结束日期

	private Integer logId; // 日志ID

	private String content; // 日志内容

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the logId
	 */
	public Integer getLogId() {
		return logId;
	}

	/**
	 * @param logId
	 *            the logId to set
	 */
	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
}
