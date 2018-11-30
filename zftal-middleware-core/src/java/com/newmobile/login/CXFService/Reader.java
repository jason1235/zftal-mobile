
package com.newmobile.login.CXFService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>reader complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="reader">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="beginDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certFlagName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="debt" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="dept" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="grade" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idCard" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lendQuantity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="limitFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="limitFlagName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maxLendQuantity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="readerCertId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="readerDept" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="readerType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="registerDay" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sex" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="voltFlag" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="voltFlagName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reader", propOrder = {
    "beginDate",
    "certFlag",
    "certFlagName",
    "certId",
    "debt",
    "dept",
    "email",
    "endDate",
    "grade",
    "idCard",
    "lendQuantity",
    "limitFlag",
    "limitFlagName",
    "maxLendQuantity",
    "name",
    "readerCertId",
    "readerDept",
    "readerType",
    "registerDay",
    "sex",
    "voltFlag",
    "voltFlagName"
})
public class Reader {

    protected String beginDate;
    protected String certFlag;
    protected String certFlagName;
    protected String certId;
    protected float debt;
    protected String dept;
    protected String email;
    protected String endDate;
    protected String grade;
    protected String idCard;
    protected int lendQuantity;
    protected String limitFlag;
    protected String limitFlagName;
    protected int maxLendQuantity;
    protected String name;
    protected String readerCertId;
    protected String readerDept;
    protected String readerType;
    protected String registerDay;
    protected String sex;
    protected int voltFlag;
    protected String voltFlagName;

    /**
     * 获取beginDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeginDate() {
        return beginDate;
    }

    /**
     * 设置beginDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeginDate(String value) {
        this.beginDate = value;
    }

    /**
     * 获取certFlag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertFlag() {
        return certFlag;
    }

    /**
     * 设置certFlag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertFlag(String value) {
        this.certFlag = value;
    }

    /**
     * 获取certFlagName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertFlagName() {
        return certFlagName;
    }

    /**
     * 设置certFlagName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertFlagName(String value) {
        this.certFlagName = value;
    }

    /**
     * 获取certId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertId() {
        return certId;
    }

    /**
     * 设置certId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertId(String value) {
        this.certId = value;
    }

    /**
     * 获取debt属性的值。
     * 
     */
    public float getDebt() {
        return debt;
    }

    /**
     * 设置debt属性的值。
     * 
     */
    public void setDebt(float value) {
        this.debt = value;
    }

    /**
     * 获取dept属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDept() {
        return dept;
    }

    /**
     * 设置dept属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDept(String value) {
        this.dept = value;
    }

    /**
     * 获取email属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置email属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * 获取endDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * 设置endDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndDate(String value) {
        this.endDate = value;
    }

    /**
     * 获取grade属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrade() {
        return grade;
    }

    /**
     * 设置grade属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrade(String value) {
        this.grade = value;
    }

    /**
     * 获取idCard属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * 设置idCard属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdCard(String value) {
        this.idCard = value;
    }

    /**
     * 获取lendQuantity属性的值。
     * 
     */
    public int getLendQuantity() {
        return lendQuantity;
    }

    /**
     * 设置lendQuantity属性的值。
     * 
     */
    public void setLendQuantity(int value) {
        this.lendQuantity = value;
    }

    /**
     * 获取limitFlag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLimitFlag() {
        return limitFlag;
    }

    /**
     * 设置limitFlag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLimitFlag(String value) {
        this.limitFlag = value;
    }

    /**
     * 获取limitFlagName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLimitFlagName() {
        return limitFlagName;
    }

    /**
     * 设置limitFlagName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLimitFlagName(String value) {
        this.limitFlagName = value;
    }

    /**
     * 获取maxLendQuantity属性的值。
     * 
     */
    public int getMaxLendQuantity() {
        return maxLendQuantity;
    }

    /**
     * 设置maxLendQuantity属性的值。
     * 
     */
    public void setMaxLendQuantity(int value) {
        this.maxLendQuantity = value;
    }

    /**
     * 获取name属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * 设置name属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * 获取readerCertId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReaderCertId() {
        return readerCertId;
    }

    /**
     * 设置readerCertId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReaderCertId(String value) {
        this.readerCertId = value;
    }

    /**
     * 获取readerDept属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReaderDept() {
        return readerDept;
    }

    /**
     * 设置readerDept属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReaderDept(String value) {
        this.readerDept = value;
    }

    /**
     * 获取readerType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReaderType() {
        return readerType;
    }

    /**
     * 设置readerType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReaderType(String value) {
        this.readerType = value;
    }

    /**
     * 获取registerDay属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegisterDay() {
        return registerDay;
    }

    /**
     * 设置registerDay属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegisterDay(String value) {
        this.registerDay = value;
    }

    /**
     * 获取sex属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置sex属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSex(String value) {
        this.sex = value;
    }

    /**
     * 获取voltFlag属性的值。
     * 
     */
    public int getVoltFlag() {
        return voltFlag;
    }

    /**
     * 设置voltFlag属性的值。
     * 
     */
    public void setVoltFlag(int value) {
        this.voltFlag = value;
    }

    /**
     * 获取voltFlagName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVoltFlagName() {
        return voltFlagName;
    }

    /**
     * 设置voltFlagName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVoltFlagName(String value) {
        this.voltFlagName = value;
    }

}
