package com.zfsoft.hrm.summary.roster.entity;


/** 
 * 花名册字段
 * @author jinjj
 * @date 2012-9-10 下午04:28:20 
 *  
 */
public class RosterColumn {

	private String classId;
	
	private String columnId;
	
	private String rosterId;
	
	private String order="";
	
	private String sort="";
	
	private String name;

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getRosterId() {
		return rosterId;
	}

	public void setRosterId(String rosterId) {
		this.rosterId = rosterId;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * 字段名称
	 * @return
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
