package com.jw.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.encoding.XMLType;

import org.apache.axis.client.Call;
import org.apache.commons.codec.binary.Base64;

import com.common.WebServiceConf;
import com.oa.service.DownLoadBean;
import com.oa.service.FileModel;
import com.util.SelectItems;
import com.util.encode.XmltoString;


public class testbean {

	/**
	 * <p>Description: </p>
	 * @param args
	 *
	 * @since 2015-5-14 下午04:59:27
	 * @author yangz
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		getFileModel("1");
	}

	
	public static void getFileModel(String id){
		init();
	}
	
	public static void init() {
		// TODO Auto-generated method stub
	
		String url = "D:\\";
		String result = null;
	
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		
		String fileDir="课程推送规则说明.doc";

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
		    result = (String) call.invoke(new Object[] { fileDir,
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
		
		try {
			byte[] a=base64Tobyte(result);
			FileOutputStream fos = new FileOutputStream(url+"课程推送规则说明.doc");  
	        fos.write(a);  
	        fos.close();  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		StringBuffer msg=new StringBuffer();
		msg.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");*/
		String zfxml = "";
		DownLoadBean bean = new DownLoadBean();
		bean.setAdjunctId(fileDir);
		bean.setDownLoadURL(WebServiceConf.DOWNLOADURL + fileDir);
		bean.setFileName(fileDir);

		String[] output = new String[] { "fileName", "adjunctId",
				"downLoadURL" };// 需要输出的字段
		@SuppressWarnings("rawtypes")
		List indexlist = new ArrayList();
		indexlist.add(bean);
		zfxml = XmltoString.xmlToStringNew(output, SelectItems
				.getReflectObjPropertyValue(indexlist,
						DownLoadBean.class, output), "DOWNLOAD");
		
		System.out.println(zfxml);
		
		
    }
	public static byte[] base64Tobyte(String att){
		return Base64.decodeBase64(att.getBytes());
	}
}
