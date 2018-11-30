package com.zfsoft.hrm.report.xentity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * 报表配置内容实体 
 * @author 沈鲁威 Patrick Shen
 * @since 2012-9-13
 * @version V1.0.0
 */
@XmlRootElement(name="content")
public class ReportContent{
	private String title;
	
	private String startTitle="项目";
	
	private String sql;
	
	private String historySql;
	
	private String sftjqxfw;
	
	public String getSftjqxfw() {
		return sftjqxfw;
	}

	public void setSftjqxfw(String sftjqxfw) {
		this.sftjqxfw = sftjqxfw;
	}

	private List<Item> columns=new ArrayList<Item>();
	
	private List<Item> rows=new ArrayList<Item>();;
	
	@XmlElement(name="title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@XmlElement(name="stitle")
	public String getStartTitle() {
		return startTitle;
	}

	public void setStartTitle(String startTitle) {
		this.startTitle = startTitle;
	}
	
	@XmlElement(name="sql")
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	
	@XmlElement(name="historySql")
	public String getHistorySql() {
		return historySql;
	}

	public void setHistorySql(String historySql) {
		this.historySql = historySql;
	}

	@XmlElement(name="item")
	@XmlElementWrapper(name="columns")
	public List<Item> getColumns() {
		return columns;
	}

	public void setColumns(List<Item> columns) {
		this.columns = columns;
	}
	@XmlElement(name="item")
	@XmlElementWrapper(name="rows")
	public List<Item> getRows() {
		return rows;
	}

	public void setRows(List<Item> rows) {
		this.rows = rows;
	}

	public Item getColumn(String fieldName) {
		for (Item item : columns) {
			if(fieldName.equals(item.getFieldName())){
				return item;
			}
		}
		return null;
	} 
	public Item getRow(String fieldName) {
		for (Item item : rows) {
			if(fieldName.equals(item.getFieldName())){
				return item;
			}
		}
		return null;
	}

	public void columnLeft(String fieldName) {
		Item item;
		Item swap;
		for (int i = 0; i < columns.size(); i++) {
			item=columns.get(i);
			if(fieldName.equals(item.getFieldName())){
				if(i==0){
					return;
				}else{
					swap=columns.get(i-1);
					columns.set(i-1, item);
					columns.set(i, swap);
					break;
				}
			}
		}
	}

	public void columnRight(String fieldName) {
		Item item;
		Item swap;
		for (int i = 0; i < columns.size(); i++) {
			item=columns.get(i);
			if(fieldName.equals(item.getFieldName())){
				if(i==columns.size()-1){
					return;
				}else{
					swap=columns.get(i+1);
					columns.set(i+1, item);
					columns.set(i, swap);
					break;
				}
			}
		}
		
	}

	public void rowUp(String fieldName) {
		Item item;
		Item swap;
		for (int i = 0; i < rows.size(); i++) {
			item=rows.get(i);
			if(fieldName.equals(item.getFieldName())){
				if(i==0){
					return;
				}else{
					swap=rows.get(i-1);
					rows.set(i-1, item);
					rows.set(i, swap);
					break;
				}
			}
		}
	}

	public void rowDown(String fieldName) {
		Item item;
		Item swap;
		for (int i = 0; i < rows.size(); i++) {
			item=rows.get(i);
			if(fieldName.equals(item.getFieldName())){
				if(i==rows.size()-1){
					return;
				}else{
					swap=rows.get(i+1);
					rows.set(i+1, item);
					rows.set(i, swap);
					break;
				}
			}
		}
	} 
	
}
