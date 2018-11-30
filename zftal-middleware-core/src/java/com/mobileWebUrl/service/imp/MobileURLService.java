package com.mobileWebUrl.service.imp;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.common.Config;
import com.mobileWebUrl.service.IMobileURLService;
import com.util.SinglePointToTencentEmail;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;


/**
 * <p>Description: 移动调用WebUrl接口类实现</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2014-4-4 上午11:06:44
 * @author yangz
 * @version 1.0
 */
@Service
@Component(value = "mobileURLService")
public class MobileURLService implements IMobileURLService{
	private static Logger logger = Logger.getLogger(MobileURLService.class);
	private final String infromation=Config.getString("mobile.infromation");

	/**
	 * <p>Description:移动学工实现 </p>
	 * @return
	 *
	 * @since 2014-4-10 上午09:28:57
	 * @author yangz
	 */
	@Override
	public String getMobileXGURL(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String WebViewURL = Config.getString("url.ydxg");//设置WebView对应url
	//	String infromation=Config.getString("mobile.infromation");
		if(infromation.equals("0")){
			logger.error("调用getMobileXGURL移动学工返回为："+WebViewURL);
			}
		return WebViewURL;
	}

	/**
	 * <p>Description:移动学习实现 </p>
	 * @return
	 *
	 * @since 2014-4-10 上午09:29:00
	 * @author yangz
	 */
	@Override
	public String getMoblieXXURL(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String WebViewURL = Config.getString("url.ydxx");//设置WebView对应url
	//	String infromation=Config.getString("mobile.infromation");
		if(infromation.equals("0")){
			logger.error("调用getMoblieXXURL移动学习返回为："+WebViewURL);
			}
		return WebViewURL;
	}

	/**
	 * <p>Description: 一周会议实现</p>
	 * @return
	 *
	 * @since 2014-4-10 上午10:23:51
	 * @author yangz
	 */
	@Override
	public String getMoblieYZHYURL(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String WebViewURL = Config.getString("url.yzhy");//设置WebView对应url
	//	String infromation=Config.getString("mobile.infromation");
		if(infromation.equals("0")){
			logger.error("调用getMoblieYZHYURL一周会议返回为："+WebViewURL);
			}
		return WebViewURL;
	}

	/**
	 * <p>Description: 失物招领实现</p>
	 * @return
	 *
	 * @since 2014-4-24 上午11:21:07
	 * @author yangz
	 */
	@Override
	public String getMobileSWZLURL(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String WebViewURL = Config.getString("url.swzl");//设置WebView对应url
	//	String infromation=Config.getString("mobile.infromation");
		if(infromation.equals("0")){
			logger.error("调用getMobileSWZLURL失物招领返回为："+WebViewURL);
			}
		return WebViewURL;
	}

	/**
	 * <p>Description: 交流社区</p>
	 * @return
	 *
	 * @since 2014-4-28 下午04:58:38
	 * @author yangz
	 */
	@Override
	public String getMobileJLSQURL(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String WebViewURL = Config.getString("url.jlsq");//设置WebView对应url
	//	String infromation=Config.getString("mobile.infromation");
		if(infromation.equals("0")){
			logger.error("调用getMobileJLSQURL交流社区返回为："+WebViewURL);
			}
		return WebViewURL;
	}

	/**
	 * <p>Description: 等级考试报名</p>
	 * @return
	 *
	 * @since 2014-4-29 下午02:36:23
	 * @author yangz
	 */
	@Override
	public String getMobileDJKSBMURL(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String WebViewURL = Config.getString("url.djksbm");//设置WebView对应url
	//	String infromation=Config.getString("mobile.infromation");
		if(infromation.equals("0")){
			logger.error("调用getMobileDJKSBMURL等级考试报名返回为："+WebViewURL);
			}
		return WebViewURL;
	}

	/**
	 * <p>Description: 教师调停课</p>
	 * @return
	 *
	 * @since 2014-4-29 下午02:41:00
	 * @author yangz
	 */
	@Override
	public String getMobileTTKSQURL(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String WebViewURL = Config.getString("url.jsttk");//设置WebView对应url
	//	String infromation=Config.getString("mobile.infromation");
		if(infromation.equals("0")){
			logger.error("调用getMobileTTKSQURL教师调停课返回为："+WebViewURL);
			}
		return WebViewURL;
	}

	/**
	 * <p>Description: 教师学生选课情况查询</p>
	 * @return
	 *
	 * @since 2014-4-29 下午02:41:17
	 * @author yangz
	 */
	@Override
	public String getMobileXKQKCXURL(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String WebViewURL = Config.getString("url.jxxkcx");//设置WebView对应url
	//	String infromation=Config.getString("mobile.infromation");
		if(infromation.equals("0")){
			logger.error("调用getMobileXKQKCXURL教师学生选课情况查询返回为："+WebViewURL);
			}
		return WebViewURL;
	}

