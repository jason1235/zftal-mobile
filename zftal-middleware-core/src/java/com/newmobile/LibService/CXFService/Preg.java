
package com.newmobile.LibService.CXFService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>preg complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="preg">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.ws.hw.com/}pregOrRelegate">
 *       &lt;sequence>
 *         &lt;element name="pregDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pregEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "preg", propOrder = {
        "pregDate",
        "pregEndDate"
})
public class Preg
        extends PregOrRelegate
{

    protected String pregDate;
    protected String pregEndDate;

    /**
     * 获取pregDate属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPregDate() {
        return pregDate;
    }

    /**
     * 设置pregDate属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPregDate(String value) {
        this.pregDate = value;
    }

    /**
     * 获取pregEndDate属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPregEndDate() {
        return pregEndDate;
    }

    /**
     * 设置pregEndDate属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPregEndDate(String value) {
        this.pregEndDate = value;
    }

}
