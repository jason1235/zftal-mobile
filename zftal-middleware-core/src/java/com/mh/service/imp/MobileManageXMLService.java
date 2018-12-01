package com.mh.service.imp;

import java.io.ByteArrayInputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.namespace.QName;

import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.common.Config;
import com.common.ConstantsSoa;
import com.common.WebServiceConf;
import com.google.gson.Gson;
import com.jw.httpUtils.HttpRequest;
import com.jw.org.tempuri.IConfigurationInfo;
import com.mh.CXFServe.service.ICaService;
import com.mh.CXFServe.service.MobileBean;
import com.mh.service.IMobileManageXMLService;
import com.newmobile.login.CXFService.IWSSerServicePortType;
import com.util.SelectItems;
import com.util.encode.XmltoString;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.untils.AESEncrypt;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.encrypt.DBEncrypt;
@Service
@Component(value = "mobileManageXMLService")
public class MobileManageXMLService implements IMobileManageXMLService {
	private final String LYXY = "LYXY";
	private static Logger logger = Logger.getLogger(MobileManageXMLService.class);
	private final String infromation=Config.getString("mobile.infromation");
//axis 框架应用
	/**
	 * <p>Description: 门户登录</p>
	 * @param userName
	 * @param passWord
	 * @return
	 *
	 * @since Nov 1, 2012 11:35:42 AM
	 * @author huangzhaoxia
	 */
	/////////////AXIS方法/////////////////
//	public String checkUse(String userName,String passWord) {
//		String zfxml = "";
//		Call servicecall = null;//清空call。
//		IWebService service =WebServiceImpl.getInstance();
//		servicecall= (Call)service.getCall();
//
//		servicecall.setOperationName(new QName(WebServiceConf.SERVICE_MHSERVICE,"checkUse"));//接口地址和调用方法名
//		try {
//			servicecall.setTargetEndpointAddress(new URL(WebServiceConf.SERVICE_MHSERVICE));//接口地址
//			zfxml = (String)servicecall.invoke(new Object[]{userName,passWord});
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//		
//		return zfxml;
//	}
	public String checkUse(String userName,String passWord,String apptoken) {
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			passWord  		= CodeUtil.decode(passWord, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	//	String infromation=Config.getString("mobile.infromation");
		if(infromation.equals("0")){
			logger.error("调用checkUse门户登录："+"userName="+userName+"passWord="+passWord);
			}
		String str=null;
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(ICaService.class);
		factory.setAddress(WebServiceConf.SERVICE_MHSERVICE);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		ICaService service = (ICaService) factory.create();
		
		str =service.checkUse(userName, passWord);
		
		if(infromation.equals("0")){
		logger.error("调用checkUse门户登录返回："+str);
		}
		return str;
	}
	/**
	 * <p>Description: 登录验证获取基本信息接口</p>
	 * @param userName
	 * @param password
	 * @param remoteURL
	 * @return
	 *
	 * @since Nov 9, 2012 10:54:23 AM
	 * @author huangzhaoxia
	 */
/////////////AXIS方法/////////////////
//	@SuppressWarnings("unchecked")
//	public String getTicket(String userName, String passWord, String remoteURL) {
//		String zfxml = "";
//		MobileBean oMobileBean = new MobileBean();
//		Call servicecall = null;//清空call。
//		IWebService service =WebServiceImpl.getInstance();
//		try {
//			QName us = new QName("urn:BeanService", "MobileBean");
//			servicecall= (Call)service.getCall();
//			servicecall.setOperationName(new QName(WebServiceConf.SERVICE_MHSERVICE,"getTicket"));//接口地址和调用方法名
//			servicecall.registerTypeMapping(MobileBean.class, us, 
//					new BeanSerializerFactory(MobileBean.class, us), new BeanDeserializerFactory(MobileBean.class, us));
//			servicecall.setTargetEndpointAddress(new URL(WebServiceConf.SERVICE_MHSERVICE));//接口地址
//			oMobileBean = (MobileBean) servicecall.invoke(new Object[]{userName,passWord,remoteURL});
//
//			if(oMobileBean==null){
//				return "";
//			}
//			String[] output = new String[]{"cacheck","jsName","xm","bm","dqxn","dqxq","ticket"};//需要输出的字段
//			List indexlist = new ArrayList();
//			indexlist.add(oMobileBean);
//			zfxml =  XmltoString.xmlToStringNew(output, SelectItems.getReflectObjPropertyValue(indexlist, MobileBean.class,output), "MH");
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//		return zfxml;
//	}
	@SuppressWarnings("unchecked")
	public String getTicket(String userName, String passWord, String remoteURL,String apptoken) {
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			passWord  		= CodeUtil.decode(passWord, apptoken);
			remoteURL  		= CodeUtil.decode(remoteURL, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// 调用WebService
	//	String infromation=Config.getString("mobile.infromation");
		if(infromation.equals("0")){
		logger.error("调用登录验证获取基本信息"+"userName="+userName+"passWord="+passWord+"remoteURL="+remoteURL);
		}
		String zfxml = "";
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(ICaService.class);
		factory.setAddress(WebServiceConf.SERVICE_MHSERVICE);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		ICaService service = (ICaService) factory.create();

		MobileBean oMobileBean = new MobileBean();
		
		oMobileBean = (MobileBean) service.getTicket(userName, passWord, remoteURL);
		String[] output = null;
		@SuppressWarnings("rawtypes")
		List indexlist = new ArrayList();
		if(oMobileBean==null || !oMobileBean.isCacheck()){
			output = new String[]{"code","message"};//需要输出的字段
			indexlist.add(oMobileBean);
			zfxml =  XmltoString.xmlToStringForError(output, SelectItems.getReflectObjPropertyValue(indexlist, MobileBean.class,output), "ResultInfo");
			if(infromation.equals("0")){
				logger.error("调用登录验证获取基本信息"+zfxml);
				}
			return zfxml;
		} else {
			output = new String[]{"cacheck","jsName","xm","bm","dqxn","dqxq","ticket","zjhm","yhm"};//需要输出的字段
			if(infromation.equals("0")){
				logger.error("调用登录验证获取基本信息"+output);
				}
		}
		
		if(ConstantsSoa.SCHOOL_INFO.equals(LYXY)){//旅游学院调换用户名和职工号码数据
			String workerCode = oMobileBean.getZjhm();
			String username = oMobileBean.getYhm();
			oMobileBean.setZjhm(username);
			oMobileBean.setYhm(workerCode);
		}
		String status=Config.getString("mobile.jwtypes");
		if(infromation.equals("0")){
			logger.error("调用登录验证获取基本信息"+status);
			}
		//判断状态status:1为教务接口，2为数据中心
		if(status.equals("2")){
			if (oMobileBean.getDqxn()== null){
			//如果当前学年为空，则使用数据中心获取
			if(infromation.equals("0")){
					logger.error("调用登录验证获取基本信息调用数据中心接口");
			}
			String endpointURL = WebServiceConf.WEBSERVICE_ZYMZ_DQXNXQ;
			org.apache.axis.client.Service  sjzxservice= new org.apache.axis.client.Service() ;
			String tmp = null;
			String strKey ="nD9i2ZemQbYrY+EnX8V9OA1NxBO2HLhI";
		    try{
		        Call sjzxcall=null;
		        sjzxcall = (Call) sjzxservice.createCall();
		        sjzxcall.setTargetEndpointAddress( new java.net.URL(endpointURL) );
		//      call.setOperationName("getSzdacx" );//设置对应方法 (原  
	            //来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
	            //名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
	            QName opAddEntry = new QName(WebServiceConf.WEBSERVICE_NAMESPACE_ZYMZ,"dqxnxq");
	            sjzxcall.setOperationName(opAddEntry);
			    tmp =  (String)sjzxcall.invoke(new Object[] {strKey});//实际调用
				System.out.print("中央民族当前学年学期" +  tmp); //测试
				SAXReader reader = new SAXReader();
				ByteArrayInputStream stream = new ByteArrayInputStream(tmp.getBytes());
				Document doc = null;
				try {
			    doc = reader.read(stream);
				Element root = doc.getRootElement();
				
			    for (Iterator<?> i = root.elementIterator("msg"); i.hasNext();) {
					Element foo = (Element) i.next();
					String xqxn = foo.elementText("dqxnxq").toString();
					oMobileBean.setDqxn(xqxn.substring(0,xqxn.lastIndexOf("-")));
					System.out.print("中央民族当前学年" +  xqxn.substring(0,xqxn.lastIndexOf("-"))); //测试
					oMobileBean.setDqxq(xqxn.substring(xqxn.lastIndexOf("-")).replace("-", ""));
					System.out.print("中央民族当前学期" + xqxn.substring(xqxn.lastIndexOf("-")).replace("-", "")); //测试
			      }
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		    }catch (Exception e) {
				// TODO: handle exception
		    	System.out.print("中央民族当前学年学期Erro" +  e); //测试
			  }
			}
		}
		else if(status.equals("1")){
		
		//如果当前学年为空，则使用教务接口获取
		if (oMobileBean.getDqxn()== null){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(infromation.equals("0")){
				logger.error("调用登录验证获取基本信息调用教务接口"+"时间="+df.format(new Date()));
				}
			try {
			String xml="";
			JaxWsProxyFactoryBean factory1 = new JaxWsProxyFactoryBean();
			factory1.setServiceClass(IConfigurationInfo.class);
			factory1.setAddress(WebServiceConf.SERVICE_JWSERVICE_CONFIG);//接口地址
			factory1.setServiceName(new QName(WebServiceConf.WEBSERVICE_NAMESPACE_JW, "ConfigurationInfo"));
			factory1.getInInterceptors().add(new LoggingInInterceptor());
			factory1.getOutInterceptors().add(new LoggingOutInterceptor());
			IConfigurationInfo service1 = (IConfigurationInfo) factory1.create();
			
				xml = service1.configurationInfo();
				if(infromation.equals("0")){
					logger.error("调用登录验证获取基本信息JWXML"+xml);
					}
				SAXReader reader = new SAXReader();
				ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes());
				Document doc = reader.read(stream);
				Element root = doc.getRootElement();
			    for (Iterator<?> i = root.elementIterator("row"); i.hasNext();) {
					Element foo = (Element) i.next();
					String xqxn = foo.elementText("dqxnxq").toString();
					oMobileBean.setDqxn(xqxn.substring(0,xqxn.lastIndexOf("-")));
					oMobileBean.setDqxq(xqxn.substring(xqxn.lastIndexOf("-")).replace("-", ""));
			    } 
			} catch (Exception e) {
			    e.printStackTrace();
				if(infromation.equals("0")){
					logger.error("调用登录验证获取基本信息调用教务接口异常"+e+"时间="+df.format(new Date()));
					}
			}
		  }
		}
		indexlist.add(oMobileBean);
		zfxml =  XmltoString.xmlToStringNew(output, SelectItems.getReflectObjPropertyValue(indexlist, MobileBean.class,output), "MH");
		if(infromation.equals("0")){
		logger.error("调用登录验证获取基本信息"+"最终返回数据为："+zfxml);
		}
		return zfxml;
	}
	/**
	 * <p>Description: 根据业务系统对照用户获取认证用户名</p>
	 * @param xtdm
	 * @param userName
	 * @return
	 *
	 * @since 2014-5-19 上午09:31:12
	 * @author yangz
	 */
	@Override
	public String getCaUser(String xtdm, String userName,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			xtdm  			= CodeUtil.decode(xtdm, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	//	String infromation=Config.getString("mobile.infromation");
		String str=null;
		if(infromation.equals("0")){
		logger.error("调用getCaUser根据业务系统对照用户获取认证用户名："+"xtdm="+xtdm+"userName"+userName);
		}
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(ICaService.class);
		factory.setAddress(WebServiceConf.SERVICE_MHSERVICE);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		ICaService service = (ICaService) factory.create();
		
		str = service.getCaUser(xtdm, userName);
		
		if(infromation.equals("0")){
		logger.error("调用getCaUser根据业务系统对照用户获取认证用户名返回："+str);
		}
		return str;
	}
	/**
	 * <p>Description: 接口获取用户票据</p>
	 * @param userName
	 * @param password
	 * @return
	 *
	 * @since 2014-5-19 下午03:58:28
	 * @author yangz
	 */
	@Override
	public String getTGC(String userName, String password,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			password  		= CodeUtil.decode(password, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	//	String infromation=Config.getString("mobile.infromation");
		String str=null;
		if(infromation.equals("0")){
		logger.error("调用getTGC接口获取用户票据："+"userName="+userName+"password"+password);
		}
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(ICaService.class);
		factory.setAddress(WebServiceConf.SERVICE_MHSERVICE);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		ICaService service = (ICaService) factory.create();
		
		str =service.getTGC(userName, password);
		
		if(infromation.equals("0")){
		logger.error("调用getTGC接口获取用户票据返回为："+str);
		}
		return str;
	}
	/**
	 * <p>Description: 登录验证获取基本信息接口</p>
	 * @param usename  用户名
	 * @param password 密码
	 * @param xtdms    系统代码
	 * @param remoteURL 请求地址
	 * @return
	 *
	 * @since 2014-8-12 下午04:47:06
	 * @author yangz
	 */
	public String getTicket2(String userName, String passWord,
			String Xtmdlst, String remoteURL,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			passWord  		= CodeUtil.decode(passWord, apptoken);
			Xtmdlst  		= CodeUtil.decode(Xtmdlst, apptoken);
			remoteURL  		= CodeUtil.decode(remoteURL, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		String zfxml = "";
		if(infromation.equals("0")){
			logger.error("调用登录验证获取基本信息getTicket2"+"userName="+userName+"passWord="+passWord+"Xtmdlst="+Xtmdlst+"remoteURL="+remoteURL);
			}
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(ICaService.class);
		factory.setAddress(WebServiceConf.SERVICE_MHSERVICE);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		ICaService service = (ICaService) factory.create();
		
		zfxml =service.getTicket2(userName, passWord, Xtmdlst, remoteURL);
		if(infromation.equals("0")){
			logger.error("调用登录验证获取基本信息getTicket2"+"最终返回数据为："+zfxml);
			}
		return  zfxml;
	}


	@Override
	public String getyzm(String sjhm) {
//		if (!ApptokenUtils.compare(apptoken)) {
//			return "app_token_error";
//		}
//		try {
//			sjhm	 = CodeUtil.decode(sjhm, apptoken);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		if(infromation.equals("0")){
			logger.error("获取手机重置密码验证码:"+"sjhm="+sjhm);
		}
		String result = null;
		String endpointURL = WebServiceConf.WEBSERVICE_PHONEANDEMAIL_MANAGE;
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		
		try {
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			// call.setOperationName("getSzdacx" );//设置对应方法 (原
			// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
			// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
			QName opAddEntry = new QName(
					WebServiceConf.WEBSERVICE_NAMESPACE_PHONEANDEMAIL,
					"phoneYzm");
			call.setOperationName(opAddEntry);
			result = (String) call.invoke(new Object[] { sjhm, new DBEncrypt().eCode(sjhm) });
			if(infromation.equals("0")){
				logger.error("获取手机重置密码验证码结果为："+result);
				}
		} catch (Exception e) {
			if(infromation.equals("0")){
				logger.error("获取手机重置密码验证码Erro" + e);
				}
		}
		return result;
	}

	@Override
	public String retakepwd(String sjhm, String yzm,String xmm) {
//		if (!ApptokenUtils.compare(apptoken)) {
//			return "app_token_error";
//		}
//		try {
//			sjhm = CodeUtil.decode(sjhm, apptoken);
//			yzm	 = CodeUtil.decode(yzm, apptoken);
//			xmm	 = CodeUtil.decode(xmm, apptoken);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		if(infromation.equals("0")){
			logger.error("验证充值密码验证码，以及充值密码:"+"sjhm="+sjhm+"yzm="+yzm+"xmm="+xmm);
		}
		String result = null;
		String endpointURL = WebServiceConf.WEBSERVICE_PHONEANDEMAIL_MANAGE;
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		
		try {
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			// call.setOperationName("getSzdacx" );//设置对应方法 (原
			// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
			// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
			QName opAddEntry = new QName(
					WebServiceConf.WEBSERVICE_NAMESPACE_PHONEANDEMAIL,
					"findPwdByphone");
			call.setOperationName(opAddEntry);
			result = (String) call.invoke(new Object[] { sjhm,yzm,xmm, new DBEncrypt().eCode(sjhm+yzm+xmm) });
			if(infromation.equals("0")){
				logger.error("验证充值密码验证码，以及充值密码结果为："+result);
				}
		} catch (Exception e) {
			if(infromation.equals("0")){
				logger.error("验证充值密码验证码，以及充值密码Erro" + e);
				}
		}
		return result;
	}
	
	
	@Override
	public String getPhoneBindingsYzm(String sjhm, String username,
			String apptoken) {
		if (!ApptokenUtils.compare(username, apptoken)) {
			return "app_token_error";
		}
		try {
			username = CodeUtil.decode(username, apptoken);
			sjhm	 = CodeUtil.decode(sjhm, apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(infromation.equals("0")){
			logger.error("获取手机绑定验证码:"+"username="+username+"sjhm="+sjhm);
		}
		String result = null;
		String endpointURL = WebServiceConf.WEBSERVICE_PHONEANDEMAIL_MANAGE;
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		
		try {
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			// call.setOperationName("getSzdacx" );//设置对应方法 (原
			// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
			// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
			QName opAddEntry = new QName(
					WebServiceConf.WEBSERVICE_NAMESPACE_PHONEANDEMAIL,
					"getbdyzm");
			call.setOperationName(opAddEntry);
			result = (String) call.invoke(new Object[] { sjhm,username, new DBEncrypt().eCode(sjhm+username) });
			if(infromation.equals("0")){
				logger.error("获取手机绑定验证码结果为："+result);
				}
		} catch (Exception e) {
			if(infromation.equals("0")){
				logger.error("获取手机绑定验证码Erro" + e);
				}
		}
		return result;
	}
	@Override
	public String getPhoneCancelBindingsYzm(String sjhm, String apptoken) {
		if (!ApptokenUtils.compare(apptoken)) {
			return "app_token_error";
		}
		try {
			sjhm = CodeUtil.decode(sjhm, apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(infromation.equals("0")){
			logger.error("获取取消手机绑定验证码："+"sjhm="+sjhm);
		}
		String result = null;
		String endpointURL = WebServiceConf.WEBSERVICE_PHONEANDEMAIL_MANAGE;
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		
		try {
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			// call.setOperationName("getSzdacx" );//设置对应方法 (原
			// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
			// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
			QName opAddEntry = new QName(
					WebServiceConf.WEBSERVICE_NAMESPACE_PHONEANDEMAIL,
					"getcelyzm");
			call.setOperationName(opAddEntry);
			result = (String) call.invoke(new Object[] { sjhm, new DBEncrypt().eCode(sjhm) });
			if(infromation.equals("0")){
				logger.error("获取取消手机绑定验证码结果为："+result);
				}
		} catch (Exception e) {
			if(infromation.equals("0")){
				logger.error("获取取消手机绑定验证码Erro" + e);
				}
		}
		return result;
	}
	@Override
	public String bindPhone(String sjhm, String yzm, String apptoken) {
		if (!ApptokenUtils.compare(apptoken)) {
			return "app_token_error";
		}
		try {
			sjhm = CodeUtil.decode(sjhm, apptoken);
			yzm  = CodeUtil.decode(yzm, apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(infromation.equals("0")){
			logger.error("绑定手机："+"sjhm="+sjhm+"yzm="+yzm);
		}
		String result = null;
		String endpointURL = WebServiceConf.WEBSERVICE_PHONEANDEMAIL_MANAGE;
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		
		try {
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			// call.setOperationName("getSzdacx" );//设置对应方法 (原
			// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
			// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
			QName opAddEntry = new QName(
					WebServiceConf.WEBSERVICE_NAMESPACE_PHONEANDEMAIL,
					"bindsjhm");
			call.setOperationName(opAddEntry);
			result = (String) call.invoke(new Object[] { sjhm,yzm, new DBEncrypt().eCode(sjhm+yzm) });
			if(infromation.equals("0")){
				logger.error("绑定手机结果为："+result);
				}
		} catch (Exception e) {
			if(infromation.equals("0")){
				logger.error("绑定手机Erro" + e);
				}
		}
		return result;
	}
	@Override
	public String cancelBindPhone(String username, String sjhm, String yzm,
			String apptoken) {
		if (!ApptokenUtils.compare(username, apptoken)) {
			return "app_token_error";
		}
		try {
			username = CodeUtil.decode(username, apptoken);
			sjhm 	 = CodeUtil.decode(sjhm, apptoken);
			yzm 	 = CodeUtil.decode(yzm, apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(infromation.equals("0")){
			logger.error("取消绑定手机："+"username="+username+"sjhm="+sjhm+"yzm="+yzm);
		}
		String result = null;
		String endpointURL = WebServiceConf.WEBSERVICE_PHONEANDEMAIL_MANAGE;
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		
		try {
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			// call.setOperationName("getSzdacx" );//设置对应方法 (原
			// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
			// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
			QName opAddEntry = new QName(
					WebServiceConf.WEBSERVICE_NAMESPACE_PHONEANDEMAIL,
					"cancelprobyphone");
			call.setOperationName(opAddEntry);
			result = (String) call.invoke(new Object[] { username,sjhm,yzm, new DBEncrypt().eCode(username+sjhm+yzm) });
			if(infromation.equals("0")){
				logger.error("取消绑定手机结果为："+result);
				}
		} catch (Exception e) {
			if(infromation.equals("0")){
				logger.error("取消绑定手机Erro" + e);
				}
		}
		return result;
	}
	@Override
	public String getEmailBindingsYzm(String username, String email,
			String apptoken) {
		if (!ApptokenUtils.compare(username, apptoken)) {
			return "app_token_error";
		}
		try {
			username = CodeUtil.decode(username, apptoken);
			email 	 = CodeUtil.decode(email, apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(infromation.equals("0")){
			logger.error("获取邮箱绑定验证码："+"username="+username+"email="+email);
		}
		String result = null;
		String endpointURL = WebServiceConf.WEBSERVICE_PHONEANDEMAIL_MANAGE;
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		
		try {
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			// call.setOperationName("getSzdacx" );//设置对应方法 (原
			// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
			// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
			QName opAddEntry = new QName(
					WebServiceConf.WEBSERVICE_NAMESPACE_PHONEANDEMAIL,
					"addbmyx");
			call.setOperationName(opAddEntry);
			result = (String) call.invoke(new Object[] { username,email, new DBEncrypt().eCode(username+email) });
			if(infromation.equals("0")){
				logger.error("获取邮箱绑定验证码结果为："+result);
				}
		} catch (Exception e) {
			if(infromation.equals("0")){
				logger.error("获取邮箱绑定验证码Erro" + e);
				}
		}
		return result;
	}
	@Override
	public String bindEmail(String username, String activecode, String apptoken) {
		if (!ApptokenUtils.compare(username, apptoken)) {
			return "app_token_error";
		}
		try {
			username = CodeUtil.decode(username, apptoken);
			activecode 	 = CodeUtil.decode(activecode, apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(infromation.equals("0")){
			logger.error("绑定邮箱："+"username="+username+"activecode="+activecode);
		}
		String result = null;
		String endpointURL = WebServiceConf.WEBSERVICE_PHONEANDEMAIL_MANAGE;
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		
		try {
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			// call.setOperationName("getSzdacx" );//设置对应方法 (原
			// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
			// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
			QName opAddEntry = new QName(
					WebServiceConf.WEBSERVICE_NAMESPACE_PHONEANDEMAIL,
					"bindbmyx");
			call.setOperationName(opAddEntry);
			result = (String) call.invoke(new Object[] { username,activecode, new DBEncrypt().eCode(username+activecode) });
			if(infromation.equals("0")){
				logger.error("绑定邮箱结果为："+result);
				}
		} catch (Exception e) {
			if(infromation.equals("0")){
				logger.error("绑定邮箱Erro" + e);
				}
		}
		return result;
	}
	@Override
	public String cancelBindEmail(String email, String radomnumber, String apptoken) {
		if (!ApptokenUtils.compare(apptoken)) {
			return "app_token_error";
		}
		try {
			email 	 = CodeUtil.decode(email, apptoken);
			radomnumber = CodeUtil.decode(radomnumber, apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(infromation.equals("0")){
			logger.error("取消邮箱绑定："+"radomnumber="+radomnumber+"email="+email);
		}
		String result = null;
		String endpointURL = WebServiceConf.WEBSERVICE_PHONEANDEMAIL_MANAGE;
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		
		try {
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			// call.setOperationName("getSzdacx" );//设置对应方法 (原
			// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
			// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
			QName opAddEntry = new QName(
					WebServiceConf.WEBSERVICE_NAMESPACE_PHONEANDEMAIL,
					"cancelprobymail");
			call.setOperationName(opAddEntry);
			result = (String) call.invoke(new Object[] { email,radomnumber, new DBEncrypt().eCode(email+radomnumber) });
			if(infromation.equals("0")){
				logger.error("取消邮箱绑定结果为："+result);
				}
		} catch (Exception e) {
			if(infromation.equals("0")){
				logger.error("取消邮箱绑定Erro" + e);
				}
		}
		return result;
	}
	
	@Override
	public String checkBindPhoneAndEmail(String username, String apptoken) {
		if (!ApptokenUtils.compare(username, apptoken)) {
			return "app_token_error";
		}
		try {
			username = CodeUtil.decode(username, apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(infromation.equals("0")){
			logger.error("获取校验是否绑定手机号和邮箱："+"username="+username);
		}
		String result = null;
		String endpointURL = WebServiceConf.WEBSERVICE_PHONEANDEMAIL_MANAGE;
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		
		try {
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			// call.setOperationName("getSzdacx" );//设置对应方法 (原
			// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
			// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
			QName opAddEntry = new QName(
					WebServiceConf.WEBSERVICE_NAMESPACE_PHONEANDEMAIL,
					"sfbdsjyx");
			call.setOperationName(opAddEntry);
			result = (String) call.invoke(new Object[] { username, new DBEncrypt().eCode(username) });
			if(infromation.equals("0")){
				logger.error("获取校验是否绑定手机号和邮箱结果为："+result);
				}
		} catch (Exception e) {
			if(infromation.equals("0")){
				logger.error("获取校验是否绑定手机号和邮箱Erro" + e);
				}
		}
		return result;
	}
	
	/**
	 * 检查是否绑定手机和邮箱
	 * @param username 用户名
	 * @return
	 */
	public String checkBindPhoneAndEmailWithoutApptoken(String username){
		if(infromation.equals("0")){
			logger.error("获取校验是否绑定手机号和邮箱："+"username="+username);
		}
		String result = null;
		String endpointURL = WebServiceConf.WEBSERVICE_PHONEANDEMAIL_MANAGE;
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		
		try {
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			// call.setOperationName("getSzdacx" );//设置对应方法 (原
			// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
			// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
			QName opAddEntry = new QName(
					WebServiceConf.WEBSERVICE_NAMESPACE_PHONEANDEMAIL,
					"sfbdsjyx");
			call.setOperationName(opAddEntry);
			result = (String) call.invoke(new Object[] { username, new DBEncrypt().eCode(username) });
			if(infromation.equals("0")){
				logger.error("获取校验是否绑定手机号和邮箱结果为："+result);
				}
		} catch (Exception e) {
			if(infromation.equals("0")){
				logger.error("获取校验是否绑定手机号和邮箱Erro" + e);
				}
		}
		return result;
	}
	
	
	@Override
	public String getCATGC(String username, String password, String apptoken) {
		if (!ApptokenUtils.compare(username, apptoken)) {
			return "app_token_error";
		}
		try {
			username = CodeUtil.decode(username, apptoken);
			password = CodeUtil.decode(password, apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = null;
		String endpointURL = WebServiceConf.WEBSERVICE_CA_MANAGE;
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		
		try {
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			// call.setOperationName("getSzdacx" );//设置对应方法 (原
			// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
			// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
			QName opAddEntry = new QName(
					WebServiceConf.WEBSERVICE_NAMESPACE_MANAGE,
					"getTGC");
			call.setOperationName(opAddEntry);
			result = (String) call.invoke(new Object[] { username, password });
			System.out.print("获取TGC认证票据：" + result); // 测试
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("TGC认证票据Erro" + e); // 测试
		}
		return result;
	}
	@Override
	public String cancelEmailYzm(String email, String apptoken) {
		if (!ApptokenUtils.compare(apptoken)) {
			return "app_token_error";
		}
		try {
			email 	 = CodeUtil.decode(email, apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(infromation.equals("0")){
			logger.error("取消邮箱绑定验证码："+"email="+email);
		}
		String result = null;
		String endpointURL = WebServiceConf.WEBSERVICE_PHONEANDEMAIL_MANAGE;
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		
		try {
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			// call.setOperationName("getSzdacx" );//设置对应方法 (原
			// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
			// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
			QName opAddEntry = new QName(
					WebServiceConf.WEBSERVICE_NAMESPACE_PHONEANDEMAIL,
					"cancelEmailYzm");
			call.setOperationName(opAddEntry);
			result = (String) call.invoke(new Object[] { email, new DBEncrypt().eCode(email) });
			if(infromation.equals("0")){
				logger.error("取消邮箱绑定验证码结果为："+result);
				}
		} catch (Exception e) {
			if(infromation.equals("0")){
				logger.error("取消邮箱绑定验证码Erro" + e);
				}
		}
		return result;
	}
	@Override
	public String findpwdbymail(String email) {
//		if (!ApptokenUtils.compare(apptoken)) {
//			return "app_token_error";
//		}
//		try {
//			email 	 = CodeUtil.decode(email, apptoken);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		if(infromation.equals("0")){
			logger.error("通过邮箱找回密码："+"email="+email);
		}
		String result = null;
		String endpointURL = WebServiceConf.WEBSERVICE_PHONEANDEMAIL_MANAGE;
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		
		try {
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			// call.setOperationName("getSzdacx" );//设置对应方法 (原
			// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
			// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
			QName opAddEntry = new QName(
					WebServiceConf.WEBSERVICE_NAMESPACE_PHONEANDEMAIL,
					"findpwdbymail");
			call.setOperationName(opAddEntry);
			result = (String) call.invoke(new Object[] { email, new DBEncrypt().eCode(email) });
			if(infromation.equals("0")){
				logger.error("通过邮箱找回密码结果为："+result);
				}
		} catch (Exception e) {
			if(infromation.equals("0")){
				logger.error("通过邮箱找回密码Erro" + e);
				}
		}
		return result;
	}
	/* (non-Javadoc)
	 * @see com.mh.service.IMobileManageXMLService#getSSOByMobile(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String getSSOByMobile(String username, String password,
			String ywxtUrl, String apptoken) {
		if (!ApptokenUtils.compare(username, apptoken)) {
			return "app_token_error";
		}
		try {
			username = CodeUtil.decode(username, apptoken);
			password = CodeUtil.decode(password, apptoken);
			ywxtUrl  = CodeUtil.decode(ywxtUrl, apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = null;
		String endpointURL = WebServiceConf.WEBSERVICE_CA_MANAGE;
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		
		try {
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			// call.setOperationName("getSzdacx" );//设置对应方法 (原
			// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
			// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
			QName opAddEntry = new QName(
					WebServiceConf.WEBSERVICE_NAMESPACE_MANAGE,
					"ssoByMobile");
			call.setOperationName(opAddEntry);
			result = (String) call.invoke(new Object[] { username, password,ywxtUrl });
			System.out.print("获取SSOByMobile：" + result); // 测试
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("SSOByMobile认证票据Erro" + e); // 测试
			e.fillInStackTrace();
		}
		//result = result.replaceAll("&amp;", "&");
		return result;
	}
	/* (non-Javadoc)
	 * @see com.mh.service.IMobileManageXMLService#ssoLogin(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String ssoLogin(String username, String password, String yhlx,
			String signalXtbm, String signalUrl, String apptoken) {
		if (!ApptokenUtils.compare(username, apptoken)) {
			return "app_token_error";
		}
		try {
			username = CodeUtil.decode(username, apptoken);
			password = CodeUtil.decode(password, apptoken);
			yhlx  	 = CodeUtil.decode(yhlx, apptoken);
			signalXtbm   = CodeUtil.decode(signalXtbm, apptoken);
			signalUrl  	 = CodeUtil.decode(signalUrl, apptoken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = null;
		String url = WebServiceConf.WEBSERVICE_MANAGE+"/zfca/ssoLogin";
		try {
			String code = AESEncrypt.Encrypt(username+"|"+password+"|"+signalXtbm+"|"+yhlx+"|"+signalUrl, 
								AESEncrypt.sKey,
								AESEncrypt.ivParameter
							);
			code = java.net.URLEncoder.encode(code, "utf-8");
			if(url.startsWith("https")){
				result = HttpRequest.httpsRequest(url+"?code="+code, "GET", null);
			}else{
				result = HttpRequest.sendGet(url, "code="+code);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	@Override
	public String ssoLogin1(String username, String password, String yhlx,
			String app_id, String gotoUrl, String apptoken) {
		String result = "";
		String url = BaseHolder.getPropertiesValue("ssologin");
		try {
			username = CodeUtil.decode(username, apptoken);
			password = CodeUtil.decode(password, apptoken);
			app_id = CodeUtil.decode(app_id, apptoken);
			yhlx = CodeUtil.decode(yhlx, apptoken);
			gotoUrl = CodeUtil.decode(gotoUrl, apptoken);
			String code = AESEncrypt.Encrypt(username+"|"+password+"|"+app_id+"|"+yhlx+"|"+gotoUrl, 
					"8218DE69DB1F364E",
					"0392039203920300"
				);
			code = URLEncoder.encode(code, "utf-8");
			result = HttpRequest.sendPost(url, "code=" + code);
			System.out.println(result);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("ssoLogin   err：");
			e.printStackTrace();
		}
		return result;
	}
	
	/*public static void main(String[] args) {
		String result = null;
		String endpointURL = "http://10.71.32.112:8080/zfca/axis/MobileManage?wsdl";
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		
		try {
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			// call.setOperationName("getSzdacx" );//设置对应方法 (原
			// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
			// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
			QName opAddEntry = new QName(
					"http://pubService.webServices.zfca.zfsoft.com",
					"getTGC");
			call.setOperationName(opAddEntry);
			result = (String) call.invoke(new Object[] { "admin", "123123." });
			System.out.print("获取TGC认证票据：" + result); // 测试
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("TGC认证票据Erro" + e); // 测试
		}
	}*/
	
	
	
}
