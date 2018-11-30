package com.newmobile.login.service.imp;

import java.io.IOException;
import java.io.InputStream;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.common.Config;
import com.common.WebServiceConf;
import com.newmobile.login.service.IMobileLoginXMLService;
import com.newmobile.login.service.IWSPushService;
import com.newmobile.login.service.IWSSerService;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.hrm.core.util.Byte_File_Object;
//import com.zfsoft.mobile.webservices.cxf.service.IWSSerService;
import com.zfsoft.service.svcinterface.ILoginService;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;



/**
 * <p>Description: 新移动登陆接口实现方法</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2015-7-23 下午03:22:11
 * @author zyang
 * @version 1.0
 */
@Service
@Component(value = "mobileLoginXMLService")
public class MobileLoginXMLService implements IMobileLoginXMLService{
	private static Logger logger = Logger.getLogger(MobileLoginXMLService.class);
	private final String infromation=Config.getString("mobile.infromation");
	
	/**
	 * 版本更新验证
	 * @param imei
	 * @param imsi
	 * @param sysinfo
	 * @param ua
	 * @param phonum
	 * @param account
	 * @param v
	 * @return
	 */
	public String versionCompare(String imei, String imsi, String sysinfo,
			String ua, String phonum, String account, String v){
		String str=null;
		if(infromation.equals("0")){
			logger.error("版本校验:v="+v);
			}
		// 调用WebService
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		
//		str =service.versionCompare(imei, imsi, sysinfo, ua, phonum, account, v);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.versionCompare(imei, imsi, sysinfo, ua, phonum, account, v, "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");  
		
		if(infromation.equals("0")){
		logger.error("版本校验返回："+str);
		}
		return str;
	}

	/**
	 * 获取通用服务
	 */
	public String getCommonService(){
		String str=null;
		if(infromation.equals("0")){
			logger.error("获取通用服务:");
			}
		// 调用WebService
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		
//		str =service.getCommonService();
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.getCommonService("WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");  
		
		
		if(infromation.equals("0")){
		logger.error("获取通用服务返回："+str);
		}
		return str;
	}
	/**
	 * 获取推荐位新闻
	 */
	public String getMhRecommendPage(String size){
		String str=null;
//		if(infromation.equals("0")){
//			logger.error("获取推荐位新闻:"+",size="+size);
//			}
//		// 调用WebService
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		int newsSize = Integer.valueOf(size);
//		
//		str =service.getMhRecommendPage(newsSize);
//		
//		if(infromation.equals("0")){
//		logger.error("获取推荐位新闻返回："+str);
//		}
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.getMhRecommendPage(Integer.valueOf(size));
		
		if(infromation.equals("0")){
		logger.error("Loading时获取信息："+str);
		}
		return str;
	}
	/**
	 * <p>Description: Loading时获取信息</p>
	 * @return
	 * 
	 * @since 2015-7-23 下午03:22:30
	 * @author zyang
	 */
	@Override
	public String getMobileAppType() {
		// TODO Auto-generated method stub
		

		String str=null;
//		// 调用WebService
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		
//		str =service.getMobileAppType();
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.getMobileAppType("WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");  
		
		if(infromation.equals("0")){
		logger.error("Loading时获取信息："+str);
		}
		return str;
	}

	/**
	 * <p>Description: 获取首页推荐位新闻</p>
	 * @param start
	 * @param size
	 * @return
	 *
	 * @since 2015-7-30 下午03:47:42
	 * @author zyang
	 *//*
	@Override
	public String getMhRecommendPage(String categoryId,String start, String size,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			categoryId  		= CodeUtil.decode(categoryId, apptoken);
			start  				= CodeUtil.decode(start, apptoken);
			size  				= CodeUtil.decode(size, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str=null;
		int starts= Integer.valueOf(start);
		int sizes =Integer.valueOf(size);
		if(infromation.equals("0")){
			logger.error("获取首页推荐位新闻："+"categoryId="+"\n"+categoryId+"start="+"\n"+start+"size="+"\n"+size);
			}
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IWSSerServicePortType.class);
		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
		
		str =service.getMhRecommendPage(categoryId,starts,sizes);
		
		if(infromation.equals("0")){
		logger.error("获取首页推荐位新闻返回为："+str);
		}
		return str;
	}*/

	/**
	 * <p>Description: 获取新闻栏目</p>
	 * @return
	 *
	 * @since 2015-7-31 下午03:26:51
	 * @author zyang
	 */
	@Override
	public String getNewsTab(String username,String strKey) {
		// TODO Auto-generated method stub
		
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("获取新闻栏目:");
			}
		// 调用WebService
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		
//		str =service.getNewsTab(username, strKey);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.getNewsTab(username,"WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");  
		
