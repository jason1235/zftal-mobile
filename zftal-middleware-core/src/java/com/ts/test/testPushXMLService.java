package com.ts.test;


import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.common.WebServiceConf;
import com.ts.CXFService.service.IWSPushServicePortType;
import com.util.MiddleWareUtil;

public class testPushXMLService {
	public static void main(String[] args) {
		
  /*      String str=null;
        String key="zfsoft_ydxy";
        String parameterList="niubinan&1&YDJWXT";
        String privatekey =parameterList+key;
        int page1=0;
        int size1=20;
        String sign=MiddleWareUtil.getRSSign(privatekey);
        System.out.println("Server said: "+ sign);
   //   String sign="{MD5}RSbW/9LwrifSVbX30Prr7A==";
        
        
		
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IWSPushServicePortType.class);
		factory.setAddress(WebServiceConf.SERVICE_TSSERVICE);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IWSPushServicePortType service = (IWSPushServicePortType) factory.create();
		
		str =service.getPushMsg(parameterList, page1, size1, sign);

	    System.out.println("Server said: "+ str);*/
	    
		
		    String str=null;
	        String key="zfsoft_ydxy";
	        String parameterList="zhangsan&R0000001&YDJWXT";
	        String privatekey =parameterList+key;
	        System.out.println("Server said: "+ privatekey);
	        String sign=MiddleWareUtil.getRSSign(privatekey);
	        System.out.println("Server said: "+ sign);
	    
	    
	    JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IWSPushServicePortType.class);
		factory.setAddress(WebServiceConf.SERVICE_TSSERVICE);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IWSPushServicePortType service = (IWSPushServicePortType) factory.create();
		
		str =service.setUserIdAndRegistrationId(parameterList,sign);
		System.out.println("Server said: "+ str);
	}
	
	


}
