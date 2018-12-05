/**
 *
 */
package com.zfsoft.sjzx.jhpz.swhpz.model;

import com.zfsoft.framework.page.BaseDataObject;

/**
 * @author Administrator 工作组日志配置
 */
public class GzzLog extends BaseDataObject {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;

	private Integer logId; // 日志Id

	private String pzmc; // 配置名称

	private String startDate; // 开始时间

	private String endDate; // 结束时间

	private int xhsj; // 消耗时间

	private Integer readLines; // 读取数据

	private Integer writtenLines; // 写入数据

	private Integer updateLines; // 更新数据

	private Integer outputLines; // 增加数据

	private Integer gzzbh; // 对应的工作组编号

	private Integer pzxh; // 配置序号
	private Integer errors;

	public void setErrors(Integer errors){
		this.errors = errors;
	}

	public Integer getErrors(){
		return this.errors;
	}

	/**
	 * @return the logId
	 */
	public Integer getLogId() {
		return logId;
	}

	/**
	 * @param logId the logId to set
	 */
	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	/**
	 * @return the pzmc
	 */
	public String getPzmc() {
		return pzmc;
	}

	/**
	 * @param pzmc the pzmc to set
	 */
	public void setPzmc(String pzmc) {
		this.pzmc = pzmc;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
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
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the xhsj
	 */
	public int getXhsj() {
		return xhsj;
	}

	/**
	 * @param xhsj the xhsj to set
	 */
	public void setXhsj(int xhsj) {
		this.xhsj = xhsj;
	}

	/**
	 * @return the readLines
	 */
	public Integer getReadLines() {
		return readLines;
	}

	/**
	 * @param readLines the readLines to set
	 */
	public void setReadLines(Integer readLines) {
		this.readLines = readLines;
	}

	/**
	 * @return the writtenLines
	 */
	public Integer getWrittenLines() {
		return writtenLines;
	}

	/**
	 * @param writtenLines the writtenLines to set
	 */
	public void setWrittenLines(Integer writtenLines) {
		this.writtenLines = writtenLines;
	}

	/**
	 * @return the updateLines
	 */
	public Integer getUpdateLines() {
		return updateLines;
	}

	/**
	 * @param updateLines the updateLines to set
	 */
	public void setUpdateLines(Integer updateLines) {
		this.updateLines = updateLines;
	}

	/**
	 * @return the outputLines
	 */
	public Integer getOutputLines() {
		return outputLines;
	}

	/**
	 * @param outputLines the outputLines to set
	 */
	public void setOutputLines(Integer outputLines) {
		this.outputLines = outputLines;
	}

	/**
	 * @return the gzzbh
	 */
	public Integer getGzzbh() {
		return gzzbh;
	}

	/**
	 * @param gzzbh the gzzbh to set
	 */
	public void setGzzbh(Integer gzzbh) {
		this.gzzbh = gzzbh;
	}

	/**
	 * @return the pzxh
	 */
	public Integer getPzxh() {
		return pzxh;
	}

	/**
	 * @param pzxh the pzxh to set
	 */
	public void setPzxh(Integer pzxh) {
		this.pzxh = pzxh;
	}

}
