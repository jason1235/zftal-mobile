package com.zfsoft.sjzx.wsdl.bean;

import java.util.List;

public class Operations {
	private String service;
	private String name;
	private List<Parameters> params;
	
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Parameters> getParams() {
		return params;
	}
	public void setParams(List<Parameters> params) {
		this.params = params;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("\n" + name);
		if (params != null && params.size() > 0) {
			for (Parameters param : params) {
				builder.append("\n Type: " + param.getType() + ",Name: " + param.getName());
			}
		}
		return builder.toString();
	}
	
}
