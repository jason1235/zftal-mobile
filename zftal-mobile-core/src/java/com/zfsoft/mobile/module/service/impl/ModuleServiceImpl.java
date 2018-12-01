package com.zfsoft.mobile.module.service.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.module.dao.IModuleDao;
import com.zfsoft.mobile.module.entity.Module;
import com.zfsoft.mobile.module.query.ModuleQuery;
import com.zfsoft.mobile.module.service.IModuleService;

public class ModuleServiceImpl implements IModuleService {

	private IModuleDao moduleDao;
	
	@Override
	public PageList<Module> getPageList(ModuleQuery query) {
		PageList<Module> pageList = new PageList<Module>();
		Paginator paginator = new Paginator();
		if (query != null) {
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage(query.getToPage());
			paginator.setItems(moduleDao.getPageCount(query));
			pageList.setPaginator(paginator);
			
			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				pageList.addAll(moduleDao.getPageList(query));
			}
		}
		return pageList;
	}

	@Override
	public void doSave(ModuleQuery query) throws Exception {

		if (query.getId() == null || "".equals(query.getId())) {
			moduleDao.insert(query);
		} else {
			moduleDao.update(query);
		}
	}

	@Override
	public Module findById(ModuleQuery query) {
		return moduleDao.findById(query);
	}

	@Override
	public void doControl(ModuleQuery query) throws Exception{
		moduleDao.moduleControl(query);
		
	}
	
	@Override
	public List<Module> getAllModules() {
		return moduleDao.getAllModules();
	}

	public IModuleDao getModuleDao() {
		return moduleDao;
	}

	public void setModuleDao(IModuleDao moduleDao) {
		this.moduleDao = moduleDao;
	}
	
	
	
}
