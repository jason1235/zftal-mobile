package com.zfsoft.common.factory;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zfsoft.util.base.StringUtil;

/**
 * 服务工厂【用于本地与EJB服务上使用】
 * 
 * @author Administrator
 * 
 */
public class ServiceFactory {
	private ServiceFactory() {

	}

	public static ApplicationContext springContext = null;

	
	synchronized private static void setApplicationContext() {

//		if (springContext == null) {
//			springContext = new ClassPathXmlApplicationContext(new String[] {
//					"com/zfsoft/config/applicationContext*.xml",
//					"com/zfsoft/config/*/applicationContext*.xml",
//					"com/zfsoft/config/comm/*/applicationContext*.xml",
//					"com/zfsoft/config/comp/*/applicationContext*.xml" });
//		}

		
		if (springContext == null) {
			springContext = new ClassPathXmlApplicationContext(new String[] {
					"classpath*:/conf/spring/common.xml",
					"classpath*:/conf/spring/config*.xml" });
		}

	}
	
	/**
	 * 常用于知道配置的服务名取服务
	 * 
	 * @param svrCode
	 * @return
	 */
	public static Object getService(String svrCode) {
		if (StringUtil.isEmpty(svrCode)) {
			return null;
		}

		if (springContext == null) {
			setApplicationContext();
		}
		//
		if (springContext != null) {

			return springContext.getBean(svrCode);
		}
		return null;

	}


	/**
	 * 用于按规范命名的服务：通过类的类型取服务
	 * 
	 * @param svrCode
	 * @return
	 */
	public static Object getService(Class cls) {
		if (cls == null) {
			return null;
		}
		String svrCode = cls.getSimpleName();
		if (svrCode.length() <= 1 || StringUtil.isEmpty(svrCode)) {
			return null;
		}

		byte bytes[] = svrCode.getBytes();
		bytes[1] = new String(bytes, 1, 1).toLowerCase().getBytes()[0];
		return getService(new String(bytes, 1, bytes.length - 1));

	}

	/**
	 * 取数据源
	 * 
	 * @return
	 */
	public static DataSource getDataSource(String svrCode) {
		return (DataSource) getService(svrCode);

	}

}
