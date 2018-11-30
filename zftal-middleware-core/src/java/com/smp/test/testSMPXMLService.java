package com.smp.test;

import org.apache.axis.client.Call;

import com.common.ToolUtil;
import com.util.MiddleWareUtil;

import javax.xml.namespace.QName;


public class testSMPXMLService {

	/**
	 * <p>Description: </p>
	 * @param args
	 *
	 * @since 2015-2-11 下午02:08:11
	 * @author yangz
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s="zfsoft";
		String userName="670";
		String sign=MiddleWareUtil.getSMPSign(s+userName);
		System.out.println("秘钥" +  sign); //测试
		String endpointURL = "http://10.71.32.118/zfsmp/services/NewsFlow?wsdl";
		org.apache.axis.client.Service  service= new org.apache.axis.client.Service() ;
		String tmp = null;
		Boolean b;
	    try{
	        Call call;
			call = (Call) service.createCall();
		    call.setTargetEndpointAddress( new java.net.URL(endpointURL) );
	//      call.setOperationName("getSzdacx" );//设置对应方法 (原  
            //来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
            //名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
            QName opAddEntry = new QName("http://10.71.32.118/zfsmp/services/NewsFlow","updateNews");
		    call.setOperationName(opAddEntry);
//		    tmp =  (String)call.invoke(new Object[] {"141999968128592212","2","测试",userName,sign});//实际调用
		    b =  (Boolean)call.invoke(new Object[] {"141999968128592212","2","测试",userName,sign});//实际调用
			System.out.println(" 中央民族大学一卡通余额" +  String.valueOf(b)); //测试
	    }catch (Exception e) {
			// TODO: handle exception
	    	System.out.print(" 中央民族大学一卡通余额Erro" +  e); //测试
		}
	}

}
