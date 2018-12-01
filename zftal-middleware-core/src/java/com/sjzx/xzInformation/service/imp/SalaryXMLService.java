package com.sjzx.xzInformation.service.imp;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.common.Config;
import com.common.WebServiceConf;
import com.sjzx.xzInformation.service.ISalaryXMLService;


/**
 * <p>Description:薪资接口实现类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2014-4-3 下午01:50:10
 * @author yangz
 * @version 1.0
 */
@Service
@Component(value = "salaryXMLService")
public class SalaryXMLService implements ISalaryXMLService{
	private static Logger logger = Logger.getLogger(SalaryXMLService.class);
	private final String infromation=Config.getString("mobile.infromation");

	/**
	 * <p>Description: 中央民族大学教工其他收入查询</p>
	 * @param username 用户名
	 * @param sendtime 发送时间
	 * @param strKey   加密后密钥
	 * @return
	 *
	 * @since 2014-4-4 下午02:53:56
	 * @author yangz
	 */
	@Override
	public String getqtsr( String username, String sendtime,String strKey) {
		// TODO Auto-generated method stub
	//	String infromation=Config.getString("mobile.infromation");
		if(infromation.equals("0")){
		logger.error("调用getqtsr中央民族大学教工其他收入查询："+"username="+username+"sendtime="+sendtime+"strKey="+strKey);
		}
		String endpointURL = WebServiceConf.WEBSERVICE_ZYMZ_QTSR;
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
		    tmp =  (String)call.invoke(new Object[] {sendtime,username,strKey});//实际调用
			System.out.print("中央民族大学教工其他收入查询：" +  tmp); //测试
	    }catch (Exception e) {
			// TODO: handle exception
	    	System.out.print("中央民族大学教工其他收入查询erro" +  e); //测试
		}
	    if(infromation.equals("0")){
	    logger.error("调用getqtsr中央民族大学教工其他收入查询返回为："+tmp);
	    }
		return tmp;
	}

	/**
	 * <p>Description:中央民族大学教工薪资明细查询 </p>
	 * @param username 用户名
	 * @param sendtime 发送时间
	 * @param strKey   加密后密钥
	 * @return
	 *
	 * @since 2014-4-10 上午09:25:58
	 * @author yangz
	 */
	@Override
	public String getXZMXCX(String username, String sendtime, String strKey) {
		// TODO Auto-generated method stub
	//	String infromation=Config.getString("mobile.infromation");
		if(infromation.equals("0")){
		logger.error("调用getXZMXCX中央民族大学教工薪资明细查询："+"username="+username+"sendtime="+sendtime+"strKey="+strKey);
		}
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
		    tmp =  (String)call.invoke(new Object[] {sendtime,username,strKey});//实际调用
			System.out.println("中央民族大学教工薪资明细" +  tmp); //测试
	    }catch (Exception e) {
			// TODO: handle exception
	    	System.out.println("中央民族大学教工薪资明细erro" +  e); //测试
		}
	    if(infromation.equals("0")){
	    logger.error("调用getXZMXCX中央民族大学教工薪资明细查询返回为："+tmp);
	    }
		return tmp;
	}

}
