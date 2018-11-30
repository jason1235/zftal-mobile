package com.ts.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;


/**
 * <p>Description: 推送接口</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2015-5-12 上午10:47:34
 * @author yangz
 * @version 1.0
 */
@WebService
public interface IMobilePushXMLService {
	
	
	/**
	 * <p>Description:             获取接收消息</p>
	 * @param userName             用户名
	 * @param pushType             推送方式
	 * @param configurationType    配置类型
 	 * @param page                 页号
	 * @param size                 获取记录数
	 * @param sign                 秘钥
	 * @return
	 *
	 * @since 2015-5-12 上午10:47:47
	 * @author yangz
	 */
	@WebMethod
	public String GetPushMsg(
			@WebParam(name="userName") String userName,
			@WebParam(name="pushType") String pushType,
			@WebParam(name="configurationType") String configurationType,
		@WebParam(name="page") String page,
		@WebParam(name="size") String size,
		@WebParam(name="sign") String sign
		//@WebParam(name="apptoken") String apptoken
		);	
	
	
	/**
	 * <p>Description:             设定用户id和设备id</p>
	 * @param userName             用户名
	 * @param imei                 移动设备号
	 * @param configurationType    配置类型
	 * @param sign                 秘钥
	 * @return
	 *
	 * @since 2015-5-12 上午11:47:54
	 * @author yangz
	 */
	@WebMethod
	public String SetUserIdAndRegistrationId(
			@WebParam(name="_userName") String _userName,
			@WebParam(name="imei") String imei,
			@WebParam(name="configurationType") String configurationType,
			@WebParam(name="sbType") String sbType, 
			@WebParam(name="sign") String sign
			//@WebParam(name="apptoken") String apptoken
			); 

}
