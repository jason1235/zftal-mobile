
package com.xg.xg.CXFService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ReqData complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="ReqData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="yhm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="yhlx" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cipher" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataMap" type="{http://xtwh.xgxt.ws.xgxtws.zfsoft.com/}MapParameter" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReqData", propOrder = {
        "yhm",
        "yhlx",
        "timestamp",
        "cipher",
        "dataMap"
})
public class ReqData {

    protected String yhm;
    protected String yhlx;
    protected String timestamp;
    protected String cipher;
    protected MapParameter dataMap;

    /**
     * 获取yhm属性的值。
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
     * 设置yhm属性的值。
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
     * 获取yhlx属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getYhlx() {
        return yhlx;
    }

    /**
     * 设置yhlx属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setYhlx(String value) {
        this.yhlx = value;
    }

    /**
     * 获取timestamp属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * 设置timestamp属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTimestamp(String value) {
        this.timestamp = value;
    }

    /**
     * 获取cipher属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCipher() {
        return cipher;
    }

    /**
     * 设置cipher属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCipher(String value) {
        this.cipher = value;
    }

    /**
     * 获取dataMap属性的值。
     *
     * @return
     *     possible object is
     *     {@link MapParameter }
     *
     */
    public MapParameter getDataMap() {
        return dataMap;
    }

    /**
     * 设置dataMap属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link MapParameter }
     *
     */
    public void setDataMap(MapParameter value) {
        this.dataMap = value;
    }

}
