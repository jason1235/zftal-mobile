package com.versionVerify.CXFServe;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.6.0
 * 2013-01-16T11:51:36.906+08:00
 * Generated source version: 2.6.0
 * 
 */
@WebService(targetNamespace = "http://service.version.webservice.general.zfsoft.com/", name = "IVersionVerifyXmlService")
@XmlSeeAlso({ObjectFactory.class})
public interface IVersionVerifyXmlService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "versionCompare", targetNamespace = "http://service.version.webservice.general.zfsoft.com/", className = "com.versionVerify.CXFServe.VersionCompare")
    @WebMethod
    @ResponseWrapper(localName = "versionCompareResponse", targetNamespace = "http://service.version.webservice.general.zfsoft.com/", className = "com.versionVerify.CXFServe.VersionCompareResponse")
    public java.lang.String versionCompare(
        @WebParam(name = "imei", targetNamespace = "")
        java.lang.String imei,
        @WebParam(name = "imsi", targetNamespace = "")
        java.lang.String imsi,
        @WebParam(name = "sysinfo", targetNamespace = "")
        java.lang.String sysinfo,
        @WebParam(name = "ua", targetNamespace = "")
        java.lang.String ua,
        @WebParam(name = "phonum", targetNamespace = "")
        java.lang.String phonum,
        @WebParam(name = "account", targetNamespace = "")
        java.lang.String account,
        @WebParam(name = "v", targetNamespace = "")
        java.lang.String v
    );
}