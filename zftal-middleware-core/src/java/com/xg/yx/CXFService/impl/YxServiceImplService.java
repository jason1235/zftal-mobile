package com.xg.yx.CXFService.impl;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

import com.xg.yx.CXFService.IYxService;

/**
 * This class was generated by Apache CXF 2.6.0
 * 2014-10-30T13:41:42.113+08:00
 * Generated source version: 2.6.0
 * 
 */
@WebServiceClient(name = "YxServiceImplService", 
                  wsdlLocation = "http://10.71.32.69:8888/zfxg-ws-web/yxService?wsdl",
                  targetNamespace = "http://impl.service.yx.xgws.zfsoft.com/") 
public class YxServiceImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.service.yx.xgws.zfsoft.com/", "YxServiceImplService");
    public final static QName YxServiceImplPort = new QName("http://impl.service.yx.xgws.zfsoft.com/", "YxServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://10.71.32.69:8888/zfxg-ws-web/yxService?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(YxServiceImplService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://10.71.32.69:8888/zfxg-ws-web/yxService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public YxServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public YxServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public YxServiceImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
/*    public YxServiceImplService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public YxServiceImplService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public YxServiceImplService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }*/

    /**
     *
     * @return
     *     returns IYxService
     */
    @WebEndpoint(name = "YxServiceImplPort")
    public IYxService getYxServiceImplPort() {
        return super.getPort(YxServiceImplPort, IYxService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IYxService
     */
    @WebEndpoint(name = "YxServiceImplPort")
    public IYxService getYxServiceImplPort(WebServiceFeature... features) {
        return super.getPort(YxServiceImplPort, IYxService.class, features);
    }

}
