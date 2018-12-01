
package com.newmobile.LibService.CXFService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>debt complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡdebtDealFlag���Ե�ֵ��
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
     * ����debtDealFlag���Ե�ֵ��
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
     * ��ȡdebtDealFlagName���Ե�ֵ��
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
     * ����debtDealFlagName���Ե�ֵ��
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
     * ��ȡdueFineAmount���Ե�ֵ��
     * 
     */
    public float getDueFineAmount() {
        return dueFineAmount;
    }

    /**
     * ����dueFineAmount���Ե�ֵ��
     * 
     */
    public void setDueFineAmount(float value) {
        this.dueFineAmount = value;
    }

    /**
     * ��ȡfineAmount���Ե�ֵ��
     * 
     */
    public float getFineAmount() {
        return fineAmount;
    }

    /**
     * ����fineAmount���Ե�ֵ��
     * 
     */
    public void setFineAmount(float value) {
        this.fineAmount = value;
    }

    /**
     * ��ȡlendDate���Ե�ֵ��
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
     * ����lendDate���Ե�ֵ��
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
     * ��ȡlocationF���Ե�ֵ��
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
     * ����locationF���Ե�ֵ��
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
     * ��ȡretDate���Ե�ֵ��
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
     * ����retDate���Ե�ֵ��
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
