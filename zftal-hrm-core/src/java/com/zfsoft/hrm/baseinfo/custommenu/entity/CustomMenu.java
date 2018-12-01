package com.zfsoft.hrm.baseinfo.custommenu.entity;

import java.util.List;

/** 
 * 自定义菜单
 * @author jinjj
 * @date 2012-12-18 下午01:37:24 
 *  
 */
public class CustomMenu {

	private String name;
	
	private String type;
	
	private String menuId;
	
	private String conditonJson;
	
	private Condition condition;
	
	private List<Condition> conditions;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return the conditonJson
	 */
	public String getConditonJson() {
		return conditonJson;
	}

	/**
	 * @param conditonJson the conditonJson to set
	 */
	public void setConditonJson(String conditonJson) {
		this.conditonJson = conditonJson;
	}

	/**
	 * @return the condition
	 */
	public Condition getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	/**
	 * @return the conditions
	 */
	public List<Condition> getConditions() {
		return conditions;
	}

	/**
	 * @param conditions the conditions to set
	 */
	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}

}
