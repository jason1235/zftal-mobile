package com.zfsoft.hrm.exportword.service;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.exportword.entity.ExportConfig;
import com.zfsoft.hrm.exportword.entity.ExportType;
import com.zfsoft.hrm.exportword.query.ExportConfigQuery;

/**
 * 
 * @author ChenMinming
 * @date 2014-8-25
 * @version V1.0.0
 */
public interface IExportConfigService {

	public ExportConfig findConfigById(String id);
	
	public ExportType findTypeById(String id);
	
	public void deleteConfig(String id);
	
	public void deleteType(String id);

	public void saveConfig(ExportConfig examineConfig);
	
	public void saveType(ExportType exportType);
	
	public List<ExportConfig> getConfigList(ExportConfig examineConfig);
	
	public List<ExportType> getTypeList(ExportType exportType);
	
	public PageList<ExportConfig> getPageList(ExportConfigQuery query);
}
