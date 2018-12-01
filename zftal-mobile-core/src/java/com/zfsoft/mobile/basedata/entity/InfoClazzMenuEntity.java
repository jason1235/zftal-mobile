package com.zfsoft.mobile.basedata.entity;

import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.xsom.impl.scd.Iterators.Map;

public class InfoClazzMenuEntity {

	// 关联信息类ID
    private String infoClassId;
    // 关联一级菜单ID
    private String firstMenuId;
    // 菜单名称
    private String menuName;
    // 排序码
    private int sequence;
    // 菜单ID
    private String classCdid;
    // 菜单编码
    private String classCdbm;
    //当前菜单长度
    private String menuLength;
    
    
    
    private List<java.util.Map<String, Object>> sxData = new ArrayList<java.util.Map<String,Object>>();
    
	public String getInfoClassId() {
		return infoClassId;
	}
	public void setInfoClassId(String infoClassId) {
		this.infoClassId = infoClassId;
	}
	public String getFirstMenuId() {
		return firstMenuId;
	}
	public void setFirstMenuId(String firstMenuId) {
		this.firstMenuId = firstMenuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
		this.menuLength = String.valueOf(menuName.length()*20);
	}
	public void setClassCdid(String classCdid) {
		this.classCdid = classCdid;
	}
	public String getClassCdid() {
		return classCdid;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public void setClassCdbm(String classCdbm) {
		this.classCdbm = classCdbm;
	}
	public String getClassCdbm() {
		return classCdbm;
	}
	public void setSxData(List<java.util.Map<String, Object>> sxData) {
		this.sxData = sxData;
	}
	public List<java.util.Map<String, Object>> getSxData() {
		return sxData;
	}
	public void setMenuLength(String menuLength) {
		this.menuLength = menuLength;
	}
	public String getMenuLength() {
		return menuLength;
	}
}
