package com.base.xtgl.dao;

import java.util.List;
import java.util.Map;

import com.base.xtgl.entity.IndexModel;
import com.base.xtgl.entity.MenuPermissionEntity;

public interface IMenuDao {
	public int getListCount(IndexModel query);

	public List<IndexModel> getList(IndexModel query);

	public void delete(String gnmkdm);

	public void insertMenu(IndexModel model);

	public void update(IndexModel model);

	public void updateSfqy(IndexModel model);
	
	public List<IndexModel> getAllList();

	public List<MenuPermissionEntity> getPermissionList(
			MenuPermissionEntity menuQuery);

	public List<MenuPermissionEntity> getNotPermissionList(
			MenuPermissionEntity menuQuery);

	public void insertYhMenu(Map<String, Object> param);

	public void deleteYhMenu(Map<String, Object> param);

	public int yanzheng(Map<String, Object> param);

	public List<IndexModel> getAllListByUser();

}
