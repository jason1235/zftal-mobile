package com.zfsoft.hrm.report.ofcentity;

import java.util.List;

public class OpenFlashChart {
	private String title;
	private List<Element> elements;
	private String bg_colour = "#ffffff";
	
	private XAxis x_axis=new XAxis(); 
	private YAxis Y_axis=new YAxis(); ; 
	private ToolTip tooltip=new ToolTip();
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Element> getElements() {
		return elements;
	}
	public void setElements(List<Element> elements) {
		this.elements = elements;
	}
	public String getBg_colour() {
		return bg_colour;
	}
	public void setBg_colour(String bg_colour) {
		this.bg_colour = bg_colour;
	}
	public XAxis getX_axis() {
		return x_axis;
	}
	public void setX_axis(XAxis x_axis) {
		this.x_axis = x_axis;
	}
	public YAxis getY_axis() {
		return Y_axis;
	}
	public void setY_axis(YAxis y_axis) {
		Y_axis = y_axis;
	}
	public ToolTip getTooltip() {
		return tooltip;
	}
	public void setTooltip(ToolTip tooltip) {
		this.tooltip = tooltip;
	}
	
}
