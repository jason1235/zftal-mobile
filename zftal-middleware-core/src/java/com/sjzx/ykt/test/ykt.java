package com.sjzx.ykt.test;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;

import com.common.Config;
import com.common.WebServiceConf;

public class ykt {
	public static void main(String[] args){
		String tmp = null;
	//	String endpointURL = WebServiceConf.WEBSERVICE_CQDRSF_SDYCXYYKTYE;
		String endpointURL = "http://datacenter.ycxy.com:8068/zfsjzx/services/SerYKTXFLS?wsdl";
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		try {
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			// call.setOperationName("getSzdacx" );//设置对应方法 (原
			// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
			// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
			QName opAddEntry = new QName(
					WebServiceConf.WEBSERVICE_NAMESPACE_ZYMZ, "getYKTXFLS");
			call.setOperationName(opAddEntry);
			tmp = (String) call.invoke(new Object[] { "2012130059", "nD9i2ZemQbYrY+EnX8V9OA1NxBO2HLhI" });
		/*	tmp = tmp.replace("name", "xm").replace("cardno", "kh")
					.replace("employeeno", "rybh");*/
			System.out.print("调用getYKTYE山东英才一卡通余额" + tmp); // 测试
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("调用getYKTYE山东英才一卡通余额Erro" + e); // 测试
		}
		
	}
}
