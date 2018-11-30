
package com.zfsoft.hrm.report.entity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.zfsoft.dao.annotation.MyBatisBean;
import com.zfsoft.dao.annotation.SQLField;

/**
 * 报表数据库映射对象
 * @author 沈鲁威 Patrick Shen
 * @since 2012-9-12
 * @version V1.0.0
 */
public class ReportXmlFile extends MyBatisBean{
	
	@SQLField(value="bbbh",key=true)
	private String reportId;//报表编号
	@SQLField(value="bbmc")
	private String reportName;//报表名称
	@SQLField(value="cdbh")
	private String menuId;//菜单编号
	@SQLField(value="bbnr")
	private byte[] content;//报表内容
	
	@SQLField(value="xqpz")
	private String fieldConfig;
	
	private String sql;
	private String historySql;
	private String sftjqxfw;     //是否添加权限范围
	
	public String getSftjqxfw() {
		return sftjqxfw;
	}
	public void setSftjqxfw(String sftjqxfw) {
		this.sftjqxfw = sftjqxfw;
	}
	public String getHistorySql() {
		return historySql;
	}
	public void setHistorySql(String historySql) {
		this.historySql = historySql;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public InputStream getContentStream(){
		if(content==null){
			return null;
		}
		ByteArrayInputStream bStream=new ByteArrayInputStream(content);
		return bStream;
	}
	/**
	 * 返回
	 */
	public String getFieldConfig() {
		return fieldConfig;
	}
	/**
	 * 设置
	 * @param fieldConfig 
	 */
	public void setFieldConfig(String fieldConfig) {
		this.fieldConfig = fieldConfig;
	}
	
	
	
}
