package com.zfsoft.hrm.dybill.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.baseinfo.dyna.html.EditParse;
import com.zfsoft.hrm.baseinfo.dyna.html.ViewParse;
import com.zfsoft.hrm.dybill.enums.ApprovStatus;
import com.zfsoft.hrm.dybill.enums.EntityType;
/**
 * 审批表单实例
 * 版权声明 
 * 作者 沈鲁威 
 * 时间 2013-06-08
 * @author Patrick Shen
 */
@XmlRootElement
public class XmlValueEntity {
	
	private Long id;
	/**
	 * 自增SELF是可以修改的  信息类 INFOCLASS 是不可以修改的
	 */
	private EntityType entityType=EntityType.INFOCLASS;
	/**
	 * 审批状态
	 */
	private ApprovStatus approvStatus=ApprovStatus.UNDEAL;
	/**
	 * 属性对应值
	 */
	private List<XmlValueProperty> valueProperties;
	/**
	 * 对应表单类
	 */
	private XmlBillClass xmlBillClass;
	/**
	 * 信息类实例编号
	 */
	private String infoEntityId;
	
	private Map<Long, String> valueMap;//值映射
	private Map<Long, String> newValueMap;//新值映射
	private Map<String, String> fieldNameValueMap;//值映射
	private Map<Long, String> viewMap;//展示映射
	private Map<Long, String> newViewMap;//展示映射
	private Map<Long, String> editMap;//编辑映射
	private Map<String, String> fieldNameStorageValueMap;//回填映射
	
