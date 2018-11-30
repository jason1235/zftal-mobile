package com.versionVerify.Test;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.common.WebServiceConf;
import com.versionVerify.CXFServe.IVersionVerifyXmlService;




public class VersionCheckXmlTest{

	
	public static void main(String[] args) {
		//纯cxf调用方式

		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IVersionVerifyXmlService.class);
		factory.setAddress(WebServiceConf.SERVICE_VERSIONSERVICE_CHECK);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IVersionVerifyXmlService service = (IVersionVerifyXmlService) factory.create();
		System.out.println("Server said: " + service.versionCompare("1", "1", "1", "1", "1", "1", "ZF1-oa-android-V1.2.9"));
	}

}

