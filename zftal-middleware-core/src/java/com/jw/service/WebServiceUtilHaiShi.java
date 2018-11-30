package com.jw.service;

import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.message.SOAPHeaderElement;

import com.util.encode.MD5Util;


public class WebServiceUtilHaiShi {
	
	private static String service_url = "http://finance2.shmtu.edu.cn:8989/financequeryservice.asmx";
    private static String namespace ="http://tempuri.org/";
    private static String methodName ="";
    private static String soapActionURI = "";
    
    private static String key="7c19d643ef9e925b468275bd5b9e64b8";
    
	@SuppressWarnings({ "unused", "rawtypes" })
	public static String ProjectBudgetQuery(String UCode, String AccYear) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String SendTime = sdf.format(new Date());
		String StrMD5 = "";
		methodName = "ProjectBudgetQuery";
		soapActionURI = namespace + methodName;
//		String UCode = "013392";
//		String UCode = "125003";
		MD5Util md5 = new MD5Util();
		StrMD5 = md5.md5Encode(UCode + SendTime + key);
//		String AccYear = "2015";
		
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(service_url));
		call.setUseSOAPAction(true);
		call.setSOAPActionURI(soapActionURI);
		call.setOperationName(new QName(namespace, methodName));
		call.addParameter(new QName(namespace, "Data"),
				org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.OUT);
		call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);	
		
		SOAPHeaderElement soapHeaderElement = new SOAPHeaderElement(namespace, "SecurityHeader");
		soapHeaderElement.setNamespaceURI(namespace);
//		soapHeaderElement.addChildElement("ThirdType").setValue(ThirdType);
//		soapHeaderElement.addChildElement("Secret1").setValue(Secret1);
//		soapHeaderElement.addChildElement("Secret2").setValue(Secret2);
	    call.addHeader(soapHeaderElement);
	    
	    call.addParameter(new QName(namespace,"UCode"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
	    call.addParameter(new QName(namespace,"SendTime"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
	    call.addParameter(new QName(namespace,"StrMD5"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
	    call.addParameter(new QName(namespace,"AccYear"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
//	    call.addParameter(new QName(namespace,"Year"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
//	    call.addParameter(new QName(namespace,"Month"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
//	    call.addParameter(new QName(namespace,"SMonth"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
//	    call.addParameter(new QName(namespace,"EYear"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
//	    call.addParameter(new QName(namespace,"EMonth"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
	    
		List<Map> list;
		try {
			return (String) call.invoke(new Object[] {UCode,SendTime,StrMD5,AccYear});
//			String obj =(String) call.invoke(new Object[] {UCode,SendTime,StrMD5,AccYear});
//			System.out.println(obj);
//			Map rMap = call.getOutputParams();
//			list = new ArrayList<Map>();
//			String value=(String) rMap.get(new QName(namespace,"Data"));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
//		return list;
	}
	
	public static String MD5(String inStr) {

		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
	
	public static void main(String[] args) {
		try {
			String result = WebServiceUtilHaiShi.SalaryQuery("125003", "2017", "01", "2017", "3");
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String ProjectBudgetDetailQuery(String UCode, String AccYear,
			String depid, String itemid, String budctrlid) throws ServiceException, MalformedURLException {
		methodName = "ProjectBudgetDetailQuery";
		soapActionURI = namespace + methodName;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String SendTime = sdf.format(new Date());
		MD5Util md5 = new MD5Util();
		String StrMD5 = null;
		try {
			StrMD5 = md5.md5Encode(UCode + SendTime + key);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(service_url));
		call.setUseSOAPAction(true);
		call.setSOAPActionURI(soapActionURI);
		call.setOperationName(new QName(namespace, methodName));
		call.addParameter(new QName(namespace, "Data"),
				org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.OUT);
		call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);	
		
		SOAPHeaderElement soapHeaderElement = new SOAPHeaderElement(namespace, "SecurityHeader");
		soapHeaderElement.setNamespaceURI(namespace);
	    call.addHeader(soapHeaderElement);
	    
	    call.addParameter(new QName(namespace,"UCode"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
	    call.addParameter(new QName(namespace,"SendTime"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
	    call.addParameter(new QName(namespace,"StrMD5"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
	    call.addParameter(new QName(namespace,"AccYear"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
	    call.addParameter(new QName(namespace,"DepID"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
	    call.addParameter(new QName(namespace,"ItemID"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
	    call.addParameter(new QName(namespace,"BudCtrlID"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
	    
		try {
			return (String) call.invoke(new Object[] {UCode,SendTime,StrMD5,AccYear,depid,itemid,budctrlid});
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String SalaryQuery(String UCode, String sYear, String sMonth,
			String eYear, String eMonth) throws MalformedURLException, ServiceException {
		methodName = "SalaryQuery";
		soapActionURI = namespace + methodName;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String SendTime = sdf.format(new Date());
		MD5Util md5 = new MD5Util();
		String StrMD5 = null;
		try {
			StrMD5 = md5.md5Encode(UCode + SendTime + key);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(service_url));
		call.setUseSOAPAction(true);
		call.setSOAPActionURI(soapActionURI);
		call.setOperationName(new QName(namespace, methodName));
		call.addParameter(new QName(namespace, "Data"),
				org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.OUT);
		call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);	
		
		SOAPHeaderElement soapHeaderElement = new SOAPHeaderElement(namespace, "SecurityHeader");
		soapHeaderElement.setNamespaceURI(namespace);
	    call.addHeader(soapHeaderElement);
	    
	    call.addParameter(new QName(namespace,"UCode"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
	    call.addParameter(new QName(namespace,"SendTime"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
	    call.addParameter(new QName(namespace,"StrMD5"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
	    call.addParameter(new QName(namespace,"SYear"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
	    call.addParameter(new QName(namespace,"SMonth"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
	    call.addParameter(new QName(namespace,"EYear"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
	    call.addParameter(new QName(namespace,"EMonth"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
	    
		try {
			return (String) call.invoke(new Object[] {UCode,SendTime,StrMD5,sYear,sMonth,eYear,eMonth});
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String SalaryDetailQuery(String UCode, String year,
			String month, String sLatoryCode) throws ServiceException, MalformedURLException {
		methodName = "SalaryDetailQuery";
		soapActionURI = namespace + methodName;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String SendTime = sdf.format(new Date());
		MD5Util md5 = new MD5Util();
		String StrMD5 = null;
		try {
			StrMD5 = md5.md5Encode(UCode + SendTime + key);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(service_url));
		call.setUseSOAPAction(true);
		call.setSOAPActionURI(soapActionURI);
		call.setOperationName(new QName(namespace, methodName));
		call.addParameter(new QName(namespace, "Data"),
				org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.OUT);
		call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);	
		
		SOAPHeaderElement soapHeaderElement = new SOAPHeaderElement(namespace, "SecurityHeader");
		soapHeaderElement.setNamespaceURI(namespace);
	    call.addHeader(soapHeaderElement);
	    
	    call.addParameter(new QName(namespace,"UCode"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
	    call.addParameter(new QName(namespace,"SendTime"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
	    call.addParameter(new QName(namespace,"StrMD5"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
	    call.addParameter(new QName(namespace,"Year"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
	    call.addParameter(new QName(namespace,"Month"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
	    call.addParameter(new QName(namespace,"SLatoryCode"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
	    
		try {
			return (String) call.invoke(new Object[] {UCode,SendTime,StrMD5,year,month,sLatoryCode});
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
    
    
}
