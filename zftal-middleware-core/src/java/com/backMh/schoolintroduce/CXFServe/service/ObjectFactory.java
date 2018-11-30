
package com.backMh.schoolintroduce.CXFServe.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.zfsoft.general.webservice.logic.schoolintroduce.service package. 
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

    private final static QName _GetSchoolIntroduceTitleList_QNAME = new QName("http://service.schoolintroduce.logic.webservice.general.zfsoft.com/", "getSchoolIntroduceTitleList");
    private final static QName _GetRootSchoolIntroduceTypeListResponse_QNAME = new QName("http://service.schoolintroduce.logic.webservice.general.zfsoft.com/", "getRootSchoolIntroduceTypeListResponse");
    private final static QName _GetSchoolIntroduceById_QNAME = new QName("http://service.schoolintroduce.logic.webservice.general.zfsoft.com/", "getSchoolIntroduceById");
    private final static QName _GetSchoolIntroduceTitleListResponse_QNAME = new QName("http://service.schoolintroduce.logic.webservice.general.zfsoft.com/", "getSchoolIntroduceTitleListResponse");
    private final static QName _GetSchoolIntroduceByIdResponse_QNAME = new QName("http://service.schoolintroduce.logic.webservice.general.zfsoft.com/", "getSchoolIntroduceByIdResponse");
    private final static QName _GetRootSchoolIntroduceTypeList_QNAME = new QName("http://service.schoolintroduce.logic.webservice.general.zfsoft.com/", "getRootSchoolIntroduceTypeList");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.zfsoft.general.webservice.logic.schoolintroduce.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetSchoolIntroduceTitleListResponse }
     * 
     */
    public GetSchoolIntroduceTitleListResponse createGetSchoolIntroduceTitleListResponse() {
        return new GetSchoolIntroduceTitleListResponse();
    }

    /**
     * Create an instance of {@link GetSchoolIntroduceById }
     * 
     */
    public GetSchoolIntroduceById createGetSchoolIntroduceById() {
        return new GetSchoolIntroduceById();
    }

    /**
     * Create an instance of {@link GetRootSchoolIntroduceTypeListResponse }
     * 
     */
    public GetRootSchoolIntroduceTypeListResponse createGetRootSchoolIntroduceTypeListResponse() {
        return new GetRootSchoolIntroduceTypeListResponse();
    }

    /**
     * Create an instance of {@link GetSchoolIntroduceTitleList }
     * 
     */
    public GetSchoolIntroduceTitleList createGetSchoolIntroduceTitleList() {
        return new GetSchoolIntroduceTitleList();
    }

    /**
     * Create an instance of {@link GetRootSchoolIntroduceTypeList }
     * 
     */
    public GetRootSchoolIntroduceTypeList createGetRootSchoolIntroduceTypeList() {
        return new GetRootSchoolIntroduceTypeList();
    }

    /**
     * Create an instance of {@link GetSchoolIntroduceByIdResponse }
     * 
     */
    public GetSchoolIntroduceByIdResponse createGetSchoolIntroduceByIdResponse() {
        return new GetSchoolIntroduceByIdResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSchoolIntroduceTitleList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.schoolintroduce.logic.webservice.general.zfsoft.com/", name = "getSchoolIntroduceTitleList")
    public JAXBElement<GetSchoolIntroduceTitleList> createGetSchoolIntroduceTitleList(GetSchoolIntroduceTitleList value) {
        return new JAXBElement<GetSchoolIntroduceTitleList>(_GetSchoolIntroduceTitleList_QNAME, GetSchoolIntroduceTitleList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRootSchoolIntroduceTypeListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.schoolintroduce.logic.webservice.general.zfsoft.com/", name = "getRootSchoolIntroduceTypeListResponse")
    public JAXBElement<GetRootSchoolIntroduceTypeListResponse> createGetRootSchoolIntroduceTypeListResponse(GetRootSchoolIntroduceTypeListResponse value) {
        return new JAXBElement<GetRootSchoolIntroduceTypeListResponse>(_GetRootSchoolIntroduceTypeListResponse_QNAME, GetRootSchoolIntroduceTypeListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSchoolIntroduceById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.schoolintroduce.logic.webservice.general.zfsoft.com/", name = "getSchoolIntroduceById")
    public JAXBElement<GetSchoolIntroduceById> createGetSchoolIntroduceById(GetSchoolIntroduceById value) {
        return new JAXBElement<GetSchoolIntroduceById>(_GetSchoolIntroduceById_QNAME, GetSchoolIntroduceById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSchoolIntroduceTitleListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.schoolintroduce.logic.webservice.general.zfsoft.com/", name = "getSchoolIntroduceTitleListResponse")
    public JAXBElement<GetSchoolIntroduceTitleListResponse> createGetSchoolIntroduceTitleListResponse(GetSchoolIntroduceTitleListResponse value) {
        return new JAXBElement<GetSchoolIntroduceTitleListResponse>(_GetSchoolIntroduceTitleListResponse_QNAME, GetSchoolIntroduceTitleListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSchoolIntroduceByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.schoolintroduce.logic.webservice.general.zfsoft.com/", name = "getSchoolIntroduceByIdResponse")
    public JAXBElement<GetSchoolIntroduceByIdResponse> createGetSchoolIntroduceByIdResponse(GetSchoolIntroduceByIdResponse value) {
        return new JAXBElement<GetSchoolIntroduceByIdResponse>(_GetSchoolIntroduceByIdResponse_QNAME, GetSchoolIntroduceByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRootSchoolIntroduceTypeList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.schoolintroduce.logic.webservice.general.zfsoft.com/", name = "getRootSchoolIntroduceTypeList")
    public JAXBElement<GetRootSchoolIntroduceTypeList> createGetRootSchoolIntroduceTypeList(GetRootSchoolIntroduceTypeList value) {
        return new JAXBElement<GetRootSchoolIntroduceTypeList>(_GetRootSchoolIntroduceTypeList_QNAME, GetRootSchoolIntroduceTypeList.class, null, value);
    }

}
