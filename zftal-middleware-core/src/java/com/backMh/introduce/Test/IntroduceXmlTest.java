package com.backMh.introduce.Test;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.backMh.introduce.CXFServe.service.IIntroduceXmlService;




public class IntroduceXmlTest{

	
	public static void main(String[] args) {
		//纯cxf调用方式

		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IIntroduceXmlService.class);
		factory.setAddress("http://10.71.32.246/zfmmh_manager/webservices/introduce/IntroduceXmlService");//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IIntroduceXmlService service = (IIntroduceXmlService) factory.create();
		System.out.println("Server said: " + service.getChildIntroduceTypeListAddPid("8ac7933f3927c7630139481accb6001d",1,5));
	}

}

