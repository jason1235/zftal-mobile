package com.common;


/**
 * <p>Description: webservice接口配置文件</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2010-10-8下午01:27:55
 * @author:xxh
 * @version 1.0
 */
public class WebServiceConf {
	//提供客户端下载附件地址 /////////////////////////////////////////
	//公司外网
//	public static final String DOWNLOADURL = "http://122.224.218.35:9990/zfmobile_port/upload/";
	public static final String DOWNLOADURL = Config.getString("webservice.host.down", "http://210.40.2.223:8888/zfmobile_port/upload/");//test
	//公司内网
//	public static final String DOWNLOADURL = "http://10.71.19.191:9990/zfmobile_port/upload/";
	//旅游
//	public static final String DOWNLOADURL = "http://60.191.79.164:9992/zfmobile_port/upload/";
	//金融
//	public static final String DOWNLOADURL = "http://oa.zfc.edu.cn:9992/zfmobile_port/upload/";

	//address////////////////////////////////////////////////////////
	//OA service
	public static final String WEBSERVICE_HOST_OA = Config.getString("webservice.host.oa", "http://oa.heuet.edu.cn/zfwebservice");//test
	
	public static final String WEBSERVICE_HOST_KEYAN = Config.getString("webservice.host.keyan", "http://oa.heuet.edu.cn/zfwebservice");//test
	//OA外网
//	public static final String WEBSERVICE_HOST_OA = "http://10.71.19.195:8018/zfwebservice";
	//旅游
//	public static final String WEBSERVICE_HOST_OA = "http://192.168.0.89:8080/zfwebservice";
	//学校JR
//	public static final String WEBSERVICE_HOST_OA = "http://192.168.100.20:8075/zfwebservice";
	//测试
//	public static final String WEBSERVICE_HOST_OA = "http://10.71.32.132:8888/zfwebservice";
	
	//旅游
//	public static final String WEBSERVICE_HOST_JW = "http://192.168.0.90:8083";
	
	//门户登录service
	public static final String WEBSERVICE_HOST_MH = Config.getString("webservice.host.mh", "http://10.71.19.195:8043");
	public static final String WEBSERVICE_HOST_NEWMH = Config.getString("webservice.host.newmh", "http://10.71.32.231:8085");
//	public static final String WEBSERVICE_HOST_MH ="http://10.71.19.195:8043";
//	public static final String WEBSERVICE_HOST_MH ="http://192.168.0.92:81";
	
	//综合service
	public static final String WEBSERVICE_HOST_XX = Config.getString("webservice.host.zh", "http://10.71.32.192:8080");
	
	//学工service
	public static final String WEBSERVICE_HOST_XG = Config.getString("webservice.host.xg", "http://10.71.32.70:8089");
	public static final String WEBSERVICE_HOST_NEWXG = Config.getString("webservice.host.xg", "http://10.71.32.70:8089");
/*	//迎新service
	public static final String WEBSERVICE_HOST_YX = Config.getString("webservice.host.yx", "http://10.71.32.70:8089");
	//离校service
	public static final String WEBSERVICE_HOST_LX = Config.getString("webservice.host.lx", "http://10.71.32.70:8089");*/
	
	//门户后台service//////////////////////////////////////////
	//旅游
	
	//公司外网门户
//	public static final String WEBSERVICE_HOST_BACKMH ="http://122.224.218.35:9992/zfmcmh/webservices";
	//公司内网门户
	public static final String WEBSERVICE_HOST_BACKMH = Config.getString("webservice.host.backmh", "http://10.71.32.205:8082/zfmmh_manager/webservices");
//	public static final String WEBSERVICE_HOST_BACKMH ="http://10.71.19.191:9992/zfmcmh/webservices";
//	public static final String WEBSERVICE_HOST_BACKMH ="http://10.71.32.205:8082/zfmmh_manager/webservices";
	
	//下载service///////////////////////////////////////////////////
	public static final String WEBSERVICE_HOST_DOWN ="http://10.71.32.205:8082/webservice-demo/downLoad";
//	public static final String WEBSERVICE_HOST_DOWN ="http://10.71.32.132:8888/webservice-demo/downLoad";
	
	
	//版本校验//////////////////////////////////////////////////
	//本地测试地址
	public static final String WEBSERVICE_HOST_VERSION = Config.getString("webservice.host.version", "http://10.71.32.205:8082/zfmmh_manager/webservices");
	
	
	//第三方新闻地址
	public static final String WEBSERVICE_HOST_DSFXE = Config.getString("webservice.host.dsf.xw", "http://10.2.1.126");
	
	//移动推送
	public static final String WEBSERVICE_HOST_TS = Config.getString("webservice.host.newmobile", "http://10.71.32.112:8090");
	
	
	//新移动接口
	public static final String WEBSERVICE_HOST_NEWMOBILE = Config.getString("webservice.host.newmobile", "http://10.71.32.121:8081");
	
/*************************************************************************************************************/	
	
