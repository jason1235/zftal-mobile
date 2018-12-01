package com.oa.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@WebService
@XmlAccessorType(XmlAccessType.FIELD)
public interface IEmailInformationXMLService {
	
	/** 1.3.11.8	修改文件传阅为已读updateWjcyZt</p>
	 * @param Yhm:当前登录的用户名
	 * @param start:页码
	 * @param size:每页显示数量
	 * @param sign:公钥
	 * @return
	 */
	@WebMethod
	public String updateWjcyZt(
			@WebParam(name="yhm") String yhm,
			@WebParam(name="id") String id,
			@WebParam(name="sign") String sign,
			@WebParam(name="apptoken") String apptoken
			);
	
	/** 1.3.11.5	根据文件id获取文件传阅发件箱详情</p>
	 * @param Yhm:当前登录的用户名
	 * @param start:页码
	 * @param size:每页显示数量
	 * @param sign:公钥
	 * @return
	 */
	@WebMethod
	public String getWjcySjxSlById(
			@WebParam(name="yhm") String yhm,
			@WebParam(name="sign") String sign,
			@WebParam(name="apptoken") String apptoken
			);
	
	/** 获取文件传阅收件箱列表</p>
	 * @param Yhm:当前登录的用户名
	 * @param start:页码
	 * @param size:每页显示数量
	 * @param sign:公钥
	 * @return
	 */
	@WebMethod
	public String getWjcySjxList(
			@WebParam(name="yhm") String yhm,
			@WebParam(name="start") String start,
			@WebParam(name="size") String size,
			@WebParam(name="sign") String sign,
			@WebParam(name="apptoken") String apptoken
			);
	
	/** 1.3.11.4	根据文件id获取文件传阅收件箱详情</p>
	 * @param Yhm:当前登录的用户名
	 * @param start:页码
	 * @param size:每页显示数量
	 * @param sign:公钥
	 * @return
	 */
	@WebMethod
	public String getWjcySjxInfoById(
			@WebParam(name="yhm") String yhm,
			@WebParam(name="eid") String eid,
			@WebParam(name="sign") String sign,
			@WebParam(name="apptoken") String apptoken
			);
	
	/** 1.3.11.2	获取文件传阅发件箱列表</p>
	 * @param Yhm:当前登录的用户名
	 * @param start:页码
	 * @param size:每页显示数量
	 * @param sign:公钥
	 * @return
	 */
	@WebMethod
	public String getWjcyFjxList(
			@WebParam(name="yhm") String yhm,
			@WebParam(name="start") String start,
			@WebParam(name="size") String size,
			@WebParam(name="sign") String sign,
			@WebParam(name="apptoken") String apptoken
			);
	
	/** 1.3.11.5	根据文件id获取文件传阅发件箱详情</p>
	 * @param Yhm:当前登录的用户名
	 * @param start:页码
	 * @param size:每页显示数量
	 * @param sign:公钥
	 * @return
	 */
	@WebMethod
	public String getWjcyFjxInfoById(
			@WebParam(name="yhm") String yhm,
			@WebParam(name="eid") String eid,
			@WebParam(name="sign") String sign,
			@WebParam(name="apptoken") String apptoken
			);
	
	/** 1.3.12.1	根据ID取得领导会议安排详细信息</p>
	 * @param yhm
	 * @param sign
	 * @param apptoken
	 * @return
	 */
	@WebMethod
	public String getLdhyhdInfo(
			@WebParam(name="yhm") String yhm,
			@WebParam(name="id") String id,
			@WebParam(name="sign") String sign,
			@WebParam(name="apptoken") String apptoken
			);
	
	
	
	/** 1.3.12.1	获取领导会议安排列表</p>
	 * @param yhm
	 * @param sign
	 * @param apptoken
	 * @return
	 */
	@WebMethod
	public String getLdhyhdList(
			@WebParam(name="yhm") String yhm,
			@WebParam(name="kssj") String kssj,
	        @WebParam(name="jssj") String jssj,
	        @WebParam(name="sign") String sign,
			@WebParam(name="apptoken") String apptoken
			);
	
	
	/**
	 * <p>Description: 1.3.11.10	移动端获取工作日志列表</p>
	 * @param yhm
	 * @param sign
	 * @param apptoken
	 * @return
	 */
	@WebMethod
	public String saveGzrzb(
			@WebParam(name="yhm") String yhm,
			@WebParam(name="rznr") String rznr,
	        @WebParam(name="rq") String rq,
	        @WebParam(name="sign") String sign,
			@WebParam(name="apptoken") String apptoken
			);
	
	/**
	 * <p>Description: 1.3.11.10	移动端获取工作日志列表</p>
	 * @param yhm
	 * @param sign
	 * @param apptoken
	 * @return
	 */
	@WebMethod
	public String getGzrzList(
			@WebParam(name="yhm") String yhm,
			@WebParam(name="start") String start,
	        @WebParam(name="size") String size,
	        @WebParam(name="sign") String sign,
			@WebParam(name="apptoken") String apptoken
			);
	
