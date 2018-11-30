package com.sjzx.ykt.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


/**
 * <p>Description:一卡通接口</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2014-4-2 上午09:03:52
 * @author yangz
 * @version 1.0
 */
@WebService
@XmlAccessorType(XmlAccessType.FIELD)
public interface IYktXMLService {
	
	/**
	 * <p>Description: 中央民族大学一卡通交易信息</p>
	 * @param username 用户名
	 * @param sendtime 当前时间
	 * @param strKey     加密后密文
	 * @param startData 查询起始时间
	 * @param endData   查询结束时间
	 * @return
	 *
	 * @since 2014-4-2 下午02:34:10
	 * @author yangz
	 */
	@WebMethod
	public String getIcCardConsumeInfo(
			@WebParam(name="username")String username,
			@WebParam(name="strKey")String strKey,
			@WebParam(name="sendtime")String sendtime,
			@WebParam(name="endData")String endData,
			@WebParam(name="startData")String startData
			
			);
	
	
	
	/**
	 * <p>Description: 中央民族大学一卡通余额</p>
	 * @param username 用户名
	 * @param strKey     加密后密钥
	 * @return
	 *
	 * @since 2014-4-4 上午10:34:30
	 * @author yangz
	 */
	@WebMethod
	public String getyktye(@WebParam(name="username") String username,@WebParam(name="strKey") String strKey);
	
	
	
	/**
	 * <p>Description: 重庆第二师范学院一卡通消费信息</p>
	 * @param username 用户名
	 * @return
	 *
	 * @since 2014-12-8 上午08:44:14
	 * @author yangz
	 */
	@WebMethod
	public String getyktxfxx(@WebParam(name="username") String username,@WebParam(name="strKey") String strKey);
	
	/**
	 * <p>Description: 重庆第二师范学院一卡通基本信息</p>
	 * @param username 用户名
	 * @param strKey   秘钥
	 * @return
	 *
	 * @since 2014-12-8 上午09:54:47
	 * @author yangz
	 */
	@WebMethod
	public String getyktjbxx(@WebParam(name="username") String username,@WebParam(name="strKey") String strKey);

}
