
package com.xg.xg.CXFService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>getLastNoticeList complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="getLastNoticeList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="yhm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="yhlx" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="num" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="sign" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pageIndex" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getLastNoticeList", propOrder = {
        "yhm",
        "yhlx",
        "type",
        "num",
        "sign",
        "pageIndex"
})
public class GetLastNoticeList {

    protected String yhm;
    protected String yhlx;
    protected String type;
    protected Integer num;
    protected String sign;
    protected Integer pageIndex;

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
     * 获取type属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getType() {
        return type;
    }

    /**
     * 设置type属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * 获取num属性的值。
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置num属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setNum(Integer value) {
        this.num = value;
    }

    /**
     * 获取sign属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSign() {
        return sign;
    }

    /**
     * 设置sign属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSign(String value) {
        this.sign = value;
    }

    /**
     * 获取pageIndex属性的值。
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getPageIndex() {
        return pageIndex;
    }

    /**
     * 设置pageIndex属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setPageIndex(Integer value) {
        this.pageIndex = value;
    }

}
