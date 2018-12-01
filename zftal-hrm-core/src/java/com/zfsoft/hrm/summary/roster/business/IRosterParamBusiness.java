package com.zfsoft.hrm.summary.roster.business;

import com.zfsoft.hrm.summary.roster.entity.RosterParam;

/** 
 * 花名册参数
 * @author jinjj
 * @date 2012-11-20 下午04:35:04 
 *  
 */
public interface IRosterParamBusiness {

	/**
	 * 保存参数，并删除旧参数
	 * @param param
	 */
	public void save(RosterParam param);
	
	/**
	 * 读取参数
	 * @param param
	 * @return
	 */
	public RosterParam getById(RosterParam param);
	
	/**
	 * 删除参数
	 * @param param
	 */
	public void delete(RosterParam param);
}
