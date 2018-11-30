package com.zfsoft.hrm.menu.dao;

import java.util.List;

import com.zfsoft.dao.entities.ApiIndexModel;

public interface IApiMenuDao {

	public int getListCount(ApiIndexModel query);

	public List<ApiIndexModel> getList(ApiIndexModel query);

	public void delete(String gnmkdm);

	public void insertMenu(ApiIndexModel model);

	public void update(ApiIndexModel model);

	public void updateSfqy(ApiIndexModel model);
	
	public List<ApiIndexModel> getAllList();
}
