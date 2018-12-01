package com.util;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.common.WebServiceConf;
import com.keyan.IRepairInfoMobileService;
import com.oa.service.IOaMobileService;

/**
 * webservice工具类
 * 此类要用到md5，使用时一起要有MD5Util
 * @author zhangb
 *
 */
public class WebServiceUtil {

	/**
	 * 教务调用webservice，返回factory
	 * @param serviceAddress地址
	 * @param serviceName接口的类
	 * @param methodName方法别名
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static JaxWsProxyFactoryBean createFactoryJw(String serviceAddress,Class serviceName,String methodName){
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(serviceName);
		factory.setAddress(serviceAddress);//接口地址
		factory.setServiceName(new QName(WebServiceConf.WEBSERVICE_NAMESPACE_JW, methodName));
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		return factory;
	}
	
	/**
	 * oa调用webservice，返回service
	 * @return
	 */
	public static IOaMobileService createServiceOa(){
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IOaMobileService.class);
		factory.setAddress(WebServiceConf.SERVICE_OASERVICE);// 接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		return (IOaMobileService)factory.create();
	}
	
	/**
	 * keyan调用webservice，返回service
	 * @return
	 */
	public static IRepairInfoMobileService createServiceKeyan(){
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IRepairInfoMobileService.class);
		factory.setAddress(WebServiceConf.SERVICE_KEYANSERVICE);// 接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		return (IRepairInfoMobileService)factory.create();
	}
	
	/**
	 * <p>Description: 综合调用webservice，返回factory</p>
	 * @param serviceAddress 地址
	 * @param serviceName    接口的类
	 * @param methodName     方法名
	 * @return
	 *
	 * @since 2014-6-23 上午10:20:09
	 * @author yangz
	 */
	public static JaxWsProxyFactoryBean createFactoryZh(String serviceAddress,Class serviceName,String methodName){
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(serviceName);
		factory.setAddress(serviceAddress);//接口地址
		factory.setServiceName(new QName(WebServiceConf.WEBSERVICE_NAMESPACE_XX, methodName));
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		return factory;
	} 
	
	public static JaxWsProxyFactoryBean createFactoryOA(String serviceAddress,Class serviceName,String methodName){
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(serviceName);
		factory.setAddress(serviceAddress);//接口地址
		factory.setServiceName(new QName(WebServiceConf.WEBSERVICE_NAMESPACE_OA, methodName));
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		return factory;
	}
	
}
