package com.versionVerify.service.imp;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.common.WebServiceConf;
import com.versionVerify.CXFServe.IVersionVerifyXmlService;
import com.versionVerify.service.IVersionCheckXmlService;


/**
 * WebService实现类.
 * 
 * 使用@WebService指向Interface定义类即可.
 */

@Service
@Component(value = "versionCheckXmlService")
public class VersionCheckXmlService implements IVersionCheckXmlService {
	/**
	 * <p>Description: </p>
	 IMEI	VARCHAR2(100)	Y			手机序列号
	 IMSI	VARCHAR2(100)	Y			sim卡序列号
	 SYSTEMINFO	VARCHAR2(50)	Y			手机系统版本
	 UA	VARCHAR2(100)	Y			手机型号
	 PHONENUM	VARCHAR2(11)	Y			手机号码
	 ACCOUNT	VARCHAR2(100)	Y			用户账号
	 VERSION	VARCHAR2(200)	Y			登陆客户端版本
	 * @return  {
	 * 
		最新版本号 比如：ZFSOFT-CTJW-TY-ZJDX-V1.1.1
		更新提示语比如：此次更新修改了以下BUG 1.。。2.。。。3.。。
		更新状态1:强制更新 2:非强制更新 3:不需要更新
		最新版本下载地址 http://www.zfsoft.com/m/*.apk
	 * 
	 * }
	 *
	 * 注意：版本格式，必须如：ZFSOFT-CTJW-TY-ZJDX-V1.1.1

	 * @since Jan 16, 2013 9:21:22 AM
	 * @author huangzhaoxia
	 */
	public String versionCompare(String imei, String imsi, String sysinfo,
			String ua, String phonum, String account, String v) {
		
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IVersionVerifyXmlService.class);
		factory.setAddress(WebServiceConf.SERVICE_VERSIONSERVICE_CHECK);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		IVersionVerifyXmlService service = (IVersionVerifyXmlService) factory.create();

		return service.versionCompare(imei, imsi, sysinfo, ua, phonum, account, v);
	}

	
}
