
package com.newmobile.LibService.CXFService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>account complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="account">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="billNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="paymentFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="paymentFlagName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="paymentItem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="paymentItemName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="paymentType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="paymentTypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "account", propOrder = {
        "amount",
        "billNo",
        "certId",
        "name",
        "paymentFlag",
        "paymentFlagName",
        "paymentItem",
        "paymentItemName",
        "paymentType",
        "paymentTypeName"
})
public class Account {

    protected float amount;
    protected String billNo;
    protected String certId;
    protected String name;
    protected String paymentFlag;
    protected String paymentFlagName;
    protected String paymentItem;
    protected String paymentItemName;
    protected String paymentType;
    protected String paymentTypeName;

    /**
     * 获取amount属性的值。
     *
     */
    public float getAmount() {
        return amount;
    }

    /**
     * 设置amount属性的值。
     *
     */
    public void setAmount(float value) {
        this.amount = value;
    }

    /**
     * 获取billNo属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 设置billNo属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBillNo(String value) {
        this.billNo = value;
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
     * 获取paymentFlag属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPaymentFlag() {
        return paymentFlag;
    }

    /**
     * 设置paymentFlag属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPaymentFlag(String value) {
        this.paymentFlag = value;
    }

    /**
     * 获取paymentFlagName属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPaymentFlagName() {
        return paymentFlagName;
    }

    /**
     * 设置paymentFlagName属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPaymentFlagName(String value) {
        this.paymentFlagName = value;
    }

    /**
     * 获取paymentItem属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPaymentItem() {
        return paymentItem;
    }

    /**
     * 设置paymentItem属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPaymentItem(String value) {
        this.paymentItem = value;
    }

    /**
     * 获取paymentItemName属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPaymentItemName() {
        return paymentItemName;
    }

    /**
     * 设置paymentItemName属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPaymentItemName(String value) {
        this.paymentItemName = value;
    }

    /**
     * 获取paymentType属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * 设置paymentType属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPaymentType(String value) {
        this.paymentType = value;
    }

    /**
     * 获取paymentTypeName属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPaymentTypeName() {
        return paymentTypeName;
    }

    /**
     * 设置paymentTypeName属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPaymentTypeName(String value) {
        this.paymentTypeName = value;
    }

}
