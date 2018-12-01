package com.xg.yx.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface IMobileYXXMLService {
	/** <p>
	 * Description:        获取迎新通知公告
	 * </p>
	 * @param userName   登陆用户名
	 * @param num        数值类型(获取记录条数,可为空,默认为6条)
	 * @param sign       验证签名
	 * @param pageIndex  
	 * @return
	 */
	@WebMethod
	public String getYxTzggTeaList(
			@WebParam(name="userName") String userName,
			@WebParam(name="num") String num,
			@WebParam(name="sign") String sign,
			@WebParam(name="pageIndex") String pageIndex,
			@WebParam(name="apptoken") String apptoken
			);
	
	/** <p>
	 * Description:        获取迎新查询通知公告详情
	 * </p>
	 * @param id        通知公告ID
	 * @param userName  登陆用户名
	 * @param sign      验证签名
	 * @return
	 */
	@WebMethod
	public String getTzggDetail(
			@WebParam(name="id") String id,
			@WebParam(name="userName") String userName,
			@WebParam(name="sign") String sign,
			@WebParam(name="apptoken") String apptoken
			);

}
