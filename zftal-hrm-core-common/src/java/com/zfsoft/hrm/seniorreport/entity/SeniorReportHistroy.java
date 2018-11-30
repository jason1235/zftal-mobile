package com.zfsoft.hrm.seniorreport.entity;


import java.util.Date;

import com.zfsoft.dao.annotation.MyBatisBean;
import com.zfsoft.dao.annotation.SQLField;
import com.zfsoft.util.date.TimeUtil;
import com.zfsoft.util.jaxb.JaxbUtil;

/**
 * 
 * @author ChenMinming
 * @date 2014-1-26
 * @version V1.0.0
 */
public class SeniorReportHistroy extends MyBatisBean{
	@SQLField(value = "cdsj")
	private Date histroyTime;//存档时间
	@SQLField(value = "CDBH", key = true)
	private String id;//存档编号
	@SQLField(value = "bbbh")
	private String reportId;//存档表单id
	@SQLField(value = "gh")
	private String gh;//存档人工号
	@SQLField(value = "bbnr")
	private String reportData;//存档内容
	
	public Date getHistroyTime() {
		return histroyTime;
	}
	public String getHistroyTimeStr() {
		return TimeUtil.getDataTime(histroyTime.getTime(), "yyyy-MM-dd(HH:mm)");
	}
	public void setHistroyTime(Date histroyTime) {
		this.histroyTime = histroyTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getGh() {
		return gh;
	}
	public void setGh(String gh) {
		this.gh = gh;
	}
	public String getReportData() {
		return reportData;
	}
	public void setReportData(String reportData) {
		this.reportData = reportData;
	}
	public SeniorReportView getView() {
		SeniorReportView view = JaxbUtil.getObjectFromXml(reportData, SeniorReportView.class);
		if (view == null) {
			return new SeniorReportView();
		}
		return view;
	}
	public void setView(SeniorReportView view) {
		this.reportData = JaxbUtil.getXmlFromObject(view);
	}

	
}
