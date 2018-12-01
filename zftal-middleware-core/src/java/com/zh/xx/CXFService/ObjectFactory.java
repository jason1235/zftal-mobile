
package com.zh.xx.CXFService;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.zh.xx.CXFService package. 
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

    private final static QName _CheckUserInf_QNAME = new QName("http://webservice.zfml.zfsoft.com/", "checkUserInf");
    private final static QName _CheckUserInfResponse_QNAME = new QName("http://webservice.zfml.zfsoft.com/", "checkUserInfResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.zh.xx.CXFService
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CheckUserInf }
     * 
     */
    public CheckUserInf createCheckUserInf() {
        return new CheckUserInf();
    }

    /**
     * Create an instance of {@link CheckUserInfResponse }
     * 
     */
    public CheckUserInfResponse createCheckUserInfResponse() {
        return new CheckUserInfResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckUserInf }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.zfml.zfsoft.com/", name = "checkUserInf")
    public JAXBElement<CheckUserInf> createCheckUserInf(CheckUserInf value) {
        return new JAXBElement<CheckUserInf>(_CheckUserInf_QNAME, CheckUserInf.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckUserInfResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.zfml.zfsoft.com/", name = "checkUserInfResponse")
    public JAXBElement<CheckUserInfResponse> createCheckUserInfResponse(CheckUserInfResponse value) {
        return new JAXBElement<CheckUserInfResponse>(_CheckUserInfResponse_QNAME, CheckUserInfResponse.class, null, value);
    }

}
