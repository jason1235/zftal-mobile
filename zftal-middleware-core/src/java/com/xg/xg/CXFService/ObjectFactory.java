
package com.xg.xg.CXFService;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xg.xg.CXFService package. 
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

    private final static QName _GetTzggCountResponse_QNAME = new QName("http://xtwh.xgxt.ws.xgxtws.zfsoft.com/", "getTzggCountResponse");
    private final static QName _GetMyJobsByYhm_QNAME = new QName("http://xtwh.xgxt.ws.xgxtws.zfsoft.com/", "getMyJobsByYhm");
    private final static QName _GetTzggCount_QNAME = new QName("http://xtwh.xgxt.ws.xgxtws.zfsoft.com/", "getTzggCount");
    private final static QName _GetTzgg_QNAME = new QName("http://xtwh.xgxt.ws.xgxtws.zfsoft.com/", "getTzgg");
    private final static QName _ReqData_QNAME = new QName("http://xtwh.xgxt.ws.xgxtws.zfsoft.com/", "reqData");
    private final static QName _MapParameterEntry_QNAME = new QName("http://xtwh.xgxt.ws.xgxtws.zfsoft.com/", "mapParameterEntry");
    private final static QName _GetMyJobsByYhmResponse_QNAME = new QName("http://xtwh.xgxt.ws.xgxtws.zfsoft.com/", "getMyJobsByYhmResponse");
    private final static QName _GetTzggResponse_QNAME = new QName("http://xtwh.xgxt.ws.xgxtws.zfsoft.com/", "getTzggResponse");
    private final static QName _ZfxgWsException_QNAME = new QName("http://xtwh.xgxt.ws.xgxtws.zfsoft.com/", "ZfxgWsException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xg.xg.CXFService
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetMyJobsByYhm }
     * 
     */
    public GetMyJobsByYhm createGetMyJobsByYhm() {
        return new GetMyJobsByYhm();
    }

    /**
     * Create an instance of {@link GetTzggCountResponse }
     * 
     */
    public GetTzggCountResponse createGetTzggCountResponse() {
        return new GetTzggCountResponse();
    }

    /**
     * Create an instance of {@link ReqData }
     * 
     */
    public ReqData createReqData() {
        return new ReqData();
    }

    /**
     * Create an instance of {@link GetTzggCount }
     * 
     */
    public GetTzggCount createGetTzggCount() {
        return new GetTzggCount();
    }

    /**
     * Create an instance of {@link GetTzgg }
     * 
     */
    public GetTzgg createGetTzgg() {
        return new GetTzgg();
    }

    /**
     * Create an instance of {@link ZfxgWsException }
     * 
     */
    public ZfxgWsException createZfxgWsException() {
        return new ZfxgWsException();
    }

    /**
     * Create an instance of {@link GetTzggResponse }
     * 
     */
    public GetTzggResponse createGetTzggResponse() {
        return new GetTzggResponse();
    }

    /**
     * Create an instance of {@link GetMyJobsByYhmResponse }
     * 
     */
    public GetMyJobsByYhmResponse createGetMyJobsByYhmResponse() {
        return new GetMyJobsByYhmResponse();
    }

    /**
     * Create an instance of {@link MapParameterEntry }
     * 
     */
    public MapParameterEntry createMapParameterEntry() {
        return new MapParameterEntry();
    }

    /**
     * Create an instance of {@link MapParameter }
     * 
     */
    public MapParameter createMapParameter() {
        return new MapParameter();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTzggCountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xtwh.xgxt.ws.xgxtws.zfsoft.com/", name = "getTzggCountResponse")
    public JAXBElement<GetTzggCountResponse> createGetTzggCountResponse(GetTzggCountResponse value) {
        return new JAXBElement<GetTzggCountResponse>(_GetTzggCountResponse_QNAME, GetTzggCountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMyJobsByYhm }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xtwh.xgxt.ws.xgxtws.zfsoft.com/", name = "getMyJobsByYhm")
    public JAXBElement<GetMyJobsByYhm> createGetMyJobsByYhm(GetMyJobsByYhm value) {
        return new JAXBElement<GetMyJobsByYhm>(_GetMyJobsByYhm_QNAME, GetMyJobsByYhm.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTzggCount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xtwh.xgxt.ws.xgxtws.zfsoft.com/", name = "getTzggCount")
    public JAXBElement<GetTzggCount> createGetTzggCount(GetTzggCount value) {
        return new JAXBElement<GetTzggCount>(_GetTzggCount_QNAME, GetTzggCount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTzgg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xtwh.xgxt.ws.xgxtws.zfsoft.com/", name = "getTzgg")
    public JAXBElement<GetTzgg> createGetTzgg(GetTzgg value) {
        return new JAXBElement<GetTzgg>(_GetTzgg_QNAME, GetTzgg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReqData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xtwh.xgxt.ws.xgxtws.zfsoft.com/", name = "reqData")
    public JAXBElement<ReqData> createReqData(ReqData value) {
        return new JAXBElement<ReqData>(_ReqData_QNAME, ReqData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MapParameterEntry }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xtwh.xgxt.ws.xgxtws.zfsoft.com/", name = "mapParameterEntry")
    public JAXBElement<MapParameterEntry> createMapParameterEntry(MapParameterEntry value) {
        return new JAXBElement<MapParameterEntry>(_MapParameterEntry_QNAME, MapParameterEntry.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMyJobsByYhmResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xtwh.xgxt.ws.xgxtws.zfsoft.com/", name = "getMyJobsByYhmResponse")
    public JAXBElement<GetMyJobsByYhmResponse> createGetMyJobsByYhmResponse(GetMyJobsByYhmResponse value) {
        return new JAXBElement<GetMyJobsByYhmResponse>(_GetMyJobsByYhmResponse_QNAME, GetMyJobsByYhmResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTzggResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xtwh.xgxt.ws.xgxtws.zfsoft.com/", name = "getTzggResponse")
    public JAXBElement<GetTzggResponse> createGetTzggResponse(GetTzggResponse value) {
        return new JAXBElement<GetTzggResponse>(_GetTzggResponse_QNAME, GetTzggResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ZfxgWsException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xtwh.xgxt.ws.xgxtws.zfsoft.com/", name = "ZfxgWsException")
    public JAXBElement<ZfxgWsException> createZfxgWsException(ZfxgWsException value) {
        return new JAXBElement<ZfxgWsException>(_ZfxgWsException_QNAME, ZfxgWsException.class, null, value);
    }

}
