package com.zfsoft.mobile.support;

import java.util.List;

import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.mobile.pushmsg.dao.IAppTypeConfigDao;
import com.zfsoft.mobile.pushmsg.entity.AppTypeConfig;

/**
 * 
 * @author ChenMinming
 * @date 2015-5-9
 * @version V1.0.0
 */
public class JPushAppTypeConfig {
	public static AppTypeConfig findConfig(String appType){
		IAppTypeConfigDao dao = SpringHolder.getBean("appTypeConfigDao",IAppTypeConfigDao.class);
		return dao.findConfig(appType);
	}
	public static List<AppTypeConfig> getConfigList(){
		IAppTypeConfigDao dao = SpringHolder.getBean("appTypeConfigDao",IAppTypeConfigDao.class);
		return dao.getConfigList();
	}
	
	public static AppTypeConfig findMIUIConfig(String appType){
		IAppTypeConfigDao dao = SpringHolder.getBean("appTypeConfigDao",IAppTypeConfigDao.class);
		return dao.findMIUIConfig(appType);
	}
	
	public static AppTypeConfig findEMUIConfig(String appType){
		IAppTypeConfigDao dao = SpringHolder.getBean("appTypeConfigDao",IAppTypeConfigDao.class);
		return dao.findEMUIConfig(appType);
	}
}
