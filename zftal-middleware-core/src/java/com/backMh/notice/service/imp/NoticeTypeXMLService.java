package com.backMh.notice.service.imp;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.backMh.notice.CXFService.service.INoticeXmlService;
import com.backMh.notice.service.INoticeTypeXmlService;
import com.common.WebServiceConf;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
/**
 * WebService实现类.
 * 
 * 使用@WebService指向Interface定义类即可.
 */

@Service
@Component(value = "noticeTypeXMLService")
public class NoticeTypeXMLService implements INoticeTypeXmlService {
	/**
	 * <p>Description:获取首页标题类型的通知公告列表的xml值 </p>
	 * @param topCount
	 * @return
	 *
	 * @since Nov 6, 2012 10:26:14 AM
	 * @author huangzhaoxia
	 */
	public String getIndexNoticeListXml(String topCount, String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			topCount  		= CodeUtil.decode(topCount, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(INoticeXmlService.class);
		factory.setAddress(WebServiceConf.SERVICE_BACKMHSERVICE_NT);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		INoticeXmlService service = (INoticeXmlService) factory.create();
		
		int MTopCount = Integer.valueOf(topCount).intValue(); 
		return service.getIndexNoticeListXml(MTopCount);
	}
	/**
	 * <p>Description:根据通知公告的id或者通知公告对象信息 </p>
	 * @param id
	 * @return
	 *
	 * @since Nov 6, 2012 10:26:23 AM
	 * @author huangzhaoxia
	 */
	public String getNoticeInfo(String id,String apptoken) {
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(INoticeXmlService.class);
		factory.setAddress(WebServiceConf.SERVICE_BACKMHSERVICE_NT);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		INoticeXmlService service = (INoticeXmlService) factory.create();

		return service.getNoticeInfo(id);
	}

}
