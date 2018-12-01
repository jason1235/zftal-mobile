package com.xg.xg.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface IMobileWorkStudyXMLService {
	
	/**
	 * 
	* @author: zhangxu
	* @Title: getTzggCount 
	* @Description: 学工公告数量的接口
	* @param @param yhm
	* @param @param yhlx
	* @param @param timestamp
	* @param @param cipher
	* @param @param dataMap
	* @param @param apptoken
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@WebMethod
	public String getTzggCount(
			@WebParam(name = "yhm")String yhm,
			@WebParam(name = "yhlx") String yhlx,
			@WebParam(name="apptoken") String apptoken
			);
	
	/** <p>
	 * Description:        获取学工通知公告
	 * </p>
	 * @param userName   登陆用户名
	 * @param yhlx       用户类型：1.tea；2.stu
	 * @param type       所属类别，目前只能传 -1
	 * @param num        数值类型(获取记录条数,可为空,默认为6条)
	 * @param sign       验证签名
	 * @param pageIndex  
	 * @return
	 */
	@WebMethod
	public String getLastNoticeList(
			@WebParam(name = "userName")String userName,
			@WebParam(name = "yhlx") String yhlx,
			@WebParam(name="type") String type,
			@WebParam(name="num") String num,
			@WebParam(name="sign") String sign,
			@WebParam(name="pageIndex") String pageIndex,
			@WebParam(name="apptoken") String apptoken
			);
	
	/**<p>
	 * Description:        获取学工通知公告详情
	 * </p>
	 * @param id         通知公告ID
	 * @param userName        登陆用户名
	 * @param sign       验证签名
	 * @return
	 */
	@WebMethod
	public String getTzggDetail(
			@WebParam(name = "id") String id,
			@WebParam(name = "userName") String userName,
			@WebParam(name = "sign") String sign,
			@WebParam(name="apptoken") String apptoken
			);
	

}
