package com.zhInterface.oa.service.imp;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.common.Config;
import com.common.JavaXML;
import com.common.WebServiceConf;
import com.common.uai;
import com.jw.org.tempuri.INoticePost;
import com.newmobile.login.CXFService.IWSSerServicePortType;
import com.util.MiddleWareUtil;
import com.util.WebServiceUtil;
import com.xg.lx.CXFService.ILxService;
import com.xg.xg.CXFService.IxgService;
import com.xg.yx.CXFService.IYxService;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zhInterface.oa.service.IMobileInterfaceXMLService;


/**
 * <p>Description: 整合相同类型的接口</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2015-3-9 上午11:25:23
 * @author yangz
 * @version 1.0
 */
@Service
@Component(value = "mobileInterfaceXMLService")
public class MobileInterfaceXMLService implements IMobileInterfaceXMLService {
	private static Logger logger = Logger
			.getLogger(MobileInterfaceXMLService.class);
	private final String infromation = Config.getString("mobile.infromation");

	/**
	 * <p>
	 * Description: 通知公告列表
	 * </p>
	 * 
	 * @param userName
	 *            用户名
	 * @param role
	 *            角色
	 * @param type
	 *            通知公告子类型
	 * @param start
	 *            当前页，从1开始
	 * @param size
	 *            获取记录条数
	 * @param sign
	 *            秘钥(移动端和中间件的秘钥)
	 * @param notictype
	 *            通知公告类型
	 * @return
	 * 
	 * @since 2015-2-2 上午10:06:49
	 * @author yangz
	 */
	@Override
	public String getNoticeList(String userName, String role, String type,
			String start, String size, String sign, String toptype,String bak,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  	= CodeUtil.decode(userName, apptoken);
			role  		= CodeUtil.decode(role, apptoken);
			type  		= CodeUtil.decode(type, apptoken);
			start  		= CodeUtil.decode(start, apptoken);
			size  		= CodeUtil.decode(size, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);
			toptype  	= CodeUtil.decode(toptype, apptoken);
			bak  		= CodeUtil.decode(bak, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str = null;
		HashMap numList = null;
		if(!bak.equals("") && !bak.equals(null)){
			try {
				 numList =uai.parse(bak);
				
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (sign.equals(MiddleWareUtil.getSign(userName))) {
			if (toptype.equals("1")) {
				// 调用OA的通知公告列表接口
				
	            if(!bak.equals("") && !bak.equals(null)){
	            	Iterator iterator = numList.keySet().iterator();                
		            while (iterator.hasNext()) {    
		             Object key = iterator.next();
		              userName = (String) numList.get("007");
		            }   
	    		}
				
				if(type.length()>=3){
					type="";
				}
				String OASign = MiddleWareUtil.getSign(userName);
				str = WebServiceUtil.createServiceOa().getNoticeList(userName,
						type, Integer.valueOf(start), Integer.valueOf(size),
						OASign);
				if (infromation.equals("0")) {
					logger.error("调用getNoticeList通知公告列表获取：" +"\n"+ "yhm=" + userName +"\n"
							+ "type=" + type +"\n"+ "start=" + start +"\n"+ "size="
							+ size +"\n"+ "sign=" + sign);
					logger.error("调用getNoticeList通知公告列表获取返回为：" + str);
				}
				return str;
			} else if (toptype.equals("2")) {
				// 调用教务的通知公告列表接口
				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
				factory.setServiceClass(IWSSerServicePortType.class);
				factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
				factory.getInInterceptors().add(new LoggingInInterceptor());
				factory.getOutInterceptors().add(new LoggingOutInterceptor());

				IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
				
				str =service.getNoticeList(userName, type, start, size);
				return str;
			    /*if(!bak.equals("") && !bak.equals(null)){
			    	Iterator iterator = numList.keySet().iterator();                
		            while (iterator.hasNext()) {    
		             Object key = iterator.next();
		              userName = (String) numList.get("015");
		            }    
	    		}
				
			    String parameterList = userName + "&" + start + "&" + size;
				String JWSign = MiddleWareUtil.getJWSign(parameterList);
				INoticePost service = (INoticePost) WebServiceUtil.createFactoryJw(
						WebServiceConf.SERVICE_JWSERVICE_NOTICE, INoticePost.class,
						"GetPostList").create();

				str = service.getPostList(parameterList, role, "0", JWSign);
				str =str.replace("sfzd", "istop").replace("是", "1");

				if (infromation.equals("0")) {
					logger.error("调用GetPostList通知公告列表获取：" +"\n"+ "userName="
							+ userName +"\n"+ "role=" + role +"\n"+ "count=" + size+"\n"
							+ "strKey=" + sign);
					logger.error("调用GetPostList通知公告列表获取返回：" + str);
				}

				return str;*/
			} else if (toptype.equals("3")) {
				// 调用学工的通知公告列表接口
				
				 if(!bak.equals("") && !bak.equals(null)){
					 Iterator iterator = numList.keySet().iterator();                
			            while (iterator.hasNext()) {    
			             Object key = iterator.next();
			              userName = (String) numList.get("011");
			            }   
		    		}
				 
				
				if (role.equals("JS")) {
					role = "tea";
				} else if (role.equals("XS")) {
					role = "stu";
				}
				int num1 = Integer.parseInt(size);
				int pageIndex1 = Integer.parseInt(start);
				String XGSign = MiddleWareUtil.getXGSign(userName);
				if (infromation.equals("0")) {
					logger.error("调用getLastNoticeList获取学工通知公告：" +"\n"+ "userName="
							+ userName +"\n"+ "yhlx=" + role +"\n"+ "type=" + type
							+"\n"+ "num=" + size +"\n"+ "sign=" + sign +"\n"+ "pageIndex"
							+ start);
				}
				// 调用WebService
				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
				factory.setServiceClass(IxgService.class);
				factory.setAddress(WebServiceConf.SERVICE_XGSERVICE);// 接口地址
				factory.getInInterceptors().add(new LoggingInInterceptor());
				factory.getOutInterceptors().add(new LoggingOutInterceptor());
				IxgService service = (IxgService) factory.create();

				str = service.getLastNoticeList(userName, role, type, num1,
						XGSign, pageIndex1);

				if (infromation.equals("0")) {
					logger.error("调用getLastNoticeList获取学工通知公告：" + str);
				}
				return str;
			} else if (toptype.equals("4")) {
				// 调用离校的通知公告列表接口
				
				
				 if(!bak.equals("") && !bak.equals(null)){
						Iterator iterator = numList.keySet().iterator();                
			            while (iterator.hasNext()) {    
			             Object key = iterator.next();
			              userName = (String) numList.get("012");
			            }   
		    		}
			 
				
				if (role.equals("JS")) {
					role = "tea";
				} else if (role.equals("XS")) {
					role = "stu";
				}
				String LXSign = MiddleWareUtil.getXGSign(userName);
				int num1 = Integer.parseInt(size);
				int pageIndex1 = Integer.parseInt(start);
				if (infromation.equals("0")) {
					logger.error("调用getLxtzggList获取离校通知公告：" + "userName="
							+ userName +"\n"+ "yhlx=" + role +"\n"+ "num=" + size+"\n"
							+ "sign=" + sign +"\n"+ "pageIndex" + start);
				}
				// 调用WebService
				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
				factory.setServiceClass(ILxService.class);
				factory.setAddress(WebServiceConf.SERVICE_LXSERVICE);// 接口地址
				factory.getInInterceptors().add(new LoggingInInterceptor());
				factory.getOutInterceptors().add(new LoggingOutInterceptor());
				ILxService service = (ILxService) factory.create();

				str = service.getLxTzggList(role, num1, userName, LXSign,
						pageIndex1);

				if (infromation.equals("0")) {
					logger.error("调用getLxtzggList获取离校通知公告：" + str);
				}
				return str;
			} else if (toptype.equals("5")) {
				// 调用迎新的通知公告列表接口
				
				 if(!bak.equals("") && !bak.equals(null)){
						Iterator iterator = numList.keySet().iterator();                
			            while (iterator.hasNext()) {    
			             Object key = iterator.next();
			              userName = (String) numList.get("009");
			            }  
		    		}
			
				
				int num1 = Integer.parseInt(size);
				String YXGSign = MiddleWareUtil.getXGSign(userName);
				if (infromation.equals("0")) {
					logger.error("调用getYxTzggTeaList 获取迎新通知公告：" + "userName="
							+ userName +"\n"+ "num=" + size +"\n"+ "sign=" + sign);
				}
				// 调用WebService
				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
				factory.setServiceClass(IYxService.class);
				factory.setAddress(WebServiceConf.SERVICE_YXSERVICE);// 接口地址
				factory.getInInterceptors().add(new LoggingInInterceptor());
				factory.getOutInterceptors().add(new LoggingOutInterceptor());
				IYxService service = (IYxService) factory.create();

				str = service.getYxTzggTeaList(num1, userName, YXGSign);

				if (infromation.equals("0")) {
					logger.error("调用getYxTzggTeaList 获取迎新通知公告：" + str);
				}
				return str;
			}
			else if(toptype.equals("6")){
				//调用OA会议通知公告列表
				
				 if(!bak.equals("") && !bak.equals(null)){
						Iterator iterator = numList.keySet().iterator();                
			            while (iterator.hasNext()) {    
			             Object key = iterator.next();
			              userName = (String) numList.get("007");
			            }  
		    		}
			
				
				int start1=Integer.valueOf(start);
				int size1=Integer.valueOf(size);
				String HYGSign =MiddleWareUtil.getSign(userName);
				str=WebServiceUtil.createServiceOa().getMeetNoticeList(userName, start1, size1, HYGSign);
				if(infromation.equals("0")){
					logger.error("调用getMeetNoticeList会议通知列表方法："+"\n"+"yhm="+userName+"\n"+"sign="+HYGSign);
					logger.error("调用getMeetNoticeList会议通知列表返回为："+str);
				}
				return str;
			}
		} else {
			String msg="验证失败";
			String code="101";
			str=JavaXML.BuildXMLReturnErro(msg,code);
			return str;
		}

		return str;
	}

	/**
	 * <p>Description: 通知公告详情</p>
	 * @param userName 用户名
	 * @param id       通知公告Id
	 * @param notictype公告类型
	 * @param count    记录数
	 * @param sign     秘钥
	 * @return
	 *
	 * @since 2015-2-5 上午09:11:01
	 * @author yangz
	 */
	@Override
	public String getNoticeInfo(String userName, String noticeId,String notictype, String type, String count,
			String sign,String bak,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  	= CodeUtil.decode(userName, apptoken);
			noticeId  	= CodeUtil.decode(noticeId, apptoken);
			notictype  	= CodeUtil.decode(notictype, apptoken);
			type  		= CodeUtil.decode(type, apptoken);
			count  		= CodeUtil.decode(count, apptoken);
			sign  		= CodeUtil.decode(sign, apptoken);
			bak  		= CodeUtil.decode(bak, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str = null;
		HashMap numList = null;
		if(!bak.equals("") && !bak.equals(null)){
			try {
				 numList =uai.parse(bak);
				
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (sign.equals(MiddleWareUtil.getSign(userName))) {
			if(notictype.equals("1")){
				//调用OA的通知公告详情
				if(!bak.equals("") && !bak.equals(null)){
					Iterator iterator = numList.keySet().iterator();                
		            while (iterator.hasNext()) {    
		             Object key = iterator.next();
		              userName = (String) numList.get("007");
		            } 
				}
			
				
				String OASign = MiddleWareUtil.getSign(userName);
				str=WebServiceUtil.createServiceOa().getNoticeInfo(userName, noticeId, OASign);
				if(infromation.equals("0")){
					logger.error("调用getConferenceInfo通知公告详情："+"\n"+"yhm="+userName+"\n"+"id="+noticeId+"\n"+"sign="+OASign);
					logger.error("调用getConferenceInfo通知公告详情返回为："+str);
				}
				return str;
			}
			else if(notictype.equals("2")){
				//调用教务的通知公告详情
				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
				factory.setServiceClass(IWSSerServicePortType.class);
				factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
				factory.getInInterceptors().add(new LoggingInInterceptor());
				factory.getOutInterceptors().add(new LoggingOutInterceptor());

				IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
				
				str =service.getNoticeInfo(noticeId);
				
				/*if(!bak.equals("") && !bak.equals(null)){
					Iterator iterator = numList.keySet().iterator();                
		            while (iterator.hasNext()) {    
		             Object key = iterator.next();
		              userName = (String) numList.get("015");
		            } 
				}
			
				
				String JWSign = MiddleWareUtil.getJWSign(noticeId);
				INoticePost service = (INoticePost) WebServiceUtil.createFactoryJw(
						WebServiceConf.SERVICE_JWSERVICE_NOTICE, INoticePost.class,
						"PostInfoSearch").create();

				str = service.postInfoSearch(noticeId, count, JWSign);

				if (infromation.equals("0")) {
					logger.error("调用PostInfoSearch通知公告详情：" +"\n"+ "noticeId=" + noticeId
							+"\n"+ "count=" + count +"\n"+ "strKey=" + JWSign);
					logger.error("调用PostInfoSearch通知公告详情返回：" + str);
				}*/
				return str;
			}
			else if(notictype.equals("3")){
				//调用学工的通知公告详情
				
				if(!bak.equals("") && !bak.equals(null)){
					Iterator iterator = numList.keySet().iterator();                
		            while (iterator.hasNext()) {    
		             Object key = iterator.next();
		              userName = (String) numList.get("011");
		            }   
				}
			
				
				String XGSign = MiddleWareUtil.getXGSign(userName);
				if(infromation.equals("0")){
					logger.error("调用getTzggDetail获取学工通知公告详情："+"\n"+"id="+noticeId+"\n"+"userName="+userName+"\n"+"sign="+XGSign);
					}
				// 调用WebService
				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
				factory.setServiceClass(IxgService.class);
				factory.setAddress(WebServiceConf.SERVICE_XGSERVICE);//接口地址
				factory.getInInterceptors().add(new LoggingInInterceptor());
				factory.getOutInterceptors().add(new LoggingOutInterceptor());
				IxgService service = (IxgService) factory.create();
				
				str =service.getTzggDetail(noticeId, userName, XGSign);
				
				if(infromation.equals("0")){
				logger.error("调用getTzggDetail获取学工通知公告详情："+str);
				}
				return str;
				
			}
			else if(notictype.equals("4")){
				// 调用离校的通知公告详情
				
				
				if(!bak.equals("") && !bak.equals(null)){
					Iterator iterator = numList.keySet().iterator();                
		            while (iterator.hasNext()) {    
		             Object key = iterator.next();
		              userName = (String) numList.get("012");
		            } 
				}
				
				String LXSign = MiddleWareUtil.getXGSign(userName);
				if(infromation.equals("0")){
					logger.error("调用getTzggDetail获取离校通知公告详情："+"\n"+"noticeId="+noticeId+"\n"+"userName="+userName+"\n"+"sign="+LXSign);
					}
				// 调用WebService
				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
				factory.setServiceClass(ILxService.class);
				factory.setAddress(WebServiceConf.SERVICE_LXSERVICE);//接口地址
				factory.getInInterceptors().add(new LoggingInInterceptor());
				factory.getOutInterceptors().add(new LoggingOutInterceptor());
				ILxService service = (ILxService) factory.create();
				
				str =service.getTzggDetail(noticeId, userName, LXSign);
				
				if(infromation.equals("0")){
				logger.error("调用getTzggDetail获取离校通知公告详情："+str);
				}
				return str;
			}
			else if(notictype.equals("5")){
				// 调用迎新的通知公告详情
				
				if(!bak.equals("") && !bak.equals(null)){
					Iterator iterator = numList.keySet().iterator();                
		            while (iterator.hasNext()) {    
		             Object key = iterator.next();
		              userName = (String) numList.get("009");
		            } 
				}
				
				
				String YXGSign = MiddleWareUtil.getXGSign(userName);
				if(infromation.equals("0")){
					logger.error("调用getTzggDetail获取迎新查询通知公告详情："+"\n"+"userName="+userName+"\n"+"id="+noticeId+"\n"
							+"sign="+YXGSign);
					}
				// 调用WebService
				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
				factory.setServiceClass(IYxService.class);
				factory.setAddress(WebServiceConf.SERVICE_YXSERVICE);//接口地址
				factory.getInInterceptors().add(new LoggingInInterceptor());
				factory.getOutInterceptors().add(new LoggingOutInterceptor());
				IYxService service = ( IYxService) factory.create();
				
				str =service.getTzggDetail(noticeId, userName, YXGSign);
				
				if(infromation.equals("0")){
				logger.error("调用getTzggDetail获取迎新查询通知公告详情："+str);
				}
				return str;
			}
			else if(notictype.equals("6")){
				//调用OA会议通知公告列表
				if(!bak.equals("") && !bak.equals(null)){
					Iterator iterator = numList.keySet().iterator();                
		            while (iterator.hasNext()) {    
		             Object key = iterator.next();
		              userName = (String) numList.get("007");
		            } 
				}
				
				String HYGSign =MiddleWareUtil.getSign(userName);
				str=WebServiceUtil.createServiceOa().getMeetNoticeInfo(userName, noticeId, HYGSign);
				if(infromation.equals("0")){
					logger.error("调用getMeetNoticeInfo会议通知详情："+"\n"+"yhm="+userName+"\n"+"sign="+HYGSign+"\n"+"noticeId="+noticeId);
					logger.error("调用getMeetNoticeInfo会议通知详情为："+str);
				}
				return str;
			}
		}
		else{
			String msg="验证失败";
			String code="101";
			str=JavaXML.BuildXMLReturnErro(msg,code);
			return str;
		}
		return str;
	}
	
	public static void main(String[] args) {
		String OASign = MiddleWareUtil.getSign("999");
		System.out.println(OASign);
	}

}
