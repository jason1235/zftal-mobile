
package com.backMh.version.CXFService.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.zfsoft.general.webservice.sys.version.service package. 
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

    private final static QName _CheckVersion_QNAME = new QName("http://service.version.sys.webservice.general.zfsoft.com/", "checkVersion");
    private final static QName _Decode_QNAME = new QName("http://service.version.sys.webservice.general.zfsoft.com/", "decode");
    private final static QName _DecodeResponse_QNAME = new QName("http://service.version.sys.webservice.general.zfsoft.com/", "decodeResponse");
    private final static QName _Encode_QNAME = new QName("http://service.version.sys.webservice.general.zfsoft.com/", "encode");
    private final static QName _CheckVersionResponse_QNAME = new QName("http://service.version.sys.webservice.general.zfsoft.com/", "checkVersionResponse");
    private final static QName _EncodeResponse_QNAME = new QName("http://service.version.sys.webservice.general.zfsoft.com/", "encodeResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.zfsoft.general.webservice.sys.version.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Encode }
     * 
     */
    public Encode createEncode() {
        return new Encode();
    }

    /**
     * Create an instance of {@link DecodeResponse }
     * 
     */
    public DecodeResponse createDecodeResponse() {
        return new DecodeResponse();
    }

    /**
     * Create an instance of {@link Decode }
     * 
     */
    public Decode createDecode() {
        return new Decode();
    }

    /**
     * Create an instance of {@link CheckVersion }
     * 
     */
    public CheckVersion createCheckVersion() {
        return new CheckVersion();
    }

    /**
     * Create an instance of {@link CheckVersionResponse }
     * 
     */
    public CheckVersionResponse createCheckVersionResponse() {
        return new CheckVersionResponse();
    }

    /**
     * Create an instance of {@link EncodeResponse }
     * 
     */
    public EncodeResponse createEncodeResponse() {
        return new EncodeResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckVersion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.version.sys.webservice.general.zfsoft.com/", name = "checkVersion")
    public JAXBElement<CheckVersion> createCheckVersion(CheckVersion value) {
        return new JAXBElement<CheckVersion>(_CheckVersion_QNAME, CheckVersion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Decode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.version.sys.webservice.general.zfsoft.com/", name = "decode")
    public JAXBElement<Decode> createDecode(Decode value) {
        return new JAXBElement<Decode>(_Decode_QNAME, Decode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DecodeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.version.sys.webservice.general.zfsoft.com/", name = "decodeResponse")
    public JAXBElement<DecodeResponse> createDecodeResponse(DecodeResponse value) {
        return new JAXBElement<DecodeResponse>(_DecodeResponse_QNAME, DecodeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Encode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.version.sys.webservice.general.zfsoft.com/", name = "encode")
    public JAXBElement<Encode> createEncode(Encode value) {
        return new JAXBElement<Encode>(_Encode_QNAME, Encode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckVersionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.version.sys.webservice.general.zfsoft.com/", name = "checkVersionResponse")
    public JAXBElement<CheckVersionResponse> createCheckVersionResponse(CheckVersionResponse value) {
        return new JAXBElement<CheckVersionResponse>(_CheckVersionResponse_QNAME, CheckVersionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EncodeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.version.sys.webservice.general.zfsoft.com/", name = "encodeResponse")
    public JAXBElement<EncodeResponse> createEncodeResponse(EncodeResponse value) {
        return new JAXBElement<EncodeResponse>(_EncodeResponse_QNAME, EncodeResponse.class, null, value);
    }

}
