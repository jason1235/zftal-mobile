package com.jw.test;


import com.common.WebServiceConf;
import com.jw.org.tempuri.ICourseSchedule;
import com.jw.org.tempuri.ICourseTask;
import com.jw.org.tempuri.IExamArrange;
import com.jw.org.tempuri.ITeacherInfo;
import com.util.MiddleWareUtil;
import com.util.WebServiceUtil;


public class EducationalPortXMLText{
	public static void main(String[] args) {
		//md5.getMD5ofStr("0901040341&111111"+WebServiceConf.KEY_JW)
		/*String str = "";
		byte[] str1;*/
		// 调用WebService
		
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IScoreSearch.class);
//		factory.setAddress(WebServiceConf.SERVICE_JWSERVICE_SCORE);//接口地址
//		factory.setServiceName(new QName(WebServiceConf.WEBSERVICE_NAMESPACE_JW, "ScoreSearch"));
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//		IScoreSearch service = (IScoreSearch) factory.create();
//		str = service.scoreSearch("0901040341", "0", md5.getMD5ofStr("0901040341"+WebServiceConf.KEY_JW));
		
		
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IConfigurationInfo.class);
//		factory.setAddress(WebServiceConf.SERVICE_JWSERVICE_CONFIG);//接口地址
//		factory.setServiceName(new QName(WebServiceConf.WEBSERVICE_NAMESPACE_JW, "ConfigurationInfo"));
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//		IConfigurationInfo service = (IConfigurationInfo) factory.create();
//		str = service.configurationInfo();
		
		
//		String str = "";
//		// 调用WebService
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(ILogin.class);
//		factory.setAddress(WebServiceConf.SERVICE_JWSERVICE_LOGIN);//接口地址
//		factory.setServiceName(new QName(WebServiceConf.WEBSERVICE_NAMESPACE_JW, "Login"));
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//		ILogin service = (ILogin) factory.create();
//
//		str = service.login("0901040341&111111", "xs",md5.getMD5ofStr("0901040341&111111"+WebServiceConf.KEY_JW));
		
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(ICourseTask.class);
//		factory.setAddress(WebServiceConf.SERVICE_JWSERVICE_COURSETASK);//课程任务接口地址
//		factory.setServiceName(new QName(WebServiceConf.WEBSERVICE_NAMESPACE_JW, "GetGradeList"));
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//		ICourseTask service = (ICourseTask) factory.create();
//		str = service.getGradeList("0");
		
		
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IStudentInfo.class);
//		factory.setAddress(WebServiceConf.SERVICE_JWSERVICE_STUDENT);//学生信息接口地址
//		factory.setServiceName(new QName(WebServiceConf.WEBSERVICE_NAMESPACE_JW, "SearchInfoSearch"));
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//		IStudentInfo service = (IStudentInfo) factory.create();
//		str = service.studentInfoSearch("0901040341", "0",md5.getMD5ofStr("0901040341"+WebServiceConf.KEY_JW));
		
		
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(ICourseTask.class);
//		factory.setAddress(WebServiceConf.SERVICE_JWSERVICE_COURSETASK);//课程任务接口地址
//		factory.setServiceName(new QName(WebServiceConf.WEBSERVICE_NAMESPACE_JW, "GetMajorList"));
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//		ICourseTask service = (ICourseTask) factory.create();
//		str = service.getMajorList("07", "0",
//				WebServiceUtil.getMD5ofStr("07"));
		
		
		/*JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(ICourseTask.class);*/
		
//		factory.setAddress(WebServiceConf.SERVICE_JWSERVICE_COURSETASK);//课程任务接口地址
	   /* factory.setAddress("http://49.122.0.17:8080/Service.svc/CourseTaskContract");//课程任务接口地址
		factory.setServiceName(new QName(WebServiceConf.WEBSERVICE_NAMESPACE_JW, "GetClassList"));
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		ICourseTask service = (ICourseTask) factory.create();
		str = service.getClassList("09"+"&"+"1105"+"&"+"2012", "0", "28716B0B0E188EDAE4BA5A1E8065A086");*/
		
		/*String parameterList = "js01" + "&" + "7F94150B1F31F218BE0E4721AC9751C3" + "&" + "N";// + AND																		
		// 调用WebService
		ICampus service = (ICampus) WebServiceUtil.createFactoryJw(
				
				WebServiceConf.SERVICE_JWSERVICE_CAMPUSSEARCH, ICampus.class, "CampusInfo")
				.create();
		str= service.campusInfo();
		System.out.println("Server said: "+ str);
		
		IStudentInfo service1 = (IStudentInfo) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_STUDENT, IStudentInfo.class,
				"StudentPhotosSearch").create();
		
		str1=service1.studentPhotosSearch("20104090958", "0094A904E1CC0B1AB2BC12D73C34AF79");
		System.out.println("Server said: "+String.valueOf(str1) );*/
