package com.sjzx.jsInformation.test;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;

import com.common.Config;
import com.common.WebServiceConf;

public class jstest {

	/**
	 * <p>Description: </p>
	 * @param args
	 *
	 * @since 2014-5-15 上午09:55:13
	 * @author yangz
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	/*	String endpointURL = WebServiceConf.WEBSERVICE_ZYMZ_KXJS;
		org.apache.axis.client.Service  service= new org.apache.axis.client.Service() ;
		String tmp = null;
	    try{
	        Call call;
			call = (Call) service.createCall();
		    call.setTargetEndpointAddress( new java.net.URL(endpointURL) );
	//      call.setOperationName("getSzdacx" );//设置对应方法 (原  
            //来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
            //名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
            QName opAddEntry = new QName(WebServiceConf.WEBSERVICE_NAMESPACE_ZYMZ,"getTeachingSiteIdle");
		    call.setOperationName(opAddEntry);
		    tmp =  (String)call.invoke(new Object[] {"单双周","主楼","2014-04-17","2013-2014","4","2","nD9i2ZemQbYrY+EnX8V9OA1NxBO2HLhI"});//实际调用
			System.out.print("中央民族大学空闲教室查询" +  tmp); //测试
	    }catch (Exception e) {
			// TODO: handle exception
	    	System.out.print("中央民族大学空闲教室查询Erro" +  e); //测试
		}*/
	    
	    
	    
	    
		String status=Config.getString("mobile.jwtypes");
		if(status.equals("2")){
		String endpointURL = WebServiceConf.WEBSERVICE_ZYMZ_KXJS;
		org.apache.axis.client.Service  service= new org.apache.axis.client.Service() ;
		String tmp = null;
		String sjzxstrKey = "nD9i2ZemQbYrY+EnX8V9OA1NxBO2HLhI";
	    try{
	        Call call;
			call = (Call) service.createCall();
		    call.setTargetEndpointAddress( new java.net.URL(endpointURL) );
	//      call.setOperationName("getSzdacx" );//设置对应方法 (原  
            //来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
            //名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
            QName opAddEntry = new QName(WebServiceConf.WEBSERVICE_NAMESPACE_ZYMZ,"getTeachingSiteIdle");
		    call.setOperationName(opAddEntry);
//		    tmp =  (String)call.invoke(new Object[] {dsz,jslb,ksjc,kssj,xn,xqmc,xqj,xq,sjzxstrKey});//实际调用
		    tmp =  (String)call.invoke(new Object[] {"单双周","实验室","11,12节","2014-08-05","2013-2014","本校区","2","2","nD9i2ZemQbYrY+EnX8V9OA1NxBO2HLhI"});//实际调用
		    tmp = tmp.replace("data", "table").replace("msg", "row");
			System.out.print("中央民族大学空闲教室查询" +  tmp); //测试
	    }catch (Exception e) {
			// TODO: handle exception
	    	System.out.print("中央民族大学空闲教室查询Erro" +  e); //测试
	}
		}
	}

}
