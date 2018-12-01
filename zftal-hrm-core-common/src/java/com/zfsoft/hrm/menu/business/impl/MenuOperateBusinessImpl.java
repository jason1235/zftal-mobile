package com.zfsoft.hrm.menu.business.impl;

import java.util.List;

import com.zfsoft.hrm.menu.business.IMenuOperateBusiness;
import com.zfsoft.hrm.menu.dao.IMenuOperateDao;
import com.zfsoft.hrm.menu.entity.MenuOperate;

/** 
 * @author jinjj
 * @date 2013-2-25 下午03:17:32 
 *  
 */
public class MenuOperateBusinessImpl implements IMenuOperateBusiness {

	private IMenuOperateDao menuOperateDao;
	
	@Override
	public void insert(MenuOperate operate){
		MenuOperate old = menuOperateDao.getEntity(operate);
		if(old == null)
			menuOperateDao.insert(operate);
	}
	
	@Override
	public void deleteByMenuId(String menuId){
		menuOperateDao.deleteByMenuId(menuId);
	}

	public void setMenuOperateDao(IMenuOperateDao menuOperateDao) {
		this.menuOperateDao = menuOperateDao;
	}

	@Override
	public List<MenuOperate> getByMenuId(String menuId) {
		MenuOperate query = new MenuOperate();
		query.setMenuId(menuId);
		return menuOperateDao.getList(query);
	}
	
}
