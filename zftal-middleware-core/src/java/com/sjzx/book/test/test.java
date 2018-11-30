package com.sjzx.book.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.log4j.Logger;



public class test {
	
	private static Logger logger = Logger.getLogger(test.class);
	/**
	 * <p>Description: </p>
	 * @param args
	 *
	 * @since 2014-12-8 上午10:30:54
	 * @author yangz
	 */
	public static void main(String[] args) {
		//绋娉殍锛alias 涓      褰浠?
		// TODO Auto-generated method stub
		/*String endpointURL = "http://111.205.5.135:8081/zfsjzx/services/userinfo?wsdl";
		org.apache.axis.client.Service  service= new org.apache.axis.client.Service() ;
		String tmp = null;
	    try{
	        Call call;
			call = (Call) service.createCall();
		    call.setTargetEndpointAddress( new java.net.URL(endpointURL) );
	//      call.setOperationName("getSzdacx" );//设置对应方法 (原  
            //来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
            //名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
            QName opAddEntry = new QName("http://ws.apache.org/axis2","userinfo");
		    call.setOperationName(opAddEntry);
		    tmp =  (String)call.invoke(new Object[] {"js01","nD9i2ZemQbYrY+EnX8V9OA1NxBO2HLhI"});
			System.out.print( tmp); //测试
	    }catch (Exception e) {
			// TODO: handle exception
	    	System.out.print(e); //测试
		}*/
		try {
			String path = "http://oacs.zjedu.gov.cn:8001/oa2016/sso/zfca?redirectUrl=%2FProject%2FZjJytOA%2FMOA%2FWorkflowTodoList.mhtml";
			System.out.println(java.net.URLDecoder.decode(path, "UTF-8"));
			
		} catch (Exception e) {
			logger.error(e, e.fillInStackTrace());
		}
	}
}
