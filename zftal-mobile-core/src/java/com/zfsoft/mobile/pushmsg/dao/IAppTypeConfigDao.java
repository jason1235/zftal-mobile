package com.zfsoft.mobile.pushmsg.dao;

import java.util.List;

import com.zfsoft.mobile.pushmsg.entity.AppTypeConfig;

/**
 * 
 * @author ChenMinming
 * @date 2015-5-9
 * @version V1.0.0
 */
public interface IAppTypeConfigDao {
	AppTypeConfig findConfig(String appType);
	List<AppTypeConfig> getConfigList();
	AppTypeConfig findMIUIConfig(String appType);
	AppTypeConfig findEMUIConfig(String appType);
}