	//nameSpace
	//OA namespace
	public static final String WEBSERVICE_NAMESPACE_OA = "http://service.oa.portal.webservice.zfsoft.com/";
//	public static final String WEBSERVICE_NAMESPACE_OA = "http://service.oa.portal.webservice.zfsoft.com/";
	//教务 namespace
	public static final String WEBSERVICE_NAMESPACE_JW = "http://tempuri.org/";
	//门户后台 namespace
	public static final String WEBSERVICE_NAMESPACE_BACKMH = "http://service.introduce.logic.webservice.general.zfsoft.com/";
	//综合namespace
	public static final String WEBSERVICE_NAMESPACE_XX = "http://webservice.zfml.zfsoft.com/";
	//第三方新闻namespace
	public static final String WEBSERVICE_NAMESPACE_DSFXE = WEBSERVICE_HOST_DSFXE+"/jcms/service/WSReceive";
	
	
	/**
	 * 说明：
	 * SERVICIE_service名称
	 * FUNCTION_方法名称
	 */
	//OA接口
	public static final String SERVICE_OASERVICE = WEBSERVICE_HOST_OA + "/oaMobileService";
	//科研
	public static final String SERVICE_KEYANSERVICE = WEBSERVICE_HOST_KEYAN;
	
/*#######################################################################################################	*/
	//教务接口连接
	public static final String SERVICE_JWSERVICE = Config.getString("webservice.host.jw");
	
	//教务附件下载
	public static final String SERVICE_JWDOWNLOADSERVICE = Config.getString("webservice.host.jw.download");
	
	//教务附件下载接口地址
	public static final String SERVICE_JWDOWNLOADSERVICES = SERVICE_JWDOWNLOADSERVICE;
	
	
	//教务登录接口
	public static final String SERVICE_JWSERVICE_LOGIN = SERVICE_JWSERVICE + "/LoginContract";
	//教务成绩接口
	public static final String SERVICE_JWSERVICE_SCORE = SERVICE_JWSERVICE + "/ScoreSearchContract";
	//教务考试接口
	public static final String SERVICE_JWSERVICE_EXAM = SERVICE_JWSERVICE + "/ExamArrangeContract";
	//教务教师考试接口
	public static final String SERVICE_JWSERVICE_TEACHER_EXAM = SERVICE_JWSERVICE + "/TeacherSuperviseArrangementContract";
	//教务课程任务接口
	public static final String SERVICE_JWSERVICE_COURSETASK = SERVICE_JWSERVICE + "/CourseTaskContract";
	//教务学生信息接口
	public static final String SERVICE_JWSERVICE_STUDENT = SERVICE_JWSERVICE + "/StudentInfoContract";
	//教务课表接口
	public static final String SERVICE_JWSERVICE_TIMETABLE = SERVICE_JWSERVICE + "/CourseScheduleContract";
	//教务通知接口
	public static final String SERVICE_JWSERVICE_NOTICE = SERVICE_JWSERVICE + "/NoticeContract";
	//教务配置信息接口
	public static final String SERVICE_JWSERVICE_CONFIG = SERVICE_JWSERVICE + "/ConfigurationInfoContract";
	
	public static final String SERVICE_JW_WEB_APP_SERVICE = Config.getString("webservice.host.jwWebApp");
	
	//教务 上课时间段接口
	public static final String SERVICE_JWSERVICE_CLASSPERIODTIME = SERVICE_JWSERVICE + "/ClassPeriodTimeContract";
	//教务校区查询
	public static final String SERVICE_JWSERVICE_CAMPUSSEARCH = SERVICE_JWSERVICE + "/CampusContract";
	//教务空闲教室查询
	public static final String SERVICE_JWSERVICE_CLASSROOMSEARCH = SERVICE_JWSERVICE + "/ClassRoomContract";
	//教务教师信息接口
	public static final String SERVICE_JWSERVICE_TEACHERINFOCONTRACT = SERVICE_JWSERVICE+ "/TeacherInfoContract";
	
/*#######################################################################################################	*/
	//门户登录接口
	//AXIS
//	public static final String SERVICE_MHSERVICE = WEBSERVICE_HOST_MH + "/zfca/axis/MobileManage?wsdl";
	//CXF
	public static final String SERVICE_MHSERVICE = WEBSERVICE_HOST_MH + "/zfwebservice/caService?wsdl";
	
