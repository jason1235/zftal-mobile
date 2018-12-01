package com.zfsoft.hrm.dybill.dao;

import java.util.List;

import com.zfsoft.dao.annotation.BaseAnDao;
import com.zfsoft.hrm.dybill.entity.SpBillInstanceLog;;

/**
 * 
 * @author ChenMinming
 * @date 2013-8-27
 * @version V1.0.0
 */
public interface ISpBillInstanceLogDao extends BaseAnDao<SpBillInstanceLog>{
	/**
	 * 通过Ids查询
	 * @param spBillInstance
	 * @return
	 */
	public List<SpBillInstanceLog> findListByInstanceId(SpBillInstanceLog spBillInstanceLog);
	
	public void deleteByInstanceId(SpBillInstanceLog spBillInstanceLog);
	
	public void findPreviousLog(SpBillInstanceLog spBillInstanceLog);
}
