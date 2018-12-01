package com.zfsoft.base;

import javax.sql.DataSource;

public class BaseTest {
	private static String[] configContext = {
		"src\\conf\\spring\\config-globalweb-action.xml",
		"src\\conf\\spring\\config-globalweb-dao.xml" ,
		"src\\conf\\spring\\config-globalweb-service.xml" };
	
	public static Object getTestBean(String beanId) {
		return new SpringUnitTest(configContext).getTestBean(beanId);
	}
	
	protected DataSource dataSource = (DataSource)getTestBean("dataSource");
	
}
