package com.newBackMh.noticelisttype.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.common.Config;
import com.common.JavaXML;
import com.common.WebServiceConf;
import com.common.XMLParser;
import com.common.backMhConfig;
import com.newBackMh.noticelisttype.service.IBackMhNoticeListTypeXMLService;
import com.newmobile.login.CXFService.IWSSerServicePortType;
import com.util.MiddleWareUtil;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.service.svcinterface.ILoginService;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;

/**
 * <p>
 * Description: 后台配置实现方法
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company: zfsoft.com
 * </p>
 * 
 * @since 2015-2-15 上午10:42:39
 * @author yangz
 * @version 1.0
 */
@Service
@Component(value = "backMhNoticeListTypeXMLService")
public class BackMhNoticeListTypeXMLService implements
		IBackMhNoticeListTypeXMLService {
	private static Logger logger = Logger
			.getLogger(BackMhNoticeListTypeXMLService.class);
	private final String infromation = Config.getString("mobile.infromation");

	/**
	 * <p>
	 * Description: 通知公告父类别
	 * </p>
	 * 
	 * @param userName
	 *            用户名
	 * @param role
	 *            角色
	 * @param bak
	 *            自定义字段
	 * @param sign
	 *            秘钥
	 * @return
	 * 
	 * @since 2015-3-6 下午02:55:23
	 * @author yangz
	 */
	@Override
	public String getMobileBackMhNewNoticeListType(String userName,
			String role, String bak, String sign,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName = CodeUtil.decode(userName, apptoken);
			role 	 = CodeUtil.decode(role, apptoken);
			bak 	 = CodeUtil.decode(bak, apptoken);
			sign 	 = CodeUtil.decode(sign, apptoken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		String str = null;
		if (infromation.equals("0")) {
			logger.error("调用获取门户后台选项卡名称为：" + "userName="+userName+"role="+role+"apptoken="+apptoken);
		}
		if (sign.equals(MiddleWareUtil.getSign(userName))) {
			if (role.equals("JS")) {

				String TabName = backMhConfig
						.getString("TeaBackMhNoticeListTypeName");
				String TabType = backMhConfig
						.getString("TeaBackMhNoticeListType");
				String TabStauts = backMhConfig
						.getString("TeaBackMhNoticeListStatus");

				// String NoticeType =
				// backMhConfig.getString("backMhNoticeListName");
				String[] tabmane = TabName.split(";");
				String[] tabtype = TabType.split(";");
				String[] tabstatus = TabStauts.split(";");
				// String[] noticetype = NoticeType.split(";");
				List tabmanelist = Arrays.asList(tabmane);
				List tabtypelist = Arrays.asList(tabtype);
				List tabstatuslist = Arrays.asList(tabstatus);
				// List tabnoticetype = Arrays.asList(noticetype);
				// List list = XMLParser.ReadXMl();
				str = JavaXML.BuildXMLForBackMhNewNoticeList(tabmanelist,
						tabtypelist, tabstatuslist);
				if (infromation.equals("0")) {
					logger.error("调用获取门户后台选项卡名称为：" + str);
				}
				return str;
			} else if (role.equals("XS")) {
				String TabName = backMhConfig
						.getString("StdBackMhNoticeListTypeName");
				String TabType = backMhConfig
						.getString("StdBackMhNoticeListType");
				// String NoticeType =
				// backMhConfig.getString("backMhNoticeListName");
				String[] tabmane = TabName.split(";");
				String[] tabtype = TabType.split(";");
				// String[] noticetype = NoticeType.split(";");
				List tabmanelist = Arrays.asList(tabmane);
				List tabtypelist = Arrays.asList(tabtype);
				// List tabnoticetype = Arrays.asList(noticetype);
				// List list = XMLParser.ReadXMl();
				str = JavaXML.BuildXMLDocBack(tabmanelist, tabtypelist);
				if (infromation.equals("0")) {
					logger.error("调用获取门户后台选项卡名称为：" + str);
				}
				return str;
			} else if (role.equals("LD")) {
				String TabName = backMhConfig
						.getString("StdBackMhNoticeListTypeName");
				String TabType = backMhConfig
						.getString("StdBackMhNoticeListType");
				// String NoticeType =
				// backMhConfig.getString("backMhNoticeListName");
				String[] tabmane = TabName.split(";");
				String[] tabtype = TabType.split(";");
				// String[] noticetype = NoticeType.split(";");
				List tabmanelist = Arrays.asList(tabmane);
				List tabtypelist = Arrays.asList(tabtype);
				// List tabnoticetype = Arrays.asList(noticetype);
				// List list = XMLParser.ReadXMl();
				str = JavaXML.BuildXMLDocBack(tabmanelist, tabtypelist);
				if (infromation.equals("0")) {
					logger.error("调用获取门户后台选项卡名称为：" + str);
				}
			}

		} else {
			String msg = "验证失败";
			String code = "101";
			str = JavaXML.BuildXMLReturnErro(msg, code);
			return str;
		}
		return str;
	}

	/**
	 * <p>
	 * Description: 通知公告TAB列表子类型
	 * </p>
	 * 
	 * @param userName
	 *            用户名
	 * @param role
	 *            角色
	 * @param parentTypeId
	 *            父类型id
	 * @param bak
	 *            自定义字段
	 * @param sign
	 *            秘钥
	 * @return
	 * 
	 * @since 2015-3-6 下午02:54:30
	 * @author yangz
	 */
	@Override
	public String getNoticeListNewTableType(String userName, String role,
			String parentTypeId, String bak, String sign,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			role 	 		= CodeUtil.decode(role, apptoken);
			parentTypeId 	= CodeUtil.decode(parentTypeId, apptoken);
			bak 	 		= CodeUtil.decode(bak, apptoken);
			sign 	 		= CodeUtil.decode(sign, apptoken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String str = null;
		
		String isXiBei = Config.getString("isxibei");
		if(!StringUtil.isEmpty(isXiBei) && isXiBei.equals("yes")){
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(IWSSerServicePortType.class);
			factory.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);//接口地址
			factory.getInInterceptors().add(new LoggingInInterceptor());
			factory.getOutInterceptors().add(new LoggingOutInterceptor());

			IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
			
			str =service.getNoticeListNewTableType();
			if (infromation.equals("0")) {
				logger.error("调用获取门户后台选项卡名称为：" + str+"apptoken="+apptoken);
			}
			return str;
		}else{
			if (sign.equals(MiddleWareUtil.getSign(userName))) {
				if (parentTypeId.equals("1")) {
					// 获取OA通知公告的子类型
					String TabName = backMhConfig
							.getString("backMhNoticeTABOAListTypeName");
					String TabType = backMhConfig
							.getString("backMhNoticeTABOAType");
					String[] tabmane = TabName.split(";");
					String[] tabtype = TabType.split(";");
					List tabmanelist = Arrays.asList(tabmane);
					List tabtypelist = Arrays.asList(tabtype);
					String label = "dpm_oa";
					// List list = XMLParser.ReadXMl();
					str = JavaXML.BuildXMLForBackMhNoticeList(tabmanelist,
							tabtypelist, label);
					if (infromation.equals("0")) {
						logger.error("调用获取门户后台选项卡名称为：" + str+"apptoken="+apptoken);
					}
					return str;
				} else if (parentTypeId.equals("2")) {
					// 获取教务通知公告的子类型
					String TabName = backMhConfig
							.getString("backMhNoticeTABJWListTypeName");
					String TabType = backMhConfig
							.getString("backMhNoticeTABJWType");
					String[] tabmane = TabName.split(";");
					String[] tabtype = TabType.split(";");
					List tabmanelist = Arrays.asList(tabmane);
					List tabtypelist = Arrays.asList(tabtype);
					String label = "dpm_jw";
					// List list = XMLParser.ReadXMl();
					str = JavaXML.BuildXMLForBackMhNoticeList(tabmanelist,
							tabtypelist, label);
					if (infromation.equals("0")) {
						logger.error("调用获取门户后台选项卡名称为：" + str+"apptoken="+apptoken);
					}
					return str;
				} else if (parentTypeId.equals("3")) {
					// 获取学工通知公告的子类型
					String TabName = backMhConfig
							.getString("backMhNoticeTABXGListTypeName");
					String TabType = backMhConfig
							.getString("backMhNoticeTABXGType");
					String[] tabmane = TabName.split(";");
					String[] tabtype = TabType.split(";");
					String label = "dpm_xg";
					List tabmanelist = Arrays.asList(tabmane);
					List tabtypelist = Arrays.asList(tabtype);
					// List list = XMLParser.ReadXMl();
					str = JavaXML.BuildXMLForBackMhNoticeList(tabmanelist,
							tabtypelist, label);
					if (infromation.equals("0")) {
						logger.error("调用获取门户后台选项卡名称为：" + str+"apptoken="+apptoken);
					}
					return str;
				} else if (parentTypeId.equals("4")) {
					// 获取离校通知公告的子类型
					String TabName = backMhConfig
							.getString("backMhNoticeTABLXListTypeName");
					String TabType = backMhConfig
							.getString("backMhNoticeTABLXType");
					String[] tabmane = TabName.split(";");
					String[] tabtype = TabType.split(";");
					String label = "dpm_lx";
					List tabmanelist = Arrays.asList(tabmane);
					List tabtypelist = Arrays.asList(tabtype);
					// List list = XMLParser.ReadXMl();
					str = JavaXML.BuildXMLForBackMhNoticeList(tabmanelist,
							tabtypelist, label);
					if (infromation.equals("0")) {
						logger.error("调用获取门户后台选项卡名称为：" + str+"apptoken="+apptoken);
					}
					return str;
				} else if (parentTypeId.equals("5")) {
					// 获取迎新通知公告的子类型
					String TabName = backMhConfig
							.getString("backMhNoticeTABYXListTypeName");
					String TabType = backMhConfig
							.getString("backMhNoticeTABYXType");
					String[] tabmane = TabName.split(";");
					String[] tabtype = TabType.split(";");
					String label = "dpm_yx";
					List tabmanelist = Arrays.asList(tabmane);
					List tabtypelist = Arrays.asList(tabtype);
					// List list = XMLParser.ReadXMl();
					str = JavaXML.BuildXMLForBackMhNoticeList(tabmanelist,
							tabtypelist, label);
					if (infromation.equals("0")) {
						logger.error("调用获取门户后台选项卡名称为：" + str+"apptoken="+apptoken);
					}
					return str;
				} else if (parentTypeId.equals("6")) {
					// 调用OA会议通知公告列表
					String TabName = backMhConfig
							.getString("backMhNoticeTABHYListTypeName");
					String TabType = backMhConfig
							.getString("backMhNoticeTABHYType");
					String[] tabmane = TabName.split(";");
					String[] tabtype = TabType.split(";");
					String label = "dpm_hy";
					List tabmanelist = Arrays.asList(tabmane);
					List tabtypelist = Arrays.asList(tabtype);
					// List list = XMLParser.ReadXMl();
					str = JavaXML.BuildXMLForBackMhNoticeList(tabmanelist,
							tabtypelist, label);
					if (infromation.equals("0")) {
						logger.error("调用获取门户后台选项卡名称为：" + str+"apptoken="+apptoken);
					}
				}
			} else {
				String msg = "验证失败";
				String code = "101";
				str = JavaXML.BuildXMLReturnErro(msg, code);
				return str;
			}
		}
		
		

		return str;

	}

	/**
	 * <p>
	 * Description: 通知公告教务类别
	 * </p>
	 * 
	 * @return
	 * 
	 * @since 2014-11-11 上午11:12:17
	 * @author 阳章
	 */
	@Override
	public String getNoticeJwType(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		
		
		String TabName = backMhConfig
				.getString("backMhNoticeTABJWListTypeName");
		String TabType = backMhConfig.getString("backMhNoticeTABJWType");
		String[] tabmane = TabName.split(";");
		String[] tabtype = TabType.split(";");
		List tabmanelist = Arrays.asList(tabmane);
		List tabtypelist = Arrays.asList(tabtype);
		// List list = XMLParser.ReadXMl();
		String TabNames = JavaXML.BuildXMLDoc(tabmanelist, tabtypelist);
		if (infromation.equals("0")) {
			logger.error("调用获取门户后台选项卡名称为：" + TabNames+"apptoken="+apptoken);
		}
		return TabNames;
	}

	/**
	 * <p>
	 * Description: 通知公告学工类别
	 * </p>
	 * 
	 * @return
	 * 
	 * @since 2014-11-12 上午11:06:10
	 * @author 阳章
	 */
	@Override
	public String getNoticeXgType(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String TabName = backMhConfig
				.getString("backMhNoticeTABXGListTypeName");
		String TabType = backMhConfig.getString("backMhNoticeTABXGType");
		String[] tabmane = TabName.split(";");
		String[] tabtype = TabType.split(";");
		List tabmanelist = Arrays.asList(tabmane);
		List tabtypelist = Arrays.asList(tabtype);
		// List list = XMLParser.ReadXMl();
		String TabNames = JavaXML.BuildXMLDoc(tabmanelist, tabtypelist);
		if (infromation.equals("0")) {
			logger.error("调用获取门户后台选项卡名称为：" + TabNames+"apptoken="+apptoken);
		}
		return TabNames;
	}

	/**
	 * <p>
	 * Description: 通知公告离校类别
	 * </p>
	 * 
	 * @return
	 * 
	 * @since 2014-11-14 上午09:44:38
	 * @author yangz
	 */
	@Override
	public String getNoticeLxType(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String TabName = backMhConfig
				.getString("backMhNoticeTABLXListTypeName");
		String TabType = backMhConfig.getString("backMhNoticeTABLXType");
		String[] tabmane = TabName.split(";");
		String[] tabtype = TabType.split(";");
		List tabmanelist = Arrays.asList(tabmane);
		List tabtypelist = Arrays.asList(tabtype);
		// List list = XMLParser.ReadXMl();
		String TabNames = JavaXML.BuildXMLDoc(tabmanelist, tabtypelist);
		if (infromation.equals("0")) {
			logger.error("调用获取门户后台选项卡名称为：" + TabNames+"apptoken="+apptoken);
		}
		return TabNames;
	}

	/**
	 * <p>
	 * Description: 通知公告迎新类别
	 * </p>
	 * 
	 * @return
	 * 
	 * @since 2014-11-14 上午09:44:50
	 * @author yangz
	 */
	@Override
	public String getNoticeYxType(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String TabName = backMhConfig
				.getString("backMhNoticeTABYXListTypeName");
		String TabType = backMhConfig.getString("backMhNoticeTABYXType");
		String[] tabmane = TabName.split(";");
		String[] tabtype = TabType.split(";");
		List tabmanelist = Arrays.asList(tabmane);
		List tabtypelist = Arrays.asList(tabtype);
		// List list = XMLParser.ReadXMl();
		String TabNames = JavaXML.BuildXMLDoc(tabmanelist, tabtypelist);
		if (infromation.equals("0")) {
			logger.error("调用获取门户后台选项卡名称为：" + TabNames+"apptoken="+apptoken);
		}
		return TabNames;
	}

	/**
	 * <p>
	 * Description: 获取通知公告的类型
	 * </p>
	 * 
	 * @return
	 * 
	 * @since 2015-2-15 上午10:42:12
	 * @author yangz
	 */
	@Override
	public String getMobileBackMhNoticeListType(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String str = null;
		String TabName = backMhConfig.getString("TeaBackMhNoticeListTypeName");
		String TabType = backMhConfig.getString("TeaBackMhNoticeListType");
		String NoticeType = backMhConfig.getString("backMhNoticeListName");
		String[] tabmane = TabName.split(";");
		String[] tabtype = TabType.split(";");
		String[] noticetype = NoticeType.split(";");
		List tabmanelist = Arrays.asList(tabmane);
		List tabtypelist = Arrays.asList(tabtype);
		List tabnoticetype = Arrays.asList(noticetype);
		// List list = XMLParser.ReadXMl();
		str = JavaXML.BuildXMLDocByNoticeTopType(tabmanelist, tabtypelist,
				tabnoticetype);
		if (infromation.equals("0")) {
			logger.error("调用获取门户后台选项卡名称为：" + str+"apptoken="+apptoken);
		}
		return str;
	}

	/**
	 * <p>
	 * Description: 获取OA通知公告的子列表
	 * </p>
	 * 
	 * @return
	 * 
	 * @since 2015-2-15 上午10:41:47
	 * @author yangz
	 */
	@Override
	public String getNoticeListTableType(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		// 获取OA通知公告的子类型
		String str = null;
		String TabName = backMhConfig
				.getString("backMhNoticeTABOAListTypeName");
		String TabType = backMhConfig.getString("backMhNoticeTABOAType");
		String[] tabmane = TabName.split(";");
		String[] tabtype = TabType.split(";");
		List tabmanelist = Arrays.asList(tabmane);
		List tabtypelist = Arrays.asList(tabtype);
		// List list = XMLParser.ReadXMl();
		str = JavaXML.BuildXMLDocBack(tabmanelist, tabtypelist);
		if (infromation.equals("0")) {
			logger.error("调用获取门户后台选项卡名称为：" + str+"apptoken="+apptoken);
		}
		return str;
	}

}
