package com.newmobile.login.service;

import java.io.InputStream;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.springframework.web.multipart.commons.CommonsMultipartFile;



/**
 * <p>Description: 新移动登陆接口</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2015-7-23 下午03:16:15
 * @author zyang
 * @version 1.0
 */
@WebService
public interface IMobileLoginXMLService {
	
	/**
	 * 获取所有服务的未读数
	* @author: zhangxu
	* @Title: getAllServiceNotreading 
	* @Description: 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String getAllServiceNotreadingByUserName(
			@WebParam(name="apptoken")String apptoken,
			@WebParam(name="username")String username
	);
	
	/**
	 * 获取所有服务的未读数
	* @author: zhangxu
	* @Title: getAllServiceNotreading 
	* @Description: 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String getAllServiceNotreading(
			@WebParam(name="apptoken")String apptoken
	);
	
	
	/**
	 * 
	* @author: zhangxu
	* @Title: visitByUserId 
	* @Description: app使用每天累计统计，在线人数分时段统计
	* @param @param userId 用户id
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String visitByUserId(
			@WebParam(name="userId")String userId,
			@WebParam(name="apptoken")String apptoken
			);
	
	/**
	 * 获取所有第三方绑定关系
	 * @param userId 用户id
	 * @param apptoken
	 * @return
	 */
	public String getThirdPartyList(
			@WebParam(name="userId")String userId,
			@WebParam(name="apptoken")String apptoken
			);
	/**
	 * 第三方登录
	 * @param openId 第三方唯一性标识
	 * @param sign 后台登录秘钥
	 * @return
	 */
	public String thirdPartyLogin(
			@WebParam(name="openId")String openId, 
			@WebParam(name="sign")String sign
			);
	/**
	 * 解绑第三方授权登录
	 * @param userId 用户id
	 * @param type 第三方类型，qq为1，微信为2，新浪微3
	 * @param apptoken
	 * @return
	 */
	public String deleteThirdParty(
			@WebParam(name="userId")String userId,
			@WebParam(name="type")String type,
			@WebParam(name="apptoken")String apptoken
			);
	/**
	 * 第三方授权
	 * @param userId 用户id
	 * @param openId 第三方唯一性标识
	 * @param type   第三方类型，qq为1，微信为2，新浪微3
	 * @param apptoken
	 * @return
	 */
	public String submitThirdParty(
			@WebParam(name="userId")String userId,
			@WebParam(name="openId")String openId, 
			@WebParam(name="type")String type,
			@WebParam(name="apptoken")String apptoken);
	
	/**
	 * 删除备忘录
	 * @param memoFileNameList  备忘录名称列表,逗号","隔开
	 * @return
	 */
	public String deleteMyMemoList(
			@WebParam(name="memoFileNameList")String memoFileNameList,
			@WebParam(name="apptoken")String apptoken
			);
	
	
	/**
	 * 提交个人备忘录类别
	 * @param userId 用户id
	 * @param memoCatalogNameList 备忘录类别集合
	 * @param apptoken 秘钥
	 * @return
	 */
	public String submitMemoCatalogList(
			@WebParam(name="userId")String userId,
			@WebParam(name="memoCatalogNameList")String memoCatalogNameList, 
			@WebParam(name="memoCatalogColorList")String memoCatalogColorList,
			@WebParam(name="apptoken")String apptoken
			);
	/**
	 * 获取备忘录类别列表
	 * @param userId 用户id
	 * @param apptoken 秘钥
	 * @return
	 */
	public String getMemoCatalogList(
			@WebParam(name="userId")String userId,
			@WebParam(name="apptoken")String apptoken
			);
	/**
	 * 获取备忘录列表
	 * @param userId
	 * @param start
	 * @param size
	 * @param title
	 * @param memoCatalogName
	 * @param apptoken 秘钥
	 * @return
	 */
	public String getMyMemoList(
			@WebParam(name="userId")String userId,
			@WebParam(name="start")String start, 
			@WebParam(name="size")String size,
			@WebParam(name="title")String title,
			@WebParam(name="memoCatalogName")String memoCatalogName,
			@WebParam(name="apptoken")String apptoken
			);
	
