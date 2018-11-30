package com.zfsoft.hrm.menu.business;

import java.util.List;

import com.zfsoft.hrm.menu.entity.MenuOperate;

/** 
 * @author jinjj
 * @date 2013-2-25 下午03:27:08 
 *  
 */
public interface IMenuOperateBusiness {

	public abstract void insert(MenuOperate operate);

	public abstract void deleteByMenuId(String menuId);
	
	public abstract List<MenuOperate> getByMenuId(String menuId);

}