	/**
	 * <p>Description: 1.3.11.9	移动端ldap认证及ＯＡ认证</p>
	 * @param yhm
	 * @param sign
	 * @param apptoken
	 * @return
	 */
	@WebMethod
	public String getLdapGh(
			@WebParam(name="email") String email,
			@WebParam(name="pw") String pw
			);
	
	
	/**
	 * <p>Description: 1.1.1.4	获取流程类型列表</p>
	 * @param yhm
	 * @param sign
	 * @param apptoken
	 * @return
	 */
	@WebMethod
	public String getLclxList(
			@WebParam(name="yhm") String yhm,
			@WebParam(name="sign") String sign,
			@WebParam(name="apptoken") String apptoken
			);
	
	/**
	 * <p>Description: 1.1.1.1	根据【待办事宜名和流程类型ID】获取已办事宜列表</p>
	 * @param yhm
	 * @param Condition:待办事宜名
	 * @param Lxid:流程类型ID

	 * @param start
	 * @param size
	 * @param sign
	 * @return
	 *
	 * @since 2015-8-25 上午10:52:22
	 * @author yangz
	 */
	@WebMethod
	public String getSoluteTaskListByConditionAndLx(
			@WebParam(name="yhm") String yhm,
			@WebParam(name="condition") String condition,
			@WebParam(name="lxid") String lxid,
			@WebParam(name="start") String start,
			@WebParam(name="size") String size,
			@WebParam(name="sign") String sign,
			@WebParam(name="apptoken") String apptoken
			);
	
	/**
	 * <p>Description: 1.1.1.1	根据【待办事宜名和流程类型ID】获取已办事宜列表</p>
	 * @param yhm
	 * @param Condition:待办事宜名
	 * @param Lxid:流程类型ID

	 * @param start
	 * @param size
	 * @param sign
	 * @return
	 *
	 * @since 2015-8-25 上午10:52:22
	 * @author yangz
	 */
	@WebMethod
	public String getDoneTaskListByConditionAndLx(
			@WebParam(name="yhm") String yhm,
			@WebParam(name="condition") String condition,
			@WebParam(name="lxid") String lxid,
			@WebParam(name="start") String start,
			@WebParam(name="size") String size,
			@WebParam(name="sign") String sign,
			@WebParam(name="apptoken") String apptoken
			);
	
	/**
	 * <p>Description: 1.1.1.1	根据【待办事宜名和流程类型ID】获取待办列表</p>
	 * @param yhm
	 * @param Condition:待办事宜名
	 * @param Lxid:流程类型ID

	 * @param start
	 * @param size
	 * @param sign
	 * @return
	 *
	 * @since 2015-8-25 上午10:52:22
	 * @author yangz
	 */
	@WebMethod
	public String getTodoTaskListByConditionAndLx(
			@WebParam(name="yhm") String yhm,
			@WebParam(name="condition") String condition,
			@WebParam(name="lxid") String lxid,
			@WebParam(name="start") String start,
			@WebParam(name="size") String size,
			@WebParam(name="sign") String sign,
			@WebParam(name="apptoken") String apptoken
			);
	
	
	/**
	 * 根据【是否阅办】获取待办列表
	 * @param yhm 用户名
	 * @param sfyb 是否阅办 (0 否 1 是)
	 * @param start 
	 * @param size
	 * @param sign
	 * @return
	 */
	public String getTodoTaskListBySfyb(@WebParam(name = "yhm")String yhm, @WebParam(name = "sfyb")String sfyb, 
			@WebParam(name = "start")String start,
			@WebParam(name = "size")String size, @WebParam(name = "sign")String sign,
			@WebParam(name = "apptoken")String apptoken);
	
	/**
	 * 判断用户签名口令方法
	 * @param yhm
	 * @param flowPassword
	 * @param sign
	 * @param apptoken
	 * @return
	 */
	@WebMethod
	public String checkPassword(@WebParam(name = "yhm")String yhm, 
			@WebParam(name = "flowPassword")String flowPassword,
			@WebParam(name = "sign")String sign,
			@WebParam(name = "apptoken")String apptoken);
	
	/**
	 * 流程任务执行
	 * @param yhm
	 * @param id
	 * @param taskId
	 * @param sign
	 * @param apptoken
	 * @return
	 */
	@WebMethod
	public String excuTaskForFlow(@WebParam(name = "yhm")String yhm, 
			@WebParam(name = "id")String id,
			@WebParam(name = "taskId")String taskId, 
			@WebParam(name = "sign")String sign,
			@WebParam(name = "apptoken")String apptoken);