	public static final String SERVICE_NEWMHSERVICE = WEBSERVICE_HOST_NEWMH + "/im/services/caService?wsdl";
	
/*#######################################################################################################	*/
	//综合接口
	public static final String SERVICE_XXSERVICE = WEBSERVICE_HOST_XX + "/zfml-web/service/zfmlWebService";
	
	
/*#######################################################################################################	*/	
	//推送接口
	public static final String SERVICE_TSSERVICE = WEBSERVICE_HOST_TS + "/zftal-mobile/service/IWSPushService";
/*#######################################################################################################	*/
	
	
	//门户后台学校概况接口
	public static final String SERVICE_BACKMHSERVICE_SI = WEBSERVICE_HOST_BACKMH + "/introduce/IntroduceXmlService";
	//门户后台新闻资讯接口
	public static final String SERVICE_BACKMHSERVICE_NI = WEBSERVICE_HOST_BACKMH + "/news/NewsXmlService";
	//门户后台标题类型接口
	public static final String SERVICE_BACKMHSERVICE_NT = WEBSERVICE_HOST_BACKMH + "/notice/NoticeXmlService";
	//门户后台首页推荐接口
	public static final String SERVICE_BACKMHSERVICE_HR = WEBSERVICE_HOST_BACKMH + "/mhrecommend/RecommendXmlService";
	//门户后台校园风景接口
	public static final String SERVICE_BACKMHSERVICE_SS = WEBSERVICE_HOST_BACKMH + "/schoolsights/SchoolsightsXmlService";
	//门户后台版本验证接口
	public static final String SERVICE_BACKMHSERVICE_V = WEBSERVICE_HOST_BACKMH + "/version/VersionXmlService";
	//门户后台新学校概况接口
	public static final String SERVICE_BACKMHSERVICE_SINEW = WEBSERVICE_HOST_BACKMH + "/sIntroduce/SIntroduceXmlService";
/*#######################################################################################################	*/
	//版本校验接口
	public static final String SERVICE_VERSIONSERVICE_CHECK = WEBSERVICE_HOST_VERSION + "/version/VersionVerifyXmlService";
	
	
	//数据中心接口
	public static final String WEBSERVICE_SJZX = Config.getString("webservice.sjzx", "");
	public static final String WEBSERVICE_MANAGE = Config.getString("webservice.manage", "");
	//数据中心命名空间
	public static final String WEBSERVICE_NAMESPACE_ZYMZ = "http://ws.apache.org/axis2";
	////获取门户返回的认证票据命名空间
	public static final String WEBSERVICE_NAMESPACE_MANAGE = "http://pubService.webServices.zfca.zfsoft.com";
	//获取门户返回的认证票据
	//public static final String WEBSERVICE_CA_MANAGE = WEBSERVICE_HOST_MH + "/zfca/axis/MobileManage?wsdl";
	public static final String WEBSERVICE_CA_MANAGE = WEBSERVICE_MANAGE + "/zfca/axis/MobileManage?wsdl";
	//获取门户绑定手机邮箱据命名空间
	public static final String WEBSERVICE_NAMESPACE_PHONEANDEMAIL = "http://service.mh.com/";
	//获取门户绑定手机邮箱相关接口地址
	public static final String WEBSERVICE_PHONEANDEMAIL_MANAGE = WEBSERVICE_HOST_MH + "/zfca/axis/CaService?WSDL";
	//数据中心一卡通交易信息接口
	public static final String WEBSERVICE_ZYMZ_YKT=WEBSERVICE_SJZX+"/zfsjzx/services/XfWebservice?wsdl";
	//数据中心一卡通余额接口
	public static final String WEBSERVICE_ZYMZ_YKTYE=WEBSERVICE_SJZX+"/zfsjzx/services/yktye?wsdl";
	//数据中心图书接口
	public static final String WEBSERVICE_ZYMZ_BOOK=WEBSERVICE_SJZX+"/zfsjzx/services/TsjyWebservice?wsdl";
	//数据中心薪资接口
	public static final String WEBSERVICE_ZYMZ_XZ=WEBSERVICE_SJZX+"/zfsjzx/services/JGxzWebservice?wsdl";
	//数据中心薪资明细接口
	public static final String WEBSERVICE_ZYMZ_XZMX=WEBSERVICE_SJZX+"/zfsjzx/services/JGxzWebservice?wsdl";
	//数据中心教务课表接口
	public static final String WEBSERVICE_ZYMZ_JWKB=WEBSERVICE_SJZX+"/zfsjzx/services/JWKBCX?wsdl";
	//数据中心教工薪资其他收入查询接口
	public static final String WEBSERVICE_ZYMZ_QTSR=WEBSERVICE_SJZX+"/zfsjzx/services/xzqtsrWebservice?wsdl";
	//数据中心空闲教室查询接口
	public static final String WEBSERVICE_ZYMZ_KXJS=WEBSERVICE_SJZX+"/zfsjzx/services/KxjsWebservice?wsdl";
	//数据中心空闲教室类型接口
	public static final String WEBSERVICE_ZYMZ_KXJSLX=WEBSERVICE_SJZX+"/zfsjzx/services/kxjslx?wsdl";
	//数据中心空闲时间段查询接口
	public static final String WEBSERVICE_ZYMZ_KXSJD=WEBSERVICE_SJZX+"/zfsjzx/services/kxsjd?wsdl";
	//数据中心校区查询接口
	public static final String WEBSERVICE_ZYMZ_XQ=WEBSERVICE_SJZX+"/zfsjzx/services/xqxxb?wsdl";
	//数据中心教学楼查询接口
	public static final String WEBSERVICE_ZYMZ_JXL=WEBSERVICE_SJZX+"/zfsjzx/services/jxlxxwebservice?wsdl";
	//数据中心当前学年学期接口
	public static final String WEBSERVICE_ZYMZ_DQXNXQ=WEBSERVICE_SJZX+"/zfsjzx/services/dqxnxq?wsdl";
	//数据中心（重庆第二师范学院）一卡通消费信息
	public static final String WEBSERVICE_CQDRSF_XFXX=WEBSERVICE_SJZX+"/zfsjzx/services/yktxfjl?wsdl";
	//数据中心（重庆第二师范学院）一卡通基本信息
	public static final String WEBSERVICE_CQDRSF_JBXX=WEBSERVICE_SJZX+"/zfsjzx/services/yktjbxx?wsdl";
    //数据中心（重庆第二师范学院）图书馆借阅信息
	public static final String WEBSERVICE_CQDRSF_TSGJYXX=WEBSERVICE_SJZX+"/zfsjzx/services/tsgjyxxb?wsdl";
	//数据中心（贵州大学）图书馆借阅信息
	public static final String WEBSERVICE_CQDRSF_GZDXTSGJYXX=WEBSERVICE_SJZX+"/zfdxc/services/SzdaService?wsdl";
	//数据中心（山东英才学院）一卡通余额
	public static final String WEBSERVICE_CQDRSF_SDYCXYYKTYE=WEBSERVICE_SJZX+"/zfsjzx/services/SerYKTYE?wsdl";
	//数据中心（山东英才学院）一卡通消费明细
	public static final String WEBSERVICE_CQDRSF_SDYCXYYKTXFMX=WEBSERVICE_SJZX+"/zfsjzx/services/SerYKTXFLS?wsdl";
	//数据中心（图书馆）借阅信息
	public static final String WEBSERVICE_CQDRSF_SDYCXYTSG=WEBSERVICE_SJZX+"/zfsjzx/services/SerWHTS?wsdl";
	
