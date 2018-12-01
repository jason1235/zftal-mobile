package com.jw.test;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import javax.xml.namespace.QName;

import com.common.WebServiceConf;
import com.jw.org.tempuri.ILogin;

public class testEducation {

	/**
	 * <p>Description: </p>
	 * @param args
	 *
	 * @since 2015-2-13 下午03:27:12
	 * @author yangz
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String parameterList = "20101646" + "&" + "6FBFC9E104E9EA7CC74C502DDC5B1DD1" + "&" + "N";// + AND
		String str = null;
		
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(ILogin.class);
		factory.setAddress(WebServiceConf.SERVICE_JWSERVICE_LOGIN);//接口地址
		factory.setServiceName(new QName(WebServiceConf.WEBSERVICE_NAMESPACE_JW, "Login"));
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		ILogin service = (ILogin) factory.create();
		str = service.login(parameterList, "JS", "0D6957FF3A6E16BFD6B55806FEF6F879");

		System.out.println(str);

	}

}
