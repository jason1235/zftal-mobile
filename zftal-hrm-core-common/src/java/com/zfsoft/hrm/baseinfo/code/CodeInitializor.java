package com.zfsoft.hrm.baseinfo.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;


public class CodeInitializor implements InitializingBean{
	
	private static Logger log = LoggerFactory.getLogger(CodeInitializor.class);
	
	@Override
	public void afterPropertiesSet() throws Exception {
		CodeUtil.initialize();
		log.debug("基础代码库加载完成");
	}
}