//		ICourseSchedule service = (ICourseSchedule) WebServiceUtil.createFactory(
//				WebServiceConf.SERVICE_JWSERVICE_TIMETABLE,
//				ICourseSchedule.class, "CourseScheduleSearch").create();
//		// 用户名&学年&学期
//		str = service.courseScheduleSearch("0901040341&2009-2010&1", "xs",
//				"0", WebServiceUtil.getMD5ofStr("0901040341&2009-2010&1"));
	/*	final String AND = "&";
//		String parameterList = "07" + AND + "0801" + AND + "2012";// + AND		
		String parameterList="07"+"&"+"0801"+"&"+"2012";
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(ICourseTask.class);
		factory.setAddress(WebServiceConf.SERVICE_JWSERVICE_COURSETASK);//学年学期列表
		factory.setServiceName(new QName(WebServiceConf.WEBSERVICE_NAMESPACE_JW, "GetClassList"));
	//	factory.setServiceName(new QName(WebServiceConf.WEBSERVICE_NAMESPACE_JW, "GetMajorList"));
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		ICourseTask service = (ICourseTask) factory.create();
	//	str=service.getMajorList("07","0",JwMD5Util.md5Ecode("07"));
		str=service.getClassList(parameterList,"0",JwMD5Util.md5Ecode(parameterList));
		System.out.println("Server said: "+ str);*/
		
/*		String str = null;
		String parameterList="大学英语A(Ⅱ)"+ "&" + "130906" + "&" + "教科312" + "&" +"2014-04-25" + "&" +"2014-08-25";
		System.out.println("parameterList said: "+ parameterList);
		String count="0";
		String strKey =MiddleWareUtil.getJWSign(parameterList);
		System.out.println("strKey said: "+ strKey);
	
		IExamArrange service = (IExamArrange) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_EXAM, IExamArrange.class,
				"ExamArrange1").create();
		str = service.examArrange1(parameterList, count, strKey);
		System.out.println("Server said: "+ str);*/
		
/*		String str = null;
		String count="0";
		String parameterList="14"+"&"+"旅游资源开发与规划";
		String strKey =MiddleWareUtil.getJWSign(parameterList);
		System.out.println("strKey said: "+ strKey);
		ICourseTask service = (ICourseTask) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_COURSETASK, ICourseTask.class,
				"GetCourseList1").create();

		str = service.getCourseList1(parameterList, count, strKey);
		System.out.println("Server said: "+ str);*/
	/*	String str = null;
		String count="0";
		String parameterList="2005-2006"+"&"+"2"+"&"+"30734023"+"&"+"2006-02-21"+"&"+"2006-06-21"+"&"+"信息学院"+"&"+"陈蒂";
		String strKey =MiddleWareUtil.getJWSign(parameterList);
		System.out.println("strKey said: "+ strKey);
		ICourseSchedule service = (ICourseSchedule) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_TIMETABLE, ICourseSchedule.class,
				"TJAllCourseScheduleSearch1").create();

		str = service.tjAllCourseScheduleSearch1(parameterList, count, strKey);
		System.out.println("strKey said: "+ str);*/
		
		//
	/*	String str = null;
		String count="0";
		String sid="21090176";
		String strKey =MiddleWareUtil.getJWSign(sid);
		System.out.println("strKey said: "+ strKey);
		ITeacherInfo service = (ITeacherInfo) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_TEACHERINFOCONTRACT, ITeacherInfo.class,
				"TeacherInfoSearch").create();

		str = service.teacherInfoSearch(sid, count, strKey);
		System.out.println("strKey said: "+ str);*/
		
	/*	byte[] str = null;
		String sid="21090176";
		String strKey =MiddleWareUtil.getJWSign(sid);
		ITeacherInfo service = (ITeacherInfo) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_TEACHERINFOCONTRACT, ITeacherInfo.class,
				"TeacherPhotosSearch").create();

		str = service.teacherPhotosSearch(sid, strKey);

		System.out.println("strKey said: "+ str);*/
		
	/*	byte[] str = null;
		ICourseTask service = (ICourseTask) WebServiceUtil.createFactoryJw(
				WebServiceConf.SERVICE_JWSERVICE_COURSETASK, ICourseTask.class,
				"CourseJxdgInfo").create();

		String kcdm="649020058";
		String strKey =MiddleWareUtil.getJWSign(kcdm);
		str = service.courseJxdgInfo(kcdm, strKey);
		System.out.println("strKey said: "+ str);*/
		
	}

	
}
