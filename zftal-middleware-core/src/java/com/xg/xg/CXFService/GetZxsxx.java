
package com.xg.xg.CXFService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>getZxsxx complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="getZxsxx">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="yhm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="xm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="num" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="sign" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getZxsxx", propOrder = {
        "yhm",
        "xm",
        "num",
        "sign"
})
public class GetZxsxx {

    protected String yhm;
    protected String xm;
    protected Integer num;
    protected String sign;

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
     * 获取xm属性的值。
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
     * 设置xm属性的值。
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

}
