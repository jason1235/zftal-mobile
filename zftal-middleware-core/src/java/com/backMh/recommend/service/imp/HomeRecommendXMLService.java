package com.backMh.recommend.service.imp;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.backMh.recommend.CXFService.service.IRecommendXmlService;
import com.backMh.recommend.service.IHomeRecommendXMLService;
import com.common.WebServiceConf;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
/**
 * WebService实现类.
 * 
 * 使用@WebService指向Interface定义类即可.
 */

@Service
@Component(value = "homeRecommendXMLService")
public class HomeRecommendXMLService implements IHomeRecommendXMLService {

	public String getMhRecommendList(String start, String size,String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			start  		= CodeUtil.decode(start, apptoken);
			size  		= CodeUtil.decode(size, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		

			// 调用WebService
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(IRecommendXmlService.class);
			factory.setAddress(WebServiceConf.SERVICE_BACKMHSERVICE_HR);//接口地址
			factory.getInInterceptors().add(new LoggingInInterceptor());
			factory.getOutInterceptors().add(new LoggingOutInterceptor());

			IRecommendXmlService service = (IRecommendXmlService) factory.create();

			return service.getMhRecommendList(Integer.valueOf(start), Integer.valueOf(size));
	}

	public String getMhRecommendPage(String start, String size,String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			start  		= CodeUtil.decode(start, apptoken);
			size  		= CodeUtil.decode(size, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IRecommendXmlService.class);
		factory.setAddress(WebServiceConf.SERVICE_BACKMHSERVICE_HR);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IRecommendXmlService service = (IRecommendXmlService) factory.create();

		return service.getMhRecommendPage(Integer.valueOf(start), Integer.valueOf(size));
	}

	public String getRecommendInfo(String id,String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			id  		= CodeUtil.decode(id, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IRecommendXmlService.class);
		factory.setAddress(WebServiceConf.SERVICE_BACKMHSERVICE_HR);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IRecommendXmlService service = (IRecommendXmlService) factory.create();

		return service.getRecommendInfo(id);
	}
	
}
