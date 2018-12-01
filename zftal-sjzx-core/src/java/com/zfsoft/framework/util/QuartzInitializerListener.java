package com.zfsoft.framework.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

import com.zfsoft.hrm.schedule.ScheduleControlService;



/**
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company: zfsoft.com
 * </p>
 * 
 * @since 2012-12-11 ÏÂÎç3:15:23
 * @author liuchaoyong
 * @version 1.0
 */
public class QuartzInitializerListener implements ServletContextListener {

	public static final String QUARTZ_FACTORY_KEY = "org.quartz.impl.StdSchedulerFactory.KEY";

	private boolean performShutdown = true;

	private Scheduler scheduler = null;

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		
		StdSchedulerFactory factory;
		try {

			String shutdownPref = servletContext
					.getInitParameter("shutdown-on-unload");

			if (shutdownPref != null) {
				performShutdown = Boolean.valueOf(shutdownPref).booleanValue();
			}
			//factory = new StdSchedulerFactory();
			scheduler = (Scheduler) SpringUtil.getBean("localQuartzScheduler");
			//scheduler = factory.getScheduler();
			String startOnLoad = servletContext
					.getInitParameter("start-scheduler-on-load");

			int startDelay = 0;
			String startDelayS = servletContext
					.getInitParameter("start-delay-seconds");
			try {
				if (startDelayS != null && startDelayS.trim().length() > 0)
					startDelay = Integer.parseInt(startDelayS);
			} catch (Exception e) {
				System.out.println("Cannot parse value of 'start-delay-seconds' to an integer: "
								+ startDelayS + ", defaulting to 5 seconds.");
				startDelay = 5;
			}

			if (startOnLoad == null
					|| (Boolean.valueOf(startOnLoad).booleanValue())) {
				if (startDelay <= 0) {
					scheduler.start();
					System.out.println("Scheduler has been started");
				} else {
					scheduler.startDelayed(startDelay);
					System.out.println("Scheduler will start in " + startDelay
							+ " seconds.");
				}
			} else {
				System.out
						.println("Scheduler has not been started. Use scheduler.start()");
			}

			String factoryKey = servletContext
					.getInitParameter("servlet-context-factory-key");
			if (factoryKey == null) {
				factoryKey = QUARTZ_FACTORY_KEY;
			}

			System.out
					.println("Storing the Quartz Scheduler Factory in the servlet context at key: "
							+ factoryKey);
			//servletContext.setAttribute(factoryKey, factory);	
			servletContext.setAttribute(factoryKey, null);	
		} catch (Exception e) {
			System.out.println("Quartz Scheduler failed to initialize: "
					+ e.toString());
			e.printStackTrace();
		}
		init();//³õÊ¼»¯
		
		
	}

	public void contextDestroyed(ServletContextEvent sce) {

		if (!performShutdown) {
			return;
		}

		try {
//			if (scheduler != null) {
//				scheduler.shutdown();
//			}
		} catch (Exception e) {
			System.out.println("Quartz Scheduler failed to shutdown cleanly: "
					+ e.toString());
			e.printStackTrace();
		}

		System.out.println("Quartz Scheduler successful shutdown.");

	}
	private void init() {
	
		JobStart js = new JobStart();
		js.setScheduler(scheduler);
		(new Thread(js)).start();
		
		WebServiceJobStart wjs = new WebServiceJobStart();
		wjs.setScheduler(scheduler);
		(new Thread(wjs)).start();
		
	 System.out.println("init()==================================start");	
	}
}
