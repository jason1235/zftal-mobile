package com.zfsoft.hrm.summary.roster.dao.daointerface;

import com.zfsoft.hrm.summary.roster.entity.RosterParam;

/** 
 * 花名册参数
 * @author jinjj
 * @date 2012-11-20 下午04:26:54 
 *  
 */
public interface IRosterParamDao {

	/**
	 * 保存
	 * @param param
	 */
	public void insert(RosterParam param);
	
	/**
	 * 读取
	 * @param param
	 * @return
	 */
	public RosterParam getById(RosterParam param);
	
	/**
	 * 删除
	 * @param param
	 */
	public void delete(RosterParam param);
}