	/**
	 * <p>
	 * Description: 邮件收件箱列表
	 * </p>
	 * 
	 * @return
	 * 
	 * @since Oct 22, 2012 1:47:47 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String getMailListByType(@WebParam(name = "yhm")
	String yhm, @WebParam(name = "type")
	String type, @WebParam(name = "start")
	String start, @WebParam(name = "size")
	String size, @WebParam(name = "sign")
	String sign,@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>
	 * Description: 邮件收件箱列表
	 * </p>
	 * 
	 * @return
	 * 
	 * @since Oct 22, 2017 1:47:47 PM
	 * @author huangzhaoxia
	 */
	public String getMailListByTypeAndCond(
			@WebParam(name = "yhm")String yhm, 
			@WebParam(name = "type")String type, 
			@WebParam(name = "start")String start,
			@WebParam(name = "size")String size, 
			@WebParam(name = "sign")String sign,
			@WebParam(name = "cond")String cond, 
			@WebParam(name = "apptoken")String apptoken
			);
	/**
	 * <p>
	 * Description:  获取邮件详情
	 * </p>
	 * 
	 * @param name
	 * @param eid
	 * @param sign
	 * @return
	 * 
	 * @since Oct 25, 2012 2:59:48 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String getMailInfoById(@WebParam(name = "yhm")
	String yhm, @WebParam(name = "eid")
	String eid, @WebParam(name = "sign")
	String sign,
	@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>
	 * Description: 删除邮件
	 * </p>
	 * 
	 * @param name
	 * @param type
	 * @param eid
	 * @param sign
	 * @return
	 * 
	 * @since Oct 25, 2012 3:01:20 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String deleteMailByID(@WebParam(name = "yhm")
	String yhm, @WebParam(name = "eid")
	String eid, @WebParam(name = "type")
	String type, @WebParam(name = "sign")
	String sign,@WebParam(name="apptoken") String apptoken);
	
	@WebMethod
	public String updateMailXbByID(
			@WebParam(name = "yhm") String yhm, 
			@WebParam(name = "id") String id, 
			@WebParam(name = "type") String type, 
			@WebParam(name = "sign") String sign,
			@WebParam(name="apptoken") String apptoken
			);
	
	

	/**
	 * <p>
	 * Description:  标记已读
	 * </p>
	 * 
	 * @param name
	 * @param eid
	 * @param sign
	 * @return
	 * 
	 * @since Oct 25, 2012 3:04:07 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String updateMailByID(@WebParam(name = "yhm")
	String yhm, @WebParam(name = "eid")
	String eid, @WebParam(name = "sign")
	String sign,@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>
	 * Description:  写信、回复、全部回复、转发
	 * </p>
	 * 
	 * @param yjid
	 * @param yhm
	 * @param fsrxm
	 * @param sjrxm
	 * @param sjrdm
	 * @param csrxm
	 * @param csrdm
	 * @param title
	 * @param content
	 * @param type
	 * @param sign
	 * @return
	 * 
	 * @since Oct 25, 2012 3:09:28 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String sendMail(@WebParam(name = "yjid")
	String yjid, @WebParam(name = "yhm")
	String yhm, @WebParam(name = "fsrxm")
	String fsrxm, @WebParam(name = "sjrxm")
	String sjrxm, @WebParam(name = "sjrdm")
	String sjrdm, @WebParam(name = "csrxm")
	String csrxm, @WebParam(name = "csrdm")
	String csrdm, @WebParam(name = "title")
	String title, @WebParam(name = "content")
	String content, @WebParam(name = "type")
	String type, @WebParam(name = "sign")
	String sign,@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>
	 * Description:  获取收件人、抄送人根结构
	 * </p>
	 * 
	 * @param name
	 * @param sign
	 * @return
	 * 
	 * @since Oct 25, 2012 3:10:48 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String getFirstDepInfo(@WebParam(name = "yhm")
	String name, @WebParam(name = "sign")
	String sign,@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>
	 * Description:  获取子级收件人、抄送人根结构
	 * </p>
	 * 
	 * @param yhm
	 * @param depnum
	 * @param depname
	 * @param sum
	 * @param sign
	 * @return
	 * 
	 * @since Oct 25, 2012 3:14:15 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String getDepAndUserByDepNum(@WebParam(name = "yhm")
	String yhm, @WebParam(name = "depnum")
	String depnum, @WebParam(name = "depname")
	String depname, @WebParam(name = "sum")
	String sum, @WebParam(name = "sign")
	String sign,@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>
	 * Description:  搜索联系人
	 * </p>
	 * 
	 * @param yhm
	 * @param name
	 * @param sign
	 * @return
	 * 
	 * @since Oct 25, 2012 3:16:01 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String getDepAndUserInfoForSearch(@WebParam(name = "yhm")
	String yhm, @WebParam(name = "name")
	String name, @WebParam(name = "sign")
	String sign,@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>
	 * Description:  获取新邮件消息
	 * </p>
	 * 
	 * @param name
	 * @param sign
	 * @return
	 * 
	 * @since Oct 25, 2012 3:21:23 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String getNewMailCount(@WebParam(name = "yhm")
	String name, @WebParam(name = "sign")
	String sign,@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>
	 * Description:  保存到草稿箱
	 * </p>
	 * 
	 * @param yhm
	 * @param sign
	 * @param yjjd
	 * @param sjrxm
	 * @param sjrdm
	 * @param csrxm
	 * @param csrdm
	 * @param title
	 * @param content
	 * @return
	 * 
	 * @since Oct 25, 2012 3:23:57 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String saveToDraft(@WebParam(name = "yjid")
	String yjid, @WebParam(name = "yhm")
	String yhm, @WebParam(name = "fsrxm")
	String fsrxm, @WebParam(name = "sjrxm")
	String sjrxm, @WebParam(name = "sjrdm")
	String sjrdm, @WebParam(name = "csrxm")
	String csrxm, @WebParam(name = "csrdm")
	String csrdm, @WebParam(name = "title")
	String title, @WebParam(name = "content")
	String content, @WebParam(name = "sign")
	String sign,@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>
	 * Description:  获取草稿邮件
	 * </p>
	 * 
	 * @param name
	 * @param sign
	 * @param yjid
	 * @return
	 * 
	 * @since Oct 25, 2012 3:25:40 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String getDraftInfoById(@WebParam(name = "yhm")
	String name, @WebParam(name = "yjid")
	String yjid, @WebParam(name = "sign")
	String sign,@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>Description: 	获取通讯录详情</p>
	 * @param yhm
	 * @param yhid
	 * @param sign
	 * @return
	 *
	 * @since Oct 25, 2012 5:01:31 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public java.lang.String getAddressInfo(@WebParam(name = "yhm")
	java.lang.String yhm, @WebParam(name = "yhid")
	java.lang.String yhid, @WebParam(name = "sign")
	java.lang.String sign,@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>Description: 	待办事宜列表</p>
	 * @param yhm
	 * @param start
	 * @param size
	 * @param sign
	 * @return
	 *
	 * @since Oct 25, 2012 5:01:57 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public java.lang.String getTodoTaskList(@WebParam(name = "yhm")
	java.lang.String yhm, @WebParam(name = "start")
	String start, @WebParam(name = "size")
	String size, @WebParam(name = "sign")
	java.lang.String sign,@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>Description: 	添加办公日程</p>
	 * @param yhm
	 * @param zt
	 * @param date
	 * @param starttime
	 * @param endtime
	 * @param content
	 * @param sign
	 * @return
	 *
	 * @since Oct 25, 2012 5:02:25 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public java.lang.String addSchedule(@WebParam(name = "yhm")
	java.lang.String yhm, @WebParam(name = "zt")
	java.lang.String zt, @WebParam(name = "date")
	java.lang.String date, @WebParam(name = "starttime")
	java.lang.String starttime, @WebParam(name = "endtime")
	java.lang.String endtime, @WebParam(name = "content")
	java.lang.String content, @WebParam(name = "sign")
	java.lang.String sign,@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>Description: 	办公日程共享人列表详情</p>
	 * @param yhm
	 * @param sign
	 * @return
	 *
	 * @since Oct 25, 2012 5:02:56 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public java.lang.String getSharePerson(@WebParam(name = "yhm")
	java.lang.String yhm, @WebParam(name = "sign")
	java.lang.String sign,@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>Description: 	通知公告列表获取</p>
	 * @param yhm
	 * @param start
	 * @param size
	 * @param sign
	 * @return
	 *
	 * @since Oct 25, 2012 5:03:18 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public java.lang.String getNoticeList(@WebParam(name = "yhm")
	java.lang.String yhm,@WebParam(name = "type") 
	String type,@WebParam(name = "start")
	String start, @WebParam(name = "size")
	String size, @WebParam(name = "sign")
	java.lang.String sign,@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>Description: 	获取通讯录列表</p>
	 * @param yhm
	 * @param updatetime
	 * @param sign
	 * @return
	 *
	 * @since Oct 25, 2012 5:03:35 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public java.lang.String getAddressList(@WebParam(name = "yhm")
	java.lang.String yhm, @WebParam(name = "updatetime")
	java.lang.String updatetime, @WebParam(name = "sign")
	java.lang.String sign,@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>Description: 	删除办公日程</p>
	 * @param yhm
	 * @param id
	 * @param sign
	 * @return
	 *
	 * @since Oct 25, 2012 5:03:48 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public java.lang.String deleteSchedule(@WebParam(name = "yhm")
	java.lang.String yhm, @WebParam(name = "id")
	java.lang.String id, @WebParam(name = "sign")
	java.lang.String sign,@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>Description: 	办公日程列表详情</p>
	 * @param yhm
	 * @param yhid
	 * @param start
	 * @param size
	 * @param sign
	 * @return
	 *
	 * @since Oct 25, 2012 5:04:57 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public java.lang.String getScheduleList(@WebParam(name = "yhm")
	java.lang.String yhm, @WebParam(name = "yhid")
	java.lang.String yhid, @WebParam(name = "start")
	String start, @WebParam(name = "size")
	String size, @WebParam(name = "sign")
	java.lang.String sign,@WebParam(name="apptoken") String apptoken);
	/**
	 * <p>Description: 	办公日程详情</p>
	 * @param yhm
	 * @param id
	 * @param sign
	 * @return
	 *
	 * @since Oct 25, 2012 5:06:51 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public java.lang.String getScheduleInfo(@WebParam(name = "yhm")
	java.lang.String yhm, @WebParam(name = "id")
	java.lang.String id, @WebParam(name = "sign")
	java.lang.String sign,@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>Description: 	我的会议、全体会议列表获取</p>
	 * @param yhm
	 * @param type
	 * @param start
	 * @param size
	 * @param sign
	 * @return
	 *
	 * @since Oct 25, 2012 5:07:09 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public java.lang.String getNewConferenceList(@WebParam(name = "yhm")
	java.lang.String yhm, @WebParam(name = "type")
	java.lang.String type, @WebParam(name = "start")
	String start, @WebParam(name = "size")
	String size, @WebParam(name = "sign")
	java.lang.String sign,@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>Description: 	获取我的会议、全体会议详情</p>
	 * @param yhm
	 * @param id
	 * @param sign
	 * @return
	 *
	 * @since Oct 25, 2012 5:07:22 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public java.lang.String getConferenceInfo(@WebParam(name = "yhm")
	java.lang.String yhm, @WebParam(name = "id")
	java.lang.String id, @WebParam(name = "sign")
	java.lang.String sign,@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>Description: 	通知公告详情</p>
	 * @param yhm
	 * @param id
	 * @param sign
	 * @return
	 *
	 * @since Oct 25, 2012 5:07:39 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public java.lang.String getNoticeInfo(@WebParam(name = "yhm")
	java.lang.String yhm, @WebParam(name = "id")
	java.lang.String id, @WebParam(name = "sign")
	java.lang.String sign,@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>Description: </p>
	 * @param key
	 * @return
	 *
	 * @since Oct 25, 2012 5:07:56 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public java.lang.String getSignKey(@WebParam(name = "key")
	java.lang.String key,@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>Description: OA登入</p>
	 * @param yhm
	 * @param sign
	 * @return
	 *
	 * @since Nov 12, 2012 5:20:39 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String checkLogin(@WebParam(name = "yhm")String yhm,@WebParam(name = "sign")String sign,@WebParam(name="apptoken") String apptoken);
	/**
	 * <p>Description: OA附件下载</p>
	 * @param id
	 * @return
	 *
	 * @since Nov 12, 2012 5:21:09 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String getFileModel(@WebParam(name = "id") String id,@WebParam(name="apptoken") String apptoken);
	/**
	 * <p>Description: 待办事宜详情</p>
	 * @param yhm
	 * @param sign
	 * @return
	 *
	 * @since Nov 12, 2012 5:20:39 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String getTableInfo(@WebParam(name = "yhm")String yhm,@WebParam(name = "id")String id, @WebParam(name = "tablename")String tablename,@WebParam(name = "sign")String sign,@WebParam(name="apptoken") String apptoken);
	/**
	 * <p>Description: 待办事宜提交</p>
	 * @param yhm
	 * @param sign
	 * @return
	 *
	 * @since Nov 12, 2012 5:20:39 PM
	 * @author huangzhaoxia
//	 */
	@WebMethod
	public String doSubmitFlow(@WebParam(name = "yhm")String yhm,@WebParam(name = "id")String id,
    		@WebParam(name = "nextid")java.lang.String nextid,
    		@WebParam(name = "nextuser")java.lang.String nextuser, 
    		@WebParam(name = "comment")String comment,
    		@WebParam(name = "sign")String sign,
    		@WebParam(name="apptoken") String apptoken
    		);
	
