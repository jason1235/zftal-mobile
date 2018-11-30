package com.zfsoft.hrm.baseinfo.data.service;

import com.zfsoft.hrm.baseinfo.data.util.ImportDataValidator;

/**
 * 信息数据导入service 
 * @author jinjj
 * @date 2012-10-25 下午04:01:13 
 *  
 */
public interface IInfoClassDataService {

	/**
	 * 数据导入
	 * @param idv
	 * @throws Exception
	 */
	public void doDataImport(ImportDataValidator idv) throws Exception;
	
	/**
	 * 数据导入,工号可以为空
	 * @param idv
	 * @throws Exception
	 */
	public void doDataImportNoCheckGh(ImportDataValidator idv) throws Exception;
}
