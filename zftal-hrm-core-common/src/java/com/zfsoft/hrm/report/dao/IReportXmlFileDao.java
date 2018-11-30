package com.zfsoft.hrm.report.dao;

import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.report.entity.ReportQuery;
import com.zfsoft.hrm.report.entity.ReportXmlFile;

public interface IReportXmlFileDao {
	
	public void insert(ReportXmlFile entity);
	
	public void update(ReportXmlFile entity);
	
	public void delete(ReportXmlFile entity);
	
	public List<ReportXmlFile> findByQuery(ReportXmlFile entity);
	
	public int count(ReportQuery query);
	
	public List<Map<String, Object>> getPagingList(ReportQuery query);
	
	public List<String> snapTimeList(ReportQuery query);
}