	/**待办事宜提交认证
	 * <p>Description: </p>
	 * @param yhm
	 * @param id
	 * @param sign
	 * @return
	 *
	 * @since Dec 7, 2012 4:55:42 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String doSubmitBefore(
			@WebParam(name = "yhm")java.lang.String yhm,
			@WebParam(name = "id")java.lang.String id, 
			@WebParam(name = "sign")java.lang.String sign,
			@WebParam(name="apptoken") String apptoken
			);
	/**
	 * <p>Description: 待办事宜提交新方法</p>
	 * @param yhm
	 * @param sign
	 * @return
	 *
	 * @since Nov 12, 2012 5:20:39 PM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String doSubmitFlowNew(@WebParam(name = "yhm")String yhm,@WebParam(name = "id")String id,
    		@WebParam(name = "nextid")java.lang.String nextid,
    		@WebParam(name = "nextuser")java.lang.String nextuser, 
    		@WebParam(name = "comment")String comment,
    		@WebParam(name = "tablename")java.lang.String tablename,
    		@WebParam(name = "ftzd")java.lang.String ftzd,
    		@WebParam(name = "ftfs")java.lang.String ftfs,
    		@WebParam(name = "zid")java.lang.String zid,
    		 @WebParam(name = "sign")java.lang.String sign,
    		 @WebParam(name="apptoken") String apptoken
    		 );
	
	/**
	 * 获取待办事宜详情
	 * @param yhm
	 * @param id
	 * @param sign
	 * @return
	 */
	@WebMethod
	public java.lang.String getFlowInfo(
			@WebParam(name = "yhm") java.lang.String yhm,
			@WebParam(name = "id") java.lang.String id,
			@WebParam(name = "sign") java.lang.String sign,
			@WebParam(name="apptoken") String apptoken
			);
	
	
	
