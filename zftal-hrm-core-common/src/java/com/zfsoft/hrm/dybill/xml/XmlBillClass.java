package com.zfsoft.hrm.dybill.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.dybill.enums.PrivilegeType;
import com.zfsoft.hrm.dybill.enums.ScanStyleType;
import com.zfsoft.util.base.StringUtil;
/**
 * 审批表单配置
 * 版权声明 
 * 作者 沈鲁威 
 * 时间 2013-06-08
 * @author Patrick Shen
 */
@XmlRootElement
public class XmlBillClass {
	/**
	 * 类编号
	 */
	private Long id;
	/**
	 * 展示名称
	 */
	private String name;
	/**
	 * 展示编号
	 */
	private String identityName;
	/**
	 * 信息类编号
	 */
	private String classId;
	/**
	 * 最大展示条数
	 */
	private Integer maxLength = 7;
	/**
	 * 最少展示条数
	 */
	private Integer minLength = 1;
	/**
	 * 显示模式，TILE平铺，LIST列表
	 */
	private ScanStyleType scanStyle=ScanStyleType.TILE;
	/**
	 * 平铺时控制横向字段数量
	 */
	private Integer colNum=2;
	/**
	 * 是否可追加
	 */
	private Boolean append=false;
	/**
	 * 是否可选择
	 */
	private Boolean choice=false;
	/**
	 * 默认抓取条数,如果不等于0，则该表单不可增删改查，数据自动由系统去抓取
	 */
	private Integer catchRecordNum=0;
	/**
	 * 权限控制
	 */
	private PrivilegeType privilegeType=PrivilegeType.SEARCH_ADD_DELETE_EDIT;
	/**
	 * 属性配置
	 */
	private List<XmlBillProperty> billPropertys;
	/**
	 * 普通字段集合
	 */
	private List<XmlBillProperty> commonBillPropertys;
	/**
	 * 图片集合
	 */
	private List<XmlBillProperty> imageBillPropertys;
	/**
	 * 照片集合
	 */
	private List<XmlBillProperty> photoBillPropertys;
	/**
	 * 抓取定义
	 */
	private XmlDefineCatch defineCatch;
	
