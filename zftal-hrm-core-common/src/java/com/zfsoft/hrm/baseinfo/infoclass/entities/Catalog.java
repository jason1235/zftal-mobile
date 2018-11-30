package com.zfsoft.hrm.baseinfo.infoclass.entities;

import java.io.Serializable;
import java.util.List;

import com.zfsoft.hrm.config.TypeFactory;
import com.zfsoft.hrm.config.type.InfoCatalogType;

/**
 * 信息类目录
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @since 2012-5-18
 * @version V1.0.0
 */
public class Catalog implements Serializable {

	private static final long serialVersionUID = -9137720205602318064L;

	private String guid;				//全局ID
	
	private String name;				//名称
	
	private Integer index;				//序号
	
	private List<InfoClass> classes;	//信息类列表
	
	private String type;                //信息分类  student 学生分类   teacher 老师分类 
	
	private String menuId;				//菜单编号
	/**
	 * 返回信息类目录类型信息
	 * @return
	 */
	public InfoCatalogType getTypeInfo() {
	
		return (InfoCatalogType) TypeFactory.getType( InfoCatalogType.class, type );
	}
	
	/**
	 * 返回全局ID
	 */
	public String getGuid() {
		return guid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 设置全局ID
	 * @param guid 全局ID
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * 返回名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * @param name 名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回序号（序号：用于显示排序）
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * 设置序号
	 * @param index 序号（序号：用于显示排序）
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

	/**
	 * 返回信息类列表
	 */
	public List<InfoClass> getClasses() {
		return classes;
	}

	/**
	 * 设置信息类列表
	 * @param clsses 信息类列表
	 */
	public void setClasses(List<InfoClass> classes) {
		this.classes = classes;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	
}
