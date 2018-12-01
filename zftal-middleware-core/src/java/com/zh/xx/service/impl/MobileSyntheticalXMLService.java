package com.zh.xx.service.impl;



import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.common.Config;
import com.common.WebServiceConf;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zh.xx.CXFService.ZfmlWebService;
import com.zh.xx.service.IMobileSyntheticalXMLService;



/**
 * <p>Description: 综合服务</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2014-6-23 上午10:01:58
 * @author yangz
 * @version 1.0
 */
@Service
@Component(value = "mobileSyntheticalXMLService")
public class MobileSyntheticalXMLService implements IMobileSyntheticalXMLService{
	private static Logger logger = Logger.getLogger(MobileSyntheticalXMLService.class);
	private final String infromation=Config.getString("mobile.infromation");

	/**
	 * <p>Description: 登录校验</p>
	 * @param userName 用户名
	 * @param passWord 密码
	 * @return
	 *
	 * @since 2014-6-23 上午10:02:38
	 * @author yangz
	 */
	@Override
	public String CheckUserInf(String userName, String password, String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			password  		= CodeUtil.decode(password, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(infromation.equals("0")){
			logger.error("调用CheckUserInf登录校验："+"userName="+userName+"passWord="+password);
			}
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(ZfmlWebService.class);
		factory.setAddress(WebServiceConf.SERVICE_XXSERVICE);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		ZfmlWebService service = (ZfmlWebService) factory.create();
		String str=service.checkUserInf(userName, password);
		if(infromation.equals("0")){
			logger.error("调用CheckUserInf登录校验返回："+str);
			}
		
		return str;
	}

}
