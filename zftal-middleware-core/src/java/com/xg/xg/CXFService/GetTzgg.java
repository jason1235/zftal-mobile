
package com.xg.xg.CXFService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>getTzgg complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="getTzgg">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ReqData" type="{http://xtwh.xgxt.ws.xgxtws.zfsoft.com/}ReqData" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTzgg", propOrder = {
    "reqData"
})
public class GetTzgg {

    @XmlElement(name = "ReqData")
    protected ReqData reqData;

    /**
     * ��ȡreqData���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ReqData }
     *     
     */
    public ReqData getReqData() {
        return reqData;
    }

    /**
     * ����reqData���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ReqData }
     *     
     */
    public void setReqData(ReqData value) {
        this.reqData = value;
    }

}
