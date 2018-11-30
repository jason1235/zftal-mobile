
package com.backMh.schoolsights.CXFService.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.zfsoft.general.webservice.logic.schoolsights.service package. 
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

    private final static QName _GetTypeSchoolSightsPageListNew_QNAME = new QName("http://service.schoolsights.logic.webservice.general.zfsoft.com/", "getTypeSchoolSightsPageListNew");
    private final static QName _GetTypeSchoolSightsPageListResponse_QNAME = new QName("http://service.schoolsights.logic.webservice.general.zfsoft.com/", "getTypeSchoolSightsPageListResponse");
    private final static QName _GetAllSchoolTypeListResponse_QNAME = new QName("http://service.schoolsights.logic.webservice.general.zfsoft.com/", "getAllSchoolTypeListResponse");
    private final static QName _GetTypeSchoolSightsPageList_QNAME = new QName("http://service.schoolsights.logic.webservice.general.zfsoft.com/", "getTypeSchoolSightsPageList");
    private final static QName _GetAllSchoolTypeList_QNAME = new QName("http://service.schoolsights.logic.webservice.general.zfsoft.com/", "getAllSchoolTypeList");
    private final static QName _GetTypeSchoolSightsPageListNewResponse_QNAME = new QName("http://service.schoolsights.logic.webservice.general.zfsoft.com/", "getTypeSchoolSightsPageListNewResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.zfsoft.general.webservice.logic.schoolsights.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetTypeSchoolSightsPageListNewResponse }
     * 
     */
    public GetTypeSchoolSightsPageListNewResponse createGetTypeSchoolSightsPageListNewResponse() {
        return new GetTypeSchoolSightsPageListNewResponse();
    }

    /**
     * Create an instance of {@link GetAllSchoolTypeList }
     * 
     */
    public GetAllSchoolTypeList createGetAllSchoolTypeList() {
        return new GetAllSchoolTypeList();
    }

    /**
     * Create an instance of {@link GetAllSchoolTypeListResponse }
     * 
     */
    public GetAllSchoolTypeListResponse createGetAllSchoolTypeListResponse() {
        return new GetAllSchoolTypeListResponse();
    }

    /**
     * Create an instance of {@link GetTypeSchoolSightsPageListResponse }
     * 
     */
    public GetTypeSchoolSightsPageListResponse createGetTypeSchoolSightsPageListResponse() {
        return new GetTypeSchoolSightsPageListResponse();
    }

    /**
     * Create an instance of {@link GetTypeSchoolSightsPageListNew }
     * 
     */
    public GetTypeSchoolSightsPageListNew createGetTypeSchoolSightsPageListNew() {
        return new GetTypeSchoolSightsPageListNew();
    }

    /**
     * Create an instance of {@link GetTypeSchoolSightsPageList }
     * 
     */
    public GetTypeSchoolSightsPageList createGetTypeSchoolSightsPageList() {
        return new GetTypeSchoolSightsPageList();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTypeSchoolSightsPageListNew }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.schoolsights.logic.webservice.general.zfsoft.com/", name = "getTypeSchoolSightsPageListNew")
    public JAXBElement<GetTypeSchoolSightsPageListNew> createGetTypeSchoolSightsPageListNew(GetTypeSchoolSightsPageListNew value) {
        return new JAXBElement<GetTypeSchoolSightsPageListNew>(_GetTypeSchoolSightsPageListNew_QNAME, GetTypeSchoolSightsPageListNew.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTypeSchoolSightsPageListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.schoolsights.logic.webservice.general.zfsoft.com/", name = "getTypeSchoolSightsPageListResponse")
    public JAXBElement<GetTypeSchoolSightsPageListResponse> createGetTypeSchoolSightsPageListResponse(GetTypeSchoolSightsPageListResponse value) {
        return new JAXBElement<GetTypeSchoolSightsPageListResponse>(_GetTypeSchoolSightsPageListResponse_QNAME, GetTypeSchoolSightsPageListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllSchoolTypeListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.schoolsights.logic.webservice.general.zfsoft.com/", name = "getAllSchoolTypeListResponse")
    public JAXBElement<GetAllSchoolTypeListResponse> createGetAllSchoolTypeListResponse(GetAllSchoolTypeListResponse value) {
        return new JAXBElement<GetAllSchoolTypeListResponse>(_GetAllSchoolTypeListResponse_QNAME, GetAllSchoolTypeListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTypeSchoolSightsPageList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.schoolsights.logic.webservice.general.zfsoft.com/", name = "getTypeSchoolSightsPageList")
    public JAXBElement<GetTypeSchoolSightsPageList> createGetTypeSchoolSightsPageList(GetTypeSchoolSightsPageList value) {
        return new JAXBElement<GetTypeSchoolSightsPageList>(_GetTypeSchoolSightsPageList_QNAME, GetTypeSchoolSightsPageList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllSchoolTypeList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.schoolsights.logic.webservice.general.zfsoft.com/", name = "getAllSchoolTypeList")
    public JAXBElement<GetAllSchoolTypeList> createGetAllSchoolTypeList(GetAllSchoolTypeList value) {
        return new JAXBElement<GetAllSchoolTypeList>(_GetAllSchoolTypeList_QNAME, GetAllSchoolTypeList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTypeSchoolSightsPageListNewResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.schoolsights.logic.webservice.general.zfsoft.com/", name = "getTypeSchoolSightsPageListNewResponse")
    public JAXBElement<GetTypeSchoolSightsPageListNewResponse> createGetTypeSchoolSightsPageListNewResponse(GetTypeSchoolSightsPageListNewResponse value) {
        return new JAXBElement<GetTypeSchoolSightsPageListNewResponse>(_GetTypeSchoolSightsPageListNewResponse_QNAME, GetTypeSchoolSightsPageListNewResponse.class, null, value);
    }

}
