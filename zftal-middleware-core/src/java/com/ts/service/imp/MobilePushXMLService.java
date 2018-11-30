package com.ts.service.imp;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.common.Config;
import com.common.JavaXML;
import com.common.WebServiceConf;
import com.newmobile.login.service.IWSPushService;
import com.newmobile.login.service.IWSSerService;
import com.ts.CXFService.service.IWSPushServicePortType;
import com.ts.service.IMobilePushXMLService;
import com.util.MiddleWareUtil;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;

/**
 * <p>Description: 推送接口实现</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2015-5-12 上午10:54:52
 * @author yangz
 * @version 1.0
 */
@Service
@Component(value = "mobilePushXMLService")
public class MobilePushXMLService implements IMobilePushXMLService{
	private static Logger logger = Logger.getLogger(MobilePushXMLService.class);
	private final String infromation=Config.getString("mobile.infromation");
	/**
	 * 连接符
	 */
	private final String AND = "&";

	/**
	 * <p>Description:             获取接收消息</p>
	 * @param userName             用户名
	 * @param pushType             推送方式
	 * @param configurationType    配置类型
 	 * @param page                 页号
	 * @param size                 获取记录数
	 * @param sign                 秘钥
	 * @return
	 *
	 * @since 2015-5-12 上午10:47:47
	 * @author yangz
	 */
	@Override
	public String GetPushMsg(String userName, String pushType,
			String configurationType, String page, String size, String sign) {
		// TODO Auto-generated method stub
		/*if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  				= CodeUtil.decode(userName, apptoken);
			pushType  				= CodeUtil.decode(pushType, apptoken);
			configurationType  		= CodeUtil.decode(configurationType, apptoken);
			page  					= CodeUtil.decode(page, apptoken);
			size  					= CodeUtil.decode(size, apptoken);
			sign  					= CodeUtil.decode(sign, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		String str=null;
		String parameterList = userName + AND + pushType + AND + configurationType;// + AND
		
		if (sign.equals(MiddleWareUtil.getSign(parameterList)) & !sign.equals("") & !sign.equals(null)) {
		
		
		int size1 = Integer.parseInt(size);
		int page1 = Integer.parseInt(page);
		String strKey=MiddleWareUtil.getRSSign(parameterList);
		if(infromation.equals("0")){
			logger.error("调用GetPushMsg获取接收消息："+"userName="+userName+"\n"+"pushType="+pushType+"\n"+"configurationType="+configurationType
					+"\n"+"page="+page+"\n"+"size="+size+"\n"+"sign="+sign+"\n"+"parameterList="+parameterList);
			}
		
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IWSPushServicePortType.class);
		factory.setAddress(WebServiceConf.SERVICE_TSSERVICE);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IWSPushServicePortType service = (IWSPushServicePortType) factory.create();
		
		str =service.getPushMsg(parameterList, page1, size1, strKey);
		
		if(infromation.equals("0")){
		logger.error("调用GetPushMsg获取接收消息返回："+str);
		}
		return str;
		}
		else{
			String msg="验证失败";
			String code="101";
			str=JavaXML.BuildXMLReturnErro(msg,code);
			return str;
		}
		
	}

	/**
	 * <p>Description:             设定用户id和设备id</p>
	 * @param userName             用户名
	 * @param imei                 移动设备号
	 * @param configurationType    配置类型
	 * @param sign                 秘钥
	 * @return
	 *
	 * @since 2015-5-12 上午11:47:54
	 * @author yangz
	 */
	@Override
	public String SetUserIdAndRegistrationId(String _userName, String imei,
			String configurationType,String sbType, String sign) {
		// TODO Auto-generated method stub
		/*if(!ApptokenUtils.compare(_userName, apptoken))
			return "app_token error";
		try {
			_userName  				= CodeUtil.decode(_userName, apptoken);
			imei  				= CodeUtil.decode(imei, apptoken);
			configurationType  		= CodeUtil.decode(configurationType, apptoken);
			sign  					= CodeUtil.decode(sign, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		String parameterList = _userName + AND + imei + AND + configurationType + AND + sbType;// + AND
		String str=null;
		if (sign.equals(Config.getString("strKey", "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS"))) {
	
		String strKey=MiddleWareUtil.getRSSign(parameterList);
		if(infromation.equals("0")){
			logger.error("调用SetUserIdAndRegistrationId获取设定用户id和设备id："+"userName="+_userName+"\n"+"imei="+imei+"\n"+"configurationType="+configurationType
					+"\n"+"sign="+sign+"\n"+"parameterList="+parameterList);
			}
		
		
		// 调用WebService
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSPushServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_TSSERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSPushServicePortType service = (IWSPushServicePortType) factory.create();
//		
//		str =service.setUserIdAndRegistrationId(parameterList, Config.getString("strKey", "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS"));
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSPushService.class);  
        bean.setAddress(WebServiceConf.SERVICE_PUSHMOBILESERVICE);  
        IWSPushService helloWorldService = (IWSPushService)bean.create();  
        str = helloWorldService.SetUserIdAndRegistrationId(parameterList,Config.getString("strKey", "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS")); 
		
		if(infromation.equals("0")){
		logger.error("调用SetUserIdAndRegistrationId获取设定用户id和设备id返回："+str);
		}
		return str;
	}
	else{
		String msg="验证失败";
		String code="101";
		str=JavaXML.BuildXMLReturnErro(msg,code);
		return str;
	}
  }
}
