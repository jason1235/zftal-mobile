
package com.newmobile.LibService.CXFService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>checkRedrResult complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡcode���Ե�ֵ��
     * 
     */
    public int getCode() {
        return code;
    }

    /**
     * ����code���Ե�ֵ��
     * 
     */
    public void setCode(int value) {
        this.code = value;
    }

    /**
     * ��ȡreader���Ե�ֵ��
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
     * ����reader���Ե�ֵ��
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
