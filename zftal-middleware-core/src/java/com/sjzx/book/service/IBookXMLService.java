package com.sjzx.book.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


/**
 * <p>Description: 图书接口</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2014-12-8 上午09:07:12
 * @author yangz
 * @version 1.0
 */
@WebService
@XmlAccessorType(XmlAccessType.FIELD)
public interface IBookXMLService {
	/**
	 * <p>Description: 图书馆借阅信息</p>
	 * @param readerbarcode 读者条码
	 * @param strKey        秘钥
	 *
	 * @since 2014-12-8 上午09:14:32
	 * @author yangz
	 */
	@WebMethod
	public String jyxxb(@WebParam(name="username")String username,@WebParam(name="strKey")String strKey,@WebParam(name="apptoken") String apptoken);

}
