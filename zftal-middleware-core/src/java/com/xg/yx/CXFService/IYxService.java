package com.xg.yx.CXFService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.6.0
 * 2014-10-30T13:41:42.066+08:00
 * Generated source version: 2.6.0
 * 
 */
@WebService(targetNamespace = "http://service.yx.xgws.zfsoft.com/", name = "IYxService")
@XmlSeeAlso({ObjectFactory.class})
public interface IYxService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getYxTzggStuList", targetNamespace = "http://service.yx.xgws.zfsoft.com/", className = "com.xg.yx.CXFService.GetYxTzggStuList")
    @WebMethod
    @ResponseWrapper(localName = "getYxTzggStuListResponse", targetNamespace = "http://service.yx.xgws.zfsoft.com/", className = "com.xg.yx.CXFService.GetYxTzggStuListResponse")
    public java.lang.String getYxTzggStuList(
        @WebParam(name = "num", targetNamespace = "")
        java.lang.Integer num,
        @WebParam(name = "yhm", targetNamespace = "")
        java.lang.String yhm,
        @WebParam(name = "sign", targetNamespace = "")
        java.lang.String sign
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getTzggDetail", targetNamespace = "http://service.yx.xgws.zfsoft.com/", className = "com.xg.yx.CXFService.GetTzggDetail")
    @WebMethod
    @ResponseWrapper(localName = "getTzggDetailResponse", targetNamespace = "http://service.yx.xgws.zfsoft.com/", className = "com.xg.yx.CXFService.GetTzggDetailResponse")
    public java.lang.String getTzggDetail(
        @WebParam(name = "id", targetNamespace = "")
        java.lang.String id,
        @WebParam(name = "yhm", targetNamespace = "")
        java.lang.String yhm,
        @WebParam(name = "sign", targetNamespace = "")
        java.lang.String sign
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getYxTzggTeaList", targetNamespace = "http://service.yx.xgws.zfsoft.com/", className = "com.xg.yx.CXFService.GetYxTzggTeaList")
    @WebMethod
    @ResponseWrapper(localName = "getYxTzggTeaListResponse", targetNamespace = "http://service.yx.xgws.zfsoft.com/", className = "com.xg.yx.CXFService.GetYxTzggTeaListResponse")
    public java.lang.String getYxTzggTeaList(
        @WebParam(name = "num", targetNamespace = "")
        java.lang.Integer num,
        @WebParam(name = "yhm", targetNamespace = "")
        java.lang.String yhm,
        @WebParam(name = "sign", targetNamespace = "")
        java.lang.String sign
    );
}
