package com.sjzx.dqxnxq.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@WebService
@XmlAccessorType(XmlAccessType.FIELD)
public interface IDqxnxqXMLService {
	/**
	 * <p>Description: 当前学年学期接口</p>
	 * @param strKey 密钥
	 * @return
	 *
	 * @since 2014-5-13 上午08:40:30
	 * @author yangz
	 */
	@WebMethod
	public String getDqxnxq(
		@WebParam(name="apptoken") String apptoken,
	   	@WebParam(name="strKey")String strKey	
	);
}
