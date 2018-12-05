
package com.newmobile.LibService.CXFService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>marc complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="marc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="author" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="callNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isbn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="marcRecNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pubYear" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="publisher" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "marc", propOrder = {
        "author",
        "callNo",
        "isbn",
        "marcRecNo",
        "pubYear",
        "publisher",
        "title"
})
public class Marc {

    protected String author;
    protected String callNo;
    protected String isbn;
    protected String marcRecNo;
    protected String pubYear;
    protected String publisher;
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
     * 获取isbn属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * 设置isbn属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setIsbn(String value) {
        this.isbn = value;
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
     * 获取pubYear属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPubYear() {
        return pubYear;
    }

    /**
     * 设置pubYear属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPubYear(String value) {
        this.pubYear = value;
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
