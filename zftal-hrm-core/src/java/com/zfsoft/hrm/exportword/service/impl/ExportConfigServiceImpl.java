package com.zfsoft.hrm.exportword.service.impl;

import java.util.List;
import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.exportword.dao.IExportConfigDao;
import com.zfsoft.hrm.exportword.dao.IExportTypeDao;
import com.zfsoft.hrm.exportword.entity.ExportConfig;
import com.zfsoft.hrm.exportword.entity.ExportType;
import com.zfsoft.hrm.exportword.query.ExportConfigQuery;
import com.zfsoft.hrm.exportword.service.IExportConfigService;
import com.zfsoft.util.base.StringUtil;

/**
 * 
 * @author ChenMinming
 * @date 2014-8-25
 * @version V1.0.0
 */
public class ExportConfigServiceImpl implements IExportConfigService{
	
	private IExportConfigDao exportConfigDao;
	private IExportTypeDao exportTypeDao;


	public void setExportConfigDao(IExportConfigDao exportConfigDao) {
		this.exportConfigDao = exportConfigDao;
	}
	public void setExportTypeDao(IExportTypeDao exportTypeDao) {
		this.exportTypeDao = exportTypeDao;
	}

	@Override
	public void deleteConfig(String id) {
		exportConfigDao.delete(id);
	}


	@Override
	public void deleteType(String id) {
		exportTypeDao.delete(id);
	}


	@Override
	public ExportConfig findConfigById(String id) {
		return exportConfigDao.findById(id);
	}


	@Override
	public ExportType findTypeById(String id) {
		return exportTypeDao.findById(id);
	}


	@Override
	public List<ExportConfig> getConfigList(ExportConfig examineConfig) {
		return exportConfigDao.getConfigList(examineConfig);
	}


	@Override
	public List<ExportType> getTypeList(ExportType exportType) {
		return exportTypeDao.getTypeList(exportType);
	}


	@Override
	public void saveConfig(ExportConfig examineConfig) {
		if(StringUtil.isEmpty(examineConfig.getId())){
			examineConfig.setCreator(SessionFactory.getUser().getYhm());
			exportConfigDao.insert(examineConfig);
		}
		else{
			exportConfigDao.update(examineConfig);
		}
	}


	@Override
	public void saveType(ExportType exportType) {
		if(StringUtil.isEmpty(exportType.getId())){
			exportTypeDao.insert(exportType);
		}
		else{
			exportTypeDao.update(exportType);
		}
	}
	@Override
	public PageList<ExportConfig> getPageList(ExportConfigQuery query) {
		PageList<ExportConfig> pageList = new PageList<ExportConfig>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(exportConfigDao.getPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<ExportConfig> list = exportConfigDao.getPagingList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

}
