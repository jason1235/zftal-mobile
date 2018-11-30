package com.zfsoft.hrm.seniorreport.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.zfsoft.util.base.StringUtil;

/**
 * 
 * @author ChenMinming
 * @date 2014-1-26
 * @version V1.0.0
 */
@XmlRootElement(name = "bodyItem")
public class BodyItem {

	private String itemId;
	private String valueList;
	private List<Col> colList;
	
	
	public BodyItem() {
	}
	
	public BodyItem(String itemId, List<Col> colList) {
		this.itemId = itemId;
		this.colList = colList;
	}
	
	@XmlTransient
	public List<Col> getColList() {
		return colList;
	}
	
	public void setColList(List<Col> colList) {
		this.colList = colList;
	}
	@XmlAttribute(name="itemId")
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	@XmlAttribute(name="valueList")
	public String getValueList() {
		if(colList==null||colList.isEmpty())
			return "";
		StringBuffer sbf = new StringBuffer();
		for (Col c : colList) {
			sbf.append(","+c.getValue());
		}
		valueList = sbf.substring(1);
		return valueList;
	}
	
	public void fillColListByValueList(){
		colList = new  ArrayList<Col>();
		if(StringUtil.isEmpty(valueList)){
			return;
		}
		String[] cString = (valueList+",END").split(",");
		for (String s : cString) {
			colList.add(new Col(s));
		}
		colList.remove(colList.size()-1);
	}

	public void setValueList(String valueList) {
		this.valueList = valueList;
	}
	
}
