package com.mh.CXFServe.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.6.0
 * 2017-02-28T15:59:52.904+08:00
 * Generated source version: 2.6.0
 * 
 */
@WebService(targetNamespace = "http://service.ca.webservice.zfsoft.com/", name = "ICaService")
@XmlSeeAlso({ObjectFactory.class})
public interface ICaService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "cancelprobyphone", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.Cancelprobyphone")
    @WebMethod
    @ResponseWrapper(localName = "cancelprobyphoneResponse", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.CancelprobyphoneResponse")
    public java.lang.String cancelprobyphone(
        @WebParam(name = "usename", targetNamespace = "")
        java.lang.String usename,
        @WebParam(name = "sjhm", targetNamespace = "")
        java.lang.String sjhm,
        @WebParam(name = "yzm", targetNamespace = "")
        java.lang.String yzm
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getTGC", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.GetTGC")
    @WebMethod
    @ResponseWrapper(localName = "getTGCResponse", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.GetTGCResponse")
    public java.lang.String getTGC(
        @WebParam(name = "usename", targetNamespace = "")
        java.lang.String usename,
        @WebParam(name = "password", targetNamespace = "")
        java.lang.String password
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getCaUser", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.GetCaUser")
    @WebMethod
    @ResponseWrapper(localName = "getCaUserResponse", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.GetCaUserResponse")
    public java.lang.String getCaUser(
        @WebParam(name = "xtdm", targetNamespace = "")
        java.lang.String xtdm,
        @WebParam(name = "usename", targetNamespace = "")
        java.lang.String usename
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getTicket", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.GetTicket")
    @WebMethod
    @ResponseWrapper(localName = "getTicketResponse", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.GetTicketResponse")
    public com.mh.CXFServe.service.MobileBean getTicket(
        @WebParam(name = "usename", targetNamespace = "")
        java.lang.String usename,
        @WebParam(name = "password", targetNamespace = "")
        java.lang.String password,
        @WebParam(name = "remoteurl", targetNamespace = "")
        java.lang.String remoteurl
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "bindUser", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.BindUser")
    @WebMethod
    @ResponseWrapper(localName = "bindUserResponse", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.BindUserResponse")
    public java.lang.String bindUser(
        @WebParam(name = "uid", targetNamespace = "")
        java.lang.String uid,
        @WebParam(name = "openid", targetNamespace = "")
        java.lang.String openid,
        @WebParam(name = "opentype", targetNamespace = "")
        java.lang.String opentype
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "addbmyx", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.Addbmyx")
    @WebMethod
    @ResponseWrapper(localName = "addbmyxResponse", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.AddbmyxResponse")
    public java.lang.String addbmyx(
        @WebParam(name = "usename", targetNamespace = "")
        java.lang.String usename,
        @WebParam(name = "email", targetNamespace = "")
        java.lang.String email
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "unbindUser", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.UnbindUser")
    @WebMethod
    @ResponseWrapper(localName = "unbindUserResponse", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.UnbindUserResponse")
    public java.lang.String unbindUser(
        @WebParam(name = "uid", targetNamespace = "")
        java.lang.String uid,
        @WebParam(name = "opentype", targetNamespace = "")
        java.lang.String opentype
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "findpwdbymail", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.Findpwdbymail")
    @WebMethod
    @ResponseWrapper(localName = "findpwdbymailResponse", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.FindpwdbymailResponse")
    public java.lang.String findpwdbymail(
        @WebParam(name = "usename", targetNamespace = "")
        java.lang.String usename
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "bindbmyx", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.Bindbmyx")
    @WebMethod
    @ResponseWrapper(localName = "bindbmyxResponse", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.BindbmyxResponse")
    public java.lang.String bindbmyx(
        @WebParam(name = "usename", targetNamespace = "")
        java.lang.String usename,
        @WebParam(name = "radomnumber", targetNamespace = "")
        java.lang.String radomnumber
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getyzm", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.Getyzm")
    @WebMethod
    @ResponseWrapper(localName = "getyzmResponse", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.GetyzmResponse")
    public java.lang.String getyzm(
        @WebParam(name = "sjhm", targetNamespace = "")
        java.lang.String sjhm
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getTicket2", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.GetTicket2")
    @WebMethod
    @ResponseWrapper(localName = "getTicket2Response", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.GetTicket2Response")
    public java.lang.String getTicket2(
        @WebParam(name = "usename", targetNamespace = "")
        java.lang.String usename,
        @WebParam(name = "password", targetNamespace = "")
        java.lang.String password,
        @WebParam(name = "xtdms", targetNamespace = "")
        java.lang.String xtdms,
        @WebParam(name = "remoteurl", targetNamespace = "")
        java.lang.String remoteurl
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getTicketByOpenid", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.GetTicketByOpenid")
    @WebMethod
    @ResponseWrapper(localName = "getTicketByOpenidResponse", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.GetTicketByOpenidResponse")
    public java.lang.String getTicketByOpenid(
        @WebParam(name = "openid", targetNamespace = "")
        java.lang.String openid
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "sfbdsjyx", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.Sfbdsjyx")
    @WebMethod
    @ResponseWrapper(localName = "sfbdsjyxResponse", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.SfbdsjyxResponse")
    public java.lang.String sfbdsjyx(
        @WebParam(name = "usename", targetNamespace = "")
        java.lang.String usename
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getbdyzm", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.Getbdyzm")
    @WebMethod
    @ResponseWrapper(localName = "getbdyzmResponse", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.GetbdyzmResponse")
    public java.lang.String getbdyzm(
        @WebParam(name = "sjhm", targetNamespace = "")
        java.lang.String sjhm,
        @WebParam(name = "usename", targetNamespace = "")
        java.lang.String usename
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "retakepwd", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.Retakepwd")
    @WebMethod
    @ResponseWrapper(localName = "retakepwdResponse", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.RetakepwdResponse")
    public java.lang.String retakepwd(
        @WebParam(name = "sjhm", targetNamespace = "")
        java.lang.String sjhm,
        @WebParam(name = "yzm", targetNamespace = "")
        java.lang.String yzm
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getTicketJson2", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.GetTicketJson2")
    @WebMethod
    @ResponseWrapper(localName = "getTicketJson2Response", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.GetTicketJson2Response")
    public java.lang.String getTicketJson2(
        @WebParam(name = "usename", targetNamespace = "")
        java.lang.String usename,
        @WebParam(name = "password", targetNamespace = "")
        java.lang.String password,
        @WebParam(name = "remoteurl", targetNamespace = "")
        java.lang.String remoteurl
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getYwxtUser", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.GetYwxtUser")
    @WebMethod
    @ResponseWrapper(localName = "getYwxtUserResponse", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.GetYwxtUserResponse")
    public java.lang.String getYwxtUser(
        @WebParam(name = "xtdm", targetNamespace = "")
        java.lang.String xtdm,
        @WebParam(name = "usename", targetNamespace = "")
        java.lang.String usename
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getTicketJson", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.GetTicketJson")
    @WebMethod
    @ResponseWrapper(localName = "getTicketJsonResponse", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.GetTicketJsonResponse")
    public java.lang.String getTicketJson(
        @WebParam(name = "usename", targetNamespace = "")
        java.lang.String usename,
        @WebParam(name = "password", targetNamespace = "")
        java.lang.String password,
        @WebParam(name = "remoteurl", targetNamespace = "")
        java.lang.String remoteurl
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "cancelprobymail", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.Cancelprobymail")
    @WebMethod
    @ResponseWrapper(localName = "cancelprobymailResponse", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.CancelprobymailResponse")
    public java.lang.String cancelprobymail(
        @WebParam(name = "usename", targetNamespace = "")
        java.lang.String usename,
        @WebParam(name = "email", targetNamespace = "")
        java.lang.String email
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "buildsjhm", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.Buildsjhm")
    @WebMethod
    @ResponseWrapper(localName = "buildsjhmResponse", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.BuildsjhmResponse")
    public java.lang.String buildsjhm(
        @WebParam(name = "sjhm", targetNamespace = "")
        java.lang.String sjhm,
        @WebParam(name = "yzm", targetNamespace = "")
        java.lang.String yzm
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getBindings", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.GetBindings")
    @WebMethod
    @ResponseWrapper(localName = "getBindingsResponse", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.GetBindingsResponse")
    public java.lang.String getBindings(
        @WebParam(name = "uid", targetNamespace = "")
        java.lang.String uid
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getUser", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.GetUser")
    @WebMethod
    @ResponseWrapper(localName = "getUserResponse", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.GetUserResponse")
    public java.lang.String getUser(
        @WebParam(name = "openid", targetNamespace = "")
        java.lang.String openid
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getcelyzm", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.Getcelyzm")
    @WebMethod
    @ResponseWrapper(localName = "getcelyzmResponse", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.GetcelyzmResponse")
    public java.lang.String getcelyzm(
        @WebParam(name = "sjhm", targetNamespace = "")
        java.lang.String sjhm
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "checkUse", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.CheckUse")
    @WebMethod
    @ResponseWrapper(localName = "checkUseResponse", targetNamespace = "http://service.ca.webservice.zfsoft.com/", className = "com.mh.CXFServe.service.CheckUseResponse")
    public java.lang.String checkUse(
        @WebParam(name = "usename", targetNamespace = "")
        java.lang.String usename,
        @WebParam(name = "password", targetNamespace = "")
        java.lang.String password
    );
}
