package com.jw.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * <p>
 * Description:教务接口
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company: zfsoft.com
 * </p>
 * 
 * @since Oct 29, 2012 9:29:08 AM
 * @author huangzhaoxia
 * @version 1.0
 */
@WebService
public interface IEducationalPortXMLService {
	/**
	 * 
	* @author: zhangxu
	* @Title: getNewEducationalLogin 
	* @Description: 新教务登录
	* @param @param yhm
	* @param @param pw
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String getNewEducationalLogin(
			@WebParam(name = "yhm")String yhm, 
			@WebParam(name = "mm")String mm
			);
	/**
	 * <p>
	 * Description:  登录验证
	 * </p>
	 * 
	 * @param userName 用户名
	 * @param password 密码
	 * @param strKey   密钥
	 * @param role     角色
	 * @param alone    是否统一登录
	 * @return
	 * 
	 * @since Oct 29, 2012 10:00:01 AM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String Login(@WebParam(name = "userName") String userName,
			@WebParam(name = "passWord") String passWord,
			@WebParam(name = "role") String role,
			@WebParam(name = "alone") String alone,
			@WebParam(name = "strKey") String strKey,
			@WebParam(name="apptoken") String apptoken);
	
	/**
	 * <p>Description: 学生登录有效验证</p>
	 * @param userName 用户名
	 * @param strKey   密钥
	 * @return
	 *
	 * @since 2014-7-16 下午03:08:26
	 * @author yangz
	 */
	@WebMethod
	public String LoginStudentEffectiveChecking(@WebParam(name = "userName") String userName,
			@WebParam(name = "strKey") String strKey,@WebParam(name="apptoken") String apptoken);
	
	/**
	 * <p>Description: 成绩查询</p>
	 * @param sid      学号
	 * @param count    记录数
	 * @param strKey   密钥
	 * @return
	 *
	 * @since 2014-7-16 下午03:09:33
	 * @author yangz
	 */
	@WebMethod
	public String ScoreSearch(@WebParam(name = "sid") String sid,
			@WebParam(name = "count") String count,
			@WebParam(name = "strKey") String strKey,@WebParam(name="apptoken") String apptoken);

	
	/**
	 * <p>Description: 成绩查询详情</p>
	 * @param sid      学号
	 * @param classId  课程ID
	 * @param count    记录数
	 * @param strKey   密钥
	 * @return
	 *
	 * @since 2014-7-16 下午03:10:20
	 * @author yangz
	 */
	@WebMethod
	public String ScoreInfoSearch(@WebParam(name = "sid") String sid,
			@WebParam(name = "classId") String classId,
			@WebParam(name = "count") String count,
			@WebParam(name = "strKey") String strKey,
			@WebParam(name="apptoken") String apptoken);
	
