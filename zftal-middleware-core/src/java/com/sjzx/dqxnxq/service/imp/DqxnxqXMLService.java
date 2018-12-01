package com.sjzx.dqxnxq.service.imp;


import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.common.Config;
import com.common.WebServiceConf;
import com.sjzx.dqxnxq.service.IDqxnxqXMLService;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.service.svcinterface.ILoginService;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;

@Service
@Component(value = "dqxnxqXMLService")
public class DqxnxqXMLService implements IDqxnxqXMLService{
	private static Logger logger = Logger.getLogger(DqxnxqXMLService.class);
	private final String infromation=Config.getString("mobile.infromation");
	/**
	 * <p>Description: 获取当前学年学期</p>
	 * @param strKey 密钥
	 * @return
	 *
	 * @since 2014-5-13 上午08:40:13
	 * @author yangz
	 */
	@Override
	public String getDqxnxq(String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			strKey  		= CodeUtil.decode(strKey, apptoken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	//	String infromation=Config.getString("mobile.infromation");
		if(infromation.equals("0")){
		logger.error("调用getDqxnxq获取当前学年学期："+"strKey="+strKey);
		}
		String endpointURL = WebServiceConf.WEBSERVICE_ZYMZ_DQXNXQ;
		org.apache.axis.client.Service  sjzxservice= new org.apache.axis.client.Service() ;
		String tmp = null;
		strKey ="nD9i2ZemQbYrY+EnX8V9OA1NxBO2HLhI";
	    try{
	        Call sjzxcall=null;
	        sjzxcall = (Call) sjzxservice.createCall();
	        sjzxcall.setTargetEndpointAddress( new java.net.URL(endpointURL) );
	//      call.setOperationName("getSzdacx" );//设置对应方法 (原  
            //来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
            //名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
            QName opAddEntry = new QName(WebServiceConf.WEBSERVICE_NAMESPACE_ZYMZ,"dqxnxq");
            sjzxcall.setOperationName(opAddEntry);
		    tmp =  (String)sjzxcall.invoke(new Object[] {strKey});//实际调用
			System.out.print("中央民族当前学年学期" +  tmp);
			
	    }catch (Exception e) {
			// TODO: handle exception
	    	System.out.print("中央民族当前学年学期Erro" +  e); 
		} 
	    if(infromation.equals("0")){
	    logger.error("调用getDqxnxq获取当前学年学期返回为："+tmp);
	    }
	    return tmp;
	}

}
