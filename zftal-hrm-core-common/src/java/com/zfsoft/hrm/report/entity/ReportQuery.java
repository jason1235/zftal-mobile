package com.zfsoft.hrm.report.entity;

import com.zfsoft.dao.query.BaseQuery;

/**
 * 报表查询条件数据库查询时使用
 * @author 沈鲁威 Patrick Shen
 * @since 2012-9-13
 * @version V1.0.0
 */
public class ReportQuery extends BaseQuery{
	private static final long serialVersionUID = 6013998169879688954L;
	private String sql;
	private String columnCondition;
	private String rowCondition;
	private String snapTime;
	
	public String getSnapTime() {
		return snapTime;
	}
	public void setSnapTime(String snapTime) {
		this.snapTime = snapTime;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getColumnCondition() {
		return columnCondition;
	}
	public void setColumnCondition(String columnCondition) {
		this.columnCondition = columnCondition;
	}
	public String getRowCondition() {
		return rowCondition;
	}
	public void setRowCondition(String rowCondition) {
		this.rowCondition = rowCondition;
	}
	
}
