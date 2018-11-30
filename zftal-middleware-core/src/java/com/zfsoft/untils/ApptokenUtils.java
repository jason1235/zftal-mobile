package com.zfsoft.untils;

import org.apache.log4j.Logger;

import com.common.Config;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.service.svcinterface.ILoginService;
import com.zfsoft.util.base.StringUtil;

public class ApptokenUtils {
	
	
	private static Logger logger = Logger.getLogger(ApptokenUtils.class);

	public static boolean compare(String apptoken){
		String keySwitch = Config.getString("keySwitch");
		if(StringUtil.isEmpty(keySwitch) || (!StringUtil.isEmpty(keySwitch) && keySwitch.equals("on"))){
			String compareKey = null;
			String realKey = null;
			try {
				ILoginService loginServiceImpl = (ILoginService)SpringHolder.getBean("loginService");
				compareKey = loginServiceImpl.getStrKey(apptoken);
				if(StringUtil.isEmpty(compareKey)){
					logger.error("---------------------------" + "app_token exception" + "-----------------------------");
					logger.error("app_token null");
					logger.error("---------------------------" + "app_token exception" + "-----------------------------");
					return false;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean compare(String userName, String apptoken){
		String keySwitch = Config.getString("keySwitch");
		if(StringUtil.isEmpty(keySwitch) || (!StringUtil.isEmpty(keySwitch) && keySwitch.equals("on"))){
			String zgh = null;
			//String realKey = null;
			try {
				userName = CodeUtil.decode(userName, apptoken);
				ILoginService loginServiceImpl = (ILoginService)SpringHolder.getBean("loginService");
				zgh = loginServiceImpl.getStrKey(apptoken);
				if(StringUtil.isEmpty(zgh)){
					logger.error("---------------------------" + "app_token exception" + "-----------------------------");
					logger.error("userName=" + userName + ",apptoken=" + apptoken);
					logger.error("app_token null");
					logger.error("---------------------------" + "app_token exception" + "-----------------------------");
					return false;
				}
				//realKey = loginServiceImpl.getStrKeyByYhm(userName);
				if(!userName.equals(zgh)){
					logger.error("---------------------------" + "app_token exception" + "-----------------------------");
					logger.error("userName=" + userName + ",apptoken=" + apptoken);
					logger.error("app_token error");
					logger.error("---------------------------" + "app_token exception" + "-----------------------------");
					return false;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e);
				return false;
				//e.printStackTrace();
			}
		}
		return true;
	}
	
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String apptoken = "f34387692f3404002d5e990eb0aac92e";
        
		String schoolNumber = "11203080316";
		String dataType = "1";
		String username = "1394";
			
		System.out.println("?username="+java.net.URLEncoder.encode(CodeUtil.encode(username, apptoken),"utf-8")+"&schoolNumber="+java.net.URLEncoder.encode(CodeUtil.encode(schoolNumber, apptoken), "utf-8")+"&dataType="+java.net.URLEncoder.encode(CodeUtil.encode(dataType, apptoken), "utf-8")+"&apptoken="+apptoken);	
	    System.out.println(java.net.URLEncoder.encode("http://wjw.xmut.edu.cn/zftal-sjyt-demoWeb/rest/servicecata/servicecatalogtree","utf-8"));
	}

}
