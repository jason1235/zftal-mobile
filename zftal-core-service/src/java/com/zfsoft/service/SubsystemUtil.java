package com.zfsoft.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.common.log.Subsystem;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.service.svcinterface.SubsystemService;
/**
 * 子系统工具
 * 
 * @author gonghui
 * 2014-5-16
 */
public class SubsystemUtil {
	
	private static final SubsystemService subsystemService= (SubsystemService) SpringHolder.getBean("subsystemService");

	private static List<Subsystem> subsystems;
	
	private static final long interval=5*60*1000;
	
	private static long cacheTime=0;
	
	/**
	 * 得到所有的子系统
	 * @return
	 */
	public static List<Subsystem> getAllSubsystems(){
		if(cacheTime==0){
			cacheAll();
		}
		if(System.currentTimeMillis() - cacheTime>=interval){
			cacheAll();
		}
		return subsystems;
	}
	
	/**
	 * 根据子系统code得到子系统
	 * @return
	 */
	public static Subsystem getSubsystem(String sysCode){
		if(StringUtils.isEmpty(sysCode)){
			return null;
		}
		List<Subsystem> subsystems=getAllSubsystems();
		for (Subsystem subsystem : subsystems) {
			if(sysCode.equals(subsystem.getSysCode())){
				return subsystem;
			}
		}
		return null;
	}
	
	private static void cacheAll(){
		subsystems=subsystemService.queryAllEnabledAndDefaultOrder();
		cacheTime=new Date().getTime();
	}
}
