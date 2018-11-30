
package com.mh.CXFServe.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>buildsjhm complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="buildsjhm">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sjhm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="yzm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "buildsjhm", propOrder = {
    "sjhm",
    "yzm"
})
public class Buildsjhm {

    protected String sjhm;
    protected String yzm;

    /**
     * 获取sjhm属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSjhm() {
        return sjhm;
    }

    /**
     * 设置sjhm属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSjhm(String value) {
        this.sjhm = value;
    }

    /**
     * 获取yzm属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYzm() {
        return yzm;
    }

    /**
     * 设置yzm属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYzm(String value) {
        this.yzm = value;
    }

}
