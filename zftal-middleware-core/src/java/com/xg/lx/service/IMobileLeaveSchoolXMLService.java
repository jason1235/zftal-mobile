package com.xg.lx.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface IMobileLeaveSchoolXMLService {
	
	/** <p>
	 * Description:        获取离校通知公告
	 * </p>
	 * @param userName   登陆用户名
	 * @param yhlx       用户类型：1.tea；2.stu
	 * @param num        数值类型(获取记录条数,可为空,默认为6条)
	 * @param sign       验证签名
	 * @param pageIndex  
	 * @return
	 */
	@WebMethod
	public String getLxtzggList(
			@WebParam(name="userName") String userName,
			@WebParam(name="yhlx")String yhlx,
			@WebParam(name="num") String num,
			@WebParam(name="sign") String sign,
			@WebParam(name="pageIndex") String pageIndex,
			@WebParam(name="apptoken") String apptoken
			);
	
	
	/**<p>
	 * Description:        获取离校通知公告详情
	 * </p>
	 * @param id         通知公告ID
	 * @param userName   登陆用户名
	 * @param sign       验证签名
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
