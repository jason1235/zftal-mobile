package com.zfsoft.hrm.menu.service.impl;

import java.util.List;

import com.zfsoft.dao.entities.IndexModel;
import com.zfsoft.hrm.menu.business.IMenuBusiness;
import com.zfsoft.hrm.menu.business.IMenuOperateBusiness;
import com.zfsoft.hrm.menu.entity.MenuOperate;
import com.zfsoft.hrm.menu.service.IMenuService;

public class MenuServiceImpl implements IMenuService{
	
	private IMenuBusiness menuBusiness;
	private IMenuOperateBusiness menuOperateBusiness;

	public void setMenuOperateBusiness(IMenuOperateBusiness menuOperateBusiness) {
		this.menuOperateBusiness = menuOperateBusiness;
	}

	@Override
	public void addMenuSuper(String name) {
		menuBusiness.addMenuSuper(name);
	}

	@Override
	public void addMenuTwo(String name) {
		menuBusiness.addMenuTwo(name);
	}

	@Override
	public void addMenuThree(String name, String catalogName, String menuId,boolean commitable) {
		menuBusiness.addMenuThree(name, catalogName, menuId,commitable);
	}
	@Override
	public IndexModel getByNameAndLevel(String name, int level){
		return menuBusiness.getByNameAndLevel(name, level);

	}
	@Override
	public void modify(IndexModel model) {
		menuBusiness.modify(model);
	}
	
	@Override
	public void remove(String name,int level) {
			menuBusiness.remove(name, level);
	}
	
	@Override
	public IndexModel getByUrl(String url) {
		return menuBusiness.getByUrl(url);
	}

	@Override
	public void addCzForAdmin(String menuId) {
		menuBusiness.addCzForAdmin(menuId);
	}

	public void setMenuBusiness(IMenuBusiness menuBusiness) {
		this.menuBusiness = menuBusiness;
	}

	@Override
	public IndexModel getById(String id) {
		return menuBusiness.getById(id);
	}

	@Override
	public List<MenuOperate> getOperateByMenuId(String menuId) {
		return menuOperateBusiness.getByMenuId(menuId);
	}

	@Override
	public List<IndexModel> getByLevel(int i) {
		return menuBusiness.getByLevel(i);
	}

}
