package com.zfsoft.hrm.schedule;

import java.util.Date;

public class TestJobDetailService {
	public static int i=0;
	
	public void execute(){
		i++;
		System.out.println("aaaaaaaaaaaaaaa"+new Date(System.currentTimeMillis())+"     " +i);
	}
	
}
