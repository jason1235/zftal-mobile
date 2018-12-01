
package com.versionVerify.CXFServe;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.zfsoft.general.webservice.version.service package. 
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

    private final static QName _VersionCompare_QNAME = new QName("http://service.version.webservice.general.zfsoft.com/", "versionCompare");
    private final static QName _VersionCompareResponse_QNAME = new QName("http://service.version.webservice.general.zfsoft.com/", "versionCompareResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.zfsoft.general.webservice.version.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link VersionCompare }
     * 
     */
    public VersionCompare createVersionCompare() {
        return new VersionCompare();
    }

    /**
     * Create an instance of {@link VersionCompareResponse }
     * 
     */
    public VersionCompareResponse createVersionCompareResponse() {
        return new VersionCompareResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VersionCompare }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.version.webservice.general.zfsoft.com/", name = "versionCompare")
    public JAXBElement<VersionCompare> createVersionCompare(VersionCompare value) {
        return new JAXBElement<VersionCompare>(_VersionCompare_QNAME, VersionCompare.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VersionCompareResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.version.webservice.general.zfsoft.com/", name = "versionCompareResponse")
    public JAXBElement<VersionCompareResponse> createVersionCompareResponse(VersionCompareResponse value) {
        return new JAXBElement<VersionCompareResponse>(_VersionCompareResponse_QNAME, VersionCompareResponse.class, null, value);
    }

}
