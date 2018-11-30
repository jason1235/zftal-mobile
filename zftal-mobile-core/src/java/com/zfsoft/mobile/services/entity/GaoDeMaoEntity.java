package com.zfsoft.mobile.services.entity;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.query.BaseQuery;

public class GaoDeMaoEntity extends BaseQuery{
	
	private String name;
	private String longitude;
	private String latitude;
	private String description;
	private String affCampus;
	private List<Point> pointList;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	@Override
	public String toString() {
		return "name:"+name+",longitude:"+longitude+",latitude:"+latitude;
	}
	public void setPointList(List<Point> pointList) {
		this.pointList = pointList;
	}
	public List<Point> getPointList() {
		return pointList;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAffCampus() {
		return affCampus;
	}
	public void setAffCampus(String affCampus) {
		this.affCampus = affCampus;
	}
}