	@XmlAttribute
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	@XmlAttribute
	public EntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}
	@XmlAttribute
	public String getInfoEntityId() {
		return infoEntityId;
	}
	public void setInfoEntityId(String infoEntityId) {
		this.infoEntityId = infoEntityId;
	}

	@XmlAttribute
	public ApprovStatus getApprovStatus() {
		if(entityType==EntityType.INFOCLASS){
			return ApprovStatus.PASS;
		}else{
			if(approvStatus==null){
				return ApprovStatus.UNDEAL;
			}
		}
		return approvStatus;
	}
	public void setApprovStatus(ApprovStatus approvStatus) {
		this.approvStatus = approvStatus;
	}
	@XmlTransient
	public XmlBillClass getXmlBillClass() {
		return xmlBillClass;
	}

	public void setXmlBillClass(XmlBillClass xmlBillClass) {
		this.xmlBillClass = xmlBillClass;
	}

	@XmlElement(name="valuePropertiy")
	public List<XmlValueProperty> getValueProperties() {
		if(valueProperties==null){
			valueProperties=new ArrayList<XmlValueProperty>();
		}
		return valueProperties;
	}
	public void setValueProperties(List<XmlValueProperty> valueProperties) {
		this.valueProperties = valueProperties;
	}
	
	@XmlTransient
	public Map<Long, String> getValueMap() {
		if(valueMap==null){
			valueMap=new HashMap<Long, String>();
			if(valueProperties==null){
				return valueMap;
			}
			for (XmlValueProperty valueProperty : valueProperties) {
				valueMap.put(valueProperty.getBillPropertyId(), valueProperty.getValue());
			}
		}
		return valueMap;
	}
	@XmlTransient
	public Map<Long, String> getNewValueMap() {
		if(newValueMap==null){
			newValueMap=new HashMap<Long, String>();
			if(valueProperties==null){
				return newValueMap;
			}
			for (XmlValueProperty valueProperty : valueProperties) {
				newValueMap.put(valueProperty.getBillPropertyId(), valueProperty.getNewValue());
			}
		}
		return newValueMap;
	}
	@XmlTransient
	public Map<String, String> getFieldNameValueMap() {
		if(fieldNameValueMap==null){
			fieldNameValueMap=new HashMap<String, String>();
			if(valueProperties==null){
				return fieldNameValueMap;
			}
			for (XmlValueProperty valueProperty : valueProperties) {
				fieldNameValueMap.put(valueProperty.getBillProperty().getFieldName(), valueProperty.getValue());
			}
		}
		return fieldNameValueMap;
	}
	
	@XmlTransient
	public Map<String, String> getFieldNameStorageValueMap() {
		if(fieldNameStorageValueMap==null){
			fieldNameStorageValueMap=new HashMap<String, String>();
			if(valueProperties==null){
				return fieldNameStorageValueMap;
			}
			for (XmlValueProperty valueProperty : valueProperties) {
				if(valueProperty.getBillProperty()!=null){
					if(null==valueProperty.getNewValue()){
						fieldNameStorageValueMap.put(valueProperty.getBillProperty().getFieldName(), valueProperty.getValue());
					}else{
						fieldNameStorageValueMap.put(valueProperty.getBillProperty().getFieldName(), valueProperty.getNewValue());
					}
				}
				
			}
		}
		return fieldNameStorageValueMap;
	}
	
	@XmlTransient
	public Map<String, String> getFieldNameNewValueMap() {
		if(fieldNameValueMap==null){
			fieldNameValueMap=new HashMap<String, String>();
			if(valueProperties==null){
				return fieldNameValueMap;
			}
			for (XmlValueProperty valueProperty : valueProperties) {
				if(valueProperty.getValue()!=null&&StringUtils.isEmpty(valueProperty.getNewValue())){
					fieldNameValueMap.put(valueProperty.getBillProperty().getFieldName(), "");
					continue;
				}
				if(valueProperty.getNewValue()==null){
					fieldNameValueMap.put(valueProperty.getBillProperty().getFieldName(), valueProperty.getValue());
				}else{
					fieldNameValueMap.put(valueProperty.getBillProperty().getFieldName(), valueProperty.getNewValue());
				}
				
			}
		}
		return fieldNameValueMap;
	}
	@XmlTransient
	public Map<Long, String> getViewMap() {
		if(viewMap==null){
			viewMap=new HashMap<Long, String>();
			if(xmlBillClass==null){
				throw new RuntimeException("请先注入表单信息类!");
			}
			if(xmlBillClass.getBillPropertys()==null){
				return viewMap;
			}
			for (XmlBillProperty billProperty : xmlBillClass.getBillPropertys()) {
				if(billProperty.getInfoProperty().getViewable()){
					viewMap.put(billProperty.getId(),
							ViewParse.parse(billProperty.getInfoProperty(),
									getValueMap().get(billProperty.getId())));
				}
			}
		}
		return viewMap;
	}
	@XmlTransient
	public Map<Long, String> getNewViewMap() {
		if(newViewMap==null){
			newViewMap=new HashMap<Long, String>();
			if(xmlBillClass==null){
				throw new RuntimeException("请先注入表单信息类!");
			}
			if(xmlBillClass.getBillPropertys()==null){
				return newViewMap;
			}
			for (XmlBillProperty billProperty : xmlBillClass.getBillPropertys()) {
				if(billProperty.getInfoProperty().getViewable()){
					if(getNewValueMap().get(billProperty.getId())==null){
						newViewMap.put(billProperty.getId(),
								ViewParse.parse(billProperty.getInfoProperty(),
										getValueMap().get(billProperty.getId())));
					}else{					
						newViewMap.put(billProperty.getId(),"<span class=\"red changeField\">"+
							ViewParse.parse(billProperty.getInfoProperty(),
									getNewValueMap().get(billProperty.getId()))+"</span>");
					}
				}
			}
		}
		return newViewMap;
	}
	@XmlTransient
	public Map<Long, String> getEditMap() {
		if(editMap==null){
			editMap=new HashMap<Long, String>();
			if(xmlBillClass==null){
				throw new RuntimeException("请先注入表单信息类!");
			}
			if(xmlBillClass.getBillPropertys()==null){
				return editMap;
			}
			for (XmlBillProperty billProperty : xmlBillClass.getBillPropertys()) {
				if(billProperty.getInfoProperty().getEditable()){
					if(null==getNewValueMap().get(billProperty.getId())){
						editMap.put(billProperty.getId(),
								EditParse.parse(billProperty.getInfoProperty(), 
										getValueMap().get(billProperty.getId())));
					}else{
						editMap.put(billProperty.getId(),
								EditParse.parse(billProperty.getInfoProperty(), 
										getNewValueMap().get(billProperty.getId())));
					}
				}else{
					editMap.put(billProperty.getId(),
							ViewParse.parse(billProperty.getInfoProperty(), 
									getValueMap().get(billProperty.getId())));
				}
			}
		}
		return editMap;
	}
	
	public XmlValueProperty getValuePropertyById(Long id) {
		for (XmlValueProperty valueProperty : valueProperties) {
			if(valueProperty.getBillPropertyId().equals(id)){
				return valueProperty;
			}
		}
		return null;
	}
	public XmlValueProperty getValuePropertyByFieldName(String fieldName) {
		for (XmlValueProperty valueProperty : valueProperties) {
			if(valueProperty.getBillProperty()==null){
				continue;
			}
			if(valueProperty.getBillProperty().getFieldName().equals(fieldName)){
				return valueProperty;
			}
		}
		return null;
	}
}
