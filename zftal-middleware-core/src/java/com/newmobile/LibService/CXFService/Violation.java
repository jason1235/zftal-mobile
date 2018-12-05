
package com.newmobile.LibService.CXFService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>violation complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="violation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="certId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dealFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dealFlagName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fineAmount" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="frozEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pulishDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="voltName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "violation", propOrder = {
        "certId",
        "dealFlag",
        "dealFlagName",
        "fineAmount",
        "frozEndDate",
        "name",
        "pulishDate",
        "voltName"
})
public class Violation {

    protected String certId;
    protected String dealFlag;
    protected String dealFlagName;
    protected float fineAmount;
    protected String frozEndDate;
    protected String name;
    protected String pulishDate;
    protected String voltName;

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
     * 获取dealFlag属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDealFlag() {
        return dealFlag;
    }

    /**
     * 设置dealFlag属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDealFlag(String value) {
        this.dealFlag = value;
    }

    /**
     * 获取dealFlagName属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDealFlagName() {
        return dealFlagName;
    }

    /**
     * 设置dealFlagName属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDealFlagName(String value) {
        this.dealFlagName = value;
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
     * 获取frozEndDate属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFrozEndDate() {
        return frozEndDate;
    }

    /**
     * 设置frozEndDate属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFrozEndDate(String value) {
        this.frozEndDate = value;
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
     * 获取pulishDate属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPulishDate() {
        return pulishDate;
    }

    /**
     * 设置pulishDate属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPulishDate(String value) {
        this.pulishDate = value;
    }

    /**
     * 获取voltName属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getVoltName() {
        return voltName;
    }

    /**
     * 设置voltName属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setVoltName(String value) {
        this.voltName = value;
    }

}
