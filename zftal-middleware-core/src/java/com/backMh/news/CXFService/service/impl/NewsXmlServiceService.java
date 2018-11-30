package com.backMh.news.CXFService.service.impl;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

import com.backMh.news.CXFService.service.INewsXmlService;

/**
 * This class was generated by Apache CXF 2.5.2
 * 2012-11-06T09:49:02.187+08:00
 * Generated source version: 2.5.2
 * 
 */
@WebServiceClient(name = "NewsXmlServiceService", 
                  wsdlLocation = "http://10.71.19.191:9992/zfmcmh/webservices/news/NewsXmlService?wsdl",
                  targetNamespace = "http://service.news.logic.webservice.general.zfsoft.com/") 
public class NewsXmlServiceService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://service.news.logic.webservice.general.zfsoft.com/", "NewsXmlServiceService");
    public final static QName NewsXmlServicePort = new QName("http://service.news.logic.webservice.general.zfsoft.com/", "NewsXmlServicePort");
    static {
        URL url = null;
        try {
            url = new URL("http://10.71.19.191:9992/zfmcmh/webservices/news/NewsXmlService?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(NewsXmlServiceService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://10.71.19.191:9992/zfmcmh/webservices/news/NewsXmlService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public NewsXmlServiceService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public NewsXmlServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public NewsXmlServiceService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns INewsXmlService
     */
    @WebEndpoint(name = "NewsXmlServicePort")
    public INewsXmlService getNewsXmlServicePort() {
        return super.getPort(NewsXmlServicePort, INewsXmlService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns INewsXmlService
     */
    @WebEndpoint(name = "NewsXmlServicePort")
    public INewsXmlService getNewsXmlServicePort(WebServiceFeature... features) {
        return super.getPort(NewsXmlServicePort, INewsXmlService.class, features);
    }

}