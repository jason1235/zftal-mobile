package com.zfsoft.hrm.menu.business;

import java.util.List;

import com.zfsoft.dao.entities.ApiIndexModel;
import com.zfsoft.dao.page.PageList;

public interface IApiMenuBusiness {

	PageList<ApiIndexModel> getMenu(ApiIndexModel query);

	void remove(String gnmkdm);

	void insertMenu(ApiIndexModel model);

	void modify(ApiIndexModel model);

	void updateSfqy(ApiIndexModel model);
	
	List<ApiIndexModel> getAllList();

}
