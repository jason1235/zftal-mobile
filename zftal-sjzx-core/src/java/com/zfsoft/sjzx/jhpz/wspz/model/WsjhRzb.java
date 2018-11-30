package com.zfsoft.sjzx.jhpz.wspz.model;

import com.zfsoft.framework.page.BaseDataObject;

public class WsjhRzb extends BaseDataObject{

	private static final long serialVersionUID = 3918724803327967936L;
	private Integer id;// ID���
	private Integer readLines;// ��ȡ��
	private Integer updateLines;// ������
	private Integer errors;// ������
	private String startDate;// ��ʼʱ��
	private String endDate;// ����ʱ��
	private String runTime;//����ʱ��
	private String logDate;// ��¼ʱ��
	private String log;// ������־
	private Integer wsjhxh;//WebService �������
	private String sjc;//ʱ��ر��
	private String failid="";//����ʧ��ID�����ڱ��ͣ�
	private String tempTime;//��ʱʱ��
	
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
