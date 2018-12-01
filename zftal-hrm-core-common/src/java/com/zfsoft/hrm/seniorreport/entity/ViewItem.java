package com.zfsoft.hrm.seniorreport.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.zfsoft.hrm.seniorreport.enums.ViewItemType;

/**
 * 
 * @author ChenMinming
 * @date 2013-12-23
 * @version V1.0.0
 */
@XmlRootElement(name="viewItem")
public class ViewItem {
	private List<ViewItem> subItem;//子对象
	private String viewType;
	private String defultValue;
	private String name;//界面显示用
	private String itemId;//唯一id
	private String sql = "1=1";
	private String dataSql;
	private String joinSql="data.gh=report.gh";
	private ViewItem pItem;
	
	@XmlElement(name = "viewItem")
	@XmlElementWrapper(name = "subItem")
	public List<ViewItem> getSubItem() {
		return subItem;
	}
	
	public void setSubItem(List<ViewItem> subItem) {
		this.subItem = subItem;
	}
	
	@XmlAttribute
	public String getViewType() {
		for (ViewItemType v : ViewItemType.values()) {
			if(v.getKey().equals(viewType))
				return viewType;
		}
		viewType = ViewItemType.COUNT_TYPE.getKey();
		return viewType;
	}
	
	public void setViewType(String viewType) {
		this.viewType = viewType;
	}
	
	@XmlAttribute
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getNum() {
		if((subItem==null)||subItem.isEmpty())
			return 1;
		int num = 0;
		for (ViewItem item : subItem) {
			num+=item.getNum();
		}
		return num;
	}
	
	@XmlAttribute
	public String getItemId() {
		return itemId;
	}
	
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	@XmlAttribute
	public String getDefultValue() {
		return defultValue;
	}

	public void setDefultValue(String defultValue) {
		this.defultValue = defultValue;
	}
	
	@XmlAttribute
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	@XmlAttribute
	public String getDataSql() {
		return dataSql;
	}

	public void setDataSql(String dataSql) {
		this.dataSql = dataSql;
	}
	@XmlAttribute
	public String getJoinSql() {
		return joinSql;
	}

	public void setJoinSql(String joinSql) {
		this.joinSql = joinSql;
	}

	@XmlTransient
	public ViewItem getpItem() {
		return pItem;
	}

	public void setpItem(ViewItem pItem) {
		this.pItem = pItem;
	}

	
}
