package com.zfsoft.hrm.tools.web.service.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;

import com.zfsoft.common.factory.ServiceFactory;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.config.SelectPersonConfig;
import com.zfsoft.hrm.config.SelectPersonConfigFactory;
import com.zfsoft.hrm.tools.web.query.SelectPersonQuery;
import com.zfsoft.hrm.tools.web.service.svcinterface.ISelectPersonService;

/**
 * 
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-11
 * @version V1.0.0
 */
public class SelectPersonServiceTest extends TestCase {

	private ISelectPersonService service;
	
	/* 
	 * (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		service = (ISelectPersonService) ServiceFactory.getService( "toolsSelectPersonService" );
	}
	
	@Test
	public void test() {
		//代码库启动
		CodeUtil.initialize();
		
		Map<String, Object> values = new HashMap<String, Object>();
		values.put( "staffid", "00" );
		
		SelectPersonQuery selectPerson = new SelectPersonQuery();
		SelectPersonConfig config = SelectPersonConfigFactory.getSelectSinglePerson("teacher");
		
		
		selectPerson.setConfig( config );
		selectPerson.setValues( values );
		
		List<DynaBean> beans = service.getPerson( selectPerson );
		
		for ( DynaBean bean : beans ) {
			System.out.println( bean.getValue( "gh") + "\t" + bean.getValue( "xm") );
		}
	}

}