	/**
	 * 获取消息推送列表
	 * @param userId 用户id
	 * @param start  开始页面
	 * @param size   每页数量
	 * @param apptoken 秘钥
	 * @return
	 */
	public String PushMsgList(
			@WebParam(name="userId")String userId, 
			@WebParam(name="start")String start, 
			@WebParam(name="size")String size,
			@WebParam(name="apptoken")String apptoken
			);
	
	/**
	 * 发布失物招领
	 * @param pulishuser	发布者id
	 * @param title			失物招领主题
	 * @param place			失物招领放置位置
	 * @param content		详情内容描述
	 * @return
	 */
	public String publishLossObject(@WebParam(name="pulishuser")String pulishuser, @WebParam(name="title")String title,
			@WebParam(name="place")String place,@WebParam(name="content")String content,@WebParam(name="apptoken")String apptoken
			,@WebParam(name="flag")String flag);
	
	/**
	 * 获取失物招领列表
	 * @param userid	发布者名称(如果为空则查询所有)
	 * @param title	检索内容(根据主题检索)
	 * @param isover	是否已招领
	 * @param start	开始页码
	 * @param isover 每页多少条
	 * @return
	 */
	public String getLossObjectList(@WebParam(name="userid")String userid, @WebParam(name="title")String title, 
			@WebParam(name="isover")String isover,@WebParam(name="start")String start, @WebParam(name="size")String size
			,@WebParam(name="apptoken")String apptoken);
	
	/**
	 * 版本更新验证
	 * @param imei
	 * @param imsi
	 * @param sysinfo
	 * @param ua
	 * @param phonum
	 * @param account
	 * @param v
	 * @return
	 */
	public String versionCompare(@WebParam(name="imei")String imei, @WebParam(name="imsi")String imsi, 
			@WebParam(name="sysinfo")String sysinfo,@WebParam(name="ua")String ua, 
			@WebParam(name="phonum")String phonum, @WebParam(name="account")String account, @WebParam(name="v")String v);
	
	/**
	 * 获取通用服务
	 * @return
	 */
	public String getCommonService();
	
	/**
	 * 获取地图数据
	 * @return
	 */
	public String getMapList();
	
	/**
	 * 提交问卷答案
	 * @param userId
	 * @param xml
	 * @param apptoken
	 * @return
	 */
	public String submitExamAnswer(@WebParam(name="userName")String userName,@WebParam(name="answer")String answer,@WebParam(name="apptoken")String apptoken);
	/**
	 * 获取问卷
	 * @param userName
	 * @param start
	 * @param size
	 * @param apptoken
	 * @return
	 */
	public String getExamList(@WebParam(name="userName")String userName,@WebParam(name="start")String start,
			@WebParam(name="size") String size,@WebParam(name="apptoken")String apptoken);
	/**
	 * 提交发布问卷
	 * @param userName
	 * @param json
	 * @param apptoken
	 * @return
	 */
	public String submitExam(@WebParam(name="userName")String userName,@WebParam(name="json")String json,
			@WebParam(name="apptoken")String apptoken);
	
	/**
	 * 获取二维码
	 * @return
	 */
	public String getQRcode(@WebParam(name="apptoken")String apptoken);
	
	/**
	 * 获取当前学期周
	 * @param ymd
	 * @param apptoken
	 * @return
	 */
	public String getTermWeek(@WebParam(name="ymd")String ymd,@WebParam(name="apptoken")String apptoken);
	
	
	/**
	 * 根据服务编码对应用做统计
	 * @param fwbm
	 * @return
	 */
	public String visitsByFwbm(@WebParam(name="fwbm")String fwbm,@WebParam(name="apptoken")String apptoken);
	
	/**
	 * 根据服务编码和学校编码对应用做统计，只在公司服务器接收
	 * @param fwbm
	 * @return
	 */
	public String insertVisitsToZFByFwbm(
			@WebParam(name="fwbm")String fwbm,
			@WebParam(name="schoolCode")String schoolCode,
			@WebParam(name="apptoken")String apptoken
			);
	
	/**
	 * app端进入app引导页调用此接口做安装统计
	 * @return
	 */
	public String installsCount();
	
