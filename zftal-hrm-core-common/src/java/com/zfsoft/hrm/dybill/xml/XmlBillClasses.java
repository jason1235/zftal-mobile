package com.zfsoft.hrm.dybill.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * 审批表单配置
 * 版权声明 
 * 作者 沈鲁威 
 * 时间 2013-06-08
 * @author Patrick Shen
 */
@XmlRootElement(name="billClasses")
public class XmlBillClasses {
	
	private List<XmlBillClass> billClasses;
	
	@XmlElement(name="billClass")
	public List<XmlBillClass> getBillClasses() {
		return billClasses;
	}

	public void setBillClasses(List<XmlBillClass> billClasses) {
		this.billClasses = billClasses;
	}
	
	public XmlBillClass getBillClassById(Long id){
		if(billClasses==null){
			return null;
		}
		for (XmlBillClass xmlBillClass : billClasses) {
			if(id.equals(xmlBillClass.getId())){
				return xmlBillClass;
			}
		}
		return null;
	}
	
	public XmlBillClass getBillClassByFieldName(String identityName){
		if(billClasses==null){
			return null;
		}
		for (XmlBillClass xmlBillClass : billClasses) {
			if(identityName.equals(xmlBillClass.getIdentityName())){
				return xmlBillClass;
			}
		}
		return null;
	}
}