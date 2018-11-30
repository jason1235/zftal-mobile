package com.zh.xx.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;



/**
 * <p>Description: 综合Webservice接口</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2014-6-23 上午09:58:43
 * @author yangz
 * @version 1.0
 */
@WebService
public interface IMobileSyntheticalXMLService {
	/**
	 * <p>Description: 登录校验</p>
	 * @param userName 用户名
	 * @param passWord 密码
	 * @return
	 *
	 * @since 2014-6-23 上午09:58:39
	 * @author yangz
	 */
	@WebMethod
	public String CheckUserInf(
			@WebParam(name="userName") String userName,
			@WebParam(name="passWord") String passWord,
			@WebParam(name="apptoken") String apptoken
			);

}
