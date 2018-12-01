
package com.backMh.notice.CXFService.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.zfsoft.general.webservice.sys.notice.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetNoticeInfo_QNAME = new QName("http://service.notice.sys.webservice.general.zfsoft.com/", "getNoticeInfo");
    private final static QName _GetNoticeInfoResponse_QNAME = new QName("http://service.notice.sys.webservice.general.zfsoft.com/", "getNoticeInfoResponse");
    private final static QName _GetIndexNoticeListXml_QNAME = new QName("http://service.notice.sys.webservice.general.zfsoft.com/", "getIndexNoticeListXml");
    private final static QName _GetIndexNoticeListXmlResponse_QNAME = new QName("http://service.notice.sys.webservice.general.zfsoft.com/", "getIndexNoticeListXmlResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.zfsoft.general.webservice.sys.notice.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetNoticeInfo }
     * 
     */
    public GetNoticeInfo createGetNoticeInfo() {
        return new GetNoticeInfo();
    }

    /**
     * Create an instance of {@link GetNoticeInfoResponse }
     * 
     */
    public GetNoticeInfoResponse createGetNoticeInfoResponse() {
        return new GetNoticeInfoResponse();
    }

    /**
     * Create an instance of {@link GetIndexNoticeListXml }
     * 
     */
    public GetIndexNoticeListXml createGetIndexNoticeListXml() {
        return new GetIndexNoticeListXml();
    }

    /**
     * Create an instance of {@link GetIndexNoticeListXmlResponse }
     * 
     */
    public GetIndexNoticeListXmlResponse createGetIndexNoticeListXmlResponse() {
        return new GetIndexNoticeListXmlResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNoticeInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.notice.sys.webservice.general.zfsoft.com/", name = "getNoticeInfo")
    public JAXBElement<GetNoticeInfo> createGetNoticeInfo(GetNoticeInfo value) {
        return new JAXBElement<GetNoticeInfo>(_GetNoticeInfo_QNAME, GetNoticeInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNoticeInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.notice.sys.webservice.general.zfsoft.com/", name = "getNoticeInfoResponse")
    public JAXBElement<GetNoticeInfoResponse> createGetNoticeInfoResponse(GetNoticeInfoResponse value) {
        return new JAXBElement<GetNoticeInfoResponse>(_GetNoticeInfoResponse_QNAME, GetNoticeInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIndexNoticeListXml }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.notice.sys.webservice.general.zfsoft.com/", name = "getIndexNoticeListXml")
    public JAXBElement<GetIndexNoticeListXml> createGetIndexNoticeListXml(GetIndexNoticeListXml value) {
        return new JAXBElement<GetIndexNoticeListXml>(_GetIndexNoticeListXml_QNAME, GetIndexNoticeListXml.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIndexNoticeListXmlResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.notice.sys.webservice.general.zfsoft.com/", name = "getIndexNoticeListXmlResponse")
    public JAXBElement<GetIndexNoticeListXmlResponse> createGetIndexNoticeListXmlResponse(GetIndexNoticeListXmlResponse value) {
        return new JAXBElement<GetIndexNoticeListXmlResponse>(_GetIndexNoticeListXmlResponse_QNAME, GetIndexNoticeListXmlResponse.class, null, value);
    }

}
