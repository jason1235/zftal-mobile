package com.zfsoft.hrm.dybill.service;

import java.util.List;

import com.zfsoft.hrm.dybill.entity.SpBillInstance;
import com.zfsoft.hrm.dybill.entity.SpBillInstanceLog;

/**
 * 
 * @author ChenMinming
 * @date 2013-8-27
 * @version V1.0.0
 */
public interface ISpBillInstanceLogService {
	
	public void insertInstanceLog(SpBillInstance spBillInstance,String staffId);
	
	public void deleteByInstanceId(String instanceId);
	
	public SpBillInstanceLog findById(String id);
	
	public List<SpBillInstanceLog> getLogList(SpBillInstanceLog log);
}
