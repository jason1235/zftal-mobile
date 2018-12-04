
package com.newmobile.LibService.CXFService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>circ complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="circ">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attachment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="author" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="barcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dueDay" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lendDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="marcRecNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="propNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="renewTimes" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
@XmlType(name = "circ", propOrder = {
        "attachment",
        "author",
        "barcode",
        "certId",
        "dueDay",
        "lendDate",
        "location",
        "marcRecNo",
        "name",
        "price",
        "propNo",
        "renewTimes",
        "title"
})
public class Circ {

    protected String attachment;
    protected String author;
    protected String barcode;
    protected String certId;
    protected String dueDay;
    protected String lendDate;
    protected String location;
    protected String marcRecNo;
    protected String name;
    protected float price;
    protected String propNo;
    protected int renewTimes;
    protected String title;

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
     * 获取dueDay属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDueDay() {
        return dueDay;
    }

    /**
     * 设置dueDay属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDueDay(String value) {
        this.dueDay = value;
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
     * 获取renewTimes属性的值。
     *
     */
    public int getRenewTimes() {
        return renewTimes;
    }

    /**
     * 设置renewTimes属性的值。
     *
     */
    public void setRenewTimes(int value) {
        this.renewTimes = value;
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
