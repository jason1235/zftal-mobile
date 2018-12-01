package com.zfsoft.hrm.dybill.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * 审批表单实例
 * 版权声明 
 * 作者 沈鲁威 
 * 时间 2013-06-08
 * @author Patrick Shen
 */
@XmlRootElement
public class XmlValueClass {
	
	private Long billClassId;
	
	private XmlBillClass xmlBillClass;
	
	private List<XmlValueEntity> valueEntities;

	@XmlAttribute
	public Long getBillClassId() {
		return billClassId;
	}
	public void setBillClassId(Long billClassId) {
		this.billClassId = billClassId;
	}
	
	@XmlElement(name="valueEntity")
	public List<XmlValueEntity> getValueEntities() {
		return valueEntities;
	}

	public void setValueEntities(List<XmlValueEntity> valueEntities) {
		this.valueEntities = valueEntities;
	}
	
	public XmlValueEntity getValueEntityById(Long id){
		if(valueEntities==null){
			return null;
		}
		for (XmlValueEntity xmlValueEntity : valueEntities) {
			if(id.equals(xmlValueEntity.getId())){
				return xmlValueEntity;
			}
		}
		return null;
	}
	
	public XmlValueEntity getValueEntityByInfoEntityId(String infoEntityId){
		if(valueEntities==null){
			return null;
		}
		for (XmlValueEntity xmlValueEntity : valueEntities) {
			if(infoEntityId.equals(xmlValueEntity.getInfoEntityId())){
				return xmlValueEntity;
			}
		}
		return null;
	}
	/**
	 * 取标记记录
	 * @return
	 */
	public List<XmlValueEntity> getLastValueEntity() {
		if(xmlBillClass==null){
			throw new RuntimeException("请先设置表单类");
		}
		List<XmlValueEntity> result = new ArrayList<XmlValueEntity>();
		List<XmlBillProperty> markPropertiess=xmlBillClass.getMarkBillPropertys();;
		XmlValueEntity xmlValueEntity;
		if(valueEntities!=null&&valueEntities.size()>=1){
			if(xmlBillClass.getMaxLength()==1){
				return valueEntities;
			}else if(xmlBillClass.getMaxLength()>1){
				if(markPropertiess!=null&&markPropertiess.size()>0){
					if(valueEntities!=null&&valueEntities.size()>=1){
						return valueEntities.subList(valueEntities.size()-1, valueEntities.size());
					}
				}else{
					for (XmlBillProperty xmlBillProperty : markPropertiess) {
						xmlValueEntity=getLastValueEntity(xmlBillProperty.getId());
						if(xmlValueEntity!=null){
							result.add(xmlValueEntity);
						}
					}
					
					if(result.size()==0){
						return valueEntities.subList(valueEntities.size()-1,valueEntities.size());
					}
				}
			}
		}
		
		return result;
	}
	
	public XmlValueEntity getLastValueEntity(Long markPropertyId) {
		XmlValueProperty valueProperty;
		for (XmlValueEntity xmlValueEntity : valueEntities) {
			valueProperty=xmlValueEntity.getValuePropertyById(markPropertyId);
			if(valueProperty!=null&&"1".equals(valueProperty.getValue())){
				return xmlValueEntity;
			}
		}
		return null;
	}
	
	public void setXmlBillClass(XmlBillClass xmlBillClass) {
		this.xmlBillClass = xmlBillClass;
	}

}