	/**
	 * <p>
	 * Description: 考试安排
	 * </p>
	 * 
	 * @param sid   学号
	 * @param count 记录数
	 * @param strKey 密钥
	 * @return
	 * 
	 * @since Oct 31, 2012 10:36:38 AM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String ExamArrange(@WebParam(name = "sid") String sid,
			@WebParam(name = "count") String count,
			@WebParam(name = "strKey") String strKey,
			@WebParam(name="apptoken") String apptoken);
	
	/**
	 * <p>
	 * Description: 补考考试安排
	 * </p>
	 * 
	 * @param sid    学号
	 * @param count  记录数
	 * @param strKey 密钥
	 * @return
	 * 
	 * @since Oct 31, 2012 10:36:38 AM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String MakeUpExamArrange(@WebParam(name = "sid") String sid,
			@WebParam(name = "count") String count,
			@WebParam(name = "strKey") String strKey,
			@WebParam(name="apptoken") String apptoken);
	
	/**
	 * <p>
	 * Description: 毕业清考考试安排
	 * </p>
	 * 
	 * @param sid     学号
	 * @param count   记录数
	 * @param strKey  密钥
	 * @return
	 * 
	 * @since Oct 31, 2012 10:36:38 AM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String GraduationMakeUpExamArrange(@WebParam(name = "sid") String sid,
			@WebParam(name = "count") String count,
			@WebParam(name = "strKey") String strKey,
			@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>Description: 教师考试安排</p>
	 * @param sid      教师ID
	 * @param type     类型（全部：1；带课班级考试安排：2；监考安排：3；主巡考安排：4）
	 * @param count    记录数
	 * @param strKey   密钥
	 * @return
	 *
	 * @since 2014-7-16 下午03:13:31
	 * @author yangz
	 */
	@WebMethod
	public String TeacherSuperviseArrangementSearch(@WebParam(name = "sid") String sid,
			@WebParam(name = "type") String type,
			@WebParam(name = "count") String count,
			@WebParam(name = "strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
	);
	/**
	 * <p>
	 * Description: 获取学年学期列表
	 * </p>
	 * 
	 * @param count 记录数
	 * @return
	 * 
	 * @since Oct 31, 2012 10:40:54 AM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String GetYearTermList(@WebParam(name = "count") String count,@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>
	 * Description: 获取专业列表
	 * </p>
	 * 
	 * @param schoolCode 学院代码
	 * @param count      记录数
	 * @param strKey     密钥
	 * @return
	 * 
	 * @since Oct 31, 2012 10:46:41 AM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String GetMajorList(
			@WebParam(name = "schoolCode") String schoolCode,
			@WebParam(name = "count") String count,
			@WebParam(name = "strKey") String strKey,
			@WebParam(name="apptoken") String apptoken);


	/**
	 * <p>Description: 获取班级列表 </p>
	 * @param xysid 学院id
	 * @param zysid 专业id
	 * @param nj    年级
	 * @param count 记录数
	 * @param strKey 密钥
	 * @return
	 *
	 * @since 2014-4-28 下午04:34:50
	 * @author yangz
	 */
	@WebMethod
	public String GetClassList(
			@WebParam(name ="xysid") String xysid,
			@WebParam(name="zysid") String zysid,
			@WebParam(name="nj") String nj,
			@WebParam(name = "count") String count,
			@WebParam(name="strKey") String strKey,
			@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>
	 * Description: 获取课程列表
	 * </p>
	 * 
	 * @param count 记录数
	 * @return
	 * 
	 * @since Oct 31, 2012 10:50:08 AM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String GetCourseList(@WebParam(name = "count") String count,@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>
	 * Description:获取学院列表
	 * </p>
	 * 
	 * @param count 记录数
	 * @return
	 * 
	 * @since Oct 31, 2012 10:51:21 AM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String GetInstituteList(@WebParam(name = "count") String count,@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>
	 * Description: 获取年级列表
	 * </p>
	 * 
	 * @param count 记录数
	 * @return
	 * 
	 * @since Oct 31, 2012 10:52:45 AM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String GetGradeList(@WebParam(name = "count") String count,@WebParam(name="apptoken") String apptoken);
	
	/**
	 * <p>Description: 获取教师列表</p>
	 * @param username 用户名
	 * @param count 记录
	 * @param strKey 密钥
	 * @return
	 *
	 * @since 2014-4-25 上午08:38:17
	 * @author yangz
	 */
	@WebMethod
	public String GetTeacherList(
			@WebParam(name="xymc") String xymc,
			@WebParam(name="teachername") String teachername,
			@WebParam(name="count") String count,
			@WebParam(name="strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
			);
	
	

	/**
	 * <p>Description: 获取专业方向</p>
	 * @param grade    年级
	 * @param SpecialtyCode 专业代码
	 * @param count    记录数
	 * @param strKey   密钥
	 * @return
	 *
	 * @since 2014-7-16 下午02:17:05
	 * @author yangz
	 */
	public String GetMajorFieldList(
			@WebParam(name="grade") String grade,
			@WebParam(name="SpecialtyCode") String SpecialtyCode,
			@WebParam(name="count") String count,
			@WebParam(name="strKey") String strKey,
			@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>
	 * Description:  课程任务搜索
	 * </p>
	 * 
	 * @param collegecode 学院代码
	 * @param SpecialtyCode 专业代码
	 * @param year 年级
	 * @param inTerm 学期
	 * @param count  记录数
	 * @param strKey 密钥
	 * @return
	 * 
	 * @since Oct 31, 2012 10:55:52 AM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String CourseTaskSearch(
			@WebParam(name = "collegecode") String collegecode,
			@WebParam(name = "specialtyCode") String specialtyCode,
			@WebParam(name = "grade") String grade,
			@WebParam(name = "inTerm") String inTerm,
			@WebParam(name = "zyfx") String zyfx,
			@WebParam(name = "count") String count,
			@WebParam(name = "strKey") String strKey,
			@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>
	 * Description:  课程任务详情
	 * </p>
	 * 
	 * @param courseid 课程id
	 * @param count    记录数
	 * @param strKey   密钥
	 * @return
	 * 
	 * @since Oct 31, 2012 10:59:24 AM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String CourseTaskInfo(@WebParam(name = "courseId") String courseId,
			@WebParam(name = "count") String count,
			@WebParam(name = "strKey") String strKey,
			@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>
	 * Description:  学生信息
	 * </p>
	 *  
	 * @param sid    学号
	 * @param count  记录数
	 * @param strKey 密钥
	 * @return
	 * 
	 * @since Oct 31, 2012 11:08:48 AM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String StudentInfoSearch(@WebParam(name = "_sid") String _sid,
			@WebParam(name = "count") String count,
			@WebParam(name = "strKey") String strKey,
			@WebParam(name="apptoken") String apptoken);


	/**
	 * <p>Description:学生照片信息 </p>
	 * @param sid     学号
	 * @param strKey  密钥
	 * @return
	 *
	 * @since 2014-4-25 上午09:18:54
	 * @author yangz
	 */
	@WebMethod
	public byte[] StudentPhotosSearch(
			@WebParam(name = "_sid") String _sid,
			@WebParam(name = "strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
	);
	
	/**
	 * <p>
	 * Description:  课表查询
	 * </p>
	 * 
	 * @param userName 用户名
	 * @param year     学年
	 * @param term     学期
	 * @param role     角色
	 * @param count    记录数 
	 * @return
	 * 
	 * @since Oct 31, 2012 11:19:33 AM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String CourseScheduleSearch(
			@WebParam(name = "userName") String userName,
			@WebParam(name = "year") String year,
			@WebParam(name = "term") String term,
			@WebParam(name = "role") String role,
			@WebParam(name = "count") String count,
			@WebParam(name = "strKey") String strKey,
			@WebParam(name="apptoken") String apptoken);
	
	/**
	 * <p>Description: 教师课表</p>
	 * @param userName
	 * @param year
	 * @param term
	 * @param role
	 * @param count
	 * @param strKey
	 * @return
	 *
	 * @since 2015-2-2 下午03:47:43
	 * @author yangz
	 */
	@WebMethod
	public String TeacherCourseScheduleSearch(
			@WebParam(name = "userName") String userName,
			@WebParam(name = "teachername") String teachername,
			@WebParam(name = "year") String year,
			@WebParam(name = "term") String term,
			@WebParam(name = "role") String role,
			@WebParam(name = "count") String count,
			@WebParam(name = "strKey") String strKey,
			@WebParam(name="apptoken") String apptoken);
	
	

	
	/**
	 * <p>Description: 通知公告列表获取</p>
	 * @param userName 用户名
	 * @param start    起始页
	 * @param size     每页多少
	 * @param totalrecords 总页数
	 * @param role     角色
	 * @param count    记录数
	 * @param strKey   秘钥
	 * @return
	 *
	 * @since 2015-8-7 下午02:57:37
	 * @author yangz
	 */
	@WebMethod
	public String GetPostList(@WebParam(name = "userName") String userName,
			@WebParam(name= "start") String start,
			@WebParam(name= "size") String size,
			@WebParam(name = "role") String role,
			@WebParam(name = "count") String count,
			@WebParam(name = "strKey") String strKey,
			@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>
	 * Description:  通知公告详情
	 * </p>
	 * 
	 * @param noticeid 通知公告id
	 * @param count    记录数
	 * @param strKey   密钥
	 * @return
	 * 
	 * @since Oct 31, 2012 11:27:20 AM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String PostInfoSearch(@WebParam(name = "noticeId") String noticeId,
			@WebParam(name = "count") String count,
			@WebParam(name = "strKey") String strKey,
			@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>
	 * Description:  学生成绩查询
	 * </p>
	 * 
	 * @param sid      学号
	 * @param count    记录数
	 * @param strKey   密钥
	 * @return
	 * 
	 * @since Oct 31, 2012 11:27:20 AM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String GradeScoreInfoSearch(@WebParam(name = "sid") String sid,
			@WebParam(name = "count") String count,
			@WebParam(name = "strKey") String strKey,
			@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>
	 * Description:  教务配置信息
	 * </p>
	 * 
	 * @param noticeid 通知id
	 * @param count    记录数
	 * @return
	 * 
	 * @since Oct 31, 2012 11:27:20 AM
	 * @author huangzhaoxia
	 */
	@WebMethod
	public String ConfigurationInfo(@WebParam(name="apptoken") String apptoken);

	/**
	 * 教务Web App配置信息
	 * 
	 * @since 2014-1-7 4:27:20 9M
	 * @author huangzhaoxia
	 * @return
	 */
	@WebMethod
	public String GetJwWebAppConfigInfo(@WebParam(name="apptoken") String apptoken);
	
	/**
	 * <p>Description:课程任务详情</p>
	 * @param kcsid 课程id
	 * @param count 记录数
	 * @param strKey 密钥
	 * @return
	 *
	 * @since 2014-4-25 上午08:45:09
	 * @author yangz
	 */
	@WebMethod
	public String CourseTaskInfoSearch(
			@WebParam(name="kcsid") String kcsid,
			@WebParam(name="count") String count,
			@WebParam(name="strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
	);
	
	
	/**
	 * <p>Description:班级课表查询</p>
	 * @param bjsid 班级id
	 * @param year  学年
	 * @param term 学期
	 * @param count 记录数
	 * @param strKey 密钥
	 * @return
	 *
	 * @since 2014-4-25 上午08:54:49
	 * @author yangz
	 */
	@WebMethod
	public String CourseScheduleClassSearch(
			@WebParam(name="bjsid") String bjsid,
			@WebParam(name="year") String year,
			@WebParam(name="term") String term,
			@WebParam(name="count") String count,
			@WebParam(name="strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
	);
	
	/**
	 * <p>Description: 上课时间段查询</p>
	 * @param xn 学年
	 * @param xq 学期
	 * @param strKey 密钥
	 * @return
	 *
	 * @since 2014-4-23 上午10:11:05
	 * @author yangz
	 */
	@WebMethod
	public String IClassPeriodTime(
			@WebParam(name = "xn") String xn,
			@WebParam(name = "xq") String xq,
			@WebParam(name = "count") String count,
			@WebParam(name = "strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
	);
	
	
	/**
	 * <p>Description: 校区查询</p>
	 * @return
	 *
	 * @since 2014-4-24 上午09:50:26
	 * @author yangz
	 */
	@WebMethod
	public String ICampus(@WebParam(name="apptoken") String apptoken);
	
	
	/**
	 * <p>Description: 教室类别查询</p>
	 * @return
	 *
	 * @since 2014-4-24 上午10:09:10
	 * @author yangz
	 */
	@WebMethod
	public String IClassroomCategory(@WebParam(name="apptoken") String apptoken);
	
	
	
	/**
	 * <p>Description: 异动信息</p>
	 * @param sid      学号id
	 * @param count    记录数
	 * @param strKey   加密后密钥
	 * @return
	 *
	 * @since 2014-6-10 上午08:49:16
	 * @author yangz
	 */
	@WebMethod
	public String StudentChangedSearch(
			@WebParam(name="sid") String sid,
			@WebParam(name="count") String count,
			@WebParam(name="strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
	);
	
	
	/**
	 * <p>Description: 学生奖励</p>
	 * @param sid      学号id
	 * @param count    记录数
	 * @param strKey   加密后密钥
	 * @return
	 *
	 * @since 2014-6-10 上午09:01:15
	 * @author yangz
	 */
	@WebMethod
	public String StudentRewardSearch(
			@WebParam(name="sid") String sid,
			@WebParam(name="count") String count,
			@WebParam(name="strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
	);
	
	
	/**
	 * <p>Description: 学生惩罚</p>
	 * @param sid      学号id
	 * @param count    记录数
	 * @param strKey   加密后密钥
	 * @return
	 *
	 * @since 2014-6-10 上午09:04:45
	 * @author yangz
	 */
	@WebMethod
	public String StudentPunishmentSearch(
			@WebParam(name="sid") String sid,
			@WebParam(name="count") String count,
			@WebParam(name="strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
	);
	
	
	
	
	/**
	 * <p>Description: 教室名称模糊查询接口</p>
	 * @param jsmc     教室名称
	 * @param count    记录数
	 * @param strKey   秘钥
	 * @return
	 *
	 * @since 2015-1-30 上午09:36:46
	 * @author yangz
	 */
	@WebMethod
	public String GetClassroomList(
			@WebParam(name="jsmc") String jsmc,
			@WebParam(name="count") String count,
			@WebParam(name="strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
	);
	
	
	/**
	 * <p>Description: 	所有课表查询</p>
	 * @param xn            学年
	 * @param xq            学期
	 * @param bjmc          班级名称
	 * @param sid           用户名
	 * @param role          角色
	 * @param jsbh          教室编号 
	 * @param jslbmc        教室类别名称
	 * @param count         记录数
	 * @param strKey        秘钥
	 * @return
	 *
	 * @since 2015-1-30 上午10:46:49
	 * @author yangz
	 */
	@WebMethod
	public String GetAllCourseSchedule(
			@WebParam(name = "xn") String xn,
			@WebParam(name = "xq") String xq,
			@WebParam(name="bjmc") String bjmc,
			@WebParam(name="sid") String sid,
			@WebParam(name="role") String role,
			@WebParam(name="jsbh") String jsbh,
			@WebParam(name="jslbmc") String jslbmc,
			@WebParam(name="count") String count,
			@WebParam(name="strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
	);
	
	
	/**
	 * <p>Description:教室课表</p>
	 * @param xn
	 * @param xq
	 * @param jsbh
	 * @return
	 *
	 * @since 2015-2-2 下午01:42:31
	 * @author yangz
	 */
	@WebMethod
	public String GetClassRoomCourseSchedule(
			@WebParam(name = "xn") String xn,
			@WebParam(name = "xq") String xq,
			@WebParam(name="bjmc") String bjmc,
			@WebParam(name="sid") String sid,
			@WebParam(name="role") String role,
			@WebParam(name="jsbh") String jsbh,
			@WebParam(name="jslbmc") String jslbmc,
			@WebParam(name="count") String count,
			@WebParam(name="strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
	);
	
	/**
	 * <p>Description:   考试安排</p>
	 * @param className  课程名称
	 * @param bjName     班级名称
	 * @param jsmc       教室名称
	 * @param startdate  开始日期
	 * @param enddate    结束日期
	 * @param count      记录数
	 * @param strKey     秘钥
	 * @return
	 *
	 * @since 2015-3-12 上午11:24:59
	 * @author yangz
	 */
/*	@WebMethod
	public String GetIExamArrange1(@WebParam(name="classname") String className,@WebParam(name="bjname") String bjName,@WebParam(name="jsmc") String jsmc,@WebParam(name="startdate") String  startdate,@WebParam(name="enddate") String enddate,@WebParam(name="count") String count,@WebParam(name="strKey") String strKey);*/

	/**
	 * <p>Description:   补考考试安排</p>
	 * @param className  课程名称
	 * @param bjName     班级名称
	 * @param jsmc       教室名称
	 * @param startdate  开始日期
	 * @param enddate    结束日期
	 * @param count      记录数
	 * @param strKey     秘钥
	 * @return
	 *
	 * @since 2015-3-12 下午04:01:33
	 * @author yangz
	 */
	/*@WebMethod
	public String GetIMakeUpExamArrange1(@WebParam(name="classname") String className,@WebParam(name="bjname") String bjName,@WebParam(name="jsmc") String jsmc,@WebParam(name="startdate") String  startdate,@WebParam(name="enddate") String enddate,@WebParam(name="count") String count,@WebParam(name="strKey") String strKey);*/
	
	/**
	 * <p>Description:   毕业补考</p>
	* @param className   课程名称
	 * @param bjName     班级名称
	 * @param jsmc       教室名称
	 * @param startdate  开始日期
	 * @param enddate    结束日期
	 * @param count      记录数
	 * @param strKey     秘钥
	 * @return
	 *
	 * @since 2015-3-12 下午04:03:19
	 * @author yangz
	 */
	/*@WebMethod
	public String GetIGraduationMakeUpExamArrange1(@WebParam(name="classname") String className,@WebParam(name="bjname") String bjName,@WebParam(name="jsmc") String jsmc,@WebParam(name="startdate") String  startdate,@WebParam(name="enddate") String enddate,@WebParam(name="count") String count,@WebParam(name="strKey") String strKey);
	*/
	
	/**
	 * <p>Description:    课程名称模糊查询</p>
	 * @param schoolCode  学院代码
	 * @param classname   课程名称
	 * @param count       记录数
	 * @param strKey      秘钥
	 * @return
	 *
	 * @since 2015-3-12 下午07:28:55
	 * @author yangz
	 */
	@WebMethod
	public String GetCourseList1(
			@WebParam(name="schoolCode") String schoolCode,
			@WebParam(name="classname") String classname,
			@WebParam(name="count") String count,
			@WebParam(name="strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
	);
	
	
	/**
	 * <p>Description:   所有课表查询</p>
	 * @param xn         学年
	 * @param xq         学期
	 * @param classcode  课程代码
	 * @param startdate  开始日期
	 * @param enddate    结束日期
	 * @param schoolname 学院名称
	 * @param teaname    教师名称   
	 * @param count      记录数
	 * @param strKey     秘钥
	 * @return
	 *
	 * @since 2015-3-12 下午07:36:26
	 * @author yangz
	 */
	@WebMethod
	public String GetIAllCourseSchedule1(
			@WebParam(name = "xn") String xn,
			@WebParam(name = "xq") String xq,
			@WebParam(name="classcode") String classcode,
			@WebParam(name="startdate") String  startdate,
			@WebParam(name="enddate") String enddate, 
			@WebParam(name="schoolname") String schoolname, 
			@WebParam(name="teaname") String teaname,
			@WebParam(name="count") String count,
			@WebParam(name="strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
	);
	
	
	/**
	 * <p>Description: 某个学期的总周次</p>
	 * @return
	 *
	 * @since 2015-3-16 上午10:02:58
	 * @author yangz
	 */
	@WebMethod
	public String GetZZCJK(
			@WebParam(name = "xn") String xn,
			@WebParam(name = "xq") String xq,
			@WebParam(name="count") String count,
			@WebParam(name="strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
	);
	
	
	/**
	 * <p>Description: 取得某个学期的第一周第一天的日期</p>
	 * @return
	 *
	 * @since 2015-3-16 上午10:07:02
	 * @author yangz
	 */
	@WebMethod
	public String GetKXDYTJK(
			@WebParam(name = "xn") String xn,
			@WebParam(name = "xq") String xq,
			@WebParam(name="count") String count,
			@WebParam(name="strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
	);
	
	
	/**
	 * <p>Description:  教师考试安排</p>
	 * @param classname  课程名称
	 * @param bjname     班级名称
	 * @param classroom  教室名称
	 * @param startdate  开始日期
	 * @param enddate    结束日期
	 * @param count      记录数
	 * @param strKey     秘钥
	 * @return
	 *
	 * @since 2015-3-16 下午03:25:36
	 * @author yangz
	 */
	@WebMethod
	public String GetTeacherSuperviseArrangement1(  
			@WebParam(name="classname") String classname,		
	        @WebParam(name="bjname") String bjname,
	        @WebParam(name="classroom") String classroom,
	        @WebParam(name="startdate") String  startdate,
	        @WebParam(name="enddate") String enddate,
	        @WebParam(name="count") String count,
	        @WebParam(name="strKey") String strKey,
	        @WebParam(name="apptoken") String apptoken
	);
	
	/**
	 * <p>Description:  学生模糊查询</p>
	 * @param nj        年级
	 * @param xy        学院
	 * @param zy        专业
	 * @param bj        班级
	 * @param userName  用户名
	 * @param count     记录数
	 * @param strKey    秘钥
	 * @return
	 *
	 * @since 2015-5-8 下午03:11:06
	 * @author yangz
	 */
	@WebMethod
	public String GetStudentList(
			@WebParam(name ="nj") String nj,
			@WebParam(name ="xy") String xy,
			@WebParam(name ="zy") String zy,
			@WebParam(name ="bj") String bj,
			@WebParam(name ="xm") String xm,
			@WebParam(name ="count") String count,
			@WebParam(name ="strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
	);
	
	
	
	/**
	 * <p>Description: 教师信息查询</p>
	 * @param sid      职工号
	 * @param count    记录数
	 * @param strKey   秘钥
	 * @return
	 *
	 * @since 2015-4-17 下午01:52:58
	 * @author yangz
	 */
	@WebMethod
	public String GetTeacherInfoSearch(
			@WebParam(name="_sid") String _sid,
			@WebParam(name="count") String count,
			@WebParam(name="strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
	);
	
	
	/**
	 * <p>Description: 教师照片信息</p>
	 * @param sid      职工号
	 * @param strKey   秘钥
	 * @return
	 *
	 * @since 2015-4-17 下午02:03:43
	 * @author yangz
	 */
	@WebMethod
	public byte[] TeacherPhotosSearch(
			@WebParam(name = "_sid") String _sid,
			@WebParam(name = "strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
	);
	
	/**
	 * <p>Description: 获取教学大纲</p>
	 * @param kcdm     课程代码
	 * @param strKey   秘钥
	 * @return
	 *
	 * @since 2015-4-20 下午05:01:54
	 * @author yangz
	 */
	@WebMethod
	public String CourseJxdgInfo(
			@WebParam(name="kcdm") String kcdm,
			@WebParam(name="strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
	);
	
	
	/**
	 * <p>Description:  用户登录无需选择角色系统自动判定角色</p>
	 * @param userName  用户名
	 * @param passWord  密码
	 * @param strKey    秘钥
	 * @return
	 *
	 * @since 2015-5-6 上午10:06:16
	 * @author yangz
	 */
	@WebMethod
	public String LoginNoJsCheck(
			@WebParam(name = "userName") String userName,
			@WebParam(name = "passWord") String passWord,
			@WebParam(name = "strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
	);
	
	/**
	 * <p>Description:  中国矿业大学未评价不能登录系统功能</p>
	 * @param userName  用户名
	 * @return
	 *
	 * @since 2015-5-6 上午10:18:56
	 * @author yangz
	 */
	@WebMethod
	public String CheckNoPjNologin(@WebParam(name = "userName") String userName,@WebParam(name="apptoken") String apptoken);
	
	

	/**
	 * <p>Description: 教务通知公告附件下载</p>
	 * @param id       id
	 * @param fileName 文件名
	 * @param length   长度
	 * @param strKey   秘钥
	 * @return
	 *
	 * @since 2015-5-15 上午09:04:26
	 * @author yangz
	 */
	@WebMethod
	public String getFileModel(
			@WebParam(name="id") String id,
			@WebParam(name="fileName") String fileName,
			@WebParam(name="length") String length,
			@WebParam(name="strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
	) ;
	

	
	/**
	 * <p>Description: 教学大纲</p>
	 * @param kcdm     课程代码
	 * @param strKey   秘钥
	 * @return
	 *
	 * @since 2015-5-21 下午05:14:08
	 * @author yangz
	 */
	@WebMethod
	public String CourseJxdgTextInfo(
			@WebParam(name="kcdm") String kcdm,
			@WebParam(name="strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
	);
	
	
	
	/**
	 * <p>Description: 获取通知公告总条数</p>
	 * @param userName 用户名
	 * @param start    起始页
	 * @param size     每页多少
	 * @param role     角色
	 * @param count    记录数
	 * @param strKey   秘钥
	 * @return
	 *
	 * @since 2015-8-7 下午05:14:18
	 * @author yangz
	 */
	@WebMethod
	public String GetPostTotalRecords(@WebParam(name = "userName") String userName,
			@WebParam(name= "start") String start,
			@WebParam(name= "size") String size,
			@WebParam(name = "role") String role,
			@WebParam(name = "count") String count,
			@WebParam(name = "strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
	);
	
	
}
