package com.jw.service.imp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.encoding.XMLType;
import javax.xml.ws.Holder;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.commons.codec.binary.Base64;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.common.Config;
import com.common.WebServiceConf;
import com.jw.org.tempuri.CourseJxdgInfo;
import com.jw.org.tempuri.ICampus;
import com.jw.org.tempuri.IClassPeriodTime;
import com.jw.org.tempuri.IClassRoom;
import com.jw.org.tempuri.IConfigurationInfo;
import com.jw.org.tempuri.ICourseSchedule;
import com.jw.org.tempuri.ICourseTask;
import com.jw.org.tempuri.IExamArrange;
import com.jw.org.tempuri.ILogin;
import com.jw.org.tempuri.INoticePost;
import com.jw.org.tempuri.IScoreSearch;
import com.jw.org.tempuri.IStudentInfo;
import com.jw.org.tempuri.ITeacherInfo;
import com.jw.org.tempuri.ITeacherSuperviseArrangement;
import com.jw.service.IEducationalPortXMLService;
import com.newmobile.login.CXFService.IWSSerServicePortType;
import com.newmobile.login.service.IWSSerService;
import com.oa.service.DownLoadBean;
import com.util.MiddleWareUtil;
import com.util.SelectItems;
import com.util.WebServiceUtil;
import com.util.base64Tobyte;
import com.util.encode.XmltoString;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;

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
@Service
@Component(value = "educationalPortXMLService")
public class EducationalPortXMLService implements IEducationalPortXMLService {
	private static Logger logger = Logger
			.getLogger(EducationalPortXMLService.class);
	private final String infromation = Config.getString("mobile.infromation");

	/**
	 * 连接符
	 */
	private final String AND = "&";
	
	
	private int ids=0;

	/**
	 * <p>
	 * Description:  登录验证
	 * </p>
	 * 
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @param strKey
	 *            密钥
	 * @param role
	 *            角色
	 * @param alone
	 *            是否统一登录
	 * @return
	 * 
	 * @since Oct 29, 2012 10:00:13 AM
	 * @author huangzhaoxia
	 */
	public String Login(String userName, String passWord, String role,
			String alone, String strKey, String apptoken) {
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			passWord  		= CodeUtil.decode(passWord, apptoken);
			role  			= CodeUtil.decode(role, apptoken);
			alone  			= CodeUtil.decode(alone, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String parameterList = userName + AND + passWord + AND + alone;// + AND
		String str = null;
		
		// 调用WebService
		ILogin service = (ILogin) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_LOGIN, ILogin.class, "Login")
				.create();

		str = service.login(parameterList, role, strKey);

		if (infromation.equals("0")) {
			logger.error("调用Login登录验证：" +"\n" + "parameterList=" + parameterList+"\n" +"role="+role+"\n" +"strKey="+strKey);
			logger.error("调用Login登录验证返回：" + str);
		}

		return str;
	}

	/**
	 * <p>
	 * Description: 学生登录有效验证
	 * </p>
	 * 
	 * @param userName
	 *            用户名
	 * @param strKey
	 *            密钥
	 * @return
	 * 
	 * @since 2014-7-16 下午03:08:26
	 * @author yangz
	 */
	@Override
	public String LoginStudentEffectiveChecking(String userName, String strKey,String apptoken) {
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// 调用WebService
		String str = null;

		ILogin service = (ILogin) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_LOGIN, ILogin.class,
				"LoginStudentEffectiveChecking").create();

		str = service.loginStudentEffectiveChecking(userName, strKey);

		if (infromation.equals("0")) {
			logger.error("调用LoginStudentEffectiveChecking登录有效性验证："+"\n"
					+ "userName=" + userName +"\n" + "strKey=" + strKey);
			logger.error("调用LoginStudentEffectiveChecking登录有效性验证返回：" + str);
		}