	/**
	 * <p>Description: 获取用户权限</p>
	 * @param yhm  用户名
	 * @param sign 密钥
	 * @return
	 *
	 * @since 2014-8-21 上午09:35:38
	 * @author yangz
	 */
	@WebMethod
	public String getSendInfo(
			@WebParam(name = "yhm") String yhm,
			@WebParam(name = "sign") String sign,
			@WebParam(name="apptoken") String apptoken
	);
	
	/**
	 * <p>Description: 公告类别方法</p>
	 * @param yhm  用户名
	 * @param sign 密钥
	 * @return
	 *
	 * @since 2014-8-21 上午11:39:07
	 * @author yangz
	 */
	@WebMethod
	public String getNoticType(
			@WebParam(name = "yhm") String yhm,
			@WebParam(name = "sign") String sign,
			@WebParam(name="apptoken") String apptoken
	);
	
	/**
	 * <p>Description: 检测待办事宜是否可以回退</p>
	 * @param yhm   用户名
	 * @param id    id(和流程提交方法中的ID是同一个)
	 * @param sign  秘钥
	 * @return
	 *
	 * @since 2014-12-16 下午02:26:52
	 * @author yangz
	 */
	@WebMethod
	public String getDoBackBefore(@WebParam(name = "yhm") String yhm,@WebParam(name="id") String id,
			@WebParam(name = "sign") String sign,@WebParam(name="apptoken") String apptoken);
	
	
	/**
	 * <p>Description: 待办事宜回退</p>
	 * @param yhm      用户名
	 * @param id       id
	 * @param comment  意见
	 * @param sign     秘钥
	 * @return
	 *
	 * @since 2014-12-16 下午02:33:49
	 * @author yangz
	 */
	@WebMethod
	public String getdoBackFlow(@WebParam(name = "yhm") String yhm,@WebParam(name="id") String id,@WebParam(name="comment") String comment,
			@WebParam(name = "sign") String sign,@WebParam(name="apptoken") String apptoken);
	