	@XmlElement
	public XmlDefineCatch getDefineCatch() {
		return defineCatch;
	}
	public void setDefineCatch(XmlDefineCatch defineCatch) {
		this.defineCatch = defineCatch;
	}
	@XmlAttribute
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@XmlAttribute(required=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlAttribute
	public String getIdentityName() {
		return identityName;
	}
	public void setIdentityName(String identityName) {
		this.identityName = identityName;
	}
	@XmlAttribute
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	@XmlAttribute
	public Integer getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}
	@XmlAttribute
	public Integer getMinLength() {
		return minLength;
	}
	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}
	@XmlAttribute
	public ScanStyleType getScanStyle() {
		return scanStyle;
	}
	public void setScanStyle(ScanStyleType scanStyle) {
		this.scanStyle = scanStyle;
	}
	@XmlAttribute
	public Integer getColNum() {
		return colNum;
	}
	public void setColNum(Integer colNum) {
		this.colNum = colNum;
	}
	@XmlAttribute
	public Boolean getAppend() {
		return append;
	}
	public void setAppend(Boolean append) {
		this.append = append;
	}
	@XmlAttribute
	public Boolean getChoice() {
		return choice;
	}
	public void setChoice(Boolean choice) {
		this.choice = choice;
	}
	@XmlAttribute
	public Integer getCatchRecordNum() {
		return catchRecordNum;
	}
	public void setCatchRecordNum(Integer catchRecordNum) {
		this.catchRecordNum = catchRecordNum;
	}
	@XmlTransient
	public PrivilegeType getPrivilegeType() {
		return privilegeType;
	}
	public void setPrivilegeType(PrivilegeType modeType) {
		this.privilegeType = modeType;
	}
	@XmlElement(name="billProperty")
	public List<XmlBillProperty> getBillPropertys() {
		return billPropertys;
	}
	
	@XmlTransient
	public List<XmlBillProperty> getCommonBillPropertys() {
		if(commonBillPropertys!=null){
			return commonBillPropertys;
		}
		commonBillPropertys=new ArrayList<XmlBillProperty>();
		if(billPropertys==null){
			return commonBillPropertys;
		}
		for(XmlBillProperty xmlBillProperty:billPropertys){
			if(!xmlBillProperty.getFieldType().equals(Type.IMAGE)&&
					!xmlBillProperty.getFieldType().equals(Type.PHOTO)){
				commonBillPropertys.add(xmlBillProperty);
			}
		}
		return commonBillPropertys;
	}
	@XmlTransient
	public List<XmlBillProperty> getMarkBillPropertys() {
		List<XmlBillProperty> markList=new ArrayList<XmlBillProperty>();
		if(billPropertys==null){
			return markList;
		}
		for(XmlBillProperty xmlBillProperty:billPropertys){
			if(xmlBillProperty.getMark()!=null&&xmlBillProperty.getMark()){
				markList.add(xmlBillProperty);
			}
		}
		return markList;
	}
	@XmlTransient
	public List<XmlBillProperty> getImageBillPropertys() {
		if(imageBillPropertys!=null){
			return imageBillPropertys;
		}
		imageBillPropertys=new ArrayList<XmlBillProperty>();
		if(billPropertys==null){
			return imageBillPropertys;
		}
		for(XmlBillProperty xmlBillProperty:billPropertys){
			if(xmlBillProperty.getFieldType().equals(Type.IMAGE)){
				imageBillPropertys.add(xmlBillProperty);
			}
		}
		return imageBillPropertys;
	}
	@XmlTransient
	public List<XmlBillProperty> getPhotoBillPropertys() {
		if(photoBillPropertys!=null){
			return photoBillPropertys;
		}
		photoBillPropertys=new ArrayList<XmlBillProperty>();
		if(billPropertys==null){
			return photoBillPropertys;
		}
		for(XmlBillProperty xmlBillProperty:billPropertys){
			if(xmlBillProperty.getFieldType().equals(Type.PHOTO)){
				photoBillPropertys.add(xmlBillProperty);
			}
		}
		return photoBillPropertys;
	}
	
	public void setBillPropertys(List<XmlBillProperty> billPropertys) {
		this.billPropertys = billPropertys;
	}
	
	public void setInfoclass(InfoClass infoclass) {
		if(StringUtil.isEmpty(identityName)){
			identityName=infoclass.getIdentityName();
		}
		if(StringUtil.isEmpty(name)){
			name=infoclass.getName();
		}
	}
	
	public XmlBillProperty getBillPropertyById(Long id){
		if(billPropertys==null){
			return null;
		}
		for (XmlBillProperty xmlBillProperty : billPropertys) {
			if(id.equals(xmlBillProperty.getId())){
				return xmlBillProperty;
			}
		}
		return null;
	}
	
	public XmlBillProperty getBillPropertyByFieldName(String fieldName){
		if(billPropertys==null||StringUtil.isEmpty(fieldName)){
			return null;
		}
		for (XmlBillProperty xmlBillProperty : billPropertys) {
			if(fieldName.equals(xmlBillProperty.getFieldName())){
				return xmlBillProperty;
			}
		}
		return null;
	}
	@XmlTransient
	public XmlDefineCatch getFullDefineCatch(){
		if(defineCatch == null)
			defineCatch = new XmlDefineCatch();
		if(defineCatch.getCatchFields()==null)
			defineCatch.setCatchFields(new ArrayList<XmlCatchField>());
		if(billPropertys==null){
			return null;
		}
		List<XmlCatchField> fields = new ArrayList<XmlCatchField>();
		for (XmlBillProperty xmlBillProperty : billPropertys) {
			XmlCatchField  catchField = 
				defineCatch.getXmlCatchFieldByPropertyId(xmlBillProperty.getId());
			if(catchField != null){
				catchField.setBillProperty(xmlBillProperty);
			}else{
				catchField = new XmlCatchField();
				catchField.setBillProperty(xmlBillProperty);
				defineCatch.getCatchFields().add(catchField);
			}
			fields.add(catchField);
		}
		defineCatch.setCatchFields(fields);
		return defineCatch;
		
	}
}