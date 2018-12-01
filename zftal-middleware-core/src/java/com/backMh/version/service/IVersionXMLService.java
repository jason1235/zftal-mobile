package com.backMh.version.service;

import javax.jws.WebParam;
import javax.jws.WebService;

/** 
 * WebService接口定义类. 
 *  
 * 使用@WebService将接口中的所有方法输出为Web Service. 
 * 可用annotation对设置方法、参数和返回值在WSDL中的定义. 
 */  
@WebService  
public interface IVersionXMLService {
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
	 * @since Nov 6, 2012 1:35:00 PM
	 * @author huangzhaoxia
	 * 注意：版本格式，必须如：ZFSOFT-CTJW-TY-ZJDX-V1.1.1
	 */
	public String checkVersion(@WebParam(name = "imei") String imei,
			@WebParam(name = "imsi")String imsi,
			@WebParam(name = "sysinfo")String sysinfo,
			@WebParam(name = "ua")String ua,
			@WebParam(name = "phonum")String phonum,
			@WebParam(name = "account")String account,
			@WebParam(name = "v")String v);

	/**
	 * <p>Description: object-c兼容加密</p>	 
	 * @param code
	 * @return
	 *
	 * @since Nov 6, 2012 1:35:35 PM
	 * @author huangzhaoxia
	 */
	public String encode(@WebParam(name = "code") String code);
	/**
	 * <p>Description: object-c兼容解密</p>
	 * @param code
	 * @return
	 * @throws Exception
	 *
	 * @since Nov 6, 2012 1:35:35 PM
	 * @author huangzhaoxia
	 */
	public String decode(@WebParam(name = "code") String code);
	
}
