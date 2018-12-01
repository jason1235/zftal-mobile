
package com.xg.lx.CXFService;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xg.lx.CXFService package. 
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

    private final static QName _GetLxdxmshxxListResponse_QNAME = new QName("http://service.lx.xgws.zfsoft.com/", "getLxdxmshxxListResponse");
    private final static QName _GetLxTzggList_QNAME = new QName("http://service.lx.xgws.zfsoft.com/", "getLxTzggList");
    private final static QName _GetTzggDetailResponse_QNAME = new QName("http://service.lx.xgws.zfsoft.com/", "getTzggDetailResponse");
    private final static QName _GetTzggDetail_QNAME = new QName("http://service.lx.xgws.zfsoft.com/", "getTzggDetail");
    private final static QName _GetLxTzggListResponse_QNAME = new QName("http://service.lx.xgws.zfsoft.com/", "getLxTzggListResponse");
    private final static QName _GetLxdxmshxxList_QNAME = new QName("http://service.lx.xgws.zfsoft.com/", "getLxdxmshxxList");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xg.lx.CXFService
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetLxdxmshxxListResponse }
     * 
     */
    public GetLxdxmshxxListResponse createGetLxdxmshxxListResponse() {
        return new GetLxdxmshxxListResponse();
    }

    /**
     * Create an instance of {@link GetLxTzggList }
     * 
     */
    public GetLxTzggList createGetLxTzggList() {
        return new GetLxTzggList();
    }

    /**
     * Create an instance of {@link GetTzggDetailResponse }
     * 
     */
    public GetTzggDetailResponse createGetTzggDetailResponse() {
        return new GetTzggDetailResponse();
    }

    /**
     * Create an instance of {@link GetTzggDetail }
     * 
     */
    public GetTzggDetail createGetTzggDetail() {
        return new GetTzggDetail();
    }

    /**
     * Create an instance of {@link GetLxTzggListResponse }
     * 
     */
    public GetLxTzggListResponse createGetLxTzggListResponse() {
        return new GetLxTzggListResponse();
    }

    /**
     * Create an instance of {@link GetLxdxmshxxList }
     * 
     */
    public GetLxdxmshxxList createGetLxdxmshxxList() {
        return new GetLxdxmshxxList();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLxdxmshxxListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.lx.xgws.zfsoft.com/", name = "getLxdxmshxxListResponse")
    public JAXBElement<GetLxdxmshxxListResponse> createGetLxdxmshxxListResponse(GetLxdxmshxxListResponse value) {
        return new JAXBElement<GetLxdxmshxxListResponse>(_GetLxdxmshxxListResponse_QNAME, GetLxdxmshxxListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLxTzggList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.lx.xgws.zfsoft.com/", name = "getLxTzggList")
    public JAXBElement<GetLxTzggList> createGetLxTzggList(GetLxTzggList value) {
        return new JAXBElement<GetLxTzggList>(_GetLxTzggList_QNAME, GetLxTzggList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTzggDetailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.lx.xgws.zfsoft.com/", name = "getTzggDetailResponse")
    public JAXBElement<GetTzggDetailResponse> createGetTzggDetailResponse(GetTzggDetailResponse value) {
        return new JAXBElement<GetTzggDetailResponse>(_GetTzggDetailResponse_QNAME, GetTzggDetailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTzggDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.lx.xgws.zfsoft.com/", name = "getTzggDetail")
    public JAXBElement<GetTzggDetail> createGetTzggDetail(GetTzggDetail value) {
        return new JAXBElement<GetTzggDetail>(_GetTzggDetail_QNAME, GetTzggDetail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLxTzggListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.lx.xgws.zfsoft.com/", name = "getLxTzggListResponse")
    public JAXBElement<GetLxTzggListResponse> createGetLxTzggListResponse(GetLxTzggListResponse value) {
        return new JAXBElement<GetLxTzggListResponse>(_GetLxTzggListResponse_QNAME, GetLxTzggListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLxdxmshxxList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.lx.xgws.zfsoft.com/", name = "getLxdxmshxxList")
    public JAXBElement<GetLxdxmshxxList> createGetLxdxmshxxList(GetLxdxmshxxList value) {
        return new JAXBElement<GetLxdxmshxxList>(_GetLxdxmshxxList_QNAME, GetLxdxmshxxList.class, null, value);
    }

}
