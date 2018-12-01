package com.zfsoft.hrm.staffturn.retire.dao.daointerface;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.staffturn.retire.entities.RetireInfo;

/** 
 * 退休检测DAO
 * @author jinjj
 * @date 2012-7-31 上午03:08:38 
 *  
 */
public interface IRetireScanDao {

	/**
	 * 获取预退休的人员集合
	 * @param paraMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<RetireInfo> getRetireList(Map<String,Object> paraMap) throws DataAccessException;
}
