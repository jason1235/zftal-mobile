package com.zfsoft.hrm.baseinfo.custommenu.dao;

import java.util.List;

import com.zfsoft.hrm.baseinfo.custommenu.entity.CustomMenu;
import com.zfsoft.hrm.baseinfo.custommenu.query.CustomMenuQuery;

/** 
 * @author jinjj
 * @date 2012-12-18 下午02:19:09 
 *  
 */
public interface ICustomMenuDao {

	public void insert(CustomMenu custom);
	
	public void update(CustomMenu custom);
	
	public void delete(String menuId);
	
	public CustomMenu getById(String menuId);
	
	public List<CustomMenu> getPagingList(CustomMenuQuery query);
	
	public int getPagingCount(CustomMenuQuery query);
	
}
