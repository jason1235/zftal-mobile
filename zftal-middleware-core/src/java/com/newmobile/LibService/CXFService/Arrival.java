
package com.newmobile.LibService.CXFService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>arrival complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="arrival">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arrivalDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="author" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="barcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="callNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="keepEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="marcRecNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="propNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="takeLocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "arrival", propOrder = {
        "arrivalDate",
        "author",
        "barcode",
        "callNo",
        "certId",
        "keepEndDate",
        "marcRecNo",
        "name",
        "propNo",
        "takeLocation",
        "title"
})
public class Arrival {

    protected String arrivalDate;
    protected String author;
    protected String barcode;
    protected String callNo;
    protected String certId;
    protected String keepEndDate;
    protected String marcRecNo;
    protected String name;
    protected String propNo;
    protected String takeLocation;
    protected String title;

    /**
     * 获取arrivalDate属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getArrivalDate() {
        return arrivalDate;
    }

    /**
     * 设置arrivalDate属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setArrivalDate(String value) {
        this.arrivalDate = value;
    }

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
     * 获取keepEndDate属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getKeepEndDate() {
        return keepEndDate;
    }

    /**
     * 设置keepEndDate属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setKeepEndDate(String value) {
        this.keepEndDate = value;
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
     * 获取takeLocation属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTakeLocation() {
        return takeLocation;
    }

    /**
     * 设置takeLocation属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTakeLocation(String value) {
        this.takeLocation = value;
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
