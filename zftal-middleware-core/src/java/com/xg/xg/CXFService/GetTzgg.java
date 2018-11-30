
package com.xg.xg.CXFService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>getTzgg complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
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
     * 获取reqData属性的值。
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
     * 设置reqData属性的值。
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
