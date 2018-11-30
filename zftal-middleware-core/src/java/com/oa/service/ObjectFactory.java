
package com.oa.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.zfsoft.webservice.portal.oa.service package. 
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

    private final static QName _FileModel_QNAME = new QName("http://service.oa.portal.webservice.zfsoft.com/", "fileModel");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.zfsoft.webservice.portal.oa.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FileModel }
     * 
     */
    public FileModel createFileModel() {
        return new FileModel();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileModel }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.oa.portal.webservice.zfsoft.com/", name = "fileModel")
    public JAXBElement<FileModel> createFileModel(FileModel value) {
        return new JAXBElement<FileModel>(_FileModel_QNAME, FileModel.class, null, value);
    }

}
