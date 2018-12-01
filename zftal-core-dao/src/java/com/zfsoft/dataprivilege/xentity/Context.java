package com.zfsoft.dataprivilege.xentity;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="context")
public class Context {
	@XmlAttribute
	private String id;
	@XmlAttribute
	private String name;
	@XmlAttribute
	private String view;
	@XmlAttribute
	private String dealclass;
	@XmlElement
	private List<DataPrivilegeUrl> url;
	
	public List<DataPrivilegeUrl> getUrl() {
		return url;
	}
	public void setUrl(List<DataPrivilegeUrl> url) {
		this.url = url;
	}
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
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public String getDealclass() {
		return dealclass;
	}
	public void setDealclass(String dealclass) {
		this.dealclass = dealclass;
	}

}
