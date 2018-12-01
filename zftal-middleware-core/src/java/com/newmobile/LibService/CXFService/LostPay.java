
package com.newmobile.LibService.CXFService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>lostPay complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="lostPay">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="barCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bookAmt" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="certId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="propNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="recoupType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="servFee" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="techFee" type="{http://www.w3.org/2001/XMLSchema}float"/>
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
@XmlType(name = "lostPay", propOrder = {
    "barCode",
    "billNo",
    "bookAmt",
    "certId",
    "name",
    "propNo",
    "recoupType",
    "servFee",
    "techFee",
    "title"
})
public class LostPay {

    protected String barCode;
    protected String billNo;
    protected float bookAmt;
    protected String certId;
    protected String name;
    protected String propNo;
    protected String recoupType;
    protected float servFee;
    protected float techFee;
    protected String title;

    /**
     * ��ȡbarCode���Ե�ֵ��
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
     * ����barCode���Ե�ֵ��
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
     * ��ȡbillNo���Ե�ֵ��
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
     * ����billNo���Ե�ֵ��
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
     * ��ȡbookAmt���Ե�ֵ��
     * 
     */
    public float getBookAmt() {
        return bookAmt;
    }

    /**
     * ����bookAmt���Ե�ֵ��
     * 
     */
    public void setBookAmt(float value) {
        this.bookAmt = value;
    }

    /**
     * ��ȡcertId���Ե�ֵ��
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
     * ����certId���Ե�ֵ��
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
     * ��ȡname���Ե�ֵ��
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
     * ����name���Ե�ֵ��
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
     * ��ȡpropNo���Ե�ֵ��
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
     * ����propNo���Ե�ֵ��
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
     * ��ȡrecoupType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecoupType() {
        return recoupType;
    }

    /**
     * ����recoupType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecoupType(String value) {
        this.recoupType = value;
    }

    /**
     * ��ȡservFee���Ե�ֵ��
     * 
     */
    public float getServFee() {
        return servFee;
    }

    /**
     * ����servFee���Ե�ֵ��
     * 
     */
    public void setServFee(float value) {
        this.servFee = value;
    }

    /**
     * ��ȡtechFee���Ե�ֵ��
     * 
     */
    public float getTechFee() {
        return techFee;
    }

    /**
     * ����techFee���Ե�ֵ��
     * 
     */
    public void setTechFee(float value) {
        this.techFee = value;
    }

    /**
     * ��ȡtitle���Ե�ֵ��
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
     * ����title���Ե�ֵ��
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
