package com.base.xtgl.service;

import java.util.List;
import java.util.Map;

import com.base.xtgl.entity.IndexModel;
import com.base.xtgl.entity.MenuPermissionEntity;
import com.zfsoft.dao.page.PageList;

public interface IMenuBusiness {
	
	PageList<IndexModel> getMenu(IndexModel query);

	void remove(String gnmkdm);

	void insertMenu(IndexModel model);

	void modify(IndexModel model);

	void updateSfqy(IndexModel model);
	
	List<IndexModel> getAllList();

	List<MenuPermissionEntity> getPermissionList(MenuPermissionEntity menuQuery);

	List<MenuPermissionEntity> getNotPermissionList(
			MenuPermissionEntity menuQuery);

	void insertYhMenu(Map<String, Object> param);

	void deleteYhMenu(Map<String, Object> param);
	
	public boolean yanzheng(String path);

	List<IndexModel> getAllListByUser();
}
