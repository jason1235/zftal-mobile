package com.zfsoft.hrm.summary.roster.service.svcinterface;

import com.zfsoft.hrm.summary.roster.entity.RosterParam;

/** 
 * 花名册参数
 * @author jinjj
 * @date 2012-11-21 上午08:46:45 
 *  
 */
public interface IRosterParamService {

	/**
	 * 保存参数
	 * @param param
	 */
	public void save(RosterParam param);
	
	/**
	 * 读取参数
	 * @param param
	 * @return
	 */
	public RosterParam getById(RosterParam param);
}
