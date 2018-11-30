package com.zfsoft.hrm.schedule.entities;

public class TriggerStore {
	private String guid;
	private String expression;
	private String serviceClass;
	private String description;
	private boolean isUsable;
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public String getServiceClass() {
		return serviceClass;
	}
	public void setServiceClass(String serviceClass) {
		this.serviceClass = serviceClass;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isUsable() {
		return isUsable;
	}
	public void setUsable(boolean isUsable) {
		this.isUsable = isUsable;
	}
	
}
