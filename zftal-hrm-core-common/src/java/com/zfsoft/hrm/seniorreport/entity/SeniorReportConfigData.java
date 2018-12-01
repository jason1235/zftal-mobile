package com.zfsoft.hrm.seniorreport.entity;

import com.zfsoft.dao.annotation.MyBatisBean;
import com.zfsoft.dao.annotation.SQLField;
import com.zfsoft.util.jaxb.JaxbUtil;

/**
 * 
 * @author ChenMinming
 * @date 2013-12-24
 * @version V1.0.0
 */
public class SeniorReportConfigData extends MyBatisBean {

	@SQLField(value = "bbbh", key = true)
	private String reportId;// 报表编号
	@SQLField(value = "bbmc")
	private String reportName;// 报表名称
	@SQLField(value = "cdbh")
	private String menuId;// 所属类型
	@SQLField(value = "bbnr")
	private String content;// 报表内容

	private String sql;
	private String historySql;

	/**
	 * 返回
	 */
	public String getReportId() {
		return reportId;
	}

	/**
	 * 设置
	 * 
	 * @param reportId
	 */
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	/**
	 * 返回
	 */
	public String getReportName() {
		return reportName;
	}

	/**
	 * 设置
	 * 
	 * @param reportName
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	/**
	 * 返回
	 */
	public String getMenuId() {
		return menuId;
	}

	/**
	 * 设置
	 * 
	 * @param menuId
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * 返回
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置
	 * 
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	public SeniorReportConfig getSeniorReportConfig() {
		SeniorReportConfig config = JaxbUtil.getObjectFromXml(content, SeniorReportConfig.class);
		if (config == null) {
			return new SeniorReportConfig();
		}
		return config;
	}

	public void setSeniorReportConfig(SeniorReportConfig config) {
		this.content = JaxbUtil.getXmlFromObject(config);
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getHistorySql() {
		return historySql;
	}

	public void setHistorySql(String historySql) {
		this.historySql = historySql;
	}
}
