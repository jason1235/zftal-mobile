package com.zfsoft.hrm.menu.service;

import java.util.List;

import com.zfsoft.dao.entities.IndexModel;
import com.zfsoft.hrm.menu.entity.MenuOperate;


public interface IMenuService {
	/**
	 * 插入顶级菜单
	 * @param model
	 */
	public void addMenuSuper(String name);
	/**
	 * 插入二级菜单
	 * @param model
	 */
	public void addMenuTwo(String name);
	/**
	 * 插入三级菜单
	 * @param model
	 */
	public void addMenuThree(String name, String catalogName, String menuId,boolean commitable);
	/**
	 * 删除菜单
	 * @param model
	 */
	public void remove(String name,int level);
	/**
	 * 删除菜单
	 * @param model
	 */
	public void modify(IndexModel model);
	/**
	 * 通过名称和层级获取
	 * @param name
	 * @param level
	 * @return
	 */
	public IndexModel getByNameAndLevel(String name, int level);
	/**
	 * 通过Url查找
	 * @param name
	 * @return
	 */
	public IndexModel getByUrl(String url);
	/**
	 * 通过ID查询
	 * @param id
	 * @return
	 */
	public IndexModel getById(String id);
	
	/**
	 * 为admin用户添加操作权限
	 * @param id
	 */
	public void addCzForAdmin(String menuId);
	/**
	 * 通过菜单获取操作
	 * @param menuId
	 * @return
	 */
	public List<MenuOperate> getOperateByMenuId(String menuId);
	/**
	 * 通过层级获取
	 * @param i
	 * @return
	 */
	public List<IndexModel> getByLevel(int i);
}
