package com.zfsoft.hrm.dybill.service;
/**
 * TODO
 * @className: ISpBillDataPushRunService 
 * @author Patrick Shen shenluwei@126.com
 * @date 2013-8-14 下午02:41:52
 */
public interface ISpBillDataPushRunService {
	/**
	 * TODO
	 * @methodName pushData 
	 * @param eventConfigId
	 * @param userId
	 * @param instanceId
	 * @return String
	 */
	public String pushData(String eventConfigId,String userId, String instanceId);
	/**
	 * TODO
	 * @methodName pushData 
	 * @param eventConfigId
	 * @param userId
	 * @param instanceId
	 * @param newValue
	 * @return String
	 */
	public String pushData(String eventConfigId,String userId, String instanceId,Boolean newValue);
}