	/**
	 * <p>Description: 会议通知列表</p>
	 * @param yhm
	 * @param start
	 * @param size
	 * @param sign
	 * @return
	 *
	 * @since 2015-2-2 下午04:39:23
	 * @author yangz
	 */
	@WebMethod
	public String getMeetNoticeList(
			@WebParam(name="yhm") String yhm,
			@WebParam(name="start") String start,
			@WebParam(name="size") String size,
			@WebParam(name="sign") String sign,
			@WebParam(name="apptoken") String apptoken
			);
	
	
	/**
	 * <p>Description: 会议通知详情</p>
	 * @param yhm
	 * @param id
	 * @param sign
	 * @return
	 *
	 * @since 2015-2-2 下午04:46:31
	 * @author yangz
	 */
	@WebMethod
	public String getMeetNoticeInfo(
			@WebParam(name="yhm") String yhm,
			@WebParam(name="id") String id,
			@WebParam(name="sign") String sign,
			@WebParam(name="apptoken") String apptoken
			);
	
	/**
	 * <p>Description: 获取领导常用审批意见 </p>
	 * @param yhm
	 * @param sign
	 * @return
	 *
	 * @since 2015-6-16 上午10:41:20
	 * @author yangz
	 */
	@WebMethod
	public String getLdcyyjbList(
			@WebParam(name="yhm") String yhm,
			@WebParam(name="sign") String sign,
			@WebParam(name="apptoken") String apptoken
			);
	
	
	/**
	 * <p>Description: 获取已办事宜列表</p>
	 * @param yhm
	 * @param start
	 * @param size
	 * @param sign
	 * @return
	 *
	 * @since 2015-8-25 上午10:52:22
	 * @author yangz
	 */
	@WebMethod
	public String getDoneTaskList(
			@WebParam(name="yhm") String yhm,
			@WebParam(name="start") String start,
			@WebParam(name="size") String size,
			@WebParam(name="sign") String sign,
			@WebParam(name="apptoken") String apptoken
			);
	
	/**
	 * <p>Description: 获取办结事宜列表</p>
	 * @param yhm
	 * @param start
	 * @param size
	 * @param sign
	 * @return
	 */
	@WebMethod
	public String getSoluteTaskList(
			@WebParam(name="yhm") String yhm,
			@WebParam(name="start") String start,
			@WebParam(name="size") String size,
			@WebParam(name="sign") String sign,
			@WebParam(name="apptoken") String apptoken
		);
	