	/*################################ZFSMP接口#######################################################################*/
	//ZFSMP地址
	public static final String WEBSERVICE_ZFSMP = Config.getString("webservice.zfsmp", "");
	//ZFSMP命名空间
	public static final String WEBSERVICE_NAMESPACE_ZFSMP = WEBSERVICE_ZFSMP+"/zfsmp/services/NewsFlow";
	
    //ZFSMP（警察学院）新闻审批接口
	public static final String WEBSERVICE_ZFSMP_ZJJC=WEBSERVICE_ZFSMP+"/zfsmp/services/NewsFlow?wsdl";
	
	
	/*#######################################################################################################*/
	 //学工接口
	public static final String SERVICE_XGSERVICE = WEBSERVICE_HOST_XG + "/zfxg-ws-web/xgService?wsdl";
	 //迎新接口
	public static final String SERVICE_YXSERVICE = WEBSERVICE_HOST_XG + "/zfxg-ws-web/yxService?wsdl";
	//离校接口
	public static final String SERVICE_LXSERVICE = WEBSERVICE_HOST_XG + "/zfxg-ws-web/lxService?wsdl";
	//获取通知公告数量
	public static final String SERVICE_NEWXGSERVICE = WEBSERVICE_HOST_NEWXG + "/xgxt/xtwhService?wsdl";
	
	
	/*##################################################################################*/
	//JCMS网站的信息
	public static final String SERVICE_DSFXWSERVICE = WEBSERVICE_HOST_DSFXE + "/jcms/service/WSReceive?wsdl";
	
	/*##################################################################################*/
	//新移动接口
	public static final String SERVICE_NEWMOBILESERVICE = WEBSERVICE_HOST_NEWMOBILE + "/zftal-mobile/webservice/WSSerService?wsdl";
	
	
	
	public static final String SERVICE_PUSHMOBILESERVICE = WEBSERVICE_HOST_NEWMOBILE + "/zftal-mobile/webservice/WSPushService?wsdl";
	//金华oa
	public static final String SERVICE_JINHUAOAESERVICE =  Config.getString("webservice.service_jinhuaoaeservice", "http://newoa.jhc.cn/oa-infoPortal/services/infoPortal.jws?wsdl");

	//新ca认证getticket2
	public static final String WEBSERVICE_NEWMH = WEBSERVICE_HOST_NEWMH + "/im/services/caService?wsdl";
}
