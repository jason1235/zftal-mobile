package com.mh.service;



import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface IMobileManageXMLService {
	/**
	 * 
	* @author: zhangxu
	* @Title: ssoLogin 
	* @Description: 
	* @param @param username 用户id
	* @param @param password 用户密码
	* @param @param yhlx     用户类型
	* @param @param signalXtbm 单点系统编码
	* @param @param signalUrl 单点相对地址
	* @param @param apptoken
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String ssoLogin(@WebParam(name = "username")String username,
			@WebParam(name = "password") String password,
			@WebParam(name = "yhlx") String yhlx,
			@WebParam(name = "signalXtbm") String signalXtbm,
			@WebParam(name = "signalUrl") String signalUrl,
			@WebParam(name="apptoken") String apptoken); 
	
	public String getSSOByMobile(@WebParam(name = "username")String username,
			@WebParam(name = "password") String password,
			@WebParam(name = "ywxtUrl") String ywxtUrl,
			@WebParam(name="apptoken") String apptoken); 
	
	public String getCATGC(@WebParam(name = "username")String username,
			@WebParam(name = "password") String password,
			@WebParam(name="apptoken") String apptoken); 

	/**
	 * <p>Description: 门户登录</p>
	 * @param userName
	 * @param passWord
	 * @return
	 *
	 * @since Nov 1, 2012 11:35:42 AM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String checkUse(
			@WebParam(name = "userName")String userName,
			@WebParam(name = "passWord") String passWord,
			@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>Description: 登录验证获取基本信息接口</p>
	 * @param userName
	 * @param password
	 * @param remoteURL
	 * @return
	 *
	 * @since Nov 9, 2012 10:54:23 AM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String getTicket(
			@WebParam(name = "userName")String userName, 
			@WebParam(name = "passWord")String password, 
			@WebParam(name = "remoteURL")String remoteURL,
			@WebParam(name="apptoken") String apptoken
			);
	
	
	
	/**
	 * <p>Description: 登录验证获取基本信息接口</p>
	 * @param usename  用户名
	 * @param password 密码
	 * @param xtdms    系统代码
	 * @param remoteURL 请求地址
	 * @return
	 *
	 * @since 2014-8-12 下午04:47:06
	 * @author yangz
	 */
	@WebMethod
	public String getTicket2(
			@WebParam(name = "userName")String userName, 
			@WebParam(name = "passWord")String passWord, 
			@WebParam(name = "Xtmdlst") String Xtmdlst,
			@WebParam(name = "remoteURL")String remoteURL,
			@WebParam(name="apptoken") String apptoken
			);
	
	/**
	 * <p>Description: </p>
	 * @param xtdm
	 * @param userName
	 * @return
	 *
	 * @since 2014-5-19 上午09:29:50
	 * @author yangz
	 */
	@WebMethod
	public String getCaUser(
			@WebParam(name = "xtdm")String xtdm, 
			@WebParam(name = "userName")String userName,
			@WebParam(name="apptoken") String apptoken
			);
	
	/**
	 * <p>Description: 获取票据单点登录</p>
	 * @param userName
	 * @param password
	 * @return
	 *
	 * @since 2014-5-19 下午03:57:37
	 * @author yangz
	 */
	@WebMethod
	public String getTGC(
			@WebParam(name ="userName")String userName,
			@WebParam(name = "password")String password,
			@WebParam(name="apptoken") String apptoken
			);
	
	
	/**
	 * 获取手机重置密码验证码
	 * @param sjhm
	 * @return
	 */
//	@WebMethod
//	public String getyzm(@WebParam(name="sjhm") String sjhm,@WebParam(name="apptoken") String apptoken);
	@WebMethod
	public String getyzm(@WebParam(name="sjhm") String sjhm);
	
	
	/**
	 * 验证充值密码验证码，以及重置密码
	 * @param sjhm
	 * @return
	 */
//	@WebMethod
//	public String retakepwd(
//			@WebParam(name="sjhm") String sjhm,
//			@WebParam(name="yzm") String yzm,
//			@WebParam(name="xmm") String xmm,
//			@WebParam(name="apptoken") String apptoken
//			);
	@WebMethod
	public String retakepwd(
			@WebParam(name="sjhm") String sjhm,
			@WebParam(name="yzm") String yzm,
			@WebParam(name="xmm") String xmm
			);
	
	/**
	 * 获取手机绑定验证码
	 * @param sjhm 手机号码
	 * @param username 用户名（明文）
	 * @param apptoken 
	 * @return
	 */
	@WebMethod
	public String getPhoneBindingsYzm(
			@WebParam(name="sjhm") String sjhm,
			@WebParam(name="username") String username,
			@WebParam(name="apptoken") String apptoken
			);
	
	/**
	 * 获取取消手机绑定验证码
	 * @param sjhm 手机号码
	 * @param apptoken
	 * @return
	 */
	@WebMethod
	public String getPhoneCancelBindingsYzm(
			@WebParam(name="sjhm") String sjhm,
			@WebParam(name="apptoken") String apptoken
			);
	
	/**
	 * 绑定到手机
	 * @param sjhm 手机号码
	 * @param yzm 验证码
	 * @param apptoken
	 * @return
	 */
	@WebMethod
	public String bindPhone(
			@WebParam(name="sjhm") String sjhm,
			@WebParam(name="yzm") String yzm,
			@WebParam(name="apptoken") String apptoken
	);
	
	/**
	 * 取消绑定手机
	 * @param username 用户名
	 * @param sjhm 手机号码
	 * @param yzm 验证码
	 * @param apptoken
	 * @return
	 */
	@WebMethod
	public String cancelBindPhone(
			@WebParam(name="username") String username,
			@WebParam(name="sjhm") String sjhm,
			@WebParam(name="yzm") String yzm,
			@WebParam(name="apptoken") String apptoken
	);
	
	/**
	 * 通过邮箱找回密码
	 * @param email email地址
	 * @param apptoken
	 * @return
	 */
//	@WebMethod
//	public String findpwdbymail(
//			@WebParam(name="email") String email,
//			@WebParam(name="apptoken") String apptoken
//	);
	@WebMethod
	public String findpwdbymail(
			@WebParam(name="email") String email
	);
	
	
	/**
	 * 获取取消绑定邮箱的激活码
	 * @param email email地址
	 * @param apptoken
	 * @return
	 */
	@WebMethod
	public String cancelEmailYzm(
			@WebParam(name="email") String email,
			@WebParam(name="apptoken") String apptoken
	);
	
	/**
	 * 获取绑定邮箱的激活码
	 * @param username 用户名
	 * @param email email地址
	 * @param apptoken
	 * @return
	 */
	@WebMethod
	public String getEmailBindingsYzm(
			@WebParam(name="username") String username,
			@WebParam(name="email") String email,
			@WebParam(name="apptoken") String apptoken
	);
	
	/**
	 * 绑定邮箱
	 * @param username 用户名
	 * @param activecode 激活码
	 * @param apptoken
	 * @return
	 */
	@WebMethod
	public String bindEmail(
			@WebParam(name="username") String username,
			@WebParam(name="activecode") String activecode,
			@WebParam(name="apptoken") String apptoken
	);
	
	/**
	 * 取消绑定邮箱
	 * @param username 用户名
	 * @param email 邮箱地址
	 * @param apptoken
	 * @return
	 */
	@WebMethod
	public String cancelBindEmail(
			@WebParam(name="email") String email,
			@WebParam(name="radomnumber") String radomnumber,
			@WebParam(name="apptoken") String apptoken
	);
	
	/**
	 * 检查是否绑定手机和邮箱
	 * @param username 用户名
	 * @param apptoken
	 * @return
	 */
	@WebMethod
	public String checkBindPhoneAndEmail(
			@WebParam(name="username") String username,
			@WebParam(name="apptoken") String apptoken
	);
	
	/**
	 * 检查是否绑定手机和邮箱
	 * @param username 用户名
	 * @return
	 */
	@WebMethod
	public String checkBindPhoneAndEmailWithoutApptoken(
			@WebParam(name="username") String username
	);
	
	@WebMethod
	public String ssoLogin1(@WebParam(name = "username")String username,
			@WebParam(name = "password") String password,
			@WebParam(name = "yhlx") String yhlx,
			@WebParam(name = "signalXtbm") String app_id,
			@WebParam(name = "signalUrl") String gotoUrl,
			@WebParam(name="apptoken") String apptoken);
}
