package com.zfsoft.mobile.module.service;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.module.entity.Module;
import com.zfsoft.mobile.module.query.ModuleQuery;

public interface IModuleService {

	PageList<Module> getPageList(ModuleQuery query);
	
	void doSave(ModuleQuery query) throws Exception;
	
	Module findById(ModuleQuery query);
	
	void doControl(ModuleQuery query) throws Exception;
	
	List<Module> getAllModules();
}
