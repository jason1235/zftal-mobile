package com.zfsoft.hrm.seniorreport.dao;

import java.util.List;
import com.zfsoft.hrm.seniorreport.entity.SeniorReportHistroy;

/**
 * 
 * @author ChenMinming
 * @date 2014-1-26
 * @version V1.0.0
 */
public interface ISeniorReportHistroyDao {
	
	public void insert(SeniorReportHistroy data);
	
	public void delete(String id);
	
	public List<SeniorReportHistroy> findList(SeniorReportHistroy query);
}
