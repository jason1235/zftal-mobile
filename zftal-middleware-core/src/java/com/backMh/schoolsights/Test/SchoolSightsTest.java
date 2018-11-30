package com.backMh.schoolsights.Test;

import javax.jws.WebParam;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.backMh.schoolsights.CXFService.service.ISchoolsightsXmlService;
import com.common.WebServiceConf;


public class SchoolSightsTest{

	
	public static void main(String[] args) {

		//纯cxf调用方式

		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(ISchoolsightsXmlService.class);
		factory.setAddress(WebServiceConf.SERVICE_BACKMHSERVICE_SS);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		ISchoolsightsXmlService service = (ISchoolsightsXmlService) factory.create();
		System.out.println("Server said: " + service.getTypeSchoolSightsPageListNew("1",1,1));

	}

}

