package com.xg.yx.test;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.common.ToolUtil;
import com.common.WebServiceConf;
import com.xg.yx.CXFService.IYxService;

public class yxtest {
	
	public static void main(String[] args) {
	String s="zfsoft";
	String str=null;
	String userName="3010223016";
	String yhlx="2";
	String type="-1";
	String sign=ToolUtil.eCode(s+userName);
	int num = 0 ;
	int pageIndex = 0;
	
	// 调用WebService
	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	factory.setServiceClass(IYxService.class);
	factory.setAddress(WebServiceConf.SERVICE_YXSERVICE);//接口地址
	factory.getInInterceptors().add(new LoggingInInterceptor());
	factory.getOutInterceptors().add(new LoggingOutInterceptor());
	IYxService service = ( IYxService) factory.create();
	
	str =service.getYxTzggTeaList(num, userName, sign);
	System.out.println(str);
	
	JaxWsProxyFactoryBean factory1 = new JaxWsProxyFactoryBean();
	factory1.setServiceClass(IYxService.class);
	factory1.setAddress(WebServiceConf.SERVICE_YXSERVICE);//接口地址
	factory1.getInInterceptors().add(new LoggingInInterceptor());
	factory1.getOutInterceptors().add(new LoggingOutInterceptor());
	IYxService service1 = ( IYxService) factory1.create();
	
	str =service1.getTzggDetail("FF9FABD2337895BBE040007F01001D15", "3010223016", sign);
	System.out.println(str);
	}
}
