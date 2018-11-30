package com.jw.test;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import org.apache.axis.client.Call;

import com.common.WebServiceConf;
import com.util.MiddleWareUtil;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.encoding.XMLType;

public class testaxis2 {

	public static void main(String[] args) {

		org.apache.axis.client.Service service = new org.apache.axis.client.Service();

		Call call;

		// Service service = new Service(); //
		try {
			call = (Call) service.createCall();

			call.setTargetEndpointAddress(new java.net.URL(
					"http://10.71.32.158/stable/file.asmx?WSDL"));// 
			call.setOperationName(new QName("http://zfsoft/zfjw/fileProcess",
					"Downloads"));
			call.addParameter(new QName("http://zfsoft/zfjw/fileProcess",
					"fileDir"), XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter(new QName("http://zfsoft/zfjw/fileProcess",
					"length"), XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter(new QName("http://zfsoft/zfjw/fileProcess",
					"strKey"), XMLType.XSD_STRING, ParameterMode.IN);
			call.setReturnClass(String.class);
			call.setReturnType(XMLType.XSD_STRING);
			call.setUseSOAPAction(true);
			call.setSOAPActionURI("http://zfsoft/zfjw/fileProcess/Downloads");
			String result = (String) call.invoke(new Object[] { "课程推送规则说明.doc",
					"", "1E2C231DAB70D121BDFCF739F5864E4A" });
			
			System.out.println(result);
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
