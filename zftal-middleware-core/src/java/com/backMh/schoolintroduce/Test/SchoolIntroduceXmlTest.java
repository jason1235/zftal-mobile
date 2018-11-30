package com.backMh.schoolintroduce.Test;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.backMh.schoolintroduce.CXFServe.service.ISIntroduceXmlService;




public class SchoolIntroduceXmlTest{

	
	public static void main(String[] args) {
		//纯cxf调用方式

		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(ISIntroduceXmlService.class);
		factory.setAddress("http://10.71.32.205:8888/zfmmh_manager/webservices/sIntroduce/SIntroduceXmlService");//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		ISIntroduceXmlService service = (ISIntroduceXmlService) factory.create();
//		System.out.println("Server said: " + service.getRootSchoolIntroduceTypeList());
//		System.out.println("Server said: " + service.getSchoolIntroduceTitleList("8ac7a04d3c4c9efe013c4ca9af940000", 1, 5));
		System.out.println("Server said: " + service.getSchoolIntroduceById("8ac7a04d3c4c9efe013c4cafd0ea0006"));
//		INewsXmlService service = (INewsXmlService) factory.create();
//		System.out.println("Server said: " + service.getTypeIdNewsPageListNew("8ac7a0fe369f261e01369ff6fd350002",1,5));
	
	}

}

