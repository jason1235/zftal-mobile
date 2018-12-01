package com.zfsoft.hrm.dybill.service;

/**
 * 表单入库
 * @author Patrick Shen
 */
public interface ISpBillStorageService {
	/**
	 * 实体入库
	 * @param entity
	 */
	public void addValueEntityToStorage(String billConfigId,String billInstanceId,Long billClassId,Long entityId);
	/**
	 * 实例入库
	 * @param entity
	 */
	public void addInstanceToStorage(String billConfigId,String billInstanceId);
	/**
	 * 实例入库
	 * @param string 
	 * @param entity
	 */
	public String hrmInstanceToStorage(String billConfigId,String billInstanceId,String guid,String type, String userId);
	/**
	 * 人入库
	 * @param entity
	 */
	public void addPersonToStorage(String billConfigId,String billInstanceId,String staffId,String deptId);
}
