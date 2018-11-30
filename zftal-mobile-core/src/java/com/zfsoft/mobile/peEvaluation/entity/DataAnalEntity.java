package com.zfsoft.mobile.peEvaluation.entity;

/**
 * 数据分析bean
 */
public class DataAnalEntity {

	private String id;    //学院ID
	private String name;  //学院名称
	private String xyrs;	//学院人数
	private String xyhgrs;	//学院合格人数
	private String xyylrs;	//学院优良人数
	private String qualified; //学院合格率
	private String excellent; //学院优良率
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getXyrs() {
		return xyrs;
	}
	public void setXyrs(String xyrs) {
		this.xyrs = xyrs;
	}
	public String getXyhgrs() {
		return xyhgrs;
	}
	public void setXyhgrs(String xyhgrs) {
		this.xyhgrs = xyhgrs;
	}
	public String getXyylrs() {
		return xyylrs;
	}
	public void setXyylrs(String xyylrs) {
		this.xyylrs = xyylrs;
	}
	public String getQualified() {
		return qualified;
	}
	public void setQualified(String qualified) {
		this.qualified = qualified;
	}
	public String getExcellent() {
		return excellent;
	}
	public void setExcellent(String excellent) {
		this.excellent = excellent;
	}
	
	
	
}