	/**
	 * 获取微博列表
	 * @param start获取哪一页
	 * @param size每页多少个
	 * @return
	 */
	public String getWeiBoList(@WebParam(name="pageindex")String pageindex
								, @WebParam(name="size")String size
								,@WebParam(name="apptoken")String apptoken);
	
	/**
	 * 根据微博编号获取相应微博所发过得微博
	 * @param wbbh
	 * @return
	 */
	public String getUserTimeLine(@WebParam(name="wbbh")String wbbh,
									@WebParam(name="pageindex")String pageindex
									,@WebParam(name="apptoken")String apptoken);
	
	/**
	 * 一卡通余额查询
	 * @param userid 工号
	 * @param strkey 秘钥
	 * @param apptoken 个人密钥
	 * @return
	 */
	String getocbalance(@WebParam(name="userName")String userName
						,@WebParam(name="strKey")String strKey
						,@WebParam(name="apptoken") String apptoken	);
	
	/**
	 * 一卡通消费查询
	 * @param detailtype 查询的类型
	 * @param ocid 一卡通卡号
	 * @param startdate 查询时间起始日期
	 * @param enddate 查询时间结束日期
	 * @param pageindex 当前页号
	 * @param pagesize 一页个数
	 * @param strkey 秘钥
	 * @param apptoken 秘钥
	 * @return
	 */
	String getocdetail(@WebParam(name="detailtype")String detailtype,@WebParam(name="ocid")String ocid,
					@WebParam(name="startdate")String startdate,@WebParam(name="enddate")String enddate,
					@WebParam(name="pageindex")String pageindex,@WebParam(name="pagesize")String pagesize,
					@WebParam(name="strkey")String strkey,@WebParam(name="apptoken") String apptoken);

	
	/**
     * 获取推荐的新闻
     * @param size 获取多少个推荐新闻，按时间来排序获取
     * @return
     */
    public String getMhRecommendPage(@WebParam(name="size")String size);
	
	
	/**
	 * <p>Description: Loading时获取信息</p>
	 * @return
	 *
	 * @since 2015-7-23 下午03:18:44
	 * @author zyang
	 */
	@WebMethod
	public String getMobileAppType();
	
	
	/**
	 * <p>Description: 获取首页推荐位新闻</p>
	 * @param start
	 * @param size
	 * @return
	 *
	 * @since 2015-7-30 下午03:46:08
	 * @author zyang
	 */
	/*@WebMethod
	public String getMhRecommendPage(
			@WebParam(name="categoryId") String categoryId,
			@WebParam(name="start") String start,
			@WebParam(name="size") String size,
			@WebParam(name="apptoken") String apptoken
		);*/
	
	
	/**
	 * <p>Description: 获取新闻栏目</p>
	 * @return
	 *
	 * @since 2015-7-31 下午03:26:06
	 * @author zyang
	 */
	@WebMethod
	public String getNewsTab(
			@WebParam(name="username") String username,
			@WebParam(name="strKey") String strKey
			);
	
	
	/**
	 * <p>Description: 获取新闻栏目对应的列表</p>
	 * @param id       栏目Id
	 * @param start    起始页
	 * @param size     每页多少
	 * @return
	 *
	 * @since 2015-7-31 下午03:27:56
	 * @author zyang
	 */
	@WebMethod
	public String getNewsList(
			@WebParam(name="id") String id,
			@WebParam(name="start") String start,
			@WebParam(name="size") String size
			);
	
	
	/**
	 * <p>Description: 登陆登陆页面</p>
	 * @param userName
	 * @param passWord
	 * @param strKey
	 * @return
	 *
	 * @since 2015-8-3 上午09:40:54
	 * @author zyang
	 */
	@WebMethod
	public String Login(
			@WebParam(name="userName") String userName,
			@WebParam(name="passWord") String passWord,
			@WebParam(name="strKey") String strKey,
			@WebParam(name = "status")String status
			);

