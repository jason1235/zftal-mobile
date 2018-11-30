package com.zfsoft.hrm.dybill.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.zfsoft.hrm.baseinfo.code.entities.Catalog;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.infoclass.enums.VerifyType;
import com.zfsoft.util.base.StringUtil;
/**
 * 审批表单配置
 * 版权声明 
 * 作者 沈鲁威 
 * 时间 2013-06-08
 * @author Patrick Shen
 */
@XmlRootElement
public class XmlBillProperty {
	/**
	 * 属性编号
	 */
	private Long id;
	/**
	 * 信息类属性编号
	 */
	private String propertyId;
	/**
	 * 属性描述
	 */
	private String descs;
	/**
	 * 提示信息
	 */
	private String titleMessage;
	/**
	 * 属性中文名称
	 */
	private String name;
	/**
	 * 英文名称
	 */
	private String fieldName;
	/**
	 * 字段类型
	 */
	private String fieldType=Type.COMMON;
	/**
	 * 验证类型
	 */
	private VerifyType verifyType;
	/**
	 * 字段最大长度
	 */
	private Integer maxLength=32;
	/**
	 * 代码编号
	 */
	private String codeId;
	/**
	 * 是否标记字段
	 */
	private Boolean mark=false;
	/**
	 * 保留小数位
	 */
	private Integer digits=0;
	/**
	 * 必填
	 */
	private Boolean required=true;
	/**
	 * 可视
	 */
	private Boolean visable=true;
	/**
	 * 可编辑
	 */
	private Boolean editable=true;
	/**
	 * 默认值
	 */
	private String defaultValue;
	/**
	 * 宽
	 */
	private Integer width=200;
	/**
	 * 高
	 */
	private Integer height=30;
	/**
	 * 文件大小，默认200k
	 */
	private Integer size=5;
	/**
	 * 
	 */
	private String viewStyle = "";
	
	@XmlTransient
	public VerifyType[] getVerifyTypes() {
		return VerifyType.values();
	}
	
	@XmlAttribute
	public VerifyType getVerifyType() {
		return verifyType;
	}
	public void setVerifyType(VerifyType verifyType) {
		this.verifyType = verifyType;
	}
	@XmlAttribute
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	@XmlAttribute
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	@XmlAttribute
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	@XmlAttribute
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	@XmlAttribute
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@XmlAttribute
	public String getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}
	@XmlAttribute
	public String getDescs() {
		return descs;
	}
	public void setDescs(String descs) {
		this.descs = descs;
	}
	@XmlAttribute
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlAttribute
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	@XmlAttribute
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
		dealVerify();
	}
	private void dealVerify() {
		if(!Type.COMMON.equals(this.fieldType)&&
				!Type.NUMBER.equals(this.fieldType)){
			this.verifyType=null;
		}
	}
	@XmlAttribute
	public Integer getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}
	@XmlAttribute
	public String getCodeId() {
		return codeId;
	}
	@XmlTransient
	public String getCodeStr() {
		Catalog catalog= CodeUtil.getCatalog(codeId);
		if(catalog==null||StringUtil.isEmpty(catalog.getName())){
			return codeId;
		}else{
			return catalog.getName();
		}
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	@XmlAttribute
	public Boolean getMark() {
		return mark;
	}
	public void setMark(Boolean mark) {
		this.mark = mark;
	}
	
	@XmlAttribute
	public String getTitleMessage() {
		return titleMessage;
	}
	public void setTitleMessage(String titleMessage) {
		this.titleMessage = titleMessage;
	}
	@XmlAttribute
	public Integer getDigits() {
		return digits;
	}
	public void setDigits(Integer digits) {
		this.digits = digits;
	}
	@XmlAttribute
	public Boolean getRequired() {
		return required;
	}
	public void setRequired(Boolean required) {
		this.required = required;
	}
	@XmlAttribute
	public Boolean getVisable() {
		return visable;
	}
	public void setVisable(Boolean visable) {
		this.visable = visable;
	}
	@XmlAttribute
	public Boolean getEditable() {
		return editable;
	}
	public void setEditable(Boolean editable) {
		this.editable = editable;
	}
	
	@XmlTransient
	public InfoProperty getInfoProperty(){
		InfoProperty newproperty=new InfoProperty();
		newproperty.setEditable(this.getEditable());
		newproperty.setViewable(this.getVisable());
		newproperty.setNeed(this.getRequired());
		newproperty.setCodeId(this.getCodeId());
		newproperty.setDescription(this.getDescs());
		newproperty.setDigits(this.getDigits());
		newproperty.setFieldLen(this.getMaxLength());
		newproperty.setFieldName(this.getFieldName());
		newproperty.setFieldType(this.getFieldType());
		newproperty.setVerifyType(this.getVerifyType());
		newproperty.setName(this.getName());
		newproperty.setUnique(this.getMark());
		newproperty.setSize(this.getSize());
		newproperty.setDefaultValue(this.getDefaultValue());
		newproperty.setWidth(width);
		newproperty.setHeight(height);
		newproperty.setViewStyle(viewStyle);
		return newproperty;
	}
	
	public void setInfoProperty(InfoProperty property) {
		this.setCodeId(property.getCodeId());
		this.setDescs(property.getDescription());
		this.setDigits(property.getDigits());
		this.setMaxLength(property.getFieldLen());
		this.setFieldName(property.getFieldName());
		this.setFieldType(property.getFieldType());
		this.setVerifyType(property.getVerifyType());
		this.setWidth(property.getWidth());
		this.setSize(property.getSize());
		this.setViewStyle(property.getViewStyle());
		this.setHeight(property.getHeight());
		this.setDefaultValue(property.getDefaultValue());
		if(StringUtil.isEmpty(name)){
			name=property.getName();
		}
		this.setMark(property.getUnique());
		if(property.getEditable()==false){
			this.setEditable(false);
		}
		if(property.getViewable()==false){
			this.setVisable(false);
		}
	}

	@XmlAttribute
	public String getViewStyle() {
		return viewStyle;
	}

	public void setViewStyle(String viewStyle) {
		this.viewStyle = viewStyle;
	}
	
	
}