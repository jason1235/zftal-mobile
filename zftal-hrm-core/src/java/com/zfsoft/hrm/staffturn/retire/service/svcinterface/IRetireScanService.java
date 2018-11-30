package com.zfsoft.hrm.staffturn.retire.service.svcinterface;

import com.zfsoft.hrm.staffturn.retire.entities.RetireScheduleTypeEnum;

/** 
 * 退休检测SERVICE
 * @author jinjj
 * @date 2012-7-31 上午03:18:51 
 *  
 */
public interface IRetireScanService {

	/**
	 * 扫描达到退休标准的人员，并对负责人员发出提醒消息
	 * @param receiver
	 * @param notifySelf 是否通知本人
	 */
	public void doScan(String receiver,int notifySelf,RetireScheduleTypeEnum period);
	
	
	/**
	 * 通过自己筛选时间进行扫描退休标准员工
	 * @param receiver
	 * @param notifySelf 是否通知本人
	 */
	public void doScanByDate(String receiver,int notifySelf,RetireScheduleTypeEnum period,String date);
}
