
package com.xg.xg.CXFService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>getQjxx complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getQjxx">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="yhm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="num" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="PAGE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "getQjxx", propOrder = {
    "yhm",
    "num",
    "page",
    "sign"
})
public class GetQjxx {

    protected String yhm;
    protected Integer num;
    @XmlElement(name = "PAGE")
    protected String page;
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
     * 获取page属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPAGE() {
        return page;
    }

    /**
     * 设置page属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPAGE(String value) {
        this.page = value;
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
