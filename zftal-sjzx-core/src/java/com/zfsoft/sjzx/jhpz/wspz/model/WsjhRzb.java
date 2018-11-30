package com.zfsoft.sjzx.jhpz.wspz.model;

import com.zfsoft.framework.page.BaseDataObject;

public class WsjhRzb extends BaseDataObject{

	private static final long serialVersionUID = 3918724803327967936L;
	private Integer id;// ID编号
	private Integer readLines;// 读取数
	private Integer updateLines;// 更新数
	private Integer errors;// 错误数
	private String startDate;// 起始时间
	private String endDate;// 结束时间
	private String runTime;//运行时间
	private String logDate;// 记录时间
	private String log;// 交换日志
	private Integer wsjhxh;//WebService 交换编号
	private String sjc;//时间截标记
	private String failid="";//更新失败ID（用于报送）
	private String tempTime;//临时时间
	
	public String getTempTime() {
		return tempTime;
	}

	public void setTempTime(String tempTime) {
		this.tempTime = tempTime;
	}

	public String getFailid() {
		return failid;
	}

	public void setFailid(String failid) {
		this.failid = failid;
	}

	public String getSjc() {
		return sjc;
	}

	public void setSjc(String sjc) {
		this.sjc = sjc;
	}

	public Integer getWsjhxh() {
		return wsjhxh;
	}

	public void setWsjhxh(Integer wsjhxh) {
		this.wsjhxh = wsjhxh;
	}

	public String getRunTime() {
		return runTime;
	}

	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getReadLines() {
		return readLines;
	}

	public void setReadLines(Integer readLines) {
		this.readLines = readLines;
	}

	public Integer getUpdateLines() {
		return updateLines;
	}

	public void setUpdateLines(Integer updateLines) {
		this.updateLines = updateLines;
	}

	public Integer getErrors() {
		return errors;
	}

	public void setErrors(Integer errors) {
		this.errors = errors;
	}


	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getLogDate() {
		return logDate;
	}

	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}


}
