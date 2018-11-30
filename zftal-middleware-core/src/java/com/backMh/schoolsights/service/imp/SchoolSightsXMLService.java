package com.backMh.schoolsights.service.imp;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.backMh.schoolsights.CXFService.service.ISchoolsightsXmlService;
import com.backMh.schoolsights.service.ISchoolSightsXMLService;
import com.common.WebServiceConf;
/**
 * WebService实现类.
 * 
 * 使用@WebService指向Interface定义类即可.
 */

@Service
@Component(value = "schoolSightsXMLService")
public class SchoolSightsXMLService implements ISchoolSightsXMLService {
	/**
	 * <p>Description:获取所有校园风景分类列表 </p>
	 * @return
	 *
	 * @since Nov 6, 2012 11:17:29 AM
	 * @author huangzhaoxia
	 */
	public String getAllSchoolTypeList() {
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(ISchoolsightsXmlService.class);
		factory.setAddress(WebServiceConf.SERVICE_BACKMHSERVICE_SS);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		ISchoolsightsXmlService service = (ISchoolsightsXmlService) factory.create();

		return service.getAllSchoolTypeList();
	}
	/**
	 * <p>Description: 按校区  分页获取校园风景列表</p>
	 * @param tid
	 * @param start --开始页
	 * @param size  --每页大小
	 * @return
	 *
	 * @since Nov 6, 2012 11:17:53 AM
	 * @author huangzhaoxia
	 */
	public String getTypeSchoolSightsPageList(String tid, String start,
			String size) {
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(ISchoolsightsXmlService.class);
		factory.setAddress(WebServiceConf.SERVICE_BACKMHSERVICE_SS);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		ISchoolsightsXmlService service = (ISchoolsightsXmlService) factory.create();

		return service.getTypeSchoolSightsPageList(tid, Integer.valueOf(start), Integer.valueOf(size));
	}
	/**
	 * <p>Description: 按校区  分页获取校园风景列表(图片绝对地址)</p>
	 * @param tid
	 * @param start --开始页
	 * @param size  --每页大小
	 * @return
	 *
	 * @since Nov 6, 2012 11:17:53 AM
	 * @author huangzhaoxia
	 */
	public String getTypeSchoolSightsPageListNew(String tid, String start,
			String size) {
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(ISchoolsightsXmlService.class);
		factory.setAddress(WebServiceConf.SERVICE_BACKMHSERVICE_SS);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		ISchoolsightsXmlService service = (ISchoolsightsXmlService) factory.create();

		return service.getTypeSchoolSightsPageListNew(tid, Integer.valueOf(start), Integer.valueOf(size));
	}
}
