
package com.newmobile.LibService.CXFService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>item complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="item">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attachment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="barCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="callNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="carrierName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="locationDept" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="marcRecNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="propNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="volPeri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="year" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "item", propOrder = {
        "attachment",
        "barCode",
        "callNo",
        "carrierName",
        "location",
        "locationDept",
        "marcRecNo",
        "price",
        "propNo",
        "state",
        "volPeri",
        "year"
})
public class Item {

    protected String attachment;
    protected String barCode;
    protected String callNo;
    protected String carrierName;
    protected String location;
    protected String locationDept;
    protected String marcRecNo;
    protected float price;
    protected String propNo;
    protected String state;
    protected String volPeri;
    protected String year;

    /**
     * 获取attachment属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAttachment() {
        return attachment;
    }

    /**
     * 设置attachment属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAttachment(String value) {
        this.attachment = value;
    }

    /**
     * 获取barCode属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBarCode() {
        return barCode;
    }

    /**
     * 设置barCode属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBarCode(String value) {
        this.barCode = value;
    }

    /**
     * 获取callNo属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCallNo() {
        return callNo;
    }

    /**
     * 设置callNo属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCallNo(String value) {
        this.callNo = value;
    }

    /**
     * 获取carrierName属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCarrierName() {
        return carrierName;
    }

    /**
     * 设置carrierName属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCarrierName(String value) {
        this.carrierName = value;
    }

    /**
     * 获取location属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getLocation() {
        return location;
    }

    /**
     * 设置location属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * 获取locationDept属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getLocationDept() {
        return locationDept;
    }

    /**
     * 设置locationDept属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setLocationDept(String value) {
        this.locationDept = value;
    }

    /**
     * 获取marcRecNo属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMarcRecNo() {
        return marcRecNo;
    }

    /**
     * 设置marcRecNo属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMarcRecNo(String value) {
        this.marcRecNo = value;
    }

    /**
     * 获取price属性的值。
     *
     */
    public float getPrice() {
        return price;
    }

    /**
     * 设置price属性的值。
     *
     */
    public void setPrice(float value) {
        this.price = value;
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
     * 获取state属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getState() {
        return state;
    }

    /**
     * 设置state属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setState(String value) {
        this.state = value;
    }

    /**
     * 获取volPeri属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getVolPeri() {
        return volPeri;
    }

    /**
     * 设置volPeri属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setVolPeri(String value) {
        this.volPeri = value;
    }

    /**
     * 获取year属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getYear() {
        return year;
    }

    /**
     * 设置year属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setYear(String value) {
        this.year = value;
    }

}
