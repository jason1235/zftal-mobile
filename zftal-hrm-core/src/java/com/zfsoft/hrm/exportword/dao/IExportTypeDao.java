package com.zfsoft.hrm.exportword.dao;

import java.util.List;

import com.zfsoft.hrm.exportword.entity.ExportType;

/**
 * 
 * @author ChenMinming
 * @date 2014-8-26
 * @version V1.0.0
 */
public interface IExportTypeDao {

	public ExportType findById(String id);
	
	public void delete(String id);

	public void update(ExportType examineConfig);

	public void insert(ExportType examineConfig);
	
	public List<ExportType> getTypeList(ExportType examineConfig);

}
