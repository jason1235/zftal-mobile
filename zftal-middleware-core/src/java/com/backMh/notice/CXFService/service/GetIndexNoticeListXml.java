
package com.backMh.notice.CXFService.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getIndexNoticeListXml complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getIndexNoticeListXml">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="topCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getIndexNoticeListXml", propOrder = {
    "topCount"
})
public class GetIndexNoticeListXml {

    protected int topCount;

    /**
     * Gets the value of the topCount property.
     * 
     */
    public int getTopCount() {
        return topCount;
    }

    /**
     * Sets the value of the topCount property.
     * 
     */
    public void setTopCount(int value) {
        this.topCount = value;
    }

}
