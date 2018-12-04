
package com.newmobile.LibService.CXFService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>checkRedrResult complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="checkRedrResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="reader" type="{http://service.ws.hw.com/}reader" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "checkRedrResult", propOrder = {
        "code",
        "reader"
})
public class CheckRedrResult {

    protected int code;
    protected Reader reader;

    /**
     * 获取code属性的值。
     *
     */
    public int getCode() {
        return code;
    }

    /**
     * 设置code属性的值。
     *
     */
    public void setCode(int value) {
        this.code = value;
    }

    /**
     * 获取reader属性的值。
     *
     * @return
     *     possible object is
     *     {@link Reader }
     *
     */
    public Reader getReader() {
        return reader;
    }

    /**
     * 设置reader属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link Reader }
     *
     */
    public void setReader(Reader value) {
        this.reader = value;
    }

}