		return str;
	}

	/**
	 * <p>
	 * Description: 成绩查询
	 * </p>
	 * 
	 * @param sid
	 *            学号
	 * @param count
	 *            记录数
	 * @param strKey
	 *            密钥
	 * @return
	 * 
	 * @since 2014-7-16 下午03:09:33
	 * @author yangz
	 */
	public String ScoreSearch(String sid, String count, String strKey,String apptoken) {
		if(!ApptokenUtils.compare(sid, apptoken))
			return "app_token error";
		try {
			sid  		= CodeUtil.decode(sid, apptoken);
			count  		= CodeUtil.decode(count, apptoken);
			strKey  	= CodeUtil.decode(strKey, apptoken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// 调用WebService
		String str = null;

		IScoreSearch service = (IScoreSearch) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_SCORE, IScoreSearch.class,
				"ScoreSearch").create();

		str = service.scoreSearch(sid, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用ScoreSearch成绩查询：" +"\n"+ "sid=" + sid +"\n"+ "count=" + count
					+"\n"+ "strKey=" + strKey);
			logger.error("调用ScoreSearch成绩查询返回：" + str);
		}
		return str;
	}

	/**
	 * <p>
	 * Description: 成绩查询详情
	 * </p>
	 * 
	 * @param sid
	 *            学号
	 * @param classId
	 *            课程ID
	 * @param count
	 *            记录数
	 * @param strKey
	 *            密钥
	 * @return
	 * 
	 * @since 2014-7-16 下午03:10:20
	 * @author yangz
	 */
	public String ScoreInfoSearch(String sid, String classId, String count,
			String strKey, String apptoken) {
		if(!ApptokenUtils.compare(sid, apptoken))
			return "app_token error";
		try {
			sid  		= CodeUtil.decode(sid, apptoken);
			classId  	= CodeUtil.decode(classId, apptoken);
			count  		= CodeUtil.decode(count, apptoken);
			strKey  	= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String parameterList = sid + AND + classId;
		String str = null;
		// 调用WebService
		String status = Config.getString("mobile.schoolcode");
		if(status.equals("")){
		IScoreSearch service = (IScoreSearch) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_SCORE, IScoreSearch.class,
				"ScoreInfoSearch").create();

		str = service.scoreInfoSearch(parameterList, count, strKey);
		}
		else if(status.equals("cqdrsfxy")){
			IScoreSearch service = (IScoreSearch) WebServiceUtil.createFactoryJw(
					WebServiceConf.SERVICE_JWSERVICE_SCORE, IScoreSearch.class,
					"ScoreInfoSearch2").create();

			str = service.scoreInfoSearch2(parameterList, count, strKey);	
		}
		if (infromation.equals("0")) {
			logger.error("调用ScoreInfoSearch成绩查询详情："+"\n" + "sid=" + sid +"\n"+ "classId="
					+ classId +"\n"+ "count=" + count +"\n"+ "strKey=" + strKey);
			logger.error("调用ScoreInfoSearch成绩查询详情返回：" + str);
		}

		return str;
	}

	/**
	 * <p>
	 * Description: 考试安排
	 * </p>
	 * 
	 * @param sid
	 *            学号
	 * @param count
	 *            记录数
	 * @param strKey
	 *            密钥
	 * @return
	 * 
	 * @since Oct 31, 2012 10:36:38 AM
	 * @author huangzhaoxia
	 */
	@Override
	public String ExamArrange(String sid, String count, String strKey,String apptoken) {
		if(!ApptokenUtils.compare(sid, apptoken))
			return "app_token error";
		try {
			sid  		= CodeUtil.decode(sid, apptoken);
			count  		= CodeUtil.decode(count, apptoken);
			strKey  	= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// 调用WebService
		String str = null;
		IExamArrange service = (IExamArrange) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_EXAM, IExamArrange.class,
				"ExamArrange").create();

		str = service.examArrange(sid, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用ExamArrange考试安排："+"\n"+ "sid=" + sid +"\n"+ "count=" + count+"\n"
					+ "strKey=" + strKey);
			logger.error("调用ExamArrange考试安排返回：" + str);
		}
		return str;
	}

	/**
	 * <p>
	 * Description: 补考考试安排
	 * </p>
	 * 
	 * @param sid
	 *            学号
	 * @param count
	 *            记录数
	 * @param strKey
	 *            密钥
	 * @return
	 * 
	 * @since Oct 31, 2012 10:36:38 AM
	 * @author huangzhaoxia
	 */
	@Override
	public String MakeUpExamArrange(String sid, String count, String strKey,String apptoken) {
		if(!ApptokenUtils.compare(sid, apptoken))
			return "app_token error";
		try {
			sid  		= CodeUtil.decode(sid, apptoken);
			count  		= CodeUtil.decode(count, apptoken);
			strKey  	= CodeUtil.decode(strKey, apptoken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// 调用WebService

		String str = null;

		IExamArrange service = (IExamArrange) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_EXAM, IExamArrange.class,
				"MakeUpExamArrange").create();

		str = service.makeUpExamArrange(sid, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用MakeUpExamArrange补考考试安排 ：" +"\n"+ "sid=" + sid+"\n"
					+ "count=" + count+"\n" + "strKey=" + strKey);
			logger.error("调用MakeUpExamArrange补考考试安排 返回：" + str);
		}
		return str;
	}

	/**
	 * <p>
	 * Description: 毕业清考考试安排
	 * </p>
	 * 
	 * @param sid
	 *            学号
	 * @param count
	 *            记录数
	 * @param strKey
	 *            密钥
	 * @return
	 * 
	 * @since Oct 31, 2012 10:36:38 AM
	 * @author huangzhaoxia
	 */
	@Override
	public String GraduationMakeUpExamArrange(String sid, String count,
			String strKey,String apptoken) {
		if(!ApptokenUtils.compare(sid, apptoken))
			return "app_token error";
		try {
			sid  		= CodeUtil.decode(sid, apptoken);
			count  		= CodeUtil.decode(count, apptoken);
			strKey  	= CodeUtil.decode(strKey, apptoken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// 调用WebService

		String str = null;

		IExamArrange service = (IExamArrange) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_EXAM, IExamArrange.class,
				"GraduationMakeUpExamArrange").create();

		str = service.graduationMakeUpExamArrange(sid, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用GraduationMakeUpExamArrange毕业清考考试安排：" +"\n"+ "sid="
					+ sid+"\n" + "count=" + count +"\n"+ "strKey=" + strKey);
			logger.error("调用GraduationMakeUpExamArrange毕业清考考试安排返回：" + str);
		}
		return str;
	}

	/**
	 * <p>
	 * Description: 教师考试安排
	 * </p>
	 * 
	 * @param sid
	 *            教师ID
	 * @param type
	 *            类型（全部：1；带课班级考试安排：2；监考安排：3；主巡考安排：4;）
	 * @param count
	 *            记录数
	 * @param strKey
	 *            密钥
	 * @return
	 * 
	 * @since 2014-7-16 下午03:13:31
	 * @author yangz
	 */
	@Override
	public String TeacherSuperviseArrangementSearch(String sid, String type,
			String count, String strKey,String apptoken) {
		if(!ApptokenUtils.compare(sid, apptoken))
			return "app_token error";
		try {
			sid  		= CodeUtil.decode(sid, apptoken);
			type  		= CodeUtil.decode(type, apptoken);
			count  		= CodeUtil.decode(count, apptoken);
			strKey  	= CodeUtil.decode(strKey, apptoken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// 调用WebService

		String str = null;

		ITeacherSuperviseArrangement service = (ITeacherSuperviseArrangement) WebServiceUtil
				.createFactoryJw(WebServiceConf.SERVICE_JWSERVICE_TEACHER_EXAM,
						ITeacherSuperviseArrangement.class,
						"TeacherSuperviseArrangementSearch").create();
		
			str = service.teacherSuperviseArrangementSearch(sid + AND + type,
					count, strKey);
		if (infromation.equals("0")) {
			logger.error("调用TeacherSuperviseArrangementSearch教师考试安排：" +"\n"+ "sid="
					+ sid +"\n"+ "type=" + type +"\n"+ "count=" + count +"\n"+ "strKey="
					+ strKey);
			logger.error("调用TeacherSuperviseArrangementSearch教师考试安排返回：" + str);
		}
		return str;
	}

	/**
	 * <p>
	 * Description: 获取学年学期列表
	 * </p>
	 * 
	 * @param count
	 *            记录数
	 * @return
	 * 
	 * @since Oct 31, 2012 10:40:54 AM
	 * @author huangzhaoxia
	 */
	public String GetYearTermList(String count,String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			count  		= CodeUtil.decode(count, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		// 调用WebService

		String str = null;

		ICourseTask service = (ICourseTask) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_COURSETASK, ICourseTask.class,
				"GetYearTermList").create();

		str = service.getYearTermList(count);

		if (infromation.equals("0")) {
			logger.error("调用GetYearTermList获取学年学期列表：" +"\n"+ "count=" + count);
			logger.error("调用GetYearTermList获取学年学期列表返回：" + str);
		}
		return str;
	}

	/**
	 * <p>
	 * Description: 获取专业列表
	 * </p>
	 * 
	 * @param schoolCode
	 *            学院代码
	 * @param count
	 *            记录数
	 * @param strKey
	 *            密钥
	 * @return
	 * 
	 * @since Oct 31, 2012 10:46:41 AM
	 * @author huangzhaoxia
	 */
	public String GetMajorList(String schoolCode, String count, String strKey,String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			schoolCode  		= CodeUtil.decode(schoolCode, apptoken);
			count  				= CodeUtil.decode(count, apptoken);
			strKey  			= CodeUtil.decode(strKey, apptoken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		// 调用WebService

		String str = null;

		ICourseTask service = (ICourseTask) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_COURSETASK, ICourseTask.class,
				"GetMajorList").create();

		str = service.getMajorList(schoolCode, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用GetMajorList获取专业列表：" +"\n"+ "schoolCode=" + schoolCode+"\n"
					+ "count=" + count+"\n" + "strKey=" + strKey);
			logger.error("调用GetMajorList获取专业列表返回：" + str);
		}
		return str;
	}

	/**
	 * <p>
	 * Description: 获取班级列表
	 * </p>
	 * 
	 * @param xysid
	 *            学院id
	 * @param zysid
	 *            专业id
	 * @param nj
	 *            年级
	 * @param count
	 *            记录数
	 * @param strKey
	 *            密钥
	 * @return
	 * 
	 * @since 2014-4-28 下午04:34:50
	 * @author yangz
	 */
	public String GetClassList(String xysid, String zysid, String nj,
			String count, String strKey,String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			xysid  		= CodeUtil.decode(xysid, apptoken);
			zysid  		= CodeUtil.decode(zysid, apptoken);
			nj  		= CodeUtil.decode(nj, apptoken);
			count  		= CodeUtil.decode(count, apptoken);
			strKey  	= CodeUtil.decode(strKey, apptoken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		// 调用WebService
		String parameterList = xysid + AND + zysid + AND + nj;
		String str = null;

		ICourseTask service = (ICourseTask) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_COURSETASK, ICourseTask.class,
				"GetClassList").create();

		str = service.getClassList(parameterList, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用GetClassList获取班级列表" +"\n"+ "xysid=" + xysid +"\n"+ "zysid="
					+ zysid +"\n"+ "nj=" + nj +"\n"+ "count=" + count +"\n"+ "strKey="
					+ strKey);
			logger.error("调用GetClassList获取班级列表返回：" + str);
		}
		return str;
	}

	/**
	 * <p>
	 * Description: 获取课程列表
	 * </p>
	 * 
	 * @param count
	 *            记录数
	 * @return
	 * 
	 * @since Oct 31, 2012 10:50:08 AM
	 * @author huangzhaoxia
	 */
	public String GetCourseList(String count,String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			count  		= CodeUtil.decode(count, apptoken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		// 调用WebService

		String str = null;

		ICourseTask service = (ICourseTask) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_COURSETASK, ICourseTask.class,
				"GetCourseList").create();

		str = service.getCourseList(count);

		if (infromation.equals("0")) {
			logger.error("调用GetCourseList获取课程列表：" +"\n"+ "count=" + count);
			logger.error("调用GetCourseList获取课程列表返回：" + str);
		}
		return str;
	}

	/**
	 * <p>
	 * Description:获取学院列表
	 * </p>
	 * 
	 * @param count
	 *            记录数
	 * @return
	 * 
	 * @since Oct 31, 2012 10:51:21 AM
	 * @author huangzhaoxia
	 */
	public String GetInstituteList(String count,String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			count  		= CodeUtil.decode(count, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		// 调用WebService
		String str = null;

		ICourseTask service = (ICourseTask) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_COURSETASK, ICourseTask.class,
				"GetInstituteList").create();

		str = service.getInstituteList(count);

		if (infromation.equals("0")) {
			logger.error("调用GetInstituteList获取学院列表：" +"\n"+ "count=" + count);
			logger.error("调用GetInstituteList获取学院列表返回：" + str);
		}
		return str;
	}

	/**
	 * <p>
	 * Description: 获取年级列表
	 * </p>
	 * 
	 * @param count
	 *            记录数
	 * @return
	 * 
	 * @since Oct 31, 2012 10:52:45 AM
	 * @author huangzhaoxia
	 */
	public String GetGradeList(String count,String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			count  		= CodeUtil.decode(count, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		// 调用WebService
		String str = null;

		ICourseTask service = (ICourseTask) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_COURSETASK, ICourseTask.class,
				"GetGradeList").create();

		str = service.getGradeList(count);

		if (infromation.equals("0")) {
			logger.error("调用GetGradeList获取年级列表：" +"\n"+ "count=" + count);
			logger.error("调用GetGradeList获取年级列表返回：" + str);
		}
		return str;
	}

	/**
	 * <p>
	 * Description: 获取教师列表
	 * </p>
	 * 
	 * @param xymc
	 *            学院名称
	 * @param count
	 *            记录
	 * @param strKey
	 *            密钥
	 * @return
	 * 
	 * @since 2014-4-25 上午08:38:17
	 * @author yangz
	 */
	@Override
	public String GetTeacherList(String xymc,String teachername, String count, String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			xymc  		= CodeUtil.decode(xymc, apptoken);
			teachername = CodeUtil.decode(teachername, apptoken);
			count  		= CodeUtil.decode(count, apptoken);
			strKey  	= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		

		String str = null;
        String parameterList = xymc + AND + teachername;
		ICourseTask service = (ICourseTask) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_COURSETASK, ICourseTask.class,
				"GetTeacherList").create();

		str = service.getTeacherList(parameterList, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用GetTeacherList教师列表：" +"\n"+ "xymc=" + xymc+"\n"+"teachername="+teachername+"\n"
					+ "count=" + count+"\n" + "strKey=" + strKey);
			logger.error("调用GetTeacherList教师列表返回：" + str);
		}
		return str;
	}

	/**
	 * <p>
	 * Description: 获取专业方向
	 * </p>
	 * 
	 * @param grade
	 *            年级
	 * @param SpecialtyCode
	 *            专业代码
	 * @param count
	 *            记录数
	 * @param strKey
	 *            密钥
	 * @return
	 * 
	 * @since 2014-7-16 下午02:17:05
	 * @author yangz
	 */
	@Override
	public String GetMajorFieldList(String grade, String SpecialtyCode,
			String count, String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			grade  				= CodeUtil.decode(grade, apptoken);
			SpecialtyCode  		= CodeUtil.decode(SpecialtyCode, apptoken);
			count  				= CodeUtil.decode(count, apptoken);
			strKey  			= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		String str = null;
		String parameterList = grade + AND + SpecialtyCode;
		ICourseTask service = (ICourseTask) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_COURSETASK, ICourseTask.class,
				"GetMajorFieldList").create();

		str = service.getMajorFieldList(parameterList, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用GetMajorFieldList获取专业方向：" +"\n"+ "parameterList="
					+ parameterList +"\n"+ "count=" + count +"\n"+ "strKey=" + strKey);
			logger.error("调用GetMajorFieldList获取专业方向返回：" + str);
		}
		return str;
	}

	/**
	 * <p>
	 * Description:  课程任务搜索
	 * </p>
	 * 
	 * @param collegecode
	 *            学院代码
	 * @param SpecialtyCode
	 *            专业代码
	 * @param grade
	 *            年级
	 * @param inTerm
	 *            学期
	 * @param zyfx
	 *            专业方向
	 * @param count
	 * @return
	 * 
	 * @since Oct 31, 2012 10:55:52 AM
	 * @author huangzhaoxia
	 */
	public String CourseTaskSearch(String collegecode, String specialtyCode,
			String grade, String inTerm, String zyfx, String count,
			String strKey,String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			collegecode  		= CodeUtil.decode(collegecode, apptoken);
			specialtyCode  		= CodeUtil.decode(specialtyCode, apptoken);
			grade  				= CodeUtil.decode(grade, apptoken);
			inTerm  			= CodeUtil.decode(inTerm, apptoken);
			zyfx  				= CodeUtil.decode(zyfx, apptoken);
			count  				= CodeUtil.decode(count, apptoken);
			strKey  			= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		String str = null;
		String parameterList = specialtyCode + AND + grade + AND + inTerm + AND
				+ zyfx;
		// 调用WebService
		ICourseTask service = (ICourseTask) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_COURSETASK, ICourseTask.class,
				"CourseTaskSearch").create();

		str = service.courseTaskSearch(parameterList, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用CourseTaskSearch课程任务搜索：" +"\n"+ "collegecode="
					+ collegecode +"\n"+ "specialtyCode=" + specialtyCode +"\n"+ "grade="
					+ grade +"\n"+ "inTerm=" + inTerm +"\n"+ "zyfx=" + zyfx +"\n"+ "count="
					+ count +"\n"+ "strKey=" + strKey);
			logger.error("调用CourseTaskSearch课程任务搜索返回：" + str);
		}
		return str;
	}

	/**
	 * <p>
	 * Description:  课程任务详情
	 * </p>
	 * 
	 * @param courseid
	 *            课程id
	 * @param count
	 *            记录数
	 * @param strKey
	 *            密钥
	 * @return
	 * 
	 * @since Oct 31, 2012 10:59:24 AM
	 * @author huangzhaoxia
	 */
	public String CourseTaskInfo(String courseId, String count, String strKey,String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			courseId  		= CodeUtil.decode(courseId, apptoken);
			count  			= CodeUtil.decode(count, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		// 调用WebService
		String str = null;
		ICourseTask service = (ICourseTask) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_COURSETASK, ICourseTask.class,
				"CourseTaskInfo").create();

		str = service.courseTaskInfo(courseId, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用CourseTaskInfo课程任务详情：" +"\n"+ "courseId=" + courseId+"\n"
					+ "count=" + count +"\n"+ "strKey=" + strKey);
			logger.error("调用CourseTaskInfo课程任务详情返回：" + str);
		}
		return str;
	}

	/**
	 * <p>
	 * Description:  学生信息
	 * </p>
	 * 
	 * @param sid
	 *            学号
	 * @param count
	 *            记录数
	 * @param strKey
	 *            密钥
	 * @return
	 * 
	 * @since Oct 31, 2012 11:08:48 AM
	 * @author huangzhaoxia
	 */
	public String StudentInfoSearch(String sid, String count, String strKey,String apptoken) {
		if(!ApptokenUtils.compare(sid, apptoken))
			return "app_token error";
		try {
			sid  		= CodeUtil.decode(sid, apptoken);
			count  		= CodeUtil.decode(count, apptoken);
			strKey  	= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		// 调用WebService
		String str = null;
		IStudentInfo service = (IStudentInfo) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_STUDENT, IStudentInfo.class,
				"SearchInfoSearch").create();

		str = service.studentInfoSearch(sid, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用StudentInfoSearch学生信息："+"\n" + "sid=" + sid +"\n"+ "count="
					+ count +"\n"+ "strKey=" + strKey);
			logger.error("调用StudentInfoSearch学生信息返回：" + str);
		}
		return str;
	}

	/**
	 * <p>
	 * Description:学生照片信息
	 * </p>
	 * 
	 * @param sid
	 *            学号
	 * @param strKey
	 *            密钥
	 * @return
	 * 
	 * @since 2014-4-25 上午09:18:54
	 * @author yangz
	 */
	@Override
	public byte[] StudentPhotosSearch(String sid, String strKey,String apptoken) {
		if(!ApptokenUtils.compare(sid, apptoken))
			return "app_token error".getBytes();
		try {
			sid  		= CodeUtil.decode(sid, apptoken);
			strKey  	= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// 调用WebService
		byte[] str = null;
		IStudentInfo service = (IStudentInfo) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_STUDENT, IStudentInfo.class,
				"StudentPhotosSearch").create();

		str = service.studentPhotosSearch(sid, strKey);

		if (infromation.equals("0")) {
			logger.error("调用StudentPhotosSearch学生照片信息：" +"\n"+ "sid=" + sid+"\n"
					+ "strKey=" + strKey);
			logger.error("调用StudentPhotosSearch学生照片信息返回：" + str);
		}
		return str;

	}

	/**
	 * <p>
	 * Description:  课表查询
	 * </p>
	 * 
	 * @param userName
	 *            用户名
	 * @param year
	 *            学年
	 * @param term
	 *            学期
	 * @param role
	 *            角色
	 * @param count
	 *            记录数
	 * @return
	 * 
	 * @since Oct 31, 2012 11:19:33 AM
	 * @author huangzhaoxia
	 */
	public String CourseScheduleSearch(String userName, String year,
			String term, String role, String count, String strKey,String apptoken) {
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			year  		= CodeUtil.decode(year, apptoken);
			term  		= CodeUtil.decode(term, apptoken);
			role  		= CodeUtil.decode(role, apptoken);
			count  		= CodeUtil.decode(count, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String status = Config.getString("mobile.jwtypes");
		String str = null;
		if (status.equals("1")) {
			// 走教务系统接口
			String parameterList = userName + AND + year + AND + term;
			// 调用WebService
			ICourseSchedule service = (ICourseSchedule) WebServiceUtil
					.createFactoryJw(
							WebServiceConf.SERVICE_JWSERVICE_TIMETABLE,
							ICourseSchedule.class, "CourseScheduleSearch")
					.create();

			str = service.courseScheduleSearch(parameterList, role, count,
					strKey);

			if (infromation.equals("0")) {
				logger.error("调用CourseScheduleSearch课表查询：" +"\n"+ "userName="
						+ userName +"\n"+ "year=" + year +"\n"+ "term=" + term +"\n"+ "role="
						+ role +"\n"+ "count=" + count +"\n"+ "strKey=" + strKey);
				logger.error("调用CourseScheduleSearch课表查询返回：" + str);
			}
			return str;
		} else if (status.equals("2")) {
			// 走数据中心接口
			String endpointURL = WebServiceConf.WEBSERVICE_ZYMZ_JWKB;
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();
			String xq = null;
			String tmp = null;
			// 将大写转换成小写
			try {
				role = role.toLowerCase();
				if (year != null) {
					xq = year;
					year = year.substring(year.length() - 4, year.length());
					System.out.println("year" + year);
				}
				if (term != null) {
					term = xq + "-" + term;
					System.out.println("term" + term);
				}

				Call call;
				call = (Call) service.createCall();
				call.setTargetEndpointAddress(new java.net.URL(endpointURL));
				// call.setOperationName("getSzdacx" );//设置对应方法 (原
				// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
				// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
				QName opAddEntry = new QName(
						WebServiceConf.WEBSERVICE_NAMESPACE_ZYMZ,
						"CourseScheduleSearch");
				call.setOperationName(opAddEntry);
				tmp = (String) call.invoke(new Object[] { role, term, userName,
						year, strKey });
				tmp = tmp.replace("data", "table").replace("msg", "row");
				System.out.println("中央民族大学教务课表信息" + tmp); // 测试
			} catch (Exception e) {
				System.out.println("中央民族大学教务课表信息Erro" + e); // 测试
			}
			if (infromation.equals("0")) {
				logger.error("调用CourseScheduleSearch" +"\n"+ "userName=" + userName+"\n"
						+ "year=" + year +"\n"+ "term=" + term +"\n"+ "role=" + role+"\n"
						+ "count=" + count +"\n"+ "strKey=" + strKey);
				logger.error("调用CourseScheduleSearch返回：" + tmp);
			}
			return tmp;
		}
		return status;
	}
	
	
	/**
	 * <p>Description: 教师课表</p>
	 * @param userName
	 * @param teachername
	 * @param year
	 * @param term
	 * @param role
	 * @param count
	 * @param strKey
	 * @return
	 *
	 * @since 2015-2-2 下午03:48:57
	 * @author yangz
	 */
	@Override
	public String TeacherCourseScheduleSearch(String userName,
			String teachername, String year, String term, String role,
			String count, String strKey,String apptoken) {
		// TODO Auto-generated method stub
		
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  	= CodeUtil.decode(userName, apptoken);
			teachername = CodeUtil.decode(teachername, apptoken);
			year  		= CodeUtil.decode(year, apptoken);
			term  		= CodeUtil.decode(term, apptoken);
			role  		= CodeUtil.decode(role, apptoken);
			count  		= CodeUtil.decode(count, apptoken);
			strKey  	= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String str = null;
		String parameterList = teachername + AND + year + AND + term;
		// 调用WebService
		ICourseSchedule service = (ICourseSchedule) WebServiceUtil
				.createFactoryJw(
						WebServiceConf.SERVICE_JWSERVICE_TIMETABLE,
						ICourseSchedule.class, "CourseScheduleSearch")
				.create();

		str = service.courseScheduleSearch(parameterList, role, count,
				strKey);

		if (infromation.equals("0")) {
			logger.error("调用CourseScheduleSearch课表查询：" +"\n"+ "userName="
					+ userName +"\n"+ "year=" + year +"\n"+ "term=" + term +"\n"+ "role="
					+"\n"+ role + "count=" + count +"\n"+ "strKey=" + strKey);
			logger.error("调用CourseScheduleSearch课表查询返回：" + str);
		}
		return str;
	}

	/**
	 * <p>
	 * Description:  通知公告列表获取
	 * </p>
	 * 
	 * @param userName
	 *            用户名
	 * @param role
	 *            角色
	 * @param count
	 *            记录数
	 * @return
	 * 
	 * @since Oct 31, 2012 11:23:58 AM
	 * @author huangzhaoxia
	 */
	public String GetPostList(String userName, String start,String size,String role, String count,
			String strKey,String apptoken) {
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			start  			= CodeUtil.decode(start, apptoken);
			size  			= CodeUtil.decode(size, apptoken);
			role  			= CodeUtil.decode(role, apptoken);
			count  			= CodeUtil.decode(count, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// 调用WebService
		String str = null;
		String parameterList = userName + AND + start + AND + size;
		INoticePost service = (INoticePost) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_NOTICE, INoticePost.class,
				"GetPostList").create();

		str = service.getPostList(parameterList, role, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用GetPostList通知公告列表获取：" + "parameterList="+parameterList+"userName=" + userName+"\n"
					+ "role=" + role +"\n"+ "count=" + count +"\n"+ "strKey=" + strKey);
			logger.error("调用GetPostList通知公告列表获取返回：" + str);
		}

		return str;
	}

	/**
	 * <p>
	 * Description:  通知公告详情
	 * </p>
	 * 
	 * @param noticeid
	 *            通知公告id
	 * @param count
	 *            记录数
	 * @param strKey
	 *            密钥
	 * @return
	 * 
	 * @since Oct 31, 2012 11:27:20 AM
	 * @author huangzhaoxia
	 */
	public String PostInfoSearch(String noticeId, String count, String strKey,String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			noticeId  		= CodeUtil.decode(noticeId, apptoken);
			count  			= CodeUtil.decode(count, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		// 调用WebService
		String str = null;

		INoticePost service = (INoticePost) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_NOTICE, INoticePost.class,
				"PostInfoSearch").create();

		str = service.postInfoSearch(noticeId, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用PostInfoSearch通知公告详情：" +"\n"+ "noticeId=" + noticeId+"\n"
					+ "count=" + count +"\n"+ "strKey=" + strKey);
			logger.error("调用PostInfoSearch通知公告详情返回：" + str);
		}
		return str;
	}

	/**
	 * <p>
	 * Description:  学生成绩查询
	 * </p>
	 * 
	 * @param sid
	 *            学号
	 * @param count
	 *            记录数
	 * @param strKey
	 *            密钥
	 * @return
	 * 
	 * @since Oct 31, 2012 11:27:20 AM
	 * @author huangzhaoxia
	 */
	public String GradeScoreInfoSearch(String sid, String count, String strKey,String apptoken) {
		if(!ApptokenUtils.compare(sid, apptoken))
			return "app_token error";
		try {
			sid  		= CodeUtil.decode(sid, apptoken);
			count  		= CodeUtil.decode(count, apptoken);
			strKey  	= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// 调用WebService
		String str = null;

		IScoreSearch service = (IScoreSearch) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_SCORE, IScoreSearch.class,
				"GradeScoreInfoSearch").create();

		str = service.gradeScoreInfoSearch(sid, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用GradeScoreInfoSearch学生成绩查询：" +"\n"+ "sid=" + sid+"\n"
					+ "count=" + count+"\n" + "strKey=" + strKey);
			logger.error("调用GradeScoreInfoSearch学生成绩查询返回：" + str);
		}

		return str;
	}

	/**
	 * <p>
	 * Description:  教务配置信息
	 * </p>
	 * 
	 * @param noticeid
	 *            通知id
	 * @param count
	 *            记录数
	 * @return
	 * 
	 * @since Oct 31, 2012 11:27:20 AM
	 * @author huangzhaoxia
	 */
	public String ConfigurationInfo(String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		// 调用WebService
		String str = null;

		IConfigurationInfo service = (IConfigurationInfo) WebServiceUtil
				.createFactoryJw(WebServiceConf.SERVICE_JWSERVICE_CONFIG,
						IConfigurationInfo.class, "ConfigurationInfo").create();

		str = service.configurationInfo();

		if (infromation.equals("0")) {
			logger.error("调用ConfigurationInfo教务配置信息：");
			logger.error("调用ConfigurationInfo教务配置信息返回：" + str);
		}
		return str;
	}

	/**
	 * 教务Web App配置信息
	 * 
	 * @since 2014-1-7 4:27:20 9M
	 * @author huangzhaoxia
	 * @return
	 */
	@Override
	public String GetJwWebAppConfigInfo(String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		StringBuffer msg = new StringBuffer();
		msg.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?> <WebApp><ServiceIp><![CDATA[");
		msg.append(WebServiceConf.SERVICE_JW_WEB_APP_SERVICE);
		msg.append("]]></ServiceIp></WebApp>");
		if (infromation.equals("0")) {
			logger.error("调用GetJwWebAppConfigInfo教务Web App配置信息：");
			logger.error("调用GetJwWebAppConfigInfo教务Web App配置信息返回："
					+ msg.toString());
		}
		return msg.toString();
	}

	/**
	 * <p>
	 * Description:课程任务详情
	 * </p>
	 * 
	 * @param kcsid
	 *            课程id
	 * @param count
	 *            记录数
	 * @param strKey
	 *            密钥
	 * @return
	 * 
	 * @since 2014-4-25 上午08:47:35
	 * @author yangz
	 */
	@Override
	public String CourseTaskInfoSearch(String kcsid, String count, String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			kcsid  		= CodeUtil.decode(kcsid, apptoken);
			count  		= CodeUtil.decode(count, apptoken);
			strKey  	= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		String str = null;

		ICourseTask service = (ICourseTask) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_COURSETASK, ICourseTask.class,
				"GetTeacherList").create();

		str = service.courseTaskSearch(kcsid, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用CourseTaskInfoSearch课程任务详情：" +"\n"+ "kcsid=" + kcsid+"\n"
					+ "count=" + count +"\n"+ "strKey=" + strKey);
			logger.error("调用CourseTaskInfoSearch课程任务详情返回：" + str);
		}
		return str;
	}

	/**
	 * <p>
	 * Description:班级课表查询
	 * </p>
	 * 
	 * @param bjsid
	 *            班级id
	 * @param year
	 *            学年
	 * @param term
	 *            学期
	 * @param count
	 *            记录数
	 * @return
	 * 
	 * @since 2014-4-25 上午08:57:34
	 * @author yangz
	 */
	@Override
	public String CourseScheduleClassSearch(String bjsid, String year,
			String term, String count, String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			bjsid  		= CodeUtil.decode(bjsid, apptoken);
			year  		= CodeUtil.decode(year, apptoken);
			term  		= CodeUtil.decode(term, apptoken);
			count  		= CodeUtil.decode(count, apptoken);
			strKey  	= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// 调用WebService
		String str = null;
		String parameterList = bjsid + AND + year + AND + term;

		ICourseSchedule service = (ICourseSchedule) WebServiceUtil
				.createFactoryJw(WebServiceConf.SERVICE_JWSERVICE_TIMETABLE,
						ICourseSchedule.class, "TjCourseScheduleSearch")
				.create();

		str = service.tjCourseScheduleSearch(parameterList, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用CourseScheduleClassSearch班级课表查询：" +"\n"+ "bjsid="
					+ bjsid +"\n"+ "year=" + year +"\n"+ "term=" + term +"\n"+ "count="
					+ count +"\n"+ "strKey=" + strKey);
			logger.error("调用CourseScheduleClassSearch班级课表查询返回：" + str);
		}
		return str;
	}

	/**
	 * <p>
	 * Description:上课时间段查询（走教务接口）
	 * </p>
	 * 
	 * @param xn
	 *            学年
	 * @param xq
	 *            学期
	 * @param count
	 *            记录数
	 * @param strKey
	 *            密钥
	 * @return
	 * 
	 * @since 2014-4-23 下午03:58:44
	 * @author yangz
	 */
	@Override
	public String IClassPeriodTime(String xn, String xq, String count,
			String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			xn  		= CodeUtil.decode(xn, apptoken);
			xq  		= CodeUtil.decode(xq, apptoken);
			count  		= CodeUtil.decode(count, apptoken);
			strKey  	= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String status = Config.getString("mobile.jwtypes");
		if (status.equals("2")) {
			String endpointURL = WebServiceConf.WEBSERVICE_ZYMZ_KXSJD;
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();
			String tmp = null;
			String sjzxstrKey = "nD9i2ZemQbYrY+EnX8V9OA1NxBO2HLhI";
			try {
				Call call;
				call = (Call) service.createCall();
				call.setTargetEndpointAddress(new java.net.URL(endpointURL));
				// call.setOperationName("getSzdacx" );//设置对应方法 (原
				// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
				// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
				QName opAddEntry = new QName(
						WebServiceConf.WEBSERVICE_NAMESPACE_ZYMZ, "getkxsjd");
				call.setOperationName(opAddEntry);
				tmp = (String) call.invoke(new Object[] { sjzxstrKey });// 实际调用
				tmp = tmp.replace("data", "table").replace("msg", "row").replace("kxsjd", "zwsjd");
				System.out.print("中央民族上课时间段查询" + tmp); // 测试
			} catch (Exception e) {
				System.out.print("中央民族上课时间段查询Erro" + e); // 测试
			}
			if (infromation.equals("0")) {
				logger.error("调用IClassPeriodTime中央民族上课时间段查询返回为：" + tmp);
			}
			return tmp;
		} else if (status.equals("1")) {
			String parameterList = xn + AND + xq;
			String str = null;
			// 调用WebService
			IClassPeriodTime service = (IClassPeriodTime) WebServiceUtil
					.createFactoryJw(
							WebServiceConf.SERVICE_JWSERVICE_CLASSPERIODTIME,
							IClassPeriodTime.class, "ClassPeriodTimeInfo")
					.create();

			str = service.classPeriodTimeInfo(parameterList, count, strKey);

			if (infromation.equals("0")) {
				logger.error("调用IClassPeriodTime上课时间段查询（走教务接口）：" +"\n"+ "xn=" + xn+"\n"
						+ "xq=" + xq +"\n"+ "count=" + count +"\n"+ "strKey=" + strKey);
				logger.error("调用IClassPeriodTime上课时间段查询（走教务接口）返回：" + str);
			}
			return str;
		}
		return status;
	}

	/**
	 * <p>
	 * Description: 校区查询
	 * </p>
	 * 
	 * @return
	 * 
	 * @since 2014-4-24 上午09:50:21
	 * @author yangz
	 */
	@Override
	public String ICampus(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String status = Config.getString("mobile.jwtypes");
		if (status.equals("2")) {
			String endpointURL = WebServiceConf.WEBSERVICE_ZYMZ_XQ;
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();
			String tmp = null;
			String sjzxstrKey = "nD9i2ZemQbYrY+EnX8V9OA1NxBO2HLhI";
			try {
				Call call;
				call = (Call) service.createCall();
				call.setTargetEndpointAddress(new java.net.URL(endpointURL));
				// call.setOperationName("getSzdacx" );//设置对应方法 (原
				// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
				// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
				QName opAddEntry = new QName(
						WebServiceConf.WEBSERVICE_NAMESPACE_ZYMZ, "getxq");
				call.setOperationName(opAddEntry);
				tmp = (String) call.invoke(new Object[] { sjzxstrKey });// 实际调用
				tmp = tmp.replace("data", "table").replace("msg", "row");
				System.out.print("中央民族校区查询" + tmp); // 测试
			} catch (Exception e) {
				System.out.print("中央民族校区查询Erro" + e); // 测试
			}
			if (infromation.equals("0")) {
				logger.error("调用IClassPeriodTime中央民族校区查询返回为：" + tmp);
			}
			return tmp;
		} else if (status.equals("1")) {
			String str = null;
			ICampus service = (ICampus) WebServiceUtil.createFactoryJw(
					WebServiceConf.SERVICE_JWSERVICE_CAMPUSSEARCH,
					ICampus.class, "CampusInfo").create();
			str = service.campusInfo();
			if (infromation.equals("0")) {
				logger.error("调用ICampus校区查询：");
				logger.error("调用ICampus校区查询返回：" + str);
			}
			return str;
		}
		return status;
	}

	/**
	 * <p>
	 * Description: 教室类别查询
	 * </p>
	 * 
	 * @return
	 * 
	 * @since 2014-4-24 上午10:09:36
	 * @author yangz
	 */
	@Override
	public String IClassroomCategory(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
		String status = Config.getString("mobile.jwtypes");
		if (status.equals("2")) {
			String endpointURL = WebServiceConf.WEBSERVICE_ZYMZ_KXJSLX;
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();
			String tmp = null;
			String strKey = "nD9i2ZemQbYrY+EnX8V9OA1NxBO2HLhI";
			try {
				Call call;
				call = (Call) service.createCall();
				call.setTargetEndpointAddress(new java.net.URL(endpointURL));
				// call.setOperationName("getSzdacx" );//设置对应方法 (原
				// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
				// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
				QName opAddEntry = new QName(
						WebServiceConf.WEBSERVICE_NAMESPACE_ZYMZ, "getjslx");
				call.setOperationName(opAddEntry);
				tmp = (String) call.invoke(new Object[] { strKey });// 实际调用
				tmp = tmp.replace("data", "table").replace("msg", "row").replace("lxmc", "jslbmc");
				System.out.print("中央民族教室类别查询" + tmp); // 测试
				
			} catch (Exception e) {
				System.out.print("中央民族教室类别查询Erro" + e); // 测试
			}
			if (infromation.equals("0")) {
				logger.error("调用IClassroomCategory中央民族教室类别查询返回为：" + tmp);
			}
			return tmp;
		} else if (status.equals("1")) {
			String str = null;
			IClassRoom service = (IClassRoom) WebServiceUtil.createFactoryJw(
					WebServiceConf.SERVICE_JWSERVICE_CLASSROOMSEARCH,
					IClassRoom.class, "ClassRoomCategoryInfo").create();

			str = service.classRoomCategoryInfo();

			if (infromation.equals("0")) {
				logger.error("调用IClassroomCategory教室类别查询：");
				logger.error("调用IClassroomCategory教室类别查询返回：" + str);
			}
			return str;
		}
		return status;
	}

	/**
	 * <p>
	 * Description: 学生异动信息
	 * </p>
	 * 
	 * @param sid
	 *            学号id
	 * @param count
	 *            记录数
	 * @param strKey
	 *            加密后密钥
	 * @return
	 * 
	 * @since 2014-6-10 上午08:49:58
	 * @author yangz
	 */
	@Override
	public String StudentChangedSearch(String sid, String count, String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(sid, apptoken))
			return "app_token error";
		try {
			sid  		= CodeUtil.decode(sid, apptoken);
			count  		= CodeUtil.decode(count, apptoken);
			strKey  	= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str = null;

		IStudentInfo service = (IStudentInfo) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_STUDENT, IStudentInfo.class,
				"StudentChangedSearch").create();

		str = service.studentChangedSearch(sid, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用StudentChangedSearch学生异动信息：" +"\n"+ "sid=" + sid+"\n"
					+ "count=" + count +"\n"+ "strKey=" + strKey);
			logger.error("调用StudentChangedSearch学生异动信息返回：" + str);
		}
		return str;
	}

	/**
	 * <p>
	 * Description: 学生奖励
	 * </p>
	 * 
	 * @param sid
	 *            学号id
	 * @param count
	 *            记录数
	 * @param strKey
	 *            加密后密钥
	 * @return
	 * 
	 * @since 2014-6-10 上午09:02:20
	 * @author yangz
	 */
	@Override
	public String StudentRewardSearch(String sid, String count, String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(sid, apptoken))
			return "app_token error";
		try {
			sid  		= CodeUtil.decode(sid, apptoken);
			count  		= CodeUtil.decode(count, apptoken);
			strKey  	= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str = null;

		IStudentInfo service = (IStudentInfo) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_STUDENT, IStudentInfo.class,
				"StudentRewardSearch").create();

		str = service.studentRewardSearch(sid, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用StudentRewardSearch学生奖励：" +"\n"+ "sid=" + sid +"\n"+ "count="
					+ count +"\n"+ "strKey=" + strKey);
			logger.error("调用StudentRewardSearch学生奖励返回：" + str);
		}
		return str;
	}

	/**
	 * <p>
	 * Description: 学生惩罚
	 * </p>
	 * 
	 * @param sid
	 *            学号id
	 * @param count
	 *            记录数
	 * @param strKey
	 *            加密后密钥
	 * @return
	 * 
	 * @since 2014-6-10 上午09:06:11
	 * @author yangz
	 */
	@Override
	public String StudentPunishmentSearch(String sid, String count,
			String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(sid, apptoken))
			return "app_token error";
		try {
			sid  		= CodeUtil.decode(sid, apptoken);
			count  		= CodeUtil.decode(count, apptoken);
			strKey  	= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str = null;
		IStudentInfo service = (IStudentInfo) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_STUDENT, IStudentInfo.class,
				"StudentPunishmentSearch").create();

		str = service.studentPunishmentSearch(sid, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用StudentPunishmentSearch学生惩罚：" +"\n"+ "sid=" + sid+"\n"
					+ "count=" + count +"\n"+ "strKey=" + strKey);
			logger.error("调用StudentPunishmentSearch学生惩罚返回：" + str);
		}
		return str;
	}

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
	@Override
	public String GetClassroomList(String jsmc, String count, String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			jsmc  		= CodeUtil.decode(jsmc, apptoken);
			count  		= CodeUtil.decode(count, apptoken);
			strKey  	= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		String str = null;
		String parameterList=jsmc;
		IClassRoom service = (IClassRoom) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_CLASSROOMSEARCH, IClassRoom.class,
				"GetClassroomList").create();

		str = service.getClassroomList(parameterList, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用GetClassroomList教室名称模糊查询：" +"\n"+ "jsmc=" + jsmc+"\n"
					+ "count=" + count +"\n"+ "strKey=" + strKey);
			logger.error("调用GetClassroomList教室名称模糊查询返回：" + str);
		}
		return str;
	}

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
	@Override
	public String GetAllCourseSchedule(String xn, String xq, String bjmc,
			String sid, String role, String jsbh, String jslbmc, String count,
			String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(sid, apptoken))
			return "app_token error";
		try {
			sid  		= CodeUtil.decode(sid, apptoken);
			xn  		= CodeUtil.decode(xn, apptoken);
			xq  		= CodeUtil.decode(xq, apptoken);
			bjmc  		= CodeUtil.decode(bjmc, apptoken);
			role  		= CodeUtil.decode(role, apptoken);
			jsbh  		= CodeUtil.decode(jsbh, apptoken);
			jslbmc  	= CodeUtil.decode(jslbmc, apptoken);
			count  		= CodeUtil.decode(count, apptoken);
			strKey  	= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str = null;
		String parameterList=xn+AND+xq+AND+bjmc+AND+sid+AND+role+AND+jsbh+AND+jslbmc;
		ICourseSchedule service = (ICourseSchedule) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_TIMETABLE, ICourseSchedule.class,
				"TJAllCourseScheduleSearch").create();

		str = service.tjAllCourseScheduleSearch(parameterList, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用AllCourseSchedule所有课表查询：" +"\n"+ "parameterList=" + parameterList+"\n"
					+ "count=" + count +"\n"+ "strKey=" + strKey);
			logger.error("调用AllCourseSchedule所有课表查询返回：" + str);
		}
		return str;
	}

	/**
	 * <p>Description: 教室课表</p>
	 * @param xn
	 * @param xq
	 * @param jsbh
	 * @param count
	 * @param strKey
	 * @return
	 *
	 * @since 2015-2-2 下午02:20:03
	 * @author yangz
	 */
	@Override
	public String GetClassRoomCourseSchedule(String xn,String xq,String bjmc,String sid,
			String role,String jsbh,String jslbmc,String count,String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(sid, apptoken))
			return "app_token error";
		try {
			sid  		= CodeUtil.decode(sid, apptoken);
			xn  		= CodeUtil.decode(xn, apptoken);
			xq  		= CodeUtil.decode(xq, apptoken);
			bjmc  		= CodeUtil.decode(bjmc, apptoken);
			strKey  	= CodeUtil.decode(strKey, apptoken);
			role  		= CodeUtil.decode(role, apptoken);
			jsbh  		= CodeUtil.decode(jsbh, apptoken);
			jslbmc  	= CodeUtil.decode(jslbmc, apptoken);
			count  		= CodeUtil.decode(count, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str = null;
		String parameterList=xn+AND+xq+AND+""+AND+""+AND+""+AND+jsbh+AND+"";
		ICourseSchedule service = (ICourseSchedule) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_TIMETABLE, ICourseSchedule.class,
				"TJAllCourseScheduleSearch").create();

		str = service.tjAllCourseScheduleSearch(parameterList, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用AllCourseSchedule所有课表查询：" +"\n"+ "parameterList=" + parameterList+"\n"
					+ "count=" + count +"\n"+ "strKey=" + strKey);
			logger.error("调用AllCourseSchedule所有课表查询返回：" + str);
		}
		return str;
	}

	/**
	 * <p>Description:   考试安排</p>
	 * @param className  课程名称
	 * @param bjName     班级名称
	 * @param jsmc       教室名称
	 * @param startdate  开始日期
	 * @param enddate    结束日期
	 * @return
	 *
	 * @since 2015-3-12 上午11:24:59
	 * @author yangz
	 */
	/*@Override
	public String GetIExamArrange1(String className, String bjName,
			String jsmc, String startdate, String enddate, String count, String strKey ) {
		// TODO Auto-generated method stub

		String str = null;
		String parameterList=className+AND+bjName+AND+jsmc+AND+startdate+AND+enddate;
		IExamArrange service = (IExamArrange) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_EXAM, IExamArrange.class,
				"ExamArrange1").create();

		str = service.examArrange1(parameterList, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用GetIExamArrange1考试安排：" +"\n"+ "parameterList=" + parameterList+"\n"
					+ "count=" + count +"\n"+ "strKey=" + strKey);
			logger.error("调用GetIExamArrange1考试安排返回：" + str);
		}
		return str;
	}*/


	/**
	 * <p>Description:   毕业补考</p>
	 * @param className  课程名称
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
/*	@Override
	public String GetIGraduationMakeUpExamArrange1(String className,
			String bjName, String jsmc, String startdate, String enddate,
			String count, String strKey) {
		// TODO Auto-generated method stub
		
		String str = null;
		String parameterList=className+AND+bjName+AND+jsmc+AND+startdate+AND+enddate;
		IExamArrange service = (IExamArrange) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_EXAM, IExamArrange.class,
				"GraduationMakeUpExamArrange1").create();

		str = service.graduationMakeUpExamArrange1(parameterList, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用GetIMakeUpExamArrange1毕业补考：" +"\n"+ "parameterList=" + parameterList+"\n"
					+ "count=" + count +"\n"+ "strKey=" + strKey);
			logger.error("调用GetIMakeUpExamArrange1毕业补考返回：" + str);
		}
		return str;
		
	}*/

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
/*	@Override
	public String GetIMakeUpExamArrange1(String className, String bjName,
			String jsmc, String startdate, String enddate, String count,
			String strKey) {
		// TODO Auto-generated method stub
		String str = null;
		String parameterList=className+AND+bjName+AND+jsmc+AND+startdate+AND+enddate;
		IExamArrange service = (IExamArrange) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_EXAM, IExamArrange.class,
				"MakeUpExamArrange1").create();

		str = service.makeUpExamArrange1(parameterList, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用GetIGraduationMakeUpExamArrange1补考考试安排：" +"\n"+ "parameterList=" + parameterList+"\n"
					+ "count=" + count +"\n"+ "strKey=" + strKey);
			logger.error("调用GetIGraduationMakeUpExamArrange1补考考试安排返回：" + str);
		}
		return str;
	}*/

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
	@Override
	public String GetCourseList1(String schoolCode, String classname,
			String count, String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			schoolCode  		= CodeUtil.decode(schoolCode, apptoken);
			classname  			= CodeUtil.decode(classname, apptoken);
			count  				= CodeUtil.decode(count, apptoken);
			strKey  			= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		String str = null;
		String parameterList=schoolCode+ AND +classname;
		ICourseTask service = (ICourseTask) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_COURSETASK, ICourseTask.class,
				"GetCourseList1").create();

		str = service.getCourseList1(parameterList, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用GetCourseList1课程名称模糊查询：" +"\n"+"schoolCode="+schoolCode+"\n"+"classname="+classname+"\n"+ "count=" + count+"\n"+"strKey="+strKey+"\n"+"parameterList="+parameterList);
			logger.error("调用GetCourseList1课程名称模糊查询返回：" + str);
		}
		return str;
	}

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
	@Override
	public String GetIAllCourseSchedule1(String xn, String xq,
			String classcode, String startdate, String enddate,
			String schoolname, String teaname, String count, String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			xn  		= CodeUtil.decode(xn, apptoken);
			xq  		= CodeUtil.decode(xq, apptoken);
			classcode  	= CodeUtil.decode(classcode, apptoken);
			startdate  	= CodeUtil.decode(startdate, apptoken);
			enddate  	= CodeUtil.decode(enddate, apptoken);
			schoolname  = CodeUtil.decode(schoolname, apptoken);
			teaname  	= CodeUtil.decode(teaname, apptoken);
			count  		= CodeUtil.decode(count, apptoken);
			strKey  	= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		String str = null;
		String parameterList=xn+AND+xq+AND+classcode+AND+startdate+AND+enddate+AND+schoolname+AND+teaname;
		ICourseSchedule service = (ICourseSchedule) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_TIMETABLE, ICourseSchedule.class,
				"TJAllCourseScheduleSearch1").create();

		str = service.tjAllCourseScheduleSearch1(parameterList, count, strKey);
		if (infromation.equals("0")) {
			logger.error("调用GetIAllCourseSchedule1所有课表查询：" +"\n"+"parameterList="+parameterList+"\n"+"xn="+xn+"\n"+"xq="+xq+"\n"+"classcode="+classcode+"\n"+"startdate="+startdate+"\n"+"enddate="+enddate+"\n"+"schoolname="+schoolname+"\n"+"teaname="+teaname+"\n"+"count=" + count+"\n"+"strKey="+strKey);
			logger.error("调用GetIAllCourseSchedule1所有课表查询返回：" + str);
		}
		return str;
	}

	/**
	 * <p>Description: 某个学期的总周次</p>
	 * @return
	 *
	 * @since 2015-3-16 上午10:03:23
	 * @author yangz
	 */
	@Override
	public String GetZZCJK(String xn,String xq,String count,String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			xn  		= CodeUtil.decode(xn, apptoken);
			xq  		= CodeUtil.decode(xq, apptoken);
			count  		= CodeUtil.decode(count, apptoken);
			strKey  	= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		String str = null;
        String parameterList=xn + AND + xq;
		IConfigurationInfo service = (IConfigurationInfo) WebServiceUtil
				.createFactoryJw(WebServiceConf.SERVICE_JWSERVICE_CONFIG,
						IConfigurationInfo.class, "GetZZCJK").create();

		str = service.getZZCJK(parameterList, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用GetZZCJK某个学期的总周次：");
			logger.error("调用GetZZCJK某个学期的总周次返回：" + str);
		}
		return str;
	}

	/**
	 * <p>Description: 取得某个学期的第一周第一天的日期</p>
	 * @return
	 *
	 * @since 2015-3-16 上午10:08:00
	 * @author yangz
	 */
	@Override
	public String GetKXDYTJK(String xn,String xq,String count,String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			xn  		= CodeUtil.decode(xn, apptoken);
			xq  		= CodeUtil.decode(xq, apptoken);
			count  		= CodeUtil.decode(count, apptoken);
			strKey  	= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		String str = null;
		String parameterList=xn + AND + xq;
		IConfigurationInfo service = (IConfigurationInfo) WebServiceUtil
				.createFactoryJw(WebServiceConf.SERVICE_JWSERVICE_CONFIG,
						IConfigurationInfo.class, "GetKXDYTJK").create();

		str = service.getKXDYTJK(parameterList, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用GetKXDYTJK取得某个学期的第一周第一天的日期：");
			logger.error("调用GetKXDYTJK取得某个学期的第一周第一天的日期返回：" + str);
		}
		return str;
	}

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
	@Override
	public String GetTeacherSuperviseArrangement1(String classname,
			String bjname, String classroom, String startdate, String enddate,
			String count, String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			classname  		= CodeUtil.decode(classname, apptoken);
			bjname  		= CodeUtil.decode(bjname, apptoken);
			classroom  		= CodeUtil.decode(classroom, apptoken);
			startdate  		= CodeUtil.decode(startdate, apptoken);
			enddate  		= CodeUtil.decode(enddate, apptoken);
			count  			= CodeUtil.decode(count, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		String str = null;
        String parameterList=classname + AND + bjname + AND + classroom + AND + startdate + AND + enddate;
		ITeacherSuperviseArrangement service = (ITeacherSuperviseArrangement) WebServiceUtil
				.createFactoryJw(WebServiceConf.SERVICE_JWSERVICE_TEACHER_EXAM,
						ITeacherSuperviseArrangement.class,
						"TeacherSuperviseArrangementSearch1").create();
		
			str = service.teacherSuperviseArrangementSearch1(parameterList,
					count, strKey);
		if (infromation.equals("0")) {
			logger.error("调用GetTeacherSuperviseArrangement1教师考试安排：" +"\n"+ "classname="
					+ classname +"\n"+ "bjname=" + bjname +"\n" +"classroom="+classroom+"\n"+"startdate="+startdate+"\n"+"enddate="+enddate+"\n"+ "count=" + count +"\n"+ "strKey="
					+ strKey);
			logger.error("调用GetTeacherSuperviseArrangement1教师考试安排返回：" + str);
		}
		return str;
	}
	

	/**
	 * <p>Description: 教师信息查询</p>
	 * @param sid      教师姓名
	 * @param count    记录数
	 * @param strKey   秘钥
	 * @return
	 *
	 * @since 2015-4-17 下午01:53:20
	 * @author yangz
	 */
	@Override
	public String GetTeacherInfoSearch(String sid, String count, String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(sid, apptoken))
			return "app_token error";
		try {
			sid  		= CodeUtil.decode(sid, apptoken);
			count  		= CodeUtil.decode(count, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str = null;
		ITeacherInfo service = (ITeacherInfo) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_TEACHERINFOCONTRACT, ITeacherInfo.class,
				"TeacherInfoSearch").create();

		str = service.teacherInfoSearch(sid, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用GetTeacherInfoSearch老师信息："+"\n" + "sid=" + sid +"\n"+ "count="
					+ count +"\n"+ "strKey=" + strKey);
			logger.error("调用GetTeacherInfoSearch老师信息返回：" + str);
		}
		return str;
	}

	/**
	 * <p>Description: 教师照片信息</p>
	 * @param sid      教师姓名
	 * @param strKey   秘钥
	 * @return
	 *
	 * @since 2015-4-17 下午02:06:35
	 * @author yangz
	 */
	@Override
	public byte[] TeacherPhotosSearch(String sid, String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(sid, apptoken))
			return "app_token error".getBytes();
		try {
			sid  		= CodeUtil.decode(sid, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		byte[] str = null;
		ITeacherInfo service = (ITeacherInfo) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_TEACHERINFOCONTRACT, ITeacherInfo.class,
				"TeacherPhotosSearch").create();

		str = service.teacherPhotosSearch(sid, strKey);

		if (infromation.equals("0")) {
			logger.error("调用TeacherPhotosSearch教师照片信息：" +"\n"+ "sid=" + sid+"\n"
					+ "strKey=" + strKey);
			logger.error("调用StudentPhotosSearch教师照片信息返回：" + str);
		}
		return str;
	}

	/**
	 * <p>Description: 获取教学大纲</p>
	 * @param kcdm     课程代码
	 * @param strKey   秘钥
	 * @return
	 *
	 * @since 2015-4-20 下午05:01:47
	 * @author yangz
	 */
	@Override
	public String CourseJxdgInfo(String kcdm, String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			kcdm  		= CodeUtil.decode(kcdm, apptoken);
			strKey  	= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		String str = null;
		ICourseTask service = (ICourseTask) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_COURSETASK, ICourseTask.class,
				"CourseJxdgInfo").create();

		str = service.courseJxdgInfo(kcdm, strKey);

		if (infromation.equals("0")) {
			logger.error("调用CourseJxdgInfo获取教学大纲："+"\n" + "kcdm" + kcdm +"\n"+ "strKey=" + strKey);
			logger.error("调用CourseJxdgInfo获取教学大纲返回：" + str);
		}
		return str;
	}

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
	@Override
	public String LoginNoJsCheck(String userName, String passWord, String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			passWord  		= CodeUtil.decode(passWord, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String parameterList = userName + AND + passWord;// + AND
		String str = null;
		
		// 调用WebService
		ILogin service = (ILogin) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_LOGIN, ILogin.class, "LoginNoJsCheck")
				.create();

		str = service.loginNoJsCheck(parameterList,strKey);

		if (infromation.equals("0")) {
			logger.error("调用LoginNoJsCheck用户登录无需选择角色系统自动判定角色验证：" +"\n" + "parameterList=" + parameterList+"\n"+"strKey="+strKey);
			logger.error("调用LoginNoJsCheck用户登录无需选择角色系统自动判定角色验证返回：" + str);
		}

		return str;
	}

	/**
	 * <p>Description:  中国矿业大学未评价不能登录系统功能</p>
	 * @param userName  用户名
	 * @return
	 *
	 * @since 2015-5-6 上午10:18:56
	 * @author yangz
	 */
	@Override
	public String CheckNoPjNologin(String userName,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String parameterList = userName;// + AND
		String str = null;
		
		// 调用WebService
		ILogin service = (ILogin) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_LOGIN, ILogin.class, "CheckNoPjNologin")
				.create();

		str = service.checkNoPjNologin(parameterList);

		if (infromation.equals("0")) {
			logger.error("调用CheckNoPjNologin未评价不能登录系统功能验证：" +"\n" + "parameterList=" + parameterList+"\n");
			logger.error("调用CheckNoPjNologin未评价不能登录系统功能验证返回：" + str);
		}

		return str;
	}

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
	@Override
	public String GetStudentList(String nj, String xy, String zy,
			String bj, String xm,String count, String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			nj  		= CodeUtil.decode(nj, apptoken);
			xy  		= CodeUtil.decode(xy, apptoken);
			zy  		= CodeUtil.decode(zy, apptoken);
			bj  		= CodeUtil.decode(bj, apptoken);
			xm  		= CodeUtil.decode(xm, apptoken);
			count  		= CodeUtil.decode(count, apptoken);
			strKey  	= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		String str = null;
        String parameterList=nj + AND +xy + AND +zy + AND +bj+ AND +xm;
		ICourseTask service = (ICourseTask) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_COURSETASK, ICourseTask.class,
				"GetStudentList").create();

		str = service.getStudentList(parameterList, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用GetStudentList学生姓名模糊查询：" +"\n"+"nj="+nj+"\n"+"xy="+xy+"\n"+"zy="+zy+"\n"+"bj="+bj+"\n"
			+"xm="+xm+"\n"+"strKey="+strKey+"\n"+"parameterList="+parameterList+"\n"		
			);
			logger.error("调用GetStudentList学生姓名模糊查询返回：" + str);
		}
		return str;
	}

	/**
	 * <p>Description: 教务通知公告附件下载</p>
	 * @param fileName 文件名
	 * @param length   长度
	 * @param strKey   秘钥
	 * @return
	 *
	 * @since 2015-5-15 上午09:04:26
	 * @author yangz
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public String getFileModel(String id,String fileName, String length, String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			id  			= CodeUtil.decode(id, apptoken);
			fileName  		= CodeUtil.decode(fileName, apptoken);
			length  		= CodeUtil.decode(length, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		String zfxml = "";
		String str ="";
		ids=ids+1;
	    id =String.valueOf(ids);
	    id ="0"+id;
	    if (infromation.equals("0")) {
			logger.error("调用getFileModel教务通知公告附件下载：" +"\n"	+"id="+id+"\n"	
			);
			logger.error("调用getFileModel教务通知公告附件下载返回：" + str);
		}
		//生成文件路径
		File f = new File(this.getClass().getResource("/").getPath());
		String url = f.getPath().split("WEB-INF")[0] + "upload//";
		
		 File file=new File(url+id); 
		 if(!file.exists()) {
			 //获取教务二进制流数据
		        org.apache.axis.client.Service service = new org.apache.axis.client.Service();
				Call call;
				try {
					call = (Call) service.createCall();

					call.setTargetEndpointAddress(new java.net.URL(WebServiceConf.SERVICE_JWDOWNLOADSERVICES));// 
					call.setOperationName(new QName("http://zfsoft/zfjw/fileProcess",
							"Downloads"));
					call.addParameter(new QName("http://zfsoft/zfjw/fileProcess",
							"fileDir"), XMLType.XSD_STRING, ParameterMode.IN);
					call.addParameter(new QName("http://zfsoft/zfjw/fileProcess",
							"length"), XMLType.XSD_STRING, ParameterMode.IN);
					call.addParameter(new QName("http://zfsoft/zfjw/fileProcess",
							"strKey"), XMLType.XSD_STRING, ParameterMode.IN);
					call.setReturnClass(String.class);
					call.setReturnType(XMLType.XSD_STRING);
					call.setUseSOAPAction(true);
					call.setSOAPActionURI("http://zfsoft/zfjw/fileProcess/Downloads");
					str = (String) call.invoke(new Object[] { fileName,
							length, strKey });
					System.out.println(str);
					
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(!str.equals("")& !str.equals(null)){
					//将接受的base64字符串转换成byte[]类型
					try {
						byte[] a=base64Tobyte.base64Tobyte(str);
						FileOutputStream fos = new FileOutputStream(url+id);  
				        fos.write(a);  
				        fos.close();  
				        
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//生成自定义XML
					DownLoadBean bean = new DownLoadBean();
					bean.setAdjunctId(id);
					bean.setDownLoadURL(WebServiceConf.DOWNLOADURL + id);
					bean.setFileName(fileName);
					String[] output = new String[] { "fileName", "adjunctId",
							"downLoadURL" };// 需要输出的字段
					
					List indexlist = new ArrayList();
					indexlist.add(bean);
					zfxml = XmltoString.xmlToStringNew(output, SelectItems
							.getReflectObjPropertyValue(indexlist,
									DownLoadBean.class, output), "DOWNLOAD");
					
					System.out.println(zfxml);
				}
		 }
		 else{
				//生成自定义XML
				DownLoadBean bean = new DownLoadBean();
				bean.setAdjunctId(id);
				bean.setDownLoadURL(WebServiceConf.DOWNLOADURL + id);
				bean.setFileName(fileName);
				String[] output = new String[] { "fileName", "adjunctId",
						"downLoadURL" };// 需要输出的字段
				
				List indexlist = new ArrayList();
				indexlist.add(bean);
				zfxml = XmltoString.xmlToStringNew(output, SelectItems
						.getReflectObjPropertyValue(indexlist,
								DownLoadBean.class, output), "DOWNLOAD");
				
				  if (infromation.equals("0")) {
						logger.error("调用getFileModel教务通知公告附件下载返回：" + zfxml);
					}
		 }
		return zfxml;
	}
	
	
	public static void main(String[] args) {
		System.out.println("Start invoking....");
		String str = null;
		String role = "XS";
			// 走教务系统接口
		String parameterList = "31609007" + "&" + "2016-2017" + "&" + "2";
		// 调用WebService
		ICourseSchedule service = (ICourseSchedule) WebServiceUtil
				.createFactoryJw(
						"http://10.71.32.43:9090/Service.svc/CourseScheduleContract",
						ICourseSchedule.class, "CourseScheduleSearch")
				.create();
		System.out.println(parameterList);
		String JWSign = MiddleWareUtil.getJWSign(parameterList);
		System.out.println(JWSign);
		
		str = service.courseScheduleSearch(parameterList, role, "0",
				JWSign);

		System.out.println("result:"+str);
//		   try
//		   {
//		     String endPoint="http://finance2.shmtu.edu.cn:8989/financequeryservice.asmx";
//		     org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		     /*Call call = (Call)service.createCall();
		     call.setTargetEndpointAddress(new java.net.URL(endPoint));
		     call.setOperation("getVersionTime");
		     call.setUseSOAPAction(true);
		     call.setSOAPActionURI("");   
		     QName qName = new QName("http://tempuri.org/","Login");
		     call.setOperationName(qName);      
		     call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);  
		     String str=(String)call.invoke( new Object[]{"000003","1","2012-01-01 12:00:00","abcdefg","kjdkfdjfjdfkjd"}); 
		     System.out.println(str);     */ 
		     
		     

				/*Call call;
				call = (Call) service.createCall();
				call.setTargetEndpointAddress(new java.net.URL(endPoint));
				// call.setOperationName("getSzdacx" );//设置对应方法 (原
				// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
				// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
				QName opAddEntry = new QName(
						"http://tempuri.org/",
						"Login");
				call.setOperationName(opAddEntry);
				String tmp = (String) call.invoke(new Object[] {"000003","1","2012-01-01 12:00:00","abcdefg","kjdkfdjfjdfkjd"});
				System.out.println(tmp);     */
				
				
				
				
				
				
				/*String endpoint = "http://finance2.shmtu.edu.cn:8989/financequeryservice.asmx";    
	            // 创建一个服务(service)调用(call)     
	            Call call = (Call) service.createCall();// 通过service创建call对象     
	            // 设置service所在URL    
	            call.setTargetEndpointAddress(new java.net.URL(endpoint));   
	      
	            call.setOperationName(new QName("http://tempuri.org/","GetSalaryYears")); 
	            //Add 是net 那边的方法 "http://tempuri.org/" 这个也要注意Namespace 的地址,不带也会报错
	           call.addParameter(new QName("http://tempuri.org/","GetSalaryYears"), 
	                   org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
	           // 这就是我搞了一天的原因所在,"test" 这个就是传参数的变量,也就是NET方面的参数,一定不要带错了
	            // 我当初不知道 ,以为这个参数是自己随便写的,结果总是报NULL,真是气死人了
	            
	            call.setUseSOAPAction(true); 
	            call.setReturnType(org.apache.axis.encoding.XMLType.SOAP_STRING); //返回参数的类型
	            call.setSOAPActionURI("http://tempuri.org/GetSalaryYears"); //这个也要注意 就是要加上要调用的方法Add,不然也会报错
	            
	            // Object 数组封装了参数，参数为"This is Test!",调用processService(String arg)     
	            String ret = (String) call.invoke(new Object[] {"000003","1","2012-01-01 12:00:00","abcdefg","kjdkfdjfjdfkjd"});    
	            System.out.println("--------"+ret);  */
	            