	/**
	 * <p>Description: 应用中心常用模块获取</p>
	 * @param userName 用户名
	 * @param strKey   密码
	 * @return
	 *
	 * @since 2015-8-3 上午10:11:15
	 * @author zyang
	 */
	@WebMethod
	public String Commonfunction(
			@WebParam(name="userName") String userName,
			@WebParam(name="strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
			);
	
	
	/**
	 * <p>Description: 应用中心所有模块获取</p>
	 * @param userName 用户名
	 * @param strKey   密码
	 * @return
	 *
	 * @since 2015-8-3 上午10:15:36
	 * @author zyang
	 */
	@WebMethod
	public String CommonAllFunction(
			@WebParam(name="userName") String userName,
			@WebParam(name="strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
			);
	
	
	/**
	 * <p>Description: 应用中心模块类型筛选</p>
	 * @param userName   用户名
	 * @param moduletype 模块类型
	 * @param strKey     秘钥
	 * @return
	 *
	 * @since 2015-8-4 下午01:47:05
	 * @author zyang
	 */
	@WebMethod
	public String CommonBrushFunction(
			@WebParam(name="userName") String userName,
			@WebParam(name="moduletype") String moduletype,
			@WebParam(name="strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
			);
	
	/**
	 * <p>Description:   应用中心订阅提交</p>
	 * @param userName   用户名
	 * @param moduletype 模块类型
	 * @param strKey     秘钥
	 * @return
	 *
	 * @since 2015-8-4 下午01:51:51
	 * @author zyang
	 */
	@WebMethod
	public String SubmitCommonFunction(
			@WebParam(name="userName") String userName,
			@WebParam(name="servicecode") String servicecode,
			@WebParam(name="apptoken") String apptoken
			);
	
	
	/**
	 * <p>Description: 应用中心未订阅模块获取</p>
	 * @param userName 用户名
	 * @param strKey   密码
	 * @return
	 *
	 * @since 2015-8-3 上午10:15:36
	 * @author zyang
	 */
	@WebMethod
	public String CommonOtherFunction(
			@WebParam(name="userName") String userName,
			@WebParam(name="strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
			);
	
	
	/**
	 * <p>Description: 我的门户</p>
	 * @param userName
	 * @param strKey
	 * @return
	 *
	 * @since 2015-10-15 上午09:24:07
	 * @author yangz
	 */
	@WebMethod
	public String myPortalFunction(
			@WebParam(name="userName") String userName,
			@WebParam(name="strKey") String strKey ,
			@WebParam(name="apptoken") String apptoken
			);
	
	
	/**
	 * <p>Description: 应用中心下拉类型筛选</p>
	 * @param userName
	 * @return
	 *
	 * @since 2015-10-26 下午02:30:38
	 * @author yangz
	 */
	@WebMethod
	public String getALLXtYwByUser(
			@WebParam(name="userName") String userName,
			@WebParam(name="apptoken") String apptoken
			);
	
	
	/**
	 * <p>Description: 我的门户图书馆未归还图书，一卡通余额，工资信息接口</p>
	 * @param userName
	 * @param strKey
	 * @return
	 */
	@WebMethod
	public String getPortalInfo(
			@WebParam(name="userName") String userName,
			@WebParam(name="strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
			);
	
	/**
	 * <p>Description: 提交客户端意见反馈</p>
	 * @param userName
	 * @param strKey
	 * @return
	 */
	@WebMethod
	public String submitSuggestion(
			@WebParam(name="userName") String userName,
			@WebParam(name="suggestion") String suggestion,
			@WebParam(name="strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
			);
	
	/**
	 * 上传头像
	 * @param userName
	 * @param fileName
	 * @param content
	 * @return
	 */
	@WebMethod
	public String uploadMyPicture(
			@WebParam(name="userName") String userName,
			@WebParam(name="fileName") String fileName,
			@WebParam(name="content") InputStream content,
			@WebParam(name="apptoken") String apptoken
			);
	
	/**
	 * 获取我的头像路径
	 * @param userName
	 * @return
	 */
	@WebMethod
	public String getMyPicturePath(
			@WebParam(name="userName") String userName,
			@WebParam(name="apptoken") String apptoken
			);
	
	/**
	 * 数字档案部门选择
	 * @param userName
	 * @param strKey
	 * @return
	 */
	@WebMethod
	public String digitalArchivesDpList(
			@WebParam(name="userName") String userName, 
			@WebParam(name="strKey")String strKey,
			@WebParam(name="apptoken") String apptoken
			); 
	
	/**
	 * 数字档案部门人员列表
	 * @param userName
	 * @param dpId
	 * @param strKey
	 * @return
	 */
	/*@WebMethod
	public String digitalArchivesPersonnelList(@WebParam(name="userName")String userName, @WebParam(name="dpId") String dpId,@WebParam(name="strKey")String strKey);
	*/
	/**
	 * 数字档案部门人员列表
	 * @param userName
	 * @param dpId
	 * @param strKey
	 * @param searchName
	 * @param start
	 * @param size
	 * @return
	 */
	@WebMethod
	public String digitalArchivesPersonnelList(
			@WebParam(name="userName")String userName, 
			@WebParam(name="dpId")String dpId,
			@WebParam(name="strKey")String strKey,
			@WebParam(name="searchName")String searchName,
			@WebParam(name="start")String start,
			@WebParam(name="size")String size ,
			@WebParam(name="apptoken") String apptoken
	);
    
	/**
	 * 数字档案个人详情信息类别
	 * @param userName
	 * @param strKey
	 * @return
	 */
	@WebMethod
	public String personDocumentInformationList(
			@WebParam(name="userName")String userName,
			@WebParam(name="strKey")String strKey,
			@WebParam(name="apptoken") String apptoken
			);
	
	/**
	 * 根据类别显示数字档案下信息类的具体记录信息
	 * @param userName
	 * @param informationId
	 * @param strKey
	 * @return
	 */
	@WebMethod
	public String personDocumentInformation(
			@WebParam(name="userName")String userName,
			@WebParam(name="informationName") String informationName,
			@WebParam(name="informationId")String informationId,
			@WebParam(name="strKey")String strKey,
			@WebParam(name="apptoken") String apptoken
			);
	
	/**
	 * 
	 * @param username
	 * @param tphdid
	 * @param strkey
	 * @return
	 */
	@WebMethod
	public String votelist(
			@WebParam(name="userName")String userName ,
			@WebParam(name="tphdid")String tphdid , 
			@WebParam(name="strKey")String strKey,
			@WebParam(name="apptoken") String apptoken
			);
	
	/**
	 * 
	 * @param username
	 * @param jmid
	 * @param tag
	 * @param strkey
	 * @return
	 */
	@WebMethod
	public String votelike(
			@WebParam(name="userName")String userName ,
			@WebParam(name="jmid")String jmid ,
			@WebParam(name="tag")String tag ,
			@WebParam(name="strKey")String strKey,
			@WebParam(name="apptoken") String apptoken
			);

	/**
	 * 
	 * @param username
	 * @param tphdid
	 * @param strkey
	 * @return
	 */
	@WebMethod
	public String voteRanklist(
			@WebParam(name="userName")String userName , 
			@WebParam(name="tphdid")String tphdid , 
			@WebParam(name="strKey")String strKey,
			@WebParam(name="apptoken") String apptoken
			);
	
	@WebMethod
    public String getCanteenList(
    		@WebParam(name="canteenname")String canteenname,
    		@WebParam(name="startStr")String startStr,
    		@WebParam(name="sizeStr")String sizeStr
    		);
	
	@WebMethod
    public String getCanteenDetailList(
    		@WebParam(name="canteenId")String canteenId
    		);
	
	@WebMethod
    public String placeOrder(
    		@WebParam(name="data")String data
    		);
	
	@WebMethod
    public String getOrderlistByUser(
    		@WebParam(name="username")String username,
    		@WebParam(name="startStr")String startStr,
    		@WebParam(name="sizeStr")String sizeStr
    		);
	
	@WebMethod
    public String findOrderDetail(
    		@WebParam(name="username")String username,
    		@WebParam(name="orderId")String orderId
    		);
	
	@WebMethod
    public String getAddressListByUser(
    		@WebParam(name="username")String username,
    		@WebParam(name="start")String start,
    		@WebParam(name="size")String size
    		);
	
	@WebMethod
    public String submitAddressForUser(
    		@WebParam(name="addressId")String addressId,
    		@WebParam(name="userId")String userId,
    		@WebParam(name="name")String name,
    		@WebParam(name="mobilePhone")String mobilePhone,
    		@WebParam(name="schoolName")String schoolName,
    		@WebParam(name="specificAddress")String specificAddress
    		);
	
	@WebMethod
    public String deleteAddressByUser(
    		@WebParam(name="addressId")String addressId
    		);
	
	//获取报修列表
	@WebMethod
    public String getReportFixList(
    		@WebParam(name="username")String username,
    		@WebParam(name="start")String start,
    		@WebParam(name="size")String size,
    		@WebParam(name="status")String status
    		);
	
	//获取报修类型
	@WebMethod
    public String getFixType();
	
	//提交报修
	@WebMethod
	public String insertReportFix(
			@WebParam(name="username")String username,
			@WebParam(name="problem")String problem,
			@WebParam(name="telephone")String telephone,
			@WebParam(name="type")String type,
			@WebParam(name="address")String address,
			@WebParam(name="content")String content,
			@WebParam(name="picPath")String picPath,
			@WebParam(name="userId")String userId
	);
	
	//评价报修
	@WebMethod
	public String updateEvaluateById(
			@WebParam(name="fixId")String fixId,
			@WebParam(name="evaluate")String evaluate,
			@WebParam(name="score")String score,
			@WebParam(name="picPath")String picPath
	);
	
	//获取报修详情
	@WebMethod
	public String getFixById(@WebParam(name="fixId")String fixId);
	
	//确认维修人,状态status 1报修中2已报修3.已评价
	@WebMethod
	public String conRepair(@WebParam(name="username")String username,@WebParam(name="fixId")String fixId,@WebParam(name="status") String status);
	
	@WebMethod
	public String fixUpload(@WebParam(name="fileName") String fileName,@WebParam(name="file")InputStream file);
	
	@WebMethod
	public String voteList(@WebParam(name="username")String username,
			@WebParam(name="start")String start,
			@WebParam(name="size")String size,
			@WebParam(name="mineVoteFlag")String mineVoteFlag,
			@WebParam(name="voteIsDraft")String voteIsDraft,
			@WebParam(name="apptoken")String apptoken);
	
	@WebMethod
	public String getMyPartInList(@WebParam(name="username")String username,
			@WebParam(name="start")String start,
			@WebParam(name="size")String size,
			@WebParam(name="voteIsDraft")String voteIsDraft,
			@WebParam(name="apptoken")String apptoken);
	
	@WebMethod
	public String getByVoteId(@WebParam(name="username")String username,
			@WebParam(name="voteId")String voteId,
			@WebParam(name="apptoken")String apptoken);
	
	
	@WebMethod
	public String getQzList();
	
	@WebMethod
	public String getVoteResultDetail(@WebParam(name="username")String username,
			@WebParam(name="voteId")String voteId,
			@WebParam(name="apptoken")String apptoken);
	
	@WebMethod
	public String getVotePartInPersonDetail(@WebParam(name="username")String username,
			@WebParam(name="voteId")String voteId,
			@WebParam(name="apptoken")String apptoken);
	
	
	@WebMethod
	public String insertVoteNew(@WebParam(name="username")String username,
			@WebParam(name="voteTitle")String voteTitle,
			@WebParam(name="voteDescription")String voteDescription,
			@WebParam(name="voteType")String voteType,
			@WebParam(name="voteIsMultiSelect")String voteIsMultiSelect,
			@WebParam(name="voteMaxChoice")String voteMaxChoice,
			@WebParam(name="voteIsAnonymous")String voteIsAnonymous,
			@WebParam(name="voteResultOnlySee")String voteResultOnlySee,
			@WebParam(name="voteMaxScore")String voteMaxScore,
			@WebParam(name="voteScoreMethod")String voteScoreMethod,
			@WebParam(name="voteEndDate")String voteEndDate,
			@WebParam(name="voteIsDraft")String voteIsDraft,
			@WebParam(name="optionJsonStr")String optionJsonStr,
			@WebParam(name="qzId")String qzId
			);
	
	@WebMethod
	public String insertVoteResult(@WebParam(name="username")String username,
	@WebParam(name="voteId")String voteId,
	@WebParam(name="scoreVoteJsonStr")String scoreVoteJsonStr);
	
	@WebMethod
	public String updateVote(@WebParam(name="voteId")String voteId,
			@WebParam(name="voteIsDraft")String voteIsDraft);
	
	
}
