package com.mobileWebUrl.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


/**
 * <p>Description: 移动调用WebUrl接口</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2014-4-4 上午11:02:07
 * @author yangz
 * @version 1.0
 */
@WebService
@XmlAccessorType(XmlAccessType.FIELD)
public interface IMobileURLService {
	/**
	 * <p>Description: 移动学工接口</p>
	 * @return
	 *
	 * @since 2014-4-4 上午11:05:25
	 * @author yangz
	 */
	@WebMethod
	public String getMobileXGURL(@WebParam(name="apptoken") String apptoken);
	
	/**
	 * <p>Description: 移动学习</p>
	 * @return
	 *
	 * @since 2014-4-8 上午08:34:29
	 * @author yangz
	 */
	@WebMethod
	public String getMoblieXXURL(@WebParam(name="apptoken") String apptoken);
	
	
	/**
	 * <p>Description: 一周会议</p>
	 * @return
	 *
	 * @since 2014-4-10 上午10:23:31
	 * @author yangz
	 */
	@WebMethod
	public String getMoblieYZHYURL(@WebParam(name="apptoken") String apptoken);
	
	
	/**
	 * <p>Description: 失物招领</p>
	 * @return
	 *
	 * @since 2014-4-24 上午11:20:34
	 * @author yangz
	 */
	@WebMethod
	public String getMobileSWZLURL(@WebParam(name="apptoken") String apptoken);
	
	/**
	 * <p>Description: 交流社区</p>
	 * @return
	 *
	 * @since 2014-4-28 下午04:56:45
	 * @author yangz
	 */
	@WebMethod
	public String getMobileJLSQURL(@WebParam(name="apptoken") String apptoken);
	
	
	/**
	 * <p>Description: 移动选课</p>
	 * @return
	 *
	 * @since 2014-4-29 下午02:40:38
	 * @author yangz
	 */
	@WebMethod
	public String getMoblieXKURL(@WebParam(name="apptoken") String apptoken);
	
	
	/**
	 * <p>Description: 等级考试报名</p>
	 * @return
	 *
	 * @since 2014-4-29 下午02:35:07
	 * @author yangz
	 */
	@WebMethod
	public String getMobileDJKSBMURL(@WebParam(name="apptoken") String apptoken);
	
	
	/**
	 * <p>Description: 教师调停课</p>
	 * @return
	 *
	 * @since 2014-4-29 下午02:38:11
	 * @author yangz
	 */
	@WebMethod
	public String getMobileTTKSQURL(@WebParam(name="apptoken") String apptoken);
	
	
	/**
	 * <p>Description: 教师学生选课情况查询</p>
	 * @return
	 *
	 * @since 2014-4-29 下午02:39:03
	 * @author yangz
	 */
	@WebMethod
	public String getMobileXKQKCXURL(@WebParam(name="apptoken") String apptoken);
	
	/**
	 * <p>Description: 部门黄页</p>
	 * @return
	 *
	 * @since 2014-5-8 下午05:08:34
	 * @author yangz
	 */
	@WebMethod
	public String getMoblieXNHYURL(@WebParam(name="apptoken") String apptoken);
	
	
	/**
	 * <p>Description: 招生简章</p>
	 * @return
	 *
	 * @since 2014-5-26 上午08:58:14
	 * @author yangz
	 */
	@WebMethod
	public String getMoblieZSJZURL(@WebParam(name="apptoken") String apptoken);
	
	/**
	 * <p>Description: 最新公文</p>
	 * @return
	 *
	 * @since 2014-6-26 上午11:49:44
	 * @author yangz
	 */
	@WebMethod
	public String getMoblieZXGWURL(@WebParam(name="apptoken") String apptoken);
	
