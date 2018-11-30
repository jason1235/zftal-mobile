package com.xg.lx.test;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.common.ToolUtil;
import com.common.WebServiceConf;
import com.xg.lx.CXFService.ILxService;

public class lxtest {
	public static void main(String args[]){
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
		factory.setServiceClass(ILxService.class);
		factory.setAddress(WebServiceConf.SERVICE_LXSERVICE);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		ILxService service = (ILxService) factory.create();
		
		str =service.getLxTzggList(yhlx, num, userName, sign, pageIndex);
		System.out.println(str);
		JaxWsProxyFactoryBean factory1 = new JaxWsProxyFactoryBean();
		factory1.setServiceClass(ILxService.class);
		factory1.setAddress(WebServiceConf.SERVICE_LXSERVICE);//接口地址
		factory1.getInInterceptors().add(new LoggingInInterceptor());
		factory1.getOutInterceptors().add(new LoggingOutInterceptor());
		ILxService service1 = (ILxService) factory1.create();
		
		str =service1.getTzggDetail("7bb75c9c42b6d45887bbee995a212cea", userName, sign);
		System.out.println(str);
	}

}