	/**
	 * <p>Description: 移动选课</p>
	 * @return
	 *
	 * @since 2014-4-29 下午02:41:33
	 * @author yangz
	 */
	@Override
	public String getMoblieXKURL( String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String WebViewURL = Config.getString("url.ydxk");//设置WebView对应url
	//	String infromation=Config.getString("mobile.infromation");
		if(infromation.equals("0")){
			logger.error("调用getMoblieXKURL移动选课返回为："+WebViewURL);
			}
		return WebViewURL;
	}

	/**
	 * <p>Description: 部门黄页</p>
	 * @return
	 *
	 * @since 2014-5-8 下午05:09:08
	 * @author yangz
	 */
	@Override
	public String getMoblieXNHYURL(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String WebViewURL = Config.getString("url.bmgy");//设置WebView对应url
	//	String infromation=Config.getString("mobile.infromation");
		if(infromation.equals("0")){
			logger.error("调用getMoblieXNHYURL部门黄页返回为："+WebViewURL);
			}
		return WebViewURL;
	}

	/**
	 * <p>Description: 招生简章</p>
	 * @return
	 *
	 * @since 2014-5-26 上午08:58:42
	 * @author yangz
	 */
	@Override
	public String getMoblieZSJZURL(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String WebViewURL = Config.getString("url.zsjz");//设置WebView对应url
	//	String infromation=Config.getString("mobile.infromation");
		if(infromation.equals("0")){
			logger.error("调用getMoblieZSJZURL招生简章返回为："+WebViewURL);
			}
		return WebViewURL;
	}

	/**
	 * <p>Description: 最新公文</p>
	 * @return
	 *
	 * @since 2014-6-26 上午11:50:13
	 * @author yangz
	 */
	@Override
	public String getMoblieZXGWURL(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String WebViewURL = Config.getString("url.zxgw");//设置WebView对应url
		//	String infromation=Config.getString("mobile.infromation");
			if(infromation.equals("0")){
				logger.error("调用getMoblieZXGWURL最新公文返回为："+WebViewURL);
				}
			return WebViewURL;
	}

	/**
	 * <p>Description: 学院共享</p>
	 * @return
	 *
	 * @since 2014-6-27 上午11:31:19
	 * @author yangz
	 */
	@Override
	public String getMobileXYGXURL(String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String WebViewURL = Config.getString("url.xygx");//设置WebView对应url
		//	String infromation=Config.getString("mobile.infromation");
			if(infromation.equals("0")){
				logger.error("调用getMobileXYGXURL学院共享返回为："+WebViewURL);
				}
			return WebViewURL;
	}

	/**
	 * <p>Description: 领导讲话</p>
	 * @return
	 *
	 * @since 2014-6-27 上午11:31:12
	 * @author yangz
	 */
	@Override
	public String getMoblieLDJHURL(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String WebViewURL = Config.getString("url.ldjh");//设置WebView对应url
		//	String infromation=Config.getString("mobile.infromation");
			if(infromation.equals("0")){
				logger.error("调用getMoblieLDJHURL领导讲话返回为："+WebViewURL);
				}
			return WebViewURL;
	}

	/**
	 * <p>Description: 日程管理</p>
	 * @return
	 *
	 * @since 2014-11-13 上午09:44:10
	 * @author yangz
	 */
	@Override
	public String getMobileRCGLURL(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String webViewURL = Config.getString("url.rcgl");
		if(infromation.equals("0")){
			logger.error("调用getMobileRCGLURL日程管理返回为："+webViewURL);
			}
		return webViewURL;
	}

	/**
	 * <p>Description: 移动迎新</p>
	 * @return
	 *
	 * @since 2014-11-17 下午02:02:13
	 * @author yangz
	 */
	@Override
	public String getMobileYDYXURL(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String webViewURL = Config.getString("url.ydyx");
		if(infromation.equals("0")){
			logger.error("调用getMobileYDYXURL移动迎新返回为："+webViewURL);
			}
		return webViewURL;
	}

	/**
	 * <p>Description: 图书馆</p>
	 * @return
	 *
	 * @since 2014-11-19 上午11:24:40
	 * @author yangz
	 */
	@Override
	public String getMobileTSG(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String webViewURL = Config.getString("url.tsg");
		if(infromation.equals("0")){
			logger.error("调用getMobileTSG图书馆返回为："+webViewURL);
			}
		return webViewURL;
	}

