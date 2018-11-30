
package com.backMh.news.CXFService.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>getIndexNewsListNew complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�����ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="getIndexNewsListNew">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="start" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getIndexNewsListNew", propOrder = {
    "start",
    "size"
})
public class GetIndexNewsListNew {

    protected int start;
    protected int size;

    /**
     * ��ȡstart���Ե�ֵ��
     * 
     */
    public int getStart() {
        return start;
    }

    /**
     * ����start���Ե�ֵ��
     * 
     */
    public void setStart(int value) {
        this.start = value;
    }

    /**
     * ��ȡsize���Ե�ֵ��
     * 
     */
    public int getSize() {
        return size;
    }

    /**
     * ����size���Ե�ֵ��
     * 
     */
    public void setSize(int value) {
        this.size = value;
    }

}
