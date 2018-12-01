package com.zfsoft.hrm.staffturn.dead.entity;

import org.junit.Test;

import com.zfsoft.hrm.staffturn.dead.entities.DeadInfo;

public class DeadInfoTest {
	@Test
	public void getG(){
		DeadInfo deadInfo=new DeadInfo();
		deadInfo.setUserId("2");
		System.out.println(deadInfo.getSqlNamesAll());
		System.out.println();
		System.out.println(deadInfo.getSqlNamesNoKeyAndIsNull());
		System.out.println();
		System.out.println(deadInfo.getSqlNamesHasKeyAndNotNull());
		System.out.println();
		System.out.println(deadInfo.getSqlNamesNoKeyAndNotNull());
	}
}
