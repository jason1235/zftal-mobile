package com.sjzx.xzInformation.test;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;

import com.common.WebServiceConf;

public class xztest {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String endpointURL = WebServiceConf.WEBSERVICE_ZYMZ_XZMX;
		org.apache.axis.client.Service  service= new org.apache.axis.client.Service() ;
		String tmp = null;
	    try{
	        Call call;
			call = (Call) service.createCall();
		    call.setTargetEndpointAddress( new java.net.URL(endpointURL) );
	//      call.setOperationName("getSzdacx" );//设置对应方法 (原  
            //来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
            //名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
            QName opAddEntry = new QName(WebServiceConf.WEBSERVICE_NAMESPACE_ZYMZ,"getXZMXCX");
		    call.setOperationName(opAddEntry);
		    tmp =  (String)call.invoke(new Object[] {"2014-01","2003064","nD9i2ZemQbYrY+EnX8V9OA1NxBO2HLhI"});//实际调用
			System.out.println("中央民族大学教工薪资明细" +  tmp); //测试
	    }catch (Exception e) {
			// TODO: handle exception
	    	System.out.println("中央民族大学教工薪资明细erro" +  e); //测试
	}
	
/*	String endpointURL = WebServiceConf.WEBSERVICE_ZYMZ_QTSR;
	org.apache.axis.client.Service  service= new org.apache.axis.client.Service() ;
	String tmp = null;
    try{
        Call call;
		call = (Call) service.createCall();
	    call.setTargetEndpointAddress( new java.net.URL(endpointURL) );
//      call.setOperationName("getSzdacx" );//设置对应方法 (原  
        //来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
        //名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
        QName opAddEntry = new QName(WebServiceConf.WEBSERVICE_NAMESPACE_ZYMZ,"getqtsr");
	    call.setOperationName(opAddEntry);
	    tmp =  (String)call.invoke(new Object[] {"2014-01","2003064","nD9i2ZemQbYrY+EnX8V9OA1NxBO2HLhI"});//实际调用
		System.out.print("中央民族大学教工其他收入查询：" +  tmp); //测试
    }catch (Exception e) {
		// TODO: handle exception
    	System.out.print("中央民族大学教工其他收入查询erro" +  e); //测试
	}*/
	}
}
