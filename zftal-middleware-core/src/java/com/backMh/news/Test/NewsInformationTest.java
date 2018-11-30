package com.backMh.news.Test;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.backMh.news.CXFService.service.INewsXmlService;
import com.common.WebServiceConf;


public class NewsInformationTest{

	
	public static void main(String[] args) {

		//纯cxf调用方式

		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(INewsXmlService.class);
		factory.setAddress("http://221.180.145.122:7077/zfmcmh/webservices/news/NewsXmlService");//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		INewsXmlService service = (INewsXmlService) factory.create();
		System.out.println("Server said: " + service.getIndexNewsList(3,17));
	}

}

