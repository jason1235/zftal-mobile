package com.newmobile.login.service;
import javax.jws.WebService;


/**
 * 
 * @author ChenMinming
 * @date 2015-4-23
 * @version V1.0.0
 */
@WebService(targetNamespace = "http://service.cxf.webservices.mobile.zfsoft.com/", name = "WSPushService") 
public interface IWSPushService {
	/**
	 * 获取此用户的所有消息推送列表
	 * @param userId	用户id
	 * @param start		页码			
	 * @param size		每页数量
	 * @return
	 */
	public String PushMsgList(String userId, int start, int size,String strKey);
	/**
	 * 用户绑定推送设备id
	 * @param userId 用户id
	 * @param registrationId 推送设备id
	 * @param sign 验证签名	md5(用户名+密钥)
	 * @return 成功则无返回
	 */
	public String SetUserIdAndRegistrationId(String parameterList,String strkey);
	/**
	 * 发送消息
	 * @param msg 
	 * 		推送类型支持广播、别名
	 * 		其中 推送对象(tsdx)暂时只支持用户名
	 * @param sign 验证签名	md5(用户名+密钥)
	 * @return 成功则返回json结果集
	 */
	public String Push(String parameterList,String sign);
	/**
	 * 查询发送的消息对象
	 * @param userId 发送者
	 * @param page 要查询的分页
	 * @param pageSize 每页显示数
	 * @param sign 验证签名	md5(用户名+密钥)
	 * @return PushListPage查询结果对象 包含以下参数
	 * 				msgList 记录集合
	 * 				paginator 分页器
	 */
	public String GetSendMsg(String parameterList, int page,
			int pageSize , String sign);
	/**
	 * 查询接收的消息对象
	 * @param userId 接收者
	 * @param page 要查询的分页 
	 * @param pageSize 每页显示数
	 * @param sign 验证签名	md5(用户名+密钥)
	 * @return PushListPage查询结果对象 包含以下参数
	 * 				msgList 记录集合
	 * 				paginator 分页器
	 */
	public String GetPushMsg(String parameterList,int page,int size,String strkey);

}
