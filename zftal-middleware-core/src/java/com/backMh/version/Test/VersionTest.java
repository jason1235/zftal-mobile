package com.backMh.version.Test;

import javax.jws.WebParam;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.backMh.version.CXFService.service.IVersionXmlService;
import com.common.WebServiceConf;


public class VersionTest{

	
	public static void main(String[] args) {

		//纯cxf调用方式

		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IVersionXmlService.class);
		factory.setAddress(WebServiceConf.SERVICE_BACKMHSERVICE_V);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IVersionXmlService service = (IVersionXmlService) factory.create();
		System.out.println("Server said: " + service.checkVersion("000000000000000",
				"310260000000000",
				"2.2",
				"sdk",
				"15555215554",
				"",
				"ZFSOFT-CTJW#ANDROID-TY-ZJDX-V1.0.4"));
	}

}