		if(infromation.equals("0")){
		logger.error("获取新闻栏目返回："+str);
		}
		return str;
	}

	/**
	 * <p>Description: 获取新闻栏目对应的列表</p>
	 * @param id       栏目Id
	 * @param start    起始页
	 * @param size     每页多少
	 * @return
	 *
	 * @since 2015-7-31 下午03:27:56
	 * @author zyang
	 */
	@Override
	public String getNewsList(String id, String start, String size) {
		// TODO Auto-generated method stub
		
		
		String str=null;
		int starts= Integer.valueOf(start);
		int sizes =Integer.valueOf(size);

		if(infromation.equals("0")){
			logger.error("获取新闻栏目对应的列表："+"id="+"\n"+id+"start="+"\n"+start+"size="+"\n"+size);
			}
		
		// 调用WebService
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		
//		str =service.getNewsList(id, starts, sizes);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
		try {
			str = helloWorldService.getNewsList(id, starts, sizes, "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(infromation.equals("0")){
		logger.error("获取新闻栏目对应的列表返回为："+str);
		}
		return str;
	}
	
	

	/**
	 * <p>Description: 登陆接口</p>
	 * @param userName
	 * @param passWord
	 * @param strKey
	 * @return
	 *
	 * @since 2015-8-3 上午09:56:12
	 * @author zyang
	 */
	@Override
	public String Login(String userName, String passWord, String strKey,String status) {
		// TODO Auto-generated method stub
		
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("获取登陆接口："+"userName="+"\n"+userName+"passWord="+"\n"+passWord+"strKey="+"\n"+strKey);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		
//		str =service.login(userName, passWord, strKey);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.login(userName, passWord,"WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS",status);  
		
		if(infromation.equals("0")){
		logger.error("登陆接口返回为："+str);
		}
		return str;
	}

	/**
	 * <p>Description: 应用中心常用模块获取</p>
	 * @param userName
	 * @param strKey
	 * @return
	 *
	 * @since 2015-8-3 上午10:12:15
	 * @author zyang
	 */
	@Override
	public String Commonfunction(String userName, String strKey,String apptoken) {
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("应用中心常用模块获取："+"userName="+"\n"+userName+"strKey="+"\n"+strKey);
			}

//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		
//		str =service.commonfunction(userName, strKey);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.Commonfunction(userName,"WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");  
		
		if(infromation.equals("0")){
		logger.error("应用中心常用模块获取返回为："+str);
		}
		return str;
	}

	/**
	 * <p>Description: 应用中心所有模块获取</p>
	 * @param userName
	 * @param strKey
	 * @return
	 *
	 * @since 2015-8-3 上午10:16:25
	 * @author zyang
	 */
	@Override
	public String CommonAllFunction(String userName, String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("应应用中心所有模块获取："+"userName="+userName+"strKey="+strKey);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		
//		str =service.commonAllFunction(userName, strKey);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.CommonAllFunction(userName,"WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");  
		
		if(infromation.equals("0")){
		logger.error("应用中心所有模块获取返回为："+str);
		}
		return str;
	}

	/**
	 * <p>Description: 应用中心模块类型筛选</p>
	 * @param userName   用户名
	 * @param moduletype 模块类型
	 * @param strKey     秘钥
	 * @return
	 *
	 * @since 2015-8-4 下午01:49:13
	 * @author zyang
	 */
	@Override
	public String CommonBrushFunction(String userName, String moduletype,
			String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			moduletype  	= CodeUtil.decode(moduletype, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("应用中心模块类型筛选："+"userName="+"\n"+userName+"moduletype="+"\n"+moduletype+"strKey="+"\n"+strKey);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		
//		str =service.commonBrushFunction(userName, moduletype, strKey);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.CommonBrushFunction(userName, moduletype,"WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS"); 
		
		if(infromation.equals("0")){
		logger.error("应用中心模块类型筛选返回为："+str);
		}
		return str;
	}

	/**
	 * <p>Description:   应用中心订阅提交</p>
	 * @param userName   用户名
	 * @param moduletype 模块类型
	 * @param strKey     秘钥
	 * @return
	 *
	 * @since 2015-8-4 下午01:53:40
	 * @author zyang
	 */
	@Override
	public String SubmitCommonFunction(String userName, String servicecode,String apptoken
			) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  			= CodeUtil.decode(userName, apptoken);
			servicecode  		= CodeUtil.decode(servicecode, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("应用中心订阅提交："+"userName="+"\n"+userName+"servicecode="+"\n"+servicecode);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		
//		str =service.submitCommonFunction(userName, servicecode, true);
//		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.SubmitCommonFunction(userName, servicecode, true,"WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS"); 
		
		if(infromation.equals("0")){
		logger.error("应用中心订阅提交返回为："+str);
		}
		return str;
	}

	/**
	 * <p>Description: 应用中心未订阅模块获取</p>
	 * @param userName 用户名
	 * @param strKey   密码
	 * @return
	 *
	 * @since 2015-8-3 上午10:15:36
	 * @author zyang
	 */
	@Override
	public String CommonOtherFunction(String userName, String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  			= CodeUtil.decode(userName, apptoken);
			strKey  			= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("应用中心未订阅模块获取："+"userName="+userName+"strKey="+strKey);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		
//		str =service.commonOtherFunction(userName, strKey);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.CommonOtherFunction(userName ,"WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS"); 
		
		if(infromation.equals("0")){
		logger.error("应用中心未订阅模块获取返回为："+str);
		}
		return str;
	}

	/**
	 * <p>Description: 我的门户</p>
	 * @param userName
	 * @param strKey
	 * @return
	 *
	 * @since 2015-10-15 上午09:24:39
	 * @author yangz
	 */
	@Override
	public String myPortalFunction(String userName, String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  			= CodeUtil.decode(userName, apptoken);
			strKey  			= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("我的门户获取："+"userName="+userName+"strKey="+strKey+"apptoken="+apptoken);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		
//		str =service.myPortalFunction(userName, strKey);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.myPortalFunction(userName,"WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS"); 
		
		if(infromation.equals("0")){
		logger.error("我的门户获取返回为："+str);
		}
		return str;
	}

	/**
	 * <p>Description: 应用中心下拉类型筛选</p>
	 * @param userName
	 * @return
	 *
	 * @since 2015-10-26 下午02:31:15
	 * @author yangz
	 */
	@Override
	public String getALLXtYwByUser(String userName,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  			= CodeUtil.decode(userName, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("应用中心下拉类型筛选获取："+"userName="+userName);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		
//		str =service.getAllXtYwByUser(userName);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.getAllXtYwByUser(userName,"WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS"); 
		
		if(infromation.equals("0")){
		logger.error("应用中心下拉类型筛选返回为："+str);
		}
		return str;
	}
	
	/**
	 * <p>Description: 我的门户图书馆未归还图书，一卡通余额，工资信息接口</p>
	 * @param userName
	 * @param strKey
	 * @return
	 */
	@Override
	public String getPortalInfo(String userName, String strKey,String apptoken) {
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  			= CodeUtil.decode(userName, apptoken);
			strKey  			= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("我的门户一卡通，图书馆，工资接口获取："+"userName="+userName);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		
//		str =service.getPortalInfo(userName, strKey);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.getPortalInfo(userName,"WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS"); 
		
		if(infromation.equals("0")){
		logger.error("我的门户一卡通，图书馆，工资接口获取："+str);
		}
		return str;
	}

	@Override
	public String submitSuggestion(String userName,String suggestion, String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  				= CodeUtil.decode(userName, apptoken);
			suggestion  			= CodeUtil.decode(suggestion, apptoken);
			strKey  				= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("意见反馈："+"userName="+userName);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		
//		str =service.submitSuggestion(userName, suggestion, strKey);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.submitSuggestion(userName, suggestion,"WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS"); 
		
		if(infromation.equals("0")){
		logger.error("意见反馈："+str);
		}
		return str;
	}

	@Override
	public String uploadMyPicture(String userName, String fileName,
			InputStream content,String apptoken) {
		// TODO Auto-generated method stub
		
		
		
		byte[] srtbyte = Byte_File_Object.getBytesFromFile(content);
		String str=null;
		if(infromation.equals("0")){
			logger.error("上传我的头像："+"userName="+userName+",fileName="+fileName+"content isnot null:" + content!=null);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		
//		str =service.uploadMyPicture(userName, fileName, srtbyte);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.uploadMyPicture(userName, fileName, srtbyte, "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS"); 
		
		if(infromation.equals("0")){
		logger.error("上传我的头像接口获取："+str);
		}
		return str;
	}

	
	@Override
	public String getMyPicturePath(String userName,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("获取我的头像路径："+"userName="+userName);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		
//		str =service.getMyPicturePath(userName);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.getMyPicturePath(userName, "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS"); 
		
		if(infromation.equals("0")){
		logger.error("获取我的头像路径接口获取："+str);
		}
		return str;
	}

	@Override
	public String digitalArchivesDpList(String userName, String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("部门列表获取："+"userName="+userName);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		
//		str =service.digitalArchivesDpList(userName,strKey);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.digitalArchivesDpList(userName, "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS"); 
		
		if(infromation.equals("0")){
		logger.error("部门列表获取："+str);
		}
		return str;
	}

	@Override
	public String digitalArchivesPersonnelList(String userName, String dpId,
			String strKey, String searchName, String start, String size,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			dpId  			= CodeUtil.decode(dpId, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
			searchName  	= CodeUtil.decode(searchName, apptoken);
			start  			= CodeUtil.decode(start, apptoken);
			size  			= CodeUtil.decode(size, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("部门人员列表："+"userName="+userName+"dpId="+dpId+"searchName="+searchName+"start="+start+"size="+size);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
		
		int Cstart = Integer.valueOf(start).intValue();
		int Csize = Integer.valueOf(size).intValue();
		
//		str =service.digitalArchivesPersonnelList(userName,dpId,strKey,searchName,Cstart,Csize);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.digitalArchivesPersonnelList(userName, dpId, "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS", searchName, Cstart, Csize); 
		
		if(infromation.equals("0")){
		logger.error("部门人员列表："+str);
		}
		return str;
	}
	
	/*@Override
	public String digitalArchivesPersonnelList(String userName, String dpId,
			String strKey) {
		// TODO Auto-generated method stub
		String str=null;
		if(infromation.equals("0")){
			logger.error("部门人员列表："+"userName="+userName);
			}
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IWSSerServicePortType.class);
		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
		
		str =service.digitalArchivesPersonnelList(userName,dpId,strKey);
		
		if(infromation.equals("0")){
		logger.error("部门人员列表："+str);
		}
		return str;
	}*/

	@Override
	public String personDocumentInformationList(String userName, String strKey,String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("获取数字档案信息类列表："+"userName="+userName);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		
//		str =service.personDocumentInformationList(userName,strKey);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.personDocumentInformationList(userName, "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
		
		if(infromation.equals("0")){
		logger.error("获取数字档案信息类列表："+str);
		}
		return str;
	}

	@Override
	public String personDocumentInformation(String userName, String informationName,
			String informationId, String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
			informationName = CodeUtil.decode(informationName, apptoken);
			informationId  	= CodeUtil.decode(informationId, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("获取数字档案具体详情信息类内容："+"userName="+userName);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		
//		str =service.personDocumentInformation(userName,informationName,informationId,strKey);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.personDocumentInformation(userName, informationName, informationId, "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
		
		if(infromation.equals("0")){
		logger.error("获取数字档案具体详情信息类内容："+str);
		}
		return str;
	}

	@Override
	public String votelist(String userName, String tphdid, String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			tphdid  		= CodeUtil.decode(tphdid, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("获取投票节目列表："+"userName="+userName+",tphdid="+tphdid);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		
//		str =service.votelist(userName, tphdid, strKey);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.votelist(userName, tphdid , "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
		
		if(infromation.equals("0")){
		logger.error("获取投票节目列表："+str);
		}
		return str;
	}

	@Override
	public String votelike(String userName, String jmid, String tag,
			String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			jmid  			= CodeUtil.decode(jmid, apptoken);
			tag  			= CodeUtil.decode(tag, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("投票交互："+"userName="+userName+",jmid="+jmid+",tag="+tag);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		
//		str =service.votelike(userName, jmid, tag, strKey);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.votelike(userName, jmid, tag, "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
		
		if(infromation.equals("0")){
		logger.error("投票交互："+str);
		}
		return str;
	}

	@Override
	public String voteRanklist(String userName, String tphdid, String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  			= CodeUtil.decode(userName, apptoken);
			tphdid  			= CodeUtil.decode(tphdid, apptoken);
			strKey  			= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("投票结果列表："+"userName="+userName+",tphdid="+tphdid);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		
//		str =service.voteRanklist(userName, tphdid, strKey);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.voteRanklist(userName, tphdid,  "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
		
		if(infromation.equals("0")){
		logger.error("投票结果列表："+str);
		}
		return str;
	}
	@Override
	public String getocbalance(String userName, String strKey, String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  			= CodeUtil.decode(userName, apptoken);
			strKey  			= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String str=null;
		if(infromation.equals("0")){
			logger.error("获取一卡通余额："+"userName="+userName+",strKey="+strKey);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		
//		str =service.getocbalance(userName, strKey);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.getocbalance(userName,   "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
		
		if(infromation.equals("0")){
		logger.error("投票结果列表："+str);
		}
		return str;
	}
	@Override
	public String getocdetail(String detailtype, String ocid, String startdate,
			String enddate, String pageindex, String pagesize, String strkey,
			String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			detailtype  = CodeUtil.decode(detailtype, apptoken);
			ocid  		= CodeUtil.decode(ocid, apptoken);
			startdate  	= CodeUtil.decode(startdate, apptoken);
			enddate  	= CodeUtil.decode(enddate, apptoken);
			pageindex  	= CodeUtil.decode(pageindex, apptoken);
			pagesize  	= CodeUtil.decode(pagesize, apptoken);
			strkey  	= CodeUtil.decode(strkey, apptoken);
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			String str=null;
			if(infromation.equals("0")){
				logger.error("获取一卡通充值消费明细："+"detailtype="+detailtype+",strkey="+strkey+",strkey="+strkey
						+",ocid="+ocid+",startdate="+startdate+",enddate="+enddate+",pageindex="+pageindex+"" +
						",pagesize="+pagesize);
			}
//			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//			factory.setServiceClass(IWSSerServicePortType.class);
//			factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//			factory.getInInterceptors().add(new LoggingInInterceptor());
//			factory.getOutInterceptors().add(new LoggingOutInterceptor());
//			
//			IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//			
//			str =service.getocdetail(detailtype, ocid, startdate,
//					enddate, pageindex, pagesize, strkey);
			
			JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
	        bean.setServiceClass(IWSSerService.class);  
	        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
	        IWSSerService helloWorldService = (IWSSerService)bean.create();  
	        str = helloWorldService.getocdetail(detailtype, ocid, startdate, enddate, pageindex, pagesize, "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
			
			if(infromation.equals("0")){
				logger.error("投票结果列表："+str);
			}
			return str;
		}
	@Override
	public String getWeiBoList(String pageindex, String size,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			pageindex       = CodeUtil.decode(pageindex, apptoken);
			size  		= CodeUtil.decode(size, apptoken);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			String str=null;
			if(infromation.equals("0")){
				logger.error("获取微博列表："+"pageindex="+pageindex+",size="+size+",apptoken="+apptoken);
			}
//			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//			factory.setServiceClass(IWSSerServicePortType.class);
//			factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//			factory.getInInterceptors().add(new LoggingInInterceptor());
//			factory.getOutInterceptors().add(new LoggingOutInterceptor());
//			
//			IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//			
//			str =service.getWeiBoList(Integer.parseInt(pageindex),Integer.parseInt(size));
			
			JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
	        bean.setServiceClass(IWSSerService.class);  
	        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
	        IWSSerService helloWorldService = (IWSSerService)bean.create();  
	        str = helloWorldService.getWeiBoList(Integer.parseInt(pageindex), Integer.parseInt(size), "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
			
			
			if(infromation.equals("0")){
				logger.error("获取微博列表WSS返回结果："+str);
			}
			return str;
	}
	@Override
	public String getUserTimeLine(String wbbh, String pageindex,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			pageindex       = CodeUtil.decode(pageindex, apptoken);
			wbbh  		= CodeUtil.decode(wbbh, apptoken);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			String str=null;
			if(infromation.equals("0")){
				logger.error("根据编号获取发表的微博列表："+"pageindex="+pageindex+",wbbh="+wbbh+",apptoken="+apptoken);
			}
//			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//			factory.setServiceClass(IWSSerServicePortType.class);
//			factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//			factory.getInInterceptors().add(new LoggingInInterceptor());
//			factory.getOutInterceptors().add(new LoggingOutInterceptor());
//			
//			IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//			
//			str =service.getUserTimeLine(wbbh,Long.parseLong(pageindex));
			
			JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
	        bean.setServiceClass(IWSSerService.class);  
	        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
	        IWSSerService helloWorldService = (IWSSerService)bean.create();  
	        str = helloWorldService.getUserTimeLine(wbbh, Long.parseLong(pageindex), "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
			
			if(infromation.equals("0")){
				logger.error("根据编号获取发表的微博列表WSS返回结果："+str);
			}
			return str;
	}
	
	/**
	 * app端进入app引导页调用此接口做安装统计
	 */
	@Override
	public String installsCount() {
		// TODO Auto-generated method stub
		if(infromation.equals("0")){
			logger.error("引导页做安装统计：");
		}
		ILoginService loginServiceImpl = (ILoginService)SpringHolder.getBean("loginService");
		loginServiceImpl.installsCount();
		return "<ResultInfo><code>201</code><message>操作成功</message></ResultInfo>";
	}
	
	/**
	 * 根据服务编码对应用做统计
	 * @param fwbm
	 * @param apptoken
	 * @return
	 */
	@Override
	public String visitsByFwbm(String fwbm,String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			fwbm       = CodeUtil.decode(fwbm, apptoken);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			String str=null;
			if(infromation.equals("0")){
				logger.error("根据服务编码对应用做统计："+"fwbm="+fwbm+",apptoken="+apptoken);
			}
//			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//			factory.setServiceClass(IWSSerServicePortType.class);
//			factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//			factory.getInInterceptors().add(new LoggingInInterceptor());
//			factory.getOutInterceptors().add(new LoggingOutInterceptor());
//			
//			IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//			
//			str =service.visitsByFwbm(fwbm);
			
			JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
	        bean.setServiceClass(IWSSerService.class);  
	        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
	        IWSSerService helloWorldService = (IWSSerService)bean.create();  
	        str = helloWorldService.visitsByFwbm(fwbm,  "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
			
			if(infromation.equals("0")){
				logger.error("根据服务编码对应用做统计返回结果："+str);
			}
			return str;
	}
	
	/**
	 * 根据服务编码和学校编码对应用做统计，
	 * @param fwbm
	 * @param apptoken
	 * @return
	 */
	@Override
	public String insertVisitsToZFByFwbm(String fwbm,String schoolCode,String apptoken) {
		String isPortal = Config.getString("isPortal");
		if(StringUtil.isEmpty(isPortal)){
			if(!ApptokenUtils.compare(apptoken))
				return "app_token error";
		}
		try {
				if(StringUtil.isEmpty(isPortal)){
					fwbm       		 = CodeUtil.decode(fwbm, apptoken);
					schoolCode       = CodeUtil.decode(schoolCode, apptoken);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			String str=null;
			if(infromation.equals("0")){
				logger.error("根据服务编码对应用做统计："+"fwbm="+fwbm+",apptoken="+apptoken);
			}
			if(StringUtil.isEmpty(isPortal)){
//				JaxWsProxyFactoryBean factoryPortal = new JaxWsProxyFactoryBean();
//				factoryPortal.setServiceClass(IWSSerServicePortType.class);
//				factoryPortal.getInInterceptors().add(new LoggingInInterceptor());
//				factoryPortal.getOutInterceptors().add(new LoggingOutInterceptor());
			    String portal_adress =  Config.getString("portal_adress","http://portal.zfsoft.com:9090/zftal-mobile/")+"service/IWSSerService";
//				
//				factoryPortal.setAddress(portal_adress);//接口地址
//				
//				IWSSerServicePortType portal_service = (IWSSerServicePortType) factoryPortal.create();
//				str =portal_service.insertVisitsToZFByFwbm(fwbm,schoolCode);
				
				JaxWsProxyFactoryBean factoryPortal = new JaxWsProxyFactoryBean();  
				factoryPortal.setServiceClass(IWSSerService.class);  
				factoryPortal.setAddress(portal_adress);  
		        IWSSerService helloWorldService1 = (IWSSerService)factoryPortal.create();  
		        str = helloWorldService1.insertVisitsToZFByFwbm(fwbm, schoolCode, "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
				
				
//				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//				factory.setServiceClass(IWSSerServicePortType.class);
//				factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//				factory.getInInterceptors().add(new LoggingInInterceptor());
//				factory.getOutInterceptors().add(new LoggingOutInterceptor());
//				IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		        str =service.visitsByFwbm(fwbm);
				
				JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
		        bean.setServiceClass(IWSSerService.class);  
		        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
		        IWSSerService helloWorldService = (IWSSerService)bean.create();  
		        str = helloWorldService.insertVisitsToZFByFwbm(fwbm, schoolCode, "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
				
			}
			if(!StringUtil.isEmpty(isPortal) && isPortal.equals("yes")){
//				JaxWsProxyFactoryBean factoryPortal = new JaxWsProxyFactoryBean();
//				factoryPortal.setServiceClass(IWSSerServicePortType.class);
//				factoryPortal.getInInterceptors().add(new LoggingInInterceptor());
//				factoryPortal.getOutInterceptors().add(new LoggingOutInterceptor());
//				factoryPortal.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//				
//				IWSSerServicePortType portal_service = (IWSSerServicePortType) factoryPortal.create();
//				str =portal_service.insertVisitsToZFByFwbm(fwbm,schoolCode);
				
				JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
		        bean.setServiceClass(IWSSerService.class);  
		        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
		        IWSSerService helloWorldService = (IWSSerService)bean.create();  
		        str = helloWorldService.insertVisitsToZFByFwbm(fwbm, schoolCode, "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
			}
			
			
			
			if(infromation.equals("0")){
				logger.error("根据服务编码对应用做统计返回结果："+str);
			}
			return str;
	}
	
	@Override
	public String getTermWeek(String ymd, String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			ymd       = CodeUtil.decode(ymd, apptoken);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			String str=null;
			if(infromation.equals("0")){
				logger.error("获取当前学期周："+"ymd="+ymd+",apptoken="+apptoken);
			}
//			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//			factory.setServiceClass(IWSSerServicePortType.class);
//			factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//			factory.getInInterceptors().add(new LoggingInInterceptor());
//			factory.getOutInterceptors().add(new LoggingOutInterceptor());
//			
//			IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//			
//			str =service.getTermWeek(ymd);
			
			JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
	        bean.setServiceClass(IWSSerService.class);  
	        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
	        IWSSerService helloWorldService = (IWSSerService)bean.create();  
	        str = helloWorldService.getTermWeek(ymd, "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
			
			if(infromation.equals("0")){
				logger.error("获取当前学期周："+str);
			}
			return str;
	}
	@Override
	public String getQRcode(String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
			String str=null;
			if(infromation.equals("0")){
				logger.error("获取二维码：apptoken="+apptoken);
			}
//			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//			factory.setServiceClass(IWSSerServicePortType.class);
//			factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//			factory.getInInterceptors().add(new LoggingInInterceptor());
//			factory.getOutInterceptors().add(new LoggingOutInterceptor());
//			
//			IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//			
//			str =service.getQRcode();
			
			JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
	        bean.setServiceClass(IWSSerService.class);  
	        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
	        IWSSerService helloWorldService = (IWSSerService)bean.create();  
	        str = helloWorldService.getQRcode("WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
			
			
			if(infromation.equals("0")){
				logger.error("获取二维码："+str);
			}
			return str;
	}
	@Override
	public String submitExam(String userName, String json,String apptoken) {
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  			= CodeUtil.decode(userName, apptoken);
			json  				= CodeUtil.decode(json, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String str=null;
		if(infromation.equals("0")){
			logger.error("提交发布问卷："+"userName="+userName+",json="+json);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		
//		str =service.submitExam(userName, json);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.submitExam(userName, json,"WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
		
		if(infromation.equals("0")){
		logger.error("提交发布问卷结果："+str);
		}
		return str;
	}
	@Override
	public String getExamList(String userName, String start, String size,
			String apptoken) {
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  			= CodeUtil.decode(userName, apptoken);
			start  				= CodeUtil.decode(start, apptoken);
			size  				= CodeUtil.decode(size, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String str=null;
		if(infromation.equals("0")){
			logger.error("获取问卷："+"userName="+userName+",start="+start+",size="+size);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		str =service.getExamList(userName, Integer.parseInt(start),Integer.parseInt(size));
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.getExamList(userName, Integer.parseInt(start),Integer.parseInt(size),"WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
		
		if(infromation.equals("0")){
		logger.error("获取问卷结果："+str);
		}
		return str;
	}
	@Override
	public String submitExamAnswer(String userName, String answer, String apptoken) {
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  			= CodeUtil.decode(userName, apptoken);
			answer  			= CodeUtil.decode(answer, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String str=null;
		if(infromation.equals("0")){
			logger.error("提交问卷答案："+"userName="+userName+",answer="+answer);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		str =service.submitExamAnswer(userName, answer);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.submitExamAnswer(userName, answer, "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
		
		if(infromation.equals("0")){
		logger.error("提交问卷答案结果："+str);
		}
		return str;
	}
	@Override
	public String getMapList() {
		String str=null;
		if(infromation.equals("0")){
			logger.error("获取地图数据列表：");
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		str =service.getMapList();
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.getMapList("WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
		
		if(infromation.equals("0")){
		logger.error("获取地图数据列表结果："+str);
		}
		return str;
	}

	@Override
	public String publishLossObject(String pulishuser, String title,
			String place, String content,String apptoken,String flag) {
		
		if(!ApptokenUtils.compare(pulishuser, apptoken))
			return "app_token error";
		try {
			pulishuser  		= CodeUtil.decode(pulishuser, apptoken);
			title  				= CodeUtil.decode(title, apptoken);
			place  				= CodeUtil.decode(place, apptoken);
			content  			= CodeUtil.decode(content, apptoken);
			flag  				= CodeUtil.decode(flag, apptoken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("发布失物招领："+"pulishuser="+pulishuser+",title="+title+
					",place="+place+",content="+content);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		str =service.publishLossObject(pulishuser, title, place, content,flag);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.publishLossObject(pulishuser, title, place, content, flag, "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
		
		if(infromation.equals("0")){
		logger.error("获取发布失物招领结果："+str);
		}
		return str;
	}

	@Override
	public String getLossObjectList(String userid, String title, String isover,
			String start, String size,String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			userid  			= CodeUtil.decode(userid, apptoken);
			title  				= CodeUtil.decode(title, apptoken);
			isover  			= CodeUtil.decode(isover, apptoken);
			start  			    = CodeUtil.decode(start, apptoken);
			size  			    = CodeUtil.decode(size, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("获取失物招领列表："+"userid="+userid+",title="+title+
					",isover="+isover+",start="+start+",size="+size);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		str =service.getLossObjectList(userid, title, isover, Integer.parseInt(start), Integer.parseInt(size));
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.getLossObjectList(userid, title, isover, Integer.parseInt(start), Integer.parseInt(size), "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
		
		if(infromation.equals("0")){
		logger.error("获取失物招领列表结果："+str);
		}
		return str;
	}

	@Override
	public String PushMsgList(String userId, String start, String size,
			String apptoken) {
		if(!ApptokenUtils.compare(userId, apptoken))
			return "app_token error";
		try {
			userId  			= CodeUtil.decode(userId, apptoken);
			start  				= CodeUtil.decode(start, apptoken);
			size  				= CodeUtil.decode(size, apptoken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("get pushMsg list ："+"userId="+userId+",start="+start+
					",size="+size);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSPushServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_PUSHMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//		IWSPushServicePortType service = (IWSPushServicePortType) factory.create();
//		str =service.pushMsgList(userId, Integer.parseInt(start), Integer.parseInt(size));
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSPushService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSPushService helloWorldService = (IWSPushService)bean.create();  
        str = helloWorldService.PushMsgList(userId, Integer.parseInt(start), Integer.parseInt(size),"WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
		
		
		if(infromation.equals("0")){
		logger.error("get pushMsg list result："+str);
		}
		return str;
	}

	@Override
	public String submitMemoCatalogList(String userId,
			String memoCatalogNameList, String memoCatalogColorList,
			String apptoken) {
		if(!ApptokenUtils.compare(userId, apptoken))
			return "app_token error";
		try {
			userId  			= CodeUtil.decode(userId, apptoken);
			memoCatalogNameList = CodeUtil.decode(memoCatalogNameList, apptoken);
			memoCatalogColorList= CodeUtil.decode(memoCatalogColorList, apptoken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("get submitMemoCatalogList："+"userId="+userId+",memoCatalogNameList="+memoCatalogNameList+
					",memoCatalogColorList="+memoCatalogColorList);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		
//		str =service.submitMemoCatalogList(userId, memoCatalogNameList, memoCatalogColorList);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.submitMemoCatalogList(userId, memoCatalogNameList, memoCatalogColorList,"WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
		
		
		if(infromation.equals("0")){
		logger.error("获取submitMemoCatalogList结果："+str);
		}
		return str;
	}

	@Override
	public String getMemoCatalogList(String userId, String apptoken) {
		if(!ApptokenUtils.compare(userId, apptoken))
			return "app_token error";
		try {
			userId  			= CodeUtil.decode(userId, apptoken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("get getMemoCatalogList："+"userId="+userId);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		str =service.getMemoCatalogList(userId);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.getMemoCatalogList(userId,"WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
		
		if(infromation.equals("0")){
		logger.error("获取getMemoCatalogList结果："+str);
		}
		return str;
	}

	@Override
	public String getMyMemoList(String userId, String start, String size,
			String title, String memoCatalogName, String apptoken) {
		if(!ApptokenUtils.compare(userId, apptoken))
			return "app_token error";
		try {
			userId  			= CodeUtil.decode(userId, apptoken);
			start  				= CodeUtil.decode(start, apptoken);
			size  				= CodeUtil.decode(size, apptoken);
			title  				= CodeUtil.decode(title, apptoken);
			memoCatalogName  	= CodeUtil.decode(memoCatalogName, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("get getMyMemoList："+"userId="+userId+",start="+start
					+",size="+size+",title="+title+",memoCatalogName="+memoCatalogName);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		str =service.getMyMemoList(userId, Integer.parseInt(start), Integer.parseInt(size), title, memoCatalogName);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.getMyMemoList(userId, Integer.parseInt(start), Integer.parseInt(size), title, memoCatalogName, "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
		
		if(infromation.equals("0")){
		logger.error("获取getMyMemoList结果："+str);
		}
		return str;
	}

	@Override
	public String deleteMyMemoList(String memoFileNameList, String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			memoFileNameList  			= CodeUtil.decode(memoFileNameList, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("get deleteMyMemoList："+"memoFileNameList="+memoFileNameList);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		str =service.deleteMyMemoList(memoFileNameList);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.deleteMyMemoList(memoFileNameList, "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
		
		if(infromation.equals("0")){
		logger.error("获取deleteMyMemoList结果："+str);
		}
		return str;
	}

	@Override
	public String thirdPartyLogin(String openId, String sign) {
		String str=null;
		if(infromation.equals("0")){
			logger.error("get thirdPartyLogin："+"openId="+openId+",sign="+sign);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		str =service.thirdPartyLogin(openId, sign);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.thirdPartyLogin(openId, sign, "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
		
		if(infromation.equals("0")){
		logger.error("获取thirdPartyLogin结果："+str);
		}
		return str;
	}

	@Override
	public String deleteThirdParty(String userId, String type, String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			userId  			= CodeUtil.decode(userId, apptoken);
			type  				= CodeUtil.decode(type, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("get deleteThirdParty："+"userId="+userId+",type="+type);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		str =service.deleteThirdParty(userId, type);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.deleteThirdParty(userId, type, "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
		
		if(infromation.equals("0")){
		logger.error("获取deleteThirdParty结果："+str);
		}
		return str;
	}

	@Override
	public String submitThirdParty(String userId, String openId, String type,
			String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			userId  			= CodeUtil.decode(userId, apptoken);
			openId  			= CodeUtil.decode(openId, apptoken);
			type  				= CodeUtil.decode(type, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("get submitThirdParty："+"userId="+userId+",type="+type+",openId="+openId);
		}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		str =service.submitThirdParty(userId, openId, type);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.submitThirdParty(userId, openId, type, "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
		
		if(infromation.equals("0")){
			logger.error("获取submitThirdParty结果："+str);
		}
		return str;
	}

	@Override
	public String getThirdPartyList(String userId, String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			userId  			= CodeUtil.decode(userId, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("get getThirdPartyList："+"userId="+userId);
			}
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		str =service.getThirdPartyList(userId);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.getThirdPartyList(userId, "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
		
		if(infromation.equals("0")){
		logger.error("获取getThirdPartyList结果："+str);
		}
		return str;
	}

	/* (non-Javadoc)
	 * @see com.newmobile.login.service.IMobileLoginXMLService#visitByUserId(java.lang.String, java.lang.String)
	 */
	@Override
	public String visitByUserId(String userId, String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			userId  			= CodeUtil.decode(userId, apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("get visitByUserId："+"userId="+userId);
		}
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.visitByUserId(userId, "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
        
		
		if(infromation.equals("0")){
			logger.error("获取visitByUserId结果："+str);
		}
		return str;
	}

	/* (non-Javadoc)
	 * @see com.newmobile.login.service.IMobileLoginXMLService#getAllServiceNotreading(java.lang.String)
	 */
	@Override
	public String getAllServiceNotreadingByUserName(String apptoken,String username) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		String str=null;
		if(infromation.equals("0")){
			logger.error("getAllServiceNotreading start----：");
		}
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.getAllServiceNotreadingByUserName("WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS",username);
        
		if(infromation.equals("0")){
			logger.error("获取visitByUserId结果："+str);
		}
		return str;
	}
	
	/* (non-Javadoc)
	 * @see com.newmobile.login.service.IMobileLoginXMLService#getAllServiceNotreading(java.lang.String)
	 */
	@Override
	public String getAllServiceNotreading(String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		String str=null;
		if(infromation.equals("0")){
			logger.error("getAllServiceNotreading start----：");
		}
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.getAllServiceNotreading("WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
        
		if(infromation.equals("0")){
			logger.error("获取visitByUserId结果："+str);
		}
		return str;
	}
	
	
    public String getCanteenList(String canteenname,String startStr,String sizeStr){
    	String str=null;
		if(infromation.equals("0")){
			logger.error("getCanteenList start----：");
		}
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
      
        try {
        	  IWSSerService helloWorldService = (IWSSerService)bean.create();  
        	  str = helloWorldService.getCanteenList(canteenname,startStr,sizeStr);
		} catch (Exception e) {

			logger.error("WangHong------GetCanteenList:" + e.getMessage());
		}
      
        
		if(infromation.equals("0")){
			logger.error("获取getCanteenList结果："+str);
		}
		return str;
    }
	
    public String getCanteenDetailList(String canteenId){
    	String str=null;
		if(infromation.equals("0")){
			logger.error("getCanteenDetailList start----：");
		}
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.getCanteenDetailList(canteenId);
        
		if(infromation.equals("0")){
			logger.error("获取getCanteenDetailList结果："+str);
		}
		return str;
    }
	
    public String placeOrder(String data){
    	String str=null;
		if(infromation.equals("0")){
			logger.error("placeOrder start----：");
		}
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.placeOrder(data);
        
		if(infromation.equals("0")){
			logger.error("获取placeOrder结果："+str);
		}
		return str;
    }
	
    public String getOrderlistByUser(String username,String startStr,String sizeStr){
    	String str=null;
		if(infromation.equals("0")){
			logger.error("getOrderlistByUser start----：");
		}
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.getOrderlistByUser(username,startStr,sizeStr);
        
		if(infromation.equals("0")){
			logger.error("获取getOrderlistByUser结果："+str);
		}
		return str;
    }
    public String findOrderDetail(String username,String orderId){
    	String str=null;
		if(infromation.equals("0")){
			logger.error("getCanteenList start----：");
		}
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.findOrderDetail(username,orderId);
        
		if(infromation.equals("0")){
			logger.error("获取findOrderDetail结果："+str);
		}
		return str;
    }
	
    public String getAddressListByUser(String username,String start,String size){
    	String str=null;
		if(infromation.equals("0")){
			logger.error("getAddressListByUser start----：");
		}
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.getAddressListByUser(username,start,size);
        
		if(infromation.equals("0")){
			logger.error("获取getAddressListByUser结果："+str);
		}
		return str;
    }
	
    public String submitAddressForUser(String addressId,String userId,String name,String mobilePhone,String schoolName,String specificAddress){
    	String str=null;
		if(infromation.equals("0")){
			logger.error("submitAddressForUser start----：");
		}
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.submitAddressForUser(addressId,userId,name,mobilePhone,schoolName,specificAddress);
        
		if(infromation.equals("0")){
			logger.error("获取submitAddressForUser结果："+str);
		}
		return str;
    }
	
    public String deleteAddressByUser(String addressId){
    	String str=null;
		if(infromation.equals("0")){
			logger.error("deleteAddressByUser start----：");
		}
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.deleteAddressByUser(addressId);
        
		if(infromation.equals("0")){
			logger.error("获取deleteAddressByUser结果："+str);
		}
		return str;
    }
    
    public String getReportFixList(String username,String startStr,String sizeStr,String status){
    	String str=null;
		if(infromation.equals("0")){
			logger.error("getReportFixList start----：");
		}
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.getReportFixList(username,startStr,sizeStr,status);
        
		if(infromation.equals("0")){
			logger.error("获取getReportFixList结果："+str);
		}
		return str;
    }
    
    public String getFixType(){
    	String str=null;
		if(infromation.equals("0")){
			logger.error("getFixType start----：");
		}
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.getFixType();
        
		if(infromation.equals("0")){
			logger.error("获取getFixType结果："+str);
		}
		return str;
    }

	@Override
	public String insertReportFix(String username, String problem,
			String telephone, String type, String address, String content,
			String picPath,String userId) {
		String str=null;
		if(infromation.equals("0")){
			logger.error("getFixType start----：");
		}
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.insertReportFix(username,problem,telephone,type,address,content,picPath,userId);
        
		if(infromation.equals("0")){
			logger.error("获取getFixType结果："+str);
		}
		return str;
	}

	@Override
	public String updateEvaluateById(String fixId, String evaluate, String score,String picPath) {
		String str=null;
		if(infromation.equals("0")){
			logger.error("updateEvaluateById start----：");
		}
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.updateEvaluateById(fixId,evaluate,score,picPath);
        
		if(infromation.equals("0")){
			logger.error("获取updateEvaluateById结果："+str);
		}
		return str;
	}

	@Override
	public String getFixById(String fixId) {
		String str=null;
		if(infromation.equals("0")){
			logger.error("getFixById start----：");
		}
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.getFixById(fixId);
        
		if(infromation.equals("0")){
			logger.error("获取getFixById结果："+str);
		}
		return str;
	}

	@Override
	public String conRepair(String username, String fixId,String status) {
		String str=null;
		if(infromation.equals("0")){
			logger.error("conRepair start----：");
		}
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.conRepair(username,fixId,status);
        
		if(infromation.equals("0")){
			logger.error("获取conRepair结果："+str);
		}
		return str;
	}
	
	
	@Override
	public String fixUpload(String fileName,InputStream file) {
		String str=null;
		if(infromation.equals("0")){
			logger.error("conRepair start----：");
		}
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.fixUpload(fileName,file);
        
		if(infromation.equals("0")){
			logger.error("获取conRepair结果："+str);
		}
		return str;
	}

	@Override
	public String voteList(String username, String start, String size,
			String mineVoteFlag, String voteIsDraft, String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			username  			= CodeUtil.decode(username, apptoken);
			start  			= CodeUtil.decode(start, apptoken);
			size  			= CodeUtil.decode(size, apptoken);
			mineVoteFlag  			= CodeUtil.decode(mineVoteFlag, apptoken);
			voteIsDraft  			= CodeUtil.decode(voteIsDraft, apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("voteList start----：");
		}
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.voteList(username,start,size,mineVoteFlag,voteIsDraft);
        
		if(infromation.equals("0")){
			logger.error("获取voteList结果："+str);
		}
		return str;
	}

	@Override
	public String getMyPartInList(String username, String start, String size,
			String voteIsDraft, String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			username  			= CodeUtil.decode(username, apptoken);
			start  			= CodeUtil.decode(start, apptoken);
			size  			= CodeUtil.decode(size, apptoken);
			voteIsDraft  			= CodeUtil.decode(voteIsDraft, apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("getMyPartInList start----：");
		}
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.getMyPartInList(username,start,size,voteIsDraft);
        
		if(infromation.equals("0")){
			logger.error("获取getMyPartInList结果："+str);
		}
		return str;
	}

	@Override
	public String getByVoteId(String username, String voteId, String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			username  			= CodeUtil.decode(username, apptoken);
			voteId  			= CodeUtil.decode(voteId, apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("getByVoteId start----：");
		}
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.getByVoteId(username,voteId);
        
		if(infromation.equals("0")){
			logger.error("获取getByVoteId结果："+str);
		}
		return str;
	}

	@Override
	public String getQzList() {
		String str=null;
		if(infromation.equals("0")){
			logger.error("getQzList start----：");
		}
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.getQzList();
        
		if(infromation.equals("0")){
			logger.error("获取getQzList结果："+str);
		}
		return str;
	}

	@Override
	public String getVoteResultDetail(String username, String voteId,
			String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			username  			= CodeUtil.decode(username, apptoken);
			voteId  			= CodeUtil.decode(voteId, apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("getVoteResultDetail start----：");
		}
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.getVoteResultDetail(username,voteId);
        
		if(infromation.equals("0")){
			logger.error("获取getVoteResultDetail结果："+str);
		}
		return str;
	}

	@Override
	public String getVotePartInPersonDetail(String username, String voteId,
			String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			username  			= CodeUtil.decode(username, apptoken);
			voteId  			= CodeUtil.decode(voteId, apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("getVotePartInPersonDetail start----：");
		}
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.getVotePartInPersonDetail(username,voteId);
        
		if(infromation.equals("0")){
			logger.error("获取getVotePartInPersonDetail结果："+str);
		}
		return str;
	}

	@Override
	public String insertVoteNew(String username, String voteTitle,
			String voteDescription, String voteType, String voteIsMultiSelect,
			String voteMaxChoice, String voteIsAnonymous,
			String voteResultOnlySee, String voteMaxScore,
			String voteScoreMethod, String voteEndDate, String voteIsDraft,
			String optionJsonStr, String qzId) {
		/*if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			username  			= CodeUtil.decode(username, apptoken);
			voteTitle  			= CodeUtil.decode(voteTitle, apptoken);
			voteDescription  			= CodeUtil.decode(voteDescription, apptoken);
			voteType  			= CodeUtil.decode(voteType, apptoken);
			voteIsMultiSelect  			= CodeUtil.decode(voteIsMultiSelect, apptoken);
			voteMaxChoice  			= CodeUtil.decode(voteMaxChoice, apptoken);
			voteIsAnonymous  			= CodeUtil.decode(voteIsAnonymous, apptoken);
			voteResultOnlySee  			= CodeUtil.decode(voteResultOnlySee, apptoken);
			voteMaxScore  			= CodeUtil.decode(voteMaxScore, apptoken);
			voteScoreMethod  			= CodeUtil.decode(voteScoreMethod, apptoken);
			voteEndDate  			= CodeUtil.decode(voteEndDate, apptoken);
			voteIsDraft  			= CodeUtil.decode(voteIsDraft, apptoken);
			optionJsonStr  			= CodeUtil.decode(optionJsonStr, apptoken);
			qzId  			= CodeUtil.decode(qzId, apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("insertVoteNew start----：");
		}
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.insertVoteNew(username,voteTitle,voteDescription,voteType,voteIsMultiSelect,voteMaxChoice,voteIsAnonymous,
        		voteResultOnlySee,voteMaxScore,voteScoreMethod,voteEndDate,voteIsDraft,optionJsonStr,qzId);
        
		if(infromation.equals("0")){
			logger.error("获取insertVoteNew结果："+str);
		}
		return str;
	}

	@Override
	public String insertVoteResult(String username, String voteId,
			String scoreVoteJsonStr) {
		/*if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			username  			= CodeUtil.decode(username, apptoken);
			voteId  			= CodeUtil.decode(voteId, apptoken);
			scoreVoteJsonStr  			= CodeUtil.decode(scoreVoteJsonStr, apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		String str=null;
		if(infromation.equals("0")){
			logger.error("insertVoteResult start----：");
		}
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.insertVoteResult(username,voteId,scoreVoteJsonStr);
        
		if(infromation.equals("0")){
			logger.error("获取insertVoteResult结果："+str);
		}
		return str;
	}

	@Override
	public String updateVote(String voteId, String voteIsDraft) {
		String str=null;
		if(infromation.equals("0")){
			logger.error("updateVote start----：");
		}
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.updateVote(voteId,voteIsDraft);
        
		if(infromation.equals("0")){
			logger.error("获取updateVote结果："+str);
		}
		return str;
	}
    
}