	/**
	 * <p>Description: 确认文件传阅</p>
	 * @param yhm 当前登录的用户名
	 * @param id wjcysjb中记录的id
	 * @param nr 确认内容
	 * @param sign 公钥
	 * @param apptoken apptoken
	 * @return
	 */
	@WebMethod
	public String confirmWjcy(
			@WebParam(name="yhm") String yhm,
			@WebParam(name="id") String id,
			@WebParam(name="nr") String nr,
			@WebParam(name="sign") String sign,
			@WebParam(name="apptoken") String apptoken);
	
	/**
	 * <p>Description: 根据收件id获取原发件并封装新的发件</p>
	 * @param yhm 当前登录的用户名
	 * @param id wjcysjb中记录的id
	 * @param sign 公钥
	 * @param apptoken apptoken
	 * @return
	 */
	@WebMethod
	public String getZfNewFj(
			@WebParam(name="yhm") String yhm,
			@WebParam(name="id") String id,
			@WebParam(name="sign") String sign,
			@WebParam(name="apptoken") String apptoken);
	

	/**
	 * <p>Description: 确认转发文件传阅</p>
	 * @param yhm 当前登录的用户名
	 * @param id wjcysjb中记录的id
	 * @param sign 公钥
	 * @param apptoken apptoken
	 * @return
	 */
	@WebMethod
	public String saveWjcyZf(
			@WebParam(name="yhm") String yhm,
			@WebParam(name="id") String id,
			@WebParam(name="zfyj") String zfyj,
			@WebParam(name="zfdx") String zfdx,
			@WebParam(name="yxqx") String yxqx,
			@WebParam(name="sign") String sign,
			@WebParam(name="apptoken") String apptoken);
	
	/**
	 * <p>Description:确认会议是否出席</p>
	 * @param yhm 当前登录的用户名
	 * @param id 此getConferenceNoticeList接口的id
	 * @param conferenceid 此getConferenceNoticeList的conferenceid
	 * @param reason 原因（不出席时，原因必填）
	 * @param ispresent 只能填y或n（y表示出席；n表示不出席）
	 * @param time 当前时间（格式2018-04-10 10:18）
	 * @param sign 公钥
	 * @param apptoken
	 * @return
	 */
	@WebMethod
	public String saveConferenceInformfeedback(
			@WebParam(name="yhm") String yhm,
			@WebParam(name="id") String id,
			@WebParam(name="conferenceid") String conferenceid,
			@WebParam(name="reason") String reason,
			@WebParam(name="ispresent") String ispresent,
			@WebParam(name="time") String time,
			@WebParam(name="sign") String sign,
			@WebParam(name="apptoken") String apptoken);
	
	/**
	 * <p>Description:获取会议通知列表</p>
	 * @param yhm 当前登录的用户名
	 * @param type 0：未确认；1已回复
	 * @param start 页码
	 * @param size 每页数量
	 * @param sign 公钥
	 * @param apptoken
	 * @return
	 */
	@WebMethod
	public String getConferenceNoticeList(
			@WebParam(name="yhm") String yhm,
			@WebParam(name="type") String type,
			@WebParam(name="start") String start,
			@WebParam(name="size") String size,
			@WebParam(name="sign") String sign,
			@WebParam(name="apptoken") String apptoken);
	
	/**
	 * <p>Description:附件上传</p>
	 * @param yhm 当前登录的用户名
	 * @param zid 主id   这里传空串,oa处理
	 * @param wjmc 文件名称
	 * @param operType 文件上传类型    这里传mail
	 * @param file 文件数据
	 * @param sign 公钥
	 * @param apptoken
	 * @return
	 */
	@WebMethod
	public String fjUploadByType(
			@WebParam(name="yhm") String yhm,
			@WebParam(name="file") FileModel file,
			@WebParam(name="sign") String sign,
			@WebParam(name="apptoken") String apptoken);
	
	
	/**
	 * <p>
	 * Description: 获取未读邮件列表
	 * </p>
	 * 
	 * @return
	 * 
	 * @since Oct 22, 2017 1:47:47 PM
	 * @author huangzhaoxia
	 */
	public String getNoReadMail(
			@WebParam(name = "yhm")String yhm, 
			@WebParam(name = "start")String start,
			@WebParam(name = "size")String size, 
			@WebParam(name = "sign")String sign,
			@WebParam(name = "cond")String cond, 
			@WebParam(name = "apptoken")String apptoken
			);
	
