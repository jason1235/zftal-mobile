package com.zfsoft.hrm.dybill.service;

/**
 * 表单整体规则校验
 * @author Patrick Shen
 */
public interface ISpBillCheckService {
	/**
	 * 整体检查
	 * @param billConfigId
	 * @param billInstanceId
	 * @return
	 */
	public String intanceCheck(String billConfigId,String billInstanceId, String billClassIdsAndPrivilege);
	/**
	 * 单类检查
	 * @param billConfigId
	 * @param billInstanceId
	 * @param billClassId
	 * @return
	 */
	public String intanceCheckSingle(String billConfigId,String billInstanceId,Long billClassId);
}
