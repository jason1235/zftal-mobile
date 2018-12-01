package com.newmobile.LibService.service;

import java.io.InputStream;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;



/**
 * <p>Description: 新移动登陆接口</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2015-7-23 下午03:16:15
 * @author zhangxu
 * @version 1.0
 */
@WebService
public interface IMobileHWWebService {
	/**
	 * 	读者信息查询
	 * @param userName
	 * @param apptoken
	 * @return
	 */
	public String getreader(@WebParam(name="userName")String userName,@WebParam(name="apptoken")String apptoken);
	
	/**
	 * 	馆藏图书查询
	 * @param userName
	 * @param apptoken
	 * @param type	查询类型.1表示根据读者证件号查询，2表示根据读者条码号查询
	 * @param value	读者证件号(type=1)或读者条码号(type=2)
	 * @return
	 */
	public String getBook(@WebParam(name="userName")String userName,@WebParam(name="apptoken")String apptoken,
			@WebParam(name="type")String type,@WebParam(name="value")String value);
	
	/**
	 * 读者借阅信息查询	
	 * @param userName
	 * @param time
	 * @param verify
	 * @param certId	读者证件号(非空)
	 * @param apptoken
	 * @return
	 */

	public String getCircs(@WebParam(name="userName")String userName,  @WebParam(name="certId")String certId
			,@WebParam(name="apptoken")String apptoken);
	
	/**
	 * 读者超期欠款信息查询
	 * @param userName
	 * @param certId	读者证件号(非空)
	 * @param apptoken
	 * @return
	 */
	public String getDebts(@WebParam(name="userName")String userName, @WebParam(name="certId")String certId
			,@WebParam(name="apptoken")String apptoken);
	
	/**
	 * 读者预约到书信息查询
	 * @param userName
	 * @param certId	读者证件号(非空)
	 * @param apptoken
	 * @return
	 */
	public String getPregArrivals(@WebParam(name="userName")String userName, @WebParam(name="certId")String certId
			,@WebParam(name="apptoken")String apptoken);
}
