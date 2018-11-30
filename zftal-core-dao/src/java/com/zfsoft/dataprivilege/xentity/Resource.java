package com.zfsoft.dataprivilege.xentity;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="context")
public class Resource {
	@XmlAttribute
	private String id;
	@XmlAttribute
	private String name;
	@XmlAttribute
	private String classname;
	@XmlAttribute
	private String method;
	@XmlElement
	private List<Param> param;
	
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
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public List<Param> getParam() {
		return param;
	}
	public void setParam(List<Param> param) {
		this.param = param;
	}
	
	public String getParamValue(String paramName){
		if(param==null){
			return null;
		}
		for (Param para : param) {
			if(para.getName().equals(paramName)){
				return para.getValue();
			}
		}
		return null;
	}
	
}