//		      Call call = (Call) service.createCall();// 通过service创建call对象     
//	            call = (Call) service.createCall();
//
//				call.setTargetEndpointAddress(new java.net.URL("http://finance2.shmtu.edu.cn:8989/financequeryservice.asmx"));// 
//				call.setOperationName(new QName("http://tempuri.org/",
//						"SalaryDetailQuery"));
//				call.addParameter(new QName("http://tempuri.org/",
//						"UCode"), XMLType.XSD_STRING, ParameterMode.IN);
//				call.addParameter(new QName("http://tempuri.org/",
//						"SendTime"), XMLType.XSD_STRING, ParameterMode.IN);
//				call.addParameter(new QName("http://tempuri.org/",
//						"StrMD5"), XMLType.XSD_STRING, ParameterMode.IN);
//				call.setReturnClass(String.class);
//				call.setReturnType(XMLType.XSD_STRING);
//				call.setUseSOAPAction(true);
//				call.setSOAPActionURI("http://tempuri.org/SalaryDetailQuery");
//				String str = (String) call.invoke(new Object[] {"125003","2017-03-01 11:37:00","1d6f239f701b54fa76df6f3e082a6bb4"});
//				System.out.println(str);
//	            
//				
//		   }catch(Exception e)
//		   {
//		     e.printStackTrace();
//		   }   
	}

	/**
	 * <p>Description: 教学大纲</p>
	 * @param kcdm     课程代码
	 * @param strKey   秘钥
	 * @return
	 *
	 * @since 2015-5-21 下午05:14:08
	 * @author yangz
	 */
	@Override
	public String CourseJxdgTextInfo(String kcdm, String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			kcdm  		= CodeUtil.decode(kcdm, apptoken);
			strKey  	= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		String str = null;
		ICourseTask service = (ICourseTask) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_COURSETASK, ICourseTask.class,
				"CourseJxdgTextInfo").create();

		str = service.courseJxdgTextInfo(kcdm, strKey);
		if (infromation.equals("0")) {
			logger.error("调用CourseJxdgTextInfo获取教学大纲："+"\n" + "kcdm" + kcdm +"\n"+ "strKey=" + strKey);
			logger.error("调用CourseJxdgTextInfo获取教学大纲返回：" + str);
		}
		return str;
	}

	/**
	 * <p>Description: 获取通知公告总记录数</p>
	 * @param userName
	 * @param start
	 * @param size
	 * @param role
	 * @param count
	 * @param strKey
	 * @return
	 *
	 * @since 2015-8-7 下午05:16:10
	 * @author yangz
	 */
	@Override
	public String GetPostTotalRecords(String userName, String start,
			String size, String role, String count, String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(userName, apptoken))
			return "app_token error";
		try {
			userName  		= CodeUtil.decode(userName, apptoken);
			start  			= CodeUtil.decode(start, apptoken);
			size  			= CodeUtil.decode(size, apptoken);
			role  			= CodeUtil.decode(role, apptoken);
			count  			= CodeUtil.decode(count, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String str = null;
		String parameterList = userName + AND + start + AND + size;
		INoticePost service = (INoticePost) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_NOTICE, INoticePost.class,
				"GetPostTotalRecords").create();

		str = service.getPostTotalRecords(parameterList, role, count, strKey);

		if (infromation.equals("0")) {
			logger.error("调用GetPostTotalRecords通知公告获取记录数：" + "parameterList="+parameterList+"userName=" + userName+"\n"
					+ "role=" + role +"\n"+ "count=" + count +"\n"+ "strKey=" + strKey);
			logger.error("调用GetPostTotalRecords通知公告获取记录数返回：" + str);
		}

		return str;
	}

	/* (non-Javadoc)
	 * @see com.jw.service.IEducationalPortXMLService#getNewEducationalLogin(java.lang.String, java.lang.String)
	 */
	@Override
	public String getNewEducationalLogin(String yhm, String mm) {
		com.jw.httpUtils.HttpRequest httpRequest = new com.jw.httpUtils.HttpRequest();
		String param = "yhm="+yhm+"&mm="+mm;
		String result = httpRequest.sendGet(Config.getString("NewEducationalLoginURL"), param);
		JSONObject jsonObject = JSONObject.fromObject(result);
		String errorCode = jsonObject.getString("errorCode");
		if(errorCode.equals("-1")){
			if(infromation.equals("0")){
				logger.error("获取登陆接口：");
			}
			
			JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
	        bean.setServiceClass(IWSSerService.class);  
	        bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
	        IWSSerService helloWorldService = (IWSSerService)bean.create();  
	        result = helloWorldService.login(yhm, mm, "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS",null);
			
			
			if(infromation.equals("0")){
				logger.error("登陆接口返回为："+result);
			}
		}else{
			return "<ResultInfo><code>404</code><message>帐号或密码不正确！</message></ResultInfo>";
		}
		return result;
	}

}
