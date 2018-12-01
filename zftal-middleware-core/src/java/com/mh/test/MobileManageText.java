package com.mh.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;

import com.common.IWebService;
import com.common.WebServiceConf;
import com.common.WebServiceImpl;
import com.mh.service.MobileBean;
import com.util.SelectItems;
import com.util.encode.XmltoString;

public class MobileManageText{
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String zfxml = "";
		Call servicecall = null;//清空call。
		IWebService service =WebServiceImpl.getInstance();
		servicecall= (Call)service.getCall();

		servicecall.setOperationName(new QName(WebServiceConf.SERVICE_MHSERVICE,"checkUse"));//接口地址和调用方法名
		try {
			servicecall.setTargetEndpointAddress(new URL(WebServiceConf.SERVICE_MHSERVICE));//接口地址
			zfxml = (String)servicecall.invoke(new Object[]{"857","hzx147456"});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
			System.out.println("Server said: "+ zfxml);
//		}
//		String zfxml = "";
//		MobileBean oMobileBean = new MobileBean();
//		Call servicecall = null;//清空call。
//		IWebService service =WebServiceImpl.getInstance();
//		servicecall= (Call)service.getCall();
//
////		servicecall.setOperationName(new QName(WebServiceConf.SERVICE_MHSERVICE,"checkUse"));//接口地址和调用方法名
//		servicecall.setOperationName(new QName("http://10.71.19.195:8043/zfca/axis/MobileManage?wsdl","getTicket"));//接口地址和调用方法名
//		try {
//			QName us = new QName("urn:BeanService", "MobileBean");
//			servicecall.registerTypeMapping(MobileBean.class, us, 
//					new BeanSerializerFactory(MobileBean.class, us), new BeanDeserializerFactory(MobileBean.class, us));
//			servicecall.setTargetEndpointAddress(new URL("http://10.71.19.195:8043/zfca/axis/MobileManage?wsdl"));//接口地址
//			oMobileBean = (MobileBean) servicecall.invoke(new Object[]{"0701040131","0",""});
//
//			if(oMobileBean==null){
//				return;
//			}
//			String[] output = new String[]{"cacheck","jsName","xm","bm","dqxn","dqxq","ticket"};//需要输出的字段
//			List indexlist = new ArrayList();
//			indexlist.add(oMobileBean);
//			zfxml =  XmltoString.xmlToStringNew(output, SelectItems.getReflectObjPropertyValue(indexlist, MobileBean.class,output), "MH");
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//		try {
//		Service service = new Service();
//		Call call = (Call) service.createCall();
//		//String endpoint = rzptConfig.rzpturl + "/axis/MobileManage?wsdl";
//		String endpoint = "http://10.71.32.201:7073/zfca/axis/MobileManage?wsdl";
//		call.setTargetEndpointAddress(new java.net.URL(endpoint));
//		QName us = new QName("urn:BeanService", "MobileBean");
//		call.registerTypeMapping(MobileBean.class, us, new BeanSerializerFactory(
//				MobileBean.class, us), new BeanDeserializerFactory(
//						MobileBean.class, us));
//		call.setOperationName(new QName(endpoint, "getTicket"));
//		
//			MobileBean res = (MobileBean) call.invoke(new Object[] {"admin","000000","http://10.71.32.201:8080/portal.do"});
//			System.out.println("##############"+res.getXm()+":"+res.getTicket());
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		} catch (ServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//		
//		
//		
			System.out.println("Server said: "+ zfxml);
		}

}
