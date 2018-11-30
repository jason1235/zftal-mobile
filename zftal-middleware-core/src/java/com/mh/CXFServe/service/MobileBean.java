
package com.mh.CXFServe.service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>mobileBean complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="mobileBean">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cacheck" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dqxn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dqxq" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isactive" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jhm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jhmtime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jsName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sfbdsj" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sjradom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sjradomtime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ticket" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="xm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="xtusers" type="{http://service.ca.webservice.zfsoft.com/}hashMap" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="yhm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="zjhm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mobileBean", propOrder = {
    "bm",
    "cacheck",
    "code",
    "dqxn",
    "dqxq",
    "isactive",
    "jhm",
    "jhmtime",
    "jsName",
    "message",
    "sfbdsj",
    "sjradom",
    "sjradomtime",
    "ticket",
    "xm",
    "xtusers",
    "yhm",
    "zjhm"
})
public class MobileBean {

    protected String bm;
    protected boolean cacheck;
    protected String code;
    protected String dqxn;
    protected String dqxq;
    protected String isactive;
    protected String jhm;
    protected String jhmtime;
    protected String jsName;
    protected String message;
    protected String sfbdsj;
    protected String sjradom;
    protected String sjradomtime;
    protected String ticket;
    protected String xm;
    @XmlElement(nillable = true)
    protected List<HashMap> xtusers;
    protected String yhm;
    protected String zjhm;

    /**
     * ��ȡbm���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBm() {
        return bm;
    }

    /**
     * ����bm���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBm(String value) {
        this.bm = value;
    }

    /**
     * ��ȡcacheck���Ե�ֵ��
     * 
     */
    public boolean isCacheck() {
        return cacheck;
    }

    /**
     * ����cacheck���Ե�ֵ��
     * 
     */
    public void setCacheck(boolean value) {
        this.cacheck = value;
    }

    /**
     * ��ȡcode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * ����code���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * ��ȡdqxn���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDqxn() {
        return dqxn;
    }

    /**
     * ����dqxn���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDqxn(String value) {
        this.dqxn = value;
    }

    /**
     * ��ȡdqxq���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDqxq() {
        return dqxq;
    }

    /**
     * ����dqxq���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDqxq(String value) {
        this.dqxq = value;
    }

    /**
     * ��ȡisactive���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsactive() {
        return isactive;
    }

    /**
     * ����isactive���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsactive(String value) {
        this.isactive = value;
    }

    /**
     * ��ȡjhm���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJhm() {
        return jhm;
    }

    /**
     * ����jhm���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJhm(String value) {
        this.jhm = value;
    }

    /**
     * ��ȡjhmtime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJhmtime() {
        return jhmtime;
    }

    /**
     * ����jhmtime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJhmtime(String value) {
        this.jhmtime = value;
    }

    /**
     * ��ȡjsName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJsName() {
        return jsName;
    }

    /**
     * ����jsName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJsName(String value) {
        this.jsName = value;
    }

    /**
     * ��ȡmessage���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * ����message���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * ��ȡsfbdsj���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSfbdsj() {
        return sfbdsj;
    }

    /**
     * ����sfbdsj���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSfbdsj(String value) {
        this.sfbdsj = value;
    }

    /**
     * ��ȡsjradom���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSjradom() {
        return sjradom;
    }

    /**
     * ����sjradom���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSjradom(String value) {
        this.sjradom = value;
    }

    /**
     * ��ȡsjradomtime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSjradomtime() {
        return sjradomtime;
    }

    /**
     * ����sjradomtime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSjradomtime(String value) {
        this.sjradomtime = value;
    }

    /**
     * ��ȡticket���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * ����ticket���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTicket(String value) {
        this.ticket = value;
    }

    /**
     * ��ȡxm���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXm() {
        return xm;
    }

    /**
     * ����xm���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXm(String value) {
        this.xm = value;
    }

    /**
     * Gets the value of the xtusers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the xtusers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getXtusers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HashMap }
     * 
     * 
     */
    public List<HashMap> getXtusers() {
        if (xtusers == null) {
            xtusers = new ArrayList<HashMap>();
        }
        return this.xtusers;
    }

    /**
     * ��ȡyhm���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYhm() {
        return yhm;
    }

    /**
     * ����yhm���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYhm(String value) {
        this.yhm = value;
    }

    /**
     * ��ȡzjhm���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZjhm() {
        return zjhm;
    }

    /**
     * ����zjhm���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZjhm(String value) {
        this.zjhm = value;
    }

}
