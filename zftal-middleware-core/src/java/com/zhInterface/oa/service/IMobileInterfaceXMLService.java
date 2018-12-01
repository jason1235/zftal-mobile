package com.zhInterface.oa.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;


/**
 * <p>Description: 整合相同类型的接口</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2015-2-2 上午09:32:44
 * @author yangz
 * @version 1.0
 */
@WebService
public interface IMobileInterfaceXMLService {
	
	
	/**
	 * <p>Description: 通知公告列表</p>
	 * @param userName       用户名 
	 * @param role           角色 
	 * @param type           通知公告子类型
	 * @param start          当前页，从1开始
	 * @param size           获取记录条数
	 * @param sign           秘钥
	 * @param notictype      通知公告类型
	 * @return
	 *
	 * @since 2015-2-2 上午10:06:49
	 * @author yangz
	 */
	@WebMethod
	public String getNoticeList(
			@WebParam(name="userName") String userName,
			@WebParam(name="role") String role,
			@WebParam(name="type") String type
			,@WebParam(name="start") String start,
			@WebParam(name="size") String size,
			@WebParam(name="sign") String sign,
			@WebParam(name="toptype") String toptype,
			@WebParam(name="bak") String bak,
			@WebParam(name="apptoken") String apptoken
			);
	
	/**
	 * <p>Description: 通知公告详情</p>
	 * @param userName 用户名
	 * @param noticeId 通知公告Id
	 * @param notictype通知公告类型
	 * @param count    记录数
	 * @param sign     秘钥
	 * @return
	 *
	 * @since 2015-2-5 上午09:11:01
	 * @author yangz
	 */
	@WebMethod
	public String getNoticeInfo(
			@WebParam(name="userName") String userName,
			@WebParam(name="noticeId") String noticeId,
			@WebParam(name="notictype") String notictype,
			@WebParam(name ="type") String type,
			@WebParam(name="count") String count,
			@WebParam(name="sign") String sign,
			@WebParam(name="bak") String bak,
			@WebParam(name="apptoken") String apptoken
			);
		

}
