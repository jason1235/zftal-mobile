package com.xg.xg.test;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.common.ToolUtil;
import com.common.WebServiceConf;
import com.util.JwMD5Util;
import com.xg.xg.CXFService.IxgService;

public class xgtest {
	
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
		factory.setServiceClass(IxgService.class);
		factory.setAddress(WebServiceConf.SERVICE_XGSERVICE);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		IxgService service = (IxgService) factory.create();
		
		str =service.getLastNoticeList(userName, yhlx, type, num, sign, pageIndex);
		System.out.println(str);
		
		JaxWsProxyFactoryBean factory1 = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IxgService.class);
		factory.setAddress(WebServiceConf.SERVICE_XGSERVICE);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		IxgService service1 = (IxgService) factory.create();
		
		str =service.getTzggDetail("7bb75c9c42b6d45887bbee995a212cea", "3010223016", sign);
		System.out.println(str);
		
	}

}
