package com.zfsoft.hrm.config;

import org.junit.Test;

import base.BaseTxTestCase;

/**
 * 
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-9
 * @version V1.0.0
 */
public class SelectPersonConfigFactoryTest extends BaseTxTestCase {
	
	@Test
	public void test() {
		
		SelectPersonConfig config = SelectPersonConfigFactory.getSelectMultiplePerson( "teacher" );
		
		printCondition(config);
		
		System.out.println("=====================================");
		config = SelectPersonConfigFactory.getSelectSinglePerson("teacher");
		
		printCondition(config);
	}

	private void printCondition(SelectPersonConfig config) {
		for ( SearchCondition condition : config.getConditions() ) {
			System.out.println( condition.getTitle() + "\t" + condition.getColumn() );
		}
	}
	
}
