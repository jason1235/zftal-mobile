package com.backMh.news.service.imp;

import javax.xml.namespace.QName;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.backMh.news.CXFService.service.INewsXmlService;
import com.backMh.news.service.INewsInformationXMLService;
import com.common.Config;
import com.common.WebServiceConf;
import com.dsf.news.service.imp.DsfXMLService;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
/**
 * WebService实现类.
 * 
 * 使用@WebService指向Interface定义类即可.
 */

@Service
@Component(value = "newsInformationXMLService")
public class NewsInformationXMLService implements INewsInformationXMLService {
	private static Logger logger = Logger.getLogger(NewsInformationXMLService.class);
	private final String infromation=Config.getString("mobile.infromation");
	/**
	 * <p>Description:获取所有新闻类别列表的xml值 </p>
	 * @return
	 *
	 * @since Nov 6, 2012 10:02:58 AM
	 * @author huangzhaoxia
	 */
	public String getAllNewsTypeList(String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(INewsXmlService.class);
		factory.setAddress(WebServiceConf.SERVICE_BACKMHSERVICE_NI);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		INewsXmlService service = (INewsXmlService) factory.create();

		return service.getAllNewsTypeList();
	}

	/**
	 * <p>Description:获取所有新闻列表的xml值 </p>

	 * @param start --开始页
	 * @param size  --每页大小
	 * @return
	 *
	 * @since Nov 6, 2012 10:02:47 AM
	 * @author huangzhaoxia
	 */
	public String getIndexNewsList(String start, String size, String apptoken) {
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
	//	String newstypes = Config.getString("mobile.newstypes");
		String temp =null;
	//	if (newstypes.equals("1")) {
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(INewsXmlService.class);
			factory.setAddress(WebServiceConf.SERVICE_BACKMHSERVICE_NI);//接口地址
			factory.getInInterceptors().add(new LoggingInInterceptor());
			factory.getOutInterceptors().add(new LoggingOutInterceptor());

			INewsXmlService service = (INewsXmlService) factory.create();
			temp =service.getIndexNewsList(Integer.valueOf(start), Integer.valueOf(size));
	/*	}
		else if(newstypes.equals("2")){
			
		}*/

		return temp;
	}
	/**
	 * <p>Description:获取id相应的新闻对象信息 </p>
	 * @param id
	 * @return
	 *
	 * @since Nov 6, 2012 10:03:14 AM
	 * @author huangzhaoxia
	 */
	public String getNewsInfo(String id, String apptoken) {
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
		factory.setServiceClass(INewsXmlService.class);
		factory.setAddress(WebServiceConf.SERVICE_BACKMHSERVICE_NI);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		INewsXmlService service = (INewsXmlService) factory.create();

		return service.getNewsInfo(id);
	}
	/**
	 * <p>Description:按资讯类别Id获取分页新闻列表 </p>
	 * @param newtypeid --资讯类别id
	 * @param start --开始页
	 * @param size  --每页大小
	 * @return
	 *
	 * @since Nov 6, 2012 10:03:58 AM
	 * @author huangzhaoxia
	 */
	public String getTypeIdNewsPageList(String tid, String start, String size, String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			tid  		= CodeUtil.decode(tid, apptoken);
			start  		= CodeUtil.decode(start, apptoken);
			size  		= CodeUtil.decode(size, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		// 调用WebService
	//	String newstypes = Config.getString("mobile.newstypes");
		String temp =null;
//		if (newstypes.equals("1")) {
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(INewsXmlService.class);
			factory.setAddress(WebServiceConf.SERVICE_BACKMHSERVICE_NI);//接口地址
			factory.getInInterceptors().add(new LoggingInInterceptor());
			factory.getOutInterceptors().add(new LoggingOutInterceptor());

			INewsXmlService service = (INewsXmlService) factory.create();
			temp =service.getTypeIdNewsPageList(tid, Integer.valueOf(start), Integer.valueOf(size));
//		}
	/*	else if(newstypes.equals("2")){
		String	strLoginId=Config.getString("webservice.host.dsf.xw.username");
		String	strPwd=Config.getString("webservice.host.dsf.xw.password");
		String	nCataId=Config.getString("webservice.host.dsf.xw.cataid");
			if(infromation.equals("0")){
				logger.error("调用getwsGetMultiInfosLink返回指定栏目下的信息，包括标题、信息的文章页访问地址、作者、来源、创建时间，不包括内容、图片、附件（每次返回最多不超过50条）："+"nCataId="+nCataId+"strLoginId="+strLoginId+"strPwd="+strPwd+"start="+start+"size="+size		
				);
				}
			int nCataIds =Integer.valueOf(nCataId);
			int nStarts=Integer.valueOf(start);
			int nEnds =Integer.valueOf(size);
			String tmp = null;
			String endpointURL = WebServiceConf.SERVICE_DSFXWSERVICE;
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();
			try {
				Call call;
				call = (Call) service.createCall();
				call.setTargetEndpointAddress(new java.net.URL(endpointURL));
				// call.setOperationName("getSzdacx" );//设置对应方法 (原
				// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
				// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
				QName opAddEntry = new QName(
						WebServiceConf.WEBSERVICE_NAMESPACE_DSFXE, "wsGetMultiInfosLink");
				call.setOperationName(opAddEntry);
				tmp = (String) call.invoke(new Object[] { nCataIds,0,nStarts,nEnds,1,"","",strLoginId,strPwd,""});
			} catch (Exception e) {
				// TODO: handle exception
				System.out.print("调用getwsGetMultiInfosLink" + e); // 测试
			}
			if(infromation.equals("0")){
				logger.error("调用getwsGetMultiInfosLink返回指定栏目下的信息，包括标题、信息的文章页访问地址、作者、来源、创建时间，不包括内容、图片、附件（每次返回最多不超过50条）返回为："+tmp);
				}
			return tmp;
		}*/
	

		return temp;
	}
	/**
	  * <p>Description:按资讯类别获取分页新闻列表 </p>

	 * @return
	 *
	 * @since Nov 6, 2012 10:03:24 AM
	 * @author huangzhaoxia
	 */
	public String getTypeNewsPageList(String newType, String start, String size, String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			newType  	= CodeUtil.decode(newType, apptoken);
			start  		= CodeUtil.decode(start, apptoken);
			size  		= CodeUtil.decode(size, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(INewsXmlService.class);
		factory.setAddress(WebServiceConf.SERVICE_BACKMHSERVICE_NI);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		INewsXmlService service = (INewsXmlService) factory.create();

		return service.getTypeNewsPageList(newType, Integer.valueOf(start), Integer.valueOf(size));
	}
	/**
	 * <p>Description:获取所有新闻列表的xml值(图片绝对地址)</p>

	 * @param start --开始页
	 * @param size  --每页大小
	 * @return
	 *
	 * @since Nov 6, 2012 10:02:47 AM
	 * @author huangzhaoxia
	 */
	public String getIndexNewsListNew(String start, String size, String apptoken) {
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
		factory.setServiceClass(INewsXmlService.class);
		factory.setAddress(WebServiceConf.SERVICE_BACKMHSERVICE_NI);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		INewsXmlService service = (INewsXmlService) factory.create();

		return service.getIndexNewsListNew(Integer.valueOf(start), Integer.valueOf(size));
	}
	/**
	 * <p>Description:按资讯类别Id获取分页新闻列表 (图片绝对地址)</p>
	 * @param newtypeid --资讯类别id
	 * @param start --开始页
	 * @param size  --每页大小
	 * @return
	 *
	 * @since Nov 6, 2012 10:03:58 AM
	 * @author huangzhaoxia
	 */
	public String getTypeIdNewsPageListNew(String tid, String start, String size, String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			tid  	= CodeUtil.decode(tid, apptoken);
			start  		= CodeUtil.decode(start, apptoken);
			size  		= CodeUtil.decode(size, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(INewsXmlService.class);
		factory.setAddress(WebServiceConf.SERVICE_BACKMHSERVICE_NI);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		INewsXmlService service = (INewsXmlService) factory.create();

		return service.getTypeIdNewsPageListNew(tid, Integer.valueOf(start), Integer.valueOf(size));
	}
	/**
	  * <p>Description:按资讯类别获取分页新闻列表 (图片绝对地址)</p>

	 * @return
	 *
	 * @since Nov 6, 2012 10:03:24 AM
	 * @author huangzhaoxia
	 */
	public String getTypeNewsPageListNew(String newType, String start, String size, String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			newType  	= CodeUtil.decode(newType, apptoken);
			start  		= CodeUtil.decode(start, apptoken);
			size  		= CodeUtil.decode(size, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(INewsXmlService.class);
		factory.setAddress(WebServiceConf.SERVICE_BACKMHSERVICE_NI);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		INewsXmlService service = (INewsXmlService) factory.create();

		return service.getTypeNewsPageListNew(newType, Integer.valueOf(start), Integer.valueOf(size));
	}
}
