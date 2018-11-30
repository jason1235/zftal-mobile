package com.backMh.introduce.service.imp;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.backMh.introduce.CXFServe.service.IIntroduceXmlService;
import com.backMh.introduce.service.ISchoolIntroduceXmlService;
import com.backMh.schoolintroduce.CXFServe.service.ISIntroduceXmlService;
import com.common.Config;
import com.common.WebServiceConf;


/**
 * WebService实现类.
 * 
 * 使用@WebService指向Interface定义类即可.
 */

@Service
@Component(value = "schoolIntroduceXmlService")
public class SchoolIntroduceXmlService implements ISchoolIntroduceXmlService {
	private static Logger logger = Logger.getLogger(SchoolIntroduceXmlService.class);
	private final String infromation=Config.getString("mobile.infromation");

	/**
	 * <p>Description:获取简介根节点类型列表 </p>
	 * @return
	 *
	 * @since Nov 5, 2012 11:10:31 AM
	 * @author huangzhaoxia
	 */
	public String getRootIntroduceTypeList(){
		// 调用WebService
		String str = null;
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IIntroduceXmlService.class);
		factory.setAddress(WebServiceConf.SERVICE_BACKMHSERVICE_SI);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IIntroduceXmlService service = (IIntroduceXmlService) factory.create();
		
		str=service.getRootIntroduceTypeList();

		if(infromation.equals("0")){
			logger.error("调用获取简介根节点类型列表"+str);
			}
		return str;
	}
	
	/**
	 * <p>Description:获取父节点id的子简介类型列表 </p>
	 * @param id
	 * @param start
	 * @param size
	 * @return
	 *
	 * @since Nov 5, 2012 11:10:49 AM
	 * @author huangzhaoxia
	 */
	public String getChildIntroduceTypeList(String id,String start,String size){
		// 调用WebService
		String str = null;
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IIntroduceXmlService.class);
		factory.setAddress(WebServiceConf.SERVICE_BACKMHSERVICE_SI);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IIntroduceXmlService service = (IIntroduceXmlService) factory.create();

		str=service.getChildIntroduceTypeList(id, Integer.valueOf(start), Integer.valueOf(size));
		
		if(infromation.equals("0")){
			logger.error("调用获取简介根节点类型列表"+str);
			}
		return str;
	}
	
	/**
	 * <p>Description: 获取某简介类型id的简介信息明细。</p>
	 * @param tid
	 * @return
	 *
	 * @since Nov 5, 2012 11:11:01 AM
	 * @author huangzhaoxia
	 */
	public String getIntroduceByTypeId(String tid){
		// 调用WebService
		String str = null;
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IIntroduceXmlService.class);
		factory.setAddress(WebServiceConf.SERVICE_BACKMHSERVICE_SI);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IIntroduceXmlService service = (IIntroduceXmlService) factory.create();
		
		str=service.getIntroduceByTypeId(tid);

		if(infromation.equals("0")){
			logger.error("调用获取简介根节点类型列表"+str);
			}
		
		return str;
	
	}
	/**
	 * <p>Description:获取父节点id的子简介类型列表（添加父节点） </p>
	 * @param id
	 * @param start
	 * @param size
	 * @return
	 *
	 * @since Nov 5, 2012 11:10:49 AM
	 * @author huangzhaoxia
	 */
	public String getChildIntroduceTypeListAddPid(String id,String start,String size){
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IIntroduceXmlService.class);
		factory.setAddress(WebServiceConf.SERVICE_BACKMHSERVICE_SI);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IIntroduceXmlService service = (IIntroduceXmlService) factory.create();

		return service.getChildIntroduceTypeListAddPid(id, Integer.valueOf(start), Integer.valueOf(size));
	}
	/**
	 * <p>Description:获取简介根节点类型列表 </p>
	 * @return
	 *
	 * @since Jan 18, 2013 4:08:25 PM
	 * @author huangzhaoxia
	 */
	public String getRootSchoolIntroduceTypeList(){
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(ISIntroduceXmlService.class);
		factory.setAddress(WebServiceConf.SERVICE_BACKMHSERVICE_SINEW);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		ISIntroduceXmlService service = (ISIntroduceXmlService) factory.create();

		return service.getRootSchoolIntroduceTypeList();
	
	}
	
	/**
	 * <p>Description: 获取简介类型下的内容标题列表</p>
	 * @param id
	 * @param start
	 * @param size
	 * @return
	 *
	 * @since Jan 18, 2013 4:09:38 PM
	 * @author huangzhaoxia
	 */
	public String getSchoolIntroduceTitleList(String id,String start,String size){
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(ISIntroduceXmlService.class);
		factory.setAddress(WebServiceConf.SERVICE_BACKMHSERVICE_SINEW);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		ISIntroduceXmlService service = (ISIntroduceXmlService) factory.create();
		return service.getSchoolIntroduceTitleList(id, Integer.valueOf(start), Integer.valueOf(size));
	
	}
	/**
	 * <p>Description: 获取简介信息明细。</p>
	 * @param tid
	 * @return
	 *
	 * @since Jan 18, 2013 4:11:21 PM
	 * @author huangzhaoxia
	 */
	public String getSchoolIntroduceById(String tid){
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(ISIntroduceXmlService.class);
		factory.setAddress(WebServiceConf.SERVICE_BACKMHSERVICE_SINEW);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		ISIntroduceXmlService service = (ISIntroduceXmlService) factory.create();

		return service.getSchoolIntroduceById(tid);
	
	}
	
}
