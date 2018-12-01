package com.zfsoft.hrm.menu.dao;

import java.util.List;

import com.zfsoft.hrm.menu.entity.MenuOperate;

/** 
 * @author jinjj
 * @date 2013-2-25 下午03:02:26 
 *  
 */
public interface IMenuOperateDao {

	/**
	 * 插入
	 * @param operate
	 */
	public void insert(MenuOperate operate);
	
	/**
	 * 根据菜单ID删除操作标志(多个)
	 * @param menuId
	 */
	public void deleteByMenuId(String menuId);
	
	/**
	 * 查询
	 * @param operate
	 * @return
	 */
	public List<MenuOperate> getList(MenuOperate operate);
	/**
	 * 查询
	 * @param operate
	 * @return
	 */
	public MenuOperate getEntity(MenuOperate operate);
}
