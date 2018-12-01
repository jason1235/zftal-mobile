
package com.keyan;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.keyan package. 
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

    private final static QName _GetMobieRepairList_QNAME = new QName("http://service.hq.portal.webservice.zfsoft.com/", "getMobieRepairList");
    private final static QName _GetMobieRepairListResponse_QNAME = new QName("http://service.hq.portal.webservice.zfsoft.com/", "getMobieRepairListResponse");
    private final static QName _GetMobieRepairType_QNAME = new QName("http://service.hq.portal.webservice.zfsoft.com/", "getMobieRepairType");
    private final static QName _GetMobieRepairTypeResponse_QNAME = new QName("http://service.hq.portal.webservice.zfsoft.com/", "getMobieRepairTypeResponse");
    private final static QName _GetRepairAddress_QNAME = new QName("http://service.hq.portal.webservice.zfsoft.com/", "getRepairAddress");
    private final static QName _GetRepairAddressResponse_QNAME = new QName("http://service.hq.portal.webservice.zfsoft.com/", "getRepairAddressResponse");
    private final static QName _SubmitRepair_QNAME = new QName("http://service.hq.portal.webservice.zfsoft.com/", "submitRepair");
    private final static QName _SubmitRepairResponse_QNAME = new QName("http://service.hq.portal.webservice.zfsoft.com/", "submitRepairResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.keyan
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetMobieRepairList }
     * 
     */
    public GetMobieRepairList createGetMobieRepairList() {
        return new GetMobieRepairList();
    }

    /**
     * Create an instance of {@link GetMobieRepairListResponse }
     * 
     */
    public GetMobieRepairListResponse createGetMobieRepairListResponse() {
        return new GetMobieRepairListResponse();
    }

    /**
     * Create an instance of {@link GetMobieRepairType }
     * 
     */
    public GetMobieRepairType createGetMobieRepairType() {
        return new GetMobieRepairType();
    }

    /**
     * Create an instance of {@link GetMobieRepairTypeResponse }
     * 
     */
    public GetMobieRepairTypeResponse createGetMobieRepairTypeResponse() {
        return new GetMobieRepairTypeResponse();
    }

    /**
     * Create an instance of {@link GetRepairAddress }
     * 
     */
    public GetRepairAddress createGetRepairAddress() {
        return new GetRepairAddress();
    }

    /**
     * Create an instance of {@link GetRepairAddressResponse }
     * 
     */
    public GetRepairAddressResponse createGetRepairAddressResponse() {
        return new GetRepairAddressResponse();
    }

    /**
     * Create an instance of {@link SubmitRepair }
     * 
     */
    public SubmitRepair createSubmitRepair() {
        return new SubmitRepair();
    }

    /**
     * Create an instance of {@link SubmitRepairResponse }
     * 
     */
    public SubmitRepairResponse createSubmitRepairResponse() {
        return new SubmitRepairResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMobieRepairList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.hq.portal.webservice.zfsoft.com/", name = "getMobieRepairList")
    public JAXBElement<GetMobieRepairList> createGetMobieRepairList(GetMobieRepairList value) {
        return new JAXBElement<GetMobieRepairList>(_GetMobieRepairList_QNAME, GetMobieRepairList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMobieRepairListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.hq.portal.webservice.zfsoft.com/", name = "getMobieRepairListResponse")
    public JAXBElement<GetMobieRepairListResponse> createGetMobieRepairListResponse(GetMobieRepairListResponse value) {
        return new JAXBElement<GetMobieRepairListResponse>(_GetMobieRepairListResponse_QNAME, GetMobieRepairListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMobieRepairType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.hq.portal.webservice.zfsoft.com/", name = "getMobieRepairType")
    public JAXBElement<GetMobieRepairType> createGetMobieRepairType(GetMobieRepairType value) {
        return new JAXBElement<GetMobieRepairType>(_GetMobieRepairType_QNAME, GetMobieRepairType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMobieRepairTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.hq.portal.webservice.zfsoft.com/", name = "getMobieRepairTypeResponse")
    public JAXBElement<GetMobieRepairTypeResponse> createGetMobieRepairTypeResponse(GetMobieRepairTypeResponse value) {
        return new JAXBElement<GetMobieRepairTypeResponse>(_GetMobieRepairTypeResponse_QNAME, GetMobieRepairTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRepairAddress }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.hq.portal.webservice.zfsoft.com/", name = "getRepairAddress")
    public JAXBElement<GetRepairAddress> createGetRepairAddress(GetRepairAddress value) {
        return new JAXBElement<GetRepairAddress>(_GetRepairAddress_QNAME, GetRepairAddress.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRepairAddressResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.hq.portal.webservice.zfsoft.com/", name = "getRepairAddressResponse")
    public JAXBElement<GetRepairAddressResponse> createGetRepairAddressResponse(GetRepairAddressResponse value) {
        return new JAXBElement<GetRepairAddressResponse>(_GetRepairAddressResponse_QNAME, GetRepairAddressResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubmitRepair }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.hq.portal.webservice.zfsoft.com/", name = "submitRepair")
    public JAXBElement<SubmitRepair> createSubmitRepair(SubmitRepair value) {
        return new JAXBElement<SubmitRepair>(_SubmitRepair_QNAME, SubmitRepair.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubmitRepairResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.hq.portal.webservice.zfsoft.com/", name = "submitRepairResponse")
    public JAXBElement<SubmitRepairResponse> createSubmitRepairResponse(SubmitRepairResponse value) {
        return new JAXBElement<SubmitRepairResponse>(_SubmitRepairResponse_QNAME, SubmitRepairResponse.class, null, value);
    }

}