	/**
	 * <p>Description: APK下载地址</p>
	 * @return
	 *
	 * @since 2015-1-28 上午09:07:32
	 * @author yangz
	 */
	@Override
	public String getMobileAPKDOWNURL(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String webViewURL = Config.getString("webservice.host.apk.down");
		if(infromation.equals("0")){
			logger.error("调用APK下载地址返回为："+webViewURL);
			}
		return webViewURL;
	}

	/**
	 * <p>Description: 单点登录到腾讯邮箱</p>
	 * @param userName
	 * @return
	 *
	 * @since 2015-2-11 上午08:54:36
	 * @author yangz
	 */
	@Override
	public String getMobileTencentMailURL(String userName, String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String zs = Config.getString("url.zs");
		System.setProperty("javax.net.ssl.trustStore", zs);
		System.getProperties().setProperty( "http.proxyHost", "10.71.19.195" );
	    System.getProperties().setProperty( "http.proxyPort","3125" );
	    System.getProperties().setProperty( "https.proxyHost", "10.71.19.195" );
	    System.getProperties().setProperty( "https.proxyPort","3125" );
		String webViewURL = SinglePointToTencentEmail.getUrl(userName);
		return webViewURL;
	}

	/**
	 * <p>Description: 通知公告(重要提示)</p>
	 * @return
	 *
	 * @since 2015-4-24 下午01:55:01
	 * @author yangz
	 */
	@Override
	public String getMobileTZGGURL(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String webViewURL = Config.getString("url.tzgg");
		if(infromation.equals("0")){
			logger.error("调用通知公告(重要提示)返回为："+webViewURL);
			}
		return webViewURL;
	}

	/**
	 * <p>Description: 校务公开</p>
	 * @return
	 *
	 * @since 2015-4-24 下午01:55:03
	 * @author yangz
	 */
	@Override
	public String getMobileXWGKURL(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String webViewURL = Config.getString("url.xwgk");
		if(infromation.equals("0")){
			logger.error("调用校务公开返回为："+webViewURL);
			}
		return webViewURL;
	}

	/**
	 * <p>Description: 办事指南（公务活动）</p>
	 * @return
	 *
	 * @since 2015-4-24 下午02:07:07
	 * @author yangz
	 */
	@Override
	public String getMobileBSZNURL(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String webViewURL = Config.getString("url.bszn");
		if(infromation.equals("0")){
			logger.error("调用办事指南（公务活动）返回为："+webViewURL);
			}
		return webViewURL;
	}

	/**
	 * <p>Description: 规章制度(学院简报)</p>
	 * @return
	 *
	 * @since 2015-4-24 下午02:11:42
	 * @author yangz
	 */
	@Override
	public String getMobileGZZDURL(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String webViewURL = Config.getString("url.gzzd");
		if(infromation.equals("0")){
			logger.error("调用办事指南（公务活动）返回为："+webViewURL);
			}
		return webViewURL;
	}

	/**
	 * <p>Description: 学院动态（工作通报）</p>
	 * @return
	 *
	 * @since 2015-4-24 下午02:11:49
	 * @author yangz
	 */
	@Override
	public String getMobileXYDTURL(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String webViewURL = Config.getString("url.xydt");
		if(infromation.equals("0")){
			logger.error("调用学院动态（工作通报）返回为："+webViewURL);
			}
		return webViewURL;
	}

	/**
	 * <p>Description: 院务公开（资源下载）</p>
	 * @return
	 *
	 * @since 2015-4-24 下午02:12:14
	 * @author yangz
	 */
	@Override
	public String getMobileYWGKURL(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String webViewURL = Config.getString("url.ywgk");
		if(infromation.equals("0")){
			logger.error("调用院务公开（资源下载）返回为："+webViewURL);
			}
		return webViewURL;
	}

	/**
	 * <p>Description: 工作量统计</p>
	 * @return
	 *
	 * @since 2015-6-19 下午03:11:40
	 * @author yangz
	 */
	@Override
	public String getMobileGZLTJ(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String webViewURL = Config.getString("url.gzltj");
		if(infromation.equals("0")){
			logger.error("调用工作量统计返回为："+webViewURL);
			}
		return webViewURL;
	}

	/**
	 * <p>Description: 调停课统计</p>
	 * @return
	 *
	 * @since 2015-7-1 下午04:10:49
	 * @author yangz
	 */
	@Override
	public String getMobileTTKSQTJURL(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String webViewURL = Config.getString("url.jsttktj");
		if(infromation.equals("0")){
			logger.error("调用调停课统计返回为："+webViewURL);
			}
		return webViewURL;
	}
	
	

}
