package com.zfsoft.hrm.dataorigin.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.dybill.xml.XmlBillProperty;
import com.zfsoft.hrm.dybill.xml.XmlCatchField;
import com.zfsoft.hrm.dybill.xml.XmlDefineCatch;
import com.zfsoft.util.base.StringUtil;

/**
 * 
 * @author Administrator
 *
 */
@XmlRootElement
public class XmlDataOriginClass {
    /**
     * 类编号
     */
    private Long id;
    /**
     * 展示名称
     */
    private String name;
    /**
     * 类型
     */
    private String originType;
    /**
     * 展示编号
     */
    private String identityName;
    /**
     * 信息类编号
     */
    private String classId;
    /**
     * 记录数
     */
    private Integer recordCnt = 1;
    /**
     * 版本号
     */
    private Integer version = 0;
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
    
    /**
     * 
     * @return
     */
    @XmlElement
    public XmlDefineCatch getDefineCatch() {
        return defineCatch;
    }
    
    /**
     * 
     * @param defineCatch
     */
    public void setDefineCatch(XmlDefineCatch defineCatch) {
        this.defineCatch = defineCatch;
    }
    
    /**
     * 
     * @return
     */
    @XmlAttribute
    public Long getId() {
        return id;
    }
    
    /**
     * 
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * 
     * @return
     */
    @XmlAttribute(required=false)
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 
     * @return
     */
    @XmlAttribute
    public String getIdentityName() {
        return identityName;
    }
    
    /**
     * 
     * @param identityName
     */
    public void setIdentityName(String identityName) {
        this.identityName = identityName;
    }
    
    /**
     * 
     * @return
     */
    @XmlAttribute
    public String getClassId() {
        return classId;
    }
    
    /**
     * 
     * @param classId
     */
    public void setClassId(String classId) {
        this.classId = classId;
    }
    
    /**
     * 
     * @return
     */
    @XmlAttribute
    public Integer getRecordCnt() {
        return recordCnt;
    }
    
    /**
     * 
     * @param recordCnt
     */
    public void setRecordCnt(Integer recordCnt) {
        this.recordCnt = recordCnt;
    }
    
    /**
     * @return the originType
     */
    @XmlAttribute
    public String getOriginType() {
        return originType;
    }

    /**
     * @param originType the originType to set
     */
    public void setOriginType(String originType) {
        this.originType = originType;
    }
    
    /**
     * @return the version
     */
    @XmlAttribute
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * 
     * @return
     */
    @XmlElement(name="billProperty")
    public List<XmlBillProperty> getBillPropertys() {
        return billPropertys;
    }

    /**
     * 
     * @return
     */
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
    
    /**
     * 
     * @return
     */
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
    
    /**
     * 
     * @return
     */
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
    
    /**
     * 
     * @return
     */
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
    
    /**
     * 
     * @param billPropertys
     */
    public void setBillPropertys(List<XmlBillProperty> billPropertys) {
        this.billPropertys = billPropertys;
    }
    
    /**
     * 
     * @param id
     * @return
     */
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
    
    /**
     * 
     * @param fieldName
     * @return
     */
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
    
    /**
     * 
     * @return
     */
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