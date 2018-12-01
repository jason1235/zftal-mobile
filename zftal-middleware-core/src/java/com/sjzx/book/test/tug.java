package com.sjzx.book.test;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;

import com.common.WebServiceConf;

public class tug {

	/**
	 * <p>Description: </p>
	 * @param args
	 *
	 * @since 2014-12-8 上午10:30:54
	 * @author yangz
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 String endpointURL = WebServiceConf.WEBSERVICE_CQDRSF_TSGJYXX;
			org.apache.axis.client.Service  service= new org.apache.axis.client.Service() ;
			String tmp = null;
		    try{
		        Call call;
				call = (Call) service.createCall();
			    call.setTargetEndpointAddress( new java.net.URL(endpointURL) );
		//      call.setOperationName("getSzdacx" );//设置对应方法 (原  
	            //来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
	            //名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
	            QName opAddEntry = new QName(WebServiceConf.WEBSERVICE_NAMESPACE_ZYMZ,"tsgjyxxb");
			    call.setOperationName(opAddEntry);
			    tmp =  (String)call.invoke(new Object[] {"2010010318","nD9i2ZemQbYrY+EnX8V9OA1NxBO2HLhI"});
				System.out.print("调用getyktjbxx重庆第二师范学院图书馆借阅信息" +  tmp); //测试
		    }catch (Exception e) {
				// TODO: handle exception
		    	System.out.print("调用getyktxfxx重庆第二师范学院图书馆借阅信息Erro" +  e); //测试
			}
		
	}

}
