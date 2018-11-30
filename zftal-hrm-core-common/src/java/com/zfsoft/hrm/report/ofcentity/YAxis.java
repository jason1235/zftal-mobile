package com.zfsoft.hrm.report.ofcentity;

import org.apache.struts2.json.annotations.JSON;

public class YAxis {
	 private String colour="#aaaaaa";
	 private String grid_colour="#eeeeee";
	 private Integer stroke= 1;
	 private Integer tick_length= 5;
	 private Integer min=0;
	 private Integer max=105;
	 private Integer steps=50;
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
	public Integer getMin() {
		return min;
	}
	public void setMin(Integer min) {
		this.min = min;
	}
	public Integer getMax() {
		return max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}
	public Integer getSteps() {
		return steps;
	}
	public void setSteps(Integer steps) {
		this.steps = steps;
	}
	 
	 

}
