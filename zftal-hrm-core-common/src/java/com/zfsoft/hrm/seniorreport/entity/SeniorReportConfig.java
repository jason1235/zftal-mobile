package com.zfsoft.hrm.seniorreport.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * 表单配置xml对象
 * @author ChenMinming
 * @date 2013-12-24
 * @version V1.0.0
 */
@XmlRootElement(name = "content")
public class SeniorReportConfig {

	//标题
	private String title;

	private String startTitle = "项目";
	//数据来源
	private String sql;
	//历史数据来源
	private String historySql;
	//列标题（表头对象）
	private List<ViewItem> titles = new ArrayList<ViewItem>();
	//行标题
	private List<ViewItem> left = new ArrayList<ViewItem>();;

	@XmlElement(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@XmlElement(name = "stitle")
	public String getStartTitle() {
		return startTitle;
	}

	public void setStartTitle(String startTitle) {
		this.startTitle = startTitle;
	}

	@XmlElement(name = "sql")
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	@XmlElement(name = "historySql")
	public String getHistorySql() {
		return historySql;
	}

	public void setHistorySql(String historySql) {
		this.historySql = historySql;
	}
	
	@XmlElement(name = "viewItem")
	@XmlElementWrapper(name = "titles")
	public List<ViewItem> getTitles() {
		return titles;
	}

	public void setTitles(List<ViewItem> titles) {
		this.titles = titles;
	}

	@XmlElement(name = "viewItem")
	@XmlElementWrapper(name = "left")
	public List<ViewItem> getLeft() {
		return left;
	}

	public void setLeft(List<ViewItem> left) {
		this.left = left;
	}
	
	public ViewItem findViewItemById(String id,boolean isTitles)
	{
		ViewItem pItem = new ViewItem();
		if(isTitles)
			pItem.setSubItem(titles);
		else
			pItem.setSubItem(left);
		return findViewItem(pItem, id);
	}
	
	private ViewItem findViewItem(ViewItem pItem,String id){
		if(pItem.getSubItem() == null || pItem.getSubItem().isEmpty()){
			return null;
		}
		for (ViewItem viewItem : pItem.getSubItem()) {
			if(viewItem.getItemId().equals(id)){
				viewItem.setpItem(pItem);
				return viewItem;
			}
			ViewItem i = findViewItem(viewItem, id);
			if(i!=null) return i;
		}
		return null;
	}

	


}
