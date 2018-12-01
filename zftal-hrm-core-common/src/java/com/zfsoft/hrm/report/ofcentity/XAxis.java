package com.zfsoft.hrm.report.ofcentity;

import org.apache.struts2.json.annotations.JSON;

public class XAxis {
	 private String colour="#aaaaaa";
	 private String grid_colour="#eeeeee";
	 private Integer stroke= 1;
	 private Integer tick_length= 5;
	 private XLabels labels=new XLabels();
	 
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	@JSON(name="grid-colour")
	public String getGrid_colour() {
		return grid_colour;
	}
	public void setGrid_colour(String grid_colour) {
		this.grid_colour = grid_colour;
	}
	public Integer getStroke() {
		return stroke;
	}
	public void setStroke(Integer stroke) {
		this.stroke = stroke;
	}
	@JSON(name="tick-length")
	public Integer getTick_length() {
		return tick_length;
	}
	public void setTick_length(Integer tick_length) {
		this.tick_length = tick_length;
	}
	public XLabels getLabels() {
		return labels;
	}
	public void setLabels(XLabels labels) {
		this.labels = labels;
	}
}
