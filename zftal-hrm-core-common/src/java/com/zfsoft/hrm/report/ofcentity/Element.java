package com.zfsoft.hrm.report.ofcentity;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

public class Element {
	private String type="area";
	private List<Value> values=new ArrayList<Value>();
	private Double fill_alpha=0.4;
	private Integer width=2;
	private Integer dot_size=4;
	private Integer halo_size=2;
	private String colour="#ff9900";
	private String fill="#dbecf6";
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Value> getValues() {
		values=new ArrayList<Value>();
		Value value=new Value();
		values.add(value);
		value.setValue(50);
		values.add(value);
		values.add(value);
		return values;
	}
	public void setValues(List<Value> values) {
		this.values = values;
	}
	@JSON(name="fill-alpha")
	public Double getFill_alpha() {
		return fill_alpha;
	}
	public void setFill_alpha(Double fill_alpha) {
		this.fill_alpha = fill_alpha;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	@JSON(name="dot-size")
	public Integer getDot_size() {
		return dot_size;
	}
	public void setDot_size(Integer dot_size) {
		this.dot_size = dot_size;
	}
	@JSON(name="halo-size")
	public Integer getHalo_size() {
		return halo_size;
	}
	public void setHalo_size(Integer halo_size) {
		this.halo_size = halo_size;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	public String getFill() {
		return fill;
	}
	public void setFill(String fill) {
		this.fill = fill;
	}
}
