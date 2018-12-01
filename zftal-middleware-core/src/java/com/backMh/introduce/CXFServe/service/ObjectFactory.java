
package com.backMh.introduce.CXFServe.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.zfsoft.general.webservice.logic.introduce.service package. 
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

    private final static QName _GetChildIntroduceTypeListAddPid_QNAME = new QName("http://service.introduce.logic.webservice.general.zfsoft.com/", "getChildIntroduceTypeListAddPid");
    private final static QName _GetChildIntroduceTypeListResponse_QNAME = new QName("http://service.introduce.logic.webservice.general.zfsoft.com/", "getChildIntroduceTypeListResponse");
    private final static QName _GetRootIntroduceTypeList_QNAME = new QName("http://service.introduce.logic.webservice.general.zfsoft.com/", "getRootIntroduceTypeList");
    private final static QName _GetRootIntroduceTypeListResponse_QNAME = new QName("http://service.introduce.logic.webservice.general.zfsoft.com/", "getRootIntroduceTypeListResponse");
    private final static QName _GetChildIntroduceTypeList_QNAME = new QName("http://service.introduce.logic.webservice.general.zfsoft.com/", "getChildIntroduceTypeList");
    private final static QName _GetIntroduceByTypeId_QNAME = new QName("http://service.introduce.logic.webservice.general.zfsoft.com/", "getIntroduceByTypeId");
    private final static QName _GetChildIntroduceTypeListAddPidResponse_QNAME = new QName("http://service.introduce.logic.webservice.general.zfsoft.com/", "getChildIntroduceTypeListAddPidResponse");
    private final static QName _GetIntroduceByTypeIdResponse_QNAME = new QName("http://service.introduce.logic.webservice.general.zfsoft.com/", "getIntroduceByTypeIdResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.zfsoft.general.webservice.logic.introduce.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetChildIntroduceTypeListResponse }
     * 
     */
    public GetChildIntroduceTypeListResponse createGetChildIntroduceTypeListResponse() {
        return new GetChildIntroduceTypeListResponse();
    }

    /**
     * Create an instance of {@link GetChildIntroduceTypeListAddPid }
     * 
     */
    public GetChildIntroduceTypeListAddPid createGetChildIntroduceTypeListAddPid() {
        return new GetChildIntroduceTypeListAddPid();
    }

    /**
     * Create an instance of {@link GetIntroduceByTypeIdResponse }
     * 
     */
    public GetIntroduceByTypeIdResponse createGetIntroduceByTypeIdResponse() {
        return new GetIntroduceByTypeIdResponse();
    }

    /**
     * Create an instance of {@link GetChildIntroduceTypeListAddPidResponse }
     * 
     */
    public GetChildIntroduceTypeListAddPidResponse createGetChildIntroduceTypeListAddPidResponse() {
        return new GetChildIntroduceTypeListAddPidResponse();
    }

    /**
     * Create an instance of {@link GetIntroduceByTypeId }
     * 
     */
    public GetIntroduceByTypeId createGetIntroduceByTypeId() {
        return new GetIntroduceByTypeId();
    }

    /**
     * Create an instance of {@link GetChildIntroduceTypeList }
     * 
     */
    public GetChildIntroduceTypeList createGetChildIntroduceTypeList() {
        return new GetChildIntroduceTypeList();
    }

    /**
     * Create an instance of {@link GetRootIntroduceTypeListResponse }
     * 
     */
    public GetRootIntroduceTypeListResponse createGetRootIntroduceTypeListResponse() {
        return new GetRootIntroduceTypeListResponse();
    }

    /**
     * Create an instance of {@link GetRootIntroduceTypeList }
     * 
     */
    public GetRootIntroduceTypeList createGetRootIntroduceTypeList() {
        return new GetRootIntroduceTypeList();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetChildIntroduceTypeListAddPid }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.introduce.logic.webservice.general.zfsoft.com/", name = "getChildIntroduceTypeListAddPid")
    public JAXBElement<GetChildIntroduceTypeListAddPid> createGetChildIntroduceTypeListAddPid(GetChildIntroduceTypeListAddPid value) {
        return new JAXBElement<GetChildIntroduceTypeListAddPid>(_GetChildIntroduceTypeListAddPid_QNAME, GetChildIntroduceTypeListAddPid.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetChildIntroduceTypeListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.introduce.logic.webservice.general.zfsoft.com/", name = "getChildIntroduceTypeListResponse")
    public JAXBElement<GetChildIntroduceTypeListResponse> createGetChildIntroduceTypeListResponse(GetChildIntroduceTypeListResponse value) {
        return new JAXBElement<GetChildIntroduceTypeListResponse>(_GetChildIntroduceTypeListResponse_QNAME, GetChildIntroduceTypeListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRootIntroduceTypeList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.introduce.logic.webservice.general.zfsoft.com/", name = "getRootIntroduceTypeList")
    public JAXBElement<GetRootIntroduceTypeList> createGetRootIntroduceTypeList(GetRootIntroduceTypeList value) {
        return new JAXBElement<GetRootIntroduceTypeList>(_GetRootIntroduceTypeList_QNAME, GetRootIntroduceTypeList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRootIntroduceTypeListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.introduce.logic.webservice.general.zfsoft.com/", name = "getRootIntroduceTypeListResponse")
    public JAXBElement<GetRootIntroduceTypeListResponse> createGetRootIntroduceTypeListResponse(GetRootIntroduceTypeListResponse value) {
        return new JAXBElement<GetRootIntroduceTypeListResponse>(_GetRootIntroduceTypeListResponse_QNAME, GetRootIntroduceTypeListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetChildIntroduceTypeList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.introduce.logic.webservice.general.zfsoft.com/", name = "getChildIntroduceTypeList")
    public JAXBElement<GetChildIntroduceTypeList> createGetChildIntroduceTypeList(GetChildIntroduceTypeList value) {
        return new JAXBElement<GetChildIntroduceTypeList>(_GetChildIntroduceTypeList_QNAME, GetChildIntroduceTypeList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIntroduceByTypeId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.introduce.logic.webservice.general.zfsoft.com/", name = "getIntroduceByTypeId")
    public JAXBElement<GetIntroduceByTypeId> createGetIntroduceByTypeId(GetIntroduceByTypeId value) {
        return new JAXBElement<GetIntroduceByTypeId>(_GetIntroduceByTypeId_QNAME, GetIntroduceByTypeId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetChildIntroduceTypeListAddPidResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.introduce.logic.webservice.general.zfsoft.com/", name = "getChildIntroduceTypeListAddPidResponse")
    public JAXBElement<GetChildIntroduceTypeListAddPidResponse> createGetChildIntroduceTypeListAddPidResponse(GetChildIntroduceTypeListAddPidResponse value) {
        return new JAXBElement<GetChildIntroduceTypeListAddPidResponse>(_GetChildIntroduceTypeListAddPidResponse_QNAME, GetChildIntroduceTypeListAddPidResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIntroduceByTypeIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.introduce.logic.webservice.general.zfsoft.com/", name = "getIntroduceByTypeIdResponse")
    public JAXBElement<GetIntroduceByTypeIdResponse> createGetIntroduceByTypeIdResponse(GetIntroduceByTypeIdResponse value) {
        return new JAXBElement<GetIntroduceByTypeIdResponse>(_GetIntroduceByTypeIdResponse_QNAME, GetIntroduceByTypeIdResponse.class, null, value);
    }

}
