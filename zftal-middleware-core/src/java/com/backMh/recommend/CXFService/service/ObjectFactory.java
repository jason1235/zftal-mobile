
package com.backMh.recommend.CXFService.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.zfsoft.general.webservice.logic.mhrecommend.service package. 
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

    private final static QName _GetMhRecommendListResponse_QNAME = new QName("http://service.mhrecommend.logic.webservice.general.zfsoft.com/", "getMhRecommendListResponse");
    private final static QName _GetMhRecommendPageResponse_QNAME = new QName("http://service.mhrecommend.logic.webservice.general.zfsoft.com/", "getMhRecommendPageResponse");
    private final static QName _GetMhRecommendPage_QNAME = new QName("http://service.mhrecommend.logic.webservice.general.zfsoft.com/", "getMhRecommendPage");
    private final static QName _GetRecommendInfo_QNAME = new QName("http://service.mhrecommend.logic.webservice.general.zfsoft.com/", "getRecommendInfo");
    private final static QName _GetMhRecommendList_QNAME = new QName("http://service.mhrecommend.logic.webservice.general.zfsoft.com/", "getMhRecommendList");
    private final static QName _GetRecommendInfoResponse_QNAME = new QName("http://service.mhrecommend.logic.webservice.general.zfsoft.com/", "getRecommendInfoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.zfsoft.general.webservice.logic.mhrecommend.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetMhRecommendListResponse }
     * 
     */
    public GetMhRecommendListResponse createGetMhRecommendListResponse() {
        return new GetMhRecommendListResponse();
    }

    /**
     * Create an instance of {@link GetMhRecommendPageResponse }
     * 
     */
    public GetMhRecommendPageResponse createGetMhRecommendPageResponse() {
        return new GetMhRecommendPageResponse();
    }

    /**
     * Create an instance of {@link GetRecommendInfo }
     * 
     */
    public GetRecommendInfo createGetRecommendInfo() {
        return new GetRecommendInfo();
    }

    /**
     * Create an instance of {@link GetMhRecommendPage }
     * 
     */
    public GetMhRecommendPage createGetMhRecommendPage() {
        return new GetMhRecommendPage();
    }

    /**
     * Create an instance of {@link GetRecommendInfoResponse }
     * 
     */
    public GetRecommendInfoResponse createGetRecommendInfoResponse() {
        return new GetRecommendInfoResponse();
    }

    /**
     * Create an instance of {@link GetMhRecommendList }
     * 
     */
    public GetMhRecommendList createGetMhRecommendList() {
        return new GetMhRecommendList();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMhRecommendListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.mhrecommend.logic.webservice.general.zfsoft.com/", name = "getMhRecommendListResponse")
    public JAXBElement<GetMhRecommendListResponse> createGetMhRecommendListResponse(GetMhRecommendListResponse value) {
        return new JAXBElement<GetMhRecommendListResponse>(_GetMhRecommendListResponse_QNAME, GetMhRecommendListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMhRecommendPageResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.mhrecommend.logic.webservice.general.zfsoft.com/", name = "getMhRecommendPageResponse")
    public JAXBElement<GetMhRecommendPageResponse> createGetMhRecommendPageResponse(GetMhRecommendPageResponse value) {
        return new JAXBElement<GetMhRecommendPageResponse>(_GetMhRecommendPageResponse_QNAME, GetMhRecommendPageResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMhRecommendPage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.mhrecommend.logic.webservice.general.zfsoft.com/", name = "getMhRecommendPage")
    public JAXBElement<GetMhRecommendPage> createGetMhRecommendPage(GetMhRecommendPage value) {
        return new JAXBElement<GetMhRecommendPage>(_GetMhRecommendPage_QNAME, GetMhRecommendPage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRecommendInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.mhrecommend.logic.webservice.general.zfsoft.com/", name = "getRecommendInfo")
    public JAXBElement<GetRecommendInfo> createGetRecommendInfo(GetRecommendInfo value) {
        return new JAXBElement<GetRecommendInfo>(_GetRecommendInfo_QNAME, GetRecommendInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMhRecommendList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.mhrecommend.logic.webservice.general.zfsoft.com/", name = "getMhRecommendList")
    public JAXBElement<GetMhRecommendList> createGetMhRecommendList(GetMhRecommendList value) {
        return new JAXBElement<GetMhRecommendList>(_GetMhRecommendList_QNAME, GetMhRecommendList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRecommendInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.mhrecommend.logic.webservice.general.zfsoft.com/", name = "getRecommendInfoResponse")
    public JAXBElement<GetRecommendInfoResponse> createGetRecommendInfoResponse(GetRecommendInfoResponse value) {
        return new JAXBElement<GetRecommendInfoResponse>(_GetRecommendInfoResponse_QNAME, GetRecommendInfoResponse.class, null, value);
    }

}
