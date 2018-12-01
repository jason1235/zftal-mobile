package com.zfsoft.hrm.report.xentity;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
/**
 * 表头标准对象
 * @author 沈鲁威 Patrick Shen
 * @since 2012-9-13
 * @version V1.0.0
 */
@XmlRootElement
public class XmlTitle {
	private String name="";
	private String fieldName;
	private Integer colspan=1;
	private Integer rowspan=1;
	private String width="";
	private String height="";
	private int deep=0;
	
	private boolean leaf=false;
	private List<XmlTitle> xmlTitle;
	
	@XmlTransient
	public int getDeep() {
		return deep;
	}
	public void setDeep(int deep) {
		this.deep = deep;
	}
	
	@XmlTransient
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	@XmlAttribute
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlAttribute(required=false)
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	@XmlTransient
	public Integer getColspan() {
		return colspan;
	}
	public void setColspan(Integer colspan) {
		this.colspan = colspan;
	}
	@XmlTransient
	public Integer getRowspan() {
		return rowspan;
	}
	
	public void setRowspan(Integer rowspan) {
		this.rowspan = rowspan;
	}
	@XmlTransient
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	@XmlTransient
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	@XmlElement(name="th")
	public List<XmlTitle> getXmlTitle() {
		return xmlTitle;
	}
	public void setXmlTitle(List<XmlTitle> xmlTitle) {
		this.xmlTitle = xmlTitle;
	}
	
	
}
