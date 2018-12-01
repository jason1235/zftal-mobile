package com.zfsoft.framework.util;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
/**
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2012-12-11 下午4:54:42
 * @author liuchaoyong
 * @version 1.0
 */
public  class SimpleJob implements StatefulJob{

	public void execute(JobExecutionContext jec) throws JobExecutionException {
		SimpleJobDetail jd=	(SimpleJobDetail)jec.getJobDetail();
		try{
		 jd.doaction();
		 //jd.dolog();
		 jd.updateLastRunTime(jd.getDsqpzb());//更新上次运行时间
		 }catch(Exception e){
			e.printStackTrace();
		}
	}


	
}
