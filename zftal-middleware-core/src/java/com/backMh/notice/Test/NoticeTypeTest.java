package com.backMh.notice.Test;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.backMh.notice.CXFService.service.INoticeXmlService;
import com.common.WebServiceConf;


public class NoticeTypeTest{

	
	public static void main(String[] args) {

		//纯cxf调用方式

		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(INoticeXmlService.class);
		factory.setAddress(WebServiceConf.SERVICE_BACKMHSERVICE_NT);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		INoticeXmlService service = (INoticeXmlService) factory.create();
		System.out.println("Server said: " + service.getNoticeInfo("8ac790fe374a68d001374a7782150005"));
	}

}

