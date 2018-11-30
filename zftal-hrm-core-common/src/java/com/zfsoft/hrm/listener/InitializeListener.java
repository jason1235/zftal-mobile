package com.zfsoft.hrm.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/** 
 * @ClassName: InitializeListener 
 * @Description: 初始化监听器，用于应用启动时加载初始化信息
 * @author jinjj
 * @date 2012-5-23 下午01:14:30 
 *  
 */
public class InitializeListener implements ServletContextListener {

	/**
	 *@see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	/**
	 *@see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
//		CodeUtil.initialize();//代码库加载
		//IInfoClassDataPatchService patch = (IInfoClassDataPatchService)SpringHolder.getBean("baseInfoClassDataPatchService");
		//patch.doPatch();
	}

}
