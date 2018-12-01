package com.sjzx.xzInformation.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


/**
 * <p>Description:薪资信息接口</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2014-4-3 下午01:41:27
 * @author yangz
 * @version 1.0
 */
@WebService
@XmlAccessorType(XmlAccessType.FIELD)
public interface ISalaryXMLService {
	
	/**
	 * <p>Description: 中央民族大学教工薪资明细查询</p>
	 * @param username 用户名
	 * @param strKey   加密后密钥
	 * @param sendtime     日期
	 * @return
	 *
	 * @since 2014-4-3 下午02:02:49
	 * @author yangz
	 */
	@WebMethod
	public String getXZMXCX(@WebParam(name="username") String username,@WebParam(name="sendtime")String sendtime,@WebParam(name="strKey")String strKey);
	
	/**
	 * <p>Description: 中央民族大学教工其他收入查询</p>
	 * @param username 用户名
	 * @param sendtime 发送时间
	 * @param strKey   加密后密钥
	 * @return
	 *
	 * @since 2014-4-4 下午02:50:47
	 * @author yangz
	 */
	@WebMethod
	public String getqtsr(
			@WebParam(name="username")String username,
			@WebParam(name="sendtime")String sendtime,
			@WebParam(name="strKey")String strKey
	);

}
