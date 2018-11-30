package com.sjzx.book.test;





import net.sf.json.JSONArray;

import org.apache.axis.client.Call;

import com.common.JavaXML;


public class testJson {

	/**
	 * <p>Description: </p>
	 * @param args
	 *
	 * @since 2015-2-10 上午09:03:14
	 * @author yangz
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String tmp = null;
		String endpointURL = "http://210.40.2.242:8020/zfdxc/services/SzdaService?wsdl";
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		try {
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			// call.setOperationName("getSzdacx" );//设置对应方法 (原
			// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
			// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
		/*	QName opAddEntry = new QName(
					WebServiceConf.WEBSERVICE_NAMESPACE_ZYMZ, "yktxfjl");*/
			call.setOperationName("getSzdacx");
			tmp = (String) call.invoke(new Object[] { "20066457","N01130302"});
			System.out.println("" + tmp); // 测试
			JSONArray array = JSONArray.fromObject(tmp);
			String temp =JavaXML.BuildXMLForJson(array);
			System.out.print("" + temp); // 测试
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("调用getyktxfxx重庆第二师范学院一卡通消费信息Erro" + e); // 测试
		}
	}

}
