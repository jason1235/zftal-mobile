
package com.newmobile.LibService.CXFService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>relegate complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="relegate">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.ws.hw.com/}pregOrRelegate">
 *       &lt;sequence>
 *         &lt;element name="relegateDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="relegateEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "relegate", propOrder = {
    "relegateDate",
    "relegateEndDate"
})
public class Relegate
    extends PregOrRelegate
{

    protected String relegateDate;
    protected String relegateEndDate;

    /**
     * ��ȡrelegateDate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelegateDate() {
        return relegateDate;
    }

    /**
     * ����relegateDate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelegateDate(String value) {
        this.relegateDate = value;
    }

    /**
     * ��ȡrelegateEndDate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelegateEndDate() {
        return relegateEndDate;
    }

    /**
     * ����relegateEndDate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelegateEndDate(String value) {
        this.relegateEndDate = value;
    }

}
