package com.zfsoft.hrm.seniorreport.dao;

import java.util.Map;
import java.util.List;

import com.zfsoft.hrm.report.entity.ReportQuery;
import com.zfsoft.hrm.seniorreport.entity.SeniorReportConfigData;

/**
 * 
 * @author ChenMinming
 * @date 2013-12-24
 * @version V1.0.0
 */
public interface ISeniorReportDao {
	public void insert(SeniorReportConfigData data);
	
	public void update(SeniorReportConfigData data);
	
	public void delete(SeniorReportConfigData data);
	
	public List<SeniorReportConfigData> findList(SeniorReportConfigData query);
	
	public int count(ReportQuery query);
	
	public String find(Map<String, String> query);
}
