package com.mh.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.common.ConstantsSoa;
import com.common.WebServiceConf;
import com.mh.CXFServe.service.ICaService;
import com.mh.CXFServe.service.MobileBean;
import com.util.SelectItems;
import com.util.encode.XmltoString;

public class MobileManageCXFText{
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		/*String zfxml = "";
		Call servicecall = null;//清空call。
		IWebService service =WebServiceImpl.getInstance();
		servicecall= (Call)service.getCall();

		servicecall.setOperationName(new QName(WebServiceConf.SERVICE_MHSERVICE,"checkUse"));//接口地址和调用方法名
		try {
			servicecall.setTargetEndpointAddress(new URL(WebServiceConf.SERVICE_MHSERVICE));//接口地址
			zfxml = (String)servicecall.invoke(new Object[]{"910","000000"});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		System.out.println("Server said: "+ zfxml);
		}*/
		String zfxml = "";
////		 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(ICaService.class);
		factory.setAddress(WebServiceConf.SERVICE_MHSERVICE);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		ICaService service = (ICaService) factory.create();

	    System.out.println("Server said: "+ service.checkUse("js01", "000000"));
		
		JaxWsProxyFactoryBean factory1 = new JaxWsProxyFactoryBean();
		factory1.setServiceClass(ICaService.class);
		factory1.setAddress(WebServiceConf.SERVICE_MHSERVICE);//接口地址
		factory1.getInInterceptors().add(new LoggingInInterceptor());
		factory1.getOutInterceptors().add(new LoggingOutInterceptor());

		ICaService service1 = (ICaService) factory.create();

		MobileBean oMobileBean = new MobileBean();
		
		oMobileBean = (MobileBean) service1.getTicket("admin", "0", "");
		if(oMobileBean==null){
			System.out.println("Server said: "+ "1111");
		}
		if(ConstantsSoa.SCHOOL_INFO.equals("LYXY")){//旅游学院调换用户名和职工号码数据
			String workerCode = oMobileBean.getZjhm();
			String username = oMobileBean.getYhm();
			oMobileBean.setZjhm(username);
			oMobileBean.setYhm(workerCode);
		}
		String[] output = new String[]{"cacheck","jsName","xm","bm","dqxn","dqxq","ticket","zjhm","yhm"};//需要输出的字段
		List indexlist = new ArrayList();
		indexlist.add(oMobileBean);
		zfxml =  XmltoString.xmlToStringNew(output, SelectItems.getReflectObjPropertyValue(indexlist, MobileBean.class,output), "MH");
		System.out.println("Server said: "+ zfxml);
		}
		

}