	/**
	 * <p>Description: 领导讲话</p>
	 * @return
	 *
	 * @since 2014-6-27 上午11:29:52
	 * @author yangz
	 */
	@WebMethod
	public String getMoblieLDJHURL(@WebParam(name="apptoken") String apptoken);
	
	
	/**
	 * <p>Description: 学院共享</p>
	 * @return
	 *
	 * @since 2014-6-27 上午11:30:46
	 * @author yangz
	 */
	@WebMethod
	public String getMobileXYGXURL(@WebParam(name="apptoken") String apptoken);
	
	
	/**
	 * <p>Description: 日程管理</p>
	 * @return
	 *
	 * @since 2014-11-13 上午09:43:40
	 * @author yangz
	 */
	@WebMethod
	public String getMobileRCGLURL(@WebParam(name="apptoken") String apptoken);
	
	
	
	/**
	 * <p>Description: 移动迎新</p>
	 * @return
	 *
	 * @since 2014-11-17 下午02:01:38
	 * @author yangz
	 */
	@WebMethod
	public String getMobileYDYXURL(@WebParam(name="apptoken") String apptoken);
	
	
	/**
	 * <p>Description: 图书馆</p>
	 * @return
	 *
	 * @since 2014-11-19 上午11:23:05
	 * @author yangz
	 */
	@WebMethod
	public String getMobileTSG(@WebParam(name="apptoken") String apptoken);
	
	
	/**
	 * <p>Description: APK下载地址</p>
	 * @return
	 *
	 * @since 2015-1-28 上午09:07:13
	 * @author yangz
	 */
	@WebMethod
	public String getMobileAPKDOWNURL(@WebParam(name="apptoken") String apptoken);
	
	
	/**
	 * <p>Description: 单点登录到腾讯邮箱</p>
	 * @param userName
	 * @return
	 *
	 * @since 2015-2-11 上午08:53:17
	 * @author yangz
	 */
	@WebMethod
	public String getMobileTencentMailURL(@WebParam(name="userName") String userName,@WebParam(name="apptoken") String apptoken);
	
	
	/**
	 * <p>Description: 校务公开</p>
	 * @return
	 *
	 * @since 2015-4-24 上午10:28:23
	 * @author yangz
	 */
	@WebMethod
	public String getMobileXWGKURL(@WebParam(name="apptoken") String apptoken);
	
	/**
	 * <p>Description: 通知公告(重要提示)</p>
	 * @return
	 *
	 * @since 2015-4-24 上午10:44:06
	 * @author yangz
	 */
	@WebMethod
	public String getMobileTZGGURL(@WebParam(name="apptoken") String apptoken);
	
	
	/**
	 * <p>Description: 办事指南（公务活动）</p>
	 * @return
	 *
	 * @since 2015-4-24 下午02:06:42
	 * @author yangz
	 */
	@WebMethod
	public String getMobileBSZNURL(@WebParam(name="apptoken") String apptoken);
	
	
	/**
	 * <p>Description: 学院动态（工作通报）</p>
	 * @return
	 *
	 * @since 2015-4-24 下午02:08:50
	 * @author yangz
	 */
	@WebMethod
	public String getMobileXYDTURL(@WebParam(name="apptoken") String apptoken);
	
	
	/**
	 * <p>Description: 规章制度(学院简报)</p>
	 * @return
	 *
	 * @since 2015-4-24 下午02:09:49
	 * @author yangz
	 */
	@WebMethod
	public String getMobileGZZDURL(@WebParam(name="apptoken") String apptoken);
	
	
	/**
	 * <p>Description: 院务公开（资源下载）</p>
	 * @return
	 *
	 * @since 2015-4-24 下午02:10:38
	 * @author yangz
	 */
	@WebMethod
	public String getMobileYWGKURL(@WebParam(name="apptoken") String apptoken);
	
	/**
	 * <p>Description: 工作量统计</p>
	 * @return
	 *
	 * @since 2015-6-19 下午03:08:33
	 * @author yangz
	 */
	@WebMethod
	public String getMobileGZLTJ(@WebParam(name="apptoken") String apptoken);

	
	/**
	 * <p>Description: 调停课统计</p>
	 * @return
	 *
	 * @since 2015-7-1 下午04:10:32
	 * @author yangz
	 */
	@WebMethod
	public String getMobileTTKSQTJURL(@WebParam(name="apptoken") String apptoken);
}
