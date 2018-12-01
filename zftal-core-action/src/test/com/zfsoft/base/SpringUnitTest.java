package com.zfsoft.base;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SpringUnitTest {

	private ApplicationContext applicaitonContent;

	public SpringUnitTest(String[] configContext) {
		applicaitonContent = new FileSystemXmlApplicationContext(configContext);
	}

	public Object getTestBean(String beanId) {
		return applicaitonContent.getBean(beanId);
	}

}
