
package com.newmobile.LibService.CXFService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>book complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="book">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="author" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="barcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="callNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="locationName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="propNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="publisher" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="stateName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "book", propOrder = {
        "author",
        "barcode",
        "callNo",
        "locationName",
        "price",
        "propNo",
        "publisher",
        "state",
        "stateName",
        "title"
})
public class Book {

    protected String author;
    protected String barcode;
    protected String callNo;
    protected String locationName;
    protected float price;
    protected String propNo;
    protected String publisher;
    protected String state;
    protected String stateName;
    protected String title;

    /**
     * 获取author属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 设置author属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAuthor(String value) {
        this.author = value;
    }

    /**
     * 获取barcode属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * 设置barcode属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBarcode(String value) {
        this.barcode = value;
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
     * 获取locationName属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * 设置locationName属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setLocationName(String value) {
        this.locationName = value;
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
     * 获取publisher属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * 设置publisher属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPublisher(String value) {
        this.publisher = value;
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
     * 获取stateName属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * 设置stateName属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setStateName(String value) {
        this.stateName = value;
    }

    /**
     * 获取title属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置title属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTitle(String value) {
        this.title = value;
    }

}
