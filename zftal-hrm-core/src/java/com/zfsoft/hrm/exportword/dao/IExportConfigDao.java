package com.zfsoft.hrm.exportword.dao;

import java.util.List;

import com.zfsoft.hrm.exportword.entity.ExportConfig;
import com.zfsoft.hrm.exportword.query.ExportConfigQuery;

/**
 * 
 * @author ChenMinming
 * @date 2014-8-25
 * @version V1.0.0
 */
public interface IExportConfigDao {
	public ExportConfig findById(String id);
	
	public void delete(String id);

	public void update(ExportConfig examineConfig);

	public void insert(ExportConfig examineConfig);
	
	public List<ExportConfig> getConfigList(ExportConfig examineConfig);
	
	public int getPagingCount(ExportConfigQuery query);
	
	public  List<ExportConfig> getPagingList(ExportConfigQuery query);
	
	
}
