package com.zfsoft.dataprivilege.xentity;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="resources")
public class Resources {
	
	@XmlElement
	List<Context> context;

	public List<Context> getContext() {
		return context;
	}

	public void setContexts(List<Context> context) {
		this.context = context;
	}
	
	
}
