package com.xg.lx.service.impl;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.common.Config;
import com.common.ToolUtil;
import com.common.WebServiceConf;
import com.xg.lx.CXFService.ILxService;
import com.xg.lx.service.IMobileLeaveSchoolXMLService;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
@Service
@Component(value = "mobileLeaveSchoolXMLService")
public class MobileLeaveSchoolXMLService implements IMobileLeaveSchoolXMLService{
	private static Logger logger = Logger.getLogger(MobileLeaveSchoolXMLService.class);
	private final String infromation=Config.getString("mobile.infromation");

	/** <p>
	 * Description:        获取离校通知公告
	 * </p>
	 * @param userName   登陆用户名
	 * @param yhlx       用户类型：1.tea；2.stu
	 * @param num        数值类型(获取记录条数,可为空,默认为6条)
	 * @param sign       验证签名
	 * @param pageIndex  
	 * @return
	 */
	public String getLxtzggList(String userName, String yhlx, String num,
			String sign, String pageIndex,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  	= CodeUtil.decode(userName, apptoken);
			yhlx  		= CodeUtil.decode(yhlx, apptoken);
			num  		= CodeUtil.decode(num, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);
			pageIndex  	= CodeUtil.decode(pageIndex, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
		if(yhlx.equals("JS")){
			yhlx="tea";
		}
		else if(yhlx.equals("XS")){
			yhlx="stu";
		}
		int num1 =Integer.parseInt(num);
		int pageIndex1 =Integer.parseInt(pageIndex);
		if(infromation.equals("0")){
			logger.error("调用getLxtzggList获取离校通知公告："+"userName="+userName+"yhlx="+yhlx
					+"num="+num+"sign="+sign+"pageIndex"+pageIndex);
			}
		String str=null;
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(ILxService.class);
		factory.setAddress(WebServiceConf.SERVICE_LXSERVICE);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		ILxService service = (ILxService) factory.create();
		
		str =service.getLxTzggList(yhlx, num1, userName, sign, pageIndex1);
		
		if(infromation.equals("0")){
		logger.error("调用getLxtzggList获取离校通知公告："+str);
		}
		return str;
	}

	/**<p>
	 * Description:        获取离校通知公告详情
	 * </p>
	 * @param id         通知公告ID
	 * @param userName   登陆用户名
	 * @param sign       验证签名
	 * @return
	 */
	public String getTzggDetail(String id, String userName, String sign,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  	= CodeUtil.decode(userName, apptoken);
			id  		= CodeUtil.decode(id, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(infromation.equals("0")){
			logger.error("调用getTzggDetail获取离校通知公告详情："+"userName="+userName+"sign="+sign);
			}
		String str=null;
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(ILxService.class);
		factory.setAddress(WebServiceConf.SERVICE_LXSERVICE);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		ILxService service = (ILxService) factory.create();
		
		str =service.getTzggDetail(id, userName, sign);
		
		if(infromation.equals("0")){
		logger.error("调用getTzggDetail获取离校通知公告详情："+str);
		}
		return str;
	}

}