	/**
	 * 流程自由退回操作前判断
	 * yhm:当前登录的用户名
     * id：grgzb编号
     * sign:公钥
	 * @return
	 */
	public String doBackBeforeByReturnNode(
			@WebParam(name = "yhm")String yhm, 
			@WebParam(name = "id")String id,
			@WebParam(name = "sign")String sign,
			@WebParam(name = "apptoken")String apptoken
			);
	
	
	/**
	 * 流程自由退回操作
	 * yhm:当前登录的用户名
	 * id：grgzb编号
     * returnNode 待退回的环节编号
     * returnSfsfh 待退回环节的sfsfh值
     * comment 意见
     * sign:公钥
	 */
	public String doBackFlowByReturnNode(
			@WebParam(name = "yhm")String yhm, 
			@WebParam(name = "id")String id,
			@WebParam(name = "returnNode")String returnNode,
			@WebParam(name = "returnSfsfh")String returnSfsfh, 
			@WebParam(name = "comment")String comment,
			@WebParam(name = "sign")String sign,
			@WebParam(name = "apptoken")String apptoken
			);
	
	/**
	 * 获取当前环节可编辑的字段列表及对应值
	 * @param yhm 当前登录的用户名
	 * @param id  grgzb编号
	 * @param tableName 表名
	 * @param zid 主键属性
	 * @param sign 公钥
	 * @return
	 */
	public String getCurrentSectionFiled(
			@WebParam(name = "yhm")String yhm, 
			@WebParam(name = "id")String id, 
			@WebParam(name = "tableName")String tableName,
			@WebParam(name = "zid")String zid,
			@WebParam(name = "sign")String sign,
			@WebParam(name = "apptoken")String apptoken
	        );
	
	/**
	 * 更新业务表数据
	 * @param yhm 当前登录的用户名
	 * @param id  grgzb编号
	 * @param tableName 表名
	 * @param paramJson 待修改的字段描述和值
	 * @param sign 公钥
	 * @return
	 */
	public String updateCurrentSectionFiled(
			@WebParam(name = "yhm")String yhm, 
			@WebParam(name = "id")String id, 
			@WebParam(name = "tableName")String tableName,
			@WebParam(name = "paramJson")String paramJson,
			@WebParam(name = "sign")String sign,
			@WebParam(name = "apptoken")String apptoken
	        );
	

/*	*//**
	 * 提交流程
	 * @return
	 *//*
	@WebMethod
	public String doSubmitFlowoneNew(
			@WebParam(name="yhm")String yhm, 
			@WebParam(name="id")String id, 
			@WebParam(name="nextid")String nextid,
			@WebParam(name="nextuser")String nextuser,
			@WebParam(name="comment")String comment,
			@WebParam(name="tablename")String tablename,
			@WebParam(name="ftzd")String ftzd,
			@WebParam(name="ftfs")String ftfs,
			@WebParam(name="zid")String zid,
			@WebParam(name="sign ")String sign,
			@WebParam(name="sffsdx")String sffsdx
	        );*/
	/*
	@WebMethod
	public String doSubmitFlowoneNew(@WebParam(name = "yhm")String yhm,
			@WebParam(name = "id")String id,
    		@WebParam(name = "nextid")java.lang.String nextid,
    		@WebParam(name = "nextuser")java.lang.String nextuser, 
    		@WebParam(name = "comment")String comment,
    		@WebParam(name = "tablename")java.lang.String tablename,
    		@WebParam(name = "ftzd")java.lang.String ftzd,
    		@WebParam(name = "ftfs")java.lang.String ftfs,
    		@WebParam(name = "zid")java.lang.String zid,
    		@WebParam(name = "sign")java.lang.String sign,
    		@WebParam(name = "sffsdx")java.lang.String sffsdx,
    		@WebParam(name="apptoken") String apptoken
    		);
	*/
	
	@WebMethod
	public String getConferenceNoticeListAndCond(@WebParam(name = "yhm")String yhm,
			@WebParam(name = "type")String type,
    		@WebParam(name = "check")java.lang.String check,
    		@WebParam(name = "start")java.lang.String start, 
    		@WebParam(name = "size")String size,
    		@WebParam(name = "sign")java.lang.String sign,
    		@WebParam(name="apptoken") String apptoken
    		);
	
	@WebMethod
	public String getNoticeListAndCond(@WebParam(name = "yhm")String yhm,
			@WebParam(name = "type")String type,
    		@WebParam(name = "check")java.lang.String check,
    		@WebParam(name = "start")java.lang.String start, 
    		@WebParam(name = "size")String size,
    		@WebParam(name = "sign")java.lang.String sign,
    		@WebParam(name="apptoken") String apptoken
    		);
	
	
	@WebMethod
	public String getFfwjList(@WebParam(name = "yhm")String yhm,
			@WebParam(name = "type")String type,
			@WebParam(name = "start")java.lang.String start, 
			@WebParam(name = "size")String size,
			@WebParam(name = "sign")java.lang.String sign,
			@WebParam(name="apptoken") String apptoken
	);
	
	
	@WebMethod
	public String getZxgwInfoById(@WebParam(name = "yhm")String yhm,
			@WebParam(name = "id")String id,
			@WebParam(name = "type")String type,
			@WebParam(name = "sign")java.lang.String sign,
			@WebParam(name="apptoken") String apptoken
	);
	
	
}
