
package com.newmobile.LibService.CXFService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>debt complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="debt">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="certId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="debtDealFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="debtDealFlagName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dueFineAmount" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="fineAmount" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="lendDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="locationF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="propNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="retDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "debt", propOrder = {
        "certId",
        "debtDealFlag",
        "debtDealFlagName",
        "dueFineAmount",
        "fineAmount",
        "lendDate",
        "locationF",
        "name",
        "propNo",
        "retDate"
})
public class Debt {

    protected String certId;
    protected String debtDealFlag;
    protected String debtDealFlagName;
    protected float dueFineAmount;
    protected float fineAmount;
    protected String lendDate;
    protected String locationF;
    protected String name;
    protected String propNo;
    protected String retDate;

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
     * 获取debtDealFlag属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDebtDealFlag() {
        return debtDealFlag;
    }

    /**
     * 设置debtDealFlag属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDebtDealFlag(String value) {
        this.debtDealFlag = value;
    }

    /**
     * 获取debtDealFlagName属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDebtDealFlagName() {
        return debtDealFlagName;
    }

    /**
     * 设置debtDealFlagName属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDebtDealFlagName(String value) {
        this.debtDealFlagName = value;
    }

    /**
     * 获取dueFineAmount属性的值。
     *
     */
    public float getDueFineAmount() {
        return dueFineAmount;
    }

    /**
     * 设置dueFineAmount属性的值。
     *
     */
    public void setDueFineAmount(float value) {
        this.dueFineAmount = value;
    }

    /**
     * 获取fineAmount属性的值。
     *
     */
    public float getFineAmount() {
        return fineAmount;
    }

    /**
     * 设置fineAmount属性的值。
     *
     */
    public void setFineAmount(float value) {
        this.fineAmount = value;
    }

    /**
     * 获取lendDate属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getLendDate() {
        return lendDate;
    }

    /**
     * 设置lendDate属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setLendDate(String value) {
        this.lendDate = value;
    }

    /**
     * 获取locationF属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getLocationF() {
        return locationF;
    }

    /**
     * 设置locationF属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setLocationF(String value) {
        this.locationF = value;
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
     * 获取propNo属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPropNo() {
        return propNo;
    }

    /**
     * 设置propNo属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPropNo(String value) {
        this.propNo = value;
    }

    /**
     * 获取retDate属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getRetDate() {
        return retDate;
    }

    /**
     * 设置retDate属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setRetDate(String value) {
        this.retDate = value;
    }

}
