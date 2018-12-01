package com.zfsoft.mobile.module.dao;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.module.entity.Module;
import com.zfsoft.mobile.module.query.ModuleQuery;

public interface IModuleDao {
	
	PageList<Module> getPageList(ModuleQuery query);
	
	int getPageCount(ModuleQuery query);
	
	void insert(ModuleQuery query);
	
	Module findById(ModuleQuery query);
	
	void update(ModuleQuery query);
	
	void moduleControl(ModuleQuery query);
	
	List<Module> getAllModules();
}
