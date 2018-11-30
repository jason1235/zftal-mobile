package com.zfsoft.hrm.baseinfo.custommenu.service;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.custommenu.entity.CustomMenu;
import com.zfsoft.hrm.baseinfo.custommenu.query.CustomMenuQuery;
import com.zfsoft.hrm.config.Direction;

/** 
 * @author jinjj
 * @date 2012-12-18 下午03:34:14 
 *  
 */
public interface ICustomMenuService {

	public void save(CustomMenu custom);
	
	public void delete(String menuId);
	
	public void update(CustomMenu custom);
	
	public CustomMenu getById(String menuId);
	
	public PageList<CustomMenu> getPageList(CustomMenuQuery query);
	
	public void doMenuMove(String menuId,Direction dirc);
}
