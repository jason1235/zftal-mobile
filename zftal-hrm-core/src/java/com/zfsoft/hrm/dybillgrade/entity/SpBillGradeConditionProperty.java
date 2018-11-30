package com.zfsoft.hrm.dybillgrade.entity;

import java.text.Collator;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import com.zfsoft.hrm.baseinfo.dyna.html.ViewParse;
import com.zfsoft.hrm.dybill.xml.XmlBillProperty;
import com.zfsoft.hrm.dybillgrade.enums.GradePropertyOperatorEnum;

/**
 * 
 * @author ChenMinming
 * @date 2014-6-5
 * @version V1.0.0
 */
@XmlRootElement
public class SpBillGradeConditionProperty {
	private String parenthesisBefore;
	private Long propertyId;
	private String operator;
	private String fieldValue;
	private String parenthesisAfter;
	private String logicalRel;
	private XmlBillProperty billProperty;
	
	@XmlAttribute
	public String getParenthesisBefore() {
		return parenthesisBefore;
	}
	public void setParenthesisBefore(String parenthesisBefore) {
		this.parenthesisBefore = parenthesisBefore;
	}
	@XmlAttribute
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	@XmlAttribute
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public String getFieldValueString(){
		if(billProperty == null) return fieldValue;
		return ViewParse.parse(billProperty.getInfoProperty(), fieldValue);
	}
	@XmlAttribute
	public String getParenthesisAfter() {
		return parenthesisAfter;
	}
	public void setParenthesisAfter(String parenthesisAfter) {
		this.parenthesisAfter = parenthesisAfter;
	}
	@XmlAttribute
	public String getLogicalRel() {
		return logicalRel;
	}
	public void setLogicalRel(String logicalRel) {
		this.logicalRel = logicalRel;
	}
	@XmlAttribute
	public Long getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}
	@XmlTransient
	public XmlBillProperty getBillProperty() {
		return billProperty;
	}
	public void setBillProperty(XmlBillProperty billProperty) {
		this.billProperty = billProperty;
	}
	
	public boolean runConditionProperties(String v){
		GradePropertyOperatorEnum operatorEnum = GradePropertyOperatorEnum.getGradePropertyOperatorEnum(operator);
		if(operatorEnum == GradePropertyOperatorEnum.NOT_EQ){
			return !runConditionProperties(v, GradePropertyOperatorEnum.EQ);
		}else if(operatorEnum == GradePropertyOperatorEnum.NOT_LIKE){
			return runConditionProperties(v, GradePropertyOperatorEnum.LIKE);
		}else if(operatorEnum == GradePropertyOperatorEnum.XY_EQ){
			return runConditionProperties(v, GradePropertyOperatorEnum.XY)
				||runConditionProperties(v, GradePropertyOperatorEnum.EQ);
		}else if(operatorEnum == GradePropertyOperatorEnum.DY_EQ){
			return runConditionProperties(v, GradePropertyOperatorEnum.DY)
				||runConditionProperties(v, GradePropertyOperatorEnum.EQ);
		}else{
			return runConditionProperties(v, operatorEnum);
		}
	}
	private boolean runConditionProperties(String v, GradePropertyOperatorEnum operatorEnum ){
		if(operatorEnum == GradePropertyOperatorEnum.EQ){
			return fieldValue.equals(v);
		}else if(operatorEnum == GradePropertyOperatorEnum.DY){
			return Collator.getInstance().compare(v.trim(),fieldValue.trim())>0;
		}else if(operatorEnum == GradePropertyOperatorEnum.XY){
			return Collator.getInstance().compare(v.trim(),fieldValue.trim())<0;
		}else if(operatorEnum == GradePropertyOperatorEnum.LIKE){
			return (v.indexOf(fieldValue)!=-1);
		}
		return false;
	}
	
}
