package com.newBackMh.parameters.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@WebService
@XmlAccessorType(XmlAccessType.FIELD)
public interface IBackMhParametersXMLService {
	
	@WebMethod
	public String getMobileBackMhTab(@WebParam(name="apptoken") String apptoken);

}
