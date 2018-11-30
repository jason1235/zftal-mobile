package com.zfsoft.hrm.baseinfo.custommenu.business.impl;

import java.util.List;

import net.sf.json.JSONArray;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.baseinfo.custommenu.business.ICustomMenuBusiness;
import com.zfsoft.hrm.baseinfo.custommenu.dao.ICustomMenuDao;
import com.zfsoft.hrm.baseinfo.custommenu.entity.Condition;
import com.zfsoft.hrm.baseinfo.custommenu.entity.CustomMenu;
import com.zfsoft.hrm.baseinfo.custommenu.query.CustomMenuQuery;
import com.zfsoft.hrm.core.exception.RuleException;

/** 
 * @author jinjj
 * @date 2012-12-18 下午03:41:09 
 *  
 */
public class CustomMenuBusinessImpl implements ICustomMenuBusiness {

	private ICustomMenuDao customMenuDao;
	
	@Override
	public void save(CustomMenu custom) {
		CustomMenu old = customMenuDao.getById(custom.getMenuId());
		if(old != null){
			throw new RuleException("分配的菜单号已使用，请重试");
		}
		customMenuDao.insert(custom);
	}

	@Override
	public void delete(String menuId) {
		customMenuDao.delete(menuId);
	}

	@Override
	public void update(CustomMenu custom) {
		customMenuDao.update(custom);
	}

	@Override
	public CustomMenu getById(String menuId) {
		return customMenuDao.getById(menuId);
	}

	@Override
	public PageList<CustomMenu> getPageList(CustomMenuQuery query) {
		PageList<CustomMenu> pageList = new PageList<CustomMenu>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(customMenuDao.getPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<CustomMenu> customMenus = customMenuDao.getPagingList(query);
				handle(customMenus);
				pageList.addAll(customMenus);
			}
		}
		return pageList;
	}

	private void handle(List<CustomMenu> customMenus){
		for(CustomMenu customMenu : customMenus){
			JSONArray jsonArray = JSONArray.fromObject(customMenu.getConditonJson());
			customMenu.setConditions(JSONArray.toList(jsonArray, Condition.class));
		}
	}
	
	public void setCustomMenuDao(ICustomMenuDao customMenuDao) {
		this.customMenuDao = customMenuDao;
	}

}
