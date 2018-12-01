package com.zfsoft.hrm.dybill.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * 审批表单实例
 * 版权声明 
 * 作者 沈鲁威 
 * 时间 2013-06-08
 * @author Patrick Shen
 */
@XmlRootElement(name="values")
public class XmlValueClasses {
	
	private List<XmlValueClass> valueClasses;

	@XmlElement(name="valueClass")
	public List<XmlValueClass> getValueClasses() {
		return valueClasses;
	}

	public void setValueClasses(List<XmlValueClass> valueClasses) {
		this.valueClasses = valueClasses;
	}
	
	public XmlValueClass getValueClassByClassId(Long id){
		if(valueClasses==null){
			return null;
		}
		for (XmlValueClass xmlValueClass : valueClasses) {
			if(id.equals(xmlValueClass.getBillClassId())){
				return xmlValueClass;
			}
		}
		return null;
	}
}
