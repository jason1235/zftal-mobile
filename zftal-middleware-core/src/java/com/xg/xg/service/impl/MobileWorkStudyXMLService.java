package com.xg.xg.service.impl;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.common.Config;
import com.common.ToolUtil;
import com.common.WebServiceConf;
import com.util.MD5Util;
import com.xg.xg.CXFService.IxgService;
import com.xg.xg.CXFService.ReqData;
import com.xg.xg.CXFService.XtwhService;
import com.xg.xg.CXFService.ZfxgWsException_Exception;
import com.xg.xg.service.IMobileWorkStudyXMLService;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
@Service
@Component(value = "mobileWorkStudyXMLService")
public class MobileWorkStudyXMLService implements IMobileWorkStudyXMLService{
	private static Logger logger = Logger.getLogger(MobileWorkStudyXMLService.class);
	private final String infromation=Config.getString("mobile.infromation");


	/** <p>
	 * Description:        获取学工通知公告
	 * </p>
	 * @param userName   登陆用户名
	 * @param yhlx       用户类型：1.tea；2.stu
	 * @param type       所属类别，目前只能传 -1
	 * @param num        数值类型(获取记录条数,可为空,默认为6条)
	 * @param sign       验证签名
	 * @param pageIndex  
	 * @return
	 */
	public String getLastNoticeList(String userName, String yhlx, String type,
			String num, String sign, String pageIndex, String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			yhlx  			= CodeUtil.decode(yhlx, apptoken);
			type  			= CodeUtil.decode(type, apptoken);
			num  			= CodeUtil.decode(num, apptoken);
			sign  			= CodeUtil.decode(sign, apptoken);
			pageIndex  		= CodeUtil.decode(pageIndex, apptoken);
			
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
			logger.error("调用getLastNoticeList获取学工通知公告："+"userName="+userName+"yhlx="+yhlx+"type="+type
					+"num="+num+"sign="+sign+"pageIndex"+pageIndex);
			}
		String str=null;
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IxgService.class);
		factory.setAddress(WebServiceConf.SERVICE_XGSERVICE);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		IxgService service = (IxgService) factory.create();
		
		str =service.getLastNoticeList(userName, yhlx, type, num1, sign, pageIndex1);
		
		if(infromation.equals("0")){
		logger.error("调用getLastNoticeList获取学工通知公告："+str);
		}
		return str;
	}

	/**<p>
	 * Description:        获取学工通知公告详情
	 * </p>
	 * @param id         通知公告ID
	 * @param userName        登陆用户名
	 * @param sign       验证签名
	 * @return
	 */
	public String getTzggDetail(String id, String userName, String sign, String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			id  			= CodeUtil.decode(id, apptoken);
			sign  			= CodeUtil.decode(sign, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
		if(infromation.equals("0")){
			logger.error("调用getTzggDetail获取学工通知公告详情："+"id="+id+"userName="+userName+"sign="+sign);
			}
		String str=null;
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IxgService.class);
		factory.setAddress(WebServiceConf.SERVICE_XGSERVICE);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		IxgService service = (IxgService) factory.create();
		
		str =service.getTzggDetail(id, userName, sign);
		
		if(infromation.equals("0")){
		logger.error("调用getTzggDetail获取学工通知公告详情："+str);
		}
		return str;
	}

	/* (non-Javadoc)
	 * @see com.xg.xg.service.IMobileWorkStudyXMLService#getTzggCount(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String getTzggCount(String yhm, String yhlx,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(yhm, apptoken))
			return "app_token error";
		try {
			yhm  			= CodeUtil.decode(yhm, apptoken);
			yhlx  			= CodeUtil.decode(yhlx, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String timestamp = String.valueOf(System.currentTimeMillis());
		String cipher = null;
		try {
			cipher = com.util.encode.MD5Util.md5Encode("zfsoft" + yhm + timestamp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
		if(infromation.equals("0")){
			logger.error("调用getTzggDetail获取学工通知公告详情："+"yhm="+yhm+"yhlx="+yhlx);
			}
		String str=null;
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(XtwhService.class);
		factory.setAddress(WebServiceConf.SERVICE_NEWXGSERVICE);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		XtwhService service = (XtwhService) factory.create();
		
		ReqData data = new ReqData();
		data.setYhm(yhm);
		data.setTimestamp(timestamp);
		data.setYhlx(yhlx);
		data.setCipher(cipher);
		try {
			str =service.getTzggCount(data);
		} catch (ZfxgWsException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(infromation.equals("0")){
		logger.error("调用getTzggDetail获取学工通知公告详情："+str);
		}
		return str;
	}

}
