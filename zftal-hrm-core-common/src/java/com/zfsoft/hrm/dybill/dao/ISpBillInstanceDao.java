package com.zfsoft.hrm.dybill.dao;

import java.util.List;

import com.zfsoft.dao.annotation.BaseAnDao;
import com.zfsoft.hrm.dybill.entity.SpBillInstance;

public interface ISpBillInstanceDao extends BaseAnDao<SpBillInstance>{
	/**
	 * 通过Ids查询
	 * @param spBillInstance
	 * @return
	 */
	public List<SpBillInstance> findListByIds(SpBillInstance spBillInstance);
}
