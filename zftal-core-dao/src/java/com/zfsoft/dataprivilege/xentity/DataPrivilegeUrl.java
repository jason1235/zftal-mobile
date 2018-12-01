package com.zfsoft.dataprivilege.xentity;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="url")
public class DataPrivilegeUrl {
	@XmlElement
	private String path;
	@XmlElement
	private List<Param> uniqueparam;
	@XmlElement
	private List<Resource> res;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public List<Param> getUniqueparam() {
		return uniqueparam;
	}
	public void setUniqueparam(List<Param> uniqueparam) {
		this.uniqueparam = uniqueparam;
	}
	public List<Resource> getRes() {
		return res;
	}
	public void setRes(List<Resource> res) {
		this.res = res;
	}
	
	
}
