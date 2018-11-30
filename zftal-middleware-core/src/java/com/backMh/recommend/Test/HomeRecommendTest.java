package com.backMh.recommend.Test;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.backMh.recommend.CXFService.service.IRecommendXmlService;
import com.common.WebServiceConf;


public class HomeRecommendTest{

	
	public static void main(String[] args) {

		//纯cxf调用方式

		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IRecommendXmlService.class);
		factory.setAddress("http://10.71.32.205:8888/zfmcmh/webservices/mhrecommend/RecommendXmlService");//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IRecommendXmlService service = (IRecommendXmlService) factory.create();
		System.out.println("Server said: " + service.getRecommendInfo("8ac7933f3c70667b013c8fa7405f0000"));
	}

}

