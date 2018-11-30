package com.zfsoft.hrm.dybill.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
//<defineCatch tableName="ss">
//<catchField>
//</catchField>
//<catchField>
//</catchField>
//<catchField>
//</catchField>
//<expression></expression>
//</defineCatch>
@XmlRootElement
public class XmlDefineCatch {
	private String tableName;
	private List<XmlCatchField> catchFields;
	private String expression;
	private String uniqueField;
	private Boolean checked;
	
	@XmlElement(name="catchField")
	public List<XmlCatchField> getCatchFields() {
		return catchFields;
	}
	public void setCatchFields(List<XmlCatchField> catchFields) {
		this.catchFields = catchFields;
	}
	@XmlAttribute
	public String getTableName() {
		return tableName;
	}

	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	@XmlElement
	public String getExpression() {
		return expression;
	}
	
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	@XmlTransient
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
	public XmlCatchField getXmlCatchFieldByPropertyId(Long propertyId){
		if(catchFields == null){
			catchFields = new ArrayList<XmlCatchField>();
			return null;
		}
		for(XmlCatchField field:catchFields){
			if(field.getBillPropertyId().equals(propertyId)){
				return field;
			}
		}
		return null;
	}
	/**
	 * 返回
	 */
	@XmlAttribute
	public String getUniqueField() {
		return uniqueField;
	}
	/**
	 * 设置
	 * @param uniqueFields 
	 */
	public void setUniqueField(String uniqueField) {
		this.uniqueField = uniqueField;
	}
	@XmlTransient
	public InfoClass getInfoClass(){
		InfoClass infoClass = new InfoClass();
		for (XmlCatchField field : catchFields) {
			if(field.getBillProperty()==null){
				continue;
			}
			infoClass.addProperty(field.getBillProperty().getInfoProperty());
		}
		return infoClass;
	}
	
	
}
