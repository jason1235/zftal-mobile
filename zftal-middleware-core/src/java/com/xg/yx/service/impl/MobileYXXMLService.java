package com.xg.yx.service.impl;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.common.Config;
import com.common.ToolUtil;
import com.common.WebServiceConf;
import com.xg.yx.CXFService.IYxService;
import com.xg.yx.service.IMobileYXXMLService;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;

@Service
@Component(value = "mobileYXXMLService")
public class MobileYXXMLService implements IMobileYXXMLService{
	private static Logger logger = Logger.getLogger(MobileYXXMLService.class);
	private final String infromation=Config.getString("mobile.infromation");

	/** <p>
	 * Description:        获取迎新查询通知公告详情
	 * </p> 
	 * @param userName   登陆用户名
	 * @param num        数值类型(获取记录条数,可为空,默认为6条)
	 * @param sign       验证签名
	 * @param pageIndex  
	 * @return
	 */
	public String getTzggDetail(String id, String userName, String sign, String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			id  			= CodeUtil.decode(id, apptoken);
			userName  		= CodeUtil.decode(userName, apptoken);
			sign  			= CodeUtil.decode(sign, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
		if(infromation.equals("0")){
			logger.error("调用getTzggDetail获取迎新查询通知公告详情："+"userName="+userName+"id="+id
					+"sign="+sign);
			}
		String str=null;
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IYxService.class);
		factory.setAddress(WebServiceConf.SERVICE_YXSERVICE);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		IYxService service = ( IYxService) factory.create();
		
		str =service.getTzggDetail(id, userName, sign);
		
		if(infromation.equals("0")){
		logger.error("调用getTzggDetail获取迎新查询通知公告详情："+str);
		}
		return str;
	}

	/** <p>
	 * Description:        获取迎新通知公告
	 * </p>
	 * @param id        通知公告ID
	 * @param userName  登陆用户名
	 * @param sign      验证签名
	 * @return
	 */
	public String getYxTzggTeaList(String userName, String num, String sign,
			String pageIndex, String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			num  			= CodeUtil.decode(num, apptoken);
			userName  		= CodeUtil.decode(userName, apptoken);
			sign  			= CodeUtil.decode(sign, apptoken);
			pageIndex  		= CodeUtil.decode(pageIndex, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		int num1 =Integer.parseInt(num);
		if(infromation.equals("0")){
			logger.error("调用getYxTzggTeaList 获取迎新通知公告："+"userName="+userName+"num="+num
					+"sign="+sign);
			}
		String str=null;
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IYxService.class);
		factory.setAddress(WebServiceConf.SERVICE_YXSERVICE);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		IYxService service = ( IYxService) factory.create();
		
		str =service.getYxTzggTeaList(num1, userName, sign);
		
		if(infromation.equals("0")){
		logger.error("调用getYxTzggTeaList 获取迎新通知公告："+str);
		}
		return str;
	}

}
