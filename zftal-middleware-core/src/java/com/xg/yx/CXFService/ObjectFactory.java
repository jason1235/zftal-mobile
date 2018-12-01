
package com.xg.yx.CXFService;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xg.yx.CXFService package. 
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

    private final static QName _GetTzggDetail_QNAME = new QName("http://service.yx.xgws.zfsoft.com/", "getTzggDetail");
    private final static QName _GetTzggDetailResponse_QNAME = new QName("http://service.yx.xgws.zfsoft.com/", "getTzggDetailResponse");
    private final static QName _GetYxTzggTeaList_QNAME = new QName("http://service.yx.xgws.zfsoft.com/", "getYxTzggTeaList");
    private final static QName _GetYxTzggTeaListResponse_QNAME = new QName("http://service.yx.xgws.zfsoft.com/", "getYxTzggTeaListResponse");
    private final static QName _GetYxTzggStuList_QNAME = new QName("http://service.yx.xgws.zfsoft.com/", "getYxTzggStuList");
    private final static QName _GetYxTzggStuListResponse_QNAME = new QName("http://service.yx.xgws.zfsoft.com/", "getYxTzggStuListResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xg.yx.CXFService
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetYxTzggStuListResponse }
     * 
     */
    public GetYxTzggStuListResponse createGetYxTzggStuListResponse() {
        return new GetYxTzggStuListResponse();
    }

    /**
     * Create an instance of {@link GetYxTzggStuList }
     * 
     */
    public GetYxTzggStuList createGetYxTzggStuList() {
        return new GetYxTzggStuList();
    }

    /**
     * Create an instance of {@link GetYxTzggTeaListResponse }
     * 
     */
    public GetYxTzggTeaListResponse createGetYxTzggTeaListResponse() {
        return new GetYxTzggTeaListResponse();
    }

    /**
     * Create an instance of {@link GetYxTzggTeaList }
     * 
     */
    public GetYxTzggTeaList createGetYxTzggTeaList() {
        return new GetYxTzggTeaList();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTzggDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.yx.xgws.zfsoft.com/", name = "getTzggDetail")
    public JAXBElement<GetTzggDetail> createGetTzggDetail(GetTzggDetail value) {
        return new JAXBElement<GetTzggDetail>(_GetTzggDetail_QNAME, GetTzggDetail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTzggDetailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.yx.xgws.zfsoft.com/", name = "getTzggDetailResponse")
    public JAXBElement<GetTzggDetailResponse> createGetTzggDetailResponse(GetTzggDetailResponse value) {
        return new JAXBElement<GetTzggDetailResponse>(_GetTzggDetailResponse_QNAME, GetTzggDetailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetYxTzggTeaList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.yx.xgws.zfsoft.com/", name = "getYxTzggTeaList")
    public JAXBElement<GetYxTzggTeaList> createGetYxTzggTeaList(GetYxTzggTeaList value) {
        return new JAXBElement<GetYxTzggTeaList>(_GetYxTzggTeaList_QNAME, GetYxTzggTeaList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetYxTzggTeaListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.yx.xgws.zfsoft.com/", name = "getYxTzggTeaListResponse")
    public JAXBElement<GetYxTzggTeaListResponse> createGetYxTzggTeaListResponse(GetYxTzggTeaListResponse value) {
        return new JAXBElement<GetYxTzggTeaListResponse>(_GetYxTzggTeaListResponse_QNAME, GetYxTzggTeaListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetYxTzggStuList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.yx.xgws.zfsoft.com/", name = "getYxTzggStuList")
    public JAXBElement<GetYxTzggStuList> createGetYxTzggStuList(GetYxTzggStuList value) {
        return new JAXBElement<GetYxTzggStuList>(_GetYxTzggStuList_QNAME, GetYxTzggStuList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetYxTzggStuListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.yx.xgws.zfsoft.com/", name = "getYxTzggStuListResponse")
    public JAXBElement<GetYxTzggStuListResponse> createGetYxTzggStuListResponse(GetYxTzggStuListResponse value) {
        return new JAXBElement<GetYxTzggStuListResponse>(_GetYxTzggStuListResponse_QNAME, GetYxTzggStuListResponse.class, null, value);
    }

}